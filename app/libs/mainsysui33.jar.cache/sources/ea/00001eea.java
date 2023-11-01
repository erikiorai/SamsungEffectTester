package com.android.systemui.notetask;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskModule_Companion_ProvideIsNoteTaskEnabledFactory.class */
public final class NoteTaskModule_Companion_ProvideIsNoteTaskEnabledFactory implements Factory<Boolean> {
    public final Provider<FeatureFlags> featureFlagsProvider;

    public NoteTaskModule_Companion_ProvideIsNoteTaskEnabledFactory(Provider<FeatureFlags> provider) {
        this.featureFlagsProvider = provider;
    }

    public static NoteTaskModule_Companion_ProvideIsNoteTaskEnabledFactory create(Provider<FeatureFlags> provider) {
        return new NoteTaskModule_Companion_ProvideIsNoteTaskEnabledFactory(provider);
    }

    public static boolean provideIsNoteTaskEnabled(FeatureFlags featureFlags) {
        return NoteTaskModule.Companion.provideIsNoteTaskEnabled(featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m3520get() {
        return Boolean.valueOf(provideIsNoteTaskEnabled((FeatureFlags) this.featureFlagsProvider.get()));
    }
}