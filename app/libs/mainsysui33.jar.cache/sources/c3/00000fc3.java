package com.android.systemui;

import com.android.systemui.dump.nano.SystemUIProtoDump;

/* loaded from: mainsysui33.jar:com/android/systemui/ProtoDumpable.class */
public interface ProtoDumpable extends Dumpable {
    void dumpProto(SystemUIProtoDump systemUIProtoDump, String[] strArr);
}