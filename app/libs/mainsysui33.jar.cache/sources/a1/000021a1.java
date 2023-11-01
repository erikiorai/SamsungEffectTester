package com.android.systemui.qs.dagger;

import android.content.Context;
import android.view.View;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvideThemedContextFactory.class */
public final class QSFragmentModule_ProvideThemedContextFactory implements Factory<Context> {
    public final Provider<View> viewProvider;

    public QSFragmentModule_ProvideThemedContextFactory(Provider<View> provider) {
        this.viewProvider = provider;
    }

    public static QSFragmentModule_ProvideThemedContextFactory create(Provider<View> provider) {
        return new QSFragmentModule_ProvideThemedContextFactory(provider);
    }

    public static Context provideThemedContext(View view) {
        return (Context) Preconditions.checkNotNullFromProvides(QSFragmentModule.provideThemedContext(view));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Context m3877get() {
        return provideThemedContext((View) this.viewProvider.get());
    }
}