package com.android.systemui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.util.Assert;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIInitializerFactory.class */
public final class SystemUIInitializerFactory {
    public static final SystemUIInitializerFactory INSTANCE = new SystemUIInitializerFactory();
    @SuppressLint({"StaticFieldLeak"})
    public static SystemUIInitializer initializer;

    public static final SystemUIInitializer createFromConfig(Context context) {
        Assert.isMainThread();
        return createFromConfigNoAssert(context);
    }

    @VisibleForTesting
    public static final SystemUIInitializer createFromConfigNoAssert(Context context) {
        SystemUIInitializer systemUIInitializer = initializer;
        SystemUIInitializer systemUIInitializer2 = systemUIInitializer;
        if (systemUIInitializer == null) {
            String string = context.getString(R$string.config_systemUIFactoryComponent);
            if (string.length() == 0) {
                throw new RuntimeException("No SystemUIFactory component configured");
            }
            try {
                systemUIInitializer2 = (SystemUIInitializer) context.getClassLoader().loadClass(string).getConstructor(Context.class).newInstance(context);
                initializer = systemUIInitializer2;
            } catch (Throwable th) {
                Log.w("SysUIInitializerFactory", "Error creating SystemUIInitializer component: " + string, th);
                throw th;
            }
        }
        return systemUIInitializer2;
    }

    public static final SystemUIInitializer createWithContext(Context context) {
        return createFromConfig(context);
    }
}