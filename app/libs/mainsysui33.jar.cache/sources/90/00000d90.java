package com.android.settingslib.applications;

import android.app.ActivityManager;
import android.app.Application;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.format.Formatter;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.SparseArray;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.ArrayUtils;
import com.android.settingslib.R$string;
import com.android.settingslib.Utils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.utils.ThreadUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/* loaded from: mainsysui33.jar:com/android/settingslib/applications/ApplicationsState.class */
public class ApplicationsState {
    public static ApplicationsState sInstance;
    public final int mAdminRetrieveFlags;
    public final BackgroundHandler mBackgroundHandler;
    public final Context mContext;
    public String mCurComputingSizePkg;
    public int mCurComputingSizeUserId;
    public UUID mCurComputingSizeUuid;
    public final IconDrawableFactory mDrawableFactory;
    public boolean mHaveDisabledApps;
    public boolean mHaveInstantApps;
    public final IPackageManager mIpm;
    public PackageIntentReceiver mPackageIntentReceiver;
    public final PackageManager mPm;
    public boolean mResumed;
    public final int mRetrieveFlags;
    public boolean mSessionsChanged;
    public final StorageStatsManager mStats;
    public final HandlerThread mThread;
    public final UserManager mUm;
    public static final Object sLock = new Object();
    public static final Pattern REMOVE_DIACRITICALS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    public static final Comparator<AppEntry> ALPHA_COMPARATOR = new Comparator<AppEntry>() { // from class: com.android.settingslib.applications.ApplicationsState.1
        public final Collator sCollator = Collator.getInstance();

        @Override // java.util.Comparator
        public int compare(AppEntry appEntry, AppEntry appEntry2) {
            ApplicationInfo applicationInfo;
            int compare;
            int compare2 = this.sCollator.compare(appEntry.label, appEntry2.label);
            if (compare2 != 0) {
                return compare2;
            }
            ApplicationInfo applicationInfo2 = appEntry.info;
            return (applicationInfo2 == null || (applicationInfo = appEntry2.info) == null || (compare = this.sCollator.compare(applicationInfo2.packageName, applicationInfo.packageName)) == 0) ? appEntry.info.uid - appEntry2.info.uid : compare;
        }
    };
    public static final Comparator<AppEntry> SIZE_COMPARATOR = new Comparator<AppEntry>() { // from class: com.android.settingslib.applications.ApplicationsState.2
        @Override // java.util.Comparator
        public int compare(AppEntry appEntry, AppEntry appEntry2) {
            long j = appEntry.size;
            long j2 = appEntry2.size;
            if (j < j2) {
                return 1;
            }
            if (j > j2) {
                return -1;
            }
            return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
        }
    };
    public static final Comparator<AppEntry> INTERNAL_SIZE_COMPARATOR = new Comparator<AppEntry>() { // from class: com.android.settingslib.applications.ApplicationsState.3
        @Override // java.util.Comparator
        public int compare(AppEntry appEntry, AppEntry appEntry2) {
            long j = appEntry.internalSize;
            long j2 = appEntry2.internalSize;
            if (j < j2) {
                return 1;
            }
            if (j > j2) {
                return -1;
            }
            return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
        }
    };
    public static final Comparator<AppEntry> EXTERNAL_SIZE_COMPARATOR = new Comparator<AppEntry>() { // from class: com.android.settingslib.applications.ApplicationsState.4
        @Override // java.util.Comparator
        public int compare(AppEntry appEntry, AppEntry appEntry2) {
            long j = appEntry.externalSize;
            long j2 = appEntry2.externalSize;
            if (j < j2) {
                return 1;
            }
            if (j > j2) {
                return -1;
            }
            return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
        }
    };
    public static final AppFilter FILTER_PERSONAL = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.5
        public int mCurrentUser;

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return UserHandle.getUserId(appEntry.info.uid) == this.mCurrentUser;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
            this.mCurrentUser = ActivityManager.getCurrentUser();
        }
    };
    public static final AppFilter FILTER_WITHOUT_DISABLED_UNTIL_USED = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.6
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return appEntry.info.enabledSetting != 4;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_WORK = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.7
        public int mCurrentUser;

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return UserHandle.getUserId(appEntry.info.uid) != this.mCurrentUser;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
            this.mCurrentUser = ActivityManager.getCurrentUser();
        }
    };
    public static final AppFilter FILTER_DOWNLOADED_AND_LAUNCHER = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.8
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            if (AppUtils.isInstant(appEntry.info)) {
                return false;
            }
            if (ApplicationsState.hasFlag(appEntry.info.flags, RecyclerView.ViewHolder.FLAG_IGNORE) || !ApplicationsState.hasFlag(appEntry.info.flags, 1) || appEntry.hasLauncherEntry) {
                return true;
            }
            return ApplicationsState.hasFlag(appEntry.info.flags, 1) && appEntry.isHomeApp;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_DOWNLOADED_AND_LAUNCHER_AND_INSTANT = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.9
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return AppUtils.isInstant(appEntry.info) || ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER.filterApp(appEntry);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_THIRD_PARTY = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.10
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return ApplicationsState.hasFlag(appEntry.info.flags, RecyclerView.ViewHolder.FLAG_IGNORE) || !ApplicationsState.hasFlag(appEntry.info.flags, 1);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_DISABLED = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.11
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            ApplicationInfo applicationInfo = appEntry.info;
            return (applicationInfo.enabled || AppUtils.isInstant(applicationInfo)) ? false : true;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_INSTANT = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.12
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return AppUtils.isInstant(appEntry.info);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_ALL_ENABLED = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.13
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            ApplicationInfo applicationInfo = appEntry.info;
            return applicationInfo.enabled && !AppUtils.isInstant(applicationInfo);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_EVERYTHING = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.14
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return !ApplicationsState.hasFlag(appEntry.info.privateFlags, 268435456);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_WITH_DOMAIN_URLS = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.15
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return !AppUtils.isInstant(appEntry.info) && ApplicationsState.hasFlag(appEntry.info.privateFlags, 16);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_NOT_HIDE = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.16
        public String[] mHidePackageNames;

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            if (ArrayUtils.contains(this.mHidePackageNames, appEntry.info.packageName)) {
                ApplicationInfo applicationInfo = appEntry.info;
                return applicationInfo.enabled && applicationInfo.enabledSetting != 4;
            }
            return true;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init(Context context) {
            this.mHidePackageNames = context.getResources().getStringArray(17236082);
        }
    };
    public static final AppFilter FILTER_GAMES = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.17
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry.info) {
                if (!ApplicationsState.hasFlag(appEntry.info.flags, 33554432) && appEntry.info.category != 0) {
                    z = false;
                }
                z = true;
            }
            return z;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_AUDIO = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.18
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry) {
                z = true;
                if (appEntry.info.category != 1) {
                    z = false;
                }
            }
            return z;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_MOVIES = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.19
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry) {
                z = appEntry.info.category == 2;
            }
            return z;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_PHOTOS = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.20
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry) {
                z = appEntry.info.category == 3;
            }
            return z;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_OTHER_APPS = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.21
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry) {
                if (!ApplicationsState.FILTER_AUDIO.filterApp(appEntry) && !ApplicationsState.FILTER_GAMES.filterApp(appEntry) && !ApplicationsState.FILTER_MOVIES.filterApp(appEntry) && !ApplicationsState.FILTER_PHOTOS.filterApp(appEntry)) {
                    z = false;
                }
                z = true;
            }
            return !z;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_APPS_EXCEPT_GAMES = new AppFilter() { // from class: com.android.settingslib.applications.ApplicationsState.22
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean filterApp;
            synchronized (appEntry) {
                filterApp = ApplicationsState.FILTER_GAMES.filterApp(appEntry);
            }
            return !filterApp;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public final ArrayList<Session> mSessions = new ArrayList<>();
    public final ArrayList<Session> mRebuildingSessions = new ArrayList<>();
    public InterestingConfigChanges mInterestingConfigChanges = new InterestingConfigChanges();
    public final SparseArray<HashMap<String, AppEntry>> mEntriesMap = new SparseArray<>();
    public final ArrayList<AppEntry> mAppEntries = new ArrayList<>();
    public List<ApplicationInfo> mApplications = new ArrayList();
    public long mCurId = 1;
    public final HashMap<String, Boolean> mSystemModules = new HashMap<>();
    public final ArrayList<WeakReference<Session>> mActiveSessions = new ArrayList<>();
    public final MainHandler mMainHandler = new MainHandler(Looper.getMainLooper());

    /* loaded from: mainsysui33.jar:com/android/settingslib/applications/ApplicationsState$AppEntry.class */
    public static class AppEntry extends SizeInfo {
        public final File apkFile;
        public long externalSize;
        public String externalSizeStr;
        public boolean hasLauncherEntry;
        public Drawable icon;
        public final long id;
        public ApplicationInfo info;
        public long internalSize;
        public String internalSizeStr;
        public boolean isHomeApp;
        public String label;
        public String labelDescription;
        public boolean launcherEntryEnabled;
        public boolean mounted;
        public long sizeLoadStart;
        public String sizeStr;
        public long size = -1;
        public boolean sizeStale = true;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.applications.ApplicationsState$AppEntry$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$E4NrVZraIZGLd57zEE59LaaRM4o(AppEntry appEntry, Context context) {
            appEntry.lambda$new$0(context);
        }

        public AppEntry(final Context context, ApplicationInfo applicationInfo, long j) {
            this.apkFile = new File(applicationInfo.sourceDir);
            this.id = j;
            this.info = applicationInfo;
            ensureLabel(context);
            if (this.labelDescription == null) {
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.applications.ApplicationsState$AppEntry$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ApplicationsState.AppEntry.$r8$lambda$E4NrVZraIZGLd57zEE59LaaRM4o(ApplicationsState.AppEntry.this, context);
                    }
                });
            }
        }

        public boolean ensureIconLocked(Context context) {
            if (ApplicationsState.isAppIconCacheEnabled(context)) {
                return false;
            }
            if (this.icon == null) {
                if (this.apkFile.exists()) {
                    this.icon = Utils.getBadgedIcon(context, this.info);
                    return true;
                }
                this.mounted = false;
                this.icon = context.getDrawable(17303693);
                return false;
            } else if (this.mounted || !this.apkFile.exists()) {
                return false;
            } else {
                this.mounted = true;
                this.icon = Utils.getBadgedIcon(context, this.info);
                return true;
            }
        }

        public void ensureLabel(Context context) {
            if (this.label == null || !this.mounted) {
                if (!this.apkFile.exists()) {
                    this.mounted = false;
                    this.label = this.info.packageName;
                    return;
                }
                this.mounted = true;
                CharSequence loadLabel = this.info.loadLabel(context.getPackageManager());
                this.label = loadLabel != null ? loadLabel.toString() : this.info.packageName;
            }
        }

        /* renamed from: ensureLabelDescriptionLocked */
        public void lambda$new$0(Context context) {
            if (UserManager.get(context).isManagedProfile(UserHandle.getUserId(this.info.uid))) {
                this.labelDescription = context.getString(R$string.accessibility_work_profile_app_description, this.label);
            } else {
                this.labelDescription = this.label;
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/applications/ApplicationsState$AppFilter.class */
    public interface AppFilter {
        boolean filterApp(AppEntry appEntry);

        void init();

        default void init(Context context) {
            init();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/applications/ApplicationsState$BackgroundHandler.class */
    public class BackgroundHandler extends Handler {
        public boolean mRunning;
        public final IPackageStatsObserver.Stub mStatsObserver;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.applications.ApplicationsState$BackgroundHandler$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$2qSpC_I8r0dptZxhgyLlm3leVfo(BackgroundHandler backgroundHandler) {
            backgroundHandler.lambda$handleMessage$0();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BackgroundHandler(Looper looper) {
            super(looper);
            ApplicationsState.this = r6;
            this.mStatsObserver = new IPackageStatsObserver.Stub() { // from class: com.android.settingslib.applications.ApplicationsState.BackgroundHandler.1
                {
                    BackgroundHandler.this = this;
                }

                public void onGetStatsCompleted(PackageStats packageStats, boolean z) {
                    boolean z2;
                    if (z) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            HashMap<String, AppEntry> hashMap = ApplicationsState.this.mEntriesMap.get(packageStats.userHandle);
                            if (hashMap == null) {
                                return;
                            }
                            AppEntry appEntry = hashMap.get(packageStats.packageName);
                            if (appEntry != null) {
                                synchronized (appEntry) {
                                    z2 = false;
                                    appEntry.sizeStale = false;
                                    appEntry.sizeLoadStart = 0L;
                                    long j = packageStats.externalCodeSize + packageStats.externalObbSize;
                                    long j2 = packageStats.externalDataSize + packageStats.externalMediaSize;
                                    long totalInternalSize = j + j2 + ApplicationsState.this.getTotalInternalSize(packageStats);
                                    if (appEntry.size != totalInternalSize || appEntry.cacheSize != packageStats.cacheSize || appEntry.codeSize != packageStats.codeSize || appEntry.dataSize != packageStats.dataSize || appEntry.externalCodeSize != j || appEntry.externalDataSize != j2 || appEntry.externalCacheSize != packageStats.externalCacheSize) {
                                        appEntry.size = totalInternalSize;
                                        appEntry.cacheSize = packageStats.cacheSize;
                                        appEntry.codeSize = packageStats.codeSize;
                                        appEntry.dataSize = packageStats.dataSize;
                                        appEntry.externalCodeSize = j;
                                        appEntry.externalDataSize = j2;
                                        appEntry.externalCacheSize = packageStats.externalCacheSize;
                                        appEntry.sizeStr = ApplicationsState.this.getSizeStr(totalInternalSize);
                                        long totalInternalSize2 = ApplicationsState.this.getTotalInternalSize(packageStats);
                                        appEntry.internalSize = totalInternalSize2;
                                        appEntry.internalSizeStr = ApplicationsState.this.getSizeStr(totalInternalSize2);
                                        long totalExternalSize = ApplicationsState.this.getTotalExternalSize(packageStats);
                                        appEntry.externalSize = totalExternalSize;
                                        appEntry.externalSizeStr = ApplicationsState.this.getSizeStr(totalExternalSize);
                                        z2 = true;
                                    }
                                }
                                if (z2) {
                                    ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(4, packageStats.packageName));
                                }
                            }
                            String str = ApplicationsState.this.mCurComputingSizePkg;
                            if (str != null && str.equals(packageStats.packageName)) {
                                BackgroundHandler backgroundHandler = BackgroundHandler.this;
                                ApplicationsState applicationsState = ApplicationsState.this;
                                if (applicationsState.mCurComputingSizeUserId == packageStats.userHandle) {
                                    applicationsState.mCurComputingSizePkg = null;
                                    backgroundHandler.sendEmptyMessage(7);
                                }
                            }
                        }
                    }
                }
            };
        }

        public /* synthetic */ void lambda$handleMessage$0() {
            try {
                try {
                    ApplicationsState applicationsState = ApplicationsState.this;
                    StorageStats queryStatsForPackage = applicationsState.mStats.queryStatsForPackage(applicationsState.mCurComputingSizeUuid, applicationsState.mCurComputingSizePkg, UserHandle.of(applicationsState.mCurComputingSizeUserId));
                    ApplicationsState applicationsState2 = ApplicationsState.this;
                    PackageStats packageStats = new PackageStats(applicationsState2.mCurComputingSizePkg, applicationsState2.mCurComputingSizeUserId);
                    packageStats.codeSize = queryStatsForPackage.getAppBytes();
                    packageStats.dataSize = queryStatsForPackage.getDataBytes();
                    packageStats.cacheSize = queryStatsForPackage.getCacheBytes();
                    this.mStatsObserver.onGetStatsCompleted(packageStats, true);
                } catch (PackageManager.NameNotFoundException | IOException e) {
                    Log.w("ApplicationsState", "Failed to query stats: " + e);
                    this.mStatsObserver.onGetStatsCompleted((PackageStats) null, false);
                }
            } catch (RemoteException e2) {
            }
        }

        public final int getCombinedSessionFlags(List<Session> list) {
            int i;
            synchronized (ApplicationsState.this.mEntriesMap) {
                i = 0;
                for (Session session : list) {
                    i |= session.mFlags;
                }
            }
            return i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:358:0x0220, code lost:
            if (r0.mounted == false) goto L91;
         */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(Message message) {
            ArrayList arrayList;
            int i;
            AppEntry appEntry;
            int i2;
            int i3;
            synchronized (ApplicationsState.this.mRebuildingSessions) {
                if (ApplicationsState.this.mRebuildingSessions.size() > 0) {
                    arrayList = new ArrayList(ApplicationsState.this.mRebuildingSessions);
                    ApplicationsState.this.mRebuildingSessions.clear();
                } else {
                    arrayList = null;
                }
            }
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((Session) it.next()).handleRebuildList();
                }
            }
            int combinedSessionFlags = getCombinedSessionFlags(ApplicationsState.this.mSessions);
            int i4 = message.what;
            int i5 = 0;
            switch (i4) {
                case 2:
                    synchronized (ApplicationsState.this.mEntriesMap) {
                        i = 0;
                        int i6 = 0;
                        while (i6 < ApplicationsState.this.mApplications.size() && i < 6) {
                            if (!this.mRunning) {
                                this.mRunning = true;
                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                            }
                            ApplicationInfo applicationInfo = ApplicationsState.this.mApplications.get(i6);
                            int userId = UserHandle.getUserId(applicationInfo.uid);
                            int i7 = i;
                            if (ApplicationsState.this.mEntriesMap.get(userId).get(applicationInfo.packageName) == null) {
                                i7 = i + 1;
                                ApplicationsState.this.getEntryLocked(applicationInfo);
                            }
                            if (userId != 0 && ApplicationsState.this.mEntriesMap.indexOfKey(0) >= 0 && (appEntry = ApplicationsState.this.mEntriesMap.get(0).get(applicationInfo.packageName)) != null && !ApplicationsState.hasFlag(appEntry.info.flags, 8388608)) {
                                ApplicationsState.this.mEntriesMap.get(0).remove(applicationInfo.packageName);
                                ApplicationsState.this.mAppEntries.remove(appEntry);
                            }
                            i6++;
                            i = i7;
                        }
                    }
                    if (i >= 6) {
                        sendEmptyMessage(2);
                        return;
                    }
                    if (!ApplicationsState.this.mMainHandler.hasMessages(8)) {
                        ApplicationsState.this.mMainHandler.sendEmptyMessage(8);
                    }
                    sendEmptyMessage(3);
                    return;
                case 3:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 1)) {
                        ArrayList<ResolveInfo> arrayList2 = new ArrayList();
                        ApplicationsState.this.mPm.getHomeActivities(arrayList2);
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            int size = ApplicationsState.this.mEntriesMap.size();
                            for (int i8 = 0; i8 < size; i8++) {
                                HashMap<String, AppEntry> valueAt = ApplicationsState.this.mEntriesMap.valueAt(i8);
                                for (ResolveInfo resolveInfo : arrayList2) {
                                    AppEntry appEntry2 = valueAt.get(resolveInfo.activityInfo.packageName);
                                    if (appEntry2 != null) {
                                        appEntry2.isHomeApp = true;
                                    }
                                }
                            }
                        }
                    }
                    sendEmptyMessage(4);
                    return;
                case 4:
                case 5:
                    if ((i4 == 4 && ApplicationsState.hasFlag(combinedSessionFlags, 8)) || (message.what == 5 && ApplicationsState.hasFlag(combinedSessionFlags, 16))) {
                        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                        intent.addCategory(message.what == 4 ? "android.intent.category.LAUNCHER" : "android.intent.category.LEANBACK_LAUNCHER");
                        for (int i9 = 0; i9 < ApplicationsState.this.mEntriesMap.size(); i9++) {
                            int keyAt = ApplicationsState.this.mEntriesMap.keyAt(i9);
                            List queryIntentActivitiesAsUser = ApplicationsState.this.mPm.queryIntentActivitiesAsUser(intent, 786944, keyAt);
                            synchronized (ApplicationsState.this.mEntriesMap) {
                                HashMap<String, AppEntry> valueAt2 = ApplicationsState.this.mEntriesMap.valueAt(i9);
                                int size2 = queryIntentActivitiesAsUser.size();
                                for (int i10 = 0; i10 < size2; i10++) {
                                    ResolveInfo resolveInfo2 = (ResolveInfo) queryIntentActivitiesAsUser.get(i10);
                                    String str = resolveInfo2.activityInfo.packageName;
                                    AppEntry appEntry3 = valueAt2.get(str);
                                    if (appEntry3 != null) {
                                        appEntry3.hasLauncherEntry = true;
                                        appEntry3.launcherEntryEnabled |= resolveInfo2.activityInfo.enabled;
                                    } else {
                                        Log.w("ApplicationsState", "Cannot find pkg: " + str + " on user " + keyAt);
                                    }
                                }
                            }
                        }
                        if (!ApplicationsState.this.mMainHandler.hasMessages(7)) {
                            ApplicationsState.this.mMainHandler.sendEmptyMessage(7);
                        }
                    }
                    if (message.what == 4) {
                        sendEmptyMessage(5);
                        return;
                    } else {
                        sendEmptyMessage(6);
                        return;
                    }
                case 6:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 2)) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            int i11 = 0;
                            while (true) {
                                i2 = i11;
                                if (i5 < ApplicationsState.this.mAppEntries.size() && i2 < 2) {
                                    AppEntry appEntry4 = ApplicationsState.this.mAppEntries.get(i5);
                                    if (appEntry4.icon != null) {
                                        i3 = i2;
                                        break;
                                    }
                                    synchronized (appEntry4) {
                                        i3 = i2;
                                        if (appEntry4.ensureIconLocked(ApplicationsState.this.mContext)) {
                                            if (!this.mRunning) {
                                                this.mRunning = true;
                                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                                            }
                                            i3 = i2 + 1;
                                        }
                                    }
                                    i5++;
                                    i11 = i3;
                                }
                            }
                        }
                        if (i2 > 0 && !ApplicationsState.this.mMainHandler.hasMessages(3)) {
                            ApplicationsState.this.mMainHandler.sendEmptyMessage(3);
                        }
                        if (i2 >= 2) {
                            sendEmptyMessage(6);
                            return;
                        }
                    }
                    sendEmptyMessage(7);
                    return;
                case 7:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 4)) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            if (ApplicationsState.this.mCurComputingSizePkg != null) {
                                return;
                            }
                            long uptimeMillis = SystemClock.uptimeMillis();
                            for (int i12 = 0; i12 < ApplicationsState.this.mAppEntries.size(); i12++) {
                                AppEntry appEntry5 = ApplicationsState.this.mAppEntries.get(i12);
                                if (ApplicationsState.hasFlag(appEntry5.info.flags, 8388608) && (appEntry5.size == -1 || appEntry5.sizeStale)) {
                                    long j = appEntry5.sizeLoadStart;
                                    if (j == 0 || j < uptimeMillis - 20000) {
                                        if (!this.mRunning) {
                                            this.mRunning = true;
                                            ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                                        }
                                        appEntry5.sizeLoadStart = uptimeMillis;
                                        ApplicationsState applicationsState = ApplicationsState.this;
                                        ApplicationInfo applicationInfo2 = appEntry5.info;
                                        applicationsState.mCurComputingSizeUuid = applicationInfo2.storageUuid;
                                        applicationsState.mCurComputingSizePkg = applicationInfo2.packageName;
                                        applicationsState.mCurComputingSizeUserId = UserHandle.getUserId(applicationInfo2.uid);
                                        ApplicationsState.this.mBackgroundHandler.post(new Runnable() { // from class: com.android.settingslib.applications.ApplicationsState$BackgroundHandler$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ApplicationsState.BackgroundHandler.$r8$lambda$2qSpC_I8r0dptZxhgyLlm3leVfo(ApplicationsState.BackgroundHandler.this);
                                            }
                                        });
                                    }
                                    return;
                                }
                            }
                            if (!ApplicationsState.this.mMainHandler.hasMessages(5)) {
                                ApplicationsState.this.mMainHandler.sendEmptyMessage(5);
                                this.mRunning = false;
                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 0));
                            }
                            return;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/applications/ApplicationsState$MainHandler.class */
    public class MainHandler extends Handler {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MainHandler(Looper looper) {
            super(looper);
            ApplicationsState.this = r4;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ApplicationsState.this.rebuildActiveSessions();
            switch (message.what) {
                case 1:
                    Session session = (Session) message.obj;
                    Iterator<WeakReference<Session>> it = ApplicationsState.this.mActiveSessions.iterator();
                    while (it.hasNext()) {
                        Session session2 = it.next().get();
                        if (session2 != null && session2 == session) {
                            session.getClass();
                            throw null;
                        }
                    }
                    return;
                case 2:
                    Iterator<WeakReference<Session>> it2 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it2.hasNext()) {
                        if (it2.next().get() != null) {
                            throw null;
                        }
                    }
                    return;
                case 3:
                    Iterator<WeakReference<Session>> it3 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it3.hasNext()) {
                        if (it3.next().get() != null) {
                            throw null;
                        }
                    }
                    return;
                case 4:
                    Iterator<WeakReference<Session>> it4 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it4.hasNext()) {
                        if (it4.next().get() != null) {
                            String str = (String) message.obj;
                            throw null;
                        }
                    }
                    return;
                case 5:
                    Iterator<WeakReference<Session>> it5 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it5.hasNext()) {
                        if (it5.next().get() != null) {
                            throw null;
                        }
                    }
                    return;
                case 6:
                    Iterator<WeakReference<Session>> it6 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it6.hasNext()) {
                        if (it6.next().get() != null) {
                            throw null;
                        }
                    }
                    return;
                case 7:
                    Iterator<WeakReference<Session>> it7 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it7.hasNext()) {
                        if (it7.next().get() != null) {
                            throw null;
                        }
                    }
                    return;
                case 8:
                    Iterator<WeakReference<Session>> it8 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it8.hasNext()) {
                        if (it8.next().get() != null) {
                            throw null;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/applications/ApplicationsState$PackageIntentReceiver.class */
    public class PackageIntentReceiver extends BroadcastReceiver {
        public PackageIntentReceiver() {
            ApplicationsState.this = r4;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                for (int i = 0; i < ApplicationsState.this.mEntriesMap.size(); i++) {
                    ApplicationsState applicationsState = ApplicationsState.this;
                    applicationsState.addPackage(encodedSchemeSpecificPart, applicationsState.mEntriesMap.keyAt(i));
                }
            } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                String encodedSchemeSpecificPart2 = intent.getData().getEncodedSchemeSpecificPart();
                for (int i2 = 0; i2 < ApplicationsState.this.mEntriesMap.size(); i2++) {
                    ApplicationsState applicationsState2 = ApplicationsState.this;
                    applicationsState2.removePackage(encodedSchemeSpecificPart2, applicationsState2.mEntriesMap.keyAt(i2));
                }
            } else if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                String encodedSchemeSpecificPart3 = intent.getData().getEncodedSchemeSpecificPart();
                for (int i3 = 0; i3 < ApplicationsState.this.mEntriesMap.size(); i3++) {
                    ApplicationsState applicationsState3 = ApplicationsState.this;
                    applicationsState3.invalidatePackage(encodedSchemeSpecificPart3, applicationsState3.mEntriesMap.keyAt(i3));
                }
            } else if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action) || "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action)) {
                String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                if (stringArrayExtra == null || stringArrayExtra.length == 0 || !"android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action)) {
                    return;
                }
                for (String str : stringArrayExtra) {
                    for (int i4 = 0; i4 < ApplicationsState.this.mEntriesMap.size(); i4++) {
                        ApplicationsState applicationsState4 = ApplicationsState.this;
                        applicationsState4.invalidatePackage(str, applicationsState4.mEntriesMap.keyAt(i4));
                    }
                }
            } else if ("android.intent.action.USER_ADDED".equals(action)) {
                ApplicationsState.this.addUser(intent.getIntExtra("android.intent.extra.user_handle", -10000));
            } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                ApplicationsState.this.removeUser(intent.getIntExtra("android.intent.extra.user_handle", -10000));
            }
        }

        public void registerReceiver() {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme("package");
            ApplicationsState.this.mContext.registerReceiver(this, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
            ApplicationsState.this.mContext.registerReceiver(this, intentFilter2);
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("android.intent.action.USER_ADDED");
            intentFilter3.addAction("android.intent.action.USER_REMOVED");
            ApplicationsState.this.mContext.registerReceiver(this, intentFilter3);
        }

        public void unregisterReceiver() {
            ApplicationsState.this.mContext.unregisterReceiver(this);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/applications/ApplicationsState$Session.class */
    public class Session implements LifecycleObserver {
        public int mFlags;
        public final boolean mHasLifecycle;
        public ArrayList<AppEntry> mLastAppList;
        public boolean mRebuildAsync;
        public Comparator<AppEntry> mRebuildComparator;
        public AppFilter mRebuildFilter;
        public boolean mRebuildForeground;
        public boolean mRebuildRequested;
        public ArrayList<AppEntry> mRebuildResult;
        public final Object mRebuildSync;
        public boolean mResumed;
        public final /* synthetic */ ApplicationsState this$0;

        public void handleRebuildList() {
            ArrayList<AppEntry> arrayList;
            if (this.mResumed) {
                synchronized (this.mRebuildSync) {
                    if (this.mRebuildRequested) {
                        AppFilter appFilter = this.mRebuildFilter;
                        Comparator<AppEntry> comparator = this.mRebuildComparator;
                        this.mRebuildRequested = false;
                        this.mRebuildFilter = null;
                        this.mRebuildComparator = null;
                        if (this.mRebuildForeground) {
                            Process.setThreadPriority(-2);
                            this.mRebuildForeground = false;
                        }
                        if (appFilter != null) {
                            appFilter.init(this.this$0.mContext);
                        }
                        synchronized (this.this$0.mEntriesMap) {
                            arrayList = new ArrayList(this.this$0.mAppEntries);
                        }
                        ArrayList<AppEntry> arrayList2 = new ArrayList<>();
                        for (AppEntry appEntry : arrayList) {
                            if (appEntry != null && (appFilter == null || appFilter.filterApp(appEntry))) {
                                synchronized (this.this$0.mEntriesMap) {
                                    if (comparator != null) {
                                        appEntry.ensureLabel(this.this$0.mContext);
                                    }
                                    arrayList2.add(appEntry);
                                }
                            }
                        }
                        if (comparator != null) {
                            synchronized (this.this$0.mEntriesMap) {
                                Collections.sort(arrayList2, comparator);
                            }
                        }
                        synchronized (this.mRebuildSync) {
                            if (!this.mRebuildRequested) {
                                this.mLastAppList = arrayList2;
                                if (!this.mRebuildAsync) {
                                    this.mRebuildResult = arrayList2;
                                    this.mRebuildSync.notifyAll();
                                } else if (!this.this$0.mMainHandler.hasMessages(1, this)) {
                                    this.this$0.mMainHandler.sendMessage(this.this$0.mMainHandler.obtainMessage(1, this));
                                }
                            }
                        }
                        Process.setThreadPriority(10);
                    }
                }
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            if (!this.mHasLifecycle) {
                onPause();
            }
            synchronized (this.this$0.mEntriesMap) {
                this.this$0.mSessions.remove(this);
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            synchronized (this.this$0.mEntriesMap) {
                if (this.mResumed) {
                    this.mResumed = false;
                    ApplicationsState applicationsState = this.this$0;
                    applicationsState.mSessionsChanged = true;
                    applicationsState.mBackgroundHandler.removeMessages(1, this);
                    this.this$0.doPauseIfNeededLocked();
                }
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            synchronized (this.this$0.mEntriesMap) {
                if (!this.mResumed) {
                    this.mResumed = true;
                    ApplicationsState applicationsState = this.this$0;
                    applicationsState.mSessionsChanged = true;
                    applicationsState.doPauseLocked();
                    this.this$0.doResumeIfNeededLocked();
                }
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/applications/ApplicationsState$SizeInfo.class */
    public static class SizeInfo {
        public long cacheSize;
        public long codeSize;
        public long dataSize;
        public long externalCacheSize;
        public long externalCodeSize;
        public long externalDataSize;
    }

    public ApplicationsState(Application application, IPackageManager iPackageManager) {
        this.mContext = application;
        this.mPm = application.getPackageManager();
        this.mDrawableFactory = IconDrawableFactory.newInstance(application);
        this.mIpm = iPackageManager;
        UserManager userManager = (UserManager) application.getSystemService(UserManager.class);
        this.mUm = userManager;
        this.mStats = (StorageStatsManager) application.getSystemService(StorageStatsManager.class);
        for (int i : userManager.getProfileIdsWithDisabled(UserHandle.myUserId())) {
            this.mEntriesMap.put(i, new HashMap<>());
        }
        HandlerThread handlerThread = new HandlerThread("ApplicationsState.Loader");
        this.mThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new BackgroundHandler(handlerThread.getLooper());
        this.mAdminRetrieveFlags = 4227584;
        this.mRetrieveFlags = 33280;
        for (ModuleInfo moduleInfo : this.mPm.getInstalledModules(0)) {
            this.mSystemModules.put(moduleInfo.getPackageName(), Boolean.valueOf(moduleInfo.isHidden()));
        }
        synchronized (this.mEntriesMap) {
            try {
                this.mEntriesMap.wait(1L);
            } catch (InterruptedException e) {
            }
        }
    }

    public static boolean anyAppIsRemoved(List<ApplicationInfo> list, List<ApplicationInfo> list2) {
        HashSet hashSet;
        if (list.size() == 0) {
            return false;
        }
        if (list2.size() < list.size()) {
            return true;
        }
        HashMap hashMap = new HashMap();
        for (ApplicationInfo applicationInfo : list2) {
            String valueOf = String.valueOf(UserHandle.getUserId(applicationInfo.uid));
            HashSet hashSet2 = (HashSet) hashMap.get(valueOf);
            HashSet hashSet3 = hashSet2;
            if (hashSet2 == null) {
                hashSet3 = new HashSet();
                hashMap.put(valueOf, hashSet3);
            }
            if (hasFlag(applicationInfo.flags, 8388608)) {
                hashSet3.add(applicationInfo.packageName);
            }
        }
        for (ApplicationInfo applicationInfo2 : list) {
            if (hasFlag(applicationInfo2.flags, 8388608) && ((hashSet = (HashSet) hashMap.get(String.valueOf(UserHandle.getUserId(applicationInfo2.uid)))) == null || !hashSet.remove(applicationInfo2.packageName))) {
                return true;
            }
        }
        return false;
    }

    public static ApplicationsState getInstance(Application application, IPackageManager iPackageManager) {
        ApplicationsState applicationsState;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new ApplicationsState(application, iPackageManager);
            }
            applicationsState = sInstance;
        }
        return applicationsState;
    }

    public static boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public static boolean isAppIconCacheEnabled(Context context) {
        return "com.android.settings".equals(context.getPackageName());
    }

    public void addPackage(String str, int i) {
        try {
            synchronized (this.mEntriesMap) {
                if (this.mResumed) {
                    if (indexOfApplicationInfoLocked(str, i) >= 0) {
                        return;
                    }
                    ApplicationInfo applicationInfo = this.mIpm.getApplicationInfo(str, this.mUm.isUserAdmin(i) ? this.mAdminRetrieveFlags : this.mRetrieveFlags, i);
                    if (applicationInfo == null) {
                        return;
                    }
                    if (!applicationInfo.enabled) {
                        if (applicationInfo.enabledSetting != 3) {
                            return;
                        }
                        this.mHaveDisabledApps = true;
                    }
                    if (AppUtils.isInstant(applicationInfo)) {
                        this.mHaveInstantApps = true;
                    }
                    this.mApplications.add(applicationInfo);
                    if (!this.mBackgroundHandler.hasMessages(2)) {
                        this.mBackgroundHandler.sendEmptyMessage(2);
                    }
                    if (!this.mMainHandler.hasMessages(2)) {
                        this.mMainHandler.sendEmptyMessage(2);
                    }
                }
            }
        } catch (RemoteException e) {
        }
    }

    public final void addUser(int i) {
        if (ArrayUtils.contains(this.mUm.getProfileIdsWithDisabled(UserHandle.myUserId()), i)) {
            synchronized (this.mEntriesMap) {
                this.mEntriesMap.put(i, new HashMap<>());
                if (this.mResumed) {
                    doPauseLocked();
                    doResumeIfNeededLocked();
                }
                if (!this.mMainHandler.hasMessages(2)) {
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    public void clearEntries() {
        for (int i = 0; i < this.mEntriesMap.size(); i++) {
            this.mEntriesMap.valueAt(i).clear();
        }
        this.mAppEntries.clear();
    }

    public void doPauseIfNeededLocked() {
        if (this.mResumed) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                if (this.mSessions.get(i).mResumed) {
                    return;
                }
            }
            doPauseLocked();
        }
    }

    public void doPauseLocked() {
        this.mResumed = false;
        PackageIntentReceiver packageIntentReceiver = this.mPackageIntentReceiver;
        if (packageIntentReceiver != null) {
            packageIntentReceiver.unregisterReceiver();
            this.mPackageIntentReceiver = null;
        }
    }

    public void doResumeIfNeededLocked() {
        int i;
        if (this.mResumed) {
            return;
        }
        this.mResumed = true;
        if (this.mPackageIntentReceiver == null) {
            PackageIntentReceiver packageIntentReceiver = new PackageIntentReceiver();
            this.mPackageIntentReceiver = packageIntentReceiver;
            packageIntentReceiver.registerReceiver();
        }
        List<ApplicationInfo> list = this.mApplications;
        this.mApplications = new ArrayList();
        for (UserInfo userInfo : this.mUm.getProfiles(UserHandle.myUserId())) {
            try {
                if (this.mEntriesMap.indexOfKey(userInfo.id) < 0) {
                    this.mEntriesMap.put(userInfo.id, new HashMap<>());
                }
                this.mApplications.addAll(this.mIpm.getInstalledApplications(userInfo.isAdmin() ? this.mAdminRetrieveFlags : this.mRetrieveFlags, userInfo.id).getList());
            } catch (Exception e) {
                Log.e("ApplicationsState", "Error during doResumeIfNeededLocked", e);
            }
        }
        if (this.mInterestingConfigChanges.applyNewConfig(this.mContext.getResources())) {
            clearEntries();
        } else {
            for (int i2 = 0; i2 < this.mAppEntries.size(); i2++) {
                this.mAppEntries.get(i2).sizeStale = true;
            }
        }
        this.mHaveDisabledApps = false;
        this.mHaveInstantApps = false;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 >= this.mApplications.size()) {
                break;
            }
            ApplicationInfo applicationInfo = this.mApplications.get(i4);
            if (!applicationInfo.enabled) {
                if (applicationInfo.enabledSetting != 3) {
                    this.mApplications.remove(i4);
                    i = i4 - 1;
                    i3 = i + 1;
                } else {
                    this.mHaveDisabledApps = true;
                }
            }
            if (isHiddenModule(applicationInfo.packageName)) {
                this.mApplications.remove(i4);
                i = i4 - 1;
            } else {
                if (!this.mHaveInstantApps && AppUtils.isInstant(applicationInfo)) {
                    this.mHaveInstantApps = true;
                }
                AppEntry appEntry = this.mEntriesMap.get(UserHandle.getUserId(applicationInfo.uid)).get(applicationInfo.packageName);
                i = i4;
                if (appEntry != null) {
                    appEntry.info = applicationInfo;
                    i = i4;
                }
            }
            i3 = i + 1;
        }
        if (anyAppIsRemoved(list, this.mApplications)) {
            clearEntries();
        }
        this.mCurComputingSizePkg = null;
        if (this.mBackgroundHandler.hasMessages(2)) {
            return;
        }
        this.mBackgroundHandler.sendEmptyMessage(2);
    }

    public final AppEntry getEntryLocked(ApplicationInfo applicationInfo) {
        AppEntry appEntry;
        int userId = UserHandle.getUserId(applicationInfo.uid);
        AppEntry appEntry2 = this.mEntriesMap.get(userId).get(applicationInfo.packageName);
        if (appEntry2 != null) {
            appEntry = appEntry2;
            if (appEntry2.info != applicationInfo) {
                appEntry2.info = applicationInfo;
                appEntry = appEntry2;
            }
        } else if (isHiddenModule(applicationInfo.packageName)) {
            return null;
        } else {
            Context context = this.mContext;
            long j = this.mCurId;
            this.mCurId = 1 + j;
            appEntry = new AppEntry(context, applicationInfo, j);
            this.mEntriesMap.get(userId).put(applicationInfo.packageName, appEntry);
            this.mAppEntries.add(appEntry);
        }
        return appEntry;
    }

    public final String getSizeStr(long j) {
        if (j >= 0) {
            return Formatter.formatFileSize(this.mContext, j);
        }
        return null;
    }

    public final long getTotalExternalSize(PackageStats packageStats) {
        if (packageStats != null) {
            return packageStats.externalCodeSize + packageStats.externalDataSize + packageStats.externalCacheSize + packageStats.externalMediaSize + packageStats.externalObbSize;
        }
        return -2L;
    }

    public final long getTotalInternalSize(PackageStats packageStats) {
        if (packageStats != null) {
            return (packageStats.codeSize + packageStats.dataSize) - packageStats.cacheSize;
        }
        return -2L;
    }

    public int indexOfApplicationInfoLocked(String str, int i) {
        for (int size = this.mApplications.size() - 1; size >= 0; size--) {
            ApplicationInfo applicationInfo = this.mApplications.get(size);
            if (applicationInfo.packageName.equals(str) && UserHandle.getUserId(applicationInfo.uid) == i) {
                return size;
            }
        }
        return -1;
    }

    public void invalidatePackage(String str, int i) {
        removePackage(str, i);
        addPackage(str, i);
    }

    public boolean isHiddenModule(String str) {
        Boolean bool = this.mSystemModules.get(str);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public void rebuildActiveSessions() {
        synchronized (this.mEntriesMap) {
            if (this.mSessionsChanged) {
                this.mActiveSessions.clear();
                for (int i = 0; i < this.mSessions.size(); i++) {
                    Session session = this.mSessions.get(i);
                    if (session.mResumed) {
                        this.mActiveSessions.add(new WeakReference<>(session));
                    }
                }
            }
        }
    }

    public void removePackage(String str, int i) {
        synchronized (this.mEntriesMap) {
            int indexOfApplicationInfoLocked = indexOfApplicationInfoLocked(str, i);
            if (indexOfApplicationInfoLocked >= 0) {
                AppEntry appEntry = this.mEntriesMap.get(i).get(str);
                if (appEntry != null) {
                    this.mEntriesMap.get(i).remove(str);
                    this.mAppEntries.remove(appEntry);
                }
                ApplicationInfo applicationInfo = this.mApplications.get(indexOfApplicationInfoLocked);
                this.mApplications.remove(indexOfApplicationInfoLocked);
                if (!applicationInfo.enabled) {
                    this.mHaveDisabledApps = false;
                    Iterator<ApplicationInfo> it = this.mApplications.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        } else if (!it.next().enabled) {
                            this.mHaveDisabledApps = true;
                            break;
                        }
                    }
                }
                if (AppUtils.isInstant(applicationInfo)) {
                    this.mHaveInstantApps = false;
                    Iterator<ApplicationInfo> it2 = this.mApplications.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        } else if (AppUtils.isInstant(it2.next())) {
                            this.mHaveInstantApps = true;
                            break;
                        }
                    }
                }
                if (!this.mMainHandler.hasMessages(2)) {
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    public final void removeUser(int i) {
        synchronized (this.mEntriesMap) {
            HashMap<String, AppEntry> hashMap = this.mEntriesMap.get(i);
            if (hashMap != null) {
                for (AppEntry appEntry : hashMap.values()) {
                    this.mAppEntries.remove(appEntry);
                    this.mApplications.remove(appEntry.info);
                }
                this.mEntriesMap.remove(i);
                if (!this.mMainHandler.hasMessages(2)) {
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    public void setInterestingConfigChanges(InterestingConfigChanges interestingConfigChanges) {
        this.mInterestingConfigChanges = interestingConfigChanges;
    }
}