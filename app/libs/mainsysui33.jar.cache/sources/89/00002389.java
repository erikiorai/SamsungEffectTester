package com.android.systemui.reardisplay;

import android.content.Context;
import com.android.systemui.statusbar.CommandQueue;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/reardisplay/RearDisplayDialogController_Factory.class */
public final class RearDisplayDialogController_Factory implements Factory<RearDisplayDialogController> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Executor> executorProvider;

    public RearDisplayDialogController_Factory(Provider<Context> provider, Provider<CommandQueue> provider2, Provider<Executor> provider3) {
        this.contextProvider = provider;
        this.commandQueueProvider = provider2;
        this.executorProvider = provider3;
    }

    public static RearDisplayDialogController_Factory create(Provider<Context> provider, Provider<CommandQueue> provider2, Provider<Executor> provider3) {
        return new RearDisplayDialogController_Factory(provider, provider2, provider3);
    }

    public static RearDisplayDialogController newInstance(Context context, CommandQueue commandQueue, Executor executor) {
        return new RearDisplayDialogController(context, commandQueue, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public RearDisplayDialogController m4101get() {
        return newInstance((Context) this.contextProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (Executor) this.executorProvider.get());
    }
}