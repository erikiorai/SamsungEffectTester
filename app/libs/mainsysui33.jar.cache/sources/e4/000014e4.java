package com.android.systemui.dagger;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import com.android.systemui.recents.RecentsImplementation;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ContextComponentHelper.class */
public interface ContextComponentHelper {
    Activity resolveActivity(String str);

    BroadcastReceiver resolveBroadcastReceiver(String str);

    RecentsImplementation resolveRecents(String str);

    Service resolveService(String str);
}