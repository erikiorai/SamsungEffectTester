package com.android.systemui.keyguard.data.quickaffordance;

import java.util.List;
import java.util.Map;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceSelectionManager.class */
public interface KeyguardQuickAffordanceSelectionManager {
    Map<String, List<String>> getSelections();

    /* renamed from: getSelections */
    Flow<Map<String, List<String>>> mo2945getSelections();

    void setSelections(String str, List<String> list);
}