package com.android.systemui.assist;

import android.content.Context;
import com.android.internal.app.AssistUtils;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistModule.class */
public abstract class AssistModule {
    public static AssistUtils provideAssistUtils(Context context) {
        return new AssistUtils(context);
    }
}