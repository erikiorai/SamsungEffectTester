package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.Collections;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/keyframe/ValueCallbackKeyframeAnimation.class */
public class ValueCallbackKeyframeAnimation<K, A> extends BaseKeyframeAnimation<K, A> {
    public final LottieFrameInfo<A> frameInfo;
    public final A valueCallbackValue;

    public ValueCallbackKeyframeAnimation(LottieValueCallback<A> lottieValueCallback) {
        this(lottieValueCallback, null);
    }

    public ValueCallbackKeyframeAnimation(LottieValueCallback<A> lottieValueCallback, A a) {
        super(Collections.emptyList());
        this.frameInfo = new LottieFrameInfo<>();
        setValueCallback(lottieValueCallback);
        this.valueCallbackValue = a;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public float getEndProgress() {
        return 1.0f;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public A getValue() {
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        A a = this.valueCallbackValue;
        return lottieValueCallback.getValueInternal(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, a, a, getProgress(), getProgress(), getProgress());
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public A getValue(Keyframe<K> keyframe, float f) {
        return getValue();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public void notifyListeners() {
        if (this.valueCallback != null) {
            super.notifyListeners();
        }
    }
}