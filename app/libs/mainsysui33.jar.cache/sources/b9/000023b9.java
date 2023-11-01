package com.android.systemui.recents;

import android.content.Context;
import com.android.systemui.R$string;
import com.android.systemui.dagger.ContextComponentHelper;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/RecentsModule.class */
public abstract class RecentsModule {
    public static RecentsImplementation provideRecentsImpl(Context context, ContextComponentHelper contextComponentHelper) {
        String string = context.getString(R$string.config_recentsComponent);
        if (string == null || string.length() == 0) {
            throw new RuntimeException("No recents component configured", null);
        }
        RecentsImplementation resolveRecents = contextComponentHelper.resolveRecents(string);
        RecentsImplementation recentsImplementation = resolveRecents;
        if (resolveRecents == null) {
            try {
                try {
                    recentsImplementation = (RecentsImplementation) context.getClassLoader().loadClass(string).newInstance();
                } catch (Throwable th) {
                    throw new RuntimeException("Error creating recents component: " + string, th);
                }
            } catch (Throwable th2) {
                throw new RuntimeException("Error loading recents component: " + string, th2);
            }
        }
        return recentsImplementation;
    }
}