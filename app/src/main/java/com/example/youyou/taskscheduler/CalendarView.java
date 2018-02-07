package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by youyou on 2018/01/06.
 */

public class CalendarView extends android.support.v4.app.Fragment {

    private static final int WEEKDAYS = 7;
    private Calendar mCalendar = null;
    private static Date now;
    private LinearLayout ParentLayout;    // カレンダーの最上位のLinearLayout

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setCalendar(Calendar calendar){
        this.mCalendar = calendar;
        now = this.mCalendar.getTime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.calendar_main, null);
        ParentLayout = (LinearLayout)view.findViewById(R.id.calendar_parent);

        setCalendar(CalendarFragmentPagerAdapter.getPresentCalendar());
        setCalendarTitle(now);
        setDayofWeek();
        setMonthDays();

        return view;
    }

    /*
    * 　date の年月を文字列 YYYY年MM月 に変換
    *
    *   @param activity date セットする日付
    */
    private String ConvDateToString(Date date){
        int nDefaultYear = getResources().getInteger(R.integer.defaultYear);
        int nDefaultMonth = getResources().getInteger(R.integer.defaultMonth);

        String string = String.valueOf(date.getYear() + nDefaultYear) + "年" + String.valueOf(date.getMonth() + nDefaultMonth) + "月";
        return string;
    }

    /*
    * 　タイトル欄に年月を設定する
    *
    *   @param activity date セットする日付
    */
    private void setCalendarTitle(Date date){
        // タイトル欄に表示する年月をセットする
        String strTitle = ConvDateToString(date);
        TextView title = (TextView)ParentLayout.findViewById(R.id.calendar_title);
        title.setText(strTitle);
    }

    /*
    * 　タイトル欄に年月を設定する
    *
    *   @param activity date セットする日付
    */
    private void setDayofWeek(){
        LinearLayout mWeekLayout = (LinearLayout)ParentLayout.findViewById((R.id.calendar_date));

        LinearLayout mWeekTitle = new LinearLayout(getActivity());

        for(int i = 0; i < 7; i++){
            DateTextView mDateTextView = new DateTextView(getActivity(), ParentLayout);

            // 曜日を表示
            String[] strDayOfTheWeek = getResources().getStringArray(R.array.day_of_the_week);
            mDateTextView.setText(strDayOfTheWeek[i]);
            mWeekTitle.addView(mDateTextView);
        }

        mWeekLayout.addView(mWeekTitle);
    }

    /*
   * 　日付欄に日付をセットする（1か月）
   *
   */
    private void setMonthDays(){
        List<Date> monthDate = getDateMonth(mCalendar);

        List<Date> mWeekDate = new ArrayList<Date>();
        int nFirstDay = getDayOfWeekFirstDay(); // 最初の日が何曜日であるか取得
        for(int j = 0; j < nFirstDay; j++){
            mWeekDate.add(null);
        }

        for(int i = 0; i < monthDate.size(); i++){
            mWeekDate.add(monthDate.get(i));

            // 1週間分のデータを入れる
            if( mWeekDate.size() == WEEKDAYS || i == (monthDate.size() -1) ) {
                setWeekDays(mWeekDate);
                mWeekDate.clear();
            }
        }
    }

    /*
   * 　月の最初の1日が何曜日であるか取得
   *
   *   @ret integer 曜日の値
   */
    private int getDayOfWeekFirstDay(){
        Calendar calendar = (Calendar)mCalendar.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int nDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        nDayOfWeek = ConvDayOfWeek(nDayOfWeek);
        return nDayOfWeek;
    }

    /*
    * 　曜日の値 Calendar.SUNDAY ～　SATURDAY　を内部値に変換する
    *
    *   @param num  Calendarクラスの曜日値
    *
    *   @ret nDayOfWeek 曜日の値（内部）
    */
    private int ConvDayOfWeek(int num){
        int nDayOfWeek = 0;
        switch(num){
            case Calendar.SUNDAY:
                nDayOfWeek = getResources().getInteger(R.integer.sunday);
                break;
            case Calendar.MONDAY:
                nDayOfWeek = getResources().getInteger(R.integer.monday);
                break;
            case Calendar.TUESDAY:
                nDayOfWeek = getResources().getInteger(R.integer.tuesday);
                break;
            case Calendar.WEDNESDAY:
                nDayOfWeek = getResources().getInteger(R.integer.wednesday);
                break;
            case Calendar.THURSDAY:
                nDayOfWeek = getResources().getInteger(R.integer.thursday);
                break;
            case Calendar.FRIDAY:
                nDayOfWeek = getResources().getInteger(R.integer.friday);
                break;
            case Calendar.SATURDAY:
                nDayOfWeek = getResources().getInteger(R.integer.saturday);
                break;
            default:
                break;
        }

        return nDayOfWeek;
    }

     /*
    * 　日付を設定する（1週間）
    *
    *   @param activity date セットする日付
    */
     private void setWeekDays(List<Date> date){
         LinearLayout mWeekLayout = (LinearLayout)ParentLayout.findViewById((R.id.calendar_date));
         LinearLayout mWeekDays = new LinearLayout(getActivity());
         for(int i = 0; i < date.size(); i++){
             DateTextView mDateTextView = new DateTextView(getActivity(), ParentLayout);
             mDateTextView.setViewHeight(getActivity(), 5);
             Date mDate = date.get(i);
             if( mDate != null ) {
                 mDateTextView.setDate(mDate);
             }
             mWeekDays.addView(mDateTextView);
         }
         mWeekLayout.addView(mWeekDays);
     }

    /*
    * 　同じ月の1日～最終日までをarrayで返す
    *
    *   @paramc calendar 現在のカレンダー
    */
    public List<Date> getDateMonth(Calendar calendar){
        Calendar tCalendar = (Calendar)mCalendar.clone();
        tCalendar.set(Calendar.DATE, 1);
        List<Date> arrayDate = new ArrayList<Date>();
        while(true){
            // 違う月になったら、抜ける
            if( IsSameMonth(mCalendar, tCalendar) == false ){
                break;
            }
            arrayDate.add(tCalendar.getTime()); // date をarray　に詰める
            tCalendar.add(Calendar.DATE, 1);    // 次の日に移動
        }
        return arrayDate;
    }

    /*
    * 　calendar1とcalendar2が同じ月か判定する
    *
    *   @paramc calendar 現在のカレンダー
    *
    *   @ret  true   同じ月
    *   @ret  false  違う月
    */
    public static boolean IsSameMonth(Calendar calendar1, Calendar calendar2){
        return calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

}
