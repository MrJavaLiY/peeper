package com.monitor.peeper.condition.vo;

import com.monitor.peeper.entity.excel.DataValue;
import lombok.Data;

import java.util.List;

@Data
public class DataValueVO {

    private int page;
    private int size;
    private int total;
    private List<DataValue> data;
}
