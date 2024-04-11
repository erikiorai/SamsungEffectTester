package com.android.keyguard.sec;

import android.content.Context;
import android.os.Handler;

import com.android.keyguard.sec.festivaleffect.FestivalQuery;
import com.android.keyguard.sec.festivaleffect.unlockeffect.autumn.KeyguardEffectViewAutumn;
import com.android.keyguard.sec.festivaleffect.unlockeffect.spring.KeyguardEffectViewSpring;
import com.android.keyguard.sec.festivaleffect.unlockeffect.summer.KeyguardEffectViewSummer;
import com.android.keyguard.sec.festivaleffect.unlockeffect.winter.KeyguardEffectViewWinter;

import java.util.Locale;

/* todo FIX EVERY SINGLE THING */
public class KeyguardFestivalEffect {
    private Context context;
    private KeyguardEffectViewBase mUnlockViewBase;


    public KeyguardFestivalEffect(Context context, Handler handler) {
        this.context = context;
    }

    public boolean isActivated() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    public boolean isUnlockEffectEnabled() {
        return true;
    }

    public boolean isChargeEffectEnable() {
        return false;
    }

    public boolean isCommonDayShowFestivalWallpaper() {
        return false;
    }

    public boolean isChargeState() {
        return false;
    }

    public KeyguardEffectViewBase getFestivalView() {
        return null;
    }

    public KeyguardEffectViewBase getUnlockEffectView() {
        mUnlockViewBase = createView(FestivalQuery.getCurrentSeason());
        return mUnlockViewBase;
    }

    private KeyguardEffectViewBase createView(int currentSeason) {
        switch (currentSeason) {
            case FestivalQuery.SPRING_EFFECT:
                return new KeyguardEffectViewSpring(context);
            case FestivalQuery.SUMMER_EFFECT:
                return new KeyguardEffectViewSummer(context);
            case FestivalQuery.AUTUMN_EFFECT:
                return new KeyguardEffectViewAutumn(context);
            case FestivalQuery.WINTER_EFFECT:
                return new KeyguardEffectViewWinter(context);
        }
        return getUnlockEffectView();
    }


    public boolean isFestivalToday() {
        return false;
    }

    public KeyguardEffectViewBase getChargeEffectView() {
        return null;
    }

    public boolean GetScreenState() {
        return false;
    }

    public boolean GetShowState() {
        return false;
    }

    public void SetScreenState(boolean Flag) {
    }

    public void SetShowState(boolean Flag) {
    }

    public int getCurrentSeason() {
        return 0;
    }

    public KeyguardEffectViewBase makeForeground(Context context, KeyguardEffectViewBase foreground, String foregroundType) {
        return null;
    }

    public String getFestivalEffectClassName(String nameOfEffect) {
        if (nameOfEffect == "Seasonal") {
            switch (FestivalQuery.getCurrentSeason()) {
                case FestivalQuery.SPRING_EFFECT:
                    nameOfEffect = "Spring";
                    break;
                case FestivalQuery.SUMMER_EFFECT:
                    nameOfEffect = "Summer";
                    break;
                case FestivalQuery.AUTUMN_EFFECT:
                    nameOfEffect = "Autumn";
                    break;
                case FestivalQuery.WINTER_EFFECT:
                    nameOfEffect = "Winter";
                    break;
            }
        }
        return "com.android.keyguard.sec.festivaleffect.unlockeffect." + nameOfEffect.toLowerCase(Locale.ENGLISH) + ".KeyguardEffectView" + nameOfEffect;
    }

    public void pauseAnimation() {
    }

    public void resumeAnimation() {
    }
}