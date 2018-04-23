package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by youyou on 2018/01/07.
 */

public class DateTextView extends android.support.v7.widget.AppCompatTextView{

    private View mParentView;
    private Date mDate;
    private Boolean bFocused = FALSE;

    public DateTextView(final Activity activity, View mParentView){
        super(activity);
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

                if( mDate != null ) {
                    // MainActivity に返すための Date を格納する
                    CalendarFragmentPagerAdapter.SetTouchedDate(mDate);
                }


                if( activity.getComponentName().getShortClassName().equals(".ChooseDate")){
                    activity.finish();
                    return true;
                }

                Log.d("ClassName", activity.getComponentName().getShortClassName());

                Log.d("Equal", "=" + activity.getComponentName().getShortClassName().equals(".ChooseDate"));

                // 親がChooseDateの場合は、finishしなきゃいけないのか
                if( event.getAction() == MotionEvent.ACTION_UP ){
                    if( mDate != null ) {
                        Intent intent = new Intent(activity, ToDoListView.class);
                        Bundle bundle = new Bundle();
                        intent.putExtra(getResources().getString(R.string.bundle_time), mDate.getTime());
                        activity.startActivity(intent);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){

        return false;
    }

    public void setBackGroundColor(int color){
        this.setBackgroundColor(color);
    }

    /*
    * 　ビューの高さを設定する.
    *   ディスプレイの高さから計算
    *
    *   @param activity activity
    *   @param nLines  日付欄の行数
    */
    public void setViewHeight(Activity activity, int nLines){
        int nHeight;
    }

    /*
        today がこのView の日付と同じ日なら、
        View に色を付ける
        @param today 今日の日付
    */
    public void setTodayColor(Date today){
        if( this.mDate.getYear() == today.getYear() &&
         this.mDate.getMonth() == today.getMonth() &&
         this.mDate.getDay() == today.getDay() ) {
            this.setBackgroundColor(Color.YELLOW);
        }
    }

    /*
    *  ToDoListテーブルに渡すための 日付セット
    *
    *   @param activity activity
    *   @param nLines  日付欄の行数
    */
    public void setDate(Date date){
        this.mDate = date;
        this.setText(String.valueOf(this.mDate.getDate()));
    }

    /*
    * 　画面サイズを返す b
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
