package com.android.systemui.opensesame.lockscreen.effect.particle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.aj.effect.Utils;
import com.android.keyguard.sec.KeyguardEffectViewBase;
import com.android.keyguard.sec.KeyguardEffectViewUtil;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class EffectViewParticle extends FrameLayout implements KeyguardEffectViewBase {
    protected static final long FRAME_RATE = 10;
    protected static final int INITIAL_PARTICLE_CNT = 5;
    protected static final float PARTICLE_INTERPOLATION_CNT = 20.0f;
    private final float PARTICLE_INTERPOLATION_X;
    private final float PARTICLE_INTERPOLATION_Y;
    private final float PARTICLE_MIN_CREATE_DISTANCE;
    protected final float SCREEN_HEIGHT;
    protected final float SCREEN_WIDTH;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private float mOldTouchX = -1.0f;
    private float mOldTouchY = -1.0f;
    protected ArrayList<ParticleBase> mParticles = new ArrayList<>();
    protected ArrayList<ParticleBase> mParticlesToRemove = new ArrayList<>();
    protected boolean mPaused;
    private long mPreviousFrameTime;
    private float mRatioX;
    private float mRatioY;
    protected Bitmap mWallpaper;

    public EffectViewParticle(Context context) {
        super(context);
        //this.mPaused = true;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Rect rect = Utils.getViewRect(new DisplayMetrics(), wm);
        SCREEN_WIDTH = rect.width();
        SCREEN_HEIGHT = rect.height();
        this.PARTICLE_INTERPOLATION_X = this.SCREEN_WIDTH / PARTICLE_INTERPOLATION_CNT;
        this.PARTICLE_INTERPOLATION_Y = this.SCREEN_HEIGHT / PARTICLE_INTERPOLATION_CNT;
        this.PARTICLE_MIN_CREATE_DISTANCE = this.PARTICLE_INTERPOLATION_X / 2.0f;
    }

    private void checkParticles() {
        for (ParticleBase particleBase : this.mParticlesToRemove) {
            this.mParticles.remove(particleBase);
        }
        this.mParticlesToRemove.clear();
        if (this.mParticles.size() > 0) {
            this.mHandler.postDelayed(this::invalidate, FRAME_RATE);
        } else {
            cleanUp();
        }
    }

    public void addParticleToRemove(ParticleBase particle) {
        this.mParticlesToRemove.add(particle);
    }

    protected ParticleBase createParticle(int color, float x, float y) {
        return null;
    }

    public float getScreenWidth() {
        return this.SCREEN_WIDTH;
    }

    public float getScreenHeight() {
        return this.SCREEN_HEIGHT;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mParticles.size() != 0) {
            long curTime = System.currentTimeMillis();
            float frameStep = ((float) (curTime - this.mPreviousFrameTime)) / 10.0f;
            this.mPreviousFrameTime = curTime;
            for (ParticleBase particle : this.mParticles) {
                if (!this.mPaused) {
                    if (!unlocking || !Utils.customActions) {
                        particle.calculatePosition(frameStep);
                    } else particle.calculatePosition(frameStep * 5);
                }
                particle.draw(canvas);
            }
            if (!this.mPaused) {
                checkParticles();
            }
        }
    }

    @Override // com.android.systemui.opensesame.lockscreen.effect.EffectViewBase
    public void update() {
        BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(getContext());
        if (newBitmapDrawable == null) {
            return;
        }
        Bitmap originBitmap = newBitmapDrawable.getBitmap();
        if (originBitmap != null) {
            this.mRatioX = originBitmap.getWidth() / this.SCREEN_WIDTH;
            this.mRatioY = originBitmap.getHeight() / this.SCREEN_HEIGHT;
            this.mWallpaper = originBitmap;
            setBackground(new BitmapDrawable(getResources(), originBitmap));
        }
    }

    @Override
    public void setContextualWallpaper(Bitmap bmp) {
        if (bmp != null) {
            this.mRatioX = bmp.getWidth() / this.SCREEN_WIDTH;
            this.mRatioY = bmp.getHeight() / this.SCREEN_HEIGHT;
            this.mWallpaper = bmp;
            setBackground(new BitmapDrawable(getResources(), bmp));
        }
    }

    @Override
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (this.mPaused) {
            return false;
        }
        if (this.mWallpaper != null) {
            float curX = event.getX();
            float curY = event.getY();
            if (event.getAction() == 0) {
                this.mOldTouchX = curX;
                this.mOldTouchY = curY;
            }
            float distanceX = this.mOldTouchX - curX;
            float distanceY = this.mOldTouchY - curY;
            float interpolationStepCntX = Math.abs(distanceX) / this.PARTICLE_INTERPOLATION_X;
            float interpolationStepCntY = Math.abs(distanceY) / this.PARTICLE_INTERPOLATION_Y;
            int interpolationStepCnt = (int) interpolationStepCntX;
            if (interpolationStepCntX < interpolationStepCntY) {
                interpolationStepCnt = (int) interpolationStepCntY;
            }
            boolean needToSkipAddParticle = false;
            if (event.getAction() == 0) {
                interpolationStepCnt = INITIAL_PARTICLE_CNT;
            } else if (interpolationStepCnt == 0) {
                if (Math.abs(distanceX) < this.PARTICLE_MIN_CREATE_DISTANCE && Math.abs(distanceY) < this.PARTICLE_MIN_CREATE_DISTANCE) {
                    needToSkipAddParticle = true;
                } else {
                    interpolationStepCnt = 1;
                }
            }
            float interpolationDistanceX = distanceX / interpolationStepCnt;
            float interpolationDistanceY = distanceY / interpolationStepCnt;
            boolean needToStartAnimation = this.mParticles.size() == 0;
            for (int step = 1; step <= interpolationStepCnt; step++) {
                float stepX = this.mOldTouchX + (step * interpolationDistanceX);
                float stepY = this.mOldTouchY + (step * interpolationDistanceY);
                float adjustedX = stepX * this.mRatioX;
                float adjustedY = stepY * this.mRatioY;
                if (adjustedX >= 0.0f && this.mWallpaper.getWidth() > adjustedX && adjustedY >= 0.0f && this.mWallpaper.getHeight() > adjustedY) {
                    int color = this.mWallpaper.getPixel((int) adjustedX, (int) adjustedY);
                    this.mParticles.add(createParticle(color, stepX, stepY));
                }
            }
            if (needToStartAnimation) {
                this.mPreviousFrameTime = System.currentTimeMillis();
                invalidate();
            }
            if (!needToSkipAddParticle) {
                this.mOldTouchX = curX;
                this.mOldTouchY = curY;
            }
        }
        return true;
    }

    @Override
    public boolean handleTouchEventForPatternLock(View view, MotionEvent motionEvent) {
        return false;
    }

    private static boolean unlocking = false;
    @Override
    public void handleUnlock(View view, MotionEvent motionEvent) {
        unlocking = true;
    }

    @Override
    public void playLockSound() {

    }

    @Override // com.android.systemui.opensesame.lockscreen.effect.EffectViewBase
    public long getUnlockDelay() {
        return 300L;
    }

    @Override
    public boolean handleHoverEvent(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.systemui.opensesame.lockscreen.effect.EffectViewBase
    public void reset() {
        unlocking = false;
        cleanUp();
    }

    @Override
    public void screenTurnedOff() {

    }

    @Override
    public void screenTurnedOn() {

    }

    @Override
    public void setHidden(boolean hidden) {

    }

    @Override
    public void show() {
        unlocking = false;
        cleanUp();
    }

    @Override
    public void showUnlockAffordance(long j, Rect rect) {
        mHandler.postDelayed(() -> {
            if (Utils.customActions) {
                if (this.mWallpaper != null) {
                    float curX = rect.centerX();
                    float curY = rect.centerY();
                    this.mOldTouchX = curX;
                    this.mOldTouchY = curY;
                    boolean needToStartAnimation = this.mParticles.size() == 0;
                    for (int step = 1; step <= INITIAL_PARTICLE_CNT * 4; step++) {
                        float stepX = this.mOldTouchX;
                        float stepY = this.mOldTouchY;
                        float adjustedX = stepX * this.mRatioX;
                        float adjustedY = stepY * this.mRatioY;
                        if (adjustedX >= 0.0f && this.mWallpaper.getWidth() > adjustedX && adjustedY >= 0.0f && this.mWallpaper.getHeight() > adjustedY) {
                            int color = this.mWallpaper.getPixel((int) adjustedX, (int) adjustedY);
                            this.mParticles.add(createParticle(color, stepX, stepY));
                        }
                    }
                    if (needToStartAnimation) {
                        this.mPreviousFrameTime = System.currentTimeMillis();
                        invalidate();
                    }
                }
            }
        }, j);
    }


    @Override // com.android.systemui.opensesame.lockscreen.effect.EffectViewBase
    public void cleanUp() {
        this.mParticles.clear();
        unlocking = false;
        invalidate();
    }

    public void onResume() {
        this.mPaused = false;
    }

    public void onPause() {
        this.mPaused = true;
    }
}