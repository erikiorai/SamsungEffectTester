package com.android.keyguard;

import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardListenModelKt.class */
public final class KeyguardListenModelKt {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);

    public static final SimpleDateFormat getDATE_FORMAT() {
        return DATE_FORMAT;
    }
}