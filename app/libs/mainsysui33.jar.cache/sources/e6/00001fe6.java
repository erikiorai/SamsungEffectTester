package com.android.systemui.plugins.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/util/RingBuffer$iterator$1.class */
public final class RingBuffer$iterator$1<T> implements Iterator<T>, KMappedMarker {
    private int position;
    public final /* synthetic */ RingBuffer<T> this$0;

    public RingBuffer$iterator$1(RingBuffer<T> ringBuffer) {
        this.this$0 = ringBuffer;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.position < this.this$0.getSize();
    }

    @Override // java.util.Iterator
    public T next() {
        if (this.position < this.this$0.getSize()) {
            T t = this.this$0.get(this.position);
            this.position++;
            return t;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}