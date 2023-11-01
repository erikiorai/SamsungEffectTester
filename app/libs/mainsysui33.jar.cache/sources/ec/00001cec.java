package com.android.systemui.media.controls.ui;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.ui.MediaPlayerData;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import kotlin.Triple;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaPlayerData.class */
public final class MediaPlayerData {
    public static final Comparator<MediaSortKey> comparator;
    public static final Map<String, MediaSortKey> mediaData;
    public static final TreeMap<MediaSortKey, MediaControlPanel> mediaPlayers;
    public static boolean shouldPrioritizeSs;
    public static SmartspaceMediaData smartspaceMediaData;
    public static final LinkedHashMap<String, MediaSortKey> visibleMediaPlayers;
    public static final MediaPlayerData INSTANCE = new MediaPlayerData();
    public static final MediaData EMPTY = new MediaData(-1, false, null, null, null, null, null, CollectionsKt__CollectionsKt.emptyList(), CollectionsKt__CollectionsKt.emptyList(), null, "INVALID", null, null, null, true, null, 0, false, null, false, null, false, 0, InstanceId.fakeInstanceId(-1), -1, 8323584, null);

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaPlayerData$MediaSortKey.class */
    public static final class MediaSortKey {
        public final MediaData data;
        public final boolean isSsMediaRec;
        public final boolean isSsReactivated;
        public final String key;
        public final long updateTime;

        public MediaSortKey(boolean z, MediaData mediaData, String str, long j, boolean z2) {
            this.isSsMediaRec = z;
            this.data = mediaData;
            this.key = str;
            this.updateTime = j;
            this.isSsReactivated = z2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof MediaSortKey) {
                MediaSortKey mediaSortKey = (MediaSortKey) obj;
                return this.isSsMediaRec == mediaSortKey.isSsMediaRec && Intrinsics.areEqual(this.data, mediaSortKey.data) && Intrinsics.areEqual(this.key, mediaSortKey.key) && this.updateTime == mediaSortKey.updateTime && this.isSsReactivated == mediaSortKey.isSsReactivated;
            }
            return false;
        }

        public final MediaData getData() {
            return this.data;
        }

        public final String getKey() {
            return this.key;
        }

        public final long getUpdateTime() {
            return this.updateTime;
        }

        public int hashCode() {
            boolean z = this.isSsMediaRec;
            int i = 1;
            boolean z2 = z;
            if (z) {
                z2 = true;
            }
            int hashCode = this.data.hashCode();
            int hashCode2 = this.key.hashCode();
            int hashCode3 = Long.hashCode(this.updateTime);
            boolean z3 = this.isSsReactivated;
            if (!z3) {
                i = z3 ? 1 : 0;
            }
            return ((((((((z2 ? 1 : 0) * 31) + hashCode) * 31) + hashCode2) * 31) + hashCode3) * 31) + i;
        }

        public final boolean isSsMediaRec() {
            return this.isSsMediaRec;
        }

        public final boolean isSsReactivated() {
            return this.isSsReactivated;
        }

        public String toString() {
            boolean z = this.isSsMediaRec;
            MediaData mediaData = this.data;
            String str = this.key;
            long j = this.updateTime;
            boolean z2 = this.isSsReactivated;
            return "MediaSortKey(isSsMediaRec=" + z + ", data=" + mediaData + ", key=" + str + ", updateTime=" + j + ", isSsReactivated=" + z2 + ")";
        }
    }

    static {
        final Comparator comparator2 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$compareByDescending$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) t2;
                Boolean isPlaying = mediaSortKey.getData().isPlaying();
                Boolean bool = Boolean.TRUE;
                boolean z = true;
                boolean z2 = Intrinsics.areEqual(isPlaying, bool) && mediaSortKey.getData().getPlaybackLocation() == 0;
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) t;
                if (!Intrinsics.areEqual(mediaSortKey2.getData().isPlaying(), bool) || mediaSortKey2.getData().getPlaybackLocation() != 0) {
                    z = false;
                }
                return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(z2), Boolean.valueOf(z));
            }
        };
        final Comparator comparator3 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$thenByDescending$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator2.compare(t, t2);
                if (compare == 0) {
                    MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) t2;
                    Boolean isPlaying = mediaSortKey.getData().isPlaying();
                    Boolean bool = Boolean.TRUE;
                    boolean z = Intrinsics.areEqual(isPlaying, bool) && mediaSortKey.getData().getPlaybackLocation() == 1;
                    MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) t;
                    boolean z2 = false;
                    if (Intrinsics.areEqual(mediaSortKey2.getData().isPlaying(), bool)) {
                        z2 = false;
                        if (mediaSortKey2.getData().getPlaybackLocation() == 1) {
                            z2 = true;
                        }
                    }
                    compare = ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(z), Boolean.valueOf(z2));
                }
                return compare;
            }
        };
        final Comparator comparator4 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$thenByDescending$2
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator3.compare(t, t2);
                if (compare == 0) {
                    compare = ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((MediaPlayerData.MediaSortKey) t2).getData().getActive()), Boolean.valueOf(((MediaPlayerData.MediaSortKey) t).getData().getActive()));
                }
                return compare;
            }
        };
        final Comparator comparator5 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$thenByDescending$3
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator4.compare(t, t2);
                if (compare == 0) {
                    MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
                    boolean z = true;
                    boolean z2 = mediaPlayerData.getShouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core() == ((MediaPlayerData.MediaSortKey) t2).isSsMediaRec();
                    if (mediaPlayerData.getShouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core() != ((MediaPlayerData.MediaSortKey) t).isSsMediaRec()) {
                        z = false;
                    }
                    compare = ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(z2), Boolean.valueOf(z));
                }
                return compare;
            }
        };
        final Comparator comparator6 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$thenByDescending$4
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator5.compare(t, t2);
                if (compare == 0) {
                    compare = ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(!((MediaPlayerData.MediaSortKey) t2).getData().getResumption()), Boolean.valueOf(!((MediaPlayerData.MediaSortKey) t).getData().getResumption()));
                }
                return compare;
            }
        };
        final Comparator comparator7 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$thenByDescending$5
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator6.compare(t, t2);
                if (compare == 0) {
                    boolean z = true;
                    boolean z2 = ((MediaPlayerData.MediaSortKey) t2).getData().getPlaybackLocation() != 2;
                    if (((MediaPlayerData.MediaSortKey) t).getData().getPlaybackLocation() == 2) {
                        z = false;
                    }
                    compare = ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(z2), Boolean.valueOf(z));
                }
                return compare;
            }
        };
        final Comparator comparator8 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$thenByDescending$6
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator7.compare(t, t2);
                if (compare == 0) {
                    compare = ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaPlayerData.MediaSortKey) t2).getData().getLastActive()), Long.valueOf(((MediaPlayerData.MediaSortKey) t).getData().getLastActive()));
                }
                return compare;
            }
        };
        final Comparator comparator9 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$thenByDescending$7
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator8.compare(t, t2);
                if (compare == 0) {
                    compare = ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaPlayerData.MediaSortKey) t2).getUpdateTime()), Long.valueOf(((MediaPlayerData.MediaSortKey) t).getUpdateTime()));
                }
                return compare;
            }
        };
        Comparator<MediaSortKey> comparator10 = new Comparator() { // from class: com.android.systemui.media.controls.ui.MediaPlayerData$special$$inlined$thenByDescending$8
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compare = comparator9.compare(t, t2);
                if (compare == 0) {
                    compare = ComparisonsKt__ComparisonsKt.compareValues(((MediaPlayerData.MediaSortKey) t2).getData().getNotificationKey(), ((MediaPlayerData.MediaSortKey) t).getData().getNotificationKey());
                }
                return compare;
            }
        };
        comparator = comparator10;
        mediaPlayers = new TreeMap<>(comparator10);
        mediaData = new LinkedHashMap();
        visibleMediaPlayers = new LinkedHashMap<>();
    }

    private MediaPlayerData() {
    }

    public static /* synthetic */ void moveIfExists$default(MediaPlayerData mediaPlayerData, String str, String str2, MediaCarouselControllerLogger mediaCarouselControllerLogger, int i, Object obj) {
        if ((i & 4) != 0) {
            mediaCarouselControllerLogger = null;
        }
        mediaPlayerData.moveIfExists(str, str2, mediaCarouselControllerLogger);
    }

    public static /* synthetic */ MediaControlPanel removeMediaPlayer$default(MediaPlayerData mediaPlayerData, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return mediaPlayerData.removeMediaPlayer(str, z);
    }

    public final void addMediaPlayer(String str, MediaData mediaData2, MediaControlPanel mediaControlPanel, SystemClock systemClock, boolean z, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        MediaControlPanel removeMediaPlayer$default = removeMediaPlayer$default(this, str, false, 2, null);
        if (removeMediaPlayer$default != null && !Intrinsics.areEqual(removeMediaPlayer$default, mediaControlPanel) && mediaCarouselControllerLogger != null) {
            mediaCarouselControllerLogger.logPotentialMemoryLeak(str);
        }
        MediaSortKey mediaSortKey = new MediaSortKey(false, mediaData2, str, systemClock.currentTimeMillis(), z);
        mediaData.put(str, mediaSortKey);
        mediaPlayers.put(mediaSortKey, mediaControlPanel);
        visibleMediaPlayers.put(str, mediaSortKey);
    }

    public final void addMediaRecommendation(String str, SmartspaceMediaData smartspaceMediaData2, MediaControlPanel mediaControlPanel, boolean z, SystemClock systemClock, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        shouldPrioritizeSs = z;
        MediaControlPanel removeMediaPlayer$default = removeMediaPlayer$default(this, str, false, 2, null);
        if (removeMediaPlayer$default != null && !Intrinsics.areEqual(removeMediaPlayer$default, mediaControlPanel) && mediaCarouselControllerLogger != null) {
            mediaCarouselControllerLogger.logPotentialMemoryLeak(str);
        }
        MediaSortKey mediaSortKey = new MediaSortKey(true, MediaData.copy$default(EMPTY, 0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, Boolean.FALSE, false, 0L, null, 0, 32505855, null), str, systemClock.currentTimeMillis(), true);
        mediaData.put(str, mediaSortKey);
        mediaPlayers.put(mediaSortKey, mediaControlPanel);
        visibleMediaPlayers.put(str, mediaSortKey);
        smartspaceMediaData = smartspaceMediaData2;
    }

    public final void clear() {
        mediaData.clear();
        mediaPlayers.clear();
        visibleMediaPlayers.clear();
    }

    public final Set<String> dataKeys() {
        return mediaData.keySet();
    }

    public final int firstActiveMediaIndex() {
        int i = 0;
        for (Object obj : mediaPlayers.entrySet()) {
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            Map.Entry entry = (Map.Entry) obj;
            if (!((MediaSortKey) entry.getKey()).isSsMediaRec() && ((MediaSortKey) entry.getKey()).getData().getActive()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public final MediaControlPanel getMediaControlPanel(int i) {
        return mediaPlayers.get(CollectionsKt___CollectionsKt.elementAt(visiblePlayerKeys(), i));
    }

    public final MediaControlPanel getMediaPlayer(String str) {
        MediaSortKey mediaSortKey = mediaData.get(str);
        return mediaSortKey != null ? mediaPlayers.get(mediaSortKey) : null;
    }

    public final int getMediaPlayerIndex(String str) {
        MediaSortKey mediaSortKey = mediaData.get(str);
        int i = 0;
        for (Object obj : mediaPlayers.entrySet()) {
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            if (Intrinsics.areEqual(((Map.Entry) obj).getKey(), mediaSortKey)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public final boolean getShouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return shouldPrioritizeSs;
    }

    public final SmartspaceMediaData getSmartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return smartspaceMediaData;
    }

    public final boolean hasActiveMediaOrRecommendationCard() {
        SmartspaceMediaData smartspaceMediaData2 = smartspaceMediaData;
        if (smartspaceMediaData2 != null) {
            Boolean valueOf = smartspaceMediaData2 != null ? Boolean.valueOf(smartspaceMediaData2.isActive()) : null;
            Intrinsics.checkNotNull(valueOf);
            if (valueOf.booleanValue()) {
                return true;
            }
        }
        return firstActiveMediaIndex() != -1;
    }

    public final boolean isSsReactivated(String str) {
        MediaSortKey mediaSortKey = mediaData.get(str);
        return mediaSortKey != null ? mediaSortKey.isSsReactivated() : false;
    }

    public final List<Triple<String, MediaData, Boolean>> mediaData() {
        Set<Map.Entry<String, MediaSortKey>> entrySet = mediaData.entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
        Iterator<T> it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            arrayList.add(new Triple(entry.getKey(), ((MediaSortKey) entry.getValue()).getData(), Boolean.valueOf(((MediaSortKey) entry.getValue()).isSsMediaRec())));
        }
        return arrayList;
    }

    public final void moveIfExists(String str, String str2, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        Map<String, MediaSortKey> map;
        MediaSortKey remove;
        if (str == null || Intrinsics.areEqual(str, str2) || (remove = (map = mediaData).remove(str)) == null) {
            return;
        }
        if (removeMediaPlayer$default(INSTANCE, str2, false, 2, null) != null && mediaCarouselControllerLogger != null) {
            mediaCarouselControllerLogger.logPotentialMemoryLeak(str2);
        }
        map.put(str2, remove);
    }

    public final Set<MediaSortKey> playerKeys() {
        return mediaPlayers.keySet();
    }

    public final Collection<MediaControlPanel> players() {
        return mediaPlayers.values();
    }

    public final MediaControlPanel removeMediaPlayer(String str, boolean z) {
        MediaSortKey remove = mediaData.remove(str);
        MediaControlPanel mediaControlPanel = null;
        if (remove != null) {
            if (remove.isSsMediaRec()) {
                smartspaceMediaData = null;
            }
            if (z) {
                visibleMediaPlayers.remove(str);
            }
            mediaControlPanel = mediaPlayers.remove(remove);
        }
        return mediaControlPanel;
    }

    public final String smartspaceMediaKey() {
        Iterator<T> it = mediaData.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (((MediaSortKey) entry.getValue()).isSsMediaRec()) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public final void updateVisibleMediaPlayers() {
        visibleMediaPlayers.clear();
        for (MediaSortKey mediaSortKey : playerKeys()) {
            visibleMediaPlayers.put(mediaSortKey.getKey(), mediaSortKey);
        }
    }

    public final Collection<MediaSortKey> visiblePlayerKeys() {
        return visibleMediaPlayers.values();
    }
}