package com.android.systemui.camera;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/camera/CameraIntentsWrapper_Factory.class */
public final class CameraIntentsWrapper_Factory implements Factory<CameraIntentsWrapper> {
    public final Provider<Context> contextProvider;

    public CameraIntentsWrapper_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static CameraIntentsWrapper_Factory create(Provider<Context> provider) {
        return new CameraIntentsWrapper_Factory(provider);
    }

    public static CameraIntentsWrapper newInstance(Context context) {
        return new CameraIntentsWrapper(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CameraIntentsWrapper m1656get() {
        return newInstance((Context) this.contextProvider.get());
    }
}