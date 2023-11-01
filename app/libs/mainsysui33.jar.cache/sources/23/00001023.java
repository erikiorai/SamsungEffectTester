package com.android.systemui.accessibility;

import android.hardware.display.DisplayManager;
import android.util.SparseArray;
import android.view.Display;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/DisplayIdIndexSupplier.class */
public abstract class DisplayIdIndexSupplier<T> {
    public final DisplayManager mDisplayManager;
    public final SparseArray<T> mSparseArray = new SparseArray<>();

    public DisplayIdIndexSupplier(DisplayManager displayManager) {
        this.mDisplayManager = displayManager;
    }

    public abstract T createInstance(Display display);

    public void forEach(Consumer<T> consumer) {
        for (int i = 0; i < this.mSparseArray.size(); i++) {
            consumer.accept(this.mSparseArray.valueAt(i));
        }
    }

    public T get(int i) {
        T t = this.mSparseArray.get(i);
        if (t != null) {
            return t;
        }
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            return null;
        }
        T createInstance = createInstance(display);
        this.mSparseArray.put(i, createInstance);
        return createInstance;
    }

    public T valueAt(int i) {
        return this.mSparseArray.get(i);
    }
}