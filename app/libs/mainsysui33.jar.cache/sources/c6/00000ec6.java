package com.android.settingslib.utils;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.text.TextUtils;
import com.android.settingslib.R$string;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* loaded from: mainsysui33.jar:com/android/settingslib/utils/PowerUtil.class */
public class PowerUtil {
    public static final long FIFTEEN_MINUTES_MILLIS;
    public static final long ONE_DAY_MILLIS;
    public static final long ONE_HOUR_MILLIS;
    public static final long ONE_MIN_MILLIS;
    public static final long SEVEN_MINUTES_MILLIS;
    public static final long TWO_DAYS_MILLIS;

    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        SEVEN_MINUTES_MILLIS = timeUnit.toMillis(7L);
        FIFTEEN_MINUTES_MILLIS = timeUnit.toMillis(15L);
        TimeUnit timeUnit2 = TimeUnit.DAYS;
        ONE_DAY_MILLIS = timeUnit2.toMillis(1L);
        TWO_DAYS_MILLIS = timeUnit2.toMillis(2L);
        ONE_HOUR_MILLIS = TimeUnit.HOURS.toMillis(1L);
        ONE_MIN_MILLIS = timeUnit.toMillis(1L);
    }

    public static String getBatteryRemainingShortStringFormatted(Context context, long j) {
        if (j <= 0) {
            return null;
        }
        return j <= ONE_DAY_MILLIS ? getRegularTimeRemainingShortString(context, j) : getMoreThanOneDayShortString(context, j, R$string.power_remaining_duration_only_short);
    }

    public static String getBatteryRemainingStringFormatted(Context context, long j, String str, boolean z) {
        if (j > 0) {
            if (j <= SEVEN_MINUTES_MILLIS) {
                return getShutdownImminentString(context, str);
            }
            long j2 = FIFTEEN_MINUTES_MILLIS;
            return j <= j2 ? getUnderFifteenString(context, StringUtil.formatElapsedTime(context, j2, false, false), str) : j >= TWO_DAYS_MILLIS ? getMoreThanTwoDaysString(context, str) : j >= ONE_DAY_MILLIS ? getMoreThanOneDayString(context, j, str, z) : getRegularTimeRemainingString(context, j, str, z);
        }
        return null;
    }

    public static String getMoreThanOneDayShortString(Context context, long j, int i) {
        return context.getString(i, StringUtil.formatElapsedTime(context, roundTimeToNearestThreshold(j, ONE_HOUR_MILLIS), false, false));
    }

    public static String getMoreThanOneDayString(Context context, long j, String str, boolean z) {
        CharSequence formatElapsedTime = StringUtil.formatElapsedTime(context, roundTimeToNearestThreshold(j, ONE_HOUR_MILLIS), false, true);
        if (TextUtils.isEmpty(str)) {
            return context.getString(z ? R$string.power_remaining_duration_only_enhanced : R$string.power_remaining_duration_only, formatElapsedTime);
        }
        return context.getString(z ? R$string.power_discharging_duration_enhanced : R$string.power_discharging_duration, formatElapsedTime, str);
    }

    public static String getMoreThanTwoDaysString(Context context, String str) {
        MeasureFormat measureFormat = MeasureFormat.getInstance(context.getResources().getConfiguration().getLocales().get(0), MeasureFormat.FormatWidth.SHORT);
        Measure measure = new Measure(2, MeasureUnit.DAY);
        return TextUtils.isEmpty(str) ? context.getString(R$string.power_remaining_only_more_than_subtext, measureFormat.formatMeasures(measure)) : context.getString(R$string.power_remaining_more_than_subtext, measureFormat.formatMeasures(measure), str);
    }

    public static String getRegularTimeRemainingShortString(Context context, long j) {
        return context.getString(R$string.power_discharge_by_only_short, DateFormat.getInstanceForSkeleton(android.text.format.DateFormat.getTimeFormatString(context)).format(Date.from(Instant.ofEpochMilli(roundTimeToNearestThreshold(System.currentTimeMillis() + j, FIFTEEN_MINUTES_MILLIS)))));
    }

    public static String getRegularTimeRemainingString(Context context, long j, String str, boolean z) {
        CharSequence formatElapsedTime = StringUtil.formatElapsedTime(context, j, false, true);
        if (TextUtils.isEmpty(str)) {
            return context.getString(z ? R$string.power_remaining_duration_only_enhanced : R$string.power_remaining_duration_only, formatElapsedTime);
        }
        return context.getString(z ? R$string.power_discharging_duration_enhanced : R$string.power_discharging_duration, formatElapsedTime, str);
    }

    public static String getShutdownImminentString(Context context, String str) {
        return TextUtils.isEmpty(str) ? context.getString(R$string.power_remaining_duration_only_shutdown_imminent) : context.getString(R$string.power_remaining_duration_shutdown_imminent, str);
    }

    public static String getUnderFifteenString(Context context, CharSequence charSequence, String str) {
        return TextUtils.isEmpty(str) ? context.getString(R$string.power_remaining_less_than_duration_only, charSequence) : context.getString(R$string.power_remaining_less_than_duration, charSequence, str);
    }

    public static long roundTimeToNearestThreshold(long j, long j2) {
        long abs = Math.abs(j);
        long abs2 = Math.abs(j2);
        long j3 = abs % abs2;
        return j3 < abs2 / 2 ? abs - j3 : (abs - j3) + abs2;
    }
}