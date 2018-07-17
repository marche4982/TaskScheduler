package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private TaskParcelable task;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // DBから
        Bundle bundle = getArguments();
        task = bundle.getParcelable(getResources().getString(R.string.bundle_tag_task));

        View view = inflater.inflate(R.layout.main_memo_list, container, false);

        ImageButton bEditButton = view.findViewById(R.id.button_memo_edit);
        bEditButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ) {
                    Activity activity = getActivity();
                    Intent intent = new Intent(activity, NewTaskCreate.class);
                    intent.putExtra("task", task);
                    activity.startActivity(intent);
                }
                return false;
            }
        });

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
