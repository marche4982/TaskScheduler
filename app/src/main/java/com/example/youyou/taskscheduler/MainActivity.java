package com.example.youyou.taskscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View parentView = View.inflate(this, R.layout.activity_main, null);
        setContentView(parentView);

        Intent intent = new Intent(this, CalendarView.class);
        startActivity(intent);
    }


    // XX年　XX月

    // 月ごとに複数回追加する

    //
}
