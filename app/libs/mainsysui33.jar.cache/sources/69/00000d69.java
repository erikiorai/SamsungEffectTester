package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import androidx.annotation.Keep;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.core.instrumentation.SettingsJankMonitor;

/* loaded from: mainsysui33.jar:com/android/settingslib/PrimarySwitchPreference.class */
public class PrimarySwitchPreference extends RestrictedPreference {
    public boolean mChecked;
    public boolean mCheckedSet;
    public boolean mEnableSwitch;
    public Switch mSwitch;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.PrimarySwitchPreference$$ExternalSyntheticLambda1.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    /* renamed from: $r8$lambda$aYN-0qoGuHaLgHlf-CATsBamDlY */
    public static /* synthetic */ boolean m934$r8$lambda$aYN0qoGuHaLgHlfCATsBamDlY(View view, MotionEvent motionEvent) {
        return lambda$onBindViewHolder$1(view, motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.PrimarySwitchPreference$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$ktK7wLFjV7KlikuDOlpGJ_i3dkY(PrimarySwitchPreference primarySwitchPreference, View view) {
        primarySwitchPreference.lambda$onBindViewHolder$0(view);
    }

    public PrimarySwitchPreference(Context context) {
        super(context);
        this.mEnableSwitch = true;
    }

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnableSwitch = true;
    }

    public /* synthetic */ void lambda$onBindViewHolder$0(View view) {
        Switch r0 = this.mSwitch;
        if (r0 == null || r0.isEnabled()) {
            boolean z = !this.mChecked;
            if (callChangeListener(Boolean.valueOf(z))) {
                SettingsJankMonitor.detectToggleJank(getKey(), this.mSwitch);
                setChecked(z);
                persistBoolean(z);
            }
        }
    }

    public static /* synthetic */ boolean lambda$onBindViewHolder$1(View view, MotionEvent motionEvent) {
        return motionEvent.getActionMasked() == 2;
    }

    @Keep
    public Boolean getCheckedState() {
        return this.mCheckedSet ? Boolean.valueOf(this.mChecked) : null;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public int getSecondTargetResId() {
        return R$layout.preference_widget_primary_switch;
    }

    public Switch getSwitch() {
        return this.mSwitch;
    }

    public boolean isChecked() {
        return this.mSwitch != null && this.mChecked;
    }

    @Override // com.android.settingslib.RestrictedPreference, com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Switch r0 = (Switch) preferenceViewHolder.findViewById(R$id.switchWidget);
        this.mSwitch = r0;
        if (r0 != null) {
            r0.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.PrimarySwitchPreference$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PrimarySwitchPreference.$r8$lambda$ktK7wLFjV7KlikuDOlpGJ_i3dkY(PrimarySwitchPreference.this, view);
                }
            });
            this.mSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.settingslib.PrimarySwitchPreference$$ExternalSyntheticLambda1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return PrimarySwitchPreference.m934$r8$lambda$aYN0qoGuHaLgHlfCATsBamDlY(view, motionEvent);
                }
            });
            this.mSwitch.setContentDescription(getTitle());
            this.mSwitch.setChecked(this.mChecked);
            this.mSwitch.setEnabled(this.mEnableSwitch);
        }
    }

    public void setChecked(boolean z) {
        if ((this.mChecked != z) || !this.mCheckedSet) {
            this.mChecked = z;
            this.mCheckedSet = true;
            Switch r0 = this.mSwitch;
            if (r0 != null) {
                r0.setChecked(z);
            }
        }
    }

    public void setSwitchEnabled(boolean z) {
        this.mEnableSwitch = z;
        Switch r0 = this.mSwitch;
        if (r0 != null) {
            r0.setEnabled(z);
        }
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public boolean shouldHideSecondTarget() {
        return getSecondTargetResId() == 0;
    }
}