package com.android.systemui;

import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.view.Choreographer;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/DejankUtils.class */
public class DejankUtils {
    public static final boolean STRICT_MODE_ENABLED;
    public static final Runnable sAnimationCallbackRunnable;
    public static Stack<String> sBlockingIpcs;
    public static final Choreographer sChoreographer;
    public static final Handler sHandler;
    public static boolean sImmediate;
    public static final Object sLock;
    public static final ArrayList<Runnable> sPendingRunnables;
    public static final Binder.ProxyTransactListener sProxy;
    public static boolean sTemporarilyIgnoreStrictMode;
    public static final HashSet<String> sWhitelistedFrameworkClasses;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.DejankUtils$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$dD18Ut4xm123khbpYgqgVQ9kPK4() {
        lambda$static$0();
    }

    static {
        boolean z = false;
        if (Build.IS_ENG || SystemProperties.getBoolean("persist.sysui.strictmode", false)) {
            z = true;
        }
        STRICT_MODE_ENABLED = z;
        sChoreographer = Choreographer.getInstance();
        sHandler = new Handler();
        sPendingRunnables = new ArrayList<>();
        sBlockingIpcs = new Stack<>();
        HashSet<String> hashSet = new HashSet<>();
        sWhitelistedFrameworkClasses = hashSet;
        sLock = new Object();
        Binder.ProxyTransactListener proxyTransactListener = new Binder.ProxyTransactListener() { // from class: com.android.systemui.DejankUtils.1
            public void onTransactEnded(Object obj) {
            }

            public Object onTransactStarted(IBinder iBinder, int i) {
                return null;
            }

            public Object onTransactStarted(IBinder iBinder, int i, int i2) {
                String interfaceDescriptor;
                synchronized (DejankUtils.sLock) {
                    if ((i2 & 1) != 1) {
                        if (!DejankUtils.sBlockingIpcs.empty() && ThreadUtils.isMainThread() && !DejankUtils.sTemporarilyIgnoreStrictMode) {
                            try {
                                interfaceDescriptor = iBinder.getInterfaceDescriptor();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            synchronized (DejankUtils.sLock) {
                                if (DejankUtils.sWhitelistedFrameworkClasses.contains(interfaceDescriptor)) {
                                    return null;
                                }
                                StrictMode.noteSlowCall("IPC detected on critical path: " + ((String) DejankUtils.sBlockingIpcs.peek()));
                                return null;
                            }
                        }
                    }
                    return null;
                }
            }
        };
        sProxy = proxyTransactListener;
        if (z) {
            hashSet.add("android.view.IWindowSession");
            hashSet.add("com.android.internal.policy.IKeyguardStateCallback");
            hashSet.add("android.os.IPowerManager");
            hashSet.add("com.android.internal.statusbar.IStatusBarService");
            Binder.setProxyTransactListener(proxyTransactListener);
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectCustomSlowCalls().penaltyFlashScreen().penaltyLog().build());
        }
        sAnimationCallbackRunnable = new Runnable() { // from class: com.android.systemui.DejankUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DejankUtils.$r8$lambda$dD18Ut4xm123khbpYgqgVQ9kPK4();
            }
        };
    }

    public static /* synthetic */ void lambda$static$0() {
        int i = 0;
        while (true) {
            ArrayList<Runnable> arrayList = sPendingRunnables;
            if (i >= arrayList.size()) {
                arrayList.clear();
                return;
            } else {
                sHandler.post(arrayList.get(i));
                i++;
            }
        }
    }

    public static void postAfterTraversal(Runnable runnable) {
        if (sImmediate) {
            runnable.run();
            return;
        }
        Assert.isMainThread();
        sPendingRunnables.add(runnable);
        postAnimationCallback();
    }

    public static void postAnimationCallback() {
        sChoreographer.postCallback(1, sAnimationCallbackRunnable, null);
    }

    public static void removeCallbacks(Runnable runnable) {
        Assert.isMainThread();
        sPendingRunnables.remove(runnable);
        sHandler.removeCallbacks(runnable);
    }

    @VisibleForTesting
    public static void setImmediate(boolean z) {
        sImmediate = z;
    }

    public static void startDetectingBlockingIpcs(String str) {
        if (STRICT_MODE_ENABLED) {
            synchronized (sLock) {
                sBlockingIpcs.push(str);
            }
        }
    }

    public static void stopDetectingBlockingIpcs(String str) {
        if (STRICT_MODE_ENABLED) {
            synchronized (sLock) {
                sBlockingIpcs.remove(str);
            }
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public static <T> T whitelistIpcs(Supplier<T> supplier) {
        if (!STRICT_MODE_ENABLED || sTemporarilyIgnoreStrictMode) {
            return supplier.get();
        }
        Object obj = sLock;
        synchronized (obj) {
            sTemporarilyIgnoreStrictMode = true;
        }
        try {
            T t = supplier.get();
            synchronized (obj) {
                sTemporarilyIgnoreStrictMode = false;
            }
            return t;
        } catch (Throwable th) {
            synchronized (sLock) {
                sTemporarilyIgnoreStrictMode = false;
                throw th;
            }
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public static void whitelistIpcs(Runnable runnable) {
        if (!STRICT_MODE_ENABLED || sTemporarilyIgnoreStrictMode) {
            runnable.run();
            return;
        }
        Object obj = sLock;
        synchronized (obj) {
            sTemporarilyIgnoreStrictMode = true;
        }
        try {
            runnable.run();
            synchronized (obj) {
                sTemporarilyIgnoreStrictMode = false;
            }
        } catch (Throwable th) {
            synchronized (sLock) {
                sTemporarilyIgnoreStrictMode = false;
                throw th;
            }
        }
    }
}