package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

/**
 * Created by youyou on 2018/01/31.
 */

public class NewTaskCreate extends Activity {

    private Button OkButton;
    private Button ReturnButton;

    private ImageButton saveButton;
    private ImageButton returnButton;

    private RadioButton radioButton_InputDate;
    private RadioButton radioButton_InputDayofWeek;

    private EditText taskNameEdit;
    private EditText memoEdit;
    private TextView startDate;
    private TextView endDate;
    private TextView remindTime;
    private TextView daySelect;
    private CheckBox isRemind;
    private Activity activity;
    private Date defDate;

    private int nRegularDay;   // 定期タスク設定

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.newtask_create);
        activity = this;

        Intent intent = getIntent();
        defDate = new Date();
        defDate.setTime(intent.getLongExtra("date", 0));

        saveButton = (ImageButton)findViewById(R.id.save_button);
        saveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    saveTask();
                    finish();
                }
                return false;
            }
        });

        returnButton = (ImageButton)findViewById(R.id.return_button);
        returnButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    finish();
                }
                return false;
            }
        });

        taskNameEdit = (EditText)findViewById(R.id.edit_taskname);
        taskNameEdit.setFocusable(true);                // フォーカスを有効に
        taskNameEdit.setFocusableInTouchMode(true);     // タッチでのフォーカス有効
        SetClearTextOnTouch(taskNameEdit, getResources().getString(R.string.newtask_value_defaulttitle));

        memoEdit = (EditText)findViewById(R.id.edit_memo);
        SetClearTextOnTouch(memoEdit, getResources().getString(R.string.newtask_value_defaultmemo));

        startDate = (TextView)findViewById(R.id.textview_startDate);
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

        daySelect = (TextView)findViewById(R.id.textview_daySelect);
        daySelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DaySelectDialog dialog = new DaySelectDialog();
                dialog.show(getFragmentManager(), "曜日選択");
            }
        });

        radioButton_InputDate = (RadioButton)findViewById(R.id.radioButton_newtask_inputdate);
        radioButton_InputDayofWeek = (RadioButton)findViewById(R.id.radioButton_newtask_inputdayofweek);
        SetDefaultRadioBtnState();
    }

    private void SetDefaultRadioBtnState(){
        SetRadioButtonState(radioButton_InputDate, true);
        SetRadioButtonState(radioButton_InputDayofWeek, false);
    }

    private void SetRadioButtonState(RadioButton radioButton, boolean checked){
        radioButton.setChecked(checked);
        radioButton.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // ボタンONOFFにあわせて、
                    int nViewId = buttonView.getId();
                    if (nViewId == R.id.radioButton_newtask_inputdate){
                        View view = (View)findViewById(R.id.layout_newtask_inputMethod_date);
                        if( isChecked == true ){    // 選択状態なら表示
                            view.setVisibility(View.VISIBLE);
                            startDate.setText(setDate(defDate));    // デフォ日時をセット
                            endDate.setText(setDate(defDate));
                        }
                        else{   // 非選択状態なら非表示
                            view.setVisibility(View.INVISIBLE);
                            startDate.setText("");                  // 日時はクリア
                            endDate.setText("");
                        }
                    }
                    else if( nViewId == R.id.radioButton_newtask_inputdayofweek){
                        View view = (View)findViewById(R.id.layout_newtask_inputMethod_dayofweek);
                        if( isChecked == true ){    // 選択状態なら表示
                            view.setVisibility(View.VISIBLE);
                        }
                        else{   // 非選択状態なら非表示
                            view.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });
        return  ;
    }

    public void SetClearTextOnTouch(final EditText editText, final String defaultString) {
        editText.setOnTouchListener(new EditText.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    int nColor = ((TextView)v).getCurrentTextColor();
                    if( nColor == getResources().getColor(R.color.color_editText_unInput) ){
                        // 未入力色なら、テキストをクリアして色を変える
                        ((TextView)v).setText("");
                    }
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            Boolean bBeforeUnInput = false; // 文字列の変更前は未入力状態
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if( editText.getCurrentTextColor() == getResources().getColor(R.color.color_editText_unInput) ){
                    // 未入力色か
                    bBeforeUnInput = true;
                }
                else{
                    bBeforeUnInput = false;
                }

                // 文字の入力前に色を変える
                if( start == 0 && count > 0 ){
                    editText.setTextColor(getResources().getColor(R.color.color_editText_Input));
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int nColor = editText.getCurrentTextColor();
                if( s.length() == 0 && bBeforeUnInput == false){
                    // 入力済みから0になったときは、未入力へ
                    s.append(defaultString);
                    editText.setTextColor(getResources().getColor(R.color.color_editText_unInput));
                    editText.setSelection(0);
                }
                else if( s.length() > 0 && bBeforeUnInput == true ){
                    // 未入力状態から入力されたら、デフォルト文字を解除
                    s.delete(1, s.length());
                }
            }
        });

    }

    public String setDate(Date date){
        String str = String.format("%04d%02d%02d", date.getYear()+1900, date.getMonth()+1, date.getDate());
        return str;
    }

    public void setDayofWeek(int dayofWeek){
        nRegularDay = dayofWeek;
        String StrDaySelect = "";
        for(Integer i = 0; i < 7; i++){
             int check = (dayofWeek >> i) & 0x01 ;
             if( check == 1) {
                 switch (i) {
                     case 0:
                         StrDaySelect += "日";
                         break;
                     case 1:
                         StrDaySelect += "月";
                         break;
                     case 2:
                         StrDaySelect += "火";
                         break;
                     case 3:
                         StrDaySelect += "水";
                         break;
                     case 4:
                         StrDaySelect += "木";
                         break;
                     case 5:
                         StrDaySelect += "金";
                         break;
                     case 6:
                         StrDaySelect += "土";
                         break;
                 }
             }
        }

        daySelect.setText(StrDaySelect);
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
        newTask.setId(TaskScheduler.db.getNewId()); // ID

        // タイトルは空だとしても必ず入れる
        newTask.setTaskName(taskNameEdit.getText().toString());    // EditText 自体にデフォルトテキストを持たせたいなあ

        String memo = new String();
        if( memoEdit.getCurrentTextColor() == getResources().getColor(R.color.color_editText_Input )) {
            // 未入力ならセットしない
            memo = memoEdit.getText().toString();
        }
        else{
            memo = "";
        }
        newTask.setTaskMemo(memo);

        if( startDate.length() > 0 ) {
            newTask.setStartDate(StringToDate(startDate.getText().toString()));
        }
        else{
            newTask.setStartDate(null);
        }

        if( startDate.length() > 0 ) {
            newTask.setEndDate(StringToDate(endDate.getText().toString()));
        }
        else{
            newTask.setEndDate(null);
        }

        newTask.setnRegularDay(nRegularDay);

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
