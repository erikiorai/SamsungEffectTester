package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.fingerprint.IUdfpsHbmListener;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import com.android.systemui.util.concurrency.Execution;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsDisplayMode.class */
public final class UdfpsDisplayMode implements UdfpsDisplayModeProvider {
    public final AuthController authController;
    public final Context context;
    public Request currentRequest;
    public final Execution execution;

    public UdfpsDisplayMode(Context context, Execution execution, AuthController authController) {
        this.context = context;
        this.execution = execution;
        this.authController = authController;
    }

    @Override // com.android.systemui.biometrics.UdfpsDisplayModeProvider
    public void disable(Runnable runnable) {
        this.execution.isMainThread();
        Log.v("UdfpsDisplayMode", "disable");
        Request request = this.currentRequest;
        if (request == null) {
            Log.w("UdfpsDisplayMode", "disable | already disabled");
            return;
        }
        Trace.beginSection("UdfpsDisplayMode.disable");
        try {
            IUdfpsHbmListener udfpsHbmListener = this.authController.getUdfpsHbmListener();
            Intrinsics.checkNotNull(udfpsHbmListener);
            udfpsHbmListener.onHbmDisabled(request.getDisplayId());
            Log.v("UdfpsDisplayMode", "disable | removed the UDFPS refresh rate request");
        } catch (RemoteException e) {
            Log.e("UdfpsDisplayMode", "disable", e);
        }
        this.currentRequest = null;
        if (runnable != null) {
            runnable.run();
        } else {
            Log.w("UdfpsDisplayMode", "disable | onDisabled is null");
        }
        Trace.endSection();
    }

    @Override // com.android.systemui.biometrics.UdfpsDisplayModeProvider
    public void enable(Runnable runnable) {
        this.execution.isMainThread();
        Log.v("UdfpsDisplayMode", "enable");
        if (this.currentRequest != null) {
            Log.e("UdfpsDisplayMode", "enable | already requested");
        } else if (this.authController.getUdfpsHbmListener() == null) {
            Log.e("UdfpsDisplayMode", "enable | mDisplayManagerCallback is null");
        } else {
            Trace.beginSection("UdfpsDisplayMode.enable");
            Request request = new Request(this.context.getDisplayId());
            this.currentRequest = request;
            try {
                IUdfpsHbmListener udfpsHbmListener = this.authController.getUdfpsHbmListener();
                Intrinsics.checkNotNull(udfpsHbmListener);
                udfpsHbmListener.onHbmEnabled(request.getDisplayId());
                Log.v("UdfpsDisplayMode", "enable | requested optimal refresh rate for UDFPS");
            } catch (RemoteException e) {
                Log.e("UdfpsDisplayMode", "enable", e);
            }
            if (runnable != null) {
                runnable.run();
            } else {
                Log.w("UdfpsDisplayMode", "enable | onEnabled is null");
            }
            Trace.endSection();
        }
    }
}