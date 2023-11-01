package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconFactory;
import com.android.systemui.shared.system.PackageManagerWrapper;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.mediaprojection.appselector.data.IconLoaderLibAppIconLoader$loadIcon$2", f = "AppIconLoader.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/IconLoaderLibAppIconLoader$loadIcon$2.class */
public final class IconLoaderLibAppIconLoader$loadIcon$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Drawable>, Object> {
    public final /* synthetic */ ComponentName $component;
    public final /* synthetic */ int $userId;
    public int label;
    public final /* synthetic */ IconLoaderLibAppIconLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IconLoaderLibAppIconLoader$loadIcon$2(IconLoaderLibAppIconLoader iconLoaderLibAppIconLoader, ComponentName componentName, int i, Continuation<? super IconLoaderLibAppIconLoader$loadIcon$2> continuation) {
        super(2, continuation);
        this.this$0 = iconLoaderLibAppIconLoader;
        this.$component = componentName;
        this.$userId = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new IconLoaderLibAppIconLoader$loadIcon$2(this.this$0, this.$component, this.$userId, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Drawable> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public final Object invokeSuspend(Object obj) {
        Provider provider;
        PackageManagerWrapper packageManagerWrapper;
        PackageManager packageManager;
        Context context;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        provider = this.this$0.iconFactoryProvider;
        AutoCloseable autoCloseable = (AutoCloseable) provider.get();
        IconLoaderLibAppIconLoader iconLoaderLibAppIconLoader = this.this$0;
        ComponentName componentName = this.$component;
        int i = this.$userId;
        try {
            IconFactory iconFactory = (IconFactory) autoCloseable;
            packageManagerWrapper = iconLoaderLibAppIconLoader.packageManagerWrapper;
            ActivityInfo activityInfo = packageManagerWrapper.getActivityInfo(componentName, i);
            if (activityInfo == null) {
                AutoCloseableKt.closeFinally(autoCloseable, (Throwable) null);
                return null;
            }
            packageManager = iconLoaderLibAppIconLoader.packageManager;
            Drawable loadIcon = activityInfo.loadIcon(packageManager);
            if (loadIcon == null) {
                AutoCloseableKt.closeFinally(autoCloseable, (Throwable) null);
                return null;
            }
            UserHandle of = UserHandle.of(i);
            BaseIconFactory.IconOptions iconOptions = new BaseIconFactory.IconOptions();
            iconOptions.setUser(of);
            BitmapInfo createBadgedIconBitmap = iconFactory.createBadgedIconBitmap(loadIcon, iconOptions);
            context = iconLoaderLibAppIconLoader.context;
            FastBitmapDrawable newIcon = createBadgedIconBitmap.newIcon(context);
            AutoCloseableKt.closeFinally(autoCloseable, (Throwable) null);
            return newIcon;
        } finally {
        }
    }
}