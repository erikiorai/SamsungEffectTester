package com.android.systemui.qs.dagger;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvidesQSUsingMediaPlayerFactory.class */
public final class QSFragmentModule_ProvidesQSUsingMediaPlayerFactory implements Factory<Boolean> {
    public final Provider<Context> contextProvider;

    public QSFragmentModule_ProvidesQSUsingMediaPlayerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static QSFragmentModule_ProvidesQSUsingMediaPlayerFactory create(Provider<Context> provider) {
        return new QSFragmentModule_ProvidesQSUsingMediaPlayerFactory(provider);
    }

    public static boolean providesQSUsingMediaPlayer(Context context) {
        return QSFragmentModule.providesQSUsingMediaPlayer(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m3885get() {
        return Boolean.valueOf(providesQSUsingMediaPlayer((Context) this.contextProvider.get()));
    }
}