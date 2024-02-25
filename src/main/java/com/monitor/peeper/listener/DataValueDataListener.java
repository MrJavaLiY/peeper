package com.monitor.peeper.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.monitor.peeper.entity.excel.DataValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class DataValueDataListener extends DataListener<DataValue> {
    @Resource
    CachePool cachePool;

    @Override
    public void invoke(DataValue data, AnalysisContext context) {
        cachePool.addCache(data);
    }
}
