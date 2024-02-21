package com.monitor.peeper.dataBase;

import com.monitor.peeper.entity.excel.NoticeConfig;

public class NoticeExcelDataBase extends ExcelDataBase {

    public NoticeExcelDataBase() {
        super();
        clazz= NoticeConfig.class;
        path=NOTICE_PATH;
        sheetName=NOTICE_SHEET_NAME;
    }

}

