package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button newTaskButton;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        FragmentManager manager = getSupportFragmentManager();
        viewPager = (ViewPager)findViewById(R.id.calendar_pager);
        CalendarFragmentPagerAdapter.setMaxCalendar(getResources().getInteger(R.integer.maxCalendar));
        CalendarFragmentPagerAdapter adapter = new CalendarFragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(CalendarList.getNowCalendarIndex());

        newTaskButton = (Button)findViewById(R.id.newTaskButton);
        newTaskButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    Intent intent = new Intent(activity, NewTaskCreate.class);
                    intent.putExtra("date", new Date().getTime());
                    activity.startActivity(intent);
                }
                return false;
            }
        });
    }

}
