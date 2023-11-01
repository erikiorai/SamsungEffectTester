package com.android.systemui.dagger;

import android.content.Context;
import android.view.textclassifier.TextClassificationManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideTextClassificationManagerFactory.class */
public final class FrameworkServicesModule_ProvideTextClassificationManagerFactory implements Factory<TextClassificationManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideTextClassificationManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideTextClassificationManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideTextClassificationManagerFactory(provider);
    }

    public static TextClassificationManager provideTextClassificationManager(Context context) {
        return (TextClassificationManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideTextClassificationManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TextClassificationManager m2356get() {
        return provideTextClassificationManager((Context) this.contextProvider.get());
    }
}