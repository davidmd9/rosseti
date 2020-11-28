package com.rosseti;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {

    private static final String PREF_AUTH_TOKEN = "PREF_AUTH_TOKEN";

    private SharedPreferences prefs;

    public Storage(Context context) {
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public void setToken(String token) {
        prefs.edit().putString(PREF_AUTH_TOKEN, "Bearer " + token).apply();
    }

    public String getToken() {
        return prefs.getString(PREF_AUTH_TOKEN, null);
    }

    public void clear(){
        prefs.edit().clear().apply();
    }
}

