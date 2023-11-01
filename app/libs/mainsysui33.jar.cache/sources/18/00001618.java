package com.android.systemui.doze;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeHost.class */
public interface DozeHost {

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeHost$Callback.class */
    public interface Callback {
        default void onAlwaysOnSuppressedChanged(boolean z) {
        }

        default void onDozingChanged(boolean z) {
        }

        default void onNotificationAlerted(Runnable runnable) {
        }

        default void onPowerSaveChanged(boolean z) {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeHost$PulseCallback.class */
    public interface PulseCallback {
        void onPulseFinished();

        void onPulseStarted();
    }

    void addCallback(Callback callback);

    void cancelGentleSleep();

    void dozeTimeTick();

    void extendPulse(int i);

    boolean isAlwaysOnSuppressed();

    boolean isPowerSaveActive();

    boolean isProvisioned();

    boolean isPulsePending();

    boolean isPulsingBlocked();

    void onIgnoreTouchWhilePulsing(boolean z);

    void onSlpiTap(float f, float f2);

    void prepareForGentleSleep(Runnable runnable);

    void pulseWhileDozing(PulseCallback pulseCallback, int i);

    void removeCallback(Callback callback);

    void setAnimateWakeup(boolean z);

    default void setAodDimmingScrim(float f) {
    }

    void setDozeScreenBrightness(int i);

    void setPulsePending(boolean z);

    void startDozing();

    void stopDozing();
}