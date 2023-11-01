package androidx.dynamicanimation.animation;

import android.util.AndroidRuntimeException;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.dynamicanimation.animation.AnimationHandler;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:androidx/dynamicanimation/animation/DynamicAnimation.class */
public abstract class DynamicAnimation<T extends DynamicAnimation<T>> implements AnimationHandler.AnimationFrameCallback {
    public AnimationHandler mAnimationHandler;
    public float mMinVisibleChange;
    public final FloatPropertyCompat mProperty;
    public final Object mTarget;
    public static final ViewProperty TRANSLATION_X = new ViewProperty("translationX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getTranslationX();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setTranslationX(f);
        }
    };
    public static final ViewProperty TRANSLATION_Y = new ViewProperty("translationY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.2
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getTranslationY();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setTranslationY(f);
        }
    };
    public static final ViewProperty TRANSLATION_Z = new ViewProperty("translationZ") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.3
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return ViewCompat.getTranslationZ(view);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            ViewCompat.setTranslationZ(view, f);
        }
    };
    public static final ViewProperty SCALE_X = new ViewProperty("scaleX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.4
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getScaleX();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setScaleX(f);
        }
    };
    public static final ViewProperty SCALE_Y = new ViewProperty("scaleY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.5
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getScaleY();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setScaleY(f);
        }
    };
    public static final ViewProperty ROTATION = new ViewProperty("rotation") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.6
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getRotation();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setRotation(f);
        }
    };
    public static final ViewProperty ROTATION_X = new ViewProperty("rotationX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.7
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getRotationX();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setRotationX(f);
        }
    };
    public static final ViewProperty ROTATION_Y = new ViewProperty("rotationY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.8
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getRotationY();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setRotationY(f);
        }
    };
    public static final ViewProperty X = new ViewProperty("x") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.9
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getX();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setX(f);
        }
    };
    public static final ViewProperty Y = new ViewProperty("y") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.10
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getY();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setY(f);
        }
    };
    public static final ViewProperty Z = new ViewProperty("z") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.11
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return ViewCompat.getZ(view);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            ViewCompat.setZ(view, f);
        }
    };
    public static final ViewProperty ALPHA = new ViewProperty("alpha") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.12
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getAlpha();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setAlpha(f);
        }
    };
    public static final ViewProperty SCROLL_X = new ViewProperty("scrollX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.13
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getScrollX();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setScrollX((int) f);
        }
    };
    public static final ViewProperty SCROLL_Y = new ViewProperty("scrollY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.14
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(View view) {
            return view.getScrollY();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(View view, float f) {
            view.setScrollY((int) f);
        }
    };
    public float mVelocity = ActionBarShadowController.ELEVATION_LOW;
    public float mValue = Float.MAX_VALUE;
    public boolean mStartValueIsSet = false;
    public boolean mRunning = false;
    public float mMaxValue = Float.MAX_VALUE;
    public float mMinValue = -Float.MAX_VALUE;
    public long mLastFrameTime = 0;
    public final ArrayList<OnAnimationEndListener> mEndListeners = new ArrayList<>();
    public final ArrayList<OnAnimationUpdateListener> mUpdateListeners = new ArrayList<>();

    /* loaded from: mainsysui33.jar:androidx/dynamicanimation/animation/DynamicAnimation$MassState.class */
    public static class MassState {
        public float mValue;
        public float mVelocity;
    }

    /* loaded from: mainsysui33.jar:androidx/dynamicanimation/animation/DynamicAnimation$OnAnimationEndListener.class */
    public interface OnAnimationEndListener {
        void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2);
    }

    /* loaded from: mainsysui33.jar:androidx/dynamicanimation/animation/DynamicAnimation$OnAnimationUpdateListener.class */
    public interface OnAnimationUpdateListener {
        void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2);
    }

    /* loaded from: mainsysui33.jar:androidx/dynamicanimation/animation/DynamicAnimation$ViewProperty.class */
    public static abstract class ViewProperty extends FloatPropertyCompat<View> {
        public ViewProperty(String str) {
            super(str);
        }
    }

    public <K> DynamicAnimation(K k, FloatPropertyCompat<K> floatPropertyCompat) {
        this.mTarget = k;
        this.mProperty = floatPropertyCompat;
        if (floatPropertyCompat == ROTATION || floatPropertyCompat == ROTATION_X || floatPropertyCompat == ROTATION_Y) {
            this.mMinVisibleChange = 0.1f;
        } else if (floatPropertyCompat == ALPHA) {
            this.mMinVisibleChange = 0.00390625f;
        } else if (floatPropertyCompat == SCALE_X || floatPropertyCompat == SCALE_Y) {
            this.mMinVisibleChange = 0.002f;
        } else {
            this.mMinVisibleChange = 1.0f;
        }
    }

    public static <T> void removeEntry(ArrayList<T> arrayList, T t) {
        int indexOf = arrayList.indexOf(t);
        if (indexOf >= 0) {
            arrayList.set(indexOf, null);
        }
    }

    public static <T> void removeNullEntries(ArrayList<T> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    public T addEndListener(OnAnimationEndListener onAnimationEndListener) {
        if (!this.mEndListeners.contains(onAnimationEndListener)) {
            this.mEndListeners.add(onAnimationEndListener);
        }
        return this;
    }

    public T addUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        if (isRunning()) {
            throw new UnsupportedOperationException("Error: Update listeners must be added beforethe animation.");
        }
        if (!this.mUpdateListeners.contains(onAnimationUpdateListener)) {
            this.mUpdateListeners.add(onAnimationUpdateListener);
        }
        return this;
    }

    public void cancel() {
        if (!getAnimationHandler().isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be canceled from the same thread as the animation handler");
        }
        if (this.mRunning) {
            endAnimationInternal(true);
        }
    }

    @Override // androidx.dynamicanimation.animation.AnimationHandler.AnimationFrameCallback
    public boolean doAnimationFrame(long j) {
        long j2 = this.mLastFrameTime;
        if (j2 == 0) {
            this.mLastFrameTime = j;
            setPropertyValue(this.mValue);
            return false;
        }
        this.mLastFrameTime = j;
        float durationScale = getAnimationHandler().getDurationScale();
        boolean updateValueAndVelocity = updateValueAndVelocity(durationScale == ActionBarShadowController.ELEVATION_LOW ? 2147483647L : ((float) (j - j2)) / durationScale);
        float min = Math.min(this.mValue, this.mMaxValue);
        this.mValue = min;
        float max = Math.max(min, this.mMinValue);
        this.mValue = max;
        setPropertyValue(max);
        if (updateValueAndVelocity) {
            endAnimationInternal(false);
        }
        return updateValueAndVelocity;
    }

    public final void endAnimationInternal(boolean z) {
        this.mRunning = false;
        getAnimationHandler().removeCallback(this);
        this.mLastFrameTime = 0L;
        this.mStartValueIsSet = false;
        for (int i = 0; i < this.mEndListeners.size(); i++) {
            if (this.mEndListeners.get(i) != null) {
                this.mEndListeners.get(i).onAnimationEnd(this, z, this.mValue, this.mVelocity);
            }
        }
        removeNullEntries(this.mEndListeners);
    }

    public AnimationHandler getAnimationHandler() {
        AnimationHandler animationHandler = this.mAnimationHandler;
        if (animationHandler == null) {
            animationHandler = AnimationHandler.getInstance();
        }
        return animationHandler;
    }

    public final float getPropertyValue() {
        return this.mProperty.getValue(this.mTarget);
    }

    public float getValueThreshold() {
        return this.mMinVisibleChange * 0.75f;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void removeEndListener(OnAnimationEndListener onAnimationEndListener) {
        removeEntry(this.mEndListeners, onAnimationEndListener);
    }

    public T setMaxValue(float f) {
        this.mMaxValue = f;
        return this;
    }

    public T setMinValue(float f) {
        this.mMinValue = f;
        return this;
    }

    public T setMinimumVisibleChange(float f) {
        if (f > ActionBarShadowController.ELEVATION_LOW) {
            this.mMinVisibleChange = f;
            setValueThreshold(f * 0.75f);
            return this;
        }
        throw new IllegalArgumentException("Minimum visible change must be positive.");
    }

    public void setPropertyValue(float f) {
        this.mProperty.setValue(this.mTarget, f);
        for (int i = 0; i < this.mUpdateListeners.size(); i++) {
            if (this.mUpdateListeners.get(i) != null) {
                this.mUpdateListeners.get(i).onAnimationUpdate(this, this.mValue, this.mVelocity);
            }
        }
        removeNullEntries(this.mUpdateListeners);
    }

    public T setStartValue(float f) {
        this.mValue = f;
        this.mStartValueIsSet = true;
        return this;
    }

    public T setStartVelocity(float f) {
        this.mVelocity = f;
        return this;
    }

    public abstract void setValueThreshold(float f);

    public void start() {
        if (!getAnimationHandler().isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
        }
        if (this.mRunning) {
            return;
        }
        startAnimationInternal();
    }

    public final void startAnimationInternal() {
        if (this.mRunning) {
            return;
        }
        this.mRunning = true;
        if (!this.mStartValueIsSet) {
            this.mValue = getPropertyValue();
        }
        float f = this.mValue;
        if (f > this.mMaxValue || f < this.mMinValue) {
            throw new IllegalArgumentException("Starting value need to be in between min value and max value");
        }
        getAnimationHandler().addAnimationFrameCallback(this, 0L);
    }

    public abstract boolean updateValueAndVelocity(long j);
}