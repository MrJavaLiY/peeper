package com.monitor.peeper.util;

import com.alibaba.excel.EasyExcel;
import com.monitor.peeper.entity.ServerMessage;
import com.monitor.peeper.listener.DataListener;
import org.apache.catalina.Server;

import java.util.ArrayList;
import java.util.List;

public class DataBaseUtil {

    private static final String SERVER_PATH = "data/server_message.xlsx";
    private static final String SERVER_SHEET_NAME = "server";

    /**
     * 直接覆盖数据
     *
     * @param data
     * @param clazz
     */
    public static void cover(List data, Class clazz) {
        String path = "";
        String sheet = "";
        if (clazz.equals(ServerMessage.class)) {
            path = SERVER_PATH;
            sheet = SERVER_SHEET_NAME;
        }
        EasyExcel.write(path, clazz).sheet(sheet).doWrite(data);
    }



    public static void main(String[] args) {
        List<ServerMessage> messages = new ArrayList<>();
        ServerMessage message = new ServerMessage();
        message.setIp("127.0.0.1");
        message.setUser("123");
        message.setPassword("1231231232131");
        messages.add(message);
        EasyExcel.write("", ServerMessage.class).sheet("server").doWrite(messages);

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read("data/server_message.xls", ServerMessage.class, new DataListener()).sheet().doRead();
    }


}

