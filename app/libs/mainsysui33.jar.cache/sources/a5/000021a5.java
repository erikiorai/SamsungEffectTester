package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.qs.customize.QSCustomizer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvidesQSCutomizerFactory.class */
public final class QSFragmentModule_ProvidesQSCutomizerFactory implements Factory<QSCustomizer> {
    public final Provider<View> viewProvider;

    public QSFragmentModule_ProvidesQSCutomizerFactory(Provider<View> provider) {
        this.viewProvider = provider;
    }

    public static QSFragmentModule_ProvidesQSCutomizerFactory create(Provider<View> provider) {
        return new QSFragmentModule_ProvidesQSCutomizerFactory(provider);
    }

    public static QSCustomizer providesQSCutomizer(View view) {
        return (QSCustomizer) Preconditions.checkNotNullFromProvides(QSFragmentModule.providesQSCutomizer(view));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSCustomizer m3881get() {
        return providesQSCutomizer((View) this.viewProvider.get());
    }
}