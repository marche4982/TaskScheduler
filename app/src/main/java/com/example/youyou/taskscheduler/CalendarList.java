package com.example.youyou.taskscheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MONTH;

/**
 * Created by youyou on 2018/04/04.
 */

public class CalendarList extends ArrayList<Calendar> {

    public CalendarList(int max){
        Calendar calendar = Calendar.getInstance();

        // 1990年1月　～　2100年 12月まで入れる
        for(int year = 1990; year <= 2100; year++){
            for(int month = 1; month <= 12; month++){
                Calendar tCalendar = (Calendar)calendar.clone();
                tCalendar.clear();
                tCalendar.set(year, month, 1);
                this.add(tCalendar);
            }
        }
    }

    public static int getNowCalendarIndex(){

        int ret  = 0;

        Calendar nowCalendar = Calendar.getInstance();
        int year = nowCalendar.get(Calendar.YEAR);
        int month = nowCalendar.get(Calendar.MONTH);

        // リスト内の year*12 + month-1 に現在のカレンダーが入ってる
        ret = (year-1990) * 12 + month-1;
        return ret;
    }

}
