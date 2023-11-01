package com.android.systemui.screenshot;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/RequestProcessor_Factory.class */
public final class RequestProcessor_Factory implements Factory<RequestProcessor> {
    public final Provider<ImageCapture> captureProvider;
    public final Provider<FeatureFlags> flagsProvider;
    public final Provider<CoroutineScope> mainScopeProvider;
    public final Provider<ScreenshotPolicy> policyProvider;

    public RequestProcessor_Factory(Provider<ImageCapture> provider, Provider<ScreenshotPolicy> provider2, Provider<FeatureFlags> provider3, Provider<CoroutineScope> provider4) {
        this.captureProvider = provider;
        this.policyProvider = provider2;
        this.flagsProvider = provider3;
        this.mainScopeProvider = provider4;
    }

    public static RequestProcessor_Factory create(Provider<ImageCapture> provider, Provider<ScreenshotPolicy> provider2, Provider<FeatureFlags> provider3, Provider<CoroutineScope> provider4) {
        return new RequestProcessor_Factory(provider, provider2, provider3, provider4);
    }

    public static RequestProcessor newInstance(ImageCapture imageCapture, ScreenshotPolicy screenshotPolicy, FeatureFlags featureFlags, CoroutineScope coroutineScope) {
        return new RequestProcessor(imageCapture, screenshotPolicy, featureFlags, coroutineScope);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public RequestProcessor m4254get() {
        return newInstance((ImageCapture) this.captureProvider.get(), (ScreenshotPolicy) this.policyProvider.get(), (FeatureFlags) this.flagsProvider.get(), (CoroutineScope) this.mainScopeProvider.get());
    }
}