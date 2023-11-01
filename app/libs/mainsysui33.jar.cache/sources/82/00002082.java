package com.android.systemui.qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.UserAwareController;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/AutoAddTracker.class */
public final class AutoAddTracker implements UserAwareController, Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final IntentFilter FILTER = new IntentFilter("android.os.action.SETTING_RESTORED");
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final AutoAddTracker$contentObserver$1 contentObserver;
    public final DumpManager dumpManager;
    public final Handler mainHandler;
    public final QSHost qsHost;
    public Set<String> restoredTiles;
    public final SecureSettings secureSettings;
    public int userId;
    public final ArraySet<String> autoAdded = new ArraySet<>();
    public final AutoAddTracker$restoreReceiver$1 restoreReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.AutoAddTracker$restoreReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Intrinsics.areEqual(intent.getAction(), "android.os.action.SETTING_RESTORED")) {
                AutoAddTracker.access$processRestoreIntent(AutoAddTracker.this, intent);
            }
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/AutoAddTracker$Builder.class */
    public static final class Builder {
        public final BroadcastDispatcher broadcastDispatcher;
        public final DumpManager dumpManager;
        public final Executor executor;
        public final Handler handler;
        public final QSHost qsHost;
        public final SecureSettings secureSettings;
        public int userId;

        public Builder(SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, QSHost qSHost, DumpManager dumpManager, Handler handler, Executor executor) {
            this.secureSettings = secureSettings;
            this.broadcastDispatcher = broadcastDispatcher;
            this.qsHost = qSHost;
            this.dumpManager = dumpManager;
            this.handler = handler;
            this.executor = executor;
        }

        public final AutoAddTracker build() {
            return new AutoAddTracker(this.secureSettings, this.broadcastDispatcher, this.qsHost, this.dumpManager, this.handler, this.executor, this.userId);
        }

        public final Builder setUserId(int i) {
            this.userId = i;
            return this;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/AutoAddTracker$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.qs.AutoAddTracker$contentObserver$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.qs.AutoAddTracker$restoreReceiver$1] */
    public AutoAddTracker(SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, QSHost qSHost, DumpManager dumpManager, Handler handler, Executor executor, int i) {
        this.secureSettings = secureSettings;
        this.broadcastDispatcher = broadcastDispatcher;
        this.qsHost = qSHost;
        this.dumpManager = dumpManager;
        this.mainHandler = handler;
        this.backgroundExecutor = executor;
        this.userId = i;
        this.contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.qs.AutoAddTracker$contentObserver$1
            public void onChange(boolean z, Collection<? extends Uri> collection, int i2, int i3) {
                if (i3 != AutoAddTracker.access$getUserId$p(AutoAddTracker.this)) {
                    return;
                }
                AutoAddTracker.access$loadTiles(AutoAddTracker.this);
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.AutoAddTracker$contentObserver$1.onChange(boolean, java.util.Collection<? extends android.net.Uri>, int, int):void] */
    public static final /* synthetic */ int access$getUserId$p(AutoAddTracker autoAddTracker) {
        return autoAddTracker.userId;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.AutoAddTracker$contentObserver$1.onChange(boolean, java.util.Collection<? extends android.net.Uri>, int, int):void] */
    public static final /* synthetic */ void access$loadTiles(AutoAddTracker autoAddTracker) {
        autoAddTracker.loadTiles();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.AutoAddTracker$restoreReceiver$1.onReceive(android.content.Context, android.content.Intent):void] */
    public static final /* synthetic */ void access$processRestoreIntent(AutoAddTracker autoAddTracker, Intent intent) {
        autoAddTracker.processRestoreIntent(intent);
    }

    public void changeUser(UserHandle userHandle) {
        if (userHandle.getIdentifier() == this.userId) {
            return;
        }
        unregisterBroadcastReceiver();
        this.userId = userHandle.getIdentifier();
        this.restoredTiles = null;
        loadTiles();
        registerBroadcastReceiver();
    }

    public final void destroy() {
        this.dumpManager.unregisterDumpable("AutoAddTracker");
        this.secureSettings.unregisterContentObserver(this.contentObserver);
        unregisterBroadcastReceiver();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        int i = this.userId;
        printWriter.println("Current user: " + i);
        ArraySet<String> arraySet = this.autoAdded;
        printWriter.println("Added tiles: " + arraySet);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0028, code lost:
        if (r8 != null) goto L5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Collection<String> getAdded() {
        Collection emptySet;
        String stringForUser = this.secureSettings.getStringForUser("qs_auto_tiles", this.userId);
        if (stringForUser != null) {
            emptySet = StringsKt__StringsKt.split$default(stringForUser, new String[]{","}, false, 0, 6, (Object) null);
        }
        emptySet = SetsKt__SetsKt.emptySet();
        return emptySet;
    }

    public final String getTilesFromListLocked() {
        return TextUtils.join(",", this.autoAdded);
    }

    public final void initialize() {
        DumpManager.registerDumpable$default(this.dumpManager, "AutoAddTracker", this, null, 4, null);
        loadTiles();
        SecureSettings secureSettings = this.secureSettings;
        secureSettings.registerContentObserverForUser(secureSettings.getUriFor("qs_auto_tiles"), this.contentObserver, -1);
        registerBroadcastReceiver();
    }

    public final boolean isAdded(String str) {
        boolean contains;
        synchronized (this.autoAdded) {
            contains = this.autoAdded.contains(str);
        }
        return contains;
    }

    public final void loadTiles() {
        synchronized (this.autoAdded) {
            this.autoAdded.clear();
            this.autoAdded.addAll(getAdded());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x003b, code lost:
        if (r8 != null) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00a5, code lost:
        if (r9 != null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00cf, code lost:
        if (r8 != null) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void processRestoreIntent(Intent intent) {
        List emptyList;
        List emptyList2;
        String tilesFromListLocked;
        Set<String> emptySet;
        List split$default;
        String stringExtra = intent.getStringExtra("setting_name");
        if (Intrinsics.areEqual(stringExtra, "sysui_qs_tiles")) {
            String stringExtra2 = intent.getStringExtra("new_value");
            if (stringExtra2 != null && (split$default = StringsKt__StringsKt.split$default(stringExtra2, new String[]{","}, false, 0, 6, (Object) null)) != null) {
                emptySet = CollectionsKt___CollectionsKt.toSet(split$default);
            }
            int i = this.userId;
            Log.w("AutoAddTracker", "Null restored tiles for user " + i);
            emptySet = SetsKt__SetsKt.emptySet();
            this.restoredTiles = emptySet;
        } else if (Intrinsics.areEqual(stringExtra, "qs_auto_tiles")) {
            Set<String> set = this.restoredTiles;
            if (set == null) {
                int i2 = this.userId;
                Log.w("AutoAddTracker", "qs_auto_tiles restored before sysui_qs_tiles for user " + i2);
                return;
            }
            String stringExtra3 = intent.getStringExtra("new_value");
            if (stringExtra3 != null) {
                emptyList = StringsKt__StringsKt.split$default(stringExtra3, new String[]{","}, false, 0, 6, (Object) null);
            }
            emptyList = CollectionsKt__CollectionsKt.emptyList();
            String stringExtra4 = intent.getStringExtra("previous_value");
            if (stringExtra4 != null) {
                emptyList2 = StringsKt__StringsKt.split$default(stringExtra4, new String[]{","}, false, 0, 6, (Object) null);
            }
            emptyList2 = CollectionsKt__CollectionsKt.emptyList();
            ArrayList arrayList = new ArrayList();
            for (Object obj : emptyList) {
                if (!set.contains((String) obj)) {
                    arrayList.add(obj);
                }
            }
            if (!arrayList.isEmpty()) {
                this.qsHost.removeTiles(arrayList);
            }
            synchronized (this.autoAdded) {
                this.autoAdded.clear();
                this.autoAdded.addAll(CollectionsKt___CollectionsKt.plus(emptyList, emptyList2));
                tilesFromListLocked = getTilesFromListLocked();
            }
            saveTiles(tilesFromListLocked);
        }
    }

    public final void registerBroadcastReceiver() {
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.restoreReceiver, FILTER, this.backgroundExecutor, UserHandle.of(this.userId), 0, null, 48, null);
    }

    public final void saveTiles(String str) {
        this.secureSettings.putStringForUser("qs_auto_tiles", str, (String) null, false, this.userId, true);
    }

    public final void setTileAdded(String str) {
        String tilesFromListLocked;
        synchronized (this.autoAdded) {
            tilesFromListLocked = this.autoAdded.add(str) ? getTilesFromListLocked() : null;
        }
        if (tilesFromListLocked != null) {
            saveTiles(tilesFromListLocked);
        }
    }

    public final void setTileRemoved(String str) {
        String tilesFromListLocked;
        synchronized (this.autoAdded) {
            tilesFromListLocked = this.autoAdded.remove(str) ? getTilesFromListLocked() : null;
        }
        if (tilesFromListLocked != null) {
            saveTiles(tilesFromListLocked);
        }
    }

    public final void unregisterBroadcastReceiver() {
        this.broadcastDispatcher.unregisterReceiver(this.restoreReceiver);
    }
}