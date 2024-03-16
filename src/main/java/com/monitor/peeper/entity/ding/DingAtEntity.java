package com.monitor.peeper.entity.ding;

import com.alibaba.fastjson.annotation.JSONField;
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

    public DingAtEntity(DingMessage ding) {
        this.atMobiles = ding.getAtMobiles();
        this.isAtAll = ding.getIsAtAll() != null && ding.getIsAtAll();
    }
}
