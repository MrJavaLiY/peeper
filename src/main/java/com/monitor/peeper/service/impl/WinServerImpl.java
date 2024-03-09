package com.monitor.peeper.service.impl;


import com.monitor.peeper.entity.WinCmdEntity;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.service.WinService;
import com.monitor.peeper.utils.ResponseEntity;
import com.monitor.peeper.utils.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WinServerImpl implements WinService {


    @Override
    public ResponseEntity<List<WinCmdEntity>> dispatch(ServerMessage serverMessage) throws Exception {
        System.out.println("windows");
        ShellUtil shell = new ShellUtil(serverMessage.getIp(), serverMessage.getUser(), serverMessage.getPassword());
        String jpsValue = shell.exec("jps -l");
        System.out.println("===========jps=======");
        System.out.println(jpsValue);
        System.out.println("=====================");
        String[] jpes = jpsValue.split("\n");
        List<WinCmdEntity> winCmdEntities = new ArrayList<>();
        for (String jpe : jpes) {
            if (jpe.contains("jps")) {
                continue;
            }
            WinCmdEntity entity = new WinCmdEntity();
            String[] jps1 = jpe.split(" ");
            entity.setPid(Integer.parseInt(jps1[0]));
            this.getjarMessage(shell, entity);
            if (entity.getPort() == 0) {
                continue;
            }
            winCmdEntities.add(entity);
        }

        return new ResponseEntity<List<WinCmdEntity>>().success(winCmdEntities, "s");
    }

    private void getjarMessage(ShellUtil shell, WinCmdEntity entity) throws Exception {
        String jvmmes = shell.exec("jcmd " + entity.getPid() + " VM.command_line");
        if (jvmmes.contains("org.jetbrains")) {
            return;
        }
        System.out.println("===========jcmd=======");
        System.out.println(jvmmes);
        System.out.println("=====================");
        String[] jvmesValues = jvmmes.split("java_command:");

        if (jvmesValues.length > 1) {
            String[] javaCommands = jvmesValues[1].split("java_class_path");
            String jarNamePath = javaCommands[0].replaceAll(" ", "").replaceAll("\n", "");
            System.out.println("jarNamePath" + jarNamePath);
            String jarPath = "";
            String jarName = "";
            if (jarNamePath.contains("\\")) {
                jarPath = jarNamePath.substring(0, jarNamePath.lastIndexOf("\\")).replaceAll("\n", "");
                jarName = jarNamePath.substring(jarNamePath.lastIndexOf("\\") + 1).replaceAll("\n", "");
            } else {
                entity.setJarName(jarNamePath);
            }
            entity.setJarPath(jarPath);
        }
        this.getPost(shell, entity);
    }

    private void getPost(ShellUtil shell, WinCmdEntity entity) throws Exception {
        if (entity.getJarName()!=null&&entity.getJarName().contains("org.jetbrains")) {
            return;
        }
        String netstat = "netstat -aon |findstr " + entity.getPid();
        String netstats = shell.exec(netstat.toString());
        System.out.println("===========netstat=======");
        System.out.println(netstats);
        System.out.println("=====================");
        String[] netstatValues = netstats.split("TCP");
        String netstatValue = netstatValues[1];
        String ss = "0.0.0.0:";
        if (netstatValue.contains(ss)) {
            String[] as = netstatValue.replaceAll(" ", "").split(ss);
            if (isInteger(as[1])) {
                entity.setPort(Integer.parseInt(as[1].replaceAll(" ", "")));
            }


        }
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


}
