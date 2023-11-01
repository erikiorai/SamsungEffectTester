package com.android.systemui.notetask;

import com.android.systemui.statusbar.CommandQueue;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskInitializer_Factory.class */
public final class NoteTaskInitializer_Factory implements Factory<NoteTaskInitializer> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Boolean> isEnabledProvider;
    public final Provider<NoteTaskController> noteTaskControllerProvider;
    public final Provider<Optional<Bubbles>> optionalBubblesProvider;

    public NoteTaskInitializer_Factory(Provider<Optional<Bubbles>> provider, Provider<NoteTaskController> provider2, Provider<CommandQueue> provider3, Provider<Boolean> provider4) {
        this.optionalBubblesProvider = provider;
        this.noteTaskControllerProvider = provider2;
        this.commandQueueProvider = provider3;
        this.isEnabledProvider = provider4;
    }

    public static NoteTaskInitializer_Factory create(Provider<Optional<Bubbles>> provider, Provider<NoteTaskController> provider2, Provider<CommandQueue> provider3, Provider<Boolean> provider4) {
        return new NoteTaskInitializer_Factory(provider, provider2, provider3, provider4);
    }

    public static NoteTaskInitializer newInstance(Optional<Bubbles> optional, NoteTaskController noteTaskController, CommandQueue commandQueue, boolean z) {
        return new NoteTaskInitializer(optional, noteTaskController, commandQueue, z);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NoteTaskInitializer m3515get() {
        return newInstance((Optional) this.optionalBubblesProvider.get(), (NoteTaskController) this.noteTaskControllerProvider.get(), (CommandQueue) this.commandQueueProvider.get(), ((Boolean) this.isEnabledProvider.get()).booleanValue());
    }
}