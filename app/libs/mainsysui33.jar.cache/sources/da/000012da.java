package com.android.systemui.classifier;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingModule_ProvidesDoubleTapTouchSlopFactory.class */
public final class FalsingModule_ProvidesDoubleTapTouchSlopFactory implements Factory<Float> {
    public final Provider<Resources> resourcesProvider;

    public FalsingModule_ProvidesDoubleTapTouchSlopFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static FalsingModule_ProvidesDoubleTapTouchSlopFactory create(Provider<Resources> provider) {
        return new FalsingModule_ProvidesDoubleTapTouchSlopFactory(provider);
    }

    public static float providesDoubleTapTouchSlop(Resources resources) {
        return FalsingModule.providesDoubleTapTouchSlop(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Float m1709get() {
        return Float.valueOf(providesDoubleTapTouchSlop((Resources) this.resourcesProvider.get()));
    }
}