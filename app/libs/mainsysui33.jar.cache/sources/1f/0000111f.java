package com.android.systemui.assist;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistModule_ProvideAssistUtilsFactory.class */
public final class AssistModule_ProvideAssistUtilsFactory implements Factory<AssistUtils> {
    public final Provider<Context> contextProvider;

    public AssistModule_ProvideAssistUtilsFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static AssistModule_ProvideAssistUtilsFactory create(Provider<Context> provider) {
        return new AssistModule_ProvideAssistUtilsFactory(provider);
    }

    public static AssistUtils provideAssistUtils(Context context) {
        return (AssistUtils) Preconditions.checkNotNullFromProvides(AssistModule.provideAssistUtils(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AssistUtils m1471get() {
        return provideAssistUtils((Context) this.contextProvider.get());
    }
}