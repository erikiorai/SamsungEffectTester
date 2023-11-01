package com.android.systemui.dagger;

import android.content.Context;
import android.util.DisplayMetrics;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/GlobalModule.class */
public class GlobalModule {
    public Context provideApplicationContext(Context context) {
        return context.getApplicationContext();
    }

    public DisplayMetrics provideDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}