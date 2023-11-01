package com.android.settingslib.utils;

import android.content.Context;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.text.SpannableStringBuilder;
import android.text.style.TtsSpan;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/settingslib/utils/StringUtil.class */
public class StringUtil {
    public static CharSequence formatElapsedTime(Context context, double d, boolean z, boolean z2) {
        int i;
        int i2;
        int i3;
        int i4;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int floor = (int) Math.floor(d / 1000.0d);
        int i5 = floor;
        if (!z) {
            i5 = floor + 30;
        }
        if (i5 >= 86400) {
            i = i5 / 86400;
            i5 -= 86400 * i;
        } else {
            i = 0;
        }
        if (i5 >= 3600) {
            i2 = i5 / 3600;
            i5 -= i2 * 3600;
        } else {
            i2 = 0;
        }
        if (i5 >= 60) {
            int i6 = i5 / 60;
            i3 = i5 - (i6 * 60);
            i4 = i6;
        } else {
            i3 = i5;
            i4 = 0;
        }
        ArrayList arrayList = new ArrayList(4);
        if (i > 0) {
            arrayList.add(new Measure(Integer.valueOf(i), MeasureUnit.DAY));
        }
        if (i2 > 0) {
            arrayList.add(new Measure(Integer.valueOf(i2), MeasureUnit.HOUR));
        }
        if (i4 > 0) {
            arrayList.add(new Measure(Integer.valueOf(i4), MeasureUnit.MINUTE));
        }
        if (z && i3 > 0) {
            arrayList.add(new Measure(Integer.valueOf(i3), MeasureUnit.SECOND));
        }
        if (arrayList.size() == 0) {
            arrayList.add(new Measure(0, z ? MeasureUnit.SECOND : MeasureUnit.MINUTE));
        }
        if (z2 && arrayList.size() > 2) {
            arrayList.subList(2, arrayList.size()).clear();
        }
        Measure[] measureArr = (Measure[]) arrayList.toArray(new Measure[arrayList.size()]);
        spannableStringBuilder.append((CharSequence) MeasureFormat.getInstance(context.getResources().getConfiguration().locale, MeasureFormat.FormatWidth.SHORT).formatMeasures(measureArr));
        if (measureArr.length == 1 && MeasureUnit.MINUTE.equals(measureArr[0].getUnit())) {
            spannableStringBuilder.setSpan(new TtsSpan.MeasureBuilder().setNumber(i4).setUnit("minute").build(), 0, spannableStringBuilder.length(), 33);
        }
        return spannableStringBuilder;
    }
}