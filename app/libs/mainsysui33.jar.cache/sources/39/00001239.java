package com.android.systemui.biometrics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.widget.FrameLayout;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$id;
import com.android.systemui.R$styleable;
import com.android.systemui.biometrics.UdfpsSurfaceView;
import com.android.systemui.doze.DozeReceiver;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsView.class */
public final class UdfpsView extends FrameLayout implements DozeReceiver {
    public UdfpsAnimationViewController<?> animationViewController;
    public String debugMessage;
    public final Paint debugTextPaint;
    public UdfpsSurfaceView ghbmView;
    public boolean isDisplayConfigured;
    public UdfpsDisplayModeProvider mUdfpsDisplayMode;
    public UdfpsOverlayParams overlayParams;
    public Rect sensorRect;
    public final float sensorTouchAreaCoefficient;
    public boolean useExpandedOverlay;

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public UdfpsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sensorRect = new Rect();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-16776961);
        paint.setTextSize(32.0f);
        this.debugTextPaint = paint;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.UdfpsView, 0, 0);
        try {
            int i = R$styleable.UdfpsView_sensorTouchAreaCoefficient;
            if (!obtainStyledAttributes.hasValue(i)) {
                throw new IllegalArgumentException("UdfpsView must contain sensorTouchAreaCoefficient".toString());
            }
            float f = obtainStyledAttributes.getFloat(i, ActionBarShadowController.ELEVATION_LOW);
            AutoCloseableKt.closeFinally(obtainStyledAttributes, (Throwable) null);
            this.sensorTouchAreaCoefficient = f;
            this.overlayParams = new UdfpsOverlayParams(null, null, 0, 0, ActionBarShadowController.ELEVATION_LOW, 0, 63, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(obtainStyledAttributes, th);
                throw th2;
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsView$configureDisplay$1.enableGhbm(android.view.Surface, java.lang.Runnable):void] */
    public static final /* synthetic */ void access$doIlluminate(UdfpsView udfpsView, Surface surface, Runnable runnable) {
        udfpsView.doIlluminate(surface, runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsView$doIlluminate$1.run():void] */
    public static final /* synthetic */ UdfpsSurfaceView access$getGhbmView$p(UdfpsView udfpsView) {
        return udfpsView.ghbmView;
    }

    public final void configureDisplay(Runnable runnable) {
        this.isDisplayConfigured = true;
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            udfpsAnimationViewController.onDisplayConfiguring();
        }
        UdfpsSurfaceView udfpsSurfaceView = this.ghbmView;
        if (udfpsSurfaceView == null) {
            doIlluminate(null, runnable);
            return;
        }
        udfpsSurfaceView.setGhbmIlluminationListener(new UdfpsSurfaceView.GhbmIlluminationListener() { // from class: com.android.systemui.biometrics.UdfpsView$configureDisplay$1
            @Override // com.android.systemui.biometrics.UdfpsSurfaceView.GhbmIlluminationListener
            public final void enableGhbm(Surface surface, Runnable runnable2) {
                UdfpsView.access$doIlluminate(UdfpsView.this, surface, runnable2);
            }
        });
        udfpsSurfaceView.setVisibility(0);
        udfpsSurfaceView.startGhbmIllumination(runnable);
    }

    public final void doIlluminate(Surface surface, final Runnable runnable) {
        if (this.ghbmView != null && surface == null) {
            Log.e("UdfpsView", "doIlluminate | surface must be non-null for GHBM");
        }
        UdfpsDisplayModeProvider udfpsDisplayModeProvider = this.mUdfpsDisplayMode;
        if (udfpsDisplayModeProvider != null) {
            udfpsDisplayModeProvider.enable(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsView$doIlluminate$1
                @Override // java.lang.Runnable
                public final void run() {
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                    UdfpsSurfaceView access$getGhbmView$p = UdfpsView.access$getGhbmView$p(this);
                    if (access$getGhbmView$p != null) {
                        access$getGhbmView$p.drawIlluminationDot(new RectF(this.getSensorRect()));
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public void dozeTimeTick() {
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            udfpsAnimationViewController.dozeTimeTick();
        }
    }

    public final UdfpsAnimationViewController<?> getAnimationViewController() {
        return this.animationViewController;
    }

    public final Rect getSensorRect() {
        return this.sensorRect;
    }

    public final boolean isDisplayConfigured() {
        return this.isDisplayConfigured;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0014, code lost:
        if (r0 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isWithinSensorArea(float f, float f2) {
        PointF pointF;
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            PointF touchTranslation = udfpsAnimationViewController.getTouchTranslation();
            pointF = touchTranslation;
        }
        pointF = new PointF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
        float centerX = this.sensorRect.centerX() + pointF.x;
        float centerY = this.sensorRect.centerY() + pointF.y;
        Rect rect = this.sensorRect;
        float f3 = (rect.right - rect.left) / 2.0f;
        float f4 = (rect.bottom - rect.top) / 2.0f;
        float f5 = this.sensorTouchAreaCoefficient;
        boolean z = false;
        if (f > centerX - (f3 * f5)) {
            z = false;
            if (f < centerX + (f3 * f5)) {
                z = false;
                if (f2 > centerY - (f4 * f5)) {
                    z = false;
                    if (f2 < centerY + (f4 * f5)) {
                        UdfpsAnimationViewController<?> udfpsAnimationViewController2 = this.animationViewController;
                        z = false;
                        if (!(udfpsAnimationViewController2 != null ? udfpsAnimationViewController2.shouldPauseAuth() : false)) {
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.v("UdfpsView", "onAttachedToWindow");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.v("UdfpsView", "onDetachedFromWindow");
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isDisplayConfigured) {
            return;
        }
        String str = this.debugMessage;
        if (str == null || str.length() == 0) {
            return;
        }
        String str2 = this.debugMessage;
        Intrinsics.checkNotNull(str2);
        canvas.drawText(str2, ActionBarShadowController.ELEVATION_LOW, 160.0f, this.debugTextPaint);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        this.ghbmView = (UdfpsSurfaceView) findViewById(R$id.hbm_view);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            Intrinsics.checkNotNull(udfpsAnimationViewController);
            if (udfpsAnimationViewController.shouldPauseAuth()) {
                z = false;
                return z;
            }
        }
        z = true;
        return z;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        int i5 = 0;
        int paddingX = udfpsAnimationViewController != null ? udfpsAnimationViewController.getPaddingX() : 0;
        UdfpsAnimationViewController<?> udfpsAnimationViewController2 = this.animationViewController;
        if (udfpsAnimationViewController2 != null) {
            i5 = udfpsAnimationViewController2.getPaddingY();
        }
        if (this.useExpandedOverlay) {
            UdfpsAnimationViewController<?> udfpsAnimationViewController3 = this.animationViewController;
            if (udfpsAnimationViewController3 != null) {
                udfpsAnimationViewController3.onSensorRectUpdated(new RectF(this.sensorRect));
                return;
            }
            return;
        }
        this.sensorRect.set(paddingX, i5, this.overlayParams.getSensorBounds().width() + paddingX, this.overlayParams.getSensorBounds().height() + i5);
        UdfpsAnimationViewController<?> udfpsAnimationViewController4 = this.animationViewController;
        if (udfpsAnimationViewController4 != null) {
            udfpsAnimationViewController4.onSensorRectUpdated(new RectF(this.sensorRect));
        }
    }

    public final void onTouchOutsideView() {
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            udfpsAnimationViewController.onTouchOutsideView();
        }
    }

    public final void setAnimationViewController(UdfpsAnimationViewController<?> udfpsAnimationViewController) {
        this.animationViewController = udfpsAnimationViewController;
    }

    public final void setDebugMessage(String str) {
        this.debugMessage = str;
        postInvalidate();
    }

    public final void setOverlayParams(UdfpsOverlayParams udfpsOverlayParams) {
        this.overlayParams = udfpsOverlayParams;
    }

    public final void setSensorRect(Rect rect) {
        this.sensorRect = rect;
    }

    public final void setUdfpsDisplayModeProvider(UdfpsDisplayModeProvider udfpsDisplayModeProvider) {
        this.mUdfpsDisplayMode = udfpsDisplayModeProvider;
    }

    public final void setUseExpandedOverlay(boolean z) {
        this.useExpandedOverlay = z;
    }

    public final void unconfigureDisplay() {
        this.isDisplayConfigured = false;
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            udfpsAnimationViewController.onDisplayUnconfigured();
        }
        UdfpsSurfaceView udfpsSurfaceView = this.ghbmView;
        if (udfpsSurfaceView != null) {
            udfpsSurfaceView.setGhbmIlluminationListener(null);
            udfpsSurfaceView.setVisibility(4);
        }
        UdfpsDisplayModeProvider udfpsDisplayModeProvider = this.mUdfpsDisplayMode;
        if (udfpsDisplayModeProvider != null) {
            udfpsDisplayModeProvider.disable(null);
        }
    }
}