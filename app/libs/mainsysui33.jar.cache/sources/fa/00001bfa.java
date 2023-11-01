package com.android.systemui.media.controls.pipeline;

import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaDeviceManager;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataCombineLatest.class */
public final class MediaDataCombineLatest implements MediaDataManager.Listener, MediaDeviceManager.Listener {
    public final Set<MediaDataManager.Listener> listeners = new LinkedHashSet();
    public final Map<String, Pair<MediaData, MediaDeviceData>> entries = new LinkedHashMap();

    public final boolean addListener(MediaDataManager.Listener listener) {
        return this.listeners.add(listener);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDeviceManager.Listener
    public void onKeyRemoved(String str) {
        remove(str);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        MediaDeviceData mediaDeviceData = null;
        if (str2 != null && !Intrinsics.areEqual(str2, str) && this.entries.containsKey(str2)) {
            Map<String, Pair<MediaData, MediaDeviceData>> map = this.entries;
            Pair<MediaData, MediaDeviceData> remove = map.remove(str2);
            if (remove != null) {
                mediaDeviceData = (MediaDeviceData) remove.getSecond();
            }
            map.put(str, TuplesKt.to(mediaData, mediaDeviceData));
            update(str, str2);
            return;
        }
        Map<String, Pair<MediaData, MediaDeviceData>> map2 = this.entries;
        Pair<MediaData, MediaDeviceData> pair = map2.get(str);
        MediaDeviceData mediaDeviceData2 = null;
        if (pair != null) {
            mediaDeviceData2 = (MediaDeviceData) pair.getSecond();
        }
        map2.put(str, TuplesKt.to(mediaData, mediaDeviceData2));
        update(str, str);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        remove(str);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDeviceManager.Listener
    public void onMediaDeviceChanged(String str, String str2, MediaDeviceData mediaDeviceData) {
        MediaData mediaData = null;
        if (str2 != null && !Intrinsics.areEqual(str2, str) && this.entries.containsKey(str2)) {
            Map<String, Pair<MediaData, MediaDeviceData>> map = this.entries;
            Pair<MediaData, MediaDeviceData> remove = map.remove(str2);
            if (remove != null) {
                mediaData = (MediaData) remove.getFirst();
            }
            map.put(str, TuplesKt.to(mediaData, mediaDeviceData));
            update(str, str2);
            return;
        }
        Map<String, Pair<MediaData, MediaDeviceData>> map2 = this.entries;
        Pair<MediaData, MediaDeviceData> pair = map2.get(str);
        MediaData mediaData2 = null;
        if (pair != null) {
            mediaData2 = (MediaData) pair.getFirst();
        }
        map2.put(str, TuplesKt.to(mediaData2, mediaDeviceData));
        update(str, str);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        for (MediaDataManager.Listener listener : CollectionsKt___CollectionsKt.toSet(this.listeners)) {
            MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded$default(listener, str, smartspaceMediaData, false, 4, null);
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        for (MediaDataManager.Listener listener : CollectionsKt___CollectionsKt.toSet(this.listeners)) {
            listener.onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public final void remove(String str) {
        if (this.entries.remove(str) != null) {
            for (MediaDataManager.Listener listener : CollectionsKt___CollectionsKt.toSet(this.listeners)) {
                listener.onMediaDataRemoved(str);
            }
        }
    }

    public final void update(String str, String str2) {
        Pair<MediaData, MediaDeviceData> pair = this.entries.get(str);
        Pair<MediaData, MediaDeviceData> pair2 = pair;
        if (pair == null) {
            pair2 = TuplesKt.to((Object) null, (Object) null);
        }
        MediaData mediaData = (MediaData) pair2.component1();
        MediaDeviceData mediaDeviceData = (MediaDeviceData) pair2.component2();
        if (mediaData == null || mediaDeviceData == null) {
            return;
        }
        MediaData copy$default = MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, null, null, null, mediaDeviceData, false, null, 0, false, null, false, null, false, 0L, null, 0, 33546239, null);
        for (MediaDataManager.Listener listener : CollectionsKt___CollectionsKt.toSet(this.listeners)) {
            MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(listener, str, str2, copy$default, false, 0, false, 56, null);
        }
    }
}