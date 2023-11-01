package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.View;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.RegionInterceptingFrameLayout;
import com.android.systemui.animation.Interpolators;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/DisplayCutoutBaseView.class */
public class DisplayCutoutBaseView extends View implements RegionInterceptingFrameLayout.RegionInterceptableView {
    public static final Companion Companion = new Companion(null);
    public ValueAnimator cameraProtectionAnimator;
    public float cameraProtectionProgress;
    public final Path cutoutPath;
    public final DisplayInfo displayInfo;
    public Display.Mode displayMode;
    public int displayRotation;
    public String displayUniqueId;
    public final int[] location;
    public final Paint paint;
    public boolean pendingConfigChange;
    public final Path protectionPath;
    public final Path protectionPathOrig;
    public final RectF protectionRect;
    public final RectF protectionRectOrig;
    public boolean shouldDrawCutout;
    public boolean showProtection;

    /* loaded from: mainsysui33.jar:com/android/systemui/DisplayCutoutBaseView$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void transformPhysicalToLogicalCoordinates(int i, int i2, int i3, Matrix matrix) {
            if (i != 0) {
                if (i == 1) {
                    matrix.postRotate(270.0f);
                    matrix.postTranslate(ActionBarShadowController.ELEVATION_LOW, i2);
                } else if (i == 2) {
                    matrix.postRotate(180.0f);
                    matrix.postTranslate(i2, i3);
                } else if (i == 3) {
                    matrix.postRotate(90.0f);
                    matrix.postTranslate(i3, ActionBarShadowController.ELEVATION_LOW);
                } else {
                    throw new IllegalArgumentException("Unknown rotation: " + i);
                }
            }
        }
    }

    public DisplayCutoutBaseView(Context context) {
        super(context);
        Resources resources = getContext().getResources();
        Display display = getContext().getDisplay();
        this.shouldDrawCutout = DisplayCutout.getFillBuiltInDisplayCutout(resources, display != null ? display.getUniqueId() : null);
        this.location = new int[2];
        this.displayInfo = new DisplayInfo();
        this.paint = new Paint();
        this.cutoutPath = new Path();
        this.protectionRect = new RectF();
        this.protectionPath = new Path();
        this.protectionRectOrig = new RectF();
        this.protectionPathOrig = new Path();
        this.cameraProtectionProgress = 0.5f;
    }

    public DisplayCutoutBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources resources = getContext().getResources();
        Display display = getContext().getDisplay();
        this.shouldDrawCutout = DisplayCutout.getFillBuiltInDisplayCutout(resources, display != null ? display.getUniqueId() : null);
        this.location = new int[2];
        this.displayInfo = new DisplayInfo();
        this.paint = new Paint();
        this.cutoutPath = new Path();
        this.protectionRect = new RectF();
        this.protectionPath = new Path();
        this.protectionRectOrig = new RectF();
        this.protectionPathOrig = new Path();
        this.cameraProtectionProgress = 0.5f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.DisplayCutoutBaseView$enableShowProtection$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setCameraProtectionAnimator$p(DisplayCutoutBaseView displayCutoutBaseView, ValueAnimator valueAnimator) {
        displayCutoutBaseView.cameraProtectionAnimator = valueAnimator;
    }

    public static /* synthetic */ void getCameraProtectionProgress$annotations() {
    }

    public static /* synthetic */ void getDisplayInfo$annotations() {
    }

    public static /* synthetic */ void getProtectionPath$annotations() {
    }

    public static /* synthetic */ void getProtectionRect$annotations() {
    }

    public static final void transformPhysicalToLogicalCoordinates(int i, int i2, int i3, Matrix matrix) {
        Companion.transformPhysicalToLogicalCoordinates(i, i2, i3, matrix);
    }

    public final boolean displayModeChanged(Display.Mode mode, Display.Mode mode2) {
        if (mode == null) {
            return true;
        }
        boolean z = true;
        if (Intrinsics.areEqual(Integer.valueOf(mode.getPhysicalHeight()), mode2 != null ? Integer.valueOf(mode2.getPhysicalHeight()) : null)) {
            int physicalWidth = mode.getPhysicalWidth();
            Integer num = null;
            if (mode2 != null) {
                num = Integer.valueOf(mode2.getPhysicalWidth());
            }
            z = !Intrinsics.areEqual(Integer.valueOf(physicalWidth), num);
        }
        return z;
    }

    public void drawCutoutProtection(Canvas canvas) {
        if (this.cameraProtectionProgress <= 0.5f || this.protectionRect.isEmpty()) {
            return;
        }
        float f = this.cameraProtectionProgress;
        canvas.scale(f, f, this.protectionRect.centerX(), this.protectionRect.centerY());
        canvas.drawPath(this.protectionPath, this.paint);
    }

    public void drawCutouts(Canvas canvas) {
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if ((displayCutout != null ? displayCutout.getCutoutPath() : null) == null) {
            return;
        }
        canvas.drawPath(this.cutoutPath, this.paint);
    }

    public void enableShowProtection(boolean z) {
        if (this.showProtection == z) {
            return;
        }
        this.showProtection = z;
        updateProtectionBoundingPath();
        if (this.showProtection) {
            requestLayout();
        }
        ValueAnimator valueAnimator = this.cameraProtectionAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofFloat(this.cameraProtectionProgress, this.showProtection ? 1.0f : 0.5f).setDuration(750L);
        this.cameraProtectionAnimator = duration;
        if (duration != null) {
            duration.setInterpolator(Interpolators.DECELERATE_QUINT);
        }
        ValueAnimator valueAnimator2 = this.cameraProtectionAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.DisplayCutoutBaseView$enableShowProtection$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                    DisplayCutoutBaseView.this.setCameraProtectionProgress(((Float) valueAnimator3.getAnimatedValue()).floatValue());
                    DisplayCutoutBaseView.this.invalidate();
                }
            });
        }
        ValueAnimator valueAnimator3 = this.cameraProtectionAnimator;
        if (valueAnimator3 != null) {
            valueAnimator3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.DisplayCutoutBaseView$enableShowProtection$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    DisplayCutoutBaseView.access$setCameraProtectionAnimator$p(DisplayCutoutBaseView.this, null);
                    DisplayCutoutBaseView displayCutoutBaseView = DisplayCutoutBaseView.this;
                    if (displayCutoutBaseView.showProtection) {
                        return;
                    }
                    displayCutoutBaseView.requestLayout();
                }
            });
        }
        ValueAnimator valueAnimator4 = this.cameraProtectionAnimator;
        if (valueAnimator4 != null) {
            valueAnimator4.start();
        }
    }

    public final float getCameraProtectionProgress() {
        return this.cameraProtectionProgress;
    }

    public final int getDisplayRotation() {
        return this.displayRotation;
    }

    @Override // com.android.systemui.RegionInterceptingFrameLayout.RegionInterceptableView
    public Region getInterceptRegion() {
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        List<Rect> list = null;
        if (displayCutout == null) {
            return null;
        }
        if (displayCutout != null) {
            list = displayCutout.getBoundingRects();
        }
        Region rectsToRegion = rectsToRegion(list);
        getRootView().getLocationOnScreen(this.location);
        int[] iArr = this.location;
        rectsToRegion.translate(-iArr[0], -iArr[1]);
        rectsToRegion.op(getRootView().getLeft(), getRootView().getTop(), getRootView().getRight(), getRootView().getBottom(), Region.Op.INTERSECT);
        return rectsToRegion;
    }

    public float getPhysicalPixelDisplaySizeRatio() {
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null) {
            return displayCutout.getCutoutPathParserInfo().getPhysicalPixelDisplaySizeRatio();
        }
        return 1.0f;
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Display display = getContext().getDisplay();
        this.displayUniqueId = display != null ? display.getUniqueId() : null;
        updateCutout();
        updateProtectionBoundingPath();
        onUpdate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.shouldDrawCutout) {
            canvas.save();
            getLocationOnScreen(this.location);
            int[] iArr = this.location;
            canvas.translate(-iArr[0], -iArr[1]);
            drawCutouts(canvas);
            drawCutoutProtection(canvas);
            canvas.restore();
        }
    }

    public void onUpdate() {
    }

    public final Region rectsToRegion(List<Rect> list) {
        Region obtain = Region.obtain();
        if (list != null) {
            for (Rect rect : list) {
                if (rect != null && !rect.isEmpty()) {
                    obtain.op(rect, Region.Op.UNION);
                }
            }
        }
        return obtain;
    }

    public final void setCameraProtectionProgress(float f) {
        this.cameraProtectionProgress = f;
    }

    public void setProtection(Path path, Rect rect) {
        this.protectionPathOrig.reset();
        this.protectionPathOrig.set(path);
        this.protectionPath.reset();
        this.protectionRectOrig.setEmpty();
        this.protectionRectOrig.set(rect);
        this.protectionRect.setEmpty();
    }

    @Override // com.android.systemui.RegionInterceptingFrameLayout.RegionInterceptableView
    public boolean shouldInterceptTouch() {
        return this.displayInfo.displayCutout != null && getVisibility() == 0 && this.shouldDrawCutout;
    }

    public final void updateConfiguration(String str) {
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = getContext().getDisplay();
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        Display.Mode mode = this.displayMode;
        this.displayMode = displayInfo.getMode();
        updateDisplayUniqueId(displayInfo.uniqueId);
        if (!(!displayModeChanged(mode, this.displayMode) && Intrinsics.areEqual(this.displayInfo.displayCutout, displayInfo.displayCutout) && this.displayRotation == displayInfo.rotation) && Intrinsics.areEqual(str, displayInfo.uniqueId)) {
            this.displayRotation = displayInfo.rotation;
            updateCutout();
            updateProtectionBoundingPath();
            onUpdate();
        }
    }

    public void updateCutout() {
        Path cutoutPath;
        if (this.pendingConfigChange) {
            return;
        }
        this.cutoutPath.reset();
        Display display = getContext().getDisplay();
        if (display != null) {
            display.getDisplayInfo(this.displayInfo);
        }
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null && (cutoutPath = displayCutout.getCutoutPath()) != null) {
            this.cutoutPath.set(cutoutPath);
        }
        invalidate();
    }

    public void updateDisplayUniqueId(String str) {
        if (Intrinsics.areEqual(this.displayUniqueId, str)) {
            return;
        }
        this.displayUniqueId = str;
        this.shouldDrawCutout = DisplayCutout.getFillBuiltInDisplayCutout(getContext().getResources(), this.displayUniqueId);
        invalidate();
    }

    public void updateProtectionBoundingPath() {
        if (this.pendingConfigChange) {
            return;
        }
        Matrix matrix = new Matrix();
        float physicalPixelDisplaySizeRatio = getPhysicalPixelDisplaySizeRatio();
        matrix.postScale(physicalPixelDisplaySizeRatio, physicalPixelDisplaySizeRatio);
        DisplayInfo displayInfo = this.displayInfo;
        int i = displayInfo.logicalWidth;
        int i2 = displayInfo.logicalHeight;
        int i3 = displayInfo.rotation;
        boolean z = true;
        if (i3 != 1) {
            z = i3 == 3;
        }
        int i4 = z ? i2 : i;
        if (z) {
            i2 = i;
        }
        transformPhysicalToLogicalCoordinates(i3, i4, i2, matrix);
        if (this.protectionPathOrig.isEmpty()) {
            return;
        }
        this.protectionPath.set(this.protectionPathOrig);
        this.protectionPath.transform(matrix);
        matrix.mapRect(this.protectionRect, this.protectionRectOrig);
    }

    public void updateRotation(int i) {
        this.displayRotation = i;
        updateCutout();
        updateProtectionBoundingPath();
        onUpdate();
    }
}