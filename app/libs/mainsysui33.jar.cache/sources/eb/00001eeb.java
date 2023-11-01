package com.android.systemui.notetask;

import android.app.KeyguardManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskModule_Companion_ProvideOptionalKeyguardManagerFactory.class */
public final class NoteTaskModule_Companion_ProvideOptionalKeyguardManagerFactory implements Factory<Optional<KeyguardManager>> {
    public final Provider<Context> contextProvider;

    public NoteTaskModule_Companion_ProvideOptionalKeyguardManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static NoteTaskModule_Companion_ProvideOptionalKeyguardManagerFactory create(Provider<Context> provider) {
        return new NoteTaskModule_Companion_ProvideOptionalKeyguardManagerFactory(provider);
    }

    public static Optional<KeyguardManager> provideOptionalKeyguardManager(Context context) {
        return (Optional) Preconditions.checkNotNullFromProvides(NoteTaskModule.Companion.provideOptionalKeyguardManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<KeyguardManager> get() {
        return provideOptionalKeyguardManager((Context) this.contextProvider.get());
    }
}