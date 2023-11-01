package com.android.systemui.qs.footer.ui.viewmodel;

import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsButtonViewModel.class */
public final class FooterActionsButtonViewModel {
    public final int backgroundColor;
    public final Icon icon;
    public final Integer iconTint;
    public final int id;
    public final Function1<Expandable, Unit> onClick;

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlin.jvm.functions.Function1<? super com.android.systemui.animation.Expandable, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public FooterActionsButtonViewModel(int i, Icon icon, Integer num, int i2, Function1<? super Expandable, Unit> function1) {
        this.id = i;
        this.icon = icon;
        this.iconTint = num;
        this.backgroundColor = i2;
        this.onClick = function1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FooterActionsButtonViewModel) {
            FooterActionsButtonViewModel footerActionsButtonViewModel = (FooterActionsButtonViewModel) obj;
            return this.id == footerActionsButtonViewModel.id && Intrinsics.areEqual(this.icon, footerActionsButtonViewModel.icon) && Intrinsics.areEqual(this.iconTint, footerActionsButtonViewModel.iconTint) && this.backgroundColor == footerActionsButtonViewModel.backgroundColor && Intrinsics.areEqual(this.onClick, footerActionsButtonViewModel.onClick);
        }
        return false;
    }

    public final int getBackgroundColor() {
        return this.backgroundColor;
    }

    public final Icon getIcon() {
        return this.icon;
    }

    public final Integer getIconTint() {
        return this.iconTint;
    }

    public final int getId() {
        return this.id;
    }

    public final Function1<Expandable, Unit> getOnClick() {
        return this.onClick;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.id);
        int hashCode2 = this.icon.hashCode();
        Integer num = this.iconTint;
        return (((((((hashCode * 31) + hashCode2) * 31) + (num == null ? 0 : num.hashCode())) * 31) + Integer.hashCode(this.backgroundColor)) * 31) + this.onClick.hashCode();
    }

    public String toString() {
        int i = this.id;
        Icon icon = this.icon;
        Integer num = this.iconTint;
        int i2 = this.backgroundColor;
        Function1<Expandable, Unit> function1 = this.onClick;
        return "FooterActionsButtonViewModel(id=" + i + ", icon=" + icon + ", iconTint=" + num + ", backgroundColor=" + i2 + ", onClick=" + function1 + ")";
    }
}