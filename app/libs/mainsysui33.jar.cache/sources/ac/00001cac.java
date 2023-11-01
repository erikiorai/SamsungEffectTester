package com.android.systemui.media.controls.ui;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.PendingIntent;
import android.app.WallpaperColors;
import android.app.smartspace.SmartspaceAction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.logging.InstanceId;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.settingslib.widget.AdaptiveIcon;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.R$anim;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.bluetooth.BroadcastDialogController;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.models.GutsViewHolder;
import com.android.systemui.media.controls.models.player.MediaAction;
import com.android.systemui.media.controls.models.player.MediaButton;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import com.android.systemui.media.controls.models.player.MediaViewHolder;
import com.android.systemui.media.controls.models.player.SeekBarObserver;
import com.android.systemui.media.controls.models.player.SeekBarViewModel;
import com.android.systemui.media.controls.models.recommendation.RecommendationViewHolder;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.MediaViewController;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.media.controls.util.SmallHash;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.surfaceeffects.ripple.MultiRippleController;
import com.android.systemui.surfaceeffects.ripple.RippleAnimation;
import com.android.systemui.surfaceeffects.ripple.RippleAnimationConfig;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.time.SystemClock;
import dagger.Lazy;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaControlPanel.class */
public class MediaControlPanel {
    public static final List<Integer> SEMANTIC_ACTIONS_ALL;
    public static final List<Integer> SEMANTIC_ACTIONS_COMPACT;
    public static final List<Integer> SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
    public static final Intent SETTINGS_INTENT = new Intent("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    public final ActivityIntentHelper mActivityIntentHelper;
    public final ActivityStarter mActivityStarter;
    public final Executor mBackgroundExecutor;
    public final BroadcastDialogController mBroadcastDialogController;
    public final BroadcastSender mBroadcastSender;
    public ColorSchemeTransition mColorSchemeTransition;
    public Context mContext;
    public MediaController mController;
    public final FalsingManager mFalsingManager;
    public FeatureFlags mFeatureFlags;
    public InstanceId mInstanceId;
    public String mKey;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public MediaUiEventLogger mLogger;
    public final Executor mMainExecutor;
    public MediaCarouselController mMediaCarouselController;
    public MediaData mMediaData;
    public Lazy<MediaDataManager> mMediaDataManagerLazy;
    public final MediaOutputDialogFactory mMediaOutputDialogFactory;
    public MediaViewController mMediaViewController;
    public MediaViewHolder mMediaViewHolder;
    public MetadataAnimationHandler mMetadataAnimationHandler;
    public MultiRippleController mMultiRippleController;
    public String mPackageName;
    public SmartspaceMediaData mRecommendationData;
    public RecommendationViewHolder mRecommendationViewHolder;
    public SeekBarObserver mSeekBarObserver;
    public final SeekBarViewModel mSeekBarViewModel;
    public int mSmartspaceMediaItemsCount;
    public String mSwitchBroadcastApp;
    public SystemClock mSystemClock;
    public MediaSession.Token mToken;
    public TurbulenceNoiseController mTurbulenceNoiseController;
    public int mUid = -1;
    public Drawable mPrevArtwork = null;
    public boolean mIsArtworkBound = false;
    public int mArtworkBoundId = 0;
    public int mArtworkNextBindRequestId = 0;
    public boolean mIsImpressed = false;
    public int mSmartspaceId = -1;
    public boolean mIsScrubbing = false;
    public boolean mIsSeekBarEnabled = false;
    public final SeekBarViewModel.ScrubbingChangeListener mScrubbingChangeListener = new SeekBarViewModel.ScrubbingChangeListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda0
        @Override // com.android.systemui.media.controls.models.player.SeekBarViewModel.ScrubbingChangeListener
        public final void onScrubbingChanged(boolean z) {
            MediaControlPanel.$r8$lambda$NvWeoMt0DfGoh_mXJH8hCldFig4(MediaControlPanel.this, z);
        }
    };
    public final SeekBarViewModel.EnabledChangeListener mEnabledChangeListener = new SeekBarViewModel.EnabledChangeListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda1
        @Override // com.android.systemui.media.controls.models.player.SeekBarViewModel.EnabledChangeListener
        public final void onEnabledChanged(boolean z) {
            MediaControlPanel.m3254$r8$lambda$SQwHpMBehkXR1TbSPoBMIhczks(MediaControlPanel.this, z);
        }
    };
    public boolean mIsCurrentBroadcastedApp = false;
    public boolean mShowBroadcastDialogButton = false;
    public TurbulenceNoiseAnimationConfig mTurbulenceNoiseAnimationConfig = null;
    @VisibleForTesting
    public MultiRippleController.Companion.RipplesFinishedListener mRipplesFinishedListener = null;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda21.run():void] */
    /* renamed from: $r8$lambda$-6IGV-IFDNeEBxNyTAc3Uw7IOEs */
    public static /* synthetic */ void m3249$r8$lambda$6IGVIFDNeEBxNyTAc3Uw7IOEs(MediaControlPanel mediaControlPanel, Drawable drawable) {
        mediaControlPanel.lambda$fetchAndUpdateRecommendationColors$22(drawable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda20.run():void] */
    /* renamed from: $r8$lambda$0NukvMTLJIyJxqYbIUQXt89m-6E */
    public static /* synthetic */ void m3250$r8$lambda$0NukvMTLJIyJxqYbIUQXt89m6E(MediaControlPanel mediaControlPanel, MediaData mediaData, int i, int i2, int i3, String str, int i4, boolean z) {
        mediaControlPanel.lambda$bindArtworkAndColors$12(mediaData, i, i2, i3, str, i4, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda16.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$4n2RskUNdC7o4chtF_Jn-UH9Ibs */
    public static /* synthetic */ void m3251$r8$lambda$4n2RskUNdC7o4chtF_JnUH9Ibs(MediaControlPanel mediaControlPanel, int i, SmartspaceAction smartspaceAction, View view, View view2) {
        mediaControlPanel.lambda$setSmartspaceRecItemOnClickListener$28(i, smartspaceAction, view, view2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda3.onLongClick(android.view.View):boolean] */
    public static /* synthetic */ boolean $r8$lambda$6G40Cb19Lm7SzSFcKyFDeC1Qgl8(MediaControlPanel mediaControlPanel, View view) {
        return mediaControlPanel.lambda$bindRecommendation$17(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda8.onRipplesFinish():void] */
    public static /* synthetic */ void $r8$lambda$ALhVXjmJ1Ar796nlShkkJk57E2I(MediaControlPanel mediaControlPanel) {
        mediaControlPanel.lambda$attachPlayer$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda4.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$AX21olSmepcLYAqhs8GI3Em19g0(MediaControlPanel mediaControlPanel, ConstraintSet constraintSet, boolean z, TextView textView) {
        mediaControlPanel.lambda$bindRecommendation$18(constraintSet, z, textView);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda24.run():void] */
    /* renamed from: $r8$lambda$IHIdvUztlt23By6k8JNCKvm-N6U */
    public static /* synthetic */ void m3252$r8$lambda$IHIdvUztlt23By6k8JNCKvmN6U(MediaControlPanel mediaControlPanel, ColorScheme colorScheme) {
        mediaControlPanel.lambda$fetchAndUpdateRecommendationColors$21(colorScheme);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda5.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$JW3izhAk4bFOcDlY5yJ3umyWTQc(MediaControlPanel mediaControlPanel, ConstraintSet constraintSet, boolean z, TextView textView) {
        mediaControlPanel.lambda$bindRecommendation$19(constraintSet, z, textView);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda17.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$Nt1BZ-R7Z4R4eeGheArAC35DuKg */
    public static /* synthetic */ void m3253$r8$lambda$Nt1BZR7Z4R4eeGheArAC35DuKg(MediaControlPanel mediaControlPanel, Runnable runnable, View view) {
        mediaControlPanel.lambda$bindGutsMenuCommon$25(runnable, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda0.onScrubbingChanged(boolean):void] */
    public static /* synthetic */ void $r8$lambda$NvWeoMt0DfGoh_mXJH8hCldFig4(MediaControlPanel mediaControlPanel, boolean z) {
        mediaControlPanel.setIsScrubbing(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda10.run():void] */
    public static /* synthetic */ void $r8$lambda$OD8aW8OrvX8ejJ3HTsRa1iK09Yg(MediaControlPanel mediaControlPanel, MediaController mediaController) {
        mediaControlPanel.lambda$bindPlayer$6(mediaController);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda1.onEnabledChanged(boolean):void] */
    /* renamed from: $r8$lambda$SQ-wHpMBehkXR1TbSPoBMIhczks */
    public static /* synthetic */ void m3254$r8$lambda$SQwHpMBehkXR1TbSPoBMIhczks(MediaControlPanel mediaControlPanel, boolean z) {
        mediaControlPanel.setIsSeekBarEnabled(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda14.run():void] */
    /* renamed from: $r8$lambda$Sapq8cLd-sTeZFqUaFVilCGH-S4 */
    public static /* synthetic */ void m3255$r8$lambda$Sapq8cLdsTeZFqUaFVilCGHS4(MediaControlPanel mediaControlPanel) {
        mediaControlPanel.lambda$setIsScrubbing$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda30.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$T5QGfL7Z2QSPsO7HgjCnaYw6fjg(int i, TextView textView) {
        textView.setTextColor(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda28.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$VHer1o9TEyP22P4VUvkhFC63w_8(MediaControlPanel mediaControlPanel, ImageButton imageButton, Runnable runnable, Drawable drawable, Drawable drawable2, View view) {
        mediaControlPanel.lambda$bindButtonCommon$14(imageButton, runnable, drawable, drawable2, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda6.run():void] */
    /* renamed from: $r8$lambda$WPo63CrRtUnrXEAWmNtP-om6CxE */
    public static /* synthetic */ void m3256$r8$lambda$WPo63CrRtUnrXEAWmNtPom6CxE(MediaControlPanel mediaControlPanel, SmartspaceMediaData smartspaceMediaData) {
        mediaControlPanel.lambda$bindRecommendation$20(smartspaceMediaData);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda19.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$X6UUI-RwHkImI1d39fKj3C_ikAw */
    public static /* synthetic */ void m3257$r8$lambda$X6UUIRwHkImI1d39fKj3C_ikAw(MediaControlPanel mediaControlPanel, View view) {
        mediaControlPanel.lambda$bindGutsMenuCommon$27(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda25.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$YUUoIrkfp2xVDX_Hq3RBqzrQM-U */
    public static /* synthetic */ boolean m3258$r8$lambda$YUUoIrkfp2xVDX_Hq3RBqzrQMU(MediaButton mediaButton, Integer num) {
        return lambda$scrubbingTimeViewsEnabled$16(mediaButton, num);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda15.run():void] */
    /* renamed from: $r8$lambda$_1ITg52Muw7weyzQ-cBZzUCXSkA */
    public static /* synthetic */ void m3259$r8$lambda$_1ITg52Muw7weyzQcBZzUCXSkA(MediaControlPanel mediaControlPanel, MediaData mediaData) {
        mediaControlPanel.lambda$bindGutsMenuForPlayer$8(mediaData);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda27.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$aNdyC2UolE0qFLvKQjC4v6z7PVk(MediaControlPanel mediaControlPanel, MediaButton mediaButton, Integer num) {
        mediaControlPanel.lambda$updateDisplayForScrubbingChange$15(mediaButton, num);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda2.invoke():java.lang.Object] */
    public static /* synthetic */ Unit $r8$lambda$dh28ZuqJIqoKsicZyyTtrXaKW5s(MediaControlPanel mediaControlPanel) {
        return mediaControlPanel.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda7.onLongClick(android.view.View):boolean] */
    public static /* synthetic */ boolean $r8$lambda$hbBVgJKJ1ULH5fHfQB2A9ffvks0(MediaControlPanel mediaControlPanel, View view) {
        return mediaControlPanel.lambda$attachPlayer$2(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda11.onLongClick(android.view.View):boolean] */
    public static /* synthetic */ boolean $r8$lambda$kzRDs_jdb06j0a1QwkeqaZy67Dc(MediaControlPanel mediaControlPanel, View view) {
        return mediaControlPanel.lambda$attachRecommendation$4(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda22.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$pNqAQX48fFL7_BoH9M3MI5ntGIY(MediaControlPanel mediaControlPanel, boolean z, MediaDeviceData mediaDeviceData, View view) {
        mediaControlPanel.lambda$bindOutputSwitcherAndBroadcastButton$7(z, mediaDeviceData, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda13.invoke():java.lang.Object] */
    public static /* synthetic */ Unit $r8$lambda$qeuWjuFz3K1TnGWl4_bMiIWdubQ(MediaControlPanel mediaControlPanel) {
        return mediaControlPanel.lambda$bindSongMetadata$10();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda12.invoke():java.lang.Object] */
    public static /* synthetic */ Unit $r8$lambda$sEMXk472q7QSoU7AqlKNRXulZgI(MediaControlPanel mediaControlPanel, TextView textView, MediaData mediaData, TextView textView2) {
        return mediaControlPanel.lambda$bindSongMetadata$9(textView, mediaData, textView2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda9.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$uGL7JuGhlMRKPpasR5UTJcqQUZ8(MediaControlPanel mediaControlPanel, PendingIntent pendingIntent, View view) {
        mediaControlPanel.lambda$bindPlayer$5(pendingIntent, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda18.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$uNbYyx1eFTjWVxpOUY7mSeqj8uo(MediaControlPanel mediaControlPanel, View view) {
        mediaControlPanel.lambda$bindGutsMenuCommon$26(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda26.run():void] */
    public static /* synthetic */ void $r8$lambda$v_CrskgVESGBPIEMUAL5mSZwJ3g(MediaControlPanel mediaControlPanel, int i, String str, int i2, ColorScheme colorScheme, boolean z, boolean z2, Drawable drawable, int i3, int i4, MediaData mediaData) {
        mediaControlPanel.lambda$bindArtworkAndColors$11(i, str, i2, colorScheme, z, z2, drawable, i3, i4, mediaData);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda29.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$x9GqWgDBBzODXWHr-M9WzhOm__E */
    public static /* synthetic */ void m3260$r8$lambda$x9GqWgDBBzODXWHrM9WzhOm__E(int i, TextView textView) {
        textView.setTextColor(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda23.invoke():java.lang.Object] */
    public static /* synthetic */ Unit $r8$lambda$zZToofQLuJ9nY3rq9PWOKL4dwNs(MediaControlPanel mediaControlPanel, ImageButton imageButton, MediaAction mediaAction, AnimationBindHandler animationBindHandler, MediaButton mediaButton) {
        return mediaControlPanel.lambda$setSemanticButton$13(imageButton, mediaAction, animationBindHandler, mediaButton);
    }

    static {
        int i = R$id.actionPlayPause;
        int i2 = R$id.actionPrev;
        int i3 = R$id.actionNext;
        SEMANTIC_ACTIONS_COMPACT = List.of(Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING = List.of(Integer.valueOf(i2), Integer.valueOf(i3));
        SEMANTIC_ACTIONS_ALL = List.of(Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(R$id.action0), Integer.valueOf(R$id.action1));
    }

    public MediaControlPanel(Context context, Executor executor, Executor executor2, ActivityStarter activityStarter, BroadcastSender broadcastSender, MediaViewController mediaViewController, SeekBarViewModel seekBarViewModel, Lazy<MediaDataManager> lazy, MediaOutputDialogFactory mediaOutputDialogFactory, MediaCarouselController mediaCarouselController, FalsingManager falsingManager, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, KeyguardStateController keyguardStateController, ActivityIntentHelper activityIntentHelper, NotificationLockscreenUserManager notificationLockscreenUserManager, BroadcastDialogController broadcastDialogController, FeatureFlags featureFlags) {
        this.mContext = context;
        this.mBackgroundExecutor = executor;
        this.mMainExecutor = executor2;
        this.mActivityStarter = activityStarter;
        this.mBroadcastSender = broadcastSender;
        this.mSeekBarViewModel = seekBarViewModel;
        this.mMediaViewController = mediaViewController;
        this.mMediaDataManagerLazy = lazy;
        this.mMediaOutputDialogFactory = mediaOutputDialogFactory;
        this.mMediaCarouselController = mediaCarouselController;
        this.mFalsingManager = falsingManager;
        this.mSystemClock = systemClock;
        this.mLogger = mediaUiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mActivityIntentHelper = activityIntentHelper;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mBroadcastDialogController = broadcastDialogController;
        seekBarViewModel.setLogSeek(new Function0() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda2
            public final Object invoke() {
                return MediaControlPanel.$r8$lambda$dh28ZuqJIqoKsicZyyTtrXaKW5s(MediaControlPanel.this);
            }
        });
        this.mFeatureFlags = featureFlags;
    }

    public /* synthetic */ boolean lambda$attachPlayer$2(View view) {
        if (this.mFalsingManager.isFalseLongTap(1)) {
            return true;
        }
        if (this.mMediaViewController.isGutsVisible()) {
            closeGuts();
            return true;
        }
        openGuts();
        return true;
    }

    public /* synthetic */ void lambda$attachPlayer$3() {
        if (this.mTurbulenceNoiseAnimationConfig == null) {
            this.mTurbulenceNoiseAnimationConfig = createLingeringNoiseAnimation();
        }
        this.mTurbulenceNoiseController.play(this.mTurbulenceNoiseAnimationConfig);
    }

    public /* synthetic */ boolean lambda$attachRecommendation$4(View view) {
        if (this.mFalsingManager.isFalseLongTap(1)) {
            return true;
        }
        if (this.mMediaViewController.isGutsVisible()) {
            closeGuts();
            return true;
        }
        openGuts();
        return true;
    }

    public /* synthetic */ void lambda$bindArtworkAndColors$11(int i, String str, int i2, ColorScheme colorScheme, boolean z, boolean z2, Drawable drawable, int i3, int i4, MediaData mediaData) {
        if (i < this.mArtworkBoundId) {
            Trace.endAsyncSection(str, i2);
            return;
        }
        this.mArtworkBoundId = i;
        boolean updateColorScheme = this.mColorSchemeTransition.updateColorScheme(colorScheme);
        ImageView albumView = this.mMediaViewHolder.getAlbumView();
        albumView.setPadding(0, 0, 0, 0);
        if (z || updateColorScheme || (!this.mIsArtworkBound && z2)) {
            if (this.mPrevArtwork == null) {
                albumView.setImageDrawable(drawable);
            } else {
                TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{this.mPrevArtwork, drawable});
                scaleTransitionDrawableLayer(transitionDrawable, 0, i3, i4);
                scaleTransitionDrawableLayer(transitionDrawable, 1, i3, i4);
                transitionDrawable.setLayerGravity(0, 17);
                transitionDrawable.setLayerGravity(1, 17);
                transitionDrawable.setCrossFadeEnabled(!z2);
                albumView.setImageDrawable(transitionDrawable);
                transitionDrawable.startTransition(z2 ? 333 : 80);
            }
            this.mPrevArtwork = drawable;
            this.mIsArtworkBound = z2;
        }
        ImageView appIcon = this.mMediaViewHolder.getAppIcon();
        appIcon.clearColorFilter();
        if (mediaData.getAppIcon() == null || mediaData.getResumption()) {
            appIcon.setColorFilter(getGrayscaleFilter());
            try {
                appIcon.setImageDrawable(this.mContext.getPackageManager().getApplicationIcon(mediaData.getPackageName()));
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaControlPanel", "Cannot find icon for package " + mediaData.getPackageName(), e);
                appIcon.setImageResource(R$drawable.ic_music_note);
            }
        } else {
            appIcon.setImageIcon(mediaData.getAppIcon());
            appIcon.setColorFilter(this.mColorSchemeTransition.getAccentPrimary().getTargetColor());
        }
        Trace.endAsyncSection(str, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$bindArtworkAndColors$12(final MediaData mediaData, final int i, final int i2, final int i3, final String str, final int i4, final boolean z) {
        WallpaperColors wallpaperColors;
        Drawable colorDrawable;
        ColorScheme colorScheme;
        boolean z2;
        ColorScheme colorScheme2;
        Icon artwork = mediaData.getArtwork();
        if (artwork != null) {
            if (artwork.getType() == 1 || artwork.getType() == 5) {
                wallpaperColors = WallpaperColors.fromBitmap(artwork.getBitmap());
            } else if (artwork.loadDrawable(this.mContext) != null) {
                wallpaperColors = WallpaperColors.fromDrawable(artwork.loadDrawable(this.mContext));
            }
            if (wallpaperColors == null) {
                colorScheme2 = new ColorScheme(wallpaperColors, true, Style.CONTENT);
                Drawable scaledBackground = getScaledBackground(artwork, i, i2);
                GradientDrawable gradientDrawable = (GradientDrawable) this.mContext.getDrawable(R$drawable.qs_media_scrim);
                gradientDrawable.setColors(new int[]{ColorUtilKt.getColorWithAlpha(MediaColorSchemesKt.backgroundStartFromScheme(colorScheme2), 0.25f), ColorUtilKt.getColorWithAlpha(MediaColorSchemesKt.backgroundEndFromScheme(colorScheme2), 0.9f)});
                z2 = true;
                colorDrawable = new LayerDrawable(new Drawable[]{scaledBackground, gradientDrawable});
            } else {
                colorDrawable = new ColorDrawable(0);
                try {
                    colorScheme = new ColorScheme(WallpaperColors.fromDrawable(this.mContext.getPackageManager().getApplicationIcon(mediaData.getPackageName())), true, Style.CONTENT);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.w("MediaControlPanel", "Cannot find icon for package " + mediaData.getPackageName(), e);
                    colorScheme = null;
                }
                z2 = false;
                colorScheme2 = colorScheme;
            }
            final ColorScheme colorScheme3 = colorScheme2;
            final boolean z3 = z2;
            final Drawable drawable = colorDrawable;
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    MediaControlPanel.$r8$lambda$v_CrskgVESGBPIEMUAL5mSZwJ3g(MediaControlPanel.this, i3, str, i4, colorScheme3, z, z3, drawable, i, i2, mediaData);
                }
            });
        }
        wallpaperColors = null;
        if (wallpaperColors == null) {
        }
        final ColorScheme colorScheme32 = colorScheme2;
        final boolean z32 = z2;
        final Drawable drawable2 = colorDrawable;
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                MediaControlPanel.$r8$lambda$v_CrskgVESGBPIEMUAL5mSZwJ3g(MediaControlPanel.this, i3, str, i4, colorScheme32, z, z32, drawable2, i, i2, mediaData);
            }
        });
    }

    public /* synthetic */ void lambda$bindButtonCommon$14(ImageButton imageButton, Runnable runnable, Drawable drawable, Drawable drawable2, View view) {
        if (this.mFalsingManager.isFalseTap(this.mFeatureFlags.isEnabled(Flags.MEDIA_FALSING_PENALTY) ? 2 : 1)) {
            return;
        }
        this.mLogger.logTapAction(imageButton.getId(), this.mUid, this.mPackageName, this.mInstanceId);
        logSmartspaceCardReported(760);
        runnable.run();
        if (this.mFeatureFlags.isEnabled(Flags.UMO_SURFACE_RIPPLE)) {
            this.mMultiRippleController.play(createTouchRippleAnimation(imageButton));
        }
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        if (drawable2 instanceof Animatable) {
            ((Animatable) drawable2).start();
        }
    }

    public /* synthetic */ void lambda$bindGutsMenuCommon$25(Runnable runnable, View view) {
        if (this.mFalsingManager.isFalseTap(1)) {
            return;
        }
        logSmartspaceCardReported(761);
        this.mLogger.logLongPressDismiss(this.mUid, this.mPackageName, this.mInstanceId);
        runnable.run();
    }

    public /* synthetic */ void lambda$bindGutsMenuCommon$26(View view) {
        if (this.mFalsingManager.isFalseTap(1)) {
            return;
        }
        closeGuts();
    }

    public /* synthetic */ void lambda$bindGutsMenuCommon$27(View view) {
        if (this.mFalsingManager.isFalseTap(1)) {
            return;
        }
        this.mLogger.logLongPressSettings(this.mUid, this.mPackageName, this.mInstanceId);
        this.mActivityStarter.startActivity(SETTINGS_INTENT, true);
    }

    public /* synthetic */ void lambda$bindGutsMenuForPlayer$8(MediaData mediaData) {
        if (this.mKey == null) {
            Log.w("MediaControlPanel", "Dismiss media with null notification. Token uid=" + mediaData.getToken().getUid());
            return;
        }
        closeGuts();
        if (((MediaDataManager) this.mMediaDataManagerLazy.get()).dismissMediaData(this.mKey, MediaViewController.GUTS_ANIMATION_DURATION + 100)) {
            return;
        }
        Log.w("MediaControlPanel", "Manager failed to dismiss media " + this.mKey);
        this.mMediaCarouselController.removePlayer(this.mKey, false, false);
    }

    public /* synthetic */ void lambda$bindOutputSwitcherAndBroadcastButton$7(boolean z, MediaDeviceData mediaDeviceData, View view) {
        if (this.mFalsingManager.isFalseTap(this.mFeatureFlags.isEnabled(Flags.MEDIA_FALSING_PENALTY) ? 2 : 1)) {
            return;
        }
        if (z) {
            if (this.mIsCurrentBroadcastedApp) {
                this.mLogger.logOpenOutputSwitcher(this.mUid, this.mPackageName, this.mInstanceId);
                this.mMediaOutputDialogFactory.create(this.mPackageName, true, this.mMediaViewHolder.getSeamlessButton());
                return;
            }
            this.mLogger.logOpenBroadcastDialog(this.mUid, this.mPackageName, this.mInstanceId);
            String charSequence = mediaDeviceData.getName().toString();
            this.mSwitchBroadcastApp = charSequence;
            this.mBroadcastDialogController.createBroadcastDialog(charSequence, this.mPackageName, true, this.mMediaViewHolder.getSeamlessButton());
            return;
        }
        this.mLogger.logOpenOutputSwitcher(this.mUid, this.mPackageName, this.mInstanceId);
        if (mediaDeviceData.getIntent() == null) {
            this.mMediaOutputDialogFactory.create(this.mPackageName, true, this.mMediaViewHolder.getSeamlessButton());
        } else if (mediaDeviceData.getIntent().isActivity()) {
            this.mActivityStarter.startActivity(mediaDeviceData.getIntent().getIntent(), true);
        } else {
            try {
                mediaDeviceData.getIntent().send();
            } catch (PendingIntent.CanceledException e) {
                Log.e("MediaControlPanel", "Device pending intent was canceled");
            }
        }
    }

    public /* synthetic */ void lambda$bindPlayer$5(PendingIntent pendingIntent, View view) {
        if (this.mFalsingManager.isFalseTap(1) || this.mMediaViewController.isGutsVisible()) {
            return;
        }
        this.mLogger.logTapContentView(this.mUid, this.mPackageName, this.mInstanceId);
        logSmartspaceCardReported(760);
        if (this.mKeyguardStateController.isShowing() && this.mActivityIntentHelper.wouldShowOverLockscreen(pendingIntent.getIntent(), this.mLockscreenUserManager.getCurrentUserId())) {
            this.mActivityStarter.startActivity(pendingIntent.getIntent(), true, (ActivityLaunchAnimator.Controller) null, true);
        } else {
            this.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent, buildLaunchAnimatorController(this.mMediaViewHolder.getPlayer()));
        }
    }

    public /* synthetic */ void lambda$bindPlayer$6(MediaController mediaController) {
        this.mSeekBarViewModel.updateController(mediaController);
    }

    public /* synthetic */ boolean lambda$bindRecommendation$17(View view) {
        View view2;
        if (this.mFalsingManager.isFalseLongTap(1) || (view2 = (View) view.getParent()) == null) {
            return true;
        }
        view2.performLongClick();
        return true;
    }

    public /* synthetic */ void lambda$bindRecommendation$18(ConstraintSet constraintSet, boolean z, TextView textView) {
        setVisibleAndAlpha(constraintSet, textView.getId(), z);
    }

    public /* synthetic */ void lambda$bindRecommendation$19(ConstraintSet constraintSet, boolean z, TextView textView) {
        setVisibleAndAlpha(constraintSet, textView.getId(), z);
    }

    public /* synthetic */ void lambda$bindRecommendation$20(SmartspaceMediaData smartspaceMediaData) {
        closeGuts();
        ((MediaDataManager) this.mMediaDataManagerLazy.get()).dismissSmartspaceRecommendation(smartspaceMediaData.getTargetId(), MediaViewController.GUTS_ANIMATION_DURATION + 100);
        Intent dismissIntent = smartspaceMediaData.getDismissIntent();
        if (dismissIntent == null) {
            Log.w("MediaControlPanel", "Cannot create dismiss action click action: extras missing dismiss_intent.");
        } else if (dismissIntent.getComponent() == null || !dismissIntent.getComponent().getClassName().equals("com.google.android.apps.gsa.staticplugins.opa.smartspace.ExportedSmartspaceTrampolineActivity")) {
            this.mBroadcastSender.sendBroadcast(dismissIntent);
        } else {
            this.mContext.startActivity(dismissIntent);
        }
    }

    public /* synthetic */ Unit lambda$bindSongMetadata$10() {
        this.mMediaViewController.refreshState();
        return Unit.INSTANCE;
    }

    public /* synthetic */ Unit lambda$bindSongMetadata$9(TextView textView, MediaData mediaData, TextView textView2) {
        textView.setText(mediaData.getSong());
        textView2.setText(mediaData.getArtist());
        this.mMediaViewController.refreshState();
        return Unit.INSTANCE;
    }

    public /* synthetic */ void lambda$fetchAndUpdateRecommendationColors$22(Drawable drawable) {
        final ColorScheme colorScheme = new ColorScheme(WallpaperColors.fromDrawable(drawable), true);
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                MediaControlPanel.m3252$r8$lambda$IHIdvUztlt23By6k8JNCKvmN6U(MediaControlPanel.this, colorScheme);
            }
        });
    }

    public /* synthetic */ Unit lambda$new$0() {
        InstanceId instanceId;
        String str = this.mPackageName;
        if (str != null && (instanceId = this.mInstanceId) != null) {
            this.mLogger.logSeek(this.mUid, str, instanceId);
        }
        logSmartspaceCardReported(760);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ boolean lambda$scrubbingTimeViewsEnabled$16(MediaButton mediaButton, Integer num) {
        return mediaButton.getActionById(num.intValue()) != null;
    }

    public /* synthetic */ void lambda$setIsScrubbing$1() {
        updateDisplayForScrubbingChange(this.mMediaData.getSemanticActions());
    }

    public /* synthetic */ Unit lambda$setSemanticButton$13(ImageButton imageButton, MediaAction mediaAction, AnimationBindHandler animationBindHandler, MediaButton mediaButton) {
        bindButtonWithAnimations(imageButton, mediaAction, animationBindHandler);
        setSemanticButtonVisibleAndAlpha(imageButton.getId(), mediaAction, mediaButton);
        return Unit.INSTANCE;
    }

    public /* synthetic */ void lambda$setSmartspaceRecItemOnClickListener$28(int i, SmartspaceAction smartspaceAction, View view, View view2) {
        if (this.mFalsingManager.isFalseTap(1)) {
            return;
        }
        if (i == -1) {
            this.mLogger.logRecommendationCardTap(this.mPackageName, this.mInstanceId);
        } else {
            this.mLogger.logRecommendationItemTap(this.mPackageName, this.mInstanceId, i);
        }
        logSmartspaceCardReported(760, i, this.mSmartspaceMediaItemsCount);
        if (shouldSmartspaceRecItemOpenInForeground(smartspaceAction)) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(smartspaceAction.getIntent(), 0, buildLaunchAnimatorController(this.mRecommendationViewHolder.getRecommendations()));
        } else {
            view.getContext().startActivity(smartspaceAction.getIntent());
        }
        this.mMediaCarouselController.setShouldScrollToKey(true);
    }

    public /* synthetic */ void lambda$updateDisplayForScrubbingChange$15(MediaButton mediaButton, Integer num) {
        setSemanticButtonVisibleAndAlpha(num.intValue(), mediaButton.getActionById(num.intValue()), mediaButton);
    }

    public void attachPlayer(MediaViewHolder mediaViewHolder) {
        this.mMediaViewHolder = mediaViewHolder;
        TransitionLayout player = mediaViewHolder.getPlayer();
        this.mSeekBarObserver = new SeekBarObserver(mediaViewHolder);
        this.mSeekBarViewModel.getProgress().observeForever(this.mSeekBarObserver);
        this.mSeekBarViewModel.attachTouchHandlers(mediaViewHolder.getSeekBar());
        this.mSeekBarViewModel.setScrubbingChangeListener(this.mScrubbingChangeListener);
        this.mSeekBarViewModel.setEnabledChangeListener(this.mEnabledChangeListener);
        this.mMediaViewController.attach(player, MediaViewController.TYPE.PLAYER);
        mediaViewHolder.getPlayer().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda7
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return MediaControlPanel.$r8$lambda$hbBVgJKJ1ULH5fHfQB2A9ffvks0(MediaControlPanel.this, view);
            }
        });
        this.mMediaViewHolder.getAlbumView().setLayerType(2, null);
        TextView titleText = this.mMediaViewHolder.getTitleText();
        TextView artistText = this.mMediaViewHolder.getArtistText();
        AnimatorSet loadAnimator = loadAnimator(R$anim.media_metadata_enter, Interpolators.EMPHASIZED_DECELERATE, titleText, artistText);
        AnimatorSet loadAnimator2 = loadAnimator(R$anim.media_metadata_exit, Interpolators.EMPHASIZED_ACCELERATE, titleText, artistText);
        this.mMultiRippleController = new MultiRippleController(mediaViewHolder.getMultiRippleView());
        this.mTurbulenceNoiseController = new TurbulenceNoiseController(mediaViewHolder.getTurbulenceNoiseView());
        if (this.mFeatureFlags.isEnabled(Flags.UMO_TURBULENCE_NOISE)) {
            MultiRippleController.Companion.RipplesFinishedListener ripplesFinishedListener = new MultiRippleController.Companion.RipplesFinishedListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda8
                public final void onRipplesFinish() {
                    MediaControlPanel.$r8$lambda$ALhVXjmJ1Ar796nlShkkJk57E2I(MediaControlPanel.this);
                }
            };
            this.mRipplesFinishedListener = ripplesFinishedListener;
            this.mMultiRippleController.addRipplesFinishedListener(ripplesFinishedListener);
        }
        this.mColorSchemeTransition = new ColorSchemeTransition(this.mContext, this.mMediaViewHolder, this.mMultiRippleController, this.mTurbulenceNoiseController);
        this.mMetadataAnimationHandler = new MetadataAnimationHandler(loadAnimator2, loadAnimator);
    }

    public void attachRecommendation(RecommendationViewHolder recommendationViewHolder) {
        this.mRecommendationViewHolder = recommendationViewHolder;
        this.mMediaViewController.attach(recommendationViewHolder.getRecommendations(), MediaViewController.TYPE.RECOMMENDATION);
        this.mRecommendationViewHolder.getRecommendations().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda11
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return MediaControlPanel.$r8$lambda$kzRDs_jdb06j0a1QwkeqaZy67Dc(MediaControlPanel.this, view);
            }
        });
    }

    public final void bindActionButtons(MediaData mediaData) {
        int i;
        MediaButton semanticActions = mediaData.getSemanticActions();
        ArrayList<ImageButton> arrayList = new ArrayList();
        for (Integer num : MediaViewHolder.Companion.getGenericButtonIds()) {
            arrayList.add(this.mMediaViewHolder.getAction(num.intValue()));
        }
        ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        ConstraintSet collapsedLayout = this.mMediaViewController.getCollapsedLayout();
        if (semanticActions != null) {
            for (ImageButton imageButton : arrayList) {
                setVisibleAndAlpha(collapsedLayout, imageButton.getId(), false);
                setVisibleAndAlpha(expandedLayout, imageButton.getId(), false);
            }
            for (Integer num2 : SEMANTIC_ACTIONS_ALL) {
                int intValue = num2.intValue();
                setSemanticButton(this.mMediaViewHolder.getAction(intValue), semanticActions.getActionById(intValue), semanticActions);
            }
        } else {
            for (Integer num3 : SEMANTIC_ACTIONS_COMPACT) {
                int intValue2 = num3.intValue();
                setVisibleAndAlpha(collapsedLayout, intValue2, false);
                setVisibleAndAlpha(expandedLayout, intValue2, false);
            }
            List<Integer> actionsToShowInCompact = mediaData.getActionsToShowInCompact();
            List<MediaAction> actions = mediaData.getActions();
            int i2 = 0;
            while (true) {
                i = i2;
                if (i2 >= actions.size()) {
                    break;
                }
                i = i2;
                if (i2 >= arrayList.size()) {
                    break;
                }
                setGenericButton((ImageButton) arrayList.get(i2), actions.get(i2), collapsedLayout, expandedLayout, actionsToShowInCompact.contains(Integer.valueOf(i2)));
                i2++;
            }
            while (i < arrayList.size()) {
                setGenericButton((ImageButton) arrayList.get(i), null, collapsedLayout, expandedLayout, false);
                i++;
            }
        }
        updateSeekBarVisibility();
    }

    public final void bindArtworkAndColors(final MediaData mediaData, String str, final boolean z) {
        final int hashCode = mediaData.hashCode();
        final String str2 = "MediaControlPanel#bindArtworkAndColors<" + str + ">";
        Trace.beginAsyncSection(str2, hashCode);
        final int i = this.mArtworkNextBindRequestId;
        this.mArtworkNextBindRequestId = i + 1;
        if (z) {
            this.mIsArtworkBound = false;
        }
        final int measuredWidth = this.mMediaViewHolder.getAlbumView().getMeasuredWidth();
        final int measuredHeight = this.mMediaViewHolder.getAlbumView().getMeasuredHeight();
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                MediaControlPanel.m3250$r8$lambda$0NukvMTLJIyJxqYbIUQXt89m6E(MediaControlPanel.this, mediaData, measuredWidth, measuredHeight, i, str2, hashCode, z);
            }
        });
    }

    public final void bindButtonCommon(final ImageButton imageButton, MediaAction mediaAction) {
        if (mediaAction == null) {
            clearButton(imageButton);
            return;
        }
        final Drawable icon = mediaAction.getIcon();
        imageButton.setImageDrawable(icon);
        imageButton.setContentDescription(mediaAction.getContentDescription());
        final Drawable background = mediaAction.getBackground();
        imageButton.setBackground(background);
        final Runnable action = mediaAction.getAction();
        if (action == null) {
            imageButton.setEnabled(false);
            return;
        }
        imageButton.setEnabled(true);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda28
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaControlPanel.$r8$lambda$VHer1o9TEyP22P4VUvkhFC63w_8(MediaControlPanel.this, imageButton, action, icon, background, view);
            }
        });
    }

    public final void bindButtonWithAnimations(ImageButton imageButton, MediaAction mediaAction, AnimationBindHandler animationBindHandler) {
        if (mediaAction == null) {
            animationBindHandler.unregisterAll();
            clearButton(imageButton);
        } else if (animationBindHandler.updateRebindId(mediaAction.getRebindId())) {
            animationBindHandler.unregisterAll();
            animationBindHandler.tryRegister(mediaAction.getIcon());
            animationBindHandler.tryRegister(mediaAction.getBackground());
            bindButtonCommon(imageButton, mediaAction);
        }
    }

    public final void bindGutsMenuCommon(boolean z, String str, GutsViewHolder gutsViewHolder, final Runnable runnable) {
        int i = 0;
        gutsViewHolder.getGutsText().setText(z ? this.mContext.getString(R$string.controls_media_close_session, str) : this.mContext.getString(R$string.controls_media_active_session));
        TextView dismissText = gutsViewHolder.getDismissText();
        if (!z) {
            i = 8;
        }
        dismissText.setVisibility(i);
        gutsViewHolder.getDismiss().setEnabled(z);
        gutsViewHolder.getDismiss().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaControlPanel.m3253$r8$lambda$Nt1BZR7Z4R4eeGheArAC35DuKg(MediaControlPanel.this, runnable, view);
            }
        });
        TextView cancelText = gutsViewHolder.getCancelText();
        if (z) {
            cancelText.setBackground(this.mContext.getDrawable(R$drawable.qs_media_outline_button));
        } else {
            cancelText.setBackground(this.mContext.getDrawable(R$drawable.qs_media_solid_button));
        }
        gutsViewHolder.getCancel().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaControlPanel.$r8$lambda$uNbYyx1eFTjWVxpOUY7mSeqj8uo(MediaControlPanel.this, view);
            }
        });
        gutsViewHolder.setDismissible(z);
        gutsViewHolder.getSettings().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaControlPanel.m3257$r8$lambda$X6UUIRwHkImI1d39fKj3C_ikAw(MediaControlPanel.this, view);
            }
        });
    }

    public final void bindGutsMenuForPlayer(final MediaData mediaData) {
        bindGutsMenuCommon(mediaData.isClearable(), mediaData.getApp(), this.mMediaViewHolder.getGutsViewHolder(), new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                MediaControlPanel.m3259$r8$lambda$_1ITg52Muw7weyzQcBZzUCXSkA(MediaControlPanel.this, mediaData);
            }
        });
    }

    public final void bindOutputSwitcherAndBroadcastButton(final boolean z, MediaData mediaData) {
        boolean z2;
        String string;
        int i;
        ViewGroup seamless = this.mMediaViewHolder.getSeamless();
        boolean z3 = false;
        seamless.setVisibility(0);
        ImageView seamlessIcon = this.mMediaViewHolder.getSeamlessIcon();
        TextView seamlessText = this.mMediaViewHolder.getSeamlessText();
        final MediaDeviceData device = mediaData.getDevice();
        if (z) {
            boolean z4 = false;
            if (device != null) {
                CharSequence name = device.getName();
                Context context = this.mContext;
                z4 = false;
                if (TextUtils.equals(name, MediaDataUtils.getAppLabel(context, this.mPackageName, context.getString(R$string.bt_le_audio_broadcast_dialog_unknown_name)))) {
                    z4 = true;
                }
            }
            this.mIsCurrentBroadcastedApp = z4;
            z3 = !z4;
            string = this.mContext.getString(R$string.bt_le_audio_broadcast_dialog_unknown_name);
            i = R$drawable.settings_input_antenna;
            z2 = true;
        } else {
            if ((device != null && !device.getEnabled()) || mediaData.getResumption()) {
                z3 = true;
            }
            z2 = !z3;
            string = this.mContext.getString(R$string.media_seamless_other_device);
            i = R$drawable.ic_media_home_devices;
        }
        this.mMediaViewHolder.getSeamlessButton().setAlpha(z3 ? 0.38f : 1.0f);
        seamless.setEnabled(z2);
        if (device != null) {
            Drawable icon = device.getIcon();
            if (icon instanceof AdaptiveIcon) {
                AdaptiveIcon adaptiveIcon = (AdaptiveIcon) icon;
                adaptiveIcon.setBackgroundColor(this.mColorSchemeTransition.getBgColor());
                seamlessIcon.setImageDrawable(adaptiveIcon);
            } else {
                seamlessIcon.setImageDrawable(icon);
            }
            if (device.getName() != null) {
                string = device.getName();
            }
        } else {
            seamlessIcon.setImageResource(i);
        }
        seamlessText.setText(string);
        seamless.setContentDescription(string);
        seamless.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaControlPanel.$r8$lambda$pNqAQX48fFL7_BoH9M3MI5ntGIY(MediaControlPanel.this, z, device, view);
            }
        });
    }

    public void bindPlayer(MediaData mediaData, String str) {
        if (this.mMediaViewHolder == null) {
            return;
        }
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, "MediaControlPanel#bindPlayer<" + str + ">");
        }
        this.mKey = str;
        this.mMediaData = mediaData;
        MediaSession.Token token = mediaData.getToken();
        this.mPackageName = mediaData.getPackageName();
        int appUid = mediaData.getAppUid();
        this.mUid = appUid;
        if (this.mSmartspaceId == -1) {
            this.mSmartspaceId = SmallHash.hash(appUid + ((int) this.mSystemClock.currentTimeMillis()));
        }
        this.mInstanceId = mediaData.getInstanceId();
        MediaSession.Token token2 = this.mToken;
        if (token2 == null || !token2.equals(token)) {
            this.mToken = token;
        }
        if (this.mToken != null) {
            this.mController = new MediaController(this.mContext, this.mToken);
        } else {
            this.mController = null;
        }
        final PendingIntent clickIntent = mediaData.getClickIntent();
        if (clickIntent != null) {
            this.mMediaViewHolder.getPlayer().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MediaControlPanel.$r8$lambda$uGL7JuGhlMRKPpasR5UTJcqQUZ8(MediaControlPanel.this, clickIntent, view);
                }
            });
        }
        final MediaController controller = getController();
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                MediaControlPanel.$r8$lambda$OD8aW8OrvX8ejJ3HTsRa1iK09Yg(MediaControlPanel.this, controller);
            }
        });
        boolean z = mediaData.getDevice() != null && mediaData.getDevice().getShowBroadcastButton();
        this.mShowBroadcastDialogButton = z;
        bindOutputSwitcherAndBroadcastButton(z, mediaData);
        bindGutsMenuForPlayer(mediaData);
        bindPlayerContentDescription(mediaData);
        bindScrubbingTime(mediaData);
        bindActionButtons(mediaData);
        bindArtworkAndColors(mediaData, str, bindSongMetadata(mediaData));
        if (!this.mMetadataAnimationHandler.isRunning()) {
            this.mMediaViewController.refreshState();
        }
        Trace.endSection();
    }

    public final void bindPlayerContentDescription(MediaData mediaData) {
        if (this.mMediaViewHolder == null) {
            return;
        }
        this.mMediaViewHolder.getPlayer().setContentDescription(this.mMediaViewController.isGutsVisible() ? this.mMediaViewHolder.getGutsViewHolder().getGutsText().getText() : mediaData != null ? this.mContext.getString(R$string.controls_media_playing_item_description, mediaData.getSong(), mediaData.getArtist(), mediaData.getApp()) : null);
    }

    public void bindRecommendation(final SmartspaceMediaData smartspaceMediaData) {
        if (this.mRecommendationViewHolder == null) {
            return;
        }
        if (!smartspaceMediaData.isValid()) {
            Log.e("MediaControlPanel", "Received an invalid recommendation list; returning");
            return;
        }
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, "MediaControlPanel#bindRecommendation<" + smartspaceMediaData.getPackageName() + ">");
        }
        this.mRecommendationData = smartspaceMediaData;
        this.mSmartspaceId = SmallHash.hash(smartspaceMediaData.getTargetId());
        this.mPackageName = smartspaceMediaData.getPackageName();
        this.mInstanceId = smartspaceMediaData.getInstanceId();
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(smartspaceMediaData.getPackageName(), 0);
            this.mUid = applicationInfo.uid;
            CharSequence appName = smartspaceMediaData.getAppName(this.mContext);
            if (appName == null) {
                Log.w("MediaControlPanel", "Fail to get media recommendation's app name");
                Trace.endSection();
                return;
            }
            Drawable applicationIcon = this.mContext.getPackageManager().getApplicationIcon(applicationInfo);
            this.mRecommendationViewHolder.getCardIcon().setImageDrawable(applicationIcon);
            fetchAndUpdateRecommendationColors(applicationIcon);
            setSmartspaceRecItemOnClickListener(this.mRecommendationViewHolder.getRecommendations(), smartspaceMediaData.getCardAction(), -1);
            bindRecommendationContentDescription(smartspaceMediaData);
            List<ImageView> mediaCoverItems = this.mRecommendationViewHolder.getMediaCoverItems();
            List<ViewGroup> mediaCoverContainers = this.mRecommendationViewHolder.getMediaCoverContainers();
            List<SmartspaceAction> validRecommendations = smartspaceMediaData.getValidRecommendations();
            boolean z = false;
            boolean z2 = false;
            char c = 0;
            for (int i = 0; i < 3; i++) {
                SmartspaceAction smartspaceAction = validRecommendations.get(i);
                ImageView imageView = mediaCoverItems.get(i);
                imageView.setImageIcon(smartspaceAction.getIcon());
                ViewGroup viewGroup = mediaCoverContainers.get(i);
                setSmartspaceRecItemOnClickListener(viewGroup, smartspaceAction, i);
                viewGroup.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return MediaControlPanel.$r8$lambda$6G40Cb19Lm7SzSFcKyFDeC1Qgl8(MediaControlPanel.this, view);
                    }
                });
                CharSequence charSequence = "";
                String string = smartspaceAction.getExtras().getString("artist_name", "");
                if (string.isEmpty()) {
                    Context context = this.mContext;
                    int i2 = R$string.controls_media_smartspace_rec_item_no_artist_description;
                    Object[] objArr = new Object[2];
                    objArr[c] = smartspaceAction.getTitle();
                    objArr[1] = appName;
                    imageView.setContentDescription(context.getString(i2, objArr));
                } else {
                    c = 0;
                    imageView.setContentDescription(this.mContext.getString(R$string.controls_media_smartspace_rec_item_description, smartspaceAction.getTitle(), string, appName));
                }
                CharSequence title = smartspaceAction.getTitle();
                z2 |= !TextUtils.isEmpty(title);
                this.mRecommendationViewHolder.getMediaTitles().get(i).setText(title);
                if (!TextUtils.isEmpty(title)) {
                    charSequence = smartspaceAction.getSubtitle();
                }
                z |= !TextUtils.isEmpty(charSequence);
                this.mRecommendationViewHolder.getMediaSubtitles().get(i).setText(charSequence);
            }
            this.mSmartspaceMediaItemsCount = 3;
            final ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
            final boolean z3 = z2;
            this.mRecommendationViewHolder.getMediaTitles().forEach(new Consumer() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MediaControlPanel.$r8$lambda$AX21olSmepcLYAqhs8GI3Em19g0(MediaControlPanel.this, expandedLayout, z3, (TextView) obj);
                }
            });
            final boolean z4 = z;
            this.mRecommendationViewHolder.getMediaSubtitles().forEach(new Consumer() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MediaControlPanel.$r8$lambda$JW3izhAk4bFOcDlY5yJ3umyWTQc(MediaControlPanel.this, expandedLayout, z4, (TextView) obj);
                }
            });
            bindGutsMenuCommon(true, appName.toString(), this.mRecommendationViewHolder.getGutsViewHolder(), new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    MediaControlPanel.m3256$r8$lambda$WPo63CrRtUnrXEAWmNtPom6CxE(MediaControlPanel.this, smartspaceMediaData);
                }
            });
            this.mController = null;
            MetadataAnimationHandler metadataAnimationHandler = this.mMetadataAnimationHandler;
            if (metadataAnimationHandler == null || !metadataAnimationHandler.isRunning()) {
                this.mMediaViewController.refreshState();
            }
            Trace.endSection();
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("MediaControlPanel", "Fail to get media recommendation's app info", e);
            Trace.endSection();
        }
    }

    public final void bindRecommendationContentDescription(SmartspaceMediaData smartspaceMediaData) {
        String str;
        if (this.mRecommendationViewHolder == null) {
            return;
        }
        if (this.mMediaViewController.isGutsVisible()) {
            str = this.mRecommendationViewHolder.getGutsViewHolder().getGutsText().getText();
        } else if (smartspaceMediaData != null) {
            Context context = this.mContext;
            str = context.getString(R$string.controls_media_smartspace_rec_description, smartspaceMediaData.getAppName(context));
        } else {
            str = null;
        }
        this.mRecommendationViewHolder.getRecommendations().setContentDescription(str);
    }

    public final void bindScrubbingTime(MediaData mediaData) {
        ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        int id = this.mMediaViewHolder.getScrubbingElapsedTimeView().getId();
        int id2 = this.mMediaViewHolder.getScrubbingTotalTimeView().getId();
        boolean z = scrubbingTimeViewsEnabled(mediaData.getSemanticActions()) && this.mIsScrubbing;
        setVisibleAndAlpha(expandedLayout, id, z);
        setVisibleAndAlpha(expandedLayout, id2, z);
    }

    public final boolean bindSongMetadata(final MediaData mediaData) {
        final TextView titleText = this.mMediaViewHolder.getTitleText();
        final TextView artistText = this.mMediaViewHolder.getArtistText();
        return this.mMetadataAnimationHandler.setNext(Pair.create(mediaData.getSong(), mediaData.getArtist()), new Function0() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda12
            public final Object invoke() {
                return MediaControlPanel.$r8$lambda$sEMXk472q7QSoU7AqlKNRXulZgI(MediaControlPanel.this, titleText, mediaData, artistText);
            }
        }, new Function0() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda13
            public final Object invoke() {
                return MediaControlPanel.$r8$lambda$qeuWjuFz3K1TnGWl4_bMiIWdubQ(MediaControlPanel.this);
            }
        });
    }

    public final ActivityLaunchAnimator.Controller buildLaunchAnimatorController(TransitionLayout transitionLayout) {
        if (transitionLayout.getParent() instanceof ViewGroup) {
            return new GhostedViewLaunchAnimatorController(transitionLayout, 31) { // from class: com.android.systemui.media.controls.ui.MediaControlPanel.1
                {
                    MediaControlPanel.this = this;
                }

                @Override // com.android.systemui.animation.GhostedViewLaunchAnimatorController
                public float getCurrentBottomCornerRadius() {
                    return getCurrentTopCornerRadius();
                }

                @Override // com.android.systemui.animation.GhostedViewLaunchAnimatorController
                public float getCurrentTopCornerRadius() {
                    return MediaControlPanel.this.mContext.getResources().getDimension(R$dimen.notification_corner_radius);
                }
            };
        }
        Log.wtf("MediaControlPanel", "Skipping player animation as it is not attached to a ViewGroup", new Exception());
        return null;
    }

    public final void clearButton(ImageButton imageButton) {
        imageButton.setImageDrawable(null);
        imageButton.setContentDescription(null);
        imageButton.setEnabled(false);
        imageButton.setBackground(null);
    }

    public final void closeGuts() {
        closeGuts(false);
    }

    public void closeGuts(boolean z) {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            mediaViewHolder.marquee(false, MediaViewController.GUTS_ANIMATION_DURATION);
        } else {
            RecommendationViewHolder recommendationViewHolder = this.mRecommendationViewHolder;
            if (recommendationViewHolder != null) {
                recommendationViewHolder.marquee(false, MediaViewController.GUTS_ANIMATION_DURATION);
            }
        }
        this.mMediaViewController.closeGuts(z);
        if (this.mMediaViewHolder != null) {
            bindPlayerContentDescription(this.mMediaData);
        } else if (this.mRecommendationViewHolder != null) {
            bindRecommendationContentDescription(this.mRecommendationData);
        }
    }

    public final TurbulenceNoiseAnimationConfig createLingeringNoiseAnimation() {
        return new TurbulenceNoiseAnimationConfig(1.2f, 1.0f, (float) ActionBarShadowController.ELEVATION_LOW, (float) ActionBarShadowController.ELEVATION_LOW, 0.3f, this.mColorSchemeTransition.getAccentPrimary().getCurrentColor(), ColorUtils.setAlphaComponent(-16777216, 0), 150, this.mMediaViewHolder.getMultiRippleView().getWidth(), this.mMediaViewHolder.getMultiRippleView().getHeight(), 7500.0f, 750.0f, 750.0f, getContext().getResources().getDisplayMetrics().density, BlendMode.PLUS, (Runnable) null);
    }

    public final RippleAnimation createTouchRippleAnimation(ImageButton imageButton) {
        float width = this.mMediaViewHolder.getMultiRippleView().getWidth() * 2;
        return new RippleAnimation(new RippleAnimationConfig(RippleShader.RippleShape.CIRCLE, 1500L, imageButton.getX() + (imageButton.getWidth() * 0.5f), imageButton.getY() + (imageButton.getHeight() * 0.5f), width, width, getContext().getResources().getDisplayMetrics().density, this.mColorSchemeTransition.getAccentPrimary().getCurrentColor(), 100, false, (float) ActionBarShadowController.ELEVATION_LOW, false));
    }

    public final void fetchAndUpdateRecommendationColors(final Drawable drawable) {
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                MediaControlPanel.m3249$r8$lambda$6IGVIFDNeEBxNyTAc3Uw7IOEs(MediaControlPanel.this, drawable);
            }
        });
    }

    public Context getContext() {
        return this.mContext;
    }

    public MediaController getController() {
        return this.mController;
    }

    public final ColorMatrixColorFilter getGrayscaleFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(ActionBarShadowController.ELEVATION_LOW);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    public MediaViewController getMediaViewController() {
        return this.mMediaViewController;
    }

    public MediaViewHolder getMediaViewHolder() {
        return this.mMediaViewHolder;
    }

    public RecommendationViewHolder getRecommendationViewHolder() {
        return this.mRecommendationViewHolder;
    }

    public final Drawable getScaledBackground(Icon icon, int i, int i2) {
        if (icon == null) {
            return null;
        }
        Drawable loadDrawable = icon.loadDrawable(this.mContext);
        Rect rect = new Rect(0, 0, i, i2);
        if (rect.width() > i || rect.height() > i2) {
            rect.offset((int) (-((rect.width() - i) / 2.0f)), (int) (-((rect.height() - i2) / 2.0f)));
        }
        loadDrawable.setBounds(rect);
        return loadDrawable;
    }

    public final int getSeekBarVisibility() {
        return this.mIsSeekBarEnabled ? 0 : 4;
    }

    public int getSurfaceForSmartspaceLogging() {
        int currentEndLocation = this.mMediaViewController.getCurrentEndLocation();
        if (currentEndLocation == 1 || currentEndLocation == 0) {
            return 4;
        }
        if (currentEndLocation == 2) {
            return 2;
        }
        return currentEndLocation == 3 ? 5 : 0;
    }

    @VisibleForTesting
    public AnimatorSet loadAnimator(int i, Interpolator interpolator, View... viewArr) {
        ArrayList arrayList = new ArrayList();
        for (View view : viewArr) {
            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this.mContext, i);
            animatorSet.getChildAnimations().get(0).setInterpolator(interpolator);
            animatorSet.setTarget(view);
            arrayList.add(animatorSet);
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(arrayList);
        return animatorSet2;
    }

    public final void logSmartspaceCardReported(int i) {
        logSmartspaceCardReported(i, 0, 0);
    }

    public final void logSmartspaceCardReported(int i, int i2, int i3) {
        this.mMediaCarouselController.logSmartspaceCardReported(i, this.mSmartspaceId, this.mUid, new int[]{getSurfaceForSmartspaceLogging()}, i2, i3);
    }

    public void onDestroy() {
        if (this.mSeekBarObserver != null) {
            this.mSeekBarViewModel.getProgress().removeObserver(this.mSeekBarObserver);
        }
        this.mSeekBarViewModel.removeScrubbingChangeListener(this.mScrubbingChangeListener);
        this.mSeekBarViewModel.removeEnabledChangeListener(this.mEnabledChangeListener);
        this.mSeekBarViewModel.onDestroy();
        this.mMediaViewController.onDestroy();
    }

    public final void openGuts() {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            mediaViewHolder.marquee(true, MediaViewController.GUTS_ANIMATION_DURATION);
        } else {
            RecommendationViewHolder recommendationViewHolder = this.mRecommendationViewHolder;
            if (recommendationViewHolder != null) {
                recommendationViewHolder.marquee(true, MediaViewController.GUTS_ANIMATION_DURATION);
            }
        }
        this.mMediaViewController.openGuts();
        if (this.mMediaViewHolder != null) {
            bindPlayerContentDescription(this.mMediaData);
        } else if (this.mRecommendationViewHolder != null) {
            bindRecommendationContentDescription(this.mRecommendationData);
        }
        this.mLogger.logLongPressOpen(this.mUid, this.mPackageName, this.mInstanceId);
    }

    public final void scaleTransitionDrawableLayer(TransitionDrawable transitionDrawable, int i, int i2, int i3) {
        Drawable drawable = transitionDrawable.getDrawable(i);
        if (drawable == null) {
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth == 0 || intrinsicHeight == 0 || i2 == 0 || i3 == 0) {
            return;
        }
        float f = intrinsicWidth;
        float f2 = intrinsicHeight;
        float f3 = f / f2;
        float f4 = i2;
        float f5 = i3;
        float f6 = f3 > f4 / f5 ? f5 / f2 : f4 / f;
        transitionDrawable.setLayerSize(i, (int) (f * f6), (int) (f6 * f2));
    }

    public final boolean scrubbingTimeViewsEnabled(final MediaButton mediaButton) {
        return mediaButton != null && SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.stream().allMatch(new Predicate() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaControlPanel.m3258$r8$lambda$YUUoIrkfp2xVDX_Hq3RBqzrQMU(MediaButton.this, (Integer) obj);
            }
        });
    }

    public final void setGenericButton(ImageButton imageButton, MediaAction mediaAction, ConstraintSet constraintSet, ConstraintSet constraintSet2, boolean z) {
        bindButtonCommon(imageButton, mediaAction);
        boolean z2 = mediaAction != null;
        setVisibleAndAlpha(constraintSet2, imageButton.getId(), z2);
        setVisibleAndAlpha(constraintSet, imageButton.getId(), z2 && z);
    }

    public final void setIsScrubbing(boolean z) {
        MediaData mediaData = this.mMediaData;
        if (mediaData == null || mediaData.getSemanticActions() == null || z == this.mIsScrubbing) {
            return;
        }
        this.mIsScrubbing = z;
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                MediaControlPanel.m3255$r8$lambda$Sapq8cLdsTeZFqUaFVilCGHS4(MediaControlPanel.this);
            }
        });
    }

    public final void setIsSeekBarEnabled(boolean z) {
        if (z == this.mIsSeekBarEnabled) {
            return;
        }
        this.mIsSeekBarEnabled = z;
        updateSeekBarVisibility();
    }

    public void setListening(boolean z) {
        this.mSeekBarViewModel.setListening(z);
    }

    /* renamed from: setRecommendationColors */
    public final void lambda$fetchAndUpdateRecommendationColors$21(ColorScheme colorScheme) {
        if (this.mRecommendationViewHolder == null) {
            return;
        }
        int surfaceFromScheme = MediaColorSchemesKt.surfaceFromScheme(colorScheme);
        final int textPrimaryFromScheme = MediaColorSchemesKt.textPrimaryFromScheme(colorScheme);
        final int textSecondaryFromScheme = MediaColorSchemesKt.textSecondaryFromScheme(colorScheme);
        this.mRecommendationViewHolder.getRecommendations().setBackgroundTintList(ColorStateList.valueOf(surfaceFromScheme));
        this.mRecommendationViewHolder.getMediaTitles().forEach(new Consumer() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda29
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaControlPanel.m3260$r8$lambda$x9GqWgDBBzODXWHrM9WzhOm__E(textPrimaryFromScheme, (TextView) obj);
            }
        });
        this.mRecommendationViewHolder.getMediaSubtitles().forEach(new Consumer() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda30
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaControlPanel.$r8$lambda$T5QGfL7Z2QSPsO7HgjCnaYw6fjg(textSecondaryFromScheme, (TextView) obj);
            }
        });
        this.mRecommendationViewHolder.getGutsViewHolder().setColors(colorScheme);
    }

    public final void setSemanticButton(final ImageButton imageButton, final MediaAction mediaAction, final MediaButton mediaButton) {
        AnimationBindHandler animationBindHandler;
        if (imageButton.getTag() == null) {
            animationBindHandler = new AnimationBindHandler();
            imageButton.setTag(animationBindHandler);
        } else {
            animationBindHandler = (AnimationBindHandler) imageButton.getTag();
        }
        final AnimationBindHandler animationBindHandler2 = animationBindHandler;
        animationBindHandler.tryExecute(new Function0() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda23
            public final Object invoke() {
                return MediaControlPanel.$r8$lambda$zZToofQLuJ9nY3rq9PWOKL4dwNs(MediaControlPanel.this, imageButton, mediaAction, animationBindHandler2, mediaButton);
            }
        });
    }

    public final void setSemanticButtonVisibleAndAlpha(int i, MediaAction mediaAction, MediaButton mediaButton) {
        ConstraintSet collapsedLayout = this.mMediaViewController.getCollapsedLayout();
        ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        boolean contains = SEMANTIC_ACTIONS_COMPACT.contains(Integer.valueOf(i));
        boolean z = (mediaAction == null || (scrubbingTimeViewsEnabled(mediaButton) && SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.contains(Integer.valueOf(i)) && this.mIsScrubbing)) ? false : true;
        setVisibleAndAlpha(expandedLayout, i, z, ((i == R$id.actionPrev && mediaButton.getReservePrev()) || (i == R$id.actionNext && mediaButton.getReserveNext())) ? 4 : 8);
        setVisibleAndAlpha(collapsedLayout, i, z && contains);
    }

    public final void setSmartspaceRecItemOnClickListener(final View view, final SmartspaceAction smartspaceAction, final int i) {
        if (view == null || smartspaceAction == null || smartspaceAction.getIntent() == null || smartspaceAction.getIntent().getExtras() == null) {
            Log.e("MediaControlPanel", "No tap action can be set up");
        } else {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda16
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MediaControlPanel.m3251$r8$lambda$4n2RskUNdC7o4chtF_JnUH9Ibs(MediaControlPanel.this, i, smartspaceAction, view, view2);
                }
            });
        }
    }

    public final void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z) {
        setVisibleAndAlpha(constraintSet, i, z, 8);
    }

    public final void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z, int i2) {
        if (z) {
            i2 = 0;
        }
        constraintSet.setVisibility(i, i2);
        constraintSet.setAlpha(i, z ? 1.0f : 0.0f);
    }

    public final boolean shouldSmartspaceRecItemOpenInForeground(SmartspaceAction smartspaceAction) {
        String string;
        if (smartspaceAction == null || smartspaceAction.getIntent() == null || smartspaceAction.getIntent().getExtras() == null || (string = smartspaceAction.getIntent().getExtras().getString("com.google.android.apps.gsa.smartspace.extra.SMARTSPACE_INTENT")) == null) {
            return false;
        }
        try {
            return Intent.parseUri(string, 1).getBooleanExtra("KEY_OPEN_IN_FOREGROUND", false);
        } catch (URISyntaxException e) {
            Log.wtf("MediaControlPanel", "Failed to create intent from URI: " + string);
            e.printStackTrace();
            return false;
        }
    }

    public final void updateDisplayForScrubbingChange(final MediaButton mediaButton) {
        bindScrubbingTime(this.mMediaData);
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.forEach(new Consumer() { // from class: com.android.systemui.media.controls.ui.MediaControlPanel$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaControlPanel.$r8$lambda$aNdyC2UolE0qFLvKQjC4v6z7PVk(MediaControlPanel.this, mediaButton, (Integer) obj);
            }
        });
        if (this.mMetadataAnimationHandler.isRunning()) {
            return;
        }
        this.mMediaViewController.refreshState();
    }

    public final void updateSeekBarVisibility() {
        ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        int i = R$id.media_progress_bar;
        expandedLayout.setVisibility(i, getSeekBarVisibility());
        expandedLayout.setAlpha(i, this.mIsSeekBarEnabled ? 1.0f : 0.0f);
    }
}