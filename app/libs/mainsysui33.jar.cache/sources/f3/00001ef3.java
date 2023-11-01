package com.android.systemui.notetask.shortcut;

import com.android.systemui.notetask.NoteTaskController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/shortcut/LaunchNoteTaskActivity_Factory.class */
public final class LaunchNoteTaskActivity_Factory implements Factory<LaunchNoteTaskActivity> {
    public final Provider<NoteTaskController> noteTaskControllerProvider;

    public LaunchNoteTaskActivity_Factory(Provider<NoteTaskController> provider) {
        this.noteTaskControllerProvider = provider;
    }

    public static LaunchNoteTaskActivity_Factory create(Provider<NoteTaskController> provider) {
        return new LaunchNoteTaskActivity_Factory(provider);
    }

    public static LaunchNoteTaskActivity newInstance(NoteTaskController noteTaskController) {
        return new LaunchNoteTaskActivity(noteTaskController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LaunchNoteTaskActivity m3528get() {
        return newInstance((NoteTaskController) this.noteTaskControllerProvider.get());
    }
}