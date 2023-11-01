package com.android.systemui.biometrics;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.ISidefpsController;
import android.os.Handler;
import android.util.Log;
import android.util.RotationUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.biometrics.BiometricDisplayListener;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/SideFpsController.class */
public final class SideFpsController implements Dumpable {
    public final ActivityTaskManager activityTaskManager;
    public final long animationDuration;
    public final Context context;
    public final Handler handler;
    public final boolean isReverseDefaultRotation;
    public final LayoutInflater layoutInflater;
    public final DelayableExecutor mainExecutor;
    public final BiometricDisplayListener orientationListener;
    public ViewPropertyAnimator overlayHideAnimator;
    public SensorLocationInternal overlayOffsets;
    public View overlayView;
    public final WindowManager.LayoutParams overlayViewParams;
    public final OverviewProxyService.OverviewProxyListener overviewProxyListener;
    public final HashSet<SideFpsUiRequestSource> requests = new HashSet<>();
    public final FingerprintSensorPropertiesInternal sensorProps;
    public final WindowManager windowManager;

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
        r0 = com.android.systemui.biometrics.SideFpsControllerKt.getSideFpsSensorProperties(r14);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SideFpsController(Context context, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, ActivityTaskManager activityTaskManager, OverviewProxyService overviewProxyService, DisplayManager displayManager, DelayableExecutor delayableExecutor, Handler handler, DumpManager dumpManager) {
        FingerprintSensorPropertiesInternal sideFpsSensorProperties;
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.windowManager = windowManager;
        this.activityTaskManager = activityTaskManager;
        this.mainExecutor = delayableExecutor;
        this.handler = handler;
        if (fingerprintManager == null || sideFpsSensorProperties == null) {
            throw new IllegalStateException("no side fingerprint sensor");
        }
        this.sensorProps = sideFpsSensorProperties;
        this.orientationListener = new BiometricDisplayListener(context, displayManager, handler, new BiometricDisplayListener.SensorType.SideFingerprint(sideFpsSensorProperties), new Function0<Unit>() { // from class: com.android.systemui.biometrics.SideFpsController$orientationListener$1
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1548invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1548invoke() {
                SideFpsController.access$onOrientationChanged(SideFpsController.this);
            }
        });
        OverviewProxyService.OverviewProxyListener overviewProxyListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.biometrics.SideFpsController$overviewProxyListener$1
            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onTaskbarStatusUpdated(boolean z, boolean z2) {
                final View view;
                Handler handler2;
                view = SideFpsController.this.overlayView;
                if (view != null) {
                    final SideFpsController sideFpsController = SideFpsController.this;
                    handler2 = sideFpsController.handler;
                    handler2.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.SideFpsController$overviewProxyListener$1$onTaskbarStatusUpdated$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SideFpsController.this.updateOverlayVisibility(view);
                        }
                    }, 500L);
                }
            }
        };
        this.overviewProxyListener = overviewProxyListener;
        this.animationDuration = context.getResources().getInteger(17694721);
        this.isReverseDefaultRotation = context.getResources().getBoolean(17891767);
        this.overlayOffsets = SensorLocationInternal.DEFAULT;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2009, 16777512, -3);
        layoutParams.setTitle("SideFpsController");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags = 536870976;
        this.overlayViewParams = layoutParams;
        fingerprintManager.setSidefpsController(new ISidefpsController.Stub() { // from class: com.android.systemui.biometrics.SideFpsController.1
            {
                SideFpsController.this = this;
            }

            public void hide(int i) {
                SideFpsController.this.hide(SideFpsUiRequestSource.AUTO_SHOW);
            }

            public void show(int i, int i2) {
                boolean isReasonToAutoShow;
                isReasonToAutoShow = SideFpsControllerKt.isReasonToAutoShow(i2, SideFpsController.this.activityTaskManager);
                if (isReasonToAutoShow) {
                    SideFpsController.this.show(SideFpsUiRequestSource.AUTO_SHOW);
                } else {
                    SideFpsController.this.hide(SideFpsUiRequestSource.AUTO_SHOW);
                }
            }
        });
        overviewProxyService.addCallback(overviewProxyListener);
        dumpManager.registerDumpable(this);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.SideFpsController$show$1.run():void] */
    public static final /* synthetic */ void access$createOverlayForDisplay(SideFpsController sideFpsController) {
        sideFpsController.createOverlayForDisplay();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.SideFpsController$show$1.run():void] */
    public static final /* synthetic */ View access$getOverlayView$p(SideFpsController sideFpsController) {
        return sideFpsController.overlayView;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.SideFpsController$orientationListener$1.invoke():void] */
    public static final /* synthetic */ void access$onOrientationChanged(SideFpsController sideFpsController) {
        sideFpsController.onOrientationChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.SideFpsController$updateOverlayVisibility$1.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setOverlayHideAnimator$p(SideFpsController sideFpsController, ViewPropertyAnimator viewPropertyAnimator) {
        sideFpsController.overlayHideAnimator = viewPropertyAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.SideFpsController$hide$1.run():void] */
    public static final /* synthetic */ void access$setOverlayView(SideFpsController sideFpsController, View view) {
        sideFpsController.setOverlayView(view);
    }

    @VisibleForTesting
    public static /* synthetic */ void getOrientationListener$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getOverlayOffsets$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getOverviewProxyListener$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getSensorProps$annotations() {
    }

    public final void createOverlayForDisplay() {
        boolean isYAligned;
        float asSideFpsAnimationRotation;
        boolean isYAligned2;
        int asSideFpsAnimation;
        final View inflate = this.layoutInflater.inflate(R$layout.sidefps_view, (ViewGroup) null, false);
        setOverlayView(inflate);
        final Display display = this.context.getDisplay();
        Intrinsics.checkNotNull(display);
        SensorLocationInternal location = this.sensorProps.getLocation(display.getUniqueId());
        if (location == null) {
            String uniqueId = display.getUniqueId();
            Log.w("SideFpsController", "No location specified for display: " + uniqueId);
        }
        SensorLocationInternal sensorLocationInternal = location;
        if (location == null) {
            sensorLocationInternal = this.sensorProps.getLocation();
        }
        this.overlayOffsets = sensorLocationInternal;
        LottieAnimationView lottieAnimationView = (LottieAnimationView) inflate.findViewById(R$id.sidefps_animation);
        isYAligned = SideFpsControllerKt.isYAligned(sensorLocationInternal);
        asSideFpsAnimationRotation = SideFpsControllerKt.asSideFpsAnimationRotation(display, isYAligned, getRotationFromDefault(display.getRotation()));
        inflate.setRotation(asSideFpsAnimationRotation);
        isYAligned2 = SideFpsControllerKt.isYAligned(sensorLocationInternal);
        asSideFpsAnimation = SideFpsControllerKt.asSideFpsAnimation(display, isYAligned2, getRotationFromDefault(display.getRotation()));
        lottieAnimationView.setAnimation(asSideFpsAnimation);
        lottieAnimationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() { // from class: com.android.systemui.biometrics.SideFpsController$createOverlayForDisplay$1
            @Override // com.airbnb.lottie.LottieOnCompositionLoadedListener
            public final void onCompositionLoaded(LottieComposition lottieComposition) {
                View view;
                View view2;
                view = SideFpsController.this.overlayView;
                if (view != null) {
                    view2 = SideFpsController.this.overlayView;
                    if (Intrinsics.areEqual(view2, inflate)) {
                        SideFpsController.this.updateOverlayParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core(display, lottieComposition.getBounds());
                    }
                }
            }
        });
        SideFpsControllerKt.addOverlayDynamicColor(lottieAnimationView, this.context);
        inflate.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.biometrics.SideFpsController$createOverlayForDisplay$2
            @Override // android.view.View.AccessibilityDelegate
            public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                return accessibilityEvent.getEventType() == 32 ? true : super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("requests:");
        Iterator<SideFpsUiRequestSource> it = this.requests.iterator();
        while (it.hasNext()) {
            SideFpsUiRequestSource next = it.next();
            printWriter.println("     " + next + ".name");
        }
    }

    public final HashSet<SideFpsUiRequestSource> getRequests() {
        return this.requests;
    }

    public final int getRotationFromDefault(int i) {
        int i2 = i;
        if (this.isReverseDefaultRotation) {
            i2 = (i + 1) % 4;
        }
        return i2;
    }

    public final void hide(SideFpsUiRequestSource sideFpsUiRequestSource) {
        this.requests.remove(sideFpsUiRequestSource);
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.SideFpsController$hide$1
            @Override // java.lang.Runnable
            public final void run() {
                if (SideFpsController.this.getRequests().isEmpty()) {
                    SideFpsController.access$setOverlayView(SideFpsController.this, null);
                }
            }
        });
    }

    public final void onOrientationChanged() {
        if (this.overlayView != null) {
            createOverlayForDisplay();
        }
    }

    public final void setOverlayView(View view) {
        View view2 = this.overlayView;
        if (view2 != null) {
            this.windowManager.removeView(view2);
            this.orientationListener.disable();
        }
        ViewPropertyAnimator viewPropertyAnimator = this.overlayHideAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        this.overlayHideAnimator = null;
        this.overlayView = view;
        if (view != null) {
            this.windowManager.addView(view, this.overlayViewParams);
            updateOverlayVisibility(view);
            this.orientationListener.enable();
        }
    }

    public final void show(SideFpsUiRequestSource sideFpsUiRequestSource) {
        this.requests.add(sideFpsUiRequestSource);
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.SideFpsController$show$1
            @Override // java.lang.Runnable
            public final void run() {
                if (SideFpsController.access$getOverlayView$p(SideFpsController.this) == null) {
                    SideFpsController.access$createOverlayForDisplay(SideFpsController.this);
                } else {
                    Log.v("SideFpsController", "overlay already shown");
                }
            }
        });
    }

    @VisibleForTesting
    public final void updateOverlayParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core(Display display, Rect rect) {
        boolean isNaturalOrientation;
        boolean isYAligned;
        Rect rect2;
        isNaturalOrientation = SideFpsControllerKt.isNaturalOrientation(display);
        boolean z = isNaturalOrientation;
        if (this.isReverseDefaultRotation) {
            z = !isNaturalOrientation;
        }
        Rect bounds = this.windowManager.getMaximumWindowMetrics().getBounds();
        int width = z ? bounds.width() : bounds.height();
        int height = z ? bounds.height() : bounds.width();
        int width2 = z ? rect.width() : rect.height();
        int height2 = z ? rect.height() : rect.width();
        isYAligned = SideFpsControllerKt.isYAligned(this.overlayOffsets);
        if (isYAligned) {
            int i = this.overlayOffsets.sensorLocationY;
            rect2 = new Rect(width - width2, i, width, height2 + i);
        } else {
            int i2 = this.overlayOffsets.sensorLocationX;
            rect2 = new Rect(i2, 0, width2 + i2, height2);
        }
        RotationUtils.rotateBounds(rect2, new Rect(0, 0, width, height), getRotationFromDefault(display.getRotation()));
        WindowManager.LayoutParams layoutParams = this.overlayViewParams;
        layoutParams.x = rect2.left;
        layoutParams.y = rect2.top;
        this.windowManager.updateViewLayout(this.overlayView, layoutParams);
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x004d, code lost:
        if (r0 == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0066, code lost:
        if (r0 == false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0069, code lost:
        r7.overlayHideAnimator = r8.animate().alpha(com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW).setStartDelay(3000).setDuration(r7.animationDuration).setListener(new com.android.systemui.biometrics.SideFpsController$updateOverlayVisibility$1());
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:?, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateOverlayVisibility(final View view) {
        boolean hasBigNavigationBar;
        boolean isYAligned;
        boolean isYAligned2;
        if (Intrinsics.areEqual(view, this.overlayView)) {
            Display display = this.context.getDisplay();
            Integer valueOf = display != null ? Integer.valueOf(display.getRotation()) : null;
            hasBigNavigationBar = SideFpsControllerKt.hasBigNavigationBar(this.windowManager.getCurrentWindowMetrics().getWindowInsets());
            if (hasBigNavigationBar) {
                if (valueOf != null && valueOf.intValue() == 3) {
                    isYAligned2 = SideFpsControllerKt.isYAligned(this.overlayOffsets);
                }
                if (valueOf != null && valueOf.intValue() == 2) {
                    isYAligned = SideFpsControllerKt.isYAligned(this.overlayOffsets);
                }
            }
            ViewPropertyAnimator viewPropertyAnimator = this.overlayHideAnimator;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
            }
            this.overlayHideAnimator = null;
            view.setAlpha(1.0f);
            view.setVisibility(0);
        }
    }
}