package com.android.systemui.keyguard.shared.model;

import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/KeyguardBouncerModel.class */
public final class KeyguardBouncerModel {
    public final CharSequence errorMessage;
    public final float expansionAmount;
    public final int promptReason;

    public KeyguardBouncerModel() {
        this(0, null, ActionBarShadowController.ELEVATION_LOW, 7, null);
    }

    public KeyguardBouncerModel(int i, CharSequence charSequence, float f) {
        this.promptReason = i;
        this.errorMessage = charSequence;
        this.expansionAmount = f;
    }

    public /* synthetic */ KeyguardBouncerModel(int i, CharSequence charSequence, float f, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? null : charSequence, (i2 & 4) != 0 ? 0.0f : f);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof KeyguardBouncerModel) {
            KeyguardBouncerModel keyguardBouncerModel = (KeyguardBouncerModel) obj;
            return this.promptReason == keyguardBouncerModel.promptReason && Intrinsics.areEqual(this.errorMessage, keyguardBouncerModel.errorMessage) && Float.compare(this.expansionAmount, keyguardBouncerModel.expansionAmount) == 0;
        }
        return false;
    }

    public final CharSequence getErrorMessage() {
        return this.errorMessage;
    }

    public final float getExpansionAmount() {
        return this.expansionAmount;
    }

    public final int getPromptReason() {
        return this.promptReason;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.promptReason);
        CharSequence charSequence = this.errorMessage;
        return (((hashCode * 31) + (charSequence == null ? 0 : charSequence.hashCode())) * 31) + Float.hashCode(this.expansionAmount);
    }

    public String toString() {
        int i = this.promptReason;
        CharSequence charSequence = this.errorMessage;
        float f = this.expansionAmount;
        return "KeyguardBouncerModel(promptReason=" + i + ", errorMessage=" + ((Object) charSequence) + ", expansionAmount=" + f + ")";
    }
}