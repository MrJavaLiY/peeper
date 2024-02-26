package com.monitor.peeper.dataBase;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.monitor.peeper.entity.excel.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadFactory;

@Data
@Slf4j
public abstract class ExcelDataBase<T> implements ReadListener<T> {
    private static final String FOLDER = "data/";

    protected static final String SERVER_PATH = FOLDER + "server_message.xlsx";
    protected static final String SERVER_SHEET_NAME = "server";

    protected static final String DATA_PATH = FOLDER + "data_value.xlsx";
    protected static final String DATA_SHEET_NAME = "data";

    protected static final String SCHEDULER_PATH = FOLDER + "scheduler_log.xlsx";
    protected static final String SCHEDULER_SHEET_NAME = "log";

    protected static final String NOTICE_PATH = FOLDER + "notice_config.xlsx";
    protected static final String NOTICE_SHEET_NAME = "message";

    protected String path = "";
    protected String sheetName = "";
    protected Class<T> clazz;

    public ExcelDataBase() {
        guard();
        initMap();
    }

    public ExcelDataBase(String path, String sheetName, Class<T> clazz) {
        this.path = path;
        this.sheetName = sheetName;
        this.clazz = clazz;
        initMap();
    }

    /**
     * 缓存的数据
     */
    protected Set<T> cache = new CopyOnWriteArraySet<>();
    protected Map<String, T> cacheMap = null;

    /**
     * 需要实现，封装index
     *
     * @param t
     * @return
     */
    abstract String getIndex(T t);

    protected void transMap() {
        initMap();
        cache.forEach(c -> {
            cacheMap.put(getIndex(c), c);
        });
    }

    protected void initMap() {
        if (cacheMap != null) {
            cacheMap.clear();
        } else {
            cacheMap = new ConcurrentHashMap<>();
        }
    }

    protected Map<String, T> newDataToMap(List<T> newData) {
        Map<String, T> newDataMap = new HashMap<>(newData.size());
        newData.forEach(c -> {
            newDataMap.put(getIndex(c), c);
        });
        return newDataMap;
    }

    public void addCache(T data) {
        cache.add(data);
        transMap();
    }

    public Set<T> getAll() {
        return cache;
    }

    //    abstract List<T> read();
    public Set<T> read() {
        EasyExcel.read(path, clazz, this).sheet().doRead();
        return getAll();
    }


    /**
     * 直接覆盖数据
     *
     * @param data
     */
    public void cover(List<T> data) {
        EasyExcel.write(path, clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 对比式新增数据
     *
     * @param newData
     */
    public void add(List<T> newData) {
        Map<String, T> newDataMap = newDataToMap(newData);
        List<T> dataAll = new ArrayList<>(cache);
        if (cacheMap == null || cacheMap.isEmpty()) {
            dataAll = newData;
        } else {
            List<T> finalDataAll = dataAll;
            newDataMap.forEach((k, v) -> {
                if (!cacheMap.containsKey(k)) {
                    finalDataAll.add(v);
                }
            });
        }
        cover(dataAll);
    }


    @Override
    public void invoke(T data, AnalysisContext context) {
        addCache(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("文件读取完成！");
    }

    private void guard() {
        Guard guard = new Guard();
        guard.start();
    }

    class Guard extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(30000);
                    cache.clear();
                    initMap();
                    read();
                    log.info("缓存已更新=================");
                    log.debug(cache.toString());
                    log.debug(cacheMap.toString());
                } catch (Exception e) {
                    log.error("{}", e);
                    break;
                }
            }
        }
    }

}

