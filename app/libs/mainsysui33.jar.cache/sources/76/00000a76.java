package com.airbnb.lottie.model;

import androidx.core.util.Pair;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/MutablePair.class */
public class MutablePair<T> {
    public T first;
    public T second;

    public static boolean objectsEqual(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            boolean z = false;
            if (objectsEqual(pair.first, this.first)) {
                z = false;
                if (objectsEqual(pair.second, this.second)) {
                    z = true;
                }
            }
            return z;
        }
        return false;
    }

    public int hashCode() {
        T t = this.first;
        int i = 0;
        int hashCode = t == null ? 0 : t.hashCode();
        T t2 = this.second;
        if (t2 != null) {
            i = t2.hashCode();
        }
        return hashCode ^ i;
    }

    public void set(T t, T t2) {
        this.first = t;
        this.second = t2;
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.first) + " " + String.valueOf(this.second) + "}";
    }
}