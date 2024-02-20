package com.example.monitor.service;


import com.example.monitor.annotation.Strategy;
import com.example.monitor.annotation.StrategyPoint;
import com.example.monitor.condition.RequestCondition;
import com.example.monitor.entity.WinCmdEntity;
import utils.ResponseEntity;

import java.util.List;

/**
 * @author ly
 */
@StrategyPoint
public interface LinuxService {
    @Strategy(value = "Linux")
    ResponseEntity<List<WinCmdEntity>> dispatch(RequestCondition condition) throws Exception;
}
