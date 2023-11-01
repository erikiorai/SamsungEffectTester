package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.View;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.systemui.R$string;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/MigrationTooltipView.class */
public class MigrationTooltipView extends BaseTooltipView {
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.MigrationTooltipView$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$XoqH39sHIoB_O9G9S4RFly-FSs4 */
    public static /* synthetic */ void m1416$r8$lambda$XoqH39sHIoB_O9G9S4RFlyFSs4(MigrationTooltipView migrationTooltipView, Intent intent, View view) {
        migrationTooltipView.lambda$new$0(intent, view);
    }

    public MigrationTooltipView(Context context, AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        super(context, accessibilityFloatingMenuView);
        final Intent intent = new Intent("android.settings.ACCESSIBILITY_DETAILS_SETTINGS");
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", AccessibilityShortcutController.ACCESSIBILITY_BUTTON_COMPONENT_NAME.flattenToShortString());
        setDescription(AnnotationLinkSpan.linkify(getContext().getText(R$string.accessibility_floating_button_migration_tooltip), new AnnotationLinkSpan.LinkInfo("link", new View.OnClickListener() { // from class: com.android.systemui.accessibility.floatingmenu.MigrationTooltipView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MigrationTooltipView.m1416$r8$lambda$XoqH39sHIoB_O9G9S4RFlyFSs4(MigrationTooltipView.this, intent, view);
            }
        })));
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public /* synthetic */ void lambda$new$0(Intent intent, View view) {
        getContext().startActivity(intent);
        hide();
    }
}