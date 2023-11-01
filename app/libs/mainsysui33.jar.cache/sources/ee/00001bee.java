package com.android.systemui.media.controls.models.player;

import android.media.session.PlaybackState;
import android.os.SystemClock;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarViewModelKt.class */
public final class SeekBarViewModelKt {
    public static final long computePosition(PlaybackState playbackState, long j) {
        long position = playbackState.getPosition();
        long j2 = position;
        if (isInMotion(playbackState)) {
            long lastPositionUpdateTime = playbackState.getLastPositionUpdateTime();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            j2 = position;
            if (lastPositionUpdateTime > 0) {
                long playbackSpeed = (playbackState.getPlaybackSpeed() * ((float) (elapsedRealtime - lastPositionUpdateTime))) + playbackState.getPosition();
                if (j < 0 || playbackSpeed <= j) {
                    j = playbackSpeed < 0 ? 0L : playbackSpeed;
                }
                j2 = j;
            }
        }
        return j2;
    }

    public static final boolean isInMotion(PlaybackState playbackState) {
        return playbackState.getState() == 3 || playbackState.getState() == 4 || playbackState.getState() == 5;
    }
}