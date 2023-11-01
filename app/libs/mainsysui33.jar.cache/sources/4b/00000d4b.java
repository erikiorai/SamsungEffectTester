package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.TypedArray;
import android.frameworks.stats.IStats;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/GraphicsUtils.class */
public class GraphicsUtils {
    public static Runnable sOnNewBitmapRunnable = new Runnable() { // from class: com.android.launcher3.icons.GraphicsUtils$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            GraphicsUtils.lambda$static$0();
        }
    };

    public static int getArea(Region region) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect = new Rect();
        int i = 0;
        while (true) {
            int i2 = i;
            if (!regionIterator.next(rect)) {
                return i2;
            }
            i = i2 + (rect.width() * rect.height());
        }
    }

    public static int getAttrColor(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    public static float getFloat(Context context, int i, float f) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        float f2 = obtainStyledAttributes.getFloat(0, f);
        obtainStyledAttributes.recycle();
        return f2;
    }

    public static Path getShapePath(int i) {
        AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(-16777216), new ColorDrawable(-16777216));
        adaptiveIconDrawable.setBounds(0, 0, i, i);
        return new Path(adaptiveIconDrawable.getIconMask());
    }

    public static /* synthetic */ void lambda$static$0() {
    }

    public static void noteNewBitmapCreated() {
        sOnNewBitmapRunnable.run();
    }

    public static int setColorAlphaBound(int i, int i2) {
        int i3;
        if (i2 < 0) {
            i3 = 0;
        } else {
            i3 = i2;
            if (i2 > 255) {
                i3 = 255;
            }
        }
        return (i & IStats.Stub.TRANSACTION_getInterfaceVersion) | (i3 << 24);
    }
}