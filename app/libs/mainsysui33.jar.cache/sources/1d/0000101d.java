package com.android.systemui.accessibility;

import android.content.Context;
import android.util.Log;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/AccessibilityButtonModeObserver.class */
public class AccessibilityButtonModeObserver extends SecureSettingsContentObserver<ModeChangedListener> {

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/AccessibilityButtonModeObserver$ModeChangedListener.class */
    public interface ModeChangedListener {
        void onAccessibilityButtonModeChanged(int i);
    }

    public AccessibilityButtonModeObserver(Context context) {
        super(context, "accessibility_button_mode");
    }

    public int getCurrentAccessibilityButtonMode() {
        return parseAccessibilityButtonMode(getSettingsValue());
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.accessibility.SecureSettingsContentObserver
    public void onValueChanged(ModeChangedListener modeChangedListener, String str) {
        modeChangedListener.onAccessibilityButtonModeChanged(parseAccessibilityButtonMode(str));
    }

    public final int parseAccessibilityButtonMode(String str) {
        int i;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.e("A11yButtonModeObserver", "Invalid string for  " + e);
            i = 0;
        }
        return i;
    }
}