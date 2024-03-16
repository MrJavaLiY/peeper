package com.monitor.peeper.service.impl;

import com.monitor.peeper.condition.PageRequestCondition;
import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.condition.vo.DataValueVO;
import com.monitor.peeper.dataBase.DataExcelDataBase;
import com.monitor.peeper.entity.excel.DataValue;
import com.monitor.peeper.service.DataPerService;
import com.monitor.peeper.utils.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 持久化服务信息
 */
@Service
@Slf4j
public class DataPerServiceImpl implements DataPerService {
    @Resource
    DataExcelDataBase dataExcelDataBase;

    @Override
    public ResponseEntity<String> add(DataValue dataValue) {
        dataExcelDataBase.add(dataValue);
        return new ResponseEntity<String>().success("", "成功");
    }

    @Override
    public ResponseEntity<DataValueVO> getPageData(PageRequestCondition condition) {
        Set<DataValue> data = dataExcelDataBase.getAll();
        List<DataValue> dataList = new ArrayList<>(data);
        DataValueVO vo = new DataValueVO();
        List<DataValue> dataValueList = new ArrayList<>();
        vo.setPage(condition.getPage());
        vo.setSize(condition.getSize());
        vo.setTotal(dataList.size());
        for (int i = condition.getPage() - 1; i < condition.getSize(); i++) {
            if (i >= dataList.size()) {
                break;
            }
            dataValueList.add(dataList.get(i));
        }
        vo.setData(dataValueList);
        return new ResponseEntity<DataValueVO>().success(vo, "成功");
    }

    @Override
    public ResponseEntity<DataValue> getOne(RequestCondition condition) {
        Set<DataValue> data = dataExcelDataBase.getAll();
        List<DataValue> dataList = new ArrayList<>(data);

        for (DataValue dataValue : dataList) {
            if (condition.getIndex().equals(dataExcelDataBase.getIndex(dataValue))) {
                return new ResponseEntity<DataValue>().success(dataValue, "");
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<String> updateData(DataValue dataValue) {
        dataExcelDataBase.update(dataValue);
        return new ResponseEntity<String>().success("", "成功");
    }

    @Override
    public ResponseEntity<String> deleteData(DataValue dataValue) {
        dataExcelDataBase.delete(dataValue);
        return new ResponseEntity<String>().success("", "成功");
    }

    @Override
    public void reNewPer(List<DataValue> dataValue) {

    }
}
