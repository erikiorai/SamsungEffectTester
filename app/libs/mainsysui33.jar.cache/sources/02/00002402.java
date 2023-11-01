package com.android.systemui.screenshot;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ActionIntentExecutor_Factory.class */
public final class ActionIntentExecutor_Factory implements Factory<ActionIntentExecutor> {
    public final Provider<CoroutineScope> applicationScopeProvider;
    public final Provider<Context> contextProvider;
    public final Provider<CoroutineDispatcher> mainDispatcherProvider;

    public ActionIntentExecutor_Factory(Provider<CoroutineScope> provider, Provider<CoroutineDispatcher> provider2, Provider<Context> provider3) {
        this.applicationScopeProvider = provider;
        this.mainDispatcherProvider = provider2;
        this.contextProvider = provider3;
    }

    public static ActionIntentExecutor_Factory create(Provider<CoroutineScope> provider, Provider<CoroutineDispatcher> provider2, Provider<Context> provider3) {
        return new ActionIntentExecutor_Factory(provider, provider2, provider3);
    }

    public static ActionIntentExecutor newInstance(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Context context) {
        return new ActionIntentExecutor(coroutineScope, coroutineDispatcher, context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActionIntentExecutor m4196get() {
        return newInstance((CoroutineScope) this.applicationScopeProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (Context) this.contextProvider.get());
    }
}