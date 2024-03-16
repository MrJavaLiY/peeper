package com.monitor.peeper.entity.ding;

import com.alibaba.fastjson.annotation.JSONField;
import com.yxkj.ptjk.entity.log.TbMessageNoticeDetailDing;
import lombok.Data;

import java.util.List;

/**
 * @author liyang
 * @date 2024-01-06
 * @description:
 */
@Data
public class DingAtEntity {
    private List<String> atMobiles;
    @JSONField(name = "isAtAll")
    private boolean isAtAll;

    public DingAtEntity() {

    }

    public DingAtEntity(TbMessageNoticeDetailDing ding) {
        this.atMobiles = ding.getAtMobiles();
        this.isAtAll = ding.getIsAtAll()==null?false: ding.getIsAtAll();
    }
}
