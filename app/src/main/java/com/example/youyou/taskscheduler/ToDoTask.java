package com.example.youyou.taskscheduler;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Date;

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
    public Date startDate;     // タスクの開始日付
    public Date endDate;       // タスクの終了日付
    public String taskName;      // タスク名
    public Boolean bIsChecked;  // タスク終了チェック

    public String taskMemo; // 説明用のメモ

    //public Boolean bHasImage;   // 画像有り無しフラグ
    //public Bitmap image;     // 画像

    // getter setter
    public int getId(){ return this.id;}
    public void setId(int id){ this.id = id;};




    public Date getStartDate(){return this.startDate;}
    public void setStartDate(Date date){ this.startDate = date;}

    public Date getEndDate(){return this.endDate;}
    public void setEndDate(Date date){ this.endDate = endDate;}

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

    //public Boolean getbHasImage(){return this.bHasImage;}
    //public void setbHasImage(Boolean bHasImage){ this.bHasImage = bHasImage;}

    //public Bitmap getImage(){return this.image;}
    //public void setImage(Bitmap image){this.image = image;}

}
