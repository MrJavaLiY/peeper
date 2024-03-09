package com.monitor.peeper.service.impl;


import com.monitor.peeper.entity.WinCmdEntity;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.service.LinuxService;
import com.monitor.peeper.utils.ResponseEntity;
import com.monitor.peeper.utils.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LinuxServerImpl implements LinuxService {
    @Override
    public ResponseEntity<List<WinCmdEntity>> dispatch(ServerMessage serverMessage) throws Exception {
        System.out.println("Linux");
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
            String jarName = javaCommands[0].replaceAll(" ", "").replaceAll("\n", "");
            String jarPath = "";
            entity.setJarName(jarName);
            entity.setJarPath(jarPath);
        }
        this.getPath(shell, entity);
        this.getPost(shell, entity);
    }

    private void getPath(ShellUtil shell, WinCmdEntity entity) throws Exception {
        String pwdx = "pwdx " + entity.getPid();
        String pwdxValue = shell.exec(pwdx);
        System.out.println("===========pwdx=======");
        System.out.println(pwdxValue);
        System.out.println("=====================");
        entity.setJarPath(pwdxValue.substring(pwdxValue.indexOf("/")).replaceAll("\n",""));
    }

    private void getPost(ShellUtil shell, WinCmdEntity entity) throws Exception {
        if (entity.getJarName().contains("org.jetbrains")) {
            return;
        }
        String sudo = " sudo lsof -i -P -n |grep " + entity.getPid();
        String sudoValue = shell.exec(sudo);
        System.out.println("===========sudo=======");
        System.out.println(sudoValue);
        System.out.println("=====================");
        String[] sudoValues = sudoValue.split("\\*:");
        String port = sudoValues[1].substring(0,sudoValues[1].indexOf("(")).replaceAll(" ", "");
        entity.setPort(Integer.parseInt(port));
    }


}
