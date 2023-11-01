package com.android.systemui.mediaprojection.appselector.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/TaskPreviewSizeProvider.class */
public final class TaskPreviewSizeProvider implements CallbackController<TaskPreviewSizeListener>, ConfigurationController.ConfigurationListener {
    public final Context context;
    public final WindowManager windowManager;
    public final Rect size = calculateSize();
    public final ArrayList<TaskPreviewSizeListener> listeners = new ArrayList<>();

    /* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/TaskPreviewSizeProvider$TaskPreviewSizeListener.class */
    public interface TaskPreviewSizeListener {
        void onTaskSizeChanged(Rect rect);
    }

    public TaskPreviewSizeProvider(Context context, WindowManager windowManager, ConfigurationController configurationController) {
        this.context = context;
        this.windowManager = windowManager;
        configurationController.addCallback(this);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(TaskPreviewSizeListener taskPreviewSizeListener) {
        this.listeners.add(taskPreviewSizeListener);
    }

    public final Rect calculateSize() {
        WindowMetrics maximumWindowMetrics = this.windowManager.getMaximumWindowMetrics();
        int height = maximumWindowMetrics.getBounds().height();
        int width = maximumWindowMetrics.getBounds().width();
        int dimensionPixelSize = Utilities.isTablet(this.context) ? height - this.context.getResources().getDimensionPixelSize(17105567) : height;
        Rect rect = new Rect(0, 0, width, dimensionPixelSize);
        rect.scale((dimensionPixelSize / height) / 4.0f);
        return rect;
    }

    public final Rect getSize() {
        return this.size;
    }

    public void onConfigChanged(Configuration configuration) {
        Rect calculateSize = calculateSize();
        if (Intrinsics.areEqual(calculateSize, this.size)) {
            return;
        }
        this.size.set(calculateSize);
        for (TaskPreviewSizeListener taskPreviewSizeListener : this.listeners) {
            taskPreviewSizeListener.onTaskSizeChanged(this.size);
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void removeCallback(TaskPreviewSizeListener taskPreviewSizeListener) {
        this.listeners.remove(taskPreviewSizeListener);
    }
}