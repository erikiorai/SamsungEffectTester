package com.android.systemui.flags;

import java.util.Collection;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/ServerFlagReader.class */
public interface ServerFlagReader {

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/ServerFlagReader$ChangeListener.class */
    public interface ChangeListener {
        void onChange();
    }

    boolean hasOverride(String str, String str2);

    void listenForChanges(Collection<? extends Flag<?>> collection, ChangeListener changeListener);

    boolean readServerOverride(String str, String str2, boolean z);
}