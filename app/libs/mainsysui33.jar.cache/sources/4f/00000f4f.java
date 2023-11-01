package com.android.systemui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.CameraManager;
import android.util.PathParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/CameraAvailabilityListener.class */
public final class CameraAvailabilityListener {
    public static final Factory Factory = new Factory(null);
    public final CameraManager cameraManager;
    public final Path cutoutProtectionPath;
    public final Set<String> excludedPackageIds;
    public final Executor executor;
    public final String targetCameraId;
    public Rect cutoutBounds = new Rect();
    public final List<CameraTransitionCallback> listeners = new ArrayList();
    public final CameraManager.AvailabilityCallback availabilityCallback = new CameraManager.AvailabilityCallback() { // from class: com.android.systemui.CameraAvailabilityListener$availabilityCallback$1
        public void onCameraClosed(String str) {
            if (Intrinsics.areEqual(CameraAvailabilityListener.access$getTargetCameraId$p(CameraAvailabilityListener.this), str)) {
                CameraAvailabilityListener.access$notifyCameraInactive(CameraAvailabilityListener.this);
            }
        }

        public void onCameraOpened(String str, String str2) {
            if (!Intrinsics.areEqual(CameraAvailabilityListener.access$getTargetCameraId$p(CameraAvailabilityListener.this), str) || CameraAvailabilityListener.access$isExcluded(CameraAvailabilityListener.this, str2)) {
                return;
            }
            CameraAvailabilityListener.access$notifyCameraActive(CameraAvailabilityListener.this);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/CameraAvailabilityListener$CameraTransitionCallback.class */
    public interface CameraTransitionCallback {
        void onApplyCameraProtection(Path path, Rect rect);

        void onHideCameraProtection();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/CameraAvailabilityListener$Factory.class */
    public static final class Factory {
        public Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final CameraAvailabilityListener build(Context context, Executor executor) {
            CameraManager cameraManager = (CameraManager) context.getSystemService("camera");
            Resources resources = context.getResources();
            String string = resources.getString(R$string.config_frontBuiltInDisplayCutoutProtection);
            return new CameraAvailabilityListener(cameraManager, pathFromString(string), resources.getString(R$string.config_protectedCameraId), resources.getString(R$string.config_cameraProtectionExcludedPackages), executor);
        }

        public final Path pathFromString(String str) {
            try {
                return PathParser.createPathFromPathData(StringsKt__StringsKt.trim(str).toString());
            } catch (Throwable th) {
                throw new IllegalArgumentException("Invalid protection path", th);
            }
        }
    }

    public CameraAvailabilityListener(CameraManager cameraManager, Path path, String str, String str2, Executor executor) {
        this.cameraManager = cameraManager;
        this.cutoutProtectionPath = path;
        this.targetCameraId = str;
        this.executor = executor;
        RectF rectF = new RectF();
        path.computeBounds(rectF, false);
        this.cutoutBounds.set(MathKt__MathJVMKt.roundToInt(rectF.left), MathKt__MathJVMKt.roundToInt(rectF.top), MathKt__MathJVMKt.roundToInt(rectF.right), MathKt__MathJVMKt.roundToInt(rectF.bottom));
        this.excludedPackageIds = CollectionsKt___CollectionsKt.toSet(StringsKt__StringsKt.split$default(str2, new String[]{","}, false, 0, 6, (Object) null));
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.CameraAvailabilityListener$availabilityCallback$1.onCameraClosed(java.lang.String):void, com.android.systemui.CameraAvailabilityListener$availabilityCallback$1.onCameraOpened(java.lang.String, java.lang.String):void] */
    public static final /* synthetic */ String access$getTargetCameraId$p(CameraAvailabilityListener cameraAvailabilityListener) {
        return cameraAvailabilityListener.targetCameraId;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.CameraAvailabilityListener$availabilityCallback$1.onCameraOpened(java.lang.String, java.lang.String):void] */
    public static final /* synthetic */ boolean access$isExcluded(CameraAvailabilityListener cameraAvailabilityListener, String str) {
        return cameraAvailabilityListener.isExcluded(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.CameraAvailabilityListener$availabilityCallback$1.onCameraOpened(java.lang.String, java.lang.String):void] */
    public static final /* synthetic */ void access$notifyCameraActive(CameraAvailabilityListener cameraAvailabilityListener) {
        cameraAvailabilityListener.notifyCameraActive();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.CameraAvailabilityListener$availabilityCallback$1.onCameraClosed(java.lang.String):void] */
    public static final /* synthetic */ void access$notifyCameraInactive(CameraAvailabilityListener cameraAvailabilityListener) {
        cameraAvailabilityListener.notifyCameraInactive();
    }

    public final void addTransitionCallback(CameraTransitionCallback cameraTransitionCallback) {
        this.listeners.add(cameraTransitionCallback);
    }

    public final boolean isExcluded(String str) {
        return this.excludedPackageIds.contains(str);
    }

    public final void notifyCameraActive() {
        for (CameraTransitionCallback cameraTransitionCallback : this.listeners) {
            cameraTransitionCallback.onApplyCameraProtection(this.cutoutProtectionPath, this.cutoutBounds);
        }
    }

    public final void notifyCameraInactive() {
        for (CameraTransitionCallback cameraTransitionCallback : this.listeners) {
            cameraTransitionCallback.onHideCameraProtection();
        }
    }

    public final void registerCameraListener() {
        this.cameraManager.registerAvailabilityCallback(this.executor, this.availabilityCallback);
    }

    public final void startListening() {
        registerCameraListener();
    }
}