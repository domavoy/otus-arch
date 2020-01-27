package ru.mdorofeev.finance.common.util;

import java.time.LocalDate;
import java.util.Date;

public class DateUtil {

    // 2010-01-01
    public static Date date(String yyyymmdd){
        return new Date(LocalDate.parse(yyyymmdd).toEpochDay());
    }
}
