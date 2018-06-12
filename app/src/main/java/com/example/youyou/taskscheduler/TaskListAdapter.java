package com.example.youyou.taskscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import static com.example.youyou.taskscheduler.TaskScheduler.db;

/**
 * Created by youyou on 2018/01/18.
 */

public class TaskListAdapter extends ArrayAdapter<ToDoTask> {

    private LayoutInflater infalter;
    private CheckBox taskCheckBox;
    private TextView taskName;
    private ToDoTask task;
    private TextView taskMemo;
    private TextView taskPeriod;

    private LinearLayout layout_column1;
    private LinearLayout layout_column2;

    public TaskListAdapter(Context context, int resource, List<ToDoTask> object){
        super(context, resource, object);
        infalter = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /* タスクをクリックした後に、メモなどを表示するように変える */
    /* タスクを2件入れると消える？ */

    public View getView(final int position, View convertView, ViewGroup parent){
        if( convertView == null ){
            convertView = infalter.inflate(R.layout.todo_task, null);
        }
        task = (ToDoTask)getItem(position);


        layout_column1 = (LinearLayout)convertView.findViewById(R.id.layout_todotask_column1);
        SetLayoutOnTouch(layout_column1);

        layout_column2 = (LinearLayout)convertView.findViewById(R.id.layout_todotask_column2);
        SetLayoutOnTouch(layout_column2);

        /* View が追加された時の動きを入れる */
        /* チェックボックス */
        taskCheckBox = (CheckBox)convertView.findViewById(R.id.task_check);
        taskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.updateCheck(isChecked);
            }
        });

        /* タスク名 */
        taskName = (TextView)convertView.findViewById(R.id.task_name);
        taskName.setText(task.taskName);

        // メモ
        taskMemo = (TextView)convertView.findViewById(R.id.textview_todotask_memo);
        taskMemo.setText(task.taskMemo);

        // 期間の設定
        taskPeriod = (TextView)convertView.findViewById(R.id.textview_todotask_period);
        SetTextViewPeriod(taskPeriod, task);

        return convertView;
    }


    //
    //  タッチされたらlayout2を隠すイベントをセットする
    //
    private void SetLayoutOnTouch(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    // layout_column1がタッチされたら
                    layout_column2 = ((LinearLayout)v.getParent()).findViewById(R.id.layout_todotask_column2);
                    if( layout_column2.getVisibility() == View.INVISIBLE){
                        layout_column2.setVisibility(View.VISIBLE);
                    }
                    else{
                        layout_column2.setVisibility(View.INVISIBLE);
                    }
                }
                return false;
            }
        });
    }

    //
    // テキストビューの期間に期間or曜日をセットする
    //
    private void SetTextViewPeriod(TextView view, ToDoTask task){
        String strPeriod = "";
        if( task.getStartDate() != null && task.getEndDate() != null ){
            Date startDate = task.getStartDate();
            Date endDate = task.getEndDate();

            String start = String.format("%04d/02d/%02d %02d:%02d%02d",
                    startDate.getYear(), startDate.getMonth(), startDate.getDate(), startDate.getHours(), startDate.getMinutes(), startDate.getSeconds());
            String end = String.format("%04d/02d/%02d %02d:%02d%02d",
                    endDate.getYear(), endDate.getMonth(), endDate.getDate(), endDate.getHours(), endDate.getMinutes(), endDate.getSeconds());

            strPeriod = start + "～" + end;
        }
        else{
            int regular = task.getnRegularDay();
            int cnt = 0;
            while(regular != 0){
                if( (regular & 0x00000001) != 0 ){
                    switch(cnt){
                        case 0:
                            strPeriod += "日";
                            break;
                        case 1:
                            strPeriod += "月";
                            break;
                        case 2:
                            strPeriod += "火";
                            break;
                        case 3:
                            strPeriod += "水";
                            break;
                        case 4:
                            strPeriod += "木";
                            break;
                        case 5:
                            strPeriod += "金";
                            break;
                        case 6:
                            strPeriod += "土";
                            break;
                        }
                    }
                }
                regular = regular >> 1;
                cnt += 1;
            }
            view.setText(strPeriod);
        }
}
