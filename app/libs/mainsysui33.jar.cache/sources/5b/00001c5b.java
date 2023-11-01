package com.android.systemui.media.controls.resume;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.media.controls.resume.ResumeMediaBrowser;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/ResumeMediaBrowserFactory.class */
public class ResumeMediaBrowserFactory {
    public final MediaBrowserFactory mBrowserFactory;
    public final Context mContext;
    public final ResumeMediaBrowserLogger mLogger;

    public ResumeMediaBrowserFactory(Context context, MediaBrowserFactory mediaBrowserFactory, ResumeMediaBrowserLogger resumeMediaBrowserLogger) {
        this.mContext = context;
        this.mBrowserFactory = mediaBrowserFactory;
        this.mLogger = resumeMediaBrowserLogger;
    }

    public ResumeMediaBrowser create(ResumeMediaBrowser.Callback callback, ComponentName componentName) {
        return new ResumeMediaBrowser(this.mContext, callback, componentName, this.mBrowserFactory, this.mLogger);
    }
}