package com.monitor.peeper.job;

import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.entity.WinCmdEntity;
import com.monitor.peeper.mode.StrategyOperate;
import com.monitor.peeper.pool.DataPool;
import com.monitor.peeper.service.FileOperService;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utils.ResponseEntity;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class MyJobImpl implements MyJob {
    @Resource
    StrategyOperate strategyOperate;
    @Resource
    FileOperService fileOperService;
    @Resource
    DataPool dataPool;

    @Override
    public void job1() {
        Date now = DateUtil.date();
        try {
            List<RequestCondition> conditionList = fileOperService.outEntity();
            for (RequestCondition condition : conditionList) {
                String ip = condition.getIp();
                Object o = strategyOperate.
                        executeMethodSpring(condition.getServerType(), condition);
                ResponseEntity<List<WinCmdEntity>> responseEntity = (ResponseEntity<List<WinCmdEntity>>) o;
                List<WinCmdEntity> data = responseEntity.getData();
                for (WinCmdEntity datum : data) {
                    Map<Integer, WinCmdEntity> serverData = dataPool.serverRealPool.get(ip);
                    int key = datum.getPort();
                    datum.setLastTime(now);
                    datum.setIp(condition.getIp());
                    if (serverData == null) {
                        dataPool.serverRealPool.put(ip, new ConcurrentHashMap<>());
                        serverData = dataPool.serverRealPool.get(ip);
                    }
                    serverData.put(key, datum);
                }
            }
            dataPool.serverRealPool.forEach((k, v) -> {
                v.forEach((k1, v1) -> {
                    if (!v1.getLastTime().equals(now)) {
                        dataPool.serverDeathPool.put(v1.getIp() + ":" + v1.getPort(), v1);
                        dataPool.serverRealPool.get(v1.getIp()).remove(v1.getPort());
                    } else if (dataPool.serverDeathPool.containsKey(v1.getIp() + ":" + v1.getPort())) {
                        dataPool.serverDeathPool.remove(v1.getIp() + ":" + v1.getPort());
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}", e);
        }
        dataPool.serverDeathPool.forEach((k, v) -> {
            Long diff = DateUtil.between(v.getLastTime(), now, DateUnit.SECOND);
            log.info("ip[{}]下端口[{}]服務名稱[{}]已下線[{}]秒了", v.getIp(), v.getPort(), v.getJarName(), diff);
        });
        dataPool.serverRealPool.forEach((k, v) -> {
            v.forEach((k1, v1) -> {
                log.info("ip[{}]下端口[{}]服務名稱[{}]在線[{}]", v1.getIp(), v1.getPort(), v1.getJarName(), v1.getLastTime());
            });
        });
    }
}
