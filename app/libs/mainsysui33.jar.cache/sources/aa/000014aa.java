package com.android.systemui.controls.ui;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$style;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.wm.shell.TaskView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/DetailDialog.class */
public final class DetailDialog extends Dialog {
    public static final Companion Companion = new Companion(null);
    public final Context activityContext;
    public final ActivityStarter activityStarter;
    public final BroadcastSender broadcastSender;
    public final ControlViewHolder cvh;
    public int detailTaskId;
    public final Intent fillInIntent;
    public final KeyguardStateController keyguardStateController;
    public final PendingIntent pendingIntent;
    public final TaskView.Listener stateCallback;
    public final TaskView taskView;
    public View taskViewContainer;
    public final float taskWidthPercentWidth;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/DetailDialog$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DetailDialog(Context context, BroadcastSender broadcastSender, TaskView taskView, PendingIntent pendingIntent, ControlViewHolder controlViewHolder, KeyguardStateController keyguardStateController, ActivityStarter activityStarter) {
        super(context, R$style.Theme_SystemUI_Dialog_Control_DetailPanel);
        this.activityContext = context;
        this.broadcastSender = broadcastSender;
        this.taskView = taskView;
        this.pendingIntent = pendingIntent;
        this.cvh = controlViewHolder;
        this.keyguardStateController = keyguardStateController;
        this.activityStarter = activityStarter;
        this.detailTaskId = -1;
        this.taskWidthPercentWidth = context.getResources().getFloat(R$dimen.controls_task_view_width_percentage);
        Intent intent = new Intent();
        intent.putExtra("controls.DISPLAY_IN_PANEL", true);
        intent.addFlags(524288);
        intent.addFlags(134217728);
        this.fillInIntent = intent;
        TaskView.Listener listener = new TaskView.Listener() { // from class: com.android.systemui.controls.ui.DetailDialog$stateCallback$1
            public void onBackPressedOnTaskRoot(int i) {
                DetailDialog.this.dismiss();
            }

            public void onInitialized() {
                View access$getTaskViewContainer$p = DetailDialog.access$getTaskViewContainer$p(DetailDialog.this);
                View view = access$getTaskViewContainer$p;
                if (access$getTaskViewContainer$p == null) {
                    view = null;
                }
                DetailDialog detailDialog = DetailDialog.this;
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = (int) (view.getWidth() * DetailDialog.access$getTaskWidthPercentWidth$p(detailDialog));
                view.setLayoutParams(layoutParams);
                DetailDialog.this.getTaskView().startActivity(DetailDialog.this.getPendingIntent(), DetailDialog.access$getFillInIntent$p(DetailDialog.this), ActivityOptions.makeCustomAnimation(DetailDialog.this.getActivityContext(), 0, 0), DetailDialog.this.getTaskViewBounds());
            }

            public void onReleased() {
                DetailDialog.this.removeDetailTask();
            }

            public void onTaskCreated(int i, ComponentName componentName) {
                DetailDialog.this.setDetailTaskId(i);
                ((ViewGroup) DetailDialog.this.requireViewById(R$id.controls_activity_view)).setAlpha(1.0f);
            }

            public void onTaskRemovalStarted(int i) {
                DetailDialog.this.setDetailTaskId(-1);
                DetailDialog.this.dismiss();
            }
        };
        this.stateCallback = listener;
        getWindow().addFlags(32);
        getWindow().addPrivateFlags(536870912);
        setContentView(R$layout.controls_detail_dialog);
        this.taskViewContainer = requireViewById(R$id.control_task_view_container);
        ViewGroup viewGroup = (ViewGroup) requireViewById(R$id.controls_activity_view);
        viewGroup.addView(taskView);
        viewGroup.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        ((ImageView) requireViewById(R$id.control_detail_close)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$2$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog.this.dismiss();
            }
        });
        requireViewById(R$id.control_detail_root).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$3$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog.this.dismiss();
            }
        });
        ((ImageView) requireViewById(R$id.control_detail_open_in_app)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DetailDialog$4$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailDialog.this.removeDetailTask();
                DetailDialog.this.dismiss();
                final DetailDialog detailDialog = DetailDialog.this;
                ActivityStarter.OnDismissAction onDismissAction = new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.DetailDialog$4$1$action$1
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        DetailDialog.this.getBroadcastSender().closeSystemDialogs();
                        DetailDialog.this.getPendingIntent().send();
                        return false;
                    }
                };
                if (DetailDialog.this.getKeyguardStateController().isUnlocked()) {
                    onDismissAction.onDismiss();
                } else {
                    DetailDialog.this.getActivityStarter().dismissKeyguardThenExecute(onDismissAction, null, true);
                }
            }
        });
        getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.controls.ui.DetailDialog.5
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                int paddingLeft = view.getPaddingLeft();
                int paddingRight = view.getPaddingRight();
                Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
                view.setPadding(paddingLeft, insets.top, paddingRight, insets.bottom);
                return WindowInsets.CONSUMED;
            }
        });
        if (ScreenDecorationsUtils.supportsRoundedCornersOnWindows(getContext().getResources())) {
            taskView.setCornerRadius(getContext().getResources().getDimensionPixelSize(R$dimen.controls_activity_view_corner_radius));
        }
        taskView.setListener(controlViewHolder.getUiExecutor(), listener);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.DetailDialog$stateCallback$1.onInitialized():void] */
    public static final /* synthetic */ Intent access$getFillInIntent$p(DetailDialog detailDialog) {
        return detailDialog.fillInIntent;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.DetailDialog$stateCallback$1.onInitialized():void] */
    public static final /* synthetic */ View access$getTaskViewContainer$p(DetailDialog detailDialog) {
        return detailDialog.taskViewContainer;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.DetailDialog$stateCallback$1.onInitialized():void] */
    public static final /* synthetic */ float access$getTaskWidthPercentWidth$p(DetailDialog detailDialog) {
        return detailDialog.taskWidthPercentWidth;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (isShowing()) {
            this.taskView.release();
            super.dismiss();
        }
    }

    public final Context getActivityContext() {
        return this.activityContext;
    }

    public final ActivityStarter getActivityStarter() {
        return this.activityStarter;
    }

    public final BroadcastSender getBroadcastSender() {
        return this.broadcastSender;
    }

    public final KeyguardStateController getKeyguardStateController() {
        return this.keyguardStateController;
    }

    public final PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    public final TaskView getTaskView() {
        return this.taskView;
    }

    public final Rect getTaskViewBounds() {
        WindowMetrics currentWindowMetrics = ((WindowManager) getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics();
        Rect bounds = currentWindowMetrics.getBounds();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        return new Rect(bounds.left - insetsIgnoringVisibility.left, bounds.top + insetsIgnoringVisibility.top + getContext().getResources().getDimensionPixelSize(R$dimen.controls_detail_dialog_header_height), bounds.right - insetsIgnoringVisibility.right, bounds.bottom - insetsIgnoringVisibility.bottom);
    }

    public final void removeDetailTask() {
        if (this.detailTaskId == -1) {
            return;
        }
        ActivityTaskManager.getInstance().removeTask(this.detailTaskId);
        this.detailTaskId = -1;
    }

    public final void setDetailTaskId(int i) {
        this.detailTaskId = i;
    }
}