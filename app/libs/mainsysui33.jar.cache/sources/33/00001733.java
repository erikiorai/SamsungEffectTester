package com.android.systemui.dreams.dagger;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.dreams.DreamOverlayNotificationCountProvider;
import com.android.systemui.dreams.DreamOverlayService;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamModule.class */
public interface DreamModule {
    static boolean providesDreamOnlyEnabledForDockUser(Resources resources) {
        return resources.getBoolean(17891630);
    }

    static Boolean providesDreamOverlayEnabled(PackageManager packageManager, ComponentName componentName) {
        try {
            return Boolean.valueOf(packageManager.getServiceInfo(componentName, RecyclerView.ViewHolder.FLAG_IGNORE).enabled);
        } catch (PackageManager.NameNotFoundException e) {
            return Boolean.FALSE;
        }
    }

    static Optional<DreamOverlayNotificationCountProvider> providesDreamOverlayNotificationCountProvider() {
        return Optional.empty();
    }

    static ComponentName providesDreamOverlayService(Context context) {
        return new ComponentName(context, DreamOverlayService.class);
    }

    static boolean providesDreamSupported(Resources resources) {
        return resources.getBoolean(17891631);
    }
}