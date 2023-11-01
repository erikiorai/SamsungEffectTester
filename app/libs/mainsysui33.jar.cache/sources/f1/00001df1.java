package com.android.systemui.mediaprojection.appselector.view;

import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import dagger.internal.InstanceFactory;
import java.util.List;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTasksAdapter_Factory_Impl.class */
public final class RecentTasksAdapter_Factory_Impl implements RecentTasksAdapter.Factory {
    public final C0054RecentTasksAdapter_Factory delegateFactory;

    public RecentTasksAdapter_Factory_Impl(C0054RecentTasksAdapter_Factory c0054RecentTasksAdapter_Factory) {
        this.delegateFactory = c0054RecentTasksAdapter_Factory;
    }

    public static Provider<RecentTasksAdapter.Factory> create(C0054RecentTasksAdapter_Factory c0054RecentTasksAdapter_Factory) {
        return InstanceFactory.create(new RecentTasksAdapter_Factory_Impl(c0054RecentTasksAdapter_Factory));
    }

    @Override // com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter.Factory
    public RecentTasksAdapter create(List<RecentTask> list, RecentTasksAdapter.RecentTaskClickListener recentTaskClickListener) {
        return this.delegateFactory.get(list, recentTaskClickListener);
    }
}