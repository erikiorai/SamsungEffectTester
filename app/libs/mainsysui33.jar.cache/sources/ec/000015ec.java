package com.android.systemui.decor;

import android.view.DisplayCutout;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/FaceScanningProviderFactoryKt.class */
public final class FaceScanningProviderFactoryKt {
    /* JADX WARN: Code restructure failed: missing block: B:13:0x001b, code lost:
        if (r3 != 2) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x002c, code lost:
        if (r3 != 2) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003d, code lost:
        if (r3 != 2) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int baseOnRotation0(int i, int i2) {
        int i3 = i;
        if (i2 != 0) {
            if (i2 == 1) {
                if (i != 0) {
                    if (i != 1) {
                    }
                    i3 = 2;
                }
                i3 = 1;
            } else if (i2 != 3) {
                if (i != 0) {
                    if (i != 1) {
                    }
                    i3 = 3;
                }
                i3 = 2;
            } else {
                if (i != 0) {
                    if (i != 1) {
                    }
                    i3 = 0;
                }
                i3 = 3;
            }
        }
        return i3;
    }

    public static final List<Integer> getBoundBaseOnCurrentRotation(DisplayCutout displayCutout) {
        ArrayList arrayList = new ArrayList();
        if (!displayCutout.getBoundingRectLeft().isEmpty()) {
            arrayList.add(0);
        }
        if (!displayCutout.getBoundingRectTop().isEmpty()) {
            arrayList.add(1);
        }
        if (!displayCutout.getBoundingRectRight().isEmpty()) {
            arrayList.add(2);
        }
        if (!displayCutout.getBoundingRectBottom().isEmpty()) {
            arrayList.add(3);
        }
        return arrayList;
    }
}