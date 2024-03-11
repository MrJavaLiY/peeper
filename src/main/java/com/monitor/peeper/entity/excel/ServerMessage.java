package com.monitor.peeper.entity.excel;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ServerMessage extends ExcelEntity {
    private String ip;
    private String user;
    private String password;
    private String type;


    public boolean isEnable() {
        return StringUtils.hasText(getEnable()) && getEnable().equals("1");
    }
    public String getIndex(ServerMessage serverMessage) {
        return serverMessage.getIp() + ":" + serverMessage.getUser();
    }


}
