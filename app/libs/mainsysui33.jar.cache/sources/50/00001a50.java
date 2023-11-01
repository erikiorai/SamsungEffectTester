package com.android.systemui.keyguard.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor", f = "KeyguardQuickAffordanceInteractor.kt", l = {225}, m = "getSelections")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$getSelections$1.class */
public final class KeyguardQuickAffordanceInteractor$getSelections$1 extends ContinuationImpl {
    public Object L$0;
    public Object L$1;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceInteractor$getSelections$1(KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, Continuation<? super KeyguardQuickAffordanceInteractor$getSelections$1> continuation) {
        super(continuation);
        this.this$0 = keyguardQuickAffordanceInteractor;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getSelections(this);
    }
}