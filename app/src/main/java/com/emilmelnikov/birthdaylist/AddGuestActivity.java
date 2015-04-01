package com.emilmelnikov.birthdaylist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.emilmelnikov.birthdaylist.GuestDbHelper.GuestEntry;

public class AddGuestActivity extends ActionBarActivity {
    private Button addGuestButton;
    private String nameText;
    private String colorText;

    private TextView nameView;
    private TextView giftsView;
    private TextView colorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);

        addGuestButton = (Button) findViewById(R.id.add_guest);
        nameView = (TextView) findViewById(R.id.name);
        giftsView = (TextView) findViewById(R.id.gifts);
        colorView = (TextView) findViewById(R.id.color);

        nameView.addTextChangedListener(new NameTextWatcher());
        colorView.addTextChangedListener(new ColorTextWatcher());

        nameText = nameView.getText().toString();
        colorText = colorView.getText().toString();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void addGuest(View view) {
        ContentValues values = new ContentValues();
        values.put(GuestEntry.COLUMN_NAME_NAME, nameView.getText().toString());
        values.put(GuestEntry.COLUMN_NAME_GIFTS, giftsView.getText().toString());
        values.put(GuestEntry.COLUMN_NAME_COLOR, Color.parseColor(colorView.getText().toString()));

        GuestDbHelper dbHelper = new GuestDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(GuestEntry.TABLE_NAME, null, values);

        NavUtils.navigateUpFromSameTask(this);
    }

    private void validateInputData() {
        if (nameText.isEmpty()) {
            addGuestButton.setEnabled(false);
            return;
        }

        if ((colorText.length() != 7)) {
            addGuestButton.setEnabled(false);
            return;
        }

        try {
            Color.parseColor(colorText);
        } catch (IllegalArgumentException e) {
            addGuestButton.setEnabled(false);
            return;
        }

        addGuestButton.setEnabled(true);
    }

    private final class NameTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            nameText = s.toString();
            validateInputData();
        }
    }

    private final class ColorTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            colorText = s.toString();
            validateInputData();
        }
    }
}
