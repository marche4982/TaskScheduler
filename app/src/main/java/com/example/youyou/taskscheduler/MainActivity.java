package com.example.youyou.taskscheduler;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        com.example.youyou.taskscheduler.CalendarView fragment = new com.example.youyou.taskscheduler.CalendarView();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main, fragment, "CalendarFragment");
        transaction.commit();
        */

        FragmentManager manager = getSupportFragmentManager();
        viewPager = (ViewPager)findViewById(R.id.calendar_pager);
        CalendarFragmentPagerAdapter.setMaxCalendar(getResources().getInteger(R.integer.maxCalendar));
        CalendarFragmentPagerAdapter adapter = new CalendarFragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getResources().getInteger(R.integer.firstPosition));
    }

}
