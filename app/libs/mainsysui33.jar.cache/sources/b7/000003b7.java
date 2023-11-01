package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/* loaded from: mainsysui33.jar:androidx/core/graphics/drawable/DrawableCompat.class */
public final class DrawableCompat {

    /* loaded from: mainsysui33.jar:androidx/core/graphics/drawable/DrawableCompat$Api19Impl.class */
    public static class Api19Impl {
        public static boolean isAutoMirrored(Drawable drawable) {
            return drawable.isAutoMirrored();
        }

        public static void setAutoMirrored(Drawable drawable, boolean z) {
            drawable.setAutoMirrored(z);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/graphics/drawable/DrawableCompat$Api21Impl.class */
    public static class Api21Impl {
        public static void setHotspot(Drawable drawable, float f, float f2) {
            drawable.setHotspot(f, f2);
        }

        public static void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
            drawable.setHotspotBounds(i, i2, i3, i4);
        }

        public static void setTint(Drawable drawable, int i) {
            drawable.setTint(i);
        }

        public static void setTintList(Drawable drawable, ColorStateList colorStateList) {
            drawable.setTintList(colorStateList);
        }

        public static void setTintMode(Drawable drawable, PorterDuff.Mode mode) {
            drawable.setTintMode(mode);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/graphics/drawable/DrawableCompat$Api23Impl.class */
    public static class Api23Impl {
        public static int getLayoutDirection(Drawable drawable) {
            return drawable.getLayoutDirection();
        }

        public static boolean setLayoutDirection(Drawable drawable, int i) {
            return drawable.setLayoutDirection(i);
        }
    }

    public static void clearColorFilter(Drawable drawable) {
        drawable.clearColorFilter();
    }

    public static int getLayoutDirection(Drawable drawable) {
        return Api23Impl.getLayoutDirection(drawable);
    }

    public static boolean isAutoMirrored(Drawable drawable) {
        return Api19Impl.isAutoMirrored(drawable);
    }

    public static void setAutoMirrored(Drawable drawable, boolean z) {
        Api19Impl.setAutoMirrored(drawable, z);
    }

    public static void setHotspot(Drawable drawable, float f, float f2) {
        Api21Impl.setHotspot(drawable, f, f2);
    }

    public static void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
        Api21Impl.setHotspotBounds(drawable, i, i2, i3, i4);
    }

    public static boolean setLayoutDirection(Drawable drawable, int i) {
        return Api23Impl.setLayoutDirection(drawable, i);
    }

    public static void setTint(Drawable drawable, int i) {
        Api21Impl.setTint(drawable, i);
    }

    public static void setTintList(Drawable drawable, ColorStateList colorStateList) {
        Api21Impl.setTintList(drawable, colorStateList);
    }

    public static void setTintMode(Drawable drawable, PorterDuff.Mode mode) {
        Api21Impl.setTintMode(drawable, mode);
    }

    public static <T extends Drawable> T unwrap(Drawable drawable) {
        Drawable drawable2 = drawable;
        if (drawable instanceof WrappedDrawable) {
            drawable2 = ((WrappedDrawable) drawable).getWrappedDrawable();
        }
        return (T) drawable2;
    }

    public static Drawable wrap(Drawable drawable) {
        return drawable;
    }
}