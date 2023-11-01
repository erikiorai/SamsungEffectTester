package com.android.systemui;

import android.content.Context;
import android.widget.Toast;

/* loaded from: mainsysui33.jar:com/android/systemui/SysUIToast.class */
public class SysUIToast {
    public static Toast makeText(Context context, int i, int i2) {
        return makeText(context, context.getString(i), i2);
    }

    public static Toast makeText(Context context, CharSequence charSequence, int i) {
        return Toast.makeText(context, charSequence, i);
    }
}