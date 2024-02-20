package com.monitor.peeper.entity;

import lombok.Data;

@Data
public class ServerMessage {
    private String ip;
    private String user;
    private String password;

}
