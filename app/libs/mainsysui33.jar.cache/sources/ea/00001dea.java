package com.android.systemui.mediaprojection.appselector.view;

import android.view.ViewGroup;
import com.android.systemui.mediaprojection.appselector.data.AppIconLoader;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskThumbnailLoader;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* renamed from: com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder_Factory  reason: case insensitive filesystem */
/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTaskViewHolder_Factory.class */
public final class C0053RecentTaskViewHolder_Factory {
    public final Provider<AppIconLoader> iconLoaderProvider;
    public final Provider<CoroutineScope> scopeProvider;
    public final Provider<TaskPreviewSizeProvider> taskViewSizeProvider;
    public final Provider<RecentTaskThumbnailLoader> thumbnailLoaderProvider;

    public C0053RecentTaskViewHolder_Factory(Provider<AppIconLoader> provider, Provider<RecentTaskThumbnailLoader> provider2, Provider<TaskPreviewSizeProvider> provider3, Provider<CoroutineScope> provider4) {
        this.iconLoaderProvider = provider;
        this.thumbnailLoaderProvider = provider2;
        this.taskViewSizeProvider = provider3;
        this.scopeProvider = provider4;
    }

    public static C0053RecentTaskViewHolder_Factory create(Provider<AppIconLoader> provider, Provider<RecentTaskThumbnailLoader> provider2, Provider<TaskPreviewSizeProvider> provider3, Provider<CoroutineScope> provider4) {
        return new C0053RecentTaskViewHolder_Factory(provider, provider2, provider3, provider4);
    }

    public static RecentTaskViewHolder newInstance(ViewGroup viewGroup, AppIconLoader appIconLoader, RecentTaskThumbnailLoader recentTaskThumbnailLoader, TaskPreviewSizeProvider taskPreviewSizeProvider, CoroutineScope coroutineScope) {
        return new RecentTaskViewHolder(viewGroup, appIconLoader, recentTaskThumbnailLoader, taskPreviewSizeProvider, coroutineScope);
    }

    public RecentTaskViewHolder get(ViewGroup viewGroup) {
        return newInstance(viewGroup, (AppIconLoader) this.iconLoaderProvider.get(), (RecentTaskThumbnailLoader) this.thumbnailLoaderProvider.get(), (TaskPreviewSizeProvider) this.taskViewSizeProvider.get(), (CoroutineScope) this.scopeProvider.get());
    }
}