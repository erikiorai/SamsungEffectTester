package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/DockTooltipView.class */
public class DockTooltipView extends BaseTooltipView {
    public final AccessibilityFloatingMenuView mAnchorView;

    public DockTooltipView(Context context, AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        super(context, accessibilityFloatingMenuView);
        this.mAnchorView = accessibilityFloatingMenuView;
        setDescription(getContext().getText(R$string.accessibility_floating_button_docking_tooltip));
    }

    @Override // com.android.systemui.accessibility.floatingmenu.BaseTooltipView
    public void hide() {
        super.hide();
        this.mAnchorView.stopTranslateXAnimation();
    }

    @Override // com.android.systemui.accessibility.floatingmenu.BaseTooltipView
    public void show() {
        super.show();
        this.mAnchorView.startTranslateXAnimation();
    }
}