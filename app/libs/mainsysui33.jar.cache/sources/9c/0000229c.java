package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.internal.app.MediaRouteDialogPresenter;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/CastTile.class */
public class CastTile extends QSTileImpl<QSTile.BooleanState> {
    public static final Intent CAST_SETTINGS = new Intent("android.settings.CAST_SETTINGS");
    public final Callback mCallback;
    public final CastController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final HotspotController.Callback mHotspotCallback;
    public boolean mHotspotConnected;
    public final KeyguardStateController mKeyguard;
    public final NetworkController mNetworkController;
    public final SignalCallback mSignalCallback;
    public boolean mWifiConnected;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/CastTile$Callback.class */
    public final class Callback implements CastController.Callback, KeyguardStateController.Callback {
        public Callback() {
            CastTile.this = r4;
        }

        public void onCastDevicesChanged() {
            CastTile.this.refreshState();
        }

        public void onKeyguardShowingChanged() {
            CastTile.this.refreshState();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/CastTile$DialogHolder.class */
    public static class DialogHolder {
        public Dialog mDialog;

        public DialogHolder() {
        }

        public final void init(Dialog dialog) {
            this.mDialog = dialog;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$DsR84bsiyIMNFQ1LrVyQXPDqQh8(CastTile castTile, View view) {
        castTile.lambda$showDialog$3(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$POKuZwVyiRf3tLhQTpY7DkkBWxw(CastTile castTile) {
        castTile.lambda$handleClick$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda3.run():void] */
    /* renamed from: $r8$lambda$mT-9UOwaMrKAblsGIhJbAVVdPgs */
    public static /* synthetic */ void m3991$r8$lambda$mT9UOwaMrKAblsGIhJbAVVdPgs(CastTile castTile, View view, Dialog dialog) {
        castTile.lambda$showDialog$2(view, dialog);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$v1mqgKldYOgkzspAIAAe1qQnu-U */
    public static /* synthetic */ void m3992$r8$lambda$v1mqgKldYOgkzspAIAAe1qQnuU(CastTile castTile, DialogHolder dialogHolder, View view) {
        castTile.lambda$showDialog$1(dialogHolder, view);
    }

    public CastTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CastController castController, KeyguardStateController keyguardStateController, NetworkController networkController, HotspotController hotspotController, DialogLaunchAnimator dialogLaunchAnimator) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback();
        this.mCallback = callback;
        SignalCallback signalCallback = new SignalCallback() { // from class: com.android.systemui.qs.tiles.CastTile.1
            {
                CastTile.this = this;
            }

            public void setWifiIndicators(WifiIndicators wifiIndicators) {
                IconState iconState;
                boolean z = wifiIndicators.enabled && (iconState = wifiIndicators.qsIcon) != null && iconState.visible;
                if (z != CastTile.this.mWifiConnected) {
                    CastTile.this.mWifiConnected = z;
                    if (CastTile.this.mHotspotConnected) {
                        return;
                    }
                    CastTile.this.refreshState();
                }
            }
        };
        this.mSignalCallback = signalCallback;
        HotspotController.Callback callback2 = new HotspotController.Callback() { // from class: com.android.systemui.qs.tiles.CastTile.2
            {
                CastTile.this = this;
            }

            public void onHotspotChanged(boolean z, int i) {
                boolean z2 = z && i > 0;
                if (z2 != CastTile.this.mHotspotConnected) {
                    CastTile.this.mHotspotConnected = z2;
                    if (CastTile.this.mWifiConnected) {
                        return;
                    }
                    CastTile.this.refreshState();
                }
            }
        };
        this.mHotspotCallback = callback2;
        this.mController = castController;
        this.mKeyguard = keyguardStateController;
        this.mNetworkController = networkController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        castController.observe(this, callback);
        keyguardStateController.observe(this, callback);
        networkController.observe(this, signalCallback);
        hotspotController.observe(this, callback2);
    }

    public /* synthetic */ void lambda$handleClick$0() {
        showDialog(null);
    }

    public /* synthetic */ void lambda$showDialog$1(DialogHolder dialogHolder, View view) {
        ActivityLaunchAnimator.Controller createActivityLaunchController = this.mDialogLaunchAnimator.createActivityLaunchController(view);
        if (createActivityLaunchController == null) {
            dialogHolder.mDialog.dismiss();
        }
        this.mActivityStarter.postStartActivityDismissingKeyguard(getLongClickIntent(), 0, createActivityLaunchController);
    }

    public /* synthetic */ void lambda$showDialog$2(View view, Dialog dialog) {
        if (view != null) {
            this.mDialogLaunchAnimator.showFromView(dialog, view, new DialogCuj(58, "cast"));
        } else {
            dialog.show();
        }
    }

    public /* synthetic */ void lambda$showDialog$3(final View view) {
        final DialogHolder dialogHolder = new DialogHolder();
        final Dialog createDialog = MediaRouteDialogPresenter.createDialog(this.mContext, 4, new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CastTile.m3992$r8$lambda$v1mqgKldYOgkzspAIAAe1qQnuU(CastTile.this, dialogHolder, view2);
            }
        }, R$style.Theme_SystemUI_Dialog_Cast, false);
        dialogHolder.init(createDialog);
        SystemUIDialog.setShowForAllUsers(createDialog, true);
        SystemUIDialog.registerDismissListener(createDialog);
        SystemUIDialog.setWindowOnTop(createDialog, this.mKeyguard.isShowing());
        SystemUIDialog.setDialogSize(createDialog);
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CastTile.m3991$r8$lambda$mT9UOwaMrKAblsGIhJbAVVdPgs(CastTile.this, view, createDialog);
            }
        });
    }

    public final boolean canCastToWifi() {
        return this.mWifiConnected || this.mHotspotConnected;
    }

    public final List<CastController.CastDevice> getActiveDevices() {
        ArrayList arrayList = new ArrayList();
        for (CastController.CastDevice castDevice : this.mController.getCastDevices()) {
            int i = castDevice.state;
            if (i == 2 || i == 1) {
                arrayList.add(castDevice);
            }
        }
        return arrayList;
    }

    public final String getDeviceName(CastController.CastDevice castDevice) {
        String str = castDevice.name;
        if (str == null) {
            str = this.mContext.getString(R$string.quick_settings_cast_device_default_name);
        }
        return str;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.CAST_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 114;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_cast_title);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (getState().state == 0) {
            return;
        }
        List<CastController.CastDevice> activeDevices = getActiveDevices();
        if (!willPopDialog()) {
            this.mController.stopCasting(activeDevices.get(0));
        } else if (this.mKeyguard.isShowing()) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CastTile.$r8$lambda$POKuZwVyiRf3tLhQTpY7DkkBWxw(CastTile.this);
                }
            });
        } else {
            showDialog(view);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleLongClick(View view) {
        handleClick(view);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (QSTileImpl.DEBUG) {
            String str = this.TAG;
            Log.d(str, "handleSetListening " + z);
        }
        if (z) {
            return;
        }
        this.mController.setDiscovering(false);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean z;
        String string = this.mContext.getString(R$string.quick_settings_cast_title);
        booleanState.label = string;
        booleanState.contentDescription = string;
        booleanState.stateDescription = "";
        booleanState.value = false;
        Iterator it = this.mController.getCastDevices().iterator();
        boolean z2 = false;
        while (true) {
            z = z2;
            if (!it.hasNext()) {
                break;
            }
            CastController.CastDevice castDevice = (CastController.CastDevice) it.next();
            int i = castDevice.state;
            if (i == 2) {
                booleanState.value = true;
                booleanState.secondaryLabel = getDeviceName(castDevice);
                booleanState.stateDescription = ((Object) booleanState.stateDescription) + "," + this.mContext.getString(R$string.accessibility_cast_name, booleanState.label);
                z = false;
                break;
            } else if (i == 1) {
                z2 = true;
            }
        }
        if (z && !booleanState.value) {
            booleanState.secondaryLabel = this.mContext.getString(R$string.quick_settings_connecting);
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R$drawable.ic_cast_connected : R$drawable.ic_cast);
        if (canCastToWifi() || booleanState.value) {
            boolean z3 = booleanState.value;
            booleanState.state = z3 ? 2 : 1;
            if (!z3) {
                booleanState.secondaryLabel = "";
            }
            booleanState.expandedAccessibilityClassName = Button.class.getName();
            booleanState.forceExpandIcon = willPopDialog();
        } else {
            booleanState.state = 0;
            booleanState.secondaryLabel = this.mContext.getString(R$string.quick_settings_cast_no_wifi);
            booleanState.forceExpandIcon = false;
        }
        booleanState.stateDescription = ((Object) booleanState.stateDescription) + ", " + ((Object) booleanState.secondaryLabel);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
        super.handleUserSwitch(i);
        this.mController.setCurrentUserId(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    public final void showDialog(final View view) {
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CastTile.$r8$lambda$DsR84bsiyIMNFQ1LrVyQXPDqQh8(CastTile.this, view);
            }
        });
    }

    public final boolean willPopDialog() {
        List<CastController.CastDevice> activeDevices = getActiveDevices();
        boolean z = false;
        if (activeDevices.isEmpty() || (activeDevices.get(0).tag instanceof MediaRouter.RouteInfo)) {
            z = true;
        }
        return z;
    }
}