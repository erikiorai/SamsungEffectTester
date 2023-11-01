package com.android.systemui.flags;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagsCommonModule_ProvidesAllFlagsFactory.class */
public final class FlagsCommonModule_ProvidesAllFlagsFactory implements Factory<Map<Integer, Flag<?>>> {

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagsCommonModule_ProvidesAllFlagsFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FlagsCommonModule_ProvidesAllFlagsFactory INSTANCE = new FlagsCommonModule_ProvidesAllFlagsFactory();
    }

    public static FlagsCommonModule_ProvidesAllFlagsFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Map<Integer, Flag<?>> providesAllFlags() {
        return (Map) Preconditions.checkNotNullFromProvides(FlagsCommonModule.providesAllFlags());
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Map<Integer, Flag<?>> get() {
        return providesAllFlags();
    }
}