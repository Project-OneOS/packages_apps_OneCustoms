package com.teamone.OneCustoms.preferences;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.R;

public class SwitchPreference extends androidx.preference.SwitchPreference {

    private final Context mContext;
    private final Vibrator mVibrator;

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mContext = context;
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SwitchPreference(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context,
                androidx.preference.R.attr.switchPreferenceStyle,
                android.R.attr.switchPreferenceStyle));
    }

    public SwitchPreference(Context context) {
        this(context, null);
    }

    @Override
    protected void performClick(View view) {
        super.performClick(view);

        doHapticFeedback();
    }

    private void doHapticFeedback() {
        final boolean hapticEnabled = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.HAPTIC_FEEDBACK_ENABLED, 1) != 0;

        if (hapticEnabled) {
            mVibrator.vibrate(VibrationEffect.get(VibrationEffect.EFFECT_CLICK));
        }
    }
}
