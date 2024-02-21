package com.monitor.peeper.dataBase;

import com.monitor.peeper.entity.excel.DataValue;

public class DataExcelDataBase extends ExcelDataBase {

    public DataExcelDataBase() {
        super();
        clazz=DataValue.class;
        path=DATA_PATH;
        sheetName=DATA_SHEET_NAME;
    }

}

