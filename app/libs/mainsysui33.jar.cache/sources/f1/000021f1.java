package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.FgsManagerController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2", f = "ForegroundServicesRepository.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2.class */
public final class ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3<FlowCollector<? super Boolean>, Boolean, Continuation<? super Unit>, Object> {
    public final /* synthetic */ FgsManagerController $fgsManagerController$inlined;
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ ForegroundServicesRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2(Continuation continuation, ForegroundServicesRepositoryImpl foregroundServicesRepositoryImpl, FgsManagerController fgsManagerController) {
        super(3, continuation);
        this.this$0 = foregroundServicesRepositoryImpl;
        this.$fgsManagerController$inlined = fgsManagerController;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (Boolean) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Boolean> flowCollector, Boolean bool, Continuation<? super Unit> continuation) {
        ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2 foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2 = new ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2(continuation, this.this$0, this.$fgsManagerController$inlined);
        foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2.L$0 = flowCollector;
        foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2.L$1 = bool;
        return foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Flow distinctUntilChanged;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                final Flow merge = FlowKt.merge(new Flow[]{this.this$0.getForegroundServicesCount(), ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1(this.$fgsManagerController$inlined, null))});
                final FgsManagerController fgsManagerController = this.$fgsManagerController$inlined;
                distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow<Boolean>() { // from class: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$2$$inlined$map$1

                    /* renamed from: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$2$$inlined$map$1$2  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepositoryImpl$hasNewChanges$lambda$2$$inlined$map$1$2.class */
                    public static final class AnonymousClass2<T> implements FlowCollector {
                        public final /* synthetic */ FgsManagerController $fgsManagerController$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        @DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$2$$inlined$map$1$2", f = "ForegroundServicesRepository.kt", l = {223}, m = "emit")
                        /* renamed from: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$lambda$2$$inlined$map$1$2$1  reason: invalid class name */
                        /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepositoryImpl$hasNewChanges$lambda$2$$inlined$map$1$2$1.class */
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

                        public AnonymousClass2(FlowCollector flowCollector, FgsManagerController fgsManagerController) {
                            this.$this_unsafeFlow = flowCollector;
                            this.$fgsManagerController$inlined = fgsManagerController;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:10:0x0040  */
                        /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            int i;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i2 = anonymousClass1.label;
                                if ((i2 & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i2 - 2147483648;
                                    Object obj2 = anonymousClass1.result;
                                    Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                    i = anonymousClass1.label;
                                    if (i != 0) {
                                        ResultKt.throwOnFailure(obj2);
                                        FlowCollector flowCollector = this.$this_unsafeFlow;
                                        Boolean boxBoolean = Boxing.boxBoolean(this.$fgsManagerController$inlined.getNewChangesSinceDialogWasDismissed());
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

                    public Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = merge.collect(new AnonymousClass2(flowCollector2, fgsManagerController), continuation);
                        return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                    }
                });
            } else {
                distinctUntilChanged = FlowKt.flowOf(Boxing.boxBoolean(false));
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, distinctUntilChanged, this) == coroutine_suspended) {
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