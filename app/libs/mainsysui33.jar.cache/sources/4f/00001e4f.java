package com.android.systemui.navigationbar;

import android.content.Context;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarModule_ProvideEdgeBackGestureHandlerFactory.class */
public final class NavigationBarModule_ProvideEdgeBackGestureHandlerFactory implements Factory<EdgeBackGestureHandler> {
    public final Provider<Context> contextProvider;
    public final Provider<EdgeBackGestureHandler.Factory> factoryProvider;

    public NavigationBarModule_ProvideEdgeBackGestureHandlerFactory(Provider<EdgeBackGestureHandler.Factory> provider, Provider<Context> provider2) {
        this.factoryProvider = provider;
        this.contextProvider = provider2;
    }

    public static NavigationBarModule_ProvideEdgeBackGestureHandlerFactory create(Provider<EdgeBackGestureHandler.Factory> provider, Provider<Context> provider2) {
        return new NavigationBarModule_ProvideEdgeBackGestureHandlerFactory(provider, provider2);
    }

    public static EdgeBackGestureHandler provideEdgeBackGestureHandler(EdgeBackGestureHandler.Factory factory, Context context) {
        return (EdgeBackGestureHandler) Preconditions.checkNotNullFromProvides(NavigationBarModule.provideEdgeBackGestureHandler(factory, context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public EdgeBackGestureHandler m3428get() {
        return provideEdgeBackGestureHandler((EdgeBackGestureHandler.Factory) this.factoryProvider.get(), (Context) this.contextProvider.get());
    }
}