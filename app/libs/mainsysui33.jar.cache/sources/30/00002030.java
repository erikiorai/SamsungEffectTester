package com.android.systemui.privacy;

import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyConfig.class */
public final class PrivacyConfig implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public final DeviceConfigProxy deviceConfigProxy;
    public final DeviceConfig.OnPropertiesChangedListener devicePropertiesChangedListener;
    public final DelayableExecutor uiExecutor;
    public final List<WeakReference<Callback>> callbacks = new ArrayList();
    public boolean micCameraAvailable = isMicCameraEnabled();
    public boolean locationAvailable = isLocationEnabled();
    public boolean mediaProjectionAvailable = isMediaProjectionEnabled();

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyConfig$Callback.class */
    public interface Callback {
        default void onFlagLocationChanged(boolean z) {
        }

        default void onFlagMediaProjectionChanged(boolean z) {
        }

        default void onFlagMicCameraChanged(boolean z) {
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyConfig$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PrivacyConfig(DelayableExecutor delayableExecutor, DeviceConfigProxy deviceConfigProxy, DumpManager dumpManager) {
        this.uiExecutor = delayableExecutor;
        this.deviceConfigProxy = deviceConfigProxy;
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (Intrinsics.areEqual("privacy", properties.getNamespace())) {
                    if (properties.getKeyset().contains("camera_mic_icons_enabled")) {
                        PrivacyConfig.access$setMicCameraAvailable$p(PrivacyConfig.this, properties.getBoolean("camera_mic_icons_enabled", true));
                        List<WeakReference> access$getCallbacks$p = PrivacyConfig.access$getCallbacks$p(PrivacyConfig.this);
                        PrivacyConfig privacyConfig = PrivacyConfig.this;
                        for (WeakReference weakReference : access$getCallbacks$p) {
                            PrivacyConfig.Callback callback = (PrivacyConfig.Callback) weakReference.get();
                            if (callback != null) {
                                callback.onFlagMicCameraChanged(privacyConfig.getMicCameraAvailable());
                            }
                        }
                    }
                    if (properties.getKeyset().contains("location_indicators_enabled")) {
                        PrivacyConfig.access$setLocationAvailable$p(PrivacyConfig.this, properties.getBoolean("location_indicators_enabled", false));
                        List<WeakReference> access$getCallbacks$p2 = PrivacyConfig.access$getCallbacks$p(PrivacyConfig.this);
                        PrivacyConfig privacyConfig2 = PrivacyConfig.this;
                        for (WeakReference weakReference2 : access$getCallbacks$p2) {
                            PrivacyConfig.Callback callback2 = (PrivacyConfig.Callback) weakReference2.get();
                            if (callback2 != null) {
                                callback2.onFlagLocationChanged(privacyConfig2.getLocationAvailable());
                            }
                        }
                    }
                    if (properties.getKeyset().contains("media_projection_indicators_enabled")) {
                        PrivacyConfig.access$setMediaProjectionAvailable$p(PrivacyConfig.this, properties.getBoolean("media_projection_indicators_enabled", true));
                        List<WeakReference> access$getCallbacks$p3 = PrivacyConfig.access$getCallbacks$p(PrivacyConfig.this);
                        PrivacyConfig privacyConfig3 = PrivacyConfig.this;
                        for (WeakReference weakReference3 : access$getCallbacks$p3) {
                            PrivacyConfig.Callback callback3 = (PrivacyConfig.Callback) weakReference3.get();
                            if (callback3 != null) {
                                callback3.onFlagMediaProjectionChanged(privacyConfig3.getMediaProjectionAvailable());
                            }
                        }
                    }
                }
            }
        };
        this.devicePropertiesChangedListener = onPropertiesChangedListener;
        DumpManager.registerDumpable$default(dumpManager, "PrivacyConfig", this, null, 4, null);
        deviceConfigProxy.addOnPropertiesChangedListener("privacy", (Executor) delayableExecutor, onPropertiesChangedListener);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyConfig$addCallback$1.run():void, com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ List access$getCallbacks$p(PrivacyConfig privacyConfig) {
        return privacyConfig.callbacks;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ void access$setLocationAvailable$p(PrivacyConfig privacyConfig, boolean z) {
        privacyConfig.locationAvailable = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ void access$setMediaProjectionAvailable$p(PrivacyConfig privacyConfig, boolean z) {
        privacyConfig.mediaProjectionAvailable = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ void access$setMicCameraAvailable$p(PrivacyConfig privacyConfig, boolean z) {
        privacyConfig.micCameraAvailable = z;
    }

    public final void addCallback(Callback callback) {
        addCallback(new WeakReference<>(callback));
    }

    public final void addCallback(final WeakReference<Callback> weakReference) {
        this.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyConfig$addCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                PrivacyConfig.access$getCallbacks$p(PrivacyConfig.this).add(weakReference);
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("PrivacyConfig state:");
        asIndenting.increaseIndent();
        try {
            boolean z = this.micCameraAvailable;
            asIndenting.println("micCameraAvailable: " + z);
            boolean z2 = this.locationAvailable;
            asIndenting.println("locationAvailable: " + z2);
            boolean z3 = this.mediaProjectionAvailable;
            asIndenting.println("mediaProjectionAvailable: " + z3);
            asIndenting.println("Callbacks:");
            asIndenting.increaseIndent();
            Iterator<T> it = this.callbacks.iterator();
            while (it.hasNext()) {
                Callback callback = (Callback) ((WeakReference) it.next()).get();
                if (callback != null) {
                    asIndenting.println(callback);
                }
            }
            asIndenting.decreaseIndent();
            asIndenting.decreaseIndent();
            asIndenting.flush();
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    public final boolean getLocationAvailable() {
        return this.locationAvailable;
    }

    public final boolean getMediaProjectionAvailable() {
        return this.mediaProjectionAvailable;
    }

    public final boolean getMicCameraAvailable() {
        return this.micCameraAvailable;
    }

    public final boolean isLocationEnabled() {
        return this.deviceConfigProxy.getBoolean("privacy", "location_indicators_enabled", false);
    }

    public final boolean isMediaProjectionEnabled() {
        return this.deviceConfigProxy.getBoolean("privacy", "media_projection_indicators_enabled", true);
    }

    public final boolean isMicCameraEnabled() {
        return this.deviceConfigProxy.getBoolean("privacy", "camera_mic_icons_enabled", true);
    }
}