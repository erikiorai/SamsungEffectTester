package com.android.systemui.keyguard.shared.model;

import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/TransitionStep.class */
public final class TransitionStep {
    public final KeyguardState from;
    public final String ownerName;
    public final KeyguardState to;
    public final TransitionState transitionState;
    public final float value;

    public TransitionStep() {
        this(null, null, ActionBarShadowController.ELEVATION_LOW, null, null, 31, null);
    }

    public TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState, String str) {
        this.from = keyguardState;
        this.to = keyguardState2;
        this.value = f;
        this.transitionState = transitionState;
        this.ownerName = str;
    }

    public /* synthetic */ TransitionStep(KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? KeyguardState.OFF : keyguardState, (i & 2) != 0 ? KeyguardState.OFF : keyguardState2, (i & 4) != 0 ? 0.0f : f, (i & 8) != 0 ? TransitionState.FINISHED : transitionState, (i & 16) != 0 ? "" : str);
    }

    public TransitionStep(TransitionInfo transitionInfo, float f, TransitionState transitionState) {
        this(transitionInfo.getFrom(), transitionInfo.getTo(), f, transitionState, transitionInfo.getOwnerName());
    }

    public static /* synthetic */ TransitionStep copy$default(TransitionStep transitionStep, KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            keyguardState = transitionStep.from;
        }
        if ((i & 2) != 0) {
            keyguardState2 = transitionStep.to;
        }
        if ((i & 4) != 0) {
            f = transitionStep.value;
        }
        if ((i & 8) != 0) {
            transitionState = transitionStep.transitionState;
        }
        if ((i & 16) != 0) {
            str = transitionStep.ownerName;
        }
        return transitionStep.copy(keyguardState, keyguardState2, f, transitionState, str);
    }

    public final TransitionStep copy(KeyguardState keyguardState, KeyguardState keyguardState2, float f, TransitionState transitionState, String str) {
        return new TransitionStep(keyguardState, keyguardState2, f, transitionState, str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TransitionStep) {
            TransitionStep transitionStep = (TransitionStep) obj;
            return this.from == transitionStep.from && this.to == transitionStep.to && Float.compare(this.value, transitionStep.value) == 0 && this.transitionState == transitionStep.transitionState && Intrinsics.areEqual(this.ownerName, transitionStep.ownerName);
        }
        return false;
    }

    public final KeyguardState getFrom() {
        return this.from;
    }

    public final KeyguardState getTo() {
        return this.to;
    }

    public final TransitionState getTransitionState() {
        return this.transitionState;
    }

    public final float getValue() {
        return this.value;
    }

    public int hashCode() {
        return (((((((this.from.hashCode() * 31) + this.to.hashCode()) * 31) + Float.hashCode(this.value)) * 31) + this.transitionState.hashCode()) * 31) + this.ownerName.hashCode();
    }

    public String toString() {
        KeyguardState keyguardState = this.from;
        KeyguardState keyguardState2 = this.to;
        float f = this.value;
        TransitionState transitionState = this.transitionState;
        String str = this.ownerName;
        return "TransitionStep(from=" + keyguardState + ", to=" + keyguardState2 + ", value=" + f + ", transitionState=" + transitionState + ", ownerName=" + str + ")";
    }
}