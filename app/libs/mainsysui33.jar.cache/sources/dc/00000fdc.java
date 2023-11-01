package com.android.systemui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.DisplayUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.Preconditions;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.CameraAvailabilityListener;
import com.android.systemui.decor.CutoutDecorProviderFactory;
import com.android.systemui.decor.DecorProvider;
import com.android.systemui.decor.DecorProviderFactory;
import com.android.systemui.decor.DecorProviderKt;
import com.android.systemui.decor.FaceScanningProviderFactory;
import com.android.systemui.decor.OverlayWindow;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.decor.RoundedCornerDecorProviderFactory;
import com.android.systemui.decor.RoundedCornerResDelegate;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;
import kotlin.Pair;

/* loaded from: mainsysui33.jar:com/android/systemui/ScreenDecorations.class */
public class ScreenDecorations implements CoreStartable, TunerService.Tunable, Dumpable {
    public static final boolean DEBUG_COLOR;
    public static final boolean DEBUG_DISABLE_SCREEN_DECORATIONS = SystemProperties.getBoolean("debug.disable_screen_decorations", false);
    public static final boolean DEBUG_SCREENSHOT_ROUNDED_CORNERS;
    public static final int[] DISPLAY_CUTOUT_IDS;
    public CameraAvailabilityListener mCameraListener;
    public SettingObserver mColorInversionSetting;
    public final Context mContext;
    public CutoutDecorProviderFactory mCutoutFactory;
    public DisplayCutout mDisplayCutout;
    public DisplayManager.DisplayListener mDisplayListener;
    public DisplayManager mDisplayManager;
    public Display.Mode mDisplayMode;
    public String mDisplayUniqueId;
    public final DecorProviderFactory mDotFactory;
    public final PrivacyDotViewController mDotViewController;
    public DelayableExecutor mExecutor;
    public final FaceScanningProviderFactory mFaceScanningFactory;
    public Handler mHandler;
    public DisplayDecorationSupport mHwcScreenDecorationSupport;
    public boolean mIsRegistered;
    public final Executor mMainExecutor;
    public boolean mPendingConfigChange;
    public int mRotation;
    public DecorProviderFactory mRoundedCornerFactory;
    public RoundedCornerResDelegate mRoundedCornerResDelegate;
    public ScreenDecorHwcLayer mScreenDecorHwcLayer;
    public ViewGroup mScreenDecorHwcWindow;
    public final SecureSettings mSecureSettings;
    public final ThreadFactory mThreadFactory;
    public final TunerService mTunerService;
    public final UserTracker mUserTracker;
    public WindowManager mWindowManager;
    public int mProviderRefreshToken = 0;
    public OverlayWindow[] mOverlays = null;
    public int mTintColor = -16777216;
    public DisplayInfo mDisplayInfo = new DisplayInfo();
    public CameraAvailabilityListener.CameraTransitionCallback mCameraTransitionCallback = new CameraAvailabilityListener.CameraTransitionCallback() { // from class: com.android.systemui.ScreenDecorations.1
        {
            ScreenDecorations.this = this;
        }

        @Override // com.android.systemui.CameraAvailabilityListener.CameraTransitionCallback
        public void onApplyCameraProtection(Path path, Rect rect) {
            ScreenDecorations.this.showCameraProtection(path, rect);
        }

        @Override // com.android.systemui.CameraAvailabilityListener.CameraTransitionCallback
        public void onHideCameraProtection() {
            ScreenDecorations.this.hideCameraProtection();
        }
    };
    public PrivacyDotViewController.ShowingListener mPrivacyDotShowingListener = new PrivacyDotViewController.ShowingListener() { // from class: com.android.systemui.ScreenDecorations.2
        {
            ScreenDecorations.this = this;
        }

        public void onPrivacyDotHidden(View view) {
            ScreenDecorations.this.updateOverlayWindowVisibilityIfViewExists(view);
        }

        public void onPrivacyDotShown(View view) {
            ScreenDecorations.this.updateOverlayWindowVisibilityIfViewExists(view);
        }
    };
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.ScreenDecorations.6
        {
            ScreenDecorations.this = this;
        }

        public void onUserChanged(int i, Context context) {
            ScreenDecorations.this.mColorInversionSetting.setUserId(i);
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            screenDecorations.updateColorInversion(screenDecorations.mColorInversionSetting.getValue());
        }
    };
    public final int mFaceScanningViewId = R$id.face_scanning_anim;

    /* loaded from: mainsysui33.jar:com/android/systemui/ScreenDecorations$DisplayCutoutView.class */
    public static class DisplayCutoutView extends DisplayCutoutBaseView {
        public final Rect mBoundingRect;
        public final List<Rect> mBounds;
        public int mColor;
        public int mInitialPosition;
        public int mPosition;
        public int mRotation;
        public Rect mTotalBounds;

        public DisplayCutoutView(Context context, int i) {
            super(context);
            this.mBounds = new ArrayList();
            this.mBoundingRect = new Rect();
            this.mTotalBounds = new Rect();
            this.mColor = -16777216;
            this.mInitialPosition = i;
            this.paint.setColor(-16777216);
            this.paint.setStyle(Paint.Style.FILL);
        }

        public static void boundsFromDirection(DisplayCutout displayCutout, int i, Rect rect) {
            if (i == 3) {
                rect.set(displayCutout.getBoundingRectLeft());
            } else if (i == 5) {
                rect.set(displayCutout.getBoundingRectRight());
            } else if (i == 48) {
                rect.set(displayCutout.getBoundingRectTop());
            } else if (i != 80) {
                rect.setEmpty();
            } else {
                rect.set(displayCutout.getBoundingRectBottom());
            }
        }

        public final int getGravity(DisplayCutout displayCutout) {
            int i = this.mPosition;
            return i == 0 ? !displayCutout.getBoundingRectLeft().isEmpty() ? 3 : 0 : i == 1 ? !displayCutout.getBoundingRectTop().isEmpty() ? 48 : 0 : i == 3 ? !displayCutout.getBoundingRectBottom().isEmpty() ? 80 : 0 : (i != 2 || displayCutout.getBoundingRectRight().isEmpty()) ? 0 : 5;
        }

        public final boolean hasCutout() {
            DisplayCutout displayCutout = this.displayInfo.displayCutout;
            if (displayCutout == null) {
                return false;
            }
            int i = this.mPosition;
            if (i == 0) {
                return !displayCutout.getBoundingRectLeft().isEmpty();
            }
            if (i == 1) {
                return !displayCutout.getBoundingRectTop().isEmpty();
            }
            if (i == 3) {
                return !displayCutout.getBoundingRectBottom().isEmpty();
            }
            if (i == 2) {
                return !displayCutout.getBoundingRectRight().isEmpty();
            }
            return false;
        }

        public final void localBounds(Rect rect) {
            DisplayCutout displayCutout = this.displayInfo.displayCutout;
            boundsFromDirection(displayCutout, getGravity(displayCutout), rect);
        }

        @Override // android.view.View
        public void onMeasure(int i, int i2) {
            if (this.mBounds.isEmpty()) {
                super.onMeasure(i, i2);
            } else if (!this.showProtection) {
                setMeasuredDimension(View.resolveSizeAndState(this.mBoundingRect.width(), i, 0), View.resolveSizeAndState(this.mBoundingRect.height(), i2, 0));
            } else {
                this.mTotalBounds.union(this.mBoundingRect);
                Rect rect = this.mTotalBounds;
                RectF rectF = this.protectionRect;
                rect.union((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                setMeasuredDimension(View.resolveSizeAndState(this.mTotalBounds.width(), i, 0), View.resolveSizeAndState(this.mTotalBounds.height(), i2, 0));
            }
        }

        public void setColor(int i) {
            if (i == this.mColor) {
                return;
            }
            this.mColor = i;
            this.paint.setColor(i);
            invalidate();
        }

        public final void updateBoundingPath() {
            Path cutoutPath = this.displayInfo.displayCutout.getCutoutPath();
            if (cutoutPath != null) {
                this.cutoutPath.set(cutoutPath);
            } else {
                this.cutoutPath.reset();
            }
        }

        @Override // com.android.systemui.DisplayCutoutBaseView
        public void updateCutout() {
            int i;
            if (!isAttachedToWindow() || this.pendingConfigChange) {
                return;
            }
            this.mPosition = ScreenDecorations.getBoundPositionFromRotation(this.mInitialPosition, this.mRotation);
            requestLayout();
            getDisplay().getDisplayInfo(this.displayInfo);
            this.mBounds.clear();
            this.mBoundingRect.setEmpty();
            this.cutoutPath.reset();
            if (ScreenDecorations.shouldDrawCutout(getContext()) && hasCutout()) {
                this.mBounds.addAll(this.displayInfo.displayCutout.getBoundingRects());
                localBounds(this.mBoundingRect);
                updateGravity();
                updateBoundingPath();
                invalidate();
                i = 0;
            } else {
                i = 8;
            }
            if (!updateVisOnUpdateCutout() || i == getVisibility()) {
                return;
            }
            setVisibility(i);
        }

        public final void updateGravity() {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams instanceof FrameLayout.LayoutParams) {
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                int gravity = getGravity(this.displayInfo.displayCutout);
                if (layoutParams2.gravity != gravity) {
                    layoutParams2.gravity = gravity;
                    setLayoutParams(layoutParams2);
                }
            }
        }

        @Override // com.android.systemui.DisplayCutoutBaseView
        public void updateRotation(int i) {
            if (i == this.mRotation) {
                return;
            }
            this.mRotation = i;
            super.updateRotation(i);
        }

        public boolean updateVisOnUpdateCutout() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/ScreenDecorations$RestartingPreDrawListener.class */
    public class RestartingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final int mPosition;
        public final Display.Mode mTargetDisplayMode;
        public final int mTargetRotation;
        public final View mView;

        public RestartingPreDrawListener(View view, int i, int i2, Display.Mode mode) {
            ScreenDecorations.this = r4;
            this.mView = view;
            this.mTargetRotation = i2;
            this.mTargetDisplayMode = mode;
            this.mPosition = i;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
            if (this.mTargetRotation != ScreenDecorations.this.mRotation || ScreenDecorations.displayModeChanged(ScreenDecorations.this.mDisplayMode, this.mTargetDisplayMode)) {
                ScreenDecorations screenDecorations = ScreenDecorations.this;
                screenDecorations.mPendingConfigChange = false;
                screenDecorations.updateConfiguration();
                this.mView.invalidate();
                return false;
            }
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/ScreenDecorations$ValidatingPreDrawListener.class */
    public class ValidatingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final View mView;

        public ValidatingPreDrawListener(View view) {
            ScreenDecorations.this = r4;
            this.mView = view;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            ScreenDecorations.this.mContext.getDisplay().getDisplayInfo(ScreenDecorations.this.mDisplayInfo);
            DisplayInfo displayInfo = ScreenDecorations.this.mDisplayInfo;
            int i = displayInfo.rotation;
            Display.Mode mode = displayInfo.getMode();
            if ((i != ScreenDecorations.this.mRotation || ScreenDecorations.displayModeChanged(ScreenDecorations.this.mDisplayMode, mode)) && !ScreenDecorations.this.mPendingConfigChange) {
                this.mView.invalidate();
                return false;
            }
            return true;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$EBpfgCHhnMOtb8GnbJCgH41moyw(ScreenDecorations screenDecorations, String str, String str2) {
        screenDecorations.lambda$onTuningChanged$6(str, str2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda8.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$Gg_7UVS63Yp6bKAGf79wlJ4YiMQ(ScreenDecorations screenDecorations, OverlayWindow overlayWindow, DecorProvider decorProvider) {
        screenDecorations.lambda$initOverlay$4(overlayWindow, decorProvider);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$bRx4s-frKyGv-SmpobluoualBbQ */
    public static /* synthetic */ void m1284$r8$lambda$bRx4sfrKyGvSmpobluoualBbQ(ScreenDecorations screenDecorations) {
        screenDecorations.startOnScreenDecorationsThread();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda5.run():void] */
    /* renamed from: $r8$lambda$hyxgT-WcomXqgiXR_O4V9CBrLkg */
    public static /* synthetic */ void m1285$r8$lambda$hyxgTWcomXqgiXR_O4V9CBrLkg(ScreenDecorations screenDecorations) {
        screenDecorations.lambda$setupDecorationsInner$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$mS1cGu9FOirCfLAMG-lrza65QcE */
    public static /* synthetic */ void m1286$r8$lambda$mS1cGu9FOirCfLAMGlrza65QcE(ScreenDecorations screenDecorations, View view) {
        screenDecorations.lambda$updateOverlayWindowVisibilityIfViewExists$1(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$qppbcf08MzRppGcG4FLf8bkvkUU(ScreenDecorations screenDecorations) {
        screenDecorations.lambda$setupDecorationsInner$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$uYH1or4L3Mcd_MWqUF-O0c0FrlA */
    public static /* synthetic */ void m1287$r8$lambda$uYH1or4L3Mcd_MWqUFO0c0FrlA(ScreenDecorations screenDecorations) {
        screenDecorations.lambda$onConfigurationChanged$5();
    }

    static {
        boolean z = SystemProperties.getBoolean("debug.screenshot_rounded_corners", false);
        DEBUG_SCREENSHOT_ROUNDED_CORNERS = z;
        DEBUG_COLOR = z;
        DISPLAY_CUTOUT_IDS = new int[]{R$id.display_cutout, R$id.display_cutout_left, R$id.display_cutout_right, R$id.display_cutout_bottom};
    }

    public ScreenDecorations(Context context, Executor executor, SecureSettings secureSettings, TunerService tunerService, UserTracker userTracker, PrivacyDotViewController privacyDotViewController, ThreadFactory threadFactory, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory, FaceScanningProviderFactory faceScanningProviderFactory) {
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mTunerService = tunerService;
        this.mUserTracker = userTracker;
        this.mDotViewController = privacyDotViewController;
        this.mThreadFactory = threadFactory;
        this.mDotFactory = privacyDotDecorProviderFactory;
        this.mFaceScanningFactory = faceScanningProviderFactory;
    }

    public static String alphaInterpretationToString(int i) {
        if (i != 0) {
            if (i != 1) {
                return "Unknown: " + i;
            }
            return "MASK";
        }
        return "COVERAGE";
    }

    public static boolean displayModeChanged(Display.Mode mode, Display.Mode mode2) {
        if (mode == null) {
            return true;
        }
        boolean z = true;
        if (mode.getPhysicalWidth() == mode2.getPhysicalWidth()) {
            z = mode.getPhysicalHeight() != mode2.getPhysicalHeight();
        }
        return z;
    }

    public static boolean eq(DisplayDecorationSupport displayDecorationSupport, DisplayDecorationSupport displayDecorationSupport2) {
        boolean z = true;
        if (displayDecorationSupport == null) {
            if (displayDecorationSupport2 != null) {
                z = false;
            }
            return z;
        } else if (displayDecorationSupport2 == null) {
            return false;
        } else {
            return displayDecorationSupport.format == displayDecorationSupport2.format && displayDecorationSupport.alphaInterpretation == displayDecorationSupport2.alphaInterpretation;
        }
    }

    public static int getBoundPositionFromRotation(int i, int i2) {
        int i3 = i - i2;
        int i4 = i3;
        if (i3 < 0) {
            i4 = i3 + 4;
        }
        return i4;
    }

    public static String getWindowTitleByPos(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        return "ScreenDecorOverlayBottom";
                    }
                    throw new IllegalArgumentException("unknown bound position: " + i);
                }
                return "ScreenDecorOverlayRight";
            }
            return "ScreenDecorOverlay";
        }
        return "ScreenDecorOverlayLeft";
    }

    public /* synthetic */ void lambda$hideCameraProtection$0(FaceScanningOverlay faceScanningOverlay) {
        Trace.beginSection("ScreenDecorations#hideOverlayRunnable");
        updateOverlayWindowVisibilityIfViewExists(faceScanningOverlay.findViewById(this.mFaceScanningViewId));
        Trace.endSection();
    }

    public /* synthetic */ void lambda$initOverlay$4(OverlayWindow overlayWindow, DecorProvider decorProvider) {
        if (overlayWindow.getView(decorProvider.getViewId()) != null) {
            return;
        }
        removeOverlayView(decorProvider.getViewId());
        overlayWindow.addDecorProvider(decorProvider, this.mRotation, this.mTintColor);
    }

    public /* synthetic */ void lambda$onConfigurationChanged$5() {
        Trace.beginSection("ScreenDecorations#onConfigurationChanged");
        this.mPendingConfigChange = false;
        updateConfiguration();
        setupDecorations();
        if (this.mOverlays != null) {
            updateLayoutParams();
        }
        Trace.endSection();
    }

    public /* synthetic */ void lambda$onTuningChanged$6(String str, String str2) {
        if (this.mOverlays == null || !"sysui_rounded_size".equals(str)) {
            return;
        }
        Trace.beginSection("ScreenDecorations#onTuningChanged");
        try {
            this.mRoundedCornerResDelegate.setTuningSizeFactor(Integer.valueOf(Integer.parseInt(str2)));
        } catch (NumberFormatException e) {
            this.mRoundedCornerResDelegate.setTuningSizeFactor(null);
        }
        updateOverlayProviderViews(new Integer[]{Integer.valueOf(R$id.rounded_corner_top_left), Integer.valueOf(R$id.rounded_corner_top_right), Integer.valueOf(R$id.rounded_corner_bottom_left), Integer.valueOf(R$id.rounded_corner_bottom_right)});
        updateHwLayerRoundedCornerExistAndSize();
        Trace.endSection();
    }

    public /* synthetic */ void lambda$setupDecorationsInner$2() {
        Trace.beginSection("ScreenDecorations#addTunable");
        this.mTunerService.addTunable(this, new String[]{"sysui_rounded_size"});
        Trace.endSection();
    }

    public /* synthetic */ void lambda$setupDecorationsInner$3() {
        Trace.beginSection("ScreenDecorations#removeTunable");
        this.mTunerService.removeTunable(this);
        Trace.endSection();
    }

    public /* synthetic */ void lambda$updateOverlayWindowVisibilityIfViewExists$1(View view) {
        OverlayWindow[] overlayWindowArr;
        if (this.mOverlays == null || !shouldOptimizeVisibility()) {
            return;
        }
        Trace.beginSection("ScreenDecorations#updateOverlayWindowVisibilityIfViewExists");
        for (OverlayWindow overlayWindow : this.mOverlays) {
            if (overlayWindow != null && overlayWindow.getView(view.getId()) != null) {
                overlayWindow.getRootView().setVisibility(getWindowVisibility(overlayWindow, true));
                Trace.endSection();
                return;
            }
        }
        Trace.endSection();
    }

    public static boolean shouldDrawCutout(Context context) {
        return DisplayCutout.getFillBuiltInDisplayCutout(context.getResources(), context.getDisplay().getUniqueId());
    }

    public final void createHwcOverlay() {
        if (this.mScreenDecorHwcWindow != null) {
            return;
        }
        this.mScreenDecorHwcWindow = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R$layout.screen_decor_hwc_layer, (ViewGroup) null);
        ScreenDecorHwcLayer screenDecorHwcLayer = new ScreenDecorHwcLayer(this.mContext, this.mHwcScreenDecorationSupport);
        this.mScreenDecorHwcLayer = screenDecorHwcLayer;
        this.mScreenDecorHwcWindow.addView(screenDecorHwcLayer, new FrameLayout.LayoutParams(-1, -1, 8388659));
        this.mWindowManager.addView(this.mScreenDecorHwcWindow, getHwcWindowLayoutParams());
        updateHwLayerRoundedCornerExistAndSize();
        updateHwLayerRoundedCornerDrawable();
        this.mScreenDecorHwcWindow.getViewTreeObserver().addOnPreDrawListener(new ValidatingPreDrawListener(this.mScreenDecorHwcWindow));
    }

    public final void createOverlay(int i, List<DecorProvider> list, boolean z) {
        if (this.mOverlays == null) {
            this.mOverlays = new OverlayWindow[4];
        }
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        OverlayWindow overlayWindow = overlayWindowArr[i];
        if (overlayWindow != null) {
            initOverlay(overlayWindow, list, z);
            return;
        }
        overlayWindowArr[i] = new OverlayWindow(this.mContext);
        initOverlay(this.mOverlays[i], list, z);
        final ViewGroup rootView = this.mOverlays[i].getRootView();
        rootView.setSystemUiVisibility(RecyclerView.ViewHolder.FLAG_TMP_DETACHED);
        rootView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        rootView.setForceDarkAllowed(false);
        this.mWindowManager.addView(rootView, getWindowLayoutParams(i));
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.ScreenDecorations.5
            {
                ScreenDecorations.this = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                rootView.removeOnLayoutChangeListener(this);
                rootView.animate().alpha(1.0f).setDuration(1000L).start();
            }
        });
        rootView.getRootView().getViewTreeObserver().addOnPreDrawListener(new ValidatingPreDrawListener(rootView.getRootView()));
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ScreenDecorations state:");
        StringBuilder sb = new StringBuilder();
        sb.append("  DEBUG_DISABLE_SCREEN_DECORATIONS:");
        boolean z = DEBUG_DISABLE_SCREEN_DECORATIONS;
        sb.append(z);
        printWriter.println(sb.toString());
        if (z) {
            return;
        }
        printWriter.println("  mIsPrivacyDotEnabled:" + isPrivacyDotEnabled());
        printWriter.println("  shouldOptimizeOverlayVisibility:" + shouldOptimizeVisibility());
        boolean hasProviders = this.mFaceScanningFactory.getHasProviders();
        printWriter.println("    supportsShowingFaceScanningAnim:" + hasProviders);
        if (hasProviders) {
            printWriter.println("      canShowFaceScanningAnim:" + this.mFaceScanningFactory.canShowFaceScanningAnim());
            printWriter.println("      shouldShowFaceScanningAnim (at time dump was taken):" + this.mFaceScanningFactory.shouldShowFaceScanningAnim());
        }
        printWriter.println("  mPendingConfigChange:" + this.mPendingConfigChange);
        if (this.mHwcScreenDecorationSupport != null) {
            printWriter.println("  mHwcScreenDecorationSupport:");
            printWriter.println("    format=" + PixelFormat.formatToString(this.mHwcScreenDecorationSupport.format));
            printWriter.println("    alphaInterpretation=" + alphaInterpretationToString(this.mHwcScreenDecorationSupport.alphaInterpretation));
        } else {
            printWriter.println("  mHwcScreenDecorationSupport: null");
        }
        if (this.mScreenDecorHwcLayer != null) {
            printWriter.println("  mScreenDecorHwcLayer:");
            printWriter.println("    transparentRegion=" + this.mScreenDecorHwcLayer.transparentRect);
        } else {
            printWriter.println("  mScreenDecorHwcLayer: null");
        }
        if (this.mOverlays != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("  mOverlays(left,top,right,bottom)=(");
            sb2.append(this.mOverlays[0] != null);
            sb2.append(",");
            sb2.append(this.mOverlays[1] != null);
            sb2.append(",");
            sb2.append(this.mOverlays[2] != null);
            sb2.append(",");
            sb2.append(this.mOverlays[3] != null);
            sb2.append(")");
            printWriter.println(sb2.toString());
            for (int i = 0; i < 4; i++) {
                OverlayWindow overlayWindow = this.mOverlays[i];
                if (overlayWindow != null) {
                    overlayWindow.dump(printWriter, getWindowTitleByPos(i));
                }
            }
        }
        this.mRoundedCornerResDelegate.dump(printWriter, strArr);
    }

    public CutoutDecorProviderFactory getCutoutFactory() {
        return new CutoutDecorProviderFactory(this.mContext.getResources(), this.mContext.getDisplay());
    }

    public final int getHeightLayoutParamByPos(int i) {
        int boundPositionFromRotation = getBoundPositionFromRotation(i, this.mRotation);
        return (boundPositionFromRotation == 1 || boundPositionFromRotation == 3) ? -2 : -1;
    }

    public final WindowManager.LayoutParams getHwcWindowLayoutParams() {
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
        windowLayoutBaseParams.width = -1;
        windowLayoutBaseParams.height = -1;
        windowLayoutBaseParams.setTitle("ScreenDecorHwcOverlay");
        windowLayoutBaseParams.gravity = 8388659;
        if (!DEBUG_COLOR) {
            windowLayoutBaseParams.setColorMode(4);
        }
        return windowLayoutBaseParams;
    }

    public View getOverlayView(int i) {
        View view;
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null) {
            return null;
        }
        for (OverlayWindow overlayWindow : overlayWindowArr) {
            if (overlayWindow != null && (view = overlayWindow.getView(i)) != null) {
                return view;
            }
        }
        return null;
    }

    public final int getOverlayWindowGravity(int i) {
        int boundPositionFromRotation = getBoundPositionFromRotation(i, this.mRotation);
        if (boundPositionFromRotation != 0) {
            if (boundPositionFromRotation != 1) {
                if (boundPositionFromRotation != 2) {
                    if (boundPositionFromRotation == 3) {
                        return 80;
                    }
                    throw new IllegalArgumentException("unknown bound position: " + i);
                }
                return 5;
            }
            return 48;
        }
        return 3;
    }

    public float getPhysicalPixelDisplaySizeRatio() {
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        Display.Mode maximumResolutionDisplayMode = DisplayUtils.getMaximumResolutionDisplayMode(this.mDisplayInfo.supportedModes);
        if (maximumResolutionDisplayMode == null) {
            return 1.0f;
        }
        return DisplayUtils.getPhysicalPixelDisplaySizeRatio(maximumResolutionDisplayMode.getPhysicalWidth(), maximumResolutionDisplayMode.getPhysicalHeight(), this.mDisplayInfo.getNaturalWidth(), this.mDisplayInfo.getNaturalHeight());
    }

    public final List<DecorProvider> getProviders(boolean z) {
        ArrayList arrayList = new ArrayList(this.mDotFactory.getProviders());
        arrayList.addAll(this.mFaceScanningFactory.getProviders());
        if (!z) {
            arrayList.addAll(this.mRoundedCornerFactory.getProviders());
            arrayList.addAll(this.mCutoutFactory.getProviders());
        }
        return arrayList;
    }

    public final int getWidthLayoutParamByPos(int i) {
        int boundPositionFromRotation = getBoundPositionFromRotation(i, this.mRotation);
        return (boundPositionFromRotation == 1 || boundPositionFromRotation == 3) ? -1 : -2;
    }

    public final WindowManager.LayoutParams getWindowLayoutBaseParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2024, 545259832, -3);
        int i = layoutParams.privateFlags | 80 | 536870912;
        layoutParams.privateFlags = i;
        if (!DEBUG_SCREENSHOT_ROUNDED_CORNERS) {
            layoutParams.privateFlags = i | 1048576;
        }
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.privateFlags |= 16777216;
        return layoutParams;
    }

    public WindowManager.LayoutParams getWindowLayoutParams(int i) {
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
        windowLayoutBaseParams.width = getWidthLayoutParamByPos(i);
        windowLayoutBaseParams.height = getHeightLayoutParamByPos(i);
        windowLayoutBaseParams.setTitle(getWindowTitleByPos(i));
        windowLayoutBaseParams.gravity = getOverlayWindowGravity(i);
        return windowLayoutBaseParams;
    }

    public final int getWindowVisibility(OverlayWindow overlayWindow, boolean z) {
        if (z) {
            int i = R$id.privacy_dot_top_left_container;
            int i2 = R$id.privacy_dot_top_right_container;
            int i3 = R$id.privacy_dot_bottom_left_container;
            int i4 = R$id.privacy_dot_bottom_right_container;
            int i5 = this.mFaceScanningViewId;
            for (int i6 = 0; i6 < 5; i6++) {
                View view = overlayWindow.getView(new int[]{i, i2, i3, i4, i5}[i6]);
                if (view != null && view.getVisibility() == 0) {
                    return 0;
                }
            }
            return 4;
        }
        return 0;
    }

    public final boolean hasHwcOverlay() {
        return this.mScreenDecorHwcWindow != null;
    }

    public boolean hasOverlays() {
        if (this.mOverlays == null) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (this.mOverlays[i] != null) {
                return true;
            }
        }
        this.mOverlays = null;
        return false;
    }

    public final boolean hasRoundedCorners() {
        return this.mRoundedCornerFactory.getHasProviders();
    }

    public boolean hasSameProviders(List<DecorProvider> list) {
        ArrayList arrayList = new ArrayList();
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr != null) {
            for (OverlayWindow overlayWindow : overlayWindowArr) {
                if (overlayWindow != null) {
                    arrayList.addAll(overlayWindow.getViewIds());
                }
            }
        }
        if (arrayList.size() != list.size()) {
            return false;
        }
        for (DecorProvider decorProvider : list) {
            if (!arrayList.contains(Integer.valueOf(decorProvider.getViewId()))) {
                return false;
            }
        }
        return true;
    }

    public void hideCameraProtection() {
        final FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) getOverlayView(this.mFaceScanningViewId);
        if (faceScanningOverlay != null) {
            faceScanningOverlay.setHideOverlayRunnable(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenDecorations.this.lambda$hideCameraProtection$0(faceScanningOverlay);
                }
            });
            faceScanningOverlay.enableShowProtection(false);
        }
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        if (screenDecorHwcLayer != null) {
            screenDecorHwcLayer.enableShowProtection(false);
            return;
        }
        int i = 0;
        for (int i2 : DISPLAY_CUTOUT_IDS) {
            View overlayView = getOverlayView(i2);
            if (overlayView instanceof DisplayCutoutView) {
                i++;
                ((DisplayCutoutView) overlayView).enableShowProtection(false);
            }
        }
        if (i == 0) {
            Log.e("ScreenDecorations", "CutoutView not initialized hideCameraProtection");
        }
    }

    public final void initOverlay(final OverlayWindow overlayWindow, List<DecorProvider> list, boolean z) {
        if (!overlayWindow.hasSameProviders(list)) {
            list.forEach(new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ScreenDecorations.$r8$lambda$Gg_7UVS63Yp6bKAGf79wlJ4YiMQ(ScreenDecorations.this, overlayWindow, (DecorProvider) obj);
                }
            });
        }
        overlayWindow.getRootView().setVisibility(getWindowVisibility(overlayWindow, z));
    }

    public final boolean isPrivacyDotEnabled() {
        return this.mDotFactory.getHasProviders();
    }

    @Override // com.android.systemui.CoreStartable
    public void onConfigurationChanged(Configuration configuration) {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
        } else {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenDecorations.m1287$r8$lambda$uYH1or4L3Mcd_MWqUFO0c0FrlA(ScreenDecorations.this);
                }
            });
        }
    }

    public void onTuningChanged(final String str, final String str2) {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
        } else {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenDecorations.$r8$lambda$EBpfgCHhnMOtb8GnbJCgH41moyw(ScreenDecorations.this, str, str2);
                }
            });
        }
    }

    public final void removeAllOverlays() {
        if (this.mOverlays == null) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (this.mOverlays[i] != null) {
                removeOverlay(i);
            }
        }
        this.mOverlays = null;
    }

    public final void removeHwcOverlay() {
        ViewGroup viewGroup = this.mScreenDecorHwcWindow;
        if (viewGroup == null) {
            return;
        }
        this.mWindowManager.removeViewImmediate(viewGroup);
        this.mScreenDecorHwcWindow = null;
        this.mScreenDecorHwcLayer = null;
    }

    public final void removeOverlay(int i) {
        OverlayWindow overlayWindow;
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null || (overlayWindow = overlayWindowArr[i]) == null) {
            return;
        }
        this.mWindowManager.removeViewImmediate(overlayWindow.getRootView());
        this.mOverlays[i] = null;
    }

    public final void removeOverlayView(int i) {
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null) {
            return;
        }
        for (OverlayWindow overlayWindow : overlayWindowArr) {
            if (overlayWindow != null) {
                overlayWindow.removeView(i);
            }
        }
    }

    public final void removeRedundantOverlayViews(List<DecorProvider> list) {
        OverlayWindow[] overlayWindowArr;
        if (this.mOverlays == null) {
            return;
        }
        int[] array = list.stream().mapToInt(new ToIntFunction() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((DecorProvider) obj).getViewId();
            }
        }).toArray();
        for (OverlayWindow overlayWindow : this.mOverlays) {
            if (overlayWindow != null) {
                overlayWindow.removeRedundantViews(array);
            }
        }
    }

    public void setSize(View view, Size size) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = size.getWidth();
        layoutParams.height = size.getHeight();
        view.setLayoutParams(layoutParams);
    }

    public final void setupCameraListener() {
        if (this.mContext.getResources().getBoolean(R$bool.config_enableDisplayCutoutProtection)) {
            CameraAvailabilityListener build = CameraAvailabilityListener.Factory.build(this.mContext, this.mExecutor);
            this.mCameraListener = build;
            build.addTransitionCallback(this.mCameraTransitionCallback);
            this.mCameraListener.startListening();
        }
    }

    public final void setupDecorations() {
        Trace.beginSection("ScreenDecorations#setupDecorations");
        setupDecorationsInner();
        Trace.endSection();
    }

    public final void setupDecorationsInner() {
        View overlayView;
        View overlayView2;
        View overlayView3;
        if (hasRoundedCorners() || shouldDrawCutout() || isPrivacyDotEnabled() || this.mFaceScanningFactory.getHasProviders()) {
            List<DecorProvider> providers = getProviders(this.mHwcScreenDecorationSupport != null);
            removeRedundantOverlayViews(providers);
            if (this.mHwcScreenDecorationSupport != null) {
                createHwcOverlay();
            } else {
                removeHwcOverlay();
            }
            boolean[] zArr = new boolean[4];
            boolean shouldOptimizeVisibility = shouldOptimizeVisibility();
            while (true) {
                Integer properBound = DecorProviderKt.getProperBound(providers);
                if (properBound == null) {
                    break;
                }
                zArr[properBound.intValue()] = true;
                Pair<List<DecorProvider>, List<DecorProvider>> partitionAlignedBound = DecorProviderKt.partitionAlignedBound(providers, properBound.intValue());
                providers = (List) partitionAlignedBound.getSecond();
                createOverlay(properBound.intValue(), (List) partitionAlignedBound.getFirst(), shouldOptimizeVisibility);
            }
            for (int i = 0; i < 4; i++) {
                if (!zArr[i]) {
                    removeOverlay(i);
                }
            }
            if (shouldOptimizeVisibility) {
                this.mDotViewController.setShowingListener(this.mPrivacyDotShowingListener);
            } else {
                this.mDotViewController.setShowingListener((PrivacyDotViewController.ShowingListener) null);
            }
            View overlayView4 = getOverlayView(R$id.privacy_dot_top_left_container);
            if (overlayView4 != null && (overlayView = getOverlayView(R$id.privacy_dot_top_right_container)) != null && (overlayView2 = getOverlayView(R$id.privacy_dot_bottom_left_container)) != null && (overlayView3 = getOverlayView(R$id.privacy_dot_bottom_right_container)) != null) {
                this.mDotViewController.initialize(overlayView4, overlayView, overlayView2, overlayView3);
            }
        } else {
            removeAllOverlays();
            removeHwcOverlay();
        }
        if (!hasOverlays() && !hasHwcOverlay()) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenDecorations.$r8$lambda$qppbcf08MzRppGcG4FLf8bkvkUU(ScreenDecorations.this);
                }
            });
            SettingObserver settingObserver = this.mColorInversionSetting;
            if (settingObserver != null) {
                settingObserver.setListening(false);
            }
            this.mUserTracker.removeCallback(this.mUserChangedCallback);
            this.mIsRegistered = false;
        } else if (!this.mIsRegistered) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenDecorations.m1285$r8$lambda$hyxgTWcomXqgiXR_O4V9CBrLkg(ScreenDecorations.this);
                }
            });
            if (this.mColorInversionSetting == null) {
                this.mColorInversionSetting = new SettingObserver(this.mSecureSettings, this.mHandler, "accessibility_display_inversion_enabled", this.mUserTracker.getUserId()) { // from class: com.android.systemui.ScreenDecorations.4
                    {
                        ScreenDecorations.this = this;
                    }

                    @Override // com.android.systemui.qs.SettingObserver
                    public void handleValueChanged(int i2, boolean z) {
                        ScreenDecorations.this.updateColorInversion(i2);
                    }
                };
            }
            this.mColorInversionSetting.setListening(true);
            this.mColorInversionSetting.onChange(false);
            updateColorInversion(this.mColorInversionSetting.getValue());
            this.mUserTracker.addCallback(this.mUserChangedCallback, this.mExecutor);
            this.mIsRegistered = true;
        }
    }

    public final boolean shouldDrawCutout() {
        return this.mCutoutFactory.getHasProviders();
    }

    public final boolean shouldOptimizeVisibility() {
        return (isPrivacyDotEnabled() || this.mFaceScanningFactory.getHasProviders()) && !(this.mHwcScreenDecorationSupport == null && (hasRoundedCorners() || shouldDrawCutout()));
    }

    public void showCameraProtection(Path path, Rect rect) {
        DisplayCutoutView displayCutoutView;
        if (this.mFaceScanningFactory.shouldShowFaceScanningAnim() && (displayCutoutView = (DisplayCutoutView) getOverlayView(this.mFaceScanningViewId)) != null) {
            displayCutoutView.setProtection(path, rect);
            displayCutoutView.enableShowProtection(true);
            updateOverlayWindowVisibilityIfViewExists(displayCutoutView.findViewById(this.mFaceScanningViewId));
            return;
        }
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        if (screenDecorHwcLayer != null) {
            screenDecorHwcLayer.setProtection(path, rect);
            this.mScreenDecorHwcLayer.enableShowProtection(true);
            return;
        }
        int i = 0;
        for (int i2 : DISPLAY_CUTOUT_IDS) {
            View overlayView = getOverlayView(i2);
            if (overlayView instanceof DisplayCutoutView) {
                i++;
                DisplayCutoutView displayCutoutView2 = (DisplayCutoutView) overlayView;
                displayCutoutView2.setProtection(path, rect);
                displayCutoutView2.enableShowProtection(true);
            }
        }
        if (i == 0) {
            Log.e("ScreenDecorations", "CutoutView not initialized showCameraProtection");
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
            return;
        }
        Handler buildHandlerOnNewThread = this.mThreadFactory.buildHandlerOnNewThread("ScreenDecorations");
        this.mHandler = buildHandlerOnNewThread;
        DelayableExecutor buildDelayableExecutorOnHandler = this.mThreadFactory.buildDelayableExecutorOnHandler(buildHandlerOnNewThread);
        this.mExecutor = buildDelayableExecutorOnHandler;
        buildDelayableExecutorOnHandler.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ScreenDecorations.m1284$r8$lambda$bRx4sfrKyGvSmpobluoualBbQ(ScreenDecorations.this);
            }
        });
        this.mDotViewController.setUiExecutor(this.mExecutor);
    }

    public final void startOnScreenDecorationsThread() {
        Trace.beginSection("ScreenDecorations#startOnScreenDecorationsThread");
        this.mWindowManager = (WindowManager) this.mContext.getSystemService(WindowManager.class);
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        DisplayInfo displayInfo = this.mDisplayInfo;
        this.mRotation = displayInfo.rotation;
        this.mDisplayMode = displayInfo.getMode();
        DisplayInfo displayInfo2 = this.mDisplayInfo;
        this.mDisplayUniqueId = displayInfo2.uniqueId;
        this.mDisplayCutout = displayInfo2.displayCutout;
        RoundedCornerResDelegate roundedCornerResDelegate = new RoundedCornerResDelegate(this.mContext.getResources(), this.mDisplayUniqueId);
        this.mRoundedCornerResDelegate = roundedCornerResDelegate;
        roundedCornerResDelegate.setPhysicalPixelDisplaySizeRatio(getPhysicalPixelDisplaySizeRatio());
        this.mRoundedCornerFactory = new RoundedCornerDecorProviderFactory(this.mRoundedCornerResDelegate);
        this.mCutoutFactory = getCutoutFactory();
        this.mHwcScreenDecorationSupport = this.mContext.getDisplay().getDisplayDecorationSupport();
        updateHwLayerRoundedCornerDrawable();
        setupDecorations();
        setupCameraListener();
        DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.ScreenDecorations.3
            {
                ScreenDecorations.this = this;
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                ScreenDecorations.this.mContext.getDisplay().getDisplayInfo(ScreenDecorations.this.mDisplayInfo);
                DisplayInfo displayInfo3 = ScreenDecorations.this.mDisplayInfo;
                int i2 = displayInfo3.rotation;
                Display.Mode mode = displayInfo3.getMode();
                ScreenDecorations screenDecorations = ScreenDecorations.this;
                if ((screenDecorations.mOverlays != null || screenDecorations.mScreenDecorHwcWindow != null) && (screenDecorations.mRotation != i2 || ScreenDecorations.displayModeChanged(ScreenDecorations.this.mDisplayMode, mode))) {
                    ScreenDecorations screenDecorations2 = ScreenDecorations.this;
                    screenDecorations2.mPendingConfigChange = true;
                    if (screenDecorations2.mOverlays != null) {
                        for (int i3 = 0; i3 < 4; i3++) {
                            OverlayWindow overlayWindow = ScreenDecorations.this.mOverlays[i3];
                            if (overlayWindow != null) {
                                ViewGroup rootView = overlayWindow.getRootView();
                                rootView.getViewTreeObserver().addOnPreDrawListener(new RestartingPreDrawListener(rootView, i3, i2, mode));
                            }
                        }
                    }
                    ViewGroup viewGroup = ScreenDecorations.this.mScreenDecorHwcWindow;
                    if (viewGroup != null) {
                        ViewTreeObserver viewTreeObserver = viewGroup.getViewTreeObserver();
                        ScreenDecorations screenDecorations3 = ScreenDecorations.this;
                        viewTreeObserver.addOnPreDrawListener(new RestartingPreDrawListener(screenDecorations3.mScreenDecorHwcWindow, -1, i2, mode));
                    }
                    ScreenDecorHwcLayer screenDecorHwcLayer = ScreenDecorations.this.mScreenDecorHwcLayer;
                    if (screenDecorHwcLayer != null) {
                        screenDecorHwcLayer.pendingConfigChange = true;
                    }
                }
                ScreenDecorations screenDecorations4 = ScreenDecorations.this;
                String str = screenDecorations4.mDisplayInfo.uniqueId;
                if (Objects.equals(str, screenDecorations4.mDisplayUniqueId)) {
                    return;
                }
                ScreenDecorations screenDecorations5 = ScreenDecorations.this;
                screenDecorations5.mDisplayUniqueId = str;
                DisplayDecorationSupport displayDecorationSupport = screenDecorations5.mContext.getDisplay().getDisplayDecorationSupport();
                ScreenDecorations.this.mRoundedCornerResDelegate.updateDisplayUniqueId(str, null);
                ScreenDecorations screenDecorations6 = ScreenDecorations.this;
                if (screenDecorations6.hasSameProviders(screenDecorations6.getProviders(displayDecorationSupport != null)) && ScreenDecorations.eq(displayDecorationSupport, ScreenDecorations.this.mHwcScreenDecorationSupport)) {
                    return;
                }
                ScreenDecorations screenDecorations7 = ScreenDecorations.this;
                screenDecorations7.mHwcScreenDecorationSupport = displayDecorationSupport;
                screenDecorations7.removeAllOverlays();
                ScreenDecorations.this.setupDecorations();
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }
        };
        this.mDisplayListener = displayListener;
        this.mDisplayManager.registerDisplayListener(displayListener, this.mHandler);
        updateConfiguration();
        Trace.endSection();
    }

    public final void updateColorInversion(int i) {
        this.mTintColor = i != 0 ? -1 : -16777216;
        if (DEBUG_COLOR) {
            this.mTintColor = -65536;
        }
        updateOverlayProviderViews(new Integer[]{Integer.valueOf(this.mFaceScanningViewId), Integer.valueOf(R$id.display_cutout), Integer.valueOf(R$id.display_cutout_left), Integer.valueOf(R$id.display_cutout_right), Integer.valueOf(R$id.display_cutout_bottom), Integer.valueOf(R$id.rounded_corner_top_left), Integer.valueOf(R$id.rounded_corner_top_right), Integer.valueOf(R$id.rounded_corner_bottom_left), Integer.valueOf(R$id.rounded_corner_bottom_right)});
    }

    public void updateConfiguration() {
        boolean z = this.mHandler.getLooper().getThread() == Thread.currentThread();
        Preconditions.checkState(z, "must call on " + this.mHandler.getLooper().getThread() + ", but was " + Thread.currentThread());
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        int i = this.mDisplayInfo.rotation;
        if (this.mRotation != i) {
            this.mDotViewController.setNewRotation(i);
        }
        Display.Mode mode = this.mDisplayInfo.getMode();
        DisplayCutout displayCutout = this.mDisplayInfo.displayCutout;
        if (!this.mPendingConfigChange && (i != this.mRotation || displayModeChanged(this.mDisplayMode, mode) || !Objects.equals(displayCutout, this.mDisplayCutout))) {
            this.mRotation = i;
            this.mDisplayMode = mode;
            this.mDisplayCutout = displayCutout;
            this.mRoundedCornerResDelegate.setPhysicalPixelDisplaySizeRatio(getPhysicalPixelDisplaySizeRatio());
            ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
            if (screenDecorHwcLayer != null) {
                screenDecorHwcLayer.pendingConfigChange = false;
                screenDecorHwcLayer.updateConfiguration(this.mDisplayUniqueId);
                updateHwLayerRoundedCornerExistAndSize();
                updateHwLayerRoundedCornerDrawable();
            }
            updateLayoutParams();
            updateOverlayProviderViews(null);
        }
        FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) getOverlayView(this.mFaceScanningViewId);
        if (faceScanningOverlay != null) {
            faceScanningOverlay.setFaceScanningAnimColor(Utils.getColorAttrDefaultColor(faceScanningOverlay.getContext(), R$attr.wallpaperTextColorAccent));
        }
    }

    public final void updateHwLayerRoundedCornerDrawable() {
        if (this.mScreenDecorHwcLayer == null) {
            return;
        }
        Drawable topRoundedDrawable = this.mRoundedCornerResDelegate.getTopRoundedDrawable();
        Drawable bottomRoundedDrawable = this.mRoundedCornerResDelegate.getBottomRoundedDrawable();
        if (topRoundedDrawable == null || bottomRoundedDrawable == null) {
            return;
        }
        this.mScreenDecorHwcLayer.updateRoundedCornerDrawable(topRoundedDrawable, bottomRoundedDrawable);
    }

    public final void updateHwLayerRoundedCornerExistAndSize() {
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        if (screenDecorHwcLayer == null) {
            return;
        }
        screenDecorHwcLayer.updateRoundedCornerExistenceAndSize(this.mRoundedCornerResDelegate.getHasTop(), this.mRoundedCornerResDelegate.getHasBottom(), this.mRoundedCornerResDelegate.getTopRoundedSize().getWidth(), this.mRoundedCornerResDelegate.getBottomRoundedSize().getWidth());
    }

    public final void updateLayoutParams() {
        Trace.beginSection("ScreenDecorations#updateLayoutParams");
        ViewGroup viewGroup = this.mScreenDecorHwcWindow;
        if (viewGroup != null) {
            this.mWindowManager.updateViewLayout(viewGroup, getHwcWindowLayoutParams());
        }
        if (this.mOverlays != null) {
            for (int i = 0; i < 4; i++) {
                OverlayWindow overlayWindow = this.mOverlays[i];
                if (overlayWindow != null) {
                    this.mWindowManager.updateViewLayout(overlayWindow.getRootView(), getWindowLayoutParams(i));
                }
            }
        }
        Trace.endSection();
    }

    public void updateOverlayProviderViews(Integer[] numArr) {
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null) {
            return;
        }
        this.mProviderRefreshToken++;
        for (OverlayWindow overlayWindow : overlayWindowArr) {
            if (overlayWindow != null) {
                overlayWindow.onReloadResAndMeasure(numArr, this.mProviderRefreshToken, this.mRotation, this.mTintColor, this.mDisplayUniqueId);
            }
        }
    }

    public void updateOverlayWindowVisibilityIfViewExists(final View view) {
        if (view == null) {
            return;
        }
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ScreenDecorations.m1286$r8$lambda$mS1cGu9FOirCfLAMGlrza65QcE(ScreenDecorations.this, view);
            }
        });
    }
}