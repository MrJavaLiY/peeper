package com.monitor.peeper.dataBase;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.monitor.peeper.entity.excel.DataValue;
import com.monitor.peeper.entity.excel.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ServerExcelDataBase extends ExcelDataBase<ServerMessage> {

    public ServerExcelDataBase() {
        super();
        clazz = ServerMessage.class;
        path = SERVER_PATH;
        sheetName = SERVER_SHEET_NAME;
    }


    @Override
    String getIndex(ServerMessage serverMessage) {
        return null;
    }
}

