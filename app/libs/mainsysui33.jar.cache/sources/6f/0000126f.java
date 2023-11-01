package com.android.systemui.broadcast;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/BroadcastSender.class */
public final class BroadcastSender {
    public final Executor bgExecutor;
    public final Context context;
    public final WakeLock.Builder wakeLockBuilder;
    public final String WAKE_LOCK_TAG = "SysUI:BroadcastSender";
    public final String WAKE_LOCK_SEND_REASON = "sendInBackground";

    public BroadcastSender(Context context, WakeLock.Builder builder, Executor executor) {
        this.context = context;
        this.wakeLockBuilder = builder;
        this.bgExecutor = executor;
    }

    public final void closeSystemDialogs() {
        sendInBackground(new Function0<Unit>() { // from class: com.android.systemui.broadcast.BroadcastSender$closeSystemDialogs$1
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1637invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1637invoke() {
                Context context;
                context = BroadcastSender.this.context;
                context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
            }
        });
    }

    public final void sendBroadcast(final Intent intent) {
        sendInBackground(new Function0<Unit>() { // from class: com.android.systemui.broadcast.BroadcastSender$sendBroadcast$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1638invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1638invoke() {
                Context context;
                context = BroadcastSender.this.context;
                context.sendBroadcast(intent);
            }
        });
    }

    public final void sendBroadcast(final Intent intent, final String str) {
        sendInBackground(new Function0<Unit>() { // from class: com.android.systemui.broadcast.BroadcastSender$sendBroadcast$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1639invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1639invoke() {
                Context context;
                context = BroadcastSender.this.context;
                context.sendBroadcast(intent, str);
            }
        });
    }

    public final void sendBroadcastAsUser(final Intent intent, final UserHandle userHandle) {
        sendInBackground(new Function0<Unit>() { // from class: com.android.systemui.broadcast.BroadcastSender$sendBroadcastAsUser$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1640invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1640invoke() {
                Context context;
                context = BroadcastSender.this.context;
                context.sendBroadcastAsUser(intent, userHandle);
            }
        });
    }

    public final void sendInBackground(final Function0<Unit> function0) {
        final WakeLock build = this.wakeLockBuilder.setTag(this.WAKE_LOCK_TAG).setMaxTimeout(5000L).build();
        build.acquire(this.WAKE_LOCK_SEND_REASON);
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.broadcast.BroadcastSender$sendInBackground$1
            @Override // java.lang.Runnable
            public final void run() {
                String str;
                try {
                    function0.invoke();
                } finally {
                    WakeLock wakeLock = build;
                    str = this.WAKE_LOCK_SEND_REASON;
                    wakeLock.release(str);
                }
            }
        });
    }
}