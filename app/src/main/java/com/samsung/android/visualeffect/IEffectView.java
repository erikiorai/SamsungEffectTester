package com.samsung.android.visualeffect;

import android.view.MotionEvent;
import android.view.View;
import java.util.HashMap;

public interface IEffectView {
    void handleCustomEvent(int i, HashMap<?, ?> hashMap);
    void handleTouchEvent(MotionEvent motionEvent, View view);
    void clearScreen();
    void setListener(IEffectListener iEffectListener);
}
