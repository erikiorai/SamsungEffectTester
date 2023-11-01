package com.airbnb.lottie;

import java.util.Arrays;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/LottieResult.class */
public final class LottieResult<V> {
    public final Throwable exception;
    public final V value;

    public LottieResult(V v) {
        this.value = v;
        this.exception = null;
    }

    public LottieResult(Throwable th) {
        this.exception = th;
        this.value = null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LottieResult) {
            LottieResult lottieResult = (LottieResult) obj;
            if (getValue() == null || !getValue().equals(lottieResult.getValue())) {
                if (getException() == null || lottieResult.getException() == null) {
                    return false;
                }
                return getException().toString().equals(getException().toString());
            }
            return true;
        }
        return false;
    }

    public Throwable getException() {
        return this.exception;
    }

    public V getValue() {
        return this.value;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{getValue(), getException()});
    }
}