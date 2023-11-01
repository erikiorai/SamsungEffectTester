package com.android.systemui.media.controls.ui;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselController$addSmartspaceMediaRecommendations$1$2.class */
public final /* synthetic */ class MediaCarouselController$addSmartspaceMediaRecommendations$1$2 extends FunctionReferenceImpl implements Function0<Unit> {
    public MediaCarouselController$addSmartspaceMediaRecommendations$1$2(Object obj) {
        super(0, obj, MediaCarouselController.class, "updateCarouselDimensions", "updateCarouselDimensions()V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke() {
        m3238invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: collision with other method in class */
    public final void m3238invoke() {
        ((MediaCarouselController) ((CallableReference) this).receiver).updateCarouselDimensions();
    }
}