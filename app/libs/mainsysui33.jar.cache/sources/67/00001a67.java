package com.android.systemui.keyguard.domain.interactor;

import dagger.internal.Factory;
import java.util.Set;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionCoreStartable_Factory.class */
public final class KeyguardTransitionCoreStartable_Factory implements Factory<KeyguardTransitionCoreStartable> {
    public final Provider<KeyguardTransitionAuditLogger> auditLoggerProvider;
    public final Provider<Set<TransitionInteractor>> interactorsProvider;

    public KeyguardTransitionCoreStartable_Factory(Provider<Set<TransitionInteractor>> provider, Provider<KeyguardTransitionAuditLogger> provider2) {
        this.interactorsProvider = provider;
        this.auditLoggerProvider = provider2;
    }

    public static KeyguardTransitionCoreStartable_Factory create(Provider<Set<TransitionInteractor>> provider, Provider<KeyguardTransitionAuditLogger> provider2) {
        return new KeyguardTransitionCoreStartable_Factory(provider, provider2);
    }

    public static KeyguardTransitionCoreStartable newInstance(Set<TransitionInteractor> set, KeyguardTransitionAuditLogger keyguardTransitionAuditLogger) {
        return new KeyguardTransitionCoreStartable(set, keyguardTransitionAuditLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardTransitionCoreStartable m3046get() {
        return newInstance((Set) this.interactorsProvider.get(), (KeyguardTransitionAuditLogger) this.auditLoggerProvider.get());
    }
}