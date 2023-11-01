package com.android.systemui.media.controls.ui;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/KeyguardMediaController$attachSinglePaneContainer$1.class */
public final /* synthetic */ class KeyguardMediaController$attachSinglePaneContainer$1 extends FunctionReferenceImpl implements Function1<Boolean, Unit> {
    public KeyguardMediaController$attachSinglePaneContainer$1(Object obj) {
        super(1, obj, KeyguardMediaController.class, "onMediaHostVisibilityChanged", "onMediaHostVisibilityChanged(Z)V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }

    public final void invoke(boolean z) {
        ((KeyguardMediaController) ((CallableReference) this).receiver).onMediaHostVisibilityChanged(z);
    }
}