package com.android.settingslib.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* loaded from: mainsysui33.jar:com/android/settingslib/utils/ThreadUtils.class */
public class ThreadUtils {
    public static volatile Thread sMainThread;
    public static volatile Handler sMainThreadHandler;
    public static volatile ExecutorService sThreadExecutor;

    public static ExecutorService getThreadExecutor() {
        ExecutorService executorService;
        synchronized (ThreadUtils.class) {
            try {
                if (sThreadExecutor == null) {
                    sThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                }
                executorService = sThreadExecutor;
            } catch (Throwable th) {
                throw th;
            }
        }
        return executorService;
    }

    public static Handler getUiThreadHandler() {
        if (sMainThreadHandler == null) {
            sMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        return sMainThreadHandler;
    }

    public static boolean isMainThread() {
        if (sMainThread == null) {
            sMainThread = Looper.getMainLooper().getThread();
        }
        return Thread.currentThread() == sMainThread;
    }

    public static Future postOnBackgroundThread(Runnable runnable) {
        return getThreadExecutor().submit(runnable);
    }

    public static void postOnMainThread(Runnable runnable) {
        getUiThreadHandler().post(runnable);
    }

    public static void postOnMainThreadDelayed(Runnable runnable, long j) {
        getUiThreadHandler().postDelayed(runnable, j);
    }
}