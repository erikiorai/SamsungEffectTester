package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsSecurityButtonViewModel.class */
public final class FooterActionsSecurityButtonViewModel {
    public final Icon icon;
    public final Function2<Context, Expandable, Unit> onClick;
    public final String text;

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: kotlin.jvm.functions.Function2<? super android.content.Context, ? super com.android.systemui.animation.Expandable, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public FooterActionsSecurityButtonViewModel(Icon icon, String str, Function2<? super Context, ? super Expandable, Unit> function2) {
        this.icon = icon;
        this.text = str;
        this.onClick = function2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FooterActionsSecurityButtonViewModel) {
            FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel = (FooterActionsSecurityButtonViewModel) obj;
            return Intrinsics.areEqual(this.icon, footerActionsSecurityButtonViewModel.icon) && Intrinsics.areEqual(this.text, footerActionsSecurityButtonViewModel.text) && Intrinsics.areEqual(this.onClick, footerActionsSecurityButtonViewModel.onClick);
        }
        return false;
    }

    public final Icon getIcon() {
        return this.icon;
    }

    public final Function2<Context, Expandable, Unit> getOnClick() {
        return this.onClick;
    }

    public final String getText() {
        return this.text;
    }

    public int hashCode() {
        int hashCode = this.icon.hashCode();
        int hashCode2 = this.text.hashCode();
        Function2<Context, Expandable, Unit> function2 = this.onClick;
        return (((hashCode * 31) + hashCode2) * 31) + (function2 == null ? 0 : function2.hashCode());
    }

    public String toString() {
        Icon icon = this.icon;
        String str = this.text;
        Function2<Context, Expandable, Unit> function2 = this.onClick;
        return "FooterActionsSecurityButtonViewModel(icon=" + icon + ", text=" + str + ", onClick=" + function2 + ")";
    }
}