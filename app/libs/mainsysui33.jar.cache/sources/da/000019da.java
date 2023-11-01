package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.R$dimen;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import com.android.systemui.keyguard.shared.model.WakeSleepReason;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.LiftReveal;
import com.android.systemui.statusbar.LightRevealEffect;
import com.android.systemui.statusbar.PowerButtonReveal;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl.class */
public final class LightRevealScrimRepositoryImpl implements LightRevealScrimRepository {
    public final Flow<LightRevealEffect> biometricRevealEffect;
    public final Context context;
    public final Flow<LightRevealEffect> faceRevealEffect;
    public final Flow<LightRevealEffect> fingerprintRevealEffect;
    public final Flow<LightRevealEffect> nonBiometricRevealEffect;
    public final PowerButtonReveal powerButtonReveal;
    public final Flow<LightRevealEffect> revealEffect;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BiometricUnlockSource.values().length];
            try {
                iArr[BiometricUnlockSource.FINGERPRINT_SENSOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[BiometricUnlockSource.FACE_SENSOR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public LightRevealScrimRepositoryImpl(KeyguardRepository keyguardRepository, Context context) {
        this.context = context;
        this.powerButtonReveal = new PowerButtonReveal(context.getResources().getDimensionPixelSize(R$dimen.physical_power_button_center_screen_location_y));
        final Flow<Point> fingerprintSensorLocation = keyguardRepository.getFingerprintSensorLocation();
        this.fingerprintRevealEffect = new Flow<LightRevealEffect>() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1$2", f = "LightRevealScrimRepository.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$special$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
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
                                Point point = (Point) obj;
                                LightRevealEffect access$constructCircleRevealFromPoint = point != null ? LightRevealScrimRepositoryImpl.access$constructCircleRevealFromPoint(this.this$0, point) : null;
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(access$constructCircleRevealFromPoint, anonymousClass1) == coroutine_suspended) {
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
                Object collect = fingerprintSensorLocation.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        final Flow<Point> faceSensorLocation = keyguardRepository.getFaceSensorLocation();
        this.faceRevealEffect = new Flow<LightRevealEffect>() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$special$$inlined$map$2$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2$2", f = "LightRevealScrimRepository.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$special$$inlined$map$2$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
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
                                Point point = (Point) obj;
                                LightRevealEffect access$constructCircleRevealFromPoint = point != null ? LightRevealScrimRepositoryImpl.access$constructCircleRevealFromPoint(this.this$0, point) : null;
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(access$constructCircleRevealFromPoint, anonymousClass1) == coroutine_suspended) {
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
                Object collect = faceSensorLocation.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        Flow<LightRevealEffect> transformLatest = FlowKt.transformLatest(keyguardRepository.getBiometricUnlockSource(), new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1(null, this));
        this.biometricRevealEffect = transformLatest;
        final Flow<WakefulnessModel> wakefulness = keyguardRepository.getWakefulness();
        Flow<LightRevealEffect> flow = new Flow<LightRevealEffect>() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$special$$inlined$map$3$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3$2", f = "LightRevealScrimRepository.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$special$$inlined$map$3$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
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
                                WakefulnessModel wakefulnessModel = (WakefulnessModel) obj;
                                boolean z = wakefulnessModel.isWakingUpOrAwake() && wakefulnessModel.getLastWakeReason() == WakeSleepReason.POWER_BUTTON;
                                boolean z2 = false;
                                if (!wakefulnessModel.isWakingUpOrAwake()) {
                                    z2 = false;
                                    if (wakefulnessModel.getLastSleepReason() == WakeSleepReason.POWER_BUTTON) {
                                        z2 = true;
                                    }
                                }
                                PowerButtonReveal access$getPowerButtonReveal$p = (z || z2) ? LightRevealScrimRepositoryImpl.access$getPowerButtonReveal$p(this.this$0) : LiftReveal.INSTANCE;
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(access$getPowerButtonReveal$p, anonymousClass1) == coroutine_suspended) {
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
                Object collect = wakefulness.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.nonBiometricRevealEffect = flow;
        this.revealEffect = FlowKt.distinctUntilChanged(FlowKt.combine(keyguardRepository.getBiometricUnlockState(), transformLatest, flow, new LightRevealScrimRepositoryImpl$revealEffect$1(null)));
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ LightRevealEffect access$constructCircleRevealFromPoint(LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl, Point point) {
        return lightRevealScrimRepositoryImpl.constructCircleRevealFromPoint(point);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ PowerButtonReveal access$getPowerButtonReveal$p(LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
        return lightRevealScrimRepositoryImpl.powerButtonReveal;
    }

    public final LightRevealEffect constructCircleRevealFromPoint(Point point) {
        int i = point.x;
        return new CircleReveal(i, point.y, 0, Math.max(Math.max(i, this.context.getDisplay().getWidth() - point.x), Math.max(point.y, this.context.getDisplay().getHeight() - point.y)));
    }

    @Override // com.android.systemui.keyguard.data.repository.LightRevealScrimRepository
    public Flow<LightRevealEffect> getRevealEffect() {
        return this.revealEffect;
    }
}