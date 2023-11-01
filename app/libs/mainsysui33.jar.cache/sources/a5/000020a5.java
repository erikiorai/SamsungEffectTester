package com.android.systemui.qs;

import android.app.IActivityManager;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl_Factory.class */
public final class FgsManagerControllerImpl_Factory implements Factory<FgsManagerControllerImpl> {
    public final Provider<IActivityManager> activityManagerProvider;
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<DialogLaunchAnimator> dialogLaunchAnimatorProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<JobScheduler> jobSchedulerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public FgsManagerControllerImpl_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<SystemClock> provider4, Provider<IActivityManager> provider5, Provider<JobScheduler> provider6, Provider<PackageManager> provider7, Provider<UserTracker> provider8, Provider<DeviceConfigProxy> provider9, Provider<DialogLaunchAnimator> provider10, Provider<BroadcastDispatcher> provider11, Provider<DumpManager> provider12) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.backgroundExecutorProvider = provider3;
        this.systemClockProvider = provider4;
        this.activityManagerProvider = provider5;
        this.jobSchedulerProvider = provider6;
        this.packageManagerProvider = provider7;
        this.userTrackerProvider = provider8;
        this.deviceConfigProxyProvider = provider9;
        this.dialogLaunchAnimatorProvider = provider10;
        this.broadcastDispatcherProvider = provider11;
        this.dumpManagerProvider = provider12;
    }

    public static FgsManagerControllerImpl_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<SystemClock> provider4, Provider<IActivityManager> provider5, Provider<JobScheduler> provider6, Provider<PackageManager> provider7, Provider<UserTracker> provider8, Provider<DeviceConfigProxy> provider9, Provider<DialogLaunchAnimator> provider10, Provider<BroadcastDispatcher> provider11, Provider<DumpManager> provider12) {
        return new FgsManagerControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static FgsManagerControllerImpl newInstance(Context context, Executor executor, Executor executor2, SystemClock systemClock, IActivityManager iActivityManager, JobScheduler jobScheduler, PackageManager packageManager, UserTracker userTracker, DeviceConfigProxy deviceConfigProxy, DialogLaunchAnimator dialogLaunchAnimator, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager) {
        return new FgsManagerControllerImpl(context, executor, executor2, systemClock, iActivityManager, jobScheduler, packageManager, userTracker, deviceConfigProxy, dialogLaunchAnimator, broadcastDispatcher, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FgsManagerControllerImpl m3718get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (SystemClock) this.systemClockProvider.get(), (IActivityManager) this.activityManagerProvider.get(), (JobScheduler) this.jobSchedulerProvider.get(), (PackageManager) this.packageManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get(), (DialogLaunchAnimator) this.dialogLaunchAnimatorProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}