package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;

import static java.lang.Boolean.FALSE;

/**
 * Created by youyou on 2018/03/25.
 */

public class DateTextView2 extends DateTextView {

    private View mParentView;
    private Date mDate;
    private Boolean bFocused = FALSE;

    public DateTextView2(final Activity activity, View mParentView){
        super(activity, mParentView);
        this.mParentView = mParentView;

        int nWeekDays = getResources().getInteger(R.integer.weekdays);
        Point point = getDisplaySize(activity);
        int mViewWidth = point.x / nWeekDays;

        this.setGravity(Gravity.CENTER);    // 中央に表示
        this.setTextSize(15);                // 文字サイズ：20
        this.setWidth(mViewWidth);           // 7等分
        this.setHeight(200);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    Intent intent = new Intent(activity, ToDoListView.class);
                    Bundle bundle = new Bundle();
                    intent.putExtra(getResources().getString(R.string.bundle_time), mDate.getTime());
                    activity.startActivity(intent);
                }
                return false;
            }
        });
    }
}
