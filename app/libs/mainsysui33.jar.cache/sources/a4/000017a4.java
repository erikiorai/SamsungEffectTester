package com.android.systemui.dump;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/SystemUIAuxiliaryDumpService_Factory.class */
public final class SystemUIAuxiliaryDumpService_Factory implements Factory<SystemUIAuxiliaryDumpService> {
    public final Provider<DumpHandler> dumpHandlerProvider;

    public SystemUIAuxiliaryDumpService_Factory(Provider<DumpHandler> provider) {
        this.dumpHandlerProvider = provider;
    }

    public static SystemUIAuxiliaryDumpService_Factory create(Provider<DumpHandler> provider) {
        return new SystemUIAuxiliaryDumpService_Factory(provider);
    }

    public static SystemUIAuxiliaryDumpService newInstance(DumpHandler dumpHandler) {
        return new SystemUIAuxiliaryDumpService(dumpHandler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SystemUIAuxiliaryDumpService m2671get() {
        return newInstance((DumpHandler) this.dumpHandlerProvider.get());
    }
}