package com.android.systemui.media.nearby;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/nearby/NearbyMediaDevicesLogger_Factory.class */
public final class NearbyMediaDevicesLogger_Factory implements Factory<NearbyMediaDevicesLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public NearbyMediaDevicesLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static NearbyMediaDevicesLogger_Factory create(Provider<LogBuffer> provider) {
        return new NearbyMediaDevicesLogger_Factory(provider);
    }

    public static NearbyMediaDevicesLogger newInstance(LogBuffer logBuffer) {
        return new NearbyMediaDevicesLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NearbyMediaDevicesLogger m3339get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}