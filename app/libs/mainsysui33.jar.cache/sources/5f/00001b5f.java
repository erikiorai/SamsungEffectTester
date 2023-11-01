package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$isIndicationAreaPadded$1", f = "KeyguardBottomAreaViewModel.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$isIndicationAreaPadded$1.class */
public final class KeyguardBottomAreaViewModel$isIndicationAreaPadded$1 extends SuspendLambda implements Function3<KeyguardQuickAffordanceViewModel, KeyguardQuickAffordanceViewModel, Continuation<? super Boolean>, Object> {
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    public KeyguardBottomAreaViewModel$isIndicationAreaPadded$1(Continuation<? super KeyguardBottomAreaViewModel$isIndicationAreaPadded$1> continuation) {
        super(3, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel2, Continuation<? super Boolean> continuation) {
        KeyguardBottomAreaViewModel$isIndicationAreaPadded$1 keyguardBottomAreaViewModel$isIndicationAreaPadded$1 = new KeyguardBottomAreaViewModel$isIndicationAreaPadded$1(continuation);
        keyguardBottomAreaViewModel$isIndicationAreaPadded$1.L$0 = keyguardQuickAffordanceViewModel;
        keyguardBottomAreaViewModel$isIndicationAreaPadded$1.L$1 = keyguardQuickAffordanceViewModel2;
        return keyguardBottomAreaViewModel$isIndicationAreaPadded$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return Boxing.boxBoolean(((KeyguardQuickAffordanceViewModel) this.L$0).isVisible() || ((KeyguardQuickAffordanceViewModel) this.L$1).isVisible());
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}