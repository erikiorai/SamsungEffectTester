package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.Set;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository_Factory.class */
public final class KeyguardQuickAffordanceRepository_Factory implements Factory<KeyguardQuickAffordanceRepository> {
    public final Provider<Context> appContextProvider;
    public final Provider<Set<KeyguardQuickAffordanceConfig>> configsProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<KeyguardQuickAffordanceLegacySettingSyncer> legacySettingSyncerProvider;
    public final Provider<KeyguardQuickAffordanceLocalUserSelectionManager> localUserSelectionManagerProvider;
    public final Provider<KeyguardQuickAffordanceRemoteUserSelectionManager> remoteUserSelectionManagerProvider;
    public final Provider<CoroutineScope> scopeProvider;
    public final Provider<UserHandle> userHandleProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public KeyguardQuickAffordanceRepository_Factory(Provider<Context> provider, Provider<CoroutineScope> provider2, Provider<KeyguardQuickAffordanceLocalUserSelectionManager> provider3, Provider<KeyguardQuickAffordanceRemoteUserSelectionManager> provider4, Provider<UserTracker> provider5, Provider<KeyguardQuickAffordanceLegacySettingSyncer> provider6, Provider<Set<KeyguardQuickAffordanceConfig>> provider7, Provider<DumpManager> provider8, Provider<UserHandle> provider9) {
        this.appContextProvider = provider;
        this.scopeProvider = provider2;
        this.localUserSelectionManagerProvider = provider3;
        this.remoteUserSelectionManagerProvider = provider4;
        this.userTrackerProvider = provider5;
        this.legacySettingSyncerProvider = provider6;
        this.configsProvider = provider7;
        this.dumpManagerProvider = provider8;
        this.userHandleProvider = provider9;
    }

    public static KeyguardQuickAffordanceRepository_Factory create(Provider<Context> provider, Provider<CoroutineScope> provider2, Provider<KeyguardQuickAffordanceLocalUserSelectionManager> provider3, Provider<KeyguardQuickAffordanceRemoteUserSelectionManager> provider4, Provider<UserTracker> provider5, Provider<KeyguardQuickAffordanceLegacySettingSyncer> provider6, Provider<Set<KeyguardQuickAffordanceConfig>> provider7, Provider<DumpManager> provider8, Provider<UserHandle> provider9) {
        return new KeyguardQuickAffordanceRepository_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static KeyguardQuickAffordanceRepository newInstance(Context context, CoroutineScope coroutineScope, KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager, KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager, UserTracker userTracker, KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, Set<KeyguardQuickAffordanceConfig> set, DumpManager dumpManager, UserHandle userHandle) {
        return new KeyguardQuickAffordanceRepository(context, coroutineScope, keyguardQuickAffordanceLocalUserSelectionManager, keyguardQuickAffordanceRemoteUserSelectionManager, userTracker, keyguardQuickAffordanceLegacySettingSyncer, set, dumpManager, userHandle);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardQuickAffordanceRepository m2968get() {
        return newInstance((Context) this.appContextProvider.get(), (CoroutineScope) this.scopeProvider.get(), (KeyguardQuickAffordanceLocalUserSelectionManager) this.localUserSelectionManagerProvider.get(), (KeyguardQuickAffordanceRemoteUserSelectionManager) this.remoteUserSelectionManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (KeyguardQuickAffordanceLegacySettingSyncer) this.legacySettingSyncerProvider.get(), (Set) this.configsProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (UserHandle) this.userHandleProvider.get());
    }
}