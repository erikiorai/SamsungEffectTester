package com.android.keyguard.sec;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes.dex */
public interface KeyguardEffectViewBase {
    void cleanUp();

    long getUnlockDelay();

    boolean handleHoverEvent(View view, MotionEvent motionEvent);

    boolean handleTouchEvent(View view, MotionEvent motionEvent);

    boolean handleTouchEventForPatternLock(View view, MotionEvent motionEvent);

    void handleUnlock(View view, MotionEvent motionEvent);

    void playLockSound();

    void reset();

    void screenTurnedOff();

    void screenTurnedOn();

    void setHidden(boolean hidden);

    void show();

    void showUnlockAffordance(long j, Rect rect);

    void update();

    void setContextualWallpaper(Bitmap bmp);

    void drawPause();

    void drawResume();
}