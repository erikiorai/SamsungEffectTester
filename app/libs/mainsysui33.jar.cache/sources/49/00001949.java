package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6", f = "KeyguardQuickAffordanceLegacySettingSyncer.kt", l = {137}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6.class */
public final class KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6 extends SuspendLambda implements Function2<Boolean, Continuation<? super Unit>, Object> {
    public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer.Binding $binding;
    public /* synthetic */ boolean Z$0;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6(KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, KeyguardQuickAffordanceLegacySettingSyncer.Binding binding, Continuation<? super KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6> continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceLegacySettingSyncer;
        this.$binding = binding;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6 keyguardQuickAffordanceLegacySettingSyncer$startSyncing$6 = new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6(this.this$0, this.$binding, continuation);
        keyguardQuickAffordanceLegacySettingSyncer$startSyncing$6.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardQuickAffordanceLegacySettingSyncer$startSyncing$6;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    public final Object invoke(boolean z, Continuation<? super Unit> continuation) {
        return create(Boolean.valueOf(z), continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        boolean isSet;
        Object obj2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            isSet = this.this$0.isSet(this.$binding.getSettingsKey());
            if (isSet != z) {
                KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer = this.this$0;
                String settingsKey = this.$binding.getSettingsKey();
                this.label = 1;
                obj2 = keyguardQuickAffordanceLegacySettingSyncer.set(settingsKey, z, this);
                if (obj2 == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}