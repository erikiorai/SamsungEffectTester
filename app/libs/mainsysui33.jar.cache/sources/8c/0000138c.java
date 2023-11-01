package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.controls.Control;
import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.controls.controller.ControlsBindingController;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@VisibleForTesting
/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl.class */
public class ControlsBindingControllerImpl implements ControlsBindingController {
    public static final Companion Companion = new Companion(null);
    public static final ControlsBindingControllerImpl$Companion$emptyCallback$1 emptyCallback = new ControlsBindingController.LoadCallback() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$Companion$emptyCallback$1
        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(List<? extends Control> list) {
            accept2((List<Control>) list);
        }

        /* renamed from: accept  reason: avoid collision after fix types in other method */
        public void accept2(List<Control> list) {
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingController.LoadCallback
        public void error(String str) {
        }
    };
    public final ControlsBindingControllerImpl$actionCallbackService$1 actionCallbackService = new IControlsActionCallback.Stub() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$actionCallbackService$1
        public void accept(IBinder iBinder, String str, int i) {
            ControlsBindingControllerImpl.access$getBackgroundExecutor$p(ControlsBindingControllerImpl.this).execute(new ControlsBindingControllerImpl.OnActionResponseRunnable(iBinder, str, i));
        }
    };
    public final DelayableExecutor backgroundExecutor;
    public final Context context;
    public ControlsProviderLifecycleManager currentProvider;
    public UserHandle currentUser;
    public final Lazy<ControlsController> lazyController;
    public LoadSubscriber loadSubscriber;
    public StatefulControlSubscriber statefulControlSubscriber;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl$CallbackRunnable.class */
    public abstract class CallbackRunnable implements Runnable {
        public final ControlsProviderLifecycleManager provider;
        public final IBinder token;

        public CallbackRunnable(IBinder iBinder) {
            ControlsBindingControllerImpl.this = r4;
            this.token = iBinder;
            this.provider = r4.currentProvider;
        }

        public abstract void doRun();

        public final ControlsProviderLifecycleManager getProvider() {
            return this.provider;
        }

        @Override // java.lang.Runnable
        public void run() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager == null) {
                Log.e("ControlsBindingControllerImpl", "No current provider set");
            } else if (!Intrinsics.areEqual(controlsProviderLifecycleManager.getUser(), ControlsBindingControllerImpl.this.currentUser)) {
                UserHandle user = this.provider.getUser();
                Log.e("ControlsBindingControllerImpl", "User " + user + " is not current user");
            } else if (Intrinsics.areEqual(this.token, this.provider.getToken())) {
                doRun();
            } else {
                IBinder iBinder = this.token;
                Log.e("ControlsBindingControllerImpl", "Provider for token:" + iBinder + " does not exist anymore");
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl$LoadSubscriber.class */
    public final class LoadSubscriber extends IControlsSubscriber.Stub {
        public Function0<Unit> _loadCancelInternal;
        public ControlsBindingController.LoadCallback callback;
        public final long requestLimit;
        public IControlsSubscription subscription;
        public final ArrayList<Control> loadedControls = new ArrayList<>();
        public AtomicBoolean isTerminated = new AtomicBoolean(false);

        public LoadSubscriber(ControlsBindingController.LoadCallback loadCallback, long j) {
            ControlsBindingControllerImpl.this = r6;
            this.callback = loadCallback;
            this.requestLimit = j;
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onNext$1.run():void, com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1.invoke():void] */
        public static final /* synthetic */ IControlsSubscription access$getSubscription$p(LoadSubscriber loadSubscriber) {
            return loadSubscriber.subscription;
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1.run():void] */
        public static final /* synthetic */ Function0 access$get_loadCancelInternal$p(LoadSubscriber loadSubscriber) {
            return loadSubscriber._loadCancelInternal;
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$maybeTerminateAndRun$2.run():void, com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onNext$1.run():void] */
        public static final /* synthetic */ AtomicBoolean access$isTerminated$p(LoadSubscriber loadSubscriber) {
            return loadSubscriber.isTerminated;
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onNext$1.run():void] */
        public static final /* synthetic */ void access$maybeTerminateAndRun(LoadSubscriber loadSubscriber, Runnable runnable) {
            loadSubscriber.maybeTerminateAndRun(runnable);
        }

        public final ControlsBindingController.LoadCallback getCallback() {
            return this.callback;
        }

        public final ArrayList<Control> getLoadedControls() {
            return this.loadedControls;
        }

        public final long getRequestLimit() {
            return this.requestLimit;
        }

        public final Runnable loadCancel() {
            return new Runnable() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1
                @Override // java.lang.Runnable
                public final void run() {
                    Function0 access$get_loadCancelInternal$p = ControlsBindingControllerImpl.LoadSubscriber.access$get_loadCancelInternal$p(ControlsBindingControllerImpl.LoadSubscriber.this);
                    if (access$get_loadCancelInternal$p != null) {
                        Log.d("ControlsBindingControllerImpl", "Canceling loadSubscribtion");
                        access$get_loadCancelInternal$p.invoke();
                    }
                }
            };
        }

        public final void maybeTerminateAndRun(final Runnable runnable) {
            if (this.isTerminated.get()) {
                return;
            }
            this._loadCancelInternal = new Function0<Unit>() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$maybeTerminateAndRun$1
                public /* bridge */ /* synthetic */ Object invoke() {
                    m1801invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1801invoke() {
                }
            };
            this.callback = ControlsBindingControllerImpl.emptyCallback;
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsBindingControllerImpl.this.currentProvider;
            if (controlsProviderLifecycleManager != null) {
                controlsProviderLifecycleManager.cancelLoadTimeout();
            }
            ControlsBindingControllerImpl.this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$maybeTerminateAndRun$2
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsBindingControllerImpl.LoadSubscriber.access$isTerminated$p(ControlsBindingControllerImpl.LoadSubscriber.this).compareAndSet(false, true);
                    runnable.run();
                }
            });
        }

        public void onComplete(IBinder iBinder) {
            maybeTerminateAndRun(new OnLoadRunnable(iBinder, this.loadedControls, this.callback));
        }

        public void onError(IBinder iBinder, String str) {
            maybeTerminateAndRun(new OnLoadErrorRunnable(iBinder, str, this.callback));
        }

        public void onNext(final IBinder iBinder, final Control control) {
            DelayableExecutor delayableExecutor = ControlsBindingControllerImpl.this.backgroundExecutor;
            final ControlsBindingControllerImpl controlsBindingControllerImpl = ControlsBindingControllerImpl.this;
            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onNext$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (ControlsBindingControllerImpl.LoadSubscriber.access$isTerminated$p(ControlsBindingControllerImpl.LoadSubscriber.this).get()) {
                        return;
                    }
                    ControlsBindingControllerImpl.LoadSubscriber.this.getLoadedControls().add(control);
                    if (ControlsBindingControllerImpl.LoadSubscriber.this.getLoadedControls().size() >= ControlsBindingControllerImpl.LoadSubscriber.this.getRequestLimit()) {
                        ControlsBindingControllerImpl.LoadSubscriber loadSubscriber = ControlsBindingControllerImpl.LoadSubscriber.this;
                        ControlsBindingControllerImpl controlsBindingControllerImpl2 = controlsBindingControllerImpl;
                        IBinder iBinder2 = iBinder;
                        ArrayList<Control> loadedControls = loadSubscriber.getLoadedControls();
                        IControlsSubscription access$getSubscription$p = ControlsBindingControllerImpl.LoadSubscriber.access$getSubscription$p(ControlsBindingControllerImpl.LoadSubscriber.this);
                        IControlsSubscription iControlsSubscription = access$getSubscription$p;
                        if (access$getSubscription$p == null) {
                            iControlsSubscription = null;
                        }
                        ControlsBindingControllerImpl.LoadSubscriber.access$maybeTerminateAndRun(loadSubscriber, new ControlsBindingControllerImpl.OnCancelAndLoadRunnable(iBinder2, loadedControls, iControlsSubscription, ControlsBindingControllerImpl.LoadSubscriber.this.getCallback()));
                    }
                }
            });
        }

        public void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription) {
            this.subscription = iControlsSubscription;
            final ControlsBindingControllerImpl controlsBindingControllerImpl = ControlsBindingControllerImpl.this;
            this._loadCancelInternal = new Function0<Unit>() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m1802invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1802invoke() {
                    ControlsProviderLifecycleManager access$getCurrentProvider$p = ControlsBindingControllerImpl.access$getCurrentProvider$p(ControlsBindingControllerImpl.this);
                    if (access$getCurrentProvider$p != null) {
                        IControlsSubscription access$getSubscription$p = ControlsBindingControllerImpl.LoadSubscriber.access$getSubscription$p(this);
                        IControlsSubscription iControlsSubscription2 = access$getSubscription$p;
                        if (access$getSubscription$p == null) {
                            iControlsSubscription2 = null;
                        }
                        access$getCurrentProvider$p.cancelSubscription(iControlsSubscription2);
                    }
                }
            };
            ControlsBindingControllerImpl.this.backgroundExecutor.execute(new OnSubscribeRunnable(iBinder, iControlsSubscription, this.requestLimit));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl$OnActionResponseRunnable.class */
    public final class OnActionResponseRunnable extends CallbackRunnable {
        public final String controlId;
        public final int response;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnActionResponseRunnable(IBinder iBinder, String str, int i) {
            super(iBinder);
            ControlsBindingControllerImpl.this = r5;
            this.controlId = str;
            this.response = i;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public void doRun() {
            ControlsProviderLifecycleManager provider = getProvider();
            if (provider != null) {
                ((ControlsController) ControlsBindingControllerImpl.this.lazyController.get()).onActionResponse(provider.getComponentName(), this.controlId, this.response);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl$OnCancelAndLoadRunnable.class */
    public final class OnCancelAndLoadRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final List<Control> list;
        public final IControlsSubscription subscription;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnCancelAndLoadRunnable(IBinder iBinder, List<Control> list, IControlsSubscription iControlsSubscription, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            ControlsBindingControllerImpl.this = r5;
            this.list = list;
            this.subscription = iControlsSubscription;
            this.callback = loadCallback;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Canceling and loading controls");
            ControlsProviderLifecycleManager provider = getProvider();
            if (provider != null) {
                provider.cancelSubscription(this.subscription);
            }
            this.callback.accept(this.list);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl$OnLoadErrorRunnable.class */
    public final class OnLoadErrorRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final String error;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnLoadErrorRunnable(IBinder iBinder, String str, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            ControlsBindingControllerImpl.this = r5;
            this.error = str;
            this.callback = loadCallback;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public void doRun() {
            this.callback.error(this.error);
            ControlsProviderLifecycleManager provider = getProvider();
            if (provider != null) {
                ComponentName componentName = provider.getComponentName();
                String str = this.error;
                Log.e("ControlsBindingControllerImpl", "onError receive from '" + componentName + "': " + str);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl$OnLoadRunnable.class */
    public final class OnLoadRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final List<Control> list;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnLoadRunnable(IBinder iBinder, List<Control> list, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            ControlsBindingControllerImpl.this = r5;
            this.list = list;
            this.callback = loadCallback;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Complete and loading controls");
            this.callback.accept(this.list);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingControllerImpl$OnSubscribeRunnable.class */
    public final class OnSubscribeRunnable extends CallbackRunnable {
        public final long requestLimit;
        public final IControlsSubscription subscription;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnSubscribeRunnable(IBinder iBinder, IControlsSubscription iControlsSubscription, long j) {
            super(iBinder);
            ControlsBindingControllerImpl.this = r5;
            this.subscription = iControlsSubscription;
            this.requestLimit = j;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Starting subscription");
            ControlsProviderLifecycleManager provider = getProvider();
            if (provider != null) {
                provider.startSubscription(this.subscription, this.requestLimit);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$actionCallbackService$1] */
    public ControlsBindingControllerImpl(Context context, DelayableExecutor delayableExecutor, Lazy<ControlsController> lazy, UserTracker userTracker) {
        this.context = context;
        this.backgroundExecutor = delayableExecutor;
        this.lazyController = lazy;
        this.currentUser = userTracker.getUserHandle();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$actionCallbackService$1.accept(android.os.IBinder, java.lang.String, int):void] */
    public static final /* synthetic */ DelayableExecutor access$getBackgroundExecutor$p(ControlsBindingControllerImpl controlsBindingControllerImpl) {
        return controlsBindingControllerImpl.backgroundExecutor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1.invoke():void, com.android.systemui.controls.controller.ControlsBindingControllerImpl$onComponentRemoved$1.run():void] */
    public static final /* synthetic */ ControlsProviderLifecycleManager access$getCurrentProvider$p(ControlsBindingControllerImpl controlsBindingControllerImpl) {
        return controlsBindingControllerImpl.currentProvider;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$onComponentRemoved$1.run():void] */
    public static final /* synthetic */ void access$unbind(ControlsBindingControllerImpl controlsBindingControllerImpl) {
        controlsBindingControllerImpl.unbind();
    }

    @Override // com.android.systemui.controls.controller.ControlsBindingController
    public void action(ComponentName componentName, ControlInfo controlInfo, ControlAction controlAction) {
        if (this.statefulControlSubscriber == null) {
            Log.w("ControlsBindingControllerImpl", "No actions can occur outside of an active subscription. Ignoring.");
        } else {
            retrieveLifecycleManager(componentName).maybeBindAndSendAction(controlInfo.getControlId(), controlAction);
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsBindingController
    public Runnable bindAndLoad(ComponentName componentName, ControlsBindingController.LoadCallback loadCallback) {
        LoadSubscriber loadSubscriber = this.loadSubscriber;
        if (loadSubscriber != null) {
            loadSubscriber.loadCancel();
        }
        LoadSubscriber loadSubscriber2 = new LoadSubscriber(loadCallback, 100000L);
        this.loadSubscriber = loadSubscriber2;
        retrieveLifecycleManager(componentName).maybeBindAndLoad(loadSubscriber2);
        return loadSubscriber2.loadCancel();
    }

    @Override // com.android.systemui.controls.controller.ControlsBindingController
    public void bindAndLoadSuggested(ComponentName componentName, ControlsBindingController.LoadCallback loadCallback) {
        LoadSubscriber loadSubscriber = this.loadSubscriber;
        if (loadSubscriber != null) {
            loadSubscriber.loadCancel();
        }
        LoadSubscriber loadSubscriber2 = new LoadSubscriber(loadCallback, 36L);
        this.loadSubscriber = loadSubscriber2;
        retrieveLifecycleManager(componentName).maybeBindAndLoadSuggested(loadSubscriber2);
    }

    public void changeUser(UserHandle userHandle) {
        if (Intrinsics.areEqual(userHandle, this.currentUser)) {
            return;
        }
        unbind();
        this.currentUser = userHandle;
    }

    @VisibleForTesting
    public ControlsProviderLifecycleManager createProviderManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core(ComponentName componentName) {
        return new ControlsProviderLifecycleManager(this.context, this.backgroundExecutor, this.actionCallbackService, this.currentUser, componentName);
    }

    @Override // com.android.systemui.controls.controller.ControlsBindingController
    public void onComponentRemoved(final ComponentName componentName) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$onComponentRemoved$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsProviderLifecycleManager access$getCurrentProvider$p = ControlsBindingControllerImpl.access$getCurrentProvider$p(ControlsBindingControllerImpl.this);
                if (access$getCurrentProvider$p != null) {
                    ComponentName componentName2 = componentName;
                    ControlsBindingControllerImpl controlsBindingControllerImpl = ControlsBindingControllerImpl.this;
                    if (Intrinsics.areEqual(access$getCurrentProvider$p.getComponentName(), componentName2)) {
                        ControlsBindingControllerImpl.access$unbind(controlsBindingControllerImpl);
                    }
                }
            }
        });
    }

    public final ControlsProviderLifecycleManager retrieveLifecycleManager(ComponentName componentName) {
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.currentProvider;
        if (controlsProviderLifecycleManager != null) {
            if (!Intrinsics.areEqual(controlsProviderLifecycleManager != null ? controlsProviderLifecycleManager.getComponentName() : null, componentName)) {
                unbind();
            }
        }
        ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = this.currentProvider;
        ControlsProviderLifecycleManager controlsProviderLifecycleManager3 = controlsProviderLifecycleManager2;
        if (controlsProviderLifecycleManager2 == null) {
            controlsProviderLifecycleManager3 = createProviderManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core(componentName);
        }
        this.currentProvider = controlsProviderLifecycleManager3;
        return controlsProviderLifecycleManager3;
    }

    @Override // com.android.systemui.controls.controller.ControlsBindingController
    public void subscribe(StructureInfo structureInfo) {
        unsubscribe();
        ControlsProviderLifecycleManager retrieveLifecycleManager = retrieveLifecycleManager(structureInfo.getComponentName());
        StatefulControlSubscriber statefulControlSubscriber = new StatefulControlSubscriber((ControlsController) this.lazyController.get(), retrieveLifecycleManager, this.backgroundExecutor, 100000L);
        this.statefulControlSubscriber = statefulControlSubscriber;
        List<ControlInfo> controls = structureInfo.getControls();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controls, 10));
        for (ControlInfo controlInfo : controls) {
            arrayList.add(controlInfo.getControlId());
        }
        retrieveLifecycleManager.maybeBindAndSubscribe(arrayList, statefulControlSubscriber);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("  ControlsBindingController:\n");
        UserHandle userHandle = this.currentUser;
        sb.append("    currentUser=" + userHandle + "\n");
        StatefulControlSubscriber statefulControlSubscriber = this.statefulControlSubscriber;
        sb.append("    StatefulControlSubscriber=" + statefulControlSubscriber);
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.currentProvider;
        sb.append("    Providers=" + controlsProviderLifecycleManager + "\n");
        return sb.toString();
    }

    public final void unbind() {
        unsubscribe();
        LoadSubscriber loadSubscriber = this.loadSubscriber;
        if (loadSubscriber != null) {
            loadSubscriber.loadCancel();
        }
        this.loadSubscriber = null;
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.currentProvider;
        if (controlsProviderLifecycleManager != null) {
            controlsProviderLifecycleManager.unbindService();
        }
        this.currentProvider = null;
    }

    @Override // com.android.systemui.controls.controller.ControlsBindingController
    public void unsubscribe() {
        StatefulControlSubscriber statefulControlSubscriber = this.statefulControlSubscriber;
        if (statefulControlSubscriber != null) {
            statefulControlSubscriber.cancel();
        }
        this.statefulControlSubscriber = null;
    }
}