package androidx.core.os;

import android.os.Trace;

@Deprecated
/* loaded from: mainsysui33.jar:androidx/core/os/TraceCompat.class */
public final class TraceCompat {

    /* loaded from: mainsysui33.jar:androidx/core/os/TraceCompat$Api18Impl.class */
    public static class Api18Impl {
        public static void beginSection(String str) {
            Trace.beginSection(str);
        }

        public static void endSection() {
            Trace.endSection();
        }
    }

    public static void beginSection(String str) {
        Api18Impl.beginSection(str);
    }

    public static void endSection() {
        Api18Impl.endSection();
    }
}