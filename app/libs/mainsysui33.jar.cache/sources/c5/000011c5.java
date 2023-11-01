package com.android.systemui.biometrics;

import com.android.keyguard.logging.BiometricMessageDeferralLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/BiometricMessageDeferral.class */
public class BiometricMessageDeferral implements Dumpable {
    public final Map<Integer, Integer> acquiredInfoToFrequency = new HashMap();
    public final Map<Integer, String> acquiredInfoToHelpString = new HashMap();
    public final BiometricMessageDeferralLogger logBuffer;
    public final Set<Integer> messagesToDefer;
    public Integer mostFrequentAcquiredInfoToDefer;
    public final float threshold;
    public int totalFrames;

    public BiometricMessageDeferral(Set<Integer> set, float f, BiometricMessageDeferralLogger biometricMessageDeferralLogger, DumpManager dumpManager) {
        this.messagesToDefer = set;
        this.threshold = f;
        this.logBuffer = biometricMessageDeferralLogger;
        DumpManager.registerDumpable$default(dumpManager, getClass().getName(), this, null, 4, null);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        Set<Integer> set = this.messagesToDefer;
        printWriter.println("messagesToDefer=" + set);
        int i = this.totalFrames;
        printWriter.println("totalFrames=" + i);
        float f = this.threshold;
        printWriter.println("threshold=" + f);
    }

    public final CharSequence getDeferredMessage() {
        Integer num = this.mostFrequentAcquiredInfoToDefer;
        if (num != null) {
            int intValue = num.intValue();
            if (this.acquiredInfoToFrequency.getOrDefault(Integer.valueOf(intValue), 0).intValue() > this.threshold * this.totalFrames) {
                return this.acquiredInfoToHelpString.get(Integer.valueOf(intValue));
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0079, code lost:
        if (r0 > r0.getOrDefault(r0, 0).intValue()) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void processFrame(int i) {
        if (this.messagesToDefer.isEmpty()) {
            return;
        }
        this.totalFrames++;
        int intValue = this.acquiredInfoToFrequency.getOrDefault(Integer.valueOf(i), 0).intValue() + 1;
        this.acquiredInfoToFrequency.put(Integer.valueOf(i), Integer.valueOf(intValue));
        if (this.messagesToDefer.contains(Integer.valueOf(i))) {
            Integer num = this.mostFrequentAcquiredInfoToDefer;
            if (num != null) {
                Map<Integer, Integer> map = this.acquiredInfoToFrequency;
                Intrinsics.checkNotNull(num);
            }
            this.mostFrequentAcquiredInfoToDefer = Integer.valueOf(i);
        }
        BiometricMessageDeferralLogger biometricMessageDeferralLogger = this.logBuffer;
        int i2 = this.totalFrames;
        Integer num2 = this.mostFrequentAcquiredInfoToDefer;
        biometricMessageDeferralLogger.logFrameProcessed(i, i2, num2 != null ? num2.toString() : null);
    }

    public final void reset() {
        this.totalFrames = 0;
        this.mostFrequentAcquiredInfoToDefer = null;
        this.acquiredInfoToFrequency.clear();
        this.acquiredInfoToHelpString.clear();
        this.logBuffer.reset();
    }

    public final boolean shouldDefer(int i) {
        return this.messagesToDefer.contains(Integer.valueOf(i));
    }

    public final void updateMessage(int i, String str) {
        if (this.messagesToDefer.contains(Integer.valueOf(i)) && !Objects.equals(this.acquiredInfoToHelpString.get(Integer.valueOf(i)), str)) {
            this.logBuffer.logUpdateMessage(i, str);
            this.acquiredInfoToHelpString.put(Integer.valueOf(i), str);
        }
    }
}