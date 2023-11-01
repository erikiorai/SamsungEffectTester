package com.android.systemui.plugins.statusbar;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(version = 1)
@DependsOn(target = StateListener.class)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/statusbar/StatusBarStateController.class */
public interface StatusBarStateController {
    public static final int VERSION = 1;

    @ProvidesInterface(version = 1)
    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/statusbar/StatusBarStateController$StateListener.class */
    public interface StateListener {
        public static final int VERSION = 1;

        default void onDozeAmountChanged(float f, float f2) {
        }

        default void onDozingChanged(boolean z) {
        }

        default void onExpandedChanged(boolean z) {
        }

        default void onFullscreenStateChanged(boolean z) {
        }

        default void onPulsingChanged(boolean z) {
        }

        default void onStateChanged(int i) {
        }

        default void onStatePostChange() {
        }

        default void onStatePreChange(int i, int i2) {
        }

        default void onUpcomingStateChanged(int i) {
        }
    }

    void addCallback(StateListener stateListener);

    float getDozeAmount();

    int getState();

    boolean isDozing();

    boolean isExpanded();

    boolean isPulsing();

    void removeCallback(StateListener stateListener);
}