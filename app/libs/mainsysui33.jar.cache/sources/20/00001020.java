package com.android.systemui.accessibility;

import android.content.Context;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/AccessibilityButtonTargetsObserver.class */
public class AccessibilityButtonTargetsObserver extends SecureSettingsContentObserver<TargetsChangedListener> {

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/AccessibilityButtonTargetsObserver$TargetsChangedListener.class */
    public interface TargetsChangedListener {
        void onAccessibilityButtonTargetsChanged(String str);
    }

    public AccessibilityButtonTargetsObserver(Context context) {
        super(context, "accessibility_button_targets");
    }

    public String getCurrentAccessibilityButtonTargets() {
        return getSettingsValue();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.accessibility.SecureSettingsContentObserver
    public void onValueChanged(TargetsChangedListener targetsChangedListener, String str) {
        targetsChangedListener.onAccessibilityButtonTargetsChanged(str);
    }
}