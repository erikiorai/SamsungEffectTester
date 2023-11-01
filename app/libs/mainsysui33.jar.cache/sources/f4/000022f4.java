package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/QRCodeScannerTile.class */
public class QRCodeScannerTile extends QSTileImpl<QSTile.State> {
    public final QRCodeScannerController.Callback mCallback;
    public final CharSequence mLabel;
    public final QRCodeScannerController mQRCodeScannerController;

    public QRCodeScannerTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, QRCodeScannerController qRCodeScannerController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLabel = this.mContext.getString(R$string.qr_code_scanner_title);
        QRCodeScannerController.Callback callback = new QRCodeScannerController.Callback() { // from class: com.android.systemui.qs.tiles.QRCodeScannerTile.1
            @Override // com.android.systemui.qrcodescanner.controller.QRCodeScannerController.Callback
            public void onQRCodeScannerActivityChanged() {
                QRCodeScannerTile.this.refreshState();
            }
        };
        this.mCallback = callback;
        this.mQRCodeScannerController = qRCodeScannerController;
        qRCodeScannerController.observe(getLifecycle(), callback);
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
        return this.mLabel;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        Intent intent = this.mQRCodeScannerController.getIntent();
        if (intent == null) {
            Log.e("QRCodeScanner", "Expected a non-null intent");
        } else {
            this.mActivityStarter.startActivity(intent, true, view == null ? null : ActivityLaunchAnimator.Controller.fromView(view, 32), true);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
        this.mQRCodeScannerController.unregisterQRCodeScannerChangeObservers(0);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleInitialize() {
        this.mQRCodeScannerController.registerQRCodeScannerChangeObservers(0);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.State state, Object obj) {
        String string = this.mContext.getString(R$string.qr_code_scanner_title);
        state.label = string;
        state.contentDescription = string;
        state.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qr_code_scanner);
        boolean isAbleToOpenCameraApp = this.mQRCodeScannerController.isAbleToOpenCameraApp();
        state.state = isAbleToOpenCameraApp ? 1 : 0;
        state.secondaryLabel = !isAbleToOpenCameraApp ? this.mContext.getString(R$string.qr_code_scanner_updating_secondary_label) : null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mQRCodeScannerController.isCameraAvailable();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.handlesLongClick = false;
        return state;
    }
}