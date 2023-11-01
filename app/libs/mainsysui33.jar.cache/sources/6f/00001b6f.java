package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.AnimationParams;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/OccludedToLockscreenTransitionViewModel.class */
public final class OccludedToLockscreenTransitionViewModel {
    public static final Companion Companion = new Companion(null);
    public static final AnimationParams LOCKSCREEN_ALPHA;
    public static final long LOCKSCREEN_ANIMATION_DURATION_MS;
    public static final AnimationParams LOCKSCREEN_TRANSLATION_Y;
    public final KeyguardTransitionInteractor interactor;
    public final Flow<Float> lockscreenAlpha = flowForAnimation(LOCKSCREEN_ALPHA);

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/OccludedToLockscreenTransitionViewModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        FromOccludedTransitionInteractor.Companion companion = FromOccludedTransitionInteractor.Companion;
        LOCKSCREEN_ANIMATION_DURATION_MS = Duration.getInWholeMilliseconds-impl(companion.m3034getTO_LOCKSCREEN_DURATIONUwyO8pc());
        LOCKSCREEN_TRANSLATION_Y = new AnimationParams(0L, companion.m3034getTO_LOCKSCREEN_DURATIONUwyO8pc(), 1, null);
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        LOCKSCREEN_ALPHA = new AnimationParams(DurationKt.toDuration(233, durationUnit), DurationKt.toDuration(250, durationUnit), null);
    }

    public OccludedToLockscreenTransitionViewModel(KeyguardTransitionInteractor keyguardTransitionInteractor) {
        this.interactor = keyguardTransitionInteractor;
    }

    public final Flow<Float> flowForAnimation(AnimationParams animationParams) {
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.interactor;
        return keyguardTransitionInteractor.m3047transitionStepAnimationSxA4cEA(keyguardTransitionInteractor.getOccludedToLockscreenTransition(), animationParams, FromOccludedTransitionInteractor.Companion.m3034getTO_LOCKSCREEN_DURATIONUwyO8pc());
    }

    public final Flow<Float> getLockscreenAlpha() {
        return this.lockscreenAlpha;
    }

    public final Flow<Float> lockscreenTranslationY(final int i) {
        final Flow<Float> flowForAnimation = flowForAnimation(LOCKSCREEN_TRANSLATION_Y);
        return new Flow<Float>() { // from class: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$lockscreenTranslationY$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$lockscreenTranslationY$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/OccludedToLockscreenTransitionViewModel$lockscreenTranslationY$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ int $translatePx$inlined;

                @DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$lockscreenTranslationY$$inlined$map$1$2", f = "OccludedToLockscreenTransitionViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$lockscreenTranslationY$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/OccludedToLockscreenTransitionViewModel$lockscreenTranslationY$$inlined$map$1$2$1.class */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    public Object L$0;
                    public int label;
                    public /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$translatePx$inlined = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        AnonymousClass1 anonymousClass12 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass12.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass12.label = i2 - 2147483648;
                            anonymousClass1 = anonymousClass12;
                            Object obj2 = anonymousClass1.result;
                            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                FlowCollector flowCollector = this.$this_unsafeFlow;
                                Float boxFloat = Boxing.boxFloat((-this.$translatePx$inlined) + (Interpolators.EMPHASIZED_DECELERATE.getInterpolation(((Number) obj).floatValue()) * this.$translatePx$inlined));
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(boxFloat, anonymousClass1) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                            } else if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            } else {
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = flowForAnimation.collect(new AnonymousClass2(flowCollector, i), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
    }
}