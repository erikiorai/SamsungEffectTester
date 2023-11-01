package com.android.systemui.qs.footer.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserName$1", f = "UserSwitcherRepository.kt", l = {98}, m = "invokeSuspend$updateState")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$currentUserName$1$updateState$1.class */
public final class UserSwitcherRepositoryImpl$currentUserName$1$updateState$1 extends ContinuationImpl {
    public Object L$0;
    public Object L$1;
    public int label;
    public /* synthetic */ Object result;

    public UserSwitcherRepositoryImpl$currentUserName$1$updateState$1(Continuation<? super UserSwitcherRepositoryImpl$currentUserName$1$updateState$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        Object invokeSuspend$updateState;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        invokeSuspend$updateState = UserSwitcherRepositoryImpl$currentUserName$1.invokeSuspend$updateState(null, null, this);
        return invokeSuspend$updateState;
    }
}