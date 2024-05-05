package com.android.systemui.opensesame.color;

/* loaded from: classes.dex */
public class ColorSet {
    public static final int MAX_COLOR_COUNT = 5;
    public int mBackgroundColor;
    public int mBaseColor;
    public int mForegroundColor;
    public int mId;
    public int mPointColor;
    public int mPrimaryColor;

    public ColorSet() {
        this.mId = -1;
        this.mBaseColor = 0;
        this.mBackgroundColor = 0;
        this.mForegroundColor = 0;
        this.mPrimaryColor = 0;
        this.mPointColor = 0;
    }

    public ColorSet(int id, int baseColor, int backgroundColor, int foregroundColor, int primaryColor, int pointColor) {
        this.mId = id;
        this.mBaseColor = baseColor;
        this.mBackgroundColor = backgroundColor;
        this.mForegroundColor = foregroundColor;
        this.mPrimaryColor = primaryColor;
        this.mPointColor = pointColor;
    }

    public void copyFrom(ColorSet colorSet) {
        this.mId = colorSet.mId;
        this.mBaseColor = colorSet.mBaseColor;
        this.mBackgroundColor = colorSet.mBackgroundColor;
        this.mForegroundColor = colorSet.mForegroundColor;
        this.mPrimaryColor = colorSet.mPrimaryColor;
        this.mPointColor = colorSet.mPointColor;
    }

    public ColorSet getClone() {
        return new ColorSet(this.mId, this.mBaseColor, this.mBackgroundColor, this.mForegroundColor, this.mPrimaryColor, this.mPointColor);
    }

    public boolean isSame(ColorSet colorSet) {
        return colorSet != null && this.mBaseColor == colorSet.mBaseColor && this.mBackgroundColor == colorSet.mBackgroundColor && this.mForegroundColor == colorSet.mForegroundColor && this.mPrimaryColor == colorSet.mPrimaryColor && this.mPointColor == colorSet.mPointColor;
    }

    public String toString() {
        return String.format("id = %d, baseColor = %x, backgroundColor = %x, foregroundColor = %x, primaryColor = %x, pointColor = %x", this.mId, this.mBaseColor, this.mBackgroundColor, this.mForegroundColor, this.mPrimaryColor, this.mPointColor);
    }
}