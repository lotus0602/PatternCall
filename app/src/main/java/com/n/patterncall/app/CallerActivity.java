package com.n.patterncall.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.n.patterncall.R;
import com.n.patterncall.util.PatternCallUtils;
import com.n.patterncall.util.PatternLockUtils;
import com.n.patterncall.util.PreferenceUtils;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

public class CallerActivity extends me.zhanghai.android.patternlock.ConfirmPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessageText.setText(R.string.pattern_draw_to_call);
        mRightButton.setEnabled(false);
    }

    @Override
    protected boolean isStealthModeEnabled() {
        return !PreferenceUtils.getBoolean(PatternLockUtils.KEY_PATTERN_VISIBLE,
                PatternLockUtils.KEY_PATTERN_VISIBLE_DEFAULT, this);
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        String key = PatternUtils.patternToSha1String(pattern);
        if (PatternCallUtils.hasKeyPatternFromSet(key, this)) {
            Uri uri = Uri.parse(PatternCallUtils.getPhoneNumberUriToString(key, this));
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            // As of API 23, have to check permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }
            return true;
        } else {
            return false;
        }
    }
}
