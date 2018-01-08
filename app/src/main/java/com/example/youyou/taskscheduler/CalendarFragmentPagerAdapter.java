package com.example.youyou.taskscheduler;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MONTH;

/**
 * Created by youyou on 2018/01/08.
 */

public class CalendarFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int mPresentPosition = -1;
    private static Calendar mCalendar = null;
    private static int nMaxCalendar = 0;
    private static List<Calendar> calendarList;

    public CalendarFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        calendarList = new ArrayList<Calendar>();
        Calendar nowCalendar = Calendar.getInstance();
        for( int i = -1 * (nMaxCalendar / 2); i < nMaxCalendar/2 ; i++){
            Calendar tCalendar = (Calendar)nowCalendar.clone();
            tCalendar.set(MONTH, tCalendar.get(MONTH) + i);
            calendarList.add((Calendar)tCalendar.clone());;
        }
    }

    public static void setMaxCalendar(int max){
        nMaxCalendar = max;
    }

    @Override
    public CalendarView getItem(int position) {
        CalendarView calendarView = new CalendarView();

        calendarView.setCalendar(calendarList.get(position));

        return calendarView;
    }

    @Override
    public int getCount() {
        return calendarList.size();
    }

}