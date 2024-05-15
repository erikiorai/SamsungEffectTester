package com.android.keyguard.util;

import android.net.Uri;

public class SettingsHelper {
    public interface OnChangedCallback {
        void onChanged(Uri uri);
    }
}
