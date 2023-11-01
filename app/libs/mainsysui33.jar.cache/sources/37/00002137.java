package com.android.systemui.qs;

import android.content.Context;
import com.android.systemui.qs.QSTileRevealController;
import com.android.systemui.qs.customize.QSCustomizerController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSTileRevealController_Factory_Factory.class */
public final class QSTileRevealController_Factory_Factory implements Factory<QSTileRevealController.Factory> {
    public final Provider<Context> contextProvider;
    public final Provider<QSCustomizerController> qsCustomizerControllerProvider;

    public QSTileRevealController_Factory_Factory(Provider<Context> provider, Provider<QSCustomizerController> provider2) {
        this.contextProvider = provider;
        this.qsCustomizerControllerProvider = provider2;
    }

    public static QSTileRevealController_Factory_Factory create(Provider<Context> provider, Provider<QSCustomizerController> provider2) {
        return new QSTileRevealController_Factory_Factory(provider, provider2);
    }

    public static QSTileRevealController.Factory newInstance(Context context, QSCustomizerController qSCustomizerController) {
        return new QSTileRevealController.Factory(context, qSCustomizerController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSTileRevealController.Factory m3802get() {
        return newInstance((Context) this.contextProvider.get(), (QSCustomizerController) this.qsCustomizerControllerProvider.get());
    }
}