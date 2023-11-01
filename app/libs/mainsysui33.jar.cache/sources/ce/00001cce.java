package com.android.systemui.media.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import com.android.keyguard.KeyguardViewController;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.media.dream.MediaDreamComplication;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeStateEvents;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHierarchyManager.class */
public final class MediaHierarchyManager {
    public static final Companion Companion = new Companion(null);
    public float animationCrossFadeProgress;
    public boolean animationPending;
    public float animationStartAlpha;
    public float animationStartCrossFadeProgress;
    public ValueAnimator animator;
    public final KeyguardBypassController bypassController;
    public float carouselAlpha;
    public boolean collapsingShadeFromQS;
    public final Context context;
    public int currentAttachmentLocation;
    public int desiredLocation;
    public int distanceForFullShadeTransition;
    public boolean dozeAnimationRunning;
    public boolean dreamMediaComplicationActive;
    public boolean dreamOverlayActive;
    public final DreamOverlayStateController dreamOverlayStateController;
    public float fullShadeTransitionProgress;
    public boolean fullyAwake;
    public boolean goingToSleep;
    public final Handler handler;
    public boolean inSplitShade;
    public boolean isCrossFadeAnimatorRunning;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final Uri lockScreenMediaPlayerUri;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost[] mediaHosts;
    public int previousLocation;
    public boolean qsExpanded;
    public float qsExpansion;
    public ViewGroupOverlay rootOverlay;
    public View rootView;
    public final SecureSettings secureSettings;
    public boolean skipQqsOnExpansion;
    public final Runnable startAnimation;
    public final SysuiStatusBarStateController statusBarStateController;
    public int statusbarState;
    public boolean allowMediaPlayerOnLockScreen = true;
    public Rect currentBounds = new Rect();
    public Rect animationStartBounds = new Rect();
    public Rect animationStartClipping = new Rect();
    public Rect currentClipping = new Rect();
    public Rect targetClipping = new Rect();
    public int crossFadeAnimationStartLocation = -1;
    public int crossFadeAnimationEndLocation = -1;
    public Rect targetBounds = new Rect();

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHierarchyManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaHierarchyManager(Context context, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, MediaCarouselController mediaCarouselController, KeyguardViewController keyguardViewController, DreamOverlayStateController dreamOverlayStateController, ConfigurationController configurationController, WakefulnessLifecycle wakefulnessLifecycle, ShadeStateEvents shadeStateEvents, SecureSettings secureSettings, Handler handler) {
        this.context = context;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.keyguardStateController = keyguardStateController;
        this.bypassController = keyguardBypassController;
        this.mediaCarouselController = mediaCarouselController;
        this.keyguardViewController = keyguardViewController;
        this.dreamOverlayStateController = dreamOverlayStateController;
        this.secureSettings = secureSettings;
        this.handler = handler;
        this.lockScreenMediaPlayerUri = secureSettings.getUriFor("media_controls_lock_screen");
        this.statusbarState = sysuiStatusBarStateController.getState();
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float lerp;
                MediaHierarchyManager.access$updateTargetState(MediaHierarchyManager.this);
                float animatedFraction = ofFloat.getAnimatedFraction();
                if (MediaHierarchyManager.access$isCrossFadeAnimatorRunning$p(MediaHierarchyManager.this)) {
                    MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                    MediaHierarchyManager.access$setAnimationCrossFadeProgress$p(mediaHierarchyManager, MathUtils.lerp(MediaHierarchyManager.access$getAnimationStartCrossFadeProgress$p(mediaHierarchyManager), 1.0f, ofFloat.getAnimatedFraction()));
                    animatedFraction = MediaHierarchyManager.access$getAnimationCrossFadeProgress$p(MediaHierarchyManager.this) < 0.5f ? 0.0f : 1.0f;
                    MediaHierarchyManager mediaHierarchyManager2 = MediaHierarchyManager.this;
                    lerp = MediaHierarchyManager.access$calculateAlphaFromCrossFade(mediaHierarchyManager2, MediaHierarchyManager.access$getAnimationCrossFadeProgress$p(mediaHierarchyManager2));
                } else {
                    lerp = MathUtils.lerp(MediaHierarchyManager.access$getAnimationStartAlpha$p(MediaHierarchyManager.this), 1.0f, ofFloat.getAnimatedFraction());
                }
                MediaHierarchyManager mediaHierarchyManager3 = MediaHierarchyManager.this;
                MediaHierarchyManager.access$interpolateBounds(mediaHierarchyManager3, MediaHierarchyManager.access$getAnimationStartBounds$p(mediaHierarchyManager3), MediaHierarchyManager.access$getTargetBounds$p(MediaHierarchyManager.this), animatedFraction, MediaHierarchyManager.access$getCurrentBounds$p(MediaHierarchyManager.this));
                MediaHierarchyManager mediaHierarchyManager4 = MediaHierarchyManager.this;
                MediaHierarchyManager.access$resolveClipping(mediaHierarchyManager4, MediaHierarchyManager.access$getCurrentClipping$p(mediaHierarchyManager4));
                MediaHierarchyManager mediaHierarchyManager5 = MediaHierarchyManager.this;
                MediaHierarchyManager.applyState$default(mediaHierarchyManager5, MediaHierarchyManager.access$getCurrentBounds$p(mediaHierarchyManager5), lerp, false, MediaHierarchyManager.access$getCurrentClipping$p(MediaHierarchyManager.this), 4, null);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$2
            public boolean cancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.cancelled = true;
                MediaHierarchyManager.access$setAnimationPending$p(MediaHierarchyManager.this, false);
                View access$getRootView$p = MediaHierarchyManager.access$getRootView$p(MediaHierarchyManager.this);
                if (access$getRootView$p != null) {
                    access$getRootView$p.removeCallbacks(MediaHierarchyManager.access$getStartAnimation$p(MediaHierarchyManager.this));
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                MediaHierarchyManager.access$setCrossFadeAnimatorRunning$p(MediaHierarchyManager.this, false);
                if (this.cancelled) {
                    return;
                }
                MediaHierarchyManager.access$applyTargetStateIfNotAnimating(MediaHierarchyManager.this);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                this.cancelled = false;
                MediaHierarchyManager.access$setAnimationPending$p(MediaHierarchyManager.this, false);
            }
        });
        this.animator = ofFloat;
        this.mediaHosts = new MediaHost[4];
        this.previousLocation = -1;
        this.desiredLocation = -1;
        this.currentAttachmentLocation = -1;
        this.startAnimation = new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager$startAnimation$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaHierarchyManager.access$getAnimator$p(MediaHierarchyManager.this).start();
            }
        };
        this.animationCrossFadeProgress = 1.0f;
        this.carouselAlpha = 1.0f;
        updateConfiguration();
        configurationController.addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager.1
            {
                MediaHierarchyManager.this = this;
            }

            public void onConfigChanged(Configuration configuration) {
                MediaHierarchyManager.this.updateConfiguration();
                MediaHierarchyManager.this.updateDesiredLocation(true, true);
            }
        });
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager.2
            {
                MediaHierarchyManager.this = this;
            }

            /* JADX WARN: Code restructure failed: missing block: B:31:0x002d, code lost:
                if ((r4 == 1.0f) == false) goto L11;
             */
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onDozeAmountChanged(float f, float f2) {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                boolean z = true;
                if (!(f == ActionBarShadowController.ELEVATION_LOW)) {
                }
                z = false;
                mediaHierarchyManager.setDozeAnimationRunning(z);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozingChanged(boolean z) {
                if (z) {
                    MediaHierarchyManager.updateDesiredLocation$default(MediaHierarchyManager.this, false, false, 3, null);
                    MediaHierarchyManager.this.setQsExpanded(false);
                    MediaHierarchyManager.this.closeGuts();
                } else {
                    MediaHierarchyManager.this.setDozeAnimationRunning(false);
                    if (MediaHierarchyManager.this.isLockScreenVisibleToUser()) {
                        MediaHierarchyManager.this.mediaCarouselController.logSmartspaceImpression(MediaHierarchyManager.this.getQsExpanded());
                    }
                }
                MediaHierarchyManager.this.mediaCarouselController.getMediaCarouselScrollHandler().setVisibleToUser(MediaHierarchyManager.this.isVisibleToUser());
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onExpandedChanged(boolean z) {
                if (MediaHierarchyManager.this.isHomeScreenShadeVisibleToUser()) {
                    MediaHierarchyManager.this.mediaCarouselController.logSmartspaceImpression(MediaHierarchyManager.this.getQsExpanded());
                }
                MediaHierarchyManager.this.mediaCarouselController.getMediaCarouselScrollHandler().setVisibleToUser(MediaHierarchyManager.this.isVisibleToUser());
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStateChanged(int i) {
                MediaHierarchyManager.this.updateTargetState();
                if (i == 2 && MediaHierarchyManager.this.isLockScreenShadeVisibleToUser()) {
                    MediaHierarchyManager.this.mediaCarouselController.logSmartspaceImpression(MediaHierarchyManager.this.getQsExpanded());
                }
                MediaHierarchyManager.this.mediaCarouselController.getMediaCarouselScrollHandler().setVisibleToUser(MediaHierarchyManager.this.isVisibleToUser());
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStatePreChange(int i, int i2) {
                MediaHierarchyManager.this.statusbarState = i2;
                MediaHierarchyManager.updateDesiredLocation$default(MediaHierarchyManager.this, false, false, 3, null);
            }
        });
        dreamOverlayStateController.addCallback(new DreamOverlayStateController.Callback() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager.3
            {
                MediaHierarchyManager.this = this;
            }

            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public void onComplicationsChanged() {
                boolean z;
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                Collection<Complication> complications = mediaHierarchyManager.dreamOverlayStateController.getComplications();
                if (!complications.isEmpty()) {
                    Iterator<T> it = complications.iterator();
                    while (true) {
                        z = false;
                        if (!it.hasNext()) {
                            break;
                        } else if (((Complication) it.next()) instanceof MediaDreamComplication) {
                            z = true;
                            break;
                        }
                    }
                } else {
                    z = false;
                }
                mediaHierarchyManager.setDreamMediaComplicationActive(z);
            }

            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public void onStateChanged() {
                MediaHierarchyManager.this.setDreamOverlayActive(MediaHierarchyManager.this.dreamOverlayStateController.isOverlayActive());
            }
        });
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager.4
            {
                MediaHierarchyManager.this = this;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onFinishedGoingToSleep() {
                MediaHierarchyManager.this.setGoingToSleep(false);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onFinishedWakingUp() {
                MediaHierarchyManager.this.setGoingToSleep(false);
                MediaHierarchyManager.this.setFullyAwake(true);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onStartedGoingToSleep() {
                MediaHierarchyManager.this.setGoingToSleep(true);
                MediaHierarchyManager.this.setFullyAwake(false);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onStartedWakingUp() {
                MediaHierarchyManager.this.setGoingToSleep(false);
            }
        });
        mediaCarouselController.setUpdateUserVisibility(new Function0<Unit>() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                MediaHierarchyManager.this = this;
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m3265invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke */
            public final void m3265invoke() {
                MediaHierarchyManager.this.mediaCarouselController.getMediaCarouselScrollHandler().setVisibleToUser(MediaHierarchyManager.this.isVisibleToUser());
            }
        });
        mediaCarouselController.setUpdateHostVisibility(new Function0<Unit>() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                MediaHierarchyManager.this = this;
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m3266invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke */
            public final void m3266invoke() {
                MediaHost[] mediaHostArr;
                for (MediaHost mediaHost : MediaHierarchyManager.this.mediaHosts) {
                    if (mediaHost != null) {
                        mediaHost.updateViewVisibility();
                    }
                }
            }
        });
        shadeStateEvents.addShadeStateEventsListener(new ShadeStateEvents.ShadeStateEventsListener() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager.7
            {
                MediaHierarchyManager.this = this;
            }

            public void onExpandImmediateChanged(boolean z) {
                MediaHierarchyManager.this.skipQqsOnExpansion = z;
                MediaHierarchyManager.updateDesiredLocation$default(MediaHierarchyManager.this, false, false, 3, null);
            }
        });
        secureSettings.registerContentObserverForUser("media_controls_lock_screen", new ContentObserver(handler) { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager$settingsObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                if (Intrinsics.areEqual(uri, MediaHierarchyManager.access$getLockScreenMediaPlayerUri$p(MediaHierarchyManager.this))) {
                    MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                    MediaHierarchyManager.access$setAllowMediaPlayerOnLockScreen$p(mediaHierarchyManager, MediaHierarchyManager.access$getSecureSettings$p(mediaHierarchyManager).getBoolForUser("media_controls_lock_screen", true, -2));
                }
            }
        }, -1);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$applyTargetStateIfNotAnimating(MediaHierarchyManager mediaHierarchyManager) {
        mediaHierarchyManager.applyTargetStateIfNotAnimating();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ float access$calculateAlphaFromCrossFade(MediaHierarchyManager mediaHierarchyManager, float f) {
        return mediaHierarchyManager.calculateAlphaFromCrossFade(f);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ float access$getAnimationCrossFadeProgress$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.animationCrossFadeProgress;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ float access$getAnimationStartAlpha$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.animationStartAlpha;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ Rect access$getAnimationStartBounds$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.animationStartBounds;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ float access$getAnimationStartCrossFadeProgress$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.animationStartCrossFadeProgress;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$startAnimation$1.run():void] */
    public static final /* synthetic */ ValueAnimator access$getAnimator$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.animator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ Rect access$getCurrentBounds$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.currentBounds;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ Rect access$getCurrentClipping$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.currentClipping;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$settingsObserver$1.onChange(boolean, android.net.Uri):void] */
    public static final /* synthetic */ Uri access$getLockScreenMediaPlayerUri$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.lockScreenMediaPlayerUri;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$createUniqueObjectHost$1.onViewAttachedToWindow(android.view.View):void] */
    public static final /* synthetic */ ViewGroupOverlay access$getRootOverlay$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.rootOverlay;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$2.onAnimationCancel(android.animation.Animator):void, com.android.systemui.media.controls.ui.MediaHierarchyManager$createUniqueObjectHost$1.onViewAttachedToWindow(android.view.View):void] */
    public static final /* synthetic */ View access$getRootView$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.rootView;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$settingsObserver$1.onChange(boolean, android.net.Uri):void] */
    public static final /* synthetic */ SecureSettings access$getSecureSettings$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.secureSettings;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$2.onAnimationCancel(android.animation.Animator):void] */
    public static final /* synthetic */ Runnable access$getStartAnimation$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.startAnimation;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ Rect access$getTargetBounds$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.targetBounds;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ Rect access$interpolateBounds(MediaHierarchyManager mediaHierarchyManager, Rect rect, Rect rect2, float f, Rect rect3) {
        return mediaHierarchyManager.interpolateBounds(rect, rect2, f, rect3);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ boolean access$isCrossFadeAnimatorRunning$p(MediaHierarchyManager mediaHierarchyManager) {
        return mediaHierarchyManager.isCrossFadeAnimatorRunning;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$resolveClipping(MediaHierarchyManager mediaHierarchyManager, Rect rect) {
        mediaHierarchyManager.resolveClipping(rect);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$settingsObserver$1.onChange(boolean, android.net.Uri):void] */
    public static final /* synthetic */ void access$setAllowMediaPlayerOnLockScreen$p(MediaHierarchyManager mediaHierarchyManager, boolean z) {
        mediaHierarchyManager.allowMediaPlayerOnLockScreen = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$setAnimationCrossFadeProgress$p(MediaHierarchyManager mediaHierarchyManager, float f) {
        mediaHierarchyManager.animationCrossFadeProgress = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$2.onAnimationCancel(android.animation.Animator):void, com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$2.onAnimationStart(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setAnimationPending$p(MediaHierarchyManager mediaHierarchyManager, boolean z) {
        mediaHierarchyManager.animationPending = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setCrossFadeAnimatorRunning$p(MediaHierarchyManager mediaHierarchyManager, boolean z) {
        mediaHierarchyManager.isCrossFadeAnimatorRunning = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$createUniqueObjectHost$1.onViewAttachedToWindow(android.view.View):void] */
    public static final /* synthetic */ void access$setRootOverlay$p(MediaHierarchyManager mediaHierarchyManager, ViewGroupOverlay viewGroupOverlay) {
        mediaHierarchyManager.rootOverlay = viewGroupOverlay;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$createUniqueObjectHost$1.onViewAttachedToWindow(android.view.View):void] */
    public static final /* synthetic */ void access$setRootView$p(MediaHierarchyManager mediaHierarchyManager, View view) {
        mediaHierarchyManager.rootView = view;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHierarchyManager$animator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$updateTargetState(MediaHierarchyManager mediaHierarchyManager) {
        mediaHierarchyManager.updateTargetState();
    }

    public static /* synthetic */ void applyState$default(MediaHierarchyManager mediaHierarchyManager, Rect rect, float f, boolean z, Rect rect2, int i, Object obj) {
        Rect rect3;
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            rect3 = MediaHierarchyManagerKt.EMPTY_RECT;
            rect2 = rect3;
        }
        mediaHierarchyManager.applyState(rect, f, z, rect2);
    }

    public static /* synthetic */ Rect interpolateBounds$default(MediaHierarchyManager mediaHierarchyManager, Rect rect, Rect rect2, float f, Rect rect3, int i, Object obj) {
        if ((i & 8) != 0) {
            rect3 = null;
        }
        return mediaHierarchyManager.interpolateBounds(rect, rect2, f, rect3);
    }

    public static /* synthetic */ void updateDesiredLocation$default(MediaHierarchyManager mediaHierarchyManager, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        mediaHierarchyManager.updateDesiredLocation(z, z2);
    }

    public final void adjustAnimatorForTransition(int i, int i2) {
        Pair<Long, Long> animationParams = getAnimationParams(i2, i);
        long longValue = ((Number) animationParams.component1()).longValue();
        long longValue2 = ((Number) animationParams.component2()).longValue();
        ValueAnimator valueAnimator = this.animator;
        valueAnimator.setDuration(longValue);
        valueAnimator.setStartDelay(longValue2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:111:0x010a, code lost:
        if (isCurrentlyFading() != false) goto L60;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyState(Rect rect, float f, boolean z, Rect rect2) {
        boolean z2;
        int i = -1;
        boolean z3 = false;
        float f2 = 1.0f;
        if (!Trace.isTagEnabled(4096L)) {
            this.currentBounds.set(rect);
            this.currentClipping = rect2;
            if (!isCurrentlyFading()) {
                f = 1.0f;
            }
            setCarouselAlpha(f);
            if (isCurrentlyInGuidedTransformation()) {
                z2 = false;
            }
            z2 = true;
            if (!z2) {
                i = this.previousLocation;
            }
            if (!z2) {
                f2 = getTransformationProgress();
            }
            this.mediaCarouselController.setCurrentState(i, resolveLocationForFading(), f2, z);
            updateHostAttachment();
            if (this.currentAttachmentLocation == -1000) {
                if (!this.currentClipping.isEmpty()) {
                    this.currentBounds.intersect(this.currentClipping);
                }
                ViewGroup mediaFrame = getMediaFrame();
                Rect rect3 = this.currentBounds;
                mediaFrame.setLeftTopRightBottom(rect3.left, rect3.top, rect3.right, rect3.bottom);
                return;
            }
            return;
        }
        Trace.traceBegin(4096L, "MediaHierarchyManager#applyState");
        try {
            this.currentBounds.set(rect);
            this.currentClipping = rect2;
            if (!isCurrentlyFading()) {
                f = 1.0f;
            }
            setCarouselAlpha(f);
            if (!isCurrentlyInGuidedTransformation() || isCurrentlyFading()) {
                z3 = true;
            }
            if (!z3) {
                i = this.previousLocation;
            }
            if (!z3) {
                f2 = getTransformationProgress();
            }
            this.mediaCarouselController.setCurrentState(i, resolveLocationForFading(), f2, z);
            updateHostAttachment();
            if (this.currentAttachmentLocation == -1000) {
                if (!this.currentClipping.isEmpty()) {
                    this.currentBounds.intersect(this.currentClipping);
                }
                ViewGroup mediaFrame2 = getMediaFrame();
                Rect rect4 = this.currentBounds;
                mediaFrame2.setLeftTopRightBottom(rect4.left, rect4.top, rect4.right, rect4.bottom);
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final void applyTargetStateIfNotAnimating() {
        if (this.animator.isRunning()) {
            return;
        }
        applyState$default(this, this.targetBounds, this.carouselAlpha, false, this.targetClipping, 4, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x003f, code lost:
        if ((r0 != null && r0.getVisible()) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean areGuidedTransitionHostsVisible() {
        MediaHost host = getHost(this.previousLocation);
        boolean z = true;
        if (host != null && host.getVisible()) {
            MediaHost host2 = getHost(this.desiredLocation);
        }
        z = false;
        return z;
    }

    public final float calculateAlphaFromCrossFade(float f) {
        return f <= 0.5f ? 1.0f - (f / 0.5f) : (f - 0.5f) / 0.5f;
    }

    public final int calculateLocation() {
        int i;
        if (getBlockLocationChanges()) {
            return this.desiredLocation;
        }
        boolean z = true;
        boolean z2 = !this.bypassController.getBypassEnabled() && this.statusbarState == 1;
        if (this.dreamOverlayActive && this.dreamMediaComplicationActive) {
            i = 3;
        } else {
            float f = this.qsExpansion;
            i = (((f > ActionBarShadowController.ELEVATION_LOW || this.inSplitShade) && !z2) || (f > 0.4f && z2) || !getHasActiveMedia() || (z2 && isSplitShadeExpanding())) ? 0 : (!(z2 && isTransformingToFullShadeAndInQQS()) && z2 && this.allowMediaPlayerOnLockScreen) ? 2 : 1;
        }
        if (i == 2) {
            MediaHost host = getHost(i);
            if (host == null || !host.getVisible()) {
                z = false;
            }
            if (!z && !this.statusBarStateController.isDozing()) {
                return 0;
            }
        }
        if (i == 2 && this.desiredLocation == 0 && this.collapsingShadeFromQS) {
            return 0;
        }
        if (i == 2 || this.desiredLocation != 2 || this.fullyAwake) {
            if (this.skipQqsOnExpansion) {
                return 0;
            }
            return i;
        }
        return 2;
    }

    public final int calculateTransformationType() {
        if (isTransitioningToFullShade()) {
            return (this.inSplitShade && areGuidedTransitionHostsVisible()) ? 0 : 1;
        }
        int i = this.previousLocation;
        if (i == 2 && this.desiredLocation == 0) {
            return 1;
        }
        if (i == 0 && this.desiredLocation == 2) {
            return 1;
        }
        return (i == 2 && this.desiredLocation == 1) ? 1 : 0;
    }

    public final void cancelAnimationAndApplyDesiredState() {
        this.animator.cancel();
        MediaHost host = getHost(this.desiredLocation);
        if (host != null) {
            applyState$default(this, host.getCurrentBounds(), 1.0f, true, null, 8, null);
        }
    }

    public final void closeGuts() {
        MediaCarouselController.closeGuts$default(this.mediaCarouselController, false, 1, null);
    }

    public final UniqueObjectHostView createUniqueObjectHost() {
        final UniqueObjectHostView uniqueObjectHostView = new UniqueObjectHostView(this.context);
        uniqueObjectHostView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager$createUniqueObjectHost$1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                if (MediaHierarchyManager.access$getRootOverlay$p(MediaHierarchyManager.this) == null) {
                    MediaHierarchyManager.access$setRootView$p(MediaHierarchyManager.this, uniqueObjectHostView.getViewRootImpl().getView());
                    MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                    View access$getRootView$p = MediaHierarchyManager.access$getRootView$p(mediaHierarchyManager);
                    Intrinsics.checkNotNull(access$getRootView$p);
                    MediaHierarchyManager.access$setRootOverlay$p(mediaHierarchyManager, (ViewGroupOverlay) access$getRootView$p.getOverlay());
                }
                uniqueObjectHostView.removeOnAttachStateChangeListener(this);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
            }
        });
        return uniqueObjectHostView;
    }

    public final Pair<Long, Long> getAnimationParams(int i, int i2) {
        long j;
        long j2 = 0;
        if (i == 2 && i2 == 1) {
            long j3 = 0;
            if (this.statusbarState == 0) {
                j3 = 0;
                if (this.keyguardStateController.isKeyguardFadingAway()) {
                    j3 = this.keyguardStateController.getKeyguardFadingAwayDelay();
                }
            }
            j2 = j3;
            j = 224;
        } else {
            j = (i == 1 && i2 == 2) ? 464L : 200L;
        }
        return TuplesKt.to(Long.valueOf(j), Long.valueOf(j2));
    }

    public final boolean getBlockLocationChanges() {
        return this.goingToSleep || this.dozeAnimationRunning;
    }

    public final int getGuidedTransformationTranslationY() {
        if (isCurrentlyInGuidedTransformation()) {
            MediaHost host = getHost(this.previousLocation);
            if (host == null) {
                return 0;
            }
            return this.targetBounds.top - host.getCurrentBounds().top;
        }
        return -1;
    }

    public final boolean getHasActiveMedia() {
        boolean z = true;
        MediaHost mediaHost = this.mediaHosts[1];
        if (mediaHost == null || !mediaHost.getVisible()) {
            z = false;
        }
        return z;
    }

    public final MediaHost getHost(int i) {
        if (i < 0) {
            return null;
        }
        return this.mediaHosts[i];
    }

    public final ViewGroup getMediaFrame() {
        return this.mediaCarouselController.getMediaFrame();
    }

    public final float getQSTransformationProgress() {
        MediaHost host = getHost(this.desiredLocation);
        MediaHost host2 = getHost(this.previousLocation);
        if (getHasActiveMedia()) {
            if (!(host != null && host.getLocation() == 0) || this.inSplitShade) {
                return -1.0f;
            }
            boolean z = false;
            if (host2 != null) {
                z = false;
                if (host2.getLocation() == 1) {
                    z = true;
                }
            }
            if (z) {
                if (host2.getVisible() || this.statusbarState != 1) {
                    return this.qsExpansion;
                }
                return -1.0f;
            }
            return -1.0f;
        }
        return -1.0f;
    }

    public final boolean getQsExpanded() {
        return this.qsExpanded;
    }

    public final float getTransformationProgress() {
        if (this.skipQqsOnExpansion) {
            return -1.0f;
        }
        float qSTransformationProgress = getQSTransformationProgress();
        if (this.statusbarState == 1 || qSTransformationProgress < ActionBarShadowController.ELEVATION_LOW) {
            if (isTransitioningToFullShade()) {
                return this.fullShadeTransitionProgress;
            }
            return -1.0f;
        }
        return qSTransformationProgress;
    }

    public final boolean hasValidStartAndEndLocations() {
        return (this.previousLocation == -1 || this.desiredLocation == -1) ? false : true;
    }

    public final Rect interpolateBounds(Rect rect, Rect rect2, float f, Rect rect3) {
        int lerp = (int) MathUtils.lerp(rect.left, rect2.left, f);
        int lerp2 = (int) MathUtils.lerp(rect.top, rect2.top, f);
        int lerp3 = (int) MathUtils.lerp(rect.right, rect2.right, f);
        int lerp4 = (int) MathUtils.lerp(rect.bottom, rect2.bottom, f);
        Rect rect4 = rect3;
        if (rect3 == null) {
            rect4 = new Rect();
        }
        rect4.set(lerp, lerp2, lerp3, lerp4);
        return rect4;
    }

    public final boolean isCurrentlyFading() {
        if (isSplitShadeExpanding()) {
            return false;
        }
        if (isTransitioningToFullShade()) {
            return true;
        }
        return this.isCrossFadeAnimatorRunning;
    }

    public final boolean isCurrentlyInGuidedTransformation() {
        return hasValidStartAndEndLocations() && getTransformationProgress() >= ActionBarShadowController.ELEVATION_LOW && areGuidedTransitionHostsVisible();
    }

    public final boolean isHomeScreenShadeVisibleToUser() {
        return !this.statusBarStateController.isDozing() && this.statusBarStateController.getState() == 0 && this.statusBarStateController.isExpanded();
    }

    public final boolean isLockScreenShadeVisibleToUser() {
        boolean z;
        if (!this.statusBarStateController.isDozing() && !this.keyguardViewController.isBouncerShowing()) {
            z = true;
            if (this.statusBarStateController.getState() != 2) {
                if (this.statusBarStateController.getState() == 1 && this.qsExpanded) {
                    z = true;
                }
            }
            return z;
        }
        z = false;
        return z;
    }

    public final boolean isLockScreenVisibleToUser() {
        boolean z = true;
        if (this.statusBarStateController.isDozing() || this.keyguardViewController.isBouncerShowing() || this.statusBarStateController.getState() != 1 || !this.allowMediaPlayerOnLockScreen || !this.statusBarStateController.isExpanded() || this.qsExpanded) {
            z = false;
        }
        return z;
    }

    public final boolean isSplitShadeExpanding() {
        return this.inSplitShade && isTransitioningToFullShade();
    }

    public final boolean isTransformingToFullShadeAndInQQS() {
        boolean z = false;
        if (isTransitioningToFullShade() && !this.inSplitShade) {
            if (this.fullShadeTransitionProgress > 0.5f) {
                z = true;
            }
            return z;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x001c, code lost:
        if ((getTransformationProgress() == 1.0f) != false) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x002d, code lost:
        if (r3.animationPending == false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isTransitionRunning() {
        boolean z = false;
        if (isCurrentlyInGuidedTransformation()) {
        }
        if (!this.animator.isRunning()) {
        }
        z = true;
        return z;
    }

    public final boolean isTransitioningToFullShade() {
        boolean z = false;
        if (!(this.fullShadeTransitionProgress == ActionBarShadowController.ELEVATION_LOW)) {
            z = false;
            if (!this.bypassController.getBypassEnabled()) {
                z = false;
                if (this.statusbarState == 1) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final boolean isVisibleToUser() {
        return isLockScreenVisibleToUser() || isLockScreenShadeVisibleToUser() || isHomeScreenShadeVisibleToUser();
    }

    /* JADX WARN: Removed duplicated region for block: B:156:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x00d8 A[Catch: all -> 0x019b, TRY_ENTER, TryCatch #0 {all -> 0x019b, blocks: (B:130:0x0020, B:135:0x002e, B:140:0x004f, B:142:0x005a, B:181:0x0177, B:181:0x0177, B:145:0x0065, B:147:0x0082, B:150:0x0090, B:154:0x00c4, B:158:0x00ce, B:160:0x00d8, B:175:0x0123, B:177:0x0157, B:179:0x0162, B:165:0x00f0, B:172:0x0115, B:151:0x00ab, B:180:0x0173, B:184:0x0185, B:187:0x0190), top: B:244:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0157 A[Catch: all -> 0x019b, TRY_LEAVE, TryCatch #0 {all -> 0x019b, blocks: (B:130:0x0020, B:135:0x002e, B:140:0x004f, B:142:0x005a, B:181:0x0177, B:181:0x0177, B:145:0x0065, B:147:0x0082, B:150:0x0090, B:154:0x00c4, B:158:0x00ce, B:160:0x00d8, B:175:0x0123, B:177:0x0157, B:179:0x0162, B:165:0x00f0, B:172:0x0115, B:151:0x00ab, B:180:0x0173, B:184:0x0185, B:187:0x0190), top: B:244:0x0020 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void performTransitionToNewLocation(boolean z, boolean z2) {
        float f;
        int i;
        View view;
        int i2;
        View view2;
        float f2 = 0.0f;
        if (!Trace.isTagEnabled(4096L)) {
            if (this.previousLocation < 0 || z) {
                cancelAnimationAndApplyDesiredState();
                return;
            }
            MediaHost host = getHost(this.desiredLocation);
            MediaHost host2 = getHost(this.previousLocation);
            if (host == null || host2 == null) {
                cancelAnimationAndApplyDesiredState();
                return;
            }
            updateTargetState();
            if (isCurrentlyInGuidedTransformation()) {
                applyTargetStateIfNotAnimating();
                return;
            } else if (!z2) {
                cancelAnimationAndApplyDesiredState();
                return;
            } else {
                boolean z3 = this.isCrossFadeAnimatorRunning;
                float f3 = this.animationCrossFadeProgress;
                this.animator.cancel();
                if (this.currentAttachmentLocation == this.previousLocation && host2.getHostView().isAttachedToWindow()) {
                    this.animationStartBounds.set(host2.getCurrentBounds());
                    this.animationStartClipping.set(host2.getCurrentClipping());
                } else {
                    this.animationStartBounds.set(this.currentBounds);
                    this.animationStartClipping.set(this.currentClipping);
                }
                boolean z4 = false;
                if (calculateTransformationType() == 1) {
                    z4 = true;
                }
                int i3 = this.previousLocation;
                if (!z3) {
                    f = 0.0f;
                    if (z4) {
                        f = (1.0f - this.carouselAlpha) / 2.0f;
                    }
                } else if (this.currentAttachmentLocation != this.crossFadeAnimationEndLocation) {
                    i = this.crossFadeAnimationStartLocation;
                    if (i == this.desiredLocation) {
                        f = 1.0f - f3;
                    } else {
                        f = f3;
                        z4 = true;
                    }
                    this.isCrossFadeAnimatorRunning = z4;
                    this.crossFadeAnimationStartLocation = i;
                    int i4 = this.desiredLocation;
                    this.crossFadeAnimationEndLocation = i4;
                    this.animationStartAlpha = this.carouselAlpha;
                    this.animationStartCrossFadeProgress = f;
                    adjustAnimatorForTransition(i4, i3);
                    if (!this.animationPending || (view = this.rootView) == null) {
                        return;
                    }
                    this.animationPending = true;
                    view.postOnAnimation(this.startAnimation);
                    return;
                } else {
                    f = 0.0f;
                    if (z4) {
                        f = 1.0f - f3;
                    }
                }
                i = i3;
                this.isCrossFadeAnimatorRunning = z4;
                this.crossFadeAnimationStartLocation = i;
                int i42 = this.desiredLocation;
                this.crossFadeAnimationEndLocation = i42;
                this.animationStartAlpha = this.carouselAlpha;
                this.animationStartCrossFadeProgress = f;
                adjustAnimatorForTransition(i42, i3);
                if (this.animationPending) {
                    return;
                }
                return;
            }
        }
        Trace.traceBegin(4096L, "MediaHierarchyManager#performTransitionToNewLocation");
        try {
            if (this.previousLocation < 0 || z) {
                cancelAnimationAndApplyDesiredState();
                Trace.traceEnd(4096L);
                return;
            }
            MediaHost host3 = getHost(this.desiredLocation);
            MediaHost host4 = getHost(this.previousLocation);
            if (host3 == null || host4 == null) {
                cancelAnimationAndApplyDesiredState();
                Trace.traceEnd(4096L);
                return;
            }
            updateTargetState();
            if (isCurrentlyInGuidedTransformation()) {
                applyTargetStateIfNotAnimating();
            } else if (z2) {
                boolean z5 = this.isCrossFadeAnimatorRunning;
                float f4 = this.animationCrossFadeProgress;
                this.animator.cancel();
                if (this.currentAttachmentLocation == this.previousLocation && host4.getHostView().isAttachedToWindow()) {
                    this.animationStartBounds.set(host4.getCurrentBounds());
                    this.animationStartClipping.set(host4.getCurrentClipping());
                    boolean z6 = false;
                    if (calculateTransformationType() == 1) {
                        z6 = true;
                    }
                    int i5 = this.previousLocation;
                    if (z5) {
                        if (z6) {
                            f2 = (1.0f - this.carouselAlpha) / 2.0f;
                        }
                    } else if (this.currentAttachmentLocation != this.crossFadeAnimationEndLocation) {
                        i2 = this.crossFadeAnimationStartLocation;
                        if (i2 == this.desiredLocation) {
                            f2 = 1.0f - f4;
                        } else {
                            f2 = f4;
                            z6 = true;
                        }
                        this.isCrossFadeAnimatorRunning = z6;
                        this.crossFadeAnimationStartLocation = i2;
                        int i6 = this.desiredLocation;
                        this.crossFadeAnimationEndLocation = i6;
                        this.animationStartAlpha = this.carouselAlpha;
                        this.animationStartCrossFadeProgress = f2;
                        adjustAnimatorForTransition(i6, i5);
                        if (!this.animationPending && (view2 = this.rootView) != null) {
                            this.animationPending = true;
                            view2.postOnAnimation(this.startAnimation);
                        }
                    } else if (z6) {
                        f2 = 1.0f - f4;
                    }
                    i2 = i5;
                    this.isCrossFadeAnimatorRunning = z6;
                    this.crossFadeAnimationStartLocation = i2;
                    int i62 = this.desiredLocation;
                    this.crossFadeAnimationEndLocation = i62;
                    this.animationStartAlpha = this.carouselAlpha;
                    this.animationStartCrossFadeProgress = f2;
                    adjustAnimatorForTransition(i62, i5);
                    if (!this.animationPending) {
                        this.animationPending = true;
                        view2.postOnAnimation(this.startAnimation);
                    }
                }
                this.animationStartBounds.set(this.currentBounds);
                this.animationStartClipping.set(this.currentClipping);
                boolean z62 = false;
                if (calculateTransformationType() == 1) {
                }
                int i52 = this.previousLocation;
                if (z5) {
                }
                i2 = i52;
                this.isCrossFadeAnimatorRunning = z62;
                this.crossFadeAnimationStartLocation = i2;
                int i622 = this.desiredLocation;
                this.crossFadeAnimationEndLocation = i622;
                this.animationStartAlpha = this.carouselAlpha;
                this.animationStartCrossFadeProgress = f2;
                adjustAnimatorForTransition(i622, i52);
                if (!this.animationPending) {
                }
            } else {
                cancelAnimationAndApplyDesiredState();
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final UniqueObjectHostView register(final MediaHost mediaHost) {
        UniqueObjectHostView createUniqueObjectHost = createUniqueObjectHost();
        mediaHost.setHostView(createUniqueObjectHost);
        mediaHost.addVisibilityChangeListener(new Function1<Boolean, Unit>() { // from class: com.android.systemui.media.controls.ui.MediaHierarchyManager$register$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Boolean) obj).booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z) {
                this.updateDesiredLocation(true, MediaHost.this.getLocation() == 1);
            }
        });
        this.mediaHosts[mediaHost.getLocation()] = mediaHost;
        if (mediaHost.getLocation() == this.desiredLocation) {
            this.desiredLocation = -1;
        }
        if (mediaHost.getLocation() == this.currentAttachmentLocation) {
            this.currentAttachmentLocation = -1;
        }
        updateDesiredLocation$default(this, false, false, 3, null);
        return createUniqueObjectHost;
    }

    public final void resolveClipping(Rect rect) {
        if (this.animationStartClipping.isEmpty()) {
            rect.set(this.targetClipping);
        } else if (this.targetClipping.isEmpty()) {
            rect.set(this.animationStartClipping);
        } else {
            rect.setIntersect(this.animationStartClipping, this.targetClipping);
        }
    }

    public final int resolveLocationForFading() {
        return this.isCrossFadeAnimatorRunning ? (((double) this.animationCrossFadeProgress) > 0.5d || this.previousLocation == -1) ? this.crossFadeAnimationEndLocation : this.crossFadeAnimationStartLocation : this.desiredLocation;
    }

    public final void setCarouselAlpha(float f) {
        if (this.carouselAlpha == f) {
            return;
        }
        this.carouselAlpha = f;
        CrossFadeHelper.fadeIn(getMediaFrame(), f);
    }

    public final void setCollapsingShadeFromQS(boolean z) {
        if (this.collapsingShadeFromQS != z) {
            this.collapsingShadeFromQS = z;
            updateDesiredLocation$default(this, true, false, 2, null);
        }
    }

    public final void setDozeAnimationRunning(boolean z) {
        if (this.dozeAnimationRunning != z) {
            this.dozeAnimationRunning = z;
            if (z) {
                return;
            }
            updateDesiredLocation$default(this, false, false, 3, null);
        }
    }

    public final void setDreamMediaComplicationActive(boolean z) {
        if (this.dreamMediaComplicationActive != z) {
            this.dreamMediaComplicationActive = z;
            updateDesiredLocation$default(this, true, false, 2, null);
        }
    }

    public final void setDreamOverlayActive(boolean z) {
        if (this.dreamOverlayActive != z) {
            this.dreamOverlayActive = z;
            updateDesiredLocation$default(this, true, false, 2, null);
        }
    }

    public final void setFullShadeTransitionProgress(float f) {
        if (this.fullShadeTransitionProgress == f) {
            return;
        }
        this.fullShadeTransitionProgress = f;
        if (this.bypassController.getBypassEnabled() || this.statusbarState != 1) {
            return;
        }
        updateDesiredLocation$default(this, isCurrentlyFading(), false, 2, null);
        if (f >= ActionBarShadowController.ELEVATION_LOW) {
            updateTargetState();
            setCarouselAlpha(calculateAlphaFromCrossFade(this.fullShadeTransitionProgress));
            applyTargetStateIfNotAnimating();
        }
    }

    public final void setFullyAwake(boolean z) {
        if (this.fullyAwake != z) {
            this.fullyAwake = z;
            if (z) {
                updateDesiredLocation$default(this, true, false, 2, null);
            }
        }
    }

    public final void setGoingToSleep(boolean z) {
        if (this.goingToSleep != z) {
            this.goingToSleep = z;
            if (z) {
                return;
            }
            updateDesiredLocation$default(this, false, false, 3, null);
        }
    }

    public final void setQsExpanded(boolean z) {
        if (this.qsExpanded != z) {
            this.qsExpanded = z;
            this.mediaCarouselController.getMediaCarouselScrollHandler().setQsExpanded(z);
        }
        if (z && (isLockScreenShadeVisibleToUser() || isHomeScreenShadeVisibleToUser())) {
            this.mediaCarouselController.logSmartspaceImpression(z);
        }
        this.mediaCarouselController.getMediaCarouselScrollHandler().setVisibleToUser(isVisibleToUser());
    }

    public final void setQsExpansion(float f) {
        if (this.qsExpansion == f) {
            return;
        }
        this.qsExpansion = f;
        updateDesiredLocation$default(this, false, false, 3, null);
        if (getQSTransformationProgress() >= ActionBarShadowController.ELEVATION_LOW) {
            updateTargetState();
            applyTargetStateIfNotAnimating();
        }
    }

    public final void setTransitionToFullShadeAmount(float f) {
        setFullShadeTransitionProgress(MathUtils.saturate(f / this.distanceForFullShadeTransition));
    }

    public final boolean shouldAnimateTransition(int i, int i2) {
        boolean z = false;
        if (isCurrentlyInGuidedTransformation() || this.skipQqsOnExpansion) {
            return false;
        }
        if (i2 == 2 && this.desiredLocation == 1 && this.statusbarState == 0) {
            return false;
        }
        if (i == 1 && i2 == 2 && (this.statusBarStateController.leaveOpenOnKeyguardHide() || this.statusbarState == 2)) {
            return true;
        }
        if (this.desiredLocation == 0 && i2 == 2 && this.statusbarState == 0) {
            return false;
        }
        if (this.statusbarState == 1 && (i == 2 || i2 == 2)) {
            return false;
        }
        if (MediaHierarchyManagerKt.isShownNotFaded(getMediaFrame()) || this.animator.isRunning() || this.animationPending) {
            z = true;
        }
        return z;
    }

    public final void updateConfiguration() {
        this.distanceForFullShadeTransition = this.context.getResources().getDimensionPixelSize(R$dimen.lockscreen_shade_media_transition_distance);
        this.inSplitShade = LargeScreenUtils.shouldUseSplitNotificationShade(this.context.getResources());
    }

    public final void updateDesiredLocation(boolean z, boolean z2) {
        if (!Trace.isTagEnabled(4096L)) {
            int calculateLocation = calculateLocation();
            int i = this.desiredLocation;
            if (calculateLocation != i || z2) {
                if (i >= 0 && calculateLocation != i) {
                    this.previousLocation = i;
                } else if (z2) {
                    boolean z3 = !this.bypassController.getBypassEnabled() && this.statusbarState == 1;
                    if (calculateLocation == 0 && this.previousLocation == 2 && !z3) {
                        this.previousLocation = 1;
                    }
                }
                boolean z4 = this.desiredLocation == -1;
                this.desiredLocation = calculateLocation;
                boolean z5 = !z && shouldAnimateTransition(calculateLocation, this.previousLocation);
                Pair<Long, Long> animationParams = getAnimationParams(this.previousLocation, calculateLocation);
                long longValue = ((Number) animationParams.component1()).longValue();
                long longValue2 = ((Number) animationParams.component2()).longValue();
                MediaHost host = getHost(calculateLocation);
                boolean z6 = false;
                if (calculateTransformationType() == 1) {
                    z6 = true;
                }
                if (!z6 || isCurrentlyInGuidedTransformation() || !z5) {
                    this.mediaCarouselController.onDesiredLocationChanged(calculateLocation, host, z5, longValue, longValue2);
                }
                performTransitionToNewLocation(z4, z5);
                return;
            }
            return;
        }
        Trace.traceBegin(4096L, "MediaHierarchyManager#updateDesiredLocation");
        try {
            int calculateLocation2 = calculateLocation();
            int i2 = this.desiredLocation;
            if (calculateLocation2 != i2 || z2) {
                if (i2 >= 0 && calculateLocation2 != i2) {
                    this.previousLocation = i2;
                } else if (z2) {
                    boolean z7 = !this.bypassController.getBypassEnabled() && this.statusbarState == 1;
                    if (calculateLocation2 == 0 && this.previousLocation == 2 && !z7) {
                        this.previousLocation = 1;
                    }
                }
                boolean z8 = this.desiredLocation == -1;
                this.desiredLocation = calculateLocation2;
                boolean z9 = !z && shouldAnimateTransition(calculateLocation2, this.previousLocation);
                Pair<Long, Long> animationParams2 = getAnimationParams(this.previousLocation, calculateLocation2);
                long longValue3 = ((Number) animationParams2.component1()).longValue();
                long longValue4 = ((Number) animationParams2.component2()).longValue();
                MediaHost host2 = getHost(calculateLocation2);
                boolean z10 = false;
                if (calculateTransformationType() == 1) {
                    z10 = true;
                }
                if (!z10 || isCurrentlyInGuidedTransformation() || !z9) {
                    this.mediaCarouselController.onDesiredLocationChanged(calculateLocation2, host2, z9, longValue3, longValue4);
                }
                performTransitionToNewLocation(z8, z9);
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final void updateHostAttachment() {
        String str;
        UniqueObjectHostView hostView;
        String str2;
        UniqueObjectHostView hostView2;
        if (!Trace.isTagEnabled(4096L)) {
            int resolveLocationForFading = resolveLocationForFading();
            boolean z = !isCurrentlyFading();
            boolean z2 = z;
            if (this.isCrossFadeAnimatorRunning) {
                MediaHost host = getHost(resolveLocationForFading);
                z2 = z;
                if (host != null && host.getVisible()) {
                    MediaHost host2 = getHost(resolveLocationForFading);
                    z2 = z;
                    if ((host2 == null || (hostView = host2.getHostView()) == null || hostView.isShown()) ? false : true) {
                        z2 = z;
                        if (resolveLocationForFading != this.desiredLocation) {
                            z2 = true;
                        }
                    }
                }
            }
            boolean z3 = isTransitionRunning() && this.rootOverlay != null && z2;
            int i = z3 ? -1000 : resolveLocationForFading;
            if (this.currentAttachmentLocation != i) {
                this.currentAttachmentLocation = i;
                ViewGroup viewGroup = (ViewGroup) getMediaFrame().getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(getMediaFrame());
                }
                if (z3) {
                    ViewGroupOverlay viewGroupOverlay = this.rootOverlay;
                    Intrinsics.checkNotNull(viewGroupOverlay);
                    viewGroupOverlay.add(getMediaFrame());
                } else {
                    MediaHost host3 = getHost(i);
                    Intrinsics.checkNotNull(host3);
                    host3.getHostView().addView(getMediaFrame());
                    if (getMediaFrame().getChildCount() > 0) {
                        View childAt = getMediaFrame().getChildAt(0);
                        if (getMediaFrame().getHeight() < childAt.getHeight()) {
                            str = MediaHierarchyManagerKt.TAG;
                            Log.wtf(str, "mediaFrame height is too small for child: " + getMediaFrame().getHeight() + " vs " + childAt.getHeight());
                        }
                    }
                }
                if (this.isCrossFadeAnimatorRunning) {
                    MediaCarouselController.onDesiredLocationChanged$default(this.mediaCarouselController, i, getHost(i), false, 0L, 0L, 24, null);
                    return;
                }
                return;
            }
            return;
        }
        Trace.traceBegin(4096L, "MediaHierarchyManager#updateHostAttachment");
        try {
            int resolveLocationForFading2 = resolveLocationForFading();
            boolean z4 = !isCurrentlyFading();
            boolean z5 = z4;
            if (this.isCrossFadeAnimatorRunning) {
                MediaHost host4 = getHost(resolveLocationForFading2);
                z5 = z4;
                if (host4 != null && host4.getVisible()) {
                    MediaHost host5 = getHost(resolveLocationForFading2);
                    z5 = z4;
                    if ((host5 == null || (hostView2 = host5.getHostView()) == null || hostView2.isShown()) ? false : true) {
                        z5 = z4;
                        if (resolveLocationForFading2 != this.desiredLocation) {
                            z5 = true;
                        }
                    }
                }
            }
            boolean z6 = isTransitionRunning() && this.rootOverlay != null && z5;
            int i2 = z6 ? -1000 : resolveLocationForFading2;
            if (this.currentAttachmentLocation != i2) {
                this.currentAttachmentLocation = i2;
                ViewGroup viewGroup2 = (ViewGroup) getMediaFrame().getParent();
                if (viewGroup2 != null) {
                    viewGroup2.removeView(getMediaFrame());
                }
                if (z6) {
                    ViewGroupOverlay viewGroupOverlay2 = this.rootOverlay;
                    Intrinsics.checkNotNull(viewGroupOverlay2);
                    viewGroupOverlay2.add(getMediaFrame());
                } else {
                    MediaHost host6 = getHost(i2);
                    Intrinsics.checkNotNull(host6);
                    host6.getHostView().addView(getMediaFrame());
                    if (getMediaFrame().getChildCount() > 0) {
                        View childAt2 = getMediaFrame().getChildAt(0);
                        if (getMediaFrame().getHeight() < childAt2.getHeight()) {
                            str2 = MediaHierarchyManagerKt.TAG;
                            Log.wtf(str2, "mediaFrame height is too small for child: " + getMediaFrame().getHeight() + " vs " + childAt2.getHeight());
                        }
                    }
                }
                if (this.isCrossFadeAnimatorRunning) {
                    MediaCarouselController.onDesiredLocationChanged$default(this.mediaCarouselController, i2, getHost(i2), false, 0L, 0L, 24, null);
                }
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final void updateTargetState() {
        MediaHost host = getHost(this.previousLocation);
        MediaHost host2 = getHost(this.desiredLocation);
        if (!isCurrentlyInGuidedTransformation() || isCurrentlyFading() || host == null || host2 == null) {
            if (host2 != null) {
                this.targetBounds.set(host2.getCurrentBounds());
                this.targetClipping = host2.getCurrentClipping();
                return;
            }
            return;
        }
        float transformationProgress = getTransformationProgress();
        if (!host2.getVisible()) {
            host2 = host;
        } else if (host.getVisible()) {
            host = host2;
            host2 = host;
        } else {
            host = host2;
        }
        this.targetBounds = interpolateBounds$default(this, host2.getCurrentBounds(), host.getCurrentBounds(), transformationProgress, null, 8, null);
        this.targetClipping = host.getCurrentClipping();
    }
}