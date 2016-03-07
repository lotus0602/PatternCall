package com.n.patterncall.app;

import android.os.Bundle;

import com.n.patterncall.util.PatternLockUtils;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

public class SetPatternActivity extends me.zhanghai.android.patternlock.SetPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        PatternLockUtils.setPattern(pattern, this);
    }
}
