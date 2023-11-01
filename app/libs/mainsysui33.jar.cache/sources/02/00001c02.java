package com.android.systemui.media.controls.pipeline;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTarget;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.R$styleable;
import com.android.internal.logging.InstanceId;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.models.player.MediaAction;
import com.android.systemui.media.controls.models.player.MediaButton;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import com.android.systemui.media.controls.models.player.MediaViewHolder;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaDataProvider;
import com.android.systemui.media.controls.resume.MediaResumeListener;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.row.HybridGroupManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataManager.class */
public final class MediaDataManager implements Dumpable, BcSmartspaceDataPlugin.SmartspaceTargetListener {
    public final ActivityStarter activityStarter;
    public boolean allowMediaRecommendations;
    public final MediaDataManager$appChangeReceiver$1 appChangeReceiver;
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public final DelayableExecutor foregroundExecutor;
    public final Set<Listener> internalListeners;
    public final MediaUiEventLogger logger;
    public final MediaControllerFactory mediaControllerFactory;
    public final MediaDataFilter mediaDataFilter;
    public final LinkedHashMap<String, MediaData> mediaEntries;
    public final MediaFlags mediaFlags;
    public final SmartspaceManager smartspaceManager;
    public SmartspaceMediaData smartspaceMediaData;
    public final SmartspaceMediaDataProvider smartspaceMediaDataProvider;
    public SmartspaceSession smartspaceSession;
    public final SystemClock systemClock;
    public final int themeText;
    public final TunerService tunerService;
    public final Executor uiExecutor;
    public boolean useMediaResumption;
    public final boolean useQsMediaPlayer;
    public static final Companion Companion = new Companion(null);
    public static final String SMARTSPACE_UI_SURFACE_LABEL = BcSmartspaceDataPlugin.UI_SURFACE_MEDIA;
    public static final String EXTRAS_MEDIA_SOURCE_PACKAGE_NAME = "package_name";
    public static final int MAX_COMPACT_ACTIONS = 3;
    public static final int MAX_NOTIFICATION_ACTIONS = MediaViewHolder.Companion.getGenericButtonIds().size();

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataManager$Listener.class */
    public interface Listener {

        /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataManager$Listener$DefaultImpls.class */
        public static final class DefaultImpls {
            public static /* synthetic */ void onMediaDataLoaded$default(Listener listener, String str, String str2, MediaData mediaData, boolean z, int i, boolean z2, int i2, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onMediaDataLoaded");
                }
                if ((i2 & 8) != 0) {
                    z = true;
                }
                if ((i2 & 16) != 0) {
                    i = 0;
                }
                if ((i2 & 32) != 0) {
                    z2 = false;
                }
                listener.onMediaDataLoaded(str, str2, mediaData, z, i, z2);
            }

            public static void onMediaDataRemoved(Listener listener, String str) {
            }

            public static void onSmartspaceMediaDataLoaded(Listener listener, String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
            }

            public static /* synthetic */ void onSmartspaceMediaDataLoaded$default(Listener listener, String str, SmartspaceMediaData smartspaceMediaData, boolean z, int i, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onSmartspaceMediaDataLoaded");
                }
                if ((i & 4) != 0) {
                    z = false;
                }
                listener.onSmartspaceMediaDataLoaded(str, smartspaceMediaData, z);
            }

            public static void onSmartspaceMediaDataRemoved(Listener listener, String str, boolean z) {
            }
        }

        void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2);

        void onMediaDataRemoved(String str);

        void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z);

        void onSmartspaceMediaDataRemoved(String str, boolean z);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r11v0, resolved type: android.content.Context */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v22, types: [com.android.systemui.media.controls.pipeline.MediaDataManager$appChangeReceiver$1, android.content.BroadcastReceiver] */
    public MediaDataManager(Context context, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilter mediaDataFilter, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, boolean z, boolean z2, SystemClock systemClock, TunerService tunerService, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager) {
        boolean allowMediaRecommendations;
        this.context = context;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.foregroundExecutor = delayableExecutor;
        this.mediaControllerFactory = mediaControllerFactory;
        this.broadcastDispatcher = broadcastDispatcher;
        this.mediaDataFilter = mediaDataFilter;
        this.activityStarter = activityStarter;
        this.smartspaceMediaDataProvider = smartspaceMediaDataProvider;
        this.useMediaResumption = z;
        this.useQsMediaPlayer = z2;
        this.systemClock = systemClock;
        this.tunerService = tunerService;
        this.mediaFlags = mediaFlags;
        this.logger = mediaUiEventLogger;
        this.smartspaceManager = smartspaceManager;
        this.themeText = Utils.getColorAttr(context, 16842806).getDefaultColor();
        this.internalListeners = new LinkedHashSet();
        this.mediaEntries = new LinkedHashMap<>();
        this.smartspaceMediaData = MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA();
        allowMediaRecommendations = MediaDataManagerKt.allowMediaRecommendations(context);
        this.allowMediaRecommendations = allowMediaRecommendations;
        ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$appChangeReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String[] stringArrayExtra;
                String encodedSchemeSpecificPart;
                String action = intent.getAction();
                if (action != null) {
                    int hashCode = action.hashCode();
                    if (hashCode == -1001645458) {
                        if (action.equals("android.intent.action.PACKAGES_SUSPENDED") && (stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list")) != null) {
                            MediaDataManager mediaDataManager = MediaDataManager.this;
                            for (String str : stringArrayExtra) {
                                MediaDataManager.access$removeAllForPackage(mediaDataManager, str);
                            }
                            return;
                        }
                        return;
                    }
                    if (hashCode != -757780528) {
                        if (hashCode != 525384130 || !action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            return;
                        }
                    } else if (!action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                        return;
                    }
                    Uri data = intent.getData();
                    if (data == null || (encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart()) == null) {
                        return;
                    }
                    MediaDataManager.access$removeAllForPackage(MediaDataManager.this, encodedSchemeSpecificPart);
                }
            }
        };
        this.appChangeReceiver = r0;
        DumpManager.registerDumpable$default(dumpManager, "MediaDataManager", this, null, 4, null);
        addInternalListener(mediaTimeoutListener);
        addInternalListener(mediaResumeListener);
        addInternalListener(mediaSessionBasedFilter);
        mediaSessionBasedFilter.addListener(mediaDeviceManager);
        mediaSessionBasedFilter.addListener(mediaDataCombineLatest);
        mediaDeviceManager.addListener(mediaDataCombineLatest);
        mediaDataCombineLatest.addListener(mediaDataFilter);
        mediaTimeoutListener.setTimeoutCallback(new Function2<String, Boolean, Unit>() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
                MediaDataManager.this = this;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke((String) obj, ((Boolean) obj2).booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(String str, boolean z3) {
                MediaDataManager.setTimedOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(MediaDataManager.this, str, z3, false, 4, null);
            }
        });
        mediaTimeoutListener.setStateCallback(new Function2<String, PlaybackState, Unit>() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
                MediaDataManager.this = this;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke((String) obj, (PlaybackState) obj2);
                return Unit.INSTANCE;
            }

            public final void invoke(String str, PlaybackState playbackState) {
                MediaDataManager.this.updateState(str, playbackState);
            }
        });
        mediaResumeListener.setManager(this);
        mediaDataFilter.setMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core(this);
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r0, new IntentFilter("android.intent.action.PACKAGES_SUSPENDED"), null, UserHandle.ALL, 0, null, 48, null);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(r0, intentFilter);
        smartspaceMediaDataProvider.registerListener(this);
        SmartspaceSession createSmartspaceSession = smartspaceManager.createSmartspaceSession(new SmartspaceConfig.Builder(context, SMARTSPACE_UI_SURFACE_LABEL).build());
        this.smartspaceSession = createSmartspaceSession;
        if (createSmartspaceSession != null) {
            createSmartspaceSession.addOnTargetsAvailableListener(executor2, new SmartspaceSession.OnTargetsAvailableListener() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$3$1
                public final void onTargetsAvailable(List<SmartspaceTarget> list) {
                    MediaDataManager.access$getSmartspaceMediaDataProvider$p(MediaDataManager.this).onTargetsAvailable(list);
                }
            });
        }
        SmartspaceSession smartspaceSession = this.smartspaceSession;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
        tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager.5
            {
                MediaDataManager.this = this;
            }

            public void onTuningChanged(String str, String str2) {
                boolean allowMediaRecommendations2;
                MediaDataManager mediaDataManager = MediaDataManager.this;
                allowMediaRecommendations2 = MediaDataManagerKt.allowMediaRecommendations(mediaDataManager.context);
                mediaDataManager.allowMediaRecommendations = allowMediaRecommendations2;
                if (MediaDataManager.this.allowMediaRecommendations) {
                    return;
                }
                MediaDataManager mediaDataManager2 = MediaDataManager.this;
                mediaDataManager2.dismissSmartspaceRecommendation(mediaDataManager2.getSmartspaceMediaData().getTargetId(), 0L);
            }
        }, new String[]{"qs_media_recommend"});
    }

    public MediaDataManager(Context context, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilter mediaDataFilter, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, SystemClock systemClock, TunerService tunerService, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager) {
        this(context, executor, executor2, delayableExecutor, mediaControllerFactory, broadcastDispatcher, dumpManager, mediaTimeoutListener, mediaResumeListener, mediaSessionBasedFilter, mediaDeviceManager, mediaDataCombineLatest, mediaDataFilter, activityStarter, smartspaceMediaDataProvider, com.android.systemui.util.Utils.useMediaResumption(context), com.android.systemui.util.Utils.useQsMediaPlayer(context), systemClock, tunerService, mediaFlags, mediaUiEventLogger, smartspaceManager);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.run():void] */
    public static final /* synthetic */ ActivityStarter access$getActivityStarter$p(MediaDataManager mediaDataManager) {
        return mediaDataManager.activityStarter;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromState$customActions$1.invoke(android.media.session.PlaybackState$CustomAction):com.android.systemui.media.controls.models.player.MediaAction] */
    public static final /* synthetic */ MediaAction access$getCustomAction(MediaDataManager mediaDataManager, PlaybackState playbackState, String str, MediaController mediaController, PlaybackState.CustomAction customAction) {
        return mediaDataManager.getCustomAction(playbackState, str, mediaController, customAction);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$dismissMediaData$1.run():void] */
    public static final /* synthetic */ MediaControllerFactory access$getMediaControllerFactory$p(MediaDataManager mediaDataManager) {
        return mediaDataManager.mediaControllerFactory;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$dismissMediaData$1.run():void] */
    public static final /* synthetic */ LinkedHashMap access$getMediaEntries$p(MediaDataManager mediaDataManager) {
        return mediaDataManager.mediaEntries;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$3$1.onTargetsAvailable(java.util.List<android.app.smartspace.SmartspaceTarget>):void] */
    public static final /* synthetic */ SmartspaceMediaDataProvider access$getSmartspaceMediaDataProvider$p(MediaDataManager mediaDataManager) {
        return mediaDataManager.smartspaceMediaDataProvider;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$addResumptionControls$1.run():void] */
    public static final /* synthetic */ void access$loadMediaDataInBgForResumption(MediaDataManager mediaDataManager, int i, MediaDescription mediaDescription, Runnable runnable, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2) {
        mediaDataManager.loadMediaDataInBgForResumption(i, mediaDescription, runnable, token, str, pendingIntent, str2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$dismissSmartspaceRecommendation$1.run():void] */
    public static final /* synthetic */ void access$notifySmartspaceMediaDataRemoved(MediaDataManager mediaDataManager, String str, boolean z) {
        mediaDataManager.notifySmartspaceMediaDataRemoved(str, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$appChangeReceiver$1.onReceive(android.content.Context, android.content.Intent):void] */
    public static final /* synthetic */ void access$removeAllForPackage(MediaDataManager mediaDataManager, String str) {
        mediaDataManager.removeAllForPackage(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$dismissMediaData$2.run():void] */
    public static final /* synthetic */ void access$removeEntry(MediaDataManager mediaDataManager, String str) {
        mediaDataManager.removeEntry(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.run():void, com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.1.onDismiss():boolean] */
    public static final /* synthetic */ boolean access$sendPendingIntent(MediaDataManager mediaDataManager, PendingIntent pendingIntent) {
        return mediaDataManager.sendPendingIntent(pendingIntent);
    }

    public static final MediaAction createActionsFromState$nextCustomAction(Iterator<MediaAction> it) {
        return it.hasNext() ? it.next() : null;
    }

    public static /* synthetic */ void setTimedOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(MediaDataManager mediaDataManager, String str, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        mediaDataManager.setTimedOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core(str, z, z2);
    }

    public final boolean addInternalListener(Listener listener) {
        return this.internalListeners.add(listener);
    }

    public final void addListener(Listener listener) {
        this.mediaDataFilter.addListener(listener);
    }

    public final void addResumptionControls(final int i, final MediaDescription mediaDescription, final Runnable runnable, final MediaSession.Token token, final String str, final PendingIntent pendingIntent, final String str2) {
        int i2;
        MediaData mediaData;
        if (!this.mediaEntries.containsKey(str2)) {
            InstanceId newInstanceId = this.logger.getNewInstanceId();
            try {
                ApplicationInfo applicationInfo = this.context.getPackageManager().getApplicationInfo(str2, 0);
                Integer valueOf = applicationInfo != null ? Integer.valueOf(applicationInfo.uid) : null;
                Intrinsics.checkNotNull(valueOf);
                i2 = valueOf.intValue();
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaDataManager", "Could not get app UID for " + str2, e);
                i2 = -1;
            }
            mediaData = MediaDataManagerKt.LOADING;
            this.mediaEntries.put(str2, MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, str2, null, null, null, false, runnable, 0, false, null, true, null, false, 0L, newInstanceId, i2, 7830527, null));
            logSingleVsMultipleMediaAdded(i2, str2, newInstanceId);
            this.logger.logResumeMediaAdded(i2, str2, newInstanceId);
        }
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$addResumptionControls$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataManager.access$loadMediaDataInBgForResumption(MediaDataManager.this, i, mediaDescription, runnable, token, str, pendingIntent, str2);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x002d, code lost:
        if (r0 == 0) goto L42;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v92, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v94, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair<List<MediaAction>, List<Integer>> createActionsFromNotification(StatusBarNotification statusBarNotification) {
        ArrayList arrayList;
        Icon icon;
        Notification notification = statusBarNotification.getNotification();
        ArrayList arrayList2 = new ArrayList();
        Notification.Action[] actionArr = notification.actions;
        int[] intArray = notification.extras.getIntArray("android.compactActions");
        if (intArray != null) {
            ?? mutableList = ArraysKt___ArraysKt.toMutableList(intArray);
            arrayList = mutableList;
        }
        arrayList = new ArrayList();
        int size = arrayList.size();
        int i = MAX_COMPACT_ACTIONS;
        ArrayList arrayList3 = arrayList;
        if (size > i) {
            Log.e("MediaDataManager", "Too many compact actions for " + statusBarNotification.getKey() + ",limiting to first " + i);
            arrayList3 = arrayList.subList(0, i);
        }
        if (actionArr != null) {
            int length = actionArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                final Notification.Action action = actionArr[i2];
                int i3 = MAX_NOTIFICATION_ACTIONS;
                if (i2 == i3) {
                    Log.w("MediaDataManager", "Too many notification actions for " + statusBarNotification.getKey() + ", limiting to first " + i3);
                    break;
                }
                if (action.getIcon() == null) {
                    CharSequence charSequence = action.title;
                    Log.i("MediaDataManager", "No icon for action " + i2 + " " + ((Object) charSequence));
                    arrayList3.remove(Integer.valueOf(i2));
                } else {
                    Runnable runnable = action.actionIntent != null ? new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (action.actionIntent.isActivity()) {
                                MediaDataManager.access$getActivityStarter$p(this).startPendingIntentDismissingKeyguard(action.actionIntent);
                            } else if (!action.isAuthenticationRequired()) {
                                MediaDataManager.access$sendPendingIntent(this, action.actionIntent);
                            } else {
                                ActivityStarter access$getActivityStarter$p = MediaDataManager.access$getActivityStarter$p(this);
                                final MediaDataManager mediaDataManager = this;
                                final Notification.Action action2 = action;
                                access$getActivityStarter$p.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.1
                                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                    public final boolean onDismiss() {
                                        return MediaDataManager.access$sendPendingIntent(MediaDataManager.this, action2.actionIntent);
                                    }
                                }, new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromNotification$runnable$1.2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                    }
                                }, true);
                            }
                        }
                    } : null;
                    Icon icon2 = action.getIcon();
                    if (icon2 != null && icon2.getType() == 2) {
                        String packageName = statusBarNotification.getPackageName();
                        Icon icon3 = action.getIcon();
                        Intrinsics.checkNotNull(icon3);
                        icon = Icon.createWithResource(packageName, icon3.getResId());
                    } else {
                        icon = action.getIcon();
                    }
                    arrayList2.add(new MediaAction(icon.setTint(this.themeText).loadDrawable(this.context), runnable, action.title, null, null, 16, null));
                }
                i2++;
            }
        }
        return new Pair<>(arrayList2, arrayList3);
    }

    public final MediaButton createActionsFromState(final String str, final MediaController mediaController, UserHandle userHandle) {
        MediaAction standardAction;
        MediaAction mediaAction;
        final PlaybackState playbackState = mediaController.getPlaybackState();
        if (playbackState == null || !this.mediaFlags.areMediaSessionActionsEnabled(str, userHandle)) {
            return null;
        }
        if (NotificationMediaManager.isConnectingState(playbackState.getState())) {
            Drawable drawable = this.context.getDrawable(17303360);
            ((Animatable) drawable).start();
            standardAction = new MediaAction(drawable, null, this.context.getString(R$string.controls_media_button_connecting), this.context.getDrawable(R$drawable.ic_media_connecting_container), 17303360);
        } else {
            standardAction = NotificationMediaManager.isPlayingState(playbackState.getState()) ? getStandardAction(mediaController, playbackState.getActions(), 2L) : getStandardAction(mediaController, playbackState.getActions(), 4L);
        }
        MediaAction standardAction2 = getStandardAction(mediaController, playbackState.getActions(), 16L);
        MediaAction standardAction3 = getStandardAction(mediaController, playbackState.getActions(), 32L);
        Iterator it = SequencesKt___SequencesKt.map(SequencesKt___SequencesKt.filterNotNull(CollectionsKt___CollectionsKt.asSequence(playbackState.getCustomActions())), new Function1<PlaybackState.CustomAction, MediaAction>() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$createActionsFromState$customActions$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final MediaAction invoke(PlaybackState.CustomAction customAction) {
                return MediaDataManager.access$getCustomAction(MediaDataManager.this, playbackState, str, mediaController, customAction);
            }
        }).iterator();
        Bundle extras = mediaController.getExtras();
        boolean z = extras != null && extras.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS");
        Bundle extras2 = mediaController.getExtras();
        boolean z2 = extras2 != null && extras2.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT");
        MediaAction createActionsFromState$nextCustomAction = standardAction2 != null ? standardAction2 : !z ? createActionsFromState$nextCustomAction(it) : null;
        if (standardAction3 != null) {
            mediaAction = standardAction3;
        } else {
            mediaAction = null;
            if (!z2) {
                mediaAction = createActionsFromState$nextCustomAction(it);
            }
        }
        return new MediaButton(standardAction, mediaAction, createActionsFromState$nextCustomAction, createActionsFromState$nextCustomAction(it), createActionsFromState$nextCustomAction(it), z2, z);
    }

    public final boolean dismissMediaData(final String str, long j) {
        boolean z = this.mediaEntries.get(str) != null;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$dismissMediaData$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSession.Token token;
                MediaData mediaData = (MediaData) MediaDataManager.access$getMediaEntries$p(MediaDataManager.this).get(str);
                if (mediaData != null) {
                    MediaDataManager mediaDataManager = MediaDataManager.this;
                    if (!mediaData.isLocalSession() || (token = mediaData.getToken()) == null) {
                        return;
                    }
                    MediaDataManager.access$getMediaControllerFactory$p(mediaDataManager).create(token).getTransportControls().stop();
                }
            }
        });
        this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$dismissMediaData$2
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataManager.access$removeEntry(MediaDataManager.this, str);
            }
        }, j);
        return z;
    }

    public final void dismissSmartspaceRecommendation(String str, long j) {
        if (Intrinsics.areEqual(this.smartspaceMediaData.getTargetId(), str) && this.smartspaceMediaData.isValid()) {
            Log.d("MediaDataManager", "Dismissing Smartspace media target");
            if (this.smartspaceMediaData.isActive()) {
                this.smartspaceMediaData = SmartspaceMediaData.copy$default(MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA(), this.smartspaceMediaData.getTargetId(), false, null, null, null, null, 0L, this.smartspaceMediaData.getInstanceId(), R$styleable.AppCompatTheme_windowNoTitle, null);
            }
            this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$dismissSmartspaceRecommendation$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaDataManager mediaDataManager = MediaDataManager.this;
                    MediaDataManager.access$notifySmartspaceMediaDataRemoved(mediaDataManager, mediaDataManager.getSmartspaceMediaData().getTargetId(), true);
                }
            }, j);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        Set<Listener> set = this.internalListeners;
        printWriter.println("internalListeners: " + set);
        Set<Listener> listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core = this.mediaDataFilter.getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        printWriter.println("externalListeners: " + listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core);
        LinkedHashMap<String, MediaData> linkedHashMap = this.mediaEntries;
        printWriter.println("mediaEntries: " + linkedHashMap);
        boolean z = this.useMediaResumption;
        printWriter.println("useMediaResumption: " + z);
        boolean z2 = this.allowMediaRecommendations;
        printWriter.println("allowMediaRecommendations: " + z2);
    }

    public final String findExistingEntry(String str, String str2) {
        if (this.mediaEntries.containsKey(str)) {
            return str;
        }
        if (this.mediaEntries.containsKey(str2)) {
            return str2;
        }
        return null;
    }

    public final ApplicationInfo getAppInfoFromPackage(String str) {
        try {
            return this.context.getPackageManager().getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("MediaDataManager", "Could not get app info for " + str, e);
            return null;
        }
    }

    public final String getAppName(StatusBarNotification statusBarNotification, ApplicationInfo applicationInfo) {
        String string = statusBarNotification.getNotification().extras.getString("android.substName");
        if (string != null) {
            return string;
        }
        return applicationInfo != null ? this.context.getPackageManager().getApplicationLabel(applicationInfo).toString() : statusBarNotification.getPackageName();
    }

    public final MediaAction getCustomAction(PlaybackState playbackState, String str, final MediaController mediaController, final PlaybackState.CustomAction customAction) {
        return new MediaAction(Icon.createWithResource(str, customAction.getIcon()).loadDrawable(this.context), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getCustomAction$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.TransportControls transportControls = mediaController.getTransportControls();
                PlaybackState.CustomAction customAction2 = customAction;
                transportControls.sendCustomAction(customAction2, customAction2.getExtras());
            }
        }, customAction.getName(), null, null, 16, null);
    }

    public final MediaAction getResumeMediaAction(Runnable runnable) {
        return new MediaAction(Icon.createWithResource(this.context, R$drawable.ic_media_play).setTint(this.themeText).loadDrawable(this.context), runnable, this.context.getString(R$string.controls_media_resume), this.context.getDrawable(R$drawable.ic_media_play_container), null, 16, null);
    }

    public final SmartspaceMediaData getSmartspaceMediaData() {
        return this.smartspaceMediaData;
    }

    public final MediaAction getStandardAction(final MediaController mediaController, long j, long j2) {
        MediaAction mediaAction = null;
        if (includesAction(j, j2)) {
            if (j2 == 4) {
                mediaAction = new MediaAction(this.context.getDrawable(R$drawable.ic_media_play), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getStandardAction$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().play();
                    }
                }, this.context.getString(R$string.controls_media_button_play), this.context.getDrawable(R$drawable.ic_media_play_container), null, 16, null);
            } else if (j2 == 2) {
                mediaAction = new MediaAction(this.context.getDrawable(R$drawable.ic_media_pause), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getStandardAction$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().pause();
                    }
                }, this.context.getString(R$string.controls_media_button_pause), this.context.getDrawable(R$drawable.ic_media_pause_container), null, 16, null);
            } else if (j2 == 16) {
                mediaAction = new MediaAction(this.context.getDrawable(R$drawable.ic_media_prev), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getStandardAction$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().skipToPrevious();
                    }
                }, this.context.getString(R$string.controls_media_button_prev), null, null, 16, null);
            } else if (j2 == 32) {
                mediaAction = new MediaAction(this.context.getDrawable(R$drawable.ic_media_next), new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$getStandardAction$4
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().skipToNext();
                    }
                }, this.context.getString(R$string.controls_media_button_next), null, null, 16, null);
            }
            return mediaAction;
        }
        return null;
    }

    public final boolean hasActiveMedia() {
        return this.mediaDataFilter.hasActiveMedia();
    }

    public final boolean hasActiveMediaOrRecommendation() {
        return this.mediaDataFilter.hasActiveMediaOrRecommendation();
    }

    public final boolean hasAnyMedia() {
        return this.mediaDataFilter.hasAnyMedia();
    }

    public final boolean hasAnyMediaOrRecommendation() {
        return this.mediaDataFilter.hasAnyMediaOrRecommendation();
    }

    public final boolean includesAction(long j, long j2) {
        boolean z = true;
        if ((j2 == 4 || j2 == 2) && (512 & j) > 0) {
            return true;
        }
        if ((j & j2) == 0) {
            z = false;
        }
        return z;
    }

    public final boolean isRemoteCastNotification(StatusBarNotification statusBarNotification) {
        return statusBarNotification.getNotification().extras.containsKey("android.mediaRemoteDevice");
    }

    public final Bitmap loadBitmapFromUri(MediaMetadata mediaMetadata) {
        String[] strArr;
        Bitmap loadBitmapFromUri;
        strArr = MediaDataManagerKt.ART_URIS;
        for (String str : strArr) {
            String string = mediaMetadata.getString(str);
            if (!TextUtils.isEmpty(string) && (loadBitmapFromUri = loadBitmapFromUri(Uri.parse(string))) != null) {
                Log.d("MediaDataManager", "loaded art from " + str);
                return loadBitmapFromUri;
            }
        }
        return null;
    }

    public final Bitmap loadBitmapFromUri(Uri uri) {
        Bitmap bitmap;
        if (uri.getScheme() == null) {
            return null;
        }
        if (uri.getScheme().equals("content") || uri.getScheme().equals("android.resource") || uri.getScheme().equals("file")) {
            try {
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.context.getContentResolver(), uri), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadBitmapFromUri$1
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        imageDecoder.setAllocator(1);
                    }
                });
            } catch (IOException e) {
                Log.e("MediaDataManager", "Unable to load bitmap", e);
                bitmap = null;
            } catch (RuntimeException e2) {
                Log.e("MediaDataManager", "Unable to load bitmap", e2);
                bitmap = null;
            }
            return bitmap;
        }
        return null;
    }

    public final void loadMediaData(final String str, final StatusBarNotification statusBarNotification, final String str2, final boolean z) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaData$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataManager.this.loadMediaDataInBg(str, statusBarNotification, str2, z);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:191:0x02e4, code lost:
        if (r31 == null) goto L88;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadMediaDataInBg(final String str, final StatusBarNotification statusBarNotification, final String str2, boolean z) {
        int i;
        InstanceId newInstanceId;
        final MediaSession.Token token = (MediaSession.Token) statusBarNotification.getNotification().extras.getParcelable("android.mediaSession", MediaSession.Token.class);
        if (token == null) {
            return;
        }
        MediaController create = this.mediaControllerFactory.create(token);
        MediaMetadata metadata = create.getMetadata();
        final Notification notification = statusBarNotification.getNotification();
        ApplicationInfo applicationInfo = (ApplicationInfo) notification.extras.getParcelable("android.appInfo", ApplicationInfo.class);
        ApplicationInfo applicationInfo2 = applicationInfo;
        if (applicationInfo == null) {
            applicationInfo2 = getAppInfoFromPackage(statusBarNotification.getPackageName());
        }
        Bitmap loadBitmapFromUri = metadata != null ? loadBitmapFromUri(metadata) : null;
        Bitmap bitmap = loadBitmapFromUri;
        if (loadBitmapFromUri == null) {
            bitmap = metadata != null ? metadata.getBitmap("android.media.metadata.ART") : null;
        }
        Bitmap bitmap2 = bitmap;
        if (bitmap == null) {
            bitmap2 = metadata != null ? metadata.getBitmap("android.media.metadata.ALBUM_ART") : null;
        }
        Icon largeIcon = bitmap2 == null ? notification.getLargeIcon() : Icon.createWithBitmap(bitmap2);
        final String appName = getAppName(statusBarNotification, applicationInfo2);
        final Icon smallIcon = statusBarNotification.getNotification().getSmallIcon();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        String string = metadata != null ? metadata.getString("android.media.metadata.DISPLAY_TITLE") : null;
        objectRef.element = string;
        if (string == null) {
            objectRef.element = metadata != null ? metadata.getString("android.media.metadata.TITLE") : null;
        }
        if (objectRef.element == null) {
            objectRef.element = HybridGroupManager.resolveTitle(notification);
        }
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        String string2 = metadata != null ? metadata.getString("android.media.metadata.ARTIST") : null;
        objectRef2.element = string2;
        if (string2 == null) {
            objectRef2.element = HybridGroupManager.resolveText(notification);
        }
        final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        if (isRemoteCastNotification(statusBarNotification)) {
            Bundle bundle = statusBarNotification.getNotification().extras;
            CharSequence charSequence = bundle.getCharSequence("android.mediaRemoteDevice", null);
            int i2 = bundle.getInt("android.mediaRemoteIcon", -1);
            PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
            Log.d("MediaDataManager", str + " is RCN for " + ((Object) charSequence));
            if (charSequence != null && i2 > -1) {
                objectRef3.element = new MediaDeviceData(pendingIntent != null && pendingIntent.isActivity(), Icon.createWithResource(statusBarNotification.getPackageName(), i2).loadDrawable(statusBarNotification.getPackageContext(this.context)), charSequence, pendingIntent, null, false, 16, null);
            }
        }
        final Ref.ObjectRef objectRef4 = new Ref.ObjectRef();
        objectRef4.element = CollectionsKt__CollectionsKt.emptyList();
        final Ref.ObjectRef objectRef5 = new Ref.ObjectRef();
        objectRef5.element = CollectionsKt__CollectionsKt.emptyList();
        final MediaButton createActionsFromState = createActionsFromState(statusBarNotification.getPackageName(), create, statusBarNotification.getUser());
        if (createActionsFromState == null) {
            Pair<List<MediaAction>, List<Integer>> createActionsFromNotification = createActionsFromNotification(statusBarNotification);
            objectRef4.element = createActionsFromNotification.getFirst();
            objectRef5.element = createActionsFromNotification.getSecond();
        }
        if (isRemoteCastNotification(statusBarNotification)) {
            i = 2;
        } else {
            MediaController.PlaybackInfo playbackInfo = create.getPlaybackInfo();
            i = playbackInfo != null && playbackInfo.getPlaybackType() == 1 ? 0 : 1;
        }
        PlaybackState playbackState = create.getPlaybackState();
        Boolean valueOf = playbackState != null ? Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())) : null;
        MediaData mediaData = this.mediaEntries.get(str);
        if (mediaData != null) {
            newInstanceId = mediaData.getInstanceId();
        }
        newInstanceId = this.logger.getNewInstanceId();
        int i3 = applicationInfo2 != null ? applicationInfo2.uid : -1;
        if (z) {
            logSingleVsMultipleMediaAdded(i3, statusBarNotification.getPackageName(), newInstanceId);
            this.logger.logActiveMediaAdded(i3, statusBarNotification.getPackageName(), newInstanceId, i);
        } else {
            boolean z2 = false;
            if (mediaData != null) {
                z2 = false;
                if (i == mediaData.getPlaybackLocation()) {
                    z2 = true;
                }
            }
            if (!z2) {
                this.logger.logPlaybackLocationChange(i3, statusBarNotification.getPackageName(), newInstanceId, i);
            }
        }
        final long elapsedRealtime = this.systemClock.elapsedRealtime();
        final Icon icon = largeIcon;
        final int i4 = i;
        final Boolean bool = valueOf;
        final InstanceId instanceId = newInstanceId;
        final int i5 = i3;
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBg$1
            @Override // java.lang.Runnable
            public final void run() {
                LinkedHashMap linkedHashMap;
                LinkedHashMap linkedHashMap2;
                LinkedHashMap linkedHashMap3;
                linkedHashMap = MediaDataManager.this.mediaEntries;
                MediaData mediaData2 = (MediaData) linkedHashMap.get(str);
                Runnable resumeAction = mediaData2 != null ? mediaData2.getResumeAction() : null;
                linkedHashMap2 = MediaDataManager.this.mediaEntries;
                MediaData mediaData3 = (MediaData) linkedHashMap2.get(str);
                boolean z3 = mediaData3 != null && mediaData3.getHasCheckedForResume();
                linkedHashMap3 = MediaDataManager.this.mediaEntries;
                MediaData mediaData4 = (MediaData) linkedHashMap3.get(str);
                MediaDataManager.this.onMediaDataLoaded(str, str2, new MediaData(statusBarNotification.getNormalizedUserId(), true, appName, smallIcon, (CharSequence) objectRef2.element, (CharSequence) objectRef.element, icon, (List) objectRef4.element, (List) objectRef5.element, createActionsFromState, statusBarNotification.getPackageName(), token, notification.contentIntent, (MediaDeviceData) objectRef3.element, mediaData4 != null ? mediaData4.getActive() : true, resumeAction, i4, false, str, z3, bool, statusBarNotification.isClearable(), elapsedRealtime, instanceId, i5, 131072, null));
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b1, code lost:
        if (r0 == null) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadMediaDataInBgForResumption(final int i, final MediaDescription mediaDescription, final Runnable runnable, final MediaSession.Token token, final String str, final PendingIntent pendingIntent, final String str2) {
        InstanceId newInstanceId;
        if (TextUtils.isEmpty(mediaDescription.getTitle())) {
            Log.e("MediaDataManager", "Description incomplete");
            this.mediaEntries.remove(str2);
            return;
        }
        Log.d("MediaDataManager", "adding track for " + i + " from browser: " + mediaDescription);
        Bitmap iconBitmap = mediaDescription.getIconBitmap();
        Bitmap bitmap = iconBitmap;
        if (iconBitmap == null) {
            bitmap = iconBitmap;
            if (mediaDescription.getIconUri() != null) {
                Uri iconUri = mediaDescription.getIconUri();
                Intrinsics.checkNotNull(iconUri);
                bitmap = loadBitmapFromUri(iconUri);
            }
        }
        Icon createWithBitmap = bitmap != null ? Icon.createWithBitmap(bitmap) : null;
        MediaData mediaData = this.mediaEntries.get(str2);
        if (mediaData != null) {
            InstanceId instanceId = mediaData.getInstanceId();
            newInstanceId = instanceId;
        }
        newInstanceId = this.logger.getNewInstanceId();
        int appUid = mediaData != null ? mediaData.getAppUid() : -1;
        final MediaAction resumeMediaAction = getResumeMediaAction(runnable);
        final long elapsedRealtime = this.systemClock.elapsedRealtime();
        final Icon icon = createWithBitmap;
        final InstanceId instanceId2 = newInstanceId;
        final int i2 = appUid;
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDataManager$loadMediaDataInBgForResumption$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataManager mediaDataManager = MediaDataManager.this;
                String str3 = str2;
                int i3 = i;
                String str4 = str;
                CharSequence subtitle = mediaDescription.getSubtitle();
                CharSequence title = mediaDescription.getTitle();
                Icon icon2 = icon;
                List listOf = CollectionsKt__CollectionsJVMKt.listOf(resumeMediaAction);
                List listOf2 = CollectionsKt__CollectionsJVMKt.listOf(0);
                MediaButton mediaButton = new MediaButton(resumeMediaAction, null, null, null, null, false, false, R$styleable.AppCompatTheme_windowNoTitle, null);
                String str5 = str2;
                mediaDataManager.onMediaDataLoaded(str3, null, new MediaData(i3, true, str4, null, subtitle, title, icon2, listOf, listOf2, mediaButton, str5, token, pendingIntent, null, false, runnable, 0, true, str5, true, null, false, elapsedRealtime, instanceId2, i2, 3211264, null));
            }
        });
    }

    public final void logSingleVsMultipleMediaAdded(int i, String str, InstanceId instanceId) {
        if (this.mediaEntries.size() == 1) {
            this.logger.logSingleMediaPlayerInCarousel(i, str, instanceId);
        } else if (this.mediaEntries.size() == 2) {
            this.logger.logMultipleMediaPlayersInCarousel(i, str, instanceId);
        }
    }

    public final void notifyMediaDataLoaded(String str, String str2, MediaData mediaData) {
        for (Listener listener : this.internalListeners) {
            Listener.DefaultImpls.onMediaDataLoaded$default(listener, str, str2, mediaData, false, 0, false, 56, null);
        }
    }

    public final void notifyMediaDataRemoved(String str) {
        for (Listener listener : this.internalListeners) {
            listener.onMediaDataRemoved(str);
        }
    }

    public final void notifySmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        for (Listener listener : this.internalListeners) {
            Listener.DefaultImpls.onSmartspaceMediaDataLoaded$default(listener, str, smartspaceMediaData, false, 4, null);
        }
    }

    public final void notifySmartspaceMediaDataRemoved(String str, boolean z) {
        for (Listener listener : this.internalListeners) {
            listener.onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData) {
        if (!Trace.isTagEnabled(4096L)) {
            Assert.isMainThread();
            if (this.mediaEntries.containsKey(str)) {
                this.mediaEntries.put(str, mediaData);
                notifyMediaDataLoaded(str, str2, mediaData);
                return;
            }
            return;
        }
        Trace.traceBegin(4096L, "MediaDataManager#onMediaDataLoaded");
        try {
            Assert.isMainThread();
            if (this.mediaEntries.containsKey(str)) {
                this.mediaEntries.put(str, mediaData);
                notifyMediaDataLoaded(str, str2, mediaData);
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final void onNotificationAdded(String str, StatusBarNotification statusBarNotification) {
        MediaData mediaData;
        if (!this.useQsMediaPlayer || !MediaDataManagerKt.isMediaNotification(statusBarNotification)) {
            onNotificationRemoved(str);
            return;
        }
        boolean z = false;
        Assert.isMainThread();
        String findExistingEntry = findExistingEntry(str, statusBarNotification.getPackageName());
        if (findExistingEntry != null) {
            if (!Intrinsics.areEqual(findExistingEntry, str)) {
                MediaData remove = this.mediaEntries.remove(findExistingEntry);
                Intrinsics.checkNotNull(remove);
                this.mediaEntries.put(str, remove);
            }
            loadMediaData(str, statusBarNotification, findExistingEntry, z);
        }
        InstanceId newInstanceId = this.logger.getNewInstanceId();
        mediaData = MediaDataManagerKt.LOADING;
        this.mediaEntries.put(str, MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, statusBarNotification.getPackageName(), null, null, null, false, null, 0, false, null, false, null, false, 0L, newInstanceId, 0, 25164799, null));
        z = true;
        loadMediaData(str, statusBarNotification, findExistingEntry, z);
    }

    public final void onNotificationRemoved(String str) {
        Assert.isMainThread();
        MediaData remove = this.mediaEntries.remove(str);
        if (this.useMediaResumption) {
            if ((remove != null ? remove.getResumeAction() : null) != null && remove.isLocalSession()) {
                Log.d("MediaDataManager", "Not removing " + str + " because resumable");
                Runnable resumeAction = remove.getResumeAction();
                Intrinsics.checkNotNull(resumeAction);
                MediaAction resumeMediaAction = getResumeMediaAction(resumeAction);
                boolean z = false;
                MediaData copy$default = MediaData.copy$default(remove, 0, false, null, null, null, null, null, CollectionsKt__CollectionsJVMKt.listOf(resumeMediaAction), CollectionsKt__CollectionsJVMKt.listOf(0), new MediaButton(resumeMediaAction, null, null, null, null, false, false, R$styleable.AppCompatTheme_windowNoTitle, null), null, null, null, null, false, null, 0, true, null, false, Boolean.FALSE, true, 0L, null, 0, 30258303, null);
                String packageName = remove.getPackageName();
                if (this.mediaEntries.put(packageName, copy$default) == null) {
                    z = true;
                }
                if (z) {
                    notifyMediaDataLoaded(packageName, str, copy$default);
                } else {
                    notifyMediaDataRemoved(str);
                    notifyMediaDataLoaded(packageName, packageName, copy$default);
                }
                this.logger.logActiveConvertedToResume(copy$default.getAppUid(), packageName, copy$default.getInstanceId());
                return;
            }
        }
        if (remove != null) {
            notifyMediaDataRemoved(str);
            this.logger.logMediaRemoved(remove.getAppUid(), remove.getPackageName(), remove.getInstanceId());
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public void onSmartspaceTargetsUpdated(List<? extends Parcelable> list) {
        if (!this.allowMediaRecommendations) {
            Log.d("MediaDataManager", "Smartspace recommendation is disabled in Settings.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof SmartspaceTarget) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            if (this.smartspaceMediaData.isActive()) {
                Log.d("MediaDataManager", "Set Smartspace media to be inactive for the data update");
                SmartspaceMediaData copy$default = SmartspaceMediaData.copy$default(MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA(), this.smartspaceMediaData.getTargetId(), false, null, null, null, null, 0L, this.smartspaceMediaData.getInstanceId(), R$styleable.AppCompatTheme_windowNoTitle, null);
                this.smartspaceMediaData = copy$default;
                notifySmartspaceMediaDataRemoved(copy$default.getTargetId(), false);
            }
        } else if (size != 1) {
            Log.wtf("MediaDataManager", "More than 1 Smartspace Media Update. Resetting the status...");
            notifySmartspaceMediaDataRemoved(this.smartspaceMediaData.getTargetId(), false);
            this.smartspaceMediaData = MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA();
        } else {
            SmartspaceTarget smartspaceTarget = (SmartspaceTarget) arrayList.get(0);
            if (Intrinsics.areEqual(this.smartspaceMediaData.getTargetId(), smartspaceTarget.getSmartspaceTargetId())) {
                return;
            }
            Log.d("MediaDataManager", "Forwarding Smartspace media update.");
            SmartspaceMediaData smartspaceMediaData = toSmartspaceMediaData(smartspaceTarget, true);
            this.smartspaceMediaData = smartspaceMediaData;
            notifySmartspaceMediaDataLoaded(smartspaceMediaData.getTargetId(), this.smartspaceMediaData);
        }
    }

    public final void onSwipeToDismiss() {
        this.mediaDataFilter.onSwipeToDismiss();
    }

    public final String packageName(SmartspaceTarget smartspaceTarget) {
        String string;
        List<SmartspaceAction> iconGrid = smartspaceTarget.getIconGrid();
        if (iconGrid == null || iconGrid.isEmpty()) {
            Log.w("MediaDataManager", "Empty or null media recommendation list.");
            return null;
        }
        for (SmartspaceAction smartspaceAction : iconGrid) {
            Bundle extras = smartspaceAction.getExtras();
            if (extras != null && (string = extras.getString(EXTRAS_MEDIA_SOURCE_PACKAGE_NAME)) != null) {
                return string;
            }
        }
        Log.w("MediaDataManager", "No valid package name is provided.");
        return null;
    }

    public final void removeAllForPackage(String str) {
        Assert.isMainThread();
        LinkedHashMap<String, MediaData> linkedHashMap = this.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry<String, MediaData> entry : linkedHashMap.entrySet()) {
            if (Intrinsics.areEqual(entry.getValue().getPackageName(), str)) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
            removeEntry((String) entry2.getKey());
        }
    }

    public final void removeEntry(String str) {
        MediaData remove = this.mediaEntries.remove(str);
        if (remove != null) {
            this.logger.logMediaRemoved(remove.getAppUid(), remove.getPackageName(), remove.getInstanceId());
        }
        notifyMediaDataRemoved(str);
    }

    public final void removeListener(Listener listener) {
        this.mediaDataFilter.removeListener(listener);
    }

    public final boolean sendPendingIntent(PendingIntent pendingIntent) {
        boolean z;
        try {
            pendingIntent.send();
            z = true;
        } catch (PendingIntent.CanceledException e) {
            Log.d("MediaDataManager", "Intent canceled", e);
            z = false;
        }
        return z;
    }

    public final void setMediaResumptionEnabled(boolean z) {
        if (this.useMediaResumption == z) {
            return;
        }
        this.useMediaResumption = z;
        if (z) {
            return;
        }
        LinkedHashMap<String, MediaData> linkedHashMap = this.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry<String, MediaData> entry : linkedHashMap.entrySet()) {
            if (!entry.getValue().getActive()) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
            this.mediaEntries.remove(entry2.getKey());
            notifyMediaDataRemoved((String) entry2.getKey());
            this.logger.logMediaRemoved(((MediaData) entry2.getValue()).getAppUid(), ((MediaData) entry2.getValue()).getPackageName(), ((MediaData) entry2.getValue()).getInstanceId());
        }
    }

    public final void setResumeAction(String str, Runnable runnable) {
        MediaData mediaData = this.mediaEntries.get(str);
        if (mediaData != null) {
            mediaData.setResumeAction(runnable);
            mediaData.setHasCheckedForResume(true);
        }
    }

    public final void setTimedOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core(String str, boolean z, boolean z2) {
        MediaData mediaData = this.mediaEntries.get(str);
        if (mediaData != null) {
            if (z && !z2) {
                this.logger.logMediaTimeout(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId());
            }
            if (mediaData.getActive() == (!z) && !z2) {
                if (mediaData.getResumption()) {
                    Log.d("MediaDataManager", "timing out resume player " + str);
                    dismissMediaData(str, 0L);
                    return;
                }
                return;
            }
            mediaData.setActive(!z);
            Log.d("MediaDataManager", "Updating " + str + " timedOut: " + z);
            onMediaDataLoaded(str, str, mediaData);
        }
    }

    public final SmartspaceMediaData toSmartspaceMediaData(SmartspaceTarget smartspaceTarget, boolean z) {
        Intent intent = (smartspaceTarget.getBaseAction() == null || smartspaceTarget.getBaseAction().getExtras() == null) ? null : (Intent) smartspaceTarget.getBaseAction().getExtras().getParcelable("dismiss_intent");
        String packageName = packageName(smartspaceTarget);
        return packageName != null ? new SmartspaceMediaData(smartspaceTarget.getSmartspaceTargetId(), z, packageName, smartspaceTarget.getBaseAction(), smartspaceTarget.getIconGrid(), intent, smartspaceTarget.getCreationTimeMillis(), this.logger.getNewInstanceId()) : SmartspaceMediaData.copy$default(MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA(), smartspaceTarget.getSmartspaceTargetId(), z, null, null, null, intent, smartspaceTarget.getCreationTimeMillis(), this.logger.getNewInstanceId(), 28, null);
    }

    public final void updateState(String str, PlaybackState playbackState) {
        MediaData mediaData = this.mediaEntries.get(str);
        if (mediaData != null) {
            if (mediaData.getToken() == null) {
                Log.d("MediaDataManager", "State updated, but token was null");
                return;
            }
            MediaButton createActionsFromState = createActionsFromState(mediaData.getPackageName(), this.mediaControllerFactory.create(mediaData.getToken()), new UserHandle(mediaData.getUserId()));
            MediaData copy$default = createActionsFromState != null ? MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, createActionsFromState, null, null, null, null, false, null, 0, false, null, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), false, 0L, null, 0, 32505343, null) : MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())), false, 0L, null, 0, 32505855, null);
            Log.d("MediaDataManager", "State updated outside of notification");
            onMediaDataLoaded(str, str, copy$default);
        }
    }
}