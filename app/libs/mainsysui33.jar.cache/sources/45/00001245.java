package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/NormalizedTouchData.class */
public final class NormalizedTouchData {
    public final long gestureStart;
    public final float major;
    public final float minor;
    public final float orientation;
    public final int pointerId;
    public final long time;
    public final float x;
    public final float y;

    public NormalizedTouchData(int i, float f, float f2, float f3, float f4, float f5, long j, long j2) {
        this.pointerId = i;
        this.x = f;
        this.y = f2;
        this.minor = f3;
        this.major = f4;
        this.orientation = f5;
        this.time = j;
        this.gestureStart = j2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof NormalizedTouchData) {
            NormalizedTouchData normalizedTouchData = (NormalizedTouchData) obj;
            return this.pointerId == normalizedTouchData.pointerId && Float.compare(this.x, normalizedTouchData.x) == 0 && Float.compare(this.y, normalizedTouchData.y) == 0 && Float.compare(this.minor, normalizedTouchData.minor) == 0 && Float.compare(this.major, normalizedTouchData.major) == 0 && Float.compare(this.orientation, normalizedTouchData.orientation) == 0 && this.time == normalizedTouchData.time && this.gestureStart == normalizedTouchData.gestureStart;
        }
        return false;
    }

    public final long getGestureStart() {
        return this.gestureStart;
    }

    public final float getMajor() {
        return this.major;
    }

    public final float getMinor() {
        return this.minor;
    }

    public final float getOrientation() {
        return this.orientation;
    }

    public final int getPointerId() {
        return this.pointerId;
    }

    public final long getTime() {
        return this.time;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public int hashCode() {
        return (((((((((((((Integer.hashCode(this.pointerId) * 31) + Float.hashCode(this.x)) * 31) + Float.hashCode(this.y)) * 31) + Float.hashCode(this.minor)) * 31) + Float.hashCode(this.major)) * 31) + Float.hashCode(this.orientation)) * 31) + Long.hashCode(this.time)) * 31) + Long.hashCode(this.gestureStart);
    }

    public final boolean isWithinSensor(Rect rect) {
        boolean z;
        float f = rect.left;
        float f2 = this.x;
        if (f <= f2 && rect.right >= f2) {
            float f3 = rect.top;
            float f4 = this.y;
            if (f3 <= f4 && rect.bottom >= f4) {
                z = true;
                return z;
            }
        }
        z = false;
        return z;
    }

    public String toString() {
        int i = this.pointerId;
        float f = this.x;
        float f2 = this.y;
        float f3 = this.minor;
        float f4 = this.major;
        float f5 = this.orientation;
        long j = this.time;
        long j2 = this.gestureStart;
        return "NormalizedTouchData(pointerId=" + i + ", x=" + f + ", y=" + f2 + ", minor=" + f3 + ", major=" + f4 + ", orientation=" + f5 + ", time=" + j + ", gestureStart=" + j2 + ")";
    }
}