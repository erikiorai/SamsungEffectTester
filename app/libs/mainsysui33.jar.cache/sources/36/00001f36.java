package com.android.systemui.people.widget;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.UnlaunchableAppActivity;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.Bubble;
import java.util.Optional;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/people/widget/LaunchConversationActivity.class */
public class LaunchConversationActivity extends Activity {
    public Executor mBgExecutor;
    public Bubble mBubble;
    public final Optional<BubblesManager> mBubblesManagerOptional;
    public CommandQueue mCommandQueue;
    public CommonNotifCollection mCommonNotifCollection;
    public NotificationEntry mEntryToBubble;
    public IStatusBarService mIStatusBarService;
    public boolean mIsForTesting;
    public UiEventLogger mUiEventLogger = new UiEventLoggerImpl();
    public final UserManager mUserManager;
    public NotificationVisibilityProvider mVisibilityProvider;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.LaunchConversationActivity$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$_3ZBksAhfkLkOJdAzglQ4NKPDH0(LaunchConversationActivity launchConversationActivity, String str, UserHandle userHandle, String str2, NotificationVisibility notificationVisibility) {
        launchConversationActivity.lambda$clearNotificationIfPresent$0(str, userHandle, str2, notificationVisibility);
    }

    public LaunchConversationActivity(NotificationVisibilityProvider notificationVisibilityProvider, CommonNotifCollection commonNotifCollection, Optional<BubblesManager> optional, UserManager userManager, CommandQueue commandQueue, Executor executor) {
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mCommonNotifCollection = commonNotifCollection;
        this.mBubblesManagerOptional = optional;
        this.mUserManager = userManager;
        this.mCommandQueue = commandQueue;
        commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.people.widget.LaunchConversationActivity.1
            {
                LaunchConversationActivity.this = this;
            }

            public void appTransitionFinished(int i) {
                if (LaunchConversationActivity.this.mBubblesManagerOptional.isPresent()) {
                    if (LaunchConversationActivity.this.mBubble != null) {
                        ((BubblesManager) LaunchConversationActivity.this.mBubblesManagerOptional.get()).expandStackAndSelectBubble(LaunchConversationActivity.this.mBubble);
                    } else if (LaunchConversationActivity.this.mEntryToBubble != null) {
                        ((BubblesManager) LaunchConversationActivity.this.mBubblesManagerOptional.get()).expandStackAndSelectBubble(LaunchConversationActivity.this.mEntryToBubble);
                    }
                }
                LaunchConversationActivity.this.mCommandQueue.removeCallback(this);
            }
        });
        this.mBgExecutor = executor;
    }

    public /* synthetic */ void lambda$clearNotificationIfPresent$0(String str, UserHandle userHandle, String str2, NotificationVisibility notificationVisibility) {
        try {
            this.mIStatusBarService.onNotificationClear(str, userHandle.getIdentifier(), str2, 0, 2, notificationVisibility);
        } catch (RemoteException e) {
            Log.e("PeopleSpaceLaunchConv", "Exception cancelling notification:" + e);
        }
    }

    public void clearNotificationIfPresent(final String str, final String str2, final UserHandle userHandle) {
        CommonNotifCollection commonNotifCollection;
        NotificationEntry entry;
        if (TextUtils.isEmpty(str) || this.mIStatusBarService == null || (commonNotifCollection = this.mCommonNotifCollection) == null || (entry = commonNotifCollection.getEntry(str)) == null || entry.getRanking() == null) {
            return;
        }
        final NotificationVisibility obtain = this.mVisibilityProvider.obtain(entry, true);
        int i = obtain.rank;
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.LaunchConversationActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LaunchConversationActivity.$r8$lambda$_3ZBksAhfkLkOJdAzglQ4NKPDH0(LaunchConversationActivity.this, str2, userHandle, str, obtain);
            }
        });
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        if (!this.mIsForTesting) {
            super.onCreate(bundle);
        }
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("extra_tile_id");
        String stringExtra2 = intent.getStringExtra("extra_package_name");
        UserHandle userHandle = (UserHandle) intent.getParcelableExtra("extra_user_handle");
        String stringExtra3 = intent.getStringExtra("extra_notification_key");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_CLICKED);
            try {
                if (this.mUserManager.isQuietModeEnabled(userHandle)) {
                    getApplicationContext().startActivity(UnlaunchableAppActivity.createInQuietModeDialogIntent(userHandle.getIdentifier()));
                    finish();
                    return;
                }
                if (this.mBubblesManagerOptional.isPresent()) {
                    this.mBubble = this.mBubblesManagerOptional.get().getBubbleWithShortcutId(stringExtra);
                    NotificationEntry entry = this.mCommonNotifCollection.getEntry(stringExtra3);
                    if (this.mBubble != null || (entry != null && entry.canBubble())) {
                        this.mEntryToBubble = entry;
                        finish();
                        return;
                    }
                }
                if (this.mIStatusBarService == null) {
                    this.mIStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                }
                clearNotificationIfPresent(stringExtra3, stringExtra2, userHandle);
                ((LauncherApps) getApplicationContext().getSystemService(LauncherApps.class)).startShortcut(stringExtra2, stringExtra, null, null, userHandle);
            } catch (Exception e) {
                Log.e("PeopleSpaceLaunchConv", "Exception launching shortcut:" + e);
            }
        }
        finish();
    }

    @VisibleForTesting
    public void setIsForTesting(boolean z, IStatusBarService iStatusBarService) {
        this.mIsForTesting = z;
        this.mIStatusBarService = iStatusBarService;
    }
}