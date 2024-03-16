package com.monitor.peeper.entity.ding;

import com.yxkj.ptjk.entity.log.TbMessageNoticeDetailDing;
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

    public DingTalkEntity(TbMessageNoticeDetailDing detail) {
        this.at = new DingAtEntity(detail);
        this.text = new DingTextEntity(detail);
        this.msgtype = "text";
    }
}
