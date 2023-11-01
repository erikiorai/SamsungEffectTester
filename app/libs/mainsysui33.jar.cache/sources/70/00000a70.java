package com.airbnb.lottie.model;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/Font.class */
public class Font {
    public final float ascent;
    public final String family;
    public final String name;
    public final String style;

    public Font(String str, String str2, String str3, float f) {
        this.family = str;
        this.name = str2;
        this.style = str3;
        this.ascent = f;
    }

    public String getFamily() {
        return this.family;
    }

    public String getName() {
        return this.name;
    }

    public String getStyle() {
        return this.style;
    }
}