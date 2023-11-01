package com.android.settingslib.graph;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.PathParser;
import com.android.settingslib.R$array;
import com.android.settingslib.R$color;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/settingslib/graph/ThemedBatteryDrawable.class */
public class ThemedBatteryDrawable extends Drawable {
    public static final Companion Companion = new Companion(null);
    public int batteryLevel;
    public boolean charging;
    public int[] colorLevels;
    public final Context context;
    public int criticalLevel;
    public boolean dualTone;
    public final Paint dualToneBackgroundFill;
    public final Paint errorPaint;
    public final Paint fillColorStrokePaint;
    public final Paint fillColorStrokeProtection;
    public final Paint fillPaint;
    public int intrinsicHeight;
    public int intrinsicWidth;
    public boolean invertFillIcon;
    public boolean powerSaveEnabled;
    public boolean showPercent;
    public final Paint textPaint;
    public final Path perimeterPath = new Path();
    public final Path scaledPerimeter = new Path();
    public final Path errorPerimeterPath = new Path();
    public final Path scaledErrorPerimeter = new Path();
    public final Path fillMask = new Path();
    public final Path scaledFill = new Path();
    public final RectF fillRect = new RectF();
    public final RectF levelRect = new RectF();
    public final Path levelPath = new Path();
    public final Matrix scaleMatrix = new Matrix();
    public final Rect padding = new Rect();
    public final Path unifiedPath = new Path();
    public final Path boltPath = new Path();
    public final Path scaledBolt = new Path();
    public final Path plusPath = new Path();
    public final Path scaledPlus = new Path();
    public int fillColor = -65281;
    public int backgroundColor = -65281;
    public int levelColor = -65281;
    public final Function0<Unit> invalidateRunnable = new Function0<Unit>() { // from class: com.android.settingslib.graph.ThemedBatteryDrawable$invalidateRunnable$1
        {
            super(0);
        }

        public /* bridge */ /* synthetic */ Object invoke() {
            m1099invoke();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke  reason: collision with other method in class */
        public final void m1099invoke() {
            ThemedBatteryDrawable.this.invalidateSelf();
        }
    };

    /* loaded from: mainsysui33.jar:com/android/settingslib/graph/ThemedBatteryDrawable$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ThemedBatteryDrawable(Context context, int i) {
        this.context = context;
        this.criticalLevel = context.getResources().getInteger(17694776);
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setAlpha(255);
        paint.setDither(true);
        paint.setStrokeWidth(5.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setBlendMode(BlendMode.SRC);
        paint.setStrokeMiter(5.0f);
        paint.setStrokeJoin(Paint.Join.ROUND);
        this.fillColorStrokePaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setDither(true);
        paint2.setStrokeWidth(5.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setBlendMode(BlendMode.CLEAR);
        paint2.setStrokeMiter(5.0f);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        this.fillColorStrokeProtection = paint2;
        Paint paint3 = new Paint(1);
        paint3.setColor(i);
        paint3.setAlpha(255);
        paint3.setDither(true);
        paint3.setStrokeWidth(ActionBarShadowController.ELEVATION_LOW);
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);
        this.fillPaint = paint3;
        Paint paint4 = new Paint(1);
        paint4.setColor(Utils.getColorStateListDefaultColor(context, R$color.batterymeter_plus_color));
        paint4.setAlpha(255);
        paint4.setDither(true);
        paint4.setStrokeWidth(ActionBarShadowController.ELEVATION_LOW);
        paint4.setStyle(Paint.Style.FILL_AND_STROKE);
        paint4.setBlendMode(BlendMode.SRC);
        this.errorPaint = paint4;
        Paint paint5 = new Paint(1);
        paint5.setColor(i);
        paint5.setAlpha(85);
        paint5.setDither(true);
        paint5.setStrokeWidth(ActionBarShadowController.ELEVATION_LOW);
        paint5.setStyle(Paint.Style.FILL_AND_STROKE);
        this.dualToneBackgroundFill = paint5;
        Paint paint6 = new Paint(1);
        paint6.setTypeface(Typeface.create("sans-serif-condensed", 1));
        paint6.setTextAlign(Paint.Align.CENTER);
        this.textPaint = paint6;
        float f = context.getResources().getDisplayMetrics().density;
        this.intrinsicHeight = (int) (20.0f * f);
        this.intrinsicWidth = (int) (f * 12.0f);
        Resources resources = context.getResources();
        TypedArray obtainTypedArray = resources.obtainTypedArray(R$array.batterymeter_color_levels);
        TypedArray obtainTypedArray2 = resources.obtainTypedArray(R$array.batterymeter_color_values);
        int length = obtainTypedArray.length();
        this.colorLevels = new int[length * 2];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            this.colorLevels[i3] = obtainTypedArray.getInt(i2, 0);
            if (obtainTypedArray2.getType(i2) == 2) {
                this.colorLevels[i3 + 1] = Utils.getColorAttrDefaultColor(this.context, obtainTypedArray2.getThemeAttributeId(i2, 0));
            } else {
                this.colorLevels[i3 + 1] = obtainTypedArray2.getColor(i2, 0);
            }
        }
        obtainTypedArray.recycle();
        obtainTypedArray2.recycle();
        loadPaths();
    }

    public final int batteryColorForLevel(int i) {
        return (this.charging || this.powerSaveEnabled) ? this.fillColor : getColorForLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float height;
        canvas.saveLayer(null, null);
        this.unifiedPath.reset();
        this.levelPath.reset();
        this.levelRect.set(this.fillRect);
        int i = this.batteryLevel;
        float f = i / 100.0f;
        if (i >= 95) {
            height = this.fillRect.top;
        } else {
            RectF rectF = this.fillRect;
            height = (rectF.height() * (1 - f)) + rectF.top;
        }
        this.levelRect.top = (float) Math.floor(height);
        this.levelPath.addRect(this.levelRect, Path.Direction.CCW);
        this.unifiedPath.addPath(this.scaledPerimeter);
        if (!this.dualTone) {
            this.unifiedPath.op(this.levelPath, Path.Op.UNION);
        }
        this.fillPaint.setColor(this.levelColor);
        if (this.charging) {
            this.unifiedPath.op(this.scaledBolt, Path.Op.DIFFERENCE);
            if (!this.invertFillIcon) {
                canvas.drawPath(this.scaledBolt, this.fillPaint);
            }
        }
        if (this.dualTone) {
            canvas.drawPath(this.unifiedPath, this.dualToneBackgroundFill);
            canvas.save();
            canvas.clipRect(ActionBarShadowController.ELEVATION_LOW, getBounds().bottom - (getBounds().height() * f), getBounds().right, getBounds().bottom);
            canvas.drawPath(this.unifiedPath, this.fillPaint);
            canvas.restore();
        } else {
            this.fillPaint.setColor(this.fillColor);
            canvas.drawPath(this.unifiedPath, this.fillPaint);
            this.fillPaint.setColor(this.levelColor);
            if (this.batteryLevel <= 15 && !this.charging) {
                canvas.save();
                canvas.clipPath(this.scaledFill);
                canvas.drawPath(this.levelPath, this.fillPaint);
                canvas.restore();
            }
        }
        if (this.charging) {
            canvas.clipOutPath(this.scaledBolt);
            if (this.invertFillIcon) {
                canvas.drawPath(this.scaledBolt, this.fillColorStrokePaint);
            } else {
                canvas.drawPath(this.scaledBolt, this.fillColorStrokeProtection);
            }
        } else if (this.powerSaveEnabled) {
            canvas.drawPath(this.scaledErrorPerimeter, this.errorPaint);
            canvas.drawPath(this.scaledPlus, this.errorPaint);
        }
        canvas.restore();
        if (this.charging || this.batteryLevel >= 100 || !this.showPercent) {
            return;
        }
        this.textPaint.setTextSize(getBounds().height() * 0.38f);
        float f2 = -this.textPaint.getFontMetrics().ascent;
        float width = getBounds().width() * 0.5f;
        float height2 = (getBounds().height() + f2) * 0.5f;
        this.textPaint.setColor(this.fillColor);
        canvas.drawText(String.valueOf(this.batteryLevel), width, height2, this.textPaint);
        this.textPaint.setColor((this.fillColor ^ (-1)) | (-16777216));
        canvas.save();
        RectF rectF2 = this.fillRect;
        float f3 = rectF2.left;
        float f4 = rectF2.top;
        float height3 = rectF2.height();
        RectF rectF3 = this.fillRect;
        canvas.clipRect(f3, f4 + (height3 * (1 - f)), rectF3.right, rectF3.bottom);
        canvas.drawText(String.valueOf(this.batteryLevel), width, height2, this.textPaint);
        canvas.restore();
    }

    public final int getColorForLevel(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.colorLevels;
            if (i2 >= iArr.length) {
                return i3;
            }
            int i4 = iArr[i2];
            i3 = iArr[i2 + 1];
            if (i <= i4) {
                if (i2 == iArr.length - 2) {
                    i3 = this.fillColor;
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
        return -1;
    }

    public final void loadPaths() {
        this.perimeterPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039902)));
        this.perimeterPath.computeBounds(new RectF(), true);
        this.errorPerimeterPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039900)));
        this.errorPerimeterPath.computeBounds(new RectF(), true);
        this.fillMask.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039901)));
        this.fillMask.computeBounds(this.fillRect, true);
        this.boltPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039899)));
        this.plusPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039903)));
        this.dualTone = this.context.getResources().getBoolean(17891386);
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateSize();
    }

    public final void postInvalidate() {
        final Function0<Unit> function0 = this.invalidateRunnable;
        unscheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.ThemedBatteryDrawable$sam$java_lang_Runnable$0
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                function0.invoke();
            }
        });
        final Function0<Unit> function02 = this.invalidateRunnable;
        scheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.ThemedBatteryDrawable$sam$java_lang_Runnable$0
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                function02.invoke();
            }
        }, 0L);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public void setBatteryLevel(int i) {
        this.invertFillIcon = i >= 67 ? true : i <= 33 ? false : this.invertFillIcon;
        this.batteryLevel = i;
        this.levelColor = batteryColorForLevel(i);
        invalidateSelf();
    }

    public final void setCharging(boolean z) {
        this.charging = z;
        postInvalidate();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.fillPaint.setColorFilter(colorFilter);
        this.fillColorStrokePaint.setColorFilter(colorFilter);
        this.dualToneBackgroundFill.setColorFilter(colorFilter);
    }

    public final void setColors(int i, int i2, int i3) {
        if (!this.dualTone) {
            i = i3;
        }
        this.fillColor = i;
        this.fillPaint.setColor(i);
        this.fillColorStrokePaint.setColor(this.fillColor);
        this.backgroundColor = i2;
        this.dualToneBackgroundFill.setColor(i2);
        this.levelColor = batteryColorForLevel(this.batteryLevel);
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
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            this.scaleMatrix.setScale(1.0f, 1.0f);
        } else {
            this.scaleMatrix.setScale(bounds.right / 12.0f, bounds.bottom / 20.0f);
        }
        this.perimeterPath.transform(this.scaleMatrix, this.scaledPerimeter);
        this.errorPerimeterPath.transform(this.scaleMatrix, this.scaledErrorPerimeter);
        this.fillMask.transform(this.scaleMatrix, this.scaledFill);
        this.scaledFill.computeBounds(this.fillRect, true);
        this.boltPath.transform(this.scaleMatrix, this.scaledBolt);
        this.plusPath.transform(this.scaleMatrix, this.scaledPlus);
        float max = Math.max((bounds.right / 12.0f) * 3.0f, 6.0f);
        this.fillColorStrokePaint.setStrokeWidth(max);
        this.fillColorStrokeProtection.setStrokeWidth(max);
    }
}