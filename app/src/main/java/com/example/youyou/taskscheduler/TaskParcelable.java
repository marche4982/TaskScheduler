package com.example.youyou.taskscheduler;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by youyou on 2018/06/25.
 */

public class TaskParcelable implements Parcelable {

    public String taskName;               // タスク名
    public String taskMemo; // 説明用のメモ
    public Long startDate;                // タスクの開始日付
    public Long endDate;                  // タスクの終了日付
    public int nRegularDay;         // 定期的な設定.各bitがOnなら設定あり、Offなら設定なし。1bit目が日曜日で7bit目が土曜


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(taskName);
        out.writeString(taskMemo);
        out.writeLong(startDate);
        out.writeLong(endDate);
        out.writeInt(nRegularDay);
    }

    public static final Parcelable.Creator<TaskParcelable> CREATOR
            = new Parcelable.Creator<TaskParcelable>() {
        public TaskParcelable createFromParcel(Parcel in) {
            return new TaskParcelable(in);
        }

        public TaskParcelable[] newArray(int size) {
            return new TaskParcelable[size];
        }
    };

    private TaskParcelable(Parcel in){
        if( in != null ){
            taskName = in.readString();
            taskMemo = in.readString();
            startDate = in.readLong();
            endDate = in.readLong();
            nRegularDay = in.readInt();
        }
    }


    public TaskParcelable(String taskName, String taskMemo, long startDate, long endDate, int nRegularDay) {
        this.taskName = taskName;
        this.taskMemo  = taskMemo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nRegularDay = nRegularDay;
    }


}

