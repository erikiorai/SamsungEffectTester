package com.android.systemui.privacy;

import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyItemMonitor;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyItemController.class */
public final class PrivacyItemController implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public final DelayableExecutor bgExecutor;
    public Runnable holdingRunnableCanceler;
    public final MyExecutor internalUiExecutor;
    public boolean listening;
    public final PrivacyLogger logger;
    public final PrivacyItemController$optionsCallback$1 optionsCallback;
    public final PrivacyConfig privacyConfig;
    public final PrivacyItemController$privacyItemMonitorCallback$1 privacyItemMonitorCallback;
    public final Set<PrivacyItemMonitor> privacyItemMonitors;
    public final SystemClock systemClock;
    public final Runnable updateListAndNotifyChanges;
    public List<PrivacyItem> privacyList = CollectionsKt__CollectionsKt.emptyList();
    public final List<WeakReference<Callback>> callbacks = new ArrayList();
    public final Runnable notifyChanges = new Runnable() { // from class: com.android.systemui.privacy.PrivacyItemController$notifyChanges$1
        @Override // java.lang.Runnable
        public final void run() {
            List<PrivacyItem> privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core = PrivacyItemController.this.getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            for (WeakReference weakReference : PrivacyItemController.access$getCallbacks$p(PrivacyItemController.this)) {
                PrivacyItemController.Callback callback = (PrivacyItemController.Callback) weakReference.get();
                if (callback != null) {
                    callback.onPrivacyItemsChanged(privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core);
                }
            }
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyItemController$Callback.class */
    public interface Callback extends PrivacyConfig.Callback {
        void onPrivacyItemsChanged(List<PrivacyItem> list);
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyItemController$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @VisibleForTesting
        public static /* synthetic */ void getTIME_TO_HOLD_INDICATORS$annotations() {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyItemController$MyExecutor.class */
    public final class MyExecutor implements Executor {
        public final DelayableExecutor delegate;
        public Runnable listeningCanceller;

        public MyExecutor(DelayableExecutor delayableExecutor) {
            PrivacyItemController.this = r4;
            this.delegate = delayableExecutor;
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            this.delegate.execute(runnable);
        }

        public final void updateListeningState() {
            Runnable runnable = this.listeningCanceller;
            if (runnable != null) {
                runnable.run();
            }
            DelayableExecutor delayableExecutor = this.delegate;
            final PrivacyItemController privacyItemController = PrivacyItemController.this;
            this.listeningCanceller = delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.privacy.PrivacyItemController$MyExecutor$updateListeningState$1
                @Override // java.lang.Runnable
                public final void run() {
                    PrivacyItemController.access$setListeningState(PrivacyItemController.this);
                }
            }, 0L);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyItemController$NotifyChangesToCallback.class */
    public static final class NotifyChangesToCallback implements Runnable {
        public final Callback callback;
        public final List<PrivacyItem> list;

        public NotifyChangesToCallback(Callback callback, List<PrivacyItem> list) {
            this.callback = callback;
            this.list = list;
        }

        @Override // java.lang.Runnable
        public void run() {
            Callback callback = this.callback;
            if (callback != null) {
                callback.onPrivacyItemsChanged(this.list);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r10v0, resolved type: com.android.systemui.privacy.PrivacyConfig */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.privacy.PrivacyItemController$optionsCallback$1, com.android.systemui.privacy.PrivacyConfig$Callback] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.privacy.PrivacyItemController$privacyItemMonitorCallback$1] */
    public PrivacyItemController(final DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, PrivacyConfig privacyConfig, Set<PrivacyItemMonitor> set, PrivacyLogger privacyLogger, SystemClock systemClock, DumpManager dumpManager) {
        this.bgExecutor = delayableExecutor2;
        this.privacyConfig = privacyConfig;
        this.privacyItemMonitors = set;
        this.logger = privacyLogger;
        this.systemClock = systemClock;
        this.internalUiExecutor = new MyExecutor(delayableExecutor);
        this.updateListAndNotifyChanges = new Runnable() { // from class: com.android.systemui.privacy.PrivacyItemController$updateListAndNotifyChanges$1
            @Override // java.lang.Runnable
            public final void run() {
                PrivacyItemController.access$updatePrivacyList(PrivacyItemController.this);
                delayableExecutor.execute(PrivacyItemController.access$getNotifyChanges$p(PrivacyItemController.this));
            }
        };
        ?? r0 = new PrivacyConfig.Callback() { // from class: com.android.systemui.privacy.PrivacyItemController$optionsCallback$1
            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public void onFlagLocationChanged(boolean z) {
                for (WeakReference weakReference : PrivacyItemController.access$getCallbacks$p(PrivacyItemController.this)) {
                    PrivacyItemController.Callback callback = (PrivacyItemController.Callback) weakReference.get();
                    if (callback != null) {
                        callback.onFlagLocationChanged(z);
                    }
                }
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public void onFlagMediaProjectionChanged(boolean z) {
                for (WeakReference weakReference : PrivacyItemController.access$getCallbacks$p(PrivacyItemController.this)) {
                    PrivacyItemController.Callback callback = (PrivacyItemController.Callback) weakReference.get();
                    if (callback != null) {
                        callback.onFlagMediaProjectionChanged(z);
                    }
                }
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public void onFlagMicCameraChanged(boolean z) {
                for (WeakReference weakReference : PrivacyItemController.access$getCallbacks$p(PrivacyItemController.this)) {
                    PrivacyItemController.Callback callback = (PrivacyItemController.Callback) weakReference.get();
                    if (callback != null) {
                        callback.onFlagMicCameraChanged(z);
                    }
                }
            }
        };
        this.optionsCallback = r0;
        this.privacyItemMonitorCallback = new PrivacyItemMonitor.Callback() { // from class: com.android.systemui.privacy.PrivacyItemController$privacyItemMonitorCallback$1
            @Override // com.android.systemui.privacy.PrivacyItemMonitor.Callback
            public void onPrivacyItemsChanged() {
                PrivacyItemController.access$update(PrivacyItemController.this);
            }
        };
        DumpManager.registerDumpable$default(dumpManager, "PrivacyItemController", this, null, 4, null);
        privacyConfig.addCallback((PrivacyConfig.Callback) r0);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyItemController$notifyChanges$1.run():void, com.android.systemui.privacy.PrivacyItemController$optionsCallback$1.onFlagLocationChanged(boolean):void, com.android.systemui.privacy.PrivacyItemController$optionsCallback$1.onFlagMediaProjectionChanged(boolean):void, com.android.systemui.privacy.PrivacyItemController$optionsCallback$1.onFlagMicCameraChanged(boolean):void] */
    public static final /* synthetic */ List access$getCallbacks$p(PrivacyItemController privacyItemController) {
        return privacyItemController.callbacks;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyItemController$updateListAndNotifyChanges$1.run():void] */
    public static final /* synthetic */ Runnable access$getNotifyChanges$p(PrivacyItemController privacyItemController) {
        return privacyItemController.notifyChanges;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyItemController$update$1.run():void] */
    public static final /* synthetic */ Runnable access$getUpdateListAndNotifyChanges$p(PrivacyItemController privacyItemController) {
        return privacyItemController.updateListAndNotifyChanges;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyItemController$MyExecutor$updateListeningState$1.run():void] */
    public static final /* synthetic */ void access$setListeningState(PrivacyItemController privacyItemController) {
        privacyItemController.setListeningState();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyItemController$privacyItemMonitorCallback$1.onPrivacyItemsChanged():void] */
    public static final /* synthetic */ void access$update(PrivacyItemController privacyItemController) {
        privacyItemController.update();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyItemController$updateListAndNotifyChanges$1.run():void] */
    public static final /* synthetic */ void access$updatePrivacyList(PrivacyItemController privacyItemController) {
        privacyItemController.updatePrivacyList();
    }

    @VisibleForTesting
    public static /* synthetic */ void getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public final void addCallback(Callback callback) {
        addCallback(new WeakReference<>(callback));
    }

    public final void addCallback(WeakReference<Callback> weakReference) {
        this.callbacks.add(weakReference);
        if ((!this.callbacks.isEmpty()) && !this.listening) {
            this.internalUiExecutor.updateListeningState();
        } else if (this.listening) {
            this.internalUiExecutor.execute(new NotifyChangesToCallback(weakReference.get(), getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core()));
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("PrivacyItemController state:");
        asIndenting.increaseIndent();
        try {
            boolean z = this.listening;
            asIndenting.println("Listening: " + z);
            asIndenting.println("Privacy Items:");
            asIndenting.increaseIndent();
            for (PrivacyItem privacyItem : getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                asIndenting.println(privacyItem.toString());
            }
            asIndenting.decreaseIndent();
            asIndenting.println("Callbacks:");
            asIndenting.increaseIndent();
            Iterator<T> it = this.callbacks.iterator();
            while (it.hasNext()) {
                Callback callback = (Callback) ((WeakReference) it.next()).get();
                if (callback != null) {
                    asIndenting.println(callback.toString());
                }
            }
            asIndenting.decreaseIndent();
            asIndenting.println("PrivacyItemMonitors:");
            asIndenting.increaseIndent();
            for (PrivacyItemMonitor privacyItemMonitor : this.privacyItemMonitors) {
                privacyItemMonitor.dump(asIndenting, strArr);
            }
            asIndenting.decreaseIndent();
            asIndenting.decreaseIndent();
            asIndenting.flush();
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    public final boolean getAllIndicatorsAvailable() {
        return getMicCameraAvailable() && getLocationAvailable() && this.privacyConfig.getMediaProjectionAvailable();
    }

    public final boolean getLocationAvailable() {
        return this.privacyConfig.getLocationAvailable();
    }

    public final boolean getMicCameraAvailable() {
        return this.privacyConfig.getMicCameraAvailable();
    }

    public final List<PrivacyItem> getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        List<PrivacyItem> list;
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.privacyList);
        }
        return list;
    }

    public final boolean isIn(PrivacyItem privacyItem, List<PrivacyItem> list) {
        List<PrivacyItem> list2 = list;
        boolean z = true;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            for (PrivacyItem privacyItem2 : list2) {
                if (privacyItem2.getPrivacyType() == privacyItem.getPrivacyType() && Intrinsics.areEqual(privacyItem2.getApplication(), privacyItem.getApplication()) && privacyItem2.getTimeStampElapsed() == privacyItem.getTimeStampElapsed()) {
                    break;
                }
            }
        }
        z = false;
        return z;
    }

    public final List<PrivacyItem> processNewList(List<PrivacyItem> list) {
        Object next;
        this.logger.logRetrievedPrivacyItemsList(list);
        long elapsedRealtime = this.systemClock.elapsedRealtime() - 5000;
        List<PrivacyItem> privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Object next2 = it.next();
            PrivacyItem privacyItem = (PrivacyItem) next2;
            if ((privacyItem.getTimeStampElapsed() <= elapsedRealtime || isIn(privacyItem, list)) ? false : false) {
                arrayList.add(next2);
            }
        }
        if (!arrayList.isEmpty()) {
            this.logger.logPrivacyItemsToHold(arrayList);
            Iterator it2 = arrayList.iterator();
            if (it2.hasNext()) {
                next = it2.next();
                if (it2.hasNext()) {
                    long timeStampElapsed = ((PrivacyItem) next).getTimeStampElapsed();
                    Object obj = next;
                    do {
                        Object next3 = it2.next();
                        long timeStampElapsed2 = ((PrivacyItem) next3).getTimeStampElapsed();
                        next = obj;
                        long j = timeStampElapsed;
                        if (timeStampElapsed > timeStampElapsed2) {
                            next = next3;
                            j = timeStampElapsed2;
                        }
                        obj = next;
                        timeStampElapsed = j;
                    } while (it2.hasNext());
                }
            } else {
                next = null;
            }
            Intrinsics.checkNotNull(next);
            long timeStampElapsed3 = ((PrivacyItem) next).getTimeStampElapsed() - elapsedRealtime;
            this.logger.logPrivacyItemsUpdateScheduled(timeStampElapsed3);
            this.holdingRunnableCanceler = this.bgExecutor.executeDelayed(this.updateListAndNotifyChanges, timeStampElapsed3);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list) {
            if (!((PrivacyItem) obj2).getPaused()) {
                arrayList2.add(obj2);
            }
        }
        return CollectionsKt___CollectionsKt.plus(arrayList2, arrayList);
    }

    public final void removeCallback(Callback callback) {
        removeCallback(new WeakReference<>(callback));
    }

    public final void removeCallback(final WeakReference<Callback> weakReference) {
        this.callbacks.removeIf(new Predicate() { // from class: com.android.systemui.privacy.PrivacyItemController$removeCallback$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Predicate
            public final boolean test(WeakReference<PrivacyItemController.Callback> weakReference2) {
                PrivacyItemController.Callback callback = weakReference2.get();
                return callback != null ? callback.equals(weakReference.get()) : true;
            }
        });
        if (this.callbacks.isEmpty()) {
            this.internalUiExecutor.updateListeningState();
        }
    }

    public final void setListeningState() {
        boolean z = !this.callbacks.isEmpty();
        if (this.listening == z) {
            return;
        }
        this.listening = z;
        if (z) {
            for (PrivacyItemMonitor privacyItemMonitor : this.privacyItemMonitors) {
                privacyItemMonitor.startListening(this.privacyItemMonitorCallback);
            }
            update();
            return;
        }
        for (PrivacyItemMonitor privacyItemMonitor2 : this.privacyItemMonitors) {
            privacyItemMonitor2.stopListening();
        }
        update();
    }

    public final void update() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyItemController$update$1
            @Override // java.lang.Runnable
            public final void run() {
                PrivacyItemController.access$getUpdateListAndNotifyChanges$p(PrivacyItemController.this).run();
            }
        });
    }

    public final void updatePrivacyList() {
        Runnable runnable = this.holdingRunnableCanceler;
        if (runnable != null) {
            runnable.run();
            Unit unit = Unit.INSTANCE;
            this.holdingRunnableCanceler = null;
        }
        if (!this.listening) {
            this.privacyList = CollectionsKt__CollectionsKt.emptyList();
            return;
        }
        Set<PrivacyItemMonitor> set = this.privacyItemMonitors;
        ArrayList arrayList = new ArrayList();
        for (PrivacyItemMonitor privacyItemMonitor : set) {
            CollectionsKt__MutableCollectionsKt.addAll(arrayList, privacyItemMonitor.getActivePrivacyItems());
        }
        this.privacyList = processNewList(CollectionsKt___CollectionsKt.distinct(arrayList));
    }
}