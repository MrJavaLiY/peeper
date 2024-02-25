package com.monitor.peeper.dataBase;

import com.alibaba.excel.EasyExcel;
import com.monitor.peeper.entity.excel.ExcelEntity;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.listener.CachePool;
import com.monitor.peeper.listener.DataListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public abstract class ExcelDataBase<T> {
    private static final String FOLDER = "data";
    public  final String SERVER_PATH = FOLDER + "/server_message.xlsx";
    public  final String SERVER_SHEET_NAME = "server";
    public  final String DATA_PATH = FOLDER + "data_value.xlsx";
    public  final String DATA_SHEET_NAME = "data";
    public  final String SCHEDULER_PATH = FOLDER + "scheduler_log.xlsx";
    public  final String SCHEDULER_SHEET_NAME = "log";

    public  final String NOTICE_PATH = FOLDER + "notice_config.xlsx";
    public  final String NOTICE_SHEET_NAME = "message";
    protected String path = "";
    protected String sheetName = "";
    protected Class<T> clazz;

    abstract List<T> read();

    /**
     * 直接覆盖数据
     *
     * @param data
     */
    public void cover(List<T> data) {
        EasyExcel.write(path, clazz).sheet(sheetName).doWrite(data);
    }



}

