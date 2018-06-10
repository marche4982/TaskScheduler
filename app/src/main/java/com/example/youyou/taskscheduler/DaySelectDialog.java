package com.example.youyou.taskscheduler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by youyou on 2018/05/12.
 */

public class DaySelectDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 曜日を選択するダイアログをつくる
        // 曜日が選択されたら、その曜日にタスクを標示する

        String[] items = {"日曜日","月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"};
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("曜日選択")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer)which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int daySelect = 0;
                        for(Integer i: checkedItems){
                            daySelect +=  ((0x01) << i);
                        }
                        ((NewTaskCreate)getActivity()).setDayofWeek(daySelect);
                    }
                })
                .setNegativeButton("Cancel", null)
                ;

         return builder.create();
    }
}
