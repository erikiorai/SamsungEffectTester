package com.android.systemui.media.controls.pipeline;

import android.app.smartspace.SmartspaceAction;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.R$styleable;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.time.SystemClock;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataFilter.class */
public final class MediaDataFilter implements MediaDataManager.Listener {
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final Executor executor;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final MediaUiEventLogger logger;
    public MediaDataManager mediaDataManager;
    public String reactivatedKey;
    public final SystemClock systemClock;
    public final UserTracker userTracker;
    public final MediaDataFilter$userTrackerCallback$1 userTrackerCallback;
    public final Set<MediaDataManager.Listener> _listeners = new LinkedHashSet();
    public final LinkedHashMap<String, MediaData> allEntries = new LinkedHashMap<>();
    public final LinkedHashMap<String, MediaData> userEntries = new LinkedHashMap<>();
    public SmartspaceMediaData smartspaceMediaData = MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA();

    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.media.controls.pipeline.MediaDataFilter$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    public MediaDataFilter(Context context, UserTracker userTracker, BroadcastSender broadcastSender, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger) {
        this.context = context;
        this.userTracker = userTracker;
        this.broadcastSender = broadcastSender;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor;
        this.systemClock = systemClock;
        this.logger = mediaUiEventLogger;
        ?? r0 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.pipeline.MediaDataFilter$userTrackerCallback$1
            public void onUserChanged(int i, Context context2) {
                MediaDataFilter.this.handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i);
            }
        };
        this.userTrackerCallback = r0;
        userTracker.addCallback((UserTracker.Callback) r0, executor);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataFilter$onSmartspaceMediaDataLoaded$$inlined$compareBy$1.compare(T, T):int] */
    public static final /* synthetic */ LinkedHashMap access$getUserEntries$p(MediaDataFilter mediaDataFilter) {
        return mediaDataFilter.userEntries;
    }

    public final boolean addListener(MediaDataManager.Listener listener) {
        return this._listeners.add(listener);
    }

    public final Set<MediaDataManager.Listener> getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return CollectionsKt___CollectionsKt.toSet(this._listeners);
    }

    public final MediaDataManager getMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        MediaDataManager mediaDataManager = this.mediaDataManager;
        if (mediaDataManager != null) {
            return mediaDataManager;
        }
        return null;
    }

    @VisibleForTesting
    public final void handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i) {
        Set<MediaDataManager.Listener> listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        List<String> mutableList = CollectionsKt___CollectionsKt.toMutableList(this.userEntries.keySet());
        this.userEntries.clear();
        for (String str : mutableList) {
            Log.d("MediaDataFilter", "Removing " + str + " after user change");
            for (MediaDataManager.Listener listener : listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                listener.onMediaDataRemoved(str);
            }
        }
        for (Map.Entry<String, MediaData> entry : this.allEntries.entrySet()) {
            String key = entry.getKey();
            MediaData value = entry.getValue();
            if (this.lockscreenUserManager.isCurrentProfile(value.getUserId())) {
                Log.d("MediaDataFilter", "Re-adding " + key + " after user change");
                this.userEntries.put(key, value);
                for (MediaDataManager.Listener listener2 : listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(listener2, key, null, value, false, 0, false, 56, null);
                }
            }
        }
    }

    public final boolean hasActiveMedia() {
        boolean z;
        LinkedHashMap<String, MediaData> linkedHashMap = this.userEntries;
        if (!linkedHashMap.isEmpty()) {
            Iterator<Map.Entry<String, MediaData>> it = linkedHashMap.entrySet().iterator();
            while (true) {
                z = false;
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getValue().getActive()) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    public final boolean hasActiveMediaOrRecommendation() {
        boolean z;
        LinkedHashMap<String, MediaData> linkedHashMap = this.userEntries;
        if (!linkedHashMap.isEmpty()) {
            for (Map.Entry<String, MediaData> entry : linkedHashMap.entrySet()) {
                if (entry.getValue().getActive()) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        boolean z2 = true;
        if (!z) {
            if (this.smartspaceMediaData.isActive()) {
                z2 = true;
                if (!this.smartspaceMediaData.isValid()) {
                    if (this.reactivatedKey != null) {
                        z2 = true;
                    }
                }
            }
            z2 = false;
        }
        return z2;
    }

    public final boolean hasAnyMedia() {
        return !this.userEntries.isEmpty();
    }

    public final boolean hasAnyMediaOrRecommendation() {
        boolean z = true;
        if (!(!this.userEntries.isEmpty())) {
            z = this.smartspaceMediaData.isActive() && this.smartspaceMediaData.isValid();
        }
        return z;
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        if (str2 != null && !Intrinsics.areEqual(str2, str)) {
            this.allEntries.remove(str2);
        }
        this.allEntries.put(str, mediaData);
        if (this.lockscreenUserManager.isCurrentProfile(mediaData.getUserId())) {
            if (str2 != null && !Intrinsics.areEqual(str2, str)) {
                this.userEntries.remove(str2);
            }
            this.userEntries.put(str, mediaData);
            for (MediaDataManager.Listener listener : getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(listener, str, str2, mediaData, false, 0, false, 56, null);
            }
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        this.allEntries.remove(str);
        if (this.userEntries.remove(str) != null) {
            for (MediaDataManager.Listener listener : getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                listener.onMediaDataRemoved(str);
            }
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        if (!smartspaceMediaData.isActive()) {
            Log.d("MediaDataFilter", "Inactive recommendation data. Skip triggering.");
            return;
        }
        this.smartspaceMediaData = smartspaceMediaData;
        SortedMap<String, MediaData> sortedMap = MapsKt__MapsJVMKt.toSortedMap(this.userEntries, new Comparator() { // from class: com.android.systemui.media.controls.pipeline.MediaDataFilter$onSmartspaceMediaDataLoaded$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                MediaData mediaData = (MediaData) MediaDataFilter.access$getUserEntries$p(MediaDataFilter.this).get((String) t);
                Comparable comparable = -1;
                Comparable valueOf = mediaData != null ? Long.valueOf(mediaData.getLastActive()) : -1;
                MediaData mediaData2 = (MediaData) MediaDataFilter.access$getUserEntries$p(MediaDataFilter.this).get((String) t2);
                if (mediaData2 != null) {
                    comparable = Long.valueOf(mediaData2.getLastActive());
                }
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, comparable);
            }
        });
        long timeSinceActiveForMostRecentMedia = timeSinceActiveForMostRecentMedia(sortedMap);
        long smartspace_max_age = MediaDataFilterKt.getSMARTSPACE_MAX_AGE();
        SmartspaceAction cardAction = smartspaceMediaData.getCardAction();
        long j = smartspace_max_age;
        if (cardAction != null) {
            long j2 = cardAction.getExtras().getLong("resumable_media_max_age_seconds", 0L);
            j = smartspace_max_age;
            if (j2 > 0) {
                j = TimeUnit.SECONDS.toMillis(j2);
            }
        }
        boolean z2 = true;
        boolean z3 = !hasActiveMedia() && hasAnyMedia();
        if (timeSinceActiveForMostRecentMedia < j) {
            if (z3) {
                String lastKey = sortedMap.lastKey();
                Log.d("MediaDataFilter", "reactivating " + lastKey + " instead of smartspace");
                this.reactivatedKey = lastKey;
                MediaData mediaData = sortedMap.get(lastKey);
                Intrinsics.checkNotNull(mediaData);
                MediaData copy$default = MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, null, null, null, null, true, null, 0, false, null, false, null, false, 0L, null, 0, 33538047, null);
                this.logger.logRecommendationActivated(copy$default.getAppUid(), copy$default.getPackageName(), copy$default.getInstanceId());
                for (MediaDataManager.Listener listener : getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(listener, lastKey, lastKey, copy$default, false, (int) (this.systemClock.currentTimeMillis() - smartspaceMediaData.getHeadphoneConnectionTimeMillis()), true, 8, null);
                }
            }
            z2 = false;
        }
        if (!smartspaceMediaData.isValid()) {
            Log.d("MediaDataFilter", "Invalid recommendation data. Skip showing the rec card");
            return;
        }
        this.logger.logRecommendationAdded(this.smartspaceMediaData.getPackageName(), this.smartspaceMediaData.getInstanceId());
        for (MediaDataManager.Listener listener2 : getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
            listener2.onSmartspaceMediaDataLoaded(str, smartspaceMediaData, z2);
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        String str2 = this.reactivatedKey;
        if (str2 != null) {
            this.reactivatedKey = null;
            Log.d("MediaDataFilter", "expiring reactivated key " + str2);
            MediaData mediaData = this.userEntries.get(str2);
            if (mediaData != null) {
                for (MediaDataManager.Listener listener : getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(listener, str2, str2, mediaData, z, 0, false, 48, null);
                }
            }
        }
        if (this.smartspaceMediaData.isActive()) {
            this.smartspaceMediaData = SmartspaceMediaData.copy$default(MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA(), this.smartspaceMediaData.getTargetId(), false, null, null, null, null, 0L, this.smartspaceMediaData.getInstanceId(), R$styleable.AppCompatTheme_windowNoTitle, null);
        }
        for (MediaDataManager.Listener listener2 : getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
            listener2.onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public final void onSwipeToDismiss() {
        Log.d("MediaDataFilter", "Media carousel swiped away");
        for (String str : CollectionsKt___CollectionsKt.toSet(this.userEntries.keySet())) {
            getMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core().setTimedOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core(str, true, true);
        }
        if (this.smartspaceMediaData.isActive()) {
            Intent dismissIntent = this.smartspaceMediaData.getDismissIntent();
            if (dismissIntent == null) {
                Log.w("MediaDataFilter", "Cannot create dismiss action click action: extras missing dismiss_intent.");
            } else if (dismissIntent.getComponent() == null || !Intrinsics.areEqual(dismissIntent.getComponent().getClassName(), "com.google.android.apps.gsa.staticplugins.opa.smartspace.ExportedSmartspaceTrampolineActivity")) {
                this.broadcastSender.sendBroadcast(dismissIntent);
            } else {
                this.context.startActivity(dismissIntent);
            }
            this.smartspaceMediaData = SmartspaceMediaData.copy$default(MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA(), this.smartspaceMediaData.getTargetId(), false, null, null, null, null, 0L, this.smartspaceMediaData.getInstanceId(), R$styleable.AppCompatTheme_windowNoTitle, null);
            getMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core().dismissSmartspaceRecommendation(this.smartspaceMediaData.getTargetId(), 0L);
        }
    }

    public final boolean removeListener(MediaDataManager.Listener listener) {
        return this._listeners.remove(listener);
    }

    public final void setMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core(MediaDataManager mediaDataManager) {
        this.mediaDataManager = mediaDataManager;
    }

    public final long timeSinceActiveForMostRecentMedia(SortedMap<String, MediaData> sortedMap) {
        long j = Long.MAX_VALUE;
        if (sortedMap.isEmpty()) {
            return RecyclerView.FOREVER_NS;
        }
        long elapsedRealtime = this.systemClock.elapsedRealtime();
        MediaData mediaData = sortedMap.get(sortedMap.lastKey());
        if (mediaData != null) {
            j = elapsedRealtime - mediaData.getLastActive();
        }
        return j;
    }
}