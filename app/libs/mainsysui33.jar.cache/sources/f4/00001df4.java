package com.android.systemui.mediaprojection.appselector.view;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/TaskPreviewSizeProvider_Factory.class */
public final class TaskPreviewSizeProvider_Factory implements Factory<TaskPreviewSizeProvider> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public TaskPreviewSizeProvider_Factory(Provider<Context> provider, Provider<WindowManager> provider2, Provider<ConfigurationController> provider3) {
        this.contextProvider = provider;
        this.windowManagerProvider = provider2;
        this.configurationControllerProvider = provider3;
    }

    public static TaskPreviewSizeProvider_Factory create(Provider<Context> provider, Provider<WindowManager> provider2, Provider<ConfigurationController> provider3) {
        return new TaskPreviewSizeProvider_Factory(provider, provider2, provider3);
    }

    public static TaskPreviewSizeProvider newInstance(Context context, WindowManager windowManager, ConfigurationController configurationController) {
        return new TaskPreviewSizeProvider(context, windowManager, configurationController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TaskPreviewSizeProvider m3377get() {
        return newInstance((Context) this.contextProvider.get(), (WindowManager) this.windowManagerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get());
    }
}