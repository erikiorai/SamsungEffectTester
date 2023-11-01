package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.Log;
import android.view.ViewDebug;
import com.android.launcher3.icons.ShadowGenerator;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/DotRenderer.class */
public class DotRenderer {
    public final Bitmap mBackgroundWithShadow;
    public final float mBitmapOffset;
    public final Paint mCirclePaint = new Paint(3);
    public final float mCircleRadius;
    public final float[] mLeftDotPosition;
    public final float[] mRightDotPosition;

    /* loaded from: mainsysui33.jar:com/android/launcher3/icons/DotRenderer$DrawParams.class */
    public static class DrawParams {
        @ViewDebug.ExportedProperty(category = "notification dot", formatToHexString = true)
        public int dotColor;
        @ViewDebug.ExportedProperty(category = "notification dot")
        public Rect iconBounds = new Rect();
        @ViewDebug.ExportedProperty(category = "notification dot")
        public boolean leftAlign;
        @ViewDebug.ExportedProperty(category = "notification dot")
        public float scale;
    }

    public DotRenderer(int i, Path path, int i2) {
        Bitmap createPill;
        int round = Math.round(i * 0.228f);
        int i3 = round <= 0 ? 1 : round;
        ShadowGenerator.Builder builder = new ShadowGenerator.Builder(0);
        builder.ambientShadowAlpha = 88;
        this.mBackgroundWithShadow = builder.setupBlurForSize(i3).createPill(i3, i3);
        this.mCircleRadius = builder.radius;
        this.mBitmapOffset = (-createPill.getHeight()) * 0.5f;
        float f = i2;
        this.mLeftDotPosition = getPathPoint(path, f, -1.0f);
        this.mRightDotPosition = getPathPoint(path, f, 1.0f);
    }

    public static float[] getPathPoint(Path path, float f, float f2) {
        float f3 = f / 2.0f;
        float f4 = (f2 * f3) + f3;
        Path path2 = new Path();
        path2.moveTo(f3, f3);
        path2.lineTo((f2 * 1.0f) + f4, ActionBarShadowController.ELEVATION_LOW);
        path2.lineTo(f4, -1.0f);
        path2.close();
        path2.op(path, Path.Op.INTERSECT);
        new PathMeasure(path2, false).getPosTan(ActionBarShadowController.ELEVATION_LOW, r0, null);
        float[] fArr = {fArr[0] / f, fArr[1] / f};
        return fArr;
    }

    public void draw(Canvas canvas, DrawParams drawParams) {
        if (drawParams == null) {
            Log.e("DotRenderer", "Invalid null argument(s) passed in call to draw.");
            return;
        }
        canvas.save();
        Rect rect = drawParams.iconBounds;
        float[] fArr = drawParams.leftAlign ? this.mLeftDotPosition : this.mRightDotPosition;
        float width = rect.left + (rect.width() * fArr[0]);
        float height = rect.top + (rect.height() * fArr[1]);
        Rect clipBounds = canvas.getClipBounds();
        canvas.translate(width + (drawParams.leftAlign ? Math.max((float) ActionBarShadowController.ELEVATION_LOW, clipBounds.left - (this.mBitmapOffset + width)) : Math.min((float) ActionBarShadowController.ELEVATION_LOW, clipBounds.right - (width - this.mBitmapOffset))), height + Math.max((float) ActionBarShadowController.ELEVATION_LOW, clipBounds.top - (this.mBitmapOffset + height)));
        float f = drawParams.scale;
        canvas.scale(f, f);
        this.mCirclePaint.setColor(-16777216);
        Bitmap bitmap = this.mBackgroundWithShadow;
        float f2 = this.mBitmapOffset;
        canvas.drawBitmap(bitmap, f2, f2, this.mCirclePaint);
        this.mCirclePaint.setColor(drawParams.dotColor);
        canvas.drawCircle(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, this.mCircleRadius, this.mCirclePaint);
        canvas.restore();
    }

    public float[] getLeftDotPosition() {
        return this.mLeftDotPosition;
    }

    public float[] getRightDotPosition() {
        return this.mRightDotPosition;
    }
}