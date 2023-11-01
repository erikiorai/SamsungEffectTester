package com.android.systemui.keyguard.ui.preview;

import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardRemotePreviewManager$preview$2.class */
public final /* synthetic */ class KeyguardRemotePreviewManager$preview$2 extends FunctionReferenceImpl implements Function1<KeyguardRemotePreviewManager.PreviewLifecycleObserver, Unit> {
    public KeyguardRemotePreviewManager$preview$2(Object obj) {
        super(1, obj, KeyguardRemotePreviewManager.class, "destroyObserver", "destroyObserver(Lcom/android/systemui/keyguard/ui/preview/KeyguardRemotePreviewManager$PreviewLifecycleObserver;)V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((KeyguardRemotePreviewManager.PreviewLifecycleObserver) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(KeyguardRemotePreviewManager.PreviewLifecycleObserver previewLifecycleObserver) {
        ((KeyguardRemotePreviewManager) ((CallableReference) this).receiver).destroyObserver(previewLifecycleObserver);
    }
}