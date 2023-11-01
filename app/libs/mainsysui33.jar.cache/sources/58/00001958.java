package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLocalUserSelectionManager_Factory.class */
public final class KeyguardQuickAffordanceLocalUserSelectionManager_Factory implements Factory<KeyguardQuickAffordanceLocalUserSelectionManager> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<UserFileManager> userFileManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public KeyguardQuickAffordanceLocalUserSelectionManager_Factory(Provider<Context> provider, Provider<UserFileManager> provider2, Provider<UserTracker> provider3, Provider<BroadcastDispatcher> provider4) {
        this.contextProvider = provider;
        this.userFileManagerProvider = provider2;
        this.userTrackerProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
    }

    public static KeyguardQuickAffordanceLocalUserSelectionManager_Factory create(Provider<Context> provider, Provider<UserFileManager> provider2, Provider<UserTracker> provider3, Provider<BroadcastDispatcher> provider4) {
        return new KeyguardQuickAffordanceLocalUserSelectionManager_Factory(provider, provider2, provider3, provider4);
    }

    public static KeyguardQuickAffordanceLocalUserSelectionManager newInstance(Context context, UserFileManager userFileManager, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher) {
        return new KeyguardQuickAffordanceLocalUserSelectionManager(context, userFileManager, userTracker, broadcastDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardQuickAffordanceLocalUserSelectionManager m2950get() {
        return newInstance((Context) this.contextProvider.get(), (UserFileManager) this.userFileManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}