package com.android.systemui.dagger;

import android.content.Context;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.WMComponent;
import com.android.systemui.util.InitializationChecker;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/GlobalRootComponent.class */
public interface GlobalRootComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/GlobalRootComponent$Builder.class */
    public interface Builder {
        GlobalRootComponent build();

        Builder context(Context context);

        Builder instrumentationTest(boolean z);
    }

    InitializationChecker getInitializationChecker();

    SysUIComponent.Builder getSysUIComponent();

    WMComponent.Builder getWMComponentBuilder();
}