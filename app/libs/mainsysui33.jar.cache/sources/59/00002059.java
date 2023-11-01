package com.android.systemui.privacy.logging;

import android.permission.PermissionGroupUsage;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyItem;
import java.text.SimpleDateFormat;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.PropertyReference1Impl;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/logging/PrivacyLogger.class */
public final class PrivacyLogger {
    public final LogBuffer buffer;

    public PrivacyLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final String listToString(List<PrivacyItem> list) {
        return CollectionsKt___CollectionsKt.joinToString$default(list, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new PropertyReference1Impl() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$listToString$1
            public Object get(Object obj) {
                return ((PrivacyItem) obj).getLog();
            }
        }, 30, (Object) null);
    }

    public final void logChipVisible(boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logChipVisible$2 privacyLogger$logChipVisible$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logChipVisible$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "Chip visible: " + bool1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logChipVisible$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logCurrentProfilesChanged(List<Integer> list) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logCurrentProfilesChanged$2 privacyLogger$logCurrentProfilesChanged$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logCurrentProfilesChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Profiles changed: " + str1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logCurrentProfilesChanged$2, null);
        obtain.setStr1(list.toString());
        logBuffer.commit(obtain);
    }

    public final void logPrivacyDialogDismissed() {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logPrivacyDialogDismissed$2 privacyLogger$logPrivacyDialogDismissed$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logPrivacyDialogDismissed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return "Privacy dialog dismissed";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logPrivacyDialogDismissed$2, null));
    }

    public final void logPrivacyItemsToHold(List<PrivacyItem> list) {
        LogLevel logLevel = LogLevel.DEBUG;
        PrivacyLogger$logPrivacyItemsToHold$2 privacyLogger$logPrivacyItemsToHold$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logPrivacyItemsToHold$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Holding items: " + str1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logPrivacyItemsToHold$2, null);
        obtain.setStr1(listToString(list));
        logBuffer.commit(obtain);
    }

    public final void logPrivacyItemsUpdateScheduled(long j) {
        SimpleDateFormat simpleDateFormat;
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logPrivacyItemsUpdateScheduled$2 privacyLogger$logPrivacyItemsUpdateScheduled$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logPrivacyItemsUpdateScheduled$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Updating items scheduled for " + str1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logPrivacyItemsUpdateScheduled$2, null);
        long currentTimeMillis = System.currentTimeMillis();
        simpleDateFormat = PrivacyLoggerKt.DATE_FORMAT;
        obtain.setStr1(simpleDateFormat.format(Long.valueOf(currentTimeMillis + j)));
        logBuffer.commit(obtain);
    }

    public final void logRetrievedPrivacyItemsList(List<PrivacyItem> list) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logRetrievedPrivacyItemsList$2 privacyLogger$logRetrievedPrivacyItemsList$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logRetrievedPrivacyItemsList$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Retrieved list to process: " + str1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logRetrievedPrivacyItemsList$2, null);
        obtain.setStr1(listToString(list));
        logBuffer.commit(obtain);
    }

    public final void logShowDialogContents(List<PrivacyDialog.PrivacyElement> list) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logShowDialogContents$2 privacyLogger$logShowDialogContents$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logShowDialogContents$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Privacy dialog shown. Contents: " + str1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logShowDialogContents$2, null);
        obtain.setStr1(list.toString());
        logBuffer.commit(obtain);
    }

    public final void logStartSettingsActivityFromDialog(String str, int i) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logStartSettingsActivityFromDialog$2 privacyLogger$logStartSettingsActivityFromDialog$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logStartSettingsActivityFromDialog$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "Start settings activity from dialog for packageName=" + str1 + ", userId=" + int1 + " ";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logStartSettingsActivityFromDialog$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logStatusBarIconsVisible(boolean z, boolean z2, boolean z3) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logStatusBarIconsVisible$2 privacyLogger$logStatusBarIconsVisible$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logStatusBarIconsVisible$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                return "Status bar icons visible: camera=" + bool1 + ", microphone=" + bool2 + ", location=" + bool3;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logStatusBarIconsVisible$2, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        logBuffer.commit(obtain);
    }

    public final void logUnfilteredPermGroupUsage(List<PermissionGroupUsage> list) {
        LogLevel logLevel = LogLevel.DEBUG;
        PrivacyLogger$logUnfilteredPermGroupUsage$2 privacyLogger$logUnfilteredPermGroupUsage$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logUnfilteredPermGroupUsage$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Perm group usage: " + str1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logUnfilteredPermGroupUsage$2, null);
        obtain.setStr1(list.toString());
        logBuffer.commit(obtain);
    }

    public final void logUpdatedItemFromAppOps(int i, int i2, String str, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logUpdatedItemFromAppOps$2 privacyLogger$logUpdatedItemFromAppOps$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logUpdatedItemFromAppOps$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                return "App Op: " + int1 + " for " + str1 + "(" + int2 + "), active=" + bool1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logUpdatedItemFromAppOps$2, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logUpdatedItemFromMediaProjection(int i, String str, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logUpdatedItemFromMediaProjection$2 privacyLogger$logUpdatedItemFromMediaProjection$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logUpdatedItemFromMediaProjection$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                return "MediaProjection: " + str1 + "(" + int1 + "), active=" + bool1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logUpdatedItemFromMediaProjection$2, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }
}