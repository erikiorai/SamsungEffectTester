package com.android.systemui.mediaprojection.appselector.view;

import android.app.ActivityOptions;
import android.app.IActivityTaskManager;
import android.graphics.Rect;
import android.os.Binder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorResultHandler;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider;
import com.android.systemui.util.recycler.HorizontalSpacerItemDecoration;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/MediaProjectionRecentsViewController.class */
public final class MediaProjectionRecentsViewController implements RecentTasksAdapter.RecentTaskClickListener, TaskPreviewSizeProvider.TaskPreviewSizeListener {
    public final IActivityTaskManager activityTaskManager;
    public List<RecentTask> lastBoundData;
    public final RecentTasksAdapter.Factory recentTasksAdapterFactory;
    public final MediaProjectionAppSelectorResultHandler resultHandler;
    public final TaskPreviewSizeProvider taskViewSizeProvider;
    public Views views;

    /* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/MediaProjectionRecentsViewController$Views.class */
    public static final class Views {
        public final View progress;
        public final View recentsContainer;
        public final RecyclerView recycler;
        public final ViewGroup root;

        public Views(ViewGroup viewGroup, View view, View view2, RecyclerView recyclerView) {
            this.root = viewGroup;
            this.recentsContainer = view;
            this.progress = view2;
            this.recycler = recyclerView;
        }

        public final View getProgress() {
            return this.progress;
        }

        public final View getRecentsContainer() {
            return this.recentsContainer;
        }

        public final RecyclerView getRecycler() {
            return this.recycler;
        }

        public final ViewGroup getRoot() {
            return this.root;
        }
    }

    public MediaProjectionRecentsViewController(RecentTasksAdapter.Factory factory, TaskPreviewSizeProvider taskPreviewSizeProvider, IActivityTaskManager iActivityTaskManager, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler) {
        this.recentTasksAdapterFactory = factory;
        this.taskViewSizeProvider = taskPreviewSizeProvider;
        this.activityTaskManager = iActivityTaskManager;
        this.resultHandler = mediaProjectionAppSelectorResultHandler;
        taskPreviewSizeProvider.addCallback((TaskPreviewSizeProvider.TaskPreviewSizeListener) this);
    }

    public final void bind(List<RecentTask> list) {
        Views views = this.views;
        if (views != null) {
            if (list.isEmpty()) {
                views.getRoot().setVisibility(8);
                return;
            }
            views.getProgress().setVisibility(8);
            views.getRecycler().setVisibility(0);
            views.getRoot().setVisibility(0);
            views.getRecycler().setAdapter(this.recentTasksAdapterFactory.create(list, this));
        }
        this.lastBoundData = list;
    }

    public final Views createRecentViews(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.media_projection_recent_tasks, viewGroup, false);
        View findViewById = viewGroup2.findViewById(R$id.media_projection_recent_tasks_container);
        setTaskHeightSize(findViewById);
        View requireViewById = viewGroup2.requireViewById(R$id.media_projection_recent_tasks_loader);
        RecyclerView recyclerView = (RecyclerView) viewGroup2.requireViewById(R$id.media_projection_recent_tasks_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext(), 0, false));
        recyclerView.addItemDecoration(new HorizontalSpacerItemDecoration(viewGroup.getResources().getDimensionPixelOffset(R$dimen.media_projection_app_selector_recents_padding)));
        return new Views(viewGroup2, findViewById, requireViewById, recyclerView);
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0011, code lost:
        if (r0 == null) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ViewGroup createView(ViewGroup viewGroup) {
        ViewGroup root;
        Views views = this.views;
        if (views != null) {
            ViewGroup root2 = views.getRoot();
            root = root2;
        }
        Views createRecentViews = createRecentViews(viewGroup);
        this.views = createRecentViews;
        List<RecentTask> list = this.lastBoundData;
        if (list != null) {
            bind(list);
        }
        root = createRecentViews.getRoot();
        return root;
    }

    @Override // com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter.RecentTaskClickListener
    public void onRecentAppClicked(RecentTask recentTask, View view) {
        Binder binder = new Binder();
        ActivityOptions makeScaleUpAnimation = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
        makeScaleUpAnimation.setLaunchCookie(binder);
        this.activityTaskManager.startActivityFromRecents(recentTask.getTaskId(), makeScaleUpAnimation.toBundle());
        this.resultHandler.returnSelectedApp(binder);
    }

    @Override // com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider.TaskPreviewSizeListener
    public void onTaskSizeChanged(Rect rect) {
        View recentsContainer;
        Views views = this.views;
        if (views == null || (recentsContainer = views.getRecentsContainer()) == null) {
            return;
        }
        setTaskHeightSize(recentsContainer);
    }

    public final void setTaskHeightSize(View view) {
        int height = this.taskViewSizeProvider.getSize().height();
        int dimensionPixelSize = view.getContext().getResources().getDimensionPixelSize(R$dimen.media_projection_app_selector_task_icon_size);
        int dimensionPixelSize2 = view.getContext().getResources().getDimensionPixelSize(R$dimen.media_projection_app_selector_task_icon_margin);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height + dimensionPixelSize + (dimensionPixelSize2 * 2);
        view.setLayoutParams(layoutParams);
    }
}