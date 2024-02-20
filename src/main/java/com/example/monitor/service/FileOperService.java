package com.example.monitor.service;


import com.example.monitor.condition.RequestCondition;
import utils.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface FileOperService {

    void in(RequestCondition condition) throws IOException;
    void in(String value,boolean b) throws IOException;

    String out() throws IOException;
    List<RequestCondition> outEntity() throws IOException;

    ResponseEntity<?> deleteById(String id) throws IOException;

    String encoder(String source) throws UnsupportedEncodingException;
    String decoder(String source) throws UnsupportedEncodingException;
}
