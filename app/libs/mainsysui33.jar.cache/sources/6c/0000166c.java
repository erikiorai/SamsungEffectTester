package com.android.systemui.doze;

import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.os.SystemClock;
import android.service.dreams.DreamService;
import android.util.Log;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.plugins.DozeServicePlugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeService.class */
public class DozeService extends DreamService implements DozeMachine.Service, DozeServicePlugin.RequestDoze, PluginListener<DozeServicePlugin> {
    public static final boolean DEBUG = Log.isLoggable("DozeService", 3);
    public final DozeComponent.Builder mDozeComponentBuilder;
    public DozeMachine mDozeMachine;
    public DozeServicePlugin mDozePlugin;
    public PluginManager mPluginManager;

    public DozeService(DozeComponent.Builder builder, PluginManager pluginManager) {
        this.mDozeComponentBuilder = builder;
        setDebug(DEBUG);
        this.mPluginManager = pluginManager;
    }

    public void dumpOnHandler(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dumpOnHandler(fileDescriptor, printWriter, strArr);
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.dump(printWriter);
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDozeMachine.onConfigurationChanged(configuration);
    }

    @Override // android.service.dreams.DreamService, android.app.Service
    public void onCreate() {
        super.onCreate();
        setWindowless(true);
        this.mPluginManager.addPluginListener((PluginListener) this, DozeServicePlugin.class, false);
        DozeMachine dozeMachine = this.mDozeComponentBuilder.build(this).getDozeMachine();
        this.mDozeMachine = dozeMachine;
        dozeMachine.onConfigurationChanged(getResources().getConfiguration());
    }

    @Override // android.service.dreams.DreamService, android.app.Service
    public void onDestroy() {
        PluginManager pluginManager = this.mPluginManager;
        if (pluginManager != null) {
            pluginManager.removePluginListener(this);
        }
        super.onDestroy();
        this.mDozeMachine.destroy();
        this.mDozeMachine = null;
    }

    @Override // android.service.dreams.DreamService
    public void onDreamingStarted() {
        super.onDreamingStarted();
        this.mDozeMachine.requestState(DozeMachine.State.INITIALIZED);
        startDozing();
        DozeServicePlugin dozeServicePlugin = this.mDozePlugin;
        if (dozeServicePlugin != null) {
            dozeServicePlugin.onDreamingStarted();
        }
    }

    @Override // android.service.dreams.DreamService
    public void onDreamingStopped() {
        super.onDreamingStopped();
        this.mDozeMachine.requestState(DozeMachine.State.FINISH);
        DozeServicePlugin dozeServicePlugin = this.mDozePlugin;
        if (dozeServicePlugin != null) {
            dozeServicePlugin.onDreamingStopped();
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(DozeServicePlugin dozeServicePlugin, Context context) {
        this.mDozePlugin = dozeServicePlugin;
        dozeServicePlugin.setDozeRequester(this);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(DozeServicePlugin dozeServicePlugin) {
        DozeServicePlugin dozeServicePlugin2 = this.mDozePlugin;
        if (dozeServicePlugin2 != null) {
            dozeServicePlugin2.onDreamingStopped();
            this.mDozePlugin = null;
        }
    }

    @Override // com.android.systemui.plugins.DozeServicePlugin.RequestDoze
    public void onRequestHideDoze() {
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.requestState(DozeMachine.State.DOZE);
        }
    }

    @Override // com.android.systemui.plugins.DozeServicePlugin.RequestDoze
    public void onRequestShowDoze() {
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.requestState(DozeMachine.State.DOZE_AOD);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Service
    public void requestWakeUp(int i) {
        PowerManager powerManager = (PowerManager) getSystemService(PowerManager.class);
        long uptimeMillis = SystemClock.uptimeMillis();
        int powerManagerWakeReason = DozeLog.getPowerManagerWakeReason(i);
        powerManager.wakeUp(uptimeMillis, powerManagerWakeReason, "com.android.systemui:NODOZE " + DozeLog.reasonToString(i));
    }

    @Override // com.android.systemui.doze.DozeMachine.Service
    public void setDozeScreenState(int i) {
        super.setDozeScreenState(i);
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.onScreenState(i);
        }
    }
}