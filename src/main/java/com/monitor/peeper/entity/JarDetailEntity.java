package com.monitor.peeper.entity;

import lombok.Data;

import java.util.Date;

@Data
public class JarDetailEntity {
    public final static String OFFLINE = "0";
    public final static String ONLINE = "1";
    public final static String NEW_OFFLINE = "2";
    public final static String STANDBY = "0";
    public final static String RUN = "1";

    private int pid;
    private String pidName;
    private int port;
    private String jarName;
    private String jarPath;
    private String jarJvmMes;
    private Date lastTime;
    private String ip;
    private String serverIndex;
    /**
     * 是否已经通知过了，
     */
    private boolean notice;
    /**
     * 通知时间是多久，主要是用来设定循环通知
     */
    private Date noticeTime;
    /**
     * 存活状态，0表示当前服务在本次心跳中死亡，1表示当前存活，2表示新上线
     */
    private String surStatus;

    /**
     * 运行状态，0表示待机，1表示运行中，跟#surStatus的1和2存活状态关联
     */
    private String runStatus;


    public String getIndex() {
        return serverIndex + "+" + port;
    }
}
