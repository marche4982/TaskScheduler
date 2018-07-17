package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmList;

import static com.example.youyou.taskscheduler.TaskScheduler.db;

public class MainActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    private ImageButton newTaskButton;
    private Activity activity;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        // 自動テストする

        // メモを削除編集する機能
            // →　編集削除ボタン付ける　→　idがないんで消せません
            // →　編集時の文字色とフォーカスカエル

        // 初期標示時に今日のタスクを追加
        // メモの色を変えられるようにする
        // カレンダーの真下にメモを来るようにしたい

        manager = getSupportFragmentManager();
        viewPager = (ViewPager)findViewById(R.id.calendar_pager);
        CalendarFragmentPagerAdapter.setMaxCalendar(getResources().getInteger(R.integer.maxCalendar));
        CalendarFragmentPagerAdapter adapter = new CalendarFragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(CalendarList.getNowCalendarIndex());

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ((MainActivity)activity).ClearTaskList();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        newTaskButton = (ImageButton)findViewById(R.id.newTaskButton);
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


    public void AddTaskList(Date touchedDate){

        FragmentTransaction transaction = manager.beginTransaction();
        if( manager.getBackStackEntryCount() != 0 ){
            // バックスタックありなら元に戻す
            manager.popBackStack();
        }

        Date dTouchdDate = CalendarFragmentPagerAdapter.GetTouchedDate();
        // DBから該当日付のレコードをとってきて、
        // その数分 add しないといけいない
        // レコードを bundle で渡して、Fragment側で受け取って
        // セットする

        RealmList<ToDoTask> taskList = SelectTaskFromDB(dTouchdDate);
        for(ToDoTask task : taskList){
            AddTaskListFragment fragment = new AddTaskListFragment();
            // 日付を渡す
            Bundle bundle = new Bundle();
            bundle.putLong("date", dTouchdDate.getTime());

            // タスクを渡す
            TaskParcelable parcel = new TaskParcelable(task.getTaskName(),task.getTaskMemo(),
                     task.getStartDate().getTime(),task.getEndDate().getTime(), task.getnRegularDay());
            bundle.putParcelable(getResources().getString(R.string.bundle_tag_task), parcel);
            fragment.setArguments(bundle);
            transaction.add(R.id.layout_main_taskList, fragment);
        }

        // バックスタックにトランザクション前の状態を置いておく
        transaction.addToBackStack(getResources().getString(R.string.fragment_backstack));

        transaction.commit();
    }

    public void ClearTaskList(){
        if( manager.getBackStackEntryCount() != 0 ){
            // バックスタックありなら元に戻す
            manager.popBackStack();
        }
    }


    public RealmList<ToDoTask> SelectTaskFromDB(Date dSelectDate){
        db.getAll();
        RealmList<ToDoTask> taskList = db.getTask(dSelectDate);
        return taskList;
    }

}
