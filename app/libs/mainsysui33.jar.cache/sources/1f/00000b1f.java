package com.android.internal.protolog.common;

/* loaded from: mainsysui33.jar:com/android/internal/protolog/common/ProtoLog.class */
public class ProtoLog {
    public static boolean REQUIRE_PROTOLOGTOOL = true;

    public static void d(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void v(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }
}