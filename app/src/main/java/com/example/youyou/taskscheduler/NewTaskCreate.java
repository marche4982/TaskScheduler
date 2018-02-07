package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

/**
 * Created by youyou on 2018/01/31.
 */

public class NewTaskCreate extends Activity {

    private Button OkButton;
    private EditText taskNameEdit;
    private EditText memoEdit;
    private EditText startEdit;
    private EditText endEdit;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.newtask_create);

        OkButton = (Button)findViewById(R.id.ok_button);
        OkButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    saveTask();
                    finish();
                }
                return false;
            }
        });

        taskNameEdit = (EditText)findViewById(R.id.edit_taskname);
        memoEdit = (EditText)findViewById(R.id.edit_memo);
        startEdit = (EditText)findViewById(R.id.edit_startdate);
        endEdit = (EditText)findViewById(R.id.edit_enddate);
    }

    public void saveTask(){
        ToDoTask newTask = new ToDoTask();
        newTask.setTaskName(taskNameEdit.getText().toString());
        newTask.setTaskMemo((memoEdit.getText().toString()));
        newTask.setStartDate(StringToDate(startEdit.getText().toString()));
        newTask.setEndDate(StringToDate(endEdit.getText().toString()));
        TaskScheduler.db.save(newTask);
    }

    private Date StringToDate(String strDate){
        Date date = new Date();

        if( strDate.length() >= 14 ) {
            String year = strDate.substring(0, 3);
            date.setYear(Integer.parseInt(year));

            String month = strDate.substring(4, 5);
            date.setMonth(Integer.parseInt(month));

            String day = strDate.substring(6, 7);
            date.setDate(Integer.parseInt(day));
        }

        return date;
    }



}
