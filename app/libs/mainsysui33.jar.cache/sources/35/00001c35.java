package com.android.systemui.media.controls.pipeline;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaTimeoutListener.class */
public final class MediaTimeoutListener implements MediaDataManager.Listener {
    public final MediaTimeoutLogger logger;
    public final DelayableExecutor mainExecutor;
    public final MediaControllerFactory mediaControllerFactory;
    public final Map<String, PlaybackStateListener> mediaListeners = new LinkedHashMap();
    public Function2<? super String, ? super PlaybackState, Unit> stateCallback;
    public final SystemClock systemClock;
    public Function2<? super String, ? super Boolean, Unit> timeoutCallback;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaTimeoutListener$PlaybackStateListener.class */
    public final class PlaybackStateListener extends MediaController.Callback {
        public Runnable cancellation;
        public boolean destroyed;
        public long expiration = RecyclerView.FOREVER_NS;
        public String key;
        public PlaybackState lastState;
        public MediaController mediaController;
        public MediaData mediaData;
        public Boolean resumption;
        public boolean timedOut;

        public PlaybackStateListener(String str, MediaData mediaData) {
            MediaTimeoutListener.this = r5;
            this.key = str;
            this.mediaData = mediaData;
            setMediaData(mediaData);
        }

        public final void destroy() {
            MediaController mediaController = this.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(this);
            }
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            this.destroyed = true;
        }

        public final void doTimeout() {
            this.cancellation = null;
            MediaTimeoutListener.this.logger.logTimeout(this.key);
            this.timedOut = true;
            this.expiration = RecyclerView.FOREVER_NS;
            MediaTimeoutListener.this.getTimeoutCallback().invoke(this.key, Boolean.valueOf(this.timedOut));
        }

        public final void expireMediaTimeout(String str, String str2) {
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                MediaTimeoutListener.this.logger.logTimeoutCancelled(str, str2);
                runnable.run();
            }
            this.expiration = RecyclerView.FOREVER_NS;
            this.cancellation = null;
        }

        public final Runnable getCancellation() {
            return this.cancellation;
        }

        public final boolean getDestroyed() {
            return this.destroyed;
        }

        public final long getExpiration() {
            return this.expiration;
        }

        public final boolean isPlaying() {
            PlaybackState playbackState = this.lastState;
            return playbackState != null ? isPlaying(playbackState.getState()) : false;
        }

        public final boolean isPlaying(int i) {
            return NotificationMediaManager.isPlayingState(i);
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            processState(playbackState, true);
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionDestroyed() {
            MediaTimeoutListener.this.logger.logSessionDestroyed(this.key);
            if (!Intrinsics.areEqual(this.resumption, Boolean.TRUE)) {
                destroy();
                return;
            }
            MediaController mediaController = this.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:107:0x0113  */
        /* JADX WARN: Removed duplicated region for block: B:118:0x01dc  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void processState(PlaybackState playbackState, boolean z) {
            boolean z2;
            boolean isPlaying;
            MediaTimeoutListener.this.logger.logPlaybackState(this.key, playbackState);
            boolean z3 = playbackState != null && isPlaying(playbackState.getState()) == isPlaying();
            PlaybackState playbackState2 = this.lastState;
            if (Intrinsics.areEqual(playbackState2 != null ? Long.valueOf(playbackState2.getActions()) : null, playbackState != null ? Long.valueOf(playbackState.getActions()) : null)) {
                MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
                PlaybackState playbackState3 = this.lastState;
                List<PlaybackState.CustomAction> customActions = playbackState3 != null ? playbackState3.getCustomActions() : null;
                List<PlaybackState.CustomAction> list = null;
                if (playbackState != null) {
                    list = playbackState.getCustomActions();
                }
                if (mediaTimeoutListener.areCustomActionListsEqual(customActions, list)) {
                    z2 = true;
                    boolean areEqual = true ^ Intrinsics.areEqual(this.resumption, Boolean.valueOf(this.mediaData.getResumption()));
                    this.lastState = playbackState;
                    if ((z2 || !z3) && playbackState != null && z) {
                        MediaTimeoutListener.this.logger.logStateCallback(this.key);
                        MediaTimeoutListener.this.getStateCallback().invoke(this.key, playbackState);
                    }
                    if (z3 || areEqual) {
                        this.resumption = Boolean.valueOf(this.mediaData.getResumption());
                        isPlaying = isPlaying();
                        if (!isPlaying) {
                            String str = this.key;
                            expireMediaTimeout(str, "playback started - " + playbackState + ", " + str);
                            this.timedOut = false;
                            if (z) {
                                MediaTimeoutListener.this.getTimeoutCallback().invoke(this.key, Boolean.valueOf(this.timedOut));
                                return;
                            }
                            return;
                        }
                        MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
                        String str2 = this.key;
                        Boolean bool = this.resumption;
                        Intrinsics.checkNotNull(bool);
                        mediaTimeoutLogger.logScheduleTimeout(str2, isPlaying, bool.booleanValue());
                        if (this.cancellation != null && !areEqual) {
                            MediaTimeoutListener.this.logger.logCancelIgnored(this.key);
                            return;
                        }
                        expireMediaTimeout(this.key, "PLAYBACK STATE CHANGED - " + playbackState + ", " + this.resumption);
                        long resume_media_timeout = this.mediaData.getResumption() ? MediaTimeoutListenerKt.getRESUME_MEDIA_TIMEOUT() : MediaTimeoutListenerKt.getPAUSED_MEDIA_TIMEOUT();
                        this.expiration = MediaTimeoutListener.this.systemClock.elapsedRealtime() + resume_media_timeout;
                        this.cancellation = MediaTimeoutListener.this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutListener$PlaybackStateListener$processState$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaTimeoutListener.PlaybackStateListener.this.doTimeout();
                            }
                        }, resume_media_timeout);
                        return;
                    }
                    return;
                }
            }
            z2 = false;
            boolean areEqual2 = true ^ Intrinsics.areEqual(this.resumption, Boolean.valueOf(this.mediaData.getResumption()));
            this.lastState = playbackState;
            if (z2) {
            }
            MediaTimeoutListener.this.logger.logStateCallback(this.key);
            MediaTimeoutListener.this.getStateCallback().invoke(this.key, playbackState);
            if (z3) {
            }
            this.resumption = Boolean.valueOf(this.mediaData.getResumption());
            isPlaying = isPlaying();
            if (!isPlaying) {
            }
        }

        public final void setKey(String str) {
            this.key = str;
        }

        public final void setMediaData(MediaData mediaData) {
            this.destroyed = false;
            MediaController mediaController = this.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(this);
            }
            this.mediaData = mediaData;
            MediaSession.Token token = mediaData.getToken();
            MediaController create = token != null ? MediaTimeoutListener.this.mediaControllerFactory.create(token) : null;
            this.mediaController = create;
            if (create != null) {
                create.registerCallback(this);
            }
            MediaController mediaController2 = this.mediaController;
            PlaybackState playbackState = null;
            if (mediaController2 != null) {
                playbackState = mediaController2.getPlaybackState();
            }
            processState(playbackState, false);
        }
    }

    public MediaTimeoutListener(MediaControllerFactory mediaControllerFactory, DelayableExecutor delayableExecutor, MediaTimeoutLogger mediaTimeoutLogger, SysuiStatusBarStateController sysuiStatusBarStateController, SystemClock systemClock) {
        this.mediaControllerFactory = mediaControllerFactory;
        this.mainExecutor = delayableExecutor;
        this.logger = mediaTimeoutLogger;
        this.systemClock = systemClock;
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutListener.1
            {
                MediaTimeoutListener.this = this;
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozingChanged(boolean z) {
                if (z) {
                    return;
                }
                Map map = MediaTimeoutListener.this.mediaListeners;
                MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
                for (Map.Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    PlaybackStateListener playbackStateListener = (PlaybackStateListener) entry.getValue();
                    if (playbackStateListener.getCancellation() != null && playbackStateListener.getExpiration() <= mediaTimeoutListener.systemClock.elapsedRealtime()) {
                        playbackStateListener.expireMediaTimeout(str, "timeout happened while dozing");
                        playbackStateListener.doTimeout();
                    }
                }
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaTimeoutListener$onMediaDataLoaded$2$1.run():void] */
    public static final /* synthetic */ MediaTimeoutLogger access$getLogger$p(MediaTimeoutListener mediaTimeoutListener) {
        return mediaTimeoutListener.logger;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaTimeoutListener$onMediaDataLoaded$2$1.run():void] */
    public static final /* synthetic */ Map access$getMediaListeners$p(MediaTimeoutListener mediaTimeoutListener) {
        return mediaTimeoutListener.mediaListeners;
    }

    public final boolean areCustomActionListsEqual(List<PlaybackState.CustomAction> list, List<PlaybackState.CustomAction> list2) {
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null || list.size() != list2.size()) {
            return false;
        }
        for (Pair pair : SequencesKt___SequencesKt.zip(CollectionsKt___CollectionsKt.asSequence(list), CollectionsKt___CollectionsKt.asSequence(list2))) {
            if (!areCustomActionsEqual((PlaybackState.CustomAction) pair.component1(), (PlaybackState.CustomAction) pair.component2())) {
                return false;
            }
        }
        return true;
    }

    public final boolean areCustomActionsEqual(PlaybackState.CustomAction customAction, PlaybackState.CustomAction customAction2) {
        if (Intrinsics.areEqual(customAction.getAction(), customAction2.getAction()) && Intrinsics.areEqual(customAction.getName(), customAction2.getName()) && customAction.getIcon() == customAction2.getIcon()) {
            if ((customAction.getExtras() == null) != (customAction2.getExtras() == null)) {
                return false;
            }
            if (customAction.getExtras() != null) {
                for (String str : customAction.getExtras().keySet()) {
                    if (!Intrinsics.areEqual(customAction.getExtras().get(str), customAction2.getExtras().get(str))) {
                        return false;
                    }
                }
                return true;
            }
            return true;
        }
        return false;
    }

    /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: kotlin.jvm.functions.Function2<? super java.lang.String, ? super android.media.session.PlaybackState, kotlin.Unit>, kotlin.jvm.functions.Function2<java.lang.String, android.media.session.PlaybackState, kotlin.Unit> */
    public final Function2<String, PlaybackState, Unit> getStateCallback() {
        Function2 function2 = this.stateCallback;
        if (function2 != null) {
            return function2;
        }
        return null;
    }

    /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit>, kotlin.jvm.functions.Function2<java.lang.String, java.lang.Boolean, kotlin.Unit> */
    public final Function2<String, Boolean, Unit> getTimeoutCallback() {
        Function2 function2 = this.timeoutCallback;
        if (function2 != null) {
            return function2;
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v31, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataLoaded(final String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        PlaybackStateListener playbackStateListener = this.mediaListeners.get(str);
        if (playbackStateListener == null) {
            playbackStateListener = null;
        } else if (!playbackStateListener.getDestroyed()) {
            return;
        } else {
            this.logger.logReuseListener(str);
        }
        boolean z3 = true;
        if ((str2 == null || Intrinsics.areEqual(str, str2)) ? false : true) {
            playbackStateListener = TypeIntrinsics.asMutableMap(this.mediaListeners).remove(str2);
            MediaTimeoutLogger mediaTimeoutLogger = this.logger;
            if (playbackStateListener == null) {
                z3 = false;
            }
            mediaTimeoutLogger.logMigrateListener(str2, str, z3);
        }
        PlaybackStateListener playbackStateListener2 = playbackStateListener;
        if (playbackStateListener2 == null) {
            this.mediaListeners.put(str, new PlaybackStateListener(str, mediaData));
            return;
        }
        boolean isPlaying = playbackStateListener2.isPlaying();
        this.logger.logUpdateListener(str, isPlaying);
        playbackStateListener2.setMediaData(mediaData);
        playbackStateListener2.setKey(str);
        this.mediaListeners.put(str, playbackStateListener2);
        if (isPlaying != playbackStateListener2.isPlaying()) {
            this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutListener$onMediaDataLoaded$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaTimeoutListener.PlaybackStateListener playbackStateListener3 = (MediaTimeoutListener.PlaybackStateListener) MediaTimeoutListener.access$getMediaListeners$p(MediaTimeoutListener.this).get(str);
                    boolean z4 = true;
                    if (playbackStateListener3 == null || !playbackStateListener3.isPlaying()) {
                        z4 = false;
                    }
                    if (z4) {
                        MediaTimeoutListener.access$getLogger$p(MediaTimeoutListener.this).logDelayedUpdate(str);
                        MediaTimeoutListener.this.getTimeoutCallback().invoke(str, Boolean.FALSE);
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        PlaybackStateListener remove = this.mediaListeners.remove(str);
        if (remove != null) {
            remove.destroy();
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded(this, str, smartspaceMediaData, z);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataRemoved(this, str, z);
    }

    public final void setStateCallback(Function2<? super String, ? super PlaybackState, Unit> function2) {
        this.stateCallback = function2;
    }

    public final void setTimeoutCallback(Function2<? super String, ? super Boolean, Unit> function2) {
        this.timeoutCallback = function2;
    }
}