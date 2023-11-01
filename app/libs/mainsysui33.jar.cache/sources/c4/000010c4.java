package com.android.systemui.animation;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.GhostView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.FrameLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import java.util.LinkedList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/GhostedViewLaunchAnimatorController.class */
public class GhostedViewLaunchAnimatorController implements ActivityLaunchAnimator.Controller {
    public static final Companion Companion = new Companion(null);
    public final Drawable background;
    public WrappedDrawable backgroundDrawable;
    public final Lazy backgroundInsets$delegate;
    public FrameLayout backgroundView;
    public final Integer cujType;
    public GhostView ghostView;
    public final Matrix ghostViewMatrix;
    public final View ghostedView;
    public final int[] ghostedViewLocation;
    public final LaunchAnimator.State ghostedViewState;
    public final float[] initialGhostViewMatrixValues;
    public InteractionJankMonitor interactionJankMonitor;
    public ViewGroup launchContainer;
    public final int[] launchContainerLocation;
    public int startBackgroundAlpha;

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/GhostedViewLaunchAnimatorController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final GradientDrawable findGradientDrawable(Drawable drawable) {
            if (drawable instanceof GradientDrawable) {
                return (GradientDrawable) drawable;
            }
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                return drawable2 != null ? GhostedViewLaunchAnimatorController.Companion.findGradientDrawable(drawable2) : null;
            } else if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                for (int i = 0; i < numberOfLayers; i++) {
                    Drawable drawable3 = layerDrawable.getDrawable(i);
                    if (drawable3 instanceof GradientDrawable) {
                        return (GradientDrawable) drawable3;
                    }
                }
                return null;
            } else {
                return null;
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/GhostedViewLaunchAnimatorController$WrappedDrawable.class */
    public static final class WrappedDrawable extends Drawable {
        public float[] cornerRadii;
        public int currentAlpha = 255;
        public Rect previousBounds = new Rect();
        public float[] previousCornerRadii;
        public final Drawable wrapped;

        public WrappedDrawable(Drawable drawable) {
            this.wrapped = drawable;
            float[] fArr = new float[8];
            for (int i = 0; i < 8; i++) {
                fArr[i] = -1.0f;
            }
            this.cornerRadii = fArr;
            this.previousCornerRadii = new float[8];
        }

        public final void applyBackgroundRadii() {
            Drawable drawable;
            if (this.cornerRadii[0] < ActionBarShadowController.ELEVATION_LOW || (drawable = this.wrapped) == null) {
                return;
            }
            savePreviousBackgroundRadii(drawable);
            applyBackgroundRadii(this.wrapped, this.cornerRadii);
        }

        public final void applyBackgroundRadii(Drawable drawable, float[] fArr) {
            if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setCornerRadii(fArr);
            } else if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 != null) {
                    applyBackgroundRadii(drawable2, fArr);
                }
            } else if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                for (int i = 0; i < numberOfLayers; i++) {
                    Drawable drawable3 = layerDrawable.getDrawable(i);
                    GradientDrawable gradientDrawable = drawable3 instanceof GradientDrawable ? (GradientDrawable) drawable3 : null;
                    if (gradientDrawable != null) {
                        gradientDrawable.setCornerRadii(fArr);
                    }
                }
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Drawable drawable = this.wrapped;
            if (drawable == null) {
                return;
            }
            drawable.copyBounds(this.previousBounds);
            drawable.setAlpha(this.currentAlpha);
            drawable.setBounds(getBounds());
            applyBackgroundRadii();
            drawable.draw(canvas);
            drawable.setAlpha(0);
            drawable.setBounds(this.previousBounds);
            restoreBackgroundRadii();
        }

        @Override // android.graphics.drawable.Drawable
        public int getAlpha() {
            return this.currentAlpha;
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            Drawable drawable = this.wrapped;
            if (drawable == null) {
                return -2;
            }
            int alpha = drawable.getAlpha();
            drawable.setAlpha(this.currentAlpha);
            int opacity = drawable.getOpacity();
            drawable.setAlpha(alpha);
            return opacity;
        }

        public final Drawable getWrapped() {
            return this.wrapped;
        }

        public final void restoreBackgroundRadii() {
            Drawable drawable;
            if (this.cornerRadii[0] < ActionBarShadowController.ELEVATION_LOW || (drawable = this.wrapped) == null) {
                return;
            }
            applyBackgroundRadii(drawable, this.previousCornerRadii);
        }

        public final void savePreviousBackgroundRadii(Drawable drawable) {
            GradientDrawable findGradientDrawable = GhostedViewLaunchAnimatorController.Companion.findGradientDrawable(drawable);
            if (findGradientDrawable == null) {
                return;
            }
            float[] cornerRadii = findGradientDrawable.getCornerRadii();
            if (cornerRadii != null) {
                ArraysKt___ArraysJvmKt.copyInto$default(cornerRadii, this.previousCornerRadii, 0, 0, 0, 14, (Object) null);
                return;
            }
            float cornerRadius = findGradientDrawable.getCornerRadius();
            updateRadii(this.previousCornerRadii, cornerRadius, cornerRadius);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            if (i != this.currentAlpha) {
                this.currentAlpha = i;
                invalidateSelf();
            }
        }

        public final void setBackgroundRadius(float f, float f2) {
            updateRadii(this.cornerRadii, f, f2);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            Drawable drawable = this.wrapped;
            if (drawable == null) {
                return;
            }
            drawable.setColorFilter(colorFilter);
        }

        public final void updateRadii(float[] fArr, float f, float f2) {
            fArr[0] = f;
            fArr[1] = f;
            fArr[2] = f;
            fArr[3] = f;
            fArr[4] = f2;
            fArr[5] = f2;
            fArr[6] = f2;
            fArr[7] = f2;
        }
    }

    public GhostedViewLaunchAnimatorController(View view, Integer num) {
        this(view, num, null, 4, null);
    }

    public GhostedViewLaunchAnimatorController(View view, Integer num, InteractionJankMonitor interactionJankMonitor) {
        this.ghostedView = view;
        this.cujType = num;
        this.interactionJankMonitor = interactionJankMonitor;
        this.launchContainer = (ViewGroup) view.getRootView();
        this.launchContainerLocation = new int[2];
        float[] fArr = new float[9];
        for (int i = 0; i < 9; i++) {
            fArr[i] = 0.0f;
        }
        this.initialGhostViewMatrixValues = fArr;
        this.ghostViewMatrix = new Matrix();
        this.backgroundInsets$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Insets>() { // from class: com.android.systemui.animation.GhostedViewLaunchAnimatorController$backgroundInsets$2
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* JADX WARN: Code restructure failed: missing block: B:5:0x0014, code lost:
                if (r0 == null) goto L8;
             */
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Insets m1440invoke() {
                Insets insets;
                Drawable access$getBackground$p = GhostedViewLaunchAnimatorController.access$getBackground$p(GhostedViewLaunchAnimatorController.this);
                if (access$getBackground$p != null) {
                    Insets opticalInsets = access$getBackground$p.getOpticalInsets();
                    insets = opticalInsets;
                }
                insets = Insets.NONE;
                return insets;
            }
        });
        this.startBackgroundAlpha = 255;
        this.ghostedViewLocation = new int[2];
        this.ghostedViewState = new LaunchAnimator.State(0, 0, 0, 0, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 63, null);
        this.background = _init_$findBackground(this.ghostedView);
    }

    public /* synthetic */ GhostedViewLaunchAnimatorController(View view, Integer num, InteractionJankMonitor interactionJankMonitor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, (i & 2) != 0 ? null : num, (i & 4) != 0 ? InteractionJankMonitor.getInstance() : interactionJankMonitor);
    }

    public static final Drawable _init_$findBackground(View view) {
        if (view.getBackground() != null) {
            return view.getBackground();
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(view);
        while (!linkedList.isEmpty()) {
            View view2 = (View) linkedList.removeFirst();
            if (view2.getBackground() != null) {
                return view2.getBackground();
            }
            if (view2 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view2;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    linkedList.add(viewGroup.getChildAt(i));
                }
            }
        }
        return null;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.GhostedViewLaunchAnimatorController$backgroundInsets$2.invoke():android.graphics.Insets] */
    public static final /* synthetic */ Drawable access$getBackground$p(GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController) {
        return ghostedViewLaunchAnimatorController.background;
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public LaunchAnimator.State createAnimatorState() {
        LaunchAnimator.State state = new LaunchAnimator.State(0, 0, 0, 0, getCurrentTopCornerRadius(), getCurrentBottomCornerRadius(), 15, null);
        fillGhostedViewState(state);
        return state;
    }

    public final void fillGhostedViewState(LaunchAnimator.State state) {
        this.ghostedView.getLocationOnScreen(this.ghostedViewLocation);
        Insets backgroundInsets = getBackgroundInsets();
        state.setTop(this.ghostedViewLocation[1] + backgroundInsets.top);
        state.setBottom((this.ghostedViewLocation[1] + this.ghostedView.getHeight()) - backgroundInsets.bottom);
        state.setLeft(this.ghostedViewLocation[0] + backgroundInsets.left);
        state.setRight((this.ghostedViewLocation[0] + this.ghostedView.getWidth()) - backgroundInsets.right);
    }

    public final Insets getBackgroundInsets() {
        return (Insets) this.backgroundInsets$delegate.getValue();
    }

    public float getCurrentBottomCornerRadius() {
        GradientDrawable findGradientDrawable;
        Drawable drawable = this.background;
        if (drawable == null || (findGradientDrawable = Companion.findGradientDrawable(drawable)) == null) {
            return ActionBarShadowController.ELEVATION_LOW;
        }
        float[] cornerRadii = findGradientDrawable.getCornerRadii();
        return cornerRadii != null ? cornerRadii[4] : findGradientDrawable.getCornerRadius();
    }

    public float getCurrentTopCornerRadius() {
        GradientDrawable findGradientDrawable;
        Drawable drawable = this.background;
        if (drawable == null || (findGradientDrawable = Companion.findGradientDrawable(drawable)) == null) {
            return ActionBarShadowController.ELEVATION_LOW;
        }
        float[] cornerRadii = findGradientDrawable.getCornerRadii();
        return cornerRadii != null ? cornerRadii[0] : findGradientDrawable.getCornerRadius();
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public ViewGroup getLaunchContainer() {
        return this.launchContainer;
    }

    public final ViewGroupOverlay getLaunchContainerOverlay() {
        return getLaunchContainer().getOverlay();
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public void onLaunchAnimationEnd(boolean z) {
        if (this.ghostView == null) {
            return;
        }
        Integer num = this.cujType;
        if (num != null) {
            this.interactionJankMonitor.end(num.intValue());
        }
        WrappedDrawable wrappedDrawable = this.backgroundDrawable;
        Drawable wrapped = wrappedDrawable != null ? wrappedDrawable.getWrapped() : null;
        if (wrapped != null) {
            wrapped.setAlpha(this.startBackgroundAlpha);
        }
        GhostView.removeGhost(this.ghostedView);
        getLaunchContainerOverlay().remove(this.backgroundView);
        View view = this.ghostedView;
        if (view instanceof LaunchableView) {
            ((LaunchableView) view).setShouldBlockVisibilityChanges(false);
            return;
        }
        view.setVisibility(4);
        this.ghostedView.setVisibility(0);
        this.ghostedView.invalidate();
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        GhostView ghostView = this.ghostView;
        if (ghostView == null) {
            return;
        }
        FrameLayout frameLayout = this.backgroundView;
        Intrinsics.checkNotNull(frameLayout);
        if (!state.getVisible()) {
            if (ghostView.getVisibility() == 0) {
                ghostView.setVisibility(4);
                this.ghostedView.setTransitionVisibility(4);
                frameLayout.setVisibility(4);
                return;
            }
            return;
        }
        if (ghostView.getVisibility() == 4) {
            ghostView.setVisibility(0);
            frameLayout.setVisibility(0);
        }
        fillGhostedViewState(this.ghostedViewState);
        int left = state.getLeft();
        int left2 = this.ghostedViewState.getLeft();
        int right = state.getRight();
        int right2 = this.ghostedViewState.getRight();
        int top = state.getTop();
        int top2 = this.ghostedViewState.getTop();
        int bottom = state.getBottom();
        int bottom2 = this.ghostedViewState.getBottom();
        float min = Math.min(state.getWidth() / this.ghostedViewState.getWidth(), state.getHeight() / this.ghostedViewState.getHeight());
        if (this.ghostedView.getParent() instanceof ViewGroup) {
            GhostView.calculateMatrix(this.ghostedView, getLaunchContainer(), this.ghostViewMatrix);
        }
        getLaunchContainer().getLocationOnScreen(this.launchContainerLocation);
        this.ghostViewMatrix.postScale(min, min, this.ghostedViewState.getCenterX() - this.launchContainerLocation[0], this.ghostedViewState.getCenterY() - this.launchContainerLocation[1]);
        this.ghostViewMatrix.postTranslate(((left - left2) + (right - right2)) / 2.0f, ((top - top2) + (bottom - bottom2)) / 2.0f);
        ghostView.setAnimationMatrix(this.ghostViewMatrix);
        Insets backgroundInsets = getBackgroundInsets();
        int top3 = state.getTop();
        int i = backgroundInsets.top;
        int left3 = state.getLeft();
        int i2 = backgroundInsets.left;
        int right3 = state.getRight();
        int i3 = backgroundInsets.right;
        int bottom3 = state.getBottom();
        int i4 = backgroundInsets.bottom;
        frameLayout.setTop((top3 - i) - this.launchContainerLocation[1]);
        frameLayout.setBottom((bottom3 + i4) - this.launchContainerLocation[1]);
        frameLayout.setLeft((left3 - i2) - this.launchContainerLocation[0]);
        frameLayout.setRight((right3 + i3) - this.launchContainerLocation[0]);
        WrappedDrawable wrappedDrawable = this.backgroundDrawable;
        Intrinsics.checkNotNull(wrappedDrawable);
        Drawable wrapped = wrappedDrawable.getWrapped();
        if (wrapped != null) {
            setBackgroundCornerRadius(wrapped, state.getTopCornerRadius(), state.getBottomCornerRadius());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00b5, code lost:
        if (r0 == null) goto L27;
     */
    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLaunchAnimationStart(boolean z) {
        Matrix matrix;
        if (!(this.ghostedView.getParent() instanceof ViewGroup)) {
            Log.w("GhostedViewLaunchAnimatorController", "Skipping animation as ghostedView is not attached to a ViewGroup");
            return;
        }
        this.backgroundView = new FrameLayout(getLaunchContainer().getContext());
        getLaunchContainerOverlay().add(this.backgroundView);
        Drawable drawable = this.background;
        this.startBackgroundAlpha = drawable != null ? drawable.getAlpha() : 255;
        WrappedDrawable wrappedDrawable = new WrappedDrawable(this.background);
        this.backgroundDrawable = wrappedDrawable;
        FrameLayout frameLayout = this.backgroundView;
        if (frameLayout != null) {
            frameLayout.setBackground(wrappedDrawable);
        }
        View view = this.ghostedView;
        LaunchableView launchableView = view instanceof LaunchableView ? (LaunchableView) view : null;
        if (launchableView != null) {
            launchableView.setShouldBlockVisibilityChanges(true);
        }
        GhostView addGhost = GhostView.addGhost(this.ghostedView, getLaunchContainer());
        this.ghostView = addGhost;
        if (addGhost != null) {
            Matrix animationMatrix = addGhost.getAnimationMatrix();
            matrix = animationMatrix;
        }
        matrix = Matrix.IDENTITY_MATRIX;
        matrix.getValues(this.initialGhostViewMatrixValues);
        Integer num = this.cujType;
        if (num != null) {
            this.interactionJankMonitor.begin(this.ghostedView, num.intValue());
        }
    }

    public void setBackgroundCornerRadius(Drawable drawable, float f, float f2) {
        WrappedDrawable wrappedDrawable = this.backgroundDrawable;
        if (wrappedDrawable != null) {
            wrappedDrawable.setBackgroundRadius(f, f2);
        }
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public void setLaunchContainer(ViewGroup viewGroup) {
        this.launchContainer = viewGroup;
    }
}