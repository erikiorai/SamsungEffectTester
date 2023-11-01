package com.android.systemui.keyguard.ui.preview;

import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$render$1", f = "KeyguardPreviewRenderer.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardPreviewRenderer$render$1.class */
public final class KeyguardPreviewRenderer$render$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ KeyguardPreviewRenderer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardPreviewRenderer$render$1(KeyguardPreviewRenderer keyguardPreviewRenderer, Continuation<? super KeyguardPreviewRenderer$render$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardPreviewRenderer;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new KeyguardPreviewRenderer$render$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        WindowManager windowManager;
        WindowManager windowManager2;
        int i;
        int i2;
        int i3;
        int i4;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            FrameLayout frameLayout = new FrameLayout(this.this$0.context);
            this.this$0.setUpBottomArea(frameLayout);
            this.this$0.setUpClock(frameLayout);
            windowManager = this.this$0.windowManager;
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(windowManager.getCurrentWindowMetrics().getBounds().width(), 1073741824);
            windowManager2 = this.this$0.windowManager;
            frameLayout.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(windowManager2.getCurrentWindowMetrics().getBounds().height(), 1073741824));
            frameLayout.layout(0, 0, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());
            i = this.this$0.width;
            i2 = this.this$0.height;
            float coerceAtMost = RangesKt___RangesKt.coerceAtMost(i / frameLayout.getMeasuredWidth(), i2 / frameLayout.getMeasuredHeight());
            frameLayout.setScaleX(coerceAtMost);
            frameLayout.setScaleY(coerceAtMost);
            frameLayout.setPivotX(ActionBarShadowController.ELEVATION_LOW);
            frameLayout.setPivotY(ActionBarShadowController.ELEVATION_LOW);
            i3 = this.this$0.width;
            float f = i3;
            float width = frameLayout.getWidth();
            float f2 = 2;
            frameLayout.setTranslationX((f - (width * coerceAtMost)) / f2);
            i4 = this.this$0.height;
            frameLayout.setTranslationY((i4 - (coerceAtMost * frameLayout.getHeight())) / f2);
            this.this$0.host.setView(frameLayout, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}