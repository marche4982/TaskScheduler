package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.Date;

/**
 * Created by youyou on 2018/03/21.
 */

public class ChooseDate extends AppCompatActivity {

    private ViewPager viewPager;
    private Button newTaskButton;
    private Activity activity;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.choose_date);

        FragmentManager manager = getSupportFragmentManager();
        viewPager = (ViewPager)findViewById(R.id.calendar_pager);
        CalendarFragmentPagerAdapter.setMaxCalendar(getResources().getInteger(R.integer.maxCalendar));
        CalendarFragmentPagerAdapter adapter = new CalendarFragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(CalendarList.getNowCalendarIndex());
    }

    /*
    CalendarFragmentPager　でどこの日付がタッチされたかを
    管理すればよい
     */

    /* 取得した日付を入れる */

    @Override
    protected void onDestroy(){

        Date touchedDate = CalendarFragmentPagerAdapter.GetTouchedDate();

        Intent data = new Intent();
        data.putExtra("touchedDate", touchedDate);
        setResult(0, data);

        super.onDestroy();


    }

}
