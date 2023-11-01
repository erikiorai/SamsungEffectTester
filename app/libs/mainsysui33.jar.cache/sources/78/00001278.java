package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.UserHandle;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/ReceiverData.class */
public final class ReceiverData {
    public final Executor executor;
    public final IntentFilter filter;
    public final String permission;
    public final BroadcastReceiver receiver;
    public final UserHandle user;

    public ReceiverData(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, String str) {
        this.receiver = broadcastReceiver;
        this.filter = intentFilter;
        this.executor = executor;
        this.user = userHandle;
        this.permission = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ReceiverData) {
            ReceiverData receiverData = (ReceiverData) obj;
            return Intrinsics.areEqual(this.receiver, receiverData.receiver) && Intrinsics.areEqual(this.filter, receiverData.filter) && Intrinsics.areEqual(this.executor, receiverData.executor) && Intrinsics.areEqual(this.user, receiverData.user) && Intrinsics.areEqual(this.permission, receiverData.permission);
        }
        return false;
    }

    public final Executor getExecutor() {
        return this.executor;
    }

    public final IntentFilter getFilter() {
        return this.filter;
    }

    public final String getPermission() {
        return this.permission;
    }

    public final BroadcastReceiver getReceiver() {
        return this.receiver;
    }

    public final UserHandle getUser() {
        return this.user;
    }

    public int hashCode() {
        int hashCode = this.receiver.hashCode();
        int hashCode2 = this.filter.hashCode();
        int hashCode3 = this.executor.hashCode();
        int hashCode4 = this.user.hashCode();
        String str = this.permission;
        return (((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        BroadcastReceiver broadcastReceiver = this.receiver;
        IntentFilter intentFilter = this.filter;
        Executor executor = this.executor;
        UserHandle userHandle = this.user;
        String str = this.permission;
        return "ReceiverData(receiver=" + broadcastReceiver + ", filter=" + intentFilter + ", executor=" + executor + ", user=" + userHandle + ", permission=" + str + ")";
    }
}