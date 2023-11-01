package com.android.systemui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Build;
import android.provider.DeviceConfig;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/LatencyTester.class */
public class LatencyTester implements CoreStartable {
    public static final boolean DEFAULT_ENABLED = Build.IS_ENG;
    public final BiometricUnlockController mBiometricUnlockController;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.LatencyTester.1
        {
            LatencyTester.this = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.systemui.latency.ACTION_FINGERPRINT_WAKE".equals(action)) {
                LatencyTester.this.fakeWakeAndUnlock(BiometricSourceType.FINGERPRINT);
            } else if ("com.android.systemui.latency.ACTION_FACE_WAKE".equals(action)) {
                LatencyTester.this.fakeWakeAndUnlock(BiometricSourceType.FACE);
            }
        }
    };
    public final DeviceConfigProxy mDeviceConfigProxy;
    public boolean mEnabled;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.LatencyTester$$ExternalSyntheticLambda0.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static /* synthetic */ void $r8$lambda$B3Tbaq_Sz7x5WQXlFigo8yifUR0(LatencyTester latencyTester, DeviceConfig.Properties properties) {
        latencyTester.lambda$new$0(properties);
    }

    public LatencyTester(BiometricUnlockController biometricUnlockController, BroadcastDispatcher broadcastDispatcher, DeviceConfigProxy deviceConfigProxy, DelayableExecutor delayableExecutor) {
        this.mBiometricUnlockController = biometricUnlockController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDeviceConfigProxy = deviceConfigProxy;
        updateEnabled();
        deviceConfigProxy.addOnPropertiesChangedListener("latency_tracker", delayableExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.LatencyTester$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                LatencyTester.$r8$lambda$B3Tbaq_Sz7x5WQXlFigo8yifUR0(LatencyTester.this, properties);
            }
        });
    }

    public /* synthetic */ void lambda$new$0(DeviceConfig.Properties properties) {
        updateEnabled();
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mEnabled=" + this.mEnabled);
    }

    public final void fakeWakeAndUnlock(BiometricSourceType biometricSourceType) {
        if (this.mEnabled) {
            this.mBiometricUnlockController.onBiometricAcquired(biometricSourceType, 0);
            this.mBiometricUnlockController.onBiometricAuthenticated(KeyguardUpdateMonitor.getCurrentUser(), biometricSourceType, true);
        }
    }

    public final void registerForBroadcasts(boolean z) {
        if (!z) {
            this.mBroadcastDispatcher.unregisterReceiver(this.mBroadcastReceiver);
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.latency.ACTION_FINGERPRINT_WAKE");
        intentFilter.addAction("com.android.systemui.latency.ACTION_FACE_WAKE");
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        registerForBroadcasts(this.mEnabled);
    }

    public final void updateEnabled() {
        boolean z = this.mEnabled;
        boolean z2 = Build.IS_DEBUGGABLE && this.mDeviceConfigProxy.getBoolean("latency_tracker", "enabled", DEFAULT_ENABLED);
        this.mEnabled = z2;
        if (z2 != z) {
            registerForBroadcasts(z2);
        }
    }
}