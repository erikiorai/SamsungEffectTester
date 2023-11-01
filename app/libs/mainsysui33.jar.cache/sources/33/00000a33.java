package com.airbnb.lottie;

import android.os.Handler;
import android.os.Looper;
import com.airbnb.lottie.utils.Logger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/LottieTask.class */
public class LottieTask<T> {
    public static Executor EXECUTOR = Executors.newCachedThreadPool();
    public final Set<LottieListener<Throwable>> failureListeners;
    public final Handler handler;
    public volatile LottieResult<T> result;
    public final Set<LottieListener<T>> successListeners;

    /* loaded from: mainsysui33.jar:com/airbnb/lottie/LottieTask$LottieFutureTask.class */
    public class LottieFutureTask extends FutureTask<LottieResult<T>> {
        public LottieFutureTask(Callable<LottieResult<T>> callable) {
            super(callable);
        }

        @Override // java.util.concurrent.FutureTask
        public void done() {
            if (isCancelled()) {
                return;
            }
            try {
                LottieTask.this.setResult(get());
            } catch (InterruptedException | ExecutionException e) {
                LottieTask.this.setResult(new LottieResult(e));
            }
        }
    }

    public LottieTask(Callable<LottieResult<T>> callable) {
        this(callable, false);
    }

    public LottieTask(Callable<LottieResult<T>> callable, boolean z) {
        this.successListeners = new LinkedHashSet(1);
        this.failureListeners = new LinkedHashSet(1);
        this.handler = new Handler(Looper.getMainLooper());
        this.result = null;
        if (!z) {
            EXECUTOR.execute(new LottieFutureTask(callable));
            return;
        }
        try {
            setResult(callable.call());
        } catch (Throwable th) {
            setResult(new LottieResult<>(th));
        }
    }

    public LottieTask<T> addFailureListener(LottieListener<Throwable> lottieListener) {
        synchronized (this) {
            if (this.result != null && this.result.getException() != null) {
                lottieListener.onResult(this.result.getException());
            }
            this.failureListeners.add(lottieListener);
        }
        return this;
    }

    public LottieTask<T> addListener(LottieListener<T> lottieListener) {
        synchronized (this) {
            if (this.result != null && this.result.getValue() != null) {
                lottieListener.onResult(this.result.getValue());
            }
            this.successListeners.add(lottieListener);
        }
        return this;
    }

    public final void notifyFailureListeners(Throwable th) {
        synchronized (this) {
            ArrayList<LottieListener> arrayList = new ArrayList(this.failureListeners);
            if (arrayList.isEmpty()) {
                Logger.warning("Lottie encountered an error but no failure listener was added:", th);
                return;
            }
            for (LottieListener lottieListener : arrayList) {
                lottieListener.onResult(th);
            }
        }
    }

    public final void notifyListeners() {
        this.handler.post(new Runnable() { // from class: com.airbnb.lottie.LottieTask.1
            @Override // java.lang.Runnable
            public void run() {
                if (LottieTask.this.result == null) {
                    return;
                }
                LottieResult lottieResult = LottieTask.this.result;
                if (lottieResult.getValue() != null) {
                    LottieTask.this.notifySuccessListeners(lottieResult.getValue());
                } else {
                    LottieTask.this.notifyFailureListeners(lottieResult.getException());
                }
            }
        });
    }

    public final void notifySuccessListeners(T t) {
        synchronized (this) {
            for (LottieListener lottieListener : new ArrayList(this.successListeners)) {
                lottieListener.onResult(t);
            }
        }
    }

    public LottieTask<T> removeFailureListener(LottieListener<Throwable> lottieListener) {
        synchronized (this) {
            this.failureListeners.remove(lottieListener);
        }
        return this;
    }

    public LottieTask<T> removeListener(LottieListener<T> lottieListener) {
        synchronized (this) {
            this.successListeners.remove(lottieListener);
        }
        return this;
    }

    public final void setResult(LottieResult<T> lottieResult) {
        if (this.result != null) {
            throw new IllegalStateException("A task may only be set once.");
        }
        this.result = lottieResult;
        notifyListeners();
    }
}