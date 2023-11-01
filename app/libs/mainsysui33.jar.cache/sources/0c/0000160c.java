package com.android.systemui.doze;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/AlwaysOnDisplayPolicy_Factory.class */
public final class AlwaysOnDisplayPolicy_Factory implements Factory<AlwaysOnDisplayPolicy> {
    public final Provider<Context> contextProvider;

    public AlwaysOnDisplayPolicy_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static AlwaysOnDisplayPolicy_Factory create(Provider<Context> provider) {
        return new AlwaysOnDisplayPolicy_Factory(provider);
    }

    public static AlwaysOnDisplayPolicy newInstance(Context context) {
        return new AlwaysOnDisplayPolicy(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AlwaysOnDisplayPolicy m2404get() {
        return newInstance((Context) this.contextProvider.get());
    }
}