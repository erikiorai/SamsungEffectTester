package com.android.systemui;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/ActivityIntentHelper_Factory.class */
public final class ActivityIntentHelper_Factory implements Factory<ActivityIntentHelper> {
    public final Provider<Context> contextProvider;

    public ActivityIntentHelper_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static ActivityIntentHelper_Factory create(Provider<Context> provider) {
        return new ActivityIntentHelper_Factory(provider);
    }

    public static ActivityIntentHelper newInstance(Context context) {
        return new ActivityIntentHelper(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActivityIntentHelper m1220get() {
        return newInstance((Context) this.contextProvider.get());
    }
}