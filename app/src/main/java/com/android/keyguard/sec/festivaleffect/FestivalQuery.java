package com.android.keyguard.sec.festivaleffect;

import android.text.format.Time;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class FestivalQuery {
    private static final String TAG = "FestivalQuery";
    private static final boolean chineseSeason_metaTag = true;

    public static final int SPRING_EFFECT = 1;
    public static final int SUMMER_EFFECT = 2;
    public static final int AUTUMN_EFFECT = 3;
    public static final int WINTER_EFFECT = 4;

    public static int getCurrentSeason() {
        int effect = 0;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (chineseSeason_metaTag) {
            int years = year - 2000;
            double leap = years * 0.2422d;
            int startOfSpring = (int) ((leap + 3.87d) - (years / 4));
            int startOfSummer = (int) ((leap + 5.52d) - (years / 4));
            int startOfAutumn = (int) ((leap + 7.5d) - (years / 4));
            int startOfWinter = (int) ((leap + 7.438d) - (years / 4));
            if (month == Calendar.FEBRUARY) {
                effect = day >= startOfSpring ? SPRING_EFFECT : WINTER_EFFECT;
            } else if (Calendar.FEBRUARY < month && month < Calendar.MAY) {
                effect = SPRING_EFFECT;
            } else if (month == Calendar.MAY) {
                effect = day >= startOfSummer ? SUMMER_EFFECT : SPRING_EFFECT;
            } else if (Calendar.MAY < month && month < Calendar.AUGUST) {
                effect = SUMMER_EFFECT;
            } else if (month == Calendar.AUGUST) {
                effect = day >= startOfAutumn ? AUTUMN_EFFECT : SUMMER_EFFECT;
            } else if (Calendar.AUGUST < month && month < Calendar.NOVEMBER) {
                effect = AUTUMN_EFFECT;
            } else if (month == Calendar.NOVEMBER) {
                effect = day >= startOfWinter ? WINTER_EFFECT : AUTUMN_EFFECT;
            } else if (month > Calendar.NOVEMBER || month < Calendar.FEBRUARY) {
                effect = WINTER_EFFECT;
            }
        } else {
            if (month < Calendar.MARCH || month == Calendar.DECEMBER)
                effect = WINTER_EFFECT;
            else if (month >= Calendar.MARCH && month < Calendar.JUNE)
                effect = SPRING_EFFECT;
            else if (month >= Calendar.JUNE && month < Calendar.SEPTEMBER)
                effect = SUMMER_EFFECT;
            else if (month >= Calendar.SEPTEMBER && month < Calendar.DECEMBER)
                effect = AUTUMN_EFFECT;
            Log.d(TAG, "getCurrentSeason: month is " + (month+1));
        }
        Log.i(TAG, "CurrentSeason: " + effect);
        return effect;
    }


}
