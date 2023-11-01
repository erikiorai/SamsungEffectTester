package com.android.systemui.keyguard.ui.preview;

import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1", f = "KeyguardRemotePreviewManager.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1.class */
public final class KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ KeyguardRemotePreviewManager.PreviewLifecycleObserver this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1(KeyguardRemotePreviewManager.PreviewLifecycleObserver previewLifecycleObserver, Continuation<? super KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1> continuation) {
        super(2, continuation);
        this.this$0 = previewLifecycleObserver;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardPreviewRenderer keyguardPreviewRenderer;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            keyguardPreviewRenderer = this.this$0.renderer;
            keyguardPreviewRenderer.destroy();
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}