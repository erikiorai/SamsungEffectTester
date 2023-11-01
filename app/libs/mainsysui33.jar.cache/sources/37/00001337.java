package com.android.systemui.clipboardoverlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.RemoteAction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.clipboardoverlay.ClipboardOverlayView;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.FloatingWindowUtil;
import com.android.systemui.screenshot.OverlayActionChip;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayView.class */
public class ClipboardOverlayView extends DraggableConstraintLayout {
    public final AccessibilityManager mAccessibilityManager;
    public final ArrayList<OverlayActionChip> mActionChips;
    public LinearLayout mActionContainer;
    public View mActionContainerBackground;
    public View mClipboardPreview;
    public View mDismissButton;
    public final DisplayMetrics mDisplayMetrics;
    public OverlayActionChip mEditChip;
    public TextView mHiddenPreview;
    public ImageView mImagePreview;
    public View mPreviewBorder;
    public OverlayActionChip mRemoteCopyChip;
    public OverlayActionChip mShareChip;
    public TextView mTextPreview;

    /* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayView$ClipboardOverlayCallbacks.class */
    public interface ClipboardOverlayCallbacks extends DraggableConstraintLayout.SwipeDismissCallbacks {
        void onDismissButtonTapped();

        void onEditButtonTapped();

        void onPreviewTapped();

        void onRemoteCopyButtonTapped();

        void onShareButtonTapped();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda5.onPreDraw():boolean] */
    public static /* synthetic */ boolean $r8$lambda$5uJkzNBd53mN5Iq85K4aELf6Bfs(ClipboardOverlayView clipboardOverlayView) {
        return clipboardOverlayView.lambda$onFinishInflate$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda7.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$C4VXUktLAGZdQYvBRwKKy6Yp6nA(ClipboardOverlayView clipboardOverlayView, ValueAnimator valueAnimator) {
        clipboardOverlayView.lambda$getEnterAnimation$7(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda9.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$JWPhRs-bI2xnA2nxyZwwPBfzu3g */
    public static /* synthetic */ void m1766$r8$lambda$JWPhRsbI2xnA2nxyZwwPBfzu3g(ClipboardOverlayView clipboardOverlayView, ValueAnimator valueAnimator) {
        clipboardOverlayView.lambda$getEnterAnimation$9(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda4.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$O0DpigbhN_S-OciZC9JRNcCKRYE */
    public static /* synthetic */ void m1767$r8$lambda$O0DpigbhN_SOciZC9JRNcCKRYE(ClipboardOverlayCallbacks clipboardOverlayCallbacks, View view) {
        clipboardOverlayCallbacks.onPreviewTapped();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda12.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$OEF82Bv_SwXJSrCziFC5fxUMxiU(ClipboardOverlayView clipboardOverlayView, ValueAnimator valueAnimator) {
        clipboardOverlayView.lambda$getExitAnimation$12(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda8.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$Pgm-2kGsmUHI23wAOcO0NS6k6u4 */
    public static /* synthetic */ void m1768$r8$lambda$Pgm2kGsmUHI23wAOcO0NS6k6u4(ClipboardOverlayView clipboardOverlayView, ValueAnimator valueAnimator) {
        clipboardOverlayView.lambda$getEnterAnimation$8(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$Qp6CeoHcOOw_lNkiNJO0sllJf7c(ClipboardOverlayCallbacks clipboardOverlayCallbacks, View view) {
        clipboardOverlayCallbacks.onShareButtonTapped();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda6.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static /* synthetic */ void $r8$lambda$RZFxMwKvRXZ2ZPw16IRmMd6gGwI(CharSequence charSequence, TextView textView, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        lambda$showTextPreview$6(charSequence, textView, view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda3.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$S7e_9jPkSFsfBIGo3wPGO4JMseQ(ClipboardOverlayCallbacks clipboardOverlayCallbacks, View view) {
        clipboardOverlayCallbacks.onRemoteCopyButtonTapped();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda11.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$Zz8uMmPnTWrFStGqlz6S_Mjopwo(ClipboardOverlayView clipboardOverlayView, ValueAnimator valueAnimator) {
        clipboardOverlayView.lambda$getExitAnimation$11(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$nKZR3htDkuNSCwkagJiv6JrP-Jw */
    public static /* synthetic */ void m1769$r8$lambda$nKZR3htDkuNSCwkagJiv6JrPJw(ClipboardOverlayCallbacks clipboardOverlayCallbacks, View view) {
        clipboardOverlayCallbacks.onEditButtonTapped();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda10.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$qVcCR6i1HuiF4SW-5d9CgVTuA7Y */
    public static /* synthetic */ void m1770$r8$lambda$qVcCR6i1HuiF4SW5d9CgVTuA7Y(ClipboardOverlayView clipboardOverlayView, ValueAnimator valueAnimator) {
        clipboardOverlayView.lambda$getExitAnimation$10(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$qqIPL0WsrOtinMvFUrNWcxjibSM(ClipboardOverlayCallbacks clipboardOverlayCallbacks, View view) {
        clipboardOverlayCallbacks.onDismissButtonTapped();
    }

    public ClipboardOverlayView(Context context) {
        this(context, null);
    }

    public ClipboardOverlayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClipboardOverlayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mActionChips = new ArrayList<>();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        ((ViewGroup) this).mContext.getDisplay().getRealMetrics(displayMetrics);
        this.mAccessibilityManager = AccessibilityManager.getInstance(((ViewGroup) this).mContext);
    }

    public static Rect computeMargins(WindowInsets windowInsets, int i) {
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Insets insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        Insets insets2 = windowInsets.getInsets(WindowInsets.Type.ime());
        if (displayCutout == null) {
            return new Rect(0, 0, 0, Math.max(insets2.bottom, insets.bottom));
        }
        Insets waterfallInsets = displayCutout.getWaterfallInsets();
        return i == 1 ? new Rect(waterfallInsets.left, Math.max(displayCutout.getSafeInsetTop(), waterfallInsets.top), waterfallInsets.right, Math.max(insets2.bottom, Math.max(displayCutout.getSafeInsetBottom(), Math.max(insets.bottom, waterfallInsets.bottom)))) : new Rect(waterfallInsets.left, waterfallInsets.top, waterfallInsets.right, Math.max(insets2.bottom, Math.max(insets.bottom, waterfallInsets.bottom)));
    }

    public static boolean fitsInView(CharSequence charSequence, TextView textView, Paint paint, float f) {
        paint.setTextSize(f);
        return paint.measureText(charSequence.toString()) < ((float) ((textView.getWidth() - textView.getPaddingLeft()) - textView.getPaddingRight()));
    }

    public static boolean isOneWord(CharSequence charSequence) {
        boolean z = true;
        if (charSequence.toString().split("\\s+", 2).length != 1) {
            z = false;
        }
        return z;
    }

    public /* synthetic */ void lambda$getEnterAnimation$7(ValueAnimator valueAnimator) {
        setAlpha(valueAnimator.getAnimatedFraction());
    }

    public /* synthetic */ void lambda$getEnterAnimation$8(ValueAnimator valueAnimator) {
        float lerp = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
        this.mClipboardPreview.setScaleX(lerp);
        this.mClipboardPreview.setScaleY(lerp);
        this.mPreviewBorder.setScaleX(lerp);
        this.mPreviewBorder.setScaleY(lerp);
        float width = (this.mClipboardPreview.getWidth() / 2.0f) + this.mClipboardPreview.getX();
        View view = this.mActionContainerBackground;
        view.setPivotX(width - view.getX());
        LinearLayout linearLayout = this.mActionContainer;
        linearLayout.setPivotX(width - ((View) linearLayout.getParent()).getX());
        float lerp2 = MathUtils.lerp(0.7f, 1.0f, valueAnimator.getAnimatedFraction());
        float lerp3 = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
        this.mActionContainer.setScaleX(lerp2);
        this.mActionContainer.setScaleY(lerp3);
        this.mActionContainerBackground.setScaleX(lerp2);
        this.mActionContainerBackground.setScaleY(lerp3);
    }

    public /* synthetic */ void lambda$getEnterAnimation$9(ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        this.mClipboardPreview.setAlpha(animatedFraction);
        this.mPreviewBorder.setAlpha(animatedFraction);
        this.mDismissButton.setAlpha(animatedFraction);
        this.mActionContainer.setAlpha(animatedFraction);
    }

    public /* synthetic */ void lambda$getExitAnimation$10(ValueAnimator valueAnimator) {
        setAlpha(1.0f - valueAnimator.getAnimatedFraction());
    }

    public /* synthetic */ void lambda$getExitAnimation$11(ValueAnimator valueAnimator) {
        float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
        this.mClipboardPreview.setScaleX(lerp);
        this.mClipboardPreview.setScaleY(lerp);
        this.mPreviewBorder.setScaleX(lerp);
        this.mPreviewBorder.setScaleY(lerp);
        float width = (this.mClipboardPreview.getWidth() / 2.0f) + this.mClipboardPreview.getX();
        View view = this.mActionContainerBackground;
        view.setPivotX(width - view.getX());
        LinearLayout linearLayout = this.mActionContainer;
        linearLayout.setPivotX(width - ((View) linearLayout.getParent()).getX());
        float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
        float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
        this.mActionContainer.setScaleX(lerp2);
        this.mActionContainer.setScaleY(lerp3);
        this.mActionContainerBackground.setScaleX(lerp2);
        this.mActionContainerBackground.setScaleY(lerp3);
    }

    public /* synthetic */ void lambda$getExitAnimation$12(ValueAnimator valueAnimator) {
        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
        this.mClipboardPreview.setAlpha(animatedFraction);
        this.mPreviewBorder.setAlpha(animatedFraction);
        this.mDismissButton.setAlpha(animatedFraction);
        this.mActionContainer.setAlpha(animatedFraction);
    }

    public /* synthetic */ boolean lambda$onFinishInflate$0() {
        int height = this.mTextPreview.getHeight();
        int paddingTop = this.mTextPreview.getPaddingTop();
        int paddingBottom = this.mTextPreview.getPaddingBottom();
        TextView textView = this.mTextPreview;
        textView.setMaxLines((height - (paddingTop + paddingBottom)) / textView.getLineHeight());
        return true;
    }

    public static /* synthetic */ void lambda$showTextPreview$6(CharSequence charSequence, TextView textView, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i3 - i != i7 - i5) {
            updateTextSize(charSequence, textView);
        }
    }

    public static void updateTextSize(CharSequence charSequence, TextView textView) {
        Paint paint = new Paint(textView.getPaint());
        Resources resources = textView.getResources();
        float dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.clipboard_overlay_min_font);
        float dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.clipboard_overlay_max_font);
        if (!isOneWord(charSequence) || !fitsInView(charSequence, textView, paint, dimensionPixelSize)) {
            textView.setAutoSizeTextTypeUniformWithConfiguration((int) dimensionPixelSize, (int) dimensionPixelSize2, 4, 0);
            textView.setGravity(8388627);
            return;
        }
        while (true) {
            float f = 4.0f + dimensionPixelSize;
            if (f >= dimensionPixelSize2 || !fitsInView(charSequence, textView, paint, f)) {
                break;
            }
            dimensionPixelSize = f;
        }
        textView.setAutoSizeTextTypeWithDefaults(0);
        textView.setGravity(17);
        textView.setTextSize(0, (int) dimensionPixelSize);
    }

    public final OverlayActionChip constructActionChip(RemoteAction remoteAction, Runnable runnable) {
        OverlayActionChip overlayActionChip = (OverlayActionChip) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R$layout.overlay_action_chip, (ViewGroup) this.mActionContainer, false);
        overlayActionChip.setText(remoteAction.getTitle());
        overlayActionChip.setContentDescription(remoteAction.getTitle());
        overlayActionChip.setIcon(remoteAction.getIcon(), false);
        overlayActionChip.setPendingIntent(remoteAction.getActionIntent(), runnable);
        overlayActionChip.setAlpha(1.0f);
        return overlayActionChip;
    }

    public Animator getEnterAnimation() {
        if (this.mAccessibilityManager.isEnabled()) {
            this.mDismissButton.setVisibility(0);
        }
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        PathInterpolator pathInterpolator = new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setInterpolator(linearInterpolator);
        ofFloat.setDuration(66L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayView.$r8$lambda$C4VXUktLAGZdQYvBRwKKy6Yp6nA(ClipboardOverlayView.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat2.setInterpolator(pathInterpolator);
        ofFloat2.setDuration(333L);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayView.m1768$r8$lambda$Pgm2kGsmUHI23wAOcO0NS6k6u4(ClipboardOverlayView.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat3.setInterpolator(linearInterpolator);
        ofFloat3.setDuration(283L);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayView.m1766$r8$lambda$JWPhRsbI2xnA2nxyZwwPBfzu3g(ClipboardOverlayView.this, valueAnimator);
            }
        });
        this.mActionContainer.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mPreviewBorder.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mClipboardPreview.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        animatorSet.play(ofFloat).with(ofFloat2);
        animatorSet.play(ofFloat3).after(50L).after(ofFloat);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView.1
            {
                ClipboardOverlayView.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ClipboardOverlayView.this.setAlpha(1.0f);
            }
        });
        return animatorSet;
    }

    public Animator getExitAnimation() {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        PathInterpolator pathInterpolator = new PathInterpolator(0.3f, ActionBarShadowController.ELEVATION_LOW, 1.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setInterpolator(linearInterpolator);
        ofFloat.setDuration(100L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda10
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayView.m1770$r8$lambda$qVcCR6i1HuiF4SW5d9CgVTuA7Y(ClipboardOverlayView.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat2.setInterpolator(pathInterpolator);
        ofFloat2.setDuration(250L);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda11
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayView.$r8$lambda$Zz8uMmPnTWrFStGqlz6S_Mjopwo(ClipboardOverlayView.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat3.setInterpolator(linearInterpolator);
        ofFloat3.setDuration(166L);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda12
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayView.$r8$lambda$OEF82Bv_SwXJSrCziFC5fxUMxiU(ClipboardOverlayView.this, valueAnimator);
            }
        });
        animatorSet.play(ofFloat3).with(ofFloat2);
        animatorSet.play(ofFloat).after(150L).after(ofFloat3);
        return animatorSet;
    }

    public boolean isInTouchRegion(int i, int i2) {
        Region region = new Region();
        Rect rect = new Rect();
        this.mPreviewBorder.getBoundsOnScreen(rect);
        rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
        region.op(rect, Region.Op.UNION);
        this.mActionContainerBackground.getBoundsOnScreen(rect);
        rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
        region.op(rect, Region.Op.UNION);
        this.mDismissButton.getBoundsOnScreen(rect);
        region.op(rect, Region.Op.UNION);
        return region.contains(i, i2);
    }

    @Override // com.android.systemui.screenshot.DraggableConstraintLayout, android.view.View
    public void onFinishInflate() {
        View findViewById = findViewById(R$id.actions_container_background);
        Objects.requireNonNull(findViewById);
        this.mActionContainerBackground = findViewById;
        LinearLayout linearLayout = (LinearLayout) findViewById(R$id.actions);
        Objects.requireNonNull(linearLayout);
        this.mActionContainer = linearLayout;
        View findViewById2 = findViewById(R$id.clipboard_preview);
        Objects.requireNonNull(findViewById2);
        this.mClipboardPreview = findViewById2;
        ImageView imageView = (ImageView) findViewById(R$id.image_preview);
        Objects.requireNonNull(imageView);
        this.mImagePreview = imageView;
        TextView textView = (TextView) findViewById(R$id.text_preview);
        Objects.requireNonNull(textView);
        this.mTextPreview = textView;
        TextView textView2 = (TextView) findViewById(R$id.hidden_preview);
        Objects.requireNonNull(textView2);
        this.mHiddenPreview = textView2;
        View findViewById3 = findViewById(R$id.preview_border);
        Objects.requireNonNull(findViewById3);
        this.mPreviewBorder = findViewById3;
        OverlayActionChip overlayActionChip = (OverlayActionChip) findViewById(R$id.edit_chip);
        Objects.requireNonNull(overlayActionChip);
        this.mEditChip = overlayActionChip;
        OverlayActionChip overlayActionChip2 = (OverlayActionChip) findViewById(R$id.share_chip);
        Objects.requireNonNull(overlayActionChip2);
        this.mShareChip = overlayActionChip2;
        OverlayActionChip overlayActionChip3 = (OverlayActionChip) findViewById(R$id.remote_copy_chip);
        Objects.requireNonNull(overlayActionChip3);
        this.mRemoteCopyChip = overlayActionChip3;
        View findViewById4 = findViewById(R$id.dismiss_button);
        Objects.requireNonNull(findViewById4);
        this.mDismissButton = findViewById4;
        this.mEditChip.setAlpha(1.0f);
        this.mShareChip.setAlpha(1.0f);
        this.mRemoteCopyChip.setAlpha(1.0f);
        this.mShareChip.setContentDescription(((ViewGroup) this).mContext.getString(17041530));
        this.mEditChip.setIcon(Icon.createWithResource(((ViewGroup) this).mContext, R$drawable.ic_screenshot_edit), true);
        this.mRemoteCopyChip.setIcon(Icon.createWithResource(((ViewGroup) this).mContext, R$drawable.ic_baseline_devices_24), true);
        this.mShareChip.setIcon(Icon.createWithResource(((ViewGroup) this).mContext, R$drawable.ic_screenshot_share), true);
        this.mRemoteCopyChip.setContentDescription(((ViewGroup) this).mContext.getString(R$string.clipboard_send_nearby_description));
        this.mTextPreview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda5
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                return ClipboardOverlayView.$r8$lambda$5uJkzNBd53mN5Iq85K4aELf6Bfs(ClipboardOverlayView.this);
            }
        });
        super.onFinishInflate();
    }

    public void reset() {
        setTranslationX(ActionBarShadowController.ELEVATION_LOW);
        setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mActionContainerBackground.setVisibility(8);
        this.mDismissButton.setVisibility(8);
        this.mShareChip.setVisibility(8);
        this.mEditChip.setVisibility(8);
        this.mRemoteCopyChip.setVisibility(8);
        setEditAccessibilityAction(false);
        resetActionChips();
    }

    public void resetActionChips() {
        Iterator<OverlayActionChip> it = this.mActionChips.iterator();
        while (it.hasNext()) {
            this.mActionContainer.removeView(it.next());
        }
        this.mActionChips.clear();
    }

    public void setActionChip(RemoteAction remoteAction, Runnable runnable) {
        this.mActionContainerBackground.setVisibility(0);
        OverlayActionChip constructActionChip = constructActionChip(remoteAction, runnable);
        this.mActionContainer.addView(constructActionChip);
        this.mActionChips.add(constructActionChip);
    }

    @Override // com.android.systemui.screenshot.DraggableConstraintLayout
    public void setCallbacks(DraggableConstraintLayout.SwipeDismissCallbacks swipeDismissCallbacks) {
        super.setCallbacks(swipeDismissCallbacks);
        final ClipboardOverlayCallbacks clipboardOverlayCallbacks = (ClipboardOverlayCallbacks) swipeDismissCallbacks;
        this.mEditChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayView.m1769$r8$lambda$nKZR3htDkuNSCwkagJiv6JrPJw(ClipboardOverlayView.ClipboardOverlayCallbacks.this, view);
            }
        });
        this.mShareChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayView.$r8$lambda$Qp6CeoHcOOw_lNkiNJO0sllJf7c(ClipboardOverlayView.ClipboardOverlayCallbacks.this, view);
            }
        });
        this.mDismissButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayView.$r8$lambda$qqIPL0WsrOtinMvFUrNWcxjibSM(ClipboardOverlayView.ClipboardOverlayCallbacks.this, view);
            }
        });
        this.mRemoteCopyChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayView.$r8$lambda$S7e_9jPkSFsfBIGo3wPGO4JMseQ(ClipboardOverlayView.ClipboardOverlayCallbacks.this, view);
            }
        });
        this.mClipboardPreview.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayView.m1767$r8$lambda$O0DpigbhN_SOciZC9JRNcCKRYE(ClipboardOverlayView.ClipboardOverlayCallbacks.this, view);
            }
        });
    }

    public void setEditAccessibilityAction(boolean z) {
        if (z) {
            ViewCompat.replaceAccessibilityAction(this.mClipboardPreview, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, ((ViewGroup) this).mContext.getString(R$string.clipboard_edit), null);
        } else {
            ViewCompat.replaceAccessibilityAction(this.mClipboardPreview, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, null, null);
        }
    }

    public void setInsets(WindowInsets windowInsets, int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        Rect computeMargins = computeMargins(windowInsets, i);
        layoutParams.setMargins(computeMargins.left, computeMargins.top, computeMargins.right, computeMargins.bottom);
        setLayoutParams(layoutParams);
        requestLayout();
    }

    public void setRemoteCopyVisibility(boolean z) {
        if (!z) {
            this.mRemoteCopyChip.setVisibility(8);
            return;
        }
        this.mRemoteCopyChip.setVisibility(0);
        this.mActionContainerBackground.setVisibility(0);
    }

    public void showDefaultTextPreview() {
        showTextPreview(((ViewGroup) this).mContext.getString(R$string.clipboard_overlay_text_copied), false);
    }

    public void showEditChip(String str) {
        this.mEditChip.setVisibility(0);
        this.mActionContainerBackground.setVisibility(0);
        this.mEditChip.setContentDescription(str);
    }

    public void showImagePreview(Bitmap bitmap) {
        if (bitmap == null) {
            this.mHiddenPreview.setText(((ViewGroup) this).mContext.getString(R$string.clipboard_text_hidden));
            showSinglePreview(this.mHiddenPreview);
            return;
        }
        this.mImagePreview.setImageBitmap(bitmap);
        showSinglePreview(this.mImagePreview);
    }

    public void showShareChip() {
        this.mShareChip.setVisibility(0);
        this.mActionContainerBackground.setVisibility(0);
    }

    public final void showSinglePreview(View view) {
        this.mTextPreview.setVisibility(8);
        this.mImagePreview.setVisibility(8);
        this.mHiddenPreview.setVisibility(8);
        view.setVisibility(0);
    }

    public void showTextPreview(final CharSequence charSequence, boolean z) {
        TextView textView = z ? this.mHiddenPreview : this.mTextPreview;
        showSinglePreview(textView);
        textView.setText(charSequence.subSequence(0, Math.min(500, charSequence.length())));
        updateTextSize(charSequence, textView);
        final TextView textView2 = textView;
        textView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                ClipboardOverlayView.$r8$lambda$RZFxMwKvRXZ2ZPw16IRmMd6gGwI(charSequence, textView2, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        this.mEditChip.setVisibility(8);
    }
}