package com.android.systemui;

import com.android.systemui.GuestResetOrExitSessionReceiver;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory_Impl.class */
public final class GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory_Impl implements GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory {
    public final C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory delegateFactory;

    public GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory_Impl(C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory c0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory) {
        this.delegateFactory = c0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory;
    }

    public static Provider<GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory> create(C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory c0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory) {
        return InstanceFactory.create(new GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory_Impl(c0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory));
    }

    @Override // com.android.systemui.GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory
    public GuestResetOrExitSessionReceiver.ResetSessionDialog create(int i) {
        return this.delegateFactory.get(i);
    }
}