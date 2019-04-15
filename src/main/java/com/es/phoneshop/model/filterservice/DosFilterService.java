package com.es.phoneshop.model.filterservice;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DosFilterService {
    private static DosFilterService instance;
    private static final int THRESHOLD = 20;
    private Map<String, AtomicInteger> ipCallCounter;
    private volatile Date lastResetedDate;

    public static DosFilterService getInstance() {
        if (instance == null) {
            synchronized (DosFilterService.class) {
                if (instance == null) {
                    instance = new DosFilterService();
                }
            }
        }
        return instance;
    }

    private DosFilterService() {
        ipCallCounter = new ConcurrentHashMap<>();
        lastResetedDate = new Date();
    }

    public boolean isAllowed(String ip) {
        resetIfPossible();
        AtomicInteger count = ipCallCounter.get(ip);
        if (count == null) {
            count = new AtomicInteger(0);
            ipCallCounter.put(ip, count);
        }
        int value = count.getAndIncrement();
        return value < THRESHOLD;
    }

    private void resetIfPossible() {
        Date curDate = new Date();
        if(curDate.getTime() - lastResetedDate.getTime() > 60 * 1000) {
            lastResetedDate = curDate;
            ipCallCounter.clear();
        }
    }
}
