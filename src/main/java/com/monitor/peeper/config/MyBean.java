package com.monitor.peeper.config;

import com.monitor.peeper.mode.StrategyOperate;
import com.monitor.peeper.service.LinuxService;
import com.monitor.peeper.service.WinService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBean {

    @Bean
    public StrategyOperate strategyOperate() {
        return new StrategyOperate(WinService.class, LinuxService.class);
    }
}
