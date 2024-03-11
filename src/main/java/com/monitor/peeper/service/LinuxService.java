package com.monitor.peeper.service;


import com.monitor.peeper.annotation.Strategy;
import com.monitor.peeper.annotation.StrategyPoint;
import com.monitor.peeper.entity.JarDetailEntity;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.utils.ResponseEntity;

import java.util.List;

/**
 * @author ly
 */
@StrategyPoint
public interface LinuxService {
    @Strategy(value = "Linux")
    ResponseEntity<List<JarDetailEntity>> dispatch(ServerMessage serverMessage) throws Exception;
}
