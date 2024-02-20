package com.monitor.peeper.controller;

import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.entity.WinCmdEntity;
import com.monitor.peeper.mode.StrategyOperate;
import com.monitor.peeper.service.FileOperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import utils.ResponseEntity;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("win")
public class WinController {
    @Resource
    StrategyOperate strategyOperate;
    @Resource
    FileOperService fileOperService;

    @PostMapping("data")
    public ResponseEntity<List<WinCmdEntity>> winData(@RequestBody RequestCondition condition) throws Exception {

//        System.out.println(fileOperService.outEntity().toString());
        return (ResponseEntity<List<WinCmdEntity>>) strategyOperate.executeMethodSpring(condition.getServerType(), condition);
    }
    @PostMapping("inConfig")
    public ResponseEntity inConfig(@RequestBody RequestCondition condition) throws IOException {
        fileOperService.in(condition);
        return new ResponseEntity().success("","成功");
    }
    @PostMapping("delConfig")
    public ResponseEntity delConfig(@RequestBody RequestCondition condition) throws IOException {
        fileOperService.deleteById(condition.getIp());
        return new ResponseEntity().success("","成功");
    }

}
