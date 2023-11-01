package com.android.systemui.doze;

import com.android.systemui.classifier.FalsingCollector;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeFalsingManagerAdapter_Factory.class */
public final class DozeFalsingManagerAdapter_Factory implements Factory<DozeFalsingManagerAdapter> {
    public final Provider<FalsingCollector> falsingCollectorProvider;

    public DozeFalsingManagerAdapter_Factory(Provider<FalsingCollector> provider) {
        this.falsingCollectorProvider = provider;
    }

    public static DozeFalsingManagerAdapter_Factory create(Provider<FalsingCollector> provider) {
        return new DozeFalsingManagerAdapter_Factory(provider);
    }

    public static DozeFalsingManagerAdapter newInstance(FalsingCollector falsingCollector) {
        return new DozeFalsingManagerAdapter(falsingCollector);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeFalsingManagerAdapter m2416get() {
        return newInstance((FalsingCollector) this.falsingCollectorProvider.get());
    }
}