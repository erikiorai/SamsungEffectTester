package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.internal.util.LatencyTracker;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.navigationbar.gestural.BackPanel;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.navigationbar.gestural.EdgePanelParams;
import kotlin.Unit;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanel.class */
public final class BackPanel extends View {
    public final SpringAnimation alphaAnimation;
    public Paint arrowBackgroundPaint;
    public RectF arrowBackgroundRect;
    public AnimatedFloat arrowHeight;
    public AnimatedFloat arrowLength;
    public final Paint arrowPaint;
    public final Path arrowPath;
    public boolean arrowsPointLeft;
    public final AnimatedFloat backgroundEdgeCornerRadius;
    public final AnimatedFloat backgroundFarCornerRadius;
    public final AnimatedFloat backgroundHeight;
    public final AnimatedFloat backgroundWidth;
    public final FloatPropertyCompat<BackPanel> currentAlpha;
    public AnimatedFloat horizontalTranslation;
    public boolean isLeftPanel;
    public final LatencyTracker latencyTracker;
    public boolean trackingBackArrowLatency;
    public AnimatedFloat verticalTranslation;

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanel$AnimatedFloat.class */
    public final class AnimatedFloat {
        public final SpringAnimation animation;
        public float pos;
        public float restingPosition;

        public AnimatedFloat(String str, SpringForce springForce) {
            SpringAnimation springAnimation = new SpringAnimation(this, new FloatPropertyCompat<AnimatedFloat>(str) { // from class: com.android.systemui.navigationbar.gestural.BackPanel$AnimatedFloat$floatProp$1
                /* JADX DEBUG: Method merged with bridge method */
                @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
                public float getValue(BackPanel.AnimatedFloat animatedFloat) {
                    return animatedFloat.getPos();
                }

                /* JADX DEBUG: Method merged with bridge method */
                @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
                public void setValue(BackPanel.AnimatedFloat animatedFloat, float f) {
                    animatedFloat.setPos(f);
                }
            });
            this.animation = springAnimation;
            springAnimation.setSpring(springForce);
        }

        public final SpringAnimation getAnimation() {
            return this.animation;
        }

        public final float getPos() {
            return this.pos;
        }

        public final void setPos(float f) {
            if (this.pos == f) {
                return;
            }
            this.pos = f;
            BackPanel.this.invalidate();
        }

        public final void snapTo(float f) {
            this.animation.cancel();
            this.restingPosition = f;
            this.animation.getSpring().setFinalPosition(f);
            setPos(f);
        }

        public final void stretchBy(float f, float f2) {
            float f3 = this.restingPosition;
            this.animation.animateToFinalPosition(f3 + (f2 * (f - f3)));
        }

        public final void stretchTo(float f) {
            this.animation.animateToFinalPosition(this.restingPosition + f);
        }

        public final void updateRestingPosition(float f, boolean z) {
            this.restingPosition = f;
            if (z) {
                this.animation.animateToFinalPosition(f);
            } else {
                snapTo(f);
            }
        }
    }

    public BackPanel(Context context, LatencyTracker latencyTracker) {
        super(context);
        this.latencyTracker = latencyTracker;
        this.arrowPath = new Path();
        Paint paint = new Paint();
        this.arrowPaint = paint;
        this.arrowBackgroundRect = new RectF();
        this.arrowBackgroundPaint = new Paint();
        this.arrowLength = new AnimatedFloat("arrowLength", new SpringForce());
        this.arrowHeight = new AnimatedFloat("arrowHeight", new SpringForce());
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(600.0f);
        springForce.setDampingRatio(0.65f);
        Unit unit = Unit.INSTANCE;
        this.backgroundWidth = new AnimatedFloat("backgroundWidth", springForce);
        SpringForce springForce2 = new SpringForce();
        springForce2.setStiffness(600.0f);
        springForce2.setDampingRatio(0.65f);
        this.backgroundHeight = new AnimatedFloat("backgroundHeight", springForce2);
        SpringForce springForce3 = new SpringForce();
        springForce3.setStiffness(400.0f);
        springForce3.setDampingRatio(0.5f);
        this.backgroundEdgeCornerRadius = new AnimatedFloat("backgroundEdgeCornerRadius", springForce3);
        SpringForce springForce4 = new SpringForce();
        springForce4.setStiffness(2200.0f);
        springForce4.setDampingRatio(1.0f);
        this.backgroundFarCornerRadius = new AnimatedFloat("backgroundDragCornerRadius", springForce4);
        this.horizontalTranslation = new AnimatedFloat("horizontalTranslation", new SpringForce());
        FloatPropertyCompat<BackPanel> floatPropertyCompat = new FloatPropertyCompat<BackPanel>() { // from class: com.android.systemui.navigationbar.gestural.BackPanel$currentAlpha$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public float getValue(BackPanel backPanel) {
                return backPanel.getAlpha();
            }

            /* JADX DEBUG: Method merged with bridge method */
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public void setValue(BackPanel backPanel, float f) {
                backPanel.setAlpha(f);
            }
        };
        this.currentAlpha = floatPropertyCompat;
        this.alphaAnimation = new SpringAnimation(this, floatPropertyCompat).setSpring(new SpringForce().setStiffness(60.0f).setDampingRatio(1.0f));
        SpringForce springForce5 = new SpringForce();
        springForce5.setStiffness(1500.0f);
        this.verticalTranslation = new AnimatedFloat("verticalTranslation", springForce5);
        setVisibility(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        Paint paint2 = this.arrowBackgroundPaint;
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
    }

    public final boolean addEndListener(BackPanelController.DelayedOnAnimationEndListener delayedOnAnimationEndListener) {
        boolean z = true;
        if (this.alphaAnimation.isRunning()) {
            this.alphaAnimation.addEndListener(delayedOnAnimationEndListener);
        } else if (this.horizontalTranslation.getAnimation().isRunning()) {
            this.horizontalTranslation.getAnimation().addEndListener(delayedOnAnimationEndListener);
        } else {
            delayedOnAnimationEndListener.runNow$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            z = false;
        }
        return z;
    }

    public final void animateVertically(float f) {
        this.verticalTranslation.stretchTo(f);
    }

    public final Path calculateArrowPath(float f, float f2) {
        this.arrowPath.reset();
        float f3 = -f2;
        this.arrowPath.moveTo(f, f3);
        this.arrowPath.lineTo(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
        this.arrowPath.lineTo(f, f2);
        this.arrowPath.moveTo(f, f3);
        return this.arrowPath;
    }

    public final void cancelAlphaAnimations() {
        this.alphaAnimation.cancel();
        setAlpha(1.0f);
    }

    public final void fadeOut() {
        this.alphaAnimation.animateToFinalPosition(ActionBarShadowController.ELEVATION_LOW);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final boolean isLeftPanel() {
        return this.isLeftPanel;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float pos = this.backgroundEdgeCornerRadius.getPos();
        float pos2 = this.backgroundFarCornerRadius.getPos();
        float f = 2;
        float pos3 = this.backgroundHeight.getPos() / f;
        canvas.save();
        if (!this.isLeftPanel) {
            canvas.scale(-1.0f, 1.0f, getWidth() / 2.0f, ActionBarShadowController.ELEVATION_LOW);
        }
        canvas.translate(this.horizontalTranslation.getPos(), (getHeight() * 0.5f) + this.verticalTranslation.getPos());
        RectF rectF = this.arrowBackgroundRect;
        rectF.left = ActionBarShadowController.ELEVATION_LOW;
        rectF.top = -pos3;
        rectF.right = this.backgroundWidth.getPos();
        rectF.bottom = pos3;
        canvas.drawPath(toPathWithRoundCorners(rectF, pos, pos2, pos2, pos), this.arrowBackgroundPaint);
        float pos4 = this.arrowLength.getPos();
        float pos5 = this.arrowHeight.getPos();
        canvas.translate((this.backgroundWidth.getPos() - pos4) / f, ActionBarShadowController.ELEVATION_LOW);
        if (!(this.arrowsPointLeft ^ this.isLeftPanel)) {
            canvas.scale(-1.0f, 1.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
            canvas.translate(-pos4, ActionBarShadowController.ELEVATION_LOW);
        }
        canvas.drawPath(calculateArrowPath(pos4, pos5), this.arrowPaint);
        canvas.restore();
        if (this.trackingBackArrowLatency) {
            this.latencyTracker.onActionEnd(15);
            this.trackingBackArrowLatency = false;
        }
    }

    public final void resetStretch() {
        this.horizontalTranslation.stretchTo(ActionBarShadowController.ELEVATION_LOW);
        this.arrowLength.stretchTo(ActionBarShadowController.ELEVATION_LOW);
        this.arrowHeight.stretchTo(ActionBarShadowController.ELEVATION_LOW);
        this.backgroundWidth.stretchTo(ActionBarShadowController.ELEVATION_LOW);
        this.backgroundHeight.stretchTo(ActionBarShadowController.ELEVATION_LOW);
        this.backgroundEdgeCornerRadius.stretchTo(ActionBarShadowController.ELEVATION_LOW);
        this.backgroundFarCornerRadius.stretchTo(ActionBarShadowController.ELEVATION_LOW);
    }

    public final void setArrowStiffness(float f, float f2) {
        SpringForce spring = this.arrowLength.getAnimation().getSpring();
        spring.setStiffness(f);
        spring.setDampingRatio(f2);
        SpringForce spring2 = this.arrowHeight.getAnimation().getSpring();
        spring2.setStiffness(f);
        spring2.setDampingRatio(f2);
    }

    public final void setArrowsPointLeft(boolean z) {
        if (this.arrowsPointLeft != z) {
            invalidate();
            this.arrowsPointLeft = z;
        }
    }

    public final void setLeftPanel(boolean z) {
        this.isLeftPanel = z;
    }

    public final void setRestingDimens$frameworks__base__packages__SystemUI__android_common__SystemUI_core(EdgePanelParams.BackIndicatorDimens backIndicatorDimens, boolean z) {
        this.horizontalTranslation.updateRestingPosition(backIndicatorDimens.getHorizontalTranslation(), z);
        this.arrowLength.updateRestingPosition(backIndicatorDimens.getArrowDimens().getLength(), z);
        this.arrowHeight.updateRestingPosition(backIndicatorDimens.getArrowDimens().getHeight(), z);
        this.backgroundWidth.updateRestingPosition(backIndicatorDimens.getBackgroundDimens().getWidth(), z);
        this.backgroundHeight.updateRestingPosition(backIndicatorDimens.getBackgroundDimens().getHeight(), z);
        this.backgroundEdgeCornerRadius.updateRestingPosition(backIndicatorDimens.getBackgroundDimens().getEdgeCornerRadius(), z);
        this.backgroundFarCornerRadius.updateRestingPosition(backIndicatorDimens.getBackgroundDimens().getFarCornerRadius(), z);
    }

    public final void setStretch(float f, float f2, float f3, EdgePanelParams.BackIndicatorDimens backIndicatorDimens) {
        this.horizontalTranslation.stretchBy(backIndicatorDimens.getHorizontalTranslation(), f);
        this.arrowLength.stretchBy(backIndicatorDimens.getArrowDimens().getLength(), f2);
        this.arrowHeight.stretchBy(backIndicatorDimens.getArrowDimens().getHeight(), f2);
        this.backgroundWidth.stretchBy(backIndicatorDimens.getBackgroundDimens().getWidth(), f3);
    }

    public final void startTrackingShowBackArrowLatency() {
        this.latencyTracker.onActionStart(15);
        this.trackingBackArrowLatency = true;
    }

    public final Path toPathWithRoundCorners(RectF rectF, float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.addRoundRect(rectF, new float[]{f, f, f2, f2, f3, f3, f4, f4}, Path.Direction.CW);
        return path;
    }

    public final void updateArrowPaint$frameworks__base__packages__SystemUI__android_common__SystemUI_core(float f) {
        this.arrowPaint.setStrokeWidth(f);
        this.arrowPaint.setColor(Utils.getColorAttrDefaultColor(getContext(), 16843827));
        this.arrowBackgroundPaint.setColor(Utils.getColorAccentDefaultColor(getContext()));
    }
}