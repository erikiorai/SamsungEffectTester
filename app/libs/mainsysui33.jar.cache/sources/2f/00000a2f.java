package com.airbnb.lottie;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/LottieLogger.class */
public interface LottieLogger {
    void debug(String str);

    void error(String str, Throwable th);

    void warning(String str);

    void warning(String str, Throwable th);
}