package com.android.keyguard.sec;

import android.content.Context;
import android.os.Handler;

/* loaded from: classes.dex */
public class KeyguardFestivalEffect {
    public KeyguardFestivalEffect(Context context, Handler handler) {
    }

    public boolean isActivated() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    public boolean isUnlockEffectEnabled() {
        return false;
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
        return null;
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
        return null;
    }

    public void pauseAnimation() {
    }

    public void resumeAnimation() {
    }
}