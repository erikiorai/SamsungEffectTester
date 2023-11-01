package com.android.systemui.screenshot;

import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/DeleteScreenshotReceiver_Factory.class */
public final class DeleteScreenshotReceiver_Factory implements Factory<DeleteScreenshotReceiver> {
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<ScreenshotSmartActions> screenshotSmartActionsProvider;

    public DeleteScreenshotReceiver_Factory(Provider<ScreenshotSmartActions> provider, Provider<Executor> provider2) {
        this.screenshotSmartActionsProvider = provider;
        this.backgroundExecutorProvider = provider2;
    }

    public static DeleteScreenshotReceiver_Factory create(Provider<ScreenshotSmartActions> provider, Provider<Executor> provider2) {
        return new DeleteScreenshotReceiver_Factory(provider, provider2);
    }

    public static DeleteScreenshotReceiver newInstance(ScreenshotSmartActions screenshotSmartActions, Executor executor) {
        return new DeleteScreenshotReceiver(screenshotSmartActions, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DeleteScreenshotReceiver m4208get() {
        return newInstance((ScreenshotSmartActions) this.screenshotSmartActionsProvider.get(), (Executor) this.backgroundExecutorProvider.get());
    }
}