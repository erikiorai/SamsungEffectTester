package com.samsung.android.visualeffect.lock.common;

import android.graphics.Bitmap;
import android.hardware.SensorEvent;
import android.view.MotionEvent;
import com.samsung.android.visualeffect.IEffectListener;

/* loaded from: classes.dex */
public interface ISPhysicsRenderer {
    void affordanceEffect(float f, float f2);

    void changeBackground(Bitmap bitmap, int i);

    void clearEffect();

    int getDrawCount();

    void initConfig(int i, int i2, IEffectListener iEffectListener);

    void onDestroy();

    void onSensorChanged(SensorEvent sensorEvent);

    boolean onTouchEvent(MotionEvent motionEvent);

    void reset();

    void screenTurnedOff();

    void screenTurnedOn();

    void setResourcesBitmap1(Bitmap bitmap);

    void setResourcesBitmap2(Bitmap bitmap);

    void show();

    void unlockEffect();
}