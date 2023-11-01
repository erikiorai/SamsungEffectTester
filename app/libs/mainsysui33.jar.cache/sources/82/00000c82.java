package com.android.keyguard;

import android.view.accessibility.AccessibilityManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/LiftToActivateListener_Factory.class */
public final class LiftToActivateListener_Factory implements Factory<LiftToActivateListener> {
    public final Provider<AccessibilityManager> accessibilityManagerProvider;

    public LiftToActivateListener_Factory(Provider<AccessibilityManager> provider) {
        this.accessibilityManagerProvider = provider;
    }

    public static LiftToActivateListener_Factory create(Provider<AccessibilityManager> provider) {
        return new LiftToActivateListener_Factory(provider);
    }

    public static LiftToActivateListener newInstance(AccessibilityManager accessibilityManager) {
        return new LiftToActivateListener(accessibilityManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LiftToActivateListener m775get() {
        return newInstance((AccessibilityManager) this.accessibilityManagerProvider.get());
    }
}