package com.android.systemui.keyguard;

import android.os.RemoteException;
import android.util.Log;
import com.android.internal.policy.IKeyguardDismissCallback;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/DismissCallbackWrapper.class */
public class DismissCallbackWrapper {
    public IKeyguardDismissCallback mCallback;

    public DismissCallbackWrapper(IKeyguardDismissCallback iKeyguardDismissCallback) {
        this.mCallback = iKeyguardDismissCallback;
    }

    public void notifyDismissCancelled() {
        try {
            this.mCallback.onDismissCancelled();
        } catch (RemoteException e) {
            Log.i("DismissCallbackWrapper", "Failed to call callback", e);
        }
    }

    public void notifyDismissError() {
        try {
            this.mCallback.onDismissError();
        } catch (RemoteException e) {
            Log.i("DismissCallbackWrapper", "Failed to call callback", e);
        }
    }

    public void notifyDismissSucceeded() {
        try {
            this.mCallback.onDismissSucceeded();
        } catch (RemoteException e) {
            Log.i("DismissCallbackWrapper", "Failed to call callback", e);
        }
    }
}