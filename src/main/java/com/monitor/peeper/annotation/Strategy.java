package com.monitor.peeper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 策略监管方法的核心注解，带有该注解的方法才可以被策略监管
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Strategy {
    /**
     * 监管方法的索引，必填
     *
     * @return
     */
    String value() default "";

    /**
     * 索引数组，可多个索引监控一个方法
     * @return
     */
    String[] values() default {};

    /**
     * 是否是默认方法，进入索引没有方法指向，则采用默认方法，而默认方法只有一个，如若设置了多个默认方法，则只认最后一个
     * @return
     */
    boolean isDefault() default false;
}
