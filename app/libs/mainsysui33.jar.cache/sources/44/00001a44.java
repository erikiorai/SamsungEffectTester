package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$isAbleToDream$1", f = "KeyguardInteractor.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardInteractor$isAbleToDream$1.class */
public final class KeyguardInteractor$isAbleToDream$1 extends SuspendLambda implements Function3<Boolean, DozeTransitionModel, Continuation<? super Boolean>, Object> {
    public /* synthetic */ Object L$0;
    public /* synthetic */ boolean Z$0;
    public int label;

    public KeyguardInteractor$isAbleToDream$1(Continuation<? super KeyguardInteractor$isAbleToDream$1> continuation) {
        super(3, continuation);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Boolean) obj).booleanValue(), (DozeTransitionModel) obj2, (Continuation) obj3);
    }

    public final Object invoke(boolean z, DozeTransitionModel dozeTransitionModel, Continuation<? super Boolean> continuation) {
        KeyguardInteractor$isAbleToDream$1 keyguardInteractor$isAbleToDream$1 = new KeyguardInteractor$isAbleToDream$1(continuation);
        keyguardInteractor$isAbleToDream$1.Z$0 = z;
        keyguardInteractor$isAbleToDream$1.L$0 = dozeTransitionModel;
        return keyguardInteractor$isAbleToDream$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return Boxing.boxBoolean(this.Z$0 && DozeStateModel.Companion.isDozeOff(((DozeTransitionModel) this.L$0).getTo()));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}