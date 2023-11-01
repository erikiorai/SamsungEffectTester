package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R$string;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUserSwitcherAnchor.class */
public final class KeyguardUserSwitcherAnchor extends LinearLayout {
    public KeyguardUserSwitcherAnchor(Context context) {
        this(context, null, 2, null);
    }

    public KeyguardUserSwitcherAnchor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public /* synthetic */ KeyguardUserSwitcherAnchor(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // android.view.View
    public AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        AccessibilityNodeInfoCompat.wrap(createAccessibilityNodeInfo).setRoleDescription(getContext().getString(R$string.accessibility_multi_user_list_switcher));
        return createAccessibilityNodeInfo;
    }
}