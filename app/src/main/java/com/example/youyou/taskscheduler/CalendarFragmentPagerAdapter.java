package com.example.youyou.taskscheduler;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Calendar;

import static java.util.Calendar.MONTH;

/**
 * Created by youyou on 2018/01/08.
 */

public class CalendarFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int mPresentPosition = -1;
    private static Calendar mCalendar = null;
    private static int nMaxCalendar = 0;

    public CalendarFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public static void setMaxCalendar(int max){
        nMaxCalendar = max;
    }

    @Override
    public CalendarView getItem(int position) {
        CalendarView calendarView = new CalendarView();

        if( mCalendar != null){
            if( position > mPresentPosition ){
                mCalendar.add(Calendar.MONTH, 1);
            }
            else if( position < mPresentPosition ){
                mCalendar.add(Calendar.MONTH, -1);
            }
        }
        else{
            mCalendar = Calendar.getInstance();
        }

        mPresentPosition = position;
        calendarView.setCalendar(mCalendar);

        return calendarView;
    }

    @Override
    public int getCount() {
        return nMaxCalendar;
    }

}