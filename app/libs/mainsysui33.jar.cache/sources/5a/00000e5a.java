package com.android.settingslib.graph;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/settingslib/graph/BatteryMeterDrawableBase.class */
public class BatteryMeterDrawableBase extends Drawable {
    public static final String TAG = BatteryMeterDrawableBase.class.getSimpleName();
    public final Paint mBatteryPaint;
    public final RectF mBoltFrame;
    public final Paint mBoltPaint;
    public final Path mBoltPath;
    public final float[] mBoltPoints;
    public final RectF mButtonFrame;
    public float mButtonHeightFraction;
    public int mChargeColor;
    public boolean mCharging;
    public final int[] mColors;
    public final int mCriticalLevel;
    public final RectF mFrame;
    public final Paint mFramePaint;
    public int mHeight;
    public int mIconTint;
    public final int mIntrinsicHeight;
    public final int mIntrinsicWidth;
    public int mLevel;
    public final Path mOutlinePath;
    public final Rect mPadding;
    public final RectF mPlusFrame;
    public final Paint mPlusPaint;
    public final Path mPlusPath;
    public final float[] mPlusPoints;
    public boolean mPowerSaveAsColorError;
    public boolean mPowerSaveEnabled;
    public final Paint mPowersavePaint;
    public final Path mShapePath;
    public boolean mShowPercent;
    public float mTextHeight;
    public final Paint mTextPaint;
    public final Path mTextPath;
    public String mWarningString;
    public float mWarningTextHeight;
    public final Paint mWarningTextPaint;
    public int mWidth;

    public int batteryColorForLevel(int i) {
        return (this.mCharging || (this.mPowerSaveEnabled && this.mPowerSaveAsColorError)) ? this.mChargeColor : getColorForLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float f;
        float height;
        float[] fArr;
        float f2;
        float f3;
        boolean z;
        float[] fArr2;
        int i = this.mLevel;
        Rect bounds = getBounds();
        if (i == -1) {
            return;
        }
        float f4 = i / 100.0f;
        int i2 = this.mHeight;
        int aspectRatio = (int) (getAspectRatio() * this.mHeight);
        int i3 = (this.mWidth - aspectRatio) / 2;
        float f5 = i2;
        int round = Math.round(this.mButtonHeightFraction * f5);
        Rect rect = this.mPadding;
        int i4 = rect.left + bounds.left;
        int i5 = (bounds.bottom - rect.bottom) - i2;
        float f6 = i4;
        float f7 = i5;
        this.mFrame.set(f6, f7, i4 + aspectRatio, i2 + i5);
        this.mFrame.offset(i3, ActionBarShadowController.ELEVATION_LOW);
        RectF rectF = this.mButtonFrame;
        float f8 = this.mFrame.left;
        float round2 = Math.round(aspectRatio * 0.28f);
        RectF rectF2 = this.mFrame;
        float f9 = round;
        rectF.set(f8 + round2, rectF2.top, rectF2.right - Math.round(f), this.mFrame.top + f9);
        this.mFrame.top += f9;
        this.mBatteryPaint.setColor(batteryColorForLevel(i));
        if (i >= 96) {
            f4 = 1.0f;
        } else if (i <= this.mCriticalLevel) {
            f4 = 0.0f;
        }
        if (f4 == 1.0f) {
            height = this.mButtonFrame.top;
        } else {
            RectF rectF3 = this.mFrame;
            height = (rectF3.height() * (1.0f - f4)) + rectF3.top;
        }
        this.mShapePath.reset();
        this.mOutlinePath.reset();
        float radiusRatio = getRadiusRatio() * (this.mFrame.height() + f9);
        this.mShapePath.setFillType(Path.FillType.WINDING);
        this.mShapePath.addRoundRect(this.mFrame, radiusRatio, radiusRatio, Path.Direction.CW);
        this.mShapePath.addRect(this.mButtonFrame, Path.Direction.CW);
        this.mOutlinePath.addRoundRect(this.mFrame, radiusRatio, radiusRatio, Path.Direction.CW);
        Path path = new Path();
        path.addRect(this.mButtonFrame, Path.Direction.CW);
        this.mOutlinePath.op(path, Path.Op.XOR);
        if (this.mCharging) {
            RectF rectF4 = this.mFrame;
            float width = rectF4.left + (rectF4.width() / 4.0f) + 1.0f;
            RectF rectF5 = this.mFrame;
            float height2 = rectF5.top + (rectF5.height() / 6.0f);
            RectF rectF6 = this.mFrame;
            float width2 = (rectF6.right - (rectF6.width() / 4.0f)) + 1.0f;
            RectF rectF7 = this.mFrame;
            float height3 = rectF7.bottom - (rectF7.height() / 10.0f);
            RectF rectF8 = this.mBoltFrame;
            if (rectF8.left != width || rectF8.top != height2 || rectF8.right != width2 || rectF8.bottom != height3) {
                rectF8.set(width, height2, width2, height3);
                this.mBoltPath.reset();
                Path path2 = this.mBoltPath;
                RectF rectF9 = this.mBoltFrame;
                float f10 = rectF9.left;
                float f11 = this.mBoltPoints[0];
                float width3 = rectF9.width();
                RectF rectF10 = this.mBoltFrame;
                path2.moveTo(f10 + (f11 * width3), rectF10.top + (this.mBoltPoints[1] * rectF10.height()));
                int i6 = 2;
                while (true) {
                    fArr2 = this.mBoltPoints;
                    if (i6 >= fArr2.length) {
                        break;
                    }
                    Path path3 = this.mBoltPath;
                    RectF rectF11 = this.mBoltFrame;
                    float f12 = rectF11.left;
                    float f13 = fArr2[i6];
                    float width4 = rectF11.width();
                    RectF rectF12 = this.mBoltFrame;
                    path3.lineTo(f12 + (f13 * width4), rectF12.top + (this.mBoltPoints[i6 + 1] * rectF12.height()));
                    i6 += 2;
                }
                Path path4 = this.mBoltPath;
                RectF rectF13 = this.mBoltFrame;
                float f14 = rectF13.left;
                float f15 = fArr2[0];
                float width5 = rectF13.width();
                RectF rectF14 = this.mBoltFrame;
                path4.lineTo(f14 + (f15 * width5), rectF14.top + (this.mBoltPoints[1] * rectF14.height()));
            }
            RectF rectF15 = this.mBoltFrame;
            float f16 = rectF15.bottom;
            if (Math.min(Math.max((f16 - height) / (f16 - rectF15.top), (float) ActionBarShadowController.ELEVATION_LOW), 1.0f) <= 0.3f) {
                canvas.drawPath(this.mBoltPath, this.mBoltPaint);
            } else {
                this.mShapePath.op(this.mBoltPath, Path.Op.DIFFERENCE);
            }
        } else if (this.mPowerSaveEnabled) {
            float width6 = (this.mFrame.width() * 2.0f) / 3.0f;
            RectF rectF16 = this.mFrame;
            float width7 = rectF16.left + ((rectF16.width() - width6) / 2.0f);
            RectF rectF17 = this.mFrame;
            float height4 = rectF17.top + ((rectF17.height() - width6) / 2.0f);
            RectF rectF18 = this.mFrame;
            float width8 = rectF18.right - ((rectF18.width() - width6) / 2.0f);
            RectF rectF19 = this.mFrame;
            float height5 = rectF19.bottom - ((rectF19.height() - width6) / 2.0f);
            RectF rectF20 = this.mPlusFrame;
            if (rectF20.left != width7 || rectF20.top != height4 || rectF20.right != width8 || rectF20.bottom != height5) {
                rectF20.set(width7, height4, width8, height5);
                this.mPlusPath.reset();
                Path path5 = this.mPlusPath;
                RectF rectF21 = this.mPlusFrame;
                float f17 = rectF21.left;
                float f18 = this.mPlusPoints[0];
                float width9 = rectF21.width();
                RectF rectF22 = this.mPlusFrame;
                path5.moveTo(f17 + (f18 * width9), rectF22.top + (this.mPlusPoints[1] * rectF22.height()));
                int i7 = 2;
                while (true) {
                    fArr = this.mPlusPoints;
                    if (i7 >= fArr.length) {
                        break;
                    }
                    Path path6 = this.mPlusPath;
                    RectF rectF23 = this.mPlusFrame;
                    float f19 = rectF23.left;
                    float f20 = fArr[i7];
                    float width10 = rectF23.width();
                    RectF rectF24 = this.mPlusFrame;
                    path6.lineTo(f19 + (f20 * width10), rectF24.top + (this.mPlusPoints[i7 + 1] * rectF24.height()));
                    i7 += 2;
                }
                Path path7 = this.mPlusPath;
                RectF rectF25 = this.mPlusFrame;
                float f21 = rectF25.left;
                float f22 = fArr[0];
                float width11 = rectF25.width();
                RectF rectF26 = this.mPlusFrame;
                path7.lineTo(f21 + (f22 * width11), rectF26.top + (this.mPlusPoints[1] * rectF26.height()));
            }
            this.mShapePath.op(this.mPlusPath, Path.Op.DIFFERENCE);
            if (this.mPowerSaveAsColorError) {
                canvas.drawPath(this.mPlusPath, this.mPlusPaint);
            }
        }
        String str = null;
        if (this.mCharging || this.mPowerSaveEnabled || i <= this.mCriticalLevel || !this.mShowPercent) {
            f2 = 0.0f;
            f3 = 0.0f;
            z = false;
        } else {
            this.mTextPaint.setColor(getColorForLevel(i));
            this.mTextPaint.setTextSize(f5 * (this.mLevel == 100 ? 0.38f : 0.5f));
            this.mTextHeight = -this.mTextPaint.getFontMetrics().ascent;
            String valueOf = String.valueOf(i);
            float f23 = (this.mWidth * 0.5f) + f6;
            float f24 = ((this.mHeight + this.mTextHeight) * 0.47f) + f7;
            boolean z2 = false;
            if (height > f24) {
                z2 = true;
            }
            str = valueOf;
            f2 = f23;
            z = z2;
            f3 = f24;
            if (!z2) {
                this.mTextPath.reset();
                this.mTextPaint.getTextPath(valueOf, 0, valueOf.length(), f23, f24, this.mTextPath);
                this.mShapePath.op(this.mTextPath, Path.Op.DIFFERENCE);
                str = valueOf;
                f2 = f23;
                z = z2;
                f3 = f24;
            }
        }
        canvas.drawPath(this.mShapePath, this.mFramePaint);
        this.mFrame.top = height;
        canvas.save();
        canvas.clipRect(this.mFrame);
        canvas.drawPath(this.mShapePath, this.mBatteryPaint);
        canvas.restore();
        if (!this.mCharging && !this.mPowerSaveEnabled) {
            if (i <= this.mCriticalLevel) {
                canvas.drawText(this.mWarningString, (this.mWidth * 0.5f) + f6, ((this.mHeight + this.mWarningTextHeight) * 0.48f) + f7, this.mWarningTextPaint);
            } else if (z) {
                canvas.drawText(str, f2, f3, this.mTextPaint);
            }
        }
        if (!this.mCharging && this.mPowerSaveEnabled && this.mPowerSaveAsColorError) {
            canvas.drawPath(this.mOutlinePath, this.mPowersavePaint);
        }
    }

    public float getAspectRatio() {
        return 0.58f;
    }

    public final int getColorForLevel(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.mColors;
            if (i2 >= iArr.length) {
                return i3;
            }
            int i4 = iArr[i2];
            i3 = iArr[i2 + 1];
            if (i <= i4) {
                return i2 == iArr.length - 2 ? this.mIconTint : i3;
            }
            i2 += 2;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        Rect rect2 = this.mPadding;
        if (rect2.left == 0 && rect2.top == 0 && rect2.right == 0 && rect2.bottom == 0) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    public float getRadiusRatio() {
        return 0.05882353f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        updateSize();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mFramePaint.setColorFilter(colorFilter);
        this.mBatteryPaint.setColorFilter(colorFilter);
        this.mWarningTextPaint.setColorFilter(colorFilter);
        this.mBoltPaint.setColorFilter(colorFilter);
        this.mPlusPaint.setColorFilter(colorFilter);
    }

    public final void updateSize() {
        Rect bounds = getBounds();
        int i = bounds.bottom;
        Rect rect = this.mPadding;
        int i2 = (i - rect.bottom) - (bounds.top + rect.top);
        this.mHeight = i2;
        this.mWidth = (bounds.right - rect.right) - (bounds.left + rect.left);
        this.mWarningTextPaint.setTextSize(i2 * 0.75f);
        this.mWarningTextHeight = -this.mWarningTextPaint.getFontMetrics().ascent;
    }
}