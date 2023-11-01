package com.android.app.viewcapture;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* loaded from: mainsysui33.jar:com/android/app/viewcapture/LooperExecutor.class */
public class LooperExecutor implements Executor {
    public final Handler mHandler;

    public LooperExecutor(Looper looper) {
        this.mHandler = new Handler(looper);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (this.mHandler.getLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            this.mHandler.post(runnable);
        }
    }

    public <T> Future<T> submit(Callable<T> callable) {
        callable.getClass();
        FutureTask futureTask = new FutureTask(callable);
        execute(futureTask);
        return futureTask;
    }
}