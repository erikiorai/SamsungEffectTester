package com.android.systemui.plugins.log;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogcatEchoTrackerDebug.class */
public final class LogcatEchoTrackerDebug implements LogcatEchoTracker {
    public static final Factory Factory = new Factory(null);
    private final Map<String, LogLevel> cachedBufferLevels;
    private final Map<String, LogLevel> cachedTagLevels;
    private final ContentResolver contentResolver;
    private final boolean logInBackgroundThread;

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogcatEchoTrackerDebug$Factory.class */
    public static final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final LogcatEchoTrackerDebug create(ContentResolver contentResolver, Looper looper) {
            LogcatEchoTrackerDebug logcatEchoTrackerDebug = new LogcatEchoTrackerDebug(contentResolver, null);
            logcatEchoTrackerDebug.attach(looper);
            return logcatEchoTrackerDebug;
        }
    }

    private LogcatEchoTrackerDebug(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        this.cachedBufferLevels = new LinkedHashMap();
        this.cachedTagLevels = new LinkedHashMap();
        this.logInBackgroundThread = true;
    }

    public /* synthetic */ LogcatEchoTrackerDebug(ContentResolver contentResolver, DefaultConstructorMarker defaultConstructorMarker) {
        this(contentResolver);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.plugins.log.LogcatEchoTrackerDebug$attach$1.onChange(boolean, android.net.Uri):void] */
    public static final /* synthetic */ Map access$getCachedBufferLevels$p(LogcatEchoTrackerDebug logcatEchoTrackerDebug) {
        return logcatEchoTrackerDebug.cachedBufferLevels;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.plugins.log.LogcatEchoTrackerDebug$attach$2.onChange(boolean, android.net.Uri):void] */
    public static final /* synthetic */ Map access$getCachedTagLevels$p(LogcatEchoTrackerDebug logcatEchoTrackerDebug) {
        return logcatEchoTrackerDebug.cachedTagLevels;
    }

    public final void attach(Looper looper) {
        this.contentResolver.registerContentObserver(Settings.Global.getUriFor("systemui/buffer"), true, new ContentObserver(new Handler(looper)) { // from class: com.android.systemui.plugins.log.LogcatEchoTrackerDebug$attach$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                LogcatEchoTrackerDebug.access$getCachedBufferLevels$p(LogcatEchoTrackerDebug.this).clear();
            }
        });
        this.contentResolver.registerContentObserver(Settings.Global.getUriFor("systemui/tag"), true, new ContentObserver(new Handler(looper)) { // from class: com.android.systemui.plugins.log.LogcatEchoTrackerDebug$attach$2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                LogcatEchoTrackerDebug.access$getCachedTagLevels$p(LogcatEchoTrackerDebug.this).clear();
            }
        });
    }

    public static final LogcatEchoTrackerDebug create(ContentResolver contentResolver, Looper looper) {
        return Factory.create(contentResolver, looper);
    }

    private final LogLevel getLogLevel(String str, String str2, Map<String, LogLevel> map) {
        LogLevel logLevel = map.get(str);
        LogLevel logLevel2 = logLevel;
        if (logLevel == null) {
            logLevel2 = readSetting(str2 + "/" + str);
            map.put(str, logLevel2);
        }
        return logLevel2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final LogLevel parseProp(String str) {
        LogLevel logLevel;
        LogLevel logLevel2;
        String lowerCase = str != null ? str.toLowerCase(Locale.ROOT) : null;
        if (lowerCase != null) {
            switch (lowerCase.hashCode()) {
                case -1408208058:
                    if (lowerCase.equals("assert")) {
                        logLevel2 = LogLevel.WTF;
                        break;
                    }
                    break;
                case 100:
                    if (lowerCase.equals("d")) {
                        logLevel2 = LogLevel.DEBUG;
                        break;
                    }
                    break;
                case 101:
                    if (lowerCase.equals("e")) {
                        logLevel2 = LogLevel.ERROR;
                        break;
                    }
                    break;
                case 105:
                    if (lowerCase.equals("i")) {
                        logLevel2 = LogLevel.INFO;
                        break;
                    }
                    break;
                case 118:
                    if (lowerCase.equals("v")) {
                        logLevel2 = LogLevel.VERBOSE;
                        break;
                    }
                    break;
                case 119:
                    if (lowerCase.equals("w")) {
                        logLevel2 = LogLevel.WARNING;
                        break;
                    }
                    break;
                case 118057:
                    if (lowerCase.equals("wtf")) {
                        logLevel2 = LogLevel.WTF;
                        break;
                    }
                    break;
                case 3237038:
                    if (lowerCase.equals("info")) {
                        logLevel2 = LogLevel.INFO;
                        break;
                    }
                    break;
                case 3641990:
                    if (lowerCase.equals("warn")) {
                        logLevel2 = LogLevel.WARNING;
                        break;
                    }
                    break;
                case 95458899:
                    if (lowerCase.equals("debug")) {
                        logLevel2 = LogLevel.DEBUG;
                        break;
                    }
                    break;
                case 96784904:
                    if (lowerCase.equals("error")) {
                        logLevel2 = LogLevel.ERROR;
                        break;
                    }
                    break;
                case 351107458:
                    if (lowerCase.equals("verbose")) {
                        logLevel2 = LogLevel.VERBOSE;
                        break;
                    }
                    break;
                case 1124446108:
                    if (lowerCase.equals("warning")) {
                        logLevel2 = LogLevel.WARNING;
                        break;
                    }
                    break;
            }
            return logLevel2;
        }
        logLevel = LogcatEchoTrackerDebugKt.DEFAULT_LEVEL;
        logLevel2 = logLevel;
        return logLevel2;
    }

    private final LogLevel readSetting(String str) {
        LogLevel logLevel;
        LogLevel logLevel2;
        try {
            logLevel2 = parseProp(Settings.Global.getString(this.contentResolver, str));
        } catch (Settings.SettingNotFoundException e) {
            logLevel = LogcatEchoTrackerDebugKt.DEFAULT_LEVEL;
            logLevel2 = logLevel;
        }
        return logLevel2;
    }

    @Override // com.android.systemui.plugins.log.LogcatEchoTracker
    public boolean getLogInBackgroundThread() {
        return this.logInBackgroundThread;
    }

    @Override // com.android.systemui.plugins.log.LogcatEchoTracker
    public boolean isBufferLoggable(String str, LogLevel logLevel) {
        boolean z;
        synchronized (this) {
            z = logLevel.ordinal() >= getLogLevel(str, "systemui/buffer", this.cachedBufferLevels).ordinal();
        }
        return z;
    }

    @Override // com.android.systemui.plugins.log.LogcatEchoTracker
    public boolean isTagLoggable(String str, LogLevel logLevel) {
        boolean z;
        synchronized (this) {
            z = logLevel.compareTo(getLogLevel(str, "systemui/tag", this.cachedTagLevels)) >= 0;
        }
        return z;
    }
}