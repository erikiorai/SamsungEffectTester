package com.android.systemui;

/* loaded from: mainsysui33.jar:com/android/systemui/BootCompleteCache.class */
public interface BootCompleteCache {

    /* loaded from: mainsysui33.jar:com/android/systemui/BootCompleteCache$BootCompleteListener.class */
    public interface BootCompleteListener {
        void onBootComplete();
    }

    boolean addListener(BootCompleteListener bootCompleteListener);

    boolean isBootComplete();
}