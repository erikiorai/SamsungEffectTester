package com.android.systemui;

import com.android.systemui.GuestResetOrExitSessionReceiver;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory_Impl.class */
public final class GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory_Impl implements GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory {
    public final C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory delegateFactory;

    public GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory_Impl(C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory c0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory) {
        this.delegateFactory = c0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory;
    }

    public static Provider<GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory> create(C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory c0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory) {
        return InstanceFactory.create(new GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory_Impl(c0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory));
    }

    @Override // com.android.systemui.GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory
    public GuestResetOrExitSessionReceiver.ExitSessionDialog create(int i, boolean z) {
        return this.delegateFactory.get(i, z);
    }
}