package com.android.systemui.keyguard.domain.interactor;

import android.content.res.ColorStateList;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Handler;
import android.os.Trace;
import android.os.UserManager;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.DejankUtils;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.data.BouncerViewDelegate;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository;
import com.android.systemui.keyguard.shared.model.BouncerShowMessageModel;
import com.android.systemui.keyguard.shared.model.KeyguardBouncerModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor.class */
public final class PrimaryBouncerInteractor {
    public final Flow<Float> bouncerExpansion;
    public final DismissCallbackRegistry dismissCallbackRegistry;
    public final FalsingCollector falsingCollector;
    public final Flow<Unit> hide;
    public final Flow<Boolean> isBackButtonEnabled;
    public final Flow<Boolean> isVisible;
    public final Flow<Boolean> keyguardAuthenticated;
    public final Flow<Float> keyguardPosition;
    public final KeyguardSecurityModel keyguardSecurityModel;
    public final KeyguardStateController keyguardStateController;
    public final Handler mainHandler;
    public final Flow<Float> panelExpansionAmount;
    public final PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor;
    public final boolean primaryBouncerFaceDelay;
    public final BouncerView primaryBouncerView;
    public final KeyguardBouncerRepository repository;
    public final Flow<Boolean> resourceUpdateRequests;
    public final Flow<Unit> screenTurnedOff;
    public final Flow<KeyguardBouncerModel> show;
    public final Flow<BouncerShowMessageModel> showMessage;
    public final Runnable showRunnable;
    public final Flow<Runnable> startingDisappearAnimation;
    public final Flow<Unit> startingToHide;

    public PrimaryBouncerInteractor(KeyguardBouncerRepository keyguardBouncerRepository, BouncerView bouncerView, Handler handler, KeyguardStateController keyguardStateController, KeyguardSecurityModel keyguardSecurityModel, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, FalsingCollector falsingCollector, DismissCallbackRegistry dismissCallbackRegistry, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.repository = keyguardBouncerRepository;
        this.primaryBouncerView = bouncerView;
        this.mainHandler = handler;
        this.keyguardStateController = keyguardStateController;
        this.keyguardSecurityModel = keyguardSecurityModel;
        this.primaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
        this.falsingCollector = falsingCollector;
        this.dismissCallbackRegistry = dismissCallbackRegistry;
        this.primaryBouncerFaceDelay = (!keyguardStateController.isFaceAuthEnabled() || keyguardUpdateMonitor.getCachedIsUnlockWithFingerprintPossible(KeyguardUpdateMonitor.getCurrentUser()) || needsFullscreenBouncer() || !keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(BiometricSourceType.FACE) || keyguardBypassController.getBypassEnabled()) ? false : true;
        this.showRunnable = new Runnable() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$showRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                PrimaryBouncerInteractor.access$getRepository$p(PrimaryBouncerInteractor.this).setPrimaryVisible(true);
                PrimaryBouncerInteractor.access$getRepository$p(PrimaryBouncerInteractor.this).setPrimaryShow(new KeyguardBouncerModel(PrimaryBouncerInteractor.access$getRepository$p(PrimaryBouncerInteractor.this).getBouncerPromptReason(), PrimaryBouncerInteractor.access$getRepository$p(PrimaryBouncerInteractor.this).getBouncerErrorMessage(), ((Number) PrimaryBouncerInteractor.access$getRepository$p(PrimaryBouncerInteractor.this).getPanelExpansionAmount().getValue()).floatValue()));
                PrimaryBouncerInteractor.access$getRepository$p(PrimaryBouncerInteractor.this).setPrimaryShowingSoon(false);
                PrimaryBouncerInteractor.access$getPrimaryBouncerCallbackInteractor$p(PrimaryBouncerInteractor.this).dispatchVisibilityChanged(0);
            }
        };
        this.keyguardAuthenticated = FlowKt.filterNotNull(keyguardBouncerRepository.getKeyguardAuthenticated());
        final Flow onScreenTurnedOff = keyguardBouncerRepository.getOnScreenTurnedOff();
        final Flow<Boolean> flow = new Flow<Boolean>() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$filter$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2", f = "PrimaryBouncerInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$filter$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                                if (((Boolean) obj).booleanValue()) {
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
                Object collect = onScreenTurnedOff.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.screenTurnedOff = new Flow<Unit>() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1$2", f = "PrimaryBouncerInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                                ((Boolean) obj).booleanValue();
                                Unit unit = Unit.INSTANCE;
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(unit, anonymousClass1) == coroutine_suspended) {
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
                Object collect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.show = FlowKt.filterNotNull(keyguardBouncerRepository.getPrimaryBouncerShow());
        final Flow primaryBouncerHide = keyguardBouncerRepository.getPrimaryBouncerHide();
        final Flow<Boolean> flow2 = new Flow<Boolean>() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$filter$2$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2$2", f = "PrimaryBouncerInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$filter$2$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                                if (((Boolean) obj).booleanValue()) {
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
                Object collect = primaryBouncerHide.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.hide = new Flow<Unit>() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$map$2$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2$2", f = "PrimaryBouncerInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$map$2$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                                ((Boolean) obj).booleanValue();
                                Unit unit = Unit.INSTANCE;
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(unit, anonymousClass1) == coroutine_suspended) {
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
                Object collect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        final Flow primaryBouncerStartingToHide = keyguardBouncerRepository.getPrimaryBouncerStartingToHide();
        final Flow<Boolean> flow3 = new Flow<Boolean>() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$3

            /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$3$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$filter$3$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$3$2", f = "PrimaryBouncerInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$3$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$filter$3$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                                if (((Boolean) obj).booleanValue()) {
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
                Object collect = primaryBouncerStartingToHide.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.startingToHide = new Flow<Unit>() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$3$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$map$3$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$3$2", f = "PrimaryBouncerInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$3$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$map$3$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                                ((Boolean) obj).booleanValue();
                                Unit unit = Unit.INSTANCE;
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(unit, anonymousClass1) == coroutine_suspended) {
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
                Object collect = flow3.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.isVisible = keyguardBouncerRepository.getPrimaryBouncerVisible();
        this.isBackButtonEnabled = FlowKt.filterNotNull(keyguardBouncerRepository.isBackButtonEnabled());
        this.showMessage = FlowKt.filterNotNull(keyguardBouncerRepository.getShowMessage());
        this.startingDisappearAnimation = FlowKt.filterNotNull(keyguardBouncerRepository.getPrimaryBouncerStartingDisappearAnimation());
        final Flow resourceUpdateRequests = keyguardBouncerRepository.getResourceUpdateRequests();
        this.resourceUpdateRequests = new Flow<Boolean>() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$4

            /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$4$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$filter$4$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$4$2", f = "PrimaryBouncerInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$4$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$special$$inlined$filter$4$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                                if (((Boolean) obj).booleanValue()) {
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
                Object collect = resourceUpdateRequests.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.keyguardPosition = keyguardBouncerRepository.getKeyguardPosition();
        this.panelExpansionAmount = keyguardBouncerRepository.getPanelExpansionAmount();
        this.bouncerExpansion = FlowKt.combine(keyguardBouncerRepository.getPanelExpansionAmount(), keyguardBouncerRepository.getPrimaryBouncerVisible(), new PrimaryBouncerInteractor$bouncerExpansion$1(null));
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$setPanelExpansion$1.run():void, com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$showRunnable$1.run():void] */
    public static final /* synthetic */ PrimaryBouncerCallbackInteractor access$getPrimaryBouncerCallbackInteractor$p(PrimaryBouncerInteractor primaryBouncerInteractor) {
        return primaryBouncerInteractor.primaryBouncerCallbackInteractor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$showRunnable$1.run():void, com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$startDisappearAnimation$finishRunnable$1.run():void] */
    public static final /* synthetic */ KeyguardBouncerRepository access$getRepository$p(PrimaryBouncerInteractor primaryBouncerInteractor) {
        return primaryBouncerInteractor.repository;
    }

    public final void cancelShowRunnable() {
        DejankUtils.removeCallbacks(this.showRunnable);
        this.mainHandler.removeCallbacks(this.showRunnable);
    }

    public final Flow<Float> getBouncerExpansion() {
        return this.bouncerExpansion;
    }

    public final Flow<Unit> getHide() {
        return this.hide;
    }

    public final Flow<Boolean> getKeyguardAuthenticated() {
        return this.keyguardAuthenticated;
    }

    public final Flow<Float> getKeyguardPosition() {
        return this.keyguardPosition;
    }

    public final Flow<Float> getPanelExpansionAmount() {
        return this.panelExpansionAmount;
    }

    public final Flow<Boolean> getResourceUpdateRequests() {
        return this.resourceUpdateRequests;
    }

    public final Flow<Unit> getScreenTurnedOff() {
        return this.screenTurnedOff;
    }

    public final Flow<KeyguardBouncerModel> getShow() {
        return this.show;
    }

    public final Flow<BouncerShowMessageModel> getShowMessage() {
        return this.showMessage;
    }

    public final Flow<Runnable> getStartingDisappearAnimation() {
        return this.startingDisappearAnimation;
    }

    public final Flow<Unit> getStartingToHide() {
        return this.startingToHide;
    }

    public final void hide() {
        Trace.beginSection("KeyguardBouncer#hide");
        if (isFullyShowing()) {
            SysUiStatsLog.write(63, 1);
            this.dismissCallbackRegistry.notifyDismissCancelled();
        }
        this.falsingCollector.onBouncerHidden();
        this.keyguardStateController.notifyBouncerShowing(false);
        cancelShowRunnable();
        this.repository.setPrimaryShowingSoon(false);
        this.repository.setPrimaryVisible(false);
        this.repository.setPrimaryHide(true);
        this.repository.setPrimaryShow(null);
        this.primaryBouncerCallbackInteractor.dispatchVisibilityChanged(4);
        Trace.endSection();
    }

    public final boolean isAnimatingAway() {
        return this.repository.getPrimaryBouncerStartingDisappearAnimation().getValue() != null;
    }

    public final Flow<Boolean> isBackButtonEnabled() {
        return this.isBackButtonEnabled;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x005c, code lost:
        if (r3.repository.getPrimaryBouncerStartingDisappearAnimation().getValue() == null) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isFullyShowing() {
        boolean z = true;
        if (((Boolean) this.repository.getPrimaryBouncerShowingSoon().getValue()).booleanValue() || ((Boolean) this.repository.getPrimaryBouncerVisible().getValue()).booleanValue()) {
            if (((Number) this.repository.getPanelExpansionAmount().getValue()).floatValue() == ActionBarShadowController.ELEVATION_LOW) {
            }
        }
        z = false;
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x005e, code lost:
        if ((((java.lang.Number) r3.repository.getPanelExpansionAmount().getValue()).floatValue() == com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) == false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isInTransit() {
        boolean z;
        if (!((Boolean) this.repository.getPrimaryBouncerShowingSoon().getValue()).booleanValue()) {
            z = false;
            if (!(((Number) this.repository.getPanelExpansionAmount().getValue()).floatValue() == 1.0f)) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    public final boolean isScrimmed() {
        return ((Boolean) this.repository.getPrimaryBouncerScrimmed().getValue()).booleanValue();
    }

    public final Flow<Boolean> isVisible() {
        return this.isVisible;
    }

    public final boolean needsFullscreenBouncer() {
        KeyguardSecurityModel.SecurityMode securityMode = this.keyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
        return securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
    }

    public final void notifyKeyguardAuthenticated(boolean z) {
        this.repository.setKeyguardAuthenticated(Boolean.valueOf(z));
    }

    public final void notifyKeyguardAuthenticatedHandled() {
        this.repository.setKeyguardAuthenticated(null);
    }

    public final void notifyUpdatedResources() {
        this.repository.setResourceUpdateRequests(false);
    }

    public final void onMessageShown() {
        this.repository.setShowMessage(null);
    }

    public final void onScreenTurnedOff() {
        this.repository.setOnScreenTurnedOff(true);
    }

    public final void setBackButtonEnabled(boolean z) {
        this.repository.setIsBackButtonEnabled(z);
    }

    public final void setDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
        BouncerViewDelegate delegate = this.primaryBouncerView.getDelegate();
        if (delegate != null) {
            delegate.setDismissAction(onDismissAction, runnable);
        }
    }

    public final void setKeyguardPosition(float f) {
        this.repository.setKeyguardPosition(f);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setPanelExpansion(float f) {
        float floatValue = ((Number) this.repository.getPanelExpansionAmount().getValue()).floatValue();
        boolean z = floatValue == f;
        if (this.repository.getPrimaryBouncerStartingDisappearAnimation().getValue() == null) {
            this.repository.setPanelExpansion(f);
        }
        int i = (f > ActionBarShadowController.ELEVATION_LOW ? 1 : (f == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
        if (i == 0) {
            if (!(floatValue == ActionBarShadowController.ELEVATION_LOW)) {
                this.falsingCollector.onBouncerShown();
                this.primaryBouncerCallbackInteractor.dispatchFullyShown();
                if (!z) {
                    this.primaryBouncerCallbackInteractor.dispatchExpansionChanged(f);
                    return;
                }
                return;
            }
        }
        if (f == 1.0f) {
            if (!(floatValue == 1.0f)) {
                hide();
                DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$setPanelExpansion$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrimaryBouncerInteractor.access$getPrimaryBouncerCallbackInteractor$p(PrimaryBouncerInteractor.this).dispatchReset();
                    }
                });
                this.primaryBouncerCallbackInteractor.dispatchFullyHidden();
                if (!z) {
                }
            }
        }
        if (!(i == 0)) {
            boolean z2 = false;
            if (floatValue == ActionBarShadowController.ELEVATION_LOW) {
                z2 = true;
            }
            if (z2) {
                this.primaryBouncerCallbackInteractor.dispatchStartingToHide();
                this.repository.setPrimaryStartingToHide(true);
            }
        }
        if (!z) {
        }
    }

    public final void show(boolean z) {
        this.repository.setOnScreenTurnedOff(false);
        this.repository.setKeyguardAuthenticated(null);
        this.repository.setPrimaryHide(false);
        this.repository.setPrimaryStartingToHide(false);
        boolean z2 = (((Boolean) this.repository.getPrimaryBouncerVisible().getValue()).booleanValue() || ((Boolean) this.repository.getPrimaryBouncerShowingSoon().getValue()).booleanValue()) && needsFullscreenBouncer();
        if (z2 || this.repository.getPrimaryBouncerShow().getValue() == null) {
            if (KeyguardUpdateMonitor.getCurrentUser() == 0 && UserManager.isSplitSystemUser()) {
                return;
            }
            Trace.beginSection("KeyguardBouncer#show");
            this.repository.setPrimaryScrimmed(z);
            if (z) {
                setPanelExpansion(ActionBarShadowController.ELEVATION_LOW);
            }
            if (z2) {
                BouncerViewDelegate delegate = this.primaryBouncerView.getDelegate();
                if (delegate != null) {
                    delegate.resume();
                    return;
                }
                return;
            }
            BouncerViewDelegate delegate2 = this.primaryBouncerView.getDelegate();
            boolean z3 = false;
            if (delegate2 != null) {
                z3 = false;
                if (delegate2.showNextSecurityScreenOrFinish()) {
                    z3 = true;
                }
            }
            if (z3) {
                return;
            }
            this.repository.setPrimaryShowingSoon(true);
            if (this.primaryBouncerFaceDelay) {
                this.mainHandler.postDelayed(this.showRunnable, 1200L);
            } else {
                DejankUtils.postAfterTraversal(this.showRunnable);
            }
            this.keyguardStateController.notifyBouncerShowing(true);
            this.primaryBouncerCallbackInteractor.dispatchStartingToShow();
            Trace.endSection();
        }
    }

    public final void showMessage(String str, ColorStateList colorStateList) {
        this.repository.setShowMessage(new BouncerShowMessageModel(str, colorStateList));
    }

    public final void startDisappearAnimation(final Runnable runnable) {
        this.repository.setPrimaryStartDisappearAnimation(new Runnable() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$startDisappearAnimation$finishRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                runnable.run();
                PrimaryBouncerInteractor.access$getRepository$p(this).setPrimaryStartDisappearAnimation(null);
            }
        });
    }

    public final void updateResources() {
        this.repository.setResourceUpdateRequests(true);
    }

    public final boolean willDismissWithAction() {
        BouncerViewDelegate delegate = this.primaryBouncerView.getDelegate();
        boolean z = true;
        if (delegate == null || !delegate.willDismissWithActions()) {
            z = false;
        }
        return z;
    }
}