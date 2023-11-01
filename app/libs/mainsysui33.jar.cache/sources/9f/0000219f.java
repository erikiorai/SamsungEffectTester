package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.qs.QSPanel;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvideQSPanelFactory.class */
public final class QSFragmentModule_ProvideQSPanelFactory implements Factory<QSPanel> {
    public final Provider<View> viewProvider;

    public QSFragmentModule_ProvideQSPanelFactory(Provider<View> provider) {
        this.viewProvider = provider;
    }

    public static QSFragmentModule_ProvideQSPanelFactory create(Provider<View> provider) {
        return new QSFragmentModule_ProvideQSPanelFactory(provider);
    }

    public static QSPanel provideQSPanel(View view) {
        return (QSPanel) Preconditions.checkNotNullFromProvides(QSFragmentModule.provideQSPanel(view));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSPanel m3875get() {
        return provideQSPanel((View) this.viewProvider.get());
    }
}