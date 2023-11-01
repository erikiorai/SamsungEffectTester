package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/UserBroadcastDispatcher$createActionReceiver$3.class */
public final /* synthetic */ class UserBroadcastDispatcher$createActionReceiver$3 extends FunctionReferenceImpl implements Function2<BroadcastReceiver, Integer, Boolean> {
    public UserBroadcastDispatcher$createActionReceiver$3(Object obj) {
        super(2, obj, PendingRemovalStore.class, "isPendingRemoval", "isPendingRemoval(Landroid/content/BroadcastReceiver;I)Z", 0);
    }

    public final Boolean invoke(BroadcastReceiver broadcastReceiver, int i) {
        return Boolean.valueOf(((PendingRemovalStore) ((CallableReference) this).receiver).isPendingRemoval(broadcastReceiver, i));
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke((BroadcastReceiver) obj, ((Number) obj2).intValue());
    }
}