package com.android.systemui;

import com.android.systemui.dagger.ContextComponentHelper;
import dagger.MembersInjector;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIAppComponentFactoryBase_MembersInjector.class */
public final class SystemUIAppComponentFactoryBase_MembersInjector implements MembersInjector<SystemUIAppComponentFactoryBase> {
    public static void injectSetComponentHelper(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase, ContextComponentHelper contextComponentHelper) {
        systemUIAppComponentFactoryBase.setComponentHelper(contextComponentHelper);
    }
}