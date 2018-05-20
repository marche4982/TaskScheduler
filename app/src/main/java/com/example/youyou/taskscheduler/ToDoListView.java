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

import io.realm.RealmResults;

import static com.example.youyou.taskscheduler.TaskScheduler.db;

/**
 * Created by youyou on 2018/01/15.
 */

public class ToDoListView extends Activity {

    private Date mDate;
    private ListView listTask;
    private RealmResults<ToDoTask> task;
    private Button newTaskButton;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);

        activity = this;

        Intent intent = getIntent();
        Long time = intent.getLongExtra(getResources().getString(R.string.bundle_time), 0);

        this.mDate = new Date();
        this.mDate.setTime(time);

        newTaskButton = (Button)findViewById(R.id.newTaskButton);
        newTaskButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    Intent intent = new Intent(activity, NewTaskCreate.class);
                    intent.putExtra("date", mDate.getTime());
                    activity.startActivity(intent);
                }
                return false;
            }
        });


        db.getAll();
        task = db.getTaskwithDate(this.mDate);

        TaskListAdapter adapter = new TaskListAdapter(getApplicationContext(), 0, task);

        listTask = (ListView)findViewById(R.id.list_task);
        listTask.setAdapter(adapter);
    }

    /* listTaskにレコードの更新処理を入れるか？ */
}
