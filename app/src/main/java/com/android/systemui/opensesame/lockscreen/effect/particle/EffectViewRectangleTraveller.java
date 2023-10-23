package com.android.systemui.opensesame.lockscreen.effect.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.aj.effect.Utils;

/* loaded from: classes.dex */
public class EffectViewRectangleTraveller extends EffectViewParticle {
    public EffectViewRectangleTraveller(Context context) {
        super(context);
    }

    @Override // com.android.systemui.opensesame.lockscreen.effect.particle.EffectViewParticle
    protected ParticleBase createParticle(int color, float x, float y) {
        return new Particle(this, color, x, y);
    }

    /* loaded from: classes.dex */
    private static class Particle extends ParticleBase {
        private static final int COLOR_BRIGHTNESS_ADJUST_RANGE = 40;
        private static final float MAX_RECTANGLE_SIZE = 40.0f;
        private static final float MAX_ROTATION_RANGE = 10.0f;
        private static final float MIN_RECTANGLE_SIZE = 10.0f;
        private static final float MIN_ROTATION_RANGE = 2.0f;
        private static final float MOVEMENT_RANGE_X = 10.0f;
        private static final float MOVEMENT_RANGE_Y = 10.0f;
        private Paint mColorPaint;
        private float mCurDegree;
        private float mMovementX;
        private float mMovementY;
        private float mRotation;
        private float mSize;

        public Particle(EffectViewParticle parent, int color, float x, float y) {
            super(parent, color, x, y);
            this.mCurDegree = 0.0f;
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        protected void init() {
            this.mColor = Utils.getPoppingHue(mColor, sRand);
            this.mMovementX = sRand.nextFloat();
            this.mMovementY = sRand.nextFloat();
            this.mSize = sRand.nextFloat();
            this.mRotation = sRand.nextFloat();
            this.mMovementX *= MOVEMENT_RANGE_X;
            this.mMovementX -= 5.0f;
            this.mMovementY *= MOVEMENT_RANGE_Y;
            this.mMovementY -= 5.0f;
            this.mRotation *= 8.0f;
            this.mRotation -= 4.0f;
            if (this.mRotation < 0.0f) {
                this.mRotation -= MIN_ROTATION_RANGE;
            } else {
                this.mRotation += MIN_ROTATION_RANGE;
            }
            this.mSize *= 30.0f;
            this.mSize += 10.0f;
            this.mColorPaint = new Paint();
            this.mColorPaint.setColor(this.mColor);
            this.mColorPaint.setStrokeWidth((this.mSize / 4.0f) + 1.0f);
            this.mColorPaint.setStyle(Paint.Style.STROKE);
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        public void calculatePosition(float step) {
            this.mX += this.mMovementX * step;
            this.mY += this.mMovementY * step;
            this.mCurDegree += this.mRotation * step;
            if (this.mY < 0.0f || this.mY > this.mParent.getScreenHeight() || this.mX < 0.0f || this.mX > this.mParent.getScreenWidth()) {
                this.mParent.addParticleToRemove(this);
            }
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate(this.mCurDegree, this.mX, this.mY);
            canvas.drawRect(this.mX - (this.mSize / MIN_ROTATION_RANGE), this.mY - (this.mSize / MIN_ROTATION_RANGE), (this.mSize / MIN_ROTATION_RANGE) + this.mX, (this.mSize / MIN_ROTATION_RANGE) + this.mY, this.mColorPaint);
            canvas.restore();
        }
    }
}