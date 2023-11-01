package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Handler;
import android.view.Display;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/BiometricDisplayListener.class */
public final class BiometricDisplayListener implements DisplayManager.DisplayListener {
    public final Context context;
    public final DisplayManager displayManager;
    public final Handler handler;
    public int lastRotation;
    public final Function0<Unit> onChanged;
    public final SensorType sensorType;

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/BiometricDisplayListener$SensorType.class */
    public static abstract class SensorType {

        /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/BiometricDisplayListener$SensorType$Generic.class */
        public static final class Generic extends SensorType {
            public static final Generic INSTANCE = new Generic();

            public Generic() {
                super(null);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/BiometricDisplayListener$SensorType$SideFingerprint.class */
        public static final class SideFingerprint extends SensorType {
            public final FingerprintSensorPropertiesInternal properties;

            public SideFingerprint(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
                super(null);
                this.properties = fingerprintSensorPropertiesInternal;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof SideFingerprint) && Intrinsics.areEqual(this.properties, ((SideFingerprint) obj).properties);
            }

            public int hashCode() {
                return this.properties.hashCode();
            }

            public String toString() {
                FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.properties;
                return "SideFingerprint(properties=" + fingerprintSensorPropertiesInternal + ")";
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/BiometricDisplayListener$SensorType$UnderDisplayFingerprint.class */
        public static final class UnderDisplayFingerprint extends SensorType {
            public static final UnderDisplayFingerprint INSTANCE = new UnderDisplayFingerprint();

            public UnderDisplayFingerprint() {
                super(null);
            }
        }

        public SensorType() {
        }

        public /* synthetic */ SensorType(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BiometricDisplayListener(Context context, DisplayManager displayManager, Handler handler, SensorType sensorType, Function0<Unit> function0) {
        this.context = context;
        this.displayManager = displayManager;
        this.handler = handler;
        this.sensorType = sensorType;
        this.onChanged = function0;
    }

    public final boolean didRotationChange() {
        Display display = this.context.getDisplay();
        boolean z = false;
        if (display != null) {
            int rotation = display.getRotation();
            int i = this.lastRotation;
            this.lastRotation = rotation;
            z = false;
            if (i != rotation) {
                z = true;
            }
        }
        return z;
    }

    public final void disable() {
        this.displayManager.unregisterDisplayListener(this);
    }

    public final void enable() {
        Display display = this.context.getDisplay();
        this.lastRotation = display != null ? display.getRotation() : 0;
        this.displayManager.registerDisplayListener(this, this.handler, 4L);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        boolean didRotationChange = didRotationChange();
        if (this.sensorType instanceof SensorType.SideFingerprint) {
            this.onChanged.invoke();
        } else if (didRotationChange) {
            this.onChanged.invoke();
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
    }
}