package com.android.systemui;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.Application;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.DumpableContainer;
import android.util.Log;
import android.util.TimingsTraceLog;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda3;
import com.android.internal.protolog.common.ProtoLog;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.NotificationChannels;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIApplication.class */
public class SystemUIApplication extends Application implements SystemUIAppComponentFactoryBase.ContextInitializer, DumpableContainer {
    public BootCompleteCacheImpl mBootCompleteCache;
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback mContextAvailableCallback;
    public DumpManager mDumpManager;
    public final ArrayMap<String, android.util.Dumpable> mDumpables = new ArrayMap<>();
    public SystemUIInitializer mInitializer;
    public CoreStartable[] mServices;
    public boolean mServicesStarted;
    public SysUIComponent mSysUIComponent;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$cXXQLUPGAUYtU2lktAZ_kDgvHXw(SystemUIApplication systemUIApplication, String str) {
        systemUIApplication.lambda$startServicesIfNeeded$1(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$pt6oxwKYJNOsdTRRgkl96N87nEY(SystemUIApplication systemUIApplication, int i, String str, Map.Entry entry) {
        systemUIApplication.lambda$startServicesIfNeeded$0(i, str, entry);
    }

    public SystemUIApplication() {
        Log.v("SystemUIService", "SystemUIApplication constructed.");
        ProtoLog.REQUIRE_PROTOLOGTOOL = false;
    }

    public /* synthetic */ void lambda$startServicesIfNeeded$0(int i, String str, Map.Entry entry) {
        this.mServices[i] = startStartable(str, (Provider) entry.getValue());
    }

    public /* synthetic */ void lambda$startServicesIfNeeded$1(String str) {
        CoreStartable[] coreStartableArr = this.mServices;
        coreStartableArr[coreStartableArr.length - 1] = startAdditionalStartable(str);
    }

    public static void notifyBootCompleted(CoreStartable coreStartable) {
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, coreStartable.getClass().getSimpleName() + ".onBootCompleted()");
        }
        coreStartable.onBootCompleted();
        Trace.endSection();
    }

    public static void overrideNotificationAppName(Context context, Notification.Builder builder, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", z ? context.getString(17040904) : context.getString(17040903));
        builder.addExtras(bundle);
    }

    public static CoreStartable startAdditionalStartable(String str) {
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, str + ".newInstance()");
        }
        try {
            try {
                CoreStartable coreStartable = (CoreStartable) Class.forName(str).newInstance();
                Trace.endSection();
                return startStartable(coreStartable);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    public static CoreStartable startStartable(CoreStartable coreStartable) {
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, coreStartable.getClass().getSimpleName() + ".start()");
        }
        coreStartable.start();
        Trace.endSection();
        return coreStartable;
    }

    public static CoreStartable startStartable(String str, Provider<CoreStartable> provider) {
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, "Provider<" + str + ">.get()");
        }
        Trace.endSection();
        return startStartable((CoreStartable) provider.get());
    }

    public static void timeInitialization(String str, Runnable runnable, TimingsTraceLog timingsTraceLog, String str2) {
        long currentTimeMillis = System.currentTimeMillis();
        timingsTraceLog.traceBegin(str2 + " " + str);
        runnable.run();
        timingsTraceLog.traceEnd();
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (currentTimeMillis2 > 1000) {
            Log.w("SystemUIService", "Initialization of " + str + " took " + currentTimeMillis2 + " ms");
        }
    }

    public boolean addDumpable(final android.util.Dumpable dumpable) {
        String dumpableName = dumpable.getDumpableName();
        if (this.mDumpables.containsKey(dumpableName)) {
            return false;
        }
        this.mDumpables.put(dumpableName, dumpable);
        this.mDumpManager.registerDumpable(dumpable.getDumpableName(), new Dumpable() { // from class: com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                dumpable.dump(printWriter, strArr);
            }
        });
        return true;
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        if (this.mServicesStarted) {
            ConfigurationController configurationController = this.mSysUIComponent.getConfigurationController();
            if (Trace.isEnabled()) {
                Trace.traceBegin(4096L, configurationController.getClass().getSimpleName() + ".onConfigurationChanged()");
            }
            configurationController.onConfigurationChanged(configuration);
            Trace.endSection();
            int length = this.mServices.length;
            for (int i = 0; i < length; i++) {
                if (this.mServices[i] != null) {
                    if (Trace.isEnabled()) {
                        Trace.traceBegin(4096L, this.mServices[i].getClass().getSimpleName() + ".onConfigurationChanged()");
                    }
                    this.mServices[i].onConfigurationChanged(configuration);
                    Trace.endSection();
                }
            }
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Log.v("SystemUIService", "SystemUIApplication created.");
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SystemUIBootTiming", 4096L);
        timingsTraceLog.traceBegin("DependencyInjection");
        SystemUIInitializer onContextAvailable = this.mContextAvailableCallback.onContextAvailable(this);
        this.mInitializer = onContextAvailable;
        SysUIComponent sysUIComponent = onContextAvailable.getSysUIComponent();
        this.mSysUIComponent = sysUIComponent;
        this.mBootCompleteCache = sysUIComponent.provideBootCacheImpl();
        timingsTraceLog.traceEnd();
        Looper.getMainLooper().setTraceTag(4096L);
        setTheme(R$style.Theme_SystemUI);
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            String currentProcessName = ActivityThread.currentProcessName();
            ApplicationInfo applicationInfo = getApplicationInfo();
            if (currentProcessName != null) {
                if (currentProcessName.startsWith(applicationInfo.processName + ":")) {
                    return;
                }
            }
            startSecondaryUserServicesIfNeeded();
            return;
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.setPriority(1000);
        int gPUContextPriority = SurfaceControl.getGPUContextPriority();
        Log.i("SystemUIService", "Found SurfaceFlinger's GPU Priority: " + gPUContextPriority);
        if (gPUContextPriority == ThreadedRenderer.EGL_CONTEXT_PRIORITY_REALTIME_NV) {
            Log.i("SystemUIService", "Setting SysUI's GPU Context priority to: " + ThreadedRenderer.EGL_CONTEXT_PRIORITY_HIGH_IMG);
            ThreadedRenderer.setContextPriority(ThreadedRenderer.EGL_CONTEXT_PRIORITY_HIGH_IMG);
        }
        try {
            ActivityManager.getService().enableBinderTracing();
        } catch (RemoteException e) {
            Log.e("SystemUIService", "Unable to enable binder tracing", e);
        }
        registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.SystemUIApplication.1
            {
                SystemUIApplication.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (SystemUIApplication.this.mBootCompleteCache.isBootComplete()) {
                    return;
                }
                SystemUIApplication.this.unregisterReceiver(this);
                SystemUIApplication.this.mBootCompleteCache.setBootComplete();
                if (SystemUIApplication.this.mServicesStarted) {
                    int length = SystemUIApplication.this.mServices.length;
                    for (int i = 0; i < length; i++) {
                        SystemUIApplication.notifyBootCompleted(SystemUIApplication.this.mServices[i]);
                    }
                }
            }
        }, intentFilter);
        registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.SystemUIApplication.2
            {
                SystemUIApplication.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction()) && SystemUIApplication.this.mBootCompleteCache.isBootComplete()) {
                    NotificationChannels.createAll(context);
                }
            }
        }, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
    }

    public boolean removeDumpable(android.util.Dumpable dumpable) {
        Log.w("SystemUIService", "removeDumpable(" + dumpable + "): not implemented");
        return false;
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.mContextAvailableCallback = contextAvailableCallback;
    }

    public void startSecondaryUserServicesIfNeeded() {
        TreeMap treeMap = new TreeMap(Comparator.comparing(new ViewCapture$WindowListener$$ExternalSyntheticLambda3()));
        treeMap.putAll(this.mSysUIComponent.getPerUserStartables());
        startServicesIfNeeded(treeMap, "StartSecondaryServices", null);
    }

    public void startServicesIfNeeded() {
        String vendorComponent = this.mInitializer.getVendorComponent(getResources());
        TreeMap treeMap = new TreeMap(Comparator.comparing(new ViewCapture$WindowListener$$ExternalSyntheticLambda3()));
        treeMap.putAll(this.mSysUIComponent.getStartables());
        treeMap.putAll(this.mSysUIComponent.getPerUserStartables());
        startServicesIfNeeded(treeMap, "StartServices", vendorComponent);
    }

    public final void startServicesIfNeeded(Map<Class<?>, Provider<CoreStartable>> map, String str, final String str2) {
        if (this.mServicesStarted) {
            return;
        }
        this.mServices = new CoreStartable[map.size() + (str2 == null ? 0 : 1)];
        if (!this.mBootCompleteCache.isBootComplete() && "1".equals(SystemProperties.get("sys.boot_completed"))) {
            this.mBootCompleteCache.setBootComplete();
        }
        this.mDumpManager = this.mSysUIComponent.createDumpManager();
        Log.v("SystemUIService", "Starting SystemUI services for user " + Process.myUserHandle().getIdentifier() + ".");
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SystemUIBootTiming", 4096L);
        timingsTraceLog.traceBegin(str);
        int i = 0;
        for (final Map.Entry<Class<?>, Provider<CoreStartable>> entry : map.entrySet()) {
            final String name = entry.getKey().getName();
            final int i2 = i;
            timeInitialization(name, new Runnable() { // from class: com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SystemUIApplication.$r8$lambda$pt6oxwKYJNOsdTRRgkl96N87nEY(SystemUIApplication.this, i2, name, entry);
                }
            }, timingsTraceLog, str);
            i++;
        }
        int i3 = 0;
        if (str2 != null) {
            timeInitialization(str2, new Runnable() { // from class: com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SystemUIApplication.$r8$lambda$cXXQLUPGAUYtU2lktAZ_kDgvHXw(SystemUIApplication.this, str2);
                }
            }, timingsTraceLog, str);
            i3 = 0;
        }
        while (i3 < this.mServices.length) {
            if (this.mBootCompleteCache.isBootComplete()) {
                notifyBootCompleted(this.mServices[i3]);
            }
            this.mDumpManager.registerDumpable(this.mServices[i3].getClass().getName(), this.mServices[i3]);
            i3++;
        }
        this.mSysUIComponent.getInitController().executePostInitTasks();
        timingsTraceLog.traceEnd();
        this.mServicesStarted = true;
    }
}