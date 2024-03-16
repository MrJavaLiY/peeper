package com.monitor.peeper.entity.ding;

import com.yxkj.ptjk.entity.log.TbMessageNoticeDetailDing;
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

    public DingTextEntity(TbMessageNoticeDetailDing ding) {
        StringBuilder value = new StringBuilder( ding.getValue()==null?"":ding.getValue());
        if (ding.getIsAtAll()!=null&&!ding.getIsAtAll()&&ding.getAtMobiles()!=null&&ding.getAtMobiles().size() != 0) {
            ding.getAtMobiles().forEach(c -> {
                value.append(" @").append(c).append(" ");
            });
        }
        this.content = value.toString();
    }
}
