package com.android.systemui.dreams.touch;

import android.graphics.Region;
import android.view.GestureDetector;
import com.android.systemui.shared.system.InputChannelCompat;
import com.google.common.util.concurrent.ListenableFuture;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/DreamTouchHandler.class */
public interface DreamTouchHandler {

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/DreamTouchHandler$TouchSession.class */
    public interface TouchSession {

        /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/DreamTouchHandler$TouchSession$Callback.class */
        public interface Callback {
            void onRemoved();
        }

        int getActiveSessionCount();

        ListenableFuture<TouchSession> pop();

        void registerCallback(Callback callback);

        boolean registerGestureListener(GestureDetector.OnGestureListener onGestureListener);

        boolean registerInputListener(InputChannelCompat.InputEventListener inputEventListener);
    }

    default void getTouchInitiationRegion(Region region) {
    }

    void onSessionStart(TouchSession touchSession);
}