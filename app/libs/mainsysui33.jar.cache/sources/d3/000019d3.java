package com.android.systemui.keyguard.data.repository;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Trace;
import android.util.Log;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import java.util.UUID;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardTransitionRepositoryImpl.class */
public final class KeyguardTransitionRepositoryImpl implements KeyguardTransitionRepository {
    public static final Companion Companion = new Companion(null);
    public final MutableSharedFlow<TransitionStep> _transitions;
    public ValueAnimator lastAnimator;
    public TransitionStep lastStep;
    public final Flow<TransitionStep> transitions;
    public UUID updateTransitionId;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardTransitionRepositoryImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardTransitionRepositoryImpl() {
        MutableSharedFlow<TransitionStep> MutableSharedFlow = SharedFlowKt.MutableSharedFlow(2, 10, BufferOverflow.DROP_OLDEST);
        this._transitions = MutableSharedFlow;
        this.transitions = FlowKt.distinctUntilChanged(FlowKt.asSharedFlow(MutableSharedFlow));
        this.lastStep = new TransitionStep(null, null, ActionBarShadowController.ELEVATION_LOW, null, null, 31, null);
        KeyguardState keyguardState = KeyguardState.OFF;
        KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
        TransitionState transitionState = TransitionState.STARTED;
        String simpleName = Reflection.getOrCreateKotlinClass(KeyguardTransitionRepositoryImpl.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        emitTransition$default(this, new TransitionStep(keyguardState, keyguardState2, ActionBarShadowController.ELEVATION_LOW, transitionState, simpleName), false, 2, null);
        TransitionState transitionState2 = TransitionState.FINISHED;
        String simpleName2 = Reflection.getOrCreateKotlinClass(KeyguardTransitionRepositoryImpl.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName2);
        emitTransition$default(this, new TransitionStep(keyguardState, keyguardState2, 1.0f, transitionState2, simpleName2), false, 2, null);
    }

    public static /* synthetic */ void emitTransition$default(KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl, TransitionStep transitionStep, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        keyguardTransitionRepositoryImpl.emitTransition(transitionStep, z);
    }

    public final void emitTransition(TransitionStep transitionStep, boolean z) {
        trace(transitionStep, z);
        if (!this._transitions.tryEmit(transitionStep)) {
            Log.w("KeyguardTransitionRepository", "Failed to emit next value without suspending");
        }
        this.lastStep = transitionStep;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository
    public Flow<TransitionStep> getTransitions() {
        return this.transitions;
    }

    /* JADX WARN: Type inference failed for: r0v22, types: [com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1$updateListener$1, android.animation.ValueAnimator$AnimatorUpdateListener] */
    @Override // com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository
    public UUID startTransition(final TransitionInfo transitionInfo) {
        if (this.lastStep.getFrom() == transitionInfo.getFrom() && this.lastStep.getTo() == transitionInfo.getTo()) {
            Log.i("KeyguardTransitionRepository", "Duplicate call to start the transition, rejecting: " + transitionInfo);
            return null;
        }
        if (this.lastStep.getTransitionState() != TransitionState.FINISHED) {
            TransitionStep transitionStep = this.lastStep;
            Log.i("KeyguardTransitionRepository", "Transition still active: " + transitionStep + ", canceling");
        }
        final float value = 1.0f - this.lastStep.getValue();
        ValueAnimator valueAnimator = this.lastAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.lastAnimator = transitionInfo.getAnimator();
        final ValueAnimator animator = transitionInfo.getAnimator();
        if (animator == null) {
            emitTransition$default(this, new TransitionStep(transitionInfo, ActionBarShadowController.ELEVATION_LOW, TransitionState.STARTED), false, 2, null);
            UUID randomUUID = UUID.randomUUID();
            this.updateTransitionId = randomUUID;
            return randomUUID;
        }
        animator.setFloatValues(value, 1.0f);
        animator.setDuration((1.0f - value) * ((float) animator.getDuration()));
        final ?? r0 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1$updateListener$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                KeyguardTransitionRepositoryImpl.emitTransition$default(KeyguardTransitionRepositoryImpl.this, new TransitionStep(transitionInfo, ((Float) valueAnimator2.getAnimatedValue()).floatValue(), TransitionState.RUNNING), false, 2, null);
            }
        };
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1$adapter$1
            public final void endAnimation(Animator animator2, float f, TransitionState transitionState) {
                KeyguardTransitionRepositoryImpl.emitTransition$default(KeyguardTransitionRepositoryImpl.this, new TransitionStep(transitionInfo, f, transitionState), false, 2, null);
                animator.removeListener(this);
                animator.removeUpdateListener(r0);
                KeyguardTransitionRepositoryImpl.this.lastAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
                TransitionStep transitionStep2;
                transitionStep2 = KeyguardTransitionRepositoryImpl.this.lastStep;
                endAnimation(animator2, transitionStep2.getValue(), TransitionState.CANCELED);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                endAnimation(animator2, 1.0f, TransitionState.FINISHED);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
                KeyguardTransitionRepositoryImpl.emitTransition$default(KeyguardTransitionRepositoryImpl.this, new TransitionStep(transitionInfo, value, TransitionState.STARTED), false, 2, null);
            }
        });
        animator.addUpdateListener(r0);
        animator.start();
        return null;
    }

    public final void trace(TransitionStep transitionStep, boolean z) {
        TransitionState transitionState = transitionStep.getTransitionState();
        TransitionState transitionState2 = TransitionState.STARTED;
        if (transitionState == transitionState2 || transitionStep.getTransitionState() == TransitionState.FINISHED) {
            String str = "Transition: " + transitionStep.getFrom() + " -> " + transitionStep.getTo() + " " + (z ? "(manual)" : "");
            int hashCode = str.hashCode();
            if (transitionStep.getTransitionState() == transitionState2) {
                Trace.beginAsyncSection(str, hashCode);
            } else if (transitionStep.getTransitionState() == TransitionState.FINISHED) {
                Trace.endAsyncSection(str, hashCode);
            }
        }
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository
    public Flow<TransitionStep> transition(KeyguardState keyguardState, KeyguardState keyguardState2) {
        return KeyguardTransitionRepository.DefaultImpls.transition(this, keyguardState, keyguardState2);
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository
    public void updateTransition(UUID uuid, float f, TransitionState transitionState) {
        if (Intrinsics.areEqual(this.updateTransitionId, uuid)) {
            if (transitionState == TransitionState.FINISHED) {
                this.updateTransitionId = null;
            }
            emitTransition(TransitionStep.copy$default(this.lastStep, null, null, f, transitionState, null, 19, null), true);
            return;
        }
        Log.wtf("KeyguardTransitionRepository", "Attempting to update with old/invalid transitionId: " + uuid);
    }
}