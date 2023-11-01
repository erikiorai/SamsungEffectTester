package com.android.systemui.media.muteawait;

import android.content.Context;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/muteawait/MediaMuteAwaitConnectionCli_Factory.class */
public final class MediaMuteAwaitConnectionCli_Factory implements Factory<MediaMuteAwaitConnectionCli> {
    public final Provider<CommandRegistry> commandRegistryProvider;
    public final Provider<Context> contextProvider;

    public MediaMuteAwaitConnectionCli_Factory(Provider<CommandRegistry> provider, Provider<Context> provider2) {
        this.commandRegistryProvider = provider;
        this.contextProvider = provider2;
    }

    public static MediaMuteAwaitConnectionCli_Factory create(Provider<CommandRegistry> provider, Provider<Context> provider2) {
        return new MediaMuteAwaitConnectionCli_Factory(provider, provider2);
    }

    public static MediaMuteAwaitConnectionCli newInstance(CommandRegistry commandRegistry, Context context) {
        return new MediaMuteAwaitConnectionCli(commandRegistry, context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaMuteAwaitConnectionCli m3331get() {
        return newInstance((CommandRegistry) this.commandRegistryProvider.get(), (Context) this.contextProvider.get());
    }
}