package com.android.systemui.media.controls.ui;

import android.graphics.Rect;
import android.util.ArraySet;
import android.view.View;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.MediaHost;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHost.class */
public final class MediaHost implements MediaHostState {
    public UniqueObjectHostView hostView;
    public boolean inited;
    public boolean listeningToMediaData;
    public final MediaDataManager mediaDataManager;
    public final MediaHierarchyManager mediaHierarchyManager;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final MediaHostStateHolder state;
    public int location = -1;
    public ArraySet<Function1<Boolean, Unit>> visibleChangedListeners = new ArraySet<>();
    public final int[] tmpLocationOnScreen = {0, 0};
    public final Rect currentBounds = new Rect();
    public final Rect currentClipping = new Rect();
    public final MediaHost$listener$1 listener = new MediaDataManager.Listener() { // from class: com.android.systemui.media.controls.ui.MediaHost$listener$1
        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
            if (z) {
                MediaHost.this.updateViewVisibility();
            }
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public void onMediaDataRemoved(String str) {
            MediaHost.this.updateViewVisibility();
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
            MediaHost.this.updateViewVisibility();
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public void onSmartspaceMediaDataRemoved(String str, boolean z) {
            if (z) {
                MediaHost.this.updateViewVisibility();
            }
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHost$MediaHostStateHolder.class */
    public static final class MediaHostStateHolder implements MediaHostState {
        public Function0<Unit> changedListener;
        public float expansion;
        public boolean falsingProtectionNeeded;
        public MeasurementInput measurementInput;
        public boolean showsOnlyActiveMedia;
        public float squishFraction = 1.0f;
        public boolean visible = true;
        public DisappearParameters disappearParameters = new DisappearParameters();
        public int lastDisappearHash = getDisappearParameters().hashCode();

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public MediaHostState copy() {
            MediaHostStateHolder mediaHostStateHolder = new MediaHostStateHolder();
            mediaHostStateHolder.setExpansion(getExpansion());
            mediaHostStateHolder.setSquishFraction(getSquishFraction());
            mediaHostStateHolder.setShowsOnlyActiveMedia(getShowsOnlyActiveMedia());
            MeasurementInput measurementInput = getMeasurementInput();
            MeasurementInput measurementInput2 = null;
            if (measurementInput != null) {
                measurementInput2 = MeasurementInput.copy$default(measurementInput, 0, 0, 3, (Object) null);
            }
            mediaHostStateHolder.setMeasurementInput(measurementInput2);
            mediaHostStateHolder.setVisible(getVisible());
            mediaHostStateHolder.setDisappearParameters(getDisappearParameters().deepCopy());
            mediaHostStateHolder.setFalsingProtectionNeeded(getFalsingProtectionNeeded());
            return mediaHostStateHolder;
        }

        public boolean equals(Object obj) {
            if (obj instanceof MediaHostState) {
                MediaHostState mediaHostState = (MediaHostState) obj;
                if (Objects.equals(getMeasurementInput(), mediaHostState.getMeasurementInput())) {
                    if (getExpansion() == mediaHostState.getExpansion()) {
                        return ((getSquishFraction() > mediaHostState.getSquishFraction() ? 1 : (getSquishFraction() == mediaHostState.getSquishFraction() ? 0 : -1)) == 0) && getShowsOnlyActiveMedia() == mediaHostState.getShowsOnlyActiveMedia() && getVisible() == mediaHostState.getVisible() && getFalsingProtectionNeeded() == mediaHostState.getFalsingProtectionNeeded() && getDisappearParameters().equals(mediaHostState.getDisappearParameters());
                    }
                    return false;
                }
                return false;
            }
            return false;
        }

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public DisappearParameters getDisappearParameters() {
            return this.disappearParameters;
        }

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public float getExpansion() {
            return this.expansion;
        }

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public boolean getFalsingProtectionNeeded() {
            return this.falsingProtectionNeeded;
        }

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public MeasurementInput getMeasurementInput() {
            return this.measurementInput;
        }

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public boolean getShowsOnlyActiveMedia() {
            return this.showsOnlyActiveMedia;
        }

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public float getSquishFraction() {
            return this.squishFraction;
        }

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public boolean getVisible() {
            return this.visible;
        }

        public int hashCode() {
            MeasurementInput measurementInput = getMeasurementInput();
            int hashCode = measurementInput != null ? measurementInput.hashCode() : 0;
            int hashCode2 = Float.hashCode(getExpansion());
            int hashCode3 = Float.hashCode(getSquishFraction());
            int hashCode4 = Boolean.hashCode(getFalsingProtectionNeeded());
            return (((((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + Boolean.hashCode(getShowsOnlyActiveMedia())) * 31) + (getVisible() ? 1 : 2)) * 31) + getDisappearParameters().hashCode();
        }

        public final void setChangedListener(Function0<Unit> function0) {
            this.changedListener = function0;
        }

        public void setDisappearParameters(DisappearParameters disappearParameters) {
            int hashCode = disappearParameters.hashCode();
            if (Integer.valueOf(this.lastDisappearHash).equals(Integer.valueOf(hashCode))) {
                return;
            }
            this.disappearParameters = disappearParameters;
            this.lastDisappearHash = hashCode;
            Function0<Unit> function0 = this.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }

        @Override // com.android.systemui.media.controls.ui.MediaHostState
        public void setExpansion(float f) {
            if (Float.valueOf(f).equals(Float.valueOf(this.expansion))) {
                return;
            }
            this.expansion = f;
            Function0<Unit> function0 = this.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }

        public void setFalsingProtectionNeeded(boolean z) {
            if (this.falsingProtectionNeeded == z) {
                return;
            }
            this.falsingProtectionNeeded = z;
            Function0<Unit> function0 = this.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }

        public void setMeasurementInput(MeasurementInput measurementInput) {
            boolean z = true;
            if (measurementInput == null || !measurementInput.equals(this.measurementInput)) {
                z = false;
            }
            if (z) {
                return;
            }
            this.measurementInput = measurementInput;
            Function0<Unit> function0 = this.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }

        public void setShowsOnlyActiveMedia(boolean z) {
            if (Boolean.valueOf(z).equals(Boolean.valueOf(this.showsOnlyActiveMedia))) {
                return;
            }
            this.showsOnlyActiveMedia = z;
            Function0<Unit> function0 = this.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }

        public void setSquishFraction(float f) {
            if (Float.valueOf(f).equals(Float.valueOf(this.squishFraction))) {
                return;
            }
            this.squishFraction = f;
            Function0<Unit> function0 = this.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }

        public void setVisible(boolean z) {
            if (this.visible == z) {
                return;
            }
            this.visible = z;
            Function0<Unit> function0 = this.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.media.controls.ui.MediaHost$listener$1] */
    public MediaHost(MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager) {
        this.state = mediaHostStateHolder;
        this.mediaHierarchyManager = mediaHierarchyManager;
        this.mediaDataManager = mediaDataManager;
        this.mediaHostStatesManager = mediaHostStatesManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.MediaHost$init$1.onViewAttachedToWindow(android.view.View):void, com.android.systemui.media.controls.ui.MediaHost$init$1.onViewDetachedFromWindow(android.view.View):void] */
    public static final /* synthetic */ void access$setListeningToMediaData(MediaHost mediaHost, boolean z) {
        mediaHost.setListeningToMediaData(z);
    }

    public final void addVisibilityChangeListener(Function1<? super Boolean, Unit> function1) {
        this.visibleChangedListeners.add(function1);
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public MediaHostState copy() {
        return this.state.copy();
    }

    public final Rect getCurrentBounds() {
        getHostView().getLocationOnScreen(this.tmpLocationOnScreen);
        int i = 0;
        int paddingLeft = this.tmpLocationOnScreen[0] + getHostView().getPaddingLeft();
        int paddingTop = this.tmpLocationOnScreen[1] + getHostView().getPaddingTop();
        int width = (this.tmpLocationOnScreen[0] + getHostView().getWidth()) - getHostView().getPaddingRight();
        int height = (this.tmpLocationOnScreen[1] + getHostView().getHeight()) - getHostView().getPaddingBottom();
        int i2 = paddingLeft;
        int i3 = width;
        if (width < paddingLeft) {
            i2 = 0;
            i3 = 0;
        }
        if (height < paddingTop) {
            height = 0;
        } else {
            i = paddingTop;
        }
        this.currentBounds.set(i2, i, i3, height);
        return this.currentBounds;
    }

    public final Rect getCurrentClipping() {
        return this.currentClipping;
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public DisappearParameters getDisappearParameters() {
        return this.state.getDisappearParameters();
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public float getExpansion() {
        return this.state.getExpansion();
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public boolean getFalsingProtectionNeeded() {
        return this.state.getFalsingProtectionNeeded();
    }

    public final UniqueObjectHostView getHostView() {
        UniqueObjectHostView uniqueObjectHostView = this.hostView;
        if (uniqueObjectHostView != null) {
            return uniqueObjectHostView;
        }
        return null;
    }

    public final int getLocation() {
        return this.location;
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public MeasurementInput getMeasurementInput() {
        return this.state.getMeasurementInput();
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public boolean getShowsOnlyActiveMedia() {
        return this.state.getShowsOnlyActiveMedia();
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public float getSquishFraction() {
        return this.state.getSquishFraction();
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public boolean getVisible() {
        return this.state.getVisible();
    }

    public final void init(final int i) {
        if (this.inited) {
            return;
        }
        this.inited = true;
        this.location = i;
        setHostView(this.mediaHierarchyManager.register(this));
        setListeningToMediaData(true);
        getHostView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.controls.ui.MediaHost$init$1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                MediaHost.access$setListeningToMediaData(MediaHost.this, true);
                MediaHost.this.updateViewVisibility();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                MediaHost.access$setListeningToMediaData(MediaHost.this, false);
            }
        });
        getHostView().setMeasurementManager(new UniqueObjectHostView.MeasurementManager() { // from class: com.android.systemui.media.controls.ui.MediaHost$init$2
            public MeasurementOutput onMeasure(MeasurementInput measurementInput) {
                MediaHost.MediaHostStateHolder mediaHostStateHolder;
                MediaHostStatesManager mediaHostStatesManager;
                MediaHost.MediaHostStateHolder mediaHostStateHolder2;
                if (View.MeasureSpec.getMode(measurementInput.getWidthMeasureSpec()) == Integer.MIN_VALUE) {
                    measurementInput.setWidthMeasureSpec(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measurementInput.getWidthMeasureSpec()), 1073741824));
                }
                mediaHostStateHolder = MediaHost.this.state;
                mediaHostStateHolder.setMeasurementInput(measurementInput);
                mediaHostStatesManager = MediaHost.this.mediaHostStatesManager;
                int i2 = i;
                mediaHostStateHolder2 = MediaHost.this.state;
                return mediaHostStatesManager.updateCarouselDimensions(i2, mediaHostStateHolder2);
            }
        });
        this.state.setChangedListener(new Function0<Unit>() { // from class: com.android.systemui.media.controls.ui.MediaHost$init$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m3269invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m3269invoke() {
                MediaHostStatesManager mediaHostStatesManager;
                MediaHost.MediaHostStateHolder mediaHostStateHolder;
                mediaHostStatesManager = MediaHost.this.mediaHostStatesManager;
                int i2 = i;
                mediaHostStateHolder = MediaHost.this.state;
                mediaHostStatesManager.updateHostState(i2, mediaHostStateHolder);
            }
        });
        updateViewVisibility();
    }

    public final void removeVisibilityChangeListener(Function1<? super Boolean, Unit> function1) {
        this.visibleChangedListeners.remove(function1);
    }

    public void setDisappearParameters(DisappearParameters disappearParameters) {
        this.state.setDisappearParameters(disappearParameters);
    }

    @Override // com.android.systemui.media.controls.ui.MediaHostState
    public void setExpansion(float f) {
        this.state.setExpansion(f);
    }

    public void setFalsingProtectionNeeded(boolean z) {
        this.state.setFalsingProtectionNeeded(z);
    }

    public final void setHostView(UniqueObjectHostView uniqueObjectHostView) {
        this.hostView = uniqueObjectHostView;
    }

    public final void setListeningToMediaData(boolean z) {
        if (z != this.listeningToMediaData) {
            this.listeningToMediaData = z;
            if (z) {
                this.mediaDataManager.addListener(this.listener);
            } else {
                this.mediaDataManager.removeListener(this.listener);
            }
        }
    }

    public void setShowsOnlyActiveMedia(boolean z) {
        this.state.setShowsOnlyActiveMedia(z);
    }

    public void setSquishFraction(float f) {
        this.state.setSquishFraction(f);
    }

    public final void updateViewVisibility() {
        this.state.setVisible(getShowsOnlyActiveMedia() ? this.mediaDataManager.hasActiveMediaOrRecommendation() : this.mediaDataManager.hasAnyMediaOrRecommendation());
        int i = getVisible() ? 0 : 8;
        if (i != getHostView().getVisibility()) {
            getHostView().setVisibility(i);
            Iterator<T> it = this.visibleChangedListeners.iterator();
            while (it.hasNext()) {
                ((Function1) it.next()).invoke(Boolean.valueOf(getVisible()));
            }
        }
    }
}