package com.android.systemui.dump;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/LogBufferFreezer.class */
public final class LogBufferFreezer {
    public final DumpManager dumpManager;
    public final DelayableExecutor executor;
    public final long freezeDuration;
    public Runnable pendingToken;

    public LogBufferFreezer(DumpManager dumpManager, DelayableExecutor delayableExecutor) {
        this(dumpManager, delayableExecutor, TimeUnit.MINUTES.toMillis(5L));
    }

    public LogBufferFreezer(DumpManager dumpManager, DelayableExecutor delayableExecutor, long j) {
        this.dumpManager = dumpManager;
        this.executor = delayableExecutor;
        this.freezeDuration = j;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dump.LogBufferFreezer$onBugreportStarted$1.run():void] */
    public static final /* synthetic */ DumpManager access$getDumpManager$p(LogBufferFreezer logBufferFreezer) {
        return logBufferFreezer.dumpManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dump.LogBufferFreezer$attach$1.onReceive(android.content.Context, android.content.Intent):void] */
    public static final /* synthetic */ void access$onBugreportStarted(LogBufferFreezer logBufferFreezer) {
        logBufferFreezer.onBugreportStarted();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dump.LogBufferFreezer$onBugreportStarted$1.run():void] */
    public static final /* synthetic */ void access$setPendingToken$p(LogBufferFreezer logBufferFreezer, Runnable runnable) {
        logBufferFreezer.pendingToken = runnable;
    }

    public final void attach(BroadcastDispatcher broadcastDispatcher) {
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.dump.LogBufferFreezer$attach$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                LogBufferFreezer.access$onBugreportStarted(LogBufferFreezer.this);
            }
        }, new IntentFilter("com.android.internal.intent.action.BUGREPORT_STARTED"), this.executor, UserHandle.ALL, 0, null, 48, null);
    }

    public final void onBugreportStarted() {
        Runnable runnable = this.pendingToken;
        if (runnable != null) {
            runnable.run();
        }
        Log.i("LogBufferFreezer", "Freezing log buffers");
        this.dumpManager.freezeBuffers();
        this.pendingToken = this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.dump.LogBufferFreezer$onBugreportStarted$1
            @Override // java.lang.Runnable
            public final void run() {
                Log.i("LogBufferFreezer", "Unfreezing log buffers");
                LogBufferFreezer.access$setPendingToken$p(LogBufferFreezer.this, null);
                LogBufferFreezer.access$getDumpManager$p(LogBufferFreezer.this).unfreezeBuffers();
            }
        }, this.freezeDuration);
    }
}