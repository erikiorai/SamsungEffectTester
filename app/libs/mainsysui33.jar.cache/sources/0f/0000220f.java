package com.android.systemui.qs.footer.data.repository;

import android.graphics.drawable.Drawable;
import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$userSwitcherStatus$1$1", f = "UserSwitcherRepository.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$userSwitcherStatus$1$1.class */
public final class UserSwitcherRepositoryImpl$userSwitcherStatus$1$1 extends SuspendLambda implements Function3<String, Pair<? extends Drawable, ? extends Boolean>, Continuation<? super UserSwitcherStatusModel.Enabled>, Object> {
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    public UserSwitcherRepositoryImpl$userSwitcherStatus$1$1(Continuation<? super UserSwitcherRepositoryImpl$userSwitcherStatus$1$1> continuation) {
        super(3, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(String str, Pair<? extends Drawable, Boolean> pair, Continuation<? super UserSwitcherStatusModel.Enabled> continuation) {
        UserSwitcherRepositoryImpl$userSwitcherStatus$1$1 userSwitcherRepositoryImpl$userSwitcherStatus$1$1 = new UserSwitcherRepositoryImpl$userSwitcherStatus$1$1(continuation);
        userSwitcherRepositoryImpl$userSwitcherStatus$1$1.L$0 = str;
        userSwitcherRepositoryImpl$userSwitcherStatus$1$1.L$1 = pair;
        return userSwitcherRepositoryImpl$userSwitcherStatus$1$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            String str = (String) this.L$0;
            Pair pair = (Pair) this.L$1;
            return new UserSwitcherStatusModel.Enabled(str, (Drawable) pair.component1(), ((Boolean) pair.component2()).booleanValue());
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}