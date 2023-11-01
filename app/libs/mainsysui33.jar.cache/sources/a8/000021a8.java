package com.android.systemui.qs.dagger;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvidesQSUsingCollapsedLandscapeMediaFactory.class */
public final class QSFragmentModule_ProvidesQSUsingCollapsedLandscapeMediaFactory implements Factory<Boolean> {
    public final Provider<Context> contextProvider;

    public QSFragmentModule_ProvidesQSUsingCollapsedLandscapeMediaFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static QSFragmentModule_ProvidesQSUsingCollapsedLandscapeMediaFactory create(Provider<Context> provider) {
        return new QSFragmentModule_ProvidesQSUsingCollapsedLandscapeMediaFactory(provider);
    }

    public static boolean providesQSUsingCollapsedLandscapeMedia(Context context) {
        return QSFragmentModule.providesQSUsingCollapsedLandscapeMedia(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m3884get() {
        return Boolean.valueOf(providesQSUsingCollapsedLandscapeMedia((Context) this.contextProvider.get()));
    }
}