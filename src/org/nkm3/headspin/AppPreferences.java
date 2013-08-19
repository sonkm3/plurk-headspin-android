package org.nkm3.headspin;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.app.Activity;

public class AppPreferences {

    static String getString(Activity activity, String key){
        String app_name = activity.getResources().getString(R.string.app_name);
        SharedPreferences preferences = activity.getSharedPreferences(app_name, activity.MODE_PRIVATE);

        return preferences.getString(key, null);
    }

    static void putString(Activity activity, String key, String value){
        String app_name = activity.getResources().getString(R.string.app_name);
        SharedPreferences preferences = activity.getSharedPreferences(app_name, activity.MODE_PRIVATE);

        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    static void remove(Activity activity, String key){
        String app_name = activity.getResources().getString(R.string.app_name);
        SharedPreferences preferences = activity.getSharedPreferences(app_name, activity.MODE_PRIVATE);

        Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
