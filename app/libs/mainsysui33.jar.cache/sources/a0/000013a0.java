package com.android.systemui.controls.controller;

import android.app.PendingIntent;
import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.UserHandle;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsBindingController;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsControllerImpl.class */
public final class ControlsControllerImpl implements Dumpable, ControlsController {
    public static final Companion Companion = new Companion(null);
    public AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper;
    public final ControlsBindingController bindingController;
    public final Context context;
    public UserHandle currentUser;
    public final DelayableExecutor executor;
    public final ControlsControllerImpl$listingCallback$1 listingCallback;
    public final ControlsListingController listingController;
    public final ControlsFavoritePersistenceWrapper persistenceWrapper;
    public final BroadcastReceiver restoreFinishedReceiver;
    public final List<Consumer<Boolean>> seedingCallbacks = new ArrayList();
    public boolean seedingInProgress;
    public final ContentObserver settingObserver;
    public final ControlsUiController uiController;
    public boolean userChanging;
    public final UserFileManager userFileManager;
    public UserStructure userStructure;
    public final UserTracker userTracker;
    public final ControlsControllerImpl$userTrackerCallback$1 userTrackerCallback;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsControllerImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r0v16, types: [com.android.systemui.controls.controller.ControlsControllerImpl$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    /* JADX WARN: Type inference failed for: r0v21, types: [java.lang.Object, com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1] */
    public ControlsControllerImpl(Context context, DelayableExecutor delayableExecutor, ControlsUiController controlsUiController, ControlsBindingController controlsBindingController, ControlsListingController controlsListingController, UserFileManager userFileManager, UserTracker userTracker, Optional<ControlsFavoritePersistenceWrapper> optional, DumpManager dumpManager) {
        this.context = context;
        this.executor = delayableExecutor;
        this.uiController = controlsUiController;
        this.bindingController = controlsBindingController;
        this.listingController = controlsListingController;
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.userChanging = true;
        UserHandle userHandle = userTracker.getUserHandle();
        this.currentUser = userHandle;
        this.userStructure = new UserStructure(context, userHandle, userFileManager);
        this.persistenceWrapper = optional.orElseGet(new Supplier() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl.1
            {
                ControlsControllerImpl.this = this;
            }

            @Override // java.util.function.Supplier
            public final ControlsFavoritePersistenceWrapper get() {
                return new ControlsFavoritePersistenceWrapper(ControlsControllerImpl.this.userStructure.getFile(), ControlsControllerImpl.this.executor, new BackupManager(ControlsControllerImpl.this.userStructure.getUserContext()));
            }
        });
        this.auxiliaryPersistenceWrapper = new AuxiliaryPersistenceWrapper(this.userStructure.getAuxiliaryFile(), (Executor) delayableExecutor);
        ?? r0 = new UserTracker.Callback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$userTrackerCallback$1
            public void onUserChanged(int i, Context context2) {
                ControlsControllerImpl.access$setUserChanging$p(ControlsControllerImpl.this, true);
                UserHandle of = UserHandle.of(i);
                if (Intrinsics.areEqual(ControlsControllerImpl.access$getCurrentUser$p(ControlsControllerImpl.this), of)) {
                    ControlsControllerImpl.access$setUserChanging$p(ControlsControllerImpl.this, false);
                } else {
                    ControlsControllerImpl.access$setValuesForUser(ControlsControllerImpl.this, of);
                }
            }
        };
        this.userTrackerCallback = r0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getIntExtra("android.intent.extra.USER_ID", -10000) == ControlsControllerImpl.this.getCurrentUserId()) {
                    DelayableExecutor access$getExecutor$p = ControlsControllerImpl.access$getExecutor$p(ControlsControllerImpl.this);
                    final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                    access$getExecutor$p.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1$onReceive$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.d("ControlsControllerImpl", "Restore finished, storing auxiliary favorites");
                            ControlsControllerImpl.this.getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core().initialize();
                            ControlsControllerImpl.access$getPersistenceWrapper$p(ControlsControllerImpl.this).storeFavorites(ControlsControllerImpl.this.getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getFavorites());
                            ControlsControllerImpl.access$resetFavorites(ControlsControllerImpl.this);
                        }
                    });
                }
            }
        };
        this.restoreFinishedReceiver = broadcastReceiver;
        this.settingObserver = new ContentObserver() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$settingObserver$1
            {
                super(null);
            }

            public void onChange(boolean z, Collection<? extends Uri> collection, int i, int i2) {
                if (ControlsControllerImpl.access$getUserChanging$p(ControlsControllerImpl.this) || i2 != ControlsControllerImpl.this.getCurrentUserId()) {
                    return;
                }
                ControlsControllerImpl.access$resetFavorites(ControlsControllerImpl.this);
            }
        };
        ?? r02 = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public void onServicesUpdated(final List<ControlsServiceInfo> list) {
                DelayableExecutor access$getExecutor$p = ControlsControllerImpl.access$getExecutor$p(ControlsControllerImpl.this);
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                access$getExecutor$p.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1$onServicesUpdated$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        List<ControlsServiceInfo> list2 = list;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                        for (ControlsServiceInfo controlsServiceInfo : list2) {
                            arrayList.add(controlsServiceInfo.componentName);
                        }
                        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
                        List<StructureInfo> allStructures = Favorites.INSTANCE.getAllStructures();
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(allStructures, 10));
                        for (StructureInfo structureInfo : allStructures) {
                            arrayList2.add(structureInfo.getComponentName());
                        }
                        Set set2 = CollectionsKt___CollectionsKt.toSet(arrayList2);
                        boolean z = false;
                        SharedPreferences sharedPreferences = ControlsControllerImpl.access$getUserFileManager$p(controlsControllerImpl).getSharedPreferences("controls_prefs", 0, ControlsControllerImpl.access$getUserTracker$p(controlsControllerImpl).getUserId());
                        Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", new LinkedHashSet());
                        Set<ComponentName> set3 = set;
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set3, 10));
                        for (ComponentName componentName : set3) {
                            arrayList3.add(componentName.getPackageName());
                        }
                        sharedPreferences.edit().putStringSet("SeedingCompleted", CollectionsKt___CollectionsKt.intersect(stringSet, arrayList3)).apply();
                        Set set4 = set2;
                        Set<ComponentName> subtract = CollectionsKt___CollectionsKt.subtract(set4, set3);
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        for (ComponentName componentName2 : subtract) {
                            Favorites.INSTANCE.removeStructures(componentName2);
                            ControlsControllerImpl.access$getBindingController$p(controlsControllerImpl2).onComponentRemoved(componentName2);
                            z = true;
                        }
                        boolean z2 = z;
                        if (!controlsControllerImpl.getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getFavorites().isEmpty()) {
                            Set<ComponentName> subtract2 = CollectionsKt___CollectionsKt.subtract(set3, set4);
                            ControlsControllerImpl controlsControllerImpl3 = controlsControllerImpl;
                            for (ComponentName componentName3 : subtract2) {
                                List<StructureInfo> cachedFavoritesAndRemoveFor = controlsControllerImpl3.getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getCachedFavoritesAndRemoveFor(componentName3);
                                if (!cachedFavoritesAndRemoveFor.isEmpty()) {
                                    for (StructureInfo structureInfo2 : cachedFavoritesAndRemoveFor) {
                                        Favorites.INSTANCE.replaceControls(structureInfo2);
                                    }
                                    z = true;
                                }
                            }
                            Set intersect = CollectionsKt___CollectionsKt.intersect(set3, set4);
                            ControlsControllerImpl controlsControllerImpl4 = controlsControllerImpl;
                            Iterator it = intersect.iterator();
                            while (true) {
                                z2 = z;
                                if (!it.hasNext()) {
                                    break;
                                }
                                controlsControllerImpl4.getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getCachedFavoritesAndRemoveFor((ComponentName) it.next());
                            }
                        }
                        if (z2) {
                            Log.d("ControlsControllerImpl", "Detected change in available services, storing updated favorites");
                            ControlsControllerImpl.access$getPersistenceWrapper$p(controlsControllerImpl).storeFavorites(Favorites.INSTANCE.getAllStructures());
                        }
                    }
                });
            }
        };
        this.listingCallback = r02;
        DumpManager.registerDumpable$default(dumpManager, ControlsControllerImpl.class.getName(), this, null, 4, null);
        resetFavorites();
        this.userChanging = false;
        userTracker.addCallback((UserTracker.Callback) r0, (Executor) delayableExecutor);
        context.registerReceiver(broadcastReceiver, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), "com.android.systemui.permission.SELF", null, 4);
        controlsListingController.addCallback((Object) r02);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2$error$1.run():void] */
    public static final /* synthetic */ ControlStatus access$createRemovedStatus(ControlsControllerImpl controlsControllerImpl, ComponentName componentName, ControlInfo controlInfo, CharSequence charSequence, boolean z) {
        return controlsControllerImpl.createRemovedStatus(componentName, controlInfo, charSequence, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2$accept$1.run():void] */
    public static final /* synthetic */ Set access$findRemoved(ControlsControllerImpl controlsControllerImpl, Set set, List list) {
        return controlsControllerImpl.findRemoved(set, list);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1$onServicesUpdated$1.run():void] */
    public static final /* synthetic */ ControlsBindingController access$getBindingController$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.bindingController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ UserHandle access$getCurrentUser$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.currentUser;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1.onServicesUpdated(java.util.List<com.android.systemui.controls.ControlsServiceInfo>):void, com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2.accept(java.util.List<android.service.controls.Control>):void, com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2.error(java.lang.String):void, com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1.onReceive(android.content.Context, android.content.Intent):void, com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1.accept(java.util.List<android.service.controls.Control>):void, com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1.error(java.lang.String):void] */
    public static final /* synthetic */ DelayableExecutor access$getExecutor$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.executor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$addFavorite$1.run():void, com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1$onServicesUpdated$1.run():void, com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2$accept$1.run():void, com.android.systemui.controls.controller.ControlsControllerImpl$refreshStatus$1.run():void, com.android.systemui.controls.controller.ControlsControllerImpl$replaceFavoritesForStructure$1.run():void, com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1$onReceive$1.run():void, com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$accept$1.run():void] */
    public static final /* synthetic */ ControlsFavoritePersistenceWrapper access$getPersistenceWrapper$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.persistenceWrapper;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$addSeedingFavoritesCallback$1.run():void] */
    public static final /* synthetic */ List access$getSeedingCallbacks$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.seedingCallbacks;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$addSeedingFavoritesCallback$1.run():void] */
    public static final /* synthetic */ boolean access$getSeedingInProgress$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.seedingInProgress;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$settingObserver$1.onChange(boolean, java.util.Collection<? extends android.net.Uri>, int, int):void] */
    public static final /* synthetic */ boolean access$getUserChanging$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.userChanging;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1$onServicesUpdated$1.run():void] */
    public static final /* synthetic */ UserFileManager access$getUserFileManager$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.userFileManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1$onServicesUpdated$1.run():void] */
    public static final /* synthetic */ UserTracker access$getUserTracker$p(ControlsControllerImpl controlsControllerImpl) {
        return controlsControllerImpl.userTracker;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1$onReceive$1.run():void, com.android.systemui.controls.controller.ControlsControllerImpl$settingObserver$1.onChange(boolean, java.util.Collection<? extends android.net.Uri>, int, int):void] */
    public static final /* synthetic */ void access$resetFavorites(ControlsControllerImpl controlsControllerImpl) {
        controlsControllerImpl.resetFavorites();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ void access$setUserChanging$p(ControlsControllerImpl controlsControllerImpl, boolean z) {
        controlsControllerImpl.userChanging = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ void access$setValuesForUser(ControlsControllerImpl controlsControllerImpl, UserHandle userHandle) {
        controlsControllerImpl.setValuesForUser(userHandle);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$accept$1.run():void, com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$error$1.run():void] */
    public static final /* synthetic */ void access$startSeeding(ControlsControllerImpl controlsControllerImpl, List list, Consumer consumer, boolean z) {
        controlsControllerImpl.startSeeding(list, consumer, z);
    }

    public static /* synthetic */ ControlStatus createRemovedStatus$default(ControlsControllerImpl controlsControllerImpl, ComponentName componentName, ControlInfo controlInfo, CharSequence charSequence, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return controlsControllerImpl.createRemovedStatus(componentName, controlInfo, charSequence, z);
    }

    @VisibleForTesting
    public static /* synthetic */ void getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getRestoreFinishedReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getSettingObserver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void action(ComponentName componentName, ControlInfo controlInfo, ControlAction controlAction) {
        if (confirmAvailability()) {
            this.bindingController.action(componentName, controlInfo, controlAction);
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void addFavorite(final ComponentName componentName, final CharSequence charSequence, final ControlInfo controlInfo) {
        if (confirmAvailability()) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$addFavorite$1
                @Override // java.lang.Runnable
                public final void run() {
                    Favorites favorites = Favorites.INSTANCE;
                    if (favorites.addFavorite(componentName, charSequence, controlInfo)) {
                        ControlsControllerImpl.access$getPersistenceWrapper$p(this).storeFavorites(favorites.getAllStructures());
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public boolean addSeedingFavoritesCallback(final Consumer<Boolean> consumer) {
        if (this.seedingInProgress) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$addSeedingFavoritesCallback$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (ControlsControllerImpl.access$getSeedingInProgress$p(ControlsControllerImpl.this)) {
                        ControlsControllerImpl.access$getSeedingCallbacks$p(ControlsControllerImpl.this).add(consumer);
                    } else {
                        consumer.accept(Boolean.FALSE);
                    }
                }
            });
            return true;
        }
        return false;
    }

    public final boolean confirmAvailability() {
        if (this.userChanging) {
            Log.w("ControlsControllerImpl", "Controls not available while user is changing");
            return false;
        }
        return true;
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public int countFavoritesForComponent(ComponentName componentName) {
        return Favorites.INSTANCE.getControlsForComponent(componentName).size();
    }

    public final ControlStatus createRemovedStatus(ComponentName componentName, ControlInfo controlInfo, CharSequence charSequence, boolean z) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(componentName.getPackageName());
        return new ControlStatus(new Control.StatelessBuilder(controlInfo.getControlId(), PendingIntent.getActivity(this.context, componentName.hashCode(), intent, 67108864)).setTitle(controlInfo.getControlTitle()).setSubtitle(controlInfo.getControlSubtitle()).setStructure(charSequence).setDeviceType(controlInfo.getDeviceType()).build(), componentName, true, z);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ControlsController state:");
        boolean z = this.userChanging;
        printWriter.println("  Changing users: " + z);
        int identifier = this.currentUser.getIdentifier();
        printWriter.println("  Current user: " + identifier);
        printWriter.println("  Favorites:");
        for (StructureInfo structureInfo : Favorites.INSTANCE.getAllStructures()) {
            printWriter.println("    " + structureInfo);
            for (ControlInfo controlInfo : structureInfo.getControls()) {
                printWriter.println("      " + controlInfo);
            }
        }
        printWriter.println(this.bindingController.toString());
    }

    public final void endSeedingCall(boolean z) {
        this.seedingInProgress = false;
        Iterator<T> it = this.seedingCallbacks.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(Boolean.valueOf(z));
        }
        this.seedingCallbacks.clear();
    }

    public final Set<String> findRemoved(Set<String> set, List<Control> list) {
        List<Control> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (Control control : list2) {
            arrayList.add(control.getControlId());
        }
        return SetsKt___SetsKt.minus(set, arrayList);
    }

    public final AuxiliaryPersistenceWrapper getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.auxiliaryPersistenceWrapper;
    }

    public int getCurrentUserId() {
        return this.currentUser.getIdentifier();
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public List<StructureInfo> getFavorites() {
        return Favorites.INSTANCE.getAllStructures();
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public List<StructureInfo> getFavoritesForComponent(ComponentName componentName) {
        return Favorites.INSTANCE.getStructuresForComponent(componentName);
    }

    public List<ControlInfo> getFavoritesForStructure(ComponentName componentName, CharSequence charSequence) {
        return Favorites.INSTANCE.getControlsForStructure(new StructureInfo(componentName, charSequence, CollectionsKt__CollectionsKt.emptyList()));
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public SelectedItem getPreferredSelection() {
        return this.uiController.getPreferredSelectedItem(getFavorites());
    }

    public void loadForComponent(final ComponentName componentName, final Consumer<ControlsController.LoadData> consumer, final Consumer<Runnable> consumer2) {
        if (!confirmAvailability()) {
            if (this.userChanging) {
                this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ControlsControllerImpl.this.loadForComponent(componentName, consumer, consumer2);
                    }
                }, 500L, TimeUnit.MILLISECONDS);
            }
            consumer.accept(ControlsControllerKt.createLoadDataObject(CollectionsKt__CollectionsKt.emptyList(), CollectionsKt__CollectionsKt.emptyList(), true));
        }
        consumer2.accept(this.bindingController.bindAndLoad(componentName, new ControlsBindingController.LoadCallback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2
            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(List<? extends Control> list) {
                accept2((List<Control>) list);
            }

            /* renamed from: accept  reason: avoid collision after fix types in other method */
            public void accept2(final List<Control> list) {
                DelayableExecutor access$getExecutor$p = ControlsControllerImpl.access$getExecutor$p(ControlsControllerImpl.this);
                final ComponentName componentName2 = componentName;
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                final Consumer<ControlsController.LoadData> consumer3 = consumer;
                access$getExecutor$p.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2$accept$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        List<ControlInfo> controlsForComponent = Favorites.INSTANCE.getControlsForComponent(componentName2);
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controlsForComponent, 10));
                        for (ControlInfo controlInfo : controlsForComponent) {
                            arrayList.add(controlInfo.getControlId());
                        }
                        Favorites favorites = Favorites.INSTANCE;
                        if (favorites.updateControls(componentName2, list)) {
                            ControlsControllerImpl.access$getPersistenceWrapper$p(controlsControllerImpl).storeFavorites(favorites.getAllStructures());
                        }
                        Set access$findRemoved = ControlsControllerImpl.access$findRemoved(controlsControllerImpl, CollectionsKt___CollectionsKt.toSet(arrayList), list);
                        List<Control> list2 = list;
                        ComponentName componentName3 = componentName2;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                        for (Control control : list2) {
                            arrayList2.add(new ControlStatus(control, componentName3, arrayList.contains(control.getControlId()), false, 8, null));
                        }
                        ArrayList arrayList3 = new ArrayList();
                        List<StructureInfo> structuresForComponent = Favorites.INSTANCE.getStructuresForComponent(componentName2);
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        ComponentName componentName4 = componentName2;
                        for (StructureInfo structureInfo : structuresForComponent) {
                            for (ControlInfo controlInfo2 : structureInfo.getControls()) {
                                if (access$findRemoved.contains(controlInfo2.getControlId())) {
                                    arrayList3.add(ControlsControllerImpl.createRemovedStatus$default(controlsControllerImpl2, componentName4, controlInfo2, structureInfo.getStructure(), false, 8, null));
                                }
                            }
                        }
                        consumer3.accept(ControlsControllerKt.createLoadDataObject$default(CollectionsKt___CollectionsKt.plus(arrayList3, arrayList2), arrayList, false, 4, null));
                    }
                });
            }

            @Override // com.android.systemui.controls.controller.ControlsBindingController.LoadCallback
            public void error(String str) {
                DelayableExecutor access$getExecutor$p = ControlsControllerImpl.access$getExecutor$p(ControlsControllerImpl.this);
                final ComponentName componentName2 = componentName;
                final Consumer<ControlsController.LoadData> consumer3 = consumer;
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                access$getExecutor$p.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2$error$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        List<StructureInfo> structuresForComponent = Favorites.INSTANCE.getStructuresForComponent(componentName2);
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        ComponentName componentName3 = componentName2;
                        ArrayList<ControlStatus> arrayList = new ArrayList();
                        for (StructureInfo structureInfo : structuresForComponent) {
                            List<ControlInfo> controls = structureInfo.getControls();
                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controls, 10));
                            for (ControlInfo controlInfo : controls) {
                                arrayList2.add(ControlsControllerImpl.access$createRemovedStatus(controlsControllerImpl2, componentName3, controlInfo, structureInfo.getStructure(), false));
                            }
                            CollectionsKt__MutableCollectionsKt.addAll(arrayList, arrayList2);
                        }
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                        for (ControlStatus controlStatus : arrayList) {
                            arrayList3.add(controlStatus.getControl().getControlId());
                        }
                        consumer3.accept(ControlsControllerKt.createLoadDataObject(arrayList, arrayList3, true));
                    }
                });
            }
        }));
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void onActionResponse(ComponentName componentName, String str, int i) {
        if (confirmAvailability()) {
            this.uiController.onActionResponse(componentName, str, i);
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void refreshStatus(final ComponentName componentName, final Control control) {
        if (!confirmAvailability()) {
            Log.d("ControlsControllerImpl", "Controls not available");
            return;
        }
        if (control.getStatus() == 1) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$refreshStatus$1
                @Override // java.lang.Runnable
                public final void run() {
                    Favorites favorites = Favorites.INSTANCE;
                    if (favorites.updateControls(componentName, CollectionsKt__CollectionsJVMKt.listOf(control))) {
                        ControlsControllerImpl.access$getPersistenceWrapper$p(this).storeFavorites(favorites.getAllStructures());
                    }
                }
            });
        }
        this.uiController.onRefreshState(componentName, CollectionsKt__CollectionsJVMKt.listOf(control));
    }

    public void replaceFavoritesForStructure(final StructureInfo structureInfo) {
        if (confirmAvailability()) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$replaceFavoritesForStructure$1
                @Override // java.lang.Runnable
                public final void run() {
                    Favorites favorites = Favorites.INSTANCE;
                    favorites.replaceControls(StructureInfo.this);
                    ControlsControllerImpl.access$getPersistenceWrapper$p(this).storeFavorites(favorites.getAllStructures());
                }
            });
        }
    }

    public final void resetFavorites() {
        Favorites favorites = Favorites.INSTANCE;
        favorites.clear();
        favorites.load(this.persistenceWrapper.readFavorites());
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void seedFavoritesForComponents(final List<ComponentName> list, final Consumer<SeedResponse> consumer) {
        if (this.seedingInProgress || list.isEmpty()) {
            return;
        }
        if (confirmAvailability()) {
            this.seedingInProgress = true;
            startSeeding(list, consumer, false);
        } else if (this.userChanging) {
            this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$seedFavoritesForComponents$1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsControllerImpl.this.seedFavoritesForComponents(list, consumer);
                }
            }, 500L, TimeUnit.MILLISECONDS);
        } else {
            for (ComponentName componentName : list) {
                consumer.accept(new SeedResponse(componentName.getPackageName(), false));
            }
        }
    }

    public final void setValuesForUser(UserHandle userHandle) {
        Log.d("ControlsControllerImpl", "Changing to user: " + userHandle);
        this.currentUser = userHandle;
        UserStructure userStructure = new UserStructure(this.context, userHandle, this.userFileManager);
        this.userStructure = userStructure;
        this.persistenceWrapper.changeFileAndBackupManager(userStructure.getFile(), new BackupManager(this.userStructure.getUserContext()));
        this.auxiliaryPersistenceWrapper.changeFile(this.userStructure.getAuxiliaryFile());
        resetFavorites();
        this.bindingController.changeUser(userHandle);
        this.listingController.changeUser(userHandle);
        this.userChanging = false;
    }

    public final void startSeeding(List<ComponentName> list, final Consumer<SeedResponse> consumer, final boolean z) {
        if (list.isEmpty()) {
            endSeedingCall(!z);
            return;
        }
        final ComponentName componentName = list.get(0);
        Log.d("ControlsControllerImpl", "Beginning request to seed favorites for: " + componentName);
        final List drop = CollectionsKt___CollectionsKt.drop(list, 1);
        this.bindingController.bindAndLoadSuggested(componentName, new ControlsBindingController.LoadCallback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1
            /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(List<? extends Control> list2) {
                accept2((List<Control>) list2);
            }

            /* renamed from: accept  reason: avoid collision after fix types in other method */
            public void accept2(final List<Control> list2) {
                DelayableExecutor access$getExecutor$p = ControlsControllerImpl.access$getExecutor$p(ControlsControllerImpl.this);
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                final Consumer<SeedResponse> consumer2 = consumer;
                final ComponentName componentName2 = componentName;
                final List<ComponentName> list3 = drop;
                final boolean z2 = z;
                access$getExecutor$p.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$accept$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArrayMap arrayMap = new ArrayMap();
                        for (Control control : list2) {
                            CharSequence structure = control.getStructure();
                            CharSequence charSequence = structure;
                            if (structure == null) {
                                charSequence = "";
                            }
                            List list4 = (List) arrayMap.get(charSequence);
                            ArrayList arrayList = list4;
                            if (list4 == null) {
                                arrayList = new ArrayList();
                            }
                            if (arrayList.size() < 6) {
                                arrayList.add(new ControlInfo(control.getControlId(), control.getTitle(), control.getSubtitle(), control.getDeviceType()));
                                arrayMap.put(charSequence, arrayList);
                            }
                        }
                        ComponentName componentName3 = componentName2;
                        for (Map.Entry entry : arrayMap.entrySet()) {
                            Favorites.INSTANCE.replaceControls(new StructureInfo(componentName3, (CharSequence) entry.getKey(), (List) entry.getValue()));
                        }
                        ControlsControllerImpl.access$getPersistenceWrapper$p(controlsControllerImpl).storeFavorites(Favorites.INSTANCE.getAllStructures());
                        consumer2.accept(new SeedResponse(componentName2.getPackageName(), true));
                        ControlsControllerImpl.access$startSeeding(controlsControllerImpl, list3, consumer2, z2);
                    }
                });
            }

            @Override // com.android.systemui.controls.controller.ControlsBindingController.LoadCallback
            public void error(String str) {
                Log.e("ControlsControllerImpl", "Unable to seed favorites: " + str);
                DelayableExecutor access$getExecutor$p = ControlsControllerImpl.access$getExecutor$p(ControlsControllerImpl.this);
                final Consumer<SeedResponse> consumer2 = consumer;
                final ComponentName componentName2 = componentName;
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                final List<ComponentName> list2 = drop;
                access$getExecutor$p.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$error$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer2.accept(new SeedResponse(componentName2.getPackageName(), false));
                        ControlsControllerImpl.access$startSeeding(controlsControllerImpl, list2, consumer2, true);
                    }
                });
            }
        });
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void subscribeToFavorites(StructureInfo structureInfo) {
        if (confirmAvailability()) {
            this.bindingController.subscribe(structureInfo);
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void unsubscribe() {
        if (confirmAvailability()) {
            this.bindingController.unsubscribe();
        }
    }
}