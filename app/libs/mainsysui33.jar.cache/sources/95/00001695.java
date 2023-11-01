package com.android.systemui.doze.dagger;

import com.android.systemui.doze.DozeMachine;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/dagger/DozeComponent.class */
public interface DozeComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/dagger/DozeComponent$Builder.class */
    public interface Builder {
        DozeComponent build(DozeMachine.Service service);
    }

    DozeMachine getDozeMachine();
}