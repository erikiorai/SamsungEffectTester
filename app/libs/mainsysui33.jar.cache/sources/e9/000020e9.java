package com.android.systemui.qs;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import java.util.Collection;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSHost.class */
public interface QSHost {

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSHost$Callback.class */
    public interface Callback {
        void onTilesChanged();
    }

    void collapsePanels();

    Context getContext();

    InstanceId getNewInstanceId();

    UiEventLogger getUiEventLogger();

    Context getUserContext();

    int getUserId();

    int indexOf(String str);

    void openPanels();

    void removeTile(String str);

    void removeTiles(Collection<String> collection);

    void warn(String str, Throwable th);
}