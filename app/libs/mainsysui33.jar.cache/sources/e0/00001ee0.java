package com.android.systemui.notetask;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.UserManager;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskController_Factory.class */
public final class NoteTaskController_Factory implements Factory<NoteTaskController> {
    public final Provider<Context> contextProvider;
    public final Provider<NoteTaskIntentResolver> intentResolverProvider;
    public final Provider<Boolean> isEnabledProvider;
    public final Provider<Optional<Bubbles>> optionalBubblesProvider;
    public final Provider<Optional<KeyguardManager>> optionalKeyguardManagerProvider;
    public final Provider<Optional<UserManager>> optionalUserManagerProvider;

    public NoteTaskController_Factory(Provider<Context> provider, Provider<NoteTaskIntentResolver> provider2, Provider<Optional<Bubbles>> provider3, Provider<Optional<KeyguardManager>> provider4, Provider<Optional<UserManager>> provider5, Provider<Boolean> provider6) {
        this.contextProvider = provider;
        this.intentResolverProvider = provider2;
        this.optionalBubblesProvider = provider3;
        this.optionalKeyguardManagerProvider = provider4;
        this.optionalUserManagerProvider = provider5;
        this.isEnabledProvider = provider6;
    }

    public static NoteTaskController_Factory create(Provider<Context> provider, Provider<NoteTaskIntentResolver> provider2, Provider<Optional<Bubbles>> provider3, Provider<Optional<KeyguardManager>> provider4, Provider<Optional<UserManager>> provider5, Provider<Boolean> provider6) {
        return new NoteTaskController_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static NoteTaskController newInstance(Context context, NoteTaskIntentResolver noteTaskIntentResolver, Optional<Bubbles> optional, Optional<KeyguardManager> optional2, Optional<UserManager> optional3, boolean z) {
        return new NoteTaskController(context, noteTaskIntentResolver, optional, optional2, optional3, z);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NoteTaskController m3514get() {
        return newInstance((Context) this.contextProvider.get(), (NoteTaskIntentResolver) this.intentResolverProvider.get(), (Optional) this.optionalBubblesProvider.get(), (Optional) this.optionalKeyguardManagerProvider.get(), (Optional) this.optionalUserManagerProvider.get(), ((Boolean) this.isEnabledProvider.get()).booleanValue());
    }
}