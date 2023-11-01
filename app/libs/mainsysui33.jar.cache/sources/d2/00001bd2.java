package com.android.systemui.media;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/RingtonePlayer_Factory.class */
public final class RingtonePlayer_Factory implements Factory<RingtonePlayer> {
    public final Provider<Context> contextProvider;

    public RingtonePlayer_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static RingtonePlayer_Factory create(Provider<Context> provider) {
        return new RingtonePlayer_Factory(provider);
    }

    public static RingtonePlayer newInstance(Context context) {
        return new RingtonePlayer(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public RingtonePlayer m3173get() {
        return newInstance((Context) this.contextProvider.get());
    }
}