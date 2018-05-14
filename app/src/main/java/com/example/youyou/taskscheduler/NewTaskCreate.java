package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by youyou on 2018/01/31.
 */

public class NewTaskCreate extends Activity {

    private Button OkButton;
    private EditText taskNameEdit;
    private EditText memoEdit;
    private TextView startDate;
    private TextView endDate;
    private Activity activity;


    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.newtask_create);
        activity = this;

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

        startDate = (TextView)findViewById(R.id.textview_startdate);
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getFragmentManager(), "startDate");
            }
        });

        endDate = (TextView)findViewById(R.id.textview_endDate);
        endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getFragmentManager(), "endDate");
            }
        });

        endDate = (TextView)findViewById(R.id.textview_endDate);
    }

    public void setStartDate(String str){
        this.startDate.setText(str);
    }

    public void setEndDate(String str){
        this.endDate.setText(str);
    }

    public void saveTask(){
        ToDoTask newTask = new ToDoTask();
        newTask.setTaskName(taskNameEdit.getText().toString());
        newTask.setTaskMemo((memoEdit.getText().toString()));

        // newTask.setStartDate(startPicker.get);
        //
        // picker からDate をいれる

        if( newTask.checkAll() == false){
            return;
        }

        TaskScheduler.db.save(newTask);
    }

    private Date StringToDate(String strDate){
        Date date = null;

        if( strDate.length() == 8 ) {
            date = new Date();

            String year = strDate.substring(0, 4);
            date.setYear(Integer.parseInt(year)-1900);

            String month = strDate.substring(4, 6);
            date.setMonth(Integer.parseInt(month)-1);

            String day = strDate.substring(6, 8);
            date.setDate(Integer.parseInt(day));
        }

        return date;
    }



}
