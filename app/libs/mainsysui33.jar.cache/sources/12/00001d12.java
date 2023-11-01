package com.android.systemui.media.controls.util;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$id;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaUiEventLogger.class */
public final class MediaUiEventLogger {
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(1048576);
    public final UiEventLogger logger;

    public MediaUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }

    public final InstanceId getNewInstanceId() {
        return this.instanceIdSequence.newInstanceId();
    }

    public final void logActiveConvertedToResume(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.ACTIVE_TO_RESUME, i, str, instanceId);
    }

    public final void logActiveMediaAdded(int i, String str, InstanceId instanceId, int i2) {
        MediaUiEvent mediaUiEvent;
        if (i2 == 0) {
            mediaUiEvent = MediaUiEvent.LOCAL_MEDIA_ADDED;
        } else if (i2 == 1) {
            mediaUiEvent = MediaUiEvent.CAST_MEDIA_ADDED;
        } else if (i2 != 2) {
            throw new IllegalArgumentException("Unknown playback location");
        } else {
            mediaUiEvent = MediaUiEvent.REMOTE_MEDIA_ADDED;
        }
        this.logger.logWithInstanceId(mediaUiEvent, i, str, instanceId);
    }

    public final void logCarouselPosition(int i) {
        MediaUiEvent mediaUiEvent;
        if (i == 0) {
            mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_QS;
        } else if (i == 1) {
            mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_QQS;
        } else if (i == 2) {
            mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_LOCKSCREEN;
        } else if (i != 3) {
            throw new IllegalArgumentException("Unknown media carousel location " + i);
        } else {
            mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_DREAM;
        }
        this.logger.log(mediaUiEvent);
    }

    public final void logCarouselSettings() {
        this.logger.log(MediaUiEvent.OPEN_SETTINGS_CAROUSEL);
    }

    public final void logLongPressDismiss(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.DISMISS_LONG_PRESS, i, str, instanceId);
    }

    public final void logLongPressOpen(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.OPEN_LONG_PRESS, i, str, instanceId);
    }

    public final void logLongPressSettings(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.OPEN_SETTINGS_LONG_PRESS, i, str, instanceId);
    }

    public final void logMediaCarouselPage(int i) {
        this.logger.logWithPosition(MediaUiEvent.CAROUSEL_PAGE, 0, (String) null, i);
    }

    public final void logMediaRemoved(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_REMOVED, i, str, instanceId);
    }

    public final void logMediaTimeout(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_TIMEOUT, i, str, instanceId);
    }

    public final void logMultipleMediaPlayersInCarousel(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_MULTIPLE_PLAYERS, i, str, instanceId);
    }

    public final void logOpenBroadcastDialog(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_OPEN_BROADCAST_DIALOG, i, str, instanceId);
    }

    public final void logOpenOutputSwitcher(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.OPEN_OUTPUT_SWITCHER, i, str, instanceId);
    }

    public final void logPlaybackLocationChange(int i, String str, InstanceId instanceId, int i2) {
        MediaUiEvent mediaUiEvent;
        if (i2 == 0) {
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_LOCAL;
        } else if (i2 == 1) {
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_CAST;
        } else if (i2 != 2) {
            throw new IllegalArgumentException("Unknown playback location");
        } else {
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_REMOTE;
        }
        this.logger.logWithInstanceId(mediaUiEvent, i, str, instanceId);
    }

    public final void logRecommendationActivated(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_RECOMMENDATION_ACTIVATED, i, str, instanceId);
    }

    public final void logRecommendationAdded(String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_RECOMMENDATION_ADDED, 0, str, instanceId);
    }

    public final void logRecommendationCardTap(String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_RECOMMENDATION_CARD_TAP, 0, str, instanceId);
    }

    public final void logRecommendationItemTap(String str, InstanceId instanceId, int i) {
        this.logger.logWithInstanceIdAndPosition(MediaUiEvent.MEDIA_RECOMMENDATION_ITEM_TAP, 0, str, instanceId, i);
    }

    public final void logRecommendationRemoved(String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_RECOMMENDATION_REMOVED, 0, str, instanceId);
    }

    public final void logResumeMediaAdded(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.RESUME_MEDIA_ADDED, i, str, instanceId);
    }

    public final void logSeek(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.ACTION_SEEK, i, str, instanceId);
    }

    public final void logSingleMediaPlayerInCarousel(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_SINGLE_PLAYER, i, str, instanceId);
    }

    public final void logSwipeDismiss() {
        this.logger.log(MediaUiEvent.DISMISS_SWIPE);
    }

    public final void logTapAction(int i, int i2, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(i == R$id.actionPlayPause ? MediaUiEvent.TAP_ACTION_PLAY_PAUSE : i == R$id.actionPrev ? MediaUiEvent.TAP_ACTION_PREV : i == R$id.actionNext ? MediaUiEvent.TAP_ACTION_NEXT : MediaUiEvent.TAP_ACTION_OTHER, i2, str, instanceId);
    }

    public final void logTapContentView(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_TAP_CONTENT_VIEW, i, str, instanceId);
    }
}