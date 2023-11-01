package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.RectangleShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/content/RectangleContent.class */
public class RectangleContent implements BaseKeyframeAnimation.AnimationListener, KeyPathElementContent, PathContent {
    public final BaseKeyframeAnimation<?, Float> cornerRadiusAnimation;
    public final boolean hidden;
    public boolean isPathValid;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final BaseKeyframeAnimation<?, PointF> positionAnimation;
    public final BaseKeyframeAnimation<?, PointF> sizeAnimation;
    public final Path path = new Path();
    public final RectF rect = new RectF();
    public CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    public RectangleContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, RectangleShape rectangleShape) {
        this.name = rectangleShape.getName();
        this.hidden = rectangleShape.isHidden();
        this.lottieDrawable = lottieDrawable;
        BaseKeyframeAnimation<PointF, PointF> createAnimation = rectangleShape.getPosition().createAnimation();
        this.positionAnimation = createAnimation;
        BaseKeyframeAnimation<PointF, PointF> createAnimation2 = rectangleShape.getSize().createAnimation();
        this.sizeAnimation = createAnimation2;
        BaseKeyframeAnimation<Float, Float> createAnimation3 = rectangleShape.getCornerRadius().createAnimation();
        this.cornerRadiusAnimation = createAnimation3;
        baseLayer.addAnimation(createAnimation);
        baseLayer.addAnimation(createAnimation2);
        baseLayer.addAnimation(createAnimation3);
        createAnimation.addUpdateListener(this);
        createAnimation2.addUpdateListener(this);
        createAnimation3.addUpdateListener(this);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        if (t == LottieProperty.RECTANGLE_SIZE) {
            this.sizeAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.CORNER_RADIUS) {
            this.cornerRadiusAnimation.setValueCallback(lottieValueCallback);
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        PointF value = this.sizeAnimation.getValue();
        float f = value.x / 2.0f;
        float f2 = value.y / 2.0f;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.cornerRadiusAnimation;
        float floatValue = baseKeyframeAnimation == null ? 0.0f : ((FloatKeyframeAnimation) baseKeyframeAnimation).getFloatValue();
        float min = Math.min(f, f2);
        float f3 = floatValue;
        if (floatValue > min) {
            f3 = min;
        }
        PointF value2 = this.positionAnimation.getValue();
        this.path.moveTo(value2.x + f, (value2.y - f2) + f3);
        this.path.lineTo(value2.x + f, (value2.y + f2) - f3);
        int i = (f3 > ActionBarShadowController.ELEVATION_LOW ? 1 : (f3 == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
        if (i > 0) {
            RectF rectF = this.rect;
            float f4 = value2.x;
            float f5 = f3 * 2.0f;
            float f6 = value2.y;
            rectF.set((f4 + f) - f5, (f6 + f2) - f5, f4 + f, f6 + f2);
            this.path.arcTo(this.rect, ActionBarShadowController.ELEVATION_LOW, 90.0f, false);
        }
        this.path.lineTo((value2.x - f) + f3, value2.y + f2);
        if (i > 0) {
            RectF rectF2 = this.rect;
            float f7 = value2.x;
            float f8 = value2.y;
            float f9 = f3 * 2.0f;
            rectF2.set(f7 - f, (f8 + f2) - f9, (f7 - f) + f9, f8 + f2);
            this.path.arcTo(this.rect, 90.0f, 90.0f, false);
        }
        this.path.lineTo(value2.x - f, (value2.y - f2) + f3);
        if (i > 0) {
            RectF rectF3 = this.rect;
            float f10 = value2.x;
            float f11 = value2.y;
            float f12 = f3 * 2.0f;
            rectF3.set(f10 - f, f11 - f2, (f10 - f) + f12, (f11 - f2) + f12);
            this.path.arcTo(this.rect, 180.0f, 90.0f, false);
        }
        this.path.lineTo((value2.x + f) - f3, value2.y - f2);
        if (i > 0) {
            RectF rectF4 = this.rect;
            float f13 = value2.x;
            float f14 = f3 * 2.0f;
            float f15 = value2.y;
            rectF4.set((f13 + f) - f14, f15 - f2, f13 + f, (f15 - f2) + f14);
            this.path.arcTo(this.rect, 270.0f, 90.0f, false);
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    public final void invalidate() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        invalidate();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List<Content> list, List<Content> list2) {
        for (int i = 0; i < list.size(); i++) {
            Content content = list.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.trimPaths.addTrimPath(trimPathContent);
                    trimPathContent.addListener(this);
                }
            }
        }
    }
}