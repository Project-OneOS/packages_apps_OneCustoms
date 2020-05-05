package com.teamone.OneCustoms.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.preference.*;

import com.android.internal.one.app.OneContextConstants;
import com.android.internal.logging.nano.MetricsProto;

import com.teamone.OneCustoms.preferences.SystemSettingSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class LockScreen extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String KEY_SCREEN_OFF_FOD = "screen_off_fod";
    private static final String KEY_SCREEN_OFF_FOD_ICON = "screen_off_fod_icon";

    private SwitchPreference mScreenOffFOD;
    private SystemSettingSwitchPreference mScreenOffFODIcon;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.one_settings_lockscreen);
        final PreferenceScreen prefScreen = getPreferenceScreen();
        Resources resources = getResources();
        PackageManager packageManager = getPackageManager();

    boolean mScreenOffFODValue = Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.SCREEN_OFF_FOD, 0) != 0;

        mScreenOffFOD = (SwitchPreference) findPreference(KEY_SCREEN_OFF_FOD);
        mScreenOffFOD.setChecked(mScreenOffFODValue);
        mScreenOffFOD.setOnPreferenceChangeListener(this);
        mScreenOffFODIcon = (SystemSettingSwitchPreference) findPreference(KEY_SCREEN_OFF_FOD_ICON);

        if (!packageManager.hasSystemFeature(OneContextConstants.Features.FOD)) {
            mScreenOffFOD.setVisible(false);
            mScreenOffFODIcon.setVisible(false);
        }

    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
    	if (preference == mScreenOffFOD) {
            int mScreenOffFODValue = (Boolean) newValue ? 1 : 0;
            Settings.System.putInt(resolver, Settings.System.SCREEN_OFF_FOD, mScreenOffFODValue);
            Settings.Secure.putInt(resolver, Settings.Secure.DOZE_ALWAYS_ON, mScreenOffFODValue);
            return true;
	}
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ONE_MODS;
    }
}
