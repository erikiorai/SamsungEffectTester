package com.android.systemui.keyguard.ui.viewmodel;

import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.data.BouncerViewDelegate;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.shared.model.BouncerShowMessageModel;
import com.android.systemui.keyguard.shared.model.KeyguardBouncerModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBouncerViewModel.class */
public final class KeyguardBouncerViewModel {
    public final Flow<Float> bouncerExpansionAmount;
    public final Flow<BouncerShowMessageModel> bouncerShowMessage;
    public final Flow<Unit> hide;
    public final PrimaryBouncerInteractor interactor;
    public final Flow<Boolean> isBouncerVisible;
    public final Flow<Boolean> keyguardAuthenticated;
    public final Flow<Float> keyguardPosition;
    public final Flow<Unit> screenTurnedOff;
    public final Flow<KeyguardBouncerModel> show;
    public final Flow<KeyguardBouncerModel> showWithFullExpansion;
    public final Flow<Runnable> startDisappearAnimation;
    public final Flow<Unit> startingToHide;
    public final Flow<Boolean> updateResources;
    public final BouncerView view;

    public KeyguardBouncerViewModel(BouncerView bouncerView, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.view = bouncerView;
        this.interactor = primaryBouncerInteractor;
        this.bouncerExpansionAmount = primaryBouncerInteractor.getPanelExpansionAmount();
        this.isBouncerVisible = primaryBouncerInteractor.isVisible();
        this.show = primaryBouncerInteractor.getShow();
        final Flow<KeyguardBouncerModel> show = primaryBouncerInteractor.getShow();
        this.showWithFullExpansion = new Flow<KeyguardBouncerModel>() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$filter$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBouncerViewModel$special$$inlined$filter$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$filter$1$2", f = "KeyguardBouncerViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$filter$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBouncerViewModel$special$$inlined$filter$1$2$1.class */
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
                                if (((KeyguardBouncerModel) obj).getExpansionAmount() == ActionBarShadowController.ELEVATION_LOW) {
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
                Object collect = show.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.hide = primaryBouncerInteractor.getHide();
        this.startingToHide = primaryBouncerInteractor.getStartingToHide();
        this.startDisappearAnimation = primaryBouncerInteractor.getStartingDisappearAnimation();
        this.keyguardPosition = primaryBouncerInteractor.getKeyguardPosition();
        this.updateResources = primaryBouncerInteractor.getResourceUpdateRequests();
        this.bouncerShowMessage = primaryBouncerInteractor.getShowMessage();
        this.keyguardAuthenticated = primaryBouncerInteractor.getKeyguardAuthenticated();
        this.screenTurnedOff = primaryBouncerInteractor.getScreenTurnedOff();
    }

    public final Flow<Float> getBouncerExpansionAmount() {
        return this.bouncerExpansionAmount;
    }

    public final Flow<BouncerShowMessageModel> getBouncerShowMessage() {
        return this.bouncerShowMessage;
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

    public final Flow<Unit> getScreenTurnedOff() {
        return this.screenTurnedOff;
    }

    public final Flow<KeyguardBouncerModel> getShow() {
        return this.show;
    }

    public final Flow<KeyguardBouncerModel> getShowWithFullExpansion() {
        return this.showWithFullExpansion;
    }

    public final Flow<Runnable> getStartDisappearAnimation() {
        return this.startDisappearAnimation;
    }

    public final Flow<Unit> getStartingToHide() {
        return this.startingToHide;
    }

    public final Flow<Boolean> getUpdateResources() {
        return this.updateResources;
    }

    public final Flow<Boolean> isBouncerVisible() {
        return this.isBouncerVisible;
    }

    public final void notifyKeyguardAuthenticated() {
        this.interactor.notifyKeyguardAuthenticatedHandled();
    }

    public final void notifyUpdateResources() {
        this.interactor.notifyUpdatedResources();
    }

    public final Flow<Integer> observeOnIsBackButtonEnabled(final Function0<Integer> function0) {
        final Flow<Boolean> isBackButtonEnabled = this.interactor.isBackButtonEnabled();
        return new Flow<Integer>() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ Function0 $systemUiVisibility$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2", f = "KeyguardBouncerViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, Function0 function0) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$systemUiVisibility$inlined = function0;
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
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                int intValue = ((Number) this.$systemUiVisibility$inlined.invoke()).intValue();
                                Integer boxInt = Boxing.boxInt(booleanValue ? intValue & (-4194305) : intValue | 4194304);
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(boxInt, anonymousClass1) == coroutine_suspended) {
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
                Object collect = isBackButtonEnabled.collect(new AnonymousClass2(flowCollector, function0), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
    }

    public final void onMessageShown() {
        this.interactor.onMessageShown();
    }

    public final void setBouncerViewDelegate(BouncerViewDelegate bouncerViewDelegate) {
        this.view.setDelegate(bouncerViewDelegate);
    }
}