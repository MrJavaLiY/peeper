package com.monitor.peeper.pool;

import com.monitor.peeper.entity.JarDetailEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataPool {
    /**
     * 两层map。外层mapKey放服务器索引，里层key放服务启动端口
     */
    public Map<String,Map<Integer, JarDetailEntity>> serverCleanPool = new ConcurrentHashMap();
    /**
     * 离线数据队列,key是IP+用户名+端口
     */
    public Map<String, JarDetailEntity> serverDeathPool  = new ConcurrentHashMap<>();
    /**
     * 存活数据队列
     */
    public Map<String,JarDetailEntity> serverRealPool = new ConcurrentHashMap();


}
