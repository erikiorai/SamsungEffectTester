package com.android.systemui.media.controls.ui;

import android.content.res.Resources;
import android.graphics.Outline;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import androidx.core.view.GestureDetectorCompat;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R$dimen;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.media.controls.models.player.MediaViewHolder;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PageIndicator;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.animation.PhysicsAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselScrollHandler.class */
public final class MediaCarouselScrollHandler {
    public int carouselHeight;
    public int carouselWidth;
    public final Function1<Boolean, Unit> closeGuts;
    public float contentTranslation;
    public int cornerRadius;
    public final Function0<Unit> dismissCallback;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public boolean falsingProtectionNeeded;
    public final GestureDetectorCompat gestureDetector;
    public final MediaCarouselScrollHandler$gestureListener$1 gestureListener;
    public final Function1<Boolean, Unit> logSmartspaceImpression;
    public final MediaUiEventLogger logger;
    public final DelayableExecutor mainExecutor;
    public ViewGroup mediaContent;
    public final PageIndicator pageIndicator;
    public int playerWidthPlusPadding;
    public boolean qsExpanded;
    public final MediaCarouselScrollHandler$scrollChangedListener$1 scrollChangedListener;
    public int scrollIntoCurrentMedia;
    public final MediaScrollView scrollView;
    public View settingsButton;
    public boolean showsSettingsButton;
    public final MediaCarouselScrollHandler$touchListener$1 touchListener;
    public Function0<Unit> translationChangedListener;
    public int visibleMediaIndex;
    public boolean visibleToUser;
    public static final Companion Companion = new Companion(null);
    public static final MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 CONTENT_TRANSLATION = new FloatPropertyCompat<MediaCarouselScrollHandler>() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
            return mediaCarouselScrollHandler.getContentTranslation();
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(MediaCarouselScrollHandler mediaCarouselScrollHandler, float f) {
            MediaCarouselScrollHandler.access$setContentTranslation(mediaCarouselScrollHandler, f);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselScrollHandler$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r12v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> */
    /* JADX DEBUG: Multi-variable search result rejected for r15v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> */
    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.media.controls.ui.MediaScrollView */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$gestureListener$1, android.view.GestureDetector$OnGestureListener] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.systemui.Gefingerpoken, com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$touchListener$1] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$scrollChangedListener$1, android.view.View$OnScrollChangeListener] */
    public MediaCarouselScrollHandler(MediaScrollView mediaScrollView, PageIndicator pageIndicator, DelayableExecutor delayableExecutor, Function0<Unit> function0, Function0<Unit> function02, Function1<? super Boolean, Unit> function1, FalsingCollector falsingCollector, FalsingManager falsingManager, Function1<? super Boolean, Unit> function12, MediaUiEventLogger mediaUiEventLogger) {
        this.scrollView = mediaScrollView;
        this.pageIndicator = pageIndicator;
        this.mainExecutor = delayableExecutor;
        this.dismissCallback = function0;
        this.translationChangedListener = function02;
        this.closeGuts = function1;
        this.falsingCollector = falsingCollector;
        this.falsingManager = falsingManager;
        this.logSmartspaceImpression = function12;
        this.logger = mediaUiEventLogger;
        ?? r0 = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$gestureListener$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                if (MediaCarouselScrollHandler.this.getFalsingProtectionNeeded()) {
                    MediaCarouselScrollHandler.access$getFalsingCollector$p(MediaCarouselScrollHandler.this).onNotificationStartDismissing();
                    return false;
                }
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                return MediaCarouselScrollHandler.access$onFling(MediaCarouselScrollHandler.this, f, f2);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                MediaCarouselScrollHandler mediaCarouselScrollHandler = MediaCarouselScrollHandler.this;
                Intrinsics.checkNotNull(motionEvent);
                Intrinsics.checkNotNull(motionEvent2);
                return mediaCarouselScrollHandler.onScroll(motionEvent, motionEvent2, f);
            }
        };
        this.gestureListener = r0;
        ?? r02 = new Gefingerpoken() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$touchListener$1
            @Override // com.android.systemui.Gefingerpoken
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                MediaCarouselScrollHandler mediaCarouselScrollHandler = MediaCarouselScrollHandler.this;
                Intrinsics.checkNotNull(motionEvent);
                return MediaCarouselScrollHandler.access$onInterceptTouch(mediaCarouselScrollHandler, motionEvent);
            }

            @Override // com.android.systemui.Gefingerpoken
            public boolean onTouchEvent(MotionEvent motionEvent) {
                MediaCarouselScrollHandler mediaCarouselScrollHandler = MediaCarouselScrollHandler.this;
                Intrinsics.checkNotNull(motionEvent);
                return MediaCarouselScrollHandler.access$onTouch(mediaCarouselScrollHandler, motionEvent);
            }
        };
        this.touchListener = r02;
        ?? r03 = new View.OnScrollChangeListener() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$scrollChangedListener$1
            @Override // android.view.View.OnScrollChangeListener
            public void onScrollChange(View view, int i, int i2, int i3, int i4) {
                if (MediaCarouselScrollHandler.this.getPlayerWidthPlusPadding() == 0) {
                    return;
                }
                int relativeScrollX = MediaCarouselScrollHandler.access$getScrollView$p(MediaCarouselScrollHandler.this).getRelativeScrollX();
                MediaCarouselScrollHandler mediaCarouselScrollHandler = MediaCarouselScrollHandler.this;
                MediaCarouselScrollHandler.access$onMediaScrollingChanged(mediaCarouselScrollHandler, relativeScrollX / mediaCarouselScrollHandler.getPlayerWidthPlusPadding(), relativeScrollX % MediaCarouselScrollHandler.this.getPlayerWidthPlusPadding());
            }
        };
        this.scrollChangedListener = r03;
        this.gestureDetector = new GestureDetectorCompat(mediaScrollView.getContext(), r0);
        mediaScrollView.setTouchListener(r02);
        mediaScrollView.setOverScrollMode(2);
        this.mediaContent = mediaScrollView.getContentContainer();
        mediaScrollView.setOnScrollChangeListener(r03);
        mediaScrollView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler.1
            {
                MediaCarouselScrollHandler.this = this;
            }

            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                if (outline != null) {
                    outline.setRoundRect(0, 0, MediaCarouselScrollHandler.this.carouselWidth, MediaCarouselScrollHandler.this.carouselHeight, MediaCarouselScrollHandler.this.cornerRadius);
                }
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$gestureListener$1.onDown(android.view.MotionEvent):boolean] */
    public static final /* synthetic */ FalsingCollector access$getFalsingCollector$p(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
        return mediaCarouselScrollHandler.falsingCollector;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$onFling$2.run():void, com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$onTouch$1.run():void, com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$scrollChangedListener$1.onScrollChange(android.view.View, int, int, int, int):void, com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$scrollToPlayer$1.run():void] */
    public static final /* synthetic */ MediaScrollView access$getScrollView$p(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
        return mediaCarouselScrollHandler.scrollView;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$gestureListener$1.onFling(android.view.MotionEvent, android.view.MotionEvent, float, float):boolean] */
    public static final /* synthetic */ boolean access$onFling(MediaCarouselScrollHandler mediaCarouselScrollHandler, float f, float f2) {
        return mediaCarouselScrollHandler.onFling(f, f2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$touchListener$1.onInterceptTouchEvent(android.view.MotionEvent):boolean] */
    public static final /* synthetic */ boolean access$onInterceptTouch(MediaCarouselScrollHandler mediaCarouselScrollHandler, MotionEvent motionEvent) {
        return mediaCarouselScrollHandler.onInterceptTouch(motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$scrollChangedListener$1.onScrollChange(android.view.View, int, int, int, int):void] */
    public static final /* synthetic */ void access$onMediaScrollingChanged(MediaCarouselScrollHandler mediaCarouselScrollHandler, int i, int i2) {
        mediaCarouselScrollHandler.onMediaScrollingChanged(i, i2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$touchListener$1.onTouchEvent(android.view.MotionEvent):boolean] */
    public static final /* synthetic */ boolean access$onTouch(MediaCarouselScrollHandler mediaCarouselScrollHandler, MotionEvent motionEvent) {
        return mediaCarouselScrollHandler.onTouch(motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1.setValue(com.android.systemui.media.controls.ui.MediaCarouselScrollHandler, float):void] */
    public static final /* synthetic */ void access$setContentTranslation(MediaCarouselScrollHandler mediaCarouselScrollHandler, float f) {
        mediaCarouselScrollHandler.setContentTranslation(f);
    }

    public static /* synthetic */ void resetTranslation$default(MediaCarouselScrollHandler mediaCarouselScrollHandler, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        mediaCarouselScrollHandler.resetTranslation(z);
    }

    public static /* synthetic */ void scrollToPlayer$default(MediaCarouselScrollHandler mediaCarouselScrollHandler, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = -1;
        }
        mediaCarouselScrollHandler.scrollToPlayer(i, i2);
    }

    public final float getContentTranslation() {
        return this.contentTranslation;
    }

    public final Function0<Unit> getDismissCallback() {
        return this.dismissCallback;
    }

    public final boolean getFalsingProtectionNeeded() {
        return this.falsingProtectionNeeded;
    }

    public final int getMaxTranslation() {
        int i;
        if (this.showsSettingsButton) {
            View view = this.settingsButton;
            View view2 = view;
            if (view == null) {
                view2 = null;
            }
            i = view2.getWidth();
        } else {
            i = this.playerWidthPlusPadding;
        }
        return i;
    }

    public final int getPlayerWidthPlusPadding() {
        return this.playerWidthPlusPadding;
    }

    public final boolean getQsExpanded() {
        return this.qsExpanded;
    }

    public final int getVisibleMediaIndex() {
        return this.visibleMediaIndex;
    }

    public final boolean getVisibleToUser() {
        return this.visibleToUser;
    }

    public final boolean isFalseTouch() {
        boolean z = true;
        if (!this.falsingProtectionNeeded || !this.falsingManager.isFalseTouch(1)) {
            z = false;
        }
        return z;
    }

    public final boolean isRtl() {
        return this.scrollView.isLayoutRtl();
    }

    public final boolean onFling(float f, float f2) {
        PhysicsAnimator.SpringConfig springConfig;
        float f3 = f * f;
        double d = f3;
        double d2 = f2;
        if (d >= 0.5d * d2 * d2 && f3 >= 1000000.0f) {
            float contentTranslation = this.scrollView.getContentTranslation();
            if (contentTranslation == ActionBarShadowController.ELEVATION_LOW) {
                int relativeScrollX = this.scrollView.getRelativeScrollX();
                int i = this.playerWidthPlusPadding;
                int i2 = i > 0 ? relativeScrollX / i : 0;
                int i3 = i2;
                if (!isRtl() ? f >= ActionBarShadowController.ELEVATION_LOW : f <= ActionBarShadowController.ELEVATION_LOW) {
                    i3 = i2 + 1;
                }
                final View childAt = this.mediaContent.getChildAt(Math.min(this.mediaContent.getChildCount() - 1, Math.max(0, i3)));
                this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$onFling$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaCarouselScrollHandler.access$getScrollView$p(MediaCarouselScrollHandler.this).smoothScrollTo(childAt.getLeft(), MediaCarouselScrollHandler.access$getScrollView$p(MediaCarouselScrollHandler.this).getScrollY());
                    }
                });
                return true;
            }
            boolean z = false;
            if (Math.signum(f) == Math.signum(contentTranslation)) {
                z = true;
            }
            float f4 = 0.0f;
            if (z) {
                if (isFalseTouch()) {
                    f4 = 0.0f;
                } else {
                    float maxTranslation = getMaxTranslation() * Math.signum(contentTranslation);
                    f4 = maxTranslation;
                    if (!this.showsSettingsButton) {
                        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$onFling$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaCarouselScrollHandler.this.getDismissCallback().invoke();
                            }
                        }, 100L);
                        f4 = maxTranslation;
                    }
                }
            }
            springConfig = MediaCarouselScrollHandlerKt.translationConfig;
            PhysicsAnimator.Companion.getInstance(this).spring(CONTENT_TRANSLATION, f4, f, springConfig).start();
            this.scrollView.setAnimationTargetX(f4);
            return true;
        }
        return false;
    }

    public final boolean onInterceptTouch(MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    public final void onMediaScrollingChanged(int i, int i2) {
        boolean z = false;
        boolean z2 = this.scrollIntoCurrentMedia != 0;
        this.scrollIntoCurrentMedia = i2;
        if (i2 != 0) {
            z = true;
        }
        int i3 = this.visibleMediaIndex;
        if (i != i3 || z2 != z) {
            this.visibleMediaIndex = i;
            if (i3 != i && this.visibleToUser) {
                this.logSmartspaceImpression.invoke(Boolean.valueOf(this.qsExpanded));
                this.logger.logMediaCarouselPage(i);
            }
            this.closeGuts.invoke(Boolean.FALSE);
            updatePlayerVisibilities();
        }
        float f = this.visibleMediaIndex;
        int i4 = this.playerWidthPlusPadding;
        float f2 = f + (i4 > 0 ? i2 / i4 : 0.0f);
        float f3 = f2;
        if (isRtl()) {
            f3 = (this.mediaContent.getChildCount() - f2) - 1;
        }
        this.pageIndicator.setLocation(f3);
        updateClipToOutline();
    }

    public final void onPlayersChanged() {
        updatePlayerVisibilities();
        updateMediaPaddings();
    }

    public final void onPrePlayerRemoved(MediaControlPanel mediaControlPanel) {
        ViewGroup viewGroup = this.mediaContent;
        MediaViewHolder mediaViewHolder = mediaControlPanel.getMediaViewHolder();
        int indexOfChild = viewGroup.indexOfChild(mediaViewHolder != null ? mediaViewHolder.getPlayer() : null);
        int i = this.visibleMediaIndex;
        boolean z = indexOfChild <= i;
        if (z) {
            this.visibleMediaIndex = Math.max(0, i - 1);
        }
        if (isRtl()) {
            z = !z;
        }
        if (z) {
            MediaScrollView mediaScrollView = this.scrollView;
            mediaScrollView.setScrollX(Math.max(mediaScrollView.getScrollX() - this.playerWidthPlusPadding, 0));
        }
    }

    public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f) {
        PhysicsAnimator.SpringConfig springConfig;
        float x = motionEvent2.getX();
        float x2 = motionEvent.getX();
        float contentTranslation = this.scrollView.getContentTranslation();
        int i = (contentTranslation > ActionBarShadowController.ELEVATION_LOW ? 1 : (contentTranslation == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
        if ((i == 0) && this.scrollView.canScrollHorizontally((int) (-(x - x2)))) {
            return false;
        }
        float f2 = contentTranslation - f;
        float abs = Math.abs(f2);
        float f3 = f2;
        if (abs > getMaxTranslation()) {
            f3 = f2;
            if (!(Math.signum(f) == Math.signum(contentTranslation))) {
                f3 = Math.abs(contentTranslation) > ((float) getMaxTranslation()) ? contentTranslation - (f * 0.2f) : Math.signum(f2) * (getMaxTranslation() + ((abs - getMaxTranslation()) * 0.2f));
            }
        }
        float f4 = f3;
        if (!(Math.signum(f3) == Math.signum(contentTranslation))) {
            boolean z = false;
            if (i == 0) {
                z = true;
            }
            f4 = f3;
            if (!z) {
                f4 = f3;
                if (this.scrollView.canScrollHorizontally(-((int) f3))) {
                    f4 = 0.0f;
                }
            }
        }
        PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(this);
        if (companion.isRunning()) {
            springConfig = MediaCarouselScrollHandlerKt.translationConfig;
            companion.spring(CONTENT_TRANSLATION, f4, (float) ActionBarShadowController.ELEVATION_LOW, springConfig).start();
        } else {
            setContentTranslation(f4);
        }
        this.scrollView.setAnimationTargetX(f4);
        return true;
    }

    public final void onSettingsButtonUpdated(View view) {
        this.settingsButton = view;
        View view2 = view;
        if (view == null) {
            view2 = null;
        }
        Resources resources = view2.getResources();
        View view3 = this.settingsButton;
        if (view3 == null) {
            view3 = null;
        }
        this.cornerRadius = resources.getDimensionPixelSize(Utils.getThemeAttr(view3.getContext(), 16844145));
        updateSettingsPresentation();
        this.scrollView.invalidateOutline();
    }

    public final boolean onTouch(MotionEvent motionEvent) {
        float f;
        PhysicsAnimator.SpringConfig springConfig;
        boolean z = motionEvent.getAction() == 1;
        if (z && this.falsingProtectionNeeded) {
            this.falsingCollector.onNotificationStopDismissing();
        }
        if (this.gestureDetector.onTouchEvent(motionEvent)) {
            if (z) {
                this.scrollView.cancelCurrentScroll();
                return true;
            }
            return false;
        } else if (motionEvent.getAction() == 2) {
            PhysicsAnimator.Companion.getInstance(this).cancel();
            return false;
        } else if (z || motionEvent.getAction() == 3) {
            int relativeScrollX = this.scrollView.getRelativeScrollX();
            int i = this.playerWidthPlusPadding;
            int i2 = relativeScrollX % i;
            int i3 = i2 > i / 2 ? i - i2 : i2 * (-1);
            if (i3 != 0) {
                int i4 = i3;
                if (isRtl()) {
                    i4 = -i3;
                }
                final int relativeScrollX2 = this.scrollView.getRelativeScrollX() + i4;
                this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$onTouch$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaCarouselScrollHandler.access$getScrollView$p(MediaCarouselScrollHandler.this).smoothScrollTo(relativeScrollX2, MediaCarouselScrollHandler.access$getScrollView$p(MediaCarouselScrollHandler.this).getScrollY());
                    }
                });
            }
            float contentTranslation = this.scrollView.getContentTranslation();
            if (contentTranslation == ActionBarShadowController.ELEVATION_LOW) {
                return false;
            }
            boolean z2 = true;
            if (Math.abs(contentTranslation) >= getMaxTranslation() / 2) {
                z2 = isFalseTouch();
            }
            if (z2) {
                f = 0.0f;
            } else {
                float maxTranslation = getMaxTranslation() * Math.signum(contentTranslation);
                f = maxTranslation;
                if (!this.showsSettingsButton) {
                    this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$onTouch$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaCarouselScrollHandler.this.getDismissCallback().invoke();
                        }
                    }, 100L);
                    f = maxTranslation;
                }
            }
            springConfig = MediaCarouselScrollHandlerKt.translationConfig;
            PhysicsAnimator.Companion.getInstance(this).spring(CONTENT_TRANSLATION, f, (float) ActionBarShadowController.ELEVATION_LOW, springConfig).start();
            this.scrollView.setAnimationTargetX(f);
            return false;
        } else {
            return false;
        }
    }

    public final void resetTranslation(boolean z) {
        PhysicsAnimator.SpringConfig springConfig;
        if (this.scrollView.getContentTranslation() == ActionBarShadowController.ELEVATION_LOW) {
            return;
        }
        if (!z) {
            PhysicsAnimator.Companion.getInstance(this).cancel();
            setContentTranslation(ActionBarShadowController.ELEVATION_LOW);
            return;
        }
        PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(this);
        MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 mediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 = CONTENT_TRANSLATION;
        springConfig = MediaCarouselScrollHandlerKt.translationConfig;
        companion.spring(mediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1, (float) ActionBarShadowController.ELEVATION_LOW, springConfig).start();
        this.scrollView.setAnimationTargetX(ActionBarShadowController.ELEVATION_LOW);
    }

    public final void scrollToPlayer(int i, int i2) {
        if (i >= 0 && i < this.mediaContent.getChildCount()) {
            this.scrollView.setRelativeScrollX(i * this.playerWidthPlusPadding);
        }
        final View childAt = this.mediaContent.getChildAt(Math.min(this.mediaContent.getChildCount() - 1, i2));
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.MediaCarouselScrollHandler$scrollToPlayer$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaCarouselScrollHandler.access$getScrollView$p(MediaCarouselScrollHandler.this).smoothScrollTo(childAt.getLeft(), MediaCarouselScrollHandler.access$getScrollView$p(MediaCarouselScrollHandler.this).getScrollY());
            }
        }, 100L);
    }

    public final void scrollToStart() {
        this.scrollView.setRelativeScrollX(0);
    }

    public final void setCarouselBounds(int i, int i2) {
        int i3 = this.carouselHeight;
        if (i2 == i3 && i == i3) {
            return;
        }
        this.carouselWidth = i;
        this.carouselHeight = i2;
        this.scrollView.invalidateOutline();
    }

    public final void setContentTranslation(float f) {
        this.contentTranslation = f;
        this.mediaContent.setTranslationX(f);
        updateSettingsPresentation();
        this.translationChangedListener.invoke();
        updateClipToOutline();
    }

    public final void setFalsingProtectionNeeded(boolean z) {
        this.falsingProtectionNeeded = z;
    }

    public final void setPlayerWidthPlusPadding(int i) {
        this.playerWidthPlusPadding = i;
        int i2 = this.visibleMediaIndex * i;
        int i3 = this.scrollIntoCurrentMedia;
        this.scrollView.setRelativeScrollX(i3 > i ? i2 + (i - (i3 - i)) : i2 + i3);
    }

    public final void setQsExpanded(boolean z) {
        this.qsExpanded = z;
    }

    public final void setShowsSettingsButton(boolean z) {
        this.showsSettingsButton = z;
    }

    public final void setVisibleToUser(boolean z) {
        this.visibleToUser = z;
    }

    public final void updateClipToOutline() {
        boolean z = true;
        if (this.contentTranslation == ActionBarShadowController.ELEVATION_LOW) {
            z = this.scrollIntoCurrentMedia != 0;
        }
        this.scrollView.setClipToOutline(z);
    }

    public final void updateMediaPaddings() {
        int dimensionPixelSize = this.scrollView.getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_padding);
        int childCount = this.mediaContent.getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = this.mediaContent.getChildAt(i);
            int i2 = i == childCount - 1 ? 0 : dimensionPixelSize;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
            if (marginLayoutParams.getMarginEnd() != i2) {
                marginLayoutParams.setMarginEnd(i2);
                childAt.setLayoutParams(marginLayoutParams);
            }
            i++;
        }
    }

    public final void updatePlayerVisibilities() {
        boolean z = this.scrollIntoCurrentMedia != 0;
        int childCount = this.mediaContent.getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = this.mediaContent.getChildAt(i);
            int i2 = this.visibleMediaIndex;
            childAt.setVisibility(i == i2 || (i == i2 + 1 && z) ? 0 : 4);
            i++;
        }
    }

    public final void updateSettingsPresentation() {
        int i = 4;
        View view = null;
        if (this.showsSettingsButton) {
            View view2 = this.settingsButton;
            View view3 = view2;
            if (view2 == null) {
                view3 = null;
            }
            if (view3.getWidth() > 0) {
                float map = MathUtils.map((float) ActionBarShadowController.ELEVATION_LOW, getMaxTranslation(), (float) ActionBarShadowController.ELEVATION_LOW, 1.0f, Math.abs(this.contentTranslation));
                float f = 1.0f - map;
                View view4 = this.settingsButton;
                View view5 = view4;
                if (view4 == null) {
                    view5 = null;
                }
                float f2 = (-view5.getWidth()) * f * 0.3f;
                if (isRtl()) {
                    if (this.contentTranslation > ActionBarShadowController.ELEVATION_LOW) {
                        float width = this.scrollView.getWidth();
                        View view6 = this.settingsButton;
                        View view7 = view6;
                        if (view6 == null) {
                            view7 = null;
                        }
                        f2 = -((width - f2) - view7.getWidth());
                    } else {
                        f2 = -f2;
                    }
                } else if (this.contentTranslation <= ActionBarShadowController.ELEVATION_LOW) {
                    float width2 = this.scrollView.getWidth();
                    View view8 = this.settingsButton;
                    View view9 = view8;
                    if (view8 == null) {
                        view9 = null;
                    }
                    f2 = (width2 - f2) - view9.getWidth();
                }
                float f3 = 50;
                View view10 = this.settingsButton;
                View view11 = view10;
                if (view10 == null) {
                    view11 = null;
                }
                view11.setRotation(f * f3 * (-Math.signum(this.contentTranslation)));
                float saturate = MathUtils.saturate(MathUtils.map(0.5f, 1.0f, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f, map));
                View view12 = this.settingsButton;
                View view13 = view12;
                if (view12 == null) {
                    view13 = null;
                }
                view13.setAlpha(saturate);
                View view14 = this.settingsButton;
                View view15 = view14;
                if (view14 == null) {
                    view15 = null;
                }
                if (!(saturate == ActionBarShadowController.ELEVATION_LOW)) {
                    i = 0;
                }
                view15.setVisibility(i);
                View view16 = this.settingsButton;
                View view17 = view16;
                if (view16 == null) {
                    view17 = null;
                }
                view17.setTranslationX(f2);
                View view18 = this.settingsButton;
                View view19 = view18;
                if (view18 == null) {
                    view19 = null;
                }
                int height = this.scrollView.getHeight();
                View view20 = this.settingsButton;
                if (view20 != null) {
                    view = view20;
                }
                view19.setTranslationY((height - view.getHeight()) / 2.0f);
                return;
            }
        }
        View view21 = this.settingsButton;
        if (view21 == null) {
            view21 = null;
        }
        view21.setVisibility(4);
    }
}