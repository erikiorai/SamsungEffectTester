package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* loaded from: mainsysui33.jar:com/android/settingslib/widget/AppPreference.class */
public class AppPreference extends Preference {
    public int mProgress;
    public boolean mProgressVisible;

    public AppPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R$layout.preference_app);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ProgressBar progressBar = (ProgressBar) preferenceViewHolder.findViewById(16908301);
        if (!this.mProgressVisible) {
            progressBar.setVisibility(8);
            return;
        }
        progressBar.setProgress(this.mProgress);
        progressBar.setVisibility(0);
    }
}