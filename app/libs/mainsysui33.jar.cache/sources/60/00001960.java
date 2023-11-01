package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1", f = "KeyguardQuickAffordanceRemoteUserSelectionManager.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1.class */
public final class KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super Map<String, ? extends List<? extends String>>>, CustomizationProviderClient, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    public KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1(Continuation continuation) {
        super(3, continuation);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (CustomizationProviderClient) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Map<String, ? extends List<? extends String>>> flowCollector, CustomizationProviderClient customizationProviderClient, Continuation<? super Unit> continuation) {
        KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1 keyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1 = new KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1(continuation);
        keyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        keyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1.L$1 = customizationProviderClient;
        return keyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        final Flow observeSelections;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            CustomizationProviderClient customizationProviderClient = (CustomizationProviderClient) this.L$1;
            Flow<Map<String, ? extends List<? extends String>>> emptyFlow = (customizationProviderClient == null || (observeSelections = customizationProviderClient.observeSelections()) == null) ? FlowKt.emptyFlow() : new Flow<Map<String, ? extends List<? extends String>>>() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$_selections$lambda$4$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$_selections$lambda$4$$inlined$map$1$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager$_selections$lambda$4$$inlined$map$1$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    @DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$_selections$lambda$4$$inlined$map$1$2", f = "KeyguardQuickAffordanceRemoteUserSelectionManager.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$_selections$lambda$4$$inlined$map$1$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager$_selections$lambda$4$$inlined$map$1$2$1.class */
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
                    /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
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
                                    Map createMapBuilder = MapsKt__MapsJVMKt.createMapBuilder();
                                    for (CustomizationProviderClient.Selection selection : (List) obj) {
                                        String slotId = selection.getSlotId();
                                        List list = (List) createMapBuilder.get(slotId);
                                        List list2 = list;
                                        if (list == null) {
                                            list2 = CollectionsKt__CollectionsKt.emptyList();
                                        }
                                        List mutableList = CollectionsKt___CollectionsKt.toMutableList(list2);
                                        mutableList.add(selection.getAffordanceId());
                                        createMapBuilder.put(slotId, mutableList);
                                    }
                                    Map build = MapsKt__MapsJVMKt.build(createMapBuilder);
                                    anonymousClass1.label = 1;
                                    if (flowCollector.emit(build, anonymousClass1) == coroutine_suspended) {
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

                public Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = observeSelections.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, emptyFlow, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}