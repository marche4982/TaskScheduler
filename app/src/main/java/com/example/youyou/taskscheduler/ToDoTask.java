package com.example.youyou.taskscheduler;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.RegEx;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import static com.example.youyou.taskscheduler.TaskScheduler.db;

/**
 * Created by youyou on 2018/01/16.
 */

public class ToDoTask extends RealmObject {
    @PrimaryKey
    public int id;

    @Required
    public Date startDate;                // タスクの開始日付
    public Date endDate;                  // タスクの終了日付
    public String taskName;               // タスク名
    public Boolean bIsChecked = false;  // タスク終了チェック

    public String taskMemo; // 説明用のメモ

    //public Boolean bHasImage;   // 画像有り無しフラグ
    //public Bitmap image;     // 画像

    // getter setter
    public int getId(){ return this.id;}
    public void setId(int id){ this.id = id;};




    public Date getStartDate(){return this.startDate;}
    public void setStartDate(Date date){ this.startDate = date;}

    public Date getEndDate(){return this.endDate;}
    public void setEndDate(Date date){ this.endDate = date;}

    public String getTaskName(){ return this.taskName;}
    public void setTaskName(String taskName){ this.taskName = taskName;}

    public Boolean getbIsChecked(){ return this.bIsChecked;}
    public void setbIsChecked(Boolean bIsChecked){ this.bIsChecked = bIsChecked;}
    public void updateCheck(boolean checked){
        this.setbIsChecked(checked);
        db.save(this);
    }

    public String getTaskMemo(){return this.taskMemo;}
    public void setTaskMemo(String taskMemo){this.taskMemo = taskMemo;}


    Boolean checkAll(){
        Boolean ret = true;

        ret |= checkTaskMemo();
        ret |= checkTaskName();
        ret |= checkStartDate();
        ret |= checkEndDate();
        ret |= checkBIsChecked();

        return ret;
    }

    Boolean checkTaskName(){
        Boolean ret = true;
        if( this.taskName == null ) {
            ret = false;
        }
        return ret;
    }

    Boolean checkBIsChecked(){
        Boolean ret = true;
        if( this.bIsChecked == null ){
            ret = false;
        }
        return ret;
    }

    Boolean checkTaskMemo(){
        Boolean ret = true;
        if( this.taskMemo == null ){
            ret = false;
        }
        return ret;
    }

    Boolean checkStartDate(){
        return checkDate(this.getStartDate());
    }

    Boolean checkEndDate(){
        return checkDate(this.getEndDate());
    }

    Boolean checkDate(Date date){
        Boolean ret = true;
        if( date == null ){
            return false;
        }

        if( date.toString().length() != 8){
            ret = false;
        }
        else{
            Pattern pattern = Pattern.compile("2[0-9]{7}");
            Matcher m = pattern.matcher(date.toString());
            if( m.groupCount() == 0 ){
                ret = false;
            }
        }

        return ret;
    }

}
