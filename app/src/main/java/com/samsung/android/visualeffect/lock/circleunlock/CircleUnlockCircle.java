package com.samsung.android.visualeffect.lock.circleunlock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/* loaded from: classes.dex */
public class CircleUnlockCircle extends View {
    private final boolean DBG = true;
    private final String TAG = "VisualEffectCircleUnlockEffect";
    private int betweenRadius;
    private int centerX;
    private int centerY;
    private float fillAnimationValue = 0.0f;
    private int fillStrokeAlpha;
    private String fillStrokeColor;
    private Paint fillStrokePaint;
    private int innerStrokeAlpha;
    private String innerStrokeColor;
    private Paint innerStrokePaint;
    private int innerStrokeWidth;
    private boolean isForShortcut = false;
    private boolean isShowSwipeCircle = true;
    private int maxRadius;
    private int minRadius;
    private int outStrokeAlpha;
    private String outStrokeColor;
    private Paint outStrokePaint;
    private int outerStrokeWidth;
    private float strokeAnimationValue = 0.0f;

    public CircleUnlockCircle(Context context, int circleMaxWidth, int circleMinWidth, int outerStrokeWidth, int innerStrokeWidth) {
        super(context);
        int i = circleMaxWidth / 2;
        this.centerY = i;
        this.centerX = i;
        this.maxRadius = i;
        this.minRadius = circleMinWidth / 2;
        this.betweenRadius = this.maxRadius - this.minRadius;
        this.outerStrokeWidth = outerStrokeWidth;
        this.innerStrokeWidth = innerStrokeWidth;
        setLayout();
    }

    private void setLayout() {
        this.outStrokePaint = new Paint();
        this.outStrokePaint.setAntiAlias(true);
        this.outStrokePaint.setStyle(Paint.Style.STROKE);
        this.outStrokePaint.setStrokeWidth(this.outerStrokeWidth);
        this.outStrokeColor = "#ffffff";
        this.outStrokeAlpha = 170;
        this.innerStrokePaint = new Paint();
        this.innerStrokePaint.setAntiAlias(true);
        this.innerStrokePaint.setStyle(Paint.Style.STROKE);
        this.innerStrokePaint.setStrokeWidth(this.innerStrokeWidth);
        this.innerStrokeColor = "#ffffff";
        this.innerStrokeAlpha = 255;
        this.fillStrokePaint = new Paint();
        this.fillStrokePaint.setAntiAlias(true);
        this.fillStrokePaint.setStyle(Paint.Style.STROKE);
        this.fillStrokeColor = "#ffffff";
        this.fillStrokeAlpha = 85;
        setAllPaintColor();
    }

    public void strokeAnimationUpdate(float value) {
        this.strokeAnimationValue = value;
        invalidate();
    }

    public void dragAnimationUpdate(float value) {
        this.fillAnimationValue = value;
        invalidate();
    }

    public void setIsForShortcut(boolean value) {
        this.isForShortcut = value;
    }

    public void showSwipeCircleEffect(boolean value) {
        this.isShowSwipeCircle = value;
    }

    public void setOuterCircleType(boolean isStroke) {
        if (isStroke) {
            this.outStrokeAlpha = 170;
            this.outStrokePaint.setStyle(Paint.Style.STROKE);
        } else {
            this.outStrokeAlpha = 90;
            this.outStrokePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        setAllPaintColor();
    }

    public void setIsWhiteBg(boolean value) {
        if (value) {
            this.outStrokeColor = "#444444";
            this.innerStrokeColor = "#444444";
            this.fillStrokeColor = "#444444";
        } else {
            this.outStrokeColor = "#FFFFFF";
            this.innerStrokeColor = "#FFFFFF";
            this.fillStrokeColor = "#FFFFFF";
        }
        setAllPaintColor();
    }

    private void setAllPaintColor() {
        this.outStrokePaint.setColor(Color.parseColor(this.outStrokeColor));
        this.outStrokePaint.setAlpha(this.outStrokeAlpha);
        this.innerStrokePaint.setColor(Color.parseColor(this.innerStrokeColor));
        this.innerStrokePaint.setAlpha(this.innerStrokeAlpha);
        this.fillStrokePaint.setColor(Color.parseColor(this.fillStrokeColor));
        this.fillStrokePaint.setAlpha(this.fillStrokeAlpha);
    }

    public void setCircleMinWidth(int value) {
        this.minRadius = value / 2;
        this.betweenRadius = this.maxRadius - this.minRadius;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = (this.minRadius + (this.betweenRadius * this.strokeAnimationValue)) - (this.outerStrokeWidth / 2.0f);
        canvas.drawCircle(this.centerX, this.centerY, radius, this.outStrokePaint);
        if (!this.isForShortcut) {
            canvas.drawCircle(this.centerX, this.centerY, this.minRadius, this.innerStrokePaint);
        }
        if (this.fillAnimationValue > 0.0f && this.isShowSwipeCircle) {
            float tvalue = Math.min(this.fillAnimationValue, this.strokeAnimationValue);
            this.fillStrokePaint.setStrokeWidth(this.betweenRadius * tvalue);
            float fillRadius = this.minRadius + ((this.betweenRadius * tvalue) / 2.0f);
            canvas.drawCircle(this.centerX, this.centerY, fillRadius, this.fillStrokePaint);
        }
    }
}