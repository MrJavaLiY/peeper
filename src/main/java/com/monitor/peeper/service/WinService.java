package com.monitor.peeper.service;


import com.monitor.peeper.annotation.Strategy;
import com.monitor.peeper.annotation.StrategyPoint;
import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.entity.WinCmdEntity;
import utils.ResponseEntity;

import java.util.List;

/**
 * @author ly
 */
@StrategyPoint
public interface WinService {
    @Strategy(value = "Windows")
    ResponseEntity<List<WinCmdEntity>> dispatch(RequestCondition condition) throws Exception;
}
