package com.example.youyou.taskscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.List;

/**
 * Created by youyou on 2018/01/18.
 */

public class TaskListAdapter extends ArrayAdapter<ToDoTask> {

    private LayoutInflater infalter;

    public TaskListAdapter(Context context, int resource, List<ToDoTask> object){
        super(context, resource, object);
        infalter = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if( convertView == null ){
            convertView = infalter.inflate(R.layout.todo_task, null);
        }

        /* View が追加された時の動きを入れる */


        return convertView;
    }

}
