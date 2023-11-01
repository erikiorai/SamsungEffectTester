package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1", f = "KeyguardQuickAffordanceLegacySettingSyncer.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1.class */
public final class KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ List<KeyguardQuickAffordanceLegacySettingSyncer.Binding> $bindings;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1(List<KeyguardQuickAffordanceLegacySettingSyncer.Binding> list, KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, Continuation<? super KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1> continuation) {
        super(2, continuation);
        this.$bindings = list;
        this.this$0 = keyguardQuickAffordanceLegacySettingSyncer;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1 keyguardQuickAffordanceLegacySettingSyncer$startSyncing$1 = new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1(this.$bindings, this.this$0, continuation);
        keyguardQuickAffordanceLegacySettingSyncer$startSyncing$1.L$0 = obj;
        return keyguardQuickAffordanceLegacySettingSyncer$startSyncing$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            List<KeyguardQuickAffordanceLegacySettingSyncer.Binding> list = this.$bindings;
            KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer = this.this$0;
            for (KeyguardQuickAffordanceLegacySettingSyncer.Binding binding : list) {
                keyguardQuickAffordanceLegacySettingSyncer.startSyncing(coroutineScope, binding);
            }
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}