package com.android.systemui.decor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/DecorProviderKt.class */
public final class DecorProviderKt {
    public static final Integer getProperBound(List<? extends DecorProvider> list) {
        int i;
        Object obj;
        if (list.isEmpty()) {
            return null;
        }
        Iterator<T> it = list.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((DecorProvider) obj).getNumOfAlignedBound() == 1) {
                break;
            }
        }
        DecorProvider decorProvider = (DecorProvider) obj;
        if (decorProvider != null) {
            return decorProvider.getAlignedBounds().get(0);
        }
        int[] iArr = new int[4];
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        for (DecorProvider decorProvider2 : list) {
            for (Integer num : decorProvider2.getAlignedBounds()) {
                int intValue = num.intValue();
                iArr[intValue] = iArr[intValue] + 1;
            }
        }
        int i2 = 0;
        Integer num2 = null;
        while (i < 4) {
            int intValue2 = new Integer[]{1, 3, 0, 2}[i].intValue();
            int i3 = i2;
            if (iArr[intValue2] > i2) {
                num2 = Integer.valueOf(intValue2);
                i3 = iArr[intValue2];
            }
            i++;
            i2 = i3;
        }
        return num2;
    }

    public static final Pair<List<DecorProvider>, List<DecorProvider>> partitionAlignedBound(List<? extends DecorProvider> list, int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list) {
            if (((DecorProvider) obj).getAlignedBounds().contains(Integer.valueOf(i))) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        return new Pair<>(arrayList, arrayList2);
    }
}