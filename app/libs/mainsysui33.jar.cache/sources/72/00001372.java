package com.android.systemui.controls;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.service.controls.Control;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ControlStatus.class */
public final class ControlStatus implements ControlInterface {
    public final ComponentName component;
    public final Control control;
    public boolean favorite;
    public final boolean removed;

    public ControlStatus(Control control, ComponentName componentName, boolean z, boolean z2) {
        this.control = control;
        this.component = componentName;
        this.favorite = z;
        this.removed = z2;
    }

    public /* synthetic */ ControlStatus(Control control, ComponentName componentName, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(control, componentName, z, (i & 8) != 0 ? false : z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ControlStatus) {
            ControlStatus controlStatus = (ControlStatus) obj;
            return Intrinsics.areEqual(this.control, controlStatus.control) && Intrinsics.areEqual(getComponent(), controlStatus.getComponent()) && getFavorite() == controlStatus.getFavorite() && getRemoved() == controlStatus.getRemoved();
        }
        return false;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public ComponentName getComponent() {
        return this.component;
    }

    public final Control getControl() {
        return this.control;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public String getControlId() {
        return this.control.getControlId();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public Icon getCustomIcon() {
        return this.control.getCustomIcon();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public int getDeviceType() {
        return this.control.getDeviceType();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public boolean getFavorite() {
        return this.favorite;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public boolean getRemoved() {
        return this.removed;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public CharSequence getSubtitle() {
        return this.control.getSubtitle();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public CharSequence getTitle() {
        return this.control.getTitle();
    }

    public int hashCode() {
        int hashCode = this.control.hashCode();
        int hashCode2 = getComponent().hashCode();
        boolean favorite = getFavorite();
        int i = 1;
        int i2 = favorite;
        if (favorite) {
            i2 = 1;
        }
        boolean removed = getRemoved();
        if (!removed) {
            i = removed;
        }
        return (((((hashCode * 31) + hashCode2) * 31) + i2) * 31) + i;
    }

    public void setFavorite(boolean z) {
        this.favorite = z;
    }

    public String toString() {
        Control control = this.control;
        ComponentName component = getComponent();
        boolean favorite = getFavorite();
        boolean removed = getRemoved();
        return "ControlStatus(control=" + control + ", component=" + component + ", favorite=" + favorite + ", removed=" + removed + ")";
    }
}