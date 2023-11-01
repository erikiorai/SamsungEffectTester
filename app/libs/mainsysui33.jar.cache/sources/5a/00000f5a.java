package com.android.systemui;

import android.content.res.Configuration;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/CoreStartable.class */
public interface CoreStartable extends Dumpable {
    @Override // com.android.systemui.Dumpable
    default void dump(PrintWriter printWriter, String[] strArr) {
    }

    default void onBootCompleted() {
    }

    default void onConfigurationChanged(Configuration configuration) {
    }

    void start();
}