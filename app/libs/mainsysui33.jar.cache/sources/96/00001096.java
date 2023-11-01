package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.AnimatedDialog;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/AnimatedDialog.class */
public final class AnimatedDialog {
    public final AnimatedBoundsLayoutListener backgroundLayoutListener;
    public final DialogLaunchAnimator.Callback callback;
    public final DialogLaunchAnimator.Controller controller;
    public View.OnLayoutChangeListener decorViewLayoutListener;
    public final Dialog dialog;
    public ViewGroup dialogContentWithBackground;
    public boolean dismissRequested;
    public boolean exitAnimationDisabled;
    public final boolean forceDisableSynchronization;
    public boolean hasInstrumentedJank;
    public final InteractionJankMonitor interactionJankMonitor;
    public boolean isDismissing;
    public boolean isOriginalDialogViewLaidOut;
    public boolean isSourceDrawnInDialog;
    public final LaunchAnimator launchAnimator;
    public final Function1<AnimatedDialog, Unit> onDialogDismissed;
    public final AnimatedDialog parentAnimatedDialog;
    public final Lazy decorView$delegate = LazyKt__LazyJVMKt.lazy(new Function0<ViewGroup>() { // from class: com.android.systemui.animation.AnimatedDialog$decorView$2
        {
            super(0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* renamed from: invoke */
        public final ViewGroup m1422invoke() {
            Window window = AnimatedDialog.this.getDialog().getWindow();
            Intrinsics.checkNotNull(window);
            return (ViewGroup) window.getDecorView();
        }
    });
    public int originalDialogBackgroundColor = -16777216;
    public boolean isLaunching = true;

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/AnimatedDialog$AnimatedBoundsLayoutListener.class */
    public static final class AnimatedBoundsLayoutListener implements View.OnLayoutChangeListener {
        public static final Companion Companion = new Companion(null);
        public ValueAnimator currentAnimator;
        public Rect lastBounds;

        /* loaded from: mainsysui33.jar:com/android/systemui/animation/AnimatedDialog$AnimatedBoundsLayoutListener$Companion.class */
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$1.onAnimationEnd(android.animation.Animator):void] */
        public static final /* synthetic */ void access$setCurrentAnimator$p(AnimatedBoundsLayoutListener animatedBoundsLayoutListener, ValueAnimator valueAnimator) {
            animatedBoundsLayoutListener.currentAnimator = valueAnimator;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(final View view, final int i, final int i2, final int i3, final int i4, int i5, int i6, int i7, int i8) {
            if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
                Rect rect = this.lastBounds;
                if (rect != null) {
                    view.setLeft(rect.left);
                    view.setTop(rect.top);
                    view.setRight(rect.right);
                    view.setBottom(rect.bottom);
                    return;
                }
                return;
            }
            if (this.lastBounds == null) {
                this.lastBounds = new Rect(i5, i6, i7, i8);
            }
            final Rect rect2 = this.lastBounds;
            Intrinsics.checkNotNull(rect2);
            final int i9 = rect2.left;
            final int i10 = rect2.top;
            final int i11 = rect2.right;
            final int i12 = rect2.bottom;
            ValueAnimator valueAnimator = this.currentAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.currentAnimator = null;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
            ofFloat.setDuration(500L);
            ofFloat.setInterpolator(Interpolators.STANDARD);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    AnimatedDialog.AnimatedBoundsLayoutListener.access$setCurrentAnimator$p(AnimatedDialog.AnimatedBoundsLayoutListener.this, null);
                }
            });
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    float animatedFraction = valueAnimator2.getAnimatedFraction();
                    rect2.left = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i9, i, animatedFraction));
                    rect2.top = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i10, i2, animatedFraction));
                    rect2.right = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i11, i3, animatedFraction));
                    rect2.bottom = MathKt__MathJVMKt.roundToInt(MathUtils.lerp(i12, i4, animatedFraction));
                    view.setLeft(rect2.left);
                    view.setTop(rect2.top);
                    view.setRight(rect2.right);
                    view.setBottom(rect2.bottom);
                }
            });
            this.currentAnimator = ofFloat;
            ofFloat.start();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r10v0, resolved type: kotlin.jvm.functions.Function1<? super com.android.systemui.animation.AnimatedDialog, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public AnimatedDialog(LaunchAnimator launchAnimator, DialogLaunchAnimator.Callback callback, InteractionJankMonitor interactionJankMonitor, DialogLaunchAnimator.Controller controller, Function1<? super AnimatedDialog, Unit> function1, Dialog dialog, boolean z, AnimatedDialog animatedDialog, boolean z2) {
        this.launchAnimator = launchAnimator;
        this.callback = callback;
        this.interactionJankMonitor = interactionJankMonitor;
        this.controller = controller;
        this.onDialogDismissed = function1;
        this.dialog = dialog;
        this.parentAnimatedDialog = animatedDialog;
        this.forceDisableSynchronization = z2;
        this.backgroundLayoutListener = z ? new AnimatedBoundsLayoutListener() : null;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.AnimatedDialog$moveSourceDrawingToDialog$2.invoke():void, com.android.systemui.animation.AnimatedDialog$start$2.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static final /* synthetic */ void access$maybeStartLaunchAnimation(AnimatedDialog animatedDialog) {
        animatedDialog.maybeStartLaunchAnimation();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.AnimatedDialog$moveSourceDrawingToDialog$1.run():void] */
    public static final /* synthetic */ void access$moveSourceDrawingToDialog(AnimatedDialog animatedDialog) {
        animatedDialog.moveSourceDrawingToDialog();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.AnimatedDialog$start$2.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static final /* synthetic */ void access$setOriginalDialogViewLaidOut$p(AnimatedDialog animatedDialog, boolean z) {
        animatedDialog.isOriginalDialogViewLaidOut = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.AnimatedDialog$moveSourceDrawingToDialog$2.invoke():void] */
    public static final /* synthetic */ void access$setSourceDrawnInDialog$p(AnimatedDialog animatedDialog, boolean z) {
        animatedDialog.isSourceDrawnInDialog = z;
    }

    public static /* synthetic */ void startAnimation$default(AnimatedDialog animatedDialog, boolean z, Function0 function0, Function0 function02, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = new Function0<Unit>() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$1
                public /* bridge */ /* synthetic */ Object invoke() {
                    m1429invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1429invoke() {
                }
            };
        }
        if ((i & 4) != 0) {
            function02 = new Function0<Unit>() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$2
                public /* bridge */ /* synthetic */ Object invoke() {
                    m1431invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1431invoke() {
                }
            };
        }
        animatedDialog.startAnimation(z, function0, function02);
    }

    public final ViewGroup findFirstViewGroupWithBackground(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getBackground() != null) {
                return viewGroup;
            }
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ViewGroup findFirstViewGroupWithBackground = findFirstViewGroupWithBackground(viewGroup.getChildAt(i));
                if (findFirstViewGroupWithBackground != null) {
                    return findFirstViewGroupWithBackground;
                }
            }
            return null;
        }
        return null;
    }

    public final DialogLaunchAnimator.Controller getController() {
        return this.controller;
    }

    public final ViewGroup getDecorView() {
        return (ViewGroup) this.decorView$delegate.getValue();
    }

    public final Dialog getDialog() {
        return this.dialog;
    }

    public final ViewGroup getDialogContentWithBackground() {
        return this.dialogContentWithBackground;
    }

    public final void hideDialogIntoView(final Function1<? super Boolean, Unit> function1) {
        if (this.decorViewLayoutListener != null) {
            getDecorView().removeOnLayoutChangeListener(this.decorViewLayoutListener);
        }
        if (shouldAnimateDialogIntoSource()) {
            startAnimation(false, new Function0<Unit>() { // from class: com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$1
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m1423invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1423invoke() {
                    AnimatedDialog.this.getDialog().getWindow().clearFlags(2);
                }
            }, new Function0<Unit>() { // from class: com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$2
                /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m1424invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1424invoke() {
                    AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener;
                    AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener2;
                    ViewGroup dialogContentWithBackground = AnimatedDialog.this.getDialogContentWithBackground();
                    Intrinsics.checkNotNull(dialogContentWithBackground);
                    dialogContentWithBackground.setVisibility(4);
                    animatedBoundsLayoutListener = AnimatedDialog.this.backgroundLayoutListener;
                    if (animatedBoundsLayoutListener != null) {
                        animatedBoundsLayoutListener2 = AnimatedDialog.this.backgroundLayoutListener;
                        dialogContentWithBackground.removeOnLayoutChangeListener(animatedBoundsLayoutListener2);
                    }
                    AnimatedDialog.this.getController().stopDrawingInOverlay();
                    final AnimatedDialog animatedDialog = AnimatedDialog.this;
                    final Function1<Boolean, Unit> function12 = function1;
                    animatedDialog.synchronizeNextDraw(new Function0<Unit>() { // from class: com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$2.1
                        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> */
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(0);
                        }

                        public /* bridge */ /* synthetic */ Object invoke() {
                            m1425invoke();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke  reason: collision with other method in class */
                        public final void m1425invoke() {
                            Function1 function13;
                            function12.invoke(Boolean.TRUE);
                            function13 = animatedDialog.onDialogDismissed;
                            function13.invoke(animatedDialog);
                        }
                    });
                }
            });
            return;
        }
        Log.i("DialogLaunchAnimator", "Skipping animation of dialog into the source");
        this.controller.onExitAnimationCancelled();
        function1.invoke(Boolean.FALSE);
        this.onDialogDismissed.invoke(this);
    }

    public final void maybeStartLaunchAnimation() {
        if (this.isSourceDrawnInDialog && this.isOriginalDialogViewLaidOut) {
            this.dialog.getWindow().addFlags(2);
            startAnimation$default(this, true, null, new Function0<Unit>() { // from class: com.android.systemui.animation.AnimatedDialog$maybeStartLaunchAnimation$1
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m1426invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1426invoke() {
                    boolean z;
                    AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener;
                    boolean z2;
                    InteractionJankMonitor interactionJankMonitor;
                    AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener2;
                    AnimatedDialog.this.isLaunching = false;
                    z = AnimatedDialog.this.dismissRequested;
                    if (z) {
                        AnimatedDialog.this.getDialog().dismiss();
                    }
                    animatedBoundsLayoutListener = AnimatedDialog.this.backgroundLayoutListener;
                    if (animatedBoundsLayoutListener != null) {
                        ViewGroup dialogContentWithBackground = AnimatedDialog.this.getDialogContentWithBackground();
                        Intrinsics.checkNotNull(dialogContentWithBackground);
                        animatedBoundsLayoutListener2 = AnimatedDialog.this.backgroundLayoutListener;
                        dialogContentWithBackground.addOnLayoutChangeListener(animatedBoundsLayoutListener2);
                    }
                    z2 = AnimatedDialog.this.hasInstrumentedJank;
                    if (z2) {
                        interactionJankMonitor = AnimatedDialog.this.interactionJankMonitor;
                        DialogCuj cuj = AnimatedDialog.this.getController().getCuj();
                        Intrinsics.checkNotNull(cuj);
                        interactionJankMonitor.end(cuj.getCujType());
                    }
                }
            }, 2, null);
        }
    }

    public final void moveSourceDrawingToDialog() {
        if (getDecorView().getViewRootImpl() == null) {
            getDecorView().post(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$moveSourceDrawingToDialog$1
                @Override // java.lang.Runnable
                public final void run() {
                    AnimatedDialog.access$moveSourceDrawingToDialog(AnimatedDialog.this);
                }
            });
            return;
        }
        this.controller.startDrawingInOverlayOf(getDecorView());
        synchronizeNextDraw(new Function0<Unit>() { // from class: com.android.systemui.animation.AnimatedDialog$moveSourceDrawingToDialog$2
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1427invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1427invoke() {
                AnimatedDialog.access$setSourceDrawnInDialog$p(AnimatedDialog.this, true);
                AnimatedDialog.access$maybeStartLaunchAnimation(AnimatedDialog.this);
            }
        });
    }

    public final void onDialogDismissed() {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            this.dialog.getContext().getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$onDialogDismissed$1
                @Override // java.lang.Runnable
                public final void run() {
                    AnimatedDialog.this.onDialogDismissed();
                }
            });
        } else if (this.isLaunching) {
            this.dismissRequested = true;
        } else if (this.isDismissing) {
        } else {
            this.isDismissing = true;
            hideDialogIntoView(new Function1<Boolean, Unit>() { // from class: com.android.systemui.animation.AnimatedDialog$onDialogDismissed$2
                {
                    super(1);
                }

                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke(((Boolean) obj).booleanValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(boolean z) {
                    if (z) {
                        AnimatedDialog.this.getDialog().hide();
                    }
                    AnimatedDialog.this.getDialog().setDismissOverride(null);
                    AnimatedDialog.this.getDialog().dismiss();
                }
            });
        }
    }

    public final void prepareForStackDismiss() {
        AnimatedDialog animatedDialog = this.parentAnimatedDialog;
        if (animatedDialog == null) {
            return;
        }
        animatedDialog.exitAnimationDisabled = true;
        animatedDialog.dialog.hide();
        this.parentAnimatedDialog.prepareForStackDismiss();
        this.parentAnimatedDialog.dialog.dismiss();
    }

    public final void setExitAnimationDisabled(boolean z) {
        this.exitAnimationDisabled = z;
    }

    public final boolean shouldAnimateDialogIntoSource() {
        if (this.exitAnimationDisabled || !this.dialog.isShowing() || this.callback.isDreaming()) {
            return false;
        }
        return this.controller.shouldAnimateExit();
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:97:0x00a3 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v5 */
    public final void start() {
        final FrameLayout frameLayout;
        ColorStateList color;
        InteractionJankMonitor.Configuration.Builder jankConfigurationBuilder;
        DialogCuj cuj = this.controller.getCuj();
        if (cuj != null && (jankConfigurationBuilder = this.controller.jankConfigurationBuilder()) != null) {
            if (cuj.getTag() != null) {
                jankConfigurationBuilder.setTag(cuj.getTag());
            }
            this.interactionJankMonitor.begin(jankConfigurationBuilder);
            this.hasInstrumentedJank = true;
        }
        this.dialog.create();
        final Window window = this.dialog.getWindow();
        Intrinsics.checkNotNull(window);
        if (window.getAttributes().width == -1 && window.getAttributes().height == -1) {
            frameLayout = null;
            int childCount = getDecorView().getChildCount();
            int i = 0;
            while (i < childCount) {
                frameLayout = findFirstViewGroupWithBackground(getDecorView().getChildAt(i));
                if (frameLayout != 0) {
                    break;
                }
                i++;
                frameLayout = frameLayout;
            }
            if (frameLayout == null) {
                throw new IllegalStateException("Unable to find ViewGroup with background");
            }
        } else {
            FrameLayout frameLayout2 = new FrameLayout(this.dialog.getContext());
            getDecorView().addView(frameLayout2, 0, new FrameLayout.LayoutParams(-1, -1));
            frameLayout = new FrameLayout(this.dialog.getContext());
            frameLayout.setBackground(getDecorView().getBackground());
            window.setBackgroundDrawableResource(17170445);
            frameLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AnimatedDialog.this.getDialog().dismiss();
                }
            });
            frameLayout.setClickable(true);
            frameLayout2.setImportantForAccessibility(2);
            frameLayout.setImportantForAccessibility(2);
            frameLayout2.addView(frameLayout, new FrameLayout.LayoutParams(window.getAttributes().width, window.getAttributes().height, window.getAttributes().gravity));
            int childCount2 = getDecorView().getChildCount();
            for (int i2 = 1; i2 < childCount2; i2++) {
                View childAt = getDecorView().getChildAt(1);
                getDecorView().removeViewAt(1);
                frameLayout.addView(childAt);
            }
            window.setLayout(-1, -1);
            this.decorViewLayoutListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$2
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                    if (window.getAttributes().width == -1 && window.getAttributes().height == -1) {
                        return;
                    }
                    ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
                    layoutParams.width = window.getAttributes().width;
                    layoutParams.height = window.getAttributes().height;
                    frameLayout.setLayoutParams(layoutParams);
                    window.setLayout(-1, -1);
                }
            };
            getDecorView().addOnLayoutChangeListener(this.decorViewLayoutListener);
        }
        this.dialogContentWithBackground = frameLayout;
        frameLayout.setTag(R$id.tag_dialog_background, Boolean.TRUE);
        GradientDrawable findGradientDrawable = GhostedViewLaunchAnimatorController.Companion.findGradientDrawable(frameLayout.getBackground());
        this.originalDialogBackgroundColor = (findGradientDrawable == null || (color = findGradientDrawable.getColor()) == null) ? -16777216 : color.getDefaultColor();
        frameLayout.setTransitionVisibility(4);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.windowAnimations = R$style.Animation_LaunchAnimation;
        attributes.layoutInDisplayCutoutMode = 3;
        boolean z = (attributes.getFitInsetsTypes() & WindowInsets.Type.navigationBars()) != 0;
        attributes.setFitInsetsTypes(attributes.getFitInsetsTypes() & (WindowInsets.Type.navigationBars() ^ (-1)));
        window.setAttributes(window.getAttributes());
        window.setDecorFitsSystemWindows(false);
        final boolean z2 = z;
        ((ViewGroup) frameLayout.getParent()).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Insets insets = windowInsets.getInsets(z2 ? WindowInsets.Type.displayCutout() | WindowInsets.Type.navigationBars() : WindowInsets.Type.displayCutout());
                view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                return WindowInsets.CONSUMED;
            }
        });
        final FrameLayout frameLayout3 = frameLayout;
        frameLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.animation.AnimatedDialog$start$2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                frameLayout3.removeOnLayoutChangeListener(this);
                AnimatedDialog.access$setOriginalDialogViewLaidOut$p(this, true);
                AnimatedDialog.access$maybeStartLaunchAnimation(this);
            }
        });
        window.clearFlags(2);
        this.dialog.setDismissOverride(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$start$3
            @Override // java.lang.Runnable
            public final void run() {
                AnimatedDialog.this.onDialogDismissed();
            }
        });
        this.dialog.show();
        moveSourceDrawingToDialog();
    }

    public final void startAnimation(boolean z, final Function0<Unit> function0, final Function0<Unit> function02) {
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController;
        GhostedViewLaunchAnimatorController createExitController;
        if (z) {
            ghostedViewLaunchAnimatorController = this.controller.createLaunchController();
        } else {
            ViewGroup viewGroup = this.dialogContentWithBackground;
            Intrinsics.checkNotNull(viewGroup);
            ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController(viewGroup, null, null, 6, null);
        }
        if (z) {
            ViewGroup viewGroup2 = this.dialogContentWithBackground;
            Intrinsics.checkNotNull(viewGroup2);
            createExitController = new GhostedViewLaunchAnimatorController(viewGroup2, null, null, 6, null);
        } else {
            createExitController = this.controller.createExitController();
        }
        ghostedViewLaunchAnimatorController.setLaunchContainer(getDecorView());
        createExitController.setLaunchContainer(getDecorView());
        final LaunchAnimator.State createAnimatorState = createExitController.createAnimatorState();
        final GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController2 = ghostedViewLaunchAnimatorController;
        final LaunchAnimator.Controller controller = createExitController;
        LaunchAnimator.startAnimation$default(this.launchAnimator, new LaunchAnimator.Controller() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1
            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public LaunchAnimator.State createAnimatorState() {
                return LaunchAnimator.Controller.this.createAnimatorState();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public ViewGroup getLaunchContainer() {
                return LaunchAnimator.Controller.this.getLaunchContainer();
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void onLaunchAnimationEnd(final boolean z2) {
                Executor mainExecutor = this.getDialog().getContext().getMainExecutor();
                final LaunchAnimator.Controller controller2 = LaunchAnimator.Controller.this;
                final LaunchAnimator.Controller controller3 = controller;
                final Function0<Unit> function03 = function02;
                mainExecutor.execute(new Runnable() { // from class: com.android.systemui.animation.AnimatedDialog$startAnimation$controller$1$onLaunchAnimationEnd$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchAnimator.Controller.this.onLaunchAnimationEnd(z2);
                        controller3.onLaunchAnimationEnd(z2);
                        function03.invoke();
                    }
                });
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
                LaunchAnimator.Controller.this.onLaunchAnimationProgress(state, f, f2);
                state.setVisible(!state.getVisible());
                controller.onLaunchAnimationProgress(state, f, f2);
                LaunchAnimator.Controller controller2 = controller;
                if (controller2 instanceof GhostedViewLaunchAnimatorController) {
                    ((GhostedViewLaunchAnimatorController) controller2).fillGhostedViewState(createAnimatorState);
                }
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void onLaunchAnimationStart(boolean z2) {
                function0.invoke();
                LaunchAnimator.Controller.this.onLaunchAnimationStart(z2);
                controller.onLaunchAnimationStart(z2);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void setLaunchContainer(ViewGroup viewGroup3) {
                LaunchAnimator.Controller.this.setLaunchContainer(viewGroup3);
                controller.setLaunchContainer(viewGroup3);
            }
        }, createAnimatorState, this.originalDialogBackgroundColor, false, false, 24, null);
    }

    public final void synchronizeNextDraw(Function0<Unit> function0) {
        ViewRootImpl viewRoot = this.controller.getViewRoot();
        View view = viewRoot != null ? viewRoot.getView() : null;
        if (this.forceDisableSynchronization || view == null) {
            function0.invoke();
            return;
        }
        ViewRootSync.INSTANCE.synchronizeNextDraw(view, getDecorView(), function0);
        getDecorView().invalidate();
        view.invalidate();
    }
}