package com.android.systemui.decor;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.FaceScanningOverlay;
import com.android.systemui.R$id;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/FaceScanningOverlayProviderImpl.class */
public final class FaceScanningOverlayProviderImpl extends BoundDecorProvider {
    public final int alignedBound;
    public final AuthController authController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Executor mainExecutor;
    public final StatusBarStateController statusBarStateController;
    public final int viewId = R$id.face_scanning_anim;

    public FaceScanningOverlayProviderImpl(int i, AuthController authController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor) {
        this.alignedBound = i;
        this.authController = authController;
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainExecutor = executor;
    }

    @Override // com.android.systemui.decor.BoundDecorProvider
    public int getAlignedBound() {
        return this.alignedBound;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public int getViewId() {
        return this.viewId;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public View inflateView(Context context, ViewGroup viewGroup, int i, int i2) {
        FaceScanningOverlay faceScanningOverlay = new FaceScanningOverlay(context, getAlignedBound(), this.statusBarStateController, this.keyguardUpdateMonitor, this.mainExecutor);
        faceScanningOverlay.setId(getViewId());
        faceScanningOverlay.setColor(i2);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        updateLayoutParams(layoutParams, i);
        viewGroup.addView(faceScanningOverlay, layoutParams);
        return faceScanningOverlay;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        updateLayoutParams(layoutParams, i2);
        view.setLayoutParams(layoutParams);
        FaceScanningOverlay faceScanningOverlay = view instanceof FaceScanningOverlay ? (FaceScanningOverlay) view : null;
        if (faceScanningOverlay != null) {
            faceScanningOverlay.setColor(i3);
            faceScanningOverlay.updateConfiguration(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0033, code lost:
        if (r5 != 3) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLayoutParams(FrameLayout.LayoutParams layoutParams, int i) {
        layoutParams.width = -1;
        layoutParams.height = -1;
        Point faceSensorLocation = this.authController.getFaceSensorLocation();
        if (faceSensorLocation != null) {
            int i2 = faceSensorLocation.y * 2;
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                    }
                }
                layoutParams.width = i2;
            }
            layoutParams.height = i2;
        }
        layoutParams.gravity = i != 0 ? i != 1 ? i != 2 ? i != 3 ? -1 : 8388613 : 8388693 : 8388611 : 8388659;
    }
}