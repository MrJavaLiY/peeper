package com.monitor.peeper.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.monitor.peeper.entity.excel.DataValue;
import com.monitor.peeper.entity.excel.ExcelEntity;
import com.monitor.peeper.entity.excel.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public abstract class DataListener<T> implements ReadListener<T> {


    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("文件读取完成！");
    }

}
