package com.android.systemui.keyguard;

import androidx.recyclerview.widget.RecyclerView;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.keyguard.CustomizationProvider", f = "CustomizationProvider.kt", l = {RecyclerView.ViewHolder.FLAG_TMP_DETACHED}, m = "querySelections")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/CustomizationProvider$querySelections$1.class */
public final class CustomizationProvider$querySelections$1 extends ContinuationImpl {
    public Object L$0;
    public Object L$1;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$querySelections$1(CustomizationProvider customizationProvider, Continuation<? super CustomizationProvider$querySelections$1> continuation) {
        super(continuation);
        this.this$0 = customizationProvider;
    }

    public final Object invokeSuspend(Object obj) {
        Object querySelections;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        querySelections = this.this$0.querySelections(this);
        return querySelections;
    }
}