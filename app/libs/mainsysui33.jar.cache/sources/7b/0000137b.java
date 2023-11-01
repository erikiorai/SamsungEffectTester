package com.android.systemui.controls;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/CustomIconCache.class */
public final class CustomIconCache {
    public final Map<String, Icon> cache = new LinkedHashMap();
    public ComponentName currentComponent;

    public final void clear() {
        synchronized (this.cache) {
            this.cache.clear();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final Icon retrieve(ComponentName componentName, String str) {
        Icon icon;
        if (Intrinsics.areEqual(componentName, this.currentComponent)) {
            synchronized (this.cache) {
                icon = this.cache.get(str);
            }
            return icon;
        }
        return null;
    }

    public final void store(ComponentName componentName, String str, Icon icon) {
        if (!Intrinsics.areEqual(componentName, this.currentComponent)) {
            clear();
            this.currentComponent = componentName;
        }
        synchronized (this.cache) {
            if (icon != null) {
                this.cache.put(str, icon);
            } else {
                this.cache.remove(str);
            }
        }
    }
}