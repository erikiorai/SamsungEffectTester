package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.os.BinderInternal;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.LogBufferFreezer;
import com.android.systemui.dump.SystemUIAuxiliaryDumpService;
import com.android.systemui.statusbar.policy.BatteryStateNotifier;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIService.class */
public class SystemUIService extends Service {
    public final BatteryStateNotifier mBatteryStateNotifier;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final DumpHandler mDumpHandler;
    public final LogBufferFreezer mLogBufferFreezer;
    public final Handler mMainHandler;

    public SystemUIService(Handler handler, DumpHandler dumpHandler, BroadcastDispatcher broadcastDispatcher, LogBufferFreezer logBufferFreezer, BatteryStateNotifier batteryStateNotifier) {
        this.mMainHandler = handler;
        this.mDumpHandler = dumpHandler;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mLogBufferFreezer = logBufferFreezer;
        this.mBatteryStateNotifier = batteryStateNotifier;
    }

    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String[] strArr2 = strArr;
        if (strArr.length == 0) {
            strArr2 = new String[]{"--dump-priority", "CRITICAL"};
        }
        this.mDumpHandler.dump(fileDescriptor, printWriter, strArr2);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
        this.mLogBufferFreezer.attach(this.mBroadcastDispatcher);
        this.mDumpHandler.init();
        if (getResources().getBoolean(R$bool.config_showNotificationForUnknownBatteryState)) {
            this.mBatteryStateNotifier.startListening();
        }
        if (Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.crash_sysui", false)) {
            throw new RuntimeException();
        }
        if (Build.IS_DEBUGGABLE) {
            BinderInternal.nSetBinderProxyCountEnabled(true);
            BinderInternal.nSetBinderProxyCountWatermarks(1000, 900);
            BinderInternal.setBinderProxyCountCallback(new BinderInternal.BinderProxyLimitListener() { // from class: com.android.systemui.SystemUIService.1
                public void onLimitReached(int i) {
                    Slog.w("SystemUIService", "uid " + i + " sent too many Binder proxies to uid " + Process.myUid());
                }
            }, this.mMainHandler);
        }
        startServiceAsUser(new Intent(getApplicationContext(), SystemUIAuxiliaryDumpService.class), UserHandle.SYSTEM);
    }
}