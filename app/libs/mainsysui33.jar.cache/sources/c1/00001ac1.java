package com.android.systemui.keyguard.shared.model;

import android.animation.ValueAnimator;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/TransitionInfo.class */
public final class TransitionInfo {
    public final ValueAnimator animator;
    public final KeyguardState from;
    public final String ownerName;
    public final KeyguardState to;

    public TransitionInfo(String str, KeyguardState keyguardState, KeyguardState keyguardState2, ValueAnimator valueAnimator) {
        this.ownerName = str;
        this.from = keyguardState;
        this.to = keyguardState2;
        this.animator = valueAnimator;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TransitionInfo) {
            TransitionInfo transitionInfo = (TransitionInfo) obj;
            return Intrinsics.areEqual(this.ownerName, transitionInfo.ownerName) && this.from == transitionInfo.from && this.to == transitionInfo.to && Intrinsics.areEqual(this.animator, transitionInfo.animator);
        }
        return false;
    }

    public final ValueAnimator getAnimator() {
        return this.animator;
    }

    public final KeyguardState getFrom() {
        return this.from;
    }

    public final String getOwnerName() {
        return this.ownerName;
    }

    public final KeyguardState getTo() {
        return this.to;
    }

    public int hashCode() {
        int hashCode = this.ownerName.hashCode();
        int hashCode2 = this.from.hashCode();
        int hashCode3 = this.to.hashCode();
        ValueAnimator valueAnimator = this.animator;
        return (((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + (valueAnimator == null ? 0 : valueAnimator.hashCode());
    }

    public String toString() {
        String str = this.ownerName;
        KeyguardState keyguardState = this.from;
        KeyguardState keyguardState2 = this.to;
        String str2 = this.animator != null ? "animated" : "manual";
        return "TransitionInfo(ownerName=" + str + ", from=" + keyguardState + ", to=" + keyguardState2 + ", " + str2 + ")";
    }
}