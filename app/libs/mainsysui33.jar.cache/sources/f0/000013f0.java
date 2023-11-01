package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.controller.ControlInfo;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlInfoWrapper.class */
public final class ControlInfoWrapper extends ElementWrapper implements ControlInterface {
    public final ComponentName component;
    public final ControlInfo controlInfo;
    public Function2<? super ComponentName, ? super String, Icon> customIconGetter;
    public boolean favorite;

    public ControlInfoWrapper(ComponentName componentName, ControlInfo controlInfo, boolean z) {
        super(null);
        this.component = componentName;
        this.controlInfo = controlInfo;
        this.favorite = z;
        this.customIconGetter = ControlInfoWrapper$customIconGetter$1.INSTANCE;
    }

    public ControlInfoWrapper(ComponentName componentName, ControlInfo controlInfo, boolean z, Function2<? super ComponentName, ? super String, Icon> function2) {
        this(componentName, controlInfo, z);
        this.customIconGetter = function2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ControlInfoWrapper) {
            ControlInfoWrapper controlInfoWrapper = (ControlInfoWrapper) obj;
            return Intrinsics.areEqual(getComponent(), controlInfoWrapper.getComponent()) && Intrinsics.areEqual(this.controlInfo, controlInfoWrapper.controlInfo) && getFavorite() == controlInfoWrapper.getFavorite();
        }
        return false;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public ComponentName getComponent() {
        return this.component;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public String getControlId() {
        return this.controlInfo.getControlId();
    }

    public final ControlInfo getControlInfo() {
        return this.controlInfo;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public Icon getCustomIcon() {
        return (Icon) this.customIconGetter.invoke(getComponent(), getControlId());
    }

    @Override // com.android.systemui.controls.ControlInterface
    public int getDeviceType() {
        return this.controlInfo.getDeviceType();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public boolean getFavorite() {
        return this.favorite;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public boolean getRemoved() {
        return ControlInterface.DefaultImpls.getRemoved(this);
    }

    @Override // com.android.systemui.controls.ControlInterface
    public CharSequence getSubtitle() {
        return this.controlInfo.getControlSubtitle();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public CharSequence getTitle() {
        return this.controlInfo.getControlTitle();
    }

    public int hashCode() {
        int hashCode = getComponent().hashCode();
        int hashCode2 = this.controlInfo.hashCode();
        boolean favorite = getFavorite();
        int i = favorite;
        if (favorite) {
            i = 1;
        }
        return (((hashCode * 31) + hashCode2) * 31) + i;
    }

    public void setFavorite(boolean z) {
        this.favorite = z;
    }

    public String toString() {
        ComponentName component = getComponent();
        ControlInfo controlInfo = this.controlInfo;
        boolean favorite = getFavorite();
        return "ControlInfoWrapper(component=" + component + ", controlInfo=" + controlInfo + ", favorite=" + favorite + ")";
    }
}