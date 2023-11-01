package com.android.systemui.accessibility;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/AccessibilityButtonModeObserver_Factory.class */
public final class AccessibilityButtonModeObserver_Factory implements Factory<AccessibilityButtonModeObserver> {
    public final Provider<Context> contextProvider;

    public AccessibilityButtonModeObserver_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static AccessibilityButtonModeObserver_Factory create(Provider<Context> provider) {
        return new AccessibilityButtonModeObserver_Factory(provider);
    }

    public static AccessibilityButtonModeObserver newInstance(Context context) {
        return new AccessibilityButtonModeObserver(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AccessibilityButtonModeObserver m1323get() {
        return newInstance((Context) this.contextProvider.get());
    }
}