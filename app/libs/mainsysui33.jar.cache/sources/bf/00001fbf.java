package com.android.systemui.plugins.log;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogMessage.class */
public interface LogMessage {

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogMessage$DefaultImpls.class */
    public static final class DefaultImpls {
        public static void dump(LogMessage logMessage, PrintWriter printWriter) {
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = LogMessageKt.DATE_FORMAT;
            LogMessageKt.printLikeLogcat(printWriter, simpleDateFormat.format(Long.valueOf(logMessage.getTimestamp())), logMessage.getLevel().getShortString(), logMessage.getTag(), (String) logMessage.getMessagePrinter().invoke(logMessage));
            Throwable exception = logMessage.getException();
            if (exception != null) {
                exception.printStackTrace(printWriter);
            }
        }
    }

    void dump(PrintWriter printWriter);

    boolean getBool1();

    boolean getBool2();

    boolean getBool3();

    boolean getBool4();

    double getDouble1();

    Throwable getException();

    int getInt1();

    int getInt2();

    LogLevel getLevel();

    long getLong1();

    long getLong2();

    Function1<LogMessage, String> getMessagePrinter();

    String getStr1();

    String getStr2();

    String getStr3();

    String getTag();

    long getTimestamp();

    void setBool1(boolean z);

    void setBool2(boolean z);

    void setBool3(boolean z);

    void setBool4(boolean z);

    void setDouble1(double d);

    void setInt1(int i);

    void setInt2(int i);

    void setLong1(long j);

    void setLong2(long j);

    void setStr1(String str);

    void setStr2(String str);

    void setStr3(String str);
}