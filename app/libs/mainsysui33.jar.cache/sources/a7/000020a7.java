package com.android.systemui.qs;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/FooterActionsController_Factory.class */
public final class FooterActionsController_Factory implements Factory<FooterActionsController> {
    public final Provider<FgsManagerController> fgsManagerControllerProvider;

    public FooterActionsController_Factory(Provider<FgsManagerController> provider) {
        this.fgsManagerControllerProvider = provider;
    }

    public static FooterActionsController_Factory create(Provider<FgsManagerController> provider) {
        return new FooterActionsController_Factory(provider);
    }

    public static FooterActionsController newInstance(FgsManagerController fgsManagerController) {
        return new FooterActionsController(fgsManagerController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FooterActionsController m3719get() {
        return newInstance((FgsManagerController) this.fgsManagerControllerProvider.get());
    }
}