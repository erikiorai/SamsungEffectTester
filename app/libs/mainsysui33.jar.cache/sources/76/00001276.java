package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.util.IndentingPrintWriter;
import android.util.SparseSetArray;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.PrintWriter;
import kotlin.Unit;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/PendingRemovalStore.class */
public final class PendingRemovalStore implements Dumpable {
    public final BroadcastDispatcherLogger logger;
    public final SparseSetArray<BroadcastReceiver> pendingRemoval = new SparseSetArray<>();

    public PendingRemovalStore(BroadcastDispatcherLogger broadcastDispatcherLogger) {
        this.logger = broadcastDispatcherLogger;
    }

    public final void clearPendingRemoval(BroadcastReceiver broadcastReceiver, int i) {
        synchronized (this.pendingRemoval) {
            this.pendingRemoval.remove(i, broadcastReceiver);
        }
        this.logger.logClearedAfterRemoval(i, broadcastReceiver);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        synchronized (this.pendingRemoval) {
            if (printWriter instanceof IndentingPrintWriter) {
                ((IndentingPrintWriter) printWriter).increaseIndent();
            }
            int size = this.pendingRemoval.size();
            for (int i = 0; i < size; i++) {
                int keyAt = this.pendingRemoval.keyAt(i);
                printWriter.print(keyAt);
                printWriter.print("->");
                printWriter.println(this.pendingRemoval.get(keyAt));
            }
            if (printWriter instanceof IndentingPrintWriter) {
                ((IndentingPrintWriter) printWriter).decreaseIndent();
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean isPendingRemoval(BroadcastReceiver broadcastReceiver, int i) {
        boolean z;
        synchronized (this.pendingRemoval) {
            if (!this.pendingRemoval.contains(i, broadcastReceiver)) {
                if (!this.pendingRemoval.contains(-1, broadcastReceiver)) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public final void tagForRemoval(BroadcastReceiver broadcastReceiver, int i) {
        this.logger.logTagForRemoval(i, broadcastReceiver);
        synchronized (this.pendingRemoval) {
            this.pendingRemoval.add(i, broadcastReceiver);
        }
    }
}