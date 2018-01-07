package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import  android.support.v7.widget.AppCompatTextView;
import android.view.Display;
import android.view.Gravity;

/**
 * Created by youyou on 2018/01/07.
 */

public class DateTextView extends android.support.v7.widget.AppCompatTextView{

    public DateTextView(Activity activity){
        super(activity);

        int nWeekDays = getResources().getInteger(R.integer.weekdays);
        Point point = getDisplaySize(activity);
        int mViewWidth = point.x / nWeekDays;

        this.setGravity(Gravity.CENTER);    // 中央に表示
        this.setTextSize(20);                // 文字サイズ：20
        this.setWidth(mViewWidth);           // 7等分
    }


    /*
    * 　画面サイズを返す
    *
    *   @param activity activity
    */
    public static Point getDisplaySize(Activity activity){
        Point point = new Point();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getSize(point);
        return point;
    }

}
