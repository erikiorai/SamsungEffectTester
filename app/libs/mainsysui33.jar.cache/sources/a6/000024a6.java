package com.android.systemui.screenshot;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.screenshot.ScreenshotProxyService$executeAfterDismissing$2", f = "ScreenshotProxyService.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotProxyService$executeAfterDismissing$2.class */
public final class ScreenshotProxyService$executeAfterDismissing$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ IOnDoneCallback $callback;
    public int label;
    public final /* synthetic */ ScreenshotProxyService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotProxyService$executeAfterDismissing$2(ScreenshotProxyService screenshotProxyService, IOnDoneCallback iOnDoneCallback, Continuation<? super ScreenshotProxyService$executeAfterDismissing$2> continuation) {
        super(2, continuation);
        this.this$0 = screenshotProxyService;
        this.$callback = iOnDoneCallback;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ScreenshotProxyService$executeAfterDismissing$2(this.this$0, this.$callback, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Optional optional;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            optional = this.this$0.mCentralSurfacesOptional;
            final IOnDoneCallback iOnDoneCallback = this.$callback;
            Consumer consumer = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotProxyService$executeAfterDismissing$2.1
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Consumer
                public final void accept(CentralSurfaces centralSurfaces) {
                    final IOnDoneCallback iOnDoneCallback2 = IOnDoneCallback.this;
                    centralSurfaces.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotProxyService.executeAfterDismissing.2.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            IOnDoneCallback.this.onDone(true);
                        }
                    }, (Runnable) null, true, true, true);
                }
            };
            final IOnDoneCallback iOnDoneCallback2 = this.$callback;
            optional.ifPresentOrElse(consumer, new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotProxyService$executeAfterDismissing$2.2
                @Override // java.lang.Runnable
                public final void run() {
                    IOnDoneCallback.this.onDone(false);
                }
            });
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}