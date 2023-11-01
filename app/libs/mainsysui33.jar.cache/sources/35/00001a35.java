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

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor.class */
public final class FromOccludedTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final CoroutineScope scope;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromOccludedTransitionInteractor$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getTO_LOCKSCREEN_DURATION-UwyO8pc  reason: not valid java name */
        public final long m3034getTO_LOCKSCREEN_DURATIONUwyO8pc() {
            return FromOccludedTransitionInteractor.TO_LOCKSCREEN_DURATION;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        DEFAULT_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(933, durationUnit);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FromOccludedTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromOccludedTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
    }

    /* renamed from: getAnimator-LRDsOJo$default  reason: not valid java name */
    public static /* synthetic */ ValueAnimator m3032getAnimatorLRDsOJo$default(FromOccludedTransitionInteractor fromOccludedTransitionInteractor, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = DEFAULT_DURATION;
        }
        return fromOccludedTransitionInteractor.m3033getAnimatorLRDsOJo(j);
    }

    /* renamed from: getAnimator-LRDsOJo  reason: not valid java name */
    public final ValueAnimator m3033getAnimatorLRDsOJo(long j) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(Duration.getInWholeMilliseconds-impl(j));
        return valueAnimator;
    }

    public final void listenForOccludedToDreaming() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromOccludedTransitionInteractor$listenForOccludedToDreaming$1(this, null), 3, (Object) null);
    }

    public final void listenForOccludedToLockscreen() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromOccludedTransitionInteractor$listenForOccludedToLockscreen$1(this, null), 3, (Object) null);
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public void start() {
        listenForOccludedToLockscreen();
        listenForOccludedToDreaming();
    }
}