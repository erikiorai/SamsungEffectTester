package com.android.systemui.keyguard.data.repository;

import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.log.table.TableLogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository_Factory.class */
public final class KeyguardBouncerRepository_Factory implements Factory<KeyguardBouncerRepository> {
    public final Provider<CoroutineScope> applicationScopeProvider;
    public final Provider<TableLogBuffer> bufferProvider;
    public final Provider<ViewMediatorCallback> viewMediatorCallbackProvider;

    public KeyguardBouncerRepository_Factory(Provider<ViewMediatorCallback> provider, Provider<CoroutineScope> provider2, Provider<TableLogBuffer> provider3) {
        this.viewMediatorCallbackProvider = provider;
        this.applicationScopeProvider = provider2;
        this.bufferProvider = provider3;
    }

    public static KeyguardBouncerRepository_Factory create(Provider<ViewMediatorCallback> provider, Provider<CoroutineScope> provider2, Provider<TableLogBuffer> provider3) {
        return new KeyguardBouncerRepository_Factory(provider, provider2, provider3);
    }

    public static KeyguardBouncerRepository newInstance(ViewMediatorCallback viewMediatorCallback, CoroutineScope coroutineScope, TableLogBuffer tableLogBuffer) {
        return new KeyguardBouncerRepository(viewMediatorCallback, coroutineScope, tableLogBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardBouncerRepository m2962get() {
        return newInstance((ViewMediatorCallback) this.viewMediatorCallbackProvider.get(), (CoroutineScope) this.applicationScopeProvider.get(), (TableLogBuffer) this.bufferProvider.get());
    }
}