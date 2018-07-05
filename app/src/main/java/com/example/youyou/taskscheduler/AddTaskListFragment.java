package com.example.youyou.taskscheduler;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import static android.view.View.GONE;

/**
 * Created by youyou on 2018/06/24.
 */

public class AddTaskListFragment extends android.support.v4.app.Fragment {

    private TextView txtviewTaskName;
    private TextView txtviewTaskMemo;
    private LinearLayout layout_column1;
    private LinearLayout layout_column2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_memo_list, container, false);

        // DBから
        Bundle bundle = getArguments();
        TaskParcelable task = bundle.getParcelable(getResources().getString(R.string.bundle_tag_task));

        txtviewTaskName = (TextView)view.findViewById(R.id.mainmemo_taskname);
        txtviewTaskName.setText(task.taskName);

        txtviewTaskMemo = (TextView)view.findViewById(R.id.mainmemo_taskmemo);
        txtviewTaskMemo.setText(task.taskMemo);

        layout_column2 = (LinearLayout)view.findViewById(R.id.layout_mainmemo_columne2);

        return view;
    }

    // メモの修正できるようにする
        // メモをクリックしたら、更新できるようにする

    // 全体的にデザインをよくする

    // 更新画面でメモ色を変えられるようにする

    // 定期的なタスクを入力できるようにする



}
