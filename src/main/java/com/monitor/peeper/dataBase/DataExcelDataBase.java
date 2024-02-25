package com.monitor.peeper.dataBase;

import com.alibaba.excel.EasyExcel;
import com.monitor.peeper.entity.excel.DataValue;
import com.monitor.peeper.entity.excel.ExcelEntity;
import com.monitor.peeper.listener.CachePool;
import com.monitor.peeper.listener.DataListener;
import com.monitor.peeper.listener.DataValueDataListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DataExcelDataBase extends ExcelDataBase<DataValue> {
    @Resource
    DataValueDataListener dataValueDataListener;
    @Resource
    CachePool cachePool;

    public DataExcelDataBase() {
        super();
        clazz = DataValue.class;
        path = DATA_PATH;
        sheetName = DATA_SHEET_NAME;
    }

    @Override
    List<DataValue> read() {
        EasyExcel.read(path, clazz, dataValueDataListener).sheet().doRead();
        return (List<DataValue>) cachePool.getAll(clazz);
    }
}

