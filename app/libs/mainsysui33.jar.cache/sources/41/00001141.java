package com.android.systemui.battery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.PathParser;
import com.android.settingslib.graph.ThemedBatteryDrawable;
import com.android.systemui.R$string;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/battery/AccessorizedBatteryDrawable.class */
public final class AccessorizedBatteryDrawable extends DrawableWrapper {
    public final Context context;
    public float density;
    public boolean displayShield;
    public final boolean dualTone;
    public final Matrix scaleMatrix;
    public final Path scaledShield;
    public float shieldLeftOffsetScaled;
    public final Paint shieldPaint;
    public final Path shieldPath;
    public float shieldTopOffsetScaled;
    public final Paint shieldTransparentOutlinePaint;

    public AccessorizedBatteryDrawable(Context context, int i) {
        super(new ThemedBatteryDrawable(context, i));
        this.context = context;
        this.shieldPath = new Path();
        this.scaledShield = new Path();
        this.scaleMatrix = new Matrix();
        this.shieldLeftOffsetScaled = 8.0f;
        this.shieldTopOffsetScaled = 10.0f;
        this.density = context.getResources().getDisplayMetrics().density;
        this.dualTone = context.getResources().getBoolean(17891386);
        Paint paint = new Paint(1);
        paint.setColor(0);
        paint.setStrokeWidth(6.0f);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.shieldTransparentOutlinePaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setColor(-65281);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setDither(true);
        this.shieldPaint = paint2;
        loadPaths();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.saveLayer(null, null);
        super.draw(canvas);
        if (this.displayShield) {
            canvas.translate(this.shieldLeftOffsetScaled, this.shieldTopOffsetScaled);
            canvas.drawPath(this.scaledShield, this.shieldTransparentOutlinePaint);
            canvas.drawPath(this.scaledShield, this.shieldPaint);
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) ((this.displayShield ? 23.0f : 20.0f) * this.density);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) ((this.displayShield ? 18.0f : 12.0f) * this.density);
    }

    public final ThemedBatteryDrawable getMainBatteryDrawable() {
        return (ThemedBatteryDrawable) getDrawable();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    public final void loadPaths() {
        this.shieldPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(R$string.config_batterymeterShieldPath)));
    }

    public final void notifyDensityChanged() {
        this.density = this.context.getResources().getDisplayMetrics().density;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateSizes();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public final void setBatteryLevel(int i) {
        getMainBatteryDrawable().setBatteryLevel(i);
    }

    public final void setCharging(boolean z) {
        getMainBatteryDrawable().setCharging(z);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(getColorFilter());
        this.shieldPaint.setColorFilter(getColorFilter());
    }

    public final void setColors(int i, int i2, int i3) {
        this.shieldPaint.setColor(this.dualTone ? i : i3);
        getMainBatteryDrawable().setColors(i, i2, i3);
    }

    public final void setDisplayShield(boolean z) {
        this.displayShield = z;
    }

    public final void setPowerSaveEnabled(boolean z) {
        getMainBatteryDrawable().setPowerSaveEnabled(z);
    }

    public final void showPercent(boolean z) {
        getMainBatteryDrawable().setShowPercent(z);
    }

    public final void updateSizes() {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            return;
        }
        float mainBatteryWidth = BatterySpecs.getMainBatteryWidth(bounds.width(), this.displayShield);
        float mainBatteryHeight = BatterySpecs.getMainBatteryHeight(bounds.height(), this.displayShield);
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int i = bounds.left;
            int i2 = bounds.top;
            drawable.setBounds(i, i2, ((int) mainBatteryWidth) + i, ((int) mainBatteryHeight) + i2);
        }
        if (this.displayShield) {
            float f = bounds.right / 18.0f;
            float f2 = bounds.bottom / 23.0f;
            this.scaleMatrix.setScale(f, f2);
            this.shieldPath.transform(this.scaleMatrix, this.scaledShield);
            this.shieldLeftOffsetScaled = 8.0f * f;
            this.shieldTopOffsetScaled = f2 * 10.0f;
            this.shieldTransparentOutlinePaint.setStrokeWidth(RangesKt___RangesKt.coerceAtLeast(f * 4.0f, 6.0f));
        }
    }
}