package com.android.launcher3.icons;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import com.android.settingslib.widget.ActionBarShadowController;
import java.nio.ByteBuffer;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/IconNormalizer.class */
public class IconNormalizer {
    public final RectF mAdaptiveIconBounds;
    public float mAdaptiveIconScale;
    public final Bitmap mBitmap;
    public final Rect mBounds;
    public final Canvas mCanvas;
    public boolean mEnableShapeDetection;
    public final float[] mLeftBorder;
    public final Matrix mMatrix;
    public final int mMaxSize;
    public final Paint mPaintMaskShape;
    public final Paint mPaintMaskShapeOutline;
    public final byte[] mPixels;
    public final float[] mRightBorder;
    public final Path mShapePath;

    public IconNormalizer(Context context, int i, boolean z) {
        int i2 = i * 2;
        this.mMaxSize = i2;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        this.mBitmap = createBitmap;
        this.mCanvas = new Canvas(createBitmap);
        this.mPixels = new byte[i2 * i2];
        this.mLeftBorder = new float[i2];
        this.mRightBorder = new float[i2];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new RectF();
        Paint paint = new Paint();
        this.mPaintMaskShape = paint;
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.mPaintMaskShapeOutline = paint2;
        paint2.setStrokeWidth(context.getResources().getDisplayMetrics().density * 2.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(-16777216);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mShapePath = new Path();
        this.mMatrix = new Matrix();
        this.mAdaptiveIconScale = ActionBarShadowController.ELEVATION_LOW;
        this.mEnableShapeDetection = z;
    }

    public static void convertToConvexArray(float[] fArr, int i, int i2, int i3) {
        int i4;
        float[] fArr2 = new float[fArr.length - 1];
        int i5 = -1;
        float f = Float.MAX_VALUE;
        for (int i6 = i2 + 1; i6 <= i3; i6++) {
            float f2 = fArr[i6];
            if (f2 > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i4 = i2;
                } else {
                    float f3 = (f2 - fArr[i5]) / (i6 - i5);
                    float f4 = i;
                    i4 = i5;
                    if ((f3 - f) * f4 < ActionBarShadowController.ELEVATION_LOW) {
                        do {
                            i4 = i5;
                            if (i5 <= i2) {
                                break;
                            }
                            i4 = i5 - 1;
                            i5 = i4;
                        } while ((((fArr[i6] - fArr[i4]) / (i6 - i4)) - fArr2[i4]) * f4 < ActionBarShadowController.ELEVATION_LOW);
                    }
                }
                f = (fArr[i6] - fArr[i4]) / (i6 - i4);
                for (int i7 = i4; i7 < i6; i7++) {
                    fArr2[i7] = f;
                    fArr[i7] = fArr[i4] + ((i7 - i4) * f);
                }
                i5 = i6;
            }
        }
    }

    public static int getNormalizedCircleSize(int i) {
        return (int) Math.round(Math.sqrt((((i * i) * 0.6597222f) * 4.0f) / 3.141592653589793d));
    }

    public static float getScale(float f, float f2, float f3) {
        float f4 = f / f2;
        float f5 = f4 < 0.7853982f ? 0.6597222f : ((1.0f - f4) * 0.040449437f) + 0.6510417f;
        float f6 = f / f3;
        float f7 = 1.0f;
        if (f6 > f5) {
            f7 = (float) Math.sqrt(f5 / f6);
        }
        return f7;
    }

    @TargetApi(26)
    public static float normalizeAdaptiveIcon(Drawable drawable, int i, RectF rectF) {
        Rect rect = new Rect(drawable.getBounds());
        drawable.setBounds(0, 0, i, i);
        Path iconMask = ((AdaptiveIconDrawable) drawable).getIconMask();
        Region region = new Region();
        region.setPath(iconMask, new Region(0, 0, i, i));
        Rect bounds = region.getBounds();
        int area = GraphicsUtils.getArea(region);
        if (rectF != null) {
            float f = i;
            rectF.set(bounds.left / f, bounds.top / f, 1.0f - (bounds.right / f), 1.0f - (bounds.bottom / f));
        }
        drawable.setBounds(rect);
        float f2 = area;
        return getScale(f2, f2, i * i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x006b, code lost:
        if (r0 > r0) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a3, code lost:
        if (r0 > r8.mMaxSize) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00bf, code lost:
        if (r0 > r8.mMaxSize) goto L105;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public float getScale(Drawable drawable, RectF rectF, Path path, boolean[] zArr) {
        int i;
        int i2;
        int i3;
        synchronized (this) {
            if (drawable instanceof AdaptiveIconDrawable) {
                if (this.mAdaptiveIconScale == ActionBarShadowController.ELEVATION_LOW) {
                    this.mAdaptiveIconScale = normalizeAdaptiveIcon(drawable, this.mMaxSize, this.mAdaptiveIconBounds);
                }
                if (rectF != null) {
                    rectF.set(this.mAdaptiveIconBounds);
                }
                return this.mAdaptiveIconScale;
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                if (intrinsicWidth > 0) {
                    i = intrinsicWidth;
                }
                i = this.mMaxSize;
                if (intrinsicHeight > 0) {
                    i3 = i;
                    i2 = intrinsicHeight;
                }
                i2 = this.mMaxSize;
                i3 = i;
            } else {
                int i4 = this.mMaxSize;
                if (intrinsicWidth <= i4) {
                    i3 = intrinsicWidth;
                    i2 = intrinsicHeight;
                }
                int max = Math.max(intrinsicWidth, intrinsicHeight);
                int i5 = this.mMaxSize;
                i3 = (intrinsicWidth * i5) / max;
                i2 = (i5 * intrinsicHeight) / max;
            }
            this.mBitmap.eraseColor(0);
            drawable.setBounds(0, 0, i3, i2);
            drawable.draw(this.mCanvas);
            ByteBuffer wrap = ByteBuffer.wrap(this.mPixels);
            wrap.rewind();
            this.mBitmap.copyPixelsToBuffer(wrap);
            int i6 = this.mMaxSize;
            int i7 = i6 + 1;
            int i8 = 0;
            int i9 = 0;
            int i10 = -1;
            int i11 = -1;
            int i12 = -1;
            while (i8 < i2) {
                int i13 = 0;
                int i14 = -1;
                int i15 = i9;
                int i16 = -1;
                while (i13 < i3) {
                    int i17 = i16;
                    int i18 = i14;
                    if ((this.mPixels[i15] & 255) > 40) {
                        int i19 = i16;
                        if (i16 == -1) {
                            i19 = i13;
                        }
                        i18 = i13;
                        i17 = i19;
                    }
                    i15++;
                    i13++;
                    i16 = i17;
                    i14 = i18;
                }
                int i20 = i15 + (i6 - i3);
                this.mLeftBorder[i8] = i16;
                this.mRightBorder[i8] = i14;
                int i21 = i7;
                int i22 = i10;
                int i23 = i11;
                if (i16 != -1) {
                    i22 = i10;
                    if (i10 == -1) {
                        i22 = i8;
                    }
                    i21 = Math.min(i7, i16);
                    i23 = Math.max(i11, i14);
                    i12 = i8;
                }
                i8++;
                i7 = i21;
                i10 = i22;
                i11 = i23;
                i9 = i20;
            }
            if (i10 == -1 || i11 == -1) {
                return 1.0f;
            }
            convertToConvexArray(this.mLeftBorder, 1, i10, i12);
            convertToConvexArray(this.mRightBorder, -1, i10, i12);
            float f = 0.0f;
            for (int i24 = 0; i24 < i2; i24++) {
                float f2 = this.mLeftBorder[i24];
                if (f2 > -1.0f) {
                    f += (this.mRightBorder[i24] - f2) + 1.0f;
                }
            }
            Rect rect = this.mBounds;
            rect.left = i7;
            rect.right = i11;
            rect.top = i10;
            rect.bottom = i12;
            if (rectF != null) {
                float f3 = i3;
                float f4 = i7 / f3;
                float f5 = i2;
                rectF.set(f4, i10 / f5, 1.0f - (i11 / f3), 1.0f - (i12 / f5));
            }
            if (zArr != null && this.mEnableShapeDetection && zArr.length > 0) {
                zArr[0] = isShape(path);
            }
            return getScale(f, ((i12 + 1) - i10) * ((i11 + 1) - i7), i3 * i2);
        }
    }

    public final boolean isShape(Path path) {
        if (Math.abs((this.mBounds.width() / this.mBounds.height()) - 1.0f) > 0.05f) {
            return false;
        }
        this.mMatrix.reset();
        this.mMatrix.setScale(this.mBounds.width(), this.mBounds.height());
        Matrix matrix = this.mMatrix;
        Rect rect = this.mBounds;
        matrix.postTranslate(rect.left, rect.top);
        path.transform(this.mMatrix, this.mShapePath);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShape);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShapeOutline);
        return isTransparentBitmap();
    }

    public final boolean isTransparentBitmap() {
        Rect rect;
        ByteBuffer wrap = ByteBuffer.wrap(this.mPixels);
        wrap.rewind();
        this.mBitmap.copyPixelsToBuffer(wrap);
        Rect rect2 = this.mBounds;
        int i = rect2.top;
        int i2 = this.mMaxSize;
        int i3 = i * i2;
        int i4 = rect2.right;
        boolean z = false;
        int i5 = 0;
        while (true) {
            rect = this.mBounds;
            if (i >= rect.bottom) {
                break;
            }
            int i6 = rect.left;
            int i7 = i3 + i6;
            int i8 = i6;
            while (i8 < this.mBounds.right) {
                int i9 = i5;
                if ((this.mPixels[i7] & 255) > 40) {
                    i9 = i5 + 1;
                }
                i7++;
                i8++;
                i5 = i9;
            }
            i3 = i7 + (i2 - i4);
            i++;
        }
        if (i5 / (rect.width() * this.mBounds.height()) < 0.005f) {
            z = true;
        }
        return z;
    }
}