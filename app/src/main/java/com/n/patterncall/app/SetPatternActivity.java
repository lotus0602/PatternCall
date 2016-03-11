package com.n.patterncall.app;

import android.content.Intent;
import android.os.Bundle;

import com.n.patterncall.util.PatternCallUtils;
import com.n.patterncall.util.PatternLockUtils;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

public class SetPatternActivity extends me.zhanghai.android.patternlock.SetPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        Intent getIntent = getIntent();

        if (getIntent.hasExtra(PatternCallActivity.INTENT_KEY_PHONE_DATA_URI)) {
            String value = getIntent().getStringExtra(PatternCallActivity.INTENT_KEY_PHONE_DATA_URI);
            PatternCallUtils.setPhoneNumberUriToString(pattern, value, this);

            getIntent.putExtra(PatternCallActivity.INTENT_KEY_PATTERN,
                    PatternUtils.patternToSha1String(pattern));
            setResult(RESULT_OK, getIntent);
            finish();
        } else {
            PatternLockUtils.setPattern(pattern, this);
        }
    }
}
