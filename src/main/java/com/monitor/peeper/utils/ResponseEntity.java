package com.monitor.peeper.utils;

import lombok.Data;

@Data
public class ResponseEntity<T> {
    private int code;
    private T data;
    private String message;

    private final static int SUCCESS_CODE = 200;
    private final static int SERVER_FAIL = 500;

    public  ResponseEntity<T> success(T data, String message) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(SUCCESS_CODE);
        responseEntity.setData(data);
        responseEntity.setMessage(message);
        return responseEntity;
    }
    public  ResponseEntity<T> faile( String message) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(SERVER_FAIL);
        responseEntity.setMessage(message);
        return responseEntity;
    }
}
