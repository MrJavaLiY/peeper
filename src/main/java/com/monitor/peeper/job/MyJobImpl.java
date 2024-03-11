package com.monitor.peeper.job;

import com.monitor.peeper.entity.JarDetailEntity;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.mode.StrategyOperate;
import com.monitor.peeper.pool.DataPool;
import com.monitor.peeper.service.FileOperService;
import com.monitor.peeper.utils.ResponseEntity;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class MyJobImpl extends DataPool implements MyJob {
    @Resource
    StrategyOperate strategyOperate;
    @Resource
    FileOperService fileOperService;

    @Override
    public void job1() {
        Date now = DateUtil.date();
        try {
            List<ServerMessage> conditionList = fileOperService.getData2View().getData();
            for (ServerMessage serverMessage : conditionList) {
                if (!serverMessage.isEnable()) {
                    continue;
                }
                String serverKey = serverMessage.getIndex(serverMessage);
                Object o = strategyOperate.
                        executeMethodSpring(serverMessage.getType(), serverMessage);
                ResponseEntity<List<JarDetailEntity>> responseEntity = (ResponseEntity<List<JarDetailEntity>>) o;
                List<JarDetailEntity> nowData = responseEntity.getData();

//这一步进行的是数据清洗，将本次拿回来的数据进行缓存
                for (JarDetailEntity datum : nowData) {
                    datum.setServerIndex(serverKey);
                    //获取当前服务器下以前的数据集
                    Map<Integer, JarDetailEntity> serverData = serverCleanPool.get(serverKey);
                    int keyPort = datum.getPort();
                    datum.setLastTime(now);
                    datum.setIp(serverMessage.getIp());
                    //如果这台服务还没有任何的数据集存在就创新的
                    if (serverData == null) {
                        serverCleanPool.put(serverKey, new ConcurrentHashMap<>());
                        serverData = serverCleanPool.get(serverKey);
                    }
                    //如果缓存中存在这个端口的数据，就标记在线，且更新在线时间和状态
                    if (serverData.containsKey(datum.getPort())) {
                        datum.setSurStatus(JarDetailEntity.ONLINE);
                    } else {
                        //说明不目前不存在这个数据，这条数据是新产生的，那就要更新状态为新上线
                        datum.setSurStatus(JarDetailEntity.NEW_OFFLINE);
                    }
                    serverData.put(keyPort, datum);
                }
            }
            //进行一个数据清洗结果的区分存储
            serverCleanPool.forEach((serverKey, v) -> {
                v.forEach((port, datum) -> {
                    //但凡经过了本轮清洗的数据，最后在线时间都是更新到了最新，时间不是最新的，说明就是存在于上次的数据，这种数据标记为离线并且存放到离线缓存中进行下一步处理
                    if (!datum.getLastTime().equals(now)) {
                        serverDeathPool.put(datum.getIndex(), datum);
                        //这一步是将这种已经离线的数据给清理出数据清洗池，避免后续心跳一直跳这个离线数据，造成相当多的数据重复
                        serverCleanPool.get(datum.getServerIndex()).remove(datum.getPort());
                        //还需要从存活池中把自己摘出来，避免两个一起提示
                        if (serverRealPool.containsKey(datum.getIndex())) {
                            serverRealPool.remove(datum.getIndex());
                        }
                    } else {
                        //存活数据队列
                        serverRealPool.put(datum.getIndex(), datum);
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}", e);
        }
        serverDeathPool.forEach((k, v) -> {
            Long diff = DateUtil.between(v.getLastTime(), now, DateUnit.SECOND);
            log.info("ip[{}]下端口[{}]服务名称[{}]已经下线[{}]秒了", v.getIp(), v.getPort(), v.getJarName(), diff);
            if (!v.isNotice()) {
                log.info("模拟钉钉消息推送");
                v.setNotice(true);
                v.setNoticeTime(now);
            } else {
                long interval = DateUtil.between(v.getNoticeTime(), now, DateUnit.SECOND);
                if (interval >= 30) {
                    log.info("时间到了，重新推送钉钉");
                    v.setNoticeTime(now);
                    v.setNotice(true);
                }
            }

        });
        serverRealPool.forEach((k, v) -> {
            log.info("ip[{}]下端口[{}]服務名稱[{}]在線[{}]", v.getIp(), v.getPort(), v.getJarName(), v.getLastTime());
        });

    }
}
