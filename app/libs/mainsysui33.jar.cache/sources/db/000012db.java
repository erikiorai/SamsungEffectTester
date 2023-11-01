package com.android.systemui.classifier;

import android.view.ViewConfiguration;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingModule_ProvidesLongTapTouchSlopFactory.class */
public final class FalsingModule_ProvidesLongTapTouchSlopFactory implements Factory<Float> {
    public final Provider<ViewConfiguration> viewConfigurationProvider;

    public FalsingModule_ProvidesLongTapTouchSlopFactory(Provider<ViewConfiguration> provider) {
        this.viewConfigurationProvider = provider;
    }

    public static FalsingModule_ProvidesLongTapTouchSlopFactory create(Provider<ViewConfiguration> provider) {
        return new FalsingModule_ProvidesLongTapTouchSlopFactory(provider);
    }

    public static float providesLongTapTouchSlop(ViewConfiguration viewConfiguration) {
        return FalsingModule.providesLongTapTouchSlop(viewConfiguration);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Float m1710get() {
        return Float.valueOf(providesLongTapTouchSlop((ViewConfiguration) this.viewConfigurationProvider.get()));
    }
}