package com.n.patterncall.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Set;

/**
 * Created by N on 2016-03-07.
 */
public class PreferenceUtils {
    private PreferenceUtils() { }

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void remove(String key, Context context) {
        getEditor(context).remove(key).commit();
    }

    /* Get Value Function */

    public static boolean getBoolean(String key, boolean defaultValue, Context context) {
        return getPreferences(context).getBoolean(key, defaultValue);
    }

    public static String getString(String key, String defaultValue, Context context) {
        return getPreferences(context).getString(key, defaultValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValue, Context context) {
        return getPreferences(context).getStringSet(key, defaultValue);
    }

    /* Set Value Function */

    public static void putString(String key, String value, Context context) {
        getEditor(context).putString(key, value).commit();
    }

    public static void putStringSet(String key, Set<String> value, Context context) {
        getEditor(context).putStringSet(key, value).commit();
    }
}
