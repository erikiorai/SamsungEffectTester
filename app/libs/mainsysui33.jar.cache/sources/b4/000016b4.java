package com.android.systemui.dreams;

import com.android.systemui.statusbar.policy.CallbackController;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayCallbackController.class */
public final class DreamOverlayCallbackController implements CallbackController<Callback> {
    public final Set<Callback> callbacks = new LinkedHashSet();
    public boolean isDreaming;

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayCallbackController$Callback.class */
    public interface Callback {
        void onStartDream();

        void onWakeUp();
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(Callback callback) {
        this.callbacks.add(callback);
    }

    public final boolean isDreaming() {
        return this.isDreaming;
    }

    public final void onStartDream() {
        this.isDreaming = true;
        for (Callback callback : this.callbacks) {
            callback.onStartDream();
        }
    }

    public final void onWakeUp() {
        this.isDreaming = false;
        for (Callback callback : this.callbacks) {
            callback.onWakeUp();
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void removeCallback(Callback callback) {
        this.callbacks.remove(callback);
    }
}