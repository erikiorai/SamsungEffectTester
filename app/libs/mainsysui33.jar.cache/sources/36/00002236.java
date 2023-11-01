package com.android.systemui.qs.footer.ui.viewmodel;

import com.android.systemui.animation.Expandable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsForegroundServicesButtonViewModel.class */
public final class FooterActionsForegroundServicesButtonViewModel {
    public final boolean displayText;
    public final int foregroundServicesCount;
    public final boolean hasNewChanges;
    public final Function1<Expandable, Unit> onClick;
    public final String text;

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlin.jvm.functions.Function1<? super com.android.systemui.animation.Expandable, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public FooterActionsForegroundServicesButtonViewModel(int i, String str, boolean z, boolean z2, Function1<? super Expandable, Unit> function1) {
        this.foregroundServicesCount = i;
        this.text = str;
        this.displayText = z;
        this.hasNewChanges = z2;
        this.onClick = function1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FooterActionsForegroundServicesButtonViewModel) {
            FooterActionsForegroundServicesButtonViewModel footerActionsForegroundServicesButtonViewModel = (FooterActionsForegroundServicesButtonViewModel) obj;
            return this.foregroundServicesCount == footerActionsForegroundServicesButtonViewModel.foregroundServicesCount && Intrinsics.areEqual(this.text, footerActionsForegroundServicesButtonViewModel.text) && this.displayText == footerActionsForegroundServicesButtonViewModel.displayText && this.hasNewChanges == footerActionsForegroundServicesButtonViewModel.hasNewChanges && Intrinsics.areEqual(this.onClick, footerActionsForegroundServicesButtonViewModel.onClick);
        }
        return false;
    }

    public final boolean getDisplayText() {
        return this.displayText;
    }

    public final int getForegroundServicesCount() {
        return this.foregroundServicesCount;
    }

    public final boolean getHasNewChanges() {
        return this.hasNewChanges;
    }

    public final Function1<Expandable, Unit> getOnClick() {
        return this.onClick;
    }

    public final String getText() {
        return this.text;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.foregroundServicesCount);
        int hashCode2 = this.text.hashCode();
        boolean z = this.displayText;
        int i = 1;
        int i2 = z ? 1 : 0;
        if (z) {
            i2 = 1;
        }
        boolean z2 = this.hasNewChanges;
        if (!z2) {
            i = z2 ? 1 : 0;
        }
        return (((((((hashCode * 31) + hashCode2) * 31) + i2) * 31) + i) * 31) + this.onClick.hashCode();
    }

    public String toString() {
        int i = this.foregroundServicesCount;
        String str = this.text;
        boolean z = this.displayText;
        boolean z2 = this.hasNewChanges;
        Function1<Expandable, Unit> function1 = this.onClick;
        return "FooterActionsForegroundServicesButtonViewModel(foregroundServicesCount=" + i + ", text=" + str + ", displayText=" + z + ", hasNewChanges=" + z2 + ", onClick=" + function1 + ")";
    }
}