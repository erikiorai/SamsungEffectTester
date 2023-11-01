package com.android.systemui.qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.permission.PermissionGroupUsage;
import android.permission.PermissionManager;
import android.safetycenter.SafetyCenterManager;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/HeaderPrivacyIconsController.class */
public final class HeaderPrivacyIconsController {
    public final ActivityStarter activityStarter;
    public final AppOpsController appOpsController;
    public final View.OnAttachStateChangeListener attachStateChangeListener;
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final String cameraSlot;
    public ChipVisibilityListener chipVisibilityListener;
    public final DeviceProvisionedController deviceProvisionedController;
    public final StatusIconContainer iconContainer;
    public boolean listening;
    public boolean locationIndicatorsEnabled;
    public final String locationSlot;
    public boolean micCameraIndicatorsEnabled;
    public final String micSlot;
    public final PermissionManager permissionManager;
    public final PrivacyItemController.Callback picCallback;
    public final OngoingPrivacyChip privacyChip;
    public boolean privacyChipLogged;
    public final PrivacyDialogController privacyDialogController;
    public final PrivacyItemController privacyItemController;
    public final PrivacyLogger privacyLogger;
    public boolean safetyCenterEnabled;
    public final SafetyCenterManager safetyCenterManager;
    public final HeaderPrivacyIconsController$safetyCenterReceiver$1 safetyCenterReceiver;
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;

    /* JADX WARN: Type inference failed for: r0v18, types: [com.android.systemui.qs.HeaderPrivacyIconsController$safetyCenterReceiver$1, android.content.BroadcastReceiver] */
    public HeaderPrivacyIconsController(PrivacyItemController privacyItemController, UiEventLogger uiEventLogger, OngoingPrivacyChip ongoingPrivacyChip, PrivacyDialogController privacyDialogController, PrivacyLogger privacyLogger, StatusIconContainer statusIconContainer, PermissionManager permissionManager, Executor executor, Executor executor2, ActivityStarter activityStarter, AppOpsController appOpsController, BroadcastDispatcher broadcastDispatcher, SafetyCenterManager safetyCenterManager, DeviceProvisionedController deviceProvisionedController) {
        this.privacyItemController = privacyItemController;
        this.uiEventLogger = uiEventLogger;
        this.privacyChip = ongoingPrivacyChip;
        this.privacyDialogController = privacyDialogController;
        this.privacyLogger = privacyLogger;
        this.iconContainer = statusIconContainer;
        this.permissionManager = permissionManager;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.activityStarter = activityStarter;
        this.appOpsController = appOpsController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.safetyCenterManager = safetyCenterManager;
        this.deviceProvisionedController = deviceProvisionedController;
        this.cameraSlot = ongoingPrivacyChip.getResources().getString(17041592);
        this.micSlot = ongoingPrivacyChip.getResources().getString(17041604);
        this.locationSlot = ongoingPrivacyChip.getResources().getString(17041602);
        ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$safetyCenterReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                HeaderPrivacyIconsController.access$setSafetyCenterEnabled$p(headerPrivacyIconsController, HeaderPrivacyIconsController.access$getSafetyCenterManager$p(headerPrivacyIconsController).isSafetyCenterEnabled());
            }
        };
        this.safetyCenterReceiver = r0;
        View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$attachStateChangeListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                BroadcastDispatcher broadcastDispatcher2;
                HeaderPrivacyIconsController$safetyCenterReceiver$1 headerPrivacyIconsController$safetyCenterReceiver$1;
                Executor executor3;
                broadcastDispatcher2 = HeaderPrivacyIconsController.this.broadcastDispatcher;
                headerPrivacyIconsController$safetyCenterReceiver$1 = HeaderPrivacyIconsController.this.safetyCenterReceiver;
                IntentFilter intentFilter = new IntentFilter("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED");
                executor3 = HeaderPrivacyIconsController.this.backgroundExecutor;
                BroadcastDispatcher.registerReceiver$default(broadcastDispatcher2, headerPrivacyIconsController$safetyCenterReceiver$1, intentFilter, executor3, null, 0, null, 56, null);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                BroadcastDispatcher broadcastDispatcher2;
                HeaderPrivacyIconsController$safetyCenterReceiver$1 headerPrivacyIconsController$safetyCenterReceiver$1;
                broadcastDispatcher2 = HeaderPrivacyIconsController.this.broadcastDispatcher;
                headerPrivacyIconsController$safetyCenterReceiver$1 = HeaderPrivacyIconsController.this.safetyCenterReceiver;
                broadcastDispatcher2.unregisterReceiver(headerPrivacyIconsController$safetyCenterReceiver$1);
            }
        };
        this.attachStateChangeListener = onAttachStateChangeListener;
        executor.execute(new Runnable() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController.1
            {
                HeaderPrivacyIconsController.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                headerPrivacyIconsController.safetyCenterEnabled = headerPrivacyIconsController.safetyCenterManager.isSafetyCenterEnabled();
            }
        });
        if (ongoingPrivacyChip.isAttachedToWindow()) {
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r0, new IntentFilter("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED"), executor, null, 0, null, 56, null);
        }
        ongoingPrivacyChip.addOnAttachStateChangeListener(onAttachStateChangeListener);
        this.picCallback = new PrivacyItemController.Callback() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$picCallback$1
            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public void onFlagLocationChanged(boolean z) {
                boolean z2;
                z2 = HeaderPrivacyIconsController.this.locationIndicatorsEnabled;
                if (z2 != z) {
                    HeaderPrivacyIconsController.this.locationIndicatorsEnabled = z;
                    update();
                }
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public void onFlagMicCameraChanged(boolean z) {
                boolean z2;
                z2 = HeaderPrivacyIconsController.this.micCameraIndicatorsEnabled;
                if (z2 != z) {
                    HeaderPrivacyIconsController.this.micCameraIndicatorsEnabled = z;
                    update();
                }
            }

            @Override // com.android.systemui.privacy.PrivacyItemController.Callback
            public void onPrivacyItemsChanged(List<PrivacyItem> list) {
                OngoingPrivacyChip ongoingPrivacyChip2;
                ongoingPrivacyChip2 = HeaderPrivacyIconsController.this.privacyChip;
                ongoingPrivacyChip2.setPrivacyList(list);
                HeaderPrivacyIconsController.this.setChipVisibility(!list.isEmpty());
            }

            public final void update() {
                OngoingPrivacyChip ongoingPrivacyChip2;
                HeaderPrivacyIconsController.this.updatePrivacyIconSlots();
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                ongoingPrivacyChip2 = headerPrivacyIconsController.privacyChip;
                headerPrivacyIconsController.setChipVisibility(!ongoingPrivacyChip2.getPrivacyList().isEmpty());
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.HeaderPrivacyIconsController$safetyCenterReceiver$1.onReceive(android.content.Context, android.content.Intent):void] */
    public static final /* synthetic */ SafetyCenterManager access$getSafetyCenterManager$p(HeaderPrivacyIconsController headerPrivacyIconsController) {
        return headerPrivacyIconsController.safetyCenterManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.HeaderPrivacyIconsController$safetyCenterReceiver$1.onReceive(android.content.Context, android.content.Intent):void] */
    public static final /* synthetic */ void access$setSafetyCenterEnabled$p(HeaderPrivacyIconsController headerPrivacyIconsController, boolean z) {
        headerPrivacyIconsController.safetyCenterEnabled = z;
    }

    public final boolean getChipEnabled() {
        return this.micCameraIndicatorsEnabled || this.locationIndicatorsEnabled;
    }

    public final void onParentInvisible() {
        this.chipVisibilityListener = null;
        this.privacyChip.setOnClickListener(null);
    }

    public final void onParentVisible() {
        this.privacyChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$onParentVisible$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DeviceProvisionedController deviceProvisionedController;
                UiEventLogger uiEventLogger;
                boolean z;
                PrivacyDialogController privacyDialogController;
                OngoingPrivacyChip ongoingPrivacyChip;
                deviceProvisionedController = HeaderPrivacyIconsController.this.deviceProvisionedController;
                if (deviceProvisionedController.isDeviceProvisioned()) {
                    uiEventLogger = HeaderPrivacyIconsController.this.uiEventLogger;
                    uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_CLICK);
                    z = HeaderPrivacyIconsController.this.safetyCenterEnabled;
                    if (z) {
                        HeaderPrivacyIconsController.this.showSafetyCenter();
                        return;
                    }
                    privacyDialogController = HeaderPrivacyIconsController.this.privacyDialogController;
                    ongoingPrivacyChip = HeaderPrivacyIconsController.this.privacyChip;
                    privacyDialogController.showDialog(ongoingPrivacyChip.getContext());
                }
            }
        });
        setChipVisibility(this.privacyChip.getVisibility() == 0);
        this.micCameraIndicatorsEnabled = this.privacyItemController.getMicCameraAvailable();
        this.locationIndicatorsEnabled = this.privacyItemController.getLocationAvailable();
        updatePrivacyIconSlots();
    }

    public final List<PermissionGroupUsage> permGroupUsage() {
        return this.permissionManager.getIndicatorAppOpUsageData(this.appOpsController.isMicMuted());
    }

    public final void setChipVisibility(boolean z) {
        int i = 0;
        if (z && getChipEnabled()) {
            this.privacyLogger.logChipVisible(true);
            if (!this.privacyChipLogged && this.listening) {
                this.privacyChipLogged = true;
                this.uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_VIEW);
            }
        } else {
            this.privacyLogger.logChipVisible(false);
        }
        OngoingPrivacyChip ongoingPrivacyChip = this.privacyChip;
        if (!z) {
            i = 8;
        }
        ongoingPrivacyChip.setVisibility(i);
        ChipVisibilityListener chipVisibilityListener = this.chipVisibilityListener;
        if (chipVisibilityListener != null) {
            chipVisibilityListener.onChipVisibilityRefreshed(z);
        }
    }

    public final void setChipVisibilityListener(ChipVisibilityListener chipVisibilityListener) {
        this.chipVisibilityListener = chipVisibilityListener;
    }

    public final void showSafetyCenter() {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$showSafetyCenter$1
            @Override // java.lang.Runnable
            public final void run() {
                List permGroupUsage;
                PrivacyLogger privacyLogger;
                Executor executor;
                permGroupUsage = HeaderPrivacyIconsController.this.permGroupUsage();
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>(permGroupUsage);
                privacyLogger = HeaderPrivacyIconsController.this.privacyLogger;
                privacyLogger.logUnfilteredPermGroupUsage(arrayList);
                final Intent intent = new Intent("android.intent.action.VIEW_SAFETY_CENTER_QS");
                intent.putParcelableArrayListExtra("android.permission.extra.PERMISSION_USAGES", arrayList);
                intent.setFlags(268435456);
                executor = HeaderPrivacyIconsController.this.uiExecutor;
                final HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                executor.execute(new Runnable() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$showSafetyCenter$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityStarter activityStarter;
                        OngoingPrivacyChip ongoingPrivacyChip;
                        activityStarter = HeaderPrivacyIconsController.this.activityStarter;
                        Intent intent2 = intent;
                        ActivityLaunchAnimator.Controller.Companion companion = ActivityLaunchAnimator.Controller.Companion;
                        ongoingPrivacyChip = HeaderPrivacyIconsController.this.privacyChip;
                        activityStarter.startActivity(intent2, true, ActivityLaunchAnimator.Controller.Companion.fromView$default(companion, ongoingPrivacyChip, null, 2, null));
                    }
                });
            }
        });
    }

    public final void startListening() {
        this.listening = true;
        this.micCameraIndicatorsEnabled = this.privacyItemController.getMicCameraAvailable();
        this.locationIndicatorsEnabled = this.privacyItemController.getLocationAvailable();
        this.privacyItemController.addCallback(this.picCallback);
    }

    public final void stopListening() {
        this.listening = false;
        this.privacyItemController.removeCallback(this.picCallback);
        this.privacyChipLogged = false;
    }

    public final void updatePrivacyIconSlots() {
        if (!getChipEnabled()) {
            this.iconContainer.removeIgnoredSlot(this.cameraSlot);
            this.iconContainer.removeIgnoredSlot(this.micSlot);
            this.iconContainer.removeIgnoredSlot(this.locationSlot);
            return;
        }
        if (this.micCameraIndicatorsEnabled) {
            this.iconContainer.addIgnoredSlot(this.cameraSlot);
            this.iconContainer.addIgnoredSlot(this.micSlot);
        } else {
            this.iconContainer.removeIgnoredSlot(this.cameraSlot);
            this.iconContainer.removeIgnoredSlot(this.micSlot);
        }
        if (this.locationIndicatorsEnabled) {
            this.iconContainer.addIgnoredSlot(this.locationSlot);
        } else {
            this.iconContainer.removeIgnoredSlot(this.locationSlot);
        }
    }
}