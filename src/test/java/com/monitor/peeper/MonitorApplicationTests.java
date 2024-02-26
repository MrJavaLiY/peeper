package com.monitor.peeper;

import com.jcraft.jsch.JSchException;
import com.monitor.peeper.dataBase.DataExcelDataBase;
import com.monitor.peeper.entity.excel.DataValue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import utils.ShellUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MonitorApplicationTests {
    @Resource
    DataExcelDataBase dataExcelDataBase;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() throws Exception, InterruptedException, JSchException {
        List<DataValue> dataValues = new ArrayList<>();
        DataValue dataValue = new DataValue();
        dataValue.setIp("127.0.0.1");
        dataValue.setPort("11111");
        dataValue.setStatus(true);
        dataValues.add(dataValue);
        dataExcelDataBase.add(dataValues);
        System.out.println(dataExcelDataBase.read().toString());
        List<DataValue> dataValue1s = new ArrayList<>();
        dataValue.setPort("111111");
        dataValue.setStatus(true);
        dataValue1s.add(dataValue);
        dataExcelDataBase.add(dataValue1s);
        System.out.println(dataExcelDataBase.read().toString());
        List<DataValue> dataValue11s = new ArrayList<>();
        dataValue.setPort("11111");
        dataValue.setStatus(true);
        dataValue11s.add(dataValue);
        dataExcelDataBase.delete(dataValue);
        System.out.println(dataExcelDataBase.read().toString());
//        Thread.sleep(60000);
    }

    public static void main(String[] args) throws Exception {
        ShellUtil shell = new ShellUtil("192.168.0.107", "root", "980322");
        String jpsValue = shell.exec("jstat -gc 2996");
        System.out.println("=========jstat=========");
        System.out.println(jpsValue);
        System.out.println("=======================");
    }


}
