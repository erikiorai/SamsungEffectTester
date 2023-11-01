package com.android.systemui.dock;

/* loaded from: mainsysui33.jar:com/android/systemui/dock/DockManager.class */
public interface DockManager {

    /* loaded from: mainsysui33.jar:com/android/systemui/dock/DockManager$AlignmentStateListener.class */
    public interface AlignmentStateListener {
        void onAlignmentStateChanged(int i);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dock/DockManager$DockEventListener.class */
    public interface DockEventListener {
        void onEvent(int i);
    }

    void addAlignmentStateListener(AlignmentStateListener alignmentStateListener);

    void addListener(DockEventListener dockEventListener);

    boolean isDocked();

    boolean isHidden();

    void removeListener(DockEventListener dockEventListener);
}