package com.android.systemui.accessibility;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.Choreographer;
import android.view.Display;
import android.view.IWindow;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import com.android.systemui.model.SysUiState;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/WindowMagnificationController.class */
public class WindowMagnificationController implements View.OnTouchListener, SurfaceHolder.Callback, MagnificationGestureDetector.OnGestureListener, ComponentCallbacks {
    public static final Range<Float> A11Y_ACTION_SCALE_RANGE;
    public static final boolean DEBUG;
    public final WindowMagnificationAnimationController mAnimationController;
    public int mBorderDragSize;
    public View mBottomDrag;
    public float mBounceEffectAnimationScale;
    public final int mBounceEffectDuration;
    public final Configuration mConfiguration;
    public final Context mContext;
    public final int mDisplayId;
    public View mDragView;
    public int mDragViewSize;
    public final MagnificationGestureDetector mGestureDetector;
    public final Handler mHandler;
    public View mLeftDrag;
    public Locale mLocale;
    public int mMinWindowSize;
    public SurfaceControl mMirrorSurface;
    public int mMirrorSurfaceMargin;
    public SurfaceView mMirrorSurfaceView;
    public final View.OnLayoutChangeListener mMirrorSurfaceViewLayoutChangeListener;
    public View mMirrorView;
    public final Choreographer.FrameCallback mMirrorViewGeometryVsyncCallback;
    public final View.OnLayoutChangeListener mMirrorViewLayoutChangeListener;
    public final Runnable mMirrorViewRunnable;
    public int mOuterBorderSize;
    public boolean mOverlapWithGestureInsets;
    public NumberFormat mPercentFormat;
    public final Resources mResources;
    public View mRightDrag;
    @VisibleForTesting
    public int mRotation;
    public float mScale;
    public final SfVsyncFrameCallbackProvider mSfVsyncFrameProvider;
    public final SysUiState mSysUiState;
    public View mTopDrag;
    public final SurfaceControl.Transaction mTransaction;
    public final Runnable mUpdateStateDescriptionRunnable;
    public final Rect mWindowBounds;
    public final Runnable mWindowInsetChangeRunnable;
    public final WindowMagnifierCallback mWindowMagnifierCallback;
    public final WindowManager mWm;
    public final Rect mMagnificationFrame = new Rect();
    public final Rect mTmpRect = new Rect();
    public final Rect mMirrorViewBounds = new Rect();
    public final Rect mSourceBounds = new Rect();
    public int mMagnificationFrameOffsetX = 0;
    public int mMagnificationFrameOffsetY = 0;
    public final Rect mMagnificationFrameBoundary = new Rect();
    public int mSystemGestureTop = -1;

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/WindowMagnificationController$MirrorWindowA11yDelegate.class */
    public class MirrorWindowA11yDelegate extends View.AccessibilityDelegate {
        public MirrorWindowA11yDelegate() {
            WindowMagnificationController.this = r4;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_zoom_in, WindowMagnificationController.this.mContext.getString(R$string.accessibility_control_zoom_in)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_zoom_out, WindowMagnificationController.this.mContext.getString(R$string.accessibility_control_zoom_out)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_up, WindowMagnificationController.this.mContext.getString(R$string.accessibility_control_move_up)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_down, WindowMagnificationController.this.mContext.getString(R$string.accessibility_control_move_down)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_left, WindowMagnificationController.this.mContext.getString(R$string.accessibility_control_move_left)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_right, WindowMagnificationController.this.mContext.getString(R$string.accessibility_control_move_right)));
            accessibilityNodeInfo.setContentDescription(WindowMagnificationController.this.mContext.getString(R$string.magnification_window_title));
            WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
            accessibilityNodeInfo.setStateDescription(windowMagnificationController.formatStateDescription(windowMagnificationController.getScale()));
        }

        public final boolean performA11yAction(int i) {
            if (i == R$id.accessibility_action_zoom_in) {
                WindowMagnificationController.this.mWindowMagnifierCallback.onPerformScaleAction(WindowMagnificationController.this.mDisplayId, ((Float) WindowMagnificationController.A11Y_ACTION_SCALE_RANGE.clamp(Float.valueOf(WindowMagnificationController.this.mScale + 1.0f))).floatValue());
            } else if (i == R$id.accessibility_action_zoom_out) {
                WindowMagnificationController.this.mWindowMagnifierCallback.onPerformScaleAction(WindowMagnificationController.this.mDisplayId, ((Float) WindowMagnificationController.A11Y_ACTION_SCALE_RANGE.clamp(Float.valueOf(WindowMagnificationController.this.mScale - 1.0f))).floatValue());
            } else if (i == R$id.accessibility_action_move_up) {
                WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
                windowMagnificationController.move(0, -windowMagnificationController.mSourceBounds.height());
            } else if (i == R$id.accessibility_action_move_down) {
                WindowMagnificationController windowMagnificationController2 = WindowMagnificationController.this;
                windowMagnificationController2.move(0, windowMagnificationController2.mSourceBounds.height());
            } else if (i == R$id.accessibility_action_move_left) {
                WindowMagnificationController windowMagnificationController3 = WindowMagnificationController.this;
                windowMagnificationController3.move(-windowMagnificationController3.mSourceBounds.width(), 0);
            } else if (i != R$id.accessibility_action_move_right) {
                return false;
            } else {
                WindowMagnificationController windowMagnificationController4 = WindowMagnificationController.this;
                windowMagnificationController4.move(windowMagnificationController4.mSourceBounds.width(), 0);
            }
            WindowMagnificationController.this.mWindowMagnifierCallback.onAccessibilityActionPerformed(WindowMagnificationController.this.mDisplayId);
            return true;
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (performA11yAction(i)) {
                return true;
            }
            return super.performAccessibilityAction(view, i, bundle);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda3.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    /* renamed from: $r8$lambda$8n6hr7Mj0HBKGp97lVRqU6I-5Pw */
    public static /* synthetic */ void m1367$r8$lambda$8n6hr7Mj0HBKGp97lVRqU6I5Pw(WindowMagnificationController windowMagnificationController, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        windowMagnificationController.lambda$new$2(view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$FeUT0AsUup2VJ62rwPm6oBL9B8g(WindowMagnificationController windowMagnificationController) {
        windowMagnificationController.onWindowInsetChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$VCu8Sa12Uofu4c0FLpRPyQMIdB4(WindowMagnificationController windowMagnificationController) {
        windowMagnificationController.lambda$new$4();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda0.onApplyWindowInsets(android.view.View, android.view.WindowInsets):android.view.WindowInsets] */
    public static /* synthetic */ WindowInsets $r8$lambda$ZGvueIqUwB9vaLOY8pw4tnC0rDI(WindowMagnificationController windowMagnificationController, View view, WindowInsets windowInsets) {
        return windowMagnificationController.lambda$createMirrorWindow$5(view, windowInsets);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$a5PMyX9zgVKe4eoClviczbD2c8U(WindowMagnificationController windowMagnificationController) {
        windowMagnificationController.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda4.doFrame(long):void] */
    public static /* synthetic */ void $r8$lambda$fIQCWtLiAbi9Vlvwxj3FaHZTPlI(WindowMagnificationController windowMagnificationController, long j) {
        windowMagnificationController.lambda$new$3(j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda2.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    /* renamed from: $r8$lambda$t_-5BOzoqh-sLnUhI6ySMDvxV_U */
    public static /* synthetic */ void m1368$r8$lambda$t_5BOzoqhsLnUhI6ySMDvxV_U(WindowMagnificationController windowMagnificationController, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        windowMagnificationController.lambda$new$1(view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    static {
        DEBUG = Log.isLoggable("WindowMagnificationController", 3) || Build.IS_DEBUGGABLE;
        A11Y_ACTION_SCALE_RANGE = new Range<>(Float.valueOf(2.0f), Float.valueOf(8.0f));
    }

    public WindowMagnificationController(Context context, Handler handler, WindowMagnificationAnimationController windowMagnificationAnimationController, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, MirrorWindowControl mirrorWindowControl, SurfaceControl.Transaction transaction, WindowMagnifierCallback windowMagnifierCallback, SysUiState sysUiState) {
        this.mContext = context;
        this.mHandler = handler;
        this.mAnimationController = windowMagnificationAnimationController;
        windowMagnificationAnimationController.setWindowMagnificationController(this);
        this.mSfVsyncFrameProvider = sfVsyncFrameCallbackProvider;
        this.mWindowMagnifierCallback = windowMagnifierCallback;
        this.mSysUiState = sysUiState;
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        Display display = context.getDisplay();
        this.mDisplayId = context.getDisplayId();
        this.mRotation = display.getRotation();
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mWm = windowManager;
        Rect rect = new Rect(windowManager.getCurrentWindowMetrics().getBounds());
        this.mWindowBounds = rect;
        Resources resources = context.getResources();
        this.mResources = resources;
        this.mScale = resources.getInteger(R$integer.magnification_default_scale);
        this.mBounceEffectDuration = resources.getInteger(17694720);
        updateDimensions();
        Size defaultWindowSizeWithWindowBounds = getDefaultWindowSizeWithWindowBounds(rect);
        setMagnificationFrame(defaultWindowSizeWithWindowBounds.getWidth(), defaultWindowSizeWithWindowBounds.getHeight(), rect.width() / 2, rect.height() / 2);
        computeBounceAnimationScale();
        this.mTransaction = transaction;
        this.mGestureDetector = new MagnificationGestureDetector(context, handler, this);
        this.mMirrorViewRunnable = new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationController.$r8$lambda$a5PMyX9zgVKe4eoClviczbD2c8U(WindowMagnificationController.this);
            }
        };
        this.mMirrorViewLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                WindowMagnificationController.m1368$r8$lambda$t_5BOzoqhsLnUhI6ySMDvxV_U(WindowMagnificationController.this, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        };
        this.mMirrorSurfaceViewLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                WindowMagnificationController.m1367$r8$lambda$8n6hr7Mj0HBKGp97lVRqU6I5Pw(WindowMagnificationController.this, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        };
        this.mMirrorViewGeometryVsyncCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda4
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                WindowMagnificationController.$r8$lambda$fIQCWtLiAbi9Vlvwxj3FaHZTPlI(WindowMagnificationController.this, j);
            }
        };
        this.mUpdateStateDescriptionRunnable = new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationController.$r8$lambda$VCu8Sa12Uofu4c0FLpRPyQMIdB4(WindowMagnificationController.this);
            }
        };
        this.mWindowInsetChangeRunnable = new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationController.$r8$lambda$FeUT0AsUup2VJ62rwPm6oBL9B8g(WindowMagnificationController.this);
            }
        };
    }

    public /* synthetic */ WindowInsets lambda$createMirrorWindow$5(View view, WindowInsets windowInsets) {
        if (!this.mHandler.hasCallbacks(this.mWindowInsetChangeRunnable)) {
            this.mHandler.post(this.mWindowInsetChangeRunnable);
        }
        return view.onApplyWindowInsets(windowInsets);
    }

    public /* synthetic */ void lambda$new$0() {
        if (this.mMirrorView != null) {
            Rect rect = new Rect(this.mMirrorViewBounds);
            this.mMirrorView.getBoundsOnScreen(this.mMirrorViewBounds);
            if (rect.width() != this.mMirrorViewBounds.width() || rect.height() != this.mMirrorViewBounds.height()) {
                this.mMirrorView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, this.mMirrorViewBounds.width(), this.mMirrorViewBounds.height())));
            }
            updateSystemUIStateIfNeeded();
            this.mWindowMagnifierCallback.onWindowMagnifierBoundsChanged(this.mDisplayId, this.mMirrorViewBounds);
        }
    }

    public /* synthetic */ void lambda$new$1(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.mHandler.hasCallbacks(this.mMirrorViewRunnable)) {
            return;
        }
        this.mHandler.post(this.mMirrorViewRunnable);
    }

    public /* synthetic */ void lambda$new$2(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        applyTapExcludeRegion();
    }

    public /* synthetic */ void lambda$new$3(long j) {
        if (isWindowVisible() && this.mMirrorSurface != null && calculateSourceBounds(this.mMagnificationFrame, this.mScale)) {
            this.mTmpRect.set(0, 0, this.mMagnificationFrame.width(), this.mMagnificationFrame.height());
            this.mTransaction.setGeometry(this.mMirrorSurface, this.mSourceBounds, this.mTmpRect, 0).apply();
            if (this.mAnimationController.isAnimating()) {
                return;
            }
            this.mWindowMagnifierCallback.onSourceBoundsChanged(this.mDisplayId, this.mSourceBounds);
        }
    }

    public /* synthetic */ void lambda$new$4() {
        if (isWindowVisible()) {
            this.mMirrorView.setStateDescription(formatStateDescription(this.mScale));
        }
    }

    public final void addDragTouchListeners() {
        this.mDragView = this.mMirrorView.findViewById(R$id.drag_handle);
        this.mLeftDrag = this.mMirrorView.findViewById(R$id.left_handle);
        this.mTopDrag = this.mMirrorView.findViewById(R$id.top_handle);
        this.mRightDrag = this.mMirrorView.findViewById(R$id.right_handle);
        this.mBottomDrag = this.mMirrorView.findViewById(R$id.bottom_handle);
        this.mDragView.setOnTouchListener(this);
        this.mLeftDrag.setOnTouchListener(this);
        this.mTopDrag.setOnTouchListener(this);
        this.mRightDrag.setOnTouchListener(this);
        this.mBottomDrag.setOnTouchListener(this);
    }

    public final void animateBounceEffect() {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.mMirrorView, PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, this.mBounceEffectAnimationScale, 1.0f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, this.mBounceEffectAnimationScale, 1.0f));
        ofPropertyValuesHolder.setDuration(this.mBounceEffectDuration);
        ofPropertyValuesHolder.start();
    }

    public final void applyTapExcludeRegion() {
        Region calculateTapExclude = calculateTapExclude();
        try {
            WindowManagerGlobal.getWindowSession().updateTapExcludeRegion(IWindow.Stub.asInterface(this.mMirrorView.getWindowToken()), calculateTapExclude);
        } catch (RemoteException e) {
        }
    }

    public final void calculateMagnificationFrameBoundary() {
        int width = this.mMagnificationFrame.width() / 2;
        int height = this.mMagnificationFrame.height() / 2;
        float f = width;
        float f2 = this.mScale;
        int i = (int) (f / f2);
        int i2 = (int) (height / f2);
        int i3 = width - i;
        int max = Math.max(i3 - this.mMagnificationFrameOffsetX, 0);
        int max2 = Math.max(i3 + this.mMagnificationFrameOffsetX, 0);
        int i4 = height - i2;
        this.mMagnificationFrameBoundary.set(-max, -Math.max(i4 - this.mMagnificationFrameOffsetY, 0), this.mWindowBounds.width() + max2, this.mWindowBounds.height() + Math.max(i4 + this.mMagnificationFrameOffsetY, 0));
    }

    public final boolean calculateSourceBounds(Rect rect, float f) {
        Rect rect2 = this.mTmpRect;
        rect2.set(this.mSourceBounds);
        int width = rect.width() / 2;
        int height = rect.height() / 2;
        int i = rect.left;
        int i2 = width - ((int) (width / f));
        int i3 = rect.right;
        int i4 = height - ((int) (height / f));
        this.mSourceBounds.set(i + i2, rect.top + i4, i3 - i2, rect.bottom - i4);
        this.mSourceBounds.offset(-this.mMagnificationFrameOffsetX, -this.mMagnificationFrameOffsetY);
        Rect rect3 = this.mSourceBounds;
        if (rect3.left < 0) {
            rect3.offsetTo(0, rect3.top);
        } else if (rect3.right > this.mWindowBounds.width()) {
            this.mSourceBounds.offsetTo(this.mWindowBounds.width() - this.mSourceBounds.width(), this.mSourceBounds.top);
        }
        Rect rect4 = this.mSourceBounds;
        if (rect4.top < 0) {
            rect4.offsetTo(rect4.left, 0);
        } else if (rect4.bottom > this.mWindowBounds.height()) {
            Rect rect5 = this.mSourceBounds;
            rect5.offsetTo(rect5.left, this.mWindowBounds.height() - this.mSourceBounds.height());
        }
        return !this.mSourceBounds.equals(rect2);
    }

    public final Region calculateTapExclude() {
        int i = this.mBorderDragSize;
        Region region = new Region(i, i, this.mMirrorView.getWidth() - this.mBorderDragSize, this.mMirrorView.getHeight() - this.mBorderDragSize);
        region.op(new Rect((this.mMirrorView.getWidth() - this.mDragViewSize) - this.mBorderDragSize, (this.mMirrorView.getHeight() - this.mDragViewSize) - this.mBorderDragSize, this.mMirrorView.getWidth(), this.mMirrorView.getHeight()), Region.Op.DIFFERENCE);
        return region;
    }

    public final void computeBounceAnimationScale() {
        float width = this.mMagnificationFrame.width() + (this.mMirrorSurfaceMargin * 2);
        this.mBounceEffectAnimationScale = Math.min(width / (width - (this.mOuterBorderSize * 2)), 1.05f);
    }

    public final void createMirror() {
        SurfaceControl mirrorDisplay = mirrorDisplay(this.mDisplayId);
        this.mMirrorSurface = mirrorDisplay;
        if (mirrorDisplay.isValid()) {
            this.mTransaction.show(this.mMirrorSurface).reparent(this.mMirrorSurface, this.mMirrorSurfaceView.getSurfaceControl());
            modifyWindowMagnification(false);
        }
    }

    public final void createMirrorWindow() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(this.mMagnificationFrame.width() + (this.mMirrorSurfaceMargin * 2), this.mMagnificationFrame.height() + (this.mMirrorSurfaceMargin * 2), 2039, 40, -2);
        layoutParams.gravity = 51;
        Rect rect = this.mMagnificationFrame;
        int i = rect.left;
        int i2 = this.mMirrorSurfaceMargin;
        layoutParams.x = i - i2;
        layoutParams.y = rect.top - i2;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.receiveInsetsIgnoringZOrder = true;
        layoutParams.setTitle(this.mContext.getString(R$string.magnification_window_title));
        layoutParams.accessibilityTitle = getAccessibilityWindowTitle();
        View inflate = LayoutInflater.from(this.mContext).inflate(R$layout.window_magnifier_view, (ViewGroup) null);
        this.mMirrorView = inflate;
        SurfaceView surfaceView = (SurfaceView) inflate.findViewById(R$id.surface_view);
        this.mMirrorSurfaceView = surfaceView;
        surfaceView.addOnLayoutChangeListener(this.mMirrorSurfaceViewLayoutChangeListener);
        this.mMirrorView.setSystemUiVisibility(5894);
        this.mMirrorView.addOnLayoutChangeListener(this.mMirrorViewLayoutChangeListener);
        this.mMirrorView.setAccessibilityDelegate(new MirrorWindowA11yDelegate());
        this.mMirrorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return WindowMagnificationController.$r8$lambda$ZGvueIqUwB9vaLOY8pw4tnC0rDI(WindowMagnificationController.this, view, windowInsets);
            }
        });
        this.mWm.addView(this.mMirrorView, layoutParams);
        SurfaceHolder holder = this.mMirrorSurfaceView.getHolder();
        holder.addCallback(this);
        holder.setFormat(1);
        addDragTouchListeners();
    }

    public void deleteWindowMagnification() {
        if (isWindowVisible()) {
            SurfaceControl surfaceControl = this.mMirrorSurface;
            if (surfaceControl != null) {
                this.mTransaction.remove(surfaceControl).apply();
                this.mMirrorSurface = null;
            }
            SurfaceView surfaceView = this.mMirrorSurfaceView;
            if (surfaceView != null) {
                surfaceView.removeOnLayoutChangeListener(this.mMirrorSurfaceViewLayoutChangeListener);
            }
            if (this.mMirrorView != null) {
                this.mHandler.removeCallbacks(this.mMirrorViewRunnable);
                this.mMirrorView.removeOnLayoutChangeListener(this.mMirrorViewLayoutChangeListener);
                this.mWm.removeView(this.mMirrorView);
                this.mMirrorView = null;
            }
            this.mMirrorViewBounds.setEmpty();
            this.mSourceBounds.setEmpty();
            updateSystemUIStateIfNeeded();
            this.mContext.unregisterComponentCallbacks(this);
            this.mWindowMagnifierCallback.onSourceBoundsChanged(this.mDisplayId, new Rect());
        }
    }

    public void deleteWindowMagnification(IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mAnimationController.deleteWindowMagnification(iRemoteMagnificationAnimationCallback);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("WindowMagnificationController (displayId=" + this.mDisplayId + "):");
        StringBuilder sb = new StringBuilder();
        sb.append("      mOverlapWithGestureInsets:");
        sb.append(this.mOverlapWithGestureInsets);
        printWriter.println(sb.toString());
        printWriter.println("      mScale:" + this.mScale);
        printWriter.println("      mWindowBounds:" + this.mWindowBounds);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("      mMirrorViewBounds:");
        sb2.append(isWindowVisible() ? this.mMirrorViewBounds : "empty");
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("      mMagnificationFrameBoundary:");
        sb3.append(isWindowVisible() ? this.mMagnificationFrameBoundary : "empty");
        printWriter.println(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("      mMagnificationFrame:");
        sb4.append(isWindowVisible() ? this.mMagnificationFrame : "empty");
        printWriter.println(sb4.toString());
        StringBuilder sb5 = new StringBuilder();
        sb5.append("      mSourceBounds:");
        sb5.append(this.mSourceBounds.isEmpty() ? "empty" : this.mSourceBounds);
        printWriter.println(sb5.toString());
        printWriter.println("      mSystemGestureTop:" + this.mSystemGestureTop);
        printWriter.println("      mMagnificationFrameOffsetX:" + this.mMagnificationFrameOffsetX);
        printWriter.println("      mMagnificationFrameOffsetY:" + this.mMagnificationFrameOffsetY);
    }

    public void enableWindowMagnification(float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mAnimationController.enableWindowMagnification(f, f2, f3, f4, f5, iRemoteMagnificationAnimationCallback);
    }

    public void enableWindowMagnificationInternal(float f, float f2, float f3) {
        enableWindowMagnificationInternal(f, f2, f3, Float.NaN, Float.NaN);
    }

    public void enableWindowMagnificationInternal(float f, float f2, float f3, float f4, float f5) {
        if (Float.compare(f, 1.0f) <= 0) {
            deleteWindowMagnification();
            return;
        }
        if (!isWindowVisible()) {
            onConfigurationChanged(this.mResources.getConfiguration());
            this.mContext.registerComponentCallbacks(this);
        }
        this.mMagnificationFrameOffsetX = Float.isNaN(f4) ? this.mMagnificationFrameOffsetX : (int) ((this.mMagnificationFrame.width() / 2) * f4);
        int height = Float.isNaN(f5) ? this.mMagnificationFrameOffsetY : (int) ((this.mMagnificationFrame.height() / 2) * f5);
        this.mMagnificationFrameOffsetY = height;
        float f6 = height;
        float exactCenterX = Float.isNaN(f2) ? 0.0f : (this.mMagnificationFrameOffsetX + f2) - this.mMagnificationFrame.exactCenterX();
        float exactCenterY = Float.isNaN(f3) ? 0.0f : (f6 + f3) - this.mMagnificationFrame.exactCenterY();
        float f7 = f;
        if (Float.isNaN(f)) {
            f7 = this.mScale;
        }
        this.mScale = f7;
        calculateMagnificationFrameBoundary();
        updateMagnificationFramePosition((int) exactCenterX, (int) exactCenterY);
        if (isWindowVisible()) {
            modifyWindowMagnification(false);
            return;
        }
        createMirrorWindow();
        showControls();
    }

    public final CharSequence formatStateDescription(float f) {
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (!locale.equals(this.mLocale)) {
            this.mLocale = locale;
            this.mPercentFormat = NumberFormat.getPercentInstance(locale);
        }
        return this.mPercentFormat.format(f);
    }

    public final String getAccessibilityWindowTitle() {
        return this.mResources.getString(17039675);
    }

    public float getCenterX() {
        return isWindowVisible() ? this.mMagnificationFrame.exactCenterX() : Float.NaN;
    }

    public float getCenterY() {
        return isWindowVisible() ? this.mMagnificationFrame.exactCenterY() : Float.NaN;
    }

    public final Size getDefaultWindowSizeWithWindowBounds(Rect rect) {
        int min = Math.min(this.mResources.getDimensionPixelSize(R$dimen.magnification_max_frame_size), Math.min(rect.width(), rect.height()) / 2) + (this.mMirrorSurfaceMargin * 2);
        return new Size(min, min);
    }

    public final int getDegreeFromRotation(int i, int i2) {
        return (((i2 - i) + 4) % 4) * 90;
    }

    public float getScale() {
        return isWindowVisible() ? this.mScale : Float.NaN;
    }

    public final boolean handleScreenSizeChanged() {
        Rect rect = new Rect(this.mWindowBounds);
        Rect bounds = this.mWm.getCurrentWindowMetrics().getBounds();
        if (bounds.equals(rect)) {
            if (DEBUG) {
                Log.d("WindowMagnificationController", "handleScreenSizeChanged -- window bounds is not changed");
                return false;
            }
            return false;
        }
        this.mWindowBounds.set(bounds);
        Size defaultWindowSizeWithWindowBounds = getDefaultWindowSizeWithWindowBounds(this.mWindowBounds);
        setMagnificationFrame(defaultWindowSizeWithWindowBounds.getWidth(), defaultWindowSizeWithWindowBounds.getHeight(), (int) ((getCenterX() * this.mWindowBounds.width()) / rect.width()), (int) ((getCenterY() * this.mWindowBounds.height()) / rect.height()));
        calculateMagnificationFrameBoundary();
        return true;
    }

    public final boolean isWindowVisible() {
        return this.mMirrorView != null;
    }

    public final SurfaceControl mirrorDisplay(int i) {
        try {
            SurfaceControl surfaceControl = new SurfaceControl();
            WindowManagerGlobal.getWindowManagerService().mirrorDisplay(i, surfaceControl);
            return surfaceControl;
        } catch (RemoteException e) {
            Log.e("WindowMagnificationController", "Unable to reach window manager", e);
            return null;
        }
    }

    public final void modifyWindowMagnification(boolean z) {
        this.mSfVsyncFrameProvider.postFrameCallback(this.mMirrorViewGeometryVsyncCallback);
        updateMirrorViewLayout(z);
    }

    public void move(int i, int i2) {
        moveWindowMagnifier(i, i2);
        this.mWindowMagnifierCallback.onMove(this.mDisplayId);
    }

    public void moveWindowMagnifier(float f, float f2) {
        if (this.mAnimationController.isAnimating() || this.mMirrorSurfaceView == null || !updateMagnificationFramePosition((int) f, (int) f2)) {
            return;
        }
        modifyWindowMagnification(false);
    }

    public void moveWindowMagnifierToPosition(float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        if (this.mMirrorSurfaceView == null) {
            return;
        }
        this.mAnimationController.moveWindowMagnifierToPosition(f, f2, iRemoteMagnificationAnimationCallback);
    }

    public void onConfigurationChanged(int i) {
        if (DEBUG) {
            Log.d("WindowMagnificationController", "onConfigurationChanged = " + Configuration.configurationDiffToString(i));
        }
        if (i == 0) {
            return;
        }
        if ((i & RecyclerView.ViewHolder.FLAG_IGNORE) != 0) {
            onRotate();
        }
        if ((i & 4) != 0) {
            updateAccessibilityWindowTitleIfNeeded();
        }
        boolean z = false;
        if ((i & RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT) != 0) {
            updateDimensions();
            computeBounceAnimationScale();
            z = true;
        }
        boolean z2 = z;
        if ((i & RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE) != 0) {
            z2 = z | handleScreenSizeChanged();
        }
        if (isWindowVisible() && z2) {
            deleteWindowMagnification();
            enableWindowMagnificationInternal(Float.NaN, Float.NaN, Float.NaN);
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        int diff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        onConfigurationChanged(diff);
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onDrag(float f, float f2) {
        move((int) f, (int) f2);
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onFinish(float f, float f2) {
        return false;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    public final void onRotate() {
        Display display = this.mContext.getDisplay();
        int i = this.mRotation;
        int rotation = display.getRotation();
        this.mRotation = rotation;
        int degreeFromRotation = getDegreeFromRotation(rotation, i);
        if (degreeFromRotation == 0 || degreeFromRotation == 180) {
            Log.w("WindowMagnificationController", "onRotate -- rotate with the device. skip it");
            return;
        }
        Rect rect = new Rect(this.mWm.getCurrentWindowMetrics().getBounds());
        if (rect.width() != this.mWindowBounds.height() || rect.height() != this.mWindowBounds.width()) {
            Log.w("WindowMagnificationController", "onRotate -- unexpected window height/width");
            return;
        }
        this.mWindowBounds.set(rect);
        Matrix matrix = new Matrix();
        matrix.setRotate(degreeFromRotation);
        if (degreeFromRotation == 90) {
            matrix.postTranslate(this.mWindowBounds.width(), ActionBarShadowController.ELEVATION_LOW);
        } else if (degreeFromRotation == 270) {
            matrix.postTranslate(ActionBarShadowController.ELEVATION_LOW, this.mWindowBounds.height());
        }
        RectF rectF = new RectF(this.mMagnificationFrame);
        int i2 = this.mMirrorSurfaceMargin;
        rectF.inset(-i2, -i2);
        matrix.mapRect(rectF);
        setWindowSizeAndCenter((int) rectF.width(), (int) rectF.height(), (int) rectF.centerX(), (int) rectF.centerY());
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onSingleTap() {
        animateBounceEffect();
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onStart(float f, float f2) {
        return true;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == this.mDragView || view == this.mLeftDrag || view == this.mTopDrag || view == this.mRightDrag || view == this.mBottomDrag) {
            return this.mGestureDetector.onTouch(motionEvent);
        }
        return false;
    }

    public final void onWindowInsetChanged() {
        if (updateSystemGestureInsetsTop()) {
            updateSystemUIStateIfNeeded();
        }
    }

    public final void setMagnificationFrame(int i, int i2, int i3, int i4) {
        int i5 = i3 - (i / 2);
        int i6 = i4 - (i2 / 2);
        this.mMagnificationFrame.set(i5, i6, i + i5, i2 + i6);
    }

    public void setScale(float f) {
        if (this.mAnimationController.isAnimating() || !isWindowVisible() || this.mScale == f) {
            return;
        }
        enableWindowMagnificationInternal(f, Float.NaN, Float.NaN);
        this.mHandler.removeCallbacks(this.mUpdateStateDescriptionRunnable);
        this.mHandler.postDelayed(this.mUpdateStateDescriptionRunnable, 100L);
    }

    public void setWindowSizeAndCenter(int i, int i2, float f, float f2) {
        int clamp = MathUtils.clamp(i, this.mMinWindowSize, this.mWindowBounds.width());
        int clamp2 = MathUtils.clamp(i2, this.mMinWindowSize, this.mWindowBounds.height());
        float f3 = f;
        if (Float.isNaN(f)) {
            f3 = this.mMagnificationFrame.centerX();
        }
        if (Float.isNaN(f3)) {
            f2 = this.mMagnificationFrame.centerY();
        }
        int i3 = this.mMirrorSurfaceMargin;
        setMagnificationFrame(clamp - (i3 * 2), clamp2 - (i3 * 2), (int) f3, (int) f2);
        calculateMagnificationFrameBoundary();
        updateMagnificationFramePosition(0, 0);
        modifyWindowMagnification(true);
    }

    public final void showControls() {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        createMirror();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public final void updateAccessibilityWindowTitleIfNeeded() {
        if (isWindowVisible()) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mMirrorView.getLayoutParams();
            layoutParams.accessibilityTitle = getAccessibilityWindowTitle();
            this.mWm.updateViewLayout(this.mMirrorView, layoutParams);
        }
    }

    public final void updateDimensions() {
        this.mMirrorSurfaceMargin = this.mResources.getDimensionPixelSize(R$dimen.magnification_mirror_surface_margin);
        this.mBorderDragSize = this.mResources.getDimensionPixelSize(R$dimen.magnification_border_drag_size);
        this.mDragViewSize = this.mResources.getDimensionPixelSize(R$dimen.magnification_drag_view_size);
        this.mOuterBorderSize = this.mResources.getDimensionPixelSize(R$dimen.magnification_outer_border_margin);
        this.mMinWindowSize = this.mResources.getDimensionPixelSize(17104911);
    }

    public final boolean updateMagnificationFramePosition(int i, int i2) {
        this.mTmpRect.set(this.mMagnificationFrame);
        this.mTmpRect.offset(i, i2);
        Rect rect = this.mTmpRect;
        int i3 = rect.left;
        Rect rect2 = this.mMagnificationFrameBoundary;
        int i4 = rect2.left;
        if (i3 < i4) {
            rect.offsetTo(i4, rect.top);
        } else {
            int i5 = rect.right;
            int i6 = rect2.right;
            if (i5 > i6) {
                int width = this.mMagnificationFrame.width();
                Rect rect3 = this.mTmpRect;
                rect3.offsetTo(i6 - width, rect3.top);
            }
        }
        Rect rect4 = this.mTmpRect;
        int i7 = rect4.top;
        Rect rect5 = this.mMagnificationFrameBoundary;
        int i8 = rect5.top;
        if (i7 < i8) {
            rect4.offsetTo(rect4.left, i8);
        } else {
            int i9 = rect4.bottom;
            int i10 = rect5.bottom;
            if (i9 > i10) {
                int height = this.mMagnificationFrame.height();
                Rect rect6 = this.mTmpRect;
                rect6.offsetTo(rect6.left, i10 - height);
            }
        }
        if (this.mTmpRect.equals(this.mMagnificationFrame)) {
            return false;
        }
        this.mMagnificationFrame.set(this.mTmpRect);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMirrorViewLayout(boolean z) {
        float f;
        int min;
        int i;
        int min2;
        if (isWindowVisible()) {
            int width = this.mWindowBounds.width() - this.mMirrorView.getWidth();
            int height = this.mWindowBounds.height() - this.mMirrorView.getHeight();
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mMirrorView.getLayoutParams();
            Rect rect = this.mMagnificationFrame;
            int i2 = rect.left;
            int i3 = this.mMirrorSurfaceMargin;
            layoutParams.x = i2 - i3;
            layoutParams.y = rect.top - i3;
            if (z) {
                layoutParams.width = rect.width() + (this.mMirrorSurfaceMargin * 2);
                layoutParams.height = this.mMagnificationFrame.height() + (this.mMirrorSurfaceMargin * 2);
            }
            int i4 = layoutParams.x;
            float f2 = 0.0f;
            if (i4 < 0) {
                min = Math.max(i4, -this.mOuterBorderSize);
            } else if (i4 <= width) {
                f = 0.0f;
                i = layoutParams.y;
                if (i < 0) {
                    if (i > height) {
                        min2 = Math.min(i - height, this.mOuterBorderSize);
                    }
                    this.mMirrorView.setTranslationX(f);
                    this.mMirrorView.setTranslationY(f2);
                    this.mWm.updateViewLayout(this.mMirrorView, layoutParams);
                }
                min2 = Math.max(i, -this.mOuterBorderSize);
                f2 = min2;
                this.mMirrorView.setTranslationX(f);
                this.mMirrorView.setTranslationY(f2);
                this.mWm.updateViewLayout(this.mMirrorView, layoutParams);
            } else {
                min = Math.min(i4 - width, this.mOuterBorderSize);
            }
            f = min;
            i = layoutParams.y;
            if (i < 0) {
            }
            f2 = min2;
            this.mMirrorView.setTranslationX(f);
            this.mMirrorView.setTranslationY(f2);
            this.mWm.updateViewLayout(this.mMirrorView, layoutParams);
        }
    }

    public final void updateSysUIState(boolean z) {
        int i;
        boolean z2 = isWindowVisible() && (i = this.mSystemGestureTop) > 0 && this.mMirrorViewBounds.bottom > i;
        if (z || z2 != this.mOverlapWithGestureInsets) {
            this.mOverlapWithGestureInsets = z2;
            this.mSysUiState.setFlag(524288, z2).commitUpdate(this.mDisplayId);
        }
    }

    public void updateSysUIStateFlag() {
        updateSysUIState(true);
    }

    public final boolean updateSystemGestureInsetsTop() {
        WindowMetrics currentWindowMetrics = this.mWm.getCurrentWindowMetrics();
        Insets insets = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
        int i = insets.bottom != 0 ? currentWindowMetrics.getBounds().bottom - insets.bottom : -1;
        if (i != this.mSystemGestureTop) {
            this.mSystemGestureTop = i;
            return true;
        }
        return false;
    }

    public final void updateSystemUIStateIfNeeded() {
        updateSysUIState(false);
    }
}