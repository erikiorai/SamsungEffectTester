package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromBouncerTransitionInteractor.class */
public final class FromBouncerTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final CoroutineScope scope;
    public final ShadeRepository shadeRepository;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromBouncerTransitionInteractor$Companion.class */
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
    public FromBouncerTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, ShadeRepository shadeRepository, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromBouncerTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.shadeRepository = shadeRepository;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
    }

    public final ValueAnimator getAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(300L);
        return valueAnimator;
    }

    public final void listenForBouncerToGone() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromBouncerTransitionInteractor$listenForBouncerToGone$1(this, null), 3, (Object) null);
    }

    public final void listenForBouncerToLockscreenOrAod() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1(this, null), 3, (Object) null);
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public void start() {
        listenForBouncerToGone();
        listenForBouncerToLockscreenOrAod();
    }
}