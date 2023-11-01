package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1", f = "KeyguardQuickAffordanceRepository.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1.class */
public final class KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super Map<String, ? extends List<KeyguardQuickAffordanceConfig>>>, KeyguardQuickAffordanceSelectionManager, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1(Continuation continuation, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
        super(3, continuation);
        this.this$0 = keyguardQuickAffordanceRepository;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (KeyguardQuickAffordanceSelectionManager) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Map<String, ? extends List<KeyguardQuickAffordanceConfig>>> flowCollector, KeyguardQuickAffordanceSelectionManager keyguardQuickAffordanceSelectionManager, Continuation<? super Unit> continuation) {
        KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1 keyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1 = new KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1(continuation, this.this$0);
        keyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        keyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1.L$1 = keyguardQuickAffordanceSelectionManager;
        return keyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final Flow<Map<String, List<String>>> mo2945getSelections = ((KeyguardQuickAffordanceSelectionManager) this.L$1).mo2945getSelections();
            final KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = this.this$0;
            Flow<Map<String, ? extends List<KeyguardQuickAffordanceConfig>>> flow = new Flow<Map<String, ? extends List<KeyguardQuickAffordanceConfig>>>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2", f = "KeyguardQuickAffordanceRepository.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository$selections$lambda$4$$inlined$map$1$2$1.class */
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

                    public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = keyguardQuickAffordanceRepository;
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
                                    Map map = (Map) obj;
                                    LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
                                    for (Map.Entry entry : map.entrySet()) {
                                        Object key = entry.getKey();
                                        List list = (List) entry.getValue();
                                        Set set = this.this$0.configs;
                                        ArrayList arrayList = new ArrayList();
                                        for (T t : set) {
                                            if (list.contains(((KeyguardQuickAffordanceConfig) t).getKey())) {
                                                arrayList.add(t);
                                            }
                                        }
                                        linkedHashMap.put(key, arrayList);
                                    }
                                    anonymousClass1.label = 1;
                                    if (flowCollector.emit(linkedHashMap, anonymousClass1) == coroutine_suspended) {
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
                    Object collect = mo2945getSelections.collect(new AnonymousClass2(flowCollector2, keyguardQuickAffordanceRepository), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutine_suspended) {
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