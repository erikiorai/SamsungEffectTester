package com.android.systemui.classifier;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingA11yDelegate_Factory.class */
public final class FalsingA11yDelegate_Factory implements Factory<FalsingA11yDelegate> {
    public final Provider<FalsingCollector> falsingCollectorProvider;

    public FalsingA11yDelegate_Factory(Provider<FalsingCollector> provider) {
        this.falsingCollectorProvider = provider;
    }

    public static FalsingA11yDelegate_Factory create(Provider<FalsingCollector> provider) {
        return new FalsingA11yDelegate_Factory(provider);
    }

    public static FalsingA11yDelegate newInstance(FalsingCollector falsingCollector) {
        return new FalsingA11yDelegate(falsingCollector);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FalsingA11yDelegate m1688get() {
        return newInstance((FalsingCollector) this.falsingCollectorProvider.get());
    }
}