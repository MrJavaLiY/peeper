package com.monitor.peeper.service.impl;

import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.dataBase.ServerExcelDataBase;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.exception.NotSM2UpdateException;
import com.monitor.peeper.service.FileOperService;
import com.monitor.peeper.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileOperServiceImpl extends ServerExcelDataBase implements FileOperService {
    @Override
    public ResponseEntity<String> addData2File(RequestCondition condition) {
        ServerMessage serverMessage = condition.getSm();
        serverMessage.setIndex(getIndex(serverMessage));
        super.add(serverMessage);
        return new ResponseEntity<String>().success("", "成功");
    }

    @Override
    public ResponseEntity<List<ServerMessage>> getData2View() {
        return new ResponseEntity<List<ServerMessage>>().success(new ArrayList<>(super.getAll()), "成功");
    }

    @Override
    public ResponseEntity<?> deleteData2File(RequestCondition condition) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setIp(condition.getIp());
        serverMessage.setUser(condition.getUser());

        ServerMessage cacheDatum = super.getCacheOne(super.getIndex(serverMessage));
        super.delete(cacheDatum);
        return getData2View();
    }

    @Override
    public ResponseEntity<ServerMessage> updateData2File(RequestCondition condition) {
        ServerMessage serverMessage = condition.getSm();
        ServerMessage cacheDatum = super.getCacheOne(super.getIndex(serverMessage));
        if (cacheDatum == null) {
            throw new NotSM2UpdateException("这次传入的ip和账号没有发现可修改的服务器，如果需要修改请先删除后新增");
        }
        if (StringUtils.hasText(condition.getSm().getEnable())) {
            cacheDatum.setEnable(condition.getSm().getEnable());
        }
        if (StringUtils.hasText(condition.getSm().getPassword())) {
            cacheDatum.setPassword(condition.getSm().getPassword());
        }
        if (StringUtils.hasText(condition.getSm().getType())) {
            cacheDatum.setType(condition.getSm().getType());
        }
        super.update(cacheDatum);
        return new ResponseEntity<ServerMessage>().success(getCacheOne(getIndex(serverMessage)), "成功");
    }

}
