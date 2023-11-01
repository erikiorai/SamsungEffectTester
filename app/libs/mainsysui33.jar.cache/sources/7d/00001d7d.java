package com.android.systemui.media.nearby;

import android.media.INearbyMediaDevicesProvider;
import android.media.INearbyMediaDevicesUpdateCallback;
import android.os.IBinder;
import com.android.systemui.statusbar.CommandQueue;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/nearby/NearbyMediaDevicesManager.class */
public final class NearbyMediaDevicesManager {
    public final NearbyMediaDevicesManager$commandQueueCallbacks$1 commandQueueCallbacks;
    public final NearbyMediaDevicesManager$deathRecipient$1 deathRecipient;
    public final NearbyMediaDevicesLogger logger;
    public List<INearbyMediaDevicesProvider> providers = new ArrayList();
    public List<INearbyMediaDevicesUpdateCallback> activeCallbacks = new ArrayList();

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.media.nearby.NearbyMediaDevicesManager$commandQueueCallbacks$1, com.android.systemui.statusbar.CommandQueue$Callbacks] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.media.nearby.NearbyMediaDevicesManager$deathRecipient$1] */
    public NearbyMediaDevicesManager(CommandQueue commandQueue, NearbyMediaDevicesLogger nearbyMediaDevicesLogger) {
        this.logger = nearbyMediaDevicesLogger;
        ?? r0 = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesManager$commandQueueCallbacks$1
            public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
                List list;
                List<INearbyMediaDevicesUpdateCallback> list2;
                List list3;
                NearbyMediaDevicesLogger nearbyMediaDevicesLogger2;
                List list4;
                NearbyMediaDevicesManager$deathRecipient$1 nearbyMediaDevicesManager$deathRecipient$1;
                list = NearbyMediaDevicesManager.this.providers;
                if (list.contains(iNearbyMediaDevicesProvider)) {
                    return;
                }
                list2 = NearbyMediaDevicesManager.this.activeCallbacks;
                for (INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback : list2) {
                    iNearbyMediaDevicesProvider.registerNearbyDevicesCallback(iNearbyMediaDevicesUpdateCallback);
                }
                list3 = NearbyMediaDevicesManager.this.providers;
                list3.add(iNearbyMediaDevicesProvider);
                nearbyMediaDevicesLogger2 = NearbyMediaDevicesManager.this.logger;
                list4 = NearbyMediaDevicesManager.this.providers;
                nearbyMediaDevicesLogger2.logProviderRegistered(list4.size());
                IBinder asBinder = iNearbyMediaDevicesProvider.asBinder();
                nearbyMediaDevicesManager$deathRecipient$1 = NearbyMediaDevicesManager.this.deathRecipient;
                asBinder.linkToDeath(nearbyMediaDevicesManager$deathRecipient$1, 0);
            }

            public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
                List list;
                NearbyMediaDevicesLogger nearbyMediaDevicesLogger2;
                List list2;
                list = NearbyMediaDevicesManager.this.providers;
                if (list.remove(iNearbyMediaDevicesProvider)) {
                    nearbyMediaDevicesLogger2 = NearbyMediaDevicesManager.this.logger;
                    list2 = NearbyMediaDevicesManager.this.providers;
                    nearbyMediaDevicesLogger2.logProviderUnregistered(list2.size());
                }
            }
        };
        this.commandQueueCallbacks = r0;
        this.deathRecipient = new IBinder.DeathRecipient() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesManager$deathRecipient$1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
            }

            public void binderDied(IBinder iBinder) {
                NearbyMediaDevicesManager.access$binderDiedInternal(NearbyMediaDevicesManager.this, iBinder);
            }
        };
        commandQueue.addCallback((CommandQueue.Callbacks) r0);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.nearby.NearbyMediaDevicesManager$deathRecipient$1.binderDied(android.os.IBinder):void] */
    public static final /* synthetic */ void access$binderDiedInternal(NearbyMediaDevicesManager nearbyMediaDevicesManager, IBinder iBinder) {
        nearbyMediaDevicesManager.binderDiedInternal(iBinder);
    }

    public final void binderDiedInternal(IBinder iBinder) {
        synchronized (this.providers) {
            int size = this.providers.size() - 1;
            while (true) {
                if (-1 >= size) {
                    break;
                } else if (Intrinsics.areEqual(this.providers.get(size).asBinder(), iBinder)) {
                    this.providers.remove(size);
                    this.logger.logProviderBinderDied(this.providers.size());
                    break;
                } else {
                    size--;
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void registerNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) {
        for (INearbyMediaDevicesProvider iNearbyMediaDevicesProvider : this.providers) {
            iNearbyMediaDevicesProvider.registerNearbyDevicesCallback(iNearbyMediaDevicesUpdateCallback);
        }
        this.activeCallbacks.add(iNearbyMediaDevicesUpdateCallback);
    }

    public final void unregisterNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) {
        this.activeCallbacks.remove(iNearbyMediaDevicesUpdateCallback);
        for (INearbyMediaDevicesProvider iNearbyMediaDevicesProvider : this.providers) {
            iNearbyMediaDevicesProvider.unregisterNearbyDevicesCallback(iNearbyMediaDevicesUpdateCallback);
        }
    }
}