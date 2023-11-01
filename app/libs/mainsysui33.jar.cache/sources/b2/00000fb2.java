package com.android.systemui;

import com.android.systemui.GuestResumeSessionReceiver;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestResumeSessionReceiver_ResetSessionDialog_Factory_Impl.class */
public final class GuestResumeSessionReceiver_ResetSessionDialog_Factory_Impl implements GuestResumeSessionReceiver.ResetSessionDialog.Factory {
    public final C0052GuestResumeSessionReceiver_ResetSessionDialog_Factory delegateFactory;

    public GuestResumeSessionReceiver_ResetSessionDialog_Factory_Impl(C0052GuestResumeSessionReceiver_ResetSessionDialog_Factory c0052GuestResumeSessionReceiver_ResetSessionDialog_Factory) {
        this.delegateFactory = c0052GuestResumeSessionReceiver_ResetSessionDialog_Factory;
    }

    public static Provider<GuestResumeSessionReceiver.ResetSessionDialog.Factory> create(C0052GuestResumeSessionReceiver_ResetSessionDialog_Factory c0052GuestResumeSessionReceiver_ResetSessionDialog_Factory) {
        return InstanceFactory.create(new GuestResumeSessionReceiver_ResetSessionDialog_Factory_Impl(c0052GuestResumeSessionReceiver_ResetSessionDialog_Factory));
    }

    @Override // com.android.systemui.GuestResumeSessionReceiver.ResetSessionDialog.Factory
    public GuestResumeSessionReceiver.ResetSessionDialog create(int i) {
        return this.delegateFactory.get(i);
    }
}