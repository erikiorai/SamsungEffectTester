package com.android.systemui.opensesame.lockscreen.effect;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes.dex */
public interface EffectViewBase {
    void cleanUp();

    Bitmap getDefaultWallpaper();

    long getUnlockDelay();

    boolean handleTouchEvent(View view, MotionEvent motionEvent);

    boolean needToSetDefaultWallpaper();

    void onPause();

    void onResume();

    void reset();

    void setDefaultWallpaper(Bitmap bitmap);

    void update();
}