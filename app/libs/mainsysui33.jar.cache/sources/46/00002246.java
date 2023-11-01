package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import com.android.systemui.animation.Expandable;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$security$1$1.class */
public final /* synthetic */ class FooterActionsViewModel$security$1$1 extends FunctionReferenceImpl implements Function2<Context, Expandable, Unit> {
    public FooterActionsViewModel$security$1$1(Object obj) {
        super(2, obj, FooterActionsViewModel.class, "onSecurityButtonClicked", "onSecurityButtonClicked(Landroid/content/Context;Lcom/android/systemui/animation/Expandable;)V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        invoke((Context) obj, (Expandable) obj2);
        return Unit.INSTANCE;
    }

    public final void invoke(Context context, Expandable expandable) {
        ((FooterActionsViewModel) ((CallableReference) this).receiver).onSecurityButtonClicked(context, expandable);
    }
}