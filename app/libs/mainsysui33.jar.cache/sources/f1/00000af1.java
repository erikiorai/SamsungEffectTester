package com.android.app.motiontool;

import com.android.app.viewcapture.data.nano.ExportedData;
import com.android.app.viewcapture.data.nano.FrameData;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/TraceMetadata.class */
public final class TraceMetadata {
    public long lastPolledTime;
    public Function0<Unit> stopTrace;
    public final String windowId;

    public TraceMetadata(String str, long j, Function0<Unit> function0) {
        this.windowId = str;
        this.lastPolledTime = j;
        this.stopTrace = function0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TraceMetadata) {
            TraceMetadata traceMetadata = (TraceMetadata) obj;
            return Intrinsics.areEqual(this.windowId, traceMetadata.windowId) && this.lastPolledTime == traceMetadata.lastPolledTime && Intrinsics.areEqual(this.stopTrace, traceMetadata.stopTrace);
        }
        return false;
    }

    public final long getLastPolledTime() {
        return this.lastPolledTime;
    }

    public final Function0<Unit> getStopTrace() {
        return this.stopTrace;
    }

    public final String getWindowId() {
        return this.windowId;
    }

    public int hashCode() {
        return (((this.windowId.hashCode() * 31) + Long.hashCode(this.lastPolledTime)) * 31) + this.stopTrace.hashCode();
    }

    public String toString() {
        String str = this.windowId;
        long j = this.lastPolledTime;
        Function0<Unit> function0 = this.stopTrace;
        return "TraceMetadata(windowId=" + str + ", lastPolledTime=" + j + ", stopTrace=" + function0 + ")";
    }

    public final void updateLastPolledTime(ExportedData exportedData) {
        FrameData[] frameDataArr;
        Long valueOf;
        if (exportedData == null || (frameDataArr = exportedData.frameData) == null) {
            return;
        }
        if (frameDataArr.length == 0) {
            valueOf = null;
        } else {
            valueOf = Long.valueOf(frameDataArr[0].timestamp);
            IntIterator it = new IntRange(1, ArraysKt___ArraysKt.getLastIndex(frameDataArr)).iterator();
            while (it.hasNext()) {
                Long valueOf2 = Long.valueOf(frameDataArr[it.nextInt()].timestamp);
                if (valueOf.compareTo(valueOf2) < 0) {
                    valueOf = valueOf2;
                }
            }
        }
        Long l = valueOf;
        if (l != null) {
            this.lastPolledTime = l.longValue();
        }
    }
}