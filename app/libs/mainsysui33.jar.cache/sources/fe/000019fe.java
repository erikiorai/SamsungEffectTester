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

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDozingTransitionInteractor.class */
public final class FromDozingTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardTransitionRepository keyguardTransitionRepository;
    public final CoroutineScope scope;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDozingTransitionInteractor$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DEFAULT_DURATION = DurationKt.toDuration(500, DurationUnit.MILLISECONDS);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FromDozingTransitionInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(r0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(FromDozingTransitionInteractor.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        this.scope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionRepository = keyguardTransitionRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
    }

    /* renamed from: getAnimator-LRDsOJo$default  reason: not valid java name */
    public static /* synthetic */ ValueAnimator m3004getAnimatorLRDsOJo$default(FromDozingTransitionInteractor fromDozingTransitionInteractor, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = DEFAULT_DURATION;
        }
        return fromDozingTransitionInteractor.m3005getAnimatorLRDsOJo(j);
    }

    /* renamed from: getAnimator-LRDsOJo  reason: not valid java name */
    public final ValueAnimator m3005getAnimatorLRDsOJo(long j) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(Duration.getInWholeMilliseconds-impl(j));
        return valueAnimator;
    }

    public final void listenForDozingToLockscreen() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new FromDozingTransitionInteractor$listenForDozingToLockscreen$1(this, null), 3, (Object) null);
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public void start() {
        listenForDozingToLockscreen();
    }
}