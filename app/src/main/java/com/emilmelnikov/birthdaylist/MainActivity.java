package com.emilmelnikov.birthdaylist;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.emilmelnikov.birthdaylist.GuestDbHelper.GuestEntry;

public class MainActivity extends ActionBarActivity
        implements BirthdayPickerFragment.OnBirthdayPickedListener {

    private Button birthdayPickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birthdayPickerButton = (Button) findViewById(R.id.birthday_picker_button);

        int year = Preferences.getBirthdayYear(this);
        int month = Preferences.getBirthdayMonth(this);
        int day = Preferences.getBirthdayDay(this);

        if ((year == -1) || (month == -1) || (day == -1)) {
            String noBirthdayPickedLabel = getString(R.string.no_birthday_picked);
            birthdayPickerButton.setText(noBirthdayPickedLabel);
        } else {
            updateBirthdayPickerButton(year, month, day);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GuestDbHelper guestDbHelper = new GuestDbHelper(this);
        SQLiteDatabase db = guestDbHelper.getReadableDatabase();

        String[] projection =
                {GuestEntry._ID, GuestEntry.COLUMN_NAME_NAME, GuestEntry.COLUMN_NAME_GIFTS, GuestEntry.COLUMN_NAME_COLOR};
        String[] from =
                {GuestEntry.COLUMN_NAME_NAME, GuestEntry.COLUMN_NAME_GIFTS, GuestEntry.COLUMN_NAME_COLOR};
        int[] to = {R.id.name, R.id.gifts};

        Cursor cursor =
                db.query(GuestEntry.TABLE_NAME, projection, null, null, null, null, GuestEntry._ID);

        ColorSimpleCursorAdapter guestListAdapter =
                new ColorSimpleCursorAdapter(this, R.layout.listitem_guest, cursor, from, to, 0, GuestEntry.COLUMN_NAME_COLOR);

        ListView guestList = (ListView) findViewById(R.id.guest_list);
        guestList.setAdapter(guestListAdapter);
    }

    @Override
    public void onBirthdayPicked(int year, int monthOfYear, int dayOfMonth) {
        SharedPreferences prefs =
                getSharedPreferences(Preferences.NAME_BIRTHDAY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(Preferences.KEY_BIRTHDAY_YEAR, year);
        editor.putInt(Preferences.KEY_BIRTHDAY_MONTH, monthOfYear);
        editor.putInt(Preferences.KEY_BIRTHDAY_DAY, dayOfMonth);

        editor.apply();

        updateBirthdayPickerButton(year, monthOfYear, dayOfMonth);
    }


    public void addGuest(View view) {
        Intent intent = new Intent(this, AddGuestActivity.class);
        startActivity(intent);
    }

    public void showBirthdayPickerDialog(View view) {
        DialogFragment dialogFragment = new BirthdayPickerFragment();
        dialogFragment.show(getFragmentManager(), "birthdayPicker");
    }

    private void updateBirthdayPickerButton(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        Date birthday = new Date(c.getTimeInMillis());

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        String birthdayLabel = dateFormat.format(birthday);
        birthdayPickerButton.setText(birthdayLabel);
    }
}
