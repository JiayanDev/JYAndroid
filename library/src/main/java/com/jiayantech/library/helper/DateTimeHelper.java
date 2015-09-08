package com.jiayantech.library.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class DateTimeHelper {
    public Context mContext;
    private Calendar mCalendar = Calendar.getInstance();
    public long time = 0;

    public DateTimeHelper(Context context) {
        mContext = context;
    }

    public Dialog showDateTimeDialog(final OnSetDateTimeListener l) {
        return showDateDialog(l, true, false);
    }

    public Dialog showDateTimeDialog(final OnSetDateTimeListener l, boolean curMax) {
        return showDateDialog(l, true, curMax);
    }

    public Dialog showDateDialog(final OnSetDateTimeListener l, boolean curMax) {
        return showDateDialog(l, false, curMax);
    }

    /**
     * @param l
     * @param pickTime
     * @param curMax   当前时间为最大时间
     * @return
     */
    public Dialog showDateDialog(final OnSetDateTimeListener l, final boolean pickTime, boolean curMax) {
        DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (pickTime) {
                    showTimeDialog(l);
                } else {
                    l.onSetDateTime(mCalendar);
                }

            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        if (curMax) dialog.getDatePicker().setMaxDate(mCalendar.getTimeInMillis());

//        dialog.getDatePicker().set
        return dialog;
    }

    public Dialog showTimeDialog(final OnSetDateTimeListener l) {
        Dialog dialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);
                mCalendar.set(Calendar.MINUTE, minute);
                l.onSetDateTime(mCalendar);
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public interface OnSetDateTimeListener {
        void onSetDateTime(Calendar calendar);
    }
}
