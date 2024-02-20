package com.monitor.peeper.pool;

import com.monitor.peeper.entity.WinCmdEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataPool {
    public Map<String,Map<Integer, WinCmdEntity>> serverRealPool = new ConcurrentHashMap();
    public Map<String,WinCmdEntity> serverDeathPool  = new ConcurrentHashMap<>();

}
