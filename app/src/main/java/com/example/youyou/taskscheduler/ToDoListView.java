package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmResults;

import static com.example.youyou.taskscheduler.TaskScheduler.db;

/**
 * Created by youyou on 2018/01/15.
 */

public class ToDoListView extends Activity {

    private Date mDate;
    private ListView listTask;
    private RealmList<ToDoTask> task;
    private Activity activity;

    private TextView text_listTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);

        activity = this;
        Intent intent = getIntent();
        Long time = intent.getLongExtra(getResources().getString(R.string.bundle_time), 0);

        this.mDate = new Date();
        this.mDate.setTime(time);

        text_listTitle = (TextView)findViewById(R.id.list_title);
        text_listTitle.setText(String.format("%04d年%02d月%02d日", this.mDate.getYear()+1900, this.mDate.getMonth()+1, this.mDate.getDate()));

        db.getAll();
        task = db.getTask(this.mDate);

        TaskListAdapter adapter = new TaskListAdapter(getApplicationContext(), 0, task);

        listTask = (ListView)findViewById(R.id.list_task);
        listTask.setAdapter(adapter);
    }

    /* listTaskにレコードの更新処理を入れるか？ */
}
