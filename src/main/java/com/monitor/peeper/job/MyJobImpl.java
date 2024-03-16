package com.monitor.peeper.job;

import com.monitor.peeper.mode.StrategyOperate;
import com.monitor.peeper.pool.DataPool;
import com.monitor.peeper.service.FileOperService;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
        Date now = new Date();
        dataPool.serverDeathPool.forEach((k, v) -> {
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
        dataPool.serverRealPool.forEach((k, v) -> {
            log.info("ip[{}]下端口[{}]服務名稱[{}]在線[{}]", v.getIp(), v.getPort(), v.getJarName(), v.getLastTime());
        });

    }
}
