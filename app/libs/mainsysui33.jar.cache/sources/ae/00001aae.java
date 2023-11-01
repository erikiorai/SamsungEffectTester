package com.android.systemui.keyguard.domain.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/quickaffordance/KeyguardQuickAffordanceRegistry.class */
public interface KeyguardQuickAffordanceRegistry<T extends KeyguardQuickAffordanceConfig> {
    T get(String str);

    List<T> getAll(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition);
}