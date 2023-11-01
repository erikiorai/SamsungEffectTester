package com.android.systemui;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.GuestResetOrExitSessionReceiver;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import javax.inject.Provider;

/* renamed from: com.android.systemui.GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory  reason: case insensitive filesystem */
/* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory.class */
public final class C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory {
    public final Provider<Context> contextProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserSwitcherController> userSwitcherControllerProvider;

    public C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory(Provider<Context> provider, Provider<UserSwitcherController> provider2, Provider<UiEventLogger> provider3) {
        this.contextProvider = provider;
        this.userSwitcherControllerProvider = provider2;
        this.uiEventLoggerProvider = provider3;
    }

    public static C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory create(Provider<Context> provider, Provider<UserSwitcherController> provider2, Provider<UiEventLogger> provider3) {
        return new C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory(provider, provider2, provider3);
    }

    public static GuestResetOrExitSessionReceiver.ResetSessionDialog newInstance(Context context, UserSwitcherController userSwitcherController, UiEventLogger uiEventLogger, int i) {
        return new GuestResetOrExitSessionReceiver.ResetSessionDialog(context, userSwitcherController, uiEventLogger, i);
    }

    public GuestResetOrExitSessionReceiver.ResetSessionDialog get(int i) {
        return newInstance((Context) this.contextProvider.get(), (UserSwitcherController) this.userSwitcherControllerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), i);
    }
}