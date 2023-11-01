package com.android.systemui.privacy;

import com.android.systemui.Dumpable;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyItemMonitor.class */
public interface PrivacyItemMonitor extends Dumpable {

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyItemMonitor$Callback.class */
    public interface Callback {
        void onPrivacyItemsChanged();
    }

    List<PrivacyItem> getActivePrivacyItems();

    void startListening(Callback callback);

    void stopListening();
}