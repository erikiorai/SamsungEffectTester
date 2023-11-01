package com.android.systemui.mediaprojection.appselector.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$layout;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTasksAdapter.class */
public final class RecentTasksAdapter extends RecyclerView.Adapter<RecentTaskViewHolder> {
    public final List<RecentTask> items;
    public final RecentTaskClickListener listener;
    public final RecentTaskViewHolder.Factory viewHolderFactory;

    /* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTasksAdapter$Factory.class */
    public interface Factory {
        RecentTasksAdapter create(List<RecentTask> list, RecentTaskClickListener recentTaskClickListener);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTasksAdapter$RecentTaskClickListener.class */
    public interface RecentTaskClickListener {
        void onRecentAppClicked(RecentTask recentTask, View view);
    }

    public RecentTasksAdapter(List<RecentTask> list, RecentTaskClickListener recentTaskClickListener, RecentTaskViewHolder.Factory factory) {
        this.items = list;
        this.listener = recentTaskClickListener;
        this.viewHolderFactory = factory;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final RecentTaskViewHolder recentTaskViewHolder, int i) {
        final RecentTask recentTask = this.items.get(i);
        recentTaskViewHolder.bind(recentTask, new Function1<View, Unit>() { // from class: com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter$onBindViewHolder$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((View) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(View view) {
                RecentTasksAdapter.RecentTaskClickListener recentTaskClickListener;
                recentTaskClickListener = RecentTasksAdapter.this.listener;
                recentTaskClickListener.onRecentAppClicked(recentTask, recentTaskViewHolder.itemView);
            }
        });
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecentTaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.viewHolderFactory.create((ViewGroup) LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.media_projection_task_item, viewGroup, false));
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(RecentTaskViewHolder recentTaskViewHolder) {
        recentTaskViewHolder.onRecycled();
    }
}