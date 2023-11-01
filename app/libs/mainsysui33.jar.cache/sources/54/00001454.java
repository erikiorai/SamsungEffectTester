package com.android.systemui.controls.settings;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsDialogManagerImpl_Factory.class */
public final class ControlsSettingsDialogManagerImpl_Factory implements Factory<ControlsSettingsDialogManagerImpl> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<ControlsSettingsRepository> controlsSettingsRepositoryProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<UserFileManager> userFileManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsSettingsDialogManagerImpl_Factory(Provider<SecureSettings> provider, Provider<UserFileManager> provider2, Provider<ControlsSettingsRepository> provider3, Provider<UserTracker> provider4, Provider<ActivityStarter> provider5) {
        this.secureSettingsProvider = provider;
        this.userFileManagerProvider = provider2;
        this.controlsSettingsRepositoryProvider = provider3;
        this.userTrackerProvider = provider4;
        this.activityStarterProvider = provider5;
    }

    public static ControlsSettingsDialogManagerImpl_Factory create(Provider<SecureSettings> provider, Provider<UserFileManager> provider2, Provider<ControlsSettingsRepository> provider3, Provider<UserTracker> provider4, Provider<ActivityStarter> provider5) {
        return new ControlsSettingsDialogManagerImpl_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ControlsSettingsDialogManagerImpl newInstance(SecureSettings secureSettings, UserFileManager userFileManager, ControlsSettingsRepository controlsSettingsRepository, UserTracker userTracker, ActivityStarter activityStarter) {
        return new ControlsSettingsDialogManagerImpl(secureSettings, userFileManager, controlsSettingsRepository, userTracker, activityStarter);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsSettingsDialogManagerImpl m1844get() {
        return newInstance((SecureSettings) this.secureSettingsProvider.get(), (UserFileManager) this.userFileManagerProvider.get(), (ControlsSettingsRepository) this.controlsSettingsRepositoryProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ActivityStarter) this.activityStarterProvider.get());
    }
}