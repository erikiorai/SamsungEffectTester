package com.android.systemui.dreams.touch.dagger;

import android.view.GestureDetector;
import com.android.systemui.dreams.touch.InputSession;
import com.android.systemui.shared.system.InputChannelCompat;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/dagger/InputSessionComponent.class */
public interface InputSessionComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/dagger/InputSessionComponent$Factory.class */
    public interface Factory {
        InputSessionComponent create(String str, InputChannelCompat.InputEventListener inputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z);
    }

    InputSession getInputSession();
}