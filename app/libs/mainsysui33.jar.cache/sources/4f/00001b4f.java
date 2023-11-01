package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1", f = "KeyguardBottomAreaViewModel.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1.class */
public final class KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super KeyguardQuickAffordanceViewModel>, Boolean, Continuation<? super Unit>, Object> {
    public final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ KeyguardBottomAreaViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1(Continuation continuation, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        super(3, continuation);
        this.this$0 = keyguardBottomAreaViewModel;
        this.$position$inlined = keyguardQuickAffordancePosition;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (Boolean) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super KeyguardQuickAffordanceViewModel> flowCollector, Boolean bool, Continuation<? super Unit> continuation) {
        KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1 keyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1 = new KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1(continuation, this.this$0, this.$position$inlined);
        keyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1.L$0 = flowCollector;
        keyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1.L$1 = bool;
        return keyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor;
        Flow<KeyguardQuickAffordanceModel> quickAffordance;
        KeyguardBottomAreaInteractor keyguardBottomAreaInteractor;
        Flow flow;
        Flow flow2;
        KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            boolean booleanValue = ((Boolean) this.L$1).booleanValue();
            if (booleanValue) {
                keyguardQuickAffordanceInteractor2 = this.this$0.quickAffordanceInteractor;
                quickAffordance = keyguardQuickAffordanceInteractor2.quickAffordanceAlwaysVisible(this.$position$inlined);
            } else {
                keyguardQuickAffordanceInteractor = this.this$0.quickAffordanceInteractor;
                quickAffordance = keyguardQuickAffordanceInteractor.quickAffordance(this.$position$inlined);
            }
            keyguardBottomAreaInteractor = this.this$0.bottomAreaInteractor;
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(keyguardBottomAreaInteractor.getAnimateDozingTransitions());
            flow = this.this$0.areQuickAffordancesFullyOpaque;
            flow2 = this.this$0.selectedPreviewSlotId;
            Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(quickAffordance, distinctUntilChanged, flow, flow2, new KeyguardBottomAreaViewModel$button$1$1(this.this$0, booleanValue, this.$position$inlined, null)));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, distinctUntilChanged2, this) == coroutine_suspended) {
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