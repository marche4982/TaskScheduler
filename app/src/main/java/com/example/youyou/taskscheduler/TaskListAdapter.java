package com.example.youyou.taskscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

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

    public TaskListAdapter(Context context, int resource, List<ToDoTask> object){
        super(context, resource, object);
        infalter = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if( convertView == null ){
            convertView = infalter.inflate(R.layout.todo_task, null);
        }
        task = (ToDoTask)getItem(position);

        /* View が追加された時の動きを入れる */
        /* チェックボックス */
        taskCheckBox = (CheckBox)convertView.findViewById(R.id.task_check);
        taskCheckBox.setChecked(task.bIsChecked);
        taskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /* TODO このtask更新は間違ってるかも */
                task.updateCheck(isChecked);
            }
        });

        /* タスク名 */
        taskName = (TextView)convertView.findViewById(R.id.task_name);
        taskName.setText(task.taskName);


        return convertView;
    }

}
