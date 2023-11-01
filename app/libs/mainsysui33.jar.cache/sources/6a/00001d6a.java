package com.android.systemui.media.dream;

import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.media.controls.pipeline.MediaDataManager;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dream/MediaDreamSentinel.class */
public class MediaDreamSentinel implements CoreStartable {
    public static final boolean DEBUG = Log.isLoggable("MediaDreamSentinel", 3);
    public final MediaDataManager.Listener mListener;
    public final MediaDataManager mMediaDataManager;

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mMediaDataManager.addListener(this.mListener);
    }
}