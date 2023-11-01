package com.android.systemui.screenshot;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.AccessibilityManager;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/TimeoutHandler.class */
public class TimeoutHandler extends Handler {
    public final Context mContext;
    public int mDefaultTimeout;
    public Runnable mOnTimeout;

    public TimeoutHandler(Context context) {
        super(Looper.getMainLooper());
        this.mDefaultTimeout = 6000;
        this.mContext = context;
        this.mOnTimeout = new Runnable() { // from class: com.android.systemui.screenshot.TimeoutHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TimeoutHandler.lambda$new$0();
            }
        };
    }

    public static /* synthetic */ void lambda$new$0() {
    }

    public void cancelTimeout() {
        removeMessages(2);
    }

    public int getDefaultTimeoutMillis() {
        return this.mDefaultTimeout;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message.what != 2) {
            return;
        }
        this.mOnTimeout.run();
    }

    public void resetTimeout() {
        cancelTimeout();
        sendMessageDelayed(obtainMessage(2), ((AccessibilityManager) this.mContext.getSystemService("accessibility")).getRecommendedTimeoutMillis(this.mDefaultTimeout, 4));
    }

    public void setDefaultTimeoutMillis(int i) {
        this.mDefaultTimeout = i;
    }

    public void setOnTimeoutRunnable(Runnable runnable) {
        this.mOnTimeout = runnable;
    }
}