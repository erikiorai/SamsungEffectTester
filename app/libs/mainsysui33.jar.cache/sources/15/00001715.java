package com.android.systemui.dreams.complication;

import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutParams.class */
public class ComplicationLayoutParams extends ViewGroup.LayoutParams {
    public static final Map<Integer, Integer> INVALID_DIRECTIONS;
    public static final int[] INVALID_POSITIONS = {3, 12};

    static {
        HashMap hashMap = new HashMap();
        INVALID_DIRECTIONS = hashMap;
        hashMap.put(2, 2);
        hashMap.put(1, 1);
        hashMap.put(4, 4);
        hashMap.put(8, 8);
    }

    public static void iteratePositions(Consumer<Integer> consumer, int i) {
        int i2 = 1;
        while (true) {
            int i3 = i2;
            if (i3 > 8) {
                return;
            }
            if ((i & i3) == i3) {
                consumer.accept(Integer.valueOf(i3));
            }
            i2 = i3 << 1;
        }
    }
}