package com.monitor.peeper.utils;

/**
 * @author liyang
 * @date 2023-12-18
 * @description:
 */

public class IdWorkerUtil {
    private static final IdWorker i = new IdWorker(8, 8, 8);

    public static long getWorkerNextId() {
        return i.nextId();
    }
}
