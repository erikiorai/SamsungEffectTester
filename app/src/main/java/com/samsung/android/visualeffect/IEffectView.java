package com.samsung.android.visualeffect;

import android.view.MotionEvent;
import android.view.View;
import java.util.HashMap;

/* loaded from: classes.dex */
public interface IEffectView {

    void clearScreen();

    void handleCustomEvent(int i, HashMap<?, ?> hashMap);

    void handleTouchEvent(MotionEvent motionEvent, View view);

    void init(EffectDataObj effectDataObj);

    void reInit(EffectDataObj effectDataObj);

    void removeListener();

    void setListener(IEffectListener iEffectListener);

}