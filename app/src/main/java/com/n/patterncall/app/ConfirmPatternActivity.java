package com.n.patterncall.app;

import android.os.Bundle;
import android.widget.Toast;

import com.n.patterncall.R;
import com.n.patterncall.util.PatternLockUtils;
import com.n.patterncall.util.PreferenceUtils;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

public class ConfirmPatternActivity extends me.zhanghai.android.patternlock.ConfirmPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isStealthModeEnabled() {
        return !PreferenceUtils.getBoolean(PatternLockUtils.KEY_PATTERN_VISIBLE,
                PatternLockUtils.KEY_PATTERN_VISIBLE_DEFAULT, this);
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        return PatternLockUtils.isPatternCorrect(pattern, this);
    }

    @Override
    protected void onForgotPassword() {
        PatternLockUtils.clearPattern(this);
        Toast.makeText(this, R.string.pattern_reset, Toast.LENGTH_SHORT).show();

        super.onForgotPassword();
    }
}
