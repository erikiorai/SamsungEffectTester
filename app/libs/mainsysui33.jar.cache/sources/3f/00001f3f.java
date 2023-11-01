package com.android.systemui.people.widget;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.app.backup.BackupManager;
import android.app.job.JobScheduler;
import android.app.people.ConversationChannel;
import android.app.people.IPeopleManager;
import android.app.people.PeopleManager;
import android.app.people.PeopleSpaceTile;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.service.notification.ConversationChannelWrapper;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.people.NotificationHelper;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.SharedPreferencesHelper;
import com.android.systemui.people.widget.PeopleBackupHelper;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleSpaceWidgetManager.class */
public class PeopleSpaceWidgetManager {
    @GuardedBy({"mLock"})
    public static Map<PeopleTileKey, TileConversationListener> mListeners = new HashMap();
    @GuardedBy({"mLock"})
    public static Map<Integer, PeopleSpaceTile> mTiles = new HashMap();
    public AppWidgetManager mAppWidgetManager;
    public BackupManager mBackupManager;
    public final BroadcastReceiver mBaseBroadcastReceiver;
    public Executor mBgExecutor;
    public BroadcastDispatcher mBroadcastDispatcher;
    public Optional<Bubbles> mBubblesOptional;
    public final Context mContext;
    public INotificationManager mINotificationManager;
    public IPeopleManager mIPeopleManager;
    public LauncherApps mLauncherApps;
    public final NotificationListener.NotificationHandler mListener;
    public final Object mLock;
    public PeopleSpaceWidgetManager mManager;
    public CommonNotifCollection mNotifCollection;
    @GuardedBy({"mLock"})
    public Map<String, Set<String>> mNotificationKeyToWidgetIdsMatchedByUri;
    public NotificationManager mNotificationManager;
    public PackageManager mPackageManager;
    public PeopleManager mPeopleManager;
    public boolean mRegisteredReceivers;
    public SharedPreferences mSharedPrefs;
    public UiEventLogger mUiEventLogger;
    public UserManager mUserManager;

    /* renamed from: com.android.systemui.people.widget.PeopleSpaceWidgetManager$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleSpaceWidgetManager$2.class */
    public class AnonymousClass2 extends BroadcastReceiver {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$2$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$8TxJaNnHhszcUaPtzhE_V2Z5H14(AnonymousClass2 anonymousClass2, Intent intent) {
            anonymousClass2.lambda$onReceive$0(intent);
        }

        public AnonymousClass2() {
            PeopleSpaceWidgetManager.this = r4;
        }

        public /* synthetic */ void lambda$onReceive$0(Intent intent) {
            PeopleSpaceWidgetManager.this.updateWidgetsFromBroadcastInBackground(intent.getAction());
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, final Intent intent) {
            PeopleSpaceWidgetManager.this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PeopleSpaceWidgetManager.AnonymousClass2.$r8$lambda$8TxJaNnHhszcUaPtzhE_V2Z5H14(PeopleSpaceWidgetManager.AnonymousClass2.this, intent);
                }
            });
        }
    }

    /* renamed from: com.android.systemui.people.widget.PeopleSpaceWidgetManager$3 */
    /* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleSpaceWidgetManager$3.class */
    public static /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x0036 -> B:49:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x003a -> B:47:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x003e -> B:53:0x002a). Please submit an issue!!! */
        static {
            int[] iArr = new int[PeopleBackupHelper.SharedFileEntryType.values().length];
            $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType = iArr;
            try {
                iArr[PeopleBackupHelper.SharedFileEntryType.WIDGET_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.SharedFileEntryType.PEOPLE_TILE_KEY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.SharedFileEntryType.CONTACT_URI.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.SharedFileEntryType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleSpaceWidgetManager$TileConversationListener.class */
    public class TileConversationListener implements PeopleManager.ConversationListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$TileConversationListener$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$cAdxdwXiIERJyzg-kL9ls6mxn6g */
        public static /* synthetic */ void m3570$r8$lambda$cAdxdwXiIERJyzgkL9ls6mxn6g(TileConversationListener tileConversationListener, ConversationChannel conversationChannel) {
            tileConversationListener.lambda$onConversationUpdate$0(conversationChannel);
        }

        public TileConversationListener() {
            PeopleSpaceWidgetManager.this = r4;
        }

        public /* synthetic */ void lambda$onConversationUpdate$0(ConversationChannel conversationChannel) {
            PeopleSpaceWidgetManager.this.updateWidgetsWithConversationChanged(conversationChannel);
        }

        public void onConversationUpdate(final ConversationChannel conversationChannel) {
            PeopleSpaceWidgetManager.this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$TileConversationListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PeopleSpaceWidgetManager.TileConversationListener.m3570$r8$lambda$cAdxdwXiIERJyzgkL9ls6mxn6g(PeopleSpaceWidgetManager.TileConversationListener.this, conversationChannel);
                }
            });
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda7.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$-T4Wx-P9Bj4I-mZ6no_GIn7OX8Q */
    public static /* synthetic */ boolean m3560$r8$lambda$T4WxP9Bj4ImZ6no_GIn7OX8Q(PeopleSpaceWidgetManager peopleSpaceWidgetManager, NotificationEntry notificationEntry) {
        return peopleSpaceWidgetManager.lambda$groupConversationNotifications$4(notificationEntry);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$CCGw0jdHEovnm-ZaxTLB3JRuAWQ */
    public static /* synthetic */ void m3561$r8$lambda$CCGw0jdHEovnmZaxTLB3JRuAWQ(PeopleSpaceWidgetManager peopleSpaceWidgetManager, int i, PeopleSpaceTile peopleSpaceTile) {
        peopleSpaceWidgetManager.lambda$addNewWidget$5(i, peopleSpaceTile);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda13.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$IzbOQu01Jgmvnmo5_NMd-N8dbcE */
    public static /* synthetic */ Optional m3562$r8$lambda$IzbOQu01Jgmvnmo5_NMdN8dbcE(PeopleSpaceWidgetManager peopleSpaceWidgetManager, Map map, Integer num) {
        return peopleSpaceWidgetManager.lambda$updateWidgetIdsBasedOnNotifications$2(map, num);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda5.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ ShortcutInfo $r8$lambda$d2d7cXCfRQ2H0V45amvHUKcpFwI(ConversationChannelWrapper conversationChannelWrapper) {
        return conversationChannelWrapper.getShortcutInfo();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$dDcAV00RWjKVXhDRRw8enHKClRg(ConversationChannelWrapper conversationChannelWrapper) {
        return lambda$getPriorityTiles$6(conversationChannelWrapper);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda4.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$jWTPGQBRg4QyylQJu3NR3QW7LxE(ConversationChannelWrapper conversationChannelWrapper) {
        return lambda$getRecentTiles$8(conversationChannelWrapper);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda10.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$kp3JYSbKq58_wgiFgYnHDdAG1rA(String str) {
        return lambda$getNewWidgets$11(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda14.accept(java.lang.Object, java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$tUPo5v4YozvK6TewIS5GAkIy1QY(PeopleSpaceWidgetManager peopleSpaceWidgetManager, Integer num, Optional optional) {
        peopleSpaceWidgetManager.lambda$updateWidgetIdsBasedOnNotifications$3(num, optional);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$tbJhr-JgEfvH0ApMHcdV6KfmLfA */
    public static /* synthetic */ ShortcutInfo m3564$r8$lambda$tbJhrJgEfvH0ApMHcdV6KfmLfA(ConversationChannelWrapper conversationChannelWrapper) {
        return conversationChannelWrapper.getShortcutInfo();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$te9P2qE6QY0XinhGpF7KhQs6dho(PeopleSpaceWidgetManager peopleSpaceWidgetManager, int[] iArr) {
        peopleSpaceWidgetManager.lambda$updateWidgets$0(iArr);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda6.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ ShortcutInfo $r8$lambda$xHCASbZnevJphb2fyWx0qem2JQo(ConversationChannel conversationChannel) {
        return conversationChannel.getShortcutInfo();
    }

    @VisibleForTesting
    public PeopleSpaceWidgetManager(Context context, AppWidgetManager appWidgetManager, IPeopleManager iPeopleManager, PeopleManager peopleManager, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional, UserManager userManager, BackupManager backupManager, INotificationManager iNotificationManager, NotificationManager notificationManager, Executor executor) {
        this.mLock = new Object();
        this.mUiEventLogger = new UiEventLoggerImpl();
        this.mNotificationKeyToWidgetIdsMatchedByUri = new HashMap();
        this.mListener = new NotificationListener.NotificationHandler() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager.1
            {
                PeopleSpaceWidgetManager.this = this;
            }

            public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
                if (notificationChannel.isConversation()) {
                    PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
                    peopleSpaceWidgetManager.updateWidgets(peopleSpaceWidgetManager.mAppWidgetManager.getAppWidgetIds(new ComponentName(PeopleSpaceWidgetManager.this.mContext, PeopleSpaceWidgetProvider.class)));
                }
            }

            public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
                PeopleSpaceWidgetManager.this.updateWidgetsWithNotificationChanged(statusBarNotification, PeopleSpaceUtils.NotificationAction.POSTED);
            }

            public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
            }

            public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
                PeopleSpaceWidgetManager.this.updateWidgetsWithNotificationChanged(statusBarNotification, PeopleSpaceUtils.NotificationAction.REMOVED);
            }

            public void onNotificationsInitialized() {
            }
        };
        this.mBaseBroadcastReceiver = new AnonymousClass2();
        this.mContext = context;
        this.mAppWidgetManager = appWidgetManager;
        this.mIPeopleManager = iPeopleManager;
        this.mPeopleManager = peopleManager;
        this.mLauncherApps = launcherApps;
        this.mNotifCollection = commonNotifCollection;
        this.mPackageManager = packageManager;
        this.mBubblesOptional = optional;
        this.mUserManager = userManager;
        this.mBackupManager = backupManager;
        this.mINotificationManager = iNotificationManager;
        this.mNotificationManager = notificationManager;
        this.mManager = this;
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mBgExecutor = executor;
    }

    public PeopleSpaceWidgetManager(Context context, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional, UserManager userManager, NotificationManager notificationManager, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        this.mLock = new Object();
        this.mUiEventLogger = new UiEventLoggerImpl();
        this.mNotificationKeyToWidgetIdsMatchedByUri = new HashMap();
        this.mListener = new NotificationListener.NotificationHandler() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager.1
            {
                PeopleSpaceWidgetManager.this = this;
            }

            public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
                if (notificationChannel.isConversation()) {
                    PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
                    peopleSpaceWidgetManager.updateWidgets(peopleSpaceWidgetManager.mAppWidgetManager.getAppWidgetIds(new ComponentName(PeopleSpaceWidgetManager.this.mContext, PeopleSpaceWidgetProvider.class)));
                }
            }

            public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
                PeopleSpaceWidgetManager.this.updateWidgetsWithNotificationChanged(statusBarNotification, PeopleSpaceUtils.NotificationAction.POSTED);
            }

            public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
            }

            public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
                PeopleSpaceWidgetManager.this.updateWidgetsWithNotificationChanged(statusBarNotification, PeopleSpaceUtils.NotificationAction.REMOVED);
            }

            public void onNotificationsInitialized() {
            }
        };
        this.mBaseBroadcastReceiver = new AnonymousClass2();
        this.mContext = context;
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
        this.mIPeopleManager = IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
        this.mLauncherApps = launcherApps;
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mPeopleManager = (PeopleManager) context.getSystemService(PeopleManager.class);
        this.mNotifCollection = commonNotifCollection;
        this.mPackageManager = packageManager;
        this.mINotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        this.mBubblesOptional = optional;
        this.mUserManager = userManager;
        this.mBackupManager = new BackupManager(context);
        this.mNotificationManager = notificationManager;
        this.mManager = this;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mBgExecutor = executor;
    }

    public static /* synthetic */ boolean lambda$getNewWidgets$11(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static /* synthetic */ boolean lambda$getPriorityTiles$6(ConversationChannelWrapper conversationChannelWrapper) {
        return conversationChannelWrapper.getNotificationChannel() != null && conversationChannelWrapper.getNotificationChannel().isImportantConversation();
    }

    public static /* synthetic */ boolean lambda$getRecentTiles$8(ConversationChannelWrapper conversationChannelWrapper) {
        return conversationChannelWrapper.getNotificationChannel() == null || !conversationChannelWrapper.getNotificationChannel().isImportantConversation();
    }

    public /* synthetic */ boolean lambda$groupConversationNotifications$4(NotificationEntry notificationEntry) {
        return NotificationHelper.isValid(notificationEntry) && NotificationHelper.isMissedCallOrHasContent(notificationEntry) && !NotificationHelper.shouldFilterOut(this.mBubblesOptional, notificationEntry);
    }

    public /* synthetic */ Optional lambda$updateWidgetIdsBasedOnNotifications$2(Map map, Integer num) {
        return getAugmentedTileForExistingWidget(num.intValue(), map);
    }

    public /* synthetic */ void lambda$updateWidgetIdsBasedOnNotifications$3(Integer num, Optional optional) {
        updateAppWidgetOptionsAndViewOptional(num.intValue(), optional);
    }

    public void addNewWidget(final int i, PeopleTileKey peopleTileKey) {
        PeopleTileKey keyFromStorageByWidgetId;
        try {
            PeopleSpaceTile tileFromPersistentStorage = getTileFromPersistentStorage(peopleTileKey, i, false);
            if (tileFromPersistentStorage == null) {
                return;
            }
            final PeopleSpaceTile augmentTileFromNotificationEntryManager = augmentTileFromNotificationEntryManager(tileFromPersistentStorage, Optional.of(Integer.valueOf(i)));
            synchronized (this.mLock) {
                keyFromStorageByWidgetId = getKeyFromStorageByWidgetId(i);
            }
            if (PeopleTileKey.isValid(keyFromStorageByWidgetId)) {
                deleteWidgets(new int[]{i});
            } else {
                this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_ADDED);
            }
            synchronized (this.mLock) {
                PeopleSpaceUtils.setSharedPreferencesStorageForTile(this.mContext, peopleTileKey, i, augmentTileFromNotificationEntryManager.getContactUri(), this.mBackupManager);
            }
            registerConversationListenerIfNeeded(i, peopleTileKey);
            try {
                this.mLauncherApps.cacheShortcuts(augmentTileFromNotificationEntryManager.getPackageName(), Collections.singletonList(augmentTileFromNotificationEntryManager.getId()), augmentTileFromNotificationEntryManager.getUserHandle(), 2);
            } catch (Exception e) {
                Log.w("PeopleSpaceWidgetMgr", "failed to cache shortcut for widget " + i, e);
            }
            this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PeopleSpaceWidgetManager.m3561$r8$lambda$CCGw0jdHEovnmZaxTLB3JRuAWQ(PeopleSpaceWidgetManager.this, i, augmentTileFromNotificationEntryManager);
                }
            });
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("PeopleSpaceWidgetMgr", "Cannot add widget " + i + " since app was uninstalled");
        }
    }

    public void attach(NotificationListener notificationListener) {
        notificationListener.addNotificationHandler(this.mListener);
    }

    public PeopleSpaceTile augmentTileFromNotificationEntryManager(PeopleSpaceTile peopleSpaceTile, Optional<Integer> optional) {
        return augmentTileFromNotifications(peopleSpaceTile, new PeopleTileKey(peopleSpaceTile), peopleSpaceTile.getContactUri() != null ? peopleSpaceTile.getContactUri().toString() : null, groupConversationNotifications(this.mNotifCollection.getAllNotifs()), optional);
    }

    /* JADX DEBUG: Type inference failed for r0v29. Raw type applied. Possible types: java.util.List<com.android.systemui.statusbar.notification.collection.NotificationEntry> */
    /* JADX WARN: Multi-variable type inference failed */
    public PeopleSpaceTile augmentTileFromNotifications(PeopleSpaceTile peopleSpaceTile, PeopleTileKey peopleTileKey, String str, Map<PeopleTileKey, Set<NotificationEntry>> map, Optional<Integer> optional) {
        boolean z = this.mPackageManager.checkPermission("android.permission.READ_CONTACTS", peopleSpaceTile.getPackageName()) == 0;
        List arrayList = new ArrayList();
        if (z) {
            arrayList = PeopleSpaceUtils.getNotificationsByUri(this.mPackageManager, str, map);
            arrayList.isEmpty();
        }
        Set<NotificationEntry> set = map.get(peopleTileKey);
        HashSet hashSet = set;
        if (set == null) {
            hashSet = new HashSet();
        }
        if (hashSet.isEmpty() && arrayList.isEmpty()) {
            return PeopleSpaceUtils.removeNotificationFields(peopleSpaceTile);
        }
        hashSet.addAll(arrayList);
        return PeopleSpaceUtils.augmentTileFromNotification(this.mContext, peopleSpaceTile, peopleTileKey, NotificationHelper.getHighestPriorityNotification(hashSet), PeopleSpaceUtils.getMessagesCount(hashSet), optional, this.mBackupManager);
    }

    public void deleteWidgets(int[] iArr) {
        PeopleTileKey peopleTileKey;
        HashSet hashSet;
        String string;
        for (int i : iArr) {
            this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_DELETED);
            synchronized (this.mLock) {
                SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
                peopleTileKey = new PeopleTileKey(sharedPreferences.getString("shortcut_id", null), sharedPreferences.getInt("user_id", -1), sharedPreferences.getString("package_name", null));
                if (!PeopleTileKey.isValid(peopleTileKey)) {
                    Log.e("PeopleSpaceWidgetMgr", "Invalid tile key trying to remove widget " + i);
                    return;
                }
                hashSet = new HashSet(this.mSharedPrefs.getStringSet(peopleTileKey.toString(), new HashSet()));
                string = this.mSharedPrefs.getString(String.valueOf(i), null);
            }
            synchronized (this.mLock) {
                PeopleSpaceUtils.removeSharedPreferencesStorageForTile(this.mContext, peopleTileKey, i, string);
            }
            if (hashSet.contains(String.valueOf(i)) && hashSet.size() == 1) {
                unregisterConversationListener(peopleTileKey, i);
                uncacheConversationShortcut(peopleTileKey);
            }
        }
    }

    public final Set<String> fetchMatchingUriWidgetIds(StatusBarNotification statusBarNotification) {
        String contactUri;
        if (NotificationHelper.shouldMatchNotificationByUri(statusBarNotification) && (contactUri = NotificationHelper.getContactUri(statusBarNotification)) != null) {
            HashSet hashSet = new HashSet(this.mSharedPrefs.getStringSet(contactUri, new HashSet()));
            if (hashSet.isEmpty()) {
                return null;
            }
            return hashSet;
        }
        return null;
    }

    public Optional<PeopleSpaceTile> getAugmentedTileForExistingWidget(int i, Map<PeopleTileKey, Set<NotificationEntry>> map) {
        PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
        if (tileForExistingWidget != null) {
            return Optional.ofNullable(augmentTileFromNotifications(tileForExistingWidget, new PeopleTileKey(tileForExistingWidget), this.mSharedPrefs.getString(String.valueOf(i), null), map, Optional.of(Integer.valueOf(i))));
        }
        Log.w("PeopleSpaceWidgetMgr", "Null tile for existing widget " + i + ", skipping update.");
        return Optional.empty();
    }

    public final PeopleTileKey getKeyFromStorageByWidgetId(int i) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
        return new PeopleTileKey(sharedPreferences.getString("shortcut_id", ""), sharedPreferences.getInt("user_id", -1), sharedPreferences.getString("package_name", ""));
    }

    public Set<String> getMatchingKeyWidgetIds(PeopleTileKey peopleTileKey) {
        return !PeopleTileKey.isValid(peopleTileKey) ? new HashSet() : new HashSet(this.mSharedPrefs.getStringSet(peopleTileKey.toString(), new HashSet()));
    }

    public final Set<String> getMatchingUriWidgetIds(StatusBarNotification statusBarNotification, PeopleSpaceUtils.NotificationAction notificationAction) {
        if (notificationAction.equals(PeopleSpaceUtils.NotificationAction.POSTED)) {
            Set<String> fetchMatchingUriWidgetIds = fetchMatchingUriWidgetIds(statusBarNotification);
            if (fetchMatchingUriWidgetIds != null && !fetchMatchingUriWidgetIds.isEmpty()) {
                this.mNotificationKeyToWidgetIdsMatchedByUri.put(statusBarNotification.getKey(), fetchMatchingUriWidgetIds);
                return fetchMatchingUriWidgetIds;
            }
        } else {
            Set<String> remove = this.mNotificationKeyToWidgetIdsMatchedByUri.remove(statusBarNotification.getKey());
            if (remove != null && !remove.isEmpty()) {
                return remove;
            }
        }
        return new HashSet();
    }

    public final Set<String> getNewWidgets(Set<String> set, final Map<String, String> map) {
        Stream<String> stream = set.stream();
        Objects.requireNonNull(map);
        return (Set) stream.map(new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) map.get((String) obj);
            }
        }).filter(new Predicate() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PeopleSpaceWidgetManager.$r8$lambda$kp3JYSbKq58_wgiFgYnHDdAG1rA((String) obj);
            }
        }).collect(Collectors.toSet());
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getNotificationPolicyState() {
        int currentInterruptionFilter;
        int i;
        NotificationManager.Policy notificationPolicy = this.mNotificationManager.getNotificationPolicy();
        if (!NotificationManager.Policy.areAllVisualEffectsSuppressed(notificationPolicy.suppressedVisualEffects) || (currentInterruptionFilter = this.mNotificationManager.getCurrentInterruptionFilter()) == 1) {
            return 1;
        }
        if (currentInterruptionFilter != 2) {
            return 2;
        }
        if (notificationPolicy.allowConversations()) {
            int i2 = notificationPolicy.priorityConversationSenders;
            if (i2 == 1) {
                return 1;
            }
            if (i2 == 2) {
                i = 4;
                if (notificationPolicy.allowMessages()) {
                    if (i != 0) {
                        return i;
                    }
                    return 2;
                }
                int allowMessagesFrom = notificationPolicy.allowMessagesFrom();
                if (allowMessagesFrom != 1) {
                    if (allowMessagesFrom != 2) {
                        return 1;
                    }
                    return i | 8;
                }
                return i | 16;
            }
        }
        i = 0;
        if (notificationPolicy.allowMessages()) {
        }
    }

    public final boolean getPackageSuspended(PeopleSpaceTile peopleSpaceTile) throws PackageManager.NameNotFoundException {
        boolean z = !TextUtils.isEmpty(peopleSpaceTile.getPackageName()) && this.mPackageManager.isPackageSuspended(peopleSpaceTile.getPackageName());
        this.mPackageManager.getApplicationInfoAsUser(peopleSpaceTile.getPackageName(), RecyclerView.ViewHolder.FLAG_IGNORE, PeopleSpaceUtils.getUserId(peopleSpaceTile));
        return z;
    }

    public RemoteViews getPreview(String str, UserHandle userHandle, String str2, Bundle bundle) {
        try {
            PeopleSpaceTile tile = PeopleSpaceUtils.getTile(this.mIPeopleManager.getConversation(str2, userHandle.getIdentifier(), str), this.mLauncherApps);
            if (tile == null) {
                return null;
            }
            PeopleSpaceTile augmentTileFromNotificationEntryManager = augmentTileFromNotificationEntryManager(tile, Optional.empty());
            return PeopleTileViewHelper.createRemoteViews(this.mContext, augmentTileFromNotificationEntryManager, 0, bundle, new PeopleTileKey(augmentTileFromNotificationEntryManager));
        } catch (Exception e) {
            Log.w("PeopleSpaceWidgetMgr", "failed to get conversation or tile", e);
            return null;
        }
    }

    public List<PeopleSpaceTile> getPriorityTiles() throws Exception {
        return PeopleSpaceUtils.getSortedTiles(this.mIPeopleManager, this.mLauncherApps, this.mUserManager, this.mINotificationManager.getConversations(true).getList().stream().filter(new Predicate() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PeopleSpaceWidgetManager.$r8$lambda$dDcAV00RWjKVXhDRRw8enHKClRg((ConversationChannelWrapper) obj);
            }
        }).map(new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PeopleSpaceWidgetManager.m3564$r8$lambda$tbJhrJgEfvH0ApMHcdV6KfmLfA((ConversationChannelWrapper) obj);
            }
        }));
    }

    public List<PeopleSpaceTile> getRecentTiles() throws Exception {
        return PeopleSpaceUtils.getSortedTiles(this.mIPeopleManager, this.mLauncherApps, this.mUserManager, Stream.concat(this.mINotificationManager.getConversations(false).getList().stream().filter(new Predicate() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PeopleSpaceWidgetManager.$r8$lambda$jWTPGQBRg4QyylQJu3NR3QW7LxE((ConversationChannelWrapper) obj);
            }
        }).map(new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PeopleSpaceWidgetManager.$r8$lambda$d2d7cXCfRQ2H0V45amvHUKcpFwI((ConversationChannelWrapper) obj);
            }
        }), this.mIPeopleManager.getRecentConversations().getList().stream().map(new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PeopleSpaceWidgetManager.$r8$lambda$xHCASbZnevJphb2fyWx0qem2JQo((ConversationChannel) obj);
            }
        })));
    }

    public PeopleSpaceTile getTileForExistingWidget(int i) {
        try {
            return getTileForExistingWidgetThrowing(i);
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "failed to retrieve tile for existing widget " + i, e);
            return null;
        }
    }

    public final PeopleSpaceTile getTileForExistingWidgetThrowing(int i) throws PackageManager.NameNotFoundException {
        PeopleSpaceTile peopleSpaceTile;
        synchronized (mTiles) {
            peopleSpaceTile = mTiles.get(Integer.valueOf(i));
        }
        if (peopleSpaceTile != null) {
            return peopleSpaceTile;
        }
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
        return getTileFromPersistentStorage(new PeopleTileKey(sharedPreferences.getString("shortcut_id", ""), sharedPreferences.getInt("user_id", -1), sharedPreferences.getString("package_name", "")), i, true);
    }

    public PeopleSpaceTile getTileFromPersistentStorage(PeopleTileKey peopleTileKey, int i, boolean z) throws PackageManager.NameNotFoundException {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            Log.e("PeopleSpaceWidgetMgr", "Invalid tile key finding tile for existing widget " + i);
            return null;
        }
        IPeopleManager iPeopleManager = this.mIPeopleManager;
        if (iPeopleManager == null || this.mLauncherApps == null) {
            Log.d("PeopleSpaceWidgetMgr", "System services are null");
            return null;
        }
        try {
            ConversationChannel conversation = iPeopleManager.getConversation(peopleTileKey.getPackageName(), peopleTileKey.getUserId(), peopleTileKey.getShortcutId());
            if (conversation == null) {
                return null;
            }
            PeopleSpaceTile.Builder builder = new PeopleSpaceTile.Builder(conversation, this.mLauncherApps);
            String string = this.mSharedPrefs.getString(String.valueOf(i), null);
            if (z && string != null && builder.build().getContactUri() == null) {
                builder.setContactUri(Uri.parse(string));
            }
            return getTileWithCurrentState(builder.build(), "android.intent.action.BOOT_COMPLETED");
        } catch (RemoteException e) {
            Log.e("PeopleSpaceWidgetMgr", "getTileFromPersistentStorage failing for widget " + i, e);
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final PeopleSpaceTile getTileWithCurrentState(PeopleSpaceTile peopleSpaceTile, String str) throws PackageManager.NameNotFoundException {
        boolean z;
        PeopleSpaceTile.Builder builder = peopleSpaceTile.toBuilder();
        switch (str.hashCode()) {
            case -1238404651:
                if (str.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case -1001645458:
                if (str.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case -864107122:
                if (str.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case -19011148:
                if (str.equals("android.intent.action.LOCALE_CHANGED")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 798292259:
                if (str.equals("android.intent.action.BOOT_COMPLETED")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 833559602:
                if (str.equals("android.intent.action.USER_UNLOCKED")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 1290767157:
                if (str.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 2106958107:
                if (str.equals("android.app.action.INTERRUPTION_FILTER_CHANGED")) {
                    z = false;
                    break;
                }
                z = true;
                break;
            default:
                z = true;
                break;
        }
        switch (z) {
            case false:
                builder.setNotificationPolicyState(getNotificationPolicyState());
                break;
            case true:
            case true:
                builder.setIsPackageSuspended(getPackageSuspended(peopleSpaceTile));
                break;
            case true:
            case true:
            case true:
                builder.setIsUserQuieted(getUserQuieted(peopleSpaceTile));
                break;
            case true:
                break;
            default:
                builder.setIsUserQuieted(getUserQuieted(peopleSpaceTile)).setIsPackageSuspended(getPackageSuspended(peopleSpaceTile)).setNotificationPolicyState(getNotificationPolicyState());
                break;
        }
        return builder.build();
    }

    public final boolean getUserQuieted(PeopleSpaceTile peopleSpaceTile) {
        return peopleSpaceTile.getUserHandle() != null && this.mUserManager.isQuietModeEnabled(peopleSpaceTile.getUserHandle());
    }

    public Map<PeopleTileKey, Set<NotificationEntry>> groupConversationNotifications(Collection<NotificationEntry> collection) {
        return (Map) collection.stream().filter(new Predicate() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PeopleSpaceWidgetManager.m3560$r8$lambda$T4WxP9Bj4ImZ6no_GIn7OX8Q(PeopleSpaceWidgetManager.this, (NotificationEntry) obj);
            }
        }).collect(Collectors.groupingBy(new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new PeopleTileKey((NotificationEntry) obj);
            }
        }, Collectors.mapping(Function.identity(), Collectors.toSet())));
    }

    public void init() {
        synchronized (this.mLock) {
            if (!this.mRegisteredReceivers) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.app.action.INTERRUPTION_FILTER_CHANGED");
                intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
                intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
                intentFilter.addAction("android.intent.action.PACKAGES_SUSPENDED");
                intentFilter.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
                intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
                intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
                intentFilter.addAction("android.intent.action.USER_UNLOCKED");
                this.mBroadcastDispatcher.registerReceiver(this.mBaseBroadcastReceiver, intentFilter, null, UserHandle.ALL);
                IntentFilter intentFilter2 = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
                intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
                intentFilter2.addDataScheme("package");
                this.mContext.registerReceiver(this.mBaseBroadcastReceiver, intentFilter2);
                IntentFilter intentFilter3 = new IntentFilter("android.intent.action.BOOT_COMPLETED");
                intentFilter3.setPriority(1000);
                this.mContext.registerReceiver(this.mBaseBroadcastReceiver, intentFilter3);
                this.mRegisteredReceivers = true;
            }
        }
    }

    public void onAppWidgetOptionsChanged(int i, Bundle bundle) {
        PeopleTileKey peopleTileKeyFromBundle = AppWidgetOptionsHelper.getPeopleTileKeyFromBundle(bundle);
        if (PeopleTileKey.isValid(peopleTileKeyFromBundle)) {
            AppWidgetOptionsHelper.removePeopleTileKey(this.mAppWidgetManager, i);
            addNewWidget(i, peopleTileKeyFromBundle);
        }
        updateWidgets(new int[]{i});
    }

    public void registerConversationListenerIfNeeded(int i, PeopleTileKey peopleTileKey) {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            Log.w("PeopleSpaceWidgetMgr", "Invalid tile key registering listener for widget " + i);
            return;
        }
        TileConversationListener tileConversationListener = new TileConversationListener();
        synchronized (mListeners) {
            if (mListeners.containsKey(peopleTileKey)) {
                return;
            }
            mListeners.put(peopleTileKey, tileConversationListener);
            this.mPeopleManager.registerConversationListener(peopleTileKey.getPackageName(), peopleTileKey.getUserId(), peopleTileKey.getShortcutId(), tileConversationListener, this.mContext.getMainExecutor());
        }
    }

    public void remapFollowupFile(Map<String, String> map) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("shared_follow_up", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            try {
                edit.putStringSet(key, getNewWidgets((Set) entry.getValue(), map));
            } catch (Exception e) {
                Log.e("PeopleSpaceWidgetMgr", "malformed entry value: " + entry.getValue(), e);
                edit.remove(key);
            }
        }
        edit.apply();
    }

    public void remapSharedFile(Map<String, String> map) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        for (Map.Entry<String, ?> entry : defaultSharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            int i = AnonymousClass3.$SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.getEntryType(entry).ordinal()];
            if (i == 1) {
                String str = map.get(key);
                if (TextUtils.isEmpty(str)) {
                    Log.w("PeopleSpaceWidgetMgr", "Key is widget id without matching new id, skipping: " + key);
                } else {
                    try {
                        edit.putString(str, (String) entry.getValue());
                    } catch (Exception e) {
                        Log.e("PeopleSpaceWidgetMgr", "malformed entry value: " + entry.getValue(), e);
                    }
                    edit.remove(key);
                }
            } else if (i == 2 || i == 3) {
                try {
                    edit.putStringSet(key, getNewWidgets((Set) entry.getValue(), map));
                } catch (Exception e2) {
                    Log.e("PeopleSpaceWidgetMgr", "malformed entry value: " + entry.getValue(), e2);
                    edit.remove(key);
                }
            } else if (i == 4) {
                Log.e("PeopleSpaceWidgetMgr", "Key not identified:" + key);
            }
        }
        edit.apply();
    }

    public void remapWidgetFiles(Map<String, String> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String valueOf = String.valueOf(entry.getKey());
            String valueOf2 = String.valueOf(entry.getValue());
            if (!valueOf.equals(valueOf2)) {
                SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(valueOf, 0);
                PeopleTileKey peopleTileKey = SharedPreferencesHelper.getPeopleTileKey(sharedPreferences);
                if (PeopleTileKey.isValid(peopleTileKey)) {
                    hashMap.put(valueOf2, peopleTileKey);
                    SharedPreferencesHelper.clear(sharedPreferences);
                }
            }
        }
        for (Map.Entry entry2 : hashMap.entrySet()) {
            SharedPreferencesHelper.setPeopleTileKey(this.mContext.getSharedPreferences((String) entry2.getKey(), 0), (PeopleTileKey) entry2.getValue());
        }
    }

    public void remapWidgets(int[] iArr, int[] iArr2) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < iArr.length; i++) {
            hashMap.put(String.valueOf(iArr[i]), String.valueOf(iArr2[i]));
        }
        remapWidgetFiles(hashMap);
        remapSharedFile(hashMap);
        remapFollowupFile(hashMap);
        int[] appWidgetIds = this.mAppWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, PeopleSpaceWidgetProvider.class));
        Bundle bundle = new Bundle();
        bundle.putBoolean("appWidgetRestoreCompleted", true);
        for (int i2 : appWidgetIds) {
            this.mAppWidgetManager.updateAppWidgetOptions(i2, bundle);
        }
        updateWidgets(appWidgetIds);
    }

    public boolean requestPinAppWidget(ShortcutInfo shortcutInfo, Bundle bundle) {
        RemoteViews preview = getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
        if (preview == null) {
            Log.w("PeopleSpaceWidgetMgr", "Skipping pinning widget: no tile for shortcutId: " + shortcutInfo.getId());
            return false;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("appWidgetPreview", preview);
        PendingIntent pendingIntent = PeopleSpaceWidgetPinnedReceiver.getPendingIntent(this.mContext, shortcutInfo);
        return this.mAppWidgetManager.requestPinAppWidget(new ComponentName(this.mContext, PeopleSpaceWidgetProvider.class), bundle2, pendingIntent);
    }

    public final void uncacheConversationShortcut(PeopleTileKey peopleTileKey) {
        try {
            this.mLauncherApps.uncacheShortcuts(peopleTileKey.getPackageName(), Collections.singletonList(peopleTileKey.getShortcutId()), UserHandle.of(peopleTileKey.getUserId()), 2);
        } catch (Exception e) {
            Log.d("PeopleSpaceWidgetMgr", "failed to uncache shortcut", e);
        }
    }

    public final void unregisterConversationListener(PeopleTileKey peopleTileKey, int i) {
        synchronized (mListeners) {
            TileConversationListener tileConversationListener = mListeners.get(peopleTileKey);
            if (tileConversationListener == null) {
                return;
            }
            mListeners.remove(peopleTileKey);
            this.mPeopleManager.unregisterConversationListener(tileConversationListener);
        }
    }

    /* renamed from: updateAppWidgetOptionsAndView */
    public void lambda$addNewWidget$5(int i, PeopleSpaceTile peopleSpaceTile) {
        if (peopleSpaceTile == null) {
            Log.w("PeopleSpaceWidgetMgr", "Storing null tile for widget " + i);
        }
        synchronized (mTiles) {
            mTiles.put(Integer.valueOf(i), peopleSpaceTile);
        }
        updateAppWidgetViews(i, peopleSpaceTile, this.mAppWidgetManager.getAppWidgetOptions(i));
    }

    public void updateAppWidgetOptionsAndViewOptional(int i, Optional<PeopleSpaceTile> optional) {
        if (optional.isPresent()) {
            lambda$addNewWidget$5(i, optional.get());
        }
    }

    public final void updateAppWidgetViews(int i, PeopleSpaceTile peopleSpaceTile, Bundle bundle) {
        PeopleTileKey keyFromStorageByWidgetId = getKeyFromStorageByWidgetId(i);
        if (PeopleTileKey.isValid(keyFromStorageByWidgetId)) {
            this.mAppWidgetManager.updateAppWidget(i, PeopleTileViewHelper.createRemoteViews(this.mContext, peopleSpaceTile, i, bundle, keyFromStorageByWidgetId));
            return;
        }
        Log.e("PeopleSpaceWidgetMgr", "Invalid tile key updating widget " + i);
    }

    public void updateSingleConversationWidgets(int[] iArr) {
        HashMap hashMap = new HashMap();
        for (int i : iArr) {
            PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
            if (tileForExistingWidget == null) {
                Log.e("PeopleSpaceWidgetMgr", "Matching conversation not found for widget " + i);
            }
            lambda$addNewWidget$5(i, tileForExistingWidget);
            hashMap.put(Integer.valueOf(i), tileForExistingWidget);
            if (tileForExistingWidget != null) {
                registerConversationListenerIfNeeded(i, new PeopleTileKey(tileForExistingWidget));
            }
        }
        PeopleSpaceUtils.getDataFromContactsOnBackgroundThread(this.mContext, this.mManager, hashMap, iArr);
    }

    public final void updateStorageAndViewWithConversationData(ConversationChannel conversationChannel, int i) {
        PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
        if (tileForExistingWidget == null) {
            return;
        }
        PeopleSpaceTile.Builder builder = tileForExistingWidget.toBuilder();
        ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
        Uri uri = null;
        if (shortcutInfo.getPersons() != null) {
            uri = null;
            if (shortcutInfo.getPersons().length > 0) {
                Person person = shortcutInfo.getPersons()[0];
                uri = person.getUri() == null ? null : Uri.parse(person.getUri());
            }
        }
        CharSequence label = shortcutInfo.getLabel();
        if (label != null) {
            builder.setUserName(label);
        }
        Icon convertDrawableToIcon = PeopleSpaceTile.convertDrawableToIcon(this.mLauncherApps.getShortcutIconDrawable(shortcutInfo, 0));
        if (convertDrawableToIcon != null) {
            builder.setUserIcon(convertDrawableToIcon);
        }
        NotificationChannel notificationChannel = conversationChannel.getNotificationChannel();
        if (notificationChannel != null) {
            builder.setIsImportantConversation(notificationChannel.isImportantConversation());
        }
        builder.setContactUri(uri).setStatuses(conversationChannel.getStatuses()).setLastInteractionTimestamp(conversationChannel.getLastEventTimestamp());
        lambda$addNewWidget$5(i, builder.build());
    }

    public final void updateWidgetIdsBasedOnNotifications(Set<String> set, Collection<NotificationEntry> collection) {
        if (set.isEmpty()) {
            return;
        }
        try {
            final Map<PeopleTileKey, Set<NotificationEntry>> groupConversationNotifications = groupConversationNotifications(collection);
            ((Map) set.stream().map(new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda12
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(Integer.parseInt((String) obj));
                }
            }).collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda13
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PeopleSpaceWidgetManager.m3562$r8$lambda$IzbOQu01Jgmvnmo5_NMdN8dbcE(PeopleSpaceWidgetManager.this, groupConversationNotifications, (Integer) obj);
                }
            }))).forEach(new BiConsumer() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda14
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    PeopleSpaceWidgetManager.$r8$lambda$tUPo5v4YozvK6TewIS5GAkIy1QY(PeopleSpaceWidgetManager.this, (Integer) obj, (Optional) obj2);
                }
            });
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "updateWidgetIdsBasedOnNotifications failing", e);
        }
    }

    public void updateWidgets(final int[] iArr) {
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PeopleSpaceWidgetManager.$r8$lambda$te9P2qE6QY0XinhGpF7KhQs6dho(PeopleSpaceWidgetManager.this, iArr);
            }
        });
    }

    @VisibleForTesting
    public void updateWidgetsFromBroadcastInBackground(String str) {
        int[] appWidgetIds = this.mAppWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, PeopleSpaceWidgetProvider.class));
        if (appWidgetIds == null) {
            return;
        }
        for (int i : appWidgetIds) {
            try {
                synchronized (this.mLock) {
                    PeopleSpaceTile tileForExistingWidgetThrowing = getTileForExistingWidgetThrowing(i);
                    if (tileForExistingWidgetThrowing == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Matching conversation not found for widget ");
                        sb.append(i);
                        Log.e("PeopleSpaceWidgetMgr", sb.toString());
                    } else {
                        lambda$addNewWidget$5(i, getTileWithCurrentState(tileForExistingWidgetThrowing, str));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("PeopleSpaceWidgetMgr", "Package no longer found for widget " + i, e);
                JobScheduler jobScheduler = (JobScheduler) this.mContext.getSystemService(JobScheduler.class);
                if (jobScheduler == null || jobScheduler.getPendingJob(74823873) == null) {
                    synchronized (this.mLock) {
                        lambda$addNewWidget$5(i, null);
                        deleteWidgets(new int[]{i});
                    }
                } else {
                    continue;
                }
            }
        }
    }

    /* renamed from: updateWidgetsInBackground */
    public final void lambda$updateWidgets$0(int[] iArr) {
        try {
            if (iArr.length == 0) {
                return;
            }
            synchronized (this.mLock) {
                updateSingleConversationWidgets(iArr);
            }
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "failed to update widgets", e);
        }
    }

    public void updateWidgetsWithConversationChanged(ConversationChannel conversationChannel) {
        ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
        synchronized (this.mLock) {
            for (String str : getMatchingKeyWidgetIds(new PeopleTileKey(shortcutInfo.getId(), shortcutInfo.getUserId(), shortcutInfo.getPackage()))) {
                updateStorageAndViewWithConversationData(conversationChannel, Integer.parseInt(str));
            }
        }
    }

    public void updateWidgetsWithNotificationChanged(final StatusBarNotification statusBarNotification, final PeopleSpaceUtils.NotificationAction notificationAction) {
        final Collection allNotifs = this.mNotifCollection.getAllNotifs();
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                PeopleSpaceWidgetManager.this.lambda$updateWidgetsWithNotificationChanged$1(statusBarNotification, notificationAction, allNotifs);
            }
        });
    }

    /* renamed from: updateWidgetsWithNotificationChangedInBackground */
    public final void lambda$updateWidgetsWithNotificationChanged$1(StatusBarNotification statusBarNotification, PeopleSpaceUtils.NotificationAction notificationAction, Collection<NotificationEntry> collection) {
        try {
            PeopleTileKey peopleTileKey = new PeopleTileKey(statusBarNotification.getShortcutId(), statusBarNotification.getUser().getIdentifier(), statusBarNotification.getPackageName());
            if (PeopleTileKey.isValid(peopleTileKey)) {
                if (this.mAppWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, PeopleSpaceWidgetProvider.class)).length == 0) {
                    Log.d("PeopleSpaceWidgetMgr", "No app widget ids returned");
                    return;
                }
                synchronized (this.mLock) {
                    Set<String> matchingKeyWidgetIds = getMatchingKeyWidgetIds(peopleTileKey);
                    matchingKeyWidgetIds.addAll(getMatchingUriWidgetIds(statusBarNotification, notificationAction));
                    updateWidgetIdsBasedOnNotifications(matchingKeyWidgetIds, collection);
                }
            }
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "updateWidgetsWithNotificationChangedInBackground failing", e);
        }
    }
}