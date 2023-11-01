package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$bouncerExpansion$1", f = "PrimaryBouncerInteractor.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor$bouncerExpansion$1.class */
public final class PrimaryBouncerInteractor$bouncerExpansion$1 extends SuspendLambda implements Function3<Float, Boolean, Continuation<? super Float>, Object> {
    public /* synthetic */ float F$0;
    public /* synthetic */ boolean Z$0;
    public int label;

    public PrimaryBouncerInteractor$bouncerExpansion$1(Continuation<? super PrimaryBouncerInteractor$bouncerExpansion$1> continuation) {
        super(3, continuation);
    }

    public final Object invoke(float f, boolean z, Continuation<? super Float> continuation) {
        PrimaryBouncerInteractor$bouncerExpansion$1 primaryBouncerInteractor$bouncerExpansion$1 = new PrimaryBouncerInteractor$bouncerExpansion$1(continuation);
        primaryBouncerInteractor$bouncerExpansion$1.F$0 = f;
        primaryBouncerInteractor$bouncerExpansion$1.Z$0 = z;
        return primaryBouncerInteractor$bouncerExpansion$1.invokeSuspend(Unit.INSTANCE);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Number) obj).floatValue(), ((Boolean) obj2).booleanValue(), (Continuation) obj3);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return Boxing.boxFloat(this.Z$0 ? 1.0f - this.F$0 : 0.0f);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}