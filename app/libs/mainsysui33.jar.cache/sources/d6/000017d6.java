package com.android.systemui.flags;

import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagsFactory.class */
public final class FlagsFactory {
    public static final FlagsFactory INSTANCE = new FlagsFactory();
    public static final Map<String, Flag<?>> flagMap = new LinkedHashMap();

    public static /* synthetic */ ReleasedFlag releasedFlag$default(FlagsFactory flagsFactory, int i, String str, String str2, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str2 = "systemui";
        }
        if ((i2 & 8) != 0) {
            z = false;
        }
        return flagsFactory.releasedFlag(i, str, str2, z);
    }

    public static /* synthetic */ ResourceBooleanFlag resourceBooleanFlag$default(FlagsFactory flagsFactory, int i, int i2, String str, String str2, boolean z, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            str2 = "systemui";
        }
        if ((i3 & 16) != 0) {
            z = false;
        }
        return flagsFactory.resourceBooleanFlag(i, i2, str, str2, z);
    }

    public static /* synthetic */ SysPropBooleanFlag sysPropBooleanFlag$default(FlagsFactory flagsFactory, int i, String str, String str2, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str2 = "systemui";
        }
        if ((i2 & 8) != 0) {
            z = false;
        }
        return flagsFactory.sysPropBooleanFlag(i, str, str2, z);
    }

    public static /* synthetic */ UnreleasedFlag unreleasedFlag$default(FlagsFactory flagsFactory, int i, String str, String str2, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str2 = "systemui";
        }
        if ((i2 & 8) != 0) {
            z = false;
        }
        return flagsFactory.unreleasedFlag(i, str, str2, z);
    }

    public final void checkForDupesAndAdd(Flag<?> flag) {
        Map<String, Flag<?>> map = flagMap;
        if (map.containsKey(flag.getName())) {
            throw new IllegalArgumentException("Name {flag.name} is already registered");
        }
        for (Map.Entry<String, Flag<?>> entry : map.entrySet()) {
            if (entry.getValue().getId() == flag.getId()) {
                throw new IllegalArgumentException("Name {flag.id} is already registered");
            }
        }
        flagMap.put(flag.getName(), flag);
    }

    public final Map<String, Flag<?>> getKnownFlags() {
        Map<String, Flag<?>> map = flagMap;
        map.containsKey(Flags.TEAMFOOD.getName());
        return map;
    }

    public final ReleasedFlag releasedFlag(int i, String str, String str2, boolean z) {
        ReleasedFlag releasedFlag = new ReleasedFlag(i, str, str2, z, false, 16, null);
        INSTANCE.checkForDupesAndAdd(releasedFlag);
        return releasedFlag;
    }

    public final ResourceBooleanFlag resourceBooleanFlag(int i, int i2, String str, String str2, boolean z) {
        ResourceBooleanFlag resourceBooleanFlag = new ResourceBooleanFlag(i, str, str2, i2, z);
        INSTANCE.checkForDupesAndAdd(resourceBooleanFlag);
        return resourceBooleanFlag;
    }

    public final SysPropBooleanFlag sysPropBooleanFlag(int i, String str, String str2, boolean z) {
        SysPropBooleanFlag sysPropBooleanFlag = new SysPropBooleanFlag(i, str, "systemui", z);
        INSTANCE.checkForDupesAndAdd(sysPropBooleanFlag);
        return sysPropBooleanFlag;
    }

    public final UnreleasedFlag unreleasedFlag(int i, String str, String str2, boolean z) {
        UnreleasedFlag unreleasedFlag = new UnreleasedFlag(i, str, str2, z, false, 16, null);
        INSTANCE.checkForDupesAndAdd(unreleasedFlag);
        return unreleasedFlag;
    }
}