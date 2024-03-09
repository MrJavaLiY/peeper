package com.monitor.peeper;

import com.alibaba.fastjson.JSONObject;
import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.controller.ServerControlController;
import com.monitor.peeper.dataBase.DataExcelDataBase;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.utils.ShellUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ServerControlTests {
    @Resource
    DataExcelDataBase dataExcelDataBase;
    @Resource
    ServerControlController controlController;

    @Test
    void contextLoads() {
    }

    @Test
    void add() throws Exception {
        RequestCondition condition = new RequestCondition();
        condition.setIp("192.168.0.107");
        condition.setUser("root");
        ServerMessage sm = new ServerMessage();
        sm.setIp("192.168.0.107");
        sm.setType("Linux");
        sm.setUser("root");
        sm.setPassword("980322");
        sm.setEnable("1");
        condition.setSm(sm);
        System.out.println(JSONObject.toJSONString(controlController.inConfig(condition)));
    }

    @Test
    void delete() throws Exception {
        RequestCondition condition = new RequestCondition();
        condition.setIp("127.0.0.1");
        condition.setServerType("Linux");
        condition.setUser("root");
        condition.setPassword("980322");
        condition.setEnable(true);
        System.out.println(JSONObject.toJSONString(controlController.delConfig(condition)));
    }

    @Test
    void update() throws Exception {
        RequestCondition condition = new RequestCondition();
        ServerMessage sm = new ServerMessage();
        sm.setIp("192.168.0.107");
        sm.setType("Linux");
        sm.setUser("root");
        sm.setPassword("980322");
        sm.setEnable("1");
        condition.setSm(sm);
        System.out.println(JSONObject.toJSONString(controlController.updateConfig(condition)));
    }

    public static void main(String[] args) throws Exception {
        ShellUtil shell = new ShellUtil("192.168.0.107", "root", "980322");
        String jpsValue = shell.exec("jstat -gc 2996");
        System.out.println("=========jstat=========");
        System.out.println(jpsValue);
        System.out.println("=======================");
    }


}
