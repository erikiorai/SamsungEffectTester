package com.android.systemui.opensesame.lockscreen.effect.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.aj.effect.Utils;

/* loaded from: classes.dex */
public class EffectViewPoppingColor extends EffectViewParticle {
    public EffectViewPoppingColor(Context context) {
        super(context);
    }

    @Override // com.android.systemui.opensesame.lockscreen.effect.particle.EffectViewParticle
    protected ParticleBase createParticle(int color, float x, float y) {
        return new Particle(this, color, x, y);
    }

    /* loaded from: classes.dex */
    private static class Particle extends ParticleBase {
        private static final int COLOR_BRIGHTNESS_ADJUST_RANGE = 40;
        private static final float MAX_CIRCLE_RADIUS = 40.0f;
        private static final float MIN_CIRCLE_RADIUS = 10.0f;
        private static final float MOVEMENT_RANGE_X = 3.0f;
        private static final float MOVEMENT_RANGE_Y_MAX = 10.0f;
        private static final float MOVEMENT_RANGE_Y_MIN = 5.0f;
        private float mCircleRadius;
        private Paint mColorCirclePaint;
        private float mMovementX;
        private float mMovementY;

        public Particle(EffectViewParticle parent, int color, float x, float y) {
            super(parent, color, x, y);
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        protected void init() {
            mColor = Utils.getPoppingHue(mColor, sRand);
            this.mColorCirclePaint = new Paint();
            this.mColorCirclePaint.setColor(this.mColor);
            this.mMovementX = sRand.nextFloat();
            this.mMovementY = sRand.nextFloat();
            this.mCircleRadius = sRand.nextFloat();
            this.mMovementX *= MOVEMENT_RANGE_X;
            this.mMovementX -= 1.5f;
            this.mMovementY *= 5.0f;
            this.mMovementY += 5.0f;
            this.mCircleRadius *= 30.0f;
            this.mCircleRadius += 10.0f;
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        public void calculatePosition(float step) {
            this.mX += this.mMovementX * step;
            this.mY -= this.mMovementY * step;
            if (this.mY < 0.0f) {
                this.mParent.addParticleToRemove(this);
            }
        }

        @Override // com.android.systemui.opensesame.lockscreen.effect.particle.ParticleBase
        public void draw(Canvas canvas) {
            canvas.drawCircle(this.mX, this.mY, this.mCircleRadius, this.mColorCirclePaint);
        }
    }
}