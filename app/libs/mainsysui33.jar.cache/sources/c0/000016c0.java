package com.android.systemui.dreams;

import com.android.systemui.statusbar.policy.CallbackController;
import java.util.List;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayNotificationCountProvider.class */
public class DreamOverlayNotificationCountProvider implements CallbackController<Callback> {
    public final List<Callback> mCallbacks;
    public final Set<String> mNotificationKeys;

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayNotificationCountProvider$Callback.class */
    public interface Callback {
        void onNotificationCountChanged(int i);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(Callback callback) {
        if (this.mCallbacks.contains(callback)) {
            return;
        }
        this.mCallbacks.add(callback);
        callback.onNotificationCountChanged(this.mNotificationKeys.size());
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }
}