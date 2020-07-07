package com.teamone.OneCustoms.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.preference.Preference;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.teamone.OneCustoms.preferences.SystemSettingListPreference;
import com.teamone.OneCustoms.preferences.CustomSeekBarPreference;

public class NetworkTraffic extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener, CompoundButton.OnCheckedChangeListener {

    private SystemSettingListPreference mLocation;
    private CustomSeekBarPreference mThreshold;

    private TextView mTextView;
    private View mSwitchBar;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        addPreferencesFromResource(R.xml.network_traffic);

        mLocation = (SystemSettingListPreference) findPreference("network_traffic_location");
        mThreshold = (CustomSeekBarPreference) findPreference("network_traffic_autohide_threshold");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.master_setting_switch, container, false);
        ((ViewGroup) view).addView(super.onCreateView(inflater, container, savedInstanceState));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean enabled = Settings.System.getInt(getContentResolver(),
                Settings.System.NETWORK_TRAFFIC_STATE, 0) == 1;

        mTextView = view.findViewById(R.id.switch_text);
        mTextView.setText(getString(enabled ?
                R.string.switch_on_text : R.string.switch_off_text));

        mSwitchBar = view.findViewById(R.id.switch_bar);
        Switch switchWidget = mSwitchBar.findViewById(android.R.id.switch_widget);
        switchWidget.setChecked(enabled);
        switchWidget.setOnCheckedChangeListener(this);
        mSwitchBar.setActivated(enabled);
        mSwitchBar.setOnClickListener(v -> {
            switchWidget.setChecked(!switchWidget.isChecked());
            mSwitchBar.setActivated(switchWidget.isChecked());
        });

        mLocation.setEnabled(enabled);
        mThreshold.setEnabled(enabled);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        Settings.System.putInt(getContentResolver(),
                Settings.System.NETWORK_TRAFFIC_STATE, isChecked ? 1 : 0);
        mTextView.setText(getString(isChecked ? R.string.switch_on_text : R.string.switch_off_text));
        mSwitchBar.setActivated(isChecked);

        mLocation.setEnabled(isChecked);
        mThreshold.setEnabled(isChecked);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ONE_MODS;
    }
}
