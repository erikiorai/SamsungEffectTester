package com.android.systemui.screenshot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.WindowManagerGlobal;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.screenshot.ICrossProfileService;
import com.android.systemui.screenshot.IOnDoneCallback;
import com.android.systemui.screenshot.IScreenshotProxy;
import java.util.function.Function;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ActionIntentExecutor.class */
public final class ActionIntentExecutor {
    public final CoroutineScope applicationScope;
    public final Context context;
    public final CoroutineDispatcher mainDispatcher;
    public final ServiceConnector<IScreenshotProxy> proxyConnector;

    public ActionIntentExecutor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Context context) {
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.context = context;
        this.proxyConnector = new ServiceConnector.Impl(context, new Intent(context, ScreenshotProxyService.class), 1073741857, context.getUserId(), new Function() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$proxyConnector$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Function
            public final IScreenshotProxy apply(IBinder iBinder) {
                return IScreenshotProxy.Stub.asInterface(iBinder);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.screenshot.ActionIntentExecutor$dismissKeyguard$onDoneBinder$1] */
    public final Object dismissKeyguard(Continuation<? super Unit> continuation) {
        final CompletableDeferred CompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default((Job) null, 1, (Object) null);
        final ?? r0 = new IOnDoneCallback.Stub() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$dismissKeyguard$onDoneBinder$1
            @Override // com.android.systemui.screenshot.IOnDoneCallback
            public void onDone(boolean z) {
                CompletableDeferred$default.complete(Unit.INSTANCE);
            }
        };
        this.proxyConnector.post(new ServiceConnector.VoidJob() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$dismissKeyguard$2
            /* JADX DEBUG: Method merged with bridge method */
            public final void runNoResult(IScreenshotProxy iScreenshotProxy) {
                iScreenshotProxy.dismissKeyguard(ActionIntentExecutor$dismissKeyguard$onDoneBinder$1.this);
            }
        });
        Object await = CompletableDeferred$default.await(continuation);
        return await == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? await : Unit.INSTANCE;
    }

    public final ServiceConnector<ICrossProfileService> getCrossProfileConnector(int i) {
        return new ServiceConnector.Impl(this.context, new Intent(this.context, ScreenshotCrossProfileService.class), 1073741857, i, new Function() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$getCrossProfileConnector$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Function
            public final ICrossProfileService apply(IBinder iBinder) {
                return ICrossProfileService.Stub.asInterface(iBinder);
            }
        });
    }

    public final Object launchCrossProfileIntent(int i, final Intent intent, final Bundle bundle, Continuation<? super Unit> continuation) {
        ServiceConnector<ICrossProfileService> crossProfileConnector = getCrossProfileConnector(i);
        final CompletableDeferred CompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default((Job) null, 1, (Object) null);
        crossProfileConnector.post(new ServiceConnector.VoidJob() { // from class: com.android.systemui.screenshot.ActionIntentExecutor$launchCrossProfileIntent$2
            /* JADX DEBUG: Method merged with bridge method */
            public final void runNoResult(ICrossProfileService iCrossProfileService) {
                iCrossProfileService.launchIntent(intent, bundle);
                CompletableDeferred$default.complete(Unit.INSTANCE);
            }
        });
        Object await = CompletableDeferred$default.await(continuation);
        return await == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? await : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0170  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object launchIntent(Intent intent, Bundle bundle, int i, boolean z, Continuation<? super Unit> continuation) {
        Continuation<? super Unit> actionIntentExecutor$launchIntent$1;
        int i2;
        ActionIntentExecutor actionIntentExecutor;
        IRemoteAnimationRunner.Stub stub;
        if (continuation instanceof ActionIntentExecutor$launchIntent$1) {
            Continuation<? super Unit> continuation2 = (ActionIntentExecutor$launchIntent$1) continuation;
            int i3 = continuation2.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                continuation2.label = i3 - 2147483648;
                actionIntentExecutor$launchIntent$1 = continuation2;
                Object obj = actionIntentExecutor$launchIntent$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i2 = actionIntentExecutor$launchIntent$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    actionIntentExecutor$launchIntent$1.L$0 = this;
                    actionIntentExecutor$launchIntent$1.L$1 = intent;
                    actionIntentExecutor$launchIntent$1.L$2 = bundle;
                    actionIntentExecutor$launchIntent$1.I$0 = i;
                    actionIntentExecutor$launchIntent$1.Z$0 = z;
                    actionIntentExecutor$launchIntent$1.label = 1;
                    actionIntentExecutor = this;
                    if (dismissKeyguard(actionIntentExecutor$launchIntent$1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i2 != 1) {
                    if (i2 == 2 || i2 == 3) {
                        z = actionIntentExecutor$launchIntent$1.Z$0;
                        ResultKt.throwOnFailure(obj);
                        if (z) {
                            stub = ActionIntentExecutorKt.SCREENSHOT_REMOTE_RUNNER;
                            try {
                                WindowManagerGlobal.getWindowManagerService().overridePendingAppTransitionRemote(new RemoteAnimationAdapter(stub, 0L, 0L), 0);
                            } catch (Exception e) {
                                Log.e("ActionIntentExecutor", "Error overriding screenshot app transition", e);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    z = actionIntentExecutor$launchIntent$1.Z$0;
                    i = actionIntentExecutor$launchIntent$1.I$0;
                    bundle = (Bundle) actionIntentExecutor$launchIntent$1.L$2;
                    intent = (Intent) actionIntentExecutor$launchIntent$1.L$1;
                    actionIntentExecutor = (ActionIntentExecutor) actionIntentExecutor$launchIntent$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (i != UserHandle.myUserId()) {
                    CoroutineDispatcher coroutineDispatcher = actionIntentExecutor.mainDispatcher;
                    ActionIntentExecutor$launchIntent$2 actionIntentExecutor$launchIntent$2 = new ActionIntentExecutor$launchIntent$2(actionIntentExecutor, intent, bundle, null);
                    actionIntentExecutor$launchIntent$1.L$0 = null;
                    actionIntentExecutor$launchIntent$1.L$1 = null;
                    actionIntentExecutor$launchIntent$1.L$2 = null;
                    actionIntentExecutor$launchIntent$1.Z$0 = z;
                    actionIntentExecutor$launchIntent$1.label = 2;
                    if (BuildersKt.withContext(coroutineDispatcher, actionIntentExecutor$launchIntent$2, actionIntentExecutor$launchIntent$1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    actionIntentExecutor$launchIntent$1.L$0 = null;
                    actionIntentExecutor$launchIntent$1.L$1 = null;
                    actionIntentExecutor$launchIntent$1.L$2 = null;
                    actionIntentExecutor$launchIntent$1.Z$0 = z;
                    actionIntentExecutor$launchIntent$1.label = 3;
                    if (actionIntentExecutor.launchCrossProfileIntent(i, intent, bundle, actionIntentExecutor$launchIntent$1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                if (z) {
                }
                return Unit.INSTANCE;
            }
        }
        actionIntentExecutor$launchIntent$1 = new ActionIntentExecutor$launchIntent$1(this, continuation);
        Object obj2 = actionIntentExecutor$launchIntent$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i2 = actionIntentExecutor$launchIntent$1.label;
        if (i2 != 0) {
        }
        if (i != UserHandle.myUserId()) {
        }
        if (z) {
        }
        return Unit.INSTANCE;
    }

    public final void launchIntentAsync(Intent intent, Bundle bundle, int i, boolean z) {
        BuildersKt.launch$default(this.applicationScope, (CoroutineContext) null, (CoroutineStart) null, new ActionIntentExecutor$launchIntentAsync$1(this, intent, bundle, i, z, null), 3, (Object) null);
    }
}