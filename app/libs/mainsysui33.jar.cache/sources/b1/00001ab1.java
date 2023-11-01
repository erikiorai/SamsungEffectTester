package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/AnimationParams.class */
public final class AnimationParams {
    public final long duration;
    public final long startTime;

    public AnimationParams(long j, long j2) {
        this.startTime = j;
        this.duration = j2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ AnimationParams(long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, null);
        if ((i & 1) != 0) {
            Duration.Companion companion = Duration.Companion;
            j = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
        }
    }

    public /* synthetic */ AnimationParams(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AnimationParams) {
            AnimationParams animationParams = (AnimationParams) obj;
            return Duration.equals-impl0(this.startTime, animationParams.startTime) && Duration.equals-impl0(this.duration, animationParams.duration);
        }
        return false;
    }

    /* renamed from: getDuration-UwyO8pc  reason: not valid java name */
    public final long m3058getDurationUwyO8pc() {
        return this.duration;
    }

    /* renamed from: getStartTime-UwyO8pc  reason: not valid java name */
    public final long m3059getStartTimeUwyO8pc() {
        return this.startTime;
    }

    public int hashCode() {
        return (Duration.hashCode-impl(this.startTime) * 31) + Duration.hashCode-impl(this.duration);
    }

    public String toString() {
        String str = Duration.toString-impl(this.startTime);
        String str2 = Duration.toString-impl(this.duration);
        return "AnimationParams(startTime=" + str + ", duration=" + str2 + ")";
    }
}