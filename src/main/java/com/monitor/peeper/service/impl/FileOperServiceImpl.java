package com.monitor.peeper.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.monitor.peeper.condition.RequestCondition;
import com.monitor.peeper.service.FileOperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utils.ResponseEntity;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class FileOperServiceImpl implements FileOperService {
    String path = "data/config.txt";
    String splitType = "&";

    @Override
    public void in(RequestCondition condition) throws IOException {
        boolean b = false;
        List<RequestCondition> outCon = outEntity();
        StringBuilder sb = new StringBuilder();
        for (RequestCondition requestCondition : outCon) {
            b = requestCondition.getIp().equals(condition.getIp());
            if (b) {
                outCon.remove(requestCondition);
                break;
            }
        }
        if (b){
          outCon.add(condition);
        }
        if (outCon.size() == 0 || !b) {
            outCon.add(condition);
        }
        for (RequestCondition requestCondition : outCon) {
            sb.append(JSONObject.toJSONString(requestCondition)).append(splitType);
        }
        this.in(sb.substring(0, sb.length() - 1), b);
    }

    @Override
    public void in(String value, boolean b) throws IOException {
        FileOutputStream fileWriter = new FileOutputStream(path, false);
        fileWriter.write(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String out() throws IOException {
        FileInputStream in = new FileInputStream(path);
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String sr = "";
        while ((sr = br.readLine()) != null) {
            sb.append(sr);
        }
        return sb.toString();
    }

    @Override
    public List<RequestCondition> outEntity() throws IOException {
        String outValue = this.out();
        List<RequestCondition> conditions = new ArrayList<>();
        if (outValue.contains(splitType)) {
            String[] outValues = outValue.split(splitType);
            System.out.println(Arrays.toString(outValues));
            for (String value : outValues) {
                conditions.add(JSONObject.parseObject(value, RequestCondition.class));
            }
        } else {
            RequestCondition condition = JSONObject.parseObject(outValue, RequestCondition.class);
            if (condition != null) {
                conditions.add(condition);
            }
        }
        return conditions;
    }

    @Override
    public ResponseEntity<?> deleteById(String id) throws IOException {
        List<RequestCondition> outValue = outEntity();
        for (int i = 0; i < outValue.size(); i++) {
            if (outValue.get(i).getIp().equals(id)) {
                outValue.remove(i);
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (RequestCondition requestCondition : outValue) {
            sb.append(JSONObject.toJSONString(requestCondition)).append(splitType);
        }
        this.in(sb.substring(0, sb.length() - 1), false);
        return new ResponseEntity<>().success("","成功");
    }
    final String enc = "UTF-8";

    @Override
    public String encoder(String source) throws UnsupportedEncodingException {
        return URLEncoder.encode(source, enc);
    }

    @Override
    public String decoder(String source) throws UnsupportedEncodingException {
        return URLDecoder.decode(source, enc);
    }

}
