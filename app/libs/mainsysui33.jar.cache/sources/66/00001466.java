package com.android.systemui.controls.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ResolveInfo;
import android.os.VibrationEffect;
import android.service.controls.Control;
import android.service.controls.actions.BooleanAction;
import android.service.controls.actions.CommandAction;
import android.service.controls.actions.FloatAction;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.settings.ControlsSettingsDialogManager;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.ui.ControlActionCoordinatorImpl;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.TaskView;
import com.android.wm.shell.TaskViewFactory;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlActionCoordinatorImpl.class */
public final class ControlActionCoordinatorImpl implements ControlActionCoordinator {
    public static final Companion Companion = new Companion(null);
    public Set<String> actionsInProgress = new LinkedHashSet();
    public Context activityContext;
    public final ActivityStarter activityStarter;
    public final DelayableExecutor bgExecutor;
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final ControlsSettingsDialogManager controlsSettingsDialogManager;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public Dialog dialog;
    public final FeatureFlags featureFlags;
    public final KeyguardStateController keyguardStateController;
    public final Optional<TaskViewFactory> taskViewFactory;
    public final DelayableExecutor uiExecutor;
    public final VibratorHelper vibrator;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlActionCoordinatorImpl$Action.class */
    public final class Action {
        public final boolean authIsRequired;
        public final boolean blockable;
        public final String controlId;
        public final Function0<Unit> f;

        public Action(String str, Function0<Unit> function0, boolean z, boolean z2) {
            ControlActionCoordinatorImpl.this = r4;
            this.controlId = str;
            this.f = function0;
            this.blockable = z;
            this.authIsRequired = z2;
        }

        public final boolean getAuthIsRequired() {
            return this.authIsRequired;
        }

        public final void invoke() {
            if (!this.blockable || ControlActionCoordinatorImpl.this.shouldRunAction(this.controlId)) {
                this.f.invoke();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlActionCoordinatorImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlActionCoordinatorImpl(Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ActivityStarter activityStarter, BroadcastSender broadcastSender, KeyguardStateController keyguardStateController, Optional<TaskViewFactory> optional, ControlsMetricsLogger controlsMetricsLogger, VibratorHelper vibratorHelper, ControlsSettingsRepository controlsSettingsRepository, ControlsSettingsDialogManager controlsSettingsDialogManager, FeatureFlags featureFlags) {
        this.context = context;
        this.bgExecutor = delayableExecutor;
        this.uiExecutor = delayableExecutor2;
        this.activityStarter = activityStarter;
        this.broadcastSender = broadcastSender;
        this.keyguardStateController = keyguardStateController;
        this.taskViewFactory = optional;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.vibrator = vibratorHelper;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.controlsSettingsDialogManager = controlsSettingsDialogManager;
        this.featureFlags = featureFlags;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlActionCoordinatorImpl$shouldRunAction$1.run():void] */
    public static final /* synthetic */ Set access$getActionsInProgress$p(ControlActionCoordinatorImpl controlActionCoordinatorImpl) {
        return controlActionCoordinatorImpl.actionsInProgress;
    }

    @VisibleForTesting
    public final void bouncerOrRun(final Action action) {
        boolean z = action.getAuthIsRequired() || !getAllowTrivialControls();
        if (this.keyguardStateController.isShowing() && z) {
            this.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$bouncerOrRun$1
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    Log.d("ControlsUiController", "Device unlocked, invoking controls action");
                    ControlActionCoordinatorImpl.Action.this.invoke();
                    return true;
                }
            }, null, true);
            return;
        }
        showSettingsDialogIfNeeded(action);
        action.invoke();
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void closeDialogs() {
        Boolean bool;
        if (!this.featureFlags.isEnabled(Flags.USE_APP_PANELS)) {
            this.controlsSettingsDialogManager.closeDialog();
        }
        Context activityContext = getActivityContext();
        Activity activity = activityContext instanceof Activity ? (Activity) activityContext : null;
        if (activity != null) {
            bool = Boolean.valueOf(activity.isFinishing() || activity.isDestroyed());
        } else {
            bool = null;
        }
        if (Intrinsics.areEqual(bool, Boolean.TRUE)) {
            this.dialog = null;
            return;
        }
        Dialog dialog = this.dialog;
        boolean z = false;
        if (dialog != null) {
            z = false;
            if (dialog.isShowing()) {
                z = true;
            }
        }
        if (z) {
            Dialog dialog2 = this.dialog;
            if (dialog2 != null) {
                dialog2.dismiss();
            }
            this.dialog = null;
        }
    }

    @VisibleForTesting
    public final Action createAction(String str, Function0<Unit> function0, boolean z, boolean z2) {
        return new Action(str, function0, z, z2);
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void drag(boolean z) {
        if (z) {
            vibrate(Vibrations.INSTANCE.getRangeEdgeEffect());
        } else {
            vibrate(Vibrations.INSTANCE.getRangeMiddleEffect());
        }
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void enableActionOnTouch(String str) {
        this.actionsInProgress.remove(str);
    }

    public Context getActivityContext() {
        Context context = this.activityContext;
        if (context != null) {
            return context;
        }
        return null;
    }

    public final boolean getAllowTrivialControls() {
        return ((Boolean) this.controlsSettingsRepository.getAllowActionOnTrivialControlsInLockscreen().getValue()).booleanValue();
    }

    public final boolean isLocked() {
        return !this.keyguardStateController.isUnlocked();
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void longPress(final ControlViewHolder controlViewHolder) {
        this.controlsMetricsLogger.longPress(controlViewHolder, isLocked());
        String controlId = controlViewHolder.getCws().getCi().getControlId();
        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$longPress$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1849invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1849invoke() {
                Control control = ControlViewHolder.this.getCws().getControl();
                if (control != null) {
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = this;
                    controlViewHolder2.getLayout().performHapticFeedback(0);
                    controlActionCoordinatorImpl.showDetail(controlViewHolder2, control.getAppIntent());
                }
            }
        };
        Control control = controlViewHolder.getCws().getControl();
        bouncerOrRun(createAction(controlId, function0, false, control != null ? control.isAuthRequired() : true));
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void setActivityContext(Context context) {
        this.activityContext = context;
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void setValue(final ControlViewHolder controlViewHolder, final String str, final float f) {
        this.controlsMetricsLogger.drag(controlViewHolder, isLocked());
        String controlId = controlViewHolder.getCws().getCi().getControlId();
        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$setValue$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1850invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1850invoke() {
                ControlViewHolder.this.action(new FloatAction(str, f));
            }
        };
        Control control = controlViewHolder.getCws().getControl();
        bouncerOrRun(createAction(controlId, function0, false, control != null ? control.isAuthRequired() : true));
    }

    public final boolean shouldRunAction(final String str) {
        boolean z;
        if (this.actionsInProgress.add(str)) {
            this.uiExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$shouldRunAction$1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlActionCoordinatorImpl.access$getActionsInProgress$p(ControlActionCoordinatorImpl.this).remove(str);
                }
            }, 3000L);
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final void showDetail(final ControlViewHolder controlViewHolder, final PendingIntent pendingIntent) {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1
            @Override // java.lang.Runnable
            public final void run() {
                Context context;
                DelayableExecutor delayableExecutor;
                context = ControlActionCoordinatorImpl.this.context;
                final List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(pendingIntent.getIntent(), 65536);
                delayableExecutor = ControlActionCoordinatorImpl.this.uiExecutor;
                final ControlActionCoordinatorImpl controlActionCoordinatorImpl = ControlActionCoordinatorImpl.this;
                final ControlViewHolder controlViewHolder2 = controlViewHolder;
                final PendingIntent pendingIntent2 = pendingIntent;
                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Optional optional;
                        Optional optional2;
                        Context context2;
                        Executor executor;
                        if (!queryIntentActivities.isEmpty()) {
                            optional = controlActionCoordinatorImpl.taskViewFactory;
                            if (optional.isPresent()) {
                                optional2 = controlActionCoordinatorImpl.taskViewFactory;
                                context2 = controlActionCoordinatorImpl.context;
                                executor = controlActionCoordinatorImpl.uiExecutor;
                                final ControlActionCoordinatorImpl controlActionCoordinatorImpl2 = controlActionCoordinatorImpl;
                                final PendingIntent pendingIntent3 = pendingIntent2;
                                final ControlViewHolder controlViewHolder3 = controlViewHolder2;
                                ((TaskViewFactory) optional2.get()).create(context2, executor, new Consumer() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl.showDetail.1.1.1
                                    /* JADX DEBUG: Method merged with bridge method */
                                    @Override // java.util.function.Consumer
                                    public final void accept(TaskView taskView) {
                                        BroadcastSender broadcastSender;
                                        KeyguardStateController keyguardStateController;
                                        ActivityStarter activityStarter;
                                        ControlActionCoordinatorImpl controlActionCoordinatorImpl3 = ControlActionCoordinatorImpl.this;
                                        Context activityContext = ControlActionCoordinatorImpl.this.getActivityContext();
                                        broadcastSender = ControlActionCoordinatorImpl.this.broadcastSender;
                                        PendingIntent pendingIntent4 = pendingIntent3;
                                        ControlViewHolder controlViewHolder4 = controlViewHolder3;
                                        keyguardStateController = ControlActionCoordinatorImpl.this.keyguardStateController;
                                        activityStarter = ControlActionCoordinatorImpl.this.activityStarter;
                                        DetailDialog detailDialog = new DetailDialog(activityContext, broadcastSender, taskView, pendingIntent4, controlViewHolder4, keyguardStateController, activityStarter);
                                        final ControlActionCoordinatorImpl controlActionCoordinatorImpl4 = ControlActionCoordinatorImpl.this;
                                        detailDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1$1$1$1$1
                                            @Override // android.content.DialogInterface.OnDismissListener
                                            public final void onDismiss(DialogInterface dialogInterface) {
                                                ControlActionCoordinatorImpl.this.dialog = null;
                                            }
                                        });
                                        detailDialog.show();
                                        controlActionCoordinatorImpl3.dialog = detailDialog;
                                    }
                                });
                                return;
                            }
                        }
                        controlViewHolder2.setErrorStatus();
                    }
                });
            }
        });
    }

    public final void showSettingsDialogIfNeeded(Action action) {
        if (action.getAuthIsRequired() || this.featureFlags.isEnabled(Flags.USE_APP_PANELS)) {
            return;
        }
        this.controlsSettingsDialogManager.maybeShowDialog(getActivityContext(), new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showSettingsDialogIfNeeded$1
            public /* bridge */ /* synthetic */ Object invoke() {
                m1852invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1852invoke() {
            }
        });
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void toggle(final ControlViewHolder controlViewHolder, final String str, final boolean z) {
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        String controlId = controlViewHolder.getCws().getCi().getControlId();
        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$toggle$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1853invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1853invoke() {
                ControlViewHolder.this.getLayout().performHapticFeedback(6);
                ControlViewHolder.this.action(new BooleanAction(str, !z));
            }
        };
        Control control = controlViewHolder.getCws().getControl();
        bouncerOrRun(createAction(controlId, function0, true, control != null ? control.isAuthRequired() : true));
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void touch(final ControlViewHolder controlViewHolder, final String str, final Control control) {
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        boolean usePanel = controlViewHolder.usePanel();
        String controlId = controlViewHolder.getCws().getCi().getControlId();
        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$touch$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1854invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1854invoke() {
                ControlViewHolder.this.getLayout().performHapticFeedback(6);
                if (ControlViewHolder.this.usePanel()) {
                    this.showDetail(ControlViewHolder.this, control.getAppIntent());
                } else {
                    ControlViewHolder.this.action(new CommandAction(str));
                }
            }
        };
        Control control2 = controlViewHolder.getCws().getControl();
        bouncerOrRun(createAction(controlId, function0, usePanel, control2 != null ? control2.isAuthRequired() : true));
    }

    public final void vibrate(VibrationEffect vibrationEffect) {
        this.vibrator.vibrate(vibrationEffect);
    }
}