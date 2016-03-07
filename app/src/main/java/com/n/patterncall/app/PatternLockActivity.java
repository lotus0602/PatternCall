package com.n.patterncall.app;

import android.content.Intent;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.n.patterncall.R;
import com.n.patterncall.util.PatternLockUtils;

public class PatternLockActivity extends AppCompatActivity {
    private static final String KEY_CONFIRM_STARTED = "confirm_started";
    private boolean isConfirmStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock);

        if (savedInstanceState != null) {
            isConfirmStarted = savedInstanceState.getBoolean(KEY_CONFIRM_STARTED);
        }

        if (!isConfirmStarted) {
            PatternLockUtils.confirmPatternIfHas(this);
            isConfirmStarted = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_CONFIRM_STARTED, isConfirmStarted);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (PatternLockUtils.checkConfirmPatternResult(this, requestCode, resultCode)) {
            isConfirmStarted = false;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static class PatternLockFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences_pattern_lock);
        }
    }
}
