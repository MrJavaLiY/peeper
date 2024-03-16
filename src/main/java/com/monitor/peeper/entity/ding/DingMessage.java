package com.monitor.peeper.entity.ding;

import lombok.Data;

import java.util.List;

@Data
public class DingMessage {
    private String webhook;
    private List<String> atMobiles;
    private Boolean isAtAll;
    private String value;
}
