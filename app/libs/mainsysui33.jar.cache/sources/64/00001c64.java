package com.android.systemui.media.controls.ui;

import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/CacheKey.class */
public final class CacheKey {
    public float expansion;
    public boolean gutsVisible;
    public int heightMeasureSpec;
    public int widthMeasureSpec;

    public CacheKey() {
        this(0, 0, ActionBarShadowController.ELEVATION_LOW, false, 15, null);
    }

    public CacheKey(int i, int i2, float f, boolean z) {
        this.widthMeasureSpec = i;
        this.heightMeasureSpec = i2;
        this.expansion = f;
        this.gutsVisible = z;
    }

    public /* synthetic */ CacheKey(int i, int i2, float f, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? -1 : i2, (i3 & 4) != 0 ? 0.0f : f, (i3 & 8) != 0 ? false : z);
    }

    public static /* synthetic */ CacheKey copy$default(CacheKey cacheKey, int i, int i2, float f, boolean z, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = cacheKey.widthMeasureSpec;
        }
        if ((i3 & 2) != 0) {
            i2 = cacheKey.heightMeasureSpec;
        }
        if ((i3 & 4) != 0) {
            f = cacheKey.expansion;
        }
        if ((i3 & 8) != 0) {
            z = cacheKey.gutsVisible;
        }
        return cacheKey.copy(i, i2, f, z);
    }

    public final CacheKey copy(int i, int i2, float f, boolean z) {
        return new CacheKey(i, i2, f, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CacheKey) {
            CacheKey cacheKey = (CacheKey) obj;
            return this.widthMeasureSpec == cacheKey.widthMeasureSpec && this.heightMeasureSpec == cacheKey.heightMeasureSpec && Float.compare(this.expansion, cacheKey.expansion) == 0 && this.gutsVisible == cacheKey.gutsVisible;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.widthMeasureSpec);
        int hashCode2 = Integer.hashCode(this.heightMeasureSpec);
        int hashCode3 = Float.hashCode(this.expansion);
        boolean z = this.gutsVisible;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        return (((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + i;
    }

    public final void setExpansion(float f) {
        this.expansion = f;
    }

    public final void setGutsVisible(boolean z) {
        this.gutsVisible = z;
    }

    public final void setHeightMeasureSpec(int i) {
        this.heightMeasureSpec = i;
    }

    public final void setWidthMeasureSpec(int i) {
        this.widthMeasureSpec = i;
    }

    public String toString() {
        int i = this.widthMeasureSpec;
        int i2 = this.heightMeasureSpec;
        float f = this.expansion;
        boolean z = this.gutsVisible;
        return "CacheKey(widthMeasureSpec=" + i + ", heightMeasureSpec=" + i2 + ", expansion=" + f + ", gutsVisible=" + z + ")";
    }
}