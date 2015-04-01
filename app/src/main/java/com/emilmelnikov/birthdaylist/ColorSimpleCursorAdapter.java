package com.emilmelnikov.birthdaylist;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class ColorSimpleCursorAdapter extends SimpleCursorAdapter {
    private String colorColumnName;

    public ColorSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags, String colorColumnName) {
        super(context, layout, c, from, to, flags);
        this.colorColumnName = colorColumnName;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        int color = cursor.getInt(cursor.getColumnIndexOrThrow(colorColumnName));
        view.setBackgroundColor(color);
    }
}
