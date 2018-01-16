package com.example.youyou.taskscheduler;

import android.app.Application;

import io.realm.RealmResults;

/**
 * Created by youyou on 2018/01/16.
 */

public class TaskScheduler extends Application {

    public DBOperate db;

    @Override
    public void onCreate(){
        super.onCreate();
        db = new DBOperate(this);
    }


}
