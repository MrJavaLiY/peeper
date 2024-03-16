package com.monitor.peeper.entity.ding;

import lombok.Data;

/**
 * @author liyang
 * @date 2024-01-06
 * @description:
 */
@Data
public class DingTextEntity {
    private String content;

    public DingTextEntity() {

    }

    public DingTextEntity(DingMessage ding) {
        StringBuilder value = new StringBuilder( ding.getValue()==null?"":ding.getValue());
        if (ding.getIsAtAll()!=null&&!ding.getIsAtAll()&&ding.getAtMobiles()!=null&& !ding.getAtMobiles().isEmpty()) {
            ding.getAtMobiles().forEach(c -> {
                value.append(" @").append(c).append(" ");
            });
        }
        this.content = value.toString();
    }
}
