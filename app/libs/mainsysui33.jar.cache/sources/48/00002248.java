package com.android.systemui.qs.footer.ui.viewmodel;

import com.android.systemui.animation.Expandable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$userSwitcherButton$1.class */
public final /* synthetic */ class FooterActionsViewModel$userSwitcherButton$1 extends FunctionReferenceImpl implements Function1<Expandable, Unit> {
    public FooterActionsViewModel$userSwitcherButton$1(Object obj) {
        super(1, obj, FooterActionsViewModel.class, "onUserSwitcherClicked", "onUserSwitcherClicked(Lcom/android/systemui/animation/Expandable;)V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Expandable) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Expandable expandable) {
        ((FooterActionsViewModel) ((CallableReference) this).receiver).onUserSwitcherClicked(expandable);
    }
}