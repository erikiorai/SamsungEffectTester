package com.android.systemui.mediaprojection.appselector.view;

import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import java.util.List;
import javax.inject.Provider;

/* renamed from: com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter_Factory  reason: case insensitive filesystem */
/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTasksAdapter_Factory.class */
public final class C0054RecentTasksAdapter_Factory {
    public final Provider<RecentTaskViewHolder.Factory> viewHolderFactoryProvider;

    public C0054RecentTasksAdapter_Factory(Provider<RecentTaskViewHolder.Factory> provider) {
        this.viewHolderFactoryProvider = provider;
    }

    public static C0054RecentTasksAdapter_Factory create(Provider<RecentTaskViewHolder.Factory> provider) {
        return new C0054RecentTasksAdapter_Factory(provider);
    }

    public static RecentTasksAdapter newInstance(List<RecentTask> list, RecentTasksAdapter.RecentTaskClickListener recentTaskClickListener, RecentTaskViewHolder.Factory factory) {
        return new RecentTasksAdapter(list, recentTaskClickListener, factory);
    }

    public RecentTasksAdapter get(List<RecentTask> list, RecentTasksAdapter.RecentTaskClickListener recentTaskClickListener) {
        return newInstance(list, recentTaskClickListener, (RecentTaskViewHolder.Factory) this.viewHolderFactoryProvider.get());
    }
}