package utils;

import lombok.Data;

@Data
public class ResponseEntity<T> {
    private int code ;
    private T data;
    private String message;

    public ResponseEntity<T> success(T data,String message){
            ResponseEntity<T> responseEntity = new ResponseEntity<>();
            responseEntity.setCode(200);
            responseEntity.setData(data);
            responseEntity.setMessage(message);
            return responseEntity;
    }
}
