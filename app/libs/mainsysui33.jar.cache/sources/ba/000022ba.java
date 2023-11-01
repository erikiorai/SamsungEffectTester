package com.android.systemui.qs.tiles;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/DeviceControlsTile.class */
public final class DeviceControlsTile extends QSTileImpl<QSTile.State> {
    public final ControlsComponent controlsComponent;
    public AtomicBoolean hasControlsApps;
    public final DeviceControlsTile$listingCallback$1 listingCallback;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.tiles.DeviceControlsTile$listingCallback$1] */
    public DeviceControlsTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ControlsComponent controlsComponent) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.controlsComponent = controlsComponent;
        this.hasControlsApps = new AtomicBoolean(false);
        this.listingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.qs.tiles.DeviceControlsTile$listingCallback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public void onServicesUpdated(List<ControlsServiceInfo> list) {
                if (DeviceControlsTile.access$getHasControlsApps$p(DeviceControlsTile.this).compareAndSet(list.isEmpty(), !list.isEmpty())) {
                    DeviceControlsTile.this.refreshState();
                }
            }
        };
        controlsComponent.getControlsListingController().ifPresent(new Consumer() { // from class: com.android.systemui.qs.tiles.DeviceControlsTile.1
            {
                DeviceControlsTile.this = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(ControlsListingController controlsListingController) {
                DeviceControlsTile deviceControlsTile = DeviceControlsTile.this;
                controlsListingController.observe(deviceControlsTile, deviceControlsTile.listingCallback);
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.DeviceControlsTile$listingCallback$1.onServicesUpdated(java.util.List<com.android.systemui.controls.ControlsServiceInfo>):void] */
    public static final /* synthetic */ AtomicBoolean access$getHasControlsApps$p(DeviceControlsTile deviceControlsTile) {
        return deviceControlsTile.hasControlsApps;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.DeviceControlsTile$handleClick$1.run():void] */
    /* renamed from: access$getMActivityStarter$p$s-474264016 */
    public static final /* synthetic */ ActivityStarter m4009access$getMActivityStarter$p$s474264016(DeviceControlsTile deviceControlsTile) {
        return deviceControlsTile.mActivityStarter;
    }

    public static /* synthetic */ void getIcon$annotations() {
    }

    public final QSTile.Icon getIcon() {
        return QSTileImpl.ResourceIcon.get(this.controlsComponent.getTileImageId());
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
        return this.mContext.getText(this.controlsComponent.getTileTitleId());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (getState().state == 0) {
            return;
        }
        final Intent intent = new Intent();
        intent.setComponent(new ComponentName(this.mContext, this.controlsComponent.getControlsUiController().get().resolveActivity()));
        intent.addFlags(335544320);
        intent.putExtra("extra_animate", true);
        final ActivityLaunchAnimator.Controller fromView = view != null ? ActivityLaunchAnimator.Controller.Companion.fromView(view, 32) : null;
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DeviceControlsTile$handleClick$1
            @Override // java.lang.Runnable
            public final void run() {
                DeviceControlsTile.m4009access$getMActivityStarter$p$s474264016(DeviceControlsTile.this).startActivity(intent, true, fromView, DeviceControlsTile.this.getState().state == 2);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleLongClick(View view) {
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.State state, Object obj) {
        CharSequence tileLabel = getTileLabel();
        state.label = tileLabel;
        state.contentDescription = tileLabel;
        state.icon = getIcon();
        if (!this.controlsComponent.isEnabled() || !this.hasControlsApps.get()) {
            state.state = 0;
            return;
        }
        int i = 1;
        if (this.controlsComponent.getVisibility() == ControlsComponent.Visibility.AVAILABLE) {
            SelectedItem preferredSelection = this.controlsComponent.getControlsController().get().getPreferredSelection();
            if (!(preferredSelection instanceof SelectedItem.StructureItem) || !((SelectedItem.StructureItem) preferredSelection).getStructure().getControls().isEmpty()) {
                i = 2;
            }
            state.state = i;
            CharSequence name = preferredSelection.getName();
            CharSequence charSequence = name;
            if (Intrinsics.areEqual(name, getTileLabel())) {
                charSequence = null;
            }
            state.secondaryLabel = charSequence;
        } else {
            state.state = 1;
            state.secondaryLabel = this.mContext.getText(R$string.controls_tile_locked);
        }
        state.stateDescription = state.secondaryLabel;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.controlsComponent.getControlsController().isPresent();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.state = 0;
        state.handlesLongClick = false;
        return state;
    }
}