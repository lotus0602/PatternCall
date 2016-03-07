package com.n.patterncall.preference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.n.patterncall.util.PatternLockUtils;
import com.n.patterncall.util.PreferenceUtils;

/**
 * Created by N on 2016-03-07.
 */
public class SetPatternPreference extends Preference
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    public SetPatternPreference(Context context) {
        super(context);
    }

    public SetPatternPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SetPatternPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SetPatternPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);

        PreferenceUtils.getPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPrepareForRemoval() {
        super.onPrepareForRemoval();

        PreferenceUtils.getPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onClick() {
        PatternLockUtils.setPatternByUser(getContext());
    }

    @Override
    public boolean shouldDisableDependents() {
        return super.shouldDisableDependents() || !PatternLockUtils.hasPattern(getContext());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, PatternLockUtils.KEY_PATTERN_SHA1)) {
            notifyChanged();
            notifyDependencyChange(shouldDisableDependents());
        }
    }
}
