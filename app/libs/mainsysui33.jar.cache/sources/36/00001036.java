package com.android.systemui.accessibility;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/ModeSwitchesController_Factory.class */
public final class ModeSwitchesController_Factory implements Factory<ModeSwitchesController> {
    public final Provider<Context> contextProvider;

    public ModeSwitchesController_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static ModeSwitchesController_Factory create(Provider<Context> provider) {
        return new ModeSwitchesController_Factory(provider);
    }

    public static ModeSwitchesController newInstance(Context context) {
        return new ModeSwitchesController(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ModeSwitchesController m1333get() {
        return newInstance((Context) this.contextProvider.get());
    }
}