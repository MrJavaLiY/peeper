package com.monitor.peeper.entity.excel;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeConfig  extends ExcelEntity{
    private String value;
    private Date time;
}
