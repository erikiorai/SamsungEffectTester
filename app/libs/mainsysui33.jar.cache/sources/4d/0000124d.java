package com.android.systemui.biometrics.udfps;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/TouchProcessorResult.class */
public abstract class TouchProcessorResult {

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/TouchProcessorResult$Failure.class */
    public static final class Failure extends TouchProcessorResult {
        public final String reason;

        public Failure() {
            this(null, 1, null);
        }

        public Failure(String str) {
            super(null);
            this.reason = str;
        }

        public /* synthetic */ Failure(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? "" : str);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Failure) && Intrinsics.areEqual(this.reason, ((Failure) obj).reason);
        }

        public final String getReason() {
            return this.reason;
        }

        public int hashCode() {
            return this.reason.hashCode();
        }

        public String toString() {
            String str = this.reason;
            return "Failure(reason=" + str + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/TouchProcessorResult$ProcessedTouch.class */
    public static final class ProcessedTouch extends TouchProcessorResult {
        public final InteractionEvent event;
        public final int pointerOnSensorId;
        public final NormalizedTouchData touchData;

        public ProcessedTouch(InteractionEvent interactionEvent, int i, NormalizedTouchData normalizedTouchData) {
            super(null);
            this.event = interactionEvent;
            this.pointerOnSensorId = i;
            this.touchData = normalizedTouchData;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ProcessedTouch) {
                ProcessedTouch processedTouch = (ProcessedTouch) obj;
                return this.event == processedTouch.event && this.pointerOnSensorId == processedTouch.pointerOnSensorId && Intrinsics.areEqual(this.touchData, processedTouch.touchData);
            }
            return false;
        }

        public final InteractionEvent getEvent() {
            return this.event;
        }

        public final int getPointerOnSensorId() {
            return this.pointerOnSensorId;
        }

        public final NormalizedTouchData getTouchData() {
            return this.touchData;
        }

        public int hashCode() {
            return (((this.event.hashCode() * 31) + Integer.hashCode(this.pointerOnSensorId)) * 31) + this.touchData.hashCode();
        }

        public String toString() {
            InteractionEvent interactionEvent = this.event;
            int i = this.pointerOnSensorId;
            NormalizedTouchData normalizedTouchData = this.touchData;
            return "ProcessedTouch(event=" + interactionEvent + ", pointerOnSensorId=" + i + ", touchData=" + normalizedTouchData + ")";
        }
    }

    public TouchProcessorResult() {
    }

    public /* synthetic */ TouchProcessorResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}