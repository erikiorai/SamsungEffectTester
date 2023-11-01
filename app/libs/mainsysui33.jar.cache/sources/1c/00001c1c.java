package com.android.systemui.media.controls.pipeline;

import android.content.Context;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.util.Utils;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataManagerKt.class */
public final class MediaDataManagerKt {
    public static final String[] ART_URIS = {"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    public static final MediaData LOADING = new MediaData(-1, false, null, null, null, null, null, CollectionsKt__CollectionsKt.emptyList(), CollectionsKt__CollectionsKt.emptyList(), null, "INVALID", null, null, null, true, null, 0, false, null, false, null, false, 0, InstanceId.fakeInstanceId(-1), -1, 8323584, null);
    public static final SmartspaceMediaData EMPTY_SMARTSPACE_MEDIA_DATA = new SmartspaceMediaData("INVALID", false, "INVALID", null, CollectionsKt__CollectionsKt.emptyList(), null, 0, InstanceId.fakeInstanceId(-1));

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager.<init>(android.content.Context, java.util.concurrent.Executor, java.util.concurrent.Executor, com.android.systemui.util.concurrency.DelayableExecutor, com.android.systemui.media.controls.util.MediaControllerFactory, com.android.systemui.broadcast.BroadcastDispatcher, com.android.systemui.dump.DumpManager, com.android.systemui.media.controls.pipeline.MediaTimeoutListener, com.android.systemui.media.controls.resume.MediaResumeListener, com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter, com.android.systemui.media.controls.pipeline.MediaDeviceManager, com.android.systemui.media.controls.pipeline.MediaDataCombineLatest, com.android.systemui.media.controls.pipeline.MediaDataFilter, com.android.systemui.plugins.ActivityStarter, com.android.systemui.media.controls.models.recommendation.SmartspaceMediaDataProvider, boolean, boolean, com.android.systemui.util.time.SystemClock, com.android.systemui.tuner.TunerService, com.android.systemui.media.controls.util.MediaFlags, com.android.systemui.media.controls.util.MediaUiEventLogger, android.app.smartspace.SmartspaceManager):void, com.android.systemui.media.controls.pipeline.MediaDataManager.5.onTuningChanged(java.lang.String, java.lang.String):void] */
    public static final /* synthetic */ boolean access$allowMediaRecommendations(Context context) {
        return allowMediaRecommendations(context);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager.loadBitmapFromUri(android.media.MediaMetadata):android.graphics.Bitmap] */
    public static final /* synthetic */ String[] access$getART_URIS$p() {
        return ART_URIS;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager.addResumptionControls(int, android.media.MediaDescription, java.lang.Runnable, android.media.session.MediaSession$Token, java.lang.String, android.app.PendingIntent, java.lang.String):void, com.android.systemui.media.controls.pipeline.MediaDataManager.onNotificationAdded(java.lang.String, android.service.notification.StatusBarNotification):void] */
    public static final /* synthetic */ MediaData access$getLOADING$p() {
        return LOADING;
    }

    public static final boolean allowMediaRecommendations(Context context) {
        boolean z = true;
        int i = Settings.Secure.getInt(context.getContentResolver(), "qs_media_recommend", 1);
        if (!Utils.useQsMediaPlayer(context) || i <= 0) {
            z = false;
        }
        return z;
    }

    public static final SmartspaceMediaData getEMPTY_SMARTSPACE_MEDIA_DATA() {
        return EMPTY_SMARTSPACE_MEDIA_DATA;
    }

    @VisibleForTesting
    public static /* synthetic */ void getEMPTY_SMARTSPACE_MEDIA_DATA$annotations() {
    }

    public static final boolean isMediaNotification(StatusBarNotification statusBarNotification) {
        return statusBarNotification.getNotification().isMediaNotification();
    }
}