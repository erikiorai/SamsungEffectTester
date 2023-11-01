package com.android.systemui.privacy;

import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.util.IndentingPrintWriter;
import com.android.internal.annotations.GuardedBy;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemMonitor;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/MediaProjectionPrivacyItemMonitor.class */
public final class MediaProjectionPrivacyItemMonitor implements PrivacyItemMonitor {
    public static final Companion Companion = new Companion(null);
    public final Handler bgHandler;
    @GuardedBy({"lock"})
    public PrivacyItemMonitor.Callback callback;
    @GuardedBy({"lock"})
    public boolean listening;
    public final PrivacyLogger logger;
    @GuardedBy({"lock"})
    public boolean mediaProjectionAvailable;
    public final MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1 mediaProjectionCallback;
    public final MediaProjectionManager mediaProjectionManager;
    public final MediaProjectionPrivacyItemMonitor$optionsCallback$1 optionsCallback;
    public final PackageManager packageManager;
    public final PrivacyConfig privacyConfig;
    public final SystemClock systemClock;
    public final Object lock = new Object();
    @GuardedBy({"lock"})
    public final List<PrivacyItem> privacyItems = new ArrayList();

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/MediaProjectionPrivacyItemMonitor$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: com.android.systemui.privacy.PrivacyConfig */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1, com.android.systemui.privacy.PrivacyConfig$Callback] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1] */
    public MediaProjectionPrivacyItemMonitor(MediaProjectionManager mediaProjectionManager, PackageManager packageManager, PrivacyConfig privacyConfig, Handler handler, SystemClock systemClock, PrivacyLogger privacyLogger) {
        this.mediaProjectionManager = mediaProjectionManager;
        this.packageManager = packageManager;
        this.privacyConfig = privacyConfig;
        this.bgHandler = handler;
        this.systemClock = systemClock;
        this.logger = privacyLogger;
        this.mediaProjectionAvailable = privacyConfig.getMediaProjectionAvailable();
        ?? r0 = new PrivacyConfig.Callback() { // from class: com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1
            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public void onFlagMediaProjectionChanged(boolean z) {
                Object access$getLock$p = MediaProjectionPrivacyItemMonitor.access$getLock$p(MediaProjectionPrivacyItemMonitor.this);
                MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor = MediaProjectionPrivacyItemMonitor.this;
                synchronized (access$getLock$p) {
                    MediaProjectionPrivacyItemMonitor.access$setMediaProjectionAvailable$p(mediaProjectionPrivacyItemMonitor, MediaProjectionPrivacyItemMonitor.access$getPrivacyConfig$p(mediaProjectionPrivacyItemMonitor).getMediaProjectionAvailable());
                    MediaProjectionPrivacyItemMonitor.access$setListeningStateLocked(mediaProjectionPrivacyItemMonitor);
                    Unit unit = Unit.INSTANCE;
                }
                MediaProjectionPrivacyItemMonitor.access$dispatchOnPrivacyItemsChanged(MediaProjectionPrivacyItemMonitor.this);
            }
        };
        this.optionsCallback = r0;
        this.mediaProjectionCallback = new MediaProjectionManager.Callback() { // from class: com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1
            public void onStart(MediaProjectionInfo mediaProjectionInfo) {
                Object access$getLock$p = MediaProjectionPrivacyItemMonitor.access$getLock$p(MediaProjectionPrivacyItemMonitor.this);
                MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor = MediaProjectionPrivacyItemMonitor.this;
                synchronized (access$getLock$p) {
                    MediaProjectionPrivacyItemMonitor.access$onMediaProjectionStartedLocked(mediaProjectionPrivacyItemMonitor, mediaProjectionInfo);
                    Unit unit = Unit.INSTANCE;
                }
                MediaProjectionPrivacyItemMonitor.access$dispatchOnPrivacyItemsChanged(MediaProjectionPrivacyItemMonitor.this);
            }

            public void onStop(MediaProjectionInfo mediaProjectionInfo) {
                Object access$getLock$p = MediaProjectionPrivacyItemMonitor.access$getLock$p(MediaProjectionPrivacyItemMonitor.this);
                MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor = MediaProjectionPrivacyItemMonitor.this;
                synchronized (access$getLock$p) {
                    MediaProjectionPrivacyItemMonitor.access$onMediaProjectionStoppedLocked(mediaProjectionPrivacyItemMonitor, mediaProjectionInfo);
                    Unit unit = Unit.INSTANCE;
                }
                MediaProjectionPrivacyItemMonitor.access$dispatchOnPrivacyItemsChanged(MediaProjectionPrivacyItemMonitor.this);
            }
        };
        privacyConfig.addCallback((PrivacyConfig.Callback) r0);
        setListeningStateLocked();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1.onStart(android.media.projection.MediaProjectionInfo):void, com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1.onStop(android.media.projection.MediaProjectionInfo):void, com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1.onFlagMediaProjectionChanged(boolean):void] */
    public static final /* synthetic */ void access$dispatchOnPrivacyItemsChanged(MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor) {
        mediaProjectionPrivacyItemMonitor.dispatchOnPrivacyItemsChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1.onStart(android.media.projection.MediaProjectionInfo):void, com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1.onStop(android.media.projection.MediaProjectionInfo):void, com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1.onFlagMediaProjectionChanged(boolean):void] */
    public static final /* synthetic */ Object access$getLock$p(MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor) {
        return mediaProjectionPrivacyItemMonitor.lock;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1.onFlagMediaProjectionChanged(boolean):void] */
    public static final /* synthetic */ PrivacyConfig access$getPrivacyConfig$p(MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor) {
        return mediaProjectionPrivacyItemMonitor.privacyConfig;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1.onStart(android.media.projection.MediaProjectionInfo):void] */
    public static final /* synthetic */ void access$onMediaProjectionStartedLocked(MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor, MediaProjectionInfo mediaProjectionInfo) {
        mediaProjectionPrivacyItemMonitor.onMediaProjectionStartedLocked(mediaProjectionInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1.onStop(android.media.projection.MediaProjectionInfo):void] */
    public static final /* synthetic */ void access$onMediaProjectionStoppedLocked(MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor, MediaProjectionInfo mediaProjectionInfo) {
        mediaProjectionPrivacyItemMonitor.onMediaProjectionStoppedLocked(mediaProjectionInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1.onFlagMediaProjectionChanged(boolean):void] */
    public static final /* synthetic */ void access$setListeningStateLocked(MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor) {
        mediaProjectionPrivacyItemMonitor.setListeningStateLocked();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1.onFlagMediaProjectionChanged(boolean):void] */
    public static final /* synthetic */ void access$setMediaProjectionAvailable$p(MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor, boolean z) {
        mediaProjectionPrivacyItemMonitor.mediaProjectionAvailable = z;
    }

    public final void dispatchOnPrivacyItemsChanged() {
        final PrivacyItemMonitor.Callback callback;
        synchronized (this.lock) {
            callback = this.callback;
        }
        if (callback != null) {
            this.bgHandler.post(new Runnable() { // from class: com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$dispatchOnPrivacyItemsChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    PrivacyItemMonitor.Callback.this.onPrivacyItemsChanged();
                }
            });
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("MediaProjectionPrivacyItemMonitor:");
        asIndenting.increaseIndent();
        try {
            synchronized (this.lock) {
                boolean z = this.listening;
                asIndenting.println("Listening: " + z);
                boolean z2 = this.mediaProjectionAvailable;
                asIndenting.println("mediaProjectionAvailable: " + z2);
                PrivacyItemMonitor.Callback callback = this.callback;
                asIndenting.println("Callback: " + callback);
                List<PrivacyItem> list = this.privacyItems;
                asIndenting.println("Privacy Items: " + list);
                Unit unit = Unit.INSTANCE;
            }
            asIndenting.decreaseIndent();
            asIndenting.flush();
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public List<PrivacyItem> getActivePrivacyItems() {
        List<PrivacyItem> list;
        synchronized (this.lock) {
            list = CollectionsKt___CollectionsKt.toList(this.privacyItems);
        }
        return list;
    }

    public final void logItemActive(PrivacyItem privacyItem, boolean z) {
        this.logger.logUpdatedItemFromMediaProjection(privacyItem.getApplication().getUid(), privacyItem.getApplication().getPackageName(), z);
    }

    public final PrivacyItem makePrivacyItem(MediaProjectionInfo mediaProjectionInfo) {
        return new PrivacyItem(PrivacyType.TYPE_MEDIA_PROJECTION, new PrivacyApplication(mediaProjectionInfo.getPackageName(), this.packageManager.getPackageUidAsUser(mediaProjectionInfo.getPackageName(), mediaProjectionInfo.getUserHandle().getIdentifier())), this.systemClock.elapsedRealtime(), false, 8, null);
    }

    @GuardedBy({"lock"})
    public final void onMediaProjectionStartedLocked(MediaProjectionInfo mediaProjectionInfo) {
        PrivacyItem makePrivacyItem = makePrivacyItem(mediaProjectionInfo);
        this.privacyItems.add(makePrivacyItem);
        logItemActive(makePrivacyItem, true);
    }

    @GuardedBy({"lock"})
    public final void onMediaProjectionStoppedLocked(MediaProjectionInfo mediaProjectionInfo) {
        PrivacyItem makePrivacyItem = makePrivacyItem(mediaProjectionInfo);
        List<PrivacyItem> list = this.privacyItems;
        Iterator<PrivacyItem> it = list.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            } else if (Intrinsics.areEqual(it.next().getApplication(), makePrivacyItem.getApplication())) {
                break;
            } else {
                i++;
            }
        }
        list.remove(i);
        logItemActive(makePrivacyItem, false);
    }

    @GuardedBy({"lock"})
    public final void setListeningStateLocked() {
        boolean z = this.mediaProjectionAvailable;
        if (this.listening == z) {
            return;
        }
        this.listening = z;
        if (z) {
            this.mediaProjectionManager.addCallback(this.mediaProjectionCallback, this.bgHandler);
            MediaProjectionInfo activeProjectionInfo = this.mediaProjectionManager.getActiveProjectionInfo();
            if (activeProjectionInfo != null) {
                onMediaProjectionStartedLocked(activeProjectionInfo);
                dispatchOnPrivacyItemsChanged();
                return;
            }
            return;
        }
        this.mediaProjectionManager.removeCallback(this.mediaProjectionCallback);
        for (PrivacyItem privacyItem : this.privacyItems) {
            logItemActive(privacyItem, false);
        }
        this.privacyItems.clear();
        dispatchOnPrivacyItemsChanged();
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public void startListening(PrivacyItemMonitor.Callback callback) {
        synchronized (this.lock) {
            this.callback = callback;
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public void stopListening() {
        synchronized (this.lock) {
            this.callback = null;
            Unit unit = Unit.INSTANCE;
        }
    }
}