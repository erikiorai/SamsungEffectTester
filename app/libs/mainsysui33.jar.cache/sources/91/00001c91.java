package com.android.systemui.media.controls.ui;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselController$addOrUpdatePlayer$1$1.class */
public final /* synthetic */ class MediaCarouselController$addOrUpdatePlayer$1$1 extends FunctionReferenceImpl implements Function0<Unit> {
    public MediaCarouselController$addOrUpdatePlayer$1$1(Object obj) {
        super(0, obj, MediaCarouselController.class, "updateCarouselDimensions", "updateCarouselDimensions()V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke() {
        m3237invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: collision with other method in class */
    public final void m3237invoke() {
        ((MediaCarouselController) ((CallableReference) this).receiver).updateCarouselDimensions();
    }
}