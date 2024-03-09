package com.monitor.peeper.service;


import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.entity.excel.ServerMessage;
import com.monitor.peeper.utils.ResponseEntity;

import java.util.List;

public interface FileOperService   {
    /**
     * 添加服务器信息到文件中，新增式添加
     *
     * @param condition
     * @return
     */
    ResponseEntity<String> addData2File(RequestCondition condition);

    /**
     * 获取所有的服务器信息
     *
     * @return
     */
    ResponseEntity<List<ServerMessage>> getData2View();

    /**
     * 根据主键删除指定条信息
     *
     * @param condition
     * @return
     */
    ResponseEntity<?> deleteData2File(RequestCondition condition);

    /**
     * 修改某条信息
     *
     * @param condition
     * @return
     */
    ResponseEntity<ServerMessage> updateData2File(RequestCondition condition);
}
