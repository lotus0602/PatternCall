package com.n.patterncall.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.n.patterncall.model.PatternDirectory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by N on 2016-03-08.
 */
public class PatternCallUtils {
    public static final String KEY_RELATED_VALUE_DEFAULT = null;

    public static final Set<String> KEY_PATTERN_SET_DEFAULT = null;
    public static final String KEY_PATTERN_SET = "pref_key_pattern_set";

    private PatternCallUtils() {}

    private static Set<String> getKeyPatternSet(Context context) {
        return PreferenceUtils.getStringSet(KEY_PATTERN_SET,
                KEY_PATTERN_SET_DEFAULT, context);
    }

    private static void setKeyPatternSet(Set<String> value, Context context) {
        PreferenceUtils.putStringSet(KEY_PATTERN_SET, value, context);
    }

    private static void addKeyPatternSet(String patternValue, Context context) {
        Set<String> set;
        if (hasKeyPatternSet(context)) {
            set = new HashSet<>(getKeyPatternSet(context));
        } else {
            set = new HashSet<>();
        }
        set.add(patternValue);
        setKeyPatternSet(set, context);
    }

    private static void removeKeyPatternFromSet(String key, Context context) {
        if (hasKeyPatternSet(context)) {
            Set<String> set = new HashSet<>(getKeyPatternSet(context));
            set.remove(key);

            setKeyPatternSet(set, context);
        }
    }

    public static String getPhoneNumberUriToString(String key, Context context) {
        return PreferenceUtils.getString(key, KEY_RELATED_VALUE_DEFAULT, context);
    }

    public static void setPhoneNumberUriToString(List<PatternView.Cell> keyPattern, String value, Context context) {
        String patternKeyAndValue = PatternUtils.patternToSha1String(keyPattern);
        PreferenceUtils.putString(patternKeyAndValue, value, context);

        addKeyPatternSet(patternKeyAndValue, context);
    }

    public static boolean hasKeyPatternSet(Context context) {
        return getKeyPatternSet(context) != null;
    }

    public static boolean hasKeyPatternFromSet(String key, Context context) {
        return getKeyPatternSet(context).contains(key);
    }

    public static void clearPattern(String key, Context context) {
        PreferenceUtils.remove(key, context);
        removeKeyPatternFromSet(key, context);
    }

    public static ArrayList<PatternDirectory> createPatternDirectories(Context context) {
        ArrayList<PatternDirectory> patternDirectories = new ArrayList<>();
        Set patternSet = getKeyPatternSet(context);

        if (!patternSet.isEmpty()) {
            Iterator iterator = patternSet.iterator();

            while (iterator.hasNext()) {
                String keyPattern = (String) iterator.next();
                String value = getPhoneNumberUriToString(keyPattern, context);

                patternDirectories.add(new PatternDirectory(keyPattern,
                        getDataFromUri(Uri.parse(value), context)));
            }
        }
        return patternDirectories;
    }

    public static String getDataFromUri(Uri uri, Context context) {
        String data = null;

        String[] proj = new String[] {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            data = cursor.getString(1) + " " + cursor.getString(2);
            cursor.close();
        }
        return data;
    }
}
