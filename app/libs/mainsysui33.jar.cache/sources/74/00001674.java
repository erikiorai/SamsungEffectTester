package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTransitionListener.class */
public final class DozeTransitionListener implements DozeMachine.Part, CallbackController<DozeTransitionCallback> {
    public final Set<DozeTransitionCallback> callbacks = new LinkedHashSet();
    public DozeMachine.State newState;
    public DozeMachine.State oldState;

    public DozeTransitionListener() {
        DozeMachine.State state = DozeMachine.State.UNINITIALIZED;
        this.oldState = state;
        this.newState = state;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(DozeTransitionCallback dozeTransitionCallback) {
        this.callbacks.add(dozeTransitionCallback);
    }

    public final DozeMachine.State getNewState() {
        return this.newState;
    }

    public final DozeMachine.State getOldState() {
        return this.oldState;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void removeCallback(DozeTransitionCallback dozeTransitionCallback) {
        this.callbacks.remove(dozeTransitionCallback);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        this.oldState = state;
        this.newState = state2;
        for (DozeTransitionCallback dozeTransitionCallback : this.callbacks) {
            dozeTransitionCallback.onDozeTransition(state, state2);
        }
    }
}