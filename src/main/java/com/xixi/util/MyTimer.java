package com.xixi.util;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by xijiaxiang on 2017/6/7.
 */
public class MyTimer {

    public  static String getShangHaiTime(){
        ZoneId shanghaizone = ZoneId.of("Asia/Shanghai");
        //LocalDateTime shanghaiDateTime = LocalDateTime.now(shanghaizone);
        LocalDate shanghiadate = LocalDate.now(shanghaizone);
        System.out.println(shanghiadate);
        return shanghiadate.toString();
    }

    public  static String getShangHaiYesterday(){
        ZoneId shanghaizone = ZoneId.of("Asia/Shanghai");
        //LocalDateTime shanghaiDateTime = LocalDateTime.now(shanghaizone);
        LocalDate shanghiadate = LocalDate.now(shanghaizone);
        shanghiadate=shanghiadate.minusDays(1);
        return shanghiadate.toString();
    }

    public static String getShangHaiYear(){
        ZoneId shanghaizone = ZoneId.of("Asia/Shanghai");
        //LocalDateTime shanghaiDateTime = LocalDateTime.now(shanghaizone);
        LocalDate shanghiadate = LocalDate.now(shanghaizone);
        System.out.println(shanghiadate.getYear());
        return shanghiadate.getYear()+"";
    }



    public static boolean isBetweenTwoDays(String day1,String day2,String compareday){
        LocalDate firstday=LocalDate.parse(day1);
        LocalDate secondday=LocalDate.parse(day2);
        LocalDate tempday=LocalDate.parse(compareday);
        if(tempday.isEqual(firstday)||tempday.isAfter(firstday)){
            if(tempday.isEqual(secondday)||tempday.isBefore(secondday)){
                return  true;
            }
        }
        return false;
    }
}
