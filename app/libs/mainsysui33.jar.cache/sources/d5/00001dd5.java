package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.android.launcher3.icons.IconFactory;
import com.android.systemui.shared.system.PackageManagerWrapper;
import javax.inject.Provider;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/IconLoaderLibAppIconLoader.class */
public final class IconLoaderLibAppIconLoader implements AppIconLoader {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final Provider<IconFactory> iconFactoryProvider;
    public final PackageManager packageManager;
    public final PackageManagerWrapper packageManagerWrapper;

    public IconLoaderLibAppIconLoader(CoroutineDispatcher coroutineDispatcher, Context context, PackageManagerWrapper packageManagerWrapper, PackageManager packageManager, Provider<IconFactory> provider) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.context = context;
        this.packageManagerWrapper = packageManagerWrapper;
        this.packageManager = packageManager;
        this.iconFactoryProvider = provider;
    }

    @Override // com.android.systemui.mediaprojection.appselector.data.AppIconLoader
    public Object loadIcon(int i, ComponentName componentName, Continuation<? super Drawable> continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new IconLoaderLibAppIconLoader$loadIcon$2(this, componentName, i, null), continuation);
    }
}