package com.monitor.peeper.dataBase;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Data
@Slf4j
public abstract class ExcelDataBase<T> implements ReadListener<T> {
    /**
     * 基础数据存储位置
     */
    private static final String FOLDER = "data/";
    /**
     * 服务器信息存储位置
     */
    protected static final String SERVER_PATH = FOLDER + "server_message.xlsx";
    protected static final String SERVER_SHEET_NAME = "server";
    /**
     * 数据存储位置
     */
    protected static final String DATA_PATH = FOLDER + "data_value.xlsx";
    protected static final String DATA_SHEET_NAME = "data";
    /**
     * 任务调度存储位置
     */
    protected static final String SCHEDULER_PATH = FOLDER + "scheduler_log.xlsx";
    protected static final String SCHEDULER_SHEET_NAME = "log";
    /**
     * 外部消息通知存储位置
     */
    protected static final String NOTICE_PATH = FOLDER + "notice_config.xlsx";
    protected static final String NOTICE_SHEET_NAME = "message";
    /**
     * 服务名称存储位置
     */
    protected static final String SERVICE_NAME_PATH = FOLDER + "serice_name.xlsx";
    protected static final String SERVICE_NAME_SHEET_NAME = "name";

    /**
     * EasyExcel使用参数
     */
    protected String path = "";
    protected String sheetName = "";
    protected Class<T> clazz;

    /**
     * 数据缓存池
     */
    protected Set<T> cache = new CopyOnWriteArraySet<>();
    protected Map<String, T> cacheMap = null;


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
     * 需要实现，封装index
     *
     * @param t
     * @return
     */
    abstract String getIndex(T t);

    /**
     * 将set转换成map，好检索
     */
    protected void transMap() {
        initMap();
        cache.forEach(c -> {
            cacheMap.put(getIndex(c), c);
        });
    }

    /**
     * 初始化缓存map
     */
    protected synchronized void initMap() {
        if (cacheMap != null) {
            cacheMap.clear();
        } else {
            cacheMap = new ConcurrentHashMap<>();
        }
    }

    /**
     * 传入list转为map
     *
     * @param newData 存数据的list
     * @return
     */
    protected Map<String, T> newDataToMap(List<T> newData) {
        Map<String, T> newDataMap = new HashMap<>(newData.size());
        newData.forEach(c -> {
            newDataMap.put(getIndex(c), c);
        });
        return newDataMap;
    }

    /**
     * 单条数据转map
     *
     * @param datum 单条业务数据
     * @return
     */
    private Map<String, T> newDatumMap(T datum) {
        Map<String, T> newDataMap = new HashMap<>(1);
        newDataMap.put(getIndex(datum), datum);
        return newDataMap;
    }

    /**
     * 添加缓存
     *
     * @param data
     */
    public void addCache(T data) {
        cache.add(data);
        transMap();
    }

    public T getCacheOne(String index) {
        if (!cacheMap.containsKey(index)) {
            synchronized (this) {
                if (!cacheMap.containsKey(index)) {
                    read();
                }
            }
        }
        return cacheMap.getOrDefault(index, null);
    }

    /**
     * 获取所有缓存
     *
     * @return
     */
    public Set<T> getAll() {
        return cache;
    }

    /**
     * 读取数据
     *
     * @return
     */
    public Set<T> read() {
        EasyExcel.read(path, clazz, this).sheet().doRead();
        transMap();
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
    public void addAll(List<T> newData) {
        Map<String, T> newDataMap = newDataToMap(newData);
        List<T> dataAll;
        if (cacheMap == null || cacheMap.isEmpty()) {
            dataAll = newData;
        } else {
            dataAll = new ArrayList<>(cache);
            newDataMap.forEach((k, v) -> {
                if (!cacheMap.containsKey(k)) {
                    dataAll.add(v);
                }
            });
        }
        cover(dataAll);
    }

    public void add(T data) {
        Map<String, T> newDataMap = new HashMap<>();
        newDataMap.put(getIndex(data), data);
        List<T> dataAll;
        if (cacheMap == null || cacheMap.isEmpty()) {
            dataAll = new ArrayList<>();
            dataAll.add(data);
        } else {
            dataAll = new ArrayList<>(cache);
            newDataMap.forEach((k, v) -> {
                if (!cacheMap.containsKey(k)) {
                    dataAll.add(v);
                }
            });
        }
        cover(dataAll);
    }

    /**
     * 删除数据并刷新缓存
     *
     * @param datum
     */
    public void delete(T datum) {
        if (cacheMap.containsKey(getIndex(datum))) {
            cache.remove(cacheMap.get(getIndex(datum)));
            List<T> dataAll = new ArrayList<>(cache);
            cover(dataAll);
            initCache();
        }
    }

    public void update(T datum) {
        if (cacheMap.containsKey(getIndex(datum))) {
            cache.remove(cacheMap.get(getIndex(datum)));
            cache.add(datum);
            List<T> dataAll = new ArrayList<>(cache);
            cover(dataAll);
            initCache();
        }
    }

    /**
     * 有就修改，无就新增
     *
     * @param datum
     */
    public void reNew(T datum) {
        if (cacheMap.containsKey(getIndex(datum))) {
            update(datum);
        }else{
            add(datum);
        }
    }

    /**
     * 读取文件
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        addCache(data);
    }

    /**
     * 读取文件完成后最后执行的
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("文件读取完成！");
    }

    /**
     * 启动守护线程
     */
    private void guard() {
        Guard guard = new Guard();
        guard.start();
        log.info("======文件读取守护线程启动======");
    }

    /**
     * 缓存更新
     */
    protected void initCache() {
        cache.clear();
        initMap();
        read();
        log.debug("======缓存已更新 时间{}======", DateUtil.date());
    }

    /**
     * 守护线程
     */
    class Guard extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(30000);
                    initCache();
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

