package com.android.systemui.decor;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.DisplayUtils;
import android.util.Size;
import android.view.RoundedCorners;
import com.android.systemui.Dumpable;
import com.android.systemui.R$array;
import com.android.systemui.R$drawable;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/RoundedCornerResDelegate.class */
public final class RoundedCornerResDelegate implements Dumpable {
    public Drawable bottomRoundedDrawable;
    public String displayUniqueId;
    public boolean hasBottom;
    public boolean hasTop;
    public int reloadToken;
    public final Resources res;
    public Drawable topRoundedDrawable;
    public Integer tuningSizeFactor;
    public Size topRoundedSize = new Size(0, 0);
    public Size bottomRoundedSize = new Size(0, 0);
    public float physicalPixelDisplaySizeRatio = 1.0f;

    public RoundedCornerResDelegate(Resources resources, String str) {
        this.res = resources;
        this.displayUniqueId = str;
        reloadRes();
        reloadMeasures();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("RoundedCornerResDelegate state:");
        boolean z = this.hasTop;
        printWriter.println("  hasTop=" + z);
        boolean z2 = this.hasBottom;
        printWriter.println("  hasBottom=" + z2);
        int width = this.topRoundedSize.getWidth();
        int height = this.topRoundedSize.getHeight();
        printWriter.println("  topRoundedSize(w,h)=(" + width + "," + height + ")");
        int width2 = this.bottomRoundedSize.getWidth();
        int height2 = this.bottomRoundedSize.getHeight();
        printWriter.println("  bottomRoundedSize(w,h)=(" + width2 + "," + height2 + ")");
        float f = this.physicalPixelDisplaySizeRatio;
        StringBuilder sb = new StringBuilder();
        sb.append("  physicalPixelDisplaySizeRatio=");
        sb.append(f);
        printWriter.println(sb.toString());
    }

    public final Drawable getBottomRoundedDrawable() {
        return this.bottomRoundedDrawable;
    }

    public final Size getBottomRoundedSize() {
        return this.bottomRoundedSize;
    }

    public final float getDensity() {
        return this.res.getDisplayMetrics().density;
    }

    public final Drawable getDrawable(int i, int i2, int i3) {
        TypedArray obtainTypedArray = this.res.obtainTypedArray(i2);
        Drawable drawable = (i < 0 || i >= obtainTypedArray.length()) ? this.res.getDrawable(i3, null) : obtainTypedArray.getDrawable(i);
        obtainTypedArray.recycle();
        return drawable;
    }

    public final boolean getHasBottom() {
        return this.hasBottom;
    }

    public final boolean getHasTop() {
        return this.hasTop;
    }

    public final Drawable getTopRoundedDrawable() {
        return this.topRoundedDrawable;
    }

    public final Size getTopRoundedSize() {
        return this.topRoundedSize;
    }

    public final void reloadMeasures() {
        Drawable drawable = this.topRoundedDrawable;
        if (drawable != null) {
            this.topRoundedSize = new Size(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        Drawable drawable2 = this.bottomRoundedDrawable;
        if (drawable2 != null) {
            this.bottomRoundedSize = new Size(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        }
        Integer num = this.tuningSizeFactor;
        if (num != null) {
            int intValue = num.intValue();
            if (intValue <= 0) {
                return;
            }
            int density = (int) (intValue * getDensity());
            if (this.topRoundedSize.getWidth() > 0) {
                this.topRoundedSize = new Size(density, density);
            }
            if (this.bottomRoundedSize.getWidth() > 0) {
                this.bottomRoundedSize = new Size(density, density);
            }
        }
        if (this.physicalPixelDisplaySizeRatio == 1.0f) {
            return;
        }
        if (this.topRoundedSize.getWidth() != 0) {
            this.topRoundedSize = new Size((int) ((this.physicalPixelDisplaySizeRatio * this.topRoundedSize.getWidth()) + 0.5f), (int) ((this.physicalPixelDisplaySizeRatio * this.topRoundedSize.getHeight()) + 0.5f));
        }
        if (this.bottomRoundedSize.getWidth() != 0) {
            this.bottomRoundedSize = new Size((int) ((this.physicalPixelDisplaySizeRatio * this.bottomRoundedSize.getWidth()) + 0.5f), (int) ((this.physicalPixelDisplaySizeRatio * this.bottomRoundedSize.getHeight()) + 0.5f));
        }
    }

    public final void reloadRes() {
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(this.res, this.displayUniqueId);
        boolean z = RoundedCorners.getRoundedCornerRadius(this.res, this.displayUniqueId) > 0;
        this.hasTop = z || RoundedCorners.getRoundedCornerTopRadius(this.res, this.displayUniqueId) > 0;
        boolean z2 = true;
        if (!z) {
            z2 = RoundedCorners.getRoundedCornerBottomRadius(this.res, this.displayUniqueId) > 0;
        }
        this.hasBottom = z2;
        this.topRoundedDrawable = getDrawable(displayUniqueIdConfigIndex, R$array.config_roundedCornerTopDrawableArray, R$drawable.rounded_corner_top);
        this.bottomRoundedDrawable = getDrawable(displayUniqueIdConfigIndex, R$array.config_roundedCornerBottomDrawableArray, R$drawable.rounded_corner_bottom);
    }

    public final void setPhysicalPixelDisplaySizeRatio(float f) {
        if (this.physicalPixelDisplaySizeRatio == f) {
            return;
        }
        this.physicalPixelDisplaySizeRatio = f;
        reloadMeasures();
    }

    public final void setTuningSizeFactor(Integer num) {
        if (Intrinsics.areEqual(this.tuningSizeFactor, num)) {
            return;
        }
        this.tuningSizeFactor = num;
        reloadMeasures();
    }

    public final void updateDisplayUniqueId(String str, Integer num) {
        if (Intrinsics.areEqual(this.displayUniqueId, str)) {
            if (num == null || this.reloadToken == num.intValue()) {
                return;
            }
            this.reloadToken = num.intValue();
            reloadMeasures();
            return;
        }
        this.displayUniqueId = str;
        if (num != null) {
            this.reloadToken = num.intValue();
        }
        reloadRes();
        reloadMeasures();
    }
}