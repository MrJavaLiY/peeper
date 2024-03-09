package com.monitor.peeper.service;


import com.monitor.peeper.annotation.Strategy;
import com.monitor.peeper.annotation.StrategyPoint;
import com.monitor.peeper.entity.WinCmdEntity;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.utils.ResponseEntity;

import java.util.List;

/**
 * @author ly
 */
@StrategyPoint
public interface LinuxService {
    @Strategy(value = "Linux")
    ResponseEntity<List<WinCmdEntity>> dispatch(ServerMessage serverMessage) throws Exception;
}
