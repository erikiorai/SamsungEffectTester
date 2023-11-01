package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.util.settings.SecureSettings;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$set$2", f = "KeyguardQuickAffordanceLegacySettingSyncer.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$set$2.class */
public final class KeyguardQuickAffordanceLegacySettingSyncer$set$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ boolean $isSet;
    public final /* synthetic */ String $settingsKey;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLegacySettingSyncer$set$2(KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, String str, boolean z, Continuation<? super KeyguardQuickAffordanceLegacySettingSyncer$set$2> continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceLegacySettingSyncer;
        this.$settingsKey = str;
        this.$isSet = z;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new KeyguardQuickAffordanceLegacySettingSyncer$set$2(this.this$0, this.$settingsKey, this.$isSet, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        SecureSettings secureSettings;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            secureSettings = this.this$0.secureSettings;
            return Boxing.boxBoolean(secureSettings.putInt(this.$settingsKey, this.$isSet ? 1 : 0));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}