package com.emilmelnikov.birthdaylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class GuestDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "guests.db";

    private static final String SQL_CREATE =
            "CREATE TABLE " + GuestEntry.TABLE_NAME + " (" +
            GuestEntry._ID + " INTEGER PRIMARY KEY" + ", " +
            GuestEntry.COLUMN_NAME_NAME + " TEXT NOT NULL" + ", " +
            GuestEntry.COLUMN_NAME_GIFTS + " TEXT" + ", " +
            GuestEntry.COLUMN_NAME_COLOR + " INTEGER NOT NULL" +
            ");";

    private static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + GuestEntry.TABLE_NAME + ";";

    public GuestDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    public static abstract class GuestEntry implements BaseColumns {
        public static final String TABLE_NAME = "guests";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_GIFTS = "gifts";
        public static final String COLUMN_NAME_COLOR = "color";
    }
}
