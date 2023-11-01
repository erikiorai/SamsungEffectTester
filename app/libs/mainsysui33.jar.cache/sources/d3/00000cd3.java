package com.android.keyguard.dagger;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardHostView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardBouncerModule_ProvidesKeyguardHostViewFactory.class */
public final class KeyguardBouncerModule_ProvidesKeyguardHostViewFactory implements Factory<KeyguardHostView> {
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<ViewGroup> rootViewProvider;

    public KeyguardBouncerModule_ProvidesKeyguardHostViewFactory(Provider<ViewGroup> provider, Provider<LayoutInflater> provider2) {
        this.rootViewProvider = provider;
        this.layoutInflaterProvider = provider2;
    }

    public static KeyguardBouncerModule_ProvidesKeyguardHostViewFactory create(Provider<ViewGroup> provider, Provider<LayoutInflater> provider2) {
        return new KeyguardBouncerModule_ProvidesKeyguardHostViewFactory(provider, provider2);
    }

    public static KeyguardHostView providesKeyguardHostView(ViewGroup viewGroup, LayoutInflater layoutInflater) {
        return (KeyguardHostView) Preconditions.checkNotNullFromProvides(KeyguardBouncerModule.providesKeyguardHostView(viewGroup, layoutInflater));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardHostView m837get() {
        return providesKeyguardHostView((ViewGroup) this.rootViewProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get());
    }
}