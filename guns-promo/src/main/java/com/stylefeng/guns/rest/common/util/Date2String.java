package com.stylefeng.guns.rest.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2String {
    public static String transfer(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
