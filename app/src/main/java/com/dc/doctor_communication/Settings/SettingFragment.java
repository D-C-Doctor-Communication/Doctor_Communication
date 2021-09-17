package com.dc.doctor_communication.Settings;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;

import com.dc.doctor_communication.R;
public class SettingFragment extends PreferenceFragment {

    //구현 x
    SharedPreferences sp;
    ListPreference textSize;
    SwitchPreference alert;
    SeekBarPreference alert_volume;
    //구현 o
    Preference notice;
    Preference feedback;

    SharedPreferences.OnSharedPreferenceChangeListener spListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.d("myapp","qqqqqq");

        }
    };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //getView().setBackgroundColor(Color.WHITE);

        addPreferencesFromResource(R.xml.setting_preferences);
        textSize = (ListPreference) findPreference("textSize");
        alert = (SwitchPreference) findPreference("alert");
        //alert_volume = (SeekBarPreference) findPreference("alert_volume");
        notice = (Preference) findPreference("notice");
        feedback  = (Preference) findPreference("feedback");
        alert_volume = (SeekBarPreference) findPreference("alert_volume");
        //비활성화
        notice.setEnabled(false);
        textSize.setEnabled(false);
        alert.setEnabled(false);
        alert_volume.setEnabled(false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if(!sp.getString("notice","").equals("")){
            notice.setSummary(sp.getString("notice",""));
        }
        if(!sp.getString("feedback","").equals("")){
            notice.setSummary(sp.getString("feedback",""));
        }
        sp.registerOnSharedPreferenceChangeListener(spListener);

    }
}
