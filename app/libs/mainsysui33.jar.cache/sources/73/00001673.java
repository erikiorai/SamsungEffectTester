package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTransitionCallback.class */
public interface DozeTransitionCallback {
    void onDozeTransition(DozeMachine.State state, DozeMachine.State state2);
}