package com.android.systemui.media.controls.resume;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/MediaBrowserFactory.class */
public class MediaBrowserFactory {
    public final Context mContext;

    public MediaBrowserFactory(Context context) {
        this.mContext = context;
    }

    public MediaBrowser create(ComponentName componentName, MediaBrowser.ConnectionCallback connectionCallback, Bundle bundle) {
        return new MediaBrowser(this.mContext, componentName, connectionCallback, bundle);
    }
}