package com.example.youyou.taskscheduler;

import android.content.Context;

import java.util.Date;
import java.util.Iterator;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

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

    public void updateCheck(ToDoTask task, boolean check){
        realm.beginTransaction();
        task.setbIsChecked(check);
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

    public int getNewId() {

        int newid = 0;
        ToDoTask task = null;

        if (realm.where(ToDoTask.class).count() > 0) {
            task = realm.where(ToDoTask.class).findAllSorted("id", Sort.DESCENDING).first();
            newid = task.getId() + 1;
        }

        return newid;
    }

    /*
    *   日付を指定してタスク取得
    *
    *   @param date 取得する日付
    */
    public RealmList<ToDoTask> getTaskwithDate(Date date){

        RealmList<ToDoTask> list = new RealmList<ToDoTask>();
        list.addAll( realm.where(ToDoTask.class).lessThanOrEqualTo("startDate", date).greaterThanOrEqualTo("endDate", date).findAll());
        return list;
    }

    public RealmList<ToDoTask> getTaskwithDayOfWeek(int DayOfWeek){
        RealmList<ToDoTask> list = new RealmList<ToDoTask>();
        list.addAll(realm.where(ToDoTask.class).findAll());
        Iterator<ToDoTask> iterator = list.iterator();
        while(iterator.hasNext()){
            int nRegularyDay = iterator.next().getnRegularDay();
            if( (nRegularyDay & (0x01 << DayOfWeek)) == 0 ){
                // 今日の曜日にbitが立っていないなら, 結果から省く
                iterator.remove();
            }
        }

        return list;
    }

    public RealmList<ToDoTask> getTask(Date date){
        RealmList<ToDoTask> list = new RealmList<ToDoTask>();
        list.addAll(getTaskwithDate(date));
        list.addAll(getTaskwithDayOfWeek(date.getDay()));

        return list;
    }

}
