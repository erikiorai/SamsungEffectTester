package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig", f = "QuickAccessWalletKeyguardQuickAffordanceConfig.kt", l = {105}, m = "getPickerScreenState")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1.class */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1 extends ContinuationImpl {
    public Object L$0;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1(QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, Continuation<? super QuickAccessWalletKeyguardQuickAffordanceConfig$getPickerScreenState$1> continuation) {
        super(continuation);
        this.this$0 = quickAccessWalletKeyguardQuickAffordanceConfig;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getPickerScreenState(this);
    }
}