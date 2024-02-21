package com.monitor.peeper.entity.excel;

import lombok.Data;

@Data
public class ServerMessage {
    private String ip;
    private String user;
    private String password;
    private String type;

}
