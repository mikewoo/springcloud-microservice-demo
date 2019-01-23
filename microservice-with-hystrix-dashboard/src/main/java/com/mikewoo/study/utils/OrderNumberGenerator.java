package com.mikewoo.study.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
public class OrderNumberGenerator {

    public final static String STR_TIMESTAMP = "yyMMddHHmmsss";

    private static final ThreadLocal<DateFormat> DF_TIMESTAMP = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(STR_TIMESTAMP);
        }
    };

    /**
     * count
     */
    private int cnt;
    /**
     * minute
     */
    private long minute;

    private static OrderNumberGenerator instance = new OrderNumberGenerator();

    /**
     *
     */
    private OrderNumberGenerator() {
    }

    public static OrderNumberGenerator getInstance() {
        return instance;
    }

    /**
     * generate an id
     * @return
     */
    public synchronized String generate(long userId) {
        long now = System.currentTimeMillis();
        long m = (long) (now / 1000 / 60 * 60 * 1000);
        if (minute < m) {
            minute = m;
            cnt = 0;
        }
        cnt++;
        return "C" + DF_TIMESTAMP.get().format(new Date(now)) + userId + cnt;
    }

}
