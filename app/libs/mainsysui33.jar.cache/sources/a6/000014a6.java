package com.android.systemui.controls.ui;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlsUiControllerImpl$show$5.class */
public final /* synthetic */ class ControlsUiControllerImpl$show$5 extends FunctionReferenceImpl implements Function1<List<? extends SelectionItem>, Unit> {
    public ControlsUiControllerImpl$show$5(Object obj) {
        super(1, obj, ControlsUiControllerImpl.class, "showControlsView", "showControlsView(Ljava/util/List;)V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((List) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(List<SelectionItem> list) {
        ((ControlsUiControllerImpl) ((CallableReference) this).receiver).showControlsView(list);
    }
}