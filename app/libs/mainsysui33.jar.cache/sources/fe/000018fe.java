package com.android.systemui.keyguard;

import android.content.pm.PackageManager;
import android.os.UserManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/WorkLockActivity_Factory.class */
public final class WorkLockActivity_Factory implements Factory<WorkLockActivity> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<UserManager> userManagerProvider;

    public WorkLockActivity_Factory(Provider<BroadcastDispatcher> provider, Provider<UserManager> provider2, Provider<PackageManager> provider3) {
        this.broadcastDispatcherProvider = provider;
        this.userManagerProvider = provider2;
        this.packageManagerProvider = provider3;
    }

    public static WorkLockActivity_Factory create(Provider<BroadcastDispatcher> provider, Provider<UserManager> provider2, Provider<PackageManager> provider3) {
        return new WorkLockActivity_Factory(provider, provider2, provider3);
    }

    public static WorkLockActivity newInstance(BroadcastDispatcher broadcastDispatcher, UserManager userManager, PackageManager packageManager) {
        return new WorkLockActivity(broadcastDispatcher, userManager, packageManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public WorkLockActivity m2911get() {
        return newInstance((BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (UserManager) this.userManagerProvider.get(), (PackageManager) this.packageManagerProvider.get());
    }
}