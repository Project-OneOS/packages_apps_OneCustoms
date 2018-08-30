package com.teamone.OneCustoms.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.preference.*;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class About extends SettingsPreferenceFragment {

    public static final String TAG = "About";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config_center_about);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ONE_MODS;
    }
}
