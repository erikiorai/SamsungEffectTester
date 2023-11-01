package com.android.systemui.keyguard.data.repository;

import android.os.Build;
import com.android.keyguard.ViewMediatorCallback;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.keyguard.shared.model.BouncerShowMessageModel;
import com.android.systemui.keyguard.shared.model.KeyguardBouncerModel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository.class */
public final class KeyguardBouncerRepository {
    public final MutableStateFlow<Boolean> _isBackButtonEnabled;
    public final MutableStateFlow<Boolean> _keyguardAuthenticated;
    public final MutableStateFlow<Float> _keyguardPosition;
    public final MutableStateFlow<Boolean> _onScreenTurnedOff;
    public final MutableStateFlow<Float> _panelExpansionAmount;
    public final MutableStateFlow<Runnable> _primaryBouncerDisappearAnimation;
    public final MutableStateFlow<Boolean> _primaryBouncerHide;
    public final MutableStateFlow<Boolean> _primaryBouncerScrimmed;
    public final MutableStateFlow<KeyguardBouncerModel> _primaryBouncerShow;
    public final MutableStateFlow<Boolean> _primaryBouncerShowingSoon;
    public final MutableStateFlow<Boolean> _primaryBouncerStartingToHide;
    public final MutableStateFlow<Boolean> _primaryBouncerVisible;
    public final MutableStateFlow<Boolean> _resourceUpdateRequests;
    public final MutableStateFlow<BouncerShowMessageModel> _showMessage;
    public final CoroutineScope applicationScope;
    public final TableLogBuffer buffer;
    public final StateFlow<Boolean> isBackButtonEnabled;
    public final StateFlow<Boolean> keyguardAuthenticated;
    public final StateFlow<Float> keyguardPosition;
    public final StateFlow<Boolean> onScreenTurnedOff;
    public final StateFlow<Float> panelExpansionAmount;
    public final StateFlow<Boolean> primaryBouncerHide;
    public final StateFlow<Boolean> primaryBouncerScrimmed;
    public final StateFlow<KeyguardBouncerModel> primaryBouncerShow;
    public final StateFlow<Boolean> primaryBouncerShowingSoon;
    public final StateFlow<Runnable> primaryBouncerStartingDisappearAnimation;
    public final StateFlow<Boolean> primaryBouncerStartingToHide;
    public final StateFlow<Boolean> primaryBouncerVisible;
    public final StateFlow<Boolean> resourceUpdateRequests;
    public final StateFlow<BouncerShowMessageModel> showMessage;
    public final ViewMediatorCallback viewMediatorCallback;

    public KeyguardBouncerRepository(ViewMediatorCallback viewMediatorCallback, CoroutineScope coroutineScope, TableLogBuffer tableLogBuffer) {
        this.viewMediatorCallback = viewMediatorCallback;
        this.applicationScope = coroutineScope;
        this.buffer = tableLogBuffer;
        Boolean bool = Boolean.FALSE;
        MutableStateFlow<Boolean> MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerVisible = MutableStateFlow;
        this.primaryBouncerVisible = FlowKt.asStateFlow(MutableStateFlow);
        MutableStateFlow<KeyguardBouncerModel> MutableStateFlow2 = StateFlowKt.MutableStateFlow((Object) null);
        this._primaryBouncerShow = MutableStateFlow2;
        this.primaryBouncerShow = FlowKt.asStateFlow(MutableStateFlow2);
        MutableStateFlow<Boolean> MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerShowingSoon = MutableStateFlow3;
        this.primaryBouncerShowingSoon = FlowKt.asStateFlow(MutableStateFlow3);
        MutableStateFlow<Boolean> MutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerHide = MutableStateFlow4;
        this.primaryBouncerHide = FlowKt.asStateFlow(MutableStateFlow4);
        MutableStateFlow<Boolean> MutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerStartingToHide = MutableStateFlow5;
        this.primaryBouncerStartingToHide = FlowKt.asStateFlow(MutableStateFlow5);
        MutableStateFlow<Runnable> MutableStateFlow6 = StateFlowKt.MutableStateFlow((Object) null);
        this._primaryBouncerDisappearAnimation = MutableStateFlow6;
        this.primaryBouncerStartingDisappearAnimation = FlowKt.asStateFlow(MutableStateFlow6);
        MutableStateFlow<Boolean> MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerScrimmed = MutableStateFlow7;
        this.primaryBouncerScrimmed = FlowKt.asStateFlow(MutableStateFlow7);
        MutableStateFlow<Float> MutableStateFlow8 = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
        this._panelExpansionAmount = MutableStateFlow8;
        this.panelExpansionAmount = FlowKt.asStateFlow(MutableStateFlow8);
        MutableStateFlow<Float> MutableStateFlow9 = StateFlowKt.MutableStateFlow(Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW));
        this._keyguardPosition = MutableStateFlow9;
        this.keyguardPosition = FlowKt.asStateFlow(MutableStateFlow9);
        MutableStateFlow<Boolean> MutableStateFlow10 = StateFlowKt.MutableStateFlow(bool);
        this._onScreenTurnedOff = MutableStateFlow10;
        this.onScreenTurnedOff = FlowKt.asStateFlow(MutableStateFlow10);
        MutableStateFlow<Boolean> MutableStateFlow11 = StateFlowKt.MutableStateFlow((Object) null);
        this._isBackButtonEnabled = MutableStateFlow11;
        this.isBackButtonEnabled = FlowKt.asStateFlow(MutableStateFlow11);
        MutableStateFlow<Boolean> MutableStateFlow12 = StateFlowKt.MutableStateFlow((Object) null);
        this._keyguardAuthenticated = MutableStateFlow12;
        this.keyguardAuthenticated = FlowKt.asStateFlow(MutableStateFlow12);
        MutableStateFlow<BouncerShowMessageModel> MutableStateFlow13 = StateFlowKt.MutableStateFlow((Object) null);
        this._showMessage = MutableStateFlow13;
        this.showMessage = FlowKt.asStateFlow(MutableStateFlow13);
        MutableStateFlow<Boolean> MutableStateFlow14 = StateFlowKt.MutableStateFlow(bool);
        this._resourceUpdateRequests = MutableStateFlow14;
        this.resourceUpdateRequests = FlowKt.asStateFlow(MutableStateFlow14);
        setUpLogging();
    }

    public final CharSequence getBouncerErrorMessage() {
        return this.viewMediatorCallback.consumeCustomMessage();
    }

    public final int getBouncerPromptReason() {
        return this.viewMediatorCallback.getBouncerPromptReason();
    }

    public final StateFlow<Boolean> getKeyguardAuthenticated() {
        return this.keyguardAuthenticated;
    }

    public final StateFlow<Float> getKeyguardPosition() {
        return this.keyguardPosition;
    }

    public final StateFlow<Boolean> getOnScreenTurnedOff() {
        return this.onScreenTurnedOff;
    }

    public final StateFlow<Float> getPanelExpansionAmount() {
        return this.panelExpansionAmount;
    }

    public final StateFlow<Boolean> getPrimaryBouncerHide() {
        return this.primaryBouncerHide;
    }

    public final StateFlow<Boolean> getPrimaryBouncerScrimmed() {
        return this.primaryBouncerScrimmed;
    }

    public final StateFlow<KeyguardBouncerModel> getPrimaryBouncerShow() {
        return this.primaryBouncerShow;
    }

    public final StateFlow<Boolean> getPrimaryBouncerShowingSoon() {
        return this.primaryBouncerShowingSoon;
    }

    public final StateFlow<Runnable> getPrimaryBouncerStartingDisappearAnimation() {
        return this.primaryBouncerStartingDisappearAnimation;
    }

    public final StateFlow<Boolean> getPrimaryBouncerStartingToHide() {
        return this.primaryBouncerStartingToHide;
    }

    public final StateFlow<Boolean> getPrimaryBouncerVisible() {
        return this.primaryBouncerVisible;
    }

    public final StateFlow<Boolean> getResourceUpdateRequests() {
        return this.resourceUpdateRequests;
    }

    public final StateFlow<BouncerShowMessageModel> getShowMessage() {
        return this.showMessage;
    }

    public final StateFlow<Boolean> isBackButtonEnabled() {
        return this.isBackButtonEnabled;
    }

    public final void setIsBackButtonEnabled(boolean z) {
        this._isBackButtonEnabled.setValue(Boolean.valueOf(z));
    }

    public final void setKeyguardAuthenticated(Boolean bool) {
        this._keyguardAuthenticated.setValue(bool);
    }

    public final void setKeyguardPosition(float f) {
        this._keyguardPosition.setValue(Float.valueOf(f));
    }

    public final void setOnScreenTurnedOff(boolean z) {
        this._onScreenTurnedOff.setValue(Boolean.valueOf(z));
    }

    public final void setPanelExpansion(float f) {
        this._panelExpansionAmount.setValue(Float.valueOf(f));
    }

    public final void setPrimaryHide(boolean z) {
        this._primaryBouncerHide.setValue(Boolean.valueOf(z));
    }

    public final void setPrimaryScrimmed(boolean z) {
        this._primaryBouncerScrimmed.setValue(Boolean.valueOf(z));
    }

    public final void setPrimaryShow(KeyguardBouncerModel keyguardBouncerModel) {
        this._primaryBouncerShow.setValue(keyguardBouncerModel);
    }

    public final void setPrimaryShowingSoon(boolean z) {
        this._primaryBouncerShowingSoon.setValue(Boolean.valueOf(z));
    }

    public final void setPrimaryStartDisappearAnimation(Runnable runnable) {
        this._primaryBouncerDisappearAnimation.setValue(runnable);
    }

    public final void setPrimaryStartingToHide(boolean z) {
        this._primaryBouncerStartingToHide.setValue(Boolean.valueOf(z));
    }

    public final void setPrimaryVisible(boolean z) {
        this._primaryBouncerVisible.setValue(Boolean.valueOf(z));
    }

    public final void setResourceUpdateRequests(boolean z) {
        this._resourceUpdateRequests.setValue(Boolean.valueOf(z));
    }

    public final void setShowMessage(BouncerShowMessageModel bouncerShowMessageModel) {
        this._showMessage.setValue(bouncerShowMessageModel);
    }

    public final void setUpLogging() {
        if (Build.IS_DEBUGGABLE) {
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow<Boolean>) this.primaryBouncerVisible, this.buffer, "", "PrimaryBouncerVisible", false), this.applicationScope);
            final Flow flow = this.primaryBouncerShow;
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow<Boolean>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$1$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$1$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$1$2", f = "KeyguardBouncerRepository.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$1$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$1$2$1.class */
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
                                    Boolean boxBoolean = Boxing.boxBoolean(((KeyguardBouncerModel) obj) != null);
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
                    Object collect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            }, this.buffer, "", "PrimaryBouncerShow", false), this.applicationScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow<Boolean>) this.primaryBouncerShowingSoon, this.buffer, "", "PrimaryBouncerShowingSoon", false), this.applicationScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow<Boolean>) this.primaryBouncerHide, this.buffer, "", "PrimaryBouncerHide", false), this.applicationScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow<Boolean>) this.primaryBouncerStartingToHide, this.buffer, "", "PrimaryBouncerStartingToHide", false), this.applicationScope);
            final Flow flow2 = this.primaryBouncerStartingDisappearAnimation;
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow<Boolean>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$2

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$2$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$2$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$2$2", f = "KeyguardBouncerRepository.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$2$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$2$2$1.class */
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
                                    Boolean boxBoolean = Boxing.boxBoolean(((Runnable) obj) != null);
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
                    Object collect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            }, this.buffer, "", "PrimaryBouncerStartingDisappearAnimation", false), this.applicationScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow<Boolean>) this.primaryBouncerScrimmed, this.buffer, "", "PrimaryBouncerScrimmed", false), this.applicationScope);
            final Flow flow3 = this.panelExpansionAmount;
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow<Integer>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$3

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$3$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$3$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$3$2", f = "KeyguardBouncerRepository.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$3$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$3$2$1.class */
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
                                    Integer boxInt = Boxing.boxInt((int) (((Number) obj).floatValue() * 1000));
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
                    Object collect = flow3.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            }, this.buffer, "", "PanelExpansionAmountMillis", -1), this.applicationScope);
            final Flow flow4 = this.keyguardPosition;
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow<Integer>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$4

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$4$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$4$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$4$2", f = "KeyguardBouncerRepository.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$4$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$4$2$1.class */
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
                                    Integer boxInt = Boxing.boxInt((int) ((Number) obj).floatValue());
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
                    Object collect = flow4.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            }, this.buffer, "", "KeyguardPosition", -1), this.applicationScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow<Boolean>) this.onScreenTurnedOff, this.buffer, "", "OnScreenTurnedOff", false), this.applicationScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow<Boolean>) FlowKt.filterNotNull(this.isBackButtonEnabled), this.buffer, "", "IsBackButtonEnabled", false), this.applicationScope);
            final Flow flow5 = this.showMessage;
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow<String>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$5

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$5$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$5$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$5$2", f = "KeyguardBouncerRepository.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository$setUpLogging$$inlined$map$5$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardBouncerRepository$setUpLogging$$inlined$map$5$2$1.class */
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
                                    BouncerShowMessageModel bouncerShowMessageModel = (BouncerShowMessageModel) obj;
                                    String message = bouncerShowMessageModel != null ? bouncerShowMessageModel.getMessage() : null;
                                    anonymousClass1.label = 1;
                                    if (flowCollector.emit(message, anonymousClass1) == coroutine_suspended) {
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
                    Object collect = flow5.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            }, this.buffer, "", "ShowMessage", (String) null), this.applicationScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow<Boolean>) this.resourceUpdateRequests, this.buffer, "", "ResourceUpdateRequests", false), this.applicationScope);
        }
    }
}