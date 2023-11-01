package com.android.systemui.media.systemsounds;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/systemsounds/HomeSoundEffectController_Factory.class */
public final class HomeSoundEffectController_Factory implements Factory<HomeSoundEffectController> {
    public final Provider<ActivityManagerWrapper> activityManagerWrapperProvider;
    public final Provider<AudioManager> audioManagerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<TaskStackChangeListeners> taskStackChangeListenersProvider;

    public HomeSoundEffectController_Factory(Provider<Context> provider, Provider<AudioManager> provider2, Provider<TaskStackChangeListeners> provider3, Provider<ActivityManagerWrapper> provider4, Provider<PackageManager> provider5) {
        this.contextProvider = provider;
        this.audioManagerProvider = provider2;
        this.taskStackChangeListenersProvider = provider3;
        this.activityManagerWrapperProvider = provider4;
        this.packageManagerProvider = provider5;
    }

    public static HomeSoundEffectController_Factory create(Provider<Context> provider, Provider<AudioManager> provider2, Provider<TaskStackChangeListeners> provider3, Provider<ActivityManagerWrapper> provider4, Provider<PackageManager> provider5) {
        return new HomeSoundEffectController_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static HomeSoundEffectController newInstance(Context context, AudioManager audioManager, TaskStackChangeListeners taskStackChangeListeners, ActivityManagerWrapper activityManagerWrapper, PackageManager packageManager) {
        return new HomeSoundEffectController(context, audioManager, taskStackChangeListeners, activityManagerWrapper, packageManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public HomeSoundEffectController m3343get() {
        return newInstance((Context) this.contextProvider.get(), (AudioManager) this.audioManagerProvider.get(), (TaskStackChangeListeners) this.taskStackChangeListenersProvider.get(), (ActivityManagerWrapper) this.activityManagerWrapperProvider.get(), (PackageManager) this.packageManagerProvider.get());
    }
}