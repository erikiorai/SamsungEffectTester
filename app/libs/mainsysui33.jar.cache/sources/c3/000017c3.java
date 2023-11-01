package com.android.systemui.flags;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/Flag.class */
public interface Flag<T> {
    int getId();

    String getName();

    String getNamespace();

    boolean getTeamfood();
}