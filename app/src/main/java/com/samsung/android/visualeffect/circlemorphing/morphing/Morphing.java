package com.samsung.android.visualeffect.circlemorphing.morphing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/* loaded from: classes10.dex */
public class Morphing {
    private static /* synthetic */ int[] STATUS_INT;
    private float dist;
    private float distx;
    private float disty;
    private StatusChangedListener listener;
    private float maxAngle1;
    private float maxAngle2;
    private float minAngle1;
    private float minAngle2;
    private Path morphPath;
    private double openAngle1;
    private double openAngle2;
    private Paint paint;
    private double rad1;
    private double rad2;
    public float radius1;
    public float radius2;
    public boolean showCircle1;
    public boolean showCircle2;
    private float smallestRadius;
    public float threshold;
    public float x1;
    public float x2;
    public float y1;
    public float y2;
    private final String TAG = "visualeffectCircleMorphingEffect";
    private final int ABSORB_LIFE = 60;
    private final float ABSORB_MAX_RATIO = 0.45f;
    private float absorbCount = 0.0f;
    private boolean isWireframe = false;
    private boolean isSticked = false;
    private STATUS currentStatus = STATUS.SEPARATED;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes10.dex */
    public enum STATUS {
        SEPARATED,
        OVERLAP,
        CONNECTED,
        ABSORBED;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static STATUS[] valuesCustom() {
            STATUS[] valuesCustom = values();
            int length = valuesCustom.length;
            STATUS[] statusArr = new STATUS[length];
            System.arraycopy(valuesCustom, 0, statusArr, 0, length);
            return statusArr;
        }
    }

    /* loaded from: classes10.dex */
    public interface StatusChangedListener {
        void onSeparated();
    }

    static /* synthetic */ int[] STATUS_FUNC() {
        int[] iArr = STATUS_INT;
        if (iArr == null) {
            iArr = new int[STATUS.valuesCustom().length];
            try {
                iArr[STATUS.ABSORBED.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[STATUS.CONNECTED.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[STATUS.OVERLAP.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[STATUS.SEPARATED.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            STATUS_INT = iArr;
        }
        return iArr;
    }

    public void setListener(StatusChangedListener listener) {
        this.listener = listener;
    }

    public Morphing(float threshold, Paint paint) {
        if (threshold == 0) {
            this.threshold = 300.0f;
        } else {
            this.threshold = threshold;
        }
        setPaint(paint);
        this.morphPath = new Path();
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
        this.isWireframe = paint.getStyle() == Paint.Style.STROKE;
    }

    public void setMorph(float x1, float y1, float radius1, boolean showCircle1, float x2, float y2, float radius2, boolean showCircle2) {
        this.x1 = x1;
        this.y1 = y1;
        this.radius1 = radius1;
        this.showCircle1 = showCircle1;
        this.x2 = x2;
        this.y2 = y2;
        this.radius2 = radius2;
        this.showCircle2 = showCircle2;
        this.smallestRadius = Math.min(radius1, radius2);
        float ratio = radius1 > radius2 ? radius2 / radius1 : radius1 / radius2;
        float bigMaxAngle = 1.5f * ratio;
        float bigMinAngle = bigMaxAngle / 2.5f;
        if (radius1 > radius2) {
            this.maxAngle1 = bigMaxAngle;
            this.minAngle1 = bigMinAngle;
            this.maxAngle2 = 1.5f;
            this.minAngle2 = 0.5f;
            return;
        }
        this.maxAngle1 = 1.5f;
        this.minAngle1 = 0.5f;
        this.maxAngle2 = bigMaxAngle;
        this.minAngle2 = bigMinAngle;
    }

    public void clear() {
        this.currentStatus = STATUS.SEPARATED;
    }

    public void drawMorph(Canvas canvas) {
        this.distx = this.x2 - this.x1;
        this.disty = this.y2 - this.y1;
        this.dist = (float) Math.sqrt((this.distx * this.distx) + (this.disty * this.disty));
        if (this.dist <= this.radius1 + this.radius2) {
            this.currentStatus = STATUS.OVERLAP;
            this.isSticked = true;
        } else if (this.isSticked) {
            if (this.dist < this.radius1 + this.radius2 + this.threshold) {
                this.currentStatus = STATUS.CONNECTED;
            } else {
                this.isSticked = false;
                this.currentStatus = STATUS.ABSORBED;
                this.absorbCount = 60.0f;
                this.listener.onSeparated();
            }
        } else if (this.absorbCount > 0.0f) {
            this.absorbCount -= 1.0f;
        } else {
            this.currentStatus = STATUS.SEPARATED;
            return;
        }
        switch (STATUS_FUNC()[this.currentStatus.ordinal()]) {
            case 1:
            default:
                return;
            case 2:
            case 3:
                drawMorphShape(canvas);
                return;
            case 4:
                drawAbsorbShape(canvas);
        }
    }

    private void drawAbsorbShape(Canvas canvas) {
        float first_x1 = this.x1 + (((float) Math.sin(this.rad1 + this.openAngle1)) * this.radius1);
        float first_y1 = this.y1 + (((float) Math.cos(this.rad1 + this.openAngle1)) * this.radius1);
        float first_x2 = this.x1 + (((float) Math.sin(this.rad1 - this.openAngle1)) * this.radius1);
        float first_y2 = this.y1 + (((float) Math.cos(this.rad1 - this.openAngle1)) * this.radius1);
        float second_x1 = this.x2 + (((float) Math.sin(this.rad2 + this.openAngle2)) * this.radius2);
        float second_y1 = this.y2 + (((float) Math.cos(this.rad2 + this.openAngle2)) * this.radius2);
        float second_x2 = this.x2 + (((float) Math.sin(this.rad2 - this.openAngle2)) * this.radius2);
        float second_y2 = this.y2 + (((float) Math.cos(this.rad2 - this.openAngle2)) * this.radius2);
        float animationRatio = quintEaseIn(this.absorbCount / 60.0f);
        float ratio1 = 1.05f + (0.45f * animationRatio);
        float ratio2 = ratio1;
        if (this.openAngle1 > this.openAngle2) {
            ratio2 = 1.0f + ((ratio1 - 1.0f) * ((float) (this.openAngle2 / this.openAngle1)));
        } else {
            ratio1 = 1.0f + ((ratio1 - 1.0f) * ((float) (this.openAngle1 / this.openAngle2)));
        }
        float firstAnchorX = this.x1 + (((float) Math.sin(this.rad1)) * this.radius1 * ratio1);
        float firstAnchorY = this.y1 + (((float) Math.cos(this.rad1)) * this.radius1 * ratio1);
        float secondAnchorX = this.x2 + (((float) Math.sin(this.rad2)) * this.radius2 * ratio2);
        float secondAnchorY = this.y2 + (((float) Math.cos(this.rad2)) * this.radius2 * ratio2);
        this.morphPath.reset();
        this.morphPath.moveTo(first_x1, first_y1);
        this.morphPath.lineTo(first_x2, first_y2);
        this.morphPath.quadTo(firstAnchorX, firstAnchorY, first_x1, first_y1);
        this.morphPath.moveTo(second_x1, second_y1);
        this.morphPath.lineTo(second_x2, second_y2);
        this.morphPath.quadTo(secondAnchorX, secondAnchorY, second_x1, second_y1);
        this.morphPath.close();
        canvas.drawPath(this.morphPath, this.paint);
        if (this.isWireframe) {
            canvas.drawLine(first_x1, first_y1, this.x1, this.y1, this.paint);
            canvas.drawLine(this.x1, this.y1, first_x2, first_y2, this.paint);
            canvas.drawLine(second_x1, second_y1, this.x2, this.y2, this.paint);
            canvas.drawLine(this.x2, this.y2, second_x2, second_y2, this.paint);
            canvas.drawCircle(firstAnchorX, firstAnchorY, 10.0f, this.paint);
            canvas.drawCircle(secondAnchorX, secondAnchorY, 10.0f, this.paint);
        }
    }

    private void drawMorphShape(Canvas canvas) {
        double rad = Math.atan2(this.disty, this.distx);
        this.rad1 = (-rad) + 1.5707963267948966d;
        this.rad2 = (-rad) - 1.5707963267948966d;
        float gap = (this.dist - this.radius1) - this.radius2;
        this.openAngle1 = this.maxAngle1 + (((this.smallestRadius + gap) * (this.minAngle1 - this.maxAngle1)) / (this.threshold + this.smallestRadius));
        this.openAngle2 = this.maxAngle2 + (((this.smallestRadius + gap) * (this.minAngle2 - this.maxAngle2)) / (this.threshold + this.smallestRadius));
        float first_x1 = this.x1 + (((float) Math.sin(this.rad1 + this.openAngle1)) * this.radius1);
        float first_y1 = this.y1 + (((float) Math.cos(this.rad1 + this.openAngle1)) * this.radius1);
        float first_x2 = this.x1 + (((float) Math.sin(this.rad1 - this.openAngle1)) * this.radius1);
        float first_y2 = this.y1 + (((float) Math.cos(this.rad1 - this.openAngle1)) * this.radius1);
        float second_x1 = this.x2 + (((float) Math.sin(this.rad2 + this.openAngle2)) * this.radius2);
        float second_y1 = this.y2 + (((float) Math.cos(this.rad2 + this.openAngle2)) * this.radius2);
        float second_x2 = this.x2 + (((float) Math.sin(this.rad2 - this.openAngle2)) * this.radius2);
        float second_y2 = this.y2 + (((float) Math.cos(this.rad2 - this.openAngle2)) * this.radius2);
        float centerX = (((first_x1 + first_x2) + second_x1) + second_x2) / 4.0f;
        float centerY = (((first_y1 + first_y2) + second_y1) + second_y2) / 4.0f;
        float centerLineLength = (((float) Math.sin(this.openAngle1)) * this.radius1) + (((float) Math.sin(this.openAngle2)) * this.radius2);
        float anchorDistRatio = (this.smallestRadius + gap) / (this.threshold + this.smallestRadius);
        if (anchorDistRatio < 0.0f) {
            anchorDistRatio = 0.0f;
        }
        float anchorDistRatioInterpolator = cubicEaseIn(anchorDistRatio);
        float anchorDist = centerLineLength * (anchorDistRatioInterpolator - 0.5f);
        float anchorx1 = centerX - (((float) Math.sin(-rad)) * anchorDist);
        float anchory1 = centerY - (((float) Math.cos(-rad)) * anchorDist);
        float anchorx2 = centerX + (((float) Math.sin(-rad)) * anchorDist);
        float anchory2 = centerY + (((float) Math.cos(-rad)) * anchorDist);
        this.morphPath.reset();
        this.morphPath.moveTo(first_x1, first_y1);
        this.morphPath.lineTo(first_x2, first_y2);
        this.morphPath.quadTo(anchorx1, anchory1, second_x1, second_y1);
        this.morphPath.lineTo(second_x2, second_y2);
        this.morphPath.quadTo(anchorx2, anchory2, first_x1, first_y1);
        this.morphPath.close();
        canvas.drawPath(this.morphPath, this.paint);
        if (this.isWireframe) {
            canvas.drawLine(this.x1, this.y1, this.x2, this.y2, this.paint);
            canvas.drawLine(first_x1, first_y1, this.x1, this.y1, this.paint);
            canvas.drawLine(this.x1, this.y1, first_x2, first_y2, this.paint);
            canvas.drawLine(second_x1, second_y1, this.x2, this.y2, this.paint);
            canvas.drawLine(this.x2, this.y2, second_x2, second_y2, this.paint);
            canvas.drawCircle(anchorx1, anchory1, 10.0f, this.paint);
            canvas.drawCircle(anchorx2, anchory2, 10.0f, this.paint);
        }
    }

    private float cubicEaseIn(float t) {
        return t * t * t;
    }

    private float quintEaseIn(float t) {
        return t * t * t * t * t;
    }
}