package com.android.systemui.accessibility;

import android.content.Context;
import com.android.systemui.recents.Recents;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/SystemActions_Factory.class */
public final class SystemActions_Factory implements Factory<SystemActions> {
    public final Provider<Optional<CentralSurfaces>> centralSurfacesOptionalLazyProvider;
    public final Provider<Context> contextProvider;
    public final Provider<NotificationShadeWindowController> notificationShadeControllerProvider;
    public final Provider<Optional<Recents>> recentsOptionalProvider;
    public final Provider<ShadeController> shadeControllerProvider;

    public SystemActions_Factory(Provider<Context> provider, Provider<NotificationShadeWindowController> provider2, Provider<ShadeController> provider3, Provider<Optional<CentralSurfaces>> provider4, Provider<Optional<Recents>> provider5) {
        this.contextProvider = provider;
        this.notificationShadeControllerProvider = provider2;
        this.shadeControllerProvider = provider3;
        this.centralSurfacesOptionalLazyProvider = provider4;
        this.recentsOptionalProvider = provider5;
    }

    public static SystemActions_Factory create(Provider<Context> provider, Provider<NotificationShadeWindowController> provider2, Provider<ShadeController> provider3, Provider<Optional<CentralSurfaces>> provider4, Provider<Optional<Recents>> provider5) {
        return new SystemActions_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static SystemActions newInstance(Context context, NotificationShadeWindowController notificationShadeWindowController, ShadeController shadeController, Lazy<Optional<CentralSurfaces>> lazy, Optional<Recents> optional) {
        return new SystemActions(context, notificationShadeWindowController, shadeController, lazy, optional);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SystemActions m1357get() {
        return newInstance((Context) this.contextProvider.get(), (NotificationShadeWindowController) this.notificationShadeControllerProvider.get(), (ShadeController) this.shadeControllerProvider.get(), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (Optional) this.recentsOptionalProvider.get());
    }
}