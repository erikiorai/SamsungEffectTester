package com.android.systemui.controls.settings;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsRepositoryImpl_Factory.class */
public final class ControlsSettingsRepositoryImpl_Factory implements Factory<ControlsSettingsRepositoryImpl> {
    public final Provider<CoroutineDispatcher> backgroundDispatcherProvider;
    public final Provider<CoroutineScope> scopeProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<UserRepository> userRepositoryProvider;

    public ControlsSettingsRepositoryImpl_Factory(Provider<CoroutineScope> provider, Provider<CoroutineDispatcher> provider2, Provider<UserRepository> provider3, Provider<SecureSettings> provider4) {
        this.scopeProvider = provider;
        this.backgroundDispatcherProvider = provider2;
        this.userRepositoryProvider = provider3;
        this.secureSettingsProvider = provider4;
    }

    public static ControlsSettingsRepositoryImpl_Factory create(Provider<CoroutineScope> provider, Provider<CoroutineDispatcher> provider2, Provider<UserRepository> provider3, Provider<SecureSettings> provider4) {
        return new ControlsSettingsRepositoryImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static ControlsSettingsRepositoryImpl newInstance(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository, SecureSettings secureSettings) {
        return new ControlsSettingsRepositoryImpl(coroutineScope, coroutineDispatcher, userRepository, secureSettings);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsSettingsRepositoryImpl m1846get() {
        return newInstance((CoroutineScope) this.scopeProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (UserRepository) this.userRepositoryProvider.get(), (SecureSettings) this.secureSettingsProvider.get());
    }
}