package com.example.monitor.pool;

import com.example.monitor.entity.WinCmdEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class DataPool {
    public Map<String,Map<Integer, WinCmdEntity>> serverRealPool = new ConcurrentHashMap();
    public Map<String,WinCmdEntity> serverDeathPool  = new ConcurrentHashMap<>();

}
