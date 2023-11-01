package com.android.systemui.qs;

import com.android.systemui.plugins.qs.QS;
import com.android.systemui.tuner.TunerService;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSAnimator_Factory.class */
public final class QSAnimator_Factory implements Factory<QSAnimator> {
    public final Provider<Executor> executorProvider;
    public final Provider<QSExpansionPathInterpolator> qsExpansionPathInterpolatorProvider;
    public final Provider<QSPanelController> qsPanelControllerProvider;
    public final Provider<QS> qsProvider;
    public final Provider<QSTileHost> qsTileHostProvider;
    public final Provider<QuickQSPanel> quickPanelProvider;
    public final Provider<QuickQSPanelController> quickQSPanelControllerProvider;
    public final Provider<QuickStatusBarHeader> quickStatusBarHeaderProvider;
    public final Provider<TunerService> tunerServiceProvider;

    public QSAnimator_Factory(Provider<QS> provider, Provider<QuickQSPanel> provider2, Provider<QuickStatusBarHeader> provider3, Provider<QSPanelController> provider4, Provider<QuickQSPanelController> provider5, Provider<QSTileHost> provider6, Provider<Executor> provider7, Provider<TunerService> provider8, Provider<QSExpansionPathInterpolator> provider9) {
        this.qsProvider = provider;
        this.quickPanelProvider = provider2;
        this.quickStatusBarHeaderProvider = provider3;
        this.qsPanelControllerProvider = provider4;
        this.quickQSPanelControllerProvider = provider5;
        this.qsTileHostProvider = provider6;
        this.executorProvider = provider7;
        this.tunerServiceProvider = provider8;
        this.qsExpansionPathInterpolatorProvider = provider9;
    }

    public static QSAnimator_Factory create(Provider<QS> provider, Provider<QuickQSPanel> provider2, Provider<QuickStatusBarHeader> provider3, Provider<QSPanelController> provider4, Provider<QuickQSPanelController> provider5, Provider<QSTileHost> provider6, Provider<Executor> provider7, Provider<TunerService> provider8, Provider<QSExpansionPathInterpolator> provider9) {
        return new QSAnimator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static QSAnimator newInstance(QS qs, QuickQSPanel quickQSPanel, QuickStatusBarHeader quickStatusBarHeader, QSPanelController qSPanelController, QuickQSPanelController quickQSPanelController, QSTileHost qSTileHost, Executor executor, TunerService tunerService, QSExpansionPathInterpolator qSExpansionPathInterpolator) {
        return new QSAnimator(qs, quickQSPanel, quickStatusBarHeader, qSPanelController, quickQSPanelController, qSTileHost, executor, tunerService, qSExpansionPathInterpolator);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSAnimator m3739get() {
        return newInstance((QS) this.qsProvider.get(), (QuickQSPanel) this.quickPanelProvider.get(), (QuickStatusBarHeader) this.quickStatusBarHeaderProvider.get(), (QSPanelController) this.qsPanelControllerProvider.get(), (QuickQSPanelController) this.quickQSPanelControllerProvider.get(), (QSTileHost) this.qsTileHostProvider.get(), (Executor) this.executorProvider.get(), (TunerService) this.tunerServiceProvider.get(), (QSExpansionPathInterpolator) this.qsExpansionPathInterpolatorProvider.get());
    }
}