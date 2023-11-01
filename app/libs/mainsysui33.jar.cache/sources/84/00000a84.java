package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.value.Keyframe;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/animatable/BaseAnimatableValue.class */
public abstract class BaseAnimatableValue<V, O> implements AnimatableValue<V, O> {
    public final List<Keyframe<V>> keyframes;

    public BaseAnimatableValue(V v) {
        this(Collections.singletonList(new Keyframe(v)));
    }

    public BaseAnimatableValue(List<Keyframe<V>> list) {
        this.keyframes = list;
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public List<Keyframe<V>> getKeyframes() {
        return this.keyframes;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
        if (r3.keyframes.get(0).isStatic() != false) goto L10;
     */
    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isStatic() {
        boolean z;
        if (!this.keyframes.isEmpty()) {
            z = false;
            if (this.keyframes.size() == 1) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.keyframes.isEmpty()) {
            sb.append("values=");
            sb.append(Arrays.toString(this.keyframes.toArray()));
        }
        return sb.toString();
    }
}