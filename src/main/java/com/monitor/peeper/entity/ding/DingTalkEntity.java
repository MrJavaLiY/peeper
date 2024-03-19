package com.monitor.peeper.entity.ding;

import lombok.Data;

/**
 * @author liyang
 * @date 2024-01-06
 * @description:
 */
@Data
public class DingTalkEntity {
    private DingAtEntity at;
    private DingTextEntity text;
    private String msgtype;


    public DingTalkEntity() {

    }

    public DingTalkEntity(DingMessage dingMessage) {
        this.at = new DingAtEntity(dingMessage);
        this.text = new DingTextEntity(dingMessage);
        this.msgtype = "text";
    }
}
