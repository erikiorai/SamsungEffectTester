package com.android.systemui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.view.DisplayCutout;
import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/ScreenDecorHwcLayer.class */
public final class ScreenDecorHwcLayer extends DisplayCutoutBaseView {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG_COLOR = ScreenDecorations.DEBUG_COLOR;
    public final int bgColor;
    public final Paint clearPaint;
    public final int color;
    public final int colorMode;
    public final ColorFilter cornerBgFilter;
    public final ColorFilter cornerFilter;
    public final Paint debugTransparentRegionPaint;
    public boolean hasBottomRoundedCorner;
    public boolean hasTopRoundedCorner;
    public int roundedCornerBottomSize;
    public Drawable roundedCornerDrawableBottom;
    public Drawable roundedCornerDrawableTop;
    public int roundedCornerTopSize;
    public final Rect tempRect;
    public final Rect transparentRect;
    public final boolean useInvertedAlphaColor;

    /* loaded from: mainsysui33.jar:com/android/systemui/ScreenDecorHwcLayer$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ScreenDecorHwcLayer(Context context, DisplayDecorationSupport displayDecorationSupport) {
        super(context);
        this.transparentRect = new Rect();
        this.tempRect = new Rect();
        if (displayDecorationSupport.format != 56) {
            String formatToString = PixelFormat.formatToString(displayDecorationSupport.format);
            throw new IllegalArgumentException("Attempting to use unsupported mode " + formatToString);
        }
        if (DEBUG_COLOR) {
            this.color = -16711936;
            this.bgColor = 0;
            this.colorMode = 0;
            this.useInvertedAlphaColor = false;
            Paint paint = new Paint();
            paint.setColor(788594432);
            paint.setStyle(Paint.Style.FILL);
            this.debugTransparentRegionPaint = paint;
        } else {
            this.colorMode = 4;
            boolean z = displayDecorationSupport.alphaInterpretation == 0;
            this.useInvertedAlphaColor = z;
            if (z) {
                this.color = 0;
                this.bgColor = -16777216;
            } else {
                this.color = -16777216;
                this.bgColor = 0;
            }
            this.debugTransparentRegionPaint = null;
        }
        this.cornerFilter = new PorterDuffColorFilter(this.color, PorterDuff.Mode.SRC_IN);
        this.cornerBgFilter = new PorterDuffColorFilter(this.bgColor, PorterDuff.Mode.SRC_OUT);
        Paint paint2 = new Paint();
        this.clearPaint = paint2;
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public final void calculateTransparentRect() {
        this.transparentRect.set(0, 0, getWidth(), getHeight());
        removeCutoutFromTransparentRegion();
        removeCutoutProtectionFromTransparentRegion();
        removeRoundedCornersFromTransparentRegion();
    }

    public final void drawRoundedCorner(Canvas canvas, Drawable drawable, int i) {
        if (this.useInvertedAlphaColor) {
            float f = i;
            canvas.drawRect(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, f, f, this.clearPaint);
            if (drawable != null) {
                drawable.setColorFilter(this.cornerBgFilter);
            }
        } else if (drawable != null) {
            drawable.setColorFilter(this.cornerFilter);
        }
        if (drawable != null) {
            drawable.draw(canvas);
        }
        if (drawable != null) {
            drawable.clearColorFilter();
        }
    }

    public final void drawRoundedCorners(Canvas canvas) {
        int roundedCornerRotationDegree;
        if (this.hasTopRoundedCorner || this.hasBottomRoundedCorner) {
            for (int i = 0; i < 4; i++) {
                canvas.save();
                canvas.rotate(getRoundedCornerRotationDegree(i * 90));
                canvas.translate(getRoundedCornerTranslationX(roundedCornerRotationDegree), getRoundedCornerTranslationY(roundedCornerRotationDegree));
                if (this.hasTopRoundedCorner && (i == 0 || i == 1)) {
                    drawRoundedCorner(canvas, this.roundedCornerDrawableTop, this.roundedCornerTopSize);
                } else if (this.hasBottomRoundedCorner && (i == 3 || i == 2)) {
                    drawRoundedCorner(canvas, this.roundedCornerDrawableBottom, this.roundedCornerBottomSize);
                }
                canvas.restore();
            }
        }
    }

    @Override // android.view.View
    public boolean gatherTransparentRegion(Region region) {
        if (region != null) {
            calculateTransparentRect();
            if (DEBUG_COLOR) {
                region.setEmpty();
                return false;
            }
            region.op(this.transparentRect, Region.Op.INTERSECT);
            return false;
        }
        return false;
    }

    public final int getRoundedCornerRotationDegree(int i) {
        return ((i - (getDisplayRotation() * 90)) + 360) % 360;
    }

    public final int getRoundedCornerSizeByPosition(int i) {
        int coerceAtLeast;
        int displayRotation = ((getDisplayRotation() + 0) + i) % 4;
        if (displayRotation == 0) {
            coerceAtLeast = RangesKt___RangesKt.coerceAtLeast(this.roundedCornerTopSize, this.roundedCornerBottomSize);
        } else if (displayRotation == 1) {
            coerceAtLeast = this.roundedCornerTopSize;
        } else if (displayRotation == 2) {
            coerceAtLeast = RangesKt___RangesKt.coerceAtLeast(this.roundedCornerTopSize, this.roundedCornerBottomSize);
        } else if (displayRotation != 3) {
            throw new IllegalArgumentException("Incorrect position: " + i);
        } else {
            coerceAtLeast = this.roundedCornerBottomSize;
        }
        return coerceAtLeast;
    }

    public final int getRoundedCornerTranslationX(int i) {
        int i2;
        int width;
        if (i == 0 || i == 90) {
            i2 = 0;
        } else {
            if (i == 180) {
                width = getWidth();
            } else if (i != 270) {
                throw new IllegalArgumentException("Incorrect degree: " + i);
            } else {
                width = getHeight();
            }
            i2 = -width;
        }
        return i2;
    }

    public final int getRoundedCornerTranslationY(int i) {
        int i2;
        int width;
        if (i != 0) {
            if (i == 90) {
                width = getWidth();
            } else if (i == 180) {
                width = getHeight();
            } else if (i != 270) {
                throw new IllegalArgumentException("Incorrect degree: " + i);
            }
            i2 = -width;
            return i2;
        }
        i2 = 0;
        return i2;
    }

    @Override // com.android.systemui.DisplayCutoutBaseView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getParent().requestTransparentRegion(this);
        if (!DEBUG_COLOR) {
            getViewRootImpl().setDisplayDecoration(true);
        }
        if (this.useInvertedAlphaColor) {
            this.paint.set(this.clearPaint);
            return;
        }
        this.paint.setColor(this.color);
        this.paint.setStyle(Paint.Style.FILL);
    }

    @Override // com.android.systemui.DisplayCutoutBaseView, android.view.View
    public void onDraw(Canvas canvas) {
        if (this.useInvertedAlphaColor) {
            canvas.drawColor(this.bgColor);
        }
        drawRoundedCorners(canvas);
        super.onDraw(canvas);
        Paint paint = this.debugTransparentRegionPaint;
        if (paint != null) {
            canvas.drawRect(this.transparentRect, paint);
        }
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public void onUpdate() {
        getParent().requestTransparentRegion(this);
    }

    public final void removeCutoutFromTransparentRegion() {
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null) {
            if (!displayCutout.getBoundingRectLeft().isEmpty()) {
                this.transparentRect.left = RangesKt___RangesKt.coerceAtLeast(displayCutout.getBoundingRectLeft().right, this.transparentRect.left);
            }
            if (!displayCutout.getBoundingRectTop().isEmpty()) {
                this.transparentRect.top = RangesKt___RangesKt.coerceAtLeast(displayCutout.getBoundingRectTop().bottom, this.transparentRect.top);
            }
            if (!displayCutout.getBoundingRectRight().isEmpty()) {
                this.transparentRect.right = RangesKt___RangesKt.coerceAtMost(displayCutout.getBoundingRectRight().left, this.transparentRect.right);
            }
            if (displayCutout.getBoundingRectBottom().isEmpty()) {
                return;
            }
            this.transparentRect.bottom = RangesKt___RangesKt.coerceAtMost(displayCutout.getBoundingRectBottom().top, this.transparentRect.bottom);
        }
    }

    public final void removeCutoutProtectionFromTransparentRegion() {
        if (this.protectionRect.isEmpty()) {
            return;
        }
        float centerX = this.protectionRect.centerX();
        float centerY = this.protectionRect.centerY();
        float cameraProtectionProgress = (centerX - this.protectionRect.left) * getCameraProtectionProgress();
        float cameraProtectionProgress2 = (centerY - this.protectionRect.top) * getCameraProtectionProgress();
        this.tempRect.set((int) Math.floor(centerX - cameraProtectionProgress), (int) Math.floor(centerY - cameraProtectionProgress2), (int) Math.ceil(centerX + cameraProtectionProgress), (int) Math.ceil(centerY + cameraProtectionProgress2));
        Rect rect = this.tempRect;
        int i = rect.left;
        int i2 = rect.top;
        int width = getWidth() - this.tempRect.right;
        int height = getHeight() - this.tempRect.bottom;
        int minOf = ComparisonsKt___ComparisonsJvmKt.minOf(i, new int[]{i2, width, height});
        if (minOf == i) {
            Rect rect2 = this.transparentRect;
            rect2.left = RangesKt___RangesKt.coerceAtLeast(this.tempRect.right, rect2.left);
        } else if (minOf == i2) {
            Rect rect3 = this.transparentRect;
            rect3.top = RangesKt___RangesKt.coerceAtLeast(this.tempRect.bottom, rect3.top);
        } else if (minOf == width) {
            Rect rect4 = this.transparentRect;
            rect4.right = RangesKt___RangesKt.coerceAtMost(this.tempRect.left, rect4.right);
        } else if (minOf == height) {
            Rect rect5 = this.transparentRect;
            rect5.bottom = RangesKt___RangesKt.coerceAtMost(this.tempRect.top, rect5.bottom);
        }
    }

    public final void removeRoundedCornersFromTransparentRegion() {
        boolean z;
        boolean z2;
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null) {
            z2 = (displayCutout.getBoundingRectTop().isEmpty() && displayCutout.getBoundingRectBottom().isEmpty()) ? false : true;
            z = (displayCutout.getBoundingRectLeft().isEmpty() && displayCutout.getBoundingRectRight().isEmpty()) ? false : true;
        } else {
            z = false;
            z2 = false;
        }
        if (getWidth() < getHeight()) {
            if (z2 || !z) {
                this.transparentRect.top = RangesKt___RangesKt.coerceAtLeast(getRoundedCornerSizeByPosition(1), this.transparentRect.top);
                this.transparentRect.bottom = RangesKt___RangesKt.coerceAtMost(getHeight() - getRoundedCornerSizeByPosition(3), this.transparentRect.bottom);
                return;
            }
            this.transparentRect.left = RangesKt___RangesKt.coerceAtLeast(getRoundedCornerSizeByPosition(0), this.transparentRect.left);
            this.transparentRect.right = RangesKt___RangesKt.coerceAtMost(getWidth() - getRoundedCornerSizeByPosition(2), this.transparentRect.right);
        } else if (!z2 || z) {
            this.transparentRect.left = RangesKt___RangesKt.coerceAtLeast(getRoundedCornerSizeByPosition(0), this.transparentRect.left);
            this.transparentRect.right = RangesKt___RangesKt.coerceAtMost(getWidth() - getRoundedCornerSizeByPosition(2), this.transparentRect.right);
        } else {
            this.transparentRect.top = RangesKt___RangesKt.coerceAtLeast(getRoundedCornerSizeByPosition(1), this.transparentRect.top);
            this.transparentRect.bottom = RangesKt___RangesKt.coerceAtMost(getHeight() - getRoundedCornerSizeByPosition(3), this.transparentRect.bottom);
        }
    }

    public final void updateRoundedCornerDrawable(Drawable drawable, Drawable drawable2) {
        this.roundedCornerDrawableTop = drawable;
        this.roundedCornerDrawableBottom = drawable2;
        updateRoundedCornerDrawableBounds();
        invalidate();
    }

    public final void updateRoundedCornerDrawableBounds() {
        Drawable drawable = this.roundedCornerDrawableTop;
        if (drawable != null && drawable != null) {
            int i = this.roundedCornerTopSize;
            drawable.setBounds(0, 0, i, i);
        }
        Drawable drawable2 = this.roundedCornerDrawableBottom;
        if (drawable2 != null && drawable2 != null) {
            int i2 = this.roundedCornerBottomSize;
            drawable2.setBounds(0, 0, i2, i2);
        }
        invalidate();
    }

    public final void updateRoundedCornerExistenceAndSize(boolean z, boolean z2, int i, int i2) {
        if (this.hasTopRoundedCorner == z && this.hasBottomRoundedCorner == z2 && this.roundedCornerTopSize == i && this.roundedCornerBottomSize == i2) {
            return;
        }
        this.hasTopRoundedCorner = z;
        this.hasBottomRoundedCorner = z2;
        this.roundedCornerTopSize = i;
        this.roundedCornerBottomSize = i2;
        updateRoundedCornerDrawableBounds();
        requestLayout();
    }
}