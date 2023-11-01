package com.android.systemui.biometrics;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.view.Display;
import android.view.WindowInsets;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.R$color;
import com.android.systemui.R$raw;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/SideFpsControllerKt.class */
public final class SideFpsControllerKt {
    public static final void addOverlayDynamicColor(final LottieAnimationView lottieAnimationView, final Context context) {
        if (lottieAnimationView.getComposition() != null) {
            addOverlayDynamicColor$update(context, lottieAnimationView);
        } else {
            lottieAnimationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() { // from class: com.android.systemui.biometrics.SideFpsControllerKt$addOverlayDynamicColor$1
                @Override // com.airbnb.lottie.LottieOnCompositionLoadedListener
                public final void onCompositionLoaded(LottieComposition lottieComposition) {
                    SideFpsControllerKt.addOverlayDynamicColor$update(context, lottieAnimationView);
                }
            });
        }
    }

    public static final void addOverlayDynamicColor$update(Context context, LottieAnimationView lottieAnimationView) {
        final int color = context.getColor(R$color.biometric_dialog_accent);
        final int color2 = context.getColor(R$color.sfps_chevron_fill);
        Iterator it = CollectionsKt__CollectionsKt.listOf(new String[]{".blue600", ".blue400"}).iterator();
        while (it.hasNext()) {
            lottieAnimationView.addValueCallback(new KeyPath((String) it.next(), "**"), (KeyPath) LottieProperty.COLOR_FILTER, (SimpleLottieValueCallback<KeyPath>) new SimpleLottieValueCallback() { // from class: com.android.systemui.biometrics.SideFpsControllerKt$addOverlayDynamicColor$update$1
                @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                public final ColorFilter getValue(LottieFrameInfo<ColorFilter> lottieFrameInfo) {
                    return new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                }

                @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                public /* bridge */ /* synthetic */ Object getValue(LottieFrameInfo lottieFrameInfo) {
                    return getValue((LottieFrameInfo<ColorFilter>) lottieFrameInfo);
                }
            });
        }
        lottieAnimationView.addValueCallback(new KeyPath(".black", "**"), (KeyPath) LottieProperty.COLOR_FILTER, (SimpleLottieValueCallback<KeyPath>) new SimpleLottieValueCallback() { // from class: com.android.systemui.biometrics.SideFpsControllerKt$addOverlayDynamicColor$update$2
            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public final ColorFilter getValue(LottieFrameInfo<ColorFilter> lottieFrameInfo) {
                return new PorterDuffColorFilter(color2, PorterDuff.Mode.SRC_ATOP);
            }

            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public /* bridge */ /* synthetic */ Object getValue(LottieFrameInfo lottieFrameInfo) {
                return getValue((LottieFrameInfo<ColorFilter>) lottieFrameInfo);
            }
        });
    }

    public static final int asSideFpsAnimation(Display display, boolean z, int i) {
        return i != 0 ? i != 2 ? z ? R$raw.sfps_pulse_landscape : R$raw.sfps_pulse : z ? R$raw.sfps_pulse : R$raw.sfps_pulse_landscape : z ? R$raw.sfps_pulse : R$raw.sfps_pulse_landscape;
    }

    public static final float asSideFpsAnimationRotation(Display display, boolean z, int i) {
        float f = 0.0f;
        if (i == 1 ? !z : i == 2 || (i == 3 && z)) {
            f = 180.0f;
        }
        return f;
    }

    public static final FingerprintSensorPropertiesInternal getSideFpsSensorProperties(FingerprintManager fingerprintManager) {
        Object obj;
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = null;
        if (fingerprintManager != null) {
            List sensorPropertiesInternal = fingerprintManager.getSensorPropertiesInternal();
            fingerprintSensorPropertiesInternal = null;
            if (sensorPropertiesInternal != null) {
                Iterator it = sensorPropertiesInternal.iterator();
                do {
                    obj = null;
                    if (!it.hasNext()) {
                        break;
                    }
                    obj = it.next();
                } while (!((FingerprintSensorPropertiesInternal) obj).isAnySidefpsType());
                fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) obj;
            }
        }
        return fingerprintSensorPropertiesInternal;
    }

    public static final boolean hasBigNavigationBar(WindowInsets windowInsets) {
        return windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom >= 70;
    }

    public static final boolean hasSideFpsSensor(FingerprintManager fingerprintManager) {
        return (fingerprintManager != null ? getSideFpsSensorProperties(fingerprintManager) : null) != null;
    }

    public static final boolean isNaturalOrientation(Display display) {
        return display.getRotation() == 0 || display.getRotation() == 2;
    }

    public static final boolean isReasonToAutoShow(int i, ActivityTaskManager activityTaskManager) {
        boolean z = true;
        if (i == 4 || (i == 6 && Intrinsics.areEqual(topClass(activityTaskManager), "com.android.settings.biometrics.fingerprint.FingerprintSettings"))) {
            z = false;
        }
        return z;
    }

    public static final boolean isYAligned(SensorLocationInternal sensorLocationInternal) {
        return sensorLocationInternal.sensorLocationY != 0;
    }

    public static final String topClass(ActivityTaskManager activityTaskManager) {
        ComponentName componentName;
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull(activityTaskManager.getTasks(1));
        String className = (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName();
        String str = className;
        if (className == null) {
            str = "";
        }
        return str;
    }
}