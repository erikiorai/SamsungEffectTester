package com.android.systemui.keyguard.ui.viewmodel;

import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardQuickAffordanceViewModel.class */
public final class KeyguardQuickAffordanceViewModel {
    public final boolean animateReveal;
    public final String configKey;
    public final Icon icon;
    public final boolean isActivated;
    public final boolean isClickable;
    public final boolean isSelected;
    public final boolean isVisible;
    public final Function1<OnClickedParameters, Unit> onClicked;
    public final boolean useLongPress;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardQuickAffordanceViewModel$OnClickedParameters.class */
    public static final class OnClickedParameters {
        public final String configKey;
        public final Expandable expandable;

        public OnClickedParameters(String str, Expandable expandable) {
            this.configKey = str;
            this.expandable = expandable;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof OnClickedParameters) {
                OnClickedParameters onClickedParameters = (OnClickedParameters) obj;
                return Intrinsics.areEqual(this.configKey, onClickedParameters.configKey) && Intrinsics.areEqual(this.expandable, onClickedParameters.expandable);
            }
            return false;
        }

        public final String getConfigKey() {
            return this.configKey;
        }

        public final Expandable getExpandable() {
            return this.expandable;
        }

        public int hashCode() {
            int hashCode = this.configKey.hashCode();
            Expandable expandable = this.expandable;
            return (hashCode * 31) + (expandable == null ? 0 : expandable.hashCode());
        }

        public String toString() {
            String str = this.configKey;
            Expandable expandable = this.expandable;
            return "OnClickedParameters(configKey=" + str + ", expandable=" + expandable + ")";
        }
    }

    public KeyguardQuickAffordanceViewModel() {
        this(null, false, false, null, null, false, false, false, false, 511, null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlin.jvm.functions.Function1<? super com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel$OnClickedParameters, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public KeyguardQuickAffordanceViewModel(String str, boolean z, boolean z2, Icon icon, Function1<? super OnClickedParameters, Unit> function1, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.configKey = str;
        this.isVisible = z;
        this.animateReveal = z2;
        this.icon = icon;
        this.onClicked = function1;
        this.isClickable = z3;
        this.isActivated = z4;
        this.isSelected = z5;
        this.useLongPress = z6;
    }

    public /* synthetic */ KeyguardQuickAffordanceViewModel(String str, boolean z, boolean z2, Icon icon, Function1 function1, boolean z3, boolean z4, boolean z5, boolean z6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? new Icon.Resource(0, null) : icon, (i & 16) != 0 ? new Function1<OnClickedParameters, Unit>() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel.1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((OnClickedParameters) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(OnClickedParameters onClickedParameters) {
            }
        } : function1, (i & 32) != 0 ? false : z3, (i & 64) != 0 ? false : z4, (i & RecyclerView.ViewHolder.FLAG_IGNORE) != 0 ? false : z5, (i & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) != 0 ? false : z6);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof KeyguardQuickAffordanceViewModel) {
            KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel = (KeyguardQuickAffordanceViewModel) obj;
            return Intrinsics.areEqual(this.configKey, keyguardQuickAffordanceViewModel.configKey) && this.isVisible == keyguardQuickAffordanceViewModel.isVisible && this.animateReveal == keyguardQuickAffordanceViewModel.animateReveal && Intrinsics.areEqual(this.icon, keyguardQuickAffordanceViewModel.icon) && Intrinsics.areEqual(this.onClicked, keyguardQuickAffordanceViewModel.onClicked) && this.isClickable == keyguardQuickAffordanceViewModel.isClickable && this.isActivated == keyguardQuickAffordanceViewModel.isActivated && this.isSelected == keyguardQuickAffordanceViewModel.isSelected && this.useLongPress == keyguardQuickAffordanceViewModel.useLongPress;
        }
        return false;
    }

    public final boolean getAnimateReveal() {
        return this.animateReveal;
    }

    public final String getConfigKey() {
        return this.configKey;
    }

    public final Icon getIcon() {
        return this.icon;
    }

    public final Function1<OnClickedParameters, Unit> getOnClicked() {
        return this.onClicked;
    }

    public final boolean getUseLongPress() {
        return this.useLongPress;
    }

    public int hashCode() {
        String str = this.configKey;
        int hashCode = str == null ? 0 : str.hashCode();
        boolean z = this.isVisible;
        int i = 1;
        int i2 = z ? 1 : 0;
        if (z) {
            i2 = 1;
        }
        boolean z2 = this.animateReveal;
        int i3 = z2 ? 1 : 0;
        if (z2) {
            i3 = 1;
        }
        int hashCode2 = this.icon.hashCode();
        int hashCode3 = this.onClicked.hashCode();
        boolean z3 = this.isClickable;
        int i4 = z3 ? 1 : 0;
        if (z3) {
            i4 = 1;
        }
        boolean z4 = this.isActivated;
        int i5 = z4 ? 1 : 0;
        if (z4) {
            i5 = 1;
        }
        boolean z5 = this.isSelected;
        int i6 = z5 ? 1 : 0;
        if (z5) {
            i6 = 1;
        }
        boolean z6 = this.useLongPress;
        if (!z6) {
            i = z6 ? 1 : 0;
        }
        return (((((((((((((((hashCode * 31) + i2) * 31) + i3) * 31) + hashCode2) * 31) + hashCode3) * 31) + i4) * 31) + i5) * 31) + i6) * 31) + i;
    }

    public final boolean isActivated() {
        return this.isActivated;
    }

    public final boolean isClickable() {
        return this.isClickable;
    }

    public final boolean isSelected() {
        return this.isSelected;
    }

    public final boolean isVisible() {
        return this.isVisible;
    }

    public String toString() {
        String str = this.configKey;
        boolean z = this.isVisible;
        boolean z2 = this.animateReveal;
        Icon icon = this.icon;
        Function1<OnClickedParameters, Unit> function1 = this.onClicked;
        boolean z3 = this.isClickable;
        boolean z4 = this.isActivated;
        boolean z5 = this.isSelected;
        boolean z6 = this.useLongPress;
        return "KeyguardQuickAffordanceViewModel(configKey=" + str + ", isVisible=" + z + ", animateReveal=" + z2 + ", icon=" + icon + ", onClicked=" + function1 + ", isClickable=" + z3 + ", isActivated=" + z4 + ", isSelected=" + z5 + ", useLongPress=" + z6 + ")";
    }
}