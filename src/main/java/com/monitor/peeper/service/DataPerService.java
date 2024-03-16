package com.monitor.peeper.service;

import com.monitor.peeper.condition.PageRequestCondition;
import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.condition.vo.DataValueVO;
import com.monitor.peeper.entity.excel.DataValue;
import com.monitor.peeper.utils.ResponseEntity;

import java.util.List;

/**
 * 持久化服务信息
 */
public interface DataPerService {
    ResponseEntity<String> add(DataValue dataValue);

    ResponseEntity<DataValueVO> getPageData(PageRequestCondition condition);

    ResponseEntity<DataValue> getOne(RequestCondition condition);

    ResponseEntity<String> updateData(DataValue dataValue);

    ResponseEntity<String> deleteData(DataValue dataValue);


    void reNewPer(List<DataValue> dataValue);
}
