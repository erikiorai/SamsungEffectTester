package com.android.systemui.media.controls.pipeline;

import android.content.ComponentName;
import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.util.Log;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaSessionBasedFilter.class */
public final class MediaSessionBasedFilter implements MediaDataManager.Listener {
    public final Executor backgroundExecutor;
    public final Executor foregroundExecutor;
    public final MediaSessionManager sessionManager;
    public final Set<MediaDataManager.Listener> listeners = new LinkedHashSet();
    public final LinkedHashMap<String, List<MediaController>> packageControllers = new LinkedHashMap<>();
    public final Map<String, Set<TokenId>> keyedTokens = new LinkedHashMap();
    public final Set<TokenId> tokensWithNotifications = new LinkedHashSet();
    public final MediaSessionBasedFilter$sessionListener$1 sessionListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$sessionListener$1
        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public void onActiveSessionsChanged(List<MediaController> list) {
            MediaSessionBasedFilter.access$handleControllersChanged(MediaSessionBasedFilter.this, list);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaSessionBasedFilter$TokenId.class */
    public static final class TokenId {
        public final int id;

        public TokenId(int i) {
            this.id = i;
        }

        public TokenId(MediaSession.Token token) {
            this(token.hashCode());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof TokenId) && this.id == ((TokenId) obj).id;
        }

        public int hashCode() {
            return Integer.hashCode(this.id);
        }

        public String toString() {
            int i = this.id;
            return "TokenId(id=" + i + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$sessionListener$1] */
    public MediaSessionBasedFilter(final Context context, MediaSessionManager mediaSessionManager, Executor executor, Executor executor2) {
        this.sessionManager = mediaSessionManager;
        this.foregroundExecutor = executor;
        this.backgroundExecutor = executor2;
        executor2.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter.1
            @Override // java.lang.Runnable
            public final void run() {
                ComponentName componentName = new ComponentName(context, NotificationListenerWithPlugins.class);
                this.sessionManager.addOnActiveSessionsChangedListener(this.sessionListener, componentName);
                MediaSessionBasedFilter mediaSessionBasedFilter = this;
                mediaSessionBasedFilter.handleControllersChanged(mediaSessionBasedFilter.sessionManager.getActiveSessions(componentName));
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataLoaded$1.run():void] */
    public static final /* synthetic */ void access$dispatchMediaDataLoaded(MediaSessionBasedFilter mediaSessionBasedFilter, String str, String str2, MediaData mediaData, boolean z) {
        mediaSessionBasedFilter.dispatchMediaDataLoaded(str, str2, mediaData, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataLoaded$1.run():void, com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataRemoved$1.run():void] */
    public static final /* synthetic */ void access$dispatchMediaDataRemoved(MediaSessionBasedFilter mediaSessionBasedFilter, String str) {
        mediaSessionBasedFilter.dispatchMediaDataRemoved(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onSmartspaceMediaDataLoaded$1.run():void] */
    public static final /* synthetic */ void access$dispatchSmartspaceMediaDataLoaded(MediaSessionBasedFilter mediaSessionBasedFilter, String str, SmartspaceMediaData smartspaceMediaData) {
        mediaSessionBasedFilter.dispatchSmartspaceMediaDataLoaded(str, smartspaceMediaData);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onSmartspaceMediaDataRemoved$1.run():void] */
    public static final /* synthetic */ void access$dispatchSmartspaceMediaDataRemoved(MediaSessionBasedFilter mediaSessionBasedFilter, String str, boolean z) {
        mediaSessionBasedFilter.dispatchSmartspaceMediaDataRemoved(str, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataLoaded$1.run():void, com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataRemoved$1.run():void] */
    public static final /* synthetic */ Map access$getKeyedTokens$p(MediaSessionBasedFilter mediaSessionBasedFilter) {
        return mediaSessionBasedFilter.keyedTokens;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataLoaded$1.run():void] */
    public static final /* synthetic */ LinkedHashMap access$getPackageControllers$p(MediaSessionBasedFilter mediaSessionBasedFilter) {
        return mediaSessionBasedFilter.packageControllers;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataLoaded$1.run():void] */
    public static final /* synthetic */ Set access$getTokensWithNotifications$p(MediaSessionBasedFilter mediaSessionBasedFilter) {
        return mediaSessionBasedFilter.tokensWithNotifications;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$sessionListener$1.onActiveSessionsChanged(java.util.List<android.media.session.MediaController>):void] */
    public static final /* synthetic */ void access$handleControllersChanged(MediaSessionBasedFilter mediaSessionBasedFilter, List list) {
        mediaSessionBasedFilter.handleControllersChanged(list);
    }

    public final boolean addListener(MediaDataManager.Listener listener) {
        return this.listeners.add(listener);
    }

    public final void dispatchMediaDataLoaded(final String str, final String str2, final MediaData mediaData, final boolean z) {
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$dispatchMediaDataLoaded$1
            @Override // java.lang.Runnable
            public final void run() {
                Set set;
                set = MediaSessionBasedFilter.this.listeners;
                Set<MediaDataManager.Listener> set2 = CollectionsKt___CollectionsKt.toSet(set);
                String str3 = str;
                String str4 = str2;
                MediaData mediaData2 = mediaData;
                boolean z2 = z;
                for (MediaDataManager.Listener listener : set2) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(listener, str3, str4, mediaData2, z2, 0, false, 48, null);
                }
            }
        });
    }

    public final void dispatchMediaDataRemoved(final String str) {
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$dispatchMediaDataRemoved$1
            @Override // java.lang.Runnable
            public final void run() {
                Set set;
                set = MediaSessionBasedFilter.this.listeners;
                Set<MediaDataManager.Listener> set2 = CollectionsKt___CollectionsKt.toSet(set);
                String str2 = str;
                for (MediaDataManager.Listener listener : set2) {
                    listener.onMediaDataRemoved(str2);
                }
            }
        });
    }

    public final void dispatchSmartspaceMediaDataLoaded(final String str, final SmartspaceMediaData smartspaceMediaData) {
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$dispatchSmartspaceMediaDataLoaded$1
            @Override // java.lang.Runnable
            public final void run() {
                Set set;
                set = MediaSessionBasedFilter.this.listeners;
                Set<MediaDataManager.Listener> set2 = CollectionsKt___CollectionsKt.toSet(set);
                String str2 = str;
                SmartspaceMediaData smartspaceMediaData2 = smartspaceMediaData;
                for (MediaDataManager.Listener listener : set2) {
                    MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded$default(listener, str2, smartspaceMediaData2, false, 4, null);
                }
            }
        });
    }

    public final void dispatchSmartspaceMediaDataRemoved(final String str, final boolean z) {
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$dispatchSmartspaceMediaDataRemoved$1
            @Override // java.lang.Runnable
            public final void run() {
                Set set;
                set = MediaSessionBasedFilter.this.listeners;
                Set<MediaDataManager.Listener> set2 = CollectionsKt___CollectionsKt.toSet(set);
                String str2 = str;
                boolean z2 = z;
                for (MediaDataManager.Listener listener : set2) {
                    listener.onSmartspaceMediaDataRemoved(str2, z2);
                }
            }
        });
    }

    public final void handleControllersChanged(List<MediaController> list) {
        this.packageControllers.clear();
        List<MediaController> list2 = list;
        for (MediaController mediaController : list2) {
            List<MediaController> list3 = this.packageControllers.get(mediaController.getPackageName());
            if (list3 != null) {
                list3.add(mediaController);
            } else {
                this.packageControllers.put(mediaController.getPackageName(), CollectionsKt__CollectionsKt.mutableListOf(new MediaController[]{mediaController}));
            }
        }
        Set<TokenId> set = this.tokensWithNotifications;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (MediaController mediaController2 : list2) {
            arrayList.add(new TokenId(mediaController2.getSessionToken()));
        }
        set.retainAll(arrayList);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataLoaded(final String str, final String str2, final MediaData mediaData, final boolean z, int i, boolean z2) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataLoaded$1
            @Override // java.lang.Runnable
            public final void run() {
                ArrayList arrayList;
                MediaSession.Token token = MediaData.this.getToken();
                if (token != null) {
                    MediaSessionBasedFilter.access$getTokensWithNotifications$p(this).add(new MediaSessionBasedFilter.TokenId(token));
                }
                String str3 = str2;
                boolean z3 = (str3 == null || Intrinsics.areEqual(str, str3)) ? false : true;
                if (z3) {
                    Set set = (Set) TypeIntrinsics.asMutableMap(MediaSessionBasedFilter.access$getKeyedTokens$p(this)).remove(str2);
                    if (set != null) {
                        Set set2 = (Set) MediaSessionBasedFilter.access$getKeyedTokens$p(this).put(str, set);
                    }
                }
                if (MediaData.this.getToken() != null) {
                    Set set3 = (Set) MediaSessionBasedFilter.access$getKeyedTokens$p(this).get(str);
                    if (set3 != null) {
                        set3.add(new MediaSessionBasedFilter.TokenId(MediaData.this.getToken()));
                    } else {
                        Set set4 = (Set) MediaSessionBasedFilter.access$getKeyedTokens$p(this).put(str, SetsKt__SetsKt.mutableSetOf(new MediaSessionBasedFilter.TokenId[]{new MediaSessionBasedFilter.TokenId(MediaData.this.getToken())}));
                    }
                }
                List list = (List) MediaSessionBasedFilter.access$getPackageControllers$p(this).get(MediaData.this.getPackageName());
                MediaController mediaController = null;
                if (list != null) {
                    List list2 = list;
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = list2.iterator();
                    while (true) {
                        arrayList = arrayList2;
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        MediaController.PlaybackInfo playbackInfo = ((MediaController) next).getPlaybackInfo();
                        if (playbackInfo != null && playbackInfo.getPlaybackType() == 2) {
                            arrayList2.add(next);
                        }
                    }
                } else {
                    arrayList = null;
                }
                if (arrayList != null && arrayList.size() == 1) {
                    mediaController = (MediaController) CollectionsKt___CollectionsKt.firstOrNull(arrayList);
                }
                if (z3 || mediaController == null || Intrinsics.areEqual(mediaController.getSessionToken(), MediaData.this.getToken()) || !MediaSessionBasedFilter.access$getTokensWithNotifications$p(this).contains(new MediaSessionBasedFilter.TokenId(mediaController.getSessionToken()))) {
                    MediaSessionBasedFilter.access$dispatchMediaDataLoaded(this, str, str2, MediaData.this, z);
                    return;
                }
                Log.d("MediaSessionBasedFilter", "filtering key=" + str + " local=" + MediaData.this.getToken() + " remote=" + mediaController.getSessionToken());
                Object obj = MediaSessionBasedFilter.access$getKeyedTokens$p(this).get(str);
                Intrinsics.checkNotNull(obj);
                if (((Set) obj).contains(new MediaSessionBasedFilter.TokenId(mediaController.getSessionToken()))) {
                    return;
                }
                MediaSessionBasedFilter.access$dispatchMediaDataRemoved(this, str);
            }
        });
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataRemoved(final String str) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onMediaDataRemoved$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSessionBasedFilter.access$getKeyedTokens$p(MediaSessionBasedFilter.this).remove(str);
                MediaSessionBasedFilter.access$dispatchMediaDataRemoved(MediaSessionBasedFilter.this, str);
            }
        });
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(final String str, final SmartspaceMediaData smartspaceMediaData, boolean z) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onSmartspaceMediaDataLoaded$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSessionBasedFilter.access$dispatchSmartspaceMediaDataLoaded(MediaSessionBasedFilter.this, str, smartspaceMediaData);
            }
        });
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(final String str, final boolean z) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter$onSmartspaceMediaDataRemoved$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSessionBasedFilter.access$dispatchSmartspaceMediaDataRemoved(MediaSessionBasedFilter.this, str, z);
            }
        });
    }
}