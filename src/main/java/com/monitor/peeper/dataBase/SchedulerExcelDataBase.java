package com.monitor.peeper.dataBase;

import com.monitor.peeper.entity.excel.SchedulerLog;

public class SchedulerExcelDataBase extends ExcelDataBase {

    public SchedulerExcelDataBase() {
        super();
        clazz=SchedulerLog.class;
        path=SCHEDULER_PATH;
        sheetName=SCHEDULER_SHEET_NAME;
    }

}

