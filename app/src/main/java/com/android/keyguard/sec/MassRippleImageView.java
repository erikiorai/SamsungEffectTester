package com.android.keyguard.sec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

/* loaded from: classes.dex */
@SuppressLint("AppCompatCustomView")
public class MassRippleImageView extends ImageView {
    private static final String TAG = "MassRippleImageView";
    float INTERVAL_STROKE;
    int IntrinsicHeight;
    int IntrinsicWidth;
    float WORKING_GAP;
    Path mPath;
    float originalStroke;
    RectF oval;
    long prevTime;
    ShapeDrawable rippleCircle;
    float stroke;

    @Override // android.view.View
    public void setPivotX(float pivotX) {
        super.setPivotX(pivotX);
    }

    @Override // android.view.View
    public void setPivotY(float pivotY) {
        super.setPivotY(pivotY);
    }

    @Override // android.view.View
    protected void onAnimationStart() {
        super.onAnimationStart();
        this.stroke = this.originalStroke;
    }

    @Override // android.view.View
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        this.stroke = this.originalStroke;
    }

    @Override // android.view.View
    public final Animation getAnimation() {
        this.stroke -= this.INTERVAL_STROKE;
        if (this.stroke < 1.0f) {
            this.stroke = 1.0f;
        }
        if (!isTooEarly()) {
            this.mPath = new Path();
            this.oval = new RectF(this.stroke, this.stroke, this.IntrinsicWidth - this.stroke, this.IntrinsicHeight - this.stroke);
            this.mPath.addOval(this.oval, Path.Direction.CW);
            this.rippleCircle = null;
            this.rippleCircle = new ShapeDrawable(new PathShape(this.mPath, this.IntrinsicWidth, this.IntrinsicHeight));
            this.rippleCircle.getPaint().setColor(-1);
            this.rippleCircle.getPaint().setStyle(Paint.Style.STROKE);
            this.rippleCircle.getPaint().setStrokeWidth(this.stroke);
            this.rippleCircle.setIntrinsicHeight(this.IntrinsicHeight);
            this.rippleCircle.setIntrinsicWidth(this.IntrinsicWidth);
            setBackground(this.rippleCircle);
        }
        return super.getAnimation();
    }

    public float translatedFromDPToPixel(float dp) {
        new DisplayMetrics();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float dpi = metrics.densityDpi;
        float ret = dp * (dpi / 160.0f);
        Log.i(TAG, "dp = " + dp + ", to Pixel = " + ret);
        return ret;
    }

    public MassRippleImageView(Context context) {
        super(context);
        this.stroke = 26.6f;
        this.originalStroke = 26.6f;
        this.INTERVAL_STROKE = 0.05f;
        this.IntrinsicHeight = 200;
        this.IntrinsicWidth = 200;
        setVisibility(View.INVISIBLE);
        Log.i(TAG, TAG);
    }

    public MassRippleImageView(Context context, float stroke, int width, int height, float duration) {
        this(context);
        Log.i(TAG, TAG);
        this.stroke = translatedFromDPToPixel(stroke);
        this.originalStroke = translatedFromDPToPixel(stroke);
        this.IntrinsicHeight = (int) translatedFromDPToPixel(height);
        this.IntrinsicWidth = (int) translatedFromDPToPixel(width);
        this.INTERVAL_STROKE = (this.originalStroke / duration) * 20.0f;
        this.WORKING_GAP = duration / 20.0f;
        getAnimation();
    }

    private boolean isTooEarly() {
        long now = System.currentTimeMillis();
        if (((float) (now - this.prevTime)) > this.WORKING_GAP) {
            this.prevTime = now;
            return false;
        }
        return true;
    }
}