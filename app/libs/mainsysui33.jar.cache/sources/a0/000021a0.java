package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.qs.QSFragment;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvideRootViewFactory.class */
public final class QSFragmentModule_ProvideRootViewFactory implements Factory<View> {
    public final Provider<QSFragment> qsFragmentProvider;

    public QSFragmentModule_ProvideRootViewFactory(Provider<QSFragment> provider) {
        this.qsFragmentProvider = provider;
    }

    public static QSFragmentModule_ProvideRootViewFactory create(Provider<QSFragment> provider) {
        return new QSFragmentModule_ProvideRootViewFactory(provider);
    }

    public static View provideRootView(QSFragment qSFragment) {
        return (View) Preconditions.checkNotNullFromProvides(QSFragmentModule.provideRootView(qSFragment));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public View m3876get() {
        return provideRootView((QSFragment) this.qsFragmentProvider.get());
    }
}