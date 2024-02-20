package com.example.monitor.mode;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationBean implements ApplicationContextAware {
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationBean.applicationContext = applicationContext;
    }
    public static  <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
