package com.android.systemui.opensesame.lockscreen.effect.particle;

import android.graphics.Canvas;
import java.util.Random;

/* loaded from: classes.dex */
public class ParticleBase {
    protected static Random sRand;
    protected int mColor;
    protected EffectViewParticle mParent;
    protected float mX;
    protected float mY;

    public ParticleBase(EffectViewParticle parent, int color, float x, float y) {
        this.mParent = parent;
        this.mColor = color;
        this.mX = x;
        this.mY = y;
        if (sRand == null) {
            sRand = new Random(System.currentTimeMillis());
        }
        init();
    }

    protected void init() {
    }

    public void calculatePosition() {
        calculatePosition(1.0f);
    }

    public void calculatePosition(float step) {
    }

    public void draw(Canvas canvas) {
    }
}