package com.monitor.peeper.service;


import com.monitor.peeper.entity.ding.DingMessage;
import com.monitor.peeper.utils.ResponseEntity;

/**
 * @author liyang
 * @date 2024-01-06
 * @description:
 */
public interface DingNoticeService {
    public ResponseEntity<String> notice(DingMessage dingMessage) ;
}
