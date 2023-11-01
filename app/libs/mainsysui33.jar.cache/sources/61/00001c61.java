package com.android.systemui.media.controls.resume;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/ResumeMediaBrowserLogger_Factory.class */
public final class ResumeMediaBrowserLogger_Factory implements Factory<ResumeMediaBrowserLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public ResumeMediaBrowserLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static ResumeMediaBrowserLogger_Factory create(Provider<LogBuffer> provider) {
        return new ResumeMediaBrowserLogger_Factory(provider);
    }

    public static ResumeMediaBrowserLogger newInstance(LogBuffer logBuffer) {
        return new ResumeMediaBrowserLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ResumeMediaBrowserLogger m3223get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}