package com.android.systemui.accessibility.floatingmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AccessibilityFloatingMenuView.class */
public class AccessibilityFloatingMenuView extends FrameLayout implements RecyclerView.OnItemTouchListener {
    public final AccessibilityTargetAdapter mAdapter;
    public int mAlignment;
    @VisibleForTesting
    public final WindowManager.LayoutParams mCurrentLayoutParams;
    public int mDisplayHeight;
    public final Rect mDisplayInsetsRect;
    public int mDisplayWidth;
    public int mDownX;
    public int mDownY;
    @VisibleForTesting
    public final ValueAnimator mDragAnimator;
    public final ValueAnimator mFadeOutAnimator;
    public float mFadeOutValue;
    public int mIconHeight;
    public int mIconWidth;
    public final Rect mImeInsetsRect;
    public int mInset;
    public boolean mIsDownInEnlargedTouchArea;
    public boolean mIsDragging;
    public boolean mIsFadeEffectEnabled;
    public boolean mIsShowing;
    public final Configuration mLastConfiguration;
    public final RecyclerView mListView;
    public int mMargin;
    public Optional<OnDragEndListener> mOnDragEndListener;
    public int mPadding;
    public final Position mPosition;
    public float mRadius;
    public int mRadiusType;
    public int mRelativeToPointerDownX;
    public int mRelativeToPointerDownY;
    @VisibleForTesting
    public int mShapeType;
    public int mSizeType;
    public float mSquareScaledTouchSlop;
    public final List<AccessibilityTarget> mTargets;
    public int mTemporaryShapeType;
    public final Handler mUiHandler;
    public final WindowManager mWindowManager;

    /* renamed from: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AccessibilityFloatingMenuView$1.class */
    public class AnonymousClass1 extends AnimatorListenerAdapter {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$1$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
        public static /* synthetic */ void $r8$lambda$xNQ8zgOB4WDG41JvpdxV8UCp7ho(AnonymousClass1 anonymousClass1, OnDragEndListener onDragEndListener) {
            anonymousClass1.lambda$onAnimationEnd$0(onDragEndListener);
        }

        public AnonymousClass1() {
            AccessibilityFloatingMenuView.this = r4;
        }

        public /* synthetic */ void lambda$onAnimationEnd$0(OnDragEndListener onDragEndListener) {
            onDragEndListener.onDragEnd(AccessibilityFloatingMenuView.this.mPosition);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            AccessibilityFloatingMenuView.this.mPosition.update(AccessibilityFloatingMenuView.this.transformCurrentPercentageXToEdge(), AccessibilityFloatingMenuView.this.calculateCurrentPercentageY());
            AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
            accessibilityFloatingMenuView.mAlignment = accessibilityFloatingMenuView.transformToAlignment(accessibilityFloatingMenuView.mPosition.getPercentageX());
            AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = AccessibilityFloatingMenuView.this;
            accessibilityFloatingMenuView2.updateLocationWith(accessibilityFloatingMenuView2.mPosition);
            AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = AccessibilityFloatingMenuView.this;
            accessibilityFloatingMenuView3.updateInsetWith(accessibilityFloatingMenuView3.getResources().getConfiguration().uiMode, AccessibilityFloatingMenuView.this.mAlignment);
            AccessibilityFloatingMenuView accessibilityFloatingMenuView4 = AccessibilityFloatingMenuView.this;
            accessibilityFloatingMenuView4.mRadiusType = accessibilityFloatingMenuView4.mAlignment == 1 ? 0 : 2;
            AccessibilityFloatingMenuView accessibilityFloatingMenuView5 = AccessibilityFloatingMenuView.this;
            accessibilityFloatingMenuView5.updateRadiusWith(accessibilityFloatingMenuView5.mSizeType, AccessibilityFloatingMenuView.this.mRadiusType, AccessibilityFloatingMenuView.this.mTargets.size());
            AccessibilityFloatingMenuView.this.fadeOut();
            AccessibilityFloatingMenuView.this.mOnDragEndListener.ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AccessibilityFloatingMenuView.AnonymousClass1.$r8$lambda$xNQ8zgOB4WDG41JvpdxV8UCp7ho(AccessibilityFloatingMenuView.AnonymousClass1.this, (AccessibilityFloatingMenuView.OnDragEndListener) obj);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AccessibilityFloatingMenuView$OnDragEndListener.class */
    public interface OnDragEndListener {
        void onDragEnd(Position position);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$4g-6BrRp1igrtsygj13wtIg2d1M */
    public static /* synthetic */ void m1396$r8$lambda$4g6BrRp1igrtsygj13wtIg2d1M(AccessibilityFloatingMenuView accessibilityFloatingMenuView, int i, int i2, ValueAnimator valueAnimator) {
        accessibilityFloatingMenuView.lambda$snapToLocation$5(i, i2, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$9PMrfZKCe1NwpL_Ydg1je4jmS9Q(AccessibilityFloatingMenuView accessibilityFloatingMenuView, ValueAnimator valueAnimator) {
        accessibilityFloatingMenuView.lambda$new$0(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda4.onApplyWindowInsets(android.view.View, android.view.WindowInsets):android.view.WindowInsets] */
    /* renamed from: $r8$lambda$B43oUCI3YZtrOMe-UwYzkG0Xpwo */
    public static /* synthetic */ WindowInsets m1397$r8$lambda$B43oUCI3YZtrOMeUwYzkG0Xpwo(AccessibilityFloatingMenuView accessibilityFloatingMenuView, View view, WindowInsets windowInsets) {
        return accessibilityFloatingMenuView.lambda$show$1(view, windowInsets);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$LBlSuzMtvTxjYf98YMGNhDwsxfo(AccessibilityFloatingMenuView accessibilityFloatingMenuView, Rect rect) {
        accessibilityFloatingMenuView.lambda$setSystemGestureExclusion$6(rect);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$_D7W9fuJHsES3b4DpXGYn11d5KQ(AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        accessibilityFloatingMenuView.lambda$fadeOut$4();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda5.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$hlHMbmdr0GIrfeQ0H6KMI0Tdmno(AccessibilityFloatingMenuView accessibilityFloatingMenuView, View view, MotionEvent motionEvent) {
        return accessibilityFloatingMenuView.lambda$setShapeType$2(view, motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$vq2JT0LUovBbtPTOc3KKDRFrhmw(AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        accessibilityFloatingMenuView.lambda$fadeIn$3();
    }

    public AccessibilityFloatingMenuView(Context context, Position position) {
        this(context, position, new RecyclerView(context));
    }

    @VisibleForTesting
    public AccessibilityFloatingMenuView(Context context, Position position, RecyclerView recyclerView) {
        super(context);
        this.mIsDragging = false;
        this.mSizeType = 0;
        this.mShapeType = 0;
        this.mDisplayInsetsRect = new Rect();
        this.mImeInsetsRect = new Rect();
        this.mOnDragEndListener = Optional.empty();
        ArrayList arrayList = new ArrayList();
        this.mTargets = arrayList;
        this.mListView = recyclerView;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mLastConfiguration = new Configuration(getResources().getConfiguration());
        this.mAdapter = new AccessibilityTargetAdapter(arrayList);
        this.mUiHandler = createUiHandler();
        this.mPosition = position;
        int transformToAlignment = transformToAlignment(position.getPercentageX());
        this.mAlignment = transformToAlignment;
        this.mRadiusType = transformToAlignment == 1 ? 0 : 2;
        updateDimensions();
        this.mCurrentLayoutParams = createDefaultLayoutParams();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, this.mFadeOutValue);
        this.mFadeOutAnimator = ofFloat;
        ofFloat.setDuration(1000L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AccessibilityFloatingMenuView.$r8$lambda$9PMrfZKCe1NwpL_Ydg1je4jmS9Q(AccessibilityFloatingMenuView.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        this.mDragAnimator = ofFloat2;
        ofFloat2.setDuration(150L);
        ofFloat2.setInterpolator(new OvershootInterpolator());
        ofFloat2.addListener(new AnonymousClass1());
        initListView();
        updateStrokeWith(getResources().getConfiguration().uiMode, this.mAlignment);
    }

    public /* synthetic */ void lambda$fadeIn$3() {
        setAlpha(1.0f);
    }

    public /* synthetic */ void lambda$fadeOut$4() {
        this.mFadeOutAnimator.start();
    }

    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public /* synthetic */ boolean lambda$setShapeType$2(View view, MotionEvent motionEvent) {
        return onTouched(motionEvent);
    }

    public /* synthetic */ void lambda$setSystemGestureExclusion$6(Rect rect) {
        setSystemGestureExclusionRects(this.mIsShowing ? Collections.singletonList(rect) : Collections.emptyList());
    }

    public /* synthetic */ WindowInsets lambda$show$1(View view, WindowInsets windowInsets) {
        return onWindowInsetsApplied(windowInsets);
    }

    public final int calculateActualLayoutHeight() {
        return ((this.mPadding + this.mIconHeight) * this.mTargets.size()) + this.mPadding;
    }

    public final float calculateCurrentPercentageX() {
        return this.mCurrentLayoutParams.x / getMaxWindowX();
    }

    public final float calculateCurrentPercentageY() {
        return this.mCurrentLayoutParams.y / getMaxWindowY();
    }

    public final WindowManager.LayoutParams createDefaultLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2024, 520, -3);
        layoutParams.receiveInsetsIgnoringZOrder = true;
        layoutParams.privateFlags |= 2097152;
        layoutParams.windowAnimations = 16973827;
        layoutParams.gravity = 8388659;
        layoutParams.x = this.mAlignment == 1 ? getMaxWindowX() : getMinWindowX();
        layoutParams.y = Math.max(0, ((int) (this.mPosition.getPercentageY() * getMaxWindowY())) - getInterval());
        updateAccessibilityTitle(layoutParams);
        return layoutParams;
    }

    public final float[] createRadii(float f, int i) {
        return i == 0 ? new float[]{f, f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, f, f} : i == 2 ? new float[]{ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, f, f, f, f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW} : new float[]{f, f, f, f, f, f, f, f};
    }

    public final Handler createUiHandler() {
        Looper myLooper = Looper.myLooper();
        Objects.requireNonNull(myLooper, "looper must not be null");
        return new Handler(myLooper);
    }

    @VisibleForTesting
    public void fadeIn() {
        if (this.mIsFadeEffectEnabled) {
            this.mFadeOutAnimator.cancel();
            this.mUiHandler.removeCallbacksAndMessages(null);
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityFloatingMenuView.$r8$lambda$vq2JT0LUovBbtPTOc3KKDRFrhmw(AccessibilityFloatingMenuView.this);
                }
            });
        }
    }

    @VisibleForTesting
    public void fadeOut() {
        if (this.mIsFadeEffectEnabled) {
            this.mUiHandler.postDelayed(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityFloatingMenuView.$r8$lambda$_D7W9fuJHsES3b4DpXGYn11d5KQ(AccessibilityFloatingMenuView.this);
                }
            }, 3000L);
        }
    }

    @VisibleForTesting
    public Rect getAvailableBounds() {
        return new Rect(0, 0, this.mDisplayWidth - getWindowWidth(), this.mDisplayHeight - getWindowHeight());
    }

    public final Insets getDisplayInsets(WindowMetrics windowMetrics) {
        return windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
    }

    public final int getInterval() {
        int percentageY = (int) (this.mPosition.getPercentageY() * getMaxWindowY());
        int i = this.mDisplayHeight - this.mImeInsetsRect.bottom;
        int windowHeight = percentageY + getWindowHeight();
        return windowHeight > i ? windowHeight - i : 0;
    }

    public final int getLargeSizeResIdWith(int i) {
        return i > 1 ? R$dimen.accessibility_floating_menu_large_multiple_radius : R$dimen.accessibility_floating_menu_large_single_radius;
    }

    public final int getLayoutHeight() {
        return Math.min(getMaxLayoutHeight(), calculateActualLayoutHeight());
    }

    public final int getLayoutWidth() {
        return (this.mPadding * 2) + this.mIconWidth;
    }

    public final int getMarginStartEndWith(Configuration configuration) {
        return (configuration == null || configuration.orientation != 1) ? 0 : this.mMargin;
    }

    public final int getMaxLayoutHeight() {
        return this.mDisplayHeight - (this.mMargin * 2);
    }

    public final int getMaxWindowX() {
        return (this.mDisplayWidth - getMarginStartEndWith(this.mLastConfiguration)) - getLayoutWidth();
    }

    public final int getMaxWindowY() {
        return this.mDisplayHeight - getWindowHeight();
    }

    public final GradientDrawable getMenuGradientDrawable() {
        return (GradientDrawable) getMenuLayerDrawable().getDrawable(0);
    }

    public final InstantInsetLayerDrawable getMenuLayerDrawable() {
        return (InstantInsetLayerDrawable) this.mListView.getBackground();
    }

    public final int getMinWindowX() {
        return -getMarginStartEndWith(this.mLastConfiguration);
    }

    public final int getRadiusResId(int i, int i2) {
        return i == 0 ? getSmallSizeResIdWith(i2) : getLargeSizeResIdWith(i2);
    }

    public final int getSmallSizeResIdWith(int i) {
        return i > 1 ? R$dimen.accessibility_floating_menu_small_multiple_radius : R$dimen.accessibility_floating_menu_small_single_radius;
    }

    public final int getWindowHeight() {
        return Math.min(this.mDisplayHeight, (this.mMargin * 2) + getLayoutHeight());
    }

    public Rect getWindowLocationOnScreen() {
        WindowManager.LayoutParams layoutParams = this.mCurrentLayoutParams;
        int i = layoutParams.x;
        int i2 = layoutParams.y;
        return new Rect(i, i2, getWindowWidth() + i, getWindowHeight() + i2);
    }

    public final int getWindowWidth() {
        return (getMarginStartEndWith(this.mLastConfiguration) * 2) + getLayoutWidth();
    }

    @VisibleForTesting
    public boolean hasExceededMaxLayoutHeight() {
        return calculateActualLayoutHeight() > getMaxLayoutHeight();
    }

    public final boolean hasExceededTouchSlop(int i, int i2, int i3, int i4) {
        return MathUtils.sq((float) (i3 - i)) + MathUtils.sq((float) (i4 - i2)) > this.mSquareScaledTouchSlop;
    }

    public void hide() {
        if (isShowing()) {
            this.mIsShowing = false;
            this.mDragAnimator.cancel();
            this.mWindowManager.removeView(this);
            setOnApplyWindowInsetsListener(null);
            setSystemGestureExclusion();
        }
    }

    public final void initListView() {
        Drawable drawable = getContext().getDrawable(R$drawable.accessibility_floating_menu_background);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.mListView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        this.mListView.setBackground(new InstantInsetLayerDrawable(new Drawable[]{drawable}));
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setLayoutManager(linearLayoutManager);
        this.mListView.addOnItemTouchListener(this);
        this.mListView.animate().setInterpolator(new OvershootInterpolator());
        this.mListView.setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this.mListView) { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.2
            {
                AccessibilityFloatingMenuView.this = this;
            }

            @Override // androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate
            public AccessibilityDelegateCompat getItemDelegate() {
                return new ItemDelegateCompat(this, AccessibilityFloatingMenuView.this);
            }
        });
        updateListViewWith(this.mLastConfiguration);
        addView(this.mListView);
    }

    public final boolean isImeVisible(Rect rect) {
        return (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x000e, code lost:
        if (r5 <= r6) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isMovingTowardsScreenEdge(int i, int i2, int i3) {
        boolean z;
        if (i == 1) {
            z = true;
        }
        z = i == 0 && i3 > i2;
        return z;
    }

    public boolean isOvalShape() {
        return this.mShapeType == 0;
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mLastConfiguration.setTo(configuration);
        if ((configuration.diff(this.mLastConfiguration) & 4) != 0) {
            updateAccessibilityTitle(this.mCurrentLayoutParams);
        }
        updateDimensions();
        updateListViewWith(configuration);
        updateItemViewWith(this.mSizeType);
        updateColor();
        updateStrokeWith(configuration.uiMode, this.mAlignment);
        updateLocationWith(this.mPosition);
        updateRadiusWith(this.mSizeType, this.mRadiusType, this.mTargets.size());
        updateScrollModeWith(hasExceededMaxLayoutHeight());
        setSystemGestureExclusion();
    }

    /* renamed from: onDragAnimationUpdate */
    public final void lambda$snapToLocation$5(ValueAnimator valueAnimator, int i, int i2) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float f = 1.0f - floatValue;
        WindowManager.LayoutParams layoutParams = this.mCurrentLayoutParams;
        layoutParams.x = (int) ((layoutParams.x * f) + (i * floatValue));
        layoutParams.y = (int) ((f * layoutParams.y) + (floatValue * i2));
        this.mWindowManager.updateViewLayout(this, layoutParams);
    }

    public void onEnabledFeaturesChanged() {
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        int action = motionEvent.getAction();
        if (action == 0) {
            fadeIn();
            this.mDownX = rawX;
            this.mDownY = rawY;
            WindowManager.LayoutParams layoutParams = this.mCurrentLayoutParams;
            this.mRelativeToPointerDownX = layoutParams.x - rawX;
            this.mRelativeToPointerDownY = layoutParams.y - rawY;
            this.mListView.animate().translationX(ActionBarShadowController.ELEVATION_LOW);
            return false;
        }
        if (action != 1) {
            if (action == 2) {
                if (this.mIsDragging || hasExceededTouchSlop(this.mDownX, this.mDownY, rawX, rawY)) {
                    if (!this.mIsDragging) {
                        this.mIsDragging = true;
                        setRadius(this.mRadius, 1);
                        setInset(0, 0);
                    }
                    this.mTemporaryShapeType = isMovingTowardsScreenEdge(this.mAlignment, rawX, this.mDownX) ? 1 : 0;
                    int i = this.mRelativeToPointerDownX;
                    int i2 = this.mRelativeToPointerDownY;
                    this.mCurrentLayoutParams.x = MathUtils.constrain(rawX + i, getMinWindowX(), getMaxWindowX());
                    this.mCurrentLayoutParams.y = MathUtils.constrain(rawY + i2, 0, getMaxWindowY());
                    this.mWindowManager.updateViewLayout(this, this.mCurrentLayoutParams);
                    return false;
                }
                return false;
            } else if (action != 3) {
                return false;
            }
        }
        if (!this.mIsDragging) {
            if (isOvalShape()) {
                fadeOut();
                return false;
            }
            setShapeType(0);
            return true;
        }
        this.mIsDragging = false;
        int minWindowX = getMinWindowX();
        int maxWindowX = getMaxWindowX();
        WindowManager.LayoutParams layoutParams2 = this.mCurrentLayoutParams;
        int i3 = minWindowX;
        if (layoutParams2.x > (minWindowX + maxWindowX) / 2) {
            i3 = maxWindowX;
        }
        snapToLocation(i3, layoutParams2.y);
        setShapeType(this.mTemporaryShapeType);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public void onTargetsChanged(List<AccessibilityTarget> list) {
        fadeIn();
        this.mTargets.clear();
        this.mTargets.addAll(list);
        onEnabledFeaturesChanged();
        updateRadiusWith(this.mSizeType, this.mRadiusType, this.mTargets.size());
        updateScrollModeWith(hasExceededMaxLayoutHeight());
        setSystemGestureExclusion();
        fadeOut();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public final boolean onTouched(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int marginStartEndWith = getMarginStartEndWith(this.mLastConfiguration);
        Rect rect = new Rect(marginStartEndWith, this.mMargin, getLayoutWidth() + marginStartEndWith, this.mMargin + getLayoutHeight());
        if (action == 0 && rect.contains(x, y)) {
            this.mIsDownInEnlargedTouchArea = true;
        }
        if (this.mIsDownInEnlargedTouchArea) {
            if (action == 1 || action == 3) {
                this.mIsDownInEnlargedTouchArea = false;
            }
            int i = this.mMargin;
            motionEvent.setLocation(x - i, y - i);
            return this.mListView.dispatchTouchEvent(motionEvent);
        }
        return false;
    }

    public final WindowInsets onWindowInsetsApplied(WindowInsets windowInsets) {
        WindowMetrics currentWindowMetrics = this.mWindowManager.getCurrentWindowMetrics();
        if (!getDisplayInsets(currentWindowMetrics).toRect().equals(this.mDisplayInsetsRect)) {
            updateDisplaySizeWith(currentWindowMetrics);
            updateLocationWith(this.mPosition);
        }
        Rect rect = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.ime()).toRect();
        if (!rect.equals(this.mImeInsetsRect)) {
            if (isImeVisible(rect)) {
                this.mImeInsetsRect.set(rect);
            } else {
                this.mImeInsetsRect.setEmpty();
            }
            updateLocationWith(this.mPosition);
        }
        return windowInsets;
    }

    public final void setInset(int i, int i2) {
        InstantInsetLayerDrawable menuLayerDrawable = getMenuLayerDrawable();
        if (menuLayerDrawable.getLayerInsetLeft(0) == i && menuLayerDrawable.getLayerInsetRight(0) == i2) {
            return;
        }
        menuLayerDrawable.setLayerInset(0, i, 0, i2, 0);
    }

    public void setOnDragEndListener(OnDragEndListener onDragEndListener) {
        this.mOnDragEndListener = Optional.ofNullable(onDragEndListener);
    }

    public final void setRadius(float f, int i) {
        getMenuGradientDrawable().setCornerRadii(createRadii(f, i));
    }

    public void setShapeType(int i) {
        fadeIn();
        this.mShapeType = i;
        updateOffsetWith(i, this.mAlignment);
        setOnTouchListener(i == 0 ? null : new View.OnTouchListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AccessibilityFloatingMenuView.$r8$lambda$hlHMbmdr0GIrfeQ0H6KMI0Tdmno(AccessibilityFloatingMenuView.this, view, motionEvent);
            }
        });
        fadeOut();
    }

    public void setSizeType(int i) {
        fadeIn();
        this.mSizeType = i;
        updateItemViewWith(i);
        updateRadiusWith(i, this.mRadiusType, this.mTargets.size());
        updateLocationWith(this.mPosition);
        updateScrollModeWith(hasExceededMaxLayoutHeight());
        updateOffsetWith(this.mShapeType, this.mAlignment);
        setSystemGestureExclusion();
        fadeOut();
    }

    public final void setSystemGestureExclusion() {
        final Rect rect = new Rect(0, 0, getWindowWidth(), getWindowHeight());
        post(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AccessibilityFloatingMenuView.$r8$lambda$LBlSuzMtvTxjYf98YMGNhDwsxfo(AccessibilityFloatingMenuView.this, rect);
            }
        });
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        this.mIsShowing = true;
        this.mWindowManager.addView(this, this.mCurrentLayoutParams);
        setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return AccessibilityFloatingMenuView.m1397$r8$lambda$B43oUCI3YZtrOMeUwYzkG0Xpwo(AccessibilityFloatingMenuView.this, view, windowInsets);
            }
        });
        setSystemGestureExclusion();
    }

    @VisibleForTesting
    public void snapToLocation(final int i, final int i2) {
        this.mDragAnimator.cancel();
        this.mDragAnimator.removeAllUpdateListeners();
        this.mDragAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AccessibilityFloatingMenuView.m1396$r8$lambda$4g6BrRp1igrtsygj13wtIg2d1M(AccessibilityFloatingMenuView.this, i, i2, valueAnimator);
            }
        });
        this.mDragAnimator.start();
    }

    public void startTranslateXAnimation() {
        fadeIn();
        TranslateAnimation translateAnimation = new TranslateAnimation(1, ActionBarShadowController.ELEVATION_LOW, 1, this.mAlignment == 1 ? 0.5f : -0.5f, 1, ActionBarShadowController.ELEVATION_LOW, 1, ActionBarShadowController.ELEVATION_LOW);
        translateAnimation.setDuration(600L);
        translateAnimation.setRepeatMode(2);
        translateAnimation.setInterpolator(new OvershootInterpolator());
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setStartOffset(600L);
        this.mListView.startAnimation(translateAnimation);
    }

    public void stopTranslateXAnimation() {
        this.mListView.clearAnimation();
        fadeOut();
    }

    public final float transformCurrentPercentageXToEdge() {
        return ((double) calculateCurrentPercentageX()) < 0.5d ? 0.0f : 1.0f;
    }

    public final int transformToAlignment(float f) {
        return f < 0.5f ? 0 : 1;
    }

    public final void updateAccessibilityTitle(WindowManager.LayoutParams layoutParams) {
        layoutParams.accessibilityTitle = getResources().getString(17039594);
    }

    public final void updateColor() {
        getMenuGradientDrawable().setColor(getResources().getColor(R$color.accessibility_floating_menu_background));
    }

    public final void updateDimensions() {
        Resources resources = getResources();
        updateDisplaySizeWith(this.mWindowManager.getCurrentWindowMetrics());
        this.mMargin = resources.getDimensionPixelSize(R$dimen.accessibility_floating_menu_margin);
        this.mInset = resources.getDimensionPixelSize(R$dimen.accessibility_floating_menu_stroke_inset);
        this.mSquareScaledTouchSlop = MathUtils.sq(ViewConfiguration.get(getContext()).getScaledTouchSlop());
        updateItemViewDimensionsWith(this.mSizeType);
    }

    public final void updateDisplaySizeWith(WindowMetrics windowMetrics) {
        Rect bounds = windowMetrics.getBounds();
        Insets displayInsets = getDisplayInsets(windowMetrics);
        this.mDisplayInsetsRect.set(displayInsets.toRect());
        bounds.inset(displayInsets);
        this.mDisplayWidth = bounds.width();
        this.mDisplayHeight = bounds.height();
    }

    public final void updateInsetWith(int i, int i2) {
        int i3 = 0;
        int i4 = (i & 48) == 32 ? this.mInset : 0;
        int i5 = i2 == 0 ? i4 : 0;
        if (i2 == 1) {
            i3 = i4;
        }
        setInset(i5, i3);
    }

    public final void updateItemViewDimensionsWith(int i) {
        Resources resources = getResources();
        this.mPadding = resources.getDimensionPixelSize(i == 0 ? R$dimen.accessibility_floating_menu_small_padding : R$dimen.accessibility_floating_menu_large_padding);
        int dimensionPixelSize = resources.getDimensionPixelSize(i == 0 ? R$dimen.accessibility_floating_menu_small_width_height : R$dimen.accessibility_floating_menu_large_width_height);
        this.mIconWidth = dimensionPixelSize;
        this.mIconHeight = dimensionPixelSize;
    }

    public final void updateItemViewWith(int i) {
        updateItemViewDimensionsWith(i);
        this.mAdapter.setItemPadding(this.mPadding);
        this.mAdapter.setIconWidthHeight(this.mIconWidth);
        this.mAdapter.notifyDataSetChanged();
    }

    public final void updateListViewWith(Configuration configuration) {
        updateMarginWith(configuration);
        this.mListView.setElevation(getResources().getDimensionPixelSize(R$dimen.accessibility_floating_menu_elevation));
    }

    public final void updateLocationWith(Position position) {
        int transformToAlignment = transformToAlignment(position.getPercentageX());
        this.mCurrentLayoutParams.x = transformToAlignment == 1 ? getMaxWindowX() : getMinWindowX();
        this.mCurrentLayoutParams.y = Math.max(0, ((int) (position.getPercentageY() * getMaxWindowY())) - getInterval());
        this.mWindowManager.updateViewLayout(this, this.mCurrentLayoutParams);
    }

    public final void updateMarginWith(Configuration configuration) {
        int marginStartEndWith = getMarginStartEndWith(configuration);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mListView.getLayoutParams();
        int i = this.mMargin;
        layoutParams.setMargins(marginStartEndWith, i, marginStartEndWith, i);
        this.mListView.setLayoutParams(layoutParams);
    }

    public final void updateOffsetWith(int i, int i2) {
        float layoutWidth = getLayoutWidth() / 2.0f;
        if (i == 0) {
            layoutWidth = 0.0f;
        }
        ViewPropertyAnimator animate = this.mListView.animate();
        if (i2 != 1) {
            layoutWidth = -layoutWidth;
        }
        animate.translationX(layoutWidth);
    }

    public void updateOpacityWith(boolean z, float f) {
        this.mIsFadeEffectEnabled = z;
        this.mFadeOutValue = f;
        this.mFadeOutAnimator.cancel();
        float f2 = 1.0f;
        this.mFadeOutAnimator.setFloatValues(1.0f, this.mFadeOutValue);
        if (this.mIsFadeEffectEnabled) {
            f2 = this.mFadeOutValue;
        }
        setAlpha(f2);
    }

    public final void updateRadiusWith(int i, int i2, int i3) {
        float dimensionPixelSize = getResources().getDimensionPixelSize(getRadiusResId(i, i3));
        this.mRadius = dimensionPixelSize;
        setRadius(dimensionPixelSize, i2);
    }

    public final void updateScrollModeWith(boolean z) {
        this.mListView.setOverScrollMode(z ? 0 : 2);
    }

    public final void updateStrokeWith(int i, int i2) {
        updateInsetWith(i, i2);
        int i3 = 0;
        boolean z = (i & 48) == 32;
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.accessibility_floating_menu_stroke_width);
        if (z) {
            i3 = dimensionPixelSize;
        }
        getMenuGradientDrawable().setStroke(i3, resources.getColor(R$color.accessibility_floating_menu_stroke_dark));
    }
}