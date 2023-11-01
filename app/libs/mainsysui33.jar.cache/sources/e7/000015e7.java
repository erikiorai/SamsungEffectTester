package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/DecorProvider.class */
public abstract class DecorProvider {
    public abstract List<Integer> getAlignedBounds();

    public final int getNumOfAlignedBound() {
        return getAlignedBounds().size();
    }

    public abstract int getViewId();

    public abstract View inflateView(Context context, ViewGroup viewGroup, int i, int i2);

    public abstract void onReloadResAndMeasure(View view, int i, int i2, int i3, String str);

    public String toString() {
        String simpleName = getClass().getSimpleName();
        List<Integer> alignedBounds = getAlignedBounds();
        return simpleName + "{alignedBounds=" + alignedBounds + "}";
    }
}