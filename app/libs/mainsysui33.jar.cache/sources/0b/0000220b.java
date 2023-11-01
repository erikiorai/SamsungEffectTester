package com.android.systemui.qs.footer.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1", f = "UserSwitcherRepository.kt", l = {85}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1.class */
public final class UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ProducerScope<Boolean> $$this$conflatedCallbackFlow;
    public int label;
    public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlinx.coroutines.channels.ProducerScope<? super java.lang.Boolean> */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1(ProducerScope<? super Boolean> producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1> continuation) {
        super(2, continuation);
        this.$$this$conflatedCallbackFlow = producerScope;
        this.this$0 = userSwitcherRepositoryImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1(this.$$this$conflatedCallbackFlow, this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object invokeSuspend$updateState;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope<Boolean> producerScope = this.$$this$conflatedCallbackFlow;
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            this.label = 1;
            invokeSuspend$updateState = UserSwitcherRepositoryImpl$isEnabled$1.invokeSuspend$updateState(producerScope, userSwitcherRepositoryImpl, this);
            if (invokeSuspend$updateState == coroutine_suspended) {
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