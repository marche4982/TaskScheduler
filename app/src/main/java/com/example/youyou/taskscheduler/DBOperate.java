package com.example.youyou.taskscheduler;

import android.content.Context;

import java.util.Date;

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
        RealmConfiguration rConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(rConfig);
        realm = Realm.getInstance(rConfig);
    }

    /*
    *   レコードを取得する
    *
    *   @param task 取得対象のタスク
    */
    public ToDoTask search(int id){
        return realm.where(ToDoTask.class).equalTo("id", id).findFirst();
    }

    public RealmResults<ToDoTask> getAll(){
        RealmResults<ToDoTask> results = realm.where(ToDoTask.class).findAll();
        return results;
    }

    /*
    *   タスクを追加or更新する
    *
    *   @param task 追加or更新するタスク
    */
    public void save(ToDoTask task){
        realm.beginTransaction();
        realm.insertOrUpdate(task);
        realm.commitTransaction();
    }

    /*
    *   タスクを削除する
    *
    *   @param task 削除するタスク
    */
    public void delete(ToDoTask task){
        realm.beginTransaction();
        search(task.getId()).deleteFromRealm();
        realm.commitTransaction();
    }

    /*
    *   日付を指定してタスク取得
    *
    *   @param date 取得する日付
    */
    public RealmResults<ToDoTask> getTaskwithDate(Date date){
        RealmResults<ToDoTask> all = realm.where(ToDoTask.class).findAll();
        RealmResults<ToDoTask> a = realm.where(ToDoTask.class).lessThanOrEqualTo("startDate", date).findAll();
        RealmResults<ToDoTask> b = realm.where(ToDoTask.class).greaterThanOrEqualTo("startDate", date).findAll();
        RealmResults<ToDoTask> d = realm.where(ToDoTask.class).lessThanOrEqualTo("startDate", date).greaterThanOrEqualTo("endDate", date).findAll();;

        return realm.where(ToDoTask.class).lessThanOrEqualTo("startDate", date).greaterThanOrEqualTo("endDate", date).findAll();
    }

}
