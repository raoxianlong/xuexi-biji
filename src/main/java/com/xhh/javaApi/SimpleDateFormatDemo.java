package com.xhh.javaApi;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatDemo {

    public static final String DATE_PARTTEN = "yyyy-MM-dd HH:mm:ss SSS";

    /**
     * 将Date类型转换为String类型
     */
    @Test
    public void date2String(){
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_PARTTEN);
        String dateStr = fmt.format(date);
        System.out.println(dateStr);
    }

    /**
     * 将String类型转换为Date类型
     * @throws ParseException
     */
    @Test
    public void sting2Date() throws ParseException {
        String dateStr = "2018-12-05 15:15:15 555";
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_PARTTEN);
        Date date = fmt.parse(dateStr);
        System.out.println(date);
    }
}
