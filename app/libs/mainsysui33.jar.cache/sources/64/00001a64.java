package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.logging.KeyguardLogger;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionAuditLogger_Factory.class */
public final class KeyguardTransitionAuditLogger_Factory implements Factory<KeyguardTransitionAuditLogger> {
    public final Provider<KeyguardTransitionInteractor> interactorProvider;
    public final Provider<KeyguardInteractor> keyguardInteractorProvider;
    public final Provider<KeyguardLogger> loggerProvider;
    public final Provider<CoroutineScope> scopeProvider;

    public KeyguardTransitionAuditLogger_Factory(Provider<CoroutineScope> provider, Provider<KeyguardTransitionInteractor> provider2, Provider<KeyguardInteractor> provider3, Provider<KeyguardLogger> provider4) {
        this.scopeProvider = provider;
        this.interactorProvider = provider2;
        this.keyguardInteractorProvider = provider3;
        this.loggerProvider = provider4;
    }

    public static KeyguardTransitionAuditLogger_Factory create(Provider<CoroutineScope> provider, Provider<KeyguardTransitionInteractor> provider2, Provider<KeyguardInteractor> provider3, Provider<KeyguardLogger> provider4) {
        return new KeyguardTransitionAuditLogger_Factory(provider, provider2, provider3, provider4);
    }

    public static KeyguardTransitionAuditLogger newInstance(CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, KeyguardLogger keyguardLogger) {
        return new KeyguardTransitionAuditLogger(coroutineScope, keyguardTransitionInteractor, keyguardInteractor, keyguardLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardTransitionAuditLogger m3044get() {
        return newInstance((CoroutineScope) this.scopeProvider.get(), (KeyguardTransitionInteractor) this.interactorProvider.get(), (KeyguardInteractor) this.keyguardInteractorProvider.get(), (KeyguardLogger) this.loggerProvider.get());
    }
}