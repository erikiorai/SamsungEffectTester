package com.airbnb.lottie.utils;

import com.airbnb.lottie.LottieLogger;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/utils/Logger.class */
public class Logger {
    public static LottieLogger INSTANCE = new LogcatLogger();

    public static void debug(String str) {
        INSTANCE.debug(str);
    }

    public static void error(String str, Throwable th) {
        INSTANCE.error(str, th);
    }

    public static void warning(String str) {
        INSTANCE.warning(str);
    }

    public static void warning(String str, Throwable th) {
        INSTANCE.warning(str, th);
    }
}