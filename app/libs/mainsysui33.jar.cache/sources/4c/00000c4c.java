package com.android.keyguard;

import android.content.Context;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUnfoldTransition_Factory.class */
public final class KeyguardUnfoldTransition_Factory implements Factory<KeyguardUnfoldTransition> {
    public final Provider<Context> contextProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<NaturalRotationUnfoldProgressProvider> unfoldProgressProvider;

    public KeyguardUnfoldTransition_Factory(Provider<Context> provider, Provider<StatusBarStateController> provider2, Provider<NaturalRotationUnfoldProgressProvider> provider3) {
        this.contextProvider = provider;
        this.statusBarStateControllerProvider = provider2;
        this.unfoldProgressProvider = provider3;
    }

    public static KeyguardUnfoldTransition_Factory create(Provider<Context> provider, Provider<StatusBarStateController> provider2, Provider<NaturalRotationUnfoldProgressProvider> provider3) {
        return new KeyguardUnfoldTransition_Factory(provider, provider2, provider3);
    }

    public static KeyguardUnfoldTransition newInstance(Context context, StatusBarStateController statusBarStateController, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        return new KeyguardUnfoldTransition(context, statusBarStateController, naturalRotationUnfoldProgressProvider);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardUnfoldTransition m707get() {
        return newInstance((Context) this.contextProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (NaturalRotationUnfoldProgressProvider) this.unfoldProgressProvider.get());
    }
}