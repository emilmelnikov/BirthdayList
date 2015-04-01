package com.emilmelnikov.birthdaylist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class BirthdayPickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public interface OnBirthdayPickedListener {
        public void onBirthdayPicked(int year, int monthOfYear, int dayOfMonth);
    }

    private OnBirthdayPickedListener onBirthdayPickedListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        onBirthdayPickedListener = (OnBirthdayPickedListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = Preferences.getBirthdayYear(getActivity());
        int month = Preferences.getBirthdayMonth(getActivity());
        int day = Preferences.getBirthdayDay(getActivity());

        if ((year == -1) || (month == -1) || (day == -1)) {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        onBirthdayPickedListener.onBirthdayPicked(year, monthOfYear, dayOfMonth);
    }
}
