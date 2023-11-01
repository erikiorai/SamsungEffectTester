package com.android.systemui.flags;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.provider.Settings;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagSettingsHelper.class */
public final class FlagSettingsHelper {
    public final ContentResolver contentResolver;

    public FlagSettingsHelper(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public final String getString(String str) {
        return Settings.Secure.getString(this.contentResolver, str);
    }

    public final void registerContentObserver(String str, boolean z, ContentObserver contentObserver) {
        this.contentResolver.registerContentObserver(Settings.Secure.getUriFor(str), z, contentObserver);
    }

    public final void unregisterContentObserver(ContentObserver contentObserver) {
        this.contentResolver.unregisterContentObserver(contentObserver);
    }
}