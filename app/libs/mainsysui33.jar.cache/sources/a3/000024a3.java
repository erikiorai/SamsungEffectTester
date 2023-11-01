package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.content.Context;
import android.os.UserManager;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicyImpl_Factory.class */
public final class ScreenshotPolicyImpl_Factory implements Factory<ScreenshotPolicyImpl> {
    public final Provider<IActivityTaskManager> atmServiceProvider;
    public final Provider<CoroutineDispatcher> bgDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<UserManager> userMgrProvider;

    public ScreenshotPolicyImpl_Factory(Provider<Context> provider, Provider<UserManager> provider2, Provider<IActivityTaskManager> provider3, Provider<CoroutineDispatcher> provider4) {
        this.contextProvider = provider;
        this.userMgrProvider = provider2;
        this.atmServiceProvider = provider3;
        this.bgDispatcherProvider = provider4;
    }

    public static ScreenshotPolicyImpl_Factory create(Provider<Context> provider, Provider<UserManager> provider2, Provider<IActivityTaskManager> provider3, Provider<CoroutineDispatcher> provider4) {
        return new ScreenshotPolicyImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static ScreenshotPolicyImpl newInstance(Context context, UserManager userManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        return new ScreenshotPolicyImpl(context, userManager, iActivityTaskManager, coroutineDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenshotPolicyImpl m4294get() {
        return newInstance((Context) this.contextProvider.get(), (UserManager) this.userMgrProvider.get(), (IActivityTaskManager) this.atmServiceProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get());
    }
}