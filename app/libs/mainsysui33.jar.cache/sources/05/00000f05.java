package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* loaded from: mainsysui33.jar:com/android/settingslib/widget/SettingsSpinnerPreference.class */
public class SettingsSpinnerPreference extends Preference implements Preference.OnPreferenceClickListener {
    public AdapterView.OnItemSelectedListener mListener;
    public final AdapterView.OnItemSelectedListener mOnSelectedListener;
    public int mPosition;
    public boolean mShouldPerformClick;

    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: com.android.settingslib.widget.SettingsSpinnerPreference.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (SettingsSpinnerPreference.this.mPosition == i) {
                    return;
                }
                SettingsSpinnerPreference.this.mPosition = i;
                if (SettingsSpinnerPreference.this.mListener != null) {
                    SettingsSpinnerPreference.this.mListener.onItemSelected(adapterView, view, i, j);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (SettingsSpinnerPreference.this.mListener != null) {
                    SettingsSpinnerPreference.this.mListener.onNothingSelected(adapterView);
                }
            }
        };
        setLayoutResource(R$layout.settings_spinner_preference);
        setOnPreferenceClickListener(this);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Spinner spinner = (Spinner) preferenceViewHolder.findViewById(R$id.spinner);
        spinner.setAdapter((SpinnerAdapter) null);
        spinner.setSelection(this.mPosition);
        spinner.setOnItemSelectedListener(this.mOnSelectedListener);
        if (this.mShouldPerformClick) {
            this.mShouldPerformClick = false;
            spinner.performClick();
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        this.mShouldPerformClick = true;
        notifyChanged();
        return true;
    }
}