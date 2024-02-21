package com.monitor.peeper.dataBase;

import com.alibaba.excel.EasyExcel;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.listener.DataListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ExcelDataBase {
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
    protected Class<?> clazz;



    /**
     * 直接覆盖数据
     *
     * @param data
     */
    public void cover(List data) {
        EasyExcel.write(path, clazz).sheet(sheetName).doWrite(data);
    }




    public static void main(String[] args) {
        List<ServerMessage> messages = new ArrayList<>();
        ServerMessage message = new ServerMessage();
        message.setIp("127.0.0.1");
        message.setUser("123");
        message.setPassword("1231231232131");
        messages.add(message);
        EasyExcel.write(new ExcelDataBase().SERVER_PATH, ServerMessage.class).sheet("server").doWrite(messages);

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read("data/server_message.xlsx", ServerMessage.class, new DataListener()).sheet().doRead();
    }


}

