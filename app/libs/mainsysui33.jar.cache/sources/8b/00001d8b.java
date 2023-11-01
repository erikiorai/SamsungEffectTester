package com.android.systemui.media.taptotransfer;

import android.content.Context;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/MediaTttCommandLineHelper_Factory.class */
public final class MediaTttCommandLineHelper_Factory implements Factory<MediaTttCommandLineHelper> {
    public final Provider<CommandRegistry> commandRegistryProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Executor> mainExecutorProvider;

    public MediaTttCommandLineHelper_Factory(Provider<CommandRegistry> provider, Provider<Context> provider2, Provider<Executor> provider3) {
        this.commandRegistryProvider = provider;
        this.contextProvider = provider2;
        this.mainExecutorProvider = provider3;
    }

    public static MediaTttCommandLineHelper_Factory create(Provider<CommandRegistry> provider, Provider<Context> provider2, Provider<Executor> provider3) {
        return new MediaTttCommandLineHelper_Factory(provider, provider2, provider3);
    }

    public static MediaTttCommandLineHelper newInstance(CommandRegistry commandRegistry, Context context, Executor executor) {
        return new MediaTttCommandLineHelper(commandRegistry, context, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTttCommandLineHelper m3346get() {
        return newInstance((CommandRegistry) this.commandRegistryProvider.get(), (Context) this.contextProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}