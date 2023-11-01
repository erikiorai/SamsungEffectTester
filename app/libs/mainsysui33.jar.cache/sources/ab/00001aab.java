package com.android.systemui.keyguard.domain.model;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/model/KeyguardQuickAffordanceModel.class */
public abstract class KeyguardQuickAffordanceModel {

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/model/KeyguardQuickAffordanceModel$Hidden.class */
    public static final class Hidden extends KeyguardQuickAffordanceModel {
        public static final Hidden INSTANCE = new Hidden();

        public Hidden() {
            super(null);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/model/KeyguardQuickAffordanceModel$Visible.class */
    public static final class Visible extends KeyguardQuickAffordanceModel {
        public final ActivationState activationState;
        public final String configKey;
        public final Icon icon;

        public Visible(String str, Icon icon, ActivationState activationState) {
            super(null);
            this.configKey = str;
            this.icon = icon;
            this.activationState = activationState;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Visible) {
                Visible visible = (Visible) obj;
                return Intrinsics.areEqual(this.configKey, visible.configKey) && Intrinsics.areEqual(this.icon, visible.icon) && Intrinsics.areEqual(this.activationState, visible.activationState);
            }
            return false;
        }

        public final ActivationState getActivationState() {
            return this.activationState;
        }

        public final String getConfigKey() {
            return this.configKey;
        }

        public final Icon getIcon() {
            return this.icon;
        }

        public int hashCode() {
            return (((this.configKey.hashCode() * 31) + this.icon.hashCode()) * 31) + this.activationState.hashCode();
        }

        public String toString() {
            String str = this.configKey;
            Icon icon = this.icon;
            ActivationState activationState = this.activationState;
            return "Visible(configKey=" + str + ", icon=" + icon + ", activationState=" + activationState + ")";
        }
    }

    public KeyguardQuickAffordanceModel() {
    }

    public /* synthetic */ KeyguardQuickAffordanceModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}