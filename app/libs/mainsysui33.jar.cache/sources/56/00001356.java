package com.android.systemui.common.coroutine;

import android.util.Log;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.SendChannel;

/* loaded from: mainsysui33.jar:com/android/systemui/common/coroutine/ChannelExt.class */
public final class ChannelExt {
    public static final ChannelExt INSTANCE = new ChannelExt();

    public static /* synthetic */ void trySendWithFailureLogging$default(ChannelExt channelExt, SendChannel sendChannel, Object obj, String str, String str2, int i, Object obj2) {
        if ((i & 4) != 0) {
            str2 = "updated state";
        }
        channelExt.trySendWithFailureLogging(sendChannel, obj, str, str2);
    }

    public final <T> void trySendWithFailureLogging(SendChannel<? super T> sendChannel, T t, String str, String str2) {
        Object obj = sendChannel.trySend-JP2dKIU(t);
        if (obj instanceof ChannelResult.Failed) {
            Throwable th = ChannelResult.exceptionOrNull-impl(obj);
            Log.e(str, "Failed to send " + str2 + " - downstream canceled or failed.", th);
        }
    }
}