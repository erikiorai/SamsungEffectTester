package com.android.systemui.animation;

import android.util.SparseArray;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/TextAnimatorKt.class */
public final class TextAnimatorKt {
    /* JADX DEBUG: Multi-variable search result rejected for r0v6, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <V> V getOrElse(SparseArray<V> sparseArray, int i, Function0<? extends V> function0) {
        V v = sparseArray.get(i);
        V v2 = v;
        if (v == null) {
            v2 = function0.invoke();
            sparseArray.put(i, v2);
        }
        return v2;
    }
}