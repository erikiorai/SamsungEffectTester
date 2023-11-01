package com.android.systemui.qs.dagger;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFlagsModule_IsReduceBrightColorsAvailableFactory.class */
public final class QSFlagsModule_IsReduceBrightColorsAvailableFactory implements Factory<Boolean> {
    public final Provider<Context> contextProvider;

    public QSFlagsModule_IsReduceBrightColorsAvailableFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static QSFlagsModule_IsReduceBrightColorsAvailableFactory create(Provider<Context> provider) {
        return new QSFlagsModule_IsReduceBrightColorsAvailableFactory(provider);
    }

    public static boolean isReduceBrightColorsAvailable(Context context) {
        return QSFlagsModule.isReduceBrightColorsAvailable(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m3874get() {
        return Boolean.valueOf(isReduceBrightColorsAvailable((Context) this.contextProvider.get()));
    }
}