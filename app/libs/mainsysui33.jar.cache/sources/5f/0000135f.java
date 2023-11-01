package com.android.systemui.common.shared.model;

/* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/Position.class */
public final class Position {
    public final int x;
    public final int y;

    public Position(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Position) {
            Position position = (Position) obj;
            return this.x == position.x && this.y == position.y;
        }
        return false;
    }

    public final int getX() {
        return this.x;
    }

    public int hashCode() {
        return (Integer.hashCode(this.x) * 31) + Integer.hashCode(this.y);
    }

    public String toString() {
        int i = this.x;
        int i2 = this.y;
        return "Position(x=" + i + ", y=" + i2 + ")";
    }
}