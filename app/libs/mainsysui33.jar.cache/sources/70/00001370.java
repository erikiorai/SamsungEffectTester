package com.android.systemui.controls;

import android.content.ComponentName;
import android.graphics.drawable.Icon;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ControlInterface.class */
public interface ControlInterface {

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ControlInterface$DefaultImpls.class */
    public static final class DefaultImpls {
        public static boolean getRemoved(ControlInterface controlInterface) {
            return false;
        }
    }

    ComponentName getComponent();

    String getControlId();

    Icon getCustomIcon();

    int getDeviceType();

    boolean getFavorite();

    boolean getRemoved();

    CharSequence getSubtitle();

    CharSequence getTitle();
}