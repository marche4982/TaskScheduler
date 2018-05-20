package com.example.youyou.taskscheduler;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by youyou on 2018/05/12.
 */

public class CustomDialogFragment extends DialogFragment {

    final Calendar calendar = Calendar.getInstance();

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DatePickerDialog dateBuilder = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateStr = String.format("%04d", year) + String.format("%02d", month+1) + String.format("%02d", dayOfMonth);
                        // MainActivityのインスタンスを取得
                        NewTaskCreate activity = (NewTaskCreate) getActivity();

                        if( getTag() == "startDate") {
                            activity.setStartDate(dateStr);
                            activity.setEndDate(dateStr);   // 終了日も同時に設定
                        }
                        else if( getTag() == "endDate"){
                            activity.setEndDate(dateStr);
                        }
                    }
                },
                calendar.get(Calendar.YEAR), // 初期選択年
                calendar.get(Calendar.MONTH), // 初期選択月
                calendar.get(Calendar.DAY_OF_MONTH) // 初期選択日
        );


        return dateBuilder;
    }
}
