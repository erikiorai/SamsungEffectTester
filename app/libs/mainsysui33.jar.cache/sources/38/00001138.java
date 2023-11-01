package com.android.systemui.assist.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.util.Pair;
import androidx.core.math.MathUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.assist.ui.CornerPathRenderer;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/ui/PerimeterPathGuide.class */
public class PerimeterPathGuide {
    public final int mBottomCornerRadiusPx;
    public final CornerPathRenderer mCornerPathRenderer;
    public final int mDeviceHeightPx;
    public final int mDeviceWidthPx;
    public final int mEdgeInset;
    public RegionAttributes[] mRegions;
    public int mRotation;
    public final Path mScratchPath;
    public final PathMeasure mScratchPathMeasure;
    public final int mTopCornerRadiusPx;

    /* loaded from: mainsysui33.jar:com/android/systemui/assist/ui/PerimeterPathGuide$Region.class */
    public enum Region {
        BOTTOM,
        BOTTOM_RIGHT,
        RIGHT,
        TOP_RIGHT,
        TOP,
        TOP_LEFT,
        LEFT,
        BOTTOM_LEFT
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/assist/ui/PerimeterPathGuide$RegionAttributes.class */
    public class RegionAttributes {
        public float absoluteLength;
        public float endCoordinate;
        public float normalizedLength;
        public Path path;

        public RegionAttributes() {
        }
    }

    public PerimeterPathGuide(Context context, CornerPathRenderer cornerPathRenderer, int i, int i2, int i3) {
        Path path = new Path();
        this.mScratchPath = path;
        this.mScratchPathMeasure = new PathMeasure(path, false);
        this.mRotation = 0;
        this.mCornerPathRenderer = cornerPathRenderer;
        this.mDeviceWidthPx = i2;
        this.mDeviceHeightPx = i3;
        this.mTopCornerRadiusPx = DisplayUtils.getCornerRadiusTop(context);
        this.mBottomCornerRadiusPx = DisplayUtils.getCornerRadiusBottom(context);
        this.mEdgeInset = i;
        this.mRegions = new RegionAttributes[8];
        int i4 = 0;
        while (true) {
            RegionAttributes[] regionAttributesArr = this.mRegions;
            if (i4 >= regionAttributesArr.length) {
                computeRegions();
                return;
            } else {
                regionAttributesArr[i4] = new RegionAttributes();
                i4++;
            }
        }
    }

    public static float makeClockwise(float f) {
        return f - 1.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0067, code lost:
        if (r0 == 3) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void computeRegions() {
        int i;
        float f;
        int i2;
        int i3 = this.mDeviceWidthPx;
        int i4 = this.mDeviceHeightPx;
        int i5 = this.mRotation;
        int i6 = i5 != 1 ? i5 != 2 ? i5 != 3 ? 0 : -270 : -180 : -90;
        Matrix matrix = new Matrix();
        matrix.postRotate(i6, this.mDeviceWidthPx / 2, this.mDeviceHeightPx / 2);
        int i7 = this.mRotation;
        if (i7 != 1) {
            i = i3;
        }
        i4 = this.mDeviceWidthPx;
        i = this.mDeviceHeightPx;
        matrix.postTranslate((i - i4) / 2, (i4 - i) / 2);
        CornerPathRenderer.Corner rotatedCorner = getRotatedCorner(CornerPathRenderer.Corner.BOTTOM_LEFT);
        CornerPathRenderer.Corner rotatedCorner2 = getRotatedCorner(CornerPathRenderer.Corner.BOTTOM_RIGHT);
        CornerPathRenderer.Corner rotatedCorner3 = getRotatedCorner(CornerPathRenderer.Corner.TOP_LEFT);
        CornerPathRenderer.Corner rotatedCorner4 = getRotatedCorner(CornerPathRenderer.Corner.TOP_RIGHT);
        RegionAttributes[] regionAttributesArr = this.mRegions;
        Region region = Region.BOTTOM_LEFT;
        regionAttributesArr[region.ordinal()].path = this.mCornerPathRenderer.getInsetPath(rotatedCorner, this.mEdgeInset);
        RegionAttributes[] regionAttributesArr2 = this.mRegions;
        Region region2 = Region.BOTTOM_RIGHT;
        regionAttributesArr2[region2.ordinal()].path = this.mCornerPathRenderer.getInsetPath(rotatedCorner2, this.mEdgeInset);
        RegionAttributes[] regionAttributesArr3 = this.mRegions;
        Region region3 = Region.TOP_RIGHT;
        regionAttributesArr3[region3.ordinal()].path = this.mCornerPathRenderer.getInsetPath(rotatedCorner4, this.mEdgeInset);
        RegionAttributes[] regionAttributesArr4 = this.mRegions;
        Region region4 = Region.TOP_LEFT;
        regionAttributesArr4[region4.ordinal()].path = this.mCornerPathRenderer.getInsetPath(rotatedCorner3, this.mEdgeInset);
        this.mRegions[region.ordinal()].path.transform(matrix);
        this.mRegions[region2.ordinal()].path.transform(matrix);
        this.mRegions[region3.ordinal()].path.transform(matrix);
        this.mRegions[region4.ordinal()].path.transform(matrix);
        Path path = new Path();
        path.moveTo(getPhysicalCornerRadius(rotatedCorner), i4 - this.mEdgeInset);
        path.lineTo(i - getPhysicalCornerRadius(rotatedCorner2), i4 - this.mEdgeInset);
        this.mRegions[Region.BOTTOM.ordinal()].path = path;
        Path path2 = new Path();
        path2.moveTo(i - getPhysicalCornerRadius(rotatedCorner4), this.mEdgeInset);
        path2.lineTo(getPhysicalCornerRadius(rotatedCorner3), this.mEdgeInset);
        this.mRegions[Region.TOP.ordinal()].path = path2;
        Path path3 = new Path();
        path3.moveTo(i - this.mEdgeInset, i4 - getPhysicalCornerRadius(rotatedCorner2));
        path3.lineTo(i - this.mEdgeInset, getPhysicalCornerRadius(rotatedCorner4));
        this.mRegions[Region.RIGHT.ordinal()].path = path3;
        Path path4 = new Path();
        path4.moveTo(this.mEdgeInset, getPhysicalCornerRadius(rotatedCorner3));
        path4.lineTo(this.mEdgeInset, i4 - getPhysicalCornerRadius(rotatedCorner));
        this.mRegions[Region.LEFT.ordinal()].path = path4;
        PathMeasure pathMeasure = new PathMeasure();
        float f2 = 0.0f;
        int i8 = 0;
        while (true) {
            RegionAttributes[] regionAttributesArr5 = this.mRegions;
            f = 0.0f;
            i2 = 0;
            if (i8 >= regionAttributesArr5.length) {
                break;
            }
            pathMeasure.setPath(regionAttributesArr5[i8].path, false);
            this.mRegions[i8].absoluteLength = pathMeasure.getLength();
            f2 += this.mRegions[i8].absoluteLength;
            i8++;
        }
        while (true) {
            RegionAttributes[] regionAttributesArr6 = this.mRegions;
            if (i2 >= regionAttributesArr6.length) {
                regionAttributesArr6[regionAttributesArr6.length - 1].endCoordinate = 1.0f;
                return;
            }
            RegionAttributes regionAttributes = regionAttributesArr6[i2];
            float f3 = regionAttributes.absoluteLength;
            regionAttributes.normalizedLength = f3 / f2;
            f += f3;
            regionAttributes.endCoordinate = f / f2;
            i2++;
        }
    }

    public float getCoord(Region region, float f) {
        RegionAttributes regionAttributes = this.mRegions[region.ordinal()];
        return regionAttributes.endCoordinate - ((1.0f - MathUtils.clamp(f, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f)) * regionAttributes.normalizedLength);
    }

    public final int getPhysicalCornerRadius(CornerPathRenderer.Corner corner) {
        return (corner == CornerPathRenderer.Corner.BOTTOM_LEFT || corner == CornerPathRenderer.Corner.BOTTOM_RIGHT) ? this.mBottomCornerRadiusPx : this.mTopCornerRadiusPx;
    }

    public float getRegionCenter(Region region) {
        return getCoord(region, 0.5f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000b, code lost:
        if (r5 > 1.0f) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Region getRegionForPoint(float f) {
        float f2;
        Region[] values;
        if (f >= ActionBarShadowController.ELEVATION_LOW) {
            f2 = f;
        }
        f2 = ((f % 1.0f) + 1.0f) % 1.0f;
        for (Region region : Region.values()) {
            if (f2 <= this.mRegions[region.ordinal()].endCoordinate) {
                return region;
            }
        }
        Log.e("PerimeterPathGuide", "Fell out of getRegionForPoint");
        return Region.BOTTOM;
    }

    public float getRegionWidth(Region region) {
        return this.mRegions[region.ordinal()].normalizedLength;
    }

    public final CornerPathRenderer.Corner getRotatedCorner(CornerPathRenderer.Corner corner) {
        int ordinal = corner.ordinal();
        int i = this.mRotation;
        if (i == 1) {
            ordinal += 3;
        } else if (i == 2) {
            ordinal += 2;
        } else if (i == 3) {
            ordinal++;
        }
        return CornerPathRenderer.Corner.values()[ordinal % 4];
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000b, code lost:
        if (r7 > 1.0f) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair<Region, Float> placePoint(float f) {
        float f2;
        if (ActionBarShadowController.ELEVATION_LOW <= f) {
            f2 = f;
        }
        f2 = ((f % 1.0f) + 1.0f) % 1.0f;
        Region regionForPoint = getRegionForPoint(f2);
        return regionForPoint.equals(Region.BOTTOM) ? Pair.create(regionForPoint, Float.valueOf(f2 / this.mRegions[regionForPoint.ordinal()].normalizedLength)) : Pair.create(regionForPoint, Float.valueOf((f2 - this.mRegions[regionForPoint.ordinal() - 1].endCoordinate) / this.mRegions[regionForPoint.ordinal()].normalizedLength));
    }

    public void setRotation(int i) {
        if (i != this.mRotation) {
            if (i == 0 || i == 1 || i == 2 || i == 3) {
                this.mRotation = i;
                computeRegions();
                return;
            }
            Log.e("PerimeterPathGuide", "Invalid rotation provided: " + i);
        }
    }

    public final void strokeRegion(Path path, Region region, float f, float f2) {
        if (f == f2) {
            return;
        }
        this.mScratchPathMeasure.setPath(this.mRegions[region.ordinal()].path, false);
        PathMeasure pathMeasure = this.mScratchPathMeasure;
        pathMeasure.getSegment(f * pathMeasure.getLength(), f2 * this.mScratchPathMeasure.getLength(), path, true);
    }

    public void strokeSegment(Path path, float f, float f2) {
        path.reset();
        float f3 = ((f % 1.0f) + 1.0f) % 1.0f;
        float f4 = ((f2 % 1.0f) + 1.0f) % 1.0f;
        float f5 = f3;
        if (f3 > f4) {
            strokeSegmentInternal(path, f3, 1.0f);
            f5 = 0.0f;
        }
        strokeSegmentInternal(path, f5, f4);
    }

    public final void strokeSegmentInternal(Path path, float f, float f2) {
        boolean z;
        Pair<Region, Float> placePoint = placePoint(f);
        Pair<Region, Float> placePoint2 = placePoint(f2);
        if (((Region) placePoint.first).equals(placePoint2.first)) {
            strokeRegion(path, (Region) placePoint.first, ((Float) placePoint.second).floatValue(), ((Float) placePoint2.second).floatValue());
            return;
        }
        strokeRegion(path, (Region) placePoint.first, ((Float) placePoint.second).floatValue(), 1.0f);
        Region[] values = Region.values();
        int length = values.length;
        int i = 0;
        boolean z2 = false;
        while (true) {
            boolean z3 = z2;
            if (i >= length) {
                return;
            }
            Region region = values[i];
            if (region.equals(placePoint.first)) {
                z = true;
            } else {
                z = z3;
                if (!z3) {
                    continue;
                } else if (region.equals(placePoint2.first)) {
                    strokeRegion(path, region, ActionBarShadowController.ELEVATION_LOW, ((Float) placePoint2.second).floatValue());
                    return;
                } else {
                    strokeRegion(path, region, ActionBarShadowController.ELEVATION_LOW, 1.0f);
                    z = z3;
                }
            }
            i++;
            z2 = z;
        }
    }
}