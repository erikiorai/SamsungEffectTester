package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.screenshot.ActionIntentExecutor", f = "ActionIntentExecutor.kt", l = {73, 76, 78}, m = "launchIntent")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ActionIntentExecutor$launchIntent$1.class */
public final class ActionIntentExecutor$launchIntent$1 extends ContinuationImpl {
    public int I$0;
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public boolean Z$0;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ ActionIntentExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionIntentExecutor$launchIntent$1(ActionIntentExecutor actionIntentExecutor, Continuation<? super ActionIntentExecutor$launchIntent$1> continuation) {
        super(continuation);
        this.this$0 = actionIntentExecutor;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.launchIntent(null, null, 0, false, this);
    }
}