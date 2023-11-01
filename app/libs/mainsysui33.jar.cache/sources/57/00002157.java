package com.android.systemui.qs;

import android.util.FloatProperty;
import android.util.MathUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/TouchAnimator.class */
public class TouchAnimator {
    public static final FloatProperty<TouchAnimator> POSITION = new FloatProperty<TouchAnimator>("position") { // from class: com.android.systemui.qs.TouchAnimator.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.Property
        public Float get(TouchAnimator touchAnimator) {
            return Float.valueOf(touchAnimator.mLastT);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.FloatProperty
        public void setValue(TouchAnimator touchAnimator, float f) {
            touchAnimator.setPosition(f);
        }
    };
    public final float mEndDelay;
    public final Interpolator mInterpolator;
    public final KeyframeSet[] mKeyframeSets;
    public float mLastT;
    public final Listener mListener;
    public final float mSpan;
    public final float mStartDelay;
    public final Object[] mTargets;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/TouchAnimator$Builder.class */
    public static class Builder {
        public float mEndDelay;
        public Interpolator mInterpolator;
        public Listener mListener;
        public float mStartDelay;
        public List<Object> mTargets = new ArrayList();
        public List<KeyframeSet> mValues = new ArrayList();

        public static Property getProperty(Object obj, String str, Class<?> cls) {
            if (obj instanceof View) {
                str.hashCode();
                boolean z = true;
                switch (str.hashCode()) {
                    case -1225497657:
                        if (str.equals("translationX")) {
                            z = false;
                            break;
                        }
                        break;
                    case -1225497656:
                        if (str.equals("translationY")) {
                            z = true;
                            break;
                        }
                        break;
                    case -1225497655:
                        if (str.equals("translationZ")) {
                            z = true;
                            break;
                        }
                        break;
                    case -908189618:
                        if (str.equals("scaleX")) {
                            z = true;
                            break;
                        }
                        break;
                    case -908189617:
                        if (str.equals("scaleY")) {
                            z = true;
                            break;
                        }
                        break;
                    case -40300674:
                        if (str.equals("rotation")) {
                            z = true;
                            break;
                        }
                        break;
                    case 120:
                        if (str.equals("x")) {
                            z = true;
                            break;
                        }
                        break;
                    case 121:
                        if (str.equals("y")) {
                            z = true;
                            break;
                        }
                        break;
                    case 92909918:
                        if (str.equals("alpha")) {
                            z = true;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        return View.TRANSLATION_X;
                    case true:
                        return View.TRANSLATION_Y;
                    case true:
                        return View.TRANSLATION_Z;
                    case true:
                        return View.SCALE_X;
                    case true:
                        return View.SCALE_Y;
                    case true:
                        return View.ROTATION;
                    case true:
                        return View.X;
                    case true:
                        return View.Y;
                    case true:
                        return View.ALPHA;
                }
            }
            return ((obj instanceof TouchAnimator) && "position".equals(str)) ? TouchAnimator.POSITION : Property.of(obj.getClass(), cls, str);
        }

        public final void add(Object obj, KeyframeSet keyframeSet) {
            this.mTargets.add(obj);
            this.mValues.add(keyframeSet);
        }

        public Builder addFloat(Object obj, String str, float... fArr) {
            add(obj, KeyframeSet.ofFloat(getProperty(obj, str, Float.TYPE), fArr));
            return this;
        }

        public TouchAnimator build() {
            List<Object> list = this.mTargets;
            Object[] array = list.toArray(new Object[list.size()]);
            List<KeyframeSet> list2 = this.mValues;
            return new TouchAnimator(array, (KeyframeSet[]) list2.toArray(new KeyframeSet[list2.size()]), this.mStartDelay, this.mEndDelay, this.mInterpolator, this.mListener, null);
        }

        public Builder setEndDelay(float f) {
            this.mEndDelay = f;
            return this;
        }

        public Builder setInterpolator(Interpolator interpolator) {
            this.mInterpolator = interpolator;
            return this;
        }

        public Builder setListener(Listener listener) {
            this.mListener = listener;
            return this;
        }

        public Builder setStartDelay(float f) {
            this.mStartDelay = f;
            return this;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/TouchAnimator$FloatKeyframeSet.class */
    public static class FloatKeyframeSet<T> extends KeyframeSet {
        public final Property<T, Float> mProperty;
        public final float[] mValues;

        public FloatKeyframeSet(Property<T, Float> property, float[] fArr) {
            super(fArr.length);
            this.mProperty = property;
            this.mValues = fArr;
        }

        @Override // com.android.systemui.qs.TouchAnimator.KeyframeSet
        public void interpolate(int i, float f, Object obj) {
            float[] fArr = this.mValues;
            float f2 = fArr[i - 1];
            this.mProperty.set(obj, Float.valueOf(f2 + ((fArr[i] - f2) * f)));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/TouchAnimator$KeyframeSet.class */
    public static abstract class KeyframeSet {
        public final float mFrameWidth;
        public final int mSize;

        public KeyframeSet(int i) {
            this.mSize = i;
            this.mFrameWidth = 1.0f / (i - 1);
        }

        public static KeyframeSet ofFloat(Property property, float... fArr) {
            return new FloatKeyframeSet(property, fArr);
        }

        public abstract void interpolate(int i, float f, Object obj);

        public void setValue(float f, Object obj) {
            int constrain = MathUtils.constrain((int) Math.ceil(f / this.mFrameWidth), 1, this.mSize - 1);
            float f2 = this.mFrameWidth;
            interpolate(constrain, (f - ((constrain - 1) * f2)) / f2, obj);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/TouchAnimator$Listener.class */
    public interface Listener {
        void onAnimationAtEnd();

        void onAnimationAtStart();

        void onAnimationStarted();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/TouchAnimator$ListenerAdapter.class */
    public static class ListenerAdapter implements Listener {
        @Override // com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationAtEnd() {
        }

        @Override // com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationAtStart() {
        }
    }

    public TouchAnimator(Object[] objArr, KeyframeSet[] keyframeSetArr, float f, float f2, Interpolator interpolator, Listener listener) {
        this.mLastT = -1.0f;
        this.mTargets = objArr;
        this.mKeyframeSets = keyframeSetArr;
        this.mStartDelay = f;
        this.mEndDelay = f2;
        this.mSpan = (1.0f - f2) - f;
        this.mInterpolator = interpolator;
        this.mListener = listener;
    }

    public /* synthetic */ TouchAnimator(Object[] objArr, KeyframeSet[] keyframeSetArr, float f, float f2, Interpolator interpolator, Listener listener, TouchAnimator-IA r15) {
        this(objArr, keyframeSetArr, f, f2, interpolator, listener);
    }

    public void setPosition(float f) {
        if (Float.isNaN(f)) {
            return;
        }
        float constrain = MathUtils.constrain((f - this.mStartDelay) / this.mSpan, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f);
        Interpolator interpolator = this.mInterpolator;
        float f2 = constrain;
        if (interpolator != null) {
            f2 = interpolator.getInterpolation(constrain);
        }
        float f3 = this.mLastT;
        if (f2 == f3) {
            return;
        }
        Listener listener = this.mListener;
        if (listener != null) {
            if (f2 == 1.0f) {
                listener.onAnimationAtEnd();
            } else if (f2 == ActionBarShadowController.ELEVATION_LOW) {
                listener.onAnimationAtStart();
            } else if (f3 <= ActionBarShadowController.ELEVATION_LOW || f3 == 1.0f) {
                listener.onAnimationStarted();
            }
            this.mLastT = f2;
        }
        int i = 0;
        while (true) {
            Object[] objArr = this.mTargets;
            if (i >= objArr.length) {
                return;
            }
            this.mKeyframeSets[i].setValue(f2, objArr[i]);
            i++;
        }
    }
}