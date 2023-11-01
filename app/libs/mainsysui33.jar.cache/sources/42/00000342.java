package androidx.core.animation;

import android.annotation.SuppressLint;
import android.util.Property;
import androidx.core.animation.AnimationHandler;
import java.lang.ref.WeakReference;

/* loaded from: mainsysui33.jar:androidx/core/animation/ObjectAnimator.class */
public final class ObjectAnimator extends ValueAnimator {
    public boolean mAutoCancel = false;
    public Property mProperty;
    public String mPropertyName;
    public WeakReference<Object> mTarget;

    public ObjectAnimator() {
    }

    public <T> ObjectAnimator(T t, Property<T, ?> property) {
        setTarget(t);
        setProperty(property);
    }

    public ObjectAnimator(Object obj, String str) {
        setTarget(obj);
        setPropertyName(str);
    }

    public static <T> ObjectAnimator ofFloat(T t, Property<T, Float> property, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(t, property);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    public static ObjectAnimator ofFloat(Object obj, String str, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, str);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    @Override // androidx.core.animation.ValueAnimator
    public void animateValue(float f) {
        Object target = getTarget();
        if (this.mTarget != null && target == null) {
            cancel();
            return;
        }
        super.animateValue(f);
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].setAnimatedValue(target);
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    @SuppressLint({"NoClone"})
    /* renamed from: clone */
    public ObjectAnimator mo151clone() {
        return (ObjectAnimator) super.mo151clone();
    }

    @Override // androidx.core.animation.ValueAnimator
    public String getNameForTrace() {
        String str = this.mAnimTraceName;
        String str2 = str;
        if (str == null) {
            str2 = "animator:" + getPropertyName();
        }
        return str2;
    }

    public String getPropertyName() {
        String str;
        String str2 = this.mPropertyName;
        String str3 = null;
        if (str2 == null) {
            Property property = this.mProperty;
            if (property != null) {
                str2 = property.getName();
            } else {
                PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
                str2 = null;
                if (propertyValuesHolderArr != null) {
                    str2 = null;
                    if (propertyValuesHolderArr.length > 0) {
                        int i = 0;
                        while (true) {
                            str2 = str3;
                            if (i >= this.mValues.length) {
                                break;
                            }
                            if (i == 0) {
                                str = "";
                            } else {
                                str = str3 + ",";
                            }
                            str3 = str + this.mValues[i].getPropertyName();
                            i++;
                        }
                    }
                }
            }
        }
        return str2;
    }

    public Object getTarget() {
        WeakReference<Object> weakReference = this.mTarget;
        return weakReference == null ? null : weakReference.get();
    }

    public final boolean hasSameTargetAndProperties(Animator animator) {
        if (!(animator instanceof ObjectAnimator)) {
            return false;
        }
        ObjectAnimator objectAnimator = (ObjectAnimator) animator;
        PropertyValuesHolder[] values = objectAnimator.getValues();
        if (objectAnimator.getTarget() != getTarget() || this.mValues.length != values.length) {
            return false;
        }
        int i = 0;
        while (true) {
            PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
            if (i >= propertyValuesHolderArr.length) {
                return true;
            }
            PropertyValuesHolder propertyValuesHolder = propertyValuesHolderArr[i];
            PropertyValuesHolder propertyValuesHolder2 = values[i];
            if (propertyValuesHolder.getPropertyName() == null || !propertyValuesHolder.getPropertyName().equals(propertyValuesHolder2.getPropertyName())) {
                return false;
            }
            i++;
        }
    }

    @Override // androidx.core.animation.ValueAnimator
    public void initAnimation() {
        if (this.mInitialized) {
            return;
        }
        Object target = getTarget();
        if (target != null) {
            int length = this.mValues.length;
            for (int i = 0; i < length; i++) {
                this.mValues[i].setupSetterAndGetter(target);
            }
        }
        super.initAnimation();
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public boolean isInitialized() {
        return this.mInitialized;
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public ObjectAnimator setDuration(long j) {
        super.setDuration(j);
        return this;
    }

    @Override // androidx.core.animation.ValueAnimator
    public void setFloatValues(float... fArr) {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null && propertyValuesHolderArr.length != 0) {
            super.setFloatValues(fArr);
            return;
        }
        Property property = this.mProperty;
        if (property != null) {
            setValues(PropertyValuesHolder.ofFloat(property, fArr));
        } else {
            setValues(PropertyValuesHolder.ofFloat(this.mPropertyName, fArr));
        }
    }

    public void setProperty(Property property) {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            PropertyValuesHolder propertyValuesHolder = propertyValuesHolderArr[0];
            String propertyName = propertyValuesHolder.getPropertyName();
            propertyValuesHolder.setProperty(property);
            this.mValuesMap.remove(propertyName);
            this.mValuesMap.put(this.mPropertyName, propertyValuesHolder);
        }
        if (this.mProperty != null) {
            this.mPropertyName = property.getName();
        }
        this.mProperty = property;
        this.mInitialized = false;
    }

    public void setPropertyName(String str) {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            PropertyValuesHolder propertyValuesHolder = propertyValuesHolderArr[0];
            String propertyName = propertyValuesHolder.getPropertyName();
            propertyValuesHolder.setPropertyName(str);
            this.mValuesMap.remove(propertyName);
            this.mValuesMap.put(str, propertyValuesHolder);
        }
        this.mPropertyName = str;
        this.mInitialized = false;
    }

    public void setTarget(Object obj) {
        if (getTarget() != obj) {
            if (isStarted()) {
                cancel();
            }
            this.mTarget = obj == null ? null : new WeakReference<>(obj);
            this.mInitialized = false;
        }
    }

    public boolean shouldAutoCancel(AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        if (animationFrameCallback != null && (animationFrameCallback instanceof ObjectAnimator)) {
            ObjectAnimator objectAnimator = (ObjectAnimator) animationFrameCallback;
            return objectAnimator.mAutoCancel && hasSameTargetAndProperties(objectAnimator);
        }
        return false;
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public void start() {
        AnimationHandler.getInstance().autoCancelBasedOn(this);
        super.start();
    }

    @Override // androidx.core.animation.ValueAnimator
    public String toString() {
        String str = "ObjectAnimator@" + Integer.toHexString(hashCode()) + ", target " + getTarget();
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