package com.android.systemui.dagger;

import android.content.Context;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsImplementation;
import com.android.systemui.statusbar.CommandQueue;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule_ProvideRecentsFactory.class */
public final class ReferenceSystemUIModule_ProvideRecentsFactory implements Factory<Recents> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<RecentsImplementation> recentsImplementationProvider;

    public ReferenceSystemUIModule_ProvideRecentsFactory(Provider<Context> provider, Provider<RecentsImplementation> provider2, Provider<CommandQueue> provider3) {
        this.contextProvider = provider;
        this.recentsImplementationProvider = provider2;
        this.commandQueueProvider = provider3;
    }

    public static ReferenceSystemUIModule_ProvideRecentsFactory create(Provider<Context> provider, Provider<RecentsImplementation> provider2, Provider<CommandQueue> provider3) {
        return new ReferenceSystemUIModule_ProvideRecentsFactory(provider, provider2, provider3);
    }

    public static Recents provideRecents(Context context, RecentsImplementation recentsImplementation, CommandQueue commandQueue) {
        return (Recents) Preconditions.checkNotNullFromProvides(ReferenceSystemUIModule.provideRecents(context, recentsImplementation, commandQueue));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Recents m2383get() {
        return provideRecents((Context) this.contextProvider.get(), (RecentsImplementation) this.recentsImplementationProvider.get(), (CommandQueue) this.commandQueueProvider.get());
    }
}