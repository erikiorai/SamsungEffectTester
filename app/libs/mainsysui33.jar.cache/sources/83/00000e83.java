package com.android.settingslib.mobile;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import com.android.settingslib.R$array;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileIconCarrierIdOverridesImpl.class */
public final class MobileIconCarrierIdOverridesImpl implements MobileIconCarrierIdOverrides {
    public static final Companion Companion = new Companion(null);
    public static final Map<Integer, Integer> MAPPING = MapsKt__MapsJVMKt.mapOf(TuplesKt.to(2032, Integer.valueOf(R$array.carrierId_2032_iconOverrides)));

    /* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileIconCarrierIdOverridesImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean overrideExists(int i, Map<Integer, Integer> map) {
            return map.containsKey(Integer.valueOf(i));
        }

        /* JADX WARN: Code restructure failed: missing block: B:9:0x0049, code lost:
            if (r0 > r0) goto L23;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Map<String, Integer> parseNetworkIconOverrideTypedArray(TypedArray typedArray) {
            int i;
            if (typedArray.length() % 2 != 0) {
                Log.w("MobileIconOverrides", "override must contain an even number of (key, value) entries. skipping");
                return MapsKt__MapsKt.emptyMap();
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            IntProgression step = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, typedArray.length()), 2);
            int first = step.getFirst();
            int last = step.getLast();
            int step2 = step.getStep();
            if (step2 > 0) {
                i = first;
            }
            if (step2 < 0 && last <= first) {
                i = first;
                while (true) {
                    String string = typedArray.getString(i);
                    int resourceId = typedArray.getResourceId(i + 1, 0);
                    if (string == null || resourceId == 0) {
                        Log.w("MobileIconOverrides", "Invalid override found. Skipping");
                    } else {
                        linkedHashMap.put(string, Integer.valueOf(resourceId));
                    }
                    if (i == last) {
                        break;
                    }
                    i += step2;
                }
            }
            return linkedHashMap;
        }
    }

    public static final Map<String, Integer> parseNetworkIconOverrideTypedArray(TypedArray typedArray) {
        return Companion.parseNetworkIconOverrideTypedArray(typedArray);
    }

    @Override // com.android.settingslib.mobile.MobileIconCarrierIdOverrides
    public boolean carrierIdEntryExists(int i) {
        return Companion.overrideExists(i, MAPPING);
    }

    @Override // com.android.settingslib.mobile.MobileIconCarrierIdOverrides
    public int getOverrideFor(int i, String str, Resources resources) {
        Integer num = MAPPING.get(Integer.valueOf(i));
        int i2 = 0;
        if (num != null) {
            TypedArray obtainTypedArray = resources.obtainTypedArray(num.intValue());
            Map<String, Integer> parseNetworkIconOverrideTypedArray = Companion.parseNetworkIconOverrideTypedArray(obtainTypedArray);
            obtainTypedArray.recycle();
            Integer num2 = parseNetworkIconOverrideTypedArray.get(str);
            i2 = 0;
            if (num2 != null) {
                i2 = num2.intValue();
            }
        }
        return i2;
    }
}