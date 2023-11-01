package com.android.systemui.classifier;

import android.content.Context;
import android.net.Uri;
import android.provider.DeviceConfig;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.FalsingPlugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.util.DeviceConfigProxy;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingManagerProxy.class */
public class FalsingManagerProxy implements FalsingManager, Dumpable {
    public final Provider<BrightLineFalsingManager> mBrightLineFalsingManagerProvider;
    public final DeviceConfigProxy mDeviceConfig;
    public final DeviceConfig.OnPropertiesChangedListener mDeviceConfigListener;
    public final DumpManager mDumpManager;
    public FalsingManager mInternalFalsingManager;
    public final PluginListener<FalsingPlugin> mPluginListener;
    public final PluginManager mPluginManager;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.FalsingManagerProxy$$ExternalSyntheticLambda0.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    /* renamed from: $r8$lambda$hCp_tSCnO9J-F4K1uJkYn_66Z-M */
    public static /* synthetic */ void m1700$r8$lambda$hCp_tSCnO9JF4K1uJkYn_66ZM(FalsingManagerProxy falsingManagerProxy, DeviceConfig.Properties properties) {
        falsingManagerProxy.lambda$new$0(properties);
    }

    public FalsingManagerProxy(PluginManager pluginManager, Executor executor, DeviceConfigProxy deviceConfigProxy, DumpManager dumpManager, Provider<BrightLineFalsingManager> provider) {
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.classifier.FalsingManagerProxy$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                FalsingManagerProxy.m1700$r8$lambda$hCp_tSCnO9JF4K1uJkYn_66ZM(FalsingManagerProxy.this, properties);
            }
        };
        this.mDeviceConfigListener = onPropertiesChangedListener;
        this.mPluginManager = pluginManager;
        this.mDumpManager = dumpManager;
        this.mDeviceConfig = deviceConfigProxy;
        this.mBrightLineFalsingManagerProvider = provider;
        setupFalsingManager();
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", executor, onPropertiesChangedListener);
        PluginListener<FalsingPlugin> pluginListener = new PluginListener<FalsingPlugin>() { // from class: com.android.systemui.classifier.FalsingManagerProxy.1
            {
                FalsingManagerProxy.this = this;
            }

            @Override // com.android.systemui.plugins.PluginListener
            public void onPluginConnected(FalsingPlugin falsingPlugin, Context context) {
                FalsingManager falsingManager = falsingPlugin.getFalsingManager(context);
                if (falsingManager != null) {
                    FalsingManagerProxy.this.mInternalFalsingManager.cleanupInternal();
                    FalsingManagerProxy.this.mInternalFalsingManager = falsingManager;
                }
            }

            @Override // com.android.systemui.plugins.PluginListener
            public void onPluginDisconnected(FalsingPlugin falsingPlugin) {
                FalsingManagerProxy.this.setupFalsingManager();
            }
        };
        this.mPluginListener = pluginListener;
        pluginManager.addPluginListener(pluginListener, FalsingPlugin.class);
        dumpManager.registerDumpable("FalsingManager", this);
    }

    public /* synthetic */ void lambda$new$0(DeviceConfig.Properties properties) {
        onDeviceConfigPropertiesChanged(properties.getNamespace());
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void addFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mInternalFalsingManager.addFalsingBeliefListener(falsingBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void addTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mInternalFalsingManager.addTapListener(falsingTapListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void cleanupInternal() {
        this.mDeviceConfig.removeOnPropertiesChangedListener(this.mDeviceConfigListener);
        this.mPluginManager.removePluginListener(this.mPluginListener);
        this.mDumpManager.unregisterDumpable("FalsingManager");
        this.mInternalFalsingManager.cleanupInternal();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void dump(PrintWriter printWriter, String[] strArr) {
        this.mInternalFalsingManager.dump(printWriter, strArr);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isClassifierEnabled() {
        return this.mInternalFalsingManager.isClassifierEnabled();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isFalseDoubleTap() {
        return this.mInternalFalsingManager.isFalseDoubleTap();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isFalseLongTap(int i) {
        return this.mInternalFalsingManager.isFalseLongTap(i);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isFalseTap(int i) {
        return this.mInternalFalsingManager.isFalseTap(i);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isFalseTouch(int i) {
        return this.mInternalFalsingManager.isFalseTouch(i);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isProximityNear() {
        return this.mInternalFalsingManager.isProximityNear();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isReportingEnabled() {
        return this.mInternalFalsingManager.isReportingEnabled();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isSimpleTap() {
        return this.mInternalFalsingManager.isSimpleTap();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isUnlockingDisabled() {
        return this.mInternalFalsingManager.isUnlockingDisabled();
    }

    public final void onDeviceConfigPropertiesChanged(String str) {
        if ("systemui".equals(str)) {
            setupFalsingManager();
        }
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
        this.mInternalFalsingManager.onProximityEvent(proximityEvent);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void onSuccessfulUnlock() {
        this.mInternalFalsingManager.onSuccessfulUnlock();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void removeFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mInternalFalsingManager.removeFalsingBeliefListener(falsingBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void removeTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mInternalFalsingManager.removeTapListener(falsingTapListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public Uri reportRejectedTouch() {
        return this.mInternalFalsingManager.reportRejectedTouch();
    }

    public final void setupFalsingManager() {
        FalsingManager falsingManager = this.mInternalFalsingManager;
        if (falsingManager != null) {
            falsingManager.cleanupInternal();
        }
        this.mInternalFalsingManager = (FalsingManager) this.mBrightLineFalsingManagerProvider.get();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean shouldEnforceBouncer() {
        return this.mInternalFalsingManager.shouldEnforceBouncer();
    }
}