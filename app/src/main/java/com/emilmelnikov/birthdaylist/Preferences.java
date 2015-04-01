package com.emilmelnikov.birthdaylist;

import android.content.Context;
import android.content.SharedPreferences;

public final class Preferences {
    private Preferences() {
        throw new AssertionError();
    }

    public static final String NAME_BIRTHDAY = "birthday";

    public static final String KEY_BIRTHDAY_YEAR = "birthday_year";
    public static final String KEY_BIRTHDAY_MONTH = "birthday_month";
    public static final String KEY_BIRTHDAY_DAY = "birthday_day";

    public static int getBirthdayYear(Context context) {
        return getBirthdayPreferences(context).getInt(KEY_BIRTHDAY_YEAR, -1);
    }

    public static int getBirthdayMonth(Context context) {
        return getBirthdayPreferences(context).getInt(KEY_BIRTHDAY_MONTH, -1);
    }

    public static int getBirthdayDay(Context context) {
        return getBirthdayPreferences(context).getInt(KEY_BIRTHDAY_DAY, -1);
    }

    private static SharedPreferences getBirthdayPreferences(Context context) {
        return context.getSharedPreferences(NAME_BIRTHDAY, Context.MODE_PRIVATE);
    }
}
