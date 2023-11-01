package com.airbnb.lottie.model;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/Marker.class */
public class Marker {
    public static String CARRIAGE_RETURN = "\r";
    public final float durationFrames;
    public final String name;
    public final float startFrame;

    public Marker(String str, float f, float f2) {
        this.name = str;
        this.durationFrames = f2;
        this.startFrame = f;
    }

    public boolean matchesName(String str) {
        if (this.name.equalsIgnoreCase(str)) {
            return true;
        }
        if (this.name.endsWith(CARRIAGE_RETURN)) {
            String str2 = this.name;
            return str2.substring(0, str2.length() - 1).equalsIgnoreCase(str);
        }
        return false;
    }
}