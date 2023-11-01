package androidx.core.animation;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.animation.AnimationUtils;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.Animator;
import androidx.core.os.TraceCompat;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: mainsysui33.jar:androidx/core/animation/ValueAnimator.class */
public class ValueAnimator extends Animator implements AnimationHandler.AnimationFrameCallback {
    public static final Interpolator sDefaultInterpolator = new AccelerateDecelerateInterpolator();
    public static float sDurationScale = 1.0f;
    public long mPauseTime;
    public boolean mReversing;
    public PropertyValuesHolder[] mValues;
    public HashMap<String, PropertyValuesHolder> mValuesMap;
    public long mStartTime = -1;
    public float mSeekFraction = -1.0f;
    public boolean mResumed = false;
    public float mOverallFraction = ActionBarShadowController.ELEVATION_LOW;
    public float mCurrentFraction = ActionBarShadowController.ELEVATION_LOW;
    public long mLastFrameTime = -1;
    public boolean mRunning = false;
    public boolean mStarted = false;
    public boolean mStartListenersCalled = false;
    public boolean mInitialized = false;
    public boolean mAnimationEndRequested = false;
    public long mDuration = 300;
    public long mStartDelay = 0;
    public int mRepeatCount = 0;
    public int mRepeatMode = 1;
    public boolean mSelfPulse = true;
    public boolean mSuppressSelfPulseRequested = false;
    public Interpolator mInterpolator = sDefaultInterpolator;
    public float mDurationScale = -1.0f;
    public String mAnimTraceName = null;

    public static float getDurationScale() {
        return sDurationScale;
    }

    public static ValueAnimator ofFloat(float... fArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(fArr);
        return valueAnimator;
    }

    public final void addAnimationCallback() {
        if (this.mSelfPulse) {
            Animator.addAnimationCallback(this);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c2, code lost:
        if (r17 != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean animateBasedOnTime(long j) {
        boolean z = false;
        if (this.mRunning) {
            long scaledDuration = getScaledDuration();
            int i = (scaledDuration > 0L ? 1 : (scaledDuration == 0L ? 0 : -1));
            float f = i > 0 ? ((float) (j - this.mStartTime)) / ((float) scaledDuration) : 1.0f;
            boolean z2 = ((int) f) > ((int) this.mOverallFraction);
            int i2 = this.mRepeatCount;
            boolean z3 = f >= ((float) (i2 + 1)) && i2 != -1;
            if (i != 0) {
                if (!z2 || z3) {
                    z = false;
                } else {
                    ArrayList<Animator.AnimatorListener> arrayList = this.mListeners;
                    z = false;
                    if (arrayList != null) {
                        int size = arrayList.size();
                        int i3 = 0;
                        while (true) {
                            z = false;
                            if (i3 >= size) {
                                break;
                            }
                            this.mListeners.get(i3).onAnimationRepeat(this);
                            i3++;
                        }
                    }
                }
                float clampFraction = clampFraction(f);
                this.mOverallFraction = clampFraction;
                animateValue(getCurrentIterationFraction(clampFraction, this.mReversing));
            }
            z = true;
            float clampFraction2 = clampFraction(f);
            this.mOverallFraction = clampFraction2;
            animateValue(getCurrentIterationFraction(clampFraction2, this.mReversing));
        }
        return z;
    }

    public void animateValue(float f) {
        float interpolation = this.mInterpolator.getInterpolation(f);
        this.mCurrentFraction = interpolation;
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].calculateValue(interpolation);
        }
        ArrayList<Animator.AnimatorUpdateListener> arrayList = this.mUpdateListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mUpdateListeners.get(i2).onAnimationUpdate(this);
            }
        }
    }

    @Override // androidx.core.animation.Animator
    public void cancel() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mAnimationEndRequested) {
            return;
        }
        if ((this.mStarted || this.mRunning) && this.mListeners != null) {
            if (!this.mRunning) {
                notifyStartListeners();
            }
            Iterator it = ((ArrayList) this.mListeners.clone()).iterator();
            while (it.hasNext()) {
                ((Animator.AnimatorListener) it.next()).onAnimationCancel(this);
            }
        }
        endAnimation();
    }

    public final float clampFraction(float f) {
        int i;
        float f2;
        if (f < ActionBarShadowController.ELEVATION_LOW) {
            f2 = 0.0f;
        } else {
            f2 = f;
            if (this.mRepeatCount != -1) {
                f2 = Math.min(f, i + 1);
            }
        }
        return f2;
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.core.animation.Animator
    @SuppressLint({"NoClone"})
    /* renamed from: clone */
    public ValueAnimator mo151clone() {
        ValueAnimator valueAnimator = (ValueAnimator) super.mo151clone();
        if (this.mUpdateListeners != null) {
            valueAnimator.mUpdateListeners = new ArrayList<>(this.mUpdateListeners);
        }
        valueAnimator.mSeekFraction = -1.0f;
        valueAnimator.mReversing = false;
        valueAnimator.mInitialized = false;
        valueAnimator.mStarted = false;
        valueAnimator.mRunning = false;
        valueAnimator.mPaused = false;
        valueAnimator.mResumed = false;
        valueAnimator.mStartListenersCalled = false;
        valueAnimator.mStartTime = -1L;
        valueAnimator.mAnimationEndRequested = false;
        valueAnimator.mPauseTime = -1L;
        valueAnimator.mLastFrameTime = -1L;
        valueAnimator.mOverallFraction = ActionBarShadowController.ELEVATION_LOW;
        valueAnimator.mCurrentFraction = ActionBarShadowController.ELEVATION_LOW;
        valueAnimator.mSelfPulse = true;
        valueAnimator.mSuppressSelfPulseRequested = false;
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            int length = propertyValuesHolderArr.length;
            valueAnimator.mValues = new PropertyValuesHolder[length];
            valueAnimator.mValuesMap = new HashMap<>(length);
            for (int i = 0; i < length; i++) {
                PropertyValuesHolder mo159clone = propertyValuesHolderArr[i].mo159clone();
                valueAnimator.mValues[i] = mo159clone;
                valueAnimator.mValuesMap.put(mo159clone.getPropertyName(), mo159clone);
            }
        }
        return valueAnimator;
    }

    @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallback
    public final boolean doAnimationFrame(long j) {
        if (this.mStartTime < 0) {
            this.mStartTime = this.mReversing ? j : (((float) this.mStartDelay) * resolveDurationScale()) + j;
        }
        if (this.mPaused) {
            this.mPauseTime = j;
            removeAnimationCallback();
            return false;
        }
        if (this.mResumed) {
            this.mResumed = false;
            long j2 = this.mPauseTime;
            if (j2 > 0) {
                this.mStartTime += j - j2;
            }
        }
        if (!this.mRunning) {
            if (this.mStartTime > j && this.mSeekFraction == -1.0f) {
                return false;
            }
            this.mRunning = true;
            startAnimation();
        }
        if (this.mLastFrameTime < 0 && this.mSeekFraction >= ActionBarShadowController.ELEVATION_LOW) {
            this.mStartTime = j - (((float) getScaledDuration()) * this.mSeekFraction);
            this.mSeekFraction = -1.0f;
        }
        this.mLastFrameTime = j;
        boolean animateBasedOnTime = animateBasedOnTime(Math.max(j, this.mStartTime));
        if (animateBasedOnTime) {
            endAnimation();
        }
        return animateBasedOnTime;
    }

    @Override // androidx.core.animation.Animator
    public void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (!this.mRunning) {
            startAnimation();
            this.mStarted = true;
        } else if (!this.mInitialized) {
            initAnimation();
        }
        animateValue(shouldPlayBackward(this.mRepeatCount, this.mReversing) ? 0.0f : 1.0f);
        endAnimation();
    }

    public final void endAnimation() {
        ArrayList<Animator.AnimatorListener> arrayList;
        if (this.mAnimationEndRequested) {
            return;
        }
        removeAnimationCallback();
        this.mAnimationEndRequested = true;
        this.mPaused = false;
        boolean z = (this.mStarted || this.mRunning) && this.mListeners != null;
        if (z && !this.mRunning) {
            notifyStartListeners();
        }
        this.mRunning = false;
        this.mStarted = false;
        this.mStartListenersCalled = false;
        this.mLastFrameTime = -1L;
        this.mStartTime = -1L;
        if (z && (arrayList = this.mListeners) != null) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationEnd(this, this.mReversing);
            }
        }
        this.mReversing = false;
        TraceCompat.endSection();
    }

    public Object getAnimatedValue() {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length <= 0) {
            return null;
        }
        return propertyValuesHolderArr[0].getAnimatedValue();
    }

    public final int getCurrentIteration(float f) {
        float clampFraction = clampFraction(f);
        double d = clampFraction;
        double floor = Math.floor(d);
        double d2 = floor;
        if (d == floor) {
            d2 = floor;
            if (clampFraction > ActionBarShadowController.ELEVATION_LOW) {
                d2 = floor - 1.0d;
            }
        }
        return (int) d2;
    }

    public final float getCurrentIterationFraction(float f, boolean z) {
        float clampFraction = clampFraction(f);
        int currentIteration = getCurrentIteration(clampFraction);
        float f2 = clampFraction - currentIteration;
        float f3 = f2;
        if (shouldPlayBackward(currentIteration, z)) {
            f3 = 1.0f - f2;
        }
        return f3;
    }

    @Override // androidx.core.animation.Animator
    public long getDuration() {
        return this.mDuration;
    }

    public String getNameForTrace() {
        String str = this.mAnimTraceName;
        String str2 = str;
        if (str == null) {
            str2 = "animator";
        }
        return str2;
    }

    public final long getScaledDuration() {
        return ((float) this.mDuration) * resolveDurationScale();
    }

    @Override // androidx.core.animation.Animator
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // androidx.core.animation.Animator
    public long getTotalDuration() {
        int i = this.mRepeatCount;
        if (i == -1) {
            return -1L;
        }
        return this.mStartDelay + (this.mDuration * (i + 1));
    }

    @SuppressLint({"ArrayReturn"})
    public PropertyValuesHolder[] getValues() {
        return this.mValues;
    }

    public void initAnimation() {
        if (this.mInitialized) {
            return;
        }
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].init();
        }
        this.mInitialized = true;
    }

    @Override // androidx.core.animation.Animator
    public boolean isInitialized() {
        return this.mInitialized;
    }

    public final boolean isPulsingInternal() {
        return this.mLastFrameTime >= 0;
    }

    @Override // androidx.core.animation.Animator
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override // androidx.core.animation.Animator
    public boolean isStarted() {
        return this.mStarted;
    }

    public final void notifyStartListeners() {
        ArrayList<Animator.AnimatorListener> arrayList = this.mListeners;
        if (arrayList != null && !this.mStartListenersCalled) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationStart(this, this.mReversing);
            }
        }
        this.mStartListenersCalled = true;
    }

    @Override // androidx.core.animation.Animator
    public boolean pulseAnimationFrame(long j) {
        if (this.mSelfPulse) {
            return false;
        }
        return doAnimationFrame(j);
    }

    public final void removeAnimationCallback() {
        if (this.mSelfPulse) {
            Animator.removeAnimationCallback(this);
        }
    }

    public final float resolveDurationScale() {
        float f = this.mDurationScale;
        if (f < ActionBarShadowController.ELEVATION_LOW) {
            f = sDurationScale;
        }
        return f;
    }

    @Override // androidx.core.animation.Animator
    public void reverse() {
        if (isPulsingInternal()) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.mStartTime = currentAnimationTimeMillis - (getScaledDuration() - (currentAnimationTimeMillis - this.mStartTime));
            this.mReversing = !this.mReversing;
        } else if (!this.mStarted) {
            start(true);
        } else {
            this.mReversing = !this.mReversing;
            end();
        }
    }

    public void setCurrentFraction(float f) {
        initAnimation();
        float clampFraction = clampFraction(f);
        if (isPulsingInternal()) {
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis() - (((float) getScaledDuration()) * clampFraction);
        } else {
            this.mSeekFraction = clampFraction;
        }
        this.mOverallFraction = clampFraction;
        animateValue(getCurrentIterationFraction(clampFraction, this.mReversing));
    }

    public void setCurrentPlayTime(long j) {
        long j2 = this.mDuration;
        setCurrentFraction(j2 > 0 ? ((float) j) / ((float) j2) : 1.0f);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.core.animation.Animator
    public ValueAnimator setDuration(long j) {
        if (j >= 0) {
            this.mDuration = j;
            return this;
        }
        throw new IllegalArgumentException("Animators cannot have negative duration: " + j);
    }

    public void setFloatValues(float... fArr) {
        if (fArr == null || fArr.length == 0) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length == 0) {
            setValues(PropertyValuesHolder.ofFloat("", fArr));
        } else {
            propertyValuesHolderArr[0].setFloatValues(fArr);
        }
        this.mInitialized = false;
    }

    @Override // androidx.core.animation.Animator
    public void setInterpolator(Interpolator interpolator) {
        if (interpolator != null) {
            this.mInterpolator = interpolator;
        } else {
            this.mInterpolator = new LinearInterpolator();
        }
    }

    @Override // androidx.core.animation.Animator
    public void setStartDelay(long j) {
        long j2 = j;
        if (j < 0) {
            Log.w("ValueAnimator", "Start delay should always be non-negative");
            j2 = 0;
        }
        this.mStartDelay = j2;
    }

    public void setValues(PropertyValuesHolder... propertyValuesHolderArr) {
        int length = propertyValuesHolderArr.length;
        this.mValues = propertyValuesHolderArr;
        this.mValuesMap = new HashMap<>(length);
        for (PropertyValuesHolder propertyValuesHolder : propertyValuesHolderArr) {
            this.mValuesMap.put(propertyValuesHolder.getPropertyName(), propertyValuesHolder);
        }
        this.mInitialized = false;
    }

    public final boolean shouldPlayBackward(int i, boolean z) {
        if (i > 0 && this.mRepeatMode == 2) {
            int i2 = this.mRepeatCount;
            if (i < i2 + 1 || i2 == -1) {
                if (z) {
                    boolean z2 = false;
                    if (i % 2 == 0) {
                        z2 = true;
                    }
                    return z2;
                }
                boolean z3 = false;
                if (i % 2 != 0) {
                    z3 = true;
                }
                return z3;
            }
        }
        return z;
    }

    @Override // androidx.core.animation.Animator
    public void skipToEndValue(boolean z) {
        initAnimation();
        float f = z ? 0.0f : 1.0f;
        if (this.mRepeatCount % 2 == 1 && this.mRepeatMode == 2) {
            f = 0.0f;
        }
        animateValue(f);
    }

    @Override // androidx.core.animation.Animator
    public void start() {
        start(false);
    }

    public final void start(boolean z) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mReversing = z;
        this.mSelfPulse = !this.mSuppressSelfPulseRequested;
        if (z) {
            float f = this.mSeekFraction;
            if (f != -1.0f && f != ActionBarShadowController.ELEVATION_LOW) {
                int i = this.mRepeatCount;
                if (i == -1) {
                    this.mSeekFraction = 1.0f - ((float) (f - Math.floor(f)));
                } else {
                    this.mSeekFraction = (i + 1) - f;
                }
            }
        }
        this.mStarted = true;
        this.mPaused = false;
        this.mRunning = false;
        this.mAnimationEndRequested = false;
        this.mLastFrameTime = -1L;
        this.mStartTime = -1L;
        if (this.mStartDelay == 0 || this.mSeekFraction >= ActionBarShadowController.ELEVATION_LOW || this.mReversing) {
            startAnimation();
            float f2 = this.mSeekFraction;
            if (f2 == -1.0f) {
                setCurrentPlayTime(0L);
            } else {
                setCurrentFraction(f2);
            }
        }
        addAnimationCallback();
    }

    public final void startAnimation() {
        TraceCompat.beginSection(getNameForTrace());
        this.mAnimationEndRequested = false;
        initAnimation();
        this.mRunning = true;
        float f = this.mSeekFraction;
        if (f >= ActionBarShadowController.ELEVATION_LOW) {
            this.mOverallFraction = f;
        } else {
            this.mOverallFraction = ActionBarShadowController.ELEVATION_LOW;
        }
        if (this.mListeners != null) {
            notifyStartListeners();
        }
    }

    @Override // androidx.core.animation.Animator
    public void startWithoutPulsing(boolean z) {
        this.mSuppressSelfPulseRequested = true;
        if (z) {
            reverse();
        } else {
            start();
        }
        this.mSuppressSelfPulseRequested = false;
    }

    public String toString() {
        String str = "ValueAnimator@" + Integer.toHexString(hashCode());
        String str2 = str;
        if (this.mValues != null) {
            int i = 0;
            while (true) {
                str2 = str;
                if (i >= this.mValues.length) {
                    break;
                }
                str = str + "\n    " + this.mValues[i].toString();
                i++;
            }
        }
        return str2;
    }
}