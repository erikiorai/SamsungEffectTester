package com.android.systemui.media.controls.ui;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/RippleData.class */
public final class RippleData {
    public float alpha;
    public float highlight;
    public float maxSize;
    public float minSize;
    public float progress;
    public float x;
    public float y;

    public RippleData(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        this.x = f;
        this.y = f2;
        this.alpha = f3;
        this.progress = f4;
        this.minSize = f5;
        this.maxSize = f6;
        this.highlight = f7;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RippleData) {
            RippleData rippleData = (RippleData) obj;
            return Float.compare(this.x, rippleData.x) == 0 && Float.compare(this.y, rippleData.y) == 0 && Float.compare(this.alpha, rippleData.alpha) == 0 && Float.compare(this.progress, rippleData.progress) == 0 && Float.compare(this.minSize, rippleData.minSize) == 0 && Float.compare(this.maxSize, rippleData.maxSize) == 0 && Float.compare(this.highlight, rippleData.highlight) == 0;
        }
        return false;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final float getMaxSize() {
        return this.maxSize;
    }

    public final float getMinSize() {
        return this.minSize;
    }

    public final float getProgress() {
        return this.progress;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public int hashCode() {
        return (((((((((((Float.hashCode(this.x) * 31) + Float.hashCode(this.y)) * 31) + Float.hashCode(this.alpha)) * 31) + Float.hashCode(this.progress)) * 31) + Float.hashCode(this.minSize)) * 31) + Float.hashCode(this.maxSize)) * 31) + Float.hashCode(this.highlight);
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final void setHighlight(float f) {
        this.highlight = f;
    }

    public final void setMaxSize(float f) {
        this.maxSize = f;
    }

    public final void setMinSize(float f) {
        this.minSize = f;
    }

    public final void setProgress(float f) {
        this.progress = f;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public String toString() {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.alpha;
        float f4 = this.progress;
        float f5 = this.minSize;
        float f6 = this.maxSize;
        float f7 = this.highlight;
        return "RippleData(x=" + f + ", y=" + f2 + ", alpha=" + f3 + ", progress=" + f4 + ", minSize=" + f5 + ", maxSize=" + f6 + ", highlight=" + f7 + ")";
    }
}