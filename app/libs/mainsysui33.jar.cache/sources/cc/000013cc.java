package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.os.IBinder;
import android.service.controls.Control;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.util.Log;
import com.android.systemui.util.concurrency.DelayableExecutor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/StatefulControlSubscriber.class */
public final class StatefulControlSubscriber extends IControlsSubscriber.Stub {
    public static final Companion Companion = new Companion(null);
    public final DelayableExecutor bgExecutor;
    public final ControlsController controller;
    public final ControlsProviderLifecycleManager provider;
    public final long requestLimit;
    public IControlsSubscription subscription;
    public boolean subscriptionOpen;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/StatefulControlSubscriber$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public StatefulControlSubscriber(ControlsController controlsController, ControlsProviderLifecycleManager controlsProviderLifecycleManager, DelayableExecutor delayableExecutor, long j) {
        this.controller = controlsController;
        this.provider = controlsProviderLifecycleManager;
        this.bgExecutor = delayableExecutor;
        this.requestLimit = j;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.StatefulControlSubscriber$cancel$1.run():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onComplete$1.invoke():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onError$1.invoke():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onSubscribe$1.invoke():void] */
    public static final /* synthetic */ ControlsProviderLifecycleManager access$getProvider$p(StatefulControlSubscriber statefulControlSubscriber) {
        return statefulControlSubscriber.provider;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.StatefulControlSubscriber$onSubscribe$1.invoke():void] */
    public static final /* synthetic */ long access$getRequestLimit$p(StatefulControlSubscriber statefulControlSubscriber) {
        return statefulControlSubscriber.requestLimit;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.StatefulControlSubscriber$cancel$1.run():void] */
    public static final /* synthetic */ IControlsSubscription access$getSubscription$p(StatefulControlSubscriber statefulControlSubscriber) {
        return statefulControlSubscriber.subscription;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.StatefulControlSubscriber$cancel$1.run():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onComplete$1.invoke():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onError$1.invoke():void] */
    public static final /* synthetic */ boolean access$getSubscriptionOpen$p(StatefulControlSubscriber statefulControlSubscriber) {
        return statefulControlSubscriber.subscriptionOpen;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.StatefulControlSubscriber$cancel$1.run():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onSubscribe$1.invoke():void] */
    public static final /* synthetic */ void access$setSubscription$p(StatefulControlSubscriber statefulControlSubscriber, IControlsSubscription iControlsSubscription) {
        statefulControlSubscriber.subscription = iControlsSubscription;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.StatefulControlSubscriber$cancel$1.run():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onComplete$1.invoke():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onError$1.invoke():void, com.android.systemui.controls.controller.StatefulControlSubscriber$onSubscribe$1.invoke():void] */
    public static final /* synthetic */ void access$setSubscriptionOpen$p(StatefulControlSubscriber statefulControlSubscriber, boolean z) {
        statefulControlSubscriber.subscriptionOpen = z;
    }

    public final void cancel() {
        if (this.subscriptionOpen) {
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$cancel$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (StatefulControlSubscriber.access$getSubscriptionOpen$p(StatefulControlSubscriber.this)) {
                        StatefulControlSubscriber.access$setSubscriptionOpen$p(StatefulControlSubscriber.this, false);
                        IControlsSubscription access$getSubscription$p = StatefulControlSubscriber.access$getSubscription$p(StatefulControlSubscriber.this);
                        if (access$getSubscription$p != null) {
                            StatefulControlSubscriber.access$getProvider$p(StatefulControlSubscriber.this).cancelSubscription(access$getSubscription$p);
                        }
                        StatefulControlSubscriber.access$setSubscription$p(StatefulControlSubscriber.this, null);
                    }
                }
            });
        }
    }

    public void onComplete(IBinder iBinder) {
        run(iBinder, new Function0<Unit>() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onComplete$1
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1811invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1811invoke() {
                if (StatefulControlSubscriber.access$getSubscriptionOpen$p(StatefulControlSubscriber.this)) {
                    StatefulControlSubscriber.access$setSubscriptionOpen$p(StatefulControlSubscriber.this, false);
                    ComponentName componentName = StatefulControlSubscriber.access$getProvider$p(StatefulControlSubscriber.this).getComponentName();
                    Log.i("StatefulControlSubscriber", "onComplete receive from '" + componentName + "'");
                }
            }
        });
    }

    public void onError(IBinder iBinder, final String str) {
        run(iBinder, new Function0<Unit>() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onError$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1812invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1812invoke() {
                if (StatefulControlSubscriber.access$getSubscriptionOpen$p(StatefulControlSubscriber.this)) {
                    StatefulControlSubscriber.access$setSubscriptionOpen$p(StatefulControlSubscriber.this, false);
                    ComponentName componentName = StatefulControlSubscriber.access$getProvider$p(StatefulControlSubscriber.this).getComponentName();
                    String str2 = str;
                    Log.e("StatefulControlSubscriber", "onError receive from '" + componentName + "': " + str2);
                }
            }
        });
    }

    public void onNext(final IBinder iBinder, final Control control) {
        run(iBinder, new Function0<Unit>() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onNext$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1813invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1813invoke() {
                boolean z;
                ControlsController controlsController;
                ControlsProviderLifecycleManager controlsProviderLifecycleManager;
                z = StatefulControlSubscriber.this.subscriptionOpen;
                if (z) {
                    controlsController = StatefulControlSubscriber.this.controller;
                    controlsProviderLifecycleManager = StatefulControlSubscriber.this.provider;
                    controlsController.refreshStatus(controlsProviderLifecycleManager.getComponentName(), control);
                    return;
                }
                IBinder iBinder2 = iBinder;
                Log.w("StatefulControlSubscriber", "Refresh outside of window for token:" + iBinder2);
            }
        });
    }

    public void onSubscribe(IBinder iBinder, final IControlsSubscription iControlsSubscription) {
        run(iBinder, new Function0<Unit>() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onSubscribe$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1814invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1814invoke() {
                StatefulControlSubscriber.access$setSubscriptionOpen$p(StatefulControlSubscriber.this, true);
                StatefulControlSubscriber.access$setSubscription$p(StatefulControlSubscriber.this, iControlsSubscription);
                StatefulControlSubscriber.access$getProvider$p(StatefulControlSubscriber.this).startSubscription(iControlsSubscription, StatefulControlSubscriber.access$getRequestLimit$p(StatefulControlSubscriber.this));
            }
        });
    }

    public final void run(IBinder iBinder, final Function0<Unit> function0) {
        if (Intrinsics.areEqual(this.provider.getToken(), iBinder)) {
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$run$1
                @Override // java.lang.Runnable
                public final void run() {
                    function0.invoke();
                }
            });
        }
    }
}