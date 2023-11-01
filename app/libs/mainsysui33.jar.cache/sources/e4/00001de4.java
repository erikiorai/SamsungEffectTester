package com.android.systemui.mediaprojection.appselector.view;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$id;
import com.android.systemui.mediaprojection.appselector.data.AppIconLoader;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskThumbnailLoader;
import com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTaskViewHolder.class */
public final class RecentTaskViewHolder extends RecyclerView.ViewHolder implements ConfigurationController.ConfigurationListener, TaskPreviewSizeProvider.TaskPreviewSizeListener {
    public final AppIconLoader iconLoader;
    public final ImageView iconView;
    public Job job;
    public final ViewGroup root;
    public final CoroutineScope scope;
    public final TaskPreviewSizeProvider taskViewSizeProvider;
    public final RecentTaskThumbnailLoader thumbnailLoader;
    public final MediaProjectionTaskView thumbnailView;

    /* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTaskViewHolder$Factory.class */
    public interface Factory {
        RecentTaskViewHolder create(ViewGroup viewGroup);
    }

    public RecentTaskViewHolder(ViewGroup viewGroup, AppIconLoader appIconLoader, RecentTaskThumbnailLoader recentTaskThumbnailLoader, TaskPreviewSizeProvider taskPreviewSizeProvider, CoroutineScope coroutineScope) {
        super(viewGroup);
        this.root = viewGroup;
        this.iconLoader = appIconLoader;
        this.thumbnailLoader = recentTaskThumbnailLoader;
        this.taskViewSizeProvider = taskPreviewSizeProvider;
        this.scope = coroutineScope;
        this.thumbnailView = (MediaProjectionTaskView) viewGroup.requireViewById(R$id.task_thumbnail);
        this.iconView = (ImageView) viewGroup.requireViewById(R$id.task_icon);
        updateThumbnailSize();
    }

    public final void bind(RecentTask recentTask, final Function1<? super View, Unit> function1) {
        this.taskViewSizeProvider.addCallback((TaskPreviewSizeProvider.TaskPreviewSizeListener) this);
        Job job = this.job;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.job = BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new RecentTaskViewHolder$bind$1(recentTask, this, null), 3, (Object) null);
        this.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder$sam$android_view_View_OnClickListener$0
            @Override // android.view.View.OnClickListener
            public final /* synthetic */ void onClick(View view) {
                function1.invoke(view);
            }
        });
    }

    public final MediaProjectionTaskView getThumbnailView() {
        return this.thumbnailView;
    }

    public final void onRecycled() {
        this.taskViewSizeProvider.removeCallback((TaskPreviewSizeProvider.TaskPreviewSizeListener) this);
        this.iconView.setImageDrawable(null);
        this.thumbnailView.bindTask(null, null);
        Job job = this.job;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.job = null;
    }

    @Override // com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider.TaskPreviewSizeListener
    public void onTaskSizeChanged(Rect rect) {
        updateThumbnailSize();
    }

    public final void updateThumbnailSize() {
        MediaProjectionTaskView mediaProjectionTaskView = this.thumbnailView;
        ViewGroup.LayoutParams layoutParams = mediaProjectionTaskView.getLayoutParams();
        layoutParams.width = this.taskViewSizeProvider.getSize().width();
        layoutParams.height = this.taskViewSizeProvider.getSize().height();
        mediaProjectionTaskView.setLayoutParams(layoutParams);
    }
}