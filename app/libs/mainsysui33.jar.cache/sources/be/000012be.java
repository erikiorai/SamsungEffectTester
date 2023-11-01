package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.classifier.FalsingClassifier;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingCollector.class */
public interface FalsingCollector {
    void avoidGesture();

    boolean isReportingEnabled();

    void onA11yAction();

    void onBouncerHidden();

    void onBouncerShown();

    void onExpansionFromPulseStopped();

    void onMotionEventComplete();

    void onNotificationActive();

    void onNotificationDismissed();

    void onNotificationStartDismissing();

    void onNotificationStartDraggingDown();

    void onNotificationStopDismissing();

    void onNotificationStopDraggingDown();

    void onQsDown();

    void onScreenOff();

    void onScreenOnFromTouch();

    void onScreenTurningOn();

    void onStartExpandingFromPulse();

    void onSuccessfulUnlock();

    void onTouchEvent(MotionEvent motionEvent);

    void onTrackingStarted(boolean z);

    void onTrackingStopped();

    void onUnlockHintStarted();

    void setNotificationExpanded();

    void setShowingAod(boolean z);

    boolean shouldEnforceBouncer();

    void updateFalseConfidence(FalsingClassifier.Result result);
}