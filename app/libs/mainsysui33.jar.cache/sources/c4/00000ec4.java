package com.android.settingslib.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

@Deprecated
/* loaded from: mainsysui33.jar:com/android/settingslib/utils/AsyncLoader.class */
public abstract class AsyncLoader<T> extends AsyncTaskLoader<T> {
    public T mResult;

    public AsyncLoader(Context context) {
        super(context);
    }

    @Override // android.content.Loader
    public void deliverResult(T t) {
        if (isReset()) {
            if (t != null) {
                onDiscardResult(t);
                return;
            }
            return;
        }
        T t2 = this.mResult;
        this.mResult = t;
        if (isStarted()) {
            super.deliverResult(t);
        }
        if (t2 == null || t2 == this.mResult) {
            return;
        }
        onDiscardResult(t2);
    }

    @Override // android.content.AsyncTaskLoader
    public void onCanceled(T t) {
        super.onCanceled(t);
        if (t != null) {
            onDiscardResult(t);
        }
    }

    public abstract void onDiscardResult(T t);

    @Override // android.content.Loader
    public void onReset() {
        super.onReset();
        onStopLoading();
        T t = this.mResult;
        if (t != null) {
            onDiscardResult(t);
        }
        this.mResult = null;
    }

    @Override // android.content.Loader
    public void onStartLoading() {
        T t = this.mResult;
        if (t != null) {
            deliverResult(t);
        }
        if (takeContentChanged() || this.mResult == null) {
            forceLoad();
        }
    }

    @Override // android.content.Loader
    public void onStopLoading() {
        cancelLoad();
    }
}