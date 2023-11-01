package com.android.systemui.qs;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSFooter.class */
public interface QSFooter {
    default void disable(int i, int i2, boolean z) {
    }

    void setExpanded(boolean z);

    void setExpansion(float f);

    void setKeyguardShowing(boolean z);

    void setVisibility(int i);
}