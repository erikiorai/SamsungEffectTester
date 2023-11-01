package com.android.systemui.plugins;

import android.net.Uri;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ProvidesInterface(version = 6)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/FalsingManager.class */
public interface FalsingManager {
    public static final int HIGH_PENALTY = 3;
    public static final int LOW_PENALTY = 1;
    public static final int MODERATE_PENALTY = 2;
    public static final int NO_PENALTY = 0;
    public static final int VERSION = 6;

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/FalsingManager$FalsingBeliefListener.class */
    public interface FalsingBeliefListener {
        void onFalse();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/FalsingManager$FalsingTapListener.class */
    public interface FalsingTapListener {
        void onAdditionalTapRequired();
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/FalsingManager$Penalty.class */
    public @interface Penalty {
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/FalsingManager$ProximityEvent.class */
    public interface ProximityEvent {
        boolean getCovered();

        long getTimestampNs();
    }

    void addFalsingBeliefListener(FalsingBeliefListener falsingBeliefListener);

    void addTapListener(FalsingTapListener falsingTapListener);

    void cleanupInternal();

    void dump(PrintWriter printWriter, String[] strArr);

    boolean isClassifierEnabled();

    boolean isFalseDoubleTap();

    boolean isFalseLongTap(int i);

    boolean isFalseTap(int i);

    boolean isFalseTouch(int i);

    boolean isProximityNear();

    boolean isReportingEnabled();

    boolean isSimpleTap();

    boolean isUnlockingDisabled();

    void onProximityEvent(ProximityEvent proximityEvent);

    void onSuccessfulUnlock();

    void removeFalsingBeliefListener(FalsingBeliefListener falsingBeliefListener);

    void removeTapListener(FalsingTapListener falsingTapListener);

    Uri reportRejectedTouch();

    boolean shouldEnforceBouncer();
}