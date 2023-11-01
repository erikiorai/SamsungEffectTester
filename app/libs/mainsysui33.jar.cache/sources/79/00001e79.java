package com.android.systemui.navigationbar;

import android.content.Context;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/TaskbarDelegate_Factory.class */
public final class TaskbarDelegate_Factory implements Factory<TaskbarDelegate> {
    public final Provider<Context> contextProvider;
    public final Provider<EdgeBackGestureHandler.Factory> edgeBackGestureHandlerFactoryProvider;
    public final Provider<LightBarTransitionsController.Factory> lightBarTransitionsControllerFactoryProvider;

    public TaskbarDelegate_Factory(Provider<Context> provider, Provider<EdgeBackGestureHandler.Factory> provider2, Provider<LightBarTransitionsController.Factory> provider3) {
        this.contextProvider = provider;
        this.edgeBackGestureHandlerFactoryProvider = provider2;
        this.lightBarTransitionsControllerFactoryProvider = provider3;
    }

    public static TaskbarDelegate_Factory create(Provider<Context> provider, Provider<EdgeBackGestureHandler.Factory> provider2, Provider<LightBarTransitionsController.Factory> provider3) {
        return new TaskbarDelegate_Factory(provider, provider2, provider3);
    }

    public static TaskbarDelegate newInstance(Context context, EdgeBackGestureHandler.Factory factory, LightBarTransitionsController.Factory factory2) {
        return new TaskbarDelegate(context, factory, factory2);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TaskbarDelegate m3452get() {
        return newInstance((Context) this.contextProvider.get(), (EdgeBackGestureHandler.Factory) this.edgeBackGestureHandlerFactoryProvider.get(), (LightBarTransitionsController.Factory) this.lightBarTransitionsControllerFactoryProvider.get());
    }
}