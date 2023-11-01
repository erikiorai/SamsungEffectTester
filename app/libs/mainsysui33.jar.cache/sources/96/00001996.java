package com.android.systemui.keyguard.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository", f = "KeyguardQuickAffordanceRepository.kt", l = {185}, m = "getAffordancePickerRepresentations")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.class */
public final class KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1 extends ContinuationImpl {
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public Object L$3;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1(KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository, Continuation<? super KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1> continuation) {
        super(continuation);
        this.this$0 = keyguardQuickAffordanceRepository;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getAffordancePickerRepresentations(this);
    }
}