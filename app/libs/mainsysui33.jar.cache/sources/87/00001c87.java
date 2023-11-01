package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Trace;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaViewHolder;
import com.android.systemui.media.controls.models.recommendation.RecommendationViewHolder;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.MediaPlayerData;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.media.controls.util.SmallHash;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PageIndicator;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.Utils;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.android.systemui.util.animation.UniqueObjectHostViewKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselController.class */
public final class MediaCarouselController implements Dumpable {
    public final ActivityStarter activityStarter;
    public int carouselMeasureHeight;
    public int carouselMeasureWidth;
    public final MediaCarouselController$configListener$1 configListener;
    public final Context context;
    public int currentCarouselHeight;
    public int currentCarouselWidth;
    public boolean currentlyShowingOnlyActive;
    public final MediaCarouselControllerLogger debugLogger;
    public MediaHostState desiredHostState;
    public boolean isRtl;
    public final MediaUiEventLogger logger;
    public final MediaScrollView mediaCarousel;
    public final MediaCarouselScrollHandler mediaCarouselScrollHandler;
    public final ViewGroup mediaContent;
    public final Provider<MediaControlPanel> mediaControlPanelFactory;
    public final ViewGroup mediaFrame;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final MediaDataManager mediaManager;
    public boolean needsReordering;
    public final PageIndicator pageIndicator;
    public boolean playersVisible;
    public View settingsButton;
    public boolean shouldScrollToKey;
    public final SystemClock systemClock;
    public Function0<Unit> updateHostVisibility;
    public Function0<Unit> updateUserVisibility;
    public final OnReorderingAllowedListener visualStabilityCallback;
    public final VisualStabilityProvider visualStabilityProvider;
    public static final Companion Companion = new Companion(null);
    public static final PathInterpolator TRANSFORM_BEZIER = new PathInterpolator(0.68f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f);
    public static final PathInterpolator REVERSE_BEZIER = new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, 0.68f, 1.0f, ActionBarShadowController.ELEVATION_LOW);
    public int desiredLocation = -1;
    public int currentEndLocation = -1;
    public int currentStartLocation = -1;
    public float currentTransitionProgress = 1.0f;
    public Set<String> keysNeedRemoval = new LinkedHashSet();
    public boolean currentlyExpanded = true;

    /* renamed from: com.android.systemui.media.controls.ui.MediaCarouselController$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselController$1.class */
    public final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0<Unit> {
        public AnonymousClass1(Object obj) {
            super(0, obj, MediaCarouselController.class, "onSwipeToDismiss", "onSwipeToDismiss()V", 0);
        }

        public /* bridge */ /* synthetic */ Object invoke() {
            m3235invoke();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke */
        public final void m3235invoke() {
            ((MediaCarouselController) ((CallableReference) this).receiver).onSwipeToDismiss();
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.MediaCarouselController$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselController$2.class */
    public final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0<Unit> {
        public AnonymousClass2(Object obj) {
            super(0, obj, MediaCarouselController.class, "updatePageIndicatorLocation", "updatePageIndicatorLocation()V", 0);
        }

        public /* bridge */ /* synthetic */ Object invoke() {
            m3236invoke();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke */
        public final void m3236invoke() {
            ((MediaCarouselController) ((CallableReference) this).receiver).updatePageIndicatorLocation();
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.MediaCarouselController$3 */
    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselController$3.class */
    public final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1<Boolean, Unit> {
        public AnonymousClass3(Object obj) {
            super(1, obj, MediaCarouselController.class, "closeGuts", "closeGuts(Z)V", 0);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke(((Boolean) obj).booleanValue());
            return Unit.INSTANCE;
        }

        public final void invoke(boolean z) {
            ((MediaCarouselController) ((CallableReference) this).receiver).closeGuts(z);
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.MediaCarouselController$4 */
    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselController$4.class */
    public final /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1<Boolean, Unit> {
        public AnonymousClass4(Object obj) {
            super(1, obj, MediaCarouselController.class, "logSmartspaceImpression", "logSmartspaceImpression(Z)V", 0);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke(((Boolean) obj).booleanValue());
            return Unit.INSTANCE;
        }

        public final void invoke(boolean z) {
            ((MediaCarouselController) ((CallableReference) this).receiver).logSmartspaceImpression(z);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float calculateAlpha(float f, float f2, float f3) {
            return MathUtils.constrain((getREVERSE_BEZIER().getInterpolation(f) - (f2 / 2200.0f)) / (f3 / 2200.0f), (float) ActionBarShadowController.ELEVATION_LOW, 1.0f);
        }

        public final PathInterpolator getREVERSE_BEZIER() {
            return MediaCarouselController.REVERSE_BEZIER;
        }
    }

    /* JADX WARN: Type inference failed for: r0v16, types: [com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1, java.lang.Object] */
    public MediaCarouselController(Context context, Provider<MediaControlPanel> provider, VisualStabilityProvider visualStabilityProvider, MediaHostStatesManager mediaHostStatesManager, ActivityStarter activityStarter, SystemClock systemClock, DelayableExecutor delayableExecutor, MediaDataManager mediaDataManager, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, DumpManager dumpManager, MediaUiEventLogger mediaUiEventLogger, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        this.context = context;
        this.mediaControlPanelFactory = provider;
        this.visualStabilityProvider = visualStabilityProvider;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.activityStarter = activityStarter;
        this.systemClock = systemClock;
        this.mediaManager = mediaDataManager;
        this.logger = mediaUiEventLogger;
        this.debugLogger = mediaCarouselControllerLogger;
        ?? r0 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1
            public void onConfigChanged(Configuration configuration) {
                if (configuration == null) {
                    return;
                }
                MediaCarouselController.access$setRtl(MediaCarouselController.this, configuration.getLayoutDirection() == 1);
                MediaCarouselController.access$updatePlayers(MediaCarouselController.this, true);
            }

            public void onDensityOrFontScaleChanged() {
                MediaCarouselController.access$updatePlayers(MediaCarouselController.this, true);
                MediaCarouselController.access$inflateSettingsButton(MediaCarouselController.this);
            }

            public void onThemeChanged() {
                MediaCarouselController.access$updatePlayers(MediaCarouselController.this, false);
                MediaCarouselController.access$inflateSettingsButton(MediaCarouselController.this);
            }

            public void onUiModeChanged() {
                MediaCarouselController.access$updatePlayers(MediaCarouselController.this, false);
                MediaCarouselController.access$inflateSettingsButton(MediaCarouselController.this);
            }
        };
        this.configListener = r0;
        DumpManager.registerDumpable$default(dumpManager, "MediaCarouselController", this, null, 4, null);
        ViewGroup inflateMediaCarousel = inflateMediaCarousel();
        this.mediaFrame = inflateMediaCarousel;
        MediaScrollView mediaScrollView = (MediaScrollView) inflateMediaCarousel.requireViewById(R$id.media_carousel_scroller);
        this.mediaCarousel = mediaScrollView;
        PageIndicator pageIndicator = (PageIndicator) inflateMediaCarousel.requireViewById(R$id.media_page_indicator);
        this.pageIndicator = pageIndicator;
        this.mediaCarouselScrollHandler = new MediaCarouselScrollHandler(mediaScrollView, pageIndicator, delayableExecutor, new AnonymousClass1(this), new AnonymousClass2(this), new AnonymousClass3(this), falsingCollector, falsingManager, new AnonymousClass4(this), mediaUiEventLogger);
        setRtl(context.getResources().getConfiguration().getLayoutDirection() == 1);
        inflateSettingsButton();
        this.mediaContent = (ViewGroup) mediaScrollView.requireViewById(R$id.media_carousel);
        configurationController.addCallback((Object) r0);
        OnReorderingAllowedListener onReorderingAllowedListener = new OnReorderingAllowedListener() { // from class: com.android.systemui.media.controls.ui.MediaCarouselController.5
            {
                MediaCarouselController.this = this;
            }

            public final void onReorderingAllowed() {
                if (MediaCarouselController.this.needsReordering) {
                    MediaCarouselController.this.needsReordering = false;
                    MediaCarouselController.reorderAllPlayers$default(MediaCarouselController.this, null, null, 2, null);
                }
                Set<String> set = MediaCarouselController.this.keysNeedRemoval;
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                for (String str : set) {
                    MediaCarouselController.removePlayer$default(mediaCarouselController, str, false, false, 6, null);
                }
                if (MediaCarouselController.this.keysNeedRemoval.size() > 0) {
                    MediaCarouselController.this.getUpdateHostVisibility().invoke();
                }
                MediaCarouselController.this.keysNeedRemoval.clear();
                MediaCarouselController mediaCarouselController2 = MediaCarouselController.this;
                if (mediaCarouselController2.updateUserVisibility != null) {
                    mediaCarouselController2.getUpdateUserVisibility().invoke();
                }
                MediaCarouselController.this.getMediaCarouselScrollHandler().scrollToStart();
            }
        };
        this.visualStabilityCallback = onReorderingAllowedListener;
        visualStabilityProvider.addPersistentReorderingAllowedListener(onReorderingAllowedListener);
        mediaDataManager.addListener(new MediaDataManager.Listener() { // from class: com.android.systemui.media.controls.ui.MediaCarouselController.6
            {
                MediaCarouselController.this = this;
            }

            @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
            public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
                Boolean isPlaying;
                MediaCarouselController.this.debugLogger.logMediaLoaded(str);
                if (MediaCarouselController.this.addOrUpdatePlayer(str, str2, mediaData, z2)) {
                    MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
                    MediaControlPanel mediaPlayer = mediaPlayerData.getMediaPlayer(str);
                    if (mediaPlayer != null) {
                        MediaCarouselController.logSmartspaceCardReported$default(MediaCarouselController.this, 759, mediaPlayer.mSmartspaceId, mediaPlayer.mUid, new int[]{4, 2, 5}, 0, 0, mediaPlayerData.getMediaPlayerIndex(str), 0, false, 432, null);
                    }
                    if (MediaCarouselController.this.getMediaCarouselScrollHandler().getVisibleToUser() && MediaCarouselController.this.getMediaCarouselScrollHandler().getVisibleMediaIndex() == mediaPlayerData.getMediaPlayerIndex(str)) {
                        MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                        mediaCarouselController.logSmartspaceImpression(mediaCarouselController.getMediaCarouselScrollHandler().getQsExpanded());
                    }
                } else if (i != 0) {
                    Collection<MediaControlPanel> players = MediaPlayerData.INSTANCE.players();
                    MediaCarouselController mediaCarouselController2 = MediaCarouselController.this;
                    int i2 = 0;
                    for (Object obj : players) {
                        if (i2 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        MediaControlPanel mediaControlPanel = (MediaControlPanel) obj;
                        if (mediaControlPanel.getRecommendationViewHolder() == null) {
                            int hash = SmallHash.hash(mediaControlPanel.mUid + ((int) mediaCarouselController2.systemClock.currentTimeMillis()));
                            mediaControlPanel.mSmartspaceId = hash;
                            mediaControlPanel.mIsImpressed = false;
                            MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController2, 759, hash, mediaControlPanel.mUid, new int[]{4, 2, 5}, 0, 0, i2, i, false, 304, null);
                        }
                        i2++;
                    }
                    if (MediaCarouselController.this.getMediaCarouselScrollHandler().getVisibleToUser() && !MediaCarouselController.this.getMediaCarouselScrollHandler().getQsExpanded()) {
                        MediaCarouselController mediaCarouselController3 = MediaCarouselController.this;
                        mediaCarouselController3.logSmartspaceImpression(mediaCarouselController3.getMediaCarouselScrollHandler().getQsExpanded());
                    }
                }
                boolean z3 = false;
                if (mediaData.isPlaying() != null ? !isPlaying.booleanValue() : mediaData.isClearable()) {
                    z3 = false;
                    if (!mediaData.getActive()) {
                        z3 = true;
                    }
                }
                if (!z3 || Utils.useMediaResumption(MediaCarouselController.this.context)) {
                    MediaCarouselController.this.keysNeedRemoval.remove(str);
                } else if (MediaCarouselController.this.isReorderingAllowed()) {
                    onMediaDataRemoved(str);
                } else {
                    MediaCarouselController.this.keysNeedRemoval.add(str);
                }
            }

            @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
            public void onMediaDataRemoved(String str) {
                MediaCarouselController.this.debugLogger.logMediaRemoved(str);
                MediaCarouselController.removePlayer$default(MediaCarouselController.this, str, false, false, 6, null);
            }

            @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
            public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
                MediaCarouselController.this.debugLogger.logRecommendationLoaded(str);
                boolean z2 = true;
                if (!smartspaceMediaData.isActive()) {
                    onSmartspaceMediaDataRemoved(smartspaceMediaData.getTargetId(), true);
                    return;
                }
                if ((!MediaCarouselController.this.mediaManager.hasActiveMedia() && MediaCarouselController.this.mediaManager.hasAnyMedia() && z) ? false : false) {
                    Collection<MediaControlPanel> players = MediaPlayerData.INSTANCE.players();
                    MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                    int i = 0;
                    for (Object obj : players) {
                        if (i < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        MediaControlPanel mediaControlPanel = (MediaControlPanel) obj;
                        if (mediaControlPanel.getRecommendationViewHolder() == null) {
                            int hash = SmallHash.hash(mediaControlPanel.mUid + ((int) mediaCarouselController.systemClock.currentTimeMillis()));
                            mediaControlPanel.mSmartspaceId = hash;
                            mediaControlPanel.mIsImpressed = false;
                            MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController, 759, hash, mediaControlPanel.mUid, new int[]{4, 2, 5}, 0, 0, i, (int) (mediaCarouselController.systemClock.currentTimeMillis() - smartspaceMediaData.getHeadphoneConnectionTimeMillis()), false, 304, null);
                        }
                        i++;
                    }
                }
                MediaCarouselController.this.addSmartspaceMediaRecommendations(str, smartspaceMediaData, z);
                MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
                MediaControlPanel mediaPlayer = mediaPlayerData.getMediaPlayer(str);
                if (mediaPlayer != null) {
                    MediaCarouselController mediaCarouselController2 = MediaCarouselController.this;
                    MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController2, 759, mediaPlayer.mSmartspaceId, mediaPlayer.mUid, new int[]{4, 2, 5}, 0, 0, mediaPlayerData.getMediaPlayerIndex(str), (int) (mediaCarouselController2.systemClock.currentTimeMillis() - smartspaceMediaData.getHeadphoneConnectionTimeMillis()), false, 304, null);
                }
                if (MediaCarouselController.this.getMediaCarouselScrollHandler().getVisibleToUser() && MediaCarouselController.this.getMediaCarouselScrollHandler().getVisibleMediaIndex() == mediaPlayerData.getMediaPlayerIndex(str)) {
                    MediaCarouselController mediaCarouselController3 = MediaCarouselController.this;
                    mediaCarouselController3.logSmartspaceImpression(mediaCarouselController3.getMediaCarouselScrollHandler().getQsExpanded());
                }
            }

            @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
            public void onSmartspaceMediaDataRemoved(String str, boolean z) {
                MediaCarouselController.this.debugLogger.logRecommendationRemoved(str, z);
                if (!z && !MediaCarouselController.this.isReorderingAllowed()) {
                    MediaCarouselController.this.keysNeedRemoval.add(str);
                    return;
                }
                MediaCarouselController.removePlayer$default(MediaCarouselController.this, str, false, false, 6, null);
                if (z) {
                    return;
                }
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                if (mediaCarouselController.updateHostVisibility != null) {
                    mediaCarouselController.getUpdateHostVisibility().invoke();
                }
            }
        });
        inflateMediaCarousel.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.controls.ui.MediaCarouselController.7
            {
                MediaCarouselController.this = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                MediaCarouselController.this.updatePageIndicatorLocation();
            }
        });
        mediaHostStatesManager.addCallback(new MediaHostStatesManager.Callback() { // from class: com.android.systemui.media.controls.ui.MediaCarouselController.8
            {
                MediaCarouselController.this = this;
            }

            @Override // com.android.systemui.media.controls.ui.MediaHostStatesManager.Callback
            public void onHostStateChanged(int i, MediaHostState mediaHostState) {
                if (i == MediaCarouselController.this.desiredLocation) {
                    MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                    MediaCarouselController.onDesiredLocationChanged$default(mediaCarouselController, mediaCarouselController.desiredLocation, mediaHostState, false, 0L, 0L, 24, null);
                }
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselController$inflateSettingsButton$2.onClick(android.view.View):void] */
    public static final /* synthetic */ ActivityStarter access$getActivityStarter$p(MediaCarouselController mediaCarouselController) {
        return mediaCarouselController.activityStarter;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselController$inflateSettingsButton$2.onClick(android.view.View):void] */
    public static final /* synthetic */ MediaUiEventLogger access$getLogger$p(MediaCarouselController mediaCarouselController) {
        return mediaCarouselController.logger;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1.onDensityOrFontScaleChanged():void, com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1.onThemeChanged():void, com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1.onUiModeChanged():void] */
    public static final /* synthetic */ void access$inflateSettingsButton(MediaCarouselController mediaCarouselController) {
        mediaCarouselController.inflateSettingsButton();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1.onConfigChanged(android.content.res.Configuration):void] */
    public static final /* synthetic */ void access$setRtl(MediaCarouselController mediaCarouselController, boolean z) {
        mediaCarouselController.setRtl(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1.onConfigChanged(android.content.res.Configuration):void, com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1.onDensityOrFontScaleChanged():void, com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1.onThemeChanged():void, com.android.systemui.media.controls.ui.MediaCarouselController$configListener$1.onUiModeChanged():void] */
    public static final /* synthetic */ void access$updatePlayers(MediaCarouselController mediaCarouselController, boolean z) {
        mediaCarouselController.updatePlayers(z);
    }

    public static /* synthetic */ void closeGuts$default(MediaCarouselController mediaCarouselController, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        mediaCarouselController.closeGuts(z);
    }

    public static /* synthetic */ void getCurrentEndLocation$annotations() {
    }

    public static /* synthetic */ void getPageIndicator$annotations() {
    }

    public static /* synthetic */ void getSettingsButton$annotations() {
    }

    public static /* synthetic */ void logSmartspaceCardReported$default(MediaCarouselController mediaCarouselController, int i, int i2, int i3, int[] iArr, int i4, int i5, int i6, int i7, boolean z, int i8, Object obj) {
        if ((i8 & 16) != 0) {
            i4 = 0;
        }
        if ((i8 & 32) != 0) {
            i5 = 0;
        }
        if ((i8 & 64) != 0) {
            i6 = mediaCarouselController.mediaCarouselScrollHandler.getVisibleMediaIndex();
        }
        if ((i8 & RecyclerView.ViewHolder.FLAG_IGNORE) != 0) {
            i7 = 0;
        }
        if ((i8 & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) != 0) {
            z = false;
        }
        mediaCarouselController.logSmartspaceCardReported(i, i2, i3, iArr, i4, i5, i6, i7, z);
    }

    public static /* synthetic */ Unit onDesiredLocationChanged$default(MediaCarouselController mediaCarouselController, int i, MediaHostState mediaHostState, boolean z, long j, long j2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            j = 200;
        }
        if ((i2 & 16) != 0) {
            j2 = 0;
        }
        return mediaCarouselController.onDesiredLocationChanged(i, mediaHostState, z, j, j2);
    }

    public static /* synthetic */ MediaControlPanel removePlayer$default(MediaCarouselController mediaCarouselController, String str, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            z2 = true;
        }
        return mediaCarouselController.removePlayer(str, z, z2);
    }

    public static /* synthetic */ void reorderAllPlayers$default(MediaCarouselController mediaCarouselController, MediaPlayerData.MediaSortKey mediaSortKey, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        mediaCarouselController.reorderAllPlayers(mediaSortKey, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:139:0x0134, code lost:
        if (r0 == null) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x032b, code lost:
        if (r0 == null) goto L104;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean addOrUpdatePlayer(String str, String str2, MediaData mediaData, boolean z) {
        String str3;
        TransitionLayout player;
        MediaPlayerData mediaPlayerData;
        String str4;
        TransitionLayout player2;
        if (!Trace.isTagEnabled(4096L)) {
            MediaPlayerData mediaPlayerData2 = MediaPlayerData.INSTANCE;
            MediaPlayerData.moveIfExists$default(mediaPlayerData2, str2, str, null, 4, null);
            MediaControlPanel mediaPlayer = mediaPlayerData2.getMediaPlayer(str);
            MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(mediaPlayerData2.visiblePlayerKeys(), this.mediaCarouselScrollHandler.getVisibleMediaIndex());
            if (mediaPlayer == null) {
                MediaControlPanel mediaControlPanel = (MediaControlPanel) this.mediaControlPanelFactory.get();
                mediaControlPanel.attachPlayer(MediaViewHolder.Companion.create(LayoutInflater.from(this.context), this.mediaContent));
                mediaControlPanel.getMediaViewController().setSizeChangedListener(new MediaCarouselController$addOrUpdatePlayer$1$1(this));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                MediaViewHolder mediaViewHolder = mediaControlPanel.getMediaViewHolder();
                if (mediaViewHolder != null && (player = mediaViewHolder.getPlayer()) != null) {
                    player.setLayoutParams(layoutParams);
                }
                mediaControlPanel.bindPlayer(mediaData, str);
                mediaControlPanel.setListening(this.currentlyExpanded);
                mediaPlayerData2.addMediaPlayer(str, mediaData, mediaControlPanel, this.systemClock, z, this.debugLogger);
                updatePlayerToState(mediaControlPanel, true);
                if (!(this.shouldScrollToKey && Intrinsics.areEqual(mediaData.isPlaying(), Boolean.TRUE)) && (this.shouldScrollToKey || !mediaData.getActive())) {
                    this.needsReordering = true;
                } else {
                    reorderAllPlayers(mediaSortKey, str);
                }
            } else {
                mediaPlayer.bindPlayer(mediaData, str);
                mediaPlayerData2.addMediaPlayer(str, mediaData, mediaPlayer, this.systemClock, z, this.debugLogger);
                SmartspaceMediaData smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core = mediaPlayerData2.getSmartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                if (smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core != null) {
                    String packageName = smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getPackageName();
                    str3 = packageName;
                }
                str3 = new String();
                if (isReorderingAllowed() || (this.shouldScrollToKey && Intrinsics.areEqual(mediaData.isPlaying(), Boolean.TRUE) && Intrinsics.areEqual(str3, mediaData.getPackageName()))) {
                    reorderAllPlayers(mediaSortKey, str);
                } else {
                    this.needsReordering = true;
                }
            }
            updatePageIndicator();
            this.mediaCarouselScrollHandler.onPlayersChanged();
            UniqueObjectHostViewKt.setRequiresRemeasuring(this.mediaFrame, true);
            if (mediaPlayerData2.players().size() != this.mediaContent.getChildCount()) {
                int size = mediaPlayerData2.players().size();
                int childCount = this.mediaContent.getChildCount();
                Log.e("MediaCarouselController", "Size of players list and number of views in carousel are out of sync. Players size is " + size + ". View count is " + childCount + ".");
            }
            return mediaPlayer == null;
        }
        Trace.traceBegin(4096L, "MediaCarouselController#addOrUpdatePlayer");
        try {
            mediaPlayerData = MediaPlayerData.INSTANCE;
        } catch (Throwable th) {
            th = th;
        }
        try {
            MediaPlayerData.moveIfExists$default(mediaPlayerData, str2, str, null, 4, null);
            MediaControlPanel mediaPlayer2 = mediaPlayerData.getMediaPlayer(str);
            MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(mediaPlayerData.visiblePlayerKeys(), this.mediaCarouselScrollHandler.getVisibleMediaIndex());
            if (mediaPlayer2 == null) {
                MediaControlPanel mediaControlPanel2 = (MediaControlPanel) this.mediaControlPanelFactory.get();
                mediaControlPanel2.attachPlayer(MediaViewHolder.Companion.create(LayoutInflater.from(this.context), this.mediaContent));
                mediaControlPanel2.getMediaViewController().setSizeChangedListener(new MediaCarouselController$addOrUpdatePlayer$1$1(this));
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
                MediaViewHolder mediaViewHolder2 = mediaControlPanel2.getMediaViewHolder();
                if (mediaViewHolder2 != null && (player2 = mediaViewHolder2.getPlayer()) != null) {
                    player2.setLayoutParams(layoutParams2);
                }
                mediaControlPanel2.bindPlayer(mediaData, str);
                mediaControlPanel2.setListening(this.currentlyExpanded);
                mediaPlayerData.addMediaPlayer(str, mediaData, mediaControlPanel2, this.systemClock, z, this.debugLogger);
                updatePlayerToState(mediaControlPanel2, true);
                if (!(this.shouldScrollToKey && Intrinsics.areEqual(mediaData.isPlaying(), Boolean.TRUE)) && (this.shouldScrollToKey || !mediaData.getActive())) {
                    this.needsReordering = true;
                } else {
                    reorderAllPlayers(mediaSortKey2, str);
                }
            } else {
                mediaPlayer2.bindPlayer(mediaData, str);
                mediaPlayerData.addMediaPlayer(str, mediaData, mediaPlayer2, this.systemClock, z, this.debugLogger);
                SmartspaceMediaData smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 = mediaPlayerData.getSmartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                if (smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 != null) {
                    String packageName2 = smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core2.getPackageName();
                    str4 = packageName2;
                }
                str4 = new String();
                if (!isReorderingAllowed() && (!this.shouldScrollToKey || !Intrinsics.areEqual(mediaData.isPlaying(), Boolean.TRUE) || !Intrinsics.areEqual(str4, mediaData.getPackageName()))) {
                    this.needsReordering = true;
                }
                reorderAllPlayers(mediaSortKey2, str);
            }
            updatePageIndicator();
            this.mediaCarouselScrollHandler.onPlayersChanged();
            UniqueObjectHostViewKt.setRequiresRemeasuring(this.mediaFrame, true);
            if (mediaPlayerData.players().size() != this.mediaContent.getChildCount()) {
                int size2 = mediaPlayerData.players().size();
                int childCount2 = this.mediaContent.getChildCount();
                Log.e("MediaCarouselController", "Size of players list and number of views in carousel are out of sync. Players size is " + size2 + ". View count is " + childCount2 + ".");
            }
            boolean z2 = mediaPlayer2 == null;
            Trace.traceEnd(4096L);
            return z2;
        } catch (Throwable th2) {
            th = th2;
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final void addSmartspaceMediaRecommendations(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        boolean z2;
        TransitionLayout recommendations;
        boolean z3;
        TransitionLayout recommendations2;
        if (!Trace.isTagEnabled(4096L)) {
            z2 = MediaCarouselControllerKt.DEBUG;
            if (z2) {
                Log.d("MediaCarouselController", "Updating smartspace target in carousel");
            }
            MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
            if (mediaPlayerData.getMediaPlayer(str) != null) {
                Log.w("MediaCarouselController", "Skip adding smartspace target in carousel");
                return;
            }
            String smartspaceMediaKey = mediaPlayerData.smartspaceMediaKey();
            if (smartspaceMediaKey != null && removePlayer$default(this, smartspaceMediaKey, false, false, 4, null) != null) {
                this.debugLogger.logPotentialMemoryLeak(smartspaceMediaKey);
            }
            MediaControlPanel mediaControlPanel = (MediaControlPanel) this.mediaControlPanelFactory.get();
            mediaControlPanel.attachRecommendation(RecommendationViewHolder.Companion.create(LayoutInflater.from(this.context), this.mediaContent));
            mediaControlPanel.getMediaViewController().setSizeChangedListener(new MediaCarouselController$addSmartspaceMediaRecommendations$1$2(this));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            RecommendationViewHolder recommendationViewHolder = mediaControlPanel.getRecommendationViewHolder();
            if (recommendationViewHolder != null && (recommendations = recommendationViewHolder.getRecommendations()) != null) {
                recommendations.setLayoutParams(layoutParams);
            }
            mediaControlPanel.bindRecommendation(smartspaceMediaData);
            mediaPlayerData.addMediaRecommendation(str, smartspaceMediaData, mediaControlPanel, z, this.systemClock, this.debugLogger);
            updatePlayerToState(mediaControlPanel, true);
            reorderAllPlayers$default(this, (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(mediaPlayerData.visiblePlayerKeys(), this.mediaCarouselScrollHandler.getVisibleMediaIndex()), null, 2, null);
            updatePageIndicator();
            UniqueObjectHostViewKt.setRequiresRemeasuring(this.mediaFrame, true);
            if (mediaPlayerData.players().size() != this.mediaContent.getChildCount()) {
                int size = mediaPlayerData.players().size();
                int childCount = this.mediaContent.getChildCount();
                Log.e("MediaCarouselController", "Size of players list and number of views in carousel are out of sync. Players size is " + size + ". View count is " + childCount + ".");
                return;
            }
            return;
        }
        Trace.traceBegin(4096L, "MediaCarouselController#addSmartspaceMediaRecommendations");
        try {
            z3 = MediaCarouselControllerKt.DEBUG;
            if (z3) {
                Log.d("MediaCarouselController", "Updating smartspace target in carousel");
            }
            MediaPlayerData mediaPlayerData2 = MediaPlayerData.INSTANCE;
            if (mediaPlayerData2.getMediaPlayer(str) != null) {
                Log.w("MediaCarouselController", "Skip adding smartspace target in carousel");
                Trace.traceEnd(4096L);
                return;
            }
            String smartspaceMediaKey2 = mediaPlayerData2.smartspaceMediaKey();
            if (smartspaceMediaKey2 != null) {
                try {
                    if (removePlayer$default(this, smartspaceMediaKey2, false, false, 4, null) != null) {
                        this.debugLogger.logPotentialMemoryLeak(smartspaceMediaKey2);
                    }
                } catch (Throwable th) {
                    th = th;
                    Trace.traceEnd(4096L);
                    throw th;
                }
            }
            MediaControlPanel mediaControlPanel2 = (MediaControlPanel) this.mediaControlPanelFactory.get();
            mediaControlPanel2.attachRecommendation(RecommendationViewHolder.Companion.create(LayoutInflater.from(this.context), this.mediaContent));
            mediaControlPanel2.getMediaViewController().setSizeChangedListener(new MediaCarouselController$addSmartspaceMediaRecommendations$1$2(this));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
            RecommendationViewHolder recommendationViewHolder2 = mediaControlPanel2.getRecommendationViewHolder();
            if (recommendationViewHolder2 != null && (recommendations2 = recommendationViewHolder2.getRecommendations()) != null) {
                recommendations2.setLayoutParams(layoutParams2);
            }
            mediaControlPanel2.bindRecommendation(smartspaceMediaData);
            mediaPlayerData2.addMediaRecommendation(str, smartspaceMediaData, mediaControlPanel2, z, this.systemClock, this.debugLogger);
            updatePlayerToState(mediaControlPanel2, true);
            reorderAllPlayers$default(this, (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(mediaPlayerData2.visiblePlayerKeys(), this.mediaCarouselScrollHandler.getVisibleMediaIndex()), null, 2, null);
            updatePageIndicator();
            UniqueObjectHostViewKt.setRequiresRemeasuring(this.mediaFrame, true);
            if (mediaPlayerData2.players().size() != this.mediaContent.getChildCount()) {
                int size2 = mediaPlayerData2.players().size();
                int childCount2 = this.mediaContent.getChildCount();
                Log.e("MediaCarouselController", "Size of players list and number of views in carousel are out of sync. Players size is " + size2 + ". View count is " + childCount2 + ".");
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void closeGuts(boolean z) {
        for (MediaControlPanel mediaControlPanel : MediaPlayerData.INSTANCE.players()) {
            mediaControlPanel.closeGuts(z);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("keysNeedRemoval: " + this.keysNeedRemoval);
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        printWriter.println("dataKeys: " + mediaPlayerData.dataKeys());
        printWriter.println("orderedPlayerSortKeys: " + mediaPlayerData.playerKeys());
        printWriter.println("visiblePlayerSortKeys: " + mediaPlayerData.visiblePlayerKeys());
        printWriter.println("smartspaceMediaData: " + mediaPlayerData.getSmartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
        printWriter.println("shouldPrioritizeSs: " + mediaPlayerData.getShouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
        printWriter.println("current size: " + this.currentCarouselWidth + " x " + this.currentCarouselHeight);
        int i = this.desiredLocation;
        StringBuilder sb = new StringBuilder();
        sb.append("location: ");
        sb.append(i);
        printWriter.println(sb.toString());
        MediaHostState mediaHostState = this.desiredHostState;
        Boolean bool = null;
        Float valueOf = mediaHostState != null ? Float.valueOf(mediaHostState.getExpansion()) : null;
        MediaHostState mediaHostState2 = this.desiredHostState;
        if (mediaHostState2 != null) {
            bool = Boolean.valueOf(mediaHostState2.getShowsOnlyActiveMedia());
        }
        printWriter.println("state: " + valueOf + ", only active " + bool);
    }

    public final MediaCarouselScrollHandler getMediaCarouselScrollHandler() {
        return this.mediaCarouselScrollHandler;
    }

    public final ViewGroup getMediaFrame() {
        return this.mediaFrame;
    }

    public final View getSettingsButton() {
        View view = this.settingsButton;
        if (view != null) {
            return view;
        }
        return null;
    }

    public final Function0<Unit> getUpdateHostVisibility() {
        Function0<Unit> function0 = this.updateHostVisibility;
        if (function0 != null) {
            return function0;
        }
        return null;
    }

    public final Function0<Unit> getUpdateUserVisibility() {
        Function0<Unit> function0 = this.updateUserVisibility;
        if (function0 != null) {
            return function0;
        }
        return null;
    }

    public final ViewGroup inflateMediaCarousel() {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.context).inflate(R$layout.media_carousel, (ViewGroup) new UniqueObjectHostView(this.context), false);
        viewGroup.setLayoutDirection(3);
        return viewGroup;
    }

    public final void inflateSettingsButton() {
        View inflate = LayoutInflater.from(this.context).inflate(R$layout.media_carousel_settings_button, this.mediaFrame, false);
        if (this.settingsButton != null) {
            this.mediaFrame.removeView(getSettingsButton());
        }
        this.settingsButton = inflate;
        this.mediaFrame.addView(getSettingsButton());
        this.mediaCarouselScrollHandler.onSettingsButtonUpdated(inflate);
        getSettingsButton().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.MediaCarouselController$inflateSettingsButton$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Intent intent;
                MediaCarouselController.access$getLogger$p(MediaCarouselController.this).logCarouselSettings();
                ActivityStarter access$getActivityStarter$p = MediaCarouselController.access$getActivityStarter$p(MediaCarouselController.this);
                intent = MediaCarouselControllerKt.settingsIntent;
                access$getActivityStarter$p.startActivity(intent, true);
            }
        });
    }

    public final boolean isReorderingAllowed() {
        return this.visualStabilityProvider.isReorderingAllowed();
    }

    public final void logSmartspaceCardReported(int i, int i2, int i3, int[] iArr, int i4, int i5) {
        logSmartspaceCardReported$default(this, i, i2, i3, iArr, i4, i5, 0, 0, false, 448, null);
    }

    public final void logSmartspaceCardReported(int i, int i2, int i3, int[] iArr, int i4, int i5, int i6, int i7, boolean z) {
        boolean z2;
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        if (mediaPlayerData.players().size() <= i6) {
            return;
        }
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAt(mediaPlayerData.visiblePlayerKeys(), i6);
        if (mediaSortKey.isSsMediaRec() || this.mediaManager.getSmartspaceMediaData().isActive() || mediaPlayerData.getSmartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core() != null) {
            int childCount = this.mediaContent.getChildCount();
            for (int i8 : iArr) {
                SysUiStatsLog.write(352, i, i2, 0, i8, z ? -1 : i6, childCount, mediaSortKey.isSsMediaRec() ? 15 : mediaSortKey.isSsReactivated() ? 43 : 31, i3, i4, i5, i7, (byte[]) null, (byte[]) null);
                z2 = MediaCarouselControllerKt.DEBUG;
                if (z2) {
                    Log.d("MediaCarouselController", "Log Smartspace card event id: " + i + " instance id: " + i2 + " surface: " + i8 + " rank: " + i6 + " cardinality: " + childCount + " isRecommendationCard: " + mediaSortKey.isSsMediaRec() + " isSsReactivated: " + mediaSortKey.isSsReactivated() + "uid: " + i3 + " interactedSubcardRank: " + i4 + " interactedSubcardCardinality: " + i5 + " received_latency_millis: " + i7);
                }
            }
        }
    }

    public final void logSmartspaceImpression(boolean z) {
        int visibleMediaIndex = this.mediaCarouselScrollHandler.getVisibleMediaIndex();
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        if (mediaPlayerData.players().size() > visibleMediaIndex) {
            MediaControlPanel mediaControlPanel = mediaPlayerData.getMediaControlPanel(visibleMediaIndex);
            if ((mediaPlayerData.hasActiveMediaOrRecommendationCard() || z) && mediaControlPanel != null) {
                logSmartspaceCardReported$default(this, 800, mediaControlPanel.mSmartspaceId, mediaControlPanel.mUid, new int[]{mediaControlPanel.getSurfaceForSmartspaceLogging()}, 0, 0, 0, 0, false, 496, null);
                mediaControlPanel.mIsImpressed = true;
            }
        }
    }

    public final void maybeResetSettingsCog() {
        Map<Integer, MediaHostState> mediaHostStates = this.mediaHostStatesManager.getMediaHostStates();
        MediaHostState mediaHostState = mediaHostStates.get(Integer.valueOf(this.currentEndLocation));
        boolean showsOnlyActiveMedia = mediaHostState != null ? mediaHostState.getShowsOnlyActiveMedia() : true;
        MediaHostState mediaHostState2 = mediaHostStates.get(Integer.valueOf(this.currentStartLocation));
        boolean showsOnlyActiveMedia2 = mediaHostState2 != null ? mediaHostState2.getShowsOnlyActiveMedia() : showsOnlyActiveMedia;
        if (this.currentlyShowingOnlyActive == showsOnlyActiveMedia) {
            float f = this.currentTransitionProgress;
            if (f == 1.0f) {
                return;
            }
            boolean z = false;
            if (f == ActionBarShadowController.ELEVATION_LOW) {
                z = true;
            }
            if (z || showsOnlyActiveMedia2 == showsOnlyActiveMedia) {
                return;
            }
        }
        this.currentlyShowingOnlyActive = showsOnlyActiveMedia;
        this.mediaCarouselScrollHandler.resetTranslation(true);
    }

    public final Unit onDesiredLocationChanged(int i, MediaHostState mediaHostState, boolean z, long j, long j2) {
        Unit unit = null;
        if (Trace.isTagEnabled(4096L)) {
            Trace.traceBegin(4096L, "MediaCarouselController#onDesiredLocationChanged");
            if (mediaHostState != null) {
                try {
                    if (this.desiredLocation != i) {
                        this.logger.logCarouselPosition(i);
                    }
                    this.desiredLocation = i;
                    this.desiredHostState = mediaHostState;
                    setCurrentlyExpanded(mediaHostState.getExpansion() > ActionBarShadowController.ELEVATION_LOW);
                    boolean z2 = (this.currentlyExpanded || this.mediaManager.hasActiveMediaOrRecommendation() || !mediaHostState.getShowsOnlyActiveMedia()) ? false : true;
                    for (MediaControlPanel mediaControlPanel : MediaPlayerData.INSTANCE.players()) {
                        if (z) {
                            mediaControlPanel.getMediaViewController().animatePendingStateChange(j, j2);
                        }
                        if (z2 && mediaControlPanel.getMediaViewController().isGutsVisible()) {
                            mediaControlPanel.closeGuts(!z);
                        }
                        mediaControlPanel.getMediaViewController().onLocationPreChange(i);
                    }
                    this.mediaCarouselScrollHandler.setShowsSettingsButton(!mediaHostState.getShowsOnlyActiveMedia());
                    this.mediaCarouselScrollHandler.setFalsingProtectionNeeded(mediaHostState.getFalsingProtectionNeeded());
                    boolean visible = mediaHostState.getVisible();
                    if (visible != this.playersVisible) {
                        this.playersVisible = visible;
                        if (visible) {
                            MediaCarouselScrollHandler.resetTranslation$default(this.mediaCarouselScrollHandler, false, 1, null);
                        }
                    }
                    updateCarouselSize();
                    unit = Unit.INSTANCE;
                } finally {
                    Trace.traceEnd(4096L);
                }
            }
        } else {
            unit = null;
            if (mediaHostState != null) {
                if (this.desiredLocation != i) {
                    this.logger.logCarouselPosition(i);
                }
                this.desiredLocation = i;
                this.desiredHostState = mediaHostState;
                setCurrentlyExpanded(mediaHostState.getExpansion() > ActionBarShadowController.ELEVATION_LOW);
                boolean z3 = (this.currentlyExpanded || this.mediaManager.hasActiveMediaOrRecommendation() || !mediaHostState.getShowsOnlyActiveMedia()) ? false : true;
                for (MediaControlPanel mediaControlPanel2 : MediaPlayerData.INSTANCE.players()) {
                    if (z) {
                        mediaControlPanel2.getMediaViewController().animatePendingStateChange(j, j2);
                    }
                    if (z3 && mediaControlPanel2.getMediaViewController().isGutsVisible()) {
                        mediaControlPanel2.closeGuts(!z);
                    }
                    mediaControlPanel2.getMediaViewController().onLocationPreChange(i);
                }
                this.mediaCarouselScrollHandler.setShowsSettingsButton(!mediaHostState.getShowsOnlyActiveMedia());
                this.mediaCarouselScrollHandler.setFalsingProtectionNeeded(mediaHostState.getFalsingProtectionNeeded());
                boolean visible2 = mediaHostState.getVisible();
                if (visible2 != this.playersVisible) {
                    this.playersVisible = visible2;
                    if (visible2) {
                        MediaCarouselScrollHandler.resetTranslation$default(this.mediaCarouselScrollHandler, false, 1, null);
                    }
                }
                updateCarouselSize();
                unit = Unit.INSTANCE;
            }
        }
        return unit;
    }

    public final void onSwipeToDismiss() {
        int i = 0;
        for (Object obj : MediaPlayerData.INSTANCE.players()) {
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            MediaControlPanel mediaControlPanel = (MediaControlPanel) obj;
            if (mediaControlPanel.mIsImpressed) {
                logSmartspaceCardReported$default(this, 761, mediaControlPanel.mSmartspaceId, mediaControlPanel.mUid, new int[]{mediaControlPanel.getSurfaceForSmartspaceLogging()}, 0, 0, i, 0, true, 176, null);
                mediaControlPanel.mIsImpressed = false;
            }
            i++;
        }
        this.logger.logSwipeDismiss();
        this.mediaManager.onSwipeToDismiss();
    }

    public final MediaControlPanel removePlayer(String str, boolean z, boolean z2) {
        MediaControlPanel mediaControlPanel;
        SmartspaceMediaData smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core;
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        if (Intrinsics.areEqual(str, mediaPlayerData.smartspaceMediaKey()) && (smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core = mediaPlayerData.getSmartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) != null) {
            this.logger.logRecommendationRemoved(smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getPackageName(), smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getInstanceId());
        }
        MediaControlPanel removeMediaPlayer = mediaPlayerData.removeMediaPlayer(str, z || z2);
        if (removeMediaPlayer != null) {
            this.mediaCarouselScrollHandler.onPrePlayerRemoved(removeMediaPlayer);
            ViewGroup viewGroup = this.mediaContent;
            MediaViewHolder mediaViewHolder = removeMediaPlayer.getMediaViewHolder();
            viewGroup.removeView(mediaViewHolder != null ? mediaViewHolder.getPlayer() : null);
            ViewGroup viewGroup2 = this.mediaContent;
            RecommendationViewHolder recommendationViewHolder = removeMediaPlayer.getRecommendationViewHolder();
            TransitionLayout transitionLayout = null;
            if (recommendationViewHolder != null) {
                transitionLayout = recommendationViewHolder.getRecommendations();
            }
            viewGroup2.removeView(transitionLayout);
            removeMediaPlayer.onDestroy();
            this.mediaCarouselScrollHandler.onPlayersChanged();
            updatePageIndicator();
            if (z) {
                this.mediaManager.dismissMediaData(str, 0L);
            }
            mediaControlPanel = removeMediaPlayer;
            if (z2) {
                this.mediaManager.dismissSmartspaceRecommendation(str, 0L);
                mediaControlPanel = removeMediaPlayer;
            }
        } else {
            mediaControlPanel = null;
        }
        return mediaControlPanel;
    }

    public final void reorderAllPlayers(MediaPlayerData.MediaSortKey mediaSortKey, String str) {
        Unit unit;
        int i;
        this.mediaContent.removeAllViews();
        for (MediaControlPanel mediaControlPanel : MediaPlayerData.INSTANCE.players()) {
            MediaViewHolder mediaViewHolder = mediaControlPanel.getMediaViewHolder();
            if (mediaViewHolder != null) {
                this.mediaContent.addView(mediaViewHolder.getPlayer());
            } else {
                RecommendationViewHolder recommendationViewHolder = mediaControlPanel.getRecommendationViewHolder();
                if (recommendationViewHolder != null) {
                    this.mediaContent.addView(recommendationViewHolder.getRecommendations());
                }
            }
        }
        this.mediaCarouselScrollHandler.onPlayersChanged();
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        mediaPlayerData.updateVisibleMediaPlayers();
        if (this.shouldScrollToKey) {
            this.shouldScrollToKey = false;
            int mediaPlayerIndex = str != null ? mediaPlayerData.getMediaPlayerIndex(str) : -1;
            if (mediaPlayerIndex != -1) {
                if (mediaSortKey != null) {
                    Iterator<T> it = mediaPlayerData.playerKeys().iterator();
                    int i2 = 0;
                    while (true) {
                        i = -1;
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        if (i2 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        if (Intrinsics.areEqual(mediaSortKey, (MediaPlayerData.MediaSortKey) next)) {
                            i = i2;
                            break;
                        }
                        i2++;
                    }
                    this.mediaCarouselScrollHandler.scrollToPlayer(i, mediaPlayerIndex);
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                if (unit == null) {
                    MediaCarouselScrollHandler.scrollToPlayer$default(this.mediaCarouselScrollHandler, 0, mediaPlayerIndex, 1, null);
                }
            }
        }
    }

    public final void setCurrentState(int i, int i2, float f, boolean z) {
        if (i == this.currentStartLocation && i2 == this.currentEndLocation) {
            if ((f == this.currentTransitionProgress) && !z) {
                return;
            }
        }
        this.currentStartLocation = i;
        this.currentEndLocation = i2;
        this.currentTransitionProgress = f;
        for (MediaControlPanel mediaControlPanel : MediaPlayerData.INSTANCE.players()) {
            updatePlayerToState(mediaControlPanel, z);
        }
        maybeResetSettingsCog();
        updatePageIndicatorAlpha();
    }

    public final void setCurrentlyExpanded(boolean z) {
        if (this.currentlyExpanded != z) {
            this.currentlyExpanded = z;
            for (MediaControlPanel mediaControlPanel : MediaPlayerData.INSTANCE.players()) {
                mediaControlPanel.setListening(this.currentlyExpanded);
            }
        }
    }

    public final void setRtl(boolean z) {
        if (z != this.isRtl) {
            this.isRtl = z;
            this.mediaFrame.setLayoutDirection(z ? 1 : 0);
            this.mediaCarouselScrollHandler.scrollToStart();
        }
    }

    public final void setShouldScrollToKey(boolean z) {
        this.shouldScrollToKey = z;
    }

    public final void setUpdateHostVisibility(Function0<Unit> function0) {
        this.updateHostVisibility = function0;
    }

    public final void setUpdateUserVisibility(Function0<Unit> function0) {
        this.updateUserVisibility = function0;
    }

    public final void updateCarouselDimensions() {
        int i;
        Iterator<MediaControlPanel> it = MediaPlayerData.INSTANCE.players().iterator();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = i3;
            if (!it.hasNext()) {
                break;
            }
            MediaViewController mediaViewController = it.next().getMediaViewController();
            i2 = Math.max(i2, mediaViewController.getCurrentWidth() + ((int) mediaViewController.getTranslationX()));
            i3 = Math.max(i, mediaViewController.getCurrentHeight() + ((int) mediaViewController.getTranslationY()));
        }
        if (i2 == this.currentCarouselWidth && i == this.currentCarouselHeight) {
            return;
        }
        this.currentCarouselWidth = i2;
        this.currentCarouselHeight = i;
        this.mediaCarouselScrollHandler.setCarouselBounds(i2, i);
        updatePageIndicatorLocation();
        updatePageIndicatorAlpha();
    }

    public final void updateCarouselSize() {
        MeasurementInput measurementInput;
        MeasurementInput measurementInput2;
        MeasurementInput measurementInput3;
        MeasurementInput measurementInput4;
        MediaHostState mediaHostState = this.desiredHostState;
        int width = (mediaHostState == null || (measurementInput4 = mediaHostState.getMeasurementInput()) == null) ? 0 : measurementInput4.getWidth();
        MediaHostState mediaHostState2 = this.desiredHostState;
        int height = (mediaHostState2 == null || (measurementInput3 = mediaHostState2.getMeasurementInput()) == null) ? 0 : measurementInput3.getHeight();
        if ((width == this.carouselMeasureWidth || width == 0) && (height == this.carouselMeasureHeight || height == 0)) {
            return;
        }
        this.carouselMeasureWidth = width;
        this.carouselMeasureHeight = height;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R$dimen.qs_media_padding);
        MediaHostState mediaHostState3 = this.desiredHostState;
        int widthMeasureSpec = (mediaHostState3 == null || (measurementInput2 = mediaHostState3.getMeasurementInput()) == null) ? 0 : measurementInput2.getWidthMeasureSpec();
        MediaHostState mediaHostState4 = this.desiredHostState;
        this.mediaCarousel.measure(widthMeasureSpec, (mediaHostState4 == null || (measurementInput = mediaHostState4.getMeasurementInput()) == null) ? 0 : measurementInput.getHeightMeasureSpec());
        MediaScrollView mediaScrollView = this.mediaCarousel;
        mediaScrollView.layout(0, 0, width, mediaScrollView.getMeasuredHeight());
        this.mediaCarouselScrollHandler.setPlayerWidthPlusPadding(dimensionPixelSize + width);
    }

    public final void updatePageIndicator() {
        int childCount = this.mediaContent.getChildCount();
        this.pageIndicator.setNumPages(childCount);
        if (childCount == 1) {
            this.pageIndicator.setLocation(ActionBarShadowController.ELEVATION_LOW);
        }
        updatePageIndicatorAlpha();
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00a4, code lost:
        if (r11 == false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePageIndicatorAlpha() {
        float lerp;
        Map<Integer, MediaHostState> mediaHostStates = this.mediaHostStatesManager.getMediaHostStates();
        MediaHostState mediaHostState = mediaHostStates.get(Integer.valueOf(this.currentEndLocation));
        boolean z = false;
        boolean visible = mediaHostState != null ? mediaHostState.getVisible() : false;
        MediaHostState mediaHostState2 = mediaHostStates.get(Integer.valueOf(this.currentStartLocation));
        if (mediaHostState2 != null) {
            z = mediaHostState2.getVisible();
        }
        float f = z ? 1.0f : 0.0f;
        MediaHostState mediaHostState3 = mediaHostStates.get(Integer.valueOf(this.currentEndLocation));
        float squishFraction = mediaHostState3 != null ? mediaHostState3.getSquishFraction() : 1.0f;
        float f2 = visible ? 1.0f : 0.0f;
        float calculateAlpha = Companion.calculateAlpha(squishFraction, 1900.0f, 167.0f);
        if (visible) {
            lerp = 1.0f;
        }
        float f3 = this.currentTransitionProgress;
        float f4 = f3;
        if (!visible) {
            f4 = 1.0f - f3;
        }
        lerp = MathUtils.lerp(f, f2 * calculateAlpha, MathUtils.constrain(MathUtils.map(0.95f, 1.0f, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f, f4), (float) ActionBarShadowController.ELEVATION_LOW, 1.0f));
        this.pageIndicator.setAlpha(lerp);
    }

    public final void updatePageIndicatorLocation() {
        int i;
        int width;
        if (this.isRtl) {
            i = this.pageIndicator.getWidth();
            width = this.currentCarouselWidth;
        } else {
            i = this.currentCarouselWidth;
            width = this.pageIndicator.getWidth();
        }
        this.pageIndicator.setTranslationX(((i - width) / 2.0f) + this.mediaCarouselScrollHandler.getContentTranslation());
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.pageIndicator.getLayoutParams();
        PageIndicator pageIndicator = this.pageIndicator;
        pageIndicator.setTranslationY((this.currentCarouselHeight - pageIndicator.getHeight()) - marginLayoutParams.bottomMargin);
    }

    public final void updatePlayerToState(MediaControlPanel mediaControlPanel, boolean z) {
        mediaControlPanel.getMediaViewController().setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, z);
    }

    public final void updatePlayers(boolean z) {
        this.pageIndicator.setTintList(ColorStateList.valueOf(this.context.getColor(R$color.media_paging_indicator)));
        Iterator<T> it = MediaPlayerData.INSTANCE.mediaData().iterator();
        while (it.hasNext()) {
            Triple triple = (Triple) it.next();
            String str = (String) triple.component1();
            MediaData mediaData = (MediaData) triple.component2();
            if (((Boolean) triple.component3()).booleanValue()) {
                MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
                SmartspaceMediaData smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core = mediaPlayerData.getSmartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                removePlayer(str, false, false);
                if (smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core != null) {
                    addSmartspaceMediaRecommendations(smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getTargetId(), smartspaceMediaData$frameworks__base__packages__SystemUI__android_common__SystemUI_core, mediaPlayerData.getShouldPrioritizeSs$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
                }
            } else {
                boolean isSsReactivated = MediaPlayerData.INSTANCE.isSsReactivated(str);
                if (z) {
                    removePlayer(str, false, false);
                }
                addOrUpdatePlayer(str, null, mediaData, isSsReactivated);
            }
        }
    }
}