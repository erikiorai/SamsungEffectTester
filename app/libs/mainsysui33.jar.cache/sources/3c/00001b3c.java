package com.android.systemui.keyguard.ui.preview;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.ArrayMap;
import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardRemotePreviewManager.class */
public final class KeyguardRemotePreviewManager {
    public static final Companion Companion = new Companion(null);
    public final ArrayMap<IBinder, PreviewLifecycleObserver> activePreviews = new ArrayMap<>();
    public final Handler backgroundHandler;
    public final CoroutineDispatcher mainDispatcher;
    public final KeyguardPreviewRendererFactory previewRendererFactory;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardRemotePreviewManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getKEY_PREVIEW_CALLBACK$annotations() {
        }

        public static /* synthetic */ void getKEY_PREVIEW_SURFACE_PACKAGE$annotations() {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardRemotePreviewManager$PreviewLifecycleObserver.class */
    public static final class PreviewLifecycleObserver implements Handler.Callback, IBinder.DeathRecipient {
        public boolean isDestroyed;
        public final CoroutineDispatcher mainDispatcher;
        public final KeyguardPreviewRenderer renderer;
        public final Function1<PreviewLifecycleObserver, Unit> requestDestruction;

        /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: kotlin.jvm.functions.Function1<? super com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager$PreviewLifecycleObserver, kotlin.Unit> */
        /* JADX WARN: Multi-variable type inference failed */
        public PreviewLifecycleObserver(KeyguardPreviewRenderer keyguardPreviewRenderer, CoroutineDispatcher coroutineDispatcher, Function1<? super PreviewLifecycleObserver, Unit> function1) {
            this.renderer = keyguardPreviewRenderer;
            this.mainDispatcher = coroutineDispatcher;
            this.requestDestruction = function1;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.requestDestruction.invoke(this);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1337) {
                this.requestDestruction.invoke(this);
                return true;
            }
            String string = message.getData().getString("slot_id");
            if (string != null) {
                this.renderer.onSlotSelected(string);
                return true;
            }
            return true;
        }

        public final IBinder onDestroy() {
            if (this.isDestroyed) {
                return null;
            }
            this.isDestroyed = true;
            IBinder hostToken = this.renderer.getHostToken();
            if (hostToken != null) {
                hostToken.unlinkToDeath(this, 0);
            }
            BuildersKt.runBlocking(this.mainDispatcher, new KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1(this, null));
            return hostToken;
        }
    }

    public KeyguardRemotePreviewManager(KeyguardPreviewRendererFactory keyguardPreviewRendererFactory, CoroutineDispatcher coroutineDispatcher, Handler handler) {
        this.previewRendererFactory = keyguardPreviewRendererFactory;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundHandler = handler;
    }

    public final void destroyObserver(PreviewLifecycleObserver previewLifecycleObserver) {
        IBinder onDestroy = previewLifecycleObserver.onDestroy();
        if (onDestroy == null || this.activePreviews.get(onDestroy) != previewLifecycleObserver) {
            return;
        }
        this.activePreviews.remove(onDestroy);
    }

    public final Bundle preview(Bundle bundle) {
        PreviewLifecycleObserver previewLifecycleObserver;
        Bundle bundle2;
        if (bundle == null) {
            return null;
        }
        try {
            KeyguardPreviewRenderer create = this.previewRendererFactory.create(bundle);
            PreviewLifecycleObserver previewLifecycleObserver2 = this.activePreviews.get(create.getHostToken());
            if (previewLifecycleObserver2 != null) {
                destroyObserver(previewLifecycleObserver2);
            }
            previewLifecycleObserver = new PreviewLifecycleObserver(create, this.mainDispatcher, new KeyguardRemotePreviewManager$preview$2(this));
            try {
                this.activePreviews.put(create.getHostToken(), previewLifecycleObserver);
                create.render();
                IBinder hostToken = create.getHostToken();
                if (hostToken != null) {
                    hostToken.linkToDeath(previewLifecycleObserver, 0);
                }
                bundle2 = new Bundle();
                bundle2.putParcelable("surface_package", create.getSurfacePackage());
                Messenger messenger = new Messenger(new Handler(this.backgroundHandler.getLooper(), previewLifecycleObserver));
                Message obtain = Message.obtain();
                obtain.replyTo = messenger;
                bundle2.putParcelable("callback", obtain);
            } catch (Exception e) {
                e = e;
                Log.e("KeyguardRemotePreviewManager", "Unable to generate preview", e);
                bundle2 = null;
                if (previewLifecycleObserver != null) {
                    destroyObserver(previewLifecycleObserver);
                    bundle2 = null;
                }
                return bundle2;
            }
        } catch (Exception e2) {
            e = e2;
            previewLifecycleObserver = null;
        }
        return bundle2;
    }
}