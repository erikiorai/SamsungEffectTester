package com.android.systemui.controls.ui;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import com.android.systemui.R$dimen;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.wm.shell.TaskView;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/PanelTaskViewController.class */
public final class PanelTaskViewController {
    public final Context activityContext;
    public int detailTaskId = -1;
    public final Intent fillInIntent;
    public final Function0<Unit> hide;
    public final PendingIntent pendingIntent;
    public final PanelTaskViewController$stateCallback$1 stateCallback;
    public final TaskView taskView;
    public final Executor uiExecutor;

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.controls.ui.PanelTaskViewController$stateCallback$1] */
    public PanelTaskViewController(Context context, Executor executor, PendingIntent pendingIntent, TaskView taskView, Function0<Unit> function0) {
        this.activityContext = context;
        this.uiExecutor = executor;
        this.pendingIntent = pendingIntent;
        this.taskView = taskView;
        this.hide = function0;
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.addFlags(134217728);
        this.fillInIntent = intent;
        this.stateCallback = new TaskView.Listener() { // from class: com.android.systemui.controls.ui.PanelTaskViewController$stateCallback$1
            public void onBackPressedOnTaskRoot(int i) {
                Function0 function02;
                PanelTaskViewController.this.dismiss();
                function02 = PanelTaskViewController.this.hide;
                function02.invoke();
            }

            public void onInitialized() {
                Context context2;
                TaskView taskView2;
                context2 = PanelTaskViewController.this.activityContext;
                final ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(context2, 0, 0);
                makeCustomAnimation.setTaskAlwaysOnTop(true);
                taskView2 = PanelTaskViewController.this.taskView;
                final PanelTaskViewController panelTaskViewController = PanelTaskViewController.this;
                taskView2.post(new Runnable() { // from class: com.android.systemui.controls.ui.PanelTaskViewController$stateCallback$1$onInitialized$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Context context3;
                        TaskView taskView3;
                        TaskView taskView4;
                        TaskView taskView5;
                        PendingIntent pendingIntent2;
                        Intent intent2;
                        TaskView taskView6;
                        context3 = PanelTaskViewController.this.activityContext;
                        int dimensionPixelSize = context3.getResources().getDimensionPixelSize(R$dimen.notification_corner_radius);
                        float[] fArr = new float[8];
                        for (int i = 0; i < 8; i++) {
                            fArr[i] = dimensionPixelSize;
                        }
                        taskView3 = PanelTaskViewController.this.taskView;
                        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
                        shapeDrawable.setTint(0);
                        taskView3.setBackground(shapeDrawable);
                        taskView4 = PanelTaskViewController.this.taskView;
                        taskView4.setClipToOutline(true);
                        taskView5 = PanelTaskViewController.this.taskView;
                        pendingIntent2 = PanelTaskViewController.this.pendingIntent;
                        intent2 = PanelTaskViewController.this.fillInIntent;
                        ActivityOptions activityOptions = makeCustomAnimation;
                        taskView6 = PanelTaskViewController.this.taskView;
                        taskView5.startActivity(pendingIntent2, intent2, activityOptions, ConvenienceExtensionsKt.getBoundsOnScreen(taskView6));
                    }
                });
            }

            public void onReleased() {
                PanelTaskViewController.this.removeDetailTask();
            }

            public void onTaskCreated(int i, ComponentName componentName) {
                PanelTaskViewController.this.detailTaskId = i;
            }

            public void onTaskRemovalStarted(int i) {
                PanelTaskViewController.this.detailTaskId = -1;
                PanelTaskViewController.this.dismiss();
            }
        };
    }

    public final void dismiss() {
        this.taskView.release();
    }

    public final void launchTaskView() {
        this.taskView.setListener(this.uiExecutor, this.stateCallback);
    }

    public final void removeDetailTask() {
        if (this.detailTaskId == -1) {
            return;
        }
        ActivityTaskManager.getInstance().removeTask(this.detailTaskId);
        this.detailTaskId = -1;
    }
}