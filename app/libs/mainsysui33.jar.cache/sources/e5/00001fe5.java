package com.android.systemui.plugins.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/util/RingBuffer.class */
public final class RingBuffer<T> implements Iterable<T>, KMappedMarker {
    private final List<T> buffer;
    private final Function0<T> factory;
    private final int maxSize;
    private long omega;

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: kotlin.jvm.functions.Function0<? extends T> */
    /* JADX WARN: Multi-variable type inference failed */
    public RingBuffer(int i, Function0<? extends T> function0) {
        this.maxSize = i;
        this.factory = function0;
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(null);
        }
        this.buffer = arrayList;
    }

    private final int indexOf(long j) {
        return (int) (j % this.maxSize);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v11, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    public final T advance() {
        int indexOf = indexOf(this.omega);
        this.omega++;
        T t = this.buffer.get(indexOf);
        T t2 = t;
        if (t == null) {
            t2 = this.factory.invoke();
            this.buffer.set(indexOf, t2);
        }
        return t2;
    }

    public final void forEach(Function1<? super T, Unit> function1) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            function1.invoke(get(i));
        }
    }

    public final T get(int i) {
        if (i >= 0 && i < getSize()) {
            T t = this.buffer.get(indexOf(Math.max(this.omega, this.maxSize) + i));
            Intrinsics.checkNotNull(t);
            return t;
        }
        throw new IndexOutOfBoundsException("Index " + i + " is out of bounds");
    }

    public final int getSize() {
        long j = this.omega;
        int i = this.maxSize;
        int i2 = i;
        if (j < i) {
            i2 = (int) j;
        }
        return i2;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new RingBuffer$iterator$1(this);
    }
}