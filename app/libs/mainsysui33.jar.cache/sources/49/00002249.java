package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel_Factory_Factory.class */
public final class FooterActionsViewModel_Factory_Factory implements Factory<FooterActionsViewModel.Factory> {
    public final Provider<Context> contextProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FooterActionsInteractor> footerActionsInteractorProvider;
    public final Provider<GlobalActionsDialogLite> globalActionsDialogLiteProvider;
    public final Provider<Boolean> showPowerButtonProvider;

    public FooterActionsViewModel_Factory_Factory(Provider<Context> provider, Provider<FalsingManager> provider2, Provider<FooterActionsInteractor> provider3, Provider<GlobalActionsDialogLite> provider4, Provider<Boolean> provider5) {
        this.contextProvider = provider;
        this.falsingManagerProvider = provider2;
        this.footerActionsInteractorProvider = provider3;
        this.globalActionsDialogLiteProvider = provider4;
        this.showPowerButtonProvider = provider5;
    }

    public static FooterActionsViewModel_Factory_Factory create(Provider<Context> provider, Provider<FalsingManager> provider2, Provider<FooterActionsInteractor> provider3, Provider<GlobalActionsDialogLite> provider4, Provider<Boolean> provider5) {
        return new FooterActionsViewModel_Factory_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static FooterActionsViewModel.Factory newInstance(Context context, FalsingManager falsingManager, FooterActionsInteractor footerActionsInteractor, Provider<GlobalActionsDialogLite> provider, boolean z) {
        return new FooterActionsViewModel.Factory(context, falsingManager, footerActionsInteractor, provider, z);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FooterActionsViewModel.Factory m3936get() {
        return newInstance((Context) this.contextProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (FooterActionsInteractor) this.footerActionsInteractorProvider.get(), this.globalActionsDialogLiteProvider, ((Boolean) this.showPowerButtonProvider.get()).booleanValue());
    }
}