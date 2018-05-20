package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
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
    private TextView remindTime;
    private CheckBox isRemind;
    private Activity activity;
    private Date defDate;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.newtask_create);
        activity = this;

        Intent intent = getIntent();
        defDate = new Date();
        defDate.setTime(intent.getLongExtra("date", 0));

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
        taskNameEdit.setFocusable(true);                // フォーカスを有効に
        taskNameEdit.setFocusableInTouchMode(true);     // タッチでのフォーカス有効

        memoEdit = (EditText)findViewById(R.id.edit_memo);

        startDate = (TextView)findViewById(R.id.textview_startdate);
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getFragmentManager(), "startDate");
            }
        });
        startDate.setText(setDate(defDate));

        endDate = (TextView)findViewById(R.id.textview_endDate);
        endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getFragmentManager(), "endDate");
            }
        });
        endDate.setText(setDate(defDate));

        endDate = (TextView)findViewById(R.id.textview_endDate);

        isRemind = (CheckBox)findViewById(R.id.remindCheckBox);

        remindTime = (TextView)findViewById(R.id.remindTime);
        remindTime.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    TimeDialogFragment dialog = new TimeDialogFragment();
                    dialog.show(getFragmentManager(), "remindTime");
                }
          }
        );
    }

    public String setDate(Date date){
        String str = String.format("%04d%02d%02d", date.getYear()+1900, date.getMonth(), date.getDate());
        return str;
    }

    public void setStartDate(String str){
        this.startDate.setText(str);
    }

    public void setEndDate(String str){
        this.endDate.setText(str);
    }

    public void setRemindTime(String str){ this.remindTime.setText(str);}

    public void saveTask(){
        ToDoTask newTask = new ToDoTask();
        newTask.setId(TaskScheduler.db.getNewId());
        newTask.setTaskName(taskNameEdit.getText().toString());
        newTask.setTaskMemo((memoEdit.getText().toString()));
        newTask.setStartDate(StringToDate(startDate.getText().toString()));
        newTask.setEndDate(StringToDate(endDate.getText().toString()));
        newTask.setbIsChecked(isRemind.isChecked());
        newTask.setRemindTime(StringToTime(remindTime.getText().toString()));

        if( newTask.checkAll() == false){
            return;
        }

        TaskScheduler.db.save(newTask);
    }

    private Date StringToTime(String strTime){
        Date date = null;
        if( strTime.length() == 5 ){
            date = new Date();

            String hour = strTime.substring(0,2);
            date.setTime(Integer.parseInt(hour));

            String minute = strTime.substring(3, 5);
            date.setMinutes(Integer.parseInt(minute));
        }

        return date;
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
