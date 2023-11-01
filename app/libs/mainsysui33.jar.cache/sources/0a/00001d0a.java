package com.android.systemui.media.controls.util;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaControllerFactory.class */
public class MediaControllerFactory {
    public final Context mContext;

    public MediaControllerFactory(Context context) {
        this.mContext = context;
    }

    public MediaController create(MediaSession.Token token) {
        return new MediaController(this.mContext, token);
    }
}