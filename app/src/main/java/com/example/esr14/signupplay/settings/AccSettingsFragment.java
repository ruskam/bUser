package com.example.esr14.signupplay.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.esr14.signupplay.R;

/**
 * Created by Bia on 04/04/2016.
 */
public class AccSettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.acc_prefs);
    }
}