package com.monitor.peeper.dataBase;

import com.monitor.peeper.entity.excel.ServerMessage;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class ServerExcelDataBase extends ExcelDataBase<ServerMessage> {

    public ServerExcelDataBase() {
        super();
        clazz = ServerMessage.class;
        path = SERVER_PATH;
        sheetName = SERVER_SHEET_NAME;
        initCache();
    }


    @Override
   protected String getIndex(ServerMessage serverMessage) {
        return serverMessage.getIp() + ":" + serverMessage.getUser();
    }
}

