package com.android.systemui.lifecycle;

import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.lifecycle.RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1", f = "RepeatWhenAttached.kt", l = {118}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/lifecycle/RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1.class */
public final class RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> $block;
    public final /* synthetic */ ViewLifecycleOwner $this_apply;
    public final /* synthetic */ View $view;
    public int label;

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlin.jvm.functions.Function3<? super androidx.lifecycle.LifecycleOwner, ? super android.view.View, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(Function3<? super LifecycleOwner, ? super View, ? super Continuation<? super Unit>, ? extends Object> function3, ViewLifecycleOwner viewLifecycleOwner, View view, Continuation<? super RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1> continuation) {
        super(2, continuation);
        this.$block = function3;
        this.$this_apply = viewLifecycleOwner;
        this.$view = view;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(this.$block, this.$this_apply, this.$view, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> function3 = this.$block;
            ViewLifecycleOwner viewLifecycleOwner = this.$this_apply;
            View view = this.$view;
            this.label = 1;
            if (function3.invoke(viewLifecycleOwner, view, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}