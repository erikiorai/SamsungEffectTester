package com.android.systemui.screenshot;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/TimeoutHandler_Factory.class */
public final class TimeoutHandler_Factory implements Factory<TimeoutHandler> {
    public final Provider<Context> contextProvider;

    public TimeoutHandler_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static TimeoutHandler_Factory create(Provider<Context> provider) {
        return new TimeoutHandler_Factory(provider);
    }

    public static TimeoutHandler newInstance(Context context) {
        return new TimeoutHandler(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TimeoutHandler m4339get() {
        return newInstance((Context) this.contextProvider.get());
    }
}