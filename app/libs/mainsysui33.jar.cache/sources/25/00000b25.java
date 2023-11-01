package com.android.keyguard;

import android.content.ContentResolver;
import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/ActiveUnlockConfig_Factory.class */
public final class ActiveUnlockConfig_Factory implements Factory<ActiveUnlockConfig> {
    public final Provider<ContentResolver> contentResolverProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;

    public ActiveUnlockConfig_Factory(Provider<Handler> provider, Provider<SecureSettings> provider2, Provider<ContentResolver> provider3, Provider<DumpManager> provider4) {
        this.handlerProvider = provider;
        this.secureSettingsProvider = provider2;
        this.contentResolverProvider = provider3;
        this.dumpManagerProvider = provider4;
    }

    public static ActiveUnlockConfig_Factory create(Provider<Handler> provider, Provider<SecureSettings> provider2, Provider<ContentResolver> provider3, Provider<DumpManager> provider4) {
        return new ActiveUnlockConfig_Factory(provider, provider2, provider3, provider4);
    }

    public static ActiveUnlockConfig newInstance(Handler handler, SecureSettings secureSettings, ContentResolver contentResolver, DumpManager dumpManager) {
        return new ActiveUnlockConfig(handler, secureSettings, contentResolver, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActiveUnlockConfig m518get() {
        return newInstance((Handler) this.handlerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (ContentResolver) this.contentResolverProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}