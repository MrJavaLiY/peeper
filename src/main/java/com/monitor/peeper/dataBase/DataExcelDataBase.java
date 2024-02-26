package com.monitor.peeper.dataBase;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.monitor.peeper.entity.excel.DataValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DataExcelDataBase extends ExcelDataBase<DataValue> {

    public DataExcelDataBase() {
        super(DATA_PATH,DATA_SHEET_NAME,DataValue.class);
    }


    @Override
    String getIndex(DataValue dataValue) {
        return dataValue.getIp() + ":" + dataValue.getPort();
    }
}

