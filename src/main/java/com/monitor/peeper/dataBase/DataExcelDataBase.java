package com.monitor.peeper.dataBase;

import com.monitor.peeper.entity.excel.DataValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataExcelDataBase extends ExcelDataBase<DataValue> {

    public DataExcelDataBase() {
        super(DATA_PATH, DATA_SHEET_NAME, DataValue.class);
    }


    @Override
   public String getIndex(DataValue dataValue) {
        return dataValue.getIp() + ":" + dataValue.getPort();
    }
}

