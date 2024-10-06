package com.samsung.android.visualeffect.lock.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.aj.effect.Utils;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;

import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class ParticleEffect extends View implements IEffectView {
    private final String TAG = "VisualEffectParticleEffect";
    private final int dotMaxLimit = 150;
    private int dotUnlockSpeed = 5;
    private int drawingBottom;
    private int drawingDelayTime = 2; // millis. original is 2 TODO idk why is this here
    private int drawingLeft;
    private int drawingMargin;
    private int drawingRight;
    private int drawingTop;
    private float[] hsvOrigin;
    private float[] hsvTemp;
    private final int initCreatedDotAmount = 250;
    private boolean isDrawing;
    private boolean isPaused;
    private int lastAddedColor;
    private float lastAddedX;
    private float lastAddedY;
    Handler mHandler;
    private int nextParticleIndex;
    private ArrayList<Particle> particleAliveList = new ArrayList<>();
    private ArrayList<Particle> particleTotalList = new ArrayList<>();

    public ParticleEffect(Context context) {
        super(context);
        float fpsRatio = 60 / ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRefreshRate();
        this.lastAddedX = 0.0f;
        this.lastAddedY = 0.0f;
        this.lastAddedColor = 0;
        this.drawingLeft = 0;
        this.drawingTop = 0;
        this.drawingRight = 1;
        this.drawingBottom = 1;
        this.drawingMargin = 11;
        this.nextParticleIndex = -1;
        this.isPaused = false;
        this.drawingDelayTime /= fpsRatio;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.visualeffect.lock.particle.ParticleEffect.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (ParticleEffect.this.isAvailableRect()) {
                    int tL = ParticleEffect.this.drawingLeft - ParticleEffect.this.drawingMargin;
                    int tT = ParticleEffect.this.drawingTop - ParticleEffect.this.drawingMargin;
                    int tR = ParticleEffect.this.drawingRight + ParticleEffect.this.drawingMargin;
                    int tB = ParticleEffect.this.drawingBottom + ParticleEffect.this.drawingMargin;
                    ParticleEffect.this.invalidate(tL, tT, tR, tB);
                } else {
                    ParticleEffect.this.invalidate(0, 0, 1, 1);
                }
                if (ParticleEffect.this.isDrawing && !ParticleEffect.this.isPaused) {
                    ParticleEffect.this.mHandler.sendEmptyMessageDelayed(0, ParticleEffect.this.drawingDelayTime);
                }
            }
        };
        this.hsvOrigin = new float[3];
        this.hsvTemp = new float[3];
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int smallestWidth = Math.min(screenWidth, screenHeight);
        float ratio = Utils.particleRatioLock ? 1.0f : smallestWidth / 1080.0f;
        Log.d(this.TAG, "ParticleEffect : Constructor, " + screenWidth + " x " + screenHeight);
        Log.d(this.TAG, "ParticleEffect : ratio = " + ratio);
        for (int i = 0; i < this.initCreatedDotAmount; i++) {
            Particle dot = new Particle(ratio, fpsRatio);
            this.particleTotalList.add(dot);
        }
    }

    public void addDots(int amount, float x, float y, int color) {
        if (this.particleAliveList.size() + amount <= this.dotMaxLimit) {
            this.lastAddedX = x;
            this.lastAddedY = y;
            this.lastAddedColor = color;
            Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), this.hsvOrigin);
            for (int i = 0; i < amount; i++) {
                this.hsvTemp[0] = this.hsvOrigin[0];
                this.hsvTemp[1] = this.hsvOrigin[1];
                this.hsvTemp[2] = this.hsvOrigin[2];
                float[] fArr = this.hsvTemp;
                fArr[1] = (float) (fArr[1] * (1.0d - (0.7d * Math.random())));
                float[] fArr2 = this.hsvTemp;
                fArr2[2] = (float) (fArr2[2] + ((1.0f - this.hsvTemp[2]) * Math.random()));
                int resultColor = Color.HSVToColor(this.hsvTemp);
                Particle dot = getNextDot();
                dot.initialize(x, y, resultColor);
                this.particleAliveList.add(dot);
            }
            startDrawing();
        }
    }

    private void startDrawing() {
        if (!this.isDrawing) {
            this.isDrawing = true;
            this.mHandler.sendEmptyMessageDelayed(0, this.drawingDelayTime);
        }
    }

    private void stopDrawing() {
        this.isDrawing = false;
    }

    private Particle getNextDot() {
        this.nextParticleIndex = this.nextParticleIndex >= this.initCreatedDotAmount + (-1) ? 0 : this.nextParticleIndex + 1;
        return this.particleTotalList.get(this.nextParticleIndex);
    }

    public void unlockDots() {
        int totalAdded = this.dotMaxLimit - this.particleAliveList.size();
        addDots(totalAdded, this.lastAddedX, this.lastAddedY, this.lastAddedColor);
        for (Particle particle : this.particleAliveList) {
            particle.unlock(this.dotUnlockSpeed);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.particleAliveList.isEmpty()) {
            stopDrawing();
            return;
        }
        int i = 0;
        while (i < this.particleAliveList.size()) {
            Particle dot = this.particleAliveList.get(i);
            if (dot != null && dot.isAlive()) {
                dot.move();
                dot.draw(canvas);
                int left = dot.getLeft();
                int right = dot.getRight();
                int top = dot.getTop();
                int bottom = dot.getBottom();
                if (i != 0) {
                    left = Math.min(this.drawingLeft, left);
                }
                this.drawingLeft = left;
                if (i != 0) {
                    top = Math.min(this.drawingTop, top);
                }
                this.drawingTop = top;
                if (i != 0) {
                    right = Math.max(this.drawingRight, right);
                }
                this.drawingRight = right;
                if (i != 0) {
                    bottom = Math.max(this.drawingBottom, bottom);
                }
                this.drawingBottom = bottom;
            } else {
                this.particleAliveList.remove(i);
                i--;
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAvailableRect() {
        return this.drawingLeft < this.drawingRight && this.drawingTop < this.drawingBottom && this.drawingLeft < getWidth() && this.drawingRight > 0 && this.drawingTop < getHeight() && this.drawingBottom > 0;
    }

    public void clearEffect() {
        stopDrawing();
        invalidate();
        if (!this.particleAliveList.isEmpty()) {
            this.particleAliveList.subList(0, this.particleAliveList.size()).clear();
        }
    }

    public void pauseEffect() {
        this.isPaused = true;
        Log.d(this.TAG, "ParticleEffect : pauseEffect");
    }

    public void resumeEffect() {
        Log.d(this.TAG, "ParticleEffect : resumeEffect");
        if (this.isPaused) {
            this.isPaused = false;
            if (this.isDrawing) {
                this.mHandler.sendEmptyMessageDelayed(0, this.drawingDelayTime);
            } else {
                clearEffect();
            }
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        addDots((Integer) params.get("Amount"), (Float) params.get("X"), (Float) params.get("Y"), (Integer) params.get("Color"));
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        clearEffect();
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
    }

    @Override
    public boolean handleHoverEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void drawPause() {
        pauseEffect();
    }

    @Override
    public void drawResume() {
        resumeEffect();
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
    }
}