package com.android.systemui.assist.ui;

import android.util.Log;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/ui/EdgeLight.class */
public final class EdgeLight {
    public int mColor;
    public float mLength;
    public float mStart;

    public EdgeLight(int i, float f, float f2) {
        this.mColor = i;
        this.mStart = f;
        this.mLength = f2;
    }

    public EdgeLight(EdgeLight edgeLight) {
        this.mColor = edgeLight.getColor();
        this.mStart = edgeLight.getStart();
        this.mLength = edgeLight.getLength();
    }

    public static EdgeLight[] copy(EdgeLight[] edgeLightArr) {
        EdgeLight[] edgeLightArr2 = new EdgeLight[edgeLightArr.length];
        for (int i = 0; i < edgeLightArr.length; i++) {
            edgeLightArr2[i] = new EdgeLight(edgeLightArr[i]);
        }
        return edgeLightArr2;
    }

    public int getColor() {
        return this.mColor;
    }

    public float getEnd() {
        return this.mStart + this.mLength;
    }

    public float getLength() {
        return this.mLength;
    }

    public float getStart() {
        return this.mStart;
    }

    public boolean setColor(int i) {
        boolean z = this.mColor != i;
        this.mColor = i;
        return z;
    }

    public void setEndpoints(float f, float f2) {
        if (f > f2) {
            Log.e("EdgeLight", String.format("Endpoint must be >= start (add 1 if necessary). Got [%f, %f]", Float.valueOf(f), Float.valueOf(f2)));
            return;
        }
        this.mStart = f;
        this.mLength = f2 - f;
    }

    public void setLength(float f) {
        this.mLength = f;
    }

    public void setStart(float f) {
        this.mStart = f;
    }
}