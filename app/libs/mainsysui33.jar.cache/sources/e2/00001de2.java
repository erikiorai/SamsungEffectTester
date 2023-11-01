package com.android.systemui.mediaprojection.appselector.view;

import android.app.IActivityTaskManager;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorResultHandler;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/MediaProjectionRecentsViewController_Factory.class */
public final class MediaProjectionRecentsViewController_Factory implements Factory<MediaProjectionRecentsViewController> {
    public final Provider<IActivityTaskManager> activityTaskManagerProvider;
    public final Provider<RecentTasksAdapter.Factory> recentTasksAdapterFactoryProvider;
    public final Provider<MediaProjectionAppSelectorResultHandler> resultHandlerProvider;
    public final Provider<TaskPreviewSizeProvider> taskViewSizeProvider;

    public MediaProjectionRecentsViewController_Factory(Provider<RecentTasksAdapter.Factory> provider, Provider<TaskPreviewSizeProvider> provider2, Provider<IActivityTaskManager> provider3, Provider<MediaProjectionAppSelectorResultHandler> provider4) {
        this.recentTasksAdapterFactoryProvider = provider;
        this.taskViewSizeProvider = provider2;
        this.activityTaskManagerProvider = provider3;
        this.resultHandlerProvider = provider4;
    }

    public static MediaProjectionRecentsViewController_Factory create(Provider<RecentTasksAdapter.Factory> provider, Provider<TaskPreviewSizeProvider> provider2, Provider<IActivityTaskManager> provider3, Provider<MediaProjectionAppSelectorResultHandler> provider4) {
        return new MediaProjectionRecentsViewController_Factory(provider, provider2, provider3, provider4);
    }

    public static MediaProjectionRecentsViewController newInstance(RecentTasksAdapter.Factory factory, TaskPreviewSizeProvider taskPreviewSizeProvider, IActivityTaskManager iActivityTaskManager, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler) {
        return new MediaProjectionRecentsViewController(factory, taskPreviewSizeProvider, iActivityTaskManager, mediaProjectionAppSelectorResultHandler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaProjectionRecentsViewController m3376get() {
        return newInstance((RecentTasksAdapter.Factory) this.recentTasksAdapterFactoryProvider.get(), (TaskPreviewSizeProvider) this.taskViewSizeProvider.get(), (IActivityTaskManager) this.activityTaskManagerProvider.get(), (MediaProjectionAppSelectorResultHandler) this.resultHandlerProvider.get());
    }
}