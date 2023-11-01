package com.android.systemui.controls.controller;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl_Factory.class */
public final class ControlsBindingControllerImpl_Factory implements Factory<ControlsBindingControllerImpl> {
    public final Provider<DelayableExecutor> backgroundExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ControlsController> controllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsBindingControllerImpl_Factory(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<ControlsController> provider3, Provider<UserTracker> provider4) {
        this.contextProvider = provider;
        this.backgroundExecutorProvider = provider2;
        this.controllerProvider = provider3;
        this.userTrackerProvider = provider4;
    }

    public static ControlsBindingControllerImpl_Factory create(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<ControlsController> provider3, Provider<UserTracker> provider4) {
        return new ControlsBindingControllerImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static ControlsBindingControllerImpl newInstance(Context context, DelayableExecutor delayableExecutor, Lazy<ControlsController> lazy, UserTracker userTracker) {
        return new ControlsBindingControllerImpl(context, delayableExecutor, lazy, userTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsBindingControllerImpl m1803get() {
        return newInstance((Context) this.contextProvider.get(), (DelayableExecutor) this.backgroundExecutorProvider.get(), DoubleCheck.lazy(this.controllerProvider), (UserTracker) this.userTrackerProvider.get());
    }
}