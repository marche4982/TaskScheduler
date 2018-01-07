package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
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

public class CalendarView extends Activity {

    private static final int WEEKDAYS = 7;
    private static Calendar mNowCalendar;

    private LinearLayout ParentLayout;    // カレンダーの最上位のLinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // カレンダーから現在時刻取得
        mNowCalendar = Calendar.getInstance();
        Date now = mNowCalendar.getTime();

        ParentLayout = (LinearLayout)View.inflate(this, R.layout.activity_main, null);
        setContentView(ParentLayout);

        setCalendarTitle(now);
        setDayofWeek();
        setMonthDays();
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

        LinearLayout mWeekTitle = new LinearLayout(this);

        for(int i = 0; i < 7; i++){
            DateTextView mDateTextView = new DateTextView(this);

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
        List<Date> monthDate = getDateMonth(mNowCalendar);

        List<Date> mWeekDate = new ArrayList<Date>();
        int nFirstDay = getDayOfWeekFirstDay(); // 最初の日が何曜日であるか取得
        for(int j = 0; j < nFirstDay; j++){
            Date date = new Date(); // 空のデータを入れる
            mWeekDate.add(date);
        }

        for(int i = 0; i < monthDate.size(); i++){
            // 1週間分のデータを入れる
            if( mWeekDate.size() == WEEKDAYS ){
                setWeekDays(mWeekDate);
                mWeekDate.clear();
            }
            mWeekDate.add(monthDate.get(i));
        }
    }

    /*
   * 　月の最初の1日が何曜日であるか取得
   *
   *   @ret integer 曜日の値
   */
    private int getDayOfWeekFirstDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

     /*
    * 　日付を設定する（1週間）
    *
    *   @param activity date セットする日付
    */
     private void setWeekDays(List<Date> date){
         LinearLayout mWeekLayout = (LinearLayout)ParentLayout.findViewById((R.id.calendar_date));

         LinearLayout mWeekDays = new LinearLayout(this);
         for(int i = 0; i < WEEKDAYS; i++){
             DateTextView mDateTextView = new DateTextView(this);
             mDateTextView.setText(String.valueOf(date.get(i).getDate()));  // 日にちをテキストにセット
             mWeekDays.addView(mDateTextView);
         }

         mWeekLayout.addView(mWeekDays);
     }

    /*
    * 　同じ月の1日～最終日までをarrayで返す
    *
    *   @paramc calendar 現在のカレンダー
    */
    public static List<Date> getDateMonth(Calendar calendar){
        Calendar tCalendar = Calendar.getInstance();
        tCalendar.set(Calendar.DATE, 1);
        List<Date> arrayDate = new ArrayList<Date>();
        while(true){
            // 違う月になったら、抜ける
            if( IsSameMonth(mNowCalendar, tCalendar) == false ){
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



    // XX年　XX月

    // 月ごとに複数回追加する
}
