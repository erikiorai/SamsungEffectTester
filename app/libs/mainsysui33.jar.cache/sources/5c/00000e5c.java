package com.android.settingslib.graph;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import com.android.settingslib.R$array;
import com.android.settingslib.R$color;
import com.android.settingslib.R$dimen;
import com.android.settingslib.R$string;
import com.android.settingslib.Utils;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/settingslib/graph/CircleBatteryDrawable.class */
public final class CircleBatteryDrawable extends Drawable {
    public static final Companion Companion = new Companion(null);
    public final Paint batteryPaint;
    public final Paint boltPaint;
    public final float[] boltPoints;
    public int chargeColor;
    public boolean charging;
    public final int[] colors;
    public final Context context;
    public final int criticalLevel;
    public boolean dualTone;
    public final Paint framePaint;
    public int height;
    public int intrinsicHeight;
    public int intrinsicWidth;
    public final Paint plusPaint;
    public boolean powerSaveEnabled;
    public final Paint powerSavePaint;
    public boolean showPercent;
    public final Paint textPaint;
    public final String warningString;
    public final Paint warningTextPaint;
    public int width;
    public final Path boltPath = new Path();
    public final Rect padding = new Rect();
    public final RectF frame = new RectF();
    public final RectF boltFrame = new RectF();
    public int iconTint = -1;
    public int batteryLevel = -1;

    /* loaded from: mainsysui33.jar:com/android/settingslib/graph/CircleBatteryDrawable$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float[] loadPoints(Resources resources, int i) {
            int i2;
            int[] intArray = resources.getIntArray(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < intArray.length; i5 += 2) {
                i3 = Math.max(i3, intArray[i5]);
                i4 = Math.max(i4, intArray[i5 + 1]);
            }
            float[] fArr = new float[intArray.length];
            for (int i6 = 0; i6 < intArray.length; i6 += 2) {
                fArr[i6] = intArray[i6] / i3;
                fArr[i6 + 1] = intArray[i2] / i4;
            }
            return fArr;
        }
    }

    public CircleBatteryDrawable(Context context, int i) {
        this.context = context;
        Resources resources = context.getResources();
        TypedArray obtainTypedArray = resources.obtainTypedArray(R$array.batterymeter_color_levels);
        TypedArray obtainTypedArray2 = resources.obtainTypedArray(R$array.batterymeter_color_values);
        this.colors = new int[obtainTypedArray.length() * 2];
        int length = obtainTypedArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            this.colors[i3] = obtainTypedArray.getInt(i2, 0);
            if (obtainTypedArray2.getType(i2) == 2) {
                this.colors[i3 + 1] = Utils.getColorAttrDefaultColor(this.context, obtainTypedArray2.getThemeAttributeId(i2, 0));
            } else {
                this.colors[i3 + 1] = obtainTypedArray2.getColor(i2, 0);
            }
        }
        obtainTypedArray.recycle();
        obtainTypedArray2.recycle();
        this.warningString = resources.getString(R$string.battery_meter_very_low_overlay_symbol);
        this.criticalLevel = resources.getInteger(17694776);
        Paint paint = new Paint(1);
        this.framePaint = paint;
        paint.setColor(i);
        paint.setDither(true);
        Paint paint2 = new Paint(1);
        this.batteryPaint = paint2;
        paint2.setDither(true);
        Paint paint3 = new Paint(1);
        this.textPaint = paint3;
        paint3.setTypeface(Typeface.create("sans-serif-condensed", 1));
        paint3.setTextAlign(Paint.Align.CENTER);
        Paint paint4 = new Paint(1);
        this.warningTextPaint = paint4;
        paint4.setTypeface(Typeface.create("sans-serif", 1));
        paint4.setTextAlign(Paint.Align.CENTER);
        int[] iArr = this.colors;
        if (iArr.length > 1) {
            paint4.setColor(iArr[1]);
        }
        this.chargeColor = Utils.getColorStateListDefaultColor(this.context, R$color.meter_consumed_color);
        Paint paint5 = new Paint(1);
        this.boltPaint = paint5;
        paint5.setColor(Utils.getColorStateListDefaultColor(this.context, R$color.batterymeter_bolt_color));
        this.boltPoints = Companion.loadPoints(resources, R$array.batterymeter_bolt_points);
        Paint paint6 = new Paint(1);
        this.plusPaint = paint6;
        paint6.setColor(Utils.getColorStateListDefaultColor(this.context, R$color.batterymeter_plus_color));
        Paint paint7 = new Paint(1);
        this.powerSavePaint = paint7;
        paint7.setColor(paint6.getColor());
        paint7.setStyle(Paint.Style.STROKE);
        this.intrinsicWidth = resources.getDimensionPixelSize(R$dimen.battery_width);
        this.intrinsicHeight = resources.getDimensionPixelSize(R$dimen.battery_height);
        this.dualTone = resources.getBoolean(17891386);
    }

    public final int batteryColorForLevel(int i) {
        return (this.charging || this.powerSaveEnabled) ? this.chargeColor : getColorForLevel(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x014b, code lost:
        if ((r0.bottom == r0) == false) goto L31;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void draw(Canvas canvas) {
        int i;
        float[] fArr;
        if (this.batteryLevel == -1) {
            return;
        }
        float min = Math.min(this.width, this.height);
        float f = min / 6.5f;
        this.framePaint.setStrokeWidth(f);
        this.framePaint.setStyle(Paint.Style.STROKE);
        this.batteryPaint.setStrokeWidth(f);
        this.batteryPaint.setStyle(Paint.Style.STROKE);
        this.powerSavePaint.setStrokeWidth(f);
        RectF rectF = this.frame;
        float f2 = f / 2.0f;
        int i2 = this.padding.left;
        float f3 = i2;
        float f4 = min - f2;
        rectF.set(f3 + f2, f2, i2 + f4, f4);
        this.batteryPaint.setColor(batteryColorForLevel(this.batteryLevel));
        if (this.charging) {
            RectF rectF2 = this.frame;
            float width = rectF2.left + (rectF2.width() / 3.0f);
            RectF rectF3 = this.frame;
            float height = rectF3.top + (rectF3.height() / 3.4f);
            RectF rectF4 = this.frame;
            float width2 = rectF4.right - (rectF4.width() / 4.0f);
            RectF rectF5 = this.frame;
            float height2 = rectF5.bottom - (rectF5.height() / 5.6f);
            RectF rectF6 = this.boltFrame;
            if (rectF6.left == width) {
                if (rectF6.top == height) {
                    if (rectF6.right == width2) {
                    }
                }
            }
            rectF6.set(width, height, width2, height2);
            this.boltPath.reset();
            Path path = this.boltPath;
            RectF rectF7 = this.boltFrame;
            float f5 = rectF7.left;
            float f6 = this.boltPoints[0];
            float width3 = rectF7.width();
            RectF rectF8 = this.boltFrame;
            path.moveTo(f5 + (f6 * width3), rectF8.top + (this.boltPoints[1] * rectF8.height()));
            int i3 = 2;
            while (true) {
                fArr = this.boltPoints;
                if (i3 >= fArr.length) {
                    break;
                }
                Path path2 = this.boltPath;
                RectF rectF9 = this.boltFrame;
                float f7 = rectF9.left;
                float f8 = fArr[i3];
                float width4 = rectF9.width();
                RectF rectF10 = this.boltFrame;
                path2.lineTo(f7 + (f8 * width4), rectF10.top + (this.boltPoints[i3 + 1] * rectF10.height()));
                i3 += 2;
            }
            Path path3 = this.boltPath;
            RectF rectF11 = this.boltFrame;
            float f9 = rectF11.left;
            float f10 = fArr[0];
            float width5 = rectF11.width();
            RectF rectF12 = this.boltFrame;
            path3.lineTo(f9 + (f10 * width5), rectF12.top + (this.boltPoints[1] * rectF12.height()));
            canvas.drawPath(this.boltPath, this.boltPaint);
        }
        canvas.drawArc(this.frame, 270.0f, 360.0f, false, this.framePaint);
        int i4 = this.batteryLevel;
        if (i4 > 0) {
            if (this.charging || !this.powerSaveEnabled) {
                canvas.drawArc(this.frame, 270.0f, i4 * 3.6f, false, this.batteryPaint);
            } else {
                canvas.drawArc(this.frame, 270.0f, i4 * 3.6f, false, this.powerSavePaint);
            }
        }
        if (this.charging || (i = this.batteryLevel) == 100 || !this.showPercent) {
            return;
        }
        this.textPaint.setColor(getColorForLevel(i));
        this.textPaint.setTextSize(this.height * 0.52f);
        float f11 = -this.textPaint.getFontMetrics().ascent;
        int i5 = this.batteryLevel;
        canvas.drawText(i5 > this.criticalLevel ? String.valueOf(i5) : this.warningString, this.width * 0.5f, (this.height + f11) * 0.47f, this.textPaint);
    }

    public final int getColorForLevel(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.colors;
            if (i2 >= iArr.length) {
                return i3;
            }
            int i4 = iArr[i2];
            i3 = iArr[i2 + 1];
            if (i <= i4) {
                if (i2 == iArr.length - 2) {
                    i3 = this.iconTint;
                }
                return i3;
            }
            i2 += 2;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.intrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.intrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        Rect rect2 = this.padding;
        if (rect2.left == 0 && rect2.top == 0 && rect2.right == 0 && rect2.bottom == 0) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    public final void postInvalidate() {
        unscheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.CircleBatteryDrawable$postInvalidate$1
            @Override // java.lang.Runnable
            public final void run() {
                CircleBatteryDrawable.this.invalidateSelf();
            }
        });
        scheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.CircleBatteryDrawable$postInvalidate$2
            @Override // java.lang.Runnable
            public final void run() {
                CircleBatteryDrawable.this.invalidateSelf();
            }
        }, 0L);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public final void setBatteryLevel(int i) {
        this.batteryLevel = i;
        postInvalidate();
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        updateSize();
    }

    public final void setCharging(boolean z) {
        this.charging = z;
        postInvalidate();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.framePaint.setColorFilter(colorFilter);
        this.batteryPaint.setColorFilter(colorFilter);
        this.warningTextPaint.setColorFilter(colorFilter);
        this.boltPaint.setColorFilter(colorFilter);
        this.plusPaint.setColorFilter(colorFilter);
    }

    public final void setColors(int i, int i2, int i3) {
        if (!this.dualTone) {
            i = i3;
        }
        this.iconTint = i;
        this.framePaint.setColor(i2);
        this.boltPaint.setColor(i);
        this.chargeColor = i;
        invalidateSelf();
    }

    public final void setPowerSaveEnabled(boolean z) {
        this.powerSaveEnabled = z;
        postInvalidate();
    }

    public final void setShowPercent(boolean z) {
        this.showPercent = z;
        postInvalidate();
    }

    public final void updateSize() {
        Resources resources = this.context.getResources();
        this.height = (getBounds().bottom - this.padding.bottom) - (getBounds().top + this.padding.top);
        this.width = (getBounds().right - this.padding.right) - (getBounds().left + this.padding.left);
        this.warningTextPaint.setTextSize(this.height * 0.75f);
        int i = R$dimen.battery_height;
        this.intrinsicHeight = resources.getDimensionPixelSize(i);
        this.intrinsicWidth = resources.getDimensionPixelSize(i);
    }
}