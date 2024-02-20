package com.example.monitor.controller;

import com.example.monitor.condition.RequestCondition;
import com.example.monitor.entity.WinCmdEntity;
import com.example.monitor.mode.StrategyOperate;
import com.example.monitor.service.FileOperService;
import com.example.monitor.service.WinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import utils.ResponseEntity;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
