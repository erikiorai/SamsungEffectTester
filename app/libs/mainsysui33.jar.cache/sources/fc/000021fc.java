package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1", f = "UserSwitcherRepository.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1.class */
public final class UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super UserSwitcherStatusModel>, Boolean, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl) {
        super(3, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (Boolean) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super UserSwitcherStatusModel> flowCollector, Boolean bool, Continuation<? super Unit> continuation) {
        UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1 userSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1 = new UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1(continuation, this.this$0);
        userSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        userSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = bool;
        return userSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Flow flowOf;
        Flow flow;
        Flow flow2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                flow = this.this$0.currentUserName;
                flow2 = this.this$0.currentUserInfo;
                flowOf = FlowKt.combine(flow, flow2, new UserSwitcherRepositoryImpl$userSwitcherStatus$1$1(null));
            } else {
                flowOf = FlowKt.flowOf(UserSwitcherStatusModel.Disabled.INSTANCE);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowOf, this) == coroutine_suspended) {
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