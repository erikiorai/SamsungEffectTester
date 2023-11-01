package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBouncerViewModel_Factory.class */
public final class KeyguardBouncerViewModel_Factory implements Factory<KeyguardBouncerViewModel> {
    public final Provider<PrimaryBouncerInteractor> interactorProvider;
    public final Provider<BouncerView> viewProvider;

    public KeyguardBouncerViewModel_Factory(Provider<BouncerView> provider, Provider<PrimaryBouncerInteractor> provider2) {
        this.viewProvider = provider;
        this.interactorProvider = provider2;
    }

    public static KeyguardBouncerViewModel_Factory create(Provider<BouncerView> provider, Provider<PrimaryBouncerInteractor> provider2) {
        return new KeyguardBouncerViewModel_Factory(provider, provider2);
    }

    public static KeyguardBouncerViewModel newInstance(BouncerView bouncerView, PrimaryBouncerInteractor primaryBouncerInteractor) {
        return new KeyguardBouncerViewModel(bouncerView, primaryBouncerInteractor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardBouncerViewModel m3089get() {
        return newInstance((BouncerView) this.viewProvider.get(), (PrimaryBouncerInteractor) this.interactorProvider.get());
    }
}