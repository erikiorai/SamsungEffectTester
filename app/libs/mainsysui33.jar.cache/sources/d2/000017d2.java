package com.android.systemui.flags;

import java.util.ArrayList;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagsCommonModule.class */
public interface FlagsCommonModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagsCommonModule$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final Map<Integer, Flag<?>> providesAllFlags() {
            Map<String, Flag<?>> knownFlags = FlagsFactory.INSTANCE.getKnownFlags();
            ArrayList arrayList = new ArrayList(knownFlags.size());
            for (Map.Entry<String, Flag<?>> entry : knownFlags.entrySet()) {
                arrayList.add(TuplesKt.to(Integer.valueOf(entry.getValue().getId()), entry.getValue()));
            }
            return MapsKt__MapsKt.toMap(arrayList);
        }
    }

    static Map<Integer, Flag<?>> providesAllFlags() {
        return Companion.providesAllFlags();
    }
}