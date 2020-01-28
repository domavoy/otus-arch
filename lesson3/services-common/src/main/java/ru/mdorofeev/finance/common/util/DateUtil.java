package ru.mdorofeev.finance.common.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    // 2010-01-01
    public static Date date(String yyyymmdd){
        return Date.from(LocalDate.parse(yyyymmdd).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}