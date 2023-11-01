package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/ScreenRecordTile.class */
public class ScreenRecordTile extends QSTileImpl<QSTile.BooleanState> implements RecordingController.RecordingStateChangeCallback {
    public final Callback mCallback;
    public final RecordingController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final FeatureFlags mFlags;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardStateController mKeyguardStateController;
    public long mMillisUntilFinished;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/ScreenRecordTile$Callback.class */
    public final class Callback implements RecordingController.RecordingStateChangeCallback {
        public Callback() {
            ScreenRecordTile.this = r4;
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public void onCountdown(long j) {
            ScreenRecordTile.this.mMillisUntilFinished = j;
            ScreenRecordTile.this.refreshState();
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public void onCountdownEnd() {
            ScreenRecordTile.this.refreshState();
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public void onRecordingEnd() {
            ScreenRecordTile.this.refreshState();
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public void onRecordingStart() {
            ScreenRecordTile.this.refreshState();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$8RRx37kCHuaityQc8XJx-HsCeM0 */
    public static /* synthetic */ void m4043$r8$lambda$8RRx37kCHuaityQc8XJxHsCeM0(ScreenRecordTile screenRecordTile, View view) {
        screenRecordTile.lambda$handleClick$0(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$YQta6JmBzcBXUibziegVimDtjfw(ScreenRecordTile screenRecordTile) {
        screenRecordTile.lambda$showPrompt$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2.onDismiss():boolean] */
    public static /* synthetic */ boolean $r8$lambda$waThNmSp0XRjOvk2FWy_CNO5iY4(ScreenRecordTile screenRecordTile, boolean z, Dialog dialog, View view) {
        return screenRecordTile.lambda$showPrompt$2(z, dialog, view);
    }

    public ScreenRecordTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, FeatureFlags featureFlags, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, RecordingController recordingController, KeyguardDismissUtil keyguardDismissUtil, KeyguardStateController keyguardStateController, DialogLaunchAnimator dialogLaunchAnimator) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback();
        this.mCallback = callback;
        this.mMillisUntilFinished = 0L;
        this.mController = recordingController;
        recordingController.observe(this, callback);
        this.mFlags = featureFlags;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mKeyguardStateController = keyguardStateController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
    }

    public /* synthetic */ void lambda$showPrompt$1() {
        this.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
        getHost().collapsePanels();
    }

    public /* synthetic */ boolean lambda$showPrompt$2(boolean z, Dialog dialog, View view) {
        if (z) {
            this.mDialogLaunchAnimator.showFromView(dialog, view, new DialogCuj(58, "screen_record"));
            return false;
        }
        dialog.show();
        return false;
    }

    public final void cancelCountdown() {
        Log.d("ScreenRecordTile", "Cancelling countdown");
        this.mController.cancelCountdown();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_screen_record_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(final View view) {
        if (this.mController.isStarting()) {
            cancelCountdown();
        } else if (this.mController.isRecording()) {
            stopRecording();
        } else {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenRecordTile.m4043$r8$lambda$8RRx37kCHuaityQc8XJxHsCeM0(ScreenRecordTile.this, view);
                }
            });
        }
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean isStarting = this.mController.isStarting();
        boolean isRecording = this.mController.isRecording();
        booleanState.value = isRecording || isStarting;
        booleanState.state = (isRecording || isStarting) ? 2 : 1;
        booleanState.label = this.mContext.getString(R$string.quick_settings_screen_record_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R$drawable.qs_screen_record_icon_on : R$drawable.qs_screen_record_icon_off);
        booleanState.forceExpandIcon = booleanState.state == 1;
        if (isRecording) {
            booleanState.secondaryLabel = this.mContext.getString(R$string.quick_settings_screen_record_stop);
        } else if (isStarting) {
            booleanState.secondaryLabel = String.format("%d...", Integer.valueOf((int) Math.floorDiv(this.mMillisUntilFinished + 500, 1000)));
        } else {
            booleanState.secondaryLabel = this.mContext.getString(R$string.quick_settings_screen_record_start);
        }
        booleanState.contentDescription = TextUtils.isEmpty(booleanState.secondaryLabel) ? booleanState.label : TextUtils.concat(booleanState.label, ", ", booleanState.secondaryLabel);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = this.mContext.getString(R$string.quick_settings_screen_record_label);
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    /* renamed from: showPrompt */
    public final void lambda$handleClick$0(final View view) {
        final boolean z = (view == null || this.mKeyguardStateController.isShowing()) ? false : true;
        final Dialog createScreenRecordDialog = this.mController.createScreenRecordDialog(this.mContext, this.mFlags, this.mDialogLaunchAnimator, this.mActivityStarter, new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ScreenRecordTile.$r8$lambda$YQta6JmBzcBXUibziegVimDtjfw(ScreenRecordTile.this);
            }
        });
        this.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                return ScreenRecordTile.$r8$lambda$waThNmSp0XRjOvk2FWy_CNO5iY4(ScreenRecordTile.this, z, createScreenRecordDialog, view);
            }
        }, false, true);
    }

    public final void stopRecording() {
        this.mController.stopRecording();
    }
}