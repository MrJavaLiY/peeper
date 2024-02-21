package com.monitor.peeper.dataBase;

import com.monitor.peeper.entity.excel.ServerMessage;

public class ServerExcelDataBase extends ExcelDataBase {

    public ServerExcelDataBase() {
        super();
        clazz=ServerMessage.class;
        path=SERVER_PATH;
        sheetName=SERVER_SHEET_NAME;
    }

}

