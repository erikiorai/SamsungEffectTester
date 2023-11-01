package com.android.systemui.qs.external;

import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.qs.external.TileLifecycleManager;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileLifecycleManager_Factory_Impl.class */
public final class TileLifecycleManager_Factory_Impl implements TileLifecycleManager.Factory {
    public final C0055TileLifecycleManager_Factory delegateFactory;

    public TileLifecycleManager_Factory_Impl(C0055TileLifecycleManager_Factory c0055TileLifecycleManager_Factory) {
        this.delegateFactory = c0055TileLifecycleManager_Factory;
    }

    public static Provider<TileLifecycleManager.Factory> create(C0055TileLifecycleManager_Factory c0055TileLifecycleManager_Factory) {
        return InstanceFactory.create(new TileLifecycleManager_Factory_Impl(c0055TileLifecycleManager_Factory));
    }

    @Override // com.android.systemui.qs.external.TileLifecycleManager.Factory
    public TileLifecycleManager create(Intent intent, UserHandle userHandle) {
        return this.delegateFactory.get(intent, userHandle);
    }
}