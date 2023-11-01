package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;

/* loaded from: mainsysui33.jar:androidx/core/widget/CompoundButtonCompat.class */
public final class CompoundButtonCompat {

    /* loaded from: mainsysui33.jar:androidx/core/widget/CompoundButtonCompat$Api21Impl.class */
    public static class Api21Impl {
        public static ColorStateList getButtonTintList(CompoundButton compoundButton) {
            return compoundButton.getButtonTintList();
        }

        public static void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
            compoundButton.setButtonTintList(colorStateList);
        }

        public static void setButtonTintMode(CompoundButton compoundButton, PorterDuff.Mode mode) {
            compoundButton.setButtonTintMode(mode);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/widget/CompoundButtonCompat$Api23Impl.class */
    public static class Api23Impl {
        public static Drawable getButtonDrawable(CompoundButton compoundButton) {
            return compoundButton.getButtonDrawable();
        }
    }

    public static Drawable getButtonDrawable(CompoundButton compoundButton) {
        return Api23Impl.getButtonDrawable(compoundButton);
    }

    public static ColorStateList getButtonTintList(CompoundButton compoundButton) {
        return Api21Impl.getButtonTintList(compoundButton);
    }

    public static void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
        Api21Impl.setButtonTintList(compoundButton, colorStateList);
    }

    public static void setButtonTintMode(CompoundButton compoundButton, PorterDuff.Mode mode) {
        Api21Impl.setButtonTintMode(compoundButton, mode);
    }
}