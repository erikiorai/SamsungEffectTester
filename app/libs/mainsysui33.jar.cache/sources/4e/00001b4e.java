package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.common.shared.model.Position;
import com.android.systemui.doze.util.BurnInHelperWrapper;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel.class */
public final class KeyguardBottomAreaViewModel {
    public static final Companion Companion = new Companion(null);
    public final Flow<Float> alpha;
    public final Flow<Boolean> areQuickAffordancesFullyOpaque;
    public final KeyguardBottomAreaInteractor bottomAreaInteractor;
    public final BurnInHelperWrapper burnInHelperWrapper;
    public final Flow<KeyguardQuickAffordanceViewModel> endButton;
    public final Flow<Float> indicationAreaTranslationX;
    public final MutableStateFlow<Boolean> isInPreviewMode;
    public final Flow<Boolean> isIndicationAreaPadded;
    public final Flow<Boolean> isOverlayContainerVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardQuickAffordanceInteractor quickAffordanceInteractor;
    public final MutableStateFlow<String> selectedPreviewSlotId;
    public final Flow<KeyguardQuickAffordanceViewModel> startButton;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getAFFORDANCE_FULLY_OPAQUE_ALPHA_THRESHOLD$annotations() {
        }
    }

    public KeyguardBottomAreaViewModel(KeyguardInteractor keyguardInteractor, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, KeyguardBottomAreaInteractor keyguardBottomAreaInteractor, BurnInHelperWrapper burnInHelperWrapper) {
        this.keyguardInteractor = keyguardInteractor;
        this.quickAffordanceInteractor = keyguardQuickAffordanceInteractor;
        this.bottomAreaInteractor = keyguardBottomAreaInteractor;
        this.burnInHelperWrapper = burnInHelperWrapper;
        Flow MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.isInPreviewMode = MutableStateFlow;
        this.selectedPreviewSlotId = StateFlowKt.MutableStateFlow("bottom_start");
        final Flow<Float> alpha = keyguardBottomAreaInteractor.getAlpha();
        this.areQuickAffordancesFullyOpaque = FlowKt.distinctUntilChanged(new Flow<Boolean>() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$1$2", f = "KeyguardBottomAreaViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$special$$inlined$map$1$2$1.class */
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
                                Boolean boxBoolean = Boxing.boxBoolean(((Number) obj).floatValue() >= 0.95f);
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(boxBoolean, anonymousClass1) == coroutine_suspended) {
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
                Object collect = alpha.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        });
        Flow<KeyguardQuickAffordanceViewModel> button = button(KeyguardQuickAffordancePosition.BOTTOM_START);
        this.startButton = button;
        Flow<KeyguardQuickAffordanceViewModel> button2 = button(KeyguardQuickAffordancePosition.BOTTOM_END);
        this.endButton = button2;
        final Flow<Boolean> isDozing = keyguardInteractor.isDozing();
        this.isOverlayContainerVisible = FlowKt.distinctUntilChanged(new Flow<Boolean>() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$2$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$special$$inlined$map$2$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$2$2", f = "KeyguardBottomAreaViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$2$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$special$$inlined$map$2$2$1.class */
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
                                Boolean boxBoolean = Boxing.boxBoolean(!((Boolean) obj).booleanValue());
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(boxBoolean, anonymousClass1) == coroutine_suspended) {
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
                Object collect = isDozing.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        });
        this.alpha = FlowKt.transformLatest(MutableStateFlow, new KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1(null, this));
        this.isIndicationAreaPadded = FlowKt.distinctUntilChanged(FlowKt.combine(button, button2, new KeyguardBottomAreaViewModel$isIndicationAreaPadded$1(null)));
        final Flow<Position> clockPosition = keyguardBottomAreaInteractor.getClockPosition();
        this.indicationAreaTranslationX = FlowKt.distinctUntilChanged(new Flow<Float>() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$3$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$special$$inlined$map$3$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$3$2", f = "KeyguardBottomAreaViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$3$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$special$$inlined$map$3$2$1.class */
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
                                Float boxFloat = Boxing.boxFloat(((Position) obj).getX());
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
                Object collect = clockPosition.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ BurnInHelperWrapper access$getBurnInHelperWrapper$p(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel) {
        return keyguardBottomAreaViewModel.burnInHelperWrapper;
    }

    public final Flow<KeyguardQuickAffordanceViewModel> button(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        return FlowKt.transformLatest(this.isInPreviewMode, new KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1(null, this, keyguardQuickAffordancePosition));
    }

    public final void enablePreviewMode(String str) {
        this.isInPreviewMode.setValue(Boolean.TRUE);
        String str2 = str;
        if (str == null) {
            str2 = "bottom_start";
        }
        onPreviewSlotSelected(str2);
    }

    public final Flow<Float> getAlpha() {
        return this.alpha;
    }

    public final Flow<KeyguardQuickAffordanceViewModel> getEndButton() {
        return this.endButton;
    }

    public final Flow<Float> getIndicationAreaTranslationX() {
        return this.indicationAreaTranslationX;
    }

    public final Flow<KeyguardQuickAffordanceViewModel> getStartButton() {
        return this.startButton;
    }

    public final Flow<Float> indicationAreaTranslationY(final int i) {
        final Flow<Float> dozeAmount = this.keyguardInteractor.getDozeAmount();
        return FlowKt.distinctUntilChanged(new Flow<Float>() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ int $defaultBurnInOffset$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardBottomAreaViewModel this$0;

                @DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2", f = "KeyguardBottomAreaViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardBottomAreaViewModel;
                    this.$defaultBurnInOffset$inlined = i;
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
                                Float boxFloat = Boxing.boxFloat(((Number) obj).floatValue() * (KeyguardBottomAreaViewModel.access$getBurnInHelperWrapper$p(this.this$0).burnInOffset(this.$defaultBurnInOffset$inlined * 2, false) - this.$defaultBurnInOffset$inlined));
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
                Object collect = dozeAmount.collect(new AnonymousClass2(flowCollector, this, i), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        });
    }

    public final Flow<Boolean> isIndicationAreaPadded() {
        return this.isIndicationAreaPadded;
    }

    public final Flow<Boolean> isOverlayContainerVisible() {
        return this.isOverlayContainerVisible;
    }

    public final void onPreviewSlotSelected(String str) {
        this.selectedPreviewSlotId.setValue(str);
    }

    public final boolean shouldConstrainToTopOfLockIcon() {
        return this.bottomAreaInteractor.shouldConstrainToTopOfLockIcon();
    }

    public final KeyguardQuickAffordanceViewModel toViewModel(KeyguardQuickAffordanceModel keyguardQuickAffordanceModel, boolean z, boolean z2, boolean z3) {
        KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel;
        if (keyguardQuickAffordanceModel instanceof KeyguardQuickAffordanceModel.Visible) {
            KeyguardQuickAffordanceModel.Visible visible = (KeyguardQuickAffordanceModel.Visible) keyguardQuickAffordanceModel;
            keyguardQuickAffordanceViewModel = new KeyguardQuickAffordanceViewModel(visible.getConfigKey(), true, z, visible.getIcon(), new Function1<KeyguardQuickAffordanceViewModel.OnClickedParameters, Unit>() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$toViewModel$1
                {
                    super(1);
                }

                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((KeyguardQuickAffordanceViewModel.OnClickedParameters) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(KeyguardQuickAffordanceViewModel.OnClickedParameters onClickedParameters) {
                    KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor;
                    keyguardQuickAffordanceInteractor = KeyguardBottomAreaViewModel.this.quickAffordanceInteractor;
                    keyguardQuickAffordanceInteractor.onQuickAffordanceTriggered(onClickedParameters.getConfigKey(), onClickedParameters.getExpandable());
                }
            }, z2, visible.getActivationState() instanceof ActivationState.Active, z3, this.quickAffordanceInteractor.getUseLongPress());
        } else if (!(keyguardQuickAffordanceModel instanceof KeyguardQuickAffordanceModel.Hidden)) {
            throw new NoWhenBranchMatchedException();
        } else {
            keyguardQuickAffordanceViewModel = new KeyguardQuickAffordanceViewModel(null, false, false, null, null, false, false, false, false, 511, null);
        }
        return keyguardQuickAffordanceViewModel;
    }
}