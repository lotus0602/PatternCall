package com.n.patterncall.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.n.patterncall.app.ConfirmPatternActivity;
import com.n.patterncall.app.SetPatternActivity;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by N on 2016-03-07.
 */
public class PatternLockUtils {
    public static final int REQUEST_CODE_CONFIRM_PATTERN = 1;

    public static final String KEY_PATTERN_DEFAULT = null;
    public static final String KEY_PATTERN_SHA1 = "pref_key_pattern";

    public static final String KEY_PATTERN_VISIBLE = "pref_key pattern_visible";
    public static final boolean KEY_PATTERN_VISIBLE_DEFAULT = true;

    private PatternLockUtils() { }

    private static String getPatternSha1(Context context) {
        return PreferenceUtils.getString(KEY_PATTERN_SHA1, KEY_PATTERN_DEFAULT, context);
    }

    public static void setPattern(List<PatternView.Cell> pattern, Context context) {
        PreferenceUtils.putString(KEY_PATTERN_SHA1,
                PatternUtils.patternToSha1String(pattern), context);
    }

    public static boolean isPatternCorrect(List<PatternView.Cell> pattern, Context context) {
        return TextUtils.equals(PatternUtils.patternToSha1String(pattern), getPatternSha1(context));
    }

    public static boolean hasPattern(Context context) {
        return !TextUtils.isEmpty(getPatternSha1(context));
    }

    public static void clearPattern(Context context) {
        PreferenceUtils.remove(KEY_PATTERN_SHA1, context);
    }

    public static void setPatternByUser(Context context) {
        context.startActivity(new Intent(context, SetPatternActivity.class));
    }

    public static void confirmPattern(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, ConfirmPatternActivity.class), requestCode);
    }

    public static void confirmPatternIfHas(Activity activity) {
        if (hasPattern(activity)) {
            confirmPattern(activity, REQUEST_CODE_CONFIRM_PATTERN);
        }
    }

    public static boolean checkConfirmPatternResult(Activity activity, int requestCode, int resultCode) {
        if (requestCode == REQUEST_CODE_CONFIRM_PATTERN && resultCode != Activity.RESULT_OK) {
            activity.finish();
            return true;    // Pattern is incorrect
        } else {
            return false;   // Pattern is correct
        }
    }
}
