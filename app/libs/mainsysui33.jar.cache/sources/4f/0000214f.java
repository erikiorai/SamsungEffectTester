package com.android.systemui.qs;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.util.settings.SettingsProxy;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/SettingObserver.class */
public abstract class SettingObserver extends ContentObserver {
    public final int mDefaultValue;
    public boolean mListening;
    public int mObservedValue;
    public final String mSettingName;
    public final SettingsProxy mSettingsProxy;
    public int mUserId;

    public SettingObserver(SettingsProxy settingsProxy, Handler handler, String str, int i) {
        this(settingsProxy, handler, str, i, 0);
    }

    public SettingObserver(SettingsProxy settingsProxy, Handler handler, String str, int i, int i2) {
        super(handler);
        this.mSettingsProxy = settingsProxy;
        this.mSettingName = str;
        this.mDefaultValue = i2;
        this.mObservedValue = i2;
        this.mUserId = i;
    }

    public String getKey() {
        return this.mSettingName;
    }

    public int getValue() {
        return this.mListening ? this.mObservedValue : getValueFromProvider();
    }

    public final int getValueFromProvider() {
        return this.mSettingsProxy.getIntForUser(this.mSettingName, this.mDefaultValue, this.mUserId);
    }

    public abstract void handleValueChanged(int i, boolean z);

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        int valueFromProvider = getValueFromProvider();
        boolean z2 = valueFromProvider != this.mObservedValue;
        this.mObservedValue = valueFromProvider;
        handleValueChanged(valueFromProvider, z2);
    }

    public void setListening(boolean z) {
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        if (!z) {
            this.mSettingsProxy.unregisterContentObserver(this);
            this.mObservedValue = this.mDefaultValue;
            return;
        }
        this.mObservedValue = getValueFromProvider();
        SettingsProxy settingsProxy = this.mSettingsProxy;
        settingsProxy.registerContentObserverForUser(settingsProxy.getUriFor(this.mSettingName), false, this, this.mUserId);
    }

    public void setUserId(int i) {
        this.mUserId = i;
        if (this.mListening) {
            setListening(false);
            setListening(true);
        }
    }

    public void setValue(int i) {
        this.mSettingsProxy.putIntForUser(this.mSettingName, i, this.mUserId);
    }
}