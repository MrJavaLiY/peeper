package com.monitor.peeper.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.monitor.peeper.entity.ding.DingMessage;
import com.monitor.peeper.entity.ding.DingTalkEntity;
import com.monitor.peeper.service.DingNoticeService;
import com.monitor.peeper.utils.ResponseEntity;
import com.xiaoleilu.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author liyang
 * @date 2024-01-06
 * @description:
 */
@Service
@Slf4j
public class DingNoticeServiceImpl implements DingNoticeService {
    @Value("${notice.}")
    @Override
    public ResponseEntity<String> notice(DingMessage dingMessage) {
        try {
            DingTalkEntity dingtalk = new DingTalkEntity(dingMessage);
            log.info("封装数据：{}", JSONObject.toJSONString(dingtalk));
            String response = HttpUtil.post(dingMessage.getWebhook(), JSONObject.toJSONString(dingtalk));
            log.info("返回数据：{}", response);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if ("true".equals(jsonObject.getString("success"))) {
                return new ResponseEntity<String>().success("", "成功");
            } else {
                return new ResponseEntity<String>().faile("成功");
            }
        } catch (Exception e) {
            log.error("发送钉钉错误{}", e);
            return new ResponseEntity<String>().faile(e.getMessage());
        }
    }

}
