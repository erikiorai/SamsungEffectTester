package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.applications.ServiceListing;
import com.android.systemui.Dumpable;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsListingControllerImpl.class */
public final class ControlsListingControllerImpl implements ControlsListingController, Dumpable {
    public static final Companion Companion = new Companion(null);
    public List<ControlsServiceInfo> availableServices;
    public final Executor backgroundExecutor;
    public final Set<ControlsListingController.ControlsListingCallback> callbacks;
    public final Context context;
    public int currentUserId;
    public ServiceListing serviceListing;
    public final Function1<Context, ServiceListing> serviceListingBuilder;
    public final ServiceListing.Callback serviceListingCallback;
    public AtomicInteger userChangeInProgress;
    public final UserTracker userTracker;

    /* renamed from: com.android.systemui.controls.management.ControlsListingControllerImpl$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsListingControllerImpl$1.class */
    public final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<Context, ServiceListing> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1, ControlsListingControllerImplKt.class, "createServiceListing", "createServiceListing(Landroid/content/Context;)Lcom/android/settingslib/applications/ServiceListing;", 1);
        }

        public final ServiceListing invoke(Context context) {
            ServiceListing createServiceListing;
            createServiceListing = ControlsListingControllerImplKt.createServiceListing(context);
            return createServiceListing;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsListingControllerImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlsListingControllerImpl(Context context, Executor executor, UserTracker userTracker, DumpManager dumpManager, FeatureFlags featureFlags) {
        this(context, executor, AnonymousClass1.INSTANCE, userTracker, dumpManager, featureFlags);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r10v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.Context, ? extends com.android.settingslib.applications.ServiceListing> */
    /* JADX WARN: Multi-variable type inference failed */
    @VisibleForTesting
    public ControlsListingControllerImpl(Context context, Executor executor, Function1<? super Context, ? extends ServiceListing> function1, UserTracker userTracker, DumpManager dumpManager, final FeatureFlags featureFlags) {
        this.context = context;
        this.backgroundExecutor = executor;
        this.serviceListingBuilder = function1;
        this.userTracker = userTracker;
        this.serviceListing = (ServiceListing) function1.invoke(context);
        this.callbacks = new LinkedHashSet();
        this.availableServices = CollectionsKt__CollectionsKt.emptyList();
        this.userChangeInProgress = new AtomicInteger(0);
        this.currentUserId = userTracker.getUserId();
        ServiceListing.Callback callback = new ServiceListing.Callback() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1
            @Override // com.android.settingslib.applications.ServiceListing.Callback
            public final void onServicesReloaded(List<ServiceInfo> list) {
                int size = list.size();
                Log.d("ControlsListingControllerImpl", "ServiceConfig reloaded, count: " + size);
                List<ServiceInfo> list2 = list;
                ControlsListingControllerImpl controlsListingControllerImpl = ControlsListingControllerImpl.this;
                final ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                for (ServiceInfo serviceInfo : list2) {
                    arrayList.add(new ControlsServiceInfo(ControlsListingControllerImpl.access$getUserTracker$p(controlsListingControllerImpl).getUserContext(), serviceInfo));
                }
                Executor access$getBackgroundExecutor$p = ControlsListingControllerImpl.access$getBackgroundExecutor$p(ControlsListingControllerImpl.this);
                final ControlsListingControllerImpl controlsListingControllerImpl2 = ControlsListingControllerImpl.this;
                final FeatureFlags featureFlags2 = featureFlags;
                access$getBackgroundExecutor$p.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (ControlsListingControllerImpl.access$getUserChangeInProgress$p(ControlsListingControllerImpl.this).get() > 0) {
                            return;
                        }
                        if (featureFlags2.isEnabled(Flags.USE_APP_PANELS)) {
                            boolean isEnabled = featureFlags2.isEnabled(Flags.APP_PANELS_ALL_APPS_ALLOWED);
                            for (ControlsServiceInfo controlsServiceInfo : arrayList) {
                                controlsServiceInfo.resolvePanelActivity(isEnabled);
                            }
                        }
                        if (Intrinsics.areEqual(arrayList, ControlsListingControllerImpl.access$getAvailableServices$p(ControlsListingControllerImpl.this))) {
                            return;
                        }
                        ControlsListingControllerImpl.access$setAvailableServices$p(ControlsListingControllerImpl.this, arrayList);
                        Set<ControlsListingController.ControlsListingCallback> access$getCallbacks$p = ControlsListingControllerImpl.access$getCallbacks$p(ControlsListingControllerImpl.this);
                        ControlsListingControllerImpl controlsListingControllerImpl3 = ControlsListingControllerImpl.this;
                        for (ControlsListingController.ControlsListingCallback controlsListingCallback : access$getCallbacks$p) {
                            controlsListingCallback.onServicesUpdated(controlsListingControllerImpl3.getCurrentServices());
                        }
                    }
                });
            }
        };
        this.serviceListingCallback = callback;
        Log.d("ControlsListingControllerImpl", "Initializing");
        DumpManager.registerDumpable$default(dumpManager, "ControlsListingControllerImpl", this, null, 4, null);
        this.serviceListing.addCallback(callback);
        this.serviceListing.setListening(true);
        this.serviceListing.reload();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.1.run():void] */
    public static final /* synthetic */ List access$getAvailableServices$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.availableServices;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.onServicesReloaded(java.util.List<android.content.pm.ServiceInfo>):void] */
    public static final /* synthetic */ Executor access$getBackgroundExecutor$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.backgroundExecutor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$addCallback$1.run():void, com.android.systemui.controls.management.ControlsListingControllerImpl$removeCallback$1.run():void, com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.1.run():void] */
    public static final /* synthetic */ Set access$getCallbacks$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.callbacks;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1.run():void] */
    public static final /* synthetic */ Context access$getContext$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.context;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1.run():void] */
    public static final /* synthetic */ ServiceListing access$getServiceListing$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.serviceListing;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1.run():void] */
    public static final /* synthetic */ Function1 access$getServiceListingBuilder$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.serviceListingBuilder;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1.run():void] */
    public static final /* synthetic */ ServiceListing.Callback access$getServiceListingCallback$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.serviceListingCallback;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$addCallback$1.run():void, com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1.run():void, com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.1.run():void] */
    public static final /* synthetic */ AtomicInteger access$getUserChangeInProgress$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.userChangeInProgress;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.onServicesReloaded(java.util.List<android.content.pm.ServiceInfo>):void] */
    public static final /* synthetic */ UserTracker access$getUserTracker$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.userTracker;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.1.run():void] */
    public static final /* synthetic */ void access$setAvailableServices$p(ControlsListingControllerImpl controlsListingControllerImpl, List list) {
        controlsListingControllerImpl.availableServices = list;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1.run():void] */
    public static final /* synthetic */ void access$setCurrentUserId$p(ControlsListingControllerImpl controlsListingControllerImpl, int i) {
        controlsListingControllerImpl.currentUserId = i;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1.run():void] */
    public static final /* synthetic */ void access$setServiceListing$p(ControlsListingControllerImpl controlsListingControllerImpl, ServiceListing serviceListing) {
        controlsListingControllerImpl.serviceListing = serviceListing;
    }

    public void addCallback(final ControlsListingController.ControlsListingCallback controlsListingCallback) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$addCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                if (ControlsListingControllerImpl.access$getUserChangeInProgress$p(ControlsListingControllerImpl.this).get() > 0) {
                    ControlsListingControllerImpl.this.addCallback(controlsListingCallback);
                    return;
                }
                List<ControlsServiceInfo> currentServices = ControlsListingControllerImpl.this.getCurrentServices();
                int size = currentServices.size();
                Log.d("ControlsListingControllerImpl", "Subscribing callback, service count: " + size);
                ControlsListingControllerImpl.access$getCallbacks$p(ControlsListingControllerImpl.this).add(controlsListingCallback);
                controlsListingCallback.onServicesUpdated(currentServices);
            }
        });
    }

    public void changeUser(final UserHandle userHandle) {
        this.userChangeInProgress.incrementAndGet();
        this.serviceListing.setListening(false);
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1
            @Override // java.lang.Runnable
            public final void run() {
                if (ControlsListingControllerImpl.access$getUserChangeInProgress$p(ControlsListingControllerImpl.this).decrementAndGet() == 0) {
                    ControlsListingControllerImpl.access$setCurrentUserId$p(ControlsListingControllerImpl.this, userHandle.getIdentifier());
                    Context createContextAsUser = ControlsListingControllerImpl.access$getContext$p(ControlsListingControllerImpl.this).createContextAsUser(userHandle, 0);
                    ControlsListingControllerImpl controlsListingControllerImpl = ControlsListingControllerImpl.this;
                    ControlsListingControllerImpl.access$setServiceListing$p(controlsListingControllerImpl, (ServiceListing) ControlsListingControllerImpl.access$getServiceListingBuilder$p(controlsListingControllerImpl).invoke(createContextAsUser));
                    ControlsListingControllerImpl.access$getServiceListing$p(ControlsListingControllerImpl.this).addCallback(ControlsListingControllerImpl.access$getServiceListingCallback$p(ControlsListingControllerImpl.this));
                    ControlsListingControllerImpl.access$getServiceListing$p(ControlsListingControllerImpl.this).setListening(true);
                    ControlsListingControllerImpl.access$getServiceListing$p(ControlsListingControllerImpl.this).reload();
                }
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ControlsListingController:");
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        if (asIndenting instanceof IndentingPrintWriter) {
            asIndenting.increaseIndent();
        }
        Set<ControlsListingController.ControlsListingCallback> set = this.callbacks;
        asIndenting.println("Callbacks: " + set);
        List<ControlsServiceInfo> currentServices = getCurrentServices();
        asIndenting.println("Services: " + currentServices);
        asIndenting.decreaseIndent();
    }

    @Override // com.android.systemui.controls.management.ControlsListingController
    public CharSequence getAppLabel(ComponentName componentName) {
        Object obj;
        Iterator<T> it = this.availableServices.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((ControlsServiceInfo) next).componentName, componentName)) {
                obj = next;
                break;
            }
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
        CharSequence charSequence = null;
        if (controlsServiceInfo != null) {
            charSequence = controlsServiceInfo.loadLabel();
        }
        return charSequence;
    }

    @Override // com.android.systemui.controls.management.ControlsListingController
    public List<ControlsServiceInfo> getCurrentServices() {
        List<ControlsServiceInfo> list = this.availableServices;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (ControlsServiceInfo controlsServiceInfo : list) {
            arrayList.add(controlsServiceInfo.copy());
        }
        return arrayList;
    }

    public int getCurrentUserId() {
        return this.currentUserId;
    }

    public void removeCallback(final ControlsListingController.ControlsListingCallback controlsListingCallback) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$removeCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                Log.d("ControlsListingControllerImpl", "Unsubscribing callback");
                ControlsListingControllerImpl.access$getCallbacks$p(ControlsListingControllerImpl.this).remove(controlsListingCallback);
            }
        });
    }
}