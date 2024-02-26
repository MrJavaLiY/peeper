package com.monitor.peeper.dataBase;

import com.alibaba.excel.read.listener.ReadListener;
import com.monitor.peeper.entity.excel.DataValue;
import com.monitor.peeper.entity.excel.SchedulerLog;

import java.util.List;
import java.util.Map;

public class SchedulerExcelDataBase extends ExcelDataBase<SchedulerLog>  {

    public SchedulerExcelDataBase() {
        super();
        clazz=SchedulerLog.class;
        path=SCHEDULER_PATH;
        sheetName=SCHEDULER_SHEET_NAME;
    }


    @Override
    String getIndex(SchedulerLog schedulerLog) {
        return null;
    }
}

