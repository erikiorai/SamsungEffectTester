package com.android.systemui.biometrics;

import android.graphics.Rect;
import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsOverlayParams.class */
public final class UdfpsOverlayParams {
    public final int logicalDisplayHeight;
    public final int logicalDisplayWidth;
    public final Rect nativeSensorBounds;
    public final int naturalDisplayHeight;
    public final int naturalDisplayWidth;
    public final Rect overlayBounds;
    public final int rotation;
    public final float scaleFactor;
    public final Rect sensorBounds;

    public UdfpsOverlayParams() {
        this(null, null, 0, 0, ActionBarShadowController.ELEVATION_LOW, 0, 63, null);
    }

    public UdfpsOverlayParams(Rect rect, Rect rect2, int i, int i2, float f, int i3) {
        this.sensorBounds = rect;
        this.overlayBounds = rect2;
        this.naturalDisplayWidth = i;
        this.naturalDisplayHeight = i2;
        this.scaleFactor = f;
        this.rotation = i3;
        Rect rect3 = new Rect(rect);
        rect3.scale(1.0f / f);
        this.nativeSensorBounds = rect3;
        this.logicalDisplayWidth = (i3 == 1 || i3 == 3) ? i2 : i;
        this.logicalDisplayHeight = i3 != 1 ? i3 == 3 ? i : i2 : i;
    }

    public /* synthetic */ UdfpsOverlayParams(Rect rect, Rect rect2, int i, int i2, float f, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? new Rect() : rect, (i4 & 2) != 0 ? new Rect() : rect2, (i4 & 4) != 0 ? 0 : i, (i4 & 8) != 0 ? 0 : i2, (i4 & 16) != 0 ? 1.0f : f, (i4 & 32) != 0 ? 0 : i3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UdfpsOverlayParams) {
            UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) obj;
            return Intrinsics.areEqual(this.sensorBounds, udfpsOverlayParams.sensorBounds) && Intrinsics.areEqual(this.overlayBounds, udfpsOverlayParams.overlayBounds) && this.naturalDisplayWidth == udfpsOverlayParams.naturalDisplayWidth && this.naturalDisplayHeight == udfpsOverlayParams.naturalDisplayHeight && Float.compare(this.scaleFactor, udfpsOverlayParams.scaleFactor) == 0 && this.rotation == udfpsOverlayParams.rotation;
        }
        return false;
    }

    public final int getLogicalDisplayHeight() {
        return this.logicalDisplayHeight;
    }

    public final int getLogicalDisplayWidth() {
        return this.logicalDisplayWidth;
    }

    public final Rect getNativeSensorBounds() {
        return this.nativeSensorBounds;
    }

    public final int getNaturalDisplayHeight() {
        return this.naturalDisplayHeight;
    }

    public final int getNaturalDisplayWidth() {
        return this.naturalDisplayWidth;
    }

    public final Rect getOverlayBounds() {
        return this.overlayBounds;
    }

    public final int getRotation() {
        return this.rotation;
    }

    public final float getScaleFactor() {
        return this.scaleFactor;
    }

    public final Rect getSensorBounds() {
        return this.sensorBounds;
    }

    public int hashCode() {
        return (((((((((this.sensorBounds.hashCode() * 31) + this.overlayBounds.hashCode()) * 31) + Integer.hashCode(this.naturalDisplayWidth)) * 31) + Integer.hashCode(this.naturalDisplayHeight)) * 31) + Float.hashCode(this.scaleFactor)) * 31) + Integer.hashCode(this.rotation);
    }

    public String toString() {
        Rect rect = this.sensorBounds;
        Rect rect2 = this.overlayBounds;
        int i = this.naturalDisplayWidth;
        int i2 = this.naturalDisplayHeight;
        float f = this.scaleFactor;
        int i3 = this.rotation;
        return "UdfpsOverlayParams(sensorBounds=" + rect + ", overlayBounds=" + rect2 + ", naturalDisplayWidth=" + i + ", naturalDisplayHeight=" + i2 + ", scaleFactor=" + f + ", rotation=" + i3 + ")";
    }
}