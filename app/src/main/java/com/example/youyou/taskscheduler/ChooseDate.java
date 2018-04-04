package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

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
        viewPager.setCurrentItem(getResources().getInteger(R.integer.firstPosition));
    }



}
