package com.android.systemui;

import android.content.Context;
import com.android.systemui.GuestResetOrExitSessionReceiver;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import javax.inject.Provider;

/* renamed from: com.android.systemui.GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory  reason: case insensitive filesystem */
/* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory.class */
public final class C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory {
    public final Provider<Context> contextProvider;
    public final Provider<UserSwitcherController> userSwitcherControllerProvider;

    public C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory(Provider<Context> provider, Provider<UserSwitcherController> provider2) {
        this.contextProvider = provider;
        this.userSwitcherControllerProvider = provider2;
    }

    public static C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory create(Provider<Context> provider, Provider<UserSwitcherController> provider2) {
        return new C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory(provider, provider2);
    }

    public static GuestResetOrExitSessionReceiver.ExitSessionDialog newInstance(Context context, UserSwitcherController userSwitcherController, int i, boolean z) {
        return new GuestResetOrExitSessionReceiver.ExitSessionDialog(context, userSwitcherController, i, z);
    }

    public GuestResetOrExitSessionReceiver.ExitSessionDialog get(int i, boolean z) {
        return newInstance((Context) this.contextProvider.get(), (UserSwitcherController) this.userSwitcherControllerProvider.get(), i, z);
    }
}