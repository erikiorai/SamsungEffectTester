package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsProvider;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsProviderLifecycleManager.class */
public final class ControlsProviderLifecycleManager implements IBinder.DeathRecipient {
    public final String TAG;
    public final IControlsActionCallback.Stub actionCallbackService;
    public int bindTryCount;
    public final ComponentName componentName;
    public final Context context;
    public final DelayableExecutor executor;
    public final Intent intent;
    public Runnable onLoadCanceller;
    @GuardedBy({"queuedServiceMethods"})
    public final Set<ServiceMethod> queuedServiceMethods;
    public boolean requiresBound;
    public final ControlsProviderLifecycleManager$serviceConnection$1 serviceConnection;
    public final IBinder token;
    public final UserHandle user;
    public ServiceWrapper wrapper;
    public static final Companion Companion = new Companion(null);
    public static final int BIND_FLAGS = 67109121;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsProviderLifecycleManager$Action.class */
    public final class Action extends ServiceMethod {
        public final ControlAction action;
        public final String id;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Action(String str, ControlAction controlAction) {
            super();
            ControlsProviderLifecycleManager.this = r4;
            this.id = str;
            this.action = controlAction;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            String str = ControlsProviderLifecycleManager.this.TAG;
            ComponentName componentName = ControlsProviderLifecycleManager.this.getComponentName();
            String str2 = this.id;
            Log.d(str, "onAction " + componentName + " - " + str2);
            ServiceWrapper serviceWrapper = ControlsProviderLifecycleManager.this.wrapper;
            return serviceWrapper != null ? serviceWrapper.action(this.id, this.action, ControlsProviderLifecycleManager.this.actionCallbackService) : false;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsProviderLifecycleManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsProviderLifecycleManager$Load.class */
    public final class Load extends ServiceMethod {
        public final IControlsSubscriber.Stub subscriber;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Load(IControlsSubscriber.Stub stub) {
            super();
            ControlsProviderLifecycleManager.this = r4;
            this.subscriber = stub;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            String str = ControlsProviderLifecycleManager.this.TAG;
            ComponentName componentName = ControlsProviderLifecycleManager.this.getComponentName();
            Log.d(str, "load " + componentName);
            ServiceWrapper serviceWrapper = ControlsProviderLifecycleManager.this.wrapper;
            return serviceWrapper != null ? serviceWrapper.load(this.subscriber) : false;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsProviderLifecycleManager$ServiceMethod.class */
    public abstract class ServiceMethod {
        public ServiceMethod() {
            ControlsProviderLifecycleManager.this = r4;
        }

        public abstract boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core();

        public final void run() {
            if (callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                return;
            }
            ControlsProviderLifecycleManager.this.queueServiceMethod(this);
            ControlsProviderLifecycleManager.this.binderDied();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsProviderLifecycleManager$Subscribe.class */
    public final class Subscribe extends ServiceMethod {
        public final List<String> list;
        public final IControlsSubscriber subscriber;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Subscribe(List<String> list, IControlsSubscriber iControlsSubscriber) {
            super();
            ControlsProviderLifecycleManager.this = r4;
            this.list = list;
            this.subscriber = iControlsSubscriber;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            String str = ControlsProviderLifecycleManager.this.TAG;
            ComponentName componentName = ControlsProviderLifecycleManager.this.getComponentName();
            List<String> list = this.list;
            Log.d(str, "subscribe " + componentName + " - " + list);
            ServiceWrapper serviceWrapper = ControlsProviderLifecycleManager.this.wrapper;
            return serviceWrapper != null ? serviceWrapper.subscribe(this.list, this.subscriber) : false;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsProviderLifecycleManager$Suggest.class */
    public final class Suggest extends ServiceMethod {
        public final IControlsSubscriber.Stub subscriber;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Suggest(IControlsSubscriber.Stub stub) {
            super();
            ControlsProviderLifecycleManager.this = r4;
            this.subscriber = stub;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            String str = ControlsProviderLifecycleManager.this.TAG;
            ComponentName componentName = ControlsProviderLifecycleManager.this.getComponentName();
            Log.d(str, "suggest " + componentName);
            ServiceWrapper serviceWrapper = ControlsProviderLifecycleManager.this.wrapper;
            return serviceWrapper != null ? serviceWrapper.loadSuggested(this.subscriber) : false;
        }
    }

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.controls.controller.ControlsProviderLifecycleManager$serviceConnection$1] */
    public ControlsProviderLifecycleManager(Context context, DelayableExecutor delayableExecutor, IControlsActionCallback.Stub stub, UserHandle userHandle, ComponentName componentName) {
        this.context = context;
        this.executor = delayableExecutor;
        this.actionCallbackService = stub;
        this.user = userHandle;
        this.componentName = componentName;
        Binder binder = new Binder();
        this.token = binder;
        this.queuedServiceMethods = new ArraySet();
        this.TAG = ControlsProviderLifecycleManager.class.getSimpleName();
        Intent intent = new Intent();
        intent.setComponent(componentName);
        Bundle bundle = new Bundle();
        bundle.putBinder("CALLBACK_TOKEN", binder);
        Unit unit = Unit.INSTANCE;
        intent.putExtra("CALLBACK_BUNDLE", bundle);
        this.intent = intent;
        this.serviceConnection = new ServiceConnection() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$serviceConnection$1
            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName2) {
                Context context2;
                String str = ControlsProviderLifecycleManager.this.TAG;
                Log.d(str, "onNullBinding " + componentName2);
                ControlsProviderLifecycleManager.this.wrapper = null;
                context2 = ControlsProviderLifecycleManager.this.context;
                context2.unbindService(this);
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName2, IBinder iBinder) {
                String str = ControlsProviderLifecycleManager.this.TAG;
                Log.d(str, "onServiceConnected " + componentName2);
                ControlsProviderLifecycleManager.this.bindTryCount = 0;
                ControlsProviderLifecycleManager.this.wrapper = new ServiceWrapper(IControlsProvider.Stub.asInterface(iBinder));
                try {
                    iBinder.linkToDeath(ControlsProviderLifecycleManager.this, 0);
                } catch (RemoteException e) {
                }
                ControlsProviderLifecycleManager.this.handlePendingServiceMethods();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName2) {
                String str = ControlsProviderLifecycleManager.this.TAG;
                Log.d(str, "onServiceDisconnected " + componentName2);
                ControlsProviderLifecycleManager.this.wrapper = null;
                ControlsProviderLifecycleManager.this.bindService(false);
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoad$1.run():void, com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoadSuggested$1.run():void] */
    public static final /* synthetic */ String access$getTAG$p(ControlsProviderLifecycleManager controlsProviderLifecycleManager) {
        return controlsProviderLifecycleManager.TAG;
    }

    public final void bindService(final boolean z) {
        this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$bindService$1
            @Override // java.lang.Runnable
            public final void run() {
                Intent intent;
                Context context;
                ControlsProviderLifecycleManager$serviceConnection$1 controlsProviderLifecycleManager$serviceConnection$1;
                int i;
                Intent intent2;
                int i2;
                Context context2;
                Intent intent3;
                ControlsProviderLifecycleManager$serviceConnection$1 controlsProviderLifecycleManager$serviceConnection$12;
                int i3;
                Context context3;
                ControlsProviderLifecycleManager$serviceConnection$1 controlsProviderLifecycleManager$serviceConnection$13;
                ControlsProviderLifecycleManager.this.requiresBound = z;
                if (!z) {
                    String str = ControlsProviderLifecycleManager.this.TAG;
                    intent = ControlsProviderLifecycleManager.this.intent;
                    Log.d(str, "Unbinding service " + intent);
                    ControlsProviderLifecycleManager.this.bindTryCount = 0;
                    if (ControlsProviderLifecycleManager.this.wrapper != null) {
                        ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                        context = controlsProviderLifecycleManager.context;
                        controlsProviderLifecycleManager$serviceConnection$1 = controlsProviderLifecycleManager.serviceConnection;
                        context.unbindService(controlsProviderLifecycleManager$serviceConnection$1);
                    }
                    ControlsProviderLifecycleManager.this.wrapper = null;
                    return;
                }
                i = ControlsProviderLifecycleManager.this.bindTryCount;
                if (i != 5) {
                    String str2 = ControlsProviderLifecycleManager.this.TAG;
                    intent2 = ControlsProviderLifecycleManager.this.intent;
                    Log.d(str2, "Binding service " + intent2);
                    ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = ControlsProviderLifecycleManager.this;
                    i2 = controlsProviderLifecycleManager2.bindTryCount;
                    controlsProviderLifecycleManager2.bindTryCount = i2 + 1;
                    try {
                        context2 = ControlsProviderLifecycleManager.this.context;
                        intent3 = ControlsProviderLifecycleManager.this.intent;
                        controlsProviderLifecycleManager$serviceConnection$12 = ControlsProviderLifecycleManager.this.serviceConnection;
                        i3 = ControlsProviderLifecycleManager.BIND_FLAGS;
                        if (context2.bindServiceAsUser(intent3, controlsProviderLifecycleManager$serviceConnection$12, i3, ControlsProviderLifecycleManager.this.getUser())) {
                            return;
                        }
                        context3 = ControlsProviderLifecycleManager.this.context;
                        controlsProviderLifecycleManager$serviceConnection$13 = ControlsProviderLifecycleManager.this.serviceConnection;
                        context3.unbindService(controlsProviderLifecycleManager$serviceConnection$13);
                    } catch (SecurityException e) {
                        Log.e(ControlsProviderLifecycleManager.this.TAG, "Failed to bind to service", e);
                    }
                }
            }
        });
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        if (this.wrapper == null) {
            return;
        }
        this.wrapper = null;
        if (this.requiresBound) {
            Log.d(this.TAG, "binderDied");
        }
    }

    public final void cancelLoadTimeout() {
        Runnable runnable = this.onLoadCanceller;
        if (runnable != null) {
            runnable.run();
        }
        this.onLoadCanceller = null;
    }

    public final void cancelSubscription(IControlsSubscription iControlsSubscription) {
        String str = this.TAG;
        Log.d(str, "cancelSubscription: " + iControlsSubscription);
        ServiceWrapper serviceWrapper = this.wrapper;
        if (serviceWrapper != null) {
            serviceWrapper.cancel(iControlsSubscription);
        }
    }

    public final ComponentName getComponentName() {
        return this.componentName;
    }

    public final IBinder getToken() {
        return this.token;
    }

    public final UserHandle getUser() {
        return this.user;
    }

    public final void handlePendingServiceMethods() {
        ArraySet<ServiceMethod> arraySet;
        synchronized (this.queuedServiceMethods) {
            arraySet = new ArraySet(this.queuedServiceMethods);
            this.queuedServiceMethods.clear();
        }
        for (ServiceMethod serviceMethod : arraySet) {
            serviceMethod.run();
        }
    }

    public final void invokeOrQueue(ServiceMethod serviceMethod) {
        Unit unit;
        if (this.wrapper != null) {
            serviceMethod.run();
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            queueServiceMethod(serviceMethod);
            bindService(true);
        }
    }

    public final void maybeBindAndLoad(final IControlsSubscriber.Stub stub) {
        this.onLoadCanceller = this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoad$1
            @Override // java.lang.Runnable
            public final void run() {
                String access$getTAG$p = ControlsProviderLifecycleManager.access$getTAG$p(ControlsProviderLifecycleManager.this);
                ComponentName componentName = ControlsProviderLifecycleManager.this.getComponentName();
                Log.d(access$getTAG$p, "Timeout waiting onLoad for " + componentName);
                stub.onError(ControlsProviderLifecycleManager.this.getToken(), "Timeout waiting onLoad");
                ControlsProviderLifecycleManager.this.unbindService();
            }
        }, 20L, TimeUnit.SECONDS);
        invokeOrQueue(new Load(stub));
    }

    public final void maybeBindAndLoadSuggested(final IControlsSubscriber.Stub stub) {
        this.onLoadCanceller = this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoadSuggested$1
            @Override // java.lang.Runnable
            public final void run() {
                String access$getTAG$p = ControlsProviderLifecycleManager.access$getTAG$p(ControlsProviderLifecycleManager.this);
                ComponentName componentName = ControlsProviderLifecycleManager.this.getComponentName();
                Log.d(access$getTAG$p, "Timeout waiting onLoadSuggested for " + componentName);
                stub.onError(ControlsProviderLifecycleManager.this.getToken(), "Timeout waiting onLoadSuggested");
                ControlsProviderLifecycleManager.this.unbindService();
            }
        }, 20L, TimeUnit.SECONDS);
        invokeOrQueue(new Suggest(stub));
    }

    public final void maybeBindAndSendAction(String str, ControlAction controlAction) {
        invokeOrQueue(new Action(str, controlAction));
    }

    public final void maybeBindAndSubscribe(List<String> list, IControlsSubscriber iControlsSubscriber) {
        invokeOrQueue(new Subscribe(list, iControlsSubscriber));
    }

    public final void queueServiceMethod(ServiceMethod serviceMethod) {
        synchronized (this.queuedServiceMethods) {
            this.queuedServiceMethods.add(serviceMethod);
        }
    }

    public final void startSubscription(IControlsSubscription iControlsSubscription, long j) {
        String str = this.TAG;
        Log.d(str, "startSubscription: " + iControlsSubscription);
        ServiceWrapper serviceWrapper = this.wrapper;
        if (serviceWrapper != null) {
            serviceWrapper.request(iControlsSubscription, j);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ControlsProviderLifecycleManager(");
        ComponentName componentName = this.componentName;
        sb.append("component=" + componentName);
        UserHandle userHandle = this.user;
        sb.append(", user=" + userHandle);
        sb.append(")");
        return sb.toString();
    }

    public final void unbindService() {
        Runnable runnable = this.onLoadCanceller;
        if (runnable != null) {
            runnable.run();
        }
        this.onLoadCanceller = null;
        bindService(false);
    }
}