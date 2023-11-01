package com.android.systemui.power.data.repository;

import android.os.PowerManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/power/data/repository/PowerRepositoryImpl.class */
public final class PowerRepositoryImpl implements PowerRepository {
    public static final Companion Companion = new Companion(null);
    public final Flow<Boolean> isInteractive;

    /* loaded from: mainsysui33.jar:com/android/systemui/power/data/repository/PowerRepositoryImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PowerRepositoryImpl(PowerManager powerManager, BroadcastDispatcher broadcastDispatcher) {
        this.isInteractive = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new PowerRepositoryImpl$isInteractive$1(broadcastDispatcher, powerManager, null));
    }

    @Override // com.android.systemui.power.data.repository.PowerRepository
    public Flow<Boolean> isInteractive() {
        return this.isInteractive;
    }
}