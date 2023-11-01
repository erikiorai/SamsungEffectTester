package com.android.systemui.qs.footer.data.repository;

import android.os.UserManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2", f = "UserSwitcherRepository.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2.class */
public final class UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public int label;
    public final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2> continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        UserManager userManager;
        boolean z;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            userManager = this.this$0.userManager;
            z = this.this$0.showUserSwitcherForSingleUser;
            return Boxing.boxBoolean(userManager.isUserSwitcherEnabled(z));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}