package com.android.systemui.decor;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/FaceScanningProviderFactory.class */
public final class FaceScanningProviderFactory extends DecorProviderFactory {
    public final AuthController authController;
    public final Context context;
    public final Display display;
    public final DisplayInfo displayInfo = new DisplayInfo();
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Executor mainExecutor;
    public final StatusBarStateController statusBarStateController;

    public FaceScanningProviderFactory(AuthController authController, Context context, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor) {
        this.authController = authController;
        this.context = context;
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainExecutor = executor;
        this.display = context.getDisplay();
    }

    public final boolean canShowFaceScanningAnim() {
        return getHasProviders() && this.keyguardUpdateMonitor.isFaceEnrolled();
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public boolean getHasProviders() {
        if (this.authController.getFaceSensorLocation() == null) {
            return false;
        }
        Display display = this.display;
        if (display != null) {
            display.getDisplayInfo(this.displayInfo);
        } else {
            Log.w("FaceScanningProvider", "display is null, can't update displayInfo");
        }
        return DisplayCutout.getFillBuiltInDisplayCutout(this.context.getResources(), this.displayInfo.uniqueId);
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public List<DecorProvider> getProviders() {
        List<Integer> boundBaseOnCurrentRotation;
        if (getHasProviders()) {
            ArrayList arrayList = new ArrayList();
            DisplayCutout displayCutout = this.displayInfo.displayCutout;
            if (displayCutout != null && (boundBaseOnCurrentRotation = FaceScanningProviderFactoryKt.getBoundBaseOnCurrentRotation(displayCutout)) != null) {
                for (Integer num : boundBaseOnCurrentRotation) {
                    arrayList.add(new FaceScanningOverlayProviderImpl(FaceScanningProviderFactoryKt.baseOnRotation0(num.intValue(), this.displayInfo.rotation), this.authController, this.statusBarStateController, this.keyguardUpdateMonitor, this.mainExecutor));
                }
            }
            return arrayList;
        }
        return CollectionsKt__CollectionsKt.emptyList();
    }

    public final boolean shouldShowFaceScanningAnim() {
        return canShowFaceScanningAnim() && this.keyguardUpdateMonitor.isFaceDetectionRunning();
    }
}