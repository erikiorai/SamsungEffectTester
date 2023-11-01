package com.android.systemui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserHandle;
import com.android.systemui.util.NotificationChannels;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestSessionNotification.class */
public final class GuestSessionNotification {
    public final Context mContext;
    public final NotificationManager mNotificationManager;

    public GuestSessionNotification(Context context, NotificationManager notificationManager) {
        this.mContext = context;
        this.mNotificationManager = notificationManager;
    }

    public void createPersistentNotification(UserInfo userInfo, boolean z) {
        if (userInfo.isGuest()) {
            String string = userInfo.isEphemeral() ? this.mContext.getString(R$string.guest_notification_ephemeral) : z ? this.mContext.getString(R$string.guest_notification_non_ephemeral) : this.mContext.getString(R$string.guest_notification_non_ephemeral_non_first_login);
            Intent intent = new Intent("android.intent.action.GUEST_EXIT");
            Intent intent2 = new Intent("android.settings.USER_SETTINGS");
            PendingIntent broadcastAsUser = PendingIntent.getBroadcastAsUser(this.mContext, 0, intent, 67108864, UserHandle.SYSTEM);
            Notification.Builder contentIntent = new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(R$drawable.ic_account_circle).setContentTitle(this.mContext.getString(R$string.guest_notification_session_active)).setContentText(string).setPriority(0).setOngoing(true).setContentIntent(PendingIntent.getActivityAsUser(this.mContext, 0, intent2, 335544320, null, UserHandle.of(userInfo.id)));
            if (!z) {
                contentIntent.addAction(R$drawable.ic_sysbar_home, this.mContext.getString(com.android.settingslib.R$string.guest_reset_guest_confirm_button), PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent("android.intent.action.GUEST_RESET"), 67108864, UserHandle.SYSTEM));
            }
            contentIntent.addAction(R$drawable.ic_sysbar_home, this.mContext.getString(com.android.settingslib.R$string.guest_exit_button), broadcastAsUser);
            overrideNotificationAppName(contentIntent);
            this.mNotificationManager.notifyAsUser(null, 70, contentIntent.build(), UserHandle.of(userInfo.id));
        }
    }

    public final void overrideNotificationAppName(Notification.Builder builder) {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", this.mContext.getString(R$string.guest_notification_app_name));
        builder.addExtras(bundle);
    }
}