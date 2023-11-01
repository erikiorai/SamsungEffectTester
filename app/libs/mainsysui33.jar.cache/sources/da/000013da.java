package com.android.systemui.controls.dagger;

import android.content.pm.PackageManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/dagger/ControlsModule.class */
public abstract class ControlsModule {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/dagger/ControlsModule$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean providesControlsFeatureEnabled(PackageManager packageManager) {
            return packageManager.hasSystemFeature("android.software.controls");
        }
    }

    public static final boolean providesControlsFeatureEnabled(PackageManager packageManager) {
        return Companion.providesControlsFeatureEnabled(packageManager);
    }
}