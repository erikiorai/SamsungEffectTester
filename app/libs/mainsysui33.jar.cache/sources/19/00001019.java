package com.android.systemui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* loaded from: mainsysui33.jar:com/android/systemui/UiOffloadThread.class */
public class UiOffloadThread {
    public final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public Future<?> execute(Runnable runnable) {
        return this.mExecutorService.submit(runnable);
    }
}