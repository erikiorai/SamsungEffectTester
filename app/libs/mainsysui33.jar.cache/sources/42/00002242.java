package com.android.systemui.qs.footer.ui.viewmodel;

import android.view.ContextThemeWrapper;
import com.android.systemui.R$string;
import com.android.systemui.animation.Expandable;
import com.android.systemui.util.PluralMessageFormaterKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

@DebugMetadata(c = "com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$foregroundServices$1", f = "FooterActionsViewModel.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$foregroundServices$1.class */
public final class FooterActionsViewModel$foregroundServices$1 extends SuspendLambda implements Function4<Integer, Boolean, FooterActionsSecurityButtonViewModel, Continuation<? super FooterActionsForegroundServicesButtonViewModel>, Object> {
    public /* synthetic */ int I$0;
    public /* synthetic */ Object L$0;
    public /* synthetic */ boolean Z$0;
    public int label;
    public final /* synthetic */ FooterActionsViewModel this$0;

    /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$foregroundServices$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$foregroundServices$1$1.class */
    public final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<Expandable, Unit> {
        public AnonymousClass1(Object obj) {
            super(1, obj, FooterActionsViewModel.class, "onForegroundServiceButtonClicked", "onForegroundServiceButtonClicked(Lcom/android/systemui/animation/Expandable;)V", 0);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Expandable) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(Expandable expandable) {
            ((FooterActionsViewModel) ((CallableReference) this).receiver).onForegroundServiceButtonClicked(expandable);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsViewModel$foregroundServices$1(FooterActionsViewModel footerActionsViewModel, Continuation<? super FooterActionsViewModel$foregroundServices$1> continuation) {
        super(4, continuation);
        this.this$0 = footerActionsViewModel;
    }

    public final Object invoke(int i, boolean z, FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel, Continuation<? super FooterActionsForegroundServicesButtonViewModel> continuation) {
        FooterActionsViewModel$foregroundServices$1 footerActionsViewModel$foregroundServices$1 = new FooterActionsViewModel$foregroundServices$1(this.this$0, continuation);
        footerActionsViewModel$foregroundServices$1.I$0 = i;
        footerActionsViewModel$foregroundServices$1.Z$0 = z;
        footerActionsViewModel$foregroundServices$1.L$0 = footerActionsSecurityButtonViewModel;
        return footerActionsViewModel$foregroundServices$1.invokeSuspend(Unit.INSTANCE);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return invoke(((Number) obj).intValue(), ((Boolean) obj2).booleanValue(), (FooterActionsSecurityButtonViewModel) obj3, (Continuation) obj4);
    }

    public final Object invokeSuspend(Object obj) {
        ContextThemeWrapper contextThemeWrapper;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            int i = this.I$0;
            boolean z = this.Z$0;
            FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel = (FooterActionsSecurityButtonViewModel) this.L$0;
            if (i <= 0) {
                return null;
            }
            contextThemeWrapper = this.this$0.context;
            return new FooterActionsForegroundServicesButtonViewModel(i, PluralMessageFormaterKt.icuMessageFormat(contextThemeWrapper.getResources(), R$string.fgs_manager_footer_label, i), footerActionsSecurityButtonViewModel == null, z, new AnonymousClass1(this.this$0));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}