package com.android.systemui.dreams.touch;

import android.os.Looper;
import android.view.Choreographer;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.shared.system.InputChannelCompat;
import com.android.systemui.shared.system.InputMonitorCompat;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/InputSession.class */
public class InputSession {
    public final GestureDetector mGestureDetector;
    public final InputChannelCompat.InputEventReceiver mInputEventReceiver;
    public final InputMonitorCompat mInputMonitor;

    public InputSession(String str, final InputChannelCompat.InputEventListener inputEventListener, GestureDetector.OnGestureListener onGestureListener, final boolean z) {
        InputMonitorCompat inputMonitorCompat = new InputMonitorCompat(str, 0);
        this.mInputMonitor = inputMonitorCompat;
        this.mGestureDetector = new GestureDetector(onGestureListener);
        this.mInputEventReceiver = inputMonitorCompat.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat.InputEventListener() { // from class: com.android.systemui.dreams.touch.InputSession$$ExternalSyntheticLambda0
            public final void onInputEvent(InputEvent inputEvent) {
                InputSession.this.lambda$new$0(inputEventListener, z, inputEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(InputChannelCompat.InputEventListener inputEventListener, boolean z, InputEvent inputEvent) {
        inputEventListener.onInputEvent(inputEvent);
        if ((inputEvent instanceof MotionEvent) && this.mGestureDetector.onTouchEvent((MotionEvent) inputEvent) && z) {
            this.mInputMonitor.pilferPointers();
        }
    }

    public void dispose() {
        InputChannelCompat.InputEventReceiver inputEventReceiver = this.mInputEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
        }
        InputMonitorCompat inputMonitorCompat = this.mInputMonitor;
        if (inputMonitorCompat != null) {
            inputMonitorCompat.dispose();
        }
    }
}