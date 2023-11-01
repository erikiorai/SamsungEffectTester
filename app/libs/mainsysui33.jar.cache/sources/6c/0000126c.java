package com.android.systemui.broadcast;

import com.android.systemui.CoreStartable;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/BroadcastDispatcherStartable.class */
public final class BroadcastDispatcherStartable implements CoreStartable {
    public final BroadcastDispatcher broadcastDispatcher;

    public BroadcastDispatcherStartable(BroadcastDispatcher broadcastDispatcher) {
        this.broadcastDispatcher = broadcastDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.broadcastDispatcher.initialize();
    }
}