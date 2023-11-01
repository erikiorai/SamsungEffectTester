package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDreamingTransitionInteractor.class */
public final class FromDreamingTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final CoroutineScope scope;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDreamingTransitionInteractor$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getTO_LOCKSCREEN_DURATION-UwyO8pc  reason: not valid java name */
        public final long m3012getTO_LOCKSCREEN_DURATIONUwyO8pc() {
            return FromDreamingTransitionInteractor.TO_LOCKSCREEN_DURATION;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        DEFAULT_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(1183, durationUnit);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FromDreamingTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromDreamingTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
    }

    /* renamed from: getAnimator-LRDsOJo$default  reason: not valid java name */
    public static /* synthetic */ ValueAnimator m3010getAnimatorLRDsOJo$default(FromDreamingTransitionInteractor fromDreamingTransitionInteractor, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = DEFAULT_DURATION;
        }
        return fromDreamingTransitionInteractor.m3011getAnimatorLRDsOJo(j);
    }

    /* renamed from: getAnimator-LRDsOJo  reason: not valid java name */
    public final ValueAnimator m3011getAnimatorLRDsOJo(long j) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(Duration.getInWholeMilliseconds-impl(j));
        return valueAnimator;
    }

    public final void listenForDreamingToDozing() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromDreamingTransitionInteractor$listenForDreamingToDozing$1(this, null), 3, (Object) null);
    }

    public final void listenForDreamingToGone() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromDreamingTransitionInteractor$listenForDreamingToGone$1(this, null), 3, (Object) null);
    }

    public final void listenForDreamingToLockscreen() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1(this, null), 3, (Object) null);
    }

    public final void listenForDreamingToOccluded() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromDreamingTransitionInteractor$listenForDreamingToOccluded$1(this, null), 3, (Object) null);
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public void start() {
        listenForDreamingToLockscreen();
        listenForDreamingToOccluded();
        listenForDreamingToGone();
        listenForDreamingToDozing();
    }
}