package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import kotlin.coroutines.Continuation;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/AppIconLoader.class */
public interface AppIconLoader {
    Object loadIcon(int i, ComponentName componentName, Continuation<? super Drawable> continuation);
}