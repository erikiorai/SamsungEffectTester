package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3", f = "KeyguardQuickAffordanceLegacySettingSyncer.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3.class */
public final class KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3 extends SuspendLambda implements Function2<Boolean, Continuation<? super Unit>, Object> {
    public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer.Binding $binding;
    public /* synthetic */ boolean Z$0;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3(KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, KeyguardQuickAffordanceLegacySettingSyncer.Binding binding, Continuation<? super KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3> continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceLegacySettingSyncer;
        this.$binding = binding;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3 keyguardQuickAffordanceLegacySettingSyncer$startSyncing$3 = new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3(this.this$0, this.$binding, continuation);
        keyguardQuickAffordanceLegacySettingSyncer$startSyncing$3.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardQuickAffordanceLegacySettingSyncer$startSyncing$3;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    public final Object invoke(boolean z, Continuation<? super Unit> continuation) {
        return create(Boolean.valueOf(z), continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        boolean isSelected;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            isSelected = this.this$0.isSelected(this.$binding.getAffordanceId());
            if (isSelected != z) {
                if (z) {
                    this.this$0.select(this.$binding.getSlotId(), this.$binding.getAffordanceId());
                } else {
                    this.this$0.unselect(this.$binding.getAffordanceId());
                }
            }
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}