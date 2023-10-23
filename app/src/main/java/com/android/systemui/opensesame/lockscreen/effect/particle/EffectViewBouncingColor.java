package com.android.systemui.opensesame.lockscreen.effect.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.aj.effect.Utils;

/* loaded from: classes.dex */
public class EffectViewBouncingColor extends EffectViewParticle {
    public EffectViewBouncingColor(Context context) {
        super(context);
    }

    @Override // com.android.systemui.opensesame.lockscreen.effect.particle.EffectViewParticle
    protected ParticleBase createParticle(int color, float x, float y) {
        return new Particle(this, color, x, y);
    }

    /* loaded from: classes.dex */
    private static class Particle extends ParticleBase {
        private static final float ACCELERATION_Y_MAX = 20.0f;
        private static final float ACCELERATION_Y_MIN = 5.0f;
        private static final int COLOR_BRIGHTNESS_ADJUST_RANGE = 40;
        private static final float DECELERATION_X_MAX = 0.25f;
        private static final float DECELERATION_X_MIN = 0.1f;
        private static final float DECELERATION_Y = 1.0f;
        private static final float MAX_CIRCLE_RADIUS = 40.0f;
        private static final float MIN_CIRCLE_RADIUS = 10.0f;
        private static final float MOVEMENT_RANGE_X_MAX = 10.0f;
        private static final float MOVEMENT_RANGE_X_MIN = 3.0f;
        private float mAccelerationY;
        private float mCircleRadius;
        private Paint mColorCirclePaint;
        private float mDecelerationX;
        private float mMovementX;

        public Particle(EffectViewParticle parent, int color, float x, float y) {
            super(parent, color, x, y);
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        protected void init() {
            mColor = Utils.getPoppingHue(mColor, sRand);
            this.mColorCirclePaint = new Paint();
            this.mColorCirclePaint.setColor(this.mColor);
            this.mMovementX = sRand.nextFloat();
            this.mAccelerationY = sRand.nextFloat();
            this.mCircleRadius = sRand.nextFloat();
            this.mMovementX *= 7.0f;
            this.mMovementX -= 3.5f;
            if (this.mMovementX < 0.0f) {
                this.mMovementX -= MOVEMENT_RANGE_X_MIN;
            } else {
                this.mMovementX += MOVEMENT_RANGE_X_MIN;
            }
            this.mAccelerationY *= 15.0f;
            this.mAccelerationY += 5.0f;
            this.mCircleRadius *= 30.0f;
            this.mCircleRadius += 10.0f;
            int selection = sRand.nextInt(3);
            if (selection == 0) {
                this.mDecelerationX = sRand.nextFloat();
                this.mDecelerationX *= 0.15f;
                this.mDecelerationX += DECELERATION_X_MIN;
                if (this.mMovementX > 0.0f) {
                    this.mDecelerationX *= -1.0f;
                    return;
                }
                return;
            }
            this.mDecelerationX = 0.0f;
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        public void calculatePosition(float step) {
            this.mX += this.mMovementX * step;
            this.mMovementX += this.mDecelerationX;
            this.mY -= this.mAccelerationY * step;
            this.mAccelerationY -= 1.0f;
            if (this.mY + this.mCircleRadius >= this.mParent.getScreenHeight()) {
                this.mAccelerationY = sRand.nextFloat();
                this.mAccelerationY *= 19.0f;
                this.mAccelerationY += 5.0f;
                this.mY = this.mParent.getScreenHeight() - this.mCircleRadius;
            }
            if (this.mX < 0.0f || this.mParent.getScreenWidth() < this.mX) {
                this.mParent.addParticleToRemove(this);
            }
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        public void draw(Canvas canvas) {
            canvas.drawCircle(this.mX, this.mY, this.mCircleRadius, this.mColorCirclePaint);
        }
    }
}