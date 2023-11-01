package com.android.systemui.common.coroutine;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/common/coroutine/ConflatedCallbackFlow.class */
public final class ConflatedCallbackFlow {
    public static final ConflatedCallbackFlow INSTANCE = new ConflatedCallbackFlow();

    public final <T> Flow<T> conflatedCallbackFlow(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> function2) {
        return FlowKt.buffer$default(FlowKt.callbackFlow(function2), -1, (BufferOverflow) null, 2, (Object) null);
    }
}