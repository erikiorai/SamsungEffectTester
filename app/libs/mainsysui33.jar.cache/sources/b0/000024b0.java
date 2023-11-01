package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Looper;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScrollCaptureResponse;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotView;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.shared.system.InputChannelCompat;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.shared.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotView.class */
public class ScreenshotView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final String TAG = LogConfig.logTag(ScreenshotView.class);
    public final AccessibilityManager mAccessibilityManager;
    public ActionIntentExecutor mActionExecutor;
    public HorizontalScrollView mActionsContainer;
    public ImageView mActionsContainerBackground;
    public LinearLayout mActionsView;
    public ScreenshotViewCallback mCallbacks;
    public long mDefaultTimeoutOfTimeoutHandler;
    public boolean mDirectionLTR;
    public FrameLayout mDismissButton;
    public final DisplayMetrics mDisplayMetrics;
    public OverlayActionChip mEditChip;
    public final Interpolator mFastOutSlowIn;
    public final float mFixedSize;
    public FeatureFlags mFlags;
    public InputChannelCompat.InputEventReceiver mInputEventReceiver;
    public InputMonitorCompat mInputMonitor;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public ViewGroup mMessageContainer;
    public TextView mMessageContent;
    public int mNavMode;
    public boolean mOrientationPortrait;
    public String mPackageName;
    public PendingInteraction mPendingInteraction;
    public boolean mPendingSharedTransition;
    public OverlayActionChip mQuickShareChip;
    public final Resources mResources;
    public ImageView mScreenshotBadge;
    public ImageView mScreenshotFlash;
    public ImageView mScreenshotPreview;
    public View mScreenshotPreviewBorder;
    public DraggableConstraintLayout mScreenshotStatic;
    public OverlayActionChip mScrollChip;
    public ImageView mScrollablePreview;
    public ImageView mScrollingScrim;
    public OverlayActionChip mShareChip;
    public boolean mShowScrollablePreview;
    public final ArrayList<OverlayActionChip> mSmartChips;
    public final GestureDetector mSwipeDetector;
    public UiEventLogger mUiEventLogger;

    /* renamed from: com.android.systemui.screenshot.ScreenshotView$10 */
    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotView$10.class */
    public static /* synthetic */ class AnonymousClass10 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$screenshot$ScreenshotView$PendingInteraction;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x0036 -> B:49:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x003a -> B:47:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x003e -> B:53:0x002a). Please submit an issue!!! */
        static {
            int[] iArr = new int[PendingInteraction.values().length];
            $SwitchMap$com$android$systemui$screenshot$ScreenshotView$PendingInteraction = iArr;
            try {
                iArr[PendingInteraction.PREVIEW.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$ScreenshotView$PendingInteraction[PendingInteraction.SHARE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$ScreenshotView$PendingInteraction[PendingInteraction.EDIT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$ScreenshotView$PendingInteraction[PendingInteraction.QUICK_SHARE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* renamed from: com.android.systemui.screenshot.ScreenshotView$5 */
    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotView$5.class */
    public class AnonymousClass5 extends AnimatorListenerAdapter {
        public final /* synthetic */ Rect val$bounds;
        public final /* synthetic */ float val$cornerScale;
        public final /* synthetic */ PointF val$finalPos;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$5$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
        /* renamed from: $r8$lambda$gHpoFcLb8bT9Xh91c93Wt-0K7Zs */
        public static /* synthetic */ void m4317$r8$lambda$gHpoFcLb8bT9Xh91c93Wt0K7Zs(AnonymousClass5 anonymousClass5, View view) {
            anonymousClass5.lambda$onAnimationEnd$0(view);
        }

        public AnonymousClass5(PointF pointF, Rect rect, float f) {
            ScreenshotView.this = r4;
            this.val$finalPos = pointF;
            this.val$bounds = rect;
            this.val$cornerScale = f;
        }

        public /* synthetic */ void lambda$onAnimationEnd$0(View view) {
            ScreenshotView.this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_EXPLICIT_DISMISSAL, 0, ScreenshotView.this.mPackageName);
            ScreenshotView.this.animateDismissal();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            ScreenshotView.this.mInteractionJankMonitor.cancel(54);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ScreenshotView.this.mDismissButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$5$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ScreenshotView.AnonymousClass5.m4317$r8$lambda$gHpoFcLb8bT9Xh91c93Wt0K7Zs(ScreenshotView.AnonymousClass5.this, view);
                }
            });
            ScreenshotView.this.mDismissButton.setAlpha(1.0f);
            float width = ScreenshotView.this.mDismissButton.getWidth() / 2.0f;
            ScreenshotView.this.mDismissButton.setX(ScreenshotView.this.mDirectionLTR ? (this.val$finalPos.x - width) + ((this.val$bounds.width() * this.val$cornerScale) / 2.0f) : (this.val$finalPos.x - width) - ((this.val$bounds.width() * this.val$cornerScale) / 2.0f));
            ScreenshotView.this.mDismissButton.setY((this.val$finalPos.y - width) - ((this.val$bounds.height() * this.val$cornerScale) / 2.0f));
            ScreenshotView.this.mScreenshotPreview.setScaleX(1.0f);
            ScreenshotView.this.mScreenshotPreview.setScaleY(1.0f);
            ScreenshotView.this.mScreenshotPreview.setX(this.val$finalPos.x - (ScreenshotView.this.mScreenshotPreview.getWidth() / 2.0f));
            ScreenshotView.this.mScreenshotPreview.setY(this.val$finalPos.y - (ScreenshotView.this.mScreenshotPreview.getHeight() / 2.0f));
            ScreenshotView.this.requestLayout();
            ScreenshotView.this.mInteractionJankMonitor.end(54);
            ScreenshotView.this.createScreenshotActionsShadeAnimation().start();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            ScreenshotView.this.mInteractionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(54, ScreenshotView.this.mScreenshotPreview).setTag("DropIn"));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotView$PendingInteraction.class */
    public enum PendingInteraction {
        PREVIEW,
        EDIT,
        SHARE,
        QUICK_SHARE
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotView$ScreenshotViewCallback.class */
    public interface ScreenshotViewCallback {
        void onDismiss();

        void onTouchOutside();

        void onUserInteraction();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$01RrDvv6GRmhsK13tSg3jieNLCk(ScreenshotView screenshotView, View view) {
        screenshotView.lambda$addQuickShareChip$16(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda0.onInputEvent(android.view.InputEvent):void] */
    public static /* synthetic */ void $r8$lambda$3BvmuZAvWHzY_61MPNmWWjAT1nE(ScreenshotView screenshotView, InputEvent inputEvent) {
        screenshotView.lambda$startInputListening$1(inputEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda20.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$7xFc3dTZc8Lxxqd43RaCfoifDVg(ScreenshotView screenshotView, String str, Runnable runnable, View view) {
        screenshotView.lambda$showScrollChip$0(str, runnable, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda9.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$8Akrvo5w1hJ-6cz6t7Cg2JamHrA */
    public static /* synthetic */ void m4299$r8$lambda$8Akrvo5w1hJ6cz6t7Cg2JamHrA(ScreenshotView screenshotView, ValueAnimator valueAnimator) {
        screenshotView.lambda$createScreenshotDropInAnimation$6(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda13.run():void] */
    public static /* synthetic */ void $r8$lambda$ICx6SCMp0w3iU4gbcMtKcvg0qXs(ScreenshotView screenshotView) {
        screenshotView.lambda$setChipIntents$14();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda18.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$Mm5-D0IOwJecIP8MYyZ0j6p_eVU */
    public static /* synthetic */ void m4300$r8$lambda$Mm5D0IOwJecIP8MYyZ0j6p_eVU(ScreenshotView screenshotView, float f, ArrayList arrayList, ValueAnimator valueAnimator) {
        screenshotView.lambda$createScreenshotActionsShadeAnimation$10(f, arrayList, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda19.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$Splk5UJ7yIWM45hPMUav0ErC_a8(ScreenshotView screenshotView, ValueAnimator valueAnimator) {
        screenshotView.lambda$createScreenshotFadeDismissAnimation$21(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda16.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$SyT_JmFjCwz0ON9aD-8DkalME8k */
    public static /* synthetic */ void m4301$r8$lambda$SyT_JmFjCwz0ON9aD8DkalME8k(ScreenshotView screenshotView, View view) {
        screenshotView.lambda$createScreenshotActionsShadeAnimation$8(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda14.run():void] */
    public static /* synthetic */ void $r8$lambda$W0hL6gkY55ONp8vZrNUtcRYkNM0(ScreenshotView screenshotView) {
        screenshotView.lambda$setChipIntents$15();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda15.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$ZDnk05nGnD34sisc_MjFpnnKi0I(ScreenshotView screenshotView, View view) {
        screenshotView.lambda$createScreenshotActionsShadeAnimation$7(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda8.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$_DUavp4ORJypIX1vEHYZVQlfwyY(ScreenshotView screenshotView, float f, float f2, float f3, PointF pointF, PointF pointF2, float f4, ValueAnimator valueAnimator) {
        screenshotView.lambda$createScreenshotDropInAnimation$5(f, f2, f3, pointF, pointF2, f4, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda3.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$fStr70ti2hcVeoJsckVBThCDxsc(ScreenshotView screenshotView, float f, float f2, Rect rect, float f3, ValueAnimator valueAnimator) {
        screenshotView.lambda$startLongScreenshotTransition$18(f, f2, rect, f3, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda5.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$iYYy_HyNZ73sjEGyJeWeeiWC_Ws(ScreenshotView screenshotView, View view) {
        screenshotView.lambda$showWorkProfileMessage$2(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda6.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$kwhH2rwDCsypySV9tJoRvXUqSlw(ScreenshotView screenshotView, ValueAnimator valueAnimator) {
        screenshotView.lambda$createScreenshotDropInAnimation$3(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$l2XZFFWeu-ZkuOFYx_G71BAfGDc */
    public static /* synthetic */ void m4302$r8$lambda$l2XZFFWeuZkuOFYx_G71BAfGDc(ScreenshotView screenshotView, ValueAnimator valueAnimator) {
        screenshotView.lambda$startLongScreenshotTransition$17(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda4.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$m0iKOORzK9aODjaXg8q9v2CSzeM(ScreenshotView screenshotView, ValueAnimator valueAnimator) {
        screenshotView.lambda$startLongScreenshotTransition$19(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda17.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$mlHfK227IczLFrh4KmJXK4Kx4Rw(ScreenshotView screenshotView, View view) {
        screenshotView.lambda$createScreenshotActionsShadeAnimation$9(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda21.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$vrt85z26vUo3rxMOI0UfkO7qwdQ(ScreenshotView screenshotView, ValueAnimator valueAnimator) {
        screenshotView.lambda$prepareScrollingTransition$20(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda7.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$wxU7Y3OyN83Yi72kdK1SCQPZ8S0(ScreenshotView screenshotView, ValueAnimator valueAnimator) {
        screenshotView.lambda$createScreenshotDropInAnimation$4(valueAnimator);
    }

    public ScreenshotView(Context context) {
        this(context, null);
    }

    public ScreenshotView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScreenshotView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ScreenshotView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPackageName = "";
        this.mSmartChips = new ArrayList<>();
        Resources resources = ((FrameLayout) this).mContext.getResources();
        this.mResources = resources;
        this.mInteractionJankMonitor = getInteractionJankMonitorInstance();
        this.mFixedSize = resources.getDimensionPixelSize(R$dimen.overlay_x_scale);
        this.mFastOutSlowIn = AnimationUtils.loadInterpolator(((FrameLayout) this).mContext, 17563661);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        ((FrameLayout) this).mContext.getDisplay().getRealMetrics(displayMetrics);
        this.mAccessibilityManager = AccessibilityManager.getInstance(((FrameLayout) this).mContext);
        GestureDetector gestureDetector = new GestureDetector(((FrameLayout) this).mContext, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.screenshot.ScreenshotView.1
            public final Rect mActionsRect = new Rect();

            {
                ScreenshotView.this = this;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                ScreenshotView.this.mActionsContainer.getBoundsOnScreen(this.mActionsRect);
                return (this.mActionsRect.contains((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY()) && ScreenshotView.this.mActionsContainer.canScrollHorizontally((int) f)) ? false : true;
            }
        });
        this.mSwipeDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.ScreenshotView.2
            {
                ScreenshotView.this = this;
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                ScreenshotView.this.startInputListening();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                ScreenshotView.this.stopInputListening();
            }
        });
    }

    public static Drawable createScreenDrawable(Resources resources, Bitmap bitmap, Insets insets) {
        int width = (bitmap.getWidth() - insets.left) - insets.right;
        int height = (bitmap.getHeight() - insets.top) - insets.bottom;
        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
        if (height != 0 && width != 0 && bitmap.getWidth() != 0 && bitmap.getHeight() != 0) {
            float f = width;
            float f2 = height;
            InsetDrawable insetDrawable = new InsetDrawable(bitmapDrawable, (insets.left * (-1.0f)) / f, (insets.top * (-1.0f)) / f2, (insets.right * (-1.0f)) / f, (insets.bottom * (-1.0f)) / f2);
            return (insets.left < 0 || insets.top < 0 || insets.right < 0 || insets.bottom < 0) ? new LayerDrawable(new Drawable[]{new ColorDrawable(-16777216), insetDrawable}) : insetDrawable;
        }
        Log.e(TAG, "Can't create inset drawable, using 0 insets bitmap and insets create degenerate region: " + bitmap.getWidth() + "x" + bitmap.getHeight() + " " + bitmapDrawable);
        return bitmapDrawable;
    }

    public /* synthetic */ void lambda$addQuickShareChip$16(View view) {
        this.mShareChip.setIsPending(false);
        this.mEditChip.setIsPending(false);
        this.mQuickShareChip.setIsPending(true);
        this.mPendingInteraction = PendingInteraction.QUICK_SHARE;
    }

    public /* synthetic */ void lambda$createScreenshotActionsShadeAnimation$10(float f, ArrayList arrayList, ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        float f2 = animatedFraction < f ? animatedFraction / f : 1.0f;
        this.mActionsContainer.setAlpha(f2);
        this.mActionsContainerBackground.setAlpha(f2);
        float f3 = (0.3f * animatedFraction) + 0.7f;
        this.mActionsContainer.setScaleX(f3);
        this.mActionsContainerBackground.setScaleX(f3);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            OverlayActionChip overlayActionChip = (OverlayActionChip) it.next();
            overlayActionChip.setAlpha(animatedFraction);
            overlayActionChip.setScaleX(1.0f / f3);
        }
        HorizontalScrollView horizontalScrollView = this.mActionsContainer;
        horizontalScrollView.setScrollX(this.mDirectionLTR ? 0 : horizontalScrollView.getWidth());
        HorizontalScrollView horizontalScrollView2 = this.mActionsContainer;
        horizontalScrollView2.setPivotX(this.mDirectionLTR ? 0.0f : horizontalScrollView2.getWidth());
        ImageView imageView = this.mActionsContainerBackground;
        imageView.setPivotX(this.mDirectionLTR ? 0.0f : imageView.getWidth());
    }

    public /* synthetic */ void lambda$createScreenshotActionsShadeAnimation$7(View view) {
        this.mShareChip.setIsPending(true);
        this.mEditChip.setIsPending(false);
        OverlayActionChip overlayActionChip = this.mQuickShareChip;
        if (overlayActionChip != null) {
            overlayActionChip.setIsPending(false);
        }
        this.mPendingInteraction = PendingInteraction.SHARE;
    }

    public /* synthetic */ void lambda$createScreenshotActionsShadeAnimation$8(View view) {
        this.mEditChip.setIsPending(true);
        this.mShareChip.setIsPending(false);
        OverlayActionChip overlayActionChip = this.mQuickShareChip;
        if (overlayActionChip != null) {
            overlayActionChip.setIsPending(false);
        }
        this.mPendingInteraction = PendingInteraction.EDIT;
    }

    public /* synthetic */ void lambda$createScreenshotActionsShadeAnimation$9(View view) {
        this.mShareChip.setIsPending(false);
        this.mEditChip.setIsPending(false);
        OverlayActionChip overlayActionChip = this.mQuickShareChip;
        if (overlayActionChip != null) {
            overlayActionChip.setIsPending(false);
        }
        this.mPendingInteraction = PendingInteraction.PREVIEW;
    }

    public /* synthetic */ void lambda$createScreenshotDropInAnimation$3(ValueAnimator valueAnimator) {
        this.mScreenshotFlash.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public /* synthetic */ void lambda$createScreenshotDropInAnimation$4(ValueAnimator valueAnimator) {
        this.mScreenshotFlash.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public /* synthetic */ void lambda$createScreenshotDropInAnimation$5(float f, float f2, float f3, PointF pointF, PointF pointF2, float f4, ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        if (animatedFraction < f) {
            float lerp = MathUtils.lerp(f2, 1.0f, this.mFastOutSlowIn.getInterpolation(animatedFraction / f));
            this.mScreenshotPreview.setScaleX(lerp);
            this.mScreenshotPreview.setScaleY(lerp);
        } else {
            this.mScreenshotPreview.setScaleX(1.0f);
            this.mScreenshotPreview.setScaleY(1.0f);
        }
        if (animatedFraction < f3) {
            float lerp2 = MathUtils.lerp(pointF.x, pointF2.x, this.mFastOutSlowIn.getInterpolation(animatedFraction / f3));
            ImageView imageView = this.mScreenshotPreview;
            imageView.setX(lerp2 - (imageView.getWidth() / 2.0f));
        } else {
            ImageView imageView2 = this.mScreenshotPreview;
            imageView2.setX(pointF2.x - (imageView2.getWidth() / 2.0f));
        }
        float lerp3 = MathUtils.lerp(pointF.y, pointF2.y, this.mFastOutSlowIn.getInterpolation(animatedFraction));
        ImageView imageView3 = this.mScreenshotPreview;
        imageView3.setY(lerp3 - (imageView3.getHeight() / 2.0f));
        if (animatedFraction >= f4) {
            this.mDismissButton.setAlpha((animatedFraction - f4) / (1.0f - f4));
            float x = this.mScreenshotPreview.getX();
            float y = this.mScreenshotPreview.getY();
            FrameLayout frameLayout = this.mDismissButton;
            frameLayout.setY(y - (frameLayout.getHeight() / 2.0f));
            if (this.mDirectionLTR) {
                this.mDismissButton.setX((x + this.mScreenshotPreview.getWidth()) - (this.mDismissButton.getWidth() / 2.0f));
                return;
            }
            FrameLayout frameLayout2 = this.mDismissButton;
            frameLayout2.setX(x - (frameLayout2.getWidth() / 2.0f));
        }
    }

    public /* synthetic */ void lambda$createScreenshotDropInAnimation$6(ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        this.mScreenshotPreviewBorder.setAlpha(animatedFraction);
        this.mScreenshotBadge.setAlpha(animatedFraction);
    }

    public /* synthetic */ void lambda$createScreenshotFadeDismissAnimation$21(ValueAnimator valueAnimator) {
        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
        this.mDismissButton.setAlpha(animatedFraction);
        this.mActionsContainerBackground.setAlpha(animatedFraction);
        this.mActionsContainer.setAlpha(animatedFraction);
        this.mScreenshotPreviewBorder.setAlpha(animatedFraction);
        this.mScreenshotBadge.setAlpha(animatedFraction);
    }

    public /* synthetic */ void lambda$prepareScrollingTransition$20(ValueAnimator valueAnimator) {
        this.mScrollingScrim.setImageTintList(ColorStateList.valueOf(Color.argb(((Float) valueAnimator.getAnimatedValue()).floatValue(), (float) ActionBarShadowController.ELEVATION_LOW, (float) ActionBarShadowController.ELEVATION_LOW, (float) ActionBarShadowController.ELEVATION_LOW)));
    }

    public /* synthetic */ void lambda$setChipIntents$11(ScreenshotController.SavedImageData savedImageData, View view) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SHARE_TAPPED, 0, this.mPackageName);
        if (!this.mFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY)) {
            startSharedTransition(savedImageData.shareTransition.get());
            return;
        }
        prepareSharedTransition();
        this.mActionExecutor.launchIntentAsync(ActionIntentCreator.INSTANCE.createShareIntent(savedImageData.uri, savedImageData.subject), savedImageData.shareTransition.get().bundle, savedImageData.owner.getIdentifier(), false);
    }

    public /* synthetic */ void lambda$setChipIntents$12(ScreenshotController.SavedImageData savedImageData, View view) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_EDIT_TAPPED, 0, this.mPackageName);
        if (!this.mFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY)) {
            startSharedTransition(savedImageData.editTransition.get());
            return;
        }
        prepareSharedTransition();
        this.mActionExecutor.launchIntentAsync(ActionIntentCreator.INSTANCE.createEditIntent(savedImageData.uri, ((FrameLayout) this).mContext), savedImageData.editTransition.get().bundle, savedImageData.owner.getIdentifier(), true);
    }

    public /* synthetic */ void lambda$setChipIntents$13(ScreenshotController.SavedImageData savedImageData, View view) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_PREVIEW_TAPPED, 0, this.mPackageName);
        if (!this.mFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY)) {
            startSharedTransition(savedImageData.viewTransition.get());
            return;
        }
        prepareSharedTransition();
        this.mActionExecutor.launchIntentAsync(ActionIntentCreator.INSTANCE.createEditIntent(savedImageData.uri, ((FrameLayout) this).mContext), savedImageData.viewTransition.get().bundle, savedImageData.owner.getIdentifier(), true);
    }

    public /* synthetic */ void lambda$setChipIntents$14() {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SMART_ACTION_TAPPED, 0, this.mPackageName);
        animateDismissal();
    }

    public /* synthetic */ void lambda$setChipIntents$15() {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SMART_ACTION_TAPPED, 0, this.mPackageName);
        animateDismissal();
    }

    public /* synthetic */ void lambda$showScrollChip$0(String str, Runnable runnable, View view) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_REQUESTED, 0, str);
        runnable.run();
    }

    public /* synthetic */ void lambda$showWorkProfileMessage$2(View view) {
        this.mMessageContainer.setVisibility(8);
    }

    public /* synthetic */ void lambda$startInputListening$1(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            if (motionEvent.getActionMasked() != 0 || getTouchRegion(false).contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                return;
            }
            this.mCallbacks.onTouchOutside();
        }
    }

    public /* synthetic */ void lambda$startLongScreenshotTransition$17(ValueAnimator valueAnimator) {
        this.mScrollingScrim.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
    }

    public /* synthetic */ void lambda$startLongScreenshotTransition$18(float f, float f2, Rect rect, float f3, ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        float lerp = MathUtils.lerp(1.0f, f, animatedFraction);
        this.mScrollablePreview.setScaleX(lerp);
        this.mScrollablePreview.setScaleY(lerp);
        this.mScrollablePreview.setX(MathUtils.lerp(f2, rect.left, animatedFraction));
        this.mScrollablePreview.setY(MathUtils.lerp(f3, rect.top, animatedFraction));
    }

    public /* synthetic */ void lambda$startLongScreenshotTransition$19(ValueAnimator valueAnimator) {
        this.mScrollablePreview.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
    }

    public void addQuickShareChip(Notification.Action action) {
        OverlayActionChip overlayActionChip = this.mQuickShareChip;
        if (overlayActionChip != null) {
            this.mSmartChips.remove(overlayActionChip);
            this.mActionsView.removeView(this.mQuickShareChip);
        }
        if (this.mPendingInteraction == PendingInteraction.QUICK_SHARE) {
            this.mPendingInteraction = null;
        }
        if (this.mPendingInteraction == null) {
            OverlayActionChip overlayActionChip2 = (OverlayActionChip) LayoutInflater.from(((FrameLayout) this).mContext).inflate(R$layout.overlay_action_chip, (ViewGroup) this.mActionsView, false);
            this.mQuickShareChip = overlayActionChip2;
            overlayActionChip2.setText(action.title);
            this.mQuickShareChip.setIcon(action.getIcon(), false);
            this.mQuickShareChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ScreenshotView.$r8$lambda$01RrDvv6GRmhsK13tSg3jieNLCk(ScreenshotView.this, view);
                }
            });
            this.mQuickShareChip.setAlpha(1.0f);
            this.mActionsView.addView(this.mQuickShareChip);
            this.mSmartChips.add(this.mQuickShareChip);
        }
    }

    public void animateDismissal() {
        this.mScreenshotStatic.dismiss();
    }

    public void badgeScreenshot(Drawable drawable) {
        this.mScreenshotBadge.setImageDrawable(drawable);
        this.mScreenshotBadge.setVisibility(drawable != null ? 0 : 8);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0136 -> B:13:0x0008). Please submit an issue!!! */
    public ValueAnimator createScreenshotActionsShadeAnimation() {
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException e) {
        }
        final ArrayList arrayList = new ArrayList();
        this.mShareChip.setContentDescription(((FrameLayout) this).mContext.getString(R$string.screenshot_share_description));
        this.mShareChip.setIcon(Icon.createWithResource(((FrameLayout) this).mContext, R$drawable.ic_screenshot_share), true);
        this.mShareChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenshotView.$r8$lambda$ZDnk05nGnD34sisc_MjFpnnKi0I(ScreenshotView.this, view);
            }
        });
        arrayList.add(this.mShareChip);
        this.mEditChip.setContentDescription(((FrameLayout) this).mContext.getString(R$string.screenshot_edit_description));
        this.mEditChip.setIcon(Icon.createWithResource(((FrameLayout) this).mContext, R$drawable.ic_screenshot_edit), true);
        this.mEditChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenshotView.m4301$r8$lambda$SyT_JmFjCwz0ON9aD8DkalME8k(ScreenshotView.this, view);
            }
        });
        arrayList.add(this.mEditChip);
        this.mScreenshotPreview.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenshotView.$r8$lambda$mlHfK227IczLFrh4KmJXK4Kx4Rw(ScreenshotView.this, view);
            }
        });
        this.mScrollChip.setText(((FrameLayout) this).mContext.getString(R$string.screenshot_scroll_label));
        this.mScrollChip.setIcon(Icon.createWithResource(((FrameLayout) this).mContext, R$drawable.ic_screenshot_scroll), true);
        arrayList.add(this.mScrollChip);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mActionsView.getChildAt(0).getLayoutParams();
        layoutParams.setMarginEnd(0);
        this.mActionsView.getChildAt(0).setLayoutParams(layoutParams);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setDuration(400L);
        this.mActionsContainer.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mActionsContainerBackground.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mActionsContainer.setVisibility(0);
        this.mActionsContainerBackground.setVisibility(0);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotView.6
            {
                ScreenshotView.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                ScreenshotView.this.mInteractionJankMonitor.cancel(54);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ScreenshotView.this.mInteractionJankMonitor.end(54);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                ScreenshotView.this.mInteractionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(54, ScreenshotView.this.mScreenshotStatic).setTag("Actions").setTimeout(ScreenshotView.this.mDefaultTimeoutOfTimeoutHandler));
            }
        });
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda18
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView.m4300$r8$lambda$Mm5D0IOwJecIP8MYyZ0j6p_eVU(ScreenshotView.this, r5, arrayList, valueAnimator);
            }
        });
        return ofFloat;
    }

    public AnimatorSet createScreenshotDropInAnimation(Rect rect, boolean z) {
        Rect rect2 = new Rect();
        this.mScreenshotPreview.getHitRect(rect2);
        float width = this.mFixedSize / (this.mOrientationPortrait ? rect.width() : rect.height());
        final float f = 1.0f / width;
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setDuration(133L);
        ofFloat.setInterpolator(this.mFastOutSlowIn);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView.$r8$lambda$kwhH2rwDCsypySV9tJoRvXUqSlw(ScreenshotView.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
        ofFloat2.setDuration(217L);
        ofFloat2.setInterpolator(this.mFastOutSlowIn);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView.$r8$lambda$wxU7Y3OyN83Yi72kdK1SCQPZ8S0(ScreenshotView.this, valueAnimator);
            }
        });
        final PointF pointF = new PointF(rect.centerX(), rect.centerY());
        final PointF pointF2 = new PointF(rect2.exactCenterX(), rect2.exactCenterY());
        int[] locationOnScreen = this.mScreenshotPreview.getLocationOnScreen();
        pointF.offset(rect2.left - locationOnScreen[0], rect2.top - locationOnScreen[1]);
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat3.setDuration(500L);
        ofFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotView.4
            {
                ScreenshotView.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                ScreenshotView.this.mScreenshotPreview.setScaleX(f);
                ScreenshotView.this.mScreenshotPreview.setScaleY(f);
                ScreenshotView.this.mScreenshotPreview.setVisibility(0);
                if (ScreenshotView.this.mAccessibilityManager.isEnabled()) {
                    ScreenshotView.this.mDismissButton.setAlpha(ActionBarShadowController.ELEVATION_LOW);
                    ScreenshotView.this.mDismissButton.setVisibility(0);
                }
            }
        });
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView.$r8$lambda$_DUavp4ORJypIX1vEHYZVQlfwyY(ScreenshotView.this, r5, f, r7, pointF, pointF2, r10, valueAnimator);
            }
        });
        this.mScreenshotFlash.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mScreenshotFlash.setVisibility(0);
        ValueAnimator ofFloat4 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat4.setDuration(100L);
        ofFloat4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView.m4299$r8$lambda$8Akrvo5w1hJ6cz6t7Cg2JamHrA(ScreenshotView.this, valueAnimator);
            }
        });
        if (z) {
            animatorSet.play(ofFloat2).after(ofFloat);
            animatorSet.play(ofFloat2).with(ofFloat3);
        } else {
            animatorSet.play(ofFloat3);
        }
        animatorSet.play(ofFloat4).after(ofFloat3);
        animatorSet.addListener(new AnonymousClass5(pointF2, rect, width));
        return animatorSet;
    }

    public ValueAnimator createScreenshotFadeDismissAnimation() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda19
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView.$r8$lambda$Splk5UJ7yIWM45hPMUav0ErC_a8(ScreenshotView.this, valueAnimator);
            }
        });
        ofFloat.setDuration(600L);
        return ofFloat;
    }

    public final InteractionJankMonitor getInteractionJankMonitorInstance() {
        return InteractionJankMonitor.getInstance();
    }

    public View getScreenshotPreview() {
        return this.mScreenshotPreview;
    }

    public final Region getSwipeRegion() {
        Region region = new Region();
        Rect rect = new Rect();
        this.mScreenshotPreview.getBoundsOnScreen(rect);
        rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
        region.op(rect, Region.Op.UNION);
        this.mActionsContainerBackground.getBoundsOnScreen(rect);
        rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
        region.op(rect, Region.Op.UNION);
        this.mDismissButton.getBoundsOnScreen(rect);
        region.op(rect, Region.Op.UNION);
        this.mMessageContainer.findViewById(R$id.message_dismiss_button).getBoundsOnScreen(rect);
        region.op(rect, Region.Op.UNION);
        return region;
    }

    public final Region getTouchRegion(boolean z) {
        Region swipeRegion = getSwipeRegion();
        if (z && this.mScrollingScrim.getVisibility() == 0) {
            Rect rect = new Rect();
            this.mScrollingScrim.getBoundsOnScreen(rect);
            swipeRegion.op(rect, Region.Op.UNION);
        }
        if (QuickStepContract.isGesturalMode(this.mNavMode)) {
            Insets insets = ((WindowManager) ((FrameLayout) this).mContext.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
            Rect rect2 = new Rect(0, 0, insets.left, this.mDisplayMetrics.heightPixels);
            swipeRegion.op(rect2, Region.Op.UNION);
            DisplayMetrics displayMetrics = this.mDisplayMetrics;
            int i = displayMetrics.widthPixels;
            rect2.set(i - insets.right, 0, i, displayMetrics.heightPixels);
            swipeRegion.op(rect2, Region.Op.UNION);
        }
        return swipeRegion;
    }

    public void hideScrollChip() {
        this.mScrollChip.setVisibility(8);
    }

    public void init(UiEventLogger uiEventLogger, ScreenshotViewCallback screenshotViewCallback, ActionIntentExecutor actionIntentExecutor, FeatureFlags featureFlags) {
        this.mUiEventLogger = uiEventLogger;
        this.mCallbacks = screenshotViewCallback;
        this.mActionExecutor = actionIntentExecutor;
        this.mFlags = featureFlags;
    }

    public boolean isDismissing() {
        return this.mScreenshotStatic.isDismissing();
    }

    public boolean isPendingSharedTransition() {
        return this.mPendingSharedTransition;
    }

    public void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(getTouchRegion(true));
    }

    @Override // android.view.View
    public void onFinishInflate() {
        ImageView imageView = (ImageView) findViewById(R$id.screenshot_scrolling_scrim);
        Objects.requireNonNull(imageView);
        this.mScrollingScrim = imageView;
        DraggableConstraintLayout draggableConstraintLayout = (DraggableConstraintLayout) findViewById(R$id.screenshot_static);
        Objects.requireNonNull(draggableConstraintLayout);
        this.mScreenshotStatic = draggableConstraintLayout;
        ViewGroup viewGroup = (ViewGroup) draggableConstraintLayout.findViewById(R$id.screenshot_message_container);
        Objects.requireNonNull(viewGroup);
        this.mMessageContainer = viewGroup;
        TextView textView = (TextView) viewGroup.findViewById(R$id.screenshot_message_content);
        Objects.requireNonNull(textView);
        this.mMessageContent = textView;
        ImageView imageView2 = (ImageView) findViewById(R$id.screenshot_preview);
        Objects.requireNonNull(imageView2);
        this.mScreenshotPreview = imageView2;
        View findViewById = findViewById(R$id.screenshot_preview_border);
        Objects.requireNonNull(findViewById);
        this.mScreenshotPreviewBorder = findViewById;
        this.mScreenshotPreview.setClipToOutline(true);
        ImageView imageView3 = (ImageView) findViewById(R$id.screenshot_badge);
        Objects.requireNonNull(imageView3);
        this.mScreenshotBadge = imageView3;
        ImageView imageView4 = (ImageView) findViewById(R$id.actions_container_background);
        Objects.requireNonNull(imageView4);
        this.mActionsContainerBackground = imageView4;
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R$id.actions_container);
        Objects.requireNonNull(horizontalScrollView);
        this.mActionsContainer = horizontalScrollView;
        LinearLayout linearLayout = (LinearLayout) findViewById(R$id.screenshot_actions);
        Objects.requireNonNull(linearLayout);
        this.mActionsView = linearLayout;
        FrameLayout frameLayout = (FrameLayout) findViewById(R$id.screenshot_dismiss_button);
        Objects.requireNonNull(frameLayout);
        this.mDismissButton = frameLayout;
        ImageView imageView5 = (ImageView) findViewById(R$id.screenshot_scrollable_preview);
        Objects.requireNonNull(imageView5);
        this.mScrollablePreview = imageView5;
        ImageView imageView6 = (ImageView) findViewById(R$id.screenshot_flash);
        Objects.requireNonNull(imageView6);
        this.mScreenshotFlash = imageView6;
        OverlayActionChip overlayActionChip = (OverlayActionChip) this.mActionsContainer.findViewById(R$id.screenshot_share_chip);
        Objects.requireNonNull(overlayActionChip);
        this.mShareChip = overlayActionChip;
        OverlayActionChip overlayActionChip2 = (OverlayActionChip) this.mActionsContainer.findViewById(R$id.screenshot_edit_chip);
        Objects.requireNonNull(overlayActionChip2);
        this.mEditChip = overlayActionChip2;
        OverlayActionChip overlayActionChip3 = (OverlayActionChip) this.mActionsContainer.findViewById(R$id.screenshot_scroll_chip);
        Objects.requireNonNull(overlayActionChip3);
        this.mScrollChip = overlayActionChip3;
        int dpToPx = (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 12.0f);
        this.mScreenshotPreview.setTouchDelegate(new TouchDelegate(new Rect(dpToPx, dpToPx, dpToPx, dpToPx), this.mScreenshotPreview));
        this.mActionsContainerBackground.setTouchDelegate(new TouchDelegate(new Rect(dpToPx, dpToPx, dpToPx, dpToPx), this.mActionsContainerBackground));
        setFocusable(true);
        this.mActionsContainer.setScrollX(0);
        this.mNavMode = getResources().getInteger(17694906);
        this.mOrientationPortrait = getResources().getConfiguration().orientation == 1;
        boolean z = false;
        if (getResources().getConfiguration().getLayoutDirection() == 0) {
            z = true;
        }
        this.mDirectionLTR = z;
        setFocusableInTouchMode(true);
        requestFocus();
        this.mScreenshotStatic.setCallbacks(new DraggableConstraintLayout.SwipeDismissCallbacks() { // from class: com.android.systemui.screenshot.ScreenshotView.3
            {
                ScreenshotView.this = this;
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onDismissComplete() {
                if (ScreenshotView.this.mInteractionJankMonitor.isInstrumenting(54)) {
                    ScreenshotView.this.mInteractionJankMonitor.end(54);
                }
                ScreenshotView.this.mCallbacks.onDismiss();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onInteraction() {
                ScreenshotView.this.mCallbacks.onUserInteraction();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onSwipeDismissInitiated(Animator animator) {
                ScreenshotView.this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SWIPE_DISMISSED, 0, ScreenshotView.this.mPackageName);
            }
        });
    }

    public void prepareScrollingTransition(ScrollCaptureResponse scrollCaptureResponse, Bitmap bitmap, Bitmap bitmap2, boolean z) {
        this.mShowScrollablePreview = z == this.mOrientationPortrait;
        this.mScrollingScrim.setImageBitmap(bitmap2);
        this.mScrollingScrim.setVisibility(0);
        if (this.mShowScrollablePreview) {
            Rect scrollableAreaOnScreen = scrollableAreaOnScreen(scrollCaptureResponse);
            float width = this.mFixedSize / (this.mOrientationPortrait ? bitmap.getWidth() : bitmap.getHeight());
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mScrollablePreview.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).width = (int) (scrollableAreaOnScreen.width() * width);
            ((ViewGroup.MarginLayoutParams) layoutParams).height = (int) (scrollableAreaOnScreen.height() * width);
            Matrix matrix = new Matrix();
            matrix.setScale(width, width);
            matrix.postTranslate((-scrollableAreaOnScreen.left) * width, (-scrollableAreaOnScreen.top) * width);
            this.mScrollablePreview.setTranslationX((this.mDirectionLTR ? scrollableAreaOnScreen.left : scrollableAreaOnScreen.right - getWidth()) * width);
            this.mScrollablePreview.setTranslationY(width * scrollableAreaOnScreen.top);
            this.mScrollablePreview.setImageMatrix(matrix);
            this.mScrollablePreview.setImageBitmap(bitmap);
            this.mScrollablePreview.setVisibility(0);
        }
        this.mDismissButton.setVisibility(8);
        this.mActionsContainer.setVisibility(8);
        this.mActionsContainerBackground.setVisibility(4);
        this.mScreenshotPreviewBorder.setVisibility(4);
        this.mScreenshotPreview.setVisibility(4);
        this.mScrollingScrim.setImageTintBlendMode(BlendMode.SRC_ATOP);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 0.3f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda21
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView.$r8$lambda$vrt85z26vUo3rxMOI0UfkO7qwdQ(ScreenshotView.this, valueAnimator);
            }
        });
        ofFloat.setDuration(200L);
        ofFloat.start();
    }

    public final void prepareSharedTransition() {
        this.mPendingSharedTransition = true;
        createScreenshotFadeDismissAnimation().start();
    }

    public void reset() {
        this.mScreenshotStatic.cancelDismissal();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        this.mScreenshotPreview.setImageDrawable(null);
        this.mScreenshotPreview.setVisibility(4);
        this.mScreenshotPreview.setAlpha(1.0f);
        this.mScreenshotPreviewBorder.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mScreenshotBadge.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mScreenshotBadge.setVisibility(8);
        this.mScreenshotBadge.setImageDrawable(null);
        this.mPendingSharedTransition = false;
        this.mActionsContainerBackground.setVisibility(8);
        this.mActionsContainer.setVisibility(8);
        this.mDismissButton.setVisibility(8);
        this.mScrollingScrim.setVisibility(8);
        this.mScrollablePreview.setVisibility(8);
        this.mScreenshotStatic.setTranslationX(ActionBarShadowController.ELEVATION_LOW);
        this.mScreenshotPreview.setContentDescription(((FrameLayout) this).mContext.getResources().getString(R$string.screenshot_preview_description));
        this.mScreenshotPreview.setOnClickListener(null);
        this.mShareChip.setOnClickListener(null);
        this.mScrollingScrim.setVisibility(8);
        this.mEditChip.setOnClickListener(null);
        this.mShareChip.setIsPending(false);
        this.mEditChip.setIsPending(false);
        this.mPendingInteraction = null;
        Iterator<OverlayActionChip> it = this.mSmartChips.iterator();
        while (it.hasNext()) {
            this.mActionsView.removeView(it.next());
        }
        this.mSmartChips.clear();
        this.mQuickShareChip = null;
        setAlpha(1.0f);
        this.mScreenshotStatic.setAlpha(1.0f);
    }

    public void restoreNonScrollingUi() {
        this.mScrollChip.setVisibility(8);
        this.mScrollablePreview.setVisibility(8);
        this.mScrollingScrim.setVisibility(8);
        if (this.mAccessibilityManager.isEnabled()) {
            this.mDismissButton.setVisibility(0);
        }
        this.mActionsContainer.setVisibility(0);
        this.mActionsContainerBackground.setVisibility(0);
        this.mScreenshotPreviewBorder.setVisibility(0);
        this.mScreenshotPreview.setVisibility(0);
        this.mCallbacks.onUserInteraction();
    }

    public final Rect scrollableAreaOnScreen(ScrollCaptureResponse scrollCaptureResponse) {
        Rect rect = new Rect(scrollCaptureResponse.getBoundsInWindow());
        Rect windowBounds = scrollCaptureResponse.getWindowBounds();
        rect.offset(windowBounds.left, windowBounds.top);
        DisplayMetrics displayMetrics = this.mDisplayMetrics;
        rect.intersect(new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels));
        return rect;
    }

    public void setChipIntents(final ScreenshotController.SavedImageData savedImageData) {
        this.mShareChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenshotView.this.lambda$setChipIntents$11(savedImageData, view);
            }
        });
        this.mEditChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenshotView.this.lambda$setChipIntents$12(savedImageData, view);
            }
        });
        this.mScreenshotPreview.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenshotView.this.lambda$setChipIntents$13(savedImageData, view);
            }
        });
        OverlayActionChip overlayActionChip = this.mQuickShareChip;
        if (overlayActionChip != null) {
            Notification.Action action = savedImageData.quickShareAction;
            if (action != null) {
                overlayActionChip.setPendingIntent(action.actionIntent, new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenshotView.$r8$lambda$ICx6SCMp0w3iU4gbcMtKcvg0qXs(ScreenshotView.this);
                    }
                });
            } else {
                Log.wtf(TAG, "Showed quick share chip, but quick share intent was null");
                if (this.mPendingInteraction == PendingInteraction.QUICK_SHARE) {
                    this.mPendingInteraction = null;
                }
                this.mQuickShareChip.setVisibility(8);
            }
        }
        PendingInteraction pendingInteraction = this.mPendingInteraction;
        if (pendingInteraction == null) {
            LayoutInflater from = LayoutInflater.from(((FrameLayout) this).mContext);
            for (Notification.Action action2 : savedImageData.smartActions) {
                OverlayActionChip overlayActionChip2 = (OverlayActionChip) from.inflate(R$layout.overlay_action_chip, (ViewGroup) this.mActionsView, false);
                overlayActionChip2.setText(action2.title);
                overlayActionChip2.setIcon(action2.getIcon(), false);
                overlayActionChip2.setPendingIntent(action2.actionIntent, new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenshotView.$r8$lambda$W0hL6gkY55ONp8vZrNUtcRYkNM0(ScreenshotView.this);
                    }
                });
                overlayActionChip2.setAlpha(1.0f);
                LinearLayout linearLayout = this.mActionsView;
                linearLayout.addView(overlayActionChip2, linearLayout.getChildCount() - 1);
                this.mSmartChips.add(overlayActionChip2);
            }
            return;
        }
        int i = AnonymousClass10.$SwitchMap$com$android$systemui$screenshot$ScreenshotView$PendingInteraction[pendingInteraction.ordinal()];
        if (i == 1) {
            this.mScreenshotPreview.callOnClick();
        } else if (i == 2) {
            this.mShareChip.callOnClick();
        } else if (i == 3) {
            this.mEditChip.callOnClick();
        } else if (i == 4) {
            this.mQuickShareChip.callOnClick();
        }
    }

    public void setDefaultTimeoutMillis(long j) {
        this.mDefaultTimeoutOfTimeoutHandler = j;
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    public void setScreenshot(Bitmap bitmap, Insets insets) {
        this.mScreenshotPreview.setImageDrawable(createScreenDrawable(this.mResources, bitmap, insets));
    }

    public void showScrollChip(final String str, final Runnable runnable) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_IMPRESSION, 0, str);
        this.mScrollChip.setVisibility(0);
        this.mScrollChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenshotView.$r8$lambda$7xFc3dTZc8Lxxqd43RaCfoifDVg(ScreenshotView.this, str, runnable, view);
            }
        });
    }

    public void showWorkProfileMessage(String str) {
        this.mMessageContent.setText(((FrameLayout) this).mContext.getString(R$string.screenshot_work_profile_notification, str));
        this.mMessageContainer.setVisibility(0);
        this.mMessageContainer.findViewById(R$id.message_dismiss_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenshotView.$r8$lambda$iYYy_HyNZ73sjEGyJeWeeiWC_Ws(ScreenshotView.this, view);
            }
        });
    }

    public final void startInputListening() {
        stopInputListening();
        InputMonitorCompat inputMonitorCompat = new InputMonitorCompat("Screenshot", 0);
        this.mInputMonitor = inputMonitorCompat;
        this.mInputEventReceiver = inputMonitorCompat.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat.InputEventListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda0
            public final void onInputEvent(InputEvent inputEvent) {
                ScreenshotView.$r8$lambda$3BvmuZAvWHzY_61MPNmWWjAT1nE(ScreenshotView.this, inputEvent);
            }
        });
    }

    public void startLongScreenshotTransition(final Rect rect, final Runnable runnable, ScrollCaptureController.LongScreenshot longScreenshot) {
        this.mPendingSharedTransition = true;
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView.m4302$r8$lambda$l2XZFFWeuZkuOFYx_G71BAfGDc(ScreenshotView.this, valueAnimator);
            }
        });
        if (this.mShowScrollablePreview) {
            this.mScrollablePreview.setImageBitmap(longScreenshot.toBitmap());
            final float x = this.mScrollablePreview.getX();
            final float y = this.mScrollablePreview.getY();
            int[] locationOnScreen = this.mScrollablePreview.getLocationOnScreen();
            rect.offset(((int) x) - locationOnScreen[0], ((int) y) - locationOnScreen[1]);
            this.mScrollablePreview.setPivotX(ActionBarShadowController.ELEVATION_LOW);
            this.mScrollablePreview.setPivotY(ActionBarShadowController.ELEVATION_LOW);
            this.mScrollablePreview.setAlpha(1.0f);
            float width = this.mScrollablePreview.getWidth() / longScreenshot.getWidth();
            Matrix matrix = new Matrix();
            matrix.setScale(width, width);
            matrix.postTranslate(longScreenshot.getLeft() * width, longScreenshot.getTop() * width);
            this.mScrollablePreview.setImageMatrix(matrix);
            final float width2 = rect.width() / this.mScrollablePreview.getWidth();
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ScreenshotView.$r8$lambda$fStr70ti2hcVeoJsckVBThCDxsc(ScreenshotView.this, width2, x, rect, y, valueAnimator);
                }
            });
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
            ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ScreenshotView.$r8$lambda$m0iKOORzK9aODjaXg8q9v2CSzeM(ScreenshotView.this, valueAnimator);
                }
            });
            animatorSet.play(ofFloat2).with(ofFloat).before(ofFloat3);
            ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotView.7
                {
                    ScreenshotView.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    runnable.run();
                }
            });
        } else {
            animatorSet.play(ofFloat);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotView.8
                {
                    ScreenshotView.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    runnable.run();
                }
            });
        }
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotView.9
            {
                ScreenshotView.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ScreenshotView.this.mCallbacks.onDismiss();
            }
        });
        animatorSet.start();
    }

    public final void startSharedTransition(ScreenshotController.SavedImageData.ActionTransition actionTransition) {
        try {
            this.mPendingSharedTransition = true;
            actionTransition.action.actionIntent.send();
            createScreenshotFadeDismissAnimation().start();
        } catch (PendingIntent.CanceledException e) {
            this.mPendingSharedTransition = false;
            Runnable runnable = actionTransition.onCancelRunnable;
            if (runnable != null) {
                runnable.run();
            }
            Log.e(TAG, "Intent cancelled", e);
        }
    }

    public void stopInputListening() {
        InputMonitorCompat inputMonitorCompat = this.mInputMonitor;
        if (inputMonitorCompat != null) {
            inputMonitorCompat.dispose();
            this.mInputMonitor = null;
        }
        InputChannelCompat.InputEventReceiver inputEventReceiver = this.mInputEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
    }

    public void updateInsets(WindowInsets windowInsets) {
        boolean z = true;
        if (((FrameLayout) this).mContext.getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        this.mOrientationPortrait = z;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mScreenshotStatic.getLayoutParams();
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Insets insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        if (displayCutout == null) {
            layoutParams.setMargins(0, 0, 0, insets.bottom);
        } else {
            Insets waterfallInsets = displayCutout.getWaterfallInsets();
            if (this.mOrientationPortrait) {
                layoutParams.setMargins(waterfallInsets.left, Math.max(displayCutout.getSafeInsetTop(), waterfallInsets.top), waterfallInsets.right, Math.max(displayCutout.getSafeInsetBottom(), Math.max(insets.bottom, waterfallInsets.bottom)));
            } else {
                layoutParams.setMargins(Math.max(displayCutout.getSafeInsetLeft(), waterfallInsets.left), waterfallInsets.top, Math.max(displayCutout.getSafeInsetRight(), waterfallInsets.right), Math.max(insets.bottom, waterfallInsets.bottom));
            }
        }
        this.mScreenshotStatic.setLayoutParams(layoutParams);
        this.mScreenshotStatic.requestLayout();
    }

    public void updateOrientation(WindowInsets windowInsets) {
        boolean z = true;
        if (((FrameLayout) this).mContext.getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        this.mOrientationPortrait = z;
        updateInsets(windowInsets);
        ViewGroup.LayoutParams layoutParams = this.mScreenshotPreview.getLayoutParams();
        if (this.mOrientationPortrait) {
            layoutParams.width = (int) this.mFixedSize;
            layoutParams.height = -2;
            this.mScreenshotPreview.setScaleType(ImageView.ScaleType.FIT_START);
        } else {
            layoutParams.width = -2;
            layoutParams.height = (int) this.mFixedSize;
            this.mScreenshotPreview.setScaleType(ImageView.ScaleType.FIT_END);
        }
        this.mScreenshotPreview.setLayoutParams(layoutParams);
    }
}