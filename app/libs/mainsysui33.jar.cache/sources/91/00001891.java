package com.android.systemui.keyguard;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.keyguard.CustomizationProvider", f = "CustomizationProvider.kt", l = {288}, m = "queryAffordances")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/CustomizationProvider$queryAffordances$1.class */
public final class CustomizationProvider$queryAffordances$1 extends ContinuationImpl {
    public Object L$0;
    public Object L$1;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$queryAffordances$1(CustomizationProvider customizationProvider, Continuation<? super CustomizationProvider$queryAffordances$1> continuation) {
        super(continuation);
        this.this$0 = customizationProvider;
    }

    public final Object invokeSuspend(Object obj) {
        Object queryAffordances;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        queryAffordances = this.this$0.queryAffordances(this);
        return queryAffordances;
    }
}