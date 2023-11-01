package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.Arrays;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/ColorExtractor.class */
public class ColorExtractor {
    public final int NUM_SAMPLES = 20;
    public final float[] mTmpHsv = new float[3];
    public final float[] mTmpHueScoreHistogram = new float[360];
    public final int[] mTmpPixels = new int[20];
    public final SparseArray<Float> mTmpRgbScores = new SparseArray<>();

    public int findDominantColorByHue(Bitmap bitmap) {
        return findDominantColorByHue(bitmap, 20);
    }

    public int findDominantColorByHue(Bitmap bitmap, int i) {
        float f;
        int i2;
        int i3;
        float f2;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int sqrt = (int) Math.sqrt((height * width) / i);
        int i4 = sqrt;
        if (sqrt < 1) {
            i4 = 1;
        }
        float[] fArr = this.mTmpHsv;
        Arrays.fill(fArr, (float) ActionBarShadowController.ELEVATION_LOW);
        float[] fArr2 = this.mTmpHueScoreHistogram;
        Arrays.fill(fArr2, (float) ActionBarShadowController.ELEVATION_LOW);
        int i5 = -1;
        int[] iArr = this.mTmpPixels;
        Arrays.fill(iArr, 0);
        int i6 = 0;
        int i7 = 0;
        float f3 = -1.0f;
        while (i6 < height) {
            int i8 = 0;
            int i9 = i5;
            while (i8 < width) {
                int pixel = bitmap.getPixel(i8, i6);
                if (((pixel >> 24) & 255) < 128) {
                    i2 = i9;
                    i3 = i7;
                    f2 = f3;
                } else {
                    int i10 = pixel | (-16777216);
                    Color.colorToHSV(i10, fArr);
                    int i11 = (int) fArr[0];
                    i2 = i9;
                    i3 = i7;
                    f2 = f3;
                    if (i11 >= 0) {
                        if (i11 >= fArr2.length) {
                            i2 = i9;
                            i3 = i7;
                            f2 = f3;
                        } else {
                            int i12 = i7;
                            if (i7 < i) {
                                iArr[i7] = i10;
                                i12 = i7 + 1;
                            }
                            float f4 = fArr2[i11] + (fArr[1] * fArr[2]);
                            fArr2[i11] = f4;
                            i2 = i9;
                            i3 = i12;
                            f2 = f3;
                            if (f4 > f3) {
                                i2 = i11;
                                f2 = f4;
                                i3 = i12;
                            }
                        }
                    }
                }
                i8 += i4;
                i9 = i2;
                i7 = i3;
                f3 = f2;
            }
            i6 += i4;
            i5 = i9;
        }
        SparseArray<Float> sparseArray = this.mTmpRgbScores;
        sparseArray.clear();
        int i13 = 0;
        float f5 = -1.0f;
        int i14 = -16777216;
        while (i13 < i7) {
            int i15 = iArr[i13];
            Color.colorToHSV(i15, fArr);
            if (((int) fArr[0]) == i5) {
                float f6 = fArr[1];
                float f7 = fArr[2];
                int i16 = ((int) (100.0f * f6)) + ((int) (10000.0f * f7));
                float f8 = f6 * f7;
                Float f9 = sparseArray.get(i16);
                if (f9 != null) {
                    f8 += f9.floatValue();
                }
                sparseArray.put(i16, Float.valueOf(f8));
                f = f5;
                if (f8 > f5) {
                    i14 = i15;
                    f = f8;
                }
            } else {
                f = f5;
            }
            i13++;
            f5 = f;
        }
        return i14;
    }
}