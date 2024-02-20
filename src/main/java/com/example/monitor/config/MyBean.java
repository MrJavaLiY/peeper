package com.example.monitor.config;

import com.example.monitor.mode.StrategyOperate;
import com.example.monitor.service.LinuxService;
import com.example.monitor.service.WinService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MyBean {

    @Bean
    public StrategyOperate strategyOperate() {
        return new StrategyOperate(WinService.class, LinuxService.class);
    }
}
