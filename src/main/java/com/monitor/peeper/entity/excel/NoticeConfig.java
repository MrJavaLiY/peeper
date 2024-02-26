package com.monitor.peeper.entity.excel;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeConfig  extends ExcelEntity{
    private String value;
    private Date time;
}
