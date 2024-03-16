package com.monitor.peeper.dataBase;

import com.monitor.peeper.entity.excel.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
@Component
public class ServerExcelDataBase extends ExcelDataBase<ServerMessage> {

    public ServerExcelDataBase() {
        super();
        clazz = ServerMessage.class;
        path = SERVER_PATH;
        sheetName = SERVER_SHEET_NAME;
        initCache();
    }


    @Override
    public String getIndex(ServerMessage serverMessage) {
        return serverMessage.getIndex(serverMessage);
    }
}

