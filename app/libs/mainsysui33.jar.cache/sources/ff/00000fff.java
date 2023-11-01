package com.android.systemui;

import android.content.Context;

@Deprecated
/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIAppComponentFactory.class */
public class SystemUIAppComponentFactory extends SystemUIAppComponentFactoryBase {
    @Override // com.android.systemui.SystemUIAppComponentFactoryBase
    public SystemUIInitializer createSystemUIInitializer(Context context) {
        return SystemUIInitializerFactory.createWithContext(context);
    }
}