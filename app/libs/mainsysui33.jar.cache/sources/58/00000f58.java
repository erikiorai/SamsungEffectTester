package com.android.systemui;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.ChooserSelector$updateUnbundledChooserEnabled$2", f = "ChooserSelector.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/ChooserSelector$updateUnbundledChooserEnabled$2.class */
public final class ChooserSelector$updateUnbundledChooserEnabled$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public int label;
    public final /* synthetic */ ChooserSelector this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChooserSelector$updateUnbundledChooserEnabled$2(ChooserSelector chooserSelector, Continuation<? super ChooserSelector$updateUnbundledChooserEnabled$2> continuation) {
        super(2, continuation);
        this.this$0 = chooserSelector;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ChooserSelector$updateUnbundledChooserEnabled$2(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        FeatureFlags featureFlags;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            featureFlags = this.this$0.featureFlags;
            return Boxing.boxBoolean(featureFlags.isEnabled(Flags.INSTANCE.getCHOOSER_UNBUNDLED()));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}