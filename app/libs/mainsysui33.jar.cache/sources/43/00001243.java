package com.android.systemui.biometrics.udfps;

import android.graphics.Point;
import android.graphics.Rect;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/EllipseOverlapDetector.class */
public final class EllipseOverlapDetector implements OverlapDetector {
    public final int neededPoints;

    public EllipseOverlapDetector(int i) {
        this.neededPoints = i;
    }

    public /* synthetic */ EllipseOverlapDetector(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 2 : i);
    }

    public final List<Point> calculateSensorPoints(Rect rect) {
        int centerX = rect.centerX();
        int centerY = rect.centerY();
        int width = rect.width() / 4;
        int width2 = rect.width() / 3;
        int i = centerX - width;
        int i2 = centerY - width;
        Point point = new Point(i, i2);
        Point point2 = new Point(centerX, centerY - width2);
        int i3 = centerX + width;
        Point point3 = new Point(i3, i2);
        Point point4 = new Point(centerX - width2, centerY);
        Point point5 = new Point(centerX, centerY);
        Point point6 = new Point(centerX + width2, centerY);
        int i4 = width + centerY;
        return CollectionsKt__CollectionsKt.listOf(new Point[]{point, point2, point3, point4, point5, point6, new Point(i, i4), new Point(centerX, centerY + width2), new Point(i3, i4)});
    }

    public final boolean checkPoint(Point point, NormalizedTouchData normalizedTouchData) {
        float cos = (float) Math.cos(normalizedTouchData.getOrientation());
        float f = point.x;
        float x = normalizedTouchData.getX();
        float sin = (float) Math.sin(normalizedTouchData.getOrientation());
        float f2 = point.y;
        float y = normalizedTouchData.getY();
        float sin2 = (float) Math.sin(normalizedTouchData.getOrientation());
        float f3 = point.x;
        float x2 = normalizedTouchData.getX();
        float cos2 = (float) Math.cos(normalizedTouchData.getOrientation());
        float f4 = point.y;
        float y2 = normalizedTouchData.getY();
        double d = (cos * (f - x)) + (sin * (f2 - y));
        double d2 = 2;
        float pow = (float) Math.pow(d, d2);
        float f5 = 2;
        return (pow / ((float) Math.pow((double) (normalizedTouchData.getMinor() / f5), d2))) + (((float) Math.pow((double) ((sin2 * (f3 - x2)) - (cos2 * (f4 - y2))), d2)) / ((float) Math.pow((double) (normalizedTouchData.getMajor() / f5), d2))) <= 1.0f;
    }

    @Override // com.android.systemui.biometrics.udfps.OverlapDetector
    public boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect) {
        int i;
        List<Point> calculateSensorPoints = calculateSensorPoints(rect);
        boolean z = false;
        if (!(calculateSensorPoints instanceof Collection) || !calculateSensorPoints.isEmpty()) {
            Iterator<T> it = calculateSensorPoints.iterator();
            int i2 = 0;
            while (true) {
                i = i2;
                if (!it.hasNext()) {
                    break;
                } else if (checkPoint((Point) it.next(), normalizedTouchData)) {
                    int i3 = i2 + 1;
                    i2 = i3;
                    if (i3 < 0) {
                        CollectionsKt__CollectionsKt.throwCountOverflow();
                        i2 = i3;
                    }
                }
            }
        } else {
            i = 0;
        }
        if (i >= this.neededPoints) {
            z = true;
        }
        return z;
    }
}