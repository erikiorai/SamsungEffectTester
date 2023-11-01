package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.UserManager;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.widget.LockPatternUtils;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/Utils.class */
public final class Utils {
    public static final Utils INSTANCE = new Utils();

    public static final float dpToPixels(Context context, float f) {
        return f * (context.getResources().getDisplayMetrics().densityDpi / 160);
    }

    /* JADX DEBUG: Type inference failed for r0v6. Raw type applied. Possible types: java.util.Iterator<T>, java.util.Iterator */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11, types: [android.hardware.biometrics.SensorPropertiesInternal] */
    public static final <T extends SensorPropertiesInternal> T findFirstSensorProperties(List<? extends T> list, int[] iArr) {
        Object obj;
        T t = null;
        if (list != null) {
            Iterator it = list.iterator();
            do {
                obj = null;
                if (!it.hasNext()) {
                    break;
                }
                obj = it.next();
            } while (!ArraysKt___ArraysKt.contains(iArr, ((SensorPropertiesInternal) obj).sensorId));
            t = (SensorPropertiesInternal) obj;
        }
        return t;
    }

    public static final int getCredentialType(LockPatternUtils lockPatternUtils, int i) {
        int i2;
        int keyguardStoredPasswordQuality = lockPatternUtils.getKeyguardStoredPasswordQuality(i);
        if (keyguardStoredPasswordQuality == 65536) {
            i2 = 2;
        } else if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
            i2 = 1;
        } else {
            i2 = 3;
            if (keyguardStoredPasswordQuality != 262144) {
                i2 = 3;
                if (keyguardStoredPasswordQuality != 327680) {
                    i2 = 3;
                    if (keyguardStoredPasswordQuality != 393216) {
                        i2 = 3;
                    }
                }
            }
        }
        return i2;
    }

    public static final boolean isBiometricAllowed(PromptInfo promptInfo) {
        return (promptInfo.getAuthenticators() & 255) != 0;
    }

    public static final boolean isDeviceCredentialAllowed(PromptInfo promptInfo) {
        return (promptInfo.getAuthenticators() & 32768) != 0;
    }

    public static final boolean isManagedProfile(Context context, int i) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        return userManager != null ? userManager.isManagedProfile(i) : false;
    }

    public static final boolean isSystem(Context context, String str) {
        boolean z = true;
        if (!(context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") == 0) || !Intrinsics.areEqual("android", str)) {
            z = false;
        }
        return z;
    }

    public static final void notifyAccessibilityContentChanged(AccessibilityManager accessibilityManager, ViewGroup viewGroup) {
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(RecyclerView.ViewHolder.FLAG_MOVED);
            obtain.setContentChangeTypes(1);
            viewGroup.sendAccessibilityEventUnchecked(obtain);
            viewGroup.notifySubtreeAccessibilityStateChanged(viewGroup, viewGroup, 1);
        }
    }
}