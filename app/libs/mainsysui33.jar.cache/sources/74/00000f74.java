package com.android.systemui;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.view.ContextThemeWrapper;
import com.android.settingslib.Utils;

/* loaded from: mainsysui33.jar:com/android/systemui/DualToneHandler.class */
public final class DualToneHandler {
    public Color darkColor;
    public Color lightColor;

    /* loaded from: mainsysui33.jar:com/android/systemui/DualToneHandler$Color.class */
    public static final class Color {
        public final int background;
        public final int fill;
        public final int single;

        public Color(int i, int i2, int i3) {
            this.single = i;
            this.background = i2;
            this.fill = i3;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Color) {
                Color color = (Color) obj;
                return this.single == color.single && this.background == color.background && this.fill == color.fill;
            }
            return false;
        }

        public final int getBackground() {
            return this.background;
        }

        public final int getFill() {
            return this.fill;
        }

        public final int getSingle() {
            return this.single;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.single) * 31) + Integer.hashCode(this.background)) * 31) + Integer.hashCode(this.fill);
        }

        public String toString() {
            int i = this.single;
            int i2 = this.background;
            int i3 = this.fill;
            return "Color(single=" + i + ", background=" + i2 + ", fill=" + i3 + ")";
        }
    }

    public DualToneHandler(Context context) {
        setColorsFromContext(context);
    }

    public final int getBackgroundColor(float f) {
        Color color = this.lightColor;
        Color color2 = color;
        if (color == null) {
            color2 = null;
        }
        int background = color2.getBackground();
        Color color3 = this.darkColor;
        if (color3 == null) {
            color3 = null;
        }
        return getColorForDarkIntensity(f, background, color3.getBackground());
    }

    public final int getColorForDarkIntensity(float f, int i, int i2) {
        return ((Integer) ArgbEvaluator.getInstance().evaluate(f, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
    }

    public final int getFillColor(float f) {
        Color color = this.lightColor;
        Color color2 = color;
        if (color == null) {
            color2 = null;
        }
        int fill = color2.getFill();
        Color color3 = this.darkColor;
        if (color3 == null) {
            color3 = null;
        }
        return getColorForDarkIntensity(f, fill, color3.getFill());
    }

    public final int getSingleColor(float f) {
        Color color = this.lightColor;
        Color color2 = color;
        if (color == null) {
            color2 = null;
        }
        int single = color2.getSingle();
        Color color3 = this.darkColor;
        if (color3 == null) {
            color3 = null;
        }
        return getColorForDarkIntensity(f, single, color3.getSingle());
    }

    public final void setColorsFromContext(Context context) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.darkIconTheme));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.lightIconTheme));
        int i = R$attr.singleToneColor;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(contextThemeWrapper, i);
        int i2 = R$attr.iconBackgroundColor;
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(contextThemeWrapper, i2);
        int i3 = R$attr.fillColor;
        this.darkColor = new Color(colorAttrDefaultColor, colorAttrDefaultColor2, Utils.getColorAttrDefaultColor(contextThemeWrapper, i3));
        this.lightColor = new Color(Utils.getColorAttrDefaultColor(contextThemeWrapper2, i), Utils.getColorAttrDefaultColor(contextThemeWrapper2, i2), Utils.getColorAttrDefaultColor(contextThemeWrapper2, i3));
    }
}