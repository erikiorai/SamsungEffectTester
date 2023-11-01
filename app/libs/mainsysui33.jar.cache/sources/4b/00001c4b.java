package com.android.systemui.media.controls.resume;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaTimeoutListenerKt;
import com.android.systemui.media.controls.resume.ResumeMediaBrowser;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/MediaResumeListener.class */
public final class MediaResumeListener implements MediaDataManager.Listener, Dumpable {
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public int currentUserId;
    public final Executor mainExecutor;
    public ResumeMediaBrowser mediaBrowser;
    public final MediaResumeListener$mediaBrowserCallback$1 mediaBrowserCallback;
    public final ResumeMediaBrowserFactory mediaBrowserFactory;
    public MediaDataManager mediaDataManager;
    public final ConcurrentLinkedQueue<Pair<ComponentName, Long>> resumeComponents = new ConcurrentLinkedQueue<>();
    public final SystemClock systemClock;
    public final TunerService tunerService;
    public boolean useMediaResumption;
    public final UserTracker userTracker;
    public final MediaResumeListener$userTrackerCallback$1 userTrackerCallback;
    public final BroadcastReceiver userUnlockReceiver;

    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.systemui.media.controls.resume.MediaResumeListener$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.media.controls.resume.MediaResumeListener$mediaBrowserCallback$1] */
    public MediaResumeListener(Context context, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, Executor executor, Executor executor2, TunerService tunerService, ResumeMediaBrowserFactory resumeMediaBrowserFactory, DumpManager dumpManager, SystemClock systemClock) {
        this.context = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.userTracker = userTracker;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.tunerService = tunerService;
        this.mediaBrowserFactory = resumeMediaBrowserFactory;
        this.systemClock = systemClock;
        this.useMediaResumption = Utils.useMediaResumption(context);
        this.currentUserId = context.getUserId();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$userUnlockReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (Intrinsics.areEqual("android.intent.action.USER_UNLOCKED", intent.getAction()) && intent.getIntExtra("android.intent.extra.user_handle", -1) == MediaResumeListener.access$getCurrentUserId$p(MediaResumeListener.this)) {
                    MediaResumeListener.access$loadMediaResumptionControls(MediaResumeListener.this);
                }
            }
        };
        this.userUnlockReceiver = broadcastReceiver;
        ?? r0 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$userTrackerCallback$1
            public void onUserChanged(int i, Context context2) {
                MediaResumeListener.access$setCurrentUserId$p(MediaResumeListener.this, i);
                MediaResumeListener.access$loadSavedComponents(MediaResumeListener.this);
            }
        };
        this.userTrackerCallback = r0;
        this.mediaBrowserCallback = new ResumeMediaBrowser.Callback() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$mediaBrowserCallback$1
            @Override // com.android.systemui.media.controls.resume.ResumeMediaBrowser.Callback
            public void addTrack(MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
                Context context2;
                Runnable resumeAction;
                MediaDataManager mediaDataManager;
                int i;
                MediaSession.Token token = resumeMediaBrowser.getToken();
                PendingIntent appIntent = resumeMediaBrowser.getAppIntent();
                context2 = MediaResumeListener.this.context;
                PackageManager packageManager = context2.getPackageManager();
                String packageName = componentName.getPackageName();
                resumeAction = MediaResumeListener.this.getResumeAction(componentName);
                try {
                    packageName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(componentName.getPackageName(), 0));
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("MediaResumeListener", "Error getting package information", e);
                }
                Log.d("MediaResumeListener", "Adding resume controls " + mediaDescription);
                mediaDataManager = MediaResumeListener.this.mediaDataManager;
                MediaDataManager mediaDataManager2 = mediaDataManager;
                if (mediaDataManager == null) {
                    mediaDataManager2 = null;
                }
                i = MediaResumeListener.this.currentUserId;
                mediaDataManager2.addResumptionControls(i, mediaDescription, resumeAction, token, packageName.toString(), appIntent, componentName.getPackageName());
            }
        };
        if (this.useMediaResumption) {
            DumpManager.registerDumpable$default(dumpManager, "MediaResumeListener", this, null, 4, null);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_UNLOCKED");
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, intentFilter, null, UserHandle.ALL, 0, null, 48, null);
            userTracker.addCallback((UserTracker.Callback) r0, executor);
            loadSavedComponents();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.resume.MediaResumeListener$userUnlockReceiver$1.onReceive(android.content.Context, android.content.Intent):void] */
    public static final /* synthetic */ int access$getCurrentUserId$p(MediaResumeListener mediaResumeListener) {
        return mediaResumeListener.currentUserId;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.resume.MediaResumeListener$getResumeAction$1.run():void] */
    public static final /* synthetic */ ResumeMediaBrowser access$getMediaBrowser$p(MediaResumeListener mediaResumeListener) {
        return mediaResumeListener.mediaBrowser;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.resume.MediaResumeListener$getResumeAction$1.run():void] */
    public static final /* synthetic */ ResumeMediaBrowserFactory access$getMediaBrowserFactory$p(MediaResumeListener mediaResumeListener) {
        return mediaResumeListener.mediaBrowserFactory;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.resume.MediaResumeListener$userUnlockReceiver$1.onReceive(android.content.Context, android.content.Intent):void] */
    public static final /* synthetic */ void access$loadMediaResumptionControls(MediaResumeListener mediaResumeListener) {
        mediaResumeListener.loadMediaResumptionControls();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.resume.MediaResumeListener$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ void access$loadSavedComponents(MediaResumeListener mediaResumeListener) {
        mediaResumeListener.loadSavedComponents();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.resume.MediaResumeListener$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ void access$setCurrentUserId$p(MediaResumeListener mediaResumeListener, int i) {
        mediaResumeListener.currentUserId = i;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.resume.MediaResumeListener$getResumeAction$1.run():void] */
    public static final /* synthetic */ void access$setMediaBrowser(MediaResumeListener mediaResumeListener, ResumeMediaBrowser resumeMediaBrowser) {
        mediaResumeListener.setMediaBrowser(resumeMediaBrowser);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.resume.MediaResumeListener$onMediaDataLoaded$1.run():void] */
    public static final /* synthetic */ void access$tryUpdateResumptionList(MediaResumeListener mediaResumeListener, String str, ComponentName componentName) {
        mediaResumeListener.tryUpdateResumptionList(str, componentName);
    }

    @VisibleForTesting
    public static /* synthetic */ void getUserUnlockReceiver$annotations() {
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        ConcurrentLinkedQueue<Pair<ComponentName, Long>> concurrentLinkedQueue = this.resumeComponents;
        printWriter.println("resumeComponents: " + concurrentLinkedQueue);
    }

    public final Runnable getResumeAction(final ComponentName componentName) {
        return new Runnable() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$getResumeAction$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                MediaResumeListener.access$setMediaBrowser(mediaResumeListener, MediaResumeListener.access$getMediaBrowserFactory$p(mediaResumeListener).create(null, componentName));
                ResumeMediaBrowser access$getMediaBrowser$p = MediaResumeListener.access$getMediaBrowser$p(MediaResumeListener.this);
                if (access$getMediaBrowser$p != null) {
                    access$getMediaBrowser$p.restart();
                }
            }
        };
    }

    public final void loadMediaResumptionControls() {
        if (this.useMediaResumption) {
            long currentTimeMillis = this.systemClock.currentTimeMillis();
            Iterator<T> it = this.resumeComponents.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                if (currentTimeMillis - ((Number) pair.getSecond()).longValue() <= MediaTimeoutListenerKt.getRESUME_MEDIA_TIMEOUT()) {
                    this.mediaBrowserFactory.create(this.mediaBrowserCallback, (ComponentName) pair.getFirst()).findRecentMedia();
                }
            }
        }
    }

    public final void loadSavedComponents() {
        long currentTimeMillis;
        this.resumeComponents.clear();
        String string = this.context.getSharedPreferences("media_control_prefs", 0).getString("browser_components_" + this.currentUserId, null);
        List<String> list = null;
        if (string != null) {
            List split = new Regex(":").split(string, 0);
            list = null;
            if (split != null) {
                if (!split.isEmpty()) {
                    ListIterator listIterator = split.listIterator(split.size());
                    while (listIterator.hasPrevious()) {
                        if (!(((String) listIterator.previous()).length() == 0)) {
                            list = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                            break;
                        }
                    }
                }
                list = CollectionsKt__CollectionsKt.emptyList();
            }
        }
        boolean z = false;
        if (list != null) {
            z = false;
            for (String str : list) {
                List split$default = StringsKt__StringsKt.split$default(str, new String[]{"/"}, false, 0, 6, (Object) null);
                ComponentName componentName = new ComponentName((String) split$default.get(0), (String) split$default.get(1));
                if (split$default.size() == 3) {
                    try {
                        currentTimeMillis = Long.parseLong((String) split$default.get(2));
                    } catch (NumberFormatException e) {
                        currentTimeMillis = this.systemClock.currentTimeMillis();
                    }
                    this.resumeComponents.add(TuplesKt.to(componentName, Long.valueOf(currentTimeMillis)));
                } else {
                    currentTimeMillis = this.systemClock.currentTimeMillis();
                }
                z = true;
                this.resumeComponents.add(TuplesKt.to(componentName, Long.valueOf(currentTimeMillis)));
            }
        }
        Log.d("MediaResumeListener", "loaded resume components " + Arrays.toString(this.resumeComponents.toArray()));
        if (z) {
            writeSharedPrefs();
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataLoaded(final String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        ArrayList arrayList;
        if (this.useMediaResumption) {
            if (!str.equals(str2)) {
                setMediaBrowser(null);
            }
            if (mediaData.getResumeAction() == null && !mediaData.getHasCheckedForResume() && mediaData.isLocalSession()) {
                Log.d("MediaResumeListener", "Checking for service component for " + mediaData.getPackageName());
                List<ResolveInfo> queryIntentServices = this.context.getPackageManager().queryIntentServices(new Intent("android.media.browse.MediaBrowserService"), 0);
                if (queryIntentServices != null) {
                    List<ResolveInfo> list = queryIntentServices;
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<T> it = list.iterator();
                    while (true) {
                        arrayList = arrayList2;
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        if (Intrinsics.areEqual(((ResolveInfo) next).serviceInfo.packageName, mediaData.getPackageName())) {
                            arrayList2.add(next);
                        }
                    }
                } else {
                    arrayList = null;
                }
                if (arrayList != null && arrayList.size() > 0) {
                    final ArrayList arrayList3 = arrayList;
                    this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$onMediaDataLoaded$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                            String str3 = str;
                            List<ResolveInfo> list2 = arrayList3;
                            Intrinsics.checkNotNull(list2);
                            MediaResumeListener.access$tryUpdateResumptionList(mediaResumeListener, str3, list2.get(0).getComponentInfo().getComponentName());
                        }
                    });
                    return;
                }
                MediaDataManager mediaDataManager = this.mediaDataManager;
                MediaDataManager mediaDataManager2 = mediaDataManager;
                if (mediaDataManager == null) {
                    mediaDataManager2 = null;
                }
                mediaDataManager2.setResumeAction(str, null);
            }
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        MediaDataManager.Listener.DefaultImpls.onMediaDataRemoved(this, str);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded(this, str, smartspaceMediaData, z);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataRemoved(this, str, z);
    }

    public final void setManager(MediaDataManager mediaDataManager) {
        this.mediaDataManager = mediaDataManager;
        this.tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$setManager$1
            public void onTuningChanged(String str, String str2) {
                Context context;
                MediaDataManager mediaDataManager2;
                boolean z;
                MediaResumeListener mediaResumeListener = MediaResumeListener.this;
                context = mediaResumeListener.context;
                mediaResumeListener.useMediaResumption = Utils.useMediaResumption(context);
                mediaDataManager2 = MediaResumeListener.this.mediaDataManager;
                MediaDataManager mediaDataManager3 = mediaDataManager2;
                if (mediaDataManager2 == null) {
                    mediaDataManager3 = null;
                }
                z = MediaResumeListener.this.useMediaResumption;
                mediaDataManager3.setMediaResumptionEnabled(z);
            }
        }, new String[]{"qs_media_resumption"});
    }

    public final void setMediaBrowser(ResumeMediaBrowser resumeMediaBrowser) {
        ResumeMediaBrowser resumeMediaBrowser2 = this.mediaBrowser;
        if (resumeMediaBrowser2 != null) {
            resumeMediaBrowser2.disconnect();
        }
        this.mediaBrowser = resumeMediaBrowser;
    }

    public final void tryUpdateResumptionList(final String str, final ComponentName componentName) {
        Log.d("MediaResumeListener", "Testing if we can connect to " + componentName);
        MediaDataManager mediaDataManager = this.mediaDataManager;
        MediaDataManager mediaDataManager2 = mediaDataManager;
        if (mediaDataManager == null) {
            mediaDataManager2 = null;
        }
        mediaDataManager2.setResumeAction(str, null);
        setMediaBrowser(this.mediaBrowserFactory.create(new ResumeMediaBrowser.Callback() { // from class: com.android.systemui.media.controls.resume.MediaResumeListener$tryUpdateResumptionList$1
            @Override // com.android.systemui.media.controls.resume.ResumeMediaBrowser.Callback
            public void addTrack(MediaDescription mediaDescription, ComponentName componentName2, ResumeMediaBrowser resumeMediaBrowser) {
                MediaDataManager mediaDataManager3;
                Runnable resumeAction;
                Log.d("MediaResumeListener", "Can get resumable media from " + componentName);
                mediaDataManager3 = this.mediaDataManager;
                MediaDataManager mediaDataManager4 = mediaDataManager3;
                if (mediaDataManager3 == null) {
                    mediaDataManager4 = null;
                }
                String str2 = str;
                resumeAction = this.getResumeAction(componentName);
                mediaDataManager4.setResumeAction(str2, resumeAction);
                this.updateResumptionList(componentName);
                this.setMediaBrowser(null);
            }

            @Override // com.android.systemui.media.controls.resume.ResumeMediaBrowser.Callback
            public void onConnected() {
                ComponentName componentName2 = componentName;
                Log.d("MediaResumeListener", "Connected to " + componentName2);
            }

            @Override // com.android.systemui.media.controls.resume.ResumeMediaBrowser.Callback
            public void onError() {
                ComponentName componentName2 = componentName;
                Log.e("MediaResumeListener", "Cannot resume with " + componentName2);
                this.setMediaBrowser(null);
            }
        }, componentName));
        ResumeMediaBrowser resumeMediaBrowser = this.mediaBrowser;
        if (resumeMediaBrowser != null) {
            resumeMediaBrowser.testConnection();
        }
    }

    public final void updateResumptionList(ComponentName componentName) {
        Object obj;
        ConcurrentLinkedQueue<Pair<ComponentName, Long>> concurrentLinkedQueue = this.resumeComponents;
        Iterator<T> it = concurrentLinkedQueue.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((ComponentName) ((Pair) obj).getFirst()).equals(componentName)) {
                break;
            }
        }
        concurrentLinkedQueue.remove(obj);
        this.resumeComponents.add(TuplesKt.to(componentName, Long.valueOf(this.systemClock.currentTimeMillis())));
        if (this.resumeComponents.size() > 5) {
            this.resumeComponents.remove();
        }
        writeSharedPrefs();
    }

    public final void writeSharedPrefs() {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = this.resumeComponents.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            sb.append(((ComponentName) pair.getFirst()).flattenToString());
            sb.append("/");
            sb.append(((Number) pair.getSecond()).longValue());
            sb.append(":");
        }
        SharedPreferences.Editor edit = this.context.getSharedPreferences("media_control_prefs", 0).edit();
        int i = this.currentUserId;
        edit.putString("browser_components_" + i, sb.toString()).apply();
    }
}