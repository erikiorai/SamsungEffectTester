package com.android.systemui;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.ChooserSelector", f = "ChooserSelector.kt", l = {50}, m = "updateUnbundledChooserEnabled")
/* loaded from: mainsysui33.jar:com/android/systemui/ChooserSelector$updateUnbundledChooserEnabled$1.class */
public final class ChooserSelector$updateUnbundledChooserEnabled$1 extends ContinuationImpl {
    public Object L$0;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ ChooserSelector this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChooserSelector$updateUnbundledChooserEnabled$1(ChooserSelector chooserSelector, Continuation<? super ChooserSelector$updateUnbundledChooserEnabled$1> continuation) {
        super(continuation);
        this.this$0 = chooserSelector;
    }

    public final Object invokeSuspend(Object obj) {
        Object updateUnbundledChooserEnabled;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        updateUnbundledChooserEnabled = this.this$0.updateUnbundledChooserEnabled(this);
        return updateUnbundledChooserEnabled;
    }
}