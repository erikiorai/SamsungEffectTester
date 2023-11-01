package com.android.systemui.screenshot;

import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotProxyService_Factory.class */
public final class ScreenshotProxyService_Factory implements Factory<ScreenshotProxyService> {
    public final Provider<Optional<CentralSurfaces>> mCentralSurfacesOptionalProvider;
    public final Provider<ShadeExpansionStateManager> mExpansionMgrProvider;
    public final Provider<CoroutineDispatcher> mMainDispatcherProvider;

    public ScreenshotProxyService_Factory(Provider<ShadeExpansionStateManager> provider, Provider<Optional<CentralSurfaces>> provider2, Provider<CoroutineDispatcher> provider3) {
        this.mExpansionMgrProvider = provider;
        this.mCentralSurfacesOptionalProvider = provider2;
        this.mMainDispatcherProvider = provider3;
    }

    public static ScreenshotProxyService_Factory create(Provider<ShadeExpansionStateManager> provider, Provider<Optional<CentralSurfaces>> provider2, Provider<CoroutineDispatcher> provider3) {
        return new ScreenshotProxyService_Factory(provider, provider2, provider3);
    }

    public static ScreenshotProxyService newInstance(ShadeExpansionStateManager shadeExpansionStateManager, Optional<CentralSurfaces> optional, CoroutineDispatcher coroutineDispatcher) {
        return new ScreenshotProxyService(shadeExpansionStateManager, optional, coroutineDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenshotProxyService m4296get() {
        return newInstance((ShadeExpansionStateManager) this.mExpansionMgrProvider.get(), (Optional) this.mCentralSurfacesOptionalProvider.get(), (CoroutineDispatcher) this.mMainDispatcherProvider.get());
    }
}