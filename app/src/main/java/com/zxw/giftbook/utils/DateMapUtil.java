package com.zxw.giftbook.utils;

import android.content.Intent;

import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/3/2.
 */

public class DateMapUtil {
    public static LinkedHashMap<String,String> yearMap=new LinkedHashMap<>();
    public static LinkedHashMap<String,String> monthMap=new LinkedHashMap<>();
    public static  LinkedHashMap<String,String> getYearMap()
    {
        if(yearMap.size()==0){
            yearMap.put("0","全部");
            yearMap.put("2017","2017年");
            yearMap.put("2018","2018年");
            yearMap.put("2019","2019年");
            yearMap.put("2020","2020年");
        }
        return yearMap;
    }
    public static  LinkedHashMap<String,String> getMonthMap()
    {
        if(monthMap.size()==0){
            monthMap.put("0","1-12月");
            monthMap.put("1","1月");
            monthMap.put("2","2月");
            monthMap.put("3","3月");
            monthMap.put("4","4月");
            monthMap.put("5","5月");
            monthMap.put("6","6月");
            monthMap.put("7","7月");
            monthMap.put("8","8月");
            monthMap.put("9","9月");
            monthMap.put("10","10月");
            monthMap.put("11","11月");
            monthMap.put("12","12月");
        }
        return monthMap;
    }
}
