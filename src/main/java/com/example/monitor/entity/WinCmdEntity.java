package com.example.monitor.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WinCmdEntity {
    private  int pid;
    private String pidName;
    private int port;
    private String jarName;
    private  String jarPath;
    private String jarJvmMes;
    private Date lastTime;
    private String ip;
}
