package com.monitor.peeper.dataBase;

import com.alibaba.excel.read.listener.ReadListener;
import com.monitor.peeper.entity.excel.DataValue;
import com.monitor.peeper.entity.excel.NoticeConfig;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public class NoticeExcelDataBase extends ExcelDataBase<NoticeConfig>  {

    public NoticeExcelDataBase() {
        super();
        clazz= NoticeConfig.class;
        path=NOTICE_PATH;
        sheetName=NOTICE_SHEET_NAME;
    }


    @Override
    String getIndex(NoticeConfig noticeConfig) {
        return noticeConfig.getIndex();
    }
}

