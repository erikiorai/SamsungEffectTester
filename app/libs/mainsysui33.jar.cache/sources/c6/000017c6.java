package com.android.systemui.flags;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagListenable.class */
public interface FlagListenable {

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagListenable$FlagEvent.class */
    public interface FlagEvent {
        int getFlagId();

        void requestNoRestart();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagListenable$Listener.class */
    public interface Listener {
        void onFlagChanged(FlagEvent flagEvent);
    }

    void addListener(Flag<?> flag, Listener listener);

    void removeListener(Listener listener);
}