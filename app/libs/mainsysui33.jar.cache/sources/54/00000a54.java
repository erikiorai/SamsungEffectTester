package com.airbnb.lottie.animation.content;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/content/TrimPathContent.class */
public class TrimPathContent implements Content, BaseKeyframeAnimation.AnimationListener {
    public final BaseKeyframeAnimation<?, Float> endAnimation;
    public final boolean hidden;
    public final List<BaseKeyframeAnimation.AnimationListener> listeners = new ArrayList();
    public final String name;
    public final BaseKeyframeAnimation<?, Float> offsetAnimation;
    public final BaseKeyframeAnimation<?, Float> startAnimation;
    public final ShapeTrimPath.Type type;

    public TrimPathContent(BaseLayer baseLayer, ShapeTrimPath shapeTrimPath) {
        this.name = shapeTrimPath.getName();
        this.hidden = shapeTrimPath.isHidden();
        this.type = shapeTrimPath.getType();
        BaseKeyframeAnimation<Float, Float> createAnimation = shapeTrimPath.getStart().createAnimation();
        this.startAnimation = createAnimation;
        BaseKeyframeAnimation<Float, Float> createAnimation2 = shapeTrimPath.getEnd().createAnimation();
        this.endAnimation = createAnimation2;
        BaseKeyframeAnimation<Float, Float> createAnimation3 = shapeTrimPath.getOffset().createAnimation();
        this.offsetAnimation = createAnimation3;
        baseLayer.addAnimation(createAnimation);
        baseLayer.addAnimation(createAnimation2);
        baseLayer.addAnimation(createAnimation3);
        createAnimation.addUpdateListener(this);
        createAnimation2.addUpdateListener(this);
        createAnimation3.addUpdateListener(this);
    }

    public void addListener(BaseKeyframeAnimation.AnimationListener animationListener) {
        this.listeners.add(animationListener);
    }

    public BaseKeyframeAnimation<?, Float> getEnd() {
        return this.endAnimation;
    }

    public BaseKeyframeAnimation<?, Float> getOffset() {
        return this.offsetAnimation;
    }

    public BaseKeyframeAnimation<?, Float> getStart() {
        return this.startAnimation;
    }

    public ShapeTrimPath.Type getType() {
        return this.type;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).onValueChanged();
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List<Content> list, List<Content> list2) {
    }
}