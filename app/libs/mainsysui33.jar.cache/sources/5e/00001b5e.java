package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

@DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$button$1$1", f = "KeyguardBottomAreaViewModel.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$button$1$1.class */
public final class KeyguardBottomAreaViewModel$button$1$1 extends SuspendLambda implements Function5<KeyguardQuickAffordanceModel, Boolean, Boolean, String, Continuation<? super KeyguardQuickAffordanceViewModel>, Object> {
    public final /* synthetic */ boolean $isInPreviewMode;
    public final /* synthetic */ KeyguardQuickAffordancePosition $position;
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public /* synthetic */ boolean Z$0;
    public /* synthetic */ boolean Z$1;
    public int label;
    public final /* synthetic */ KeyguardBottomAreaViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewModel$button$1$1(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, boolean z, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, Continuation<? super KeyguardBottomAreaViewModel$button$1$1> continuation) {
        super(5, continuation);
        this.this$0 = keyguardBottomAreaViewModel;
        this.$isInPreviewMode = z;
        this.$position = keyguardQuickAffordancePosition;
    }

    public final Object invoke(KeyguardQuickAffordanceModel keyguardQuickAffordanceModel, boolean z, boolean z2, String str, Continuation<? super KeyguardQuickAffordanceViewModel> continuation) {
        KeyguardBottomAreaViewModel$button$1$1 keyguardBottomAreaViewModel$button$1$1 = new KeyguardBottomAreaViewModel$button$1$1(this.this$0, this.$isInPreviewMode, this.$position, continuation);
        keyguardBottomAreaViewModel$button$1$1.L$0 = keyguardQuickAffordanceModel;
        keyguardBottomAreaViewModel$button$1$1.Z$0 = z;
        keyguardBottomAreaViewModel$button$1$1.Z$1 = z2;
        keyguardBottomAreaViewModel$button$1$1.L$1 = str;
        return keyguardBottomAreaViewModel$button$1$1.invokeSuspend(Unit.INSTANCE);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        return invoke((KeyguardQuickAffordanceModel) obj, ((Boolean) obj2).booleanValue(), ((Boolean) obj3).booleanValue(), (String) obj4, (Continuation) obj5);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardQuickAffordanceViewModel viewModel;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardQuickAffordanceModel keyguardQuickAffordanceModel = (KeyguardQuickAffordanceModel) this.L$0;
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            String str = (String) this.L$1;
            KeyguardBottomAreaViewModel keyguardBottomAreaViewModel = this.this$0;
            boolean z3 = this.$isInPreviewMode;
            boolean z4 = true;
            boolean z5 = !z3 && z;
            boolean z6 = z2 && !z3;
            if (!z3 || !Intrinsics.areEqual(str, this.$position.toSlotId())) {
                z4 = false;
            }
            viewModel = keyguardBottomAreaViewModel.toViewModel(keyguardQuickAffordanceModel, z5, z6, z4);
            return viewModel;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}