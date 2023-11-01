package com.android.systemui.classifier;

import android.view.ViewConfiguration;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingModule_ProvidesSingleTapTouchSlopFactory.class */
public final class FalsingModule_ProvidesSingleTapTouchSlopFactory implements Factory<Float> {
    public final Provider<ViewConfiguration> viewConfigurationProvider;

    public FalsingModule_ProvidesSingleTapTouchSlopFactory(Provider<ViewConfiguration> provider) {
        this.viewConfigurationProvider = provider;
    }

    public static FalsingModule_ProvidesSingleTapTouchSlopFactory create(Provider<ViewConfiguration> provider) {
        return new FalsingModule_ProvidesSingleTapTouchSlopFactory(provider);
    }

    public static float providesSingleTapTouchSlop(ViewConfiguration viewConfiguration) {
        return FalsingModule.providesSingleTapTouchSlop(viewConfiguration);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Float m1711get() {
        return Float.valueOf(providesSingleTapTouchSlop((ViewConfiguration) this.viewConfigurationProvider.get()));
    }
}