package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.systemui.R$drawable;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/RenderInfo.class */
public final class RenderInfo {
    public final int enabledBackground;
    public final int foreground;
    public final Drawable icon;
    public static final Companion Companion = new Companion(null);
    public static final SparseArray<Drawable> iconMap = new SparseArray<>();
    public static final ArrayMap<ComponentName, Drawable> appIconMap = new ArrayMap<>();

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/RenderInfo$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ RenderInfo lookup$default(Companion companion, Context context, ComponentName componentName, int i, int i2, int i3, Object obj) {
            if ((i3 & 8) != 0) {
                i2 = 0;
            }
            return companion.lookup(context, componentName, i, i2);
        }

        public final void clearCache() {
            RenderInfo.iconMap.clear();
            RenderInfo.appIconMap.clear();
        }

        public final RenderInfo lookup(Context context, ComponentName componentName, int i, int i2) {
            Map map;
            Map map2;
            Drawable drawable;
            int i3 = i;
            if (i2 > 0) {
                i3 = (i * 1000) + i2;
            }
            map = RenderInfoKt.deviceColorMap;
            Pair pair = (Pair) MapsKt__MapsKt.getValue(map, Integer.valueOf(i3));
            int intValue = ((Number) pair.component1()).intValue();
            int intValue2 = ((Number) pair.component2()).intValue();
            map2 = RenderInfoKt.deviceIconMap;
            int intValue3 = ((Number) MapsKt__MapsKt.getValue(map2, Integer.valueOf(i3))).intValue();
            if (intValue3 == -1) {
                Drawable drawable2 = (Drawable) RenderInfo.appIconMap.get(componentName);
                drawable = drawable2;
                if (drawable2 == null) {
                    drawable = context.getResources().getDrawable(R$drawable.ic_device_unknown_on, null);
                    RenderInfo.appIconMap.put(componentName, drawable);
                }
            } else {
                Drawable drawable3 = (Drawable) RenderInfo.iconMap.get(intValue3);
                Drawable drawable4 = drawable3;
                if (drawable3 == null) {
                    drawable4 = context.getResources().getDrawable(intValue3, null);
                    RenderInfo.iconMap.put(intValue3, drawable4);
                }
                drawable = drawable4;
            }
            Intrinsics.checkNotNull(drawable);
            return new RenderInfo(drawable.getConstantState().newDrawable(context.getResources()), intValue, intValue2);
        }

        public final void registerComponentIcon(ComponentName componentName, Drawable drawable) {
            RenderInfo.appIconMap.put(componentName, drawable);
        }
    }

    public RenderInfo(Drawable drawable, int i, int i2) {
        this.icon = drawable;
        this.foreground = i;
        this.enabledBackground = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RenderInfo) {
            RenderInfo renderInfo = (RenderInfo) obj;
            return Intrinsics.areEqual(this.icon, renderInfo.icon) && this.foreground == renderInfo.foreground && this.enabledBackground == renderInfo.enabledBackground;
        }
        return false;
    }

    public final int getEnabledBackground() {
        return this.enabledBackground;
    }

    public final int getForeground() {
        return this.foreground;
    }

    public final Drawable getIcon() {
        return this.icon;
    }

    public int hashCode() {
        return (((this.icon.hashCode() * 31) + Integer.hashCode(this.foreground)) * 31) + Integer.hashCode(this.enabledBackground);
    }

    public String toString() {
        Drawable drawable = this.icon;
        int i = this.foreground;
        int i2 = this.enabledBackground;
        return "RenderInfo(icon=" + drawable + ", foreground=" + i + ", enabledBackground=" + i2 + ")";
    }
}