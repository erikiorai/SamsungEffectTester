package com.android.systemui.plugins.log;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogMessageKt.class */
public final class LogMessageKt {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);

    /* JADX INFO: Access modifiers changed from: private */
    public static final void printLikeLogcat(PrintWriter printWriter, String str, String str2, String str3, String str4) {
        printWriter.print(str);
        printWriter.print(" ");
        printWriter.print(str2);
        printWriter.print(" ");
        printWriter.print(str3);
        printWriter.print(": ");
        printWriter.println(str4);
    }
}