package com.android.systemui.notetask;

import android.content.pm.PackageManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskIntentResolver_Factory.class */
public final class NoteTaskIntentResolver_Factory implements Factory<NoteTaskIntentResolver> {
    public final Provider<PackageManager> packageManagerProvider;

    public NoteTaskIntentResolver_Factory(Provider<PackageManager> provider) {
        this.packageManagerProvider = provider;
    }

    public static NoteTaskIntentResolver_Factory create(Provider<PackageManager> provider) {
        return new NoteTaskIntentResolver_Factory(provider);
    }

    public static NoteTaskIntentResolver newInstance(PackageManager packageManager) {
        return new NoteTaskIntentResolver(packageManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NoteTaskIntentResolver m3517get() {
        return newInstance((PackageManager) this.packageManagerProvider.get());
    }
}