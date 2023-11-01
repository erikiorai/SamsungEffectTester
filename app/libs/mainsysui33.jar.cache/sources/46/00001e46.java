package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.Bundle;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarComponent.class */
public interface NavigationBarComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarComponent$Factory.class */
    public interface Factory {
        NavigationBarComponent create(Context context, Bundle bundle);
    }

    NavigationBar getNavigationBar();
}