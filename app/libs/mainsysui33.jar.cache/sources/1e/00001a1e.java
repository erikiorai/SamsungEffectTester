package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import java.util.UUID;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor.class */
public final class FromLockscreenTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final CoroutineScope scope;
    public final ShadeRepository shadeRepository;
    public UUID transitionId;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FromLockscreenTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, ShadeRepository shadeRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardTransitionRepository keyguardTransitionRepository) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromLockscreenTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.shadeRepository = shadeRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
    }

    public final ValueAnimator getAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(500L);
        return valueAnimator;
    }

    public final void listenForLockscreenToAod() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromLockscreenTransitionInteractor$listenForLockscreenToAod$1(this, null), 3, (Object) null);
    }

    public final void listenForLockscreenToBouncer() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1(this, null), 3, (Object) null);
    }

    public final void listenForLockscreenToBouncerDragging() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1(this, null), 3, (Object) null);
    }

    public final void listenForLockscreenToDreaming() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1(this, null), 3, (Object) null);
    }

    public final void listenForLockscreenToGone() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromLockscreenTransitionInteractor$listenForLockscreenToGone$1(this, null), 3, (Object) null);
    }

    public final void listenForLockscreenToOccluded() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1(this, null), 3, (Object) null);
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public void start() {
        listenForLockscreenToGone();
        listenForLockscreenToOccluded();
        listenForLockscreenToAod();
        listenForLockscreenToBouncer();
        listenForLockscreenToDreaming();
        listenForLockscreenToBouncerDragging();
    }
}