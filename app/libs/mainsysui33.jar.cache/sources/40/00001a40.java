package com.android.systemui.keyguard.domain.interactor;

import android.graphics.Point;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardInteractor.class */
public final class KeyguardInteractor {
    public final Flow<BiometricUnlockModel> biometricUnlockState;
    public final Flow<Float> dozeAmount;
    public final Flow<DozeTransitionModel> dozeTransitionModel;
    public final Flow<Point> faceSensorLocation;
    public final Flow<Point> fingerprintSensorLocation;
    public final Flow<Boolean> isAbleToDream;
    public final Flow<Boolean> isBouncerShowing;
    public final Flow<Boolean> isDozing;
    public final Flow<Boolean> isDreaming;
    public final Flow<Boolean> isDreamingWithOverlay;
    public final Flow<Boolean> isKeyguardGoingAway;
    public final Flow<Boolean> isKeyguardOccluded;
    public final Flow<Boolean> isKeyguardShowing;
    public final KeyguardRepository repository;
    public final Flow<StatusBarState> statusBarState;
    public final Flow<WakefulnessModel> wakefulnessModel;

    public KeyguardInteractor(KeyguardRepository keyguardRepository) {
        this.repository = keyguardRepository;
        this.dozeAmount = keyguardRepository.getLinearDozeAmount();
        this.isDozing = keyguardRepository.isDozing();
        Flow<DozeTransitionModel> dozeTransitionModel = keyguardRepository.getDozeTransitionModel();
        this.dozeTransitionModel = dozeTransitionModel;
        Flow<Boolean> isDreaming = keyguardRepository.isDreaming();
        this.isDreaming = isDreaming;
        Flow<Boolean> isDreamingWithOverlay = keyguardRepository.isDreamingWithOverlay();
        this.isDreamingWithOverlay = isDreamingWithOverlay;
        this.isAbleToDream = FlowKt.distinctUntilChanged(com.android.systemui.util.kotlin.FlowKt.sample(FlowKt.merge(new Flow[]{isDreaming, isDreamingWithOverlay}), dozeTransitionModel, new KeyguardInteractor$isAbleToDream$1(null)));
        this.isKeyguardShowing = keyguardRepository.isKeyguardShowing();
        this.isKeyguardOccluded = keyguardRepository.isKeyguardOccluded();
        this.isKeyguardGoingAway = keyguardRepository.isKeyguardGoingAway();
        this.isBouncerShowing = keyguardRepository.isBouncerShowing();
        this.wakefulnessModel = keyguardRepository.getWakefulness();
        this.statusBarState = keyguardRepository.getStatusBarState();
        this.biometricUnlockState = keyguardRepository.getBiometricUnlockState();
        this.fingerprintSensorLocation = keyguardRepository.getFingerprintSensorLocation();
        this.faceSensorLocation = keyguardRepository.getFaceSensorLocation();
    }

    public final Flow<DozeTransitionModel> dozeTransitionTo(final DozeStateModel... dozeStateModelArr) {
        final Flow<DozeTransitionModel> flow = this.dozeTransitionModel;
        return new Flow<DozeTransitionModel>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$dozeTransitionTo$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$dozeTransitionTo$$inlined$filter$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardInteractor$dozeTransitionTo$$inlined$filter$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ DozeStateModel[] $states$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$dozeTransitionTo$$inlined$filter$1$2", f = "KeyguardInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$dozeTransitionTo$$inlined$filter$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardInteractor$dozeTransitionTo$$inlined$filter$1$2$1.class */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    public Object L$0;
                    public Object L$1;
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

                public AnonymousClass2(FlowCollector flowCollector, DozeStateModel[] dozeStateModelArr) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$states$inlined = dozeStateModelArr;
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
                                if (ArraysKt___ArraysKt.contains(this.$states$inlined, ((DozeTransitionModel) obj).getTo())) {
                                    anonymousClass1.label = 1;
                                    if (flowCollector.emit(obj, anonymousClass1) == coroutine_suspended) {
                                        return coroutine_suspended;
                                    }
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
                Object collect = flow.collect(new AnonymousClass2(flowCollector, dozeStateModelArr), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
    }

    public final Flow<BiometricUnlockModel> getBiometricUnlockState() {
        return this.biometricUnlockState;
    }

    public final Flow<Float> getDozeAmount() {
        return this.dozeAmount;
    }

    public final Flow<DozeTransitionModel> getDozeTransitionModel() {
        return this.dozeTransitionModel;
    }

    public final Flow<StatusBarState> getStatusBarState() {
        return this.statusBarState;
    }

    public final Flow<WakefulnessModel> getWakefulnessModel() {
        return this.wakefulnessModel;
    }

    public final Flow<Boolean> isAbleToDream() {
        return this.isAbleToDream;
    }

    public final Flow<Boolean> isBouncerShowing() {
        return this.isBouncerShowing;
    }

    public final Flow<Boolean> isDozing() {
        return this.isDozing;
    }

    public final Flow<Boolean> isDreaming() {
        return this.isDreaming;
    }

    public final Flow<Boolean> isDreamingWithOverlay() {
        return this.isDreamingWithOverlay;
    }

    public final Flow<Boolean> isKeyguardGoingAway() {
        return this.isKeyguardGoingAway;
    }

    public final Flow<Boolean> isKeyguardOccluded() {
        return this.isKeyguardOccluded;
    }

    public final Flow<Boolean> isKeyguardShowing() {
        return this.isKeyguardShowing;
    }

    /* renamed from: isKeyguardShowing  reason: collision with other method in class */
    public final boolean m3039isKeyguardShowing() {
        return this.repository.mo2969isKeyguardShowing();
    }
}