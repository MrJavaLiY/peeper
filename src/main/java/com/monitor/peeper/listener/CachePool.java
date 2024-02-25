package com.monitor.peeper.listener;

import com.alibaba.excel.util.ListUtils;
import com.mchange.v1.util.SetUtils;
import com.monitor.peeper.entity.excel.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class CachePool {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    /**
     * 缓存的数据
     */
    private static Map<String, ServerMessage> serverMessageCachedData = new ConcurrentHashMap<>();
    private static Map<String, DataValue> dataValueCachedData = new ConcurrentHashMap<>();
    private static Map<String, NoticeConfig> noticeConfigCachedData = new ConcurrentHashMap<>();
    private static Map<String, SchedulerLog> schedulerLogCachedData = new ConcurrentHashMap<>();

    private static Map<Class, Map> cacheMap = new ConcurrentHashMap<>();

    {
        cacheMap.put(ServerMessage.class, serverMessageCachedData);
        cacheMap.put(DataValue.class, dataValueCachedData);
        cacheMap.put(NoticeConfig.class, noticeConfigCachedData);
        cacheMap.put(SchedulerLog.class, schedulerLogCachedData);

    }

    public void initPool() {
        serverMessageCachedData = new ConcurrentHashMap<>();
        dataValueCachedData = new ConcurrentHashMap<>();
        noticeConfigCachedData = new ConcurrentHashMap<>();
        schedulerLogCachedData = new ConcurrentHashMap<>();
    }


    public ExcelEntity getCache(String index, Class<ExcelEntity> t) {
        if (t.equals(ServerMessage.class)) {
            return serverMessageCachedData.get(index);
        } else if (t.equals(DataValue.class)) {
            return dataValueCachedData.get(index);
        } else if (t.equals(NoticeConfig.class)) {
            return noticeConfigCachedData.get(index);
        } else if (t.equals(SchedulerLog.class)) {
            return schedulerLogCachedData.get(index);
        } else {
            return null;
        }

    }

    public void addCache(ExcelEntity data) {
        String index = data.getIndex();
        if (data.getClass().equals(ServerMessage.class)) {
            serverMessageCachedData.put(index, (ServerMessage) data);
        } else if (data.getClass().equals(DataValue.class)) {
            dataValueCachedData.put(index, (DataValue) data);
        } else if (data.getClass().equals(NoticeConfig.class)) {
            noticeConfigCachedData.put(index, (NoticeConfig) data);
        } else if (data.getClass().equals(SchedulerLog.class)) {
            schedulerLogCachedData.put(index, (SchedulerLog) data);
        }

    }

    public  List getAll(Class c) {
        List list = new ArrayList();
        cacheMap.get(c).forEach((k, v) -> {
            list.add(v);
        });
        return list;
    }


}
