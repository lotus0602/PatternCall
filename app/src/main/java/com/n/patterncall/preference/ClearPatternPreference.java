package com.n.patterncall.preference;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.widget.Toast;

import com.n.patterncall.R;
import com.n.patterncall.util.PatternLockUtils;

/**
 * Created by N on 2016-03-07.
 */
public class ClearPatternPreference extends DialogPreference {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClearPatternPreference(Context context) {
        super(context);
    }

    public ClearPatternPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearPatternPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClearPatternPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            Context context = getContext();
            PatternLockUtils.clearPattern(context);
            Toast.makeText(getContext(), R.string.pattern_cleared, Toast.LENGTH_SHORT).show();
        }
    }
}
