package com.android.systemui.qs.dagger;

import com.android.systemui.qs.QSFooter;
import com.android.systemui.qs.QSFooterViewController;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvidesQSFooterFactory.class */
public final class QSFragmentModule_ProvidesQSFooterFactory implements Factory<QSFooter> {
    public final Provider<QSFooterViewController> qsFooterViewControllerProvider;

    public QSFragmentModule_ProvidesQSFooterFactory(Provider<QSFooterViewController> provider) {
        this.qsFooterViewControllerProvider = provider;
    }

    public static QSFragmentModule_ProvidesQSFooterFactory create(Provider<QSFooterViewController> provider) {
        return new QSFragmentModule_ProvidesQSFooterFactory(provider);
    }

    public static QSFooter providesQSFooter(QSFooterViewController qSFooterViewController) {
        return (QSFooter) Preconditions.checkNotNullFromProvides(QSFragmentModule.providesQSFooter(qSFooterViewController));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSFooter m3882get() {
        return providesQSFooter((QSFooterViewController) this.qsFooterViewControllerProvider.get());
    }
}