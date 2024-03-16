package com.monitor.peeper.dataBase;

import com.monitor.peeper.entity.excel.ServiceName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServiceNameExcelDataBase extends ExcelDataBase<ServiceName> {

    public ServiceNameExcelDataBase() {
        super(SERVICE_NAME_PATH, SERVICE_NAME_SHEET_NAME, ServiceName.class);
    }


    @Override
    public String getIndex(ServiceName serviceName) {
        return serviceName.getServiceIndex();
    }
}

