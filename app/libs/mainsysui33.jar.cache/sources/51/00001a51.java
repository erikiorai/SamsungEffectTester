package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$1", f = "KeyguardQuickAffordanceInteractor.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$quickAffordance$1.class */
public final class KeyguardQuickAffordanceInteractor$quickAffordance$1 extends SuspendLambda implements Function4<KeyguardQuickAffordanceModel, Boolean, Boolean, Continuation<? super KeyguardQuickAffordanceModel>, Object> {
    public /* synthetic */ Object L$0;
    public /* synthetic */ boolean Z$0;
    public /* synthetic */ boolean Z$1;
    public int label;

    public KeyguardQuickAffordanceInteractor$quickAffordance$1(Continuation<? super KeyguardQuickAffordanceInteractor$quickAffordance$1> continuation) {
        super(4, continuation);
    }

    public final Object invoke(KeyguardQuickAffordanceModel keyguardQuickAffordanceModel, boolean z, boolean z2, Continuation<? super KeyguardQuickAffordanceModel> continuation) {
        KeyguardQuickAffordanceInteractor$quickAffordance$1 keyguardQuickAffordanceInteractor$quickAffordance$1 = new KeyguardQuickAffordanceInteractor$quickAffordance$1(continuation);
        keyguardQuickAffordanceInteractor$quickAffordance$1.L$0 = keyguardQuickAffordanceModel;
        keyguardQuickAffordanceInteractor$quickAffordance$1.Z$0 = z;
        keyguardQuickAffordanceInteractor$quickAffordance$1.Z$1 = z2;
        return keyguardQuickAffordanceInteractor$quickAffordance$1.invokeSuspend(Unit.INSTANCE);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return invoke((KeyguardQuickAffordanceModel) obj, ((Boolean) obj2).booleanValue(), ((Boolean) obj3).booleanValue(), (Continuation) obj4);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardQuickAffordanceModel.Hidden hidden = (KeyguardQuickAffordanceModel) this.L$0;
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            if (z || !z2) {
                hidden = KeyguardQuickAffordanceModel.Hidden.INSTANCE;
            }
            return hidden;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}