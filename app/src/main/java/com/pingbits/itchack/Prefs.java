package com.pingbits.itchack;


import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static final String prefsKey = "com.pingbits.dexter";
    private Context context;

    public Prefs(Context context) {
        this.context = context;
    }

    public void saveStartValue(boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE);
        prefs.edit().putBoolean("start", value).apply();
    }

    public boolean getStartValue() {
        SharedPreferences prefs = context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE);
        return prefs.getBoolean("start", false);
    }
}
