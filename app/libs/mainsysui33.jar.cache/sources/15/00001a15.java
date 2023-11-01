package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromGoneTransitionInteractor.class */
public final class FromGoneTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final CoroutineScope scope;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromGoneTransitionInteractor$Companion.class */
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
    public FromGoneTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromGoneTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
    }

    public final ValueAnimator getAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(500L);
        return valueAnimator;
    }

    public final void listenForGoneToAod() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromGoneTransitionInteractor$listenForGoneToAod$1(this, null), 3, (Object) null);
    }

    public final void listenForGoneToDreaming() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromGoneTransitionInteractor$listenForGoneToDreaming$1(this, null), 3, (Object) null);
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public void start() {
        listenForGoneToAod();
        listenForGoneToDreaming();
    }
}