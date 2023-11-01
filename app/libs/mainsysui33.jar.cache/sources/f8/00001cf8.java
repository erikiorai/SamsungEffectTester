package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Trace;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$id;
import com.android.systemui.R$xml;
import com.android.systemui.media.controls.models.GutsViewHolder;
import com.android.systemui.media.controls.models.player.MediaViewHolder;
import com.android.systemui.media.controls.models.recommendation.RecommendationViewHolder;
import com.android.systemui.media.controls.ui.MediaHostStatesManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.animation.TransitionLayoutController;
import com.android.systemui.util.animation.TransitionViewState;
import com.android.systemui.util.animation.WidgetState;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaViewController.class */
public final class MediaViewController {
    public boolean animateNextStateChange;
    public long animationDelay;
    public long animationDuration;
    public final ConstraintSet collapsedLayout;
    public final ConfigurationController configurationController;
    public final MediaViewController$configurationListener$1 configurationListener;
    public final Context context;
    public int currentEndLocation;
    public int currentHeight;
    public int currentStartLocation;
    public float currentTransitionProgress;
    public int currentWidth;
    public final ConstraintSet expandedLayout;
    public boolean firstRefresh = true;
    public boolean isGutsVisible;
    public final TransitionLayoutController layoutController;
    public final MediaViewLogger logger;
    public final MeasurementOutput measurement;
    public final MediaHostStatesManager mediaHostStatesManager;
    public Function0<Unit> sizeChangedListener;
    public final MediaHostStatesManager.Callback stateCallback;
    public final CacheKey tmpKey;
    public final TransitionViewState tmpState;
    public final TransitionViewState tmpState2;
    public final TransitionViewState tmpState3;
    public TransitionLayout transitionLayout;
    public TYPE type;
    public final Map<CacheKey, TransitionViewState> viewStates;
    public static final Companion Companion = new Companion(null);
    public static final long GUTS_ANIMATION_DURATION = 500;
    public static final Set<Integer> controlIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(R$id.media_progress_bar), Integer.valueOf(R$id.actionNext), Integer.valueOf(R$id.actionPrev), Integer.valueOf(R$id.action0), Integer.valueOf(R$id.action1), Integer.valueOf(R$id.action2), Integer.valueOf(R$id.action3), Integer.valueOf(R$id.action4), Integer.valueOf(R$id.media_scrubbing_elapsed_time), Integer.valueOf(R$id.media_scrubbing_total_time)});
    public static final Set<Integer> detailIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(R$id.header_title), Integer.valueOf(R$id.header_artist), Integer.valueOf(R$id.actionPlayPause)});
    public static final Set<Integer> backgroundIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(R$id.album_art), Integer.valueOf(R$id.turbulence_noise_view), Integer.valueOf(R$id.touch_ripple_view)});

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaViewController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaViewController$TYPE.class */
    public enum TYPE {
        PLAYER,
        RECOMMENDATION
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaViewController$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TYPE.values().length];
            try {
                iArr[TYPE.PLAYER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[TYPE.RECOMMENDATION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r0v18, types: [com.android.systemui.media.controls.ui.MediaViewController$configurationListener$1, java.lang.Object] */
    public MediaViewController(Context context, ConfigurationController configurationController, MediaHostStatesManager mediaHostStatesManager, MediaViewLogger mediaViewLogger) {
        this.context = context;
        this.configurationController = configurationController;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.logger = mediaViewLogger;
        TransitionLayoutController transitionLayoutController = new TransitionLayoutController();
        this.layoutController = transitionLayoutController;
        this.measurement = new MeasurementOutput(0, 0);
        this.type = TYPE.PLAYER;
        this.viewStates = new LinkedHashMap();
        this.currentEndLocation = -1;
        this.currentStartLocation = -1;
        this.currentTransitionProgress = 1.0f;
        this.tmpState = new TransitionViewState();
        this.tmpState2 = new TransitionViewState();
        this.tmpState3 = new TransitionViewState();
        this.tmpKey = new CacheKey(0, 0, ActionBarShadowController.ELEVATION_LOW, false, 15, null);
        ?? r0 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.MediaViewController$configurationListener$1
            public void onConfigChanged(Configuration configuration) {
                if (configuration != null) {
                    MediaViewController mediaViewController = MediaViewController.this;
                    TransitionLayout access$getTransitionLayout$p = MediaViewController.access$getTransitionLayout$p(mediaViewController);
                    boolean z = false;
                    if (access$getTransitionLayout$p != null) {
                        z = false;
                        if (access$getTransitionLayout$p.getRawLayoutDirection() == configuration.getLayoutDirection()) {
                            z = true;
                        }
                    }
                    if (z) {
                        return;
                    }
                    TransitionLayout access$getTransitionLayout$p2 = MediaViewController.access$getTransitionLayout$p(mediaViewController);
                    if (access$getTransitionLayout$p2 != null) {
                        access$getTransitionLayout$p2.setLayoutDirection(configuration.getLayoutDirection());
                    }
                    mediaViewController.refreshState();
                }
            }
        };
        this.configurationListener = r0;
        this.stateCallback = new MediaHostStatesManager.Callback() { // from class: com.android.systemui.media.controls.ui.MediaViewController$stateCallback$1
            @Override // com.android.systemui.media.controls.ui.MediaHostStatesManager.Callback
            public void onHostStateChanged(int i, MediaHostState mediaHostState) {
                int i2;
                float f;
                int i3;
                if (i != MediaViewController.this.getCurrentEndLocation()) {
                    i3 = MediaViewController.this.currentStartLocation;
                    if (i != i3) {
                        return;
                    }
                }
                MediaViewController mediaViewController = MediaViewController.this;
                i2 = mediaViewController.currentStartLocation;
                int currentEndLocation = MediaViewController.this.getCurrentEndLocation();
                f = MediaViewController.this.currentTransitionProgress;
                mediaViewController.setCurrentState(i2, currentEndLocation, f, false);
            }
        };
        this.collapsedLayout = new ConstraintSet();
        this.expandedLayout = new ConstraintSet();
        mediaHostStatesManager.addController(this);
        transitionLayoutController.setSizeChangedListener(new Function2<Integer, Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.MediaViewController.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
                MediaViewController.this = this;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke(((Number) obj).intValue(), ((Number) obj2).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i, int i2) {
                MediaViewController.this.setCurrentWidth(i);
                MediaViewController.this.setCurrentHeight(i2);
                MediaViewController.this.getSizeChangedListener().invoke();
            }
        });
        configurationController.addCallback((Object) r0);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaViewController$configurationListener$1.onConfigChanged(android.content.res.Configuration):void] */
    public static final /* synthetic */ TransitionLayout access$getTransitionLayout$p(MediaViewController mediaViewController) {
        return mediaViewController.transitionLayout;
    }

    private static /* synthetic */ void getTransitionLayout$annotations() {
    }

    public final void animatePendingStateChange(long j, long j2) {
        this.animateNextStateChange = true;
        this.animationDuration = j;
        this.animationDelay = j2;
    }

    public final void attach(TransitionLayout transitionLayout, TYPE type) {
        if (!Trace.isTagEnabled(4096L)) {
            updateMediaViewControllerType(type);
            MediaViewLogger mediaViewLogger = this.logger;
            mediaViewLogger.logMediaLocation("attach " + type, this.currentStartLocation, this.currentEndLocation);
            this.transitionLayout = transitionLayout;
            this.layoutController.attach(transitionLayout);
            int i = this.currentEndLocation;
            if (i == -1) {
                return;
            }
            setCurrentState(this.currentStartLocation, i, this.currentTransitionProgress, true);
            return;
        }
        Trace.traceBegin(4096L, "MediaViewController#attach");
        try {
            updateMediaViewControllerType(type);
            MediaViewLogger mediaViewLogger2 = this.logger;
            mediaViewLogger2.logMediaLocation("attach " + type, this.currentStartLocation, this.currentEndLocation);
            this.transitionLayout = transitionLayout;
            this.layoutController.attach(transitionLayout);
            int i2 = this.currentEndLocation;
            if (i2 == -1) {
                return;
            }
            setCurrentState(this.currentStartLocation, i2, this.currentTransitionProgress, true);
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final void closeGuts(boolean z) {
        if (this.isGutsVisible) {
            this.isGutsVisible = false;
            if (!z) {
                animatePendingStateChange(GUTS_ANIMATION_DURATION, 0L);
            }
            setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, z);
        }
    }

    public final ConstraintSet constraintSetForExpansion(float f) {
        return f > ActionBarShadowController.ELEVATION_LOW ? this.expandedLayout : this.collapsedLayout;
    }

    public final void ensureAllMeasurements() {
        for (Map.Entry<Integer, MediaHostState> entry : this.mediaHostStatesManager.getMediaHostStates().entrySet()) {
            obtainViewState(entry.getValue());
        }
    }

    public final ConstraintSet getCollapsedLayout() {
        return this.collapsedLayout;
    }

    public final int getCurrentEndLocation() {
        return this.currentEndLocation;
    }

    public final int getCurrentHeight() {
        return this.currentHeight;
    }

    public final int getCurrentWidth() {
        return this.currentWidth;
    }

    public final ConstraintSet getExpandedLayout() {
        return this.expandedLayout;
    }

    public final CacheKey getKey(MediaHostState mediaHostState, boolean z, CacheKey cacheKey) {
        MeasurementInput measurementInput = mediaHostState.getMeasurementInput();
        cacheKey.setHeightMeasureSpec(measurementInput != null ? measurementInput.getHeightMeasureSpec() : 0);
        MeasurementInput measurementInput2 = mediaHostState.getMeasurementInput();
        int i = 0;
        if (measurementInput2 != null) {
            i = measurementInput2.getWidthMeasureSpec();
        }
        cacheKey.setWidthMeasureSpec(i);
        cacheKey.setExpansion(mediaHostState.getExpansion());
        cacheKey.setGutsVisible(z);
        return cacheKey;
    }

    public final MeasurementOutput getMeasurementsForState(MediaHostState mediaHostState) {
        if (!Trace.isTagEnabled(4096L)) {
            TransitionViewState obtainViewState = obtainViewState(mediaHostState);
            if (obtainViewState == null) {
                return null;
            }
            this.measurement.setMeasuredWidth(obtainViewState.getMeasureWidth());
            this.measurement.setMeasuredHeight(obtainViewState.getMeasureHeight());
            return this.measurement;
        }
        Trace.traceBegin(4096L, "MediaViewController#getMeasurementsForState");
        try {
            TransitionViewState obtainViewState2 = obtainViewState(mediaHostState);
            if (obtainViewState2 == null) {
                Trace.traceEnd(4096L);
                return null;
            }
            this.measurement.setMeasuredWidth(obtainViewState2.getMeasureWidth());
            this.measurement.setMeasuredHeight(obtainViewState2.getMeasureHeight());
            MeasurementOutput measurementOutput = this.measurement;
            Trace.traceEnd(4096L);
            return measurementOutput;
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final Function0<Unit> getSizeChangedListener() {
        Function0<Unit> function0 = this.sizeChangedListener;
        if (function0 != null) {
            return function0;
        }
        return null;
    }

    public final MediaHostStatesManager.Callback getStateCallback() {
        return this.stateCallback;
    }

    public final float getTranslationX() {
        TransitionLayout transitionLayout = this.transitionLayout;
        return transitionLayout != null ? transitionLayout.getTranslationX() : 0.0f;
    }

    public final float getTranslationY() {
        TransitionLayout transitionLayout = this.transitionLayout;
        return transitionLayout != null ? transitionLayout.getTranslationY() : 0.0f;
    }

    public final boolean isGutsVisible() {
        return this.isGutsVisible;
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x012c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final TransitionViewState obtainViewState(MediaHostState mediaHostState) {
        TransitionViewState calculateViewState;
        TransitionViewState transitionViewState = null;
        if (mediaHostState != null) {
            if (mediaHostState.getMeasurementInput() == null) {
                transitionViewState = null;
            } else {
                CacheKey key = getKey(mediaHostState, this.isGutsVisible, this.tmpKey);
                TransitionViewState transitionViewState2 = this.viewStates.get(key);
                if (transitionViewState2 != null) {
                    return mediaHostState.getSquishFraction() <= 1.0f ? squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(transitionViewState2, mediaHostState.getSquishFraction()) : transitionViewState2;
                }
                CacheKey copy$default = CacheKey.copy$default(key, 0, 0, ActionBarShadowController.ELEVATION_LOW, false, 15, null);
                if (this.transitionLayout == null) {
                    return null;
                }
                if (!(mediaHostState.getExpansion() == ActionBarShadowController.ELEVATION_LOW)) {
                    if (!(mediaHostState.getExpansion() == 1.0f)) {
                        MediaHostState copy = mediaHostState.copy();
                        copy.setExpansion(ActionBarShadowController.ELEVATION_LOW);
                        TransitionViewState obtainViewState = obtainViewState(copy);
                        MediaHostState copy2 = mediaHostState.copy();
                        copy2.setExpansion(1.0f);
                        calculateViewState = TransitionLayoutController.getInterpolatedState$default(this.layoutController, obtainViewState, obtainViewState(copy2), mediaHostState.getExpansion(), (TransitionViewState) null, 8, (Object) null);
                        transitionViewState = calculateViewState;
                        if (mediaHostState.getSquishFraction() <= 1.0f) {
                            return squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(calculateViewState, mediaHostState.getSquishFraction());
                        }
                    }
                }
                TransitionLayout transitionLayout = this.transitionLayout;
                Intrinsics.checkNotNull(transitionLayout);
                MeasurementInput measurementInput = mediaHostState.getMeasurementInput();
                Intrinsics.checkNotNull(measurementInput);
                calculateViewState = transitionLayout.calculateViewState(measurementInput, constraintSetForExpansion(mediaHostState.getExpansion()), new TransitionViewState());
                setGutsViewState(calculateViewState);
                this.viewStates.put(copy$default, calculateViewState);
                transitionViewState = calculateViewState;
                if (mediaHostState.getSquishFraction() <= 1.0f) {
                }
            }
        }
        return transitionViewState;
    }

    public final TransitionViewState obtainViewStateForLocation(int i) {
        MediaHostState mediaHostState = this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i));
        if (mediaHostState == null) {
            return null;
        }
        TransitionViewState obtainViewState = obtainViewState(mediaHostState);
        if (obtainViewState != null) {
            updateViewStateSize(obtainViewState, i, this.tmpState);
            return this.tmpState;
        }
        return obtainViewState;
    }

    public final void onDestroy() {
        this.mediaHostStatesManager.removeController(this);
        this.configurationController.removeCallback(this.configurationListener);
    }

    public final void onLocationPreChange(int i) {
        TransitionViewState obtainViewStateForLocation = obtainViewStateForLocation(i);
        if (obtainViewStateForLocation != null) {
            this.layoutController.setMeasureState(obtainViewStateForLocation);
        }
    }

    public final void openGuts() {
        if (this.isGutsVisible) {
            return;
        }
        this.isGutsVisible = true;
        animatePendingStateChange(GUTS_ANIMATION_DURATION, 0L);
        setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, false);
    }

    public final void refreshState() {
        if (!Trace.isTagEnabled(4096L)) {
            this.viewStates.clear();
            if (this.firstRefresh) {
                ensureAllMeasurements();
                this.firstRefresh = false;
            }
            setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, true);
            return;
        }
        Trace.traceBegin(4096L, "MediaViewController#refreshState");
        try {
            this.viewStates.clear();
            if (this.firstRefresh) {
                ensureAllMeasurements();
                this.firstRefresh = false;
            }
            setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, true);
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final void setCurrentHeight(int i) {
        this.currentHeight = i;
    }

    public final void setCurrentState(int i, int i2, float f, boolean z) {
        if (!Trace.isTagEnabled(4096L)) {
            this.currentEndLocation = i2;
            this.currentStartLocation = i;
            this.currentTransitionProgress = f;
            this.logger.logMediaLocation("setCurrentState", i, i2);
            boolean z2 = this.animateNextStateChange && !z;
            MediaHostState mediaHostState = this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i2));
            if (mediaHostState == null) {
                return;
            }
            MediaHostState mediaHostState2 = this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i));
            TransitionViewState obtainViewState = obtainViewState(mediaHostState);
            if (obtainViewState == null) {
                return;
            }
            TransitionViewState updateViewStateSize = updateViewStateSize(obtainViewState, i2, this.tmpState2);
            Intrinsics.checkNotNull(updateViewStateSize);
            this.layoutController.setMeasureState(updateViewStateSize);
            this.animateNextStateChange = false;
            if (this.transitionLayout == null) {
                return;
            }
            TransitionViewState updateViewStateSize2 = updateViewStateSize(obtainViewState(mediaHostState2), i, this.tmpState3);
            if (!mediaHostState.getVisible()) {
                if (updateViewStateSize2 != null && mediaHostState2 != null && mediaHostState2.getVisible()) {
                    updateViewStateSize2 = this.layoutController.getGoneState(updateViewStateSize2, mediaHostState2.getDisappearParameters(), f, this.tmpState);
                }
                updateViewStateSize2 = updateViewStateSize;
            } else if (mediaHostState2 == null || mediaHostState2.getVisible()) {
                if (!(f == 1.0f) && updateViewStateSize2 != null) {
                    if (!(f == ActionBarShadowController.ELEVATION_LOW)) {
                        updateViewStateSize2 = this.layoutController.getInterpolatedState(updateViewStateSize2, updateViewStateSize, f, this.tmpState);
                    }
                }
                updateViewStateSize2 = updateViewStateSize;
            } else {
                updateViewStateSize2 = this.layoutController.getGoneState(updateViewStateSize, mediaHostState.getDisappearParameters(), 1.0f - f, this.tmpState);
            }
            this.logger.logMediaSize("setCurrentState", updateViewStateSize2.getWidth(), updateViewStateSize2.getHeight());
            this.layoutController.setState(updateViewStateSize2, z, z2, this.animationDuration, this.animationDelay);
            return;
        }
        Trace.traceBegin(4096L, "MediaViewController#setCurrentState");
        try {
            this.currentEndLocation = i2;
            this.currentStartLocation = i;
            this.currentTransitionProgress = f;
            this.logger.logMediaLocation("setCurrentState", i, i2);
            boolean z3 = this.animateNextStateChange && !z;
            MediaHostState mediaHostState3 = this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i2));
            if (mediaHostState3 == null) {
                return;
            }
            MediaHostState mediaHostState4 = this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i));
            TransitionViewState obtainViewState2 = obtainViewState(mediaHostState3);
            if (obtainViewState2 == null) {
                return;
            }
            TransitionViewState updateViewStateSize3 = updateViewStateSize(obtainViewState2, i2, this.tmpState2);
            Intrinsics.checkNotNull(updateViewStateSize3);
            this.layoutController.setMeasureState(updateViewStateSize3);
            this.animateNextStateChange = false;
            if (this.transitionLayout == null) {
                return;
            }
            TransitionViewState updateViewStateSize4 = updateViewStateSize(obtainViewState(mediaHostState4), i, this.tmpState3);
            if (mediaHostState3.getVisible()) {
                if (mediaHostState4 == null || mediaHostState4.getVisible()) {
                    if (!(f == 1.0f) && updateViewStateSize4 != null) {
                        updateViewStateSize3 = (f > ActionBarShadowController.ELEVATION_LOW ? 1 : (f == ActionBarShadowController.ELEVATION_LOW ? 0 : -1)) == 0 ? updateViewStateSize4 : this.layoutController.getInterpolatedState(updateViewStateSize4, updateViewStateSize3, f, this.tmpState);
                    }
                } else {
                    updateViewStateSize3 = this.layoutController.getGoneState(updateViewStateSize3, mediaHostState3.getDisappearParameters(), 1.0f - f, this.tmpState);
                }
            } else if (updateViewStateSize4 != null && mediaHostState4 != null && mediaHostState4.getVisible()) {
                updateViewStateSize3 = this.layoutController.getGoneState(updateViewStateSize4, mediaHostState4.getDisappearParameters(), f, this.tmpState);
            }
            this.logger.logMediaSize("setCurrentState", updateViewStateSize3.getWidth(), updateViewStateSize3.getHeight());
            this.layoutController.setState(updateViewStateSize3, z, z3, this.animationDuration, this.animationDelay);
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final void setCurrentWidth(int i) {
        this.currentWidth = i;
    }

    public final void setGutsViewState(TransitionViewState transitionViewState) {
        Set<Integer> controlsIds;
        int i = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i == 1) {
            controlsIds = MediaViewHolder.Companion.getControlsIds();
        } else if (i != 2) {
            throw new NoWhenBranchMatchedException();
        } else {
            controlsIds = RecommendationViewHolder.Companion.getControlsIds();
        }
        Set<Integer> ids = GutsViewHolder.Companion.getIds();
        Iterator<T> it = controlsIds.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WidgetState widgetState = (WidgetState) transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
            if (widgetState != null) {
                widgetState.setAlpha(this.isGutsVisible ? 0.0f : widgetState.getAlpha());
                widgetState.setGone(this.isGutsVisible ? true : widgetState.getGone());
            }
        }
        for (Number number : ids) {
            WidgetState widgetState2 = (WidgetState) transitionViewState.getWidgetStates().get(Integer.valueOf(number.intValue()));
            if (widgetState2 != null) {
                widgetState2.setAlpha(this.isGutsVisible ? widgetState2.getAlpha() : 0.0f);
                widgetState2.setGone(this.isGutsVisible ? widgetState2.getGone() : true);
            }
        }
    }

    public final void setSizeChangedListener(Function0<Unit> function0) {
        this.sizeChangedListener = function0;
    }

    public final TransitionViewState squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(TransitionViewState transitionViewState, float f) {
        TransitionViewState copy$default = TransitionViewState.copy$default(transitionViewState, (TransitionViewState) null, 1, (Object) null);
        int measureHeight = (int) (copy$default.getMeasureHeight() * f);
        copy$default.setHeight(measureHeight);
        for (Number number : controlIds) {
            WidgetState widgetState = (WidgetState) copy$default.getWidgetStates().get(Integer.valueOf(number.intValue()));
            if (widgetState != null) {
                widgetState.setAlpha(MediaCarouselController.Companion.calculateAlpha(f, 1400.0f, 167.0f));
            }
        }
        for (Number number2 : detailIds) {
            WidgetState widgetState2 = (WidgetState) copy$default.getWidgetStates().get(Integer.valueOf(number2.intValue()));
            if (widgetState2 != null) {
                widgetState2.setAlpha(MediaCarouselController.Companion.calculateAlpha(f, 1067.0f, 167.0f));
            }
        }
        for (Number number3 : backgroundIds) {
            WidgetState widgetState3 = (WidgetState) copy$default.getWidgetStates().get(Integer.valueOf(number3.intValue()));
            if (widgetState3 != null) {
                widgetState3.setHeight(measureHeight);
            }
        }
        for (Number number4 : RecommendationViewHolder.Companion.getMediaContainersIds()) {
            WidgetState widgetState4 = (WidgetState) copy$default.getWidgetStates().get(Integer.valueOf(number4.intValue()));
            if (widgetState4 != null) {
                widgetState4.setAlpha(MediaCarouselController.Companion.calculateAlpha(f, 967.0f, 167.0f));
            }
        }
        for (Number number5 : RecommendationViewHolder.Companion.getMediaTitlesAndSubtitlesIds()) {
            WidgetState widgetState5 = (WidgetState) copy$default.getWidgetStates().get(Integer.valueOf(number5.intValue()));
            if (widgetState5 != null) {
                widgetState5.setAlpha(MediaCarouselController.Companion.calculateAlpha(f, 1000.0f, 167.0f));
            }
        }
        return copy$default;
    }

    public final void updateMediaViewControllerType(TYPE type) {
        this.type = type;
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            this.collapsedLayout.load(this.context, R$xml.media_session_collapsed);
            this.expandedLayout.load(this.context, R$xml.media_session_expanded);
        } else if (i == 2) {
            this.collapsedLayout.load(this.context, R$xml.media_recommendation_collapsed);
            this.expandedLayout.load(this.context, R$xml.media_recommendation_expanded);
        }
        refreshState();
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x005a, code lost:
        if (r0.getMeasureWidth() != r0.getMeasuredWidth()) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final TransitionViewState updateViewStateSize(TransitionViewState transitionViewState, int i, TransitionViewState transitionViewState2) {
        TransitionViewState copy;
        if (transitionViewState == null || (copy = transitionViewState.copy(transitionViewState2)) == null) {
            return null;
        }
        MediaHostState mediaHostState = this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i));
        MeasurementOutput measurementOutput = this.mediaHostStatesManager.getCarouselSizes().get(Integer.valueOf(i));
        boolean z = false;
        if (measurementOutput != null) {
            if (copy.getMeasureHeight() == measurementOutput.getMeasuredHeight()) {
                z = false;
            }
            copy.setMeasureHeight(Math.max(measurementOutput.getMeasuredHeight(), copy.getMeasureHeight()));
            copy.setMeasureWidth(Math.max(measurementOutput.getMeasuredWidth(), copy.getMeasureWidth()));
            copy.setHeight(copy.getMeasureHeight());
            copy.setWidth(copy.getMeasureWidth());
            for (Number number : backgroundIds) {
                WidgetState widgetState = (WidgetState) copy.getWidgetStates().get(Integer.valueOf(number.intValue()));
                if (widgetState != null) {
                    widgetState.setHeight(copy.getHeight());
                    widgetState.setWidth(copy.getWidth());
                }
            }
            z = true;
        }
        TransitionViewState transitionViewState3 = copy;
        if (z) {
            transitionViewState3 = copy;
            if (mediaHostState != null) {
                transitionViewState3 = copy;
                if (mediaHostState.getSquishFraction() <= 1.0f) {
                    transitionViewState3 = squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(copy, mediaHostState.getSquishFraction());
                }
            }
        }
        this.logger.logMediaSize("update to carousel", transitionViewState3.getWidth(), transitionViewState3.getHeight());
        return transitionViewState3;
    }
}