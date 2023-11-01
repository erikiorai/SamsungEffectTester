package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.RegionInterceptingFrameLayout;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/OverlayWindow.class */
public final class OverlayWindow {
    public final Context context;
    public final ViewGroup rootView;
    public final Map<Integer, Pair<View, DecorProvider>> viewProviderMap = new LinkedHashMap();

    public OverlayWindow(Context context) {
        this.context = context;
        this.rootView = new RegionInterceptingFrameLayout(context);
    }

    public final void addDecorProvider(DecorProvider decorProvider, int i, int i2) {
        this.viewProviderMap.put(Integer.valueOf(decorProvider.getViewId()), new Pair<>(decorProvider.inflateView(this.context, this.rootView, i, i2), decorProvider));
    }

    public final void dump(PrintWriter printWriter, String str) {
        printWriter.println("  " + str + "=");
        ViewGroup viewGroup = this.rootView;
        printWriter.println("    rootView=" + viewGroup);
        int childCount = this.rootView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.rootView.getChildAt(i);
            Pair<View, DecorProvider> pair = this.viewProviderMap.get(Integer.valueOf(childAt.getId()));
            DecorProvider decorProvider = pair != null ? (DecorProvider) pair.getSecond() : null;
            printWriter.println("    child[" + i + "]=" + childAt + " " + decorProvider);
        }
    }

    public final ViewGroup getRootView() {
        return this.rootView;
    }

    public final View getView(int i) {
        Pair<View, DecorProvider> pair = this.viewProviderMap.get(Integer.valueOf(i));
        return pair != null ? (View) pair.getFirst() : null;
    }

    public final List<Integer> getViewIds() {
        return CollectionsKt___CollectionsKt.toList(this.viewProviderMap.keySet());
    }

    public final boolean hasSameProviders(List<? extends DecorProvider> list) {
        boolean z;
        boolean z2 = false;
        if (list.size() == this.viewProviderMap.size()) {
            List<? extends DecorProvider> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                for (DecorProvider decorProvider : list2) {
                    if (!(getView(decorProvider.getViewId()) != null)) {
                        z = false;
                        break;
                    }
                }
            }
            z = true;
            z2 = false;
            if (z) {
                z2 = true;
            }
        }
        return z2;
    }

    public final void onReloadResAndMeasure(Integer[] numArr, int i, int i2, int i3, String str) {
        Unit unit;
        if (numArr != null) {
            for (Integer num : numArr) {
                Pair<View, DecorProvider> pair = this.viewProviderMap.get(Integer.valueOf(num.intValue()));
                if (pair != null) {
                    ((DecorProvider) pair.getSecond()).onReloadResAndMeasure((View) pair.getFirst(), i, i2, i3, str);
                }
            }
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            Iterator<T> it = this.viewProviderMap.values().iterator();
            while (it.hasNext()) {
                Pair pair2 = (Pair) it.next();
                ((DecorProvider) pair2.getSecond()).onReloadResAndMeasure((View) pair2.getFirst(), i, i2, i3, str);
            }
        }
    }

    public final void removeRedundantViews(int[] iArr) {
        for (Number number : getViewIds()) {
            int intValue = number.intValue();
            if (iArr == null || !ArraysKt___ArraysKt.contains(iArr, intValue)) {
                removeView(intValue);
            }
        }
    }

    public final void removeView(int i) {
        View view = getView(i);
        if (view != null) {
            this.rootView.removeView(view);
            this.viewProviderMap.remove(Integer.valueOf(i));
        }
    }
}