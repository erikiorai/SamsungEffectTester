package com.android.systemui.navigationbar.gestural;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/GestureModule_ProvidsBackGestureTfClassifierProviderFactory.class */
public final class GestureModule_ProvidsBackGestureTfClassifierProviderFactory implements Factory<BackGestureTfClassifierProvider> {

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/GestureModule_ProvidsBackGestureTfClassifierProviderFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final GestureModule_ProvidsBackGestureTfClassifierProviderFactory INSTANCE = new GestureModule_ProvidsBackGestureTfClassifierProviderFactory();
    }

    public static GestureModule_ProvidsBackGestureTfClassifierProviderFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static BackGestureTfClassifierProvider providsBackGestureTfClassifierProvider() {
        return (BackGestureTfClassifierProvider) Preconditions.checkNotNullFromProvides(GestureModule.providsBackGestureTfClassifierProvider());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BackGestureTfClassifierProvider m3499get() {
        return providsBackGestureTfClassifierProvider();
    }
}