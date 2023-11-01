package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.qs.QuickStatusBarHeader;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvidesQuickStatusBarHeaderFactory.class */
public final class QSFragmentModule_ProvidesQuickStatusBarHeaderFactory implements Factory<QuickStatusBarHeader> {
    public final Provider<View> viewProvider;

    public QSFragmentModule_ProvidesQuickStatusBarHeaderFactory(Provider<View> provider) {
        this.viewProvider = provider;
    }

    public static QSFragmentModule_ProvidesQuickStatusBarHeaderFactory create(Provider<View> provider) {
        return new QSFragmentModule_ProvidesQuickStatusBarHeaderFactory(provider);
    }

    public static QuickStatusBarHeader providesQuickStatusBarHeader(View view) {
        return (QuickStatusBarHeader) Preconditions.checkNotNullFromProvides(QSFragmentModule.providesQuickStatusBarHeader(view));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QuickStatusBarHeader m3887get() {
        return providesQuickStatusBarHeader((View) this.viewProvider.get());
    }
}