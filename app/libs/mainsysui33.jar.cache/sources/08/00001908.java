package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.camera.CameraGestureHelper;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/CameraQuickAffordanceConfig_Factory.class */
public final class CameraQuickAffordanceConfig_Factory implements Factory<CameraQuickAffordanceConfig> {
    public final Provider<CameraGestureHelper> cameraGestureHelperProvider;
    public final Provider<Context> contextProvider;

    public CameraQuickAffordanceConfig_Factory(Provider<Context> provider, Provider<CameraGestureHelper> provider2) {
        this.contextProvider = provider;
        this.cameraGestureHelperProvider = provider2;
    }

    public static CameraQuickAffordanceConfig_Factory create(Provider<Context> provider, Provider<CameraGestureHelper> provider2) {
        return new CameraQuickAffordanceConfig_Factory(provider, provider2);
    }

    public static CameraQuickAffordanceConfig newInstance(Context context, Lazy<CameraGestureHelper> lazy) {
        return new CameraQuickAffordanceConfig(context, lazy);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CameraQuickAffordanceConfig m2917get() {
        return newInstance((Context) this.contextProvider.get(), DoubleCheck.lazy(this.cameraGestureHelperProvider));
    }
}