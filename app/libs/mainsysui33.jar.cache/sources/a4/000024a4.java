package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.LifecycleService;
import com.android.systemui.screenshot.IScreenshotProxy;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.Optional;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotProxyService.class */
public final class ScreenshotProxyService extends LifecycleService {
    public static final Companion Companion = new Companion(null);
    public final IBinder mBinder = new IScreenshotProxy.Stub() { // from class: com.android.systemui.screenshot.ScreenshotProxyService$mBinder$1
        @Override // com.android.systemui.screenshot.IScreenshotProxy
        public void dismissKeyguard(IOnDoneCallback iOnDoneCallback) {
            BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(ScreenshotProxyService.this), (CoroutineContext) null, (CoroutineStart) null, new ScreenshotProxyService$mBinder$1$dismissKeyguard$1(ScreenshotProxyService.this, iOnDoneCallback, null), 3, (Object) null);
        }

        @Override // com.android.systemui.screenshot.IScreenshotProxy
        public boolean isNotificationShadeExpanded() {
            ShadeExpansionStateManager shadeExpansionStateManager;
            shadeExpansionStateManager = ScreenshotProxyService.this.mExpansionMgr;
            boolean z = !shadeExpansionStateManager.isClosed();
            Log.d("ScreenshotProxyService", "isNotificationShadeExpanded(): " + z);
            return z;
        }
    };
    public final Optional<CentralSurfaces> mCentralSurfacesOptional;
    public final ShadeExpansionStateManager mExpansionMgr;
    public final CoroutineDispatcher mMainDispatcher;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotProxyService$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ScreenshotProxyService(ShadeExpansionStateManager shadeExpansionStateManager, Optional<CentralSurfaces> optional, CoroutineDispatcher coroutineDispatcher) {
        this.mExpansionMgr = shadeExpansionStateManager;
        this.mCentralSurfacesOptional = optional;
        this.mMainDispatcher = coroutineDispatcher;
    }

    public final Object executeAfterDismissing(IOnDoneCallback iOnDoneCallback, Continuation<? super Unit> continuation) {
        Object withContext = BuildersKt.withContext(this.mMainDispatcher, new ScreenshotProxyService$executeAfterDismissing$2(this, iOnDoneCallback, null), continuation);
        return withContext == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? withContext : Unit.INSTANCE;
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d("ScreenshotProxyService", "onBind: " + intent);
        return this.mBinder;
    }
}