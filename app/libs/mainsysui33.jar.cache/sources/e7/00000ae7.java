package com.airbnb.lottie.value;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/value/ScaleXY.class */
public class ScaleXY {
    public float scaleX;
    public float scaleY;

    public ScaleXY() {
        this(1.0f, 1.0f);
    }

    public ScaleXY(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
    }

    public boolean equals(float f, float f2) {
        return this.scaleX == f && this.scaleY == f2;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void set(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
    }

    public String toString() {
        return getScaleX() + "x" + getScaleY();
    }
}