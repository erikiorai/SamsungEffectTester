package com.android.systemui.dagger;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.model.SysUiState;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SystemUIModule_ProvideSysUiStateFactory.class */
public final class SystemUIModule_ProvideSysUiStateFactory implements Factory<SysUiState> {
    public final Provider<DumpManager> dumpManagerProvider;

    public SystemUIModule_ProvideSysUiStateFactory(Provider<DumpManager> provider) {
        this.dumpManagerProvider = provider;
    }

    public static SystemUIModule_ProvideSysUiStateFactory create(Provider<DumpManager> provider) {
        return new SystemUIModule_ProvideSysUiStateFactory(provider);
    }

    public static SysUiState provideSysUiState(DumpManager dumpManager) {
        return (SysUiState) Preconditions.checkNotNullFromProvides(SystemUIModule.provideSysUiState(dumpManager));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SysUiState m2390get() {
        return provideSysUiState((DumpManager) this.dumpManagerProvider.get());
    }
}