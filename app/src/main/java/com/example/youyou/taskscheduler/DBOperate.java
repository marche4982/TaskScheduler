package com.example.youyou.taskscheduler;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by youyou on 2018/01/16.
 */

public class DBOperate {

    private Realm realm;

    DBOperate(Context context){
        Realm.init(context);
        RealmConfiguration rConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(rConfig);
        realm = Realm.getInstance(rConfig);
    }

    public RealmResults<ToDoTask> getAll(){
        RealmResults<ToDoTask> results = realm.where(ToDoTask.class).findAll();
        return results;
    }

    // DBOperateには基本的な出し入れの関数のみ定義するようにしよう
    // getAll
    // search
    // insert
    // delete
    // update

}
