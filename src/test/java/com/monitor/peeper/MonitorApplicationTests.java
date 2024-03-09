package com.monitor.peeper;

import com.jcraft.jsch.JSchException;
import com.monitor.peeper.dataBase.DataExcelDataBase;
import com.monitor.peeper.utils.ShellUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MonitorApplicationTests {
    @Resource
    DataExcelDataBase dataExcelDataBase;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() throws Exception, InterruptedException, JSchException {

    }

    public static void main(String[] args) throws Exception {
        ShellUtil shell = new ShellUtil("192.168.0.107", "root", "980322");
        String jpsValue = shell.exec("jstat -gc 2996");
        System.out.println("=========jstat=========");
        System.out.println(jpsValue);
        System.out.println("=======================");
    }


}
