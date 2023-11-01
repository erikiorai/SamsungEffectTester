package com.android.systemui.accessibility;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/AccessibilityButtonTargetsObserver_Factory.class */
public final class AccessibilityButtonTargetsObserver_Factory implements Factory<AccessibilityButtonTargetsObserver> {
    public final Provider<Context> contextProvider;

    public AccessibilityButtonTargetsObserver_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static AccessibilityButtonTargetsObserver_Factory create(Provider<Context> provider) {
        return new AccessibilityButtonTargetsObserver_Factory(provider);
    }

    public static AccessibilityButtonTargetsObserver newInstance(Context context) {
        return new AccessibilityButtonTargetsObserver(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AccessibilityButtonTargetsObserver m1324get() {
        return newInstance((Context) this.contextProvider.get());
    }
}