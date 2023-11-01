package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Switch;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/widget/MainSwitchPreference.class */
public class MainSwitchPreference extends TwoStatePreference implements OnMainSwitchChangeListener {
    public MainSwitchBar mMainSwitchBar;
    public final List<OnMainSwitchChangeListener> mSwitchChangeListeners;
    public CharSequence mTitle;

    public MainSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSwitchChangeListeners = new ArrayList();
        init(context, attributeSet);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        setLayoutResource(R$layout.settingslib_main_switch_layout);
        this.mSwitchChangeListeners.add(this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.preference.R$styleable.Preference, 0, 0);
            setTitle(obtainStyledAttributes.getText(androidx.preference.R$styleable.Preference_android_title));
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.setDividerAllowedAbove(false);
        preferenceViewHolder.setDividerAllowedBelow(false);
        this.mMainSwitchBar = (MainSwitchBar) preferenceViewHolder.findViewById(R$id.settingslib_main_switch_bar);
        updateStatus(isChecked());
        registerListenerToSwitchBar();
    }

    @Override // com.android.settingslib.widget.OnMainSwitchChangeListener
    public void onSwitchChanged(Switch r4, boolean z) {
        super.setChecked(z);
    }

    public final void registerListenerToSwitchBar() {
        for (OnMainSwitchChangeListener onMainSwitchChangeListener : this.mSwitchChangeListeners) {
            this.mMainSwitchBar.addOnSwitchChangeListener(onMainSwitchChangeListener);
        }
    }

    @Override // androidx.preference.TwoStatePreference
    public void setChecked(boolean z) {
        super.setChecked(z);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar == null || mainSwitchBar.isChecked() == z) {
            return;
        }
        this.mMainSwitchBar.setChecked(z);
    }

    @Override // androidx.preference.Preference
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar != null) {
            mainSwitchBar.setTitle(charSequence);
        }
    }

    public void updateStatus(boolean z) {
        setChecked(z);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar != null) {
            mainSwitchBar.setTitle(this.mTitle);
            this.mMainSwitchBar.show();
        }
    }
}