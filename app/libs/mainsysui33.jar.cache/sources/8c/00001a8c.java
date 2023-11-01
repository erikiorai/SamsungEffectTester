package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.util.ListenerSet;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerCallbackInteractor.class */
public final class PrimaryBouncerCallbackInteractor {
    public ListenerSet<KeyguardBouncer.KeyguardResetCallback> resetCallbacks = new ListenerSet<>();
    public ArrayList<KeyguardBouncer.PrimaryBouncerExpansionCallback> expansionCallbacks = new ArrayList<>();

    public final void addBouncerExpansionCallback(KeyguardBouncer.PrimaryBouncerExpansionCallback primaryBouncerExpansionCallback) {
        if (this.expansionCallbacks.contains(primaryBouncerExpansionCallback)) {
            return;
        }
        this.expansionCallbacks.add(primaryBouncerExpansionCallback);
    }

    public final void dispatchExpansionChanged(float f) {
        Iterator<KeyguardBouncer.PrimaryBouncerExpansionCallback> it = this.expansionCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onExpansionChanged(f);
        }
    }

    public final void dispatchFullyHidden() {
        Iterator<KeyguardBouncer.PrimaryBouncerExpansionCallback> it = this.expansionCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onFullyHidden();
        }
    }

    public final void dispatchFullyShown() {
        Iterator<KeyguardBouncer.PrimaryBouncerExpansionCallback> it = this.expansionCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onFullyShown();
        }
    }

    public final void dispatchReset() {
        Iterator it = this.resetCallbacks.iterator();
        while (it.hasNext()) {
            ((KeyguardBouncer.KeyguardResetCallback) it.next()).onKeyguardReset();
        }
    }

    public final void dispatchStartingToHide() {
        Iterator<KeyguardBouncer.PrimaryBouncerExpansionCallback> it = this.expansionCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onStartingToHide();
        }
    }

    public final void dispatchStartingToShow() {
        Iterator<KeyguardBouncer.PrimaryBouncerExpansionCallback> it = this.expansionCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onStartingToShow();
        }
    }

    public final void dispatchVisibilityChanged(int i) {
        Iterator<KeyguardBouncer.PrimaryBouncerExpansionCallback> it = this.expansionCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onVisibilityChanged(i == 0);
        }
    }

    public final void removeBouncerExpansionCallback(KeyguardBouncer.PrimaryBouncerExpansionCallback primaryBouncerExpansionCallback) {
        this.expansionCallbacks.remove(primaryBouncerExpansionCallback);
    }
}