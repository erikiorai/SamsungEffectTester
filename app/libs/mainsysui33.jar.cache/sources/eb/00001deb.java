package com.android.systemui.mediaprojection.appselector.view;

import android.view.ViewGroup;
import com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTaskViewHolder_Factory_Impl.class */
public final class RecentTaskViewHolder_Factory_Impl implements RecentTaskViewHolder.Factory {
    public final C0053RecentTaskViewHolder_Factory delegateFactory;

    public RecentTaskViewHolder_Factory_Impl(C0053RecentTaskViewHolder_Factory c0053RecentTaskViewHolder_Factory) {
        this.delegateFactory = c0053RecentTaskViewHolder_Factory;
    }

    public static Provider<RecentTaskViewHolder.Factory> create(C0053RecentTaskViewHolder_Factory c0053RecentTaskViewHolder_Factory) {
        return InstanceFactory.create(new RecentTaskViewHolder_Factory_Impl(c0053RecentTaskViewHolder_Factory));
    }

    @Override // com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder.Factory
    public RecentTaskViewHolder create(ViewGroup viewGroup) {
        return this.delegateFactory.get(viewGroup);
    }
}