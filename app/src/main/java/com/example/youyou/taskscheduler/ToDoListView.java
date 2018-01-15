package com.example.youyou.taskscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Date;

/**
 * Created by youyou on 2018/01/15.
 */

public class ToDoListView extends Activity {

    private Date mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Long time = intent.getLongExtra(getResources().getString(R.string.bundle_time), 0);
        this.mDate.setTime(time);
    }

    // 明日はここから
    // ArrrayAdapter のクラスを使って。
    // list_task に arrayAdapter をセットする


    // DBから、mDate にひっかかるデータを引っ張ってくる

    // ★DBを先に作らないとダメか。★
}
