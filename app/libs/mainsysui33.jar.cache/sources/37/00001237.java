package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.android.systemui.R$color;
import com.android.systemui.R$drawable;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsSurfaceView.class */
public class UdfpsSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public boolean mAwaitingSurfaceToStartIllumination;
    public GhbmIlluminationListener mGhbmIlluminationListener;
    public boolean mHasValidSurface;
    public final SurfaceHolder mHolder;
    public Runnable mOnDisplayConfigured;
    public final Paint mSensorPaint;
    public Drawable mUdfpsIconPressed;

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsSurfaceView$GhbmIlluminationListener.class */
    public interface GhbmIlluminationListener {
        void enableGhbm(Surface surface, Runnable runnable);
    }

    public UdfpsSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setZOrderOnTop(true);
        SurfaceHolder holder = getHolder();
        this.mHolder = holder;
        holder.addCallback(this);
        holder.setFormat(1);
        Paint paint = new Paint(0);
        this.mSensorPaint = paint;
        paint.setAntiAlias(true);
        paint.setColor(context.getColor(R$color.config_udfpsColor));
        paint.setStyle(Paint.Style.FILL);
        this.mUdfpsIconPressed = context.getDrawable(R$drawable.udfps_icon_pressed);
    }

    public final void doIlluminate(Runnable runnable) {
        GhbmIlluminationListener ghbmIlluminationListener = this.mGhbmIlluminationListener;
        if (ghbmIlluminationListener == null) {
            Log.e("UdfpsSurfaceView", "doIlluminate | mGhbmIlluminationListener is null");
        } else {
            ghbmIlluminationListener.enableGhbm(this.mHolder.getSurface(), runnable);
        }
    }

    public void drawIlluminationDot(RectF rectF) {
        if (!this.mHasValidSurface) {
            Log.e("UdfpsSurfaceView", "drawIlluminationDot | the surface is destroyed or was never created.");
            return;
        }
        Canvas canvas = null;
        try {
            Canvas lockCanvas = this.mHolder.lockCanvas();
            this.mUdfpsIconPressed.setBounds(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
            this.mUdfpsIconPressed.draw(lockCanvas);
            canvas = lockCanvas;
            lockCanvas.drawOval(rectF, this.mSensorPaint);
            this.mHolder.unlockCanvasAndPost(lockCanvas);
        } catch (Throwable th) {
            if (canvas != null) {
                this.mHolder.unlockCanvasAndPost(canvas);
            }
            throw th;
        }
    }

    public void setGhbmIlluminationListener(GhbmIlluminationListener ghbmIlluminationListener) {
        this.mGhbmIlluminationListener = ghbmIlluminationListener;
    }

    public void startGhbmIllumination(Runnable runnable) {
        if (this.mGhbmIlluminationListener == null) {
            Log.e("UdfpsSurfaceView", "startIllumination | mGhbmIlluminationListener is null");
        } else if (this.mHasValidSurface) {
            doIlluminate(runnable);
        } else {
            this.mAwaitingSurfaceToStartIllumination = true;
            this.mOnDisplayConfigured = runnable;
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mHasValidSurface = true;
        if (this.mAwaitingSurfaceToStartIllumination) {
            doIlluminate(this.mOnDisplayConfigured);
            this.mOnDisplayConfigured = null;
            this.mAwaitingSurfaceToStartIllumination = false;
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.mHasValidSurface = false;
    }
}