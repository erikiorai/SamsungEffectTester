package com.android.systemui.accessibility.floatingmenu;

import android.text.TextUtils;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/Position.class */
public class Position {
    public static final TextUtils.SimpleStringSplitter sStringCommaSplitter = new TextUtils.SimpleStringSplitter(',');
    public float mPercentageX;
    public float mPercentageY;

    public Position(float f, float f2) {
        update(f, f2);
    }

    public static Position fromString(String str) {
        TextUtils.SimpleStringSplitter simpleStringSplitter = sStringCommaSplitter;
        simpleStringSplitter.setString(str);
        if (simpleStringSplitter.hasNext()) {
            return new Position(Float.parseFloat(simpleStringSplitter.next()), Float.parseFloat(simpleStringSplitter.next()));
        }
        throw new IllegalArgumentException("Invalid Position string: " + str);
    }

    public float getPercentageX() {
        return this.mPercentageX;
    }

    public float getPercentageY() {
        return this.mPercentageY;
    }

    public String toString() {
        return this.mPercentageX + ", " + this.mPercentageY;
    }

    public void update(float f, float f2) {
        this.mPercentageX = f;
        this.mPercentageY = f2;
    }
}