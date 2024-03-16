package com.monitor.peeper.condition;

import lombok.Data;

@Data
public class PageRequestCondition extends RequestCondition {
    private int page =1;
    private int size = 10;
}
