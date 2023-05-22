package com.aj.effect.lock;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public interface KeyguardEffectViewBase {
    void reset();

    void showUnlockAffordance(long j, Rect rect);

    void update();

    boolean handleTouchEventForPatternLock(MotionEvent motionEvent);

    boolean handleTouchEvent(View view, MotionEvent motionEvent);

    void screenTurnedOn();

    void screenTurnedOff();

    void handleUnlock(View view, MotionEvent motionEvent);

    void show();

    void playLockSound();

    long getUnlockDelay();
}
