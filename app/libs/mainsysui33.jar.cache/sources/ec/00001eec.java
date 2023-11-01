package com.android.systemui.notetask;

import android.content.Context;
import android.os.UserManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskModule_Companion_ProvideOptionalUserManagerFactory.class */
public final class NoteTaskModule_Companion_ProvideOptionalUserManagerFactory implements Factory<Optional<UserManager>> {
    public final Provider<Context> contextProvider;

    public NoteTaskModule_Companion_ProvideOptionalUserManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static NoteTaskModule_Companion_ProvideOptionalUserManagerFactory create(Provider<Context> provider) {
        return new NoteTaskModule_Companion_ProvideOptionalUserManagerFactory(provider);
    }

    public static Optional<UserManager> provideOptionalUserManager(Context context) {
        return (Optional) Preconditions.checkNotNullFromProvides(NoteTaskModule.Companion.provideOptionalUserManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<UserManager> get() {
        return provideOptionalUserManager((Context) this.contextProvider.get());
    }
}