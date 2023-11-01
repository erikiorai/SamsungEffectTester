package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.systemui.CoreStartable;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionCoreStartable.class */
public final class KeyguardTransitionCoreStartable implements CoreStartable {
    public static final Companion Companion = new Companion(null);
    public final KeyguardTransitionAuditLogger auditLogger;
    public final Set<TransitionInteractor> interactors;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionCoreStartable$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardTransitionCoreStartable(Set<TransitionInteractor> set, KeyguardTransitionAuditLogger keyguardTransitionAuditLogger) {
        this.interactors = set;
        this.auditLogger = keyguardTransitionAuditLogger;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        for (TransitionInteractor transitionInteractor : this.interactors) {
            if (transitionInteractor instanceof FromBouncerTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromAodTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromGoneTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromLockscreenTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromDreamingTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromOccludedTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (!(transitionInteractor instanceof FromDozingTransitionInteractor)) {
                throw new NoWhenBranchMatchedException();
            } else {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            }
            transitionInteractor.start();
        }
        this.auditLogger.start();
    }
}