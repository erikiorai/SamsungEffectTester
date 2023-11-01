package com.android.systemui.assist;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistLogger_Factory.class */
public final class AssistLogger_Factory implements Factory<AssistLogger> {
    public final Provider<AssistUtils> assistUtilsProvider;
    public final Provider<Context> contextProvider;
    public final Provider<PhoneStateMonitor> phoneStateMonitorProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public AssistLogger_Factory(Provider<Context> provider, Provider<UiEventLogger> provider2, Provider<AssistUtils> provider3, Provider<PhoneStateMonitor> provider4, Provider<UserTracker> provider5) {
        this.contextProvider = provider;
        this.uiEventLoggerProvider = provider2;
        this.assistUtilsProvider = provider3;
        this.phoneStateMonitorProvider = provider4;
        this.userTrackerProvider = provider5;
    }

    public static AssistLogger_Factory create(Provider<Context> provider, Provider<UiEventLogger> provider2, Provider<AssistUtils> provider3, Provider<PhoneStateMonitor> provider4, Provider<UserTracker> provider5) {
        return new AssistLogger_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static AssistLogger newInstance(Context context, UiEventLogger uiEventLogger, AssistUtils assistUtils, PhoneStateMonitor phoneStateMonitor, UserTracker userTracker) {
        return new AssistLogger(context, uiEventLogger, assistUtils, phoneStateMonitor, userTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AssistLogger m1469get() {
        return newInstance((Context) this.contextProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (AssistUtils) this.assistUtilsProvider.get(), (PhoneStateMonitor) this.phoneStateMonitorProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}