package com.example.youyou.taskscheduler;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by youyou on 2018/05/20.
 */

public class TimeDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle onSavedInstance){

        final Date date = new Date();

        TimePickerDialog dialog = new TimePickerDialog(
                getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String timeStr = String.format("%02d:%02d", hourOfDay, minute);
                        // MainActivityのインスタンスを取得
                        NewTaskCreate activity = (NewTaskCreate) getActivity();
                        activity.setRemindTime(timeStr);
                    }
                },
                date.getHours(),
                date.getMinutes(),
                true
        );

        return dialog;
    }
}
