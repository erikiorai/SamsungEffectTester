package com.android.systemui.keyguard.shared.quickaffordance;

import kotlin.NoWhenBranchMatchedException;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/quickaffordance/KeyguardQuickAffordancePosition.class */
public enum KeyguardQuickAffordancePosition {
    BOTTOM_START,
    BOTTOM_END;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/quickaffordance/KeyguardQuickAffordancePosition$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardQuickAffordancePosition.values().length];
            try {
                iArr[KeyguardQuickAffordancePosition.BOTTOM_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[KeyguardQuickAffordancePosition.BOTTOM_END.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final String toSlotId() {
        String str;
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1) {
            str = "bottom_start";
        } else if (i != 2) {
            throw new NoWhenBranchMatchedException();
        } else {
            str = "bottom_end";
        }
        return str;
    }
}